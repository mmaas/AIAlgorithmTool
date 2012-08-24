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

import org.jbox2d.dynamics.BodyType;

/***
 * Model representation of a box
 * 
 * @author Marco Maas
 * @version 1.0
 */
public class Box extends Shape {
	float boxSizeX, boxSizeY;

	/***
	 * Constructor with all values for boxes
	 * 
	 * @param bodyPositionX
	 *            world position x of the body
	 * @param bodyPositionY
	 *            world position y of the body
	 * @param bodyType
	 *            type of simulation: dynamic for cars, static for world
	 * @param boxSizeX
	 *            x size
	 * @param boxSizeY
	 *            y size
	 * @param density
	 *            density of the box
	 * @param friction
	 *            friction of the box
	 * @param isFixture
	 *            own body or only fixture
	 * @param v
	 *            the vehicle to which the shape belongs
	 */
	public Box(float bodyPositionX, float bodyPositionY, BodyType bodyType,
			float boxSizeX, float boxSizeY, float density, float friction,
			boolean isFixture, Vehicle v) {
		super(bodyPositionX, bodyPositionY, bodyType, density, friction,
				isFixture, v);
		this.boxSizeX = boxSizeX;
		this.boxSizeY = boxSizeY;
	}

	/***
	 * Constructor with all values for boxes
	 * 
	 * @param bodyPositionX
	 *            world position x of the body
	 * @param bodyPositionY
	 *            world position y of the body
	 * @param bodyType
	 *            type of simulation: dynamic for cars, static for world
	 * @param boxSizeX
	 *            x size
	 * @param boxSizeY
	 *            y size
	 * @param density
	 *            density of the box
	 * @param friction
	 *            friction of the box
	 * @param isFixture
	 *            own body or only fixture
	 * @param w
	 *            the world to which the shape belongs
	 */
	public Box(float bodyPositionX, float bodyPositionY, BodyType bodyType,
			float boxSizeX, float boxSizeY, float density, float friction,
			boolean isFixture, World w) {
		super(bodyPositionX, bodyPositionY, bodyType, density, friction,
				isFixture, w);
		this.boxSizeX = boxSizeX;
		this.boxSizeY = boxSizeY;
	}

	public float getBoxSizeX() {
		return boxSizeX;
	}

	public void setBoxSizeX(float boxSizeX) {
		this.boxSizeX = boxSizeX;
	}

	public float getBoxSizeY() {
		return boxSizeY;
	}

	public void setBoxSizeY(float boxSizeY) {
		this.boxSizeY = boxSizeY;
	}

}
