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
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;

/***
 * Representation of the racing track (or world) in the model
 * 
 * @author Marco Maas
 * @version 1.0
 */
public class World {
	protected Vector<Part> parts;

	/***
	 * Simple Constructor
	 */
	public World() {
		parts = new Vector<Part>();
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

	/***
	 * Create a randomized world out of polygon fixtures with increasing
	 * difficulty. To achieve this the max allowed gradient rises every 25
	 * meters. Total length of the racing track is 2500m.
	 */
	public void createRandom() {
		// TODO Auto-generated method stub
		float x = 0;
		Polygon p;

		Vec2[] wall = new Vec2[4];
		wall[0] = new Vec2(-1.39f, 10);
		wall[1] = new Vec2(-1.39f, -15.75f);
		wall[2] = new Vec2(-0.61f, -15.75f);
		wall[3] = new Vec2(-0.61f, 10);

		p = new Polygon(wall[0].x, wall[0].y, BodyType.STATIC, 0, 1, wall,
				false, this);
		addPart(p);

		Vec2[] start = new Vec2[4];
		start[0] = new Vec2(-1, -2.5f);
		start[1] = new Vec2(-1, -3.25f);
		start[2] = new Vec2(11, -3.25f);
		start[3] = new Vec2(11f, -2.5f);
		x += 11;

		p = new Polygon(start[0].x, start[0].y, BodyType.STATIC, 0, 1, start,
				false, this);
		addPart(p);
		float gradient = 0, distance = 0, maxGradient = 0;
		while (x < 2500) {
			Vec2[] vec = new Vec2[4];
			vec[0] = new Vec2(start[3].x, start[3].y);
			vec[1] = new Vec2(start[3].x, start[3].y - 0.75f);
			float r = (float) (Math.random() * maxGradient * 5 - maxGradient * 2.5f);
			if (r > 0) {
				gradient += r;
				distance += 2.5f;
			} else {
				gradient = 0;
				distance = 0;
			}
			if (maxGradient < 0.5f)
				maxGradient = (float) (0.2f + (x / 25) * 0.05);
			if ((distance > 0f) && gradient / distance > maxGradient) {
				do {
					r = (float) (Math.random() * maxGradient * 5 - maxGradient * 2.5f);
				} while (r > 0f);
				gradient = 0;
				distance = 0;
			}
			vec[2] = new Vec2(start[3].x + 2.5f, start[3].y + r - 0.75f);
			vec[3] = new Vec2(start[3].x + 2.5f, start[3].y + r);
			x += 2.5f;

			p = new Polygon(vec[0].x, vec[0].y, BodyType.STATIC, 0, 0.7f, vec,
					true, this);
			addPart(p);
			start = vec;
		}

	}

}
