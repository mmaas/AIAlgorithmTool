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
package model;

import geneticalgorithm.GeneticAlgorithmDataFactory;
import java.util.BitSet;

/***
 * Abstract class for genoms.
 * 
 * @author Marco Maas
 * @version 1.0
 */
public abstract class GeneticAlgorithmData extends AlgorithmData {
	public float distance, fitness;
	public BitSet fixedInt;
	GeneticAlgorithmDataFactory factory;

	/***
	 * creates fixedpoint integer from a floating point number x
	 * 
	 * @param x
	 *            float x
	 * @return fixedpoint integer representation of x
	 */
	public BitSet toFixedInt(float x) {
		// TODO Auto-generated method stub
		BitSet bits = new BitSet();
		float f = x;
		int count = -1;
		while (count < 9) {
			if (f > Math.pow(2, -count)) {
				f -= Math.pow(2, -count);
				bits.set(count + 1);
			}
			count += 1;
		}
		// System.out.println(x + " " + bits + " " + toFloat(bits));
		return bits;
	}

	public BitSet getFixedInt() {
		return fixedInt;
	}

	/***
	 * Sets genom to a fixed integer representation and creates float
	 * representation from it
	 * 
	 * @param fixedInt
	 *            genom
	 */
	public void setFixedInt(BitSet fixedInt) {
		this.fixedInt = fixedInt;
		createFloatRepresentation();
	}

	/***
	 * creates floating point representation from a given genom
	 */
	abstract void createFloatRepresentation();

	/***
	 * tests if the car is in the at the genetic algorithm defined boundaries
	 * 
	 * @param bits
	 *            fixedpoint representation
	 * @return isvalid
	 */
	public abstract boolean isValid(BitSet bits);
}
