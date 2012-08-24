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

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import com.jogamp.opengl.util.FPSAnimator;
/***
 * OpenGLPlayTab
 * 
 * @author Marco Maas
 * @version 1.0
 */
public class OpenGLPlayTab implements KeyListener {

	protected View v;
	protected JFrame f;
	protected int sizeX, sizeY;
	protected JScrollPane scroll;
	protected boolean debug;
	protected boolean blend;
	public boolean pause;
	protected int zoom;
	FPSAnimator animator;
	public boolean stop;
	public boolean algorithmInformation;
	public Vector<String> output;
	public Vector<String> outputAlgorithmInformation;
	public int cars;
	final long startTime = System.currentTimeMillis();
/***
 * Simple constructor which sets default options
 * @param v the view to which this instance belongs
 */
	public OpenGLPlayTab(View v) {
		this.v = v;
		debug = false;
		blend = false;
		stop = true;
		pause = true;
		algorithmInformation = false;
		zoom = 90;
		output = new Vector<String>();
		outputAlgorithmInformation = new Vector<String>();
		cars = 0;
		initialize();
	}

	/***
	 * Creates window, sets options and adds a opengl output with limited fps.
	 */
	private void initialize() {
		// TODO Auto-generated method stub
		f = new JFrame(
				"AI Algorithm Tool: Designing Cars with Genetic Algorithm");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(1280, 720);
		sizeX = 1280;
		sizeY = 720;

		final Toolkit toolkit = Toolkit.getDefaultToolkit();
		final Dimension screenSize = toolkit.getScreenSize();
		final int x = (screenSize.width - f.getWidth()) / 2;
		final int y = (screenSize.height - f.getHeight()) / 2;
		if (screenSize.width < 1920 || screenSize.height < 1080)
			f.setLocation(x, y);
		else
			f.setLocation(0, y);

		f.addKeyListener(this);

		GLProfile glp = GLProfile.getDefault();
		GLCapabilities caps = new GLCapabilities(glp);
		caps.setDoubleBuffered(true);
		caps.setNumSamples(4);
		caps.setSampleBuffers(true);
		GLCanvas canvas = new GLCanvas(caps);

		scroll = new JScrollPane(canvas,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.addKeyListener(this);

		f.add(scroll);
		f.setResizable(false);
		f.setVisible(true);

		canvas.addGLEventListener(new Box2DDrawerOpenGL(this));
		canvas.setEnabled(false);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		animator = new FPSAnimator(canvas, 60);
		animator.add(canvas);
		animator.setUpdateFPSFrames(5, null);
		animator.start();

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getKeyChar() == 't') {
			v.toggle();
		}
		if (arg0.getKeyChar() == 'd') {
			debug = !debug;
		}
		if (arg0.getKeyChar() == 'b') {
			blend = !blend;
		}
		if (arg0.getKeyChar() == 'a') {
			algorithmInformation = !algorithmInformation;
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getKeyChar() == '+') {
			if (zoom > 5)
				zoom -= 5;
		}
		if (arg0.getKeyChar() == '-') {
			if (zoom < 175)
				zoom += 5;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
/***
 * stops the animator which limits the frames
 * @param b stop yes/no
 */
	public void stop(boolean b) {
		// TODO Auto-generated method stub
		stop = b;
		if (b) {
			animator.stop();
		} else {
			animator.start();
		}
	}

}
