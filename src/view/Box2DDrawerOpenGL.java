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
package view;

import geneticalgorithm.GeneticAlgorithm;

import java.text.DecimalFormat;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.fixedfunc.GLMatrixFunc;
import javax.media.opengl.glu.GLU;

import model.Shape;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.ShapeType;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;

import com.jogamp.opengl.util.gl2.GLUT;

/***
 * Drawer which creates a OpenGL output of the current simulated scene.
 * 
 * @author Marco Maas
 * @version 1.0
 */
public class Box2DDrawerOpenGL implements GLEventListener {

	OpenGLPlayTab pT;
	final GLUT glut;
	float[] pos = new float[2];

	/***
	 * Simple constructor
	 * 
	 * @param pT
	 *            the openglplaytab to which this drawer belongs
	 */
	public Box2DDrawerOpenGL(OpenGLPlayTab pT) {
		// TODO Auto-generated constructor stub
		this.pT = pT;
		glut = new GLUT();
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub

	}

	@Override
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3,
			int arg4) {
		// TODO Auto-generated method stub

	}

	/***
	 * Display method which is invoked for every drawn picture. If the view is
	 * toggled only information will be drawn.
	 */
	@Override
	public void display(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		if (!pT.stop) {
			if (pT.v.toggleStatus)
				render(drawable);
			else {
				GL2 gl = drawable.getGL().getGL2();
				gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
				gl.glClear(GL.GL_COLOR_BUFFER_BIT);
				drawInterface(drawable.getGL().getGL2(), pos);
			}
		}
	}

	/***
	 * Draws the world, the car and the information. There is also a debug view
	 * and a blending effect.
	 * 
	 * @param drawable
	 *            OpenGL drawable object
	 */
	private void render(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		GL2 gl = drawable.getGL().getGL2();

		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		gl.glLineWidth(1.5f);

		if (pT.blend) {
			gl.glEnable(GL.GL_BLEND);
			gl.glBlendFunc(GL.GL_DST_COLOR, GL.GL_SRC_COLOR);
		} else {
			gl.glDisable(GL.GL_BLEND);
		}

		World source = pT.v.physicWorld;
		Body b = source.getBodyList();
		Shape s;
		boolean cameraChanged = false;

		for (int i = 0; i < source.getBodyCount(); i++) {
			s = (Shape) b.getUserData();
			if (s != null && !cameraChanged && s.getV() != null
					&& s.getV().getisActive()) {
				GLU glu = new GLU();

				gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
				gl.glLoadIdentity();
				glu.gluPerspective(pT.zoom,
						(float) pT.sizeX / (float) pT.sizeY, 1, 20);
				gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);

				gl.glLoadIdentity();
				Body b2 = pT.v.c.p.vehicles.get(0);
				Fixture fixture = b2.getFixtureList();
				Vec2 bottomLeft = fixture.getAABB().lowerBound;
				Vec2 topRight = fixture.getAABB().upperBound;
				pos[0] = (bottomLeft.x + topRight.x) / 2;

				pos[1] = (bottomLeft.y + topRight.y) / 2;
				glu.gluLookAt(pos[0], pos[1], 20, pos[0], pos[1], 1, 0, 1, 0);
				gl.glViewport(0, 0, pT.sizeX, pT.sizeY);
				cameraChanged = true;
			}

			if ((b.isActive() && b.getType() != BodyType.STATIC)
					|| s.getBodyType() == BodyType.STATIC) {
				drawShape(gl, s, b);
			}
			b = b.getNext();
		}

		if (pT.debug)
			drawAxes(gl);

		drawInterface(gl, pos);

		cameraChanged = false;
		gl.glFlush();
	}

	/***
	 * Interface with information of the scene is drawn
	 * 
	 * @param gl
	 *            OpenGL frame
	 * @param pos
	 *            position in the frame
	 */
	private void drawInterface(GL2 gl, float[] pos) {
		// TODO Auto-generated method stub
		float offset = 0.85f;
		if (pT.zoom == 90) {
			// Performance Informations
			long endTime = System.currentTimeMillis();
			DecimalFormat df = new DecimalFormat("0.00");
			gl.glColor3f(1, 0, 0);
			gl.glRasterPos3f(pos[0] - 1.75f, pos[1] + offset, 19); // set
																	// position
			offset -= 0.035f;
			glut.glutBitmapString(
					7,
					"FPS: " + df.format(pT.animator.getLastFPS())
							+ "   Cars per Second: " + pT.cars
							/ Math.abs(((pT.startTime - endTime) / 1000)));

			gl.glColor3f(0, 0, 0);
			gl.glRasterPos3f(pos[0] - 1.75f, pos[1] + offset, 19); // set
																	// position
			glut.glutBitmapString(7,
					"Average FPS: " + df.format(pT.animator.getTotalFPS()));
			// Genetic Algorithm Informations
			int count = 0;
			offset = -1f;
			for (int i = pT.output.size() - 1; i >= 0; i--) {
				String s = pT.output.get(i);
				if (count > 50)
					break;
				offset += 0.035f;
				gl.glRasterPos3f(pos[0] - 1.75f, pos[1] + offset, 19); // set
																		// position
				glut.glutBitmapString(7, s);
				count++;
			}

			Body b = pT.v.c.p.vehicles.get(2);
			gl.glRasterPos3f(pos[0], pos[1] + 0.8f, 19);
			glut.glutBitmapString(8, df.format(b.getPosition().x));
			if (pT.algorithmInformation) {
				count = 0;
				offset = -1f;
				for (int i = pT.outputAlgorithmInformation.size() - 1; i >= 0; i--) {
					if (i >= pT.outputAlgorithmInformation.size()) {
						break;
					}
					String s = pT.outputAlgorithmInformation.get(i);
					if (count > 52)
						break;
					offset += 0.035f;
					gl.glRasterPos3f(pos[0] + 1.f, pos[1] + offset, 19); // set
																			// position
					glut.glutBitmapString(7, s);
					count++;
				}
			}
			// Userinput reaction
			GeneticAlgorithm ga = (GeneticAlgorithm) pT.v.c.a;
			if (ga != null && ga.showBestcar) {
				gl.glColor3f(0, 0, 0);
				gl.glRasterPos3f(pos[0], pos[1] + 0.6f, 19); // set
																// position
				System.out.println("bin da");
				glut.glutBitmapString(8,
						"Userinput erhalten: Zeige bestes Fahrzeug");
			}
		}
	}

	/***
	 * Draw the given shape
	 * 
	 * @param gl
	 *            OpenGL frame
	 * @param s
	 *            the shape to draw
	 * @param b
	 *            the body to draw
	 */
	private void drawShape(GL2 gl, Shape s, Body b) {
		// TODO Auto-generated method stub
		Fixture fixture = b.getFixtureList();

		while (fixture != null) {
			drawFixture(gl, fixture, b);
			if (pT.debug)
				drawAABB(gl, fixture, b);
			fixture = fixture.getNext();
		}
	}

	/***
	 * Draw the given fixture
	 * 
	 * @param gl
	 *            OpenGL frame
	 * @param fixture
	 *            the fixture to draw
	 * @param b
	 *            the body to draw
	 */
	private void drawFixture(GL2 gl, Fixture fixture, Body b) {
		// TODO Auto-generated method stub
		Vec2 pos;
		float angle = b.getAngle();

		gl.glPushMatrix();

		if (fixture.getType() == ShapeType.CIRCLE) {
			Vec2 bottomLeft = fixture.getAABB().lowerBound;
			Vec2 topRight = fixture.getAABB().upperBound;

			gl.glTranslatef((bottomLeft.x + topRight.x) / 2,
					(bottomLeft.y + topRight.y) / 2, 0);
		} else {
			pos = b.getPosition();
			gl.glTranslatef(pos.x, pos.y, 0);
		}
		gl.glRotated(Math.toDegrees(angle), 0, 0, 1);

		if (fixture.getType() == ShapeType.CIRCLE) {
			CircleShape circle = (CircleShape) fixture.getShape();

			gl.glColor4f(1, 0, 0, 1 / 3);

			gl.glBegin(GL2.GL_LINE_LOOP);
			for (float a = 0; a < 360; a += 5) {
				gl.glVertex3d(Math.sin(Math.toRadians(a)) * circle.m_radius,
						Math.cos(Math.toRadians(a)) * circle.m_radius, 0);
			}
			gl.glEnd();

			gl.glColor4f(0, 1, 0, 0.5f);
			gl.glBegin(GL.GL_LINES);
			gl.glVertex3d(0, 0, 0);
			gl.glVertex3d(circle.m_radius, 0, 0);
			gl.glEnd();

		} else if (fixture.getType() == ShapeType.POLYGON) {
			PolygonShape poly = (PolygonShape) fixture.getShape();
			Vec2 vertices[] = poly.getVertices();
			if (b.getType() != BodyType.STATIC) {
				gl.glColor4f(0, 0.5f, 0.5f, 0.5f);
				gl.glBegin(GL2.GL_LINE_LOOP);
				for (int i = poly.getVertexCount() - 1; i >= 0; i--) {
					gl.glVertex3d(vertices[i].x, vertices[i].y, 0.5f);
				}
				gl.glEnd();

				gl.glPointSize(4);
				gl.glColor4f(0, 0, 0, 0.5f);
				gl.glBegin(GL2.GL_POINTS);
				for (int i = poly.getVertexCount() - 1; i >= 0; i--) {
					gl.glVertex3d(vertices[i].x, vertices[i].y, 0.5f);
				}
				gl.glEnd();

			} else {
				gl.glColor4f(0.33f, 0.33f, 0.33f, 1);
				gl.glBegin(GL2.GL_POLYGON);
				for (int i = poly.getVertexCount() - 1; i >= 0; i--) {
					gl.glVertex3d(vertices[i].x, vertices[i].y, 0);
				}
				gl.glEnd();
			}
		}

		gl.glPopMatrix();
	}

	/***
	 * Draw axis aligned bounding boxes for debugging
	 * 
	 * @param gl
	 *            OpenGL frame
	 * @param fixture
	 *            the fixture to draw
	 * @param b
	 *            the body to draw
	 */
	private void drawAABB(GL2 gl, Fixture fixture, Body b) {
		// TODO Auto-generated method stub
		gl.glPushMatrix();

		Vec2 bottomLeft = fixture.getAABB().lowerBound;
		Vec2 topRight = fixture.getAABB().upperBound;
		// if (fixture.getType() == ShapeType.CIRCLE) {
		// System.out.println(bottomLeft);
		// System.out.println(topRight);
		// }
		gl.glBegin(GL.GL_LINE_LOOP);
		gl.glColor3f(0, 0, 1);
		gl.glVertex2d(bottomLeft.x, bottomLeft.y);
		gl.glVertex2d(topRight.x, bottomLeft.y);
		gl.glVertex2d(topRight.x, topRight.y);
		gl.glVertex2d(bottomLeft.x, topRight.y);
		gl.glEnd();
		gl.glPopMatrix();

		gl.glPointSize(5);
		gl.glBegin(GL.GL_POINTS);
		gl.glColor3f(0.5f, 0.5f, 0.5f);
		gl.glVertex2d(fixture.getAABB().getCenter().x, fixture.getAABB()
				.getCenter().y);
		gl.glEnd();
	}

	/***
	 * Draws axes for debugging
	 * 
	 * @param gl
	 *            OpenGL frame
	 */
	public void drawAxes(GL2 gl) {
		// X - Axis red
		gl.glBegin(GL.GL_LINES);
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		gl.glVertex3f(-5000.0f, -5.f, 0.0f);
		gl.glVertex3f(5000.0f, -5.f, 0.0f);
		gl.glEnd();
		// Y - Axis green
		gl.glBegin(GL.GL_LINES);
		gl.glColor3f(0.0f, 1.0f, 0.0f);
		gl.glVertex3f(0.0f, -5000.0f, 0.0f);
		gl.glVertex3f(0.0f, 5000.0f, 0.0f);
		gl.glEnd();
	}
}