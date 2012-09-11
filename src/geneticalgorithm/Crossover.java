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

/***
 * Interface for crossover operations.
 * 
 * @author Marco Maas
 * @version 1.0
 */

public interface Crossover {
	/***
	 * Crossover takes a rating and creates a new generation to be tested
	 * 
	 * @param rating
	 *            (fitness sorted Vector:oldGeneration plus fitness rating)
	 * @param nextGeneration
	 *            nextGeneration to be tested
	 */
	public void crossover(int lastGeneration,Vector<GeneticAlgorithmData> nextGeneration);
}
