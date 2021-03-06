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
import model.LinesAlgorithmData;

import org.jbox2d.common.Vec2;

/***
 * Crossover operation. Types: 2Point,1Point,Uniform and random
 * 
 * @author Marco Maas
 * @version 1.0
 */
public class CrossoverOperator implements Crossover {
	GeneticAlgorithm g;
	public boolean fixedMask;
	BitSet globalMask = null;
	public int crossover = 75;
	RouletteWheelSelection roulette;

	/***
	 * Simple constructor which sets default values
	 * 
	 * @param g
	 *            geneticalgorithm
	 */
	public CrossoverOperator(GeneticAlgorithm g) {
		this.g = g;
		roulette = new RouletteWheelSelection(g);
		
		fixedMask = false;
	}

	public void crossover(int lastGeneration,Vector<GeneticAlgorithmData> nextGeneration) {
		Vector<Vec2> rating = roulette.rate(lastGeneration, g.sumGeneration);
		LinesAlgorithmDataFactory factory = (LinesAlgorithmDataFactory)g.fac;
		if (globalMask == null) {
			globalMask = generateBitmask();
		} else if (!fixedMask) {
			globalMask = generateBitmask();
		} else {
			// keep old bitmask
		}
		BitSet child1, child2;
		LinesAlgorithmData parent1, parent2;
		Vector<Integer> vecWheels = new Vector<Integer>();
		if (factory.lineNumber != 3) {
			vecWheels.add(2);
			vecWheels.add(factory.lineNumber - 1);
		} else {
			vecWheels.add(1);
			vecWheels.add(factory.lineNumber);
		}

		int select1, select2;
		float r;
		while (nextGeneration.size() < g.population) {
			do {
				r = (float) Math.random();
				select1 = roulette.select(r, rating);
				r = (float) Math.random();
				select2 = roulette.select(r, rating);
			} while (select1 == select2);
			parent1 = (LinesAlgorithmData) g.aData.get(select1);
			parent2 = (LinesAlgorithmData) g.aData.get(select2);
			child1 = applyBitmask(parent1.getFixedInt(), parent2.getFixedInt(),
					globalMask);
			child2 = applyBitmask(parent2.getFixedInt(), parent1.getFixedInt(),
					globalMask);
			if (parent1.isValid(child1)) {
				nextGeneration.add(new LinesAlgorithmData(vecWheels.size(),
						vecWheels, child1, factory));
				g.c.registeredViews.get(0).pTab.outputAlgorithmInformation
						.add(nextGeneration.size()+". "+"Child 1 of " + (select1 + 1) + " and "
								+ (select2 + 1) + " is added.");
			}
			if (nextGeneration.size() < g.population)
				if (parent1.isValid(child2)) {
					nextGeneration.add(new LinesAlgorithmData(vecWheels
							.size(), vecWheels, child2, factory));
					g.c.registeredViews.get(0).pTab.outputAlgorithmInformation
							.add(nextGeneration.size()+". "+"Child 2 of " + (select1 + 1) + " und "
									+ (select2 + 1) + " is added.");
				}
		}
	}

	/***
	 * Method which applies a crossover bitmask to a pair of genoms in 10bit
	 * fixedpoint representation
	 * 
	 * @param parent1
	 *            first parent
	 * @param parent2
	 *            second parent
	 * @param mask
	 *            mask to be applied
	 * @return child as BitSet in fixedpoint representation
	 */
	private BitSet applyBitmask(BitSet parent1, BitSet parent2, BitSet mask) {
		BitSet children = new BitSet();
		LinesAlgorithmDataFactory factory = (LinesAlgorithmDataFactory)g.fac;
		for (int i = 0; i < factory.lineNumber * 30; i++) {
			children.set(i, (parent1.get(i) && mask.get(i)) || (parent2.get(i)
					&& (!mask.get(i))));
		}
		return children;
	}

	/***
	 * Creates bitmask, depending on value of crAlgorithm
	 * 
	 * @return bitmask as BitSet
	 */
	private BitSet generateBitmask() {
		BitSet mask = null;
		if (g.crAlgorithm == 0) {
			mask = generate2PointBitmask();
		}
		if (g.crAlgorithm == 1) {
			mask = generate1PointBitmask();
		}
		if (g.crAlgorithm == 2) {
			mask = generateUniformBitmask();
		}
		if (g.crAlgorithm == 3) {
			mask = generateRandomBitmask();
		}
		return mask;
	}

	/***
	 * Randomized 2Point bitmask is created, depending on crossover type crType.
	 * A whole point, x coordinate of one point, y coordinate of one point,
	 * length of one point or whole genom are the target of crossover.
	 * 
	 * @return a new 2Point bitmask
	 */
	private BitSet generate2PointBitmask() {
		// TODO Auto-generated method stub
		LinesAlgorithmDataFactory factory = (LinesAlgorithmDataFactory)g.fac;
		BitSet mask = new BitSet();
		int k, start, end;
		Random rg = new Random();

		if (g.crType == 0) {
			k = rg.nextInt(factory.lineNumber);
			start = rg.nextInt(15) + 1;
			end = start + 15;
			mask.set(k * 30 + start, k * 30 + end, true);
		} else if (g.crType == 1) {
			k = rg.nextInt(factory.lineNumber);

			start = rg.nextInt(5) + 1;
			end = start + 5;
			mask.set(k * 30 + start, k * 30 + end, true);

		} else if (g.crType == 2) {
			k = rg.nextInt(factory.lineNumber);
			start = rg.nextInt(5) + 1;
			end = start + 5;
			mask.set(k * 30 + 10 + start, k * 30 + 10 + end, true);

		} else if (g.crType == 3) {
			k = rg.nextInt(factory.lineNumber);
			start = rg.nextInt(5) + 1;
			end = start + 5;
			mask.set(k * 30 + 20 + start, k * 30 + 20 + end, true);
		} else if (g.crType == 4) {
			start = rg.nextInt(factory.lineNumber * 15) + 1;
			end = start + (factory.lineNumber * 15);
			mask.set(start, end, true);
		}
		return mask;
	}

	/***
	 * Randomized 1Point bitmask is created, depending on crossover type crType.
	 * A whole point, x coordinate of one point, y coordinate of one point,
	 * length of one point or whole genom are the target of crossover.
	 * 
	 * @return a new 1Point bitmask
	 */
	private BitSet generate1PointBitmask() {
		// TODO Auto-generated method stub
		LinesAlgorithmDataFactory factory = (LinesAlgorithmDataFactory)g.fac;
		BitSet mask = new BitSet();
		int k, point;
		Random rg = new Random();

		if (g.crType == 0) {
			k = rg.nextInt(factory.lineNumber);
			point = rg.nextInt(30) + 1;
			mask.set(k * 30, k * 30 + point, true);
		} else if (g.crType == 1) {
			k = rg.nextInt(factory.lineNumber);

			point = rg.nextInt(10) + 1;
			mask.set(k * 30, k * 30 + point, true);
		} else if (g.crType == 2) {
			k = rg.nextInt(factory.lineNumber);
			point = rg.nextInt(10) + 1;
			mask.set(k * 30 + 10, k * 30 + 10 + point, true);
		} else if (g.crType == 3) {
			k = rg.nextInt(factory.lineNumber);
			point = rg.nextInt(10) + 1;
			mask.set(k * 30 + 20, k * 30 + 20 + point, true);
		} else if (g.crType == 4) {
			point = rg.nextInt(factory.lineNumber * 30) + 1;
			mask.set(0, point, true);
		}

		return mask;
	}

	/***
	 * Randomized uniform bitmask is created, depending on crossover type
	 * crType. A whole point, x coordinate of one point, y coordinate of one
	 * point, length of one point or whole genom are the target of crossover.
	 * 
	 * @return a new uniform bitmask
	 */
	private BitSet generateUniformBitmask() {
		// TODO Auto-generated method stub
		return null;
	}

	/***
	 * random bitmask is created, depending on crossover type crType. A whole
	 * point, x coordinate of one point, y coordinate of one point, length of
	 * one point or whole genom are the target of crossover.
	 * 
	 * @return a new random bitmask
	 */
	private BitSet generateRandomBitmask() {
		// TODO Auto-generated method stub
		LinesAlgorithmDataFactory factory = (LinesAlgorithmDataFactory)g.fac;
		BitSet mask = new BitSet();
		float r;
		int k;
		Random rg = new Random();
		if (g.crType == 0) {

			k = rg.nextInt(factory.lineNumber);
			for (int i = k * 30; i < (k + 1) * 30; i++) {
				r = (float) Math.random();
				if (r < 0.5f)
					mask.set(i, false);
				else
					mask.set(i, true);
			}
		} else if (g.crType == 1) {
			k = rg.nextInt(factory.lineNumber);
			for (int i = k * 30; i < k * 30 + 10; i++) {
				r = (float) Math.random();
				if (r < 0.5f)
					mask.set(i, false);
				else
					mask.set(i, true);
			}
		} else if (g.crType == 2) {
			k = rg.nextInt(factory.lineNumber);
			for (int i = k * 30 + 10; i < k * 30 + 20; i++) {
				r = (float) Math.random();
				if (r < 0.5f)
					mask.set(i, false);
				else
					mask.set(i, true);
			}
		} else if (g.crType == 3) {
			k = rg.nextInt(factory.lineNumber);
			for (int i = k * 30 + 20; i < (k + 1) * 30; i++) {
				r = (float) Math.random();
				if (r < 0.5f)
					mask.set(i, false);
				else
					mask.set(i, true);
			}
		} else if (g.crType == 4) {
			for (int i = 0; i < factory.lineNumber * 30; i++) {
				r = rg.nextFloat();
				if (r < 0.5f)
					mask.set(i, false);
				else
					mask.set(i, true);
			}
		}

		return mask;
	}

	/***
	 * method which prints a bitset to the console
	 * 
	 * @param set
	 *            bitmask to be printed
	 */
	public void printBitset(BitSet set) {
		LinesAlgorithmDataFactory factory = (LinesAlgorithmDataFactory)g.fac;
		if (set == null) {
			return;
		}
		for (int i = 0; i < factory.lineNumber * 30; i++) {
			if (i % 10 == 0 && i!=0) {
				System.out.print(".");
			}
			if (i % 30 == 0 && i!=0) {
				System.out.print(".");
			}
			if (set.get(i)) {
				System.out.print(1);
			} else {
				System.out.print(0);
			}
		}
		System.out.print("\n");
	}

	/***
	 * A method which tests all bitmask creation methods and prints their
	 * results
	 */
	void testBitmasks() {
		BitSet set = null;
		for (int i = 0; i < 4; i++) {
			g.crAlgorithm = i;
			for (int j = 0; j < 5; j++) {
				System.out.print(i + "" + j);
				g.crType = j;
				set = generateBitmask();
				printBitset(set);
			}
		}
	}

}
