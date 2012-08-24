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
 * Creates randomized cars according to the restrictions of a specific genetic
 * algorithm.
 * 
 * @author Marco Maas
 * @version 1.0
 */
public class GeneticAlgorithmDataFactory {
	GeneticAlgorithm g;

	/***
	 * simple constructor
	 * 
	 * @param g
	 *            genetic algorithm
	 */
	public GeneticAlgorithmDataFactory(GeneticAlgorithm g) {
		this.g = g;
	}

	/***
	 * Create the genom of one random car according to the restrictions
	 * 
	 * @param addDirect
	 *            should the car directly be added to the genom list of the
	 *            genetic algorithm
	 * @return the created genom
	 */
	public GeneticAlgorithmData createData(boolean addDirect) {
		float[][] points = new float[g.lineNumber][2];

		for (int i = 0; i < g.lineNumber; i++) {
			int j = 1;
			points[i][j] = (float) (Math.random() * 2);
			j = 0;
			points[i][j] = (float) Math.random();
		}

		if (g.fixedLength)
			points[0][0] = 0;

		float[] lengths = new float[g.lineNumber];

		for (int i = 0; i < g.lineNumber; i++) {
			do {
				lengths[i] = (float) (Math.random());
			} while ((lengths[i] * g.maxLineLength + (points[i][1] - 1)
					* g.maxHeight / 2) > g.maxHeight / 2);
		}

		Vector<Vec2> vecPoints = new Vector<Vec2>();
		Vector<Float> vecLengths = new Vector<Float>();

		for (int i = 0; i < g.lineNumber; i++) {
			vecPoints.add(new Vec2(points[i][0], points[i][1]));
		}

		for (int i = 0; i < g.lineNumber; i++) {
			vecLengths.add(lengths[i]);
		}

		Vector<Integer> vecWheels = new Vector<Integer>();
		if (g.lineNumber != 3) {
			vecWheels.add(2);
			vecWheels.add(g.lineNumber - 1);
		} else {
			vecWheels.add(1);
			vecWheels.add(g.lineNumber);
		}

		if (!addDirect) {
			return new GeneticAlgorithmData(vecPoints, vecLengths,
					vecWheels.size(), vecWheels, g);
		} else {
			g.aData.add(new GeneticAlgorithmData(vecPoints, vecLengths,
					vecWheels.size(), vecWheels, g));
			GeneticAlgorithmData toReturn = (GeneticAlgorithmData) g.aData
					.lastElement();
			return toReturn;
		}
	}
}
