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
 * Creates randomized cars according to the restrictions of a specific genetic
 * algorithm.
 * 
 * @author Marco Maas
 * @version 1.0
 */
public class LinesAlgorithmDataFactory implements GeneticAlgorithmDataFactory {
	public int lineNumber;
	public float maxLength;
	public float maxHeight;
	public float maxLineLength;
	public boolean fixedLength, allWheel;

	/***
	 * simple constructor
	 * 
	 * @param maxLength
	 * @param maxHeight
	 * @param maxLineLength
	 * @param lineNumber
	 */
	public LinesAlgorithmDataFactory(float maxLength,
			float maxHeight, float maxLineLength, int lineNumber) {
		this.maxLength = maxLength;
		this.maxHeight = maxHeight;
		this.maxLineLength = maxLineLength;
		this.lineNumber = lineNumber;
		fixedLength = true;
		allWheel = true;
	}

	/***
	 * Create the genom of one random car according to the restrictions
	 * 
	 * @return the created genom
	 */
	public LinesAlgorithmData createData() {
		float[][] points = new float[lineNumber][2];

		for (int i = 0; i < lineNumber; i++) {
			int j = 1;
			points[i][j] = (float) (Math.random() * 2);
			j = 0;
			points[i][j] = (float) Math.random();
		}

		if (fixedLength)
			points[0][0] = 0;

		float[] lengths = new float[lineNumber];

		for (int i = 0; i < lineNumber; i++) {
			do {
				lengths[i] = (float) (Math.random());
			} while ((lengths[i] * maxLineLength + (points[i][1] - 1)
					* maxHeight / 2) > maxHeight / 2);
		}

		Vector<Vec2> vecPoints = new Vector<Vec2>();
		Vector<Float> vecLengths = new Vector<Float>();

		for (int i = 0; i < lineNumber; i++) {
			vecPoints.add(new Vec2(points[i][0], points[i][1]));
		}

		for (int i = 0; i < lineNumber; i++) {
			vecLengths.add(lengths[i]);
		}

		Vector<Integer> vecWheels = new Vector<Integer>();
		if (lineNumber != 3) {
			vecWheels.add(2);
			vecWheels.add(lineNumber - 1);
		} else {
			vecWheels.add(1);
			vecWheels.add(lineNumber);
		}
		return new LinesAlgorithmData(vecPoints, vecLengths, vecWheels.size(),
				vecWheels, this);

	}
}
