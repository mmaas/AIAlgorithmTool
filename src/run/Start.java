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
package run;

import geneticalgorithm.GeneticAlgorithm;
import geneticalgorithm.LinesAlgorithmDataFactory;
import view.*;
import model.*;
import control.*;

/***
 * Start class which assembles the needed classes and then starts evaluation
 * 
 * @author Marco Maas
 * @version 1.0
 */
public class Start {

	public static World w;
	public static GeneticAlgorithm g;
	public static Physic p;
	public static Control control;
	public static View v;
	public static Model m;

	/***
	 * Creates the world, the physic, the model and the control. Then the
	 * genetic algorithm and the view is created. At last the simulation begins.
	 * 
	 * @param args
	 *            not needed
	 */
	public static void main(String[] args) {

		w = new World();
		w.createRandom();

		p = new Physic(w, 0.0f, -9.81f);

		m = new Model(w);

		control = new Control(p, g, m);
		control.initPhysic(1.0f / 60.0f, 8, 3);

		LinesAlgorithmDataFactory factory = new LinesAlgorithmDataFactory(5f,8f,8f,5);
		g = new GeneticAlgorithm(50, factory, control);

		v = new View(control, m, g, control.getPhysicWorld());
		control.addView(v);

		g.evaluate();

	}

}
