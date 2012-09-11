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

import model.LinesAlgorithmData;

import org.jbox2d.common.Vec2;
/***
 * Implemented Roulette Wheel Selection.
 * 
 * @author Marco Maas
 * @version 1.0
 */
public class RouletteWheelSelection {
	GeneticAlgorithm g;
	
	public RouletteWheelSelection(GeneticAlgorithm g) {
		this.g = g;
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
	
	/***
	 * Normalizes fitness from a generation to a specific total fitness.
	 * 
	 * @param generation
	 *            generation which should be rated
	 * @param fitness
	 *            total fitness of the generation which should be rated
	 * @return sorted vector with genoms and their normalized rating in percent
	 */
	public Vector<Vec2> rate(int generation, float fitness) {
		LinesAlgorithmData gA;
		Vector<Vec2> rating = new Vector<Vec2>();
		for (int i = (generation - 1) * g.population; i < generation * g.population; i++) {
			gA = (LinesAlgorithmData) g.aData.get(i);
			rating.add(new Vec2(i, gA.fitness / fitness));
		}
		java.util.Collections.sort(rating, new Vec2Comparator());
		// System.out.print((int) rating.firstElement().x + ","
		// + rating.firstElement().y + " ");
		for (int i = 1; i < g.population; i++) {
			rating.get(i).y = rating.get(i).y + rating.get(i - 1).y;
			// System.out.print((int) rating.get(i).x + "," + rating.get(i).y
			// + " ");
		}
		return rating;
	}
}
