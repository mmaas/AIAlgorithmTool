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

public class Circle extends Shape {
	float radius;

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	/***
	 * Constructor with all values for circles.
	 * 
	 * @param bodyPositionX
	 *            world position x of the body
	 * @param bodyPositionY
	 *            world position y of the body
	 * @param bodyType
	 *            type of simulation: dynamic for cars, static for world
	 * @param density
	 *            density of the circle
	 * @param friction
	 *            friction of the circle
	 * @param radius
	 *            radius of the circle
	 * @param isFixture
	 *            own body or only fixture
	 * @param v
	 *            the vehicle to which the shape belongs
	 */
	public Circle(float bodyPositionX, float bodyPositionY, BodyType bodyType,
			float density, float friction, float radius, boolean isFixture,
			Vehicle v) {
		super(bodyPositionX, bodyPositionY, bodyType, density, friction,
				isFixture, v);
		this.radius = radius;
	}

	/***
	 * Constructor with all values for circles.
	 * 
	 * @param bodyPositionX
	 *            world position x of the body
	 * @param bodyPositionY
	 *            world position y of the body
	 * @param bodyType
	 *            type of simulation: dynamic for cars, static for world
	 * @param density
	 *            density of the circle
	 * @param friction
	 *            friction of the circle
	 * @param radius
	 *            radius of the circle
	 * @param isFixture
	 *            own body or only fixture
	 * @param w
	 *            the world to which the shape belongs
	 */
	public Circle(float bodyPositionX, float bodyPositionY, BodyType bodyType,
			float density, float friction, float radius, boolean isFixture,
			World w) {
		super(bodyPositionX, bodyPositionY, bodyType, density, friction,
				isFixture, w);
		this.radius = radius;
	}

}
