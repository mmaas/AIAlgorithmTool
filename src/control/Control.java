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

import org.jbox2d.dynamics.World;

import view.View;
import model.Model;

/***
 * Control from MVC design pattern
 * 
 * @author Marco Maas
 * @version 1.0
 */

public class Control {

	public Physic p;
	public Algorithm a;
	protected Model m;
	public Vector<View> registeredViews;

	public Control(Physic p, Algorithm a, Model m) {
		this.p = p;
		this.a = a;
		this.m = m;
		registeredViews = new Vector<View>();
	}

	/***
	 * Adds a view to the list of views, standard MVC method
	 * 
	 * @param view
	 *            view to add
	 */
	public void addView(View view) {
		registeredViews.add(view);
	}

	/***
	 * Initializes the physic engine with following parameters
	 * 
	 * @param tS
	 *            timestep, usually 1/60 second
	 * @param vI
	 *            velocity iterations, which defines the precision of velocity
	 *            computation
	 * @param pI
	 *            position iterations, which defines the precision of position
	 *            computation
	 */
	public void initPhysic(float tS, int vI, int pI) {
		// TODO Auto-generated method stub
		p.posIt = pI;
		p.velIt = vI;
		p.timestep = tS;
	}

	/***
	 * Simple getter for the physical world
	 * 
	 * @return physical world
	 */
	public World getPhysicWorld() {
		return p.physicWorld;
	}

	/***
	 * Compute a step in the physical world
	 */
	public void step() {
		p.step();
	}

}
