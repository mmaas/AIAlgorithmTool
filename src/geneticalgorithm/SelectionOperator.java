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

import org.jbox2d.common.Vec2;

/***
 * Implemented selection operator. Roulette Wheel selection.
 * 
 * @author Marco Maas
 * @version 1.0
 */
public class SelectionOperator implements Selection {
	GeneticAlgorithm g;

	/***
	 * simple constructor
	 * 
	 * @param g
	 *            genetic algorithm
	 */
	public SelectionOperator(GeneticAlgorithm g) {
		this.g = g;
	}

	public void selection(Vector<Vec2> rating,
			Vector<GeneticAlgorithmData> nextGeneration) {
		int count = (int) ((1f - g.cross.crossover / 100f) * g.population);
		nextGeneration.add((GeneticAlgorithmData) g.aData.get((int) rating.lastElement().x));
		g.c.registeredViews.get(0).pTab.outputAlgorithmInformation
				.add(nextGeneration.size()+". "+"Best car " + (int) (rating.lastElement().x + 1)
						+ " is added.");
		count--;
		int select;
		float r;
		while (count > 0) {
			r = (float) Math.random();
			select = select(r, rating);
			nextGeneration.add((GeneticAlgorithmData) g.aData.get(select));
			g.c.registeredViews.get(0).pTab.outputAlgorithmInformation
			.add(nextGeneration.size()+". "+"Car " + (select + 1) + " is added.");
			count--;
		}

	}

	/***
	 * Roulette wheel selection.
	 * 
	 * @param r
	 *            random number between zero and 1
	 * @param rating
	 *            sorted list of normalized rated genoms
	 * @return
	 */
	int select(float r, Vector<Vec2> rating) {
		// TODO Auto-generated method stub
		for (int i = 0; i < rating.size(); i++) {
			if (rating.get(i).y > r)
				return (int) rating.get(i).x;
		}
		return 0;
	}

}
