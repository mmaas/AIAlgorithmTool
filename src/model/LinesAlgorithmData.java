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
import geneticalgorithm.LinesAlgorithmDataFactory;

import java.util.BitSet;
import java.util.Vector;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;

/***
 * GeneticAlgorithm data, which describes a car genom with floating point and
 * fixpoint integer representation
 * 
 * @author Marco Maas
 * @version 1.0
 */
public class LinesAlgorithmData extends GeneticAlgorithmData {
	Vector<Vec2> points;
	Vector<Float> lengths;
	int wheelsCount;
	Vector<Integer> wheelPositions;

	/***
	 * Constructor with all floating point information needed. It creates the
	 * fixedpoint integer representation and stores both.
	 * 
	 * @param points	points of the genom
	 * @param lenghts	lengths of the lines
	 * @param wheelsCount	wheel number
	 * @param wheelPosition	wheel positions
	 * @param factory	belonging factory
	 */
	public LinesAlgorithmData(Vector<Vec2> points, Vector<Float> lenghts,
			int wheelsCount, Vector<Integer> wheelPosition,
			GeneticAlgorithmDataFactory factory) {
		this.wheelsCount = wheelsCount;
		this.wheelPositions = wheelPosition;
		this.points = points;
		this.lengths = lenghts;
		this.factory = (LinesAlgorithmDataFactory) factory;
		fixedInt = createFixedIntRepresentation();
	}

	/***
	 * Constructor with all fixedpoint point information needed. It creates the
	 * floating point representation and stores both.
	 * 
	 * @param wheelsCount
	 *            how much wheels
	 * @param wheelPosition
	 *            at which points are these wheels
	 * @param fixedInt
	 *            genom in fixedpoint
	 * @param factory
	 *            belonging factory
	 */
	public LinesAlgorithmData(int wheelsCount, Vector<Integer> wheelPosition,
			BitSet fixedInt, GeneticAlgorithmDataFactory factory) {
		this.wheelsCount = wheelsCount;
		this.wheelPositions = wheelPosition;
		points = new Vector<Vec2>();
		lengths = new Vector<Float>();
		this.fixedInt = fixedInt;
		this.factory = (LinesAlgorithmDataFactory) factory;
		createFloatRepresentation();
	}

	/***
	 * Creates a fixedpoint integer representation out of the floating points
	 * 
	 * @return BitSet in 10bit fixed point integer
	 */
	private BitSet createFixedIntRepresentation() {
		// TODO Auto-generated method stub
		BitSet bits = new BitSet();
		BitSet bitX, bitY, bitL;
		for (int i = 0; i < points.size(); i++) {
			bitX = toFixedInt(points.get(i).x);
			append(bits, bitX, 30 * i);
			bitY = toFixedInt(points.get(i).y);
			append(bits, bitY, 10 + 30 * i);
			bitL = toFixedInt(lengths.get(i));
			append(bits, bitL, 20 + 30 * i);

		}
		// for (int i = 0; i < gen.getLineNumber(); i++) {
		// System.out.println(toFloat(bits.get(30 * i, 30 * i + 10)) + " "
		// + toFloat(bits.get(10 + 30 * i, 20 + 30 * i)) + " "
		// + toFloat(bits.get(20 + 30 * i, 30 + 30 * i)));
		// }
		return bits;
	}

	/***
	 * Appends to bitsets at position i
	 * 
	 * @param appendToThis
	 *            begin
	 * @param fromThis
	 *            end
	 * @param i
	 *            where
	 */
	private void append(BitSet appendToThis, BitSet fromThis, int i) {
		for (int j = 0; j < 10; j++) {
			appendToThis.set(j + i, fromThis.get(j));
		}
	}

	void createFloatRepresentation() {
		// TODO Auto-generated method stub
		LinesAlgorithmDataFactory factory = (LinesAlgorithmDataFactory) this.factory;
		points.clear();
		lengths.clear();
		for (int i = 0; i < factory.lineNumber; i++) {
			points.add(new Vec2(toFloat(fixedInt.get(30 * i, 30 * i + 10)),
					toFloat(fixedInt.get(10 + 30 * i, 20 + 30 * i))));
			lengths.add(toFloat(fixedInt.get(20 + 30 * i, 30 + 30 * i)));
		}
	}

	/***
	 * Creates a float from a given fixedpoint integer
	 * 
	 * @param bits
	 *            fixedpoint integer
	 * @return float
	 */
	private Float toFloat(BitSet bits) {
		// TODO Auto-generated method stub
		float f = 0;
		for (int i = 0; i < 10; i++) {
			if (bits.get(i))
				f += Math.pow(2, -(i - 1));
		}
		// workaround for physic
		return f + 0.01f;
	}

	public Car createVehicle() {
		// TODO Auto-generated method stub
		LinesAlgorithmDataFactory factory = (LinesAlgorithmDataFactory) this.factory;
		float sumX = 0;
		for (Vec2 vec : points) {
			sumX += vec.x;
		}

		Car car = new Car();
		Vec2[] vertices = new Vec2[points.size() * 2];
		vertices[0] = new Vec2(points.firstElement().x * factory.maxLength
				/ sumX, (points.firstElement().y - 1) * factory.maxHeight / 2
				+ lengths.firstElement() * factory.maxLineLength);
		vertices[1] = new Vec2(points.firstElement().x * factory.maxLength
				/ sumX, (points.firstElement().y - 1) * factory.maxHeight / 2);

		for (int i = 2; i <= factory.lineNumber; i++) {
			vertices[i] = new Vec2(vertices[i - 1].x + points.get(i - 1).x
					* factory.maxLength / sumX, (points.get(i - 1).y - 1)
					* factory.maxHeight / 2);
		}
		int diff = 1;
		for (int i = factory.lineNumber + 1; i < factory.lineNumber * 2; i++) {
			vertices[i] = new Vec2(vertices[i - diff].x, (points.get(i - diff
					- 1).y - 1)
					* factory.maxHeight
					/ 2
					+ lengths.get(i - diff - 1)
					* factory.maxLineLength);
			diff += 2;
		}

		// for (int i = 0; i < vertices.length; i++) {
		// System.out.println(i + ": " + vertices[i].x + "," + vertices[i].y);
		// }

		Polygon p = new Polygon(0, 0, BodyType.DYNAMIC, 7.85f, 0.5f, vertices,
				false, car);
		car.addPart(p);
		// float r;
		// Wenn zuf�lliger Radius �ndern
		for (int i = 0; i < wheelsCount; i++) {
			// do {
			// r = (float) Math.random();
			// } while (r < 0.1f);
			Circle c = new Circle(vertices[wheelPositions.get(i)].x,
					vertices[wheelPositions.get(i)].y, BodyType.DYNAMIC, 0.94f,
					0.1f, 1f, false, car);
			car.addPart(c);
		}

		return car;
	}

	public boolean isValid(BitSet bits) {
		LinesAlgorithmDataFactory factory = (LinesAlgorithmDataFactory) this.factory;
		Vector<Vec2> tempPoints = new Vector<Vec2>();
		Vector<Float> tempLengths = new Vector<Float>();
		boolean returnValue = true;
		for (int i = 0; i < factory.lineNumber; i++) {
			tempPoints.add(new Vec2(toFloat(bits.get(30 * i, 30 * i + 10)),
					toFloat(bits.get(10 + 30 * i, 20 + 30 * i))));
			tempLengths.add(toFloat(bits.get(20 + 30 * i, 30 + 30 * i)));
		}
		int i = 0;
		while (i < factory.lineNumber) {
			if (tempPoints.get(i).x > 0f
					&& tempPoints.get(i).x <= 1f
					&& tempPoints.get(i).y > 0f
					&& tempPoints.get(i).y <= 2f
					&& tempLengths.get(i) > 0f
					&& tempLengths.get(i) <= 1f
					&& ((tempLengths.get(i) * factory.maxLineLength + (tempPoints
							.get(i).y - 1f) * factory.maxHeight / 2) <= (factory.maxHeight / 2))) {
				if (factory.fixedLength && i == 0 && tempPoints.get(i).x > 0.01) {
					returnValue = false;
					break;
				}
				i++;
			} else {
				returnValue = false;
				break;
			}
		}
		return returnValue;
	}

}
