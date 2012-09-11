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

import model.GeneticAlgorithmData;
import model.LinesAlgorithmData;

import org.jbox2d.common.Vec2;

/***
 * Implemented selection operator. Roulette Wheel selection.
 * 
 * @author Marco Maas
 * @version 1.0
 */
public class SelectionOperator implements Selection {
	GeneticAlgorithm g;
	RouletteWheelSelection roulette;
	/***
	 * simple constructor
	 * 
	 * @param g
	 *            genetic algorithm
	 */
	public SelectionOperator(GeneticAlgorithm g) {
		this.g = g;
		roulette = new RouletteWheelSelection(g);
	}

	public void selection(int lastGeneration,Vector<GeneticAlgorithmData> nextGeneration) {
		Vector<Vec2> rating = roulette.rate(lastGeneration, g.sumGeneration);
		final CrossoverOperator cross = (CrossoverOperator) g.cross;
		int count = (int) ((1f - cross.crossover / 100f) * g.population);
		nextGeneration.add((GeneticAlgorithmData) g.aData.get((int) rating.lastElement().x));
		g.c.registeredViews.get(0).pTab.outputAlgorithmInformation
				.add(nextGeneration.size()+". "+"Best car " + (int) (rating.lastElement().x + 1)
						+ " is added.");
		count--;
		int select;
		float r;
		while (count > 0) {
			r = (float) Math.random();
			select = roulette.select(r, rating);
			nextGeneration.add((LinesAlgorithmData) g.aData.get(select));
			g.c.registeredViews.get(0).pTab.outputAlgorithmInformation
			.add(nextGeneration.size()+". "+"Car " + (select + 1) + " is added.");
			count--;
		}

	}

}
