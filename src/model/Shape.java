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
 * Abstract class Shape, which defines the minimal features of a canvas
 * 
 * @author Marco Maas
 * @version 1.0
 */
public abstract class Shape extends Part  {
	float bodyPositionX, bodyPositionY, density, friction;
	boolean isFixture;
	BodyType bodyType;
	Vehicle v;
	World w;
	/***
	 * Constructor which creates a shape from given arguments
	 * 
	 * @param bodyPositionX
	 *            world position x of the body
	 * @param bodyPositionY
	 *            world position y of the body
	 * @param bodyType
	 *            type of simulation: dynamic for cars, static for world
	 * @param density
	 *            density of the shape
	 * @param friction
	 *            friction of the shape
	 * @param isFixture
	 *            own body or only fixture
	 * @param v
	 *            the vehicle to which the shape belongs
	 */
	public Shape(float bodyPositionX, float bodyPositionY, BodyType bodyType,
			float density, float friction, boolean isFixture, Vehicle v) {
		this.bodyPositionX = bodyPositionX;
		this.bodyPositionY = bodyPositionY;
		this.bodyType = bodyType;
		this.density = density;
		this.friction = friction;
		this.isFixture = isFixture;
		this.v = v;
	}

	public boolean isFixture() {
		return isFixture;
	}

	public void setFixture(boolean isFixture) {
		this.isFixture = isFixture;
	}
	/***
	 * Constructor which creates a shape from given arguments
	 * 
	 * @param bodyPositionX
	 *            world position x of the body
	 * @param bodyPositionY
	 *            world position y of the body
	 * @param bodyType
	 *            type of simulation: dynamic for cars, static for world
	 * @param density
	 *            density of the shape
	 * @param friction
	 *            friction of the shape
	 * @param isFixture
	 *            own body or only fixture
	 * @param w
	 *            the world to which the shape belongs
	 */
	public Shape(float bodyPositionX, float bodyPositionY, BodyType bodyType,
			float density, float friction, boolean isFixture, World w) {
		this.bodyPositionX = bodyPositionX;
		this.bodyPositionY = bodyPositionY;
		this.bodyType = bodyType;
		this.density = density;
		this.friction = friction;
		this.isFixture = isFixture;
		this.w = w;
	}

	public Vehicle getV() {
		return v;
	}

	public World getW() {
		return w;
	}

	public float getBodyPositionX() {
		return bodyPositionX;
	}

	public void setBodyPositionX(float bodyPositionX) {
		this.bodyPositionX = bodyPositionX;
	}

	public float getBodyPositionY() {
		return bodyPositionY;
	}

	public void setBodyPositionY(float bodyPositionY) {
		this.bodyPositionY = bodyPositionY;
	}

	public float getDensity() {
		return density;
	}

	public void setDensity(float density) {
		this.density = density;
	}

	public float getFriction() {
		return friction;
	}

	public void setFriction(float friction) {
		this.friction = friction;
	}

	public BodyType getBodyType() {
		return bodyType;
	}

	public void setBodyType(BodyType bodyType) {
		this.bodyType = bodyType;
	}

}
