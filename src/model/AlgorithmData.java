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

/***
 * Abstract class AlgorithmData, which defines the minimal features to perform
 * 
 * @author Marco Maas
 * @version 1.0
 */
public abstract class AlgorithmData {
	/***
	 * 1 AlgorithmData represents exactly one vehicle
	 */
	protected Vehicle v;

	public AlgorithmData() {
	}

	/***
	 * Compute the vehicle as part of the model, from the given AlgorithmData of
	 * this instance
	 * 
	 * @return Vehicle
	 */
	public Vehicle createVehicle() {
		// TODO Auto-generated method stub
		return null;
	}

}
