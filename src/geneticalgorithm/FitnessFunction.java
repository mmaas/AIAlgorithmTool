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

import model.Car;
import model.GeneticAlgorithmData;

import org.jbox2d.dynamics.Body;

/***
 * Implemented fitness function.
 * 
 * @author Marco Maas
 * @version 1.0
 */
public class FitnessFunction implements Fitness {
	GeneticAlgorithm gA;

	/***
	 * simple constructor
	 * 
	 * @param gA
	 *            genetic algorithm
	 */
	public FitnessFunction(GeneticAlgorithm gA) {
		this.gA = gA;
	}

	public void fitness(GeneticAlgorithmData g) {
		Car car = (Car) g.createVehicle();
		car.setActive(true);
		gA.c.p.addVehicle(car);
		Body b = gA.c.p.vehicles.get(2);
		float lastPosition = Math.round(b.getPosition().x * 100f) / 100f;
		float newPosition, maxPosition = 0;
		int count = 0;
		int max = 3060;
		gA.c.registeredViews.get(0).pTab.stop(false);

		do {
			if (!gA.c.registeredViews.get(0).pTab.pause) {
				gA.c.step();
				newPosition = Math.round(b.getPosition().x * 100f) / 100f;
				if (newPosition >= 2500)
					break;
				if (gA.c.registeredViews.get(0).toggleStatus) {
					try {
						Thread.sleep(17);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (newPosition > lastPosition) {
					lastPosition = newPosition;
					maxPosition = newPosition;
					count = 0;
				} else {
					if (count < max) {
						count += 17;
					} else {
						gA.c.registeredViews.get(0).pTab.stop = true;
						break;
					}
				}
				max = 3060 + 510 * (int) (maxPosition / 20);
			}
		} while (true);
		gA.c.registeredViews.get(0).pTab.stop(true);
		if (!gA.showBestcar) {
			if (gA.best < 1) {
				g.fitness = Math.round(maxPosition * 100.f) / 100.f;
				g.distance = g.fitness;
			} else if (maxPosition > gA.best) {
				g.fitness = gA.best;

				g.fitness += (maxPosition - gA.best) * Math.log(gA.best);

				g.fitness = Math.round((g.fitness * 100f) / 100f);
				g.distance = Math.round(maxPosition * 100.f) / 100.f;
			} else {
				g.fitness = Math.round(maxPosition * 100.f) / 100.f;
				g.distance = g.fitness;
			}
		}
		if (g.distance > 2499) {
			gA.done = true;
		}
	}

}
