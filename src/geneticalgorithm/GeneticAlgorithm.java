/***
 * Copyright 2012 Marco Maas
 *
 * This file is part of AIAlgorithmTool.
 *
 *   AIAlgorithmTool is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 *   AIAlgorithmTool is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *  along with AIAlgorithmTool.  If not, see <http://www.gnu.org/licenses/>.
 */
package geneticalgorithm;

import java.util.Vector;

import control.Algorithm;
import control.Control;
import view.OpenGLPlayTab;

import model.AlgorithmData;
import model.GeneticAlgorithmData;
import model.LinesAlgorithmData;

/***
 * Core of the genetic algorithm.
 * 
 * @author Marco Maas
 * @version 1.0
 */
public class GeneticAlgorithm extends Algorithm {
	GeneticAlgorithmDataFactory fac;
	Mutation m;
	Crossover cross;
	GeneticAlgorithmPanel gap;
	Fitness fit;
	Selection sel;

	public int population;
	public int mutation;
	public int crType, crAlgorithm;
	public boolean started, changed;
	public boolean showBestcar;
	float sumGeneration;
	float best;
	boolean showLatest;
	boolean showOverall;
	public boolean done;

	/***
	 * Constructor which takes arguments and sets default values, where there is
	 * no argument.
	 * 
	 * @param population
	 *            population size
	 * @param fac
	 *            genom represantation factory
	 * @param c
	 *            control
	 */
	public GeneticAlgorithm(int population, GeneticAlgorithmDataFactory fac,
			Control c) {
		super(c);
		this.population = population;
		this.fac = fac;
		crType = 0;
		crAlgorithm = 0;
		mutation = 20;
		sumGeneration = 0;
		best = 0;
		started = false;
		changed = false;
		showBestcar = false;
		showLatest = false;
		showOverall = false;
		done = false;
		m = new MutationOperator(this);
		cross = new CrossoverOperator(this);
		gap = new GeneticAlgorithmPanel(this);
		fit = new FitnessFunction(this);
		sel = new SelectionOperator(this);
		this.p = gap.createPanel();
	}

	/***
	 * simple getter for genoms of all previous cars
	 * 
	 * @param position
	 *            get which car
	 * @return genom
	 */
	public AlgorithmData getaData(int position) {
		return super.aData.get(position);
	}

	/***
	 * adds a genom to the pool
	 * 
	 * @param aData
	 *            genom to be added
	 */
	public void addaData(AlgorithmData aData) {
		super.aData.add(aData);
	}

	public void evaluate() {
		float sumDistance = 0f;
		int i = 1;
		int j = 1;
		OpenGLPlayTab pT = c.registeredViews.get(0).pTab;
		pT.output.add("Generation " + i);
		while (aData.size() < population) {
			GeneticAlgorithmData gA = fac.createData();
			aData.add(gA);

			fit.fitness(gA);

			if (changed) {
				aData.remove(aData.size() - 1);
				j--;
				changed = false;
			} else {
				pT.output.add("Car " + j + ": " + gA.fitness);
				pT.cars += 1;
				sumGeneration += gA.fitness;
				sumDistance += gA.distance;
			}
			c.p.removeVehicle();
			j++;
		}
		pT.output.add("Total fitness: " + sumGeneration);
		pT.output.add("Average fitness: " + sumGeneration / population);
		Vector<GeneticAlgorithmData> nextGeneration = new Vector<GeneticAlgorithmData>();
		while (true) {
			nextGeneration.clear();
			c.registeredViews.get(0).pTab.outputAlgorithmInformation.clear();
			c.registeredViews.get(0).pTab.outputAlgorithmInformation
					.add("Generation " + (i + 1));

			pT.output.add("Select...");
			sel.selection(i, nextGeneration);

			pT.output.add("Crossover...");
			cross.crossover(i, nextGeneration);

			pT.output.add("Mutate...");
			m.mutate(nextGeneration);

			LinesAlgorithmData bestCar = (LinesAlgorithmData) nextGeneration
					.firstElement();
			best = bestCar.fitness;
			pT.output.add("Best fitness: " + best);
			pT.output.add("******************************");

			System.out.println(i + " " + sumDistance
					/ population + " " + bestCar.distance);

			sumGeneration = 0;
			sumDistance = 0;
			i++;
			j = (i - 1) * population + 1;
			pT.output.add("Generation " + i);
			gap.b2.setEnabled(true);
			gap.b3.setEnabled(true);
			for (int k = 0; k < nextGeneration.size(); k++) {
				aData.add(nextGeneration.elementAt(k));
				if (showBestcar) {
					c.registeredViews.get(0).toggleStatus = true;
					showBest(i);
					showBestcar = false;
				}
				fit.fitness((LinesAlgorithmData) aData.lastElement());
//				if (!done) {
					pT.output.add("Car " + (j + k) + ": "
							+ nextGeneration.elementAt(k).fitness);
//				}
				pT.cars += 1;
				sumGeneration += nextGeneration.elementAt(k).fitness;
				sumDistance += nextGeneration.elementAt(k).distance;
				c.p.removeVehicle();
//				if (done) {
//					k--;
//					String s = "The last car has finished the track.";
//					if (!pT.output.lastElement().equals(s))
//						pT.output.add(s);
//				}
			}
			pT.output.add("Total fitness: " + sumGeneration);
			pT.output.add("Average fitness: " + sumGeneration / population);
		}

	}

	/***
	 * method to show best car from lastgeneration or overall bestcar
	 * 
	 * @param generation
	 *            bestcar from which generation
	 */
	private void showBest(int generation) {
		float bestDistance = 0f;
		int best = 0;
		LinesAlgorithmData ga;
		if (showLatest) {
			for (int i = (generation - 2) * population; i < (generation - 2)
					* population + population; i++) {
				ga = (LinesAlgorithmData) aData.get(i);
				if (ga.distance > bestDistance) {
					bestDistance = ga.distance;
					best = i;
				}
			}
			ga = (LinesAlgorithmData) aData.get(best);
			c.registeredViews.get(0).pTab.output
					.add(("Show best car " + (best + 1) + " with distance "
							+ bestDistance + " again"));
			fit.fitness(ga);
			showLatest = false;
		} else if (showOverall) {
			for (int i = 0; i < (generation - 2) * population + population; i++) {
				ga = (LinesAlgorithmData) aData.get(i);
				if (ga.distance > bestDistance) {
					bestDistance = ga.distance;
					best = i;
				}
			}
			ga = (LinesAlgorithmData) aData.get(best);
			c.registeredViews.get(0).pTab.output
					.add(("Show total best car " + (best + 1)
							+ " with distance " + bestDistance + " again"));
			fit.fitness(ga);
			showOverall = false;
		}
	}

}
