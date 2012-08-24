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

import java.util.Vector;

/***
 * Abstract class Vehicle, which defines the minimal features
 * 
 * @author Marco Maas
 * @version 1.0
 */
public abstract class Vehicle {

	protected Vector<Part> parts;
	boolean isActive;

	/***
	 * Simple constructor which creates a empty list of parts
	 */
	public Vehicle() {
		parts = new Vector<Part>();
		isActive = false;
	}

	public boolean getisActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Part getPart(int i) {
		return parts.get(i);
	}

	public void addPart(Part part) {
		parts.add(part);
	}

	public void removePart(int i) {
		parts.remove(i);
	}

	public int getLength() {
		return parts.size();
	}

}
