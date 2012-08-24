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

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.joints.LineJointDef;
import org.jbox2d.dynamics.joints.PrismaticJointDef;
import org.jbox2d.dynamics.joints.RevoluteJointDef;
import org.jbox2d.dynamics.joints.WeldJointDef;

/***
 * JointFactory, which creates different kinds of joints between 2 bodies.
 * 
 * @author Marco Maas
 * @version 1.0
 */
public class JointFactory {

	PhysicFactory pf;

	/***
	 * simple constructor
	 * 
	 * @param pf
	 *            physicfactory
	 */
	public JointFactory(PhysicFactory pf) {
		this.pf = pf;
	}

	/***
	 * Create a weld joint between two bodies
	 * 
	 * @param a
	 *            first body
	 * @param b
	 *            second body
	 */
	void createWeldJoint(Body a, Body b) {
		// TODO Auto-generated method stub
		if (!(a == null) && !(b == null)) {
			WeldJointDef jointDef = new WeldJointDef();
			jointDef.initialize(
					a,
					b,
					new Vec2((a.getPosition().x + b.getPosition().x) / 2, (a
							.getPosition().y + b.getPosition().y) / 2));
			pf.physicWorld.createJoint(jointDef);
		}
	}

	/***
	 * Create a line joint between two bodies
	 * 
	 * @param a
	 *            first body
	 * @param b
	 *            second body
	 */
	void createLineJoint(Body a, Body b) {
		// TODO Auto-generated method stub
		if (!(a == null) && !(b == null)) {
			LineJointDef jd = new LineJointDef();
			jd.initialize(a, b, a.getWorldCenter(), new Vec2(0, 1));
			jd.motorSpeed = 1f;
			jd.maxMotorForce = 100;
			jd.enableMotor = true;
			pf.physicWorld.createJoint(jd);
		}
	}

	/***
	 * Create a revolute joint between two bodies
	 * 
	 * @param a
	 *            first body
	 * @param b
	 *            second body
	 */
	void createRevoluteJoint(Body a, Body b, boolean isEngine) {
		// TODO Auto-generated method stub
		if (!(a == null) && !(b == null)) {
			RevoluteJointDef jd = new RevoluteJointDef();
			jd.initialize(a, b, a.getWorldCenter());
			jd.enableMotor = isEngine;
			jd.motorSpeed = (float) (-5f * Math.log(pf.mass
					* ((Math.E - 1) / 30) + 1f));
			jd.maxMotorTorque = 100;
			pf.physicWorld.createJoint(jd);
		}
	}

	/***
	 * Create a prismatic joint between two bodies
	 * 
	 * @param a
	 *            first body
	 * @param b
	 *            second body
	 */
	void createPrismaticJoint(Body a, Body b) {
		// TODO Auto-generated method stub
		if (!(a == null) && !(b == null)) {
			PrismaticJointDef jd = new PrismaticJointDef();
			jd.initialize(a, b, b.getPosition(), new Vec2(0, 1));
			jd.enableLimit = true;
			jd.enableMotor = false;
			jd.maxMotorForce = 10000;
			jd.motorSpeed = 5;
			jd.lowerTranslation = 0f;
			jd.upperTranslation = 0.32f;
			pf.physicWorld.createJoint(jd);
		}
	}
}
