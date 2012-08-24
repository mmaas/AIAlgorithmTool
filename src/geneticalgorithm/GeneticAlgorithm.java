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

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;

import control.Algorithm;
import control.Control;
import view.OpenGLPlayTab;

import model.AlgorithmData;
import model.GeneticAlgorithmData;

/***
 * Core of the genetic algorithm.
 * 
 * @author Marco Maas
 * @version 1.0
 */
public class GeneticAlgorithm extends Algorithm {
	MutationOperator m = new MutationOperator(this);
	CrossoverOperator cross = new CrossoverOperator(this);
	GeneticAlgorithmPanel gap = new GeneticAlgorithmPanel(this);
	FitnessFunction fit = new FitnessFunction(this);
	SelectionOperator sel = new SelectionOperator(this);
	GeneticAlgorithmDataFactory fac = new GeneticAlgorithmDataFactory(this);
	public Control c;
	public float maxLength;
	public float maxHeight;
	public float maxLineLength;
	public int lineNumber;
	public int population;
	public int mutation;

	public boolean started, changed, fixedLength, allWheel;
	public boolean showBestcar;
	boolean showLatest;
	boolean showOverall;
	public boolean done;

	/***
	 * Constructor which takes arguments and sets default values, where there is
	 * no argument.
	 * 
	 * @param maxLength
	 *            maximum length of the cars
	 * @param maxHeight
	 *            maximum heigth of the cars
	 * @param population
	 *            population size
	 * @param maxLineLength
	 *            maximum y size of the lines
	 * @param lineNumber
	 *            count of lines
	 * @param c
	 *            a specific control from MVC
	 */
	public GeneticAlgorithm(float maxLength, float maxHeight, int population,
			int maxLineLength, int lineNumber, Control c) {
		this.maxLength = maxLength;
		this.maxHeight = maxHeight;
		this.maxLineLength = maxLineLength;
		this.lineNumber = lineNumber;
		this.c = c;
		this.population = population;
		mutation = 20;
		started = false;
		changed = false;
		fixedLength = true;
		allWheel = true;
		showBestcar = false;
		showLatest = false;
		showOverall = false;
		done = false;
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
		int i = 1;
		int j = 1;
		OpenGLPlayTab pT = c.registeredViews.get(0).pTab;
		pT.output.add("Generation " + i);
		float sum = 0, best = 0;
		while (aData.size() < population) {
			fac.createData(true);

			GeneticAlgorithmData gA = (GeneticAlgorithmData) aData
					.lastElement();
			
			fit.fitness(gA, best);

			if (changed) {
				aData.remove(aData.size() - 1);
				j--;
				changed = false;
			} else {
				pT.output.add("Car " + j + ": " + gA.fitness);
				pT.cars += 1;
				sum += gA.fitness;
			}
			Body b = c.p.physicWorld.getBodyList();

			while (b != null) {
				if (b.m_type == BodyType.DYNAMIC) {
					b.setActive(false);
					c.p.physicWorld.destroyBody(b);
				}
				b = b.getNext();
			}

			c.p.vehicles.clear();
			j++;
		}
		pT.output.add("Total fitness: " + sum);
		pT.output.add("Average fitness: " + sum/population);
		Vector<GeneticAlgorithmData> nextGeneration = new Vector<GeneticAlgorithmData>();
		Vector<Vec2> rating = new Vector<Vec2>();
		while (true) {
			nextGeneration.clear();
			rating.clear();
			c.registeredViews.get(0).pTab.outputAlgorithmInformation.clear();
			c.registeredViews.get(0).pTab.outputAlgorithmInformation
					.add("Generation " + (i+1));

			pT.output.add("Rate...");
			rating = rate(i, sum);

			pT.output.add("Select...");
			sel.selection(rating, nextGeneration);

			pT.output.add("Crossover...");
			cross.crossover(rating, nextGeneration);
			
			pT.output.add("Mutate...");
			m.mutate(nextGeneration);

			GeneticAlgorithmData bestCar = (GeneticAlgorithmData) (aData
					.get((int) rating.lastElement().x));
			best = bestCar.fitness;
			pT.output.add("Best: " + best);
			pT.output.add("******************************");
			
			sum = 0;
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
				fit.fitness((GeneticAlgorithmData) aData.lastElement(), best);
				if (!done) {
					pT.output.add("Car " + (j + k) + ": "
							+ nextGeneration.elementAt(k).fitness);
				}
				pT.cars += 1;
				sum += nextGeneration.elementAt(k).fitness;
				for (Body b : c.p.vehicles) {
					b.setActive(false);
					c.p.physicWorld.destroyBody(b);
				}
				c.p.vehicles.clear();
				if (done) {
					k--;
					String s = "The last car has finished the track.";
					if (!pT.output.lastElement().equals(s))
						pT.output.add(s);
				}
			}
			pT.output.add("Total fitness: " + sum);
			pT.output.add("Average fitness: " + sum/population);
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
		GeneticAlgorithmData ga;
		if (showLatest) {
			for (int i = (generation - 2) * population; i < (generation - 2)
					* population + population; i++) {
				ga = (GeneticAlgorithmData) aData.get(i);
				if (ga.distance > bestDistance) {
					bestDistance = ga.distance;
					best = i;
				}
			}
			ga = (GeneticAlgorithmData) aData.get(best);
			c.registeredViews.get(0).pTab.output
					.add(("Show best car " + (best + 1)
							+ " with distance " + bestDistance + " again"));
			fit.fitness(ga, bestDistance);
			showLatest = false;
		} else if (showOverall) {
			for (int i = 0; i < (generation - 2) * population + population; i++) {
				ga = (GeneticAlgorithmData) aData.get(i);
				if (ga.distance > bestDistance) {
					bestDistance = ga.distance;
					best = i;
				}
			}
			ga = (GeneticAlgorithmData) aData.get(best);
			c.registeredViews.get(0).pTab.output
					.add(("Show total best car " + (best + 1)
							+ " with distance " + bestDistance + " again"));
			fit.fitness(ga, bestDistance);
			showOverall = false;
		}
	}

	/***
	 * Normalizes fitness from a generation to a specific total fitness.
	 * 
	 * @param generation
	 *            generation which should be rated
	 * @param fitness
	 *            total fitness of the generation which should be rated
	 * @return sorted vector with genoms and their normalized rating in percent
	 */
	private Vector<Vec2> rate(int generation, float fitness) {
		GeneticAlgorithmData gA;
		Vector<Vec2> rating = new Vector<Vec2>();
		for (int i = (generation - 1) * population; i < generation * population; i++) {
			gA = (GeneticAlgorithmData) aData.get(i);
			rating.add(new Vec2(i, gA.fitness / fitness));
		}
		java.util.Collections.sort(rating, new Vec2Comparator());
		// System.out.print((int) rating.firstElement().x + ","
		// + rating.firstElement().y + " ");
		for (int i = 1; i < population; i++) {
			rating.get(i).y = rating.get(i).y + rating.get(i - 1).y;
			// System.out.print((int) rating.get(i).x + "," + rating.get(i).y
			// + " ");
		}
		return rating;
	}

}
