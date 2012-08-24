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
package geneticalgorithm;

import java.util.Comparator;

import org.jbox2d.common.Vec2;

/***
 * A simple comparator to compare 2 Vec2, which represents rated vehicles with
 * its rating and its number
 * 
 * @author Marco Maas
 * @version 1.0
 */
public class Vec2Comparator implements Comparator<Vec2> {

	@Override
	public int compare(Vec2 o1, Vec2 o2) {
		// TODO Auto-generated method stub
		int comp = Float.compare(o1.y, o2.y);
		if (comp < 0)
			return -1;
		if (comp == 0)
			return 0;
		if (comp > 0)
			return 1;
		return 0;
	}

}
