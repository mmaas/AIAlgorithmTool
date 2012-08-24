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

import model.Shape;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.ShapeType;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

/***
 * ShapeFactory for Box2D physics engine. Takes model representation of shapes
 * and transforms them to Box2D shapes and fixtures.
 * 
 * @author Marco Maas
 * @version 1.0
 */
public class ShapeFactory {

	PhysicFactory pf;

	/***
	 * Simple constructor
	 * 
	 * @param pf
	 *            The physic factory
	 */
	public ShapeFactory(PhysicFactory pf) {
		this.pf = pf;
	}

	/***
	 * A body with one box fixture is created.
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
	 * @param group
	 *            group of the box: for sorting (non) colliding bodies.
	 * @param s
	 *            the model shape representation which belongs to the created
	 *            box
	 * @return created Body
	 */
	public Body createBox(float bodyPositionX, float bodyPositionY,
			BodyType bodyType, float boxSizeX, float boxSizeY, float density,
			float friction, int group, Shape s) {
		// Quadratische Box um PositionX und Y
		BodyDef bodyDef;
		Body body;
		Vec2 bodyPosition;

		bodyPosition = new Vec2(bodyPositionX, bodyPositionY);
		bodyDef = new BodyDef();
		bodyDef.type = bodyType;
		bodyDef.position = bodyPosition;
		bodyDef.userData = s;
		body = pf.physicWorld.createBody(bodyDef);

		if (s.getV() != null)
			body.setActive(s.getV().getisActive());
		if (s.getBodyType() == BodyType.STATIC)
			body.setActive(true);

		createBoxFixture(body, bodyType, boxSizeX, boxSizeY, density, friction,
				group);
		body.resetMassData();
		return body;
	}

	/***
	 * A body with one polygon fixture is created.
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
	 * @param v
	 *            list of vertices which defines the polygon
	 * @param j
	 *            count of vertices
	 * @param group
	 *            group of the polygon: for sorting (non) colliding bodies.
	 * @param s
	 *            the model shape representation which belongs to the created
	 *            polygon
	 * @return created Body
	 */
	public Body createPolygon(float bodyPositionX, float bodyPositionY,
			BodyType bodyType, float density, float friction, Vec2[] v, int j,
			int group, Shape s) {
		// Polygon um bodyPositionX und Y
		BodyDef bodyDef;
		Body body;
		Vec2 bodyPosition;

		bodyPosition = new Vec2(bodyPositionX, bodyPositionY);
		bodyDef = new BodyDef();
		bodyDef.type = bodyType;
		bodyDef.position = bodyPosition;
		bodyDef.userData = s;
		body = pf.physicWorld.createBody(bodyDef);

		if (s.getV() != null)
			body.setActive(s.getV().getisActive());
		if (s.getBodyType() == BodyType.STATIC)
			body.setActive(true);

		createPolygonFixture(body, bodyType, density, friction, v, j, group);
		body.resetMassData();
		return body;
	}

	/***
	 * A body with one circle fixture is created.
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
	 * @param group
	 *            group of the polygon: for sorting (non) colliding bodies.
	 * @param s
	 *            the model shape representation which belongs to the created
	 *            circle
	 * @return
	 */
	public Body createCircle(float bodyPositionX, float bodyPositionY,
			BodyType bodyType, float density, float friction, float radius,
			int group, Shape s) {
		// Kreis um bodyPositionX und Y
		BodyDef bodyDef;
		Body body;
		Vec2 bodyPosition;

		bodyPosition = new Vec2(bodyPositionX, bodyPositionY);
		bodyDef = new BodyDef();
		bodyDef.type = bodyType;
		bodyDef.position = bodyPosition;
		bodyDef.userData = s;
		body = pf.physicWorld.createBody(bodyDef);

		if (s.getV() != null)
			body.setActive(s.getV().getisActive());
		if (s.getBodyType() == BodyType.STATIC)
			body.setActive(true);

		createCircleFixture(new Vec2(0, 0), body, bodyType, density, friction,
				radius, group);
		body.resetMassData();
		return body;
	}

	/***
	 * A box fixture is added to a body.
	 * 
	 * @param body
	 *            body to which the fixture is added.
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
	 * @param group
	 *            group of the box: for sorting (non) colliding bodies.
	 */
	public void createBoxFixture(Body body, BodyType bodyType, float boxSizeX,
			float boxSizeY, float density, float friction, int group) {
		PolygonShape box;
		FixtureDef fixtureDef;

		box = new PolygonShape();
		box.setAsBox(boxSizeX, boxSizeY);

		if (bodyType != BodyType.STATIC) {
			fixtureDef = new FixtureDef();
			fixtureDef.density = density;
			fixtureDef.friction = friction;
			fixtureDef.shape = box;
			fixtureDef.filter.groupIndex = group;
			fixtureDef.restitution = 0.1f;
			body.createFixture(fixtureDef);
		} else {
			body.createFixture(box, 0.0f);
		}
	}

	/***
	 * A circle fixture is added to a body.
	 * 
	 * @param bodyposition
	 *            local position of the circle, mostly (0,0)
	 * @param body
	 *            body to which the fixture is added.
	 * @param bodyType
	 *            type of simulation: dynamic for cars, static for world
	 * @param density
	 *            density of the box
	 * @param friction
	 *            friction of the box
	 * @param radius
	 * @param group
	 *            group of the box: for sorting (non) colliding bodies.
	 */
	public void createCircleFixture(Vec2 bodyposition, Body body,
			BodyType bodyType, float density, float friction, float radius,
			int group) {
		CircleShape circle;
		FixtureDef fixtureDef;

		circle = new CircleShape();
		circle.m_type = ShapeType.CIRCLE;
		circle.m_p.set(bodyposition);
		circle.m_radius = radius;
		if (bodyType != BodyType.STATIC) {
			fixtureDef = new FixtureDef();
			fixtureDef.density = density;
			fixtureDef.friction = friction;
			fixtureDef.shape = circle;
			fixtureDef.filter.groupIndex = group;
			fixtureDef.restitution = 0.3f;
			body.createFixture(fixtureDef);
		} else {
			body.createFixture(circle, 0.0f);
		}
	}

	/***
	 * A polygon fixture is added to a body.
	 * 
	 * @param body
	 *            body to which the fixture is added.
	 * @param bodyType
	 *            type of simulation: dynamic for cars, static for world
	 * @param density
	 *            density of the polygon
	 * @param friction
	 *            friction of the polygon
	 * @param v
	 *            list of vertices which defines the polygon
	 * @param j
	 *            count of vertices
	 * @param group
	 *            group of the box: for sorting (non) colliding bodies.
	 */
	public void createPolygonFixture(Body body, BodyType bodyType,
			float density, float friction, Vec2[] v, int j, int group) {
		PolygonShape polygon;
		FixtureDef fixtureDef;

		polygon = new PolygonShape();
		polygon.set(v, v.length);

		if (bodyType != BodyType.STATIC) {
			fixtureDef = new FixtureDef();
			fixtureDef.density = density;
			fixtureDef.friction = friction;
			fixtureDef.shape = polygon;
			fixtureDef.filter.groupIndex = group;
			fixtureDef.restitution = 0.1f;
			body.createFixture(fixtureDef);
		} else {
			body.createFixture(polygon, 0.0f);
		}
	}
}
