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
package control;

import java.util.Vector;

import model.Box;
import model.Car;
import model.Circle;
import model.Part;
import model.Polygon;
import model.Shape;
import model.Vehicle;

import org.jbox2d.collision.shapes.ShapeType;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

/***
 * PhysicFactory, which creates physical representation of the vehicles and the
 * world. The Box2d library is used.
 * 
 * @author Marco Maas
 * @version 1.0
 */

public class PhysicFactory {
	JointFactory jf = new JointFactory(this);
	ShapeFactory sf = new ShapeFactory(this);
	World physicWorld;
	boolean allWheel;
	float mass = 0;

	/***
	 * Constructor
	 * 
	 * @param w
	 *            Box2D world in which the objects are created
	 */
	public PhysicFactory(World w) {
		physicWorld = w;
		allWheel = true;
	}

	/***
	 * This method creates the race track from the shape representation. In this
	 * case the race track consists of one body with multiple polygons, so there
	 * are no joints needed.
	 * 
	 * @param worldData
	 *            world to be added
	 */
	public void createParts(model.World worldData) {
		int i = worldData.getLength();
		int j = 0;
		Body activeBody = null;

		while (j < i) {
			Shape s = (Shape) worldData.getPart(j);
			if (s.isFixture()) {
				if (s instanceof Box) {
					Box b = (Box) s;
					sf.createBoxFixture(activeBody, b.getBodyType(),
							b.getBoxSizeX(), b.getBoxSizeY(), b.getDensity(),
							b.getFriction(), -2);
				}
				if (s instanceof Polygon) {
					Polygon poly = (Polygon) s;
					sf.createPolygonFixture(activeBody, poly.getBodyType(),
							poly.getDensity(), poly.getFriction(),
							poly.getVertices(), poly.getJ(), -2);
				}
				if (s instanceof Circle) {
					Circle circle = (Circle) s;
					sf.createCircleFixture(new Vec2(0, 0), activeBody,
							circle.getBodyType(), circle.getDensity(),
							circle.getFriction(), circle.getRadius(), -2);
				}
			} else {
				if (s instanceof Box) {
					Box b = (Box) s;
					activeBody = sf.createBox(b.getBodyPositionX(),
							b.getBodyPositionY(), b.getBodyType(),
							b.getBoxSizeX(), b.getBoxSizeY(), b.getDensity(),
							b.getFriction(), -2, b);
				}
				if (s instanceof Polygon) {
					Polygon poly = (Polygon) s;
					activeBody = sf.createPolygon(poly.getBodyPositionX(),
							poly.getBodyPositionY(), poly.getBodyType(),
							poly.getDensity(), poly.getFriction(),
							poly.getVertices(), poly.getJ(), -2, poly);
				}
				if (s instanceof Circle) {
					Circle circle = (Circle) s;
					activeBody = sf.createCircle(circle.getBodyPositionX(),
							circle.getBodyPositionY(), circle.getBodyType(),
							circle.getDensity(), circle.getFriction(),
							circle.getRadius(), -2, circle);
				}
			}
			j++;
		}
	}

	/***
	 * This method creates a car from the shape representation. A car consists
	 * of one body with multiple polygons, 2 axes and 2 wheels bound via joints
	 * to the these axes.
	 * 
	 * @param c
	 *            car to be added
	 * @return Vector of bodies which belongs to the car (for deletion after
	 *         fitness testing)
	 */
	public Vector<Body> createParts(Car c) {
		int i = c.getLength();
		int j = 0;
		Body previousBody = null, activeBody = null;
		Vector<Body> bodies = new Vector<Body>();
		boolean engine = false;
		mass = 0;

		while (j < i) {
			Part p = c.getPart(j);
			if (j == 0) {
				if (p instanceof Box) {
					Box b = (Box) p;
					previousBody = sf.createBox(b.getBodyPositionX(),
							b.getBodyPositionY(), b.getBodyType(),
							b.getBoxSizeX(), b.getBoxSizeY(), b.getDensity(),
							b.getFriction(), -1, b);
				}
				if (p instanceof Polygon) {
					Polygon poly = (Polygon) p;
					if (poly.getJ() < 5) {

						previousBody = sf.createPolygon(
								poly.getBodyPositionX(),
								poly.getBodyPositionY(), poly.getBodyType(),
								poly.getDensity(), poly.getFriction(),
								poly.getVertices(), poly.getJ(), -1, poly);
					} else {
						Vec2[] vertPart = new Vec2[4];
						for (int k = 0; k < 3; k++) {
							vertPart[k] = poly.getVertices()[k];
						}
						vertPart[3] = poly.getVertices()[poly.getJ() - 1];
						previousBody = sf.createPolygon(
								poly.getBodyPositionX(),
								poly.getBodyPositionY(), poly.getBodyType(),
								poly.getDensity(), poly.getFriction(),
								vertPart, 4, -1, poly);
						int countSquare = 1;
						int endCount = (poly.getJ() / 2) - 1;
						int offset = 2;
						while (countSquare < endCount) {
							Vec2 temp = vertPart[3];
							vertPart[0] = temp;
							vertPart[1] = poly.getVertices()[countSquare + 1];
							vertPart[2] = poly.getVertices()[countSquare + 2];
							vertPart[3] = poly.getVertices()[poly.getJ()
									- offset];
							sf.createPolygonFixture(previousBody,
									poly.getBodyType(), poly.getDensity(),
									poly.getFriction(), vertPart, 4, -1);
							offset++;
							countSquare++;
						}
					}
				}
				if (p instanceof Circle) {
					Circle circle = (Circle) p;
					previousBody = sf.createCircle(circle.getBodyPositionX(),
							circle.getBodyPositionY(), circle.getBodyType(),
							circle.getDensity(), circle.getFriction(),
							circle.getRadius(), -1, circle);
				}
				previousBody.setFixedRotation(false);
				mass = previousBody.getMass();
				bodies.add(previousBody);
			} else {
				if (p instanceof Box) {
					Box b = (Box) p;
					activeBody = sf.createBox(b.getBodyPositionX(),
							b.getBodyPositionY(), b.getBodyType(),
							b.getBoxSizeX(), b.getBoxSizeY(), b.getDensity(),
							b.getFriction(), -1, b);
				}
				if (p instanceof Polygon) {
					Polygon poly = (Polygon) p;

					if (poly.getJ() < 5) {

						activeBody = sf.createPolygon(poly.getBodyPositionX(),
								poly.getBodyPositionY(), poly.getBodyType(),
								poly.getDensity(), poly.getFriction(),
								poly.getVertices(), poly.getJ(), -1, poly);
					} else {
						Vec2[] vertPart = new Vec2[4];
						for (int k = 0; k < 3; k++) {
							vertPart[k] = poly.getVertices()[k];
						}
						vertPart[3] = poly.getVertices()[poly.getJ() - 1];
						activeBody = sf.createPolygon(poly.getBodyPositionX(),
								poly.getBodyPositionY(), poly.getBodyType(),
								poly.getDensity(), poly.getFriction(),
								vertPart, 4, -1, poly);
						int countSquare = 1;
						int endCount = (poly.getJ() / 2) - 1;
						int offset = 2;
						while (countSquare < endCount) {
							Vec2 temp = vertPart[3];
							vertPart[0] = temp;
							vertPart[1] = poly.getVertices()[countSquare + 1];
							vertPart[2] = poly.getVertices()[countSquare + 2];
							vertPart[3] = poly.getVertices()[poly.getJ()
									- offset];
							sf.createPolygonFixture(activeBody,
									poly.getBodyType(), poly.getDensity(),
									poly.getFriction(), vertPart, 4, -1);
							offset++;
							countSquare++;
						}
					}
				}
				if (p instanceof Circle) {
					Circle circle = (Circle) p;
					activeBody = sf.createCircle(circle.getBodyPositionX(),
							circle.getBodyPositionY(), circle.getBodyType(),
							circle.getDensity(), circle.getFriction(),
							circle.getRadius(), -1, circle);
				}
				activeBody.setFixedRotation(false);
				bodies.add(activeBody);
				if (previousBody != null
						&& activeBody != null
						&& activeBody.getFixtureList().getShape().getType() == ShapeType.CIRCLE) {
					Shape s = (Shape) activeBody.getUserData();
					Vehicle v = s.getV();
					Box b = new Box(activeBody.getWorldCenter().x,
							activeBody.getWorldCenter().y, BodyType.DYNAMIC,
							0.16f, 0.32f, 10, 0.1f, false, v);
					Body temporaryBody = sf.createBox(
							activeBody.getWorldCenter().x,
							activeBody.getWorldCenter().y, BodyType.DYNAMIC,
							0.16f, 0.32f, 10, 0.1f, -1, b);
					jf.createPrismaticJoint(previousBody, temporaryBody);
					bodies.add(temporaryBody);
					if (!engine || allWheel)
						jf.createRevoluteJoint(temporaryBody, activeBody, true);
					else
						jf.createRevoluteJoint(temporaryBody, activeBody, false);
					engine = true;
					if (activeBody.getFixtureList().getShape().getType() != ShapeType.CIRCLE)
						previousBody = activeBody;
				}
			}
			j++;
		}
		mass = 0;
		engine = false;
		return bodies;
	}

	/***
	 * Simple getter for allWheel
	 * 
	 * @return value of boolean allWheel
	 */
	public boolean isAllWheel() {
		return allWheel;
	}

	/***
	 * simple setter for allWheel
	 * 
	 * @param allWheel
	 *            new Value
	 */
	public void setAllWheel(boolean allWheel) {
		this.allWheel = allWheel;
	}
}
