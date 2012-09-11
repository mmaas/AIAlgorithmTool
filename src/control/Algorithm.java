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

import javax.swing.JTabbedPane;

import model.AlgorithmData;


/***
 * Abstract class Algorithm, which defines the minimal features to perform
 * 
 * @author Marco Maas
 * @version 1.0
 */

public abstract class Algorithm {
	/***
	 * Some kind of represantation for Vehicles
	 */
	public Vector<AlgorithmData> aData;
	/***
	 * Control for physic simulation
	 */
	public Control c;
	/***
	 * Panel with information and configuration
	 */
	public JTabbedPane p;
	/***
	 * Simple constructor
	 * @param c control
	 */
	public Algorithm(Control c) {
		// TODO Auto-generated constructor stub
		aData = new Vector<AlgorithmData>();
		this.c = c;
	}

	/***
	 * Some kind of evaluation, which takes each AlgorithmData, rates it and
	 * trys to improve the result by changing the AlgorithmData
	 */
	public abstract void evaluate();
}
