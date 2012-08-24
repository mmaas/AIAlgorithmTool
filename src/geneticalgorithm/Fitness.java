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

import model.GeneticAlgorithmData;

/***
 * Interface for fitness functions.
 * 
 * @author Marco Maas
 * @version 1.0
 */
public interface Fitness {
	/***
	 * A fitness function takes a specific genom and the best result from the
	 * last generation, tests the genom on the racing track and sets the fitness
	 * to a specific value depending on the best fitness from the last
	 * generation
	 * 
	 * @param g
	 *            genom of car to bested
	 * @param best
	 *            best fitness from last generation
	 */
	public void fitness(GeneticAlgorithmData g, float best);
}
