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

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;

/***
 * Model representation of a Polygon
 * 
 * @author Marco Maas
 * @version 1.0
 */
public class Polygon extends Shape {
	Vec2[] vertices;
	int j;

	/***
	 * Constructor which creates a polygon from given arguments
	 * 
	 * @param bodyPositionX
	 *            world position x of the body
	 * @param bodyPositionY
	 *            world position y of the body
	 * @param bodyType
	 *            type of simulation: dynamic for cars, static for world
	 * @param density
	 *            density of the polygon
	 * @param friction
	 *            friction of the polygon
	 * @param vertices
	 *            list of vertices which defines the polygon
	 * @param isFixture
	 *            own body or only fixture
	 * @param v
	 *            the vehicle to which the shape belongs
	 */
	public Polygon(float bodyPositionX, float bodyPositionY, BodyType bodyType,
			float density, float friction, Vec2[] vertices, boolean isFixture,
			Vehicle v) {
		super(bodyPositionX, bodyPositionY, bodyType, density, friction,
				isFixture, v);
		this.vertices = vertices;
		j = vertices.length;
	}

	/***
	 * Constructor which creates a polygon from given arguments
	 * 
	 * @param bodyPositionX
	 *            world position x of the body
	 * @param bodyPositionY
	 *            world position y of the body
	 * @param bodyType
	 *            type of simulation: dynamic for cars, static for world
	 * @param density
	 *            density of the polygon
	 * @param friction
	 *            friction of the polygon
	 * @param vertices
	 *            list of vertices which defines the polygon
	 * @param isFixture
	 *            own body or only fixture
	 * @param v
	 *            the world to which the shape belongs
	 */
	public Polygon(float bodyPositionX, float bodyPositionY, BodyType bodyType,
			float density, float friction, Vec2[] vertices, boolean isFixture,
			World w) {
		super(bodyPositionX, bodyPositionY, bodyType, density, friction,
				isFixture, w);
		this.vertices = vertices;
		j = vertices.length;
	}

	public Vec2[] getVertices() {
		return vertices;
	}

	public void setVertices(Vec2[] vertices) {
		this.vertices = vertices;
		j = vertices.length;
	}

	public int getJ() {
		return j;
	}

}
