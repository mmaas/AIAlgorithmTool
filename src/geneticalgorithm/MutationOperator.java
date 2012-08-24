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

import java.util.BitSet;
import java.util.Random;
import java.util.Vector;

import model.GeneticAlgorithmData;

/***
 * Implemented mutation operator.
 * 
 * @author Marco Maas
 * @version 1.0
 */
public class MutationOperator implements Mutation {
	GeneticAlgorithm g;

	/***
	 * simple constructor
	 * 
	 * @param g
	 *            genetic algorithm
	 */
	public MutationOperator(GeneticAlgorithm g) {
		this.g = g;
	}

	public void mutate(Vector<GeneticAlgorithmData> nextGeneration) {
		// TODO Auto-generated method stub
		Random rg = new Random();
		float r;
		int toChange, count = 1;
		boolean fail = false;
		BitSet bits = null;
		for (GeneticAlgorithmData ga : nextGeneration) {
			r = rg.nextFloat();
			if (r <= (float) g.mutation / 100f) {
				bits = ga.getFixedInt();
				toChange = rg.nextInt(g.lineNumber * 3);

				int trys = 0;
				if (toChange == 0 && g.fixedLength) {
					do {
						toChange = rg.nextInt(g.lineNumber * 3);
					} while (toChange == 0);
				}
				do {
					if ((trys % 100) == 99) {
						toChange = rg.nextInt(g.lineNumber * 3);
					}
					if (trys > g.lineNumber*300) {
						fail = true;
						break;
					}
					if ((toChange - 1) % 3 == 0)
						r = rg.nextFloat() * 2;
					else
						r = rg.nextFloat();
					BitSet newBits = ga.toFixedInt(r);
					for (int i = 0; i < 10; i++) {
						bits.set(toChange * 10 + i, newBits.get(i));
					}
					trys++;
				} while (!ga.isValid(bits));
				if (!fail) {
					ga.setFixedInt(bits);
					if (toChange % 3 == 0)
						g.c.registeredViews.get(0).pTab.outputAlgorithmInformation
								.add("Car " + count+" line "+((toChange/3)+1)+" x value mutated.");
					if ((toChange - 1) % 3 == 0)
						g.c.registeredViews.get(0).pTab.outputAlgorithmInformation
								.add("Car " + count+" line "+((toChange/3)+1)+" y value mutated.");
					if ((toChange - 2) % 3 == 0)
						g.c.registeredViews.get(0).pTab.outputAlgorithmInformation
								.add("Car " + count+" line "+((toChange/3)+1)+" length mutated.");
				} else {
					fail = false;
				}
			}
			count++;
		}
	}
}