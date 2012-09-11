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

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

import model.*;

/***
 * Physic which performs creation of the simulated physical objects and computes
 * the steps
 * 
 * @author Marco Maas
 * @version 1.0
 */
public class Physic {
	/***
	 * worldData is a reference to the world, which is defined as a part of the
	 * model
	 */
	protected model.World worldData;
	/***
	 * A list of bodies, which represents the currently simulated vehicle
	 */
	public Vector<Body> vehicles;
	/***
	 * gravity constant, normally 1g
	 */
	protected Vec2 gravity;
	/***
	 * physical represantation of worldData
	 */
	public World physicWorld;
	/***
	 * factory which takes model data and creates physical bodies
	 */
	public PhysicFactory factory;
	public float timestep;
	public int velIt, posIt;

	/***
	 * Simple constructor
	 * 
	 * @param worldData
	 *            The world to be simulated
	 * @param gravityX
	 *            gravity in x direction
	 * @param gravityY
	 *            gravity in y direction
	 */
	public Physic(model.World worldData, float gravityX, float gravityY) {
		gravity = new Vec2(gravityX, gravityY);
		physicWorld = new World(gravity, true);
		factory = new PhysicFactory(physicWorld);
		vehicles = new Vector<Body>();

		addWorld(worldData);
	}

	/***
	 * Takes a vehicle and creates physical represantation
	 * 
	 * @param v
	 *            Vehicle to add
	 */
	public void addVehicle(Vehicle v) {
		if (v instanceof Car) {
			Vector<Body> b = factory.createParts((Car) v);
			vehicles.addAll(b);
		}

	}

	/***
	 * Removes all simulated Vehicles
	 */
	public void removeVehicle() {
		Body b = physicWorld.getBodyList();

		while (b != null) {
			if (b.m_type == BodyType.DYNAMIC) {
				b.setActive(false);
				physicWorld.destroyBody(b);
			}
			b = b.getNext();
		}

		vehicles.clear();
	}

	/***
	 * Takes a world and creates physical represantation
	 * 
	 * @param w
	 *            world to add
	 * 
	 */
	public void addWorld(model.World w) {
		factory.createParts(w);
		worldData = w;
	}

	/***
	 * Computes one step in the physic engine
	 */
	public void step() {
		physicWorld.clearForces();
		applyForces();
		physicWorld.step(timestep, velIt, posIt);

	}

	/***
	 * method to manually apply forces to the currently tested vehicle
	 */
	private void applyForces() {
		// TODO Auto-generated method stub
		Body b = physicWorld.getBodyList();
		int c = physicWorld.getBodyCount();
		for (int i = 0; i < c; i++) {
			if (b.isActive() && b.getType() != BodyType.STATIC) {
			}
			b.getNext();
		}
	}

}
