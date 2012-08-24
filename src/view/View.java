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

import org.jbox2d.dynamics.World;

import model.Model;
import control.Algorithm;
import control.Control;

/***
 * View from MVC pattern
 * 
 * @author Marco Maas
 * @version 1.0
 */
public class View {
	protected Control c;
	protected Model m;
	protected World physicWorld;
	public OpenGLPlayTab pTab;
	protected PhysicConfigTab pCTab;
	protected ConfigurationTab cTab;
	public Boolean toggleStatus;
	@SuppressWarnings("unused")
	private HelpTab hTab;

	/***
	 * simple constructor
	 * 
	 * @param c
	 *            connection control <-> view
	 * @param m
	 *            connection model <-> view
	 * @param a
	 *            connection algorithm <-> view
	 * @param w
	 *            the box2d world to be drawn
	 */
	public View(Control c, Model m, Algorithm a, World w) {
		this.c = c;
		this.m = m;
		physicWorld = w;
		initialize(a);
	}

	/***
	 * Creates parts of the view and sets options.
	 * 
	 * @param a
	 *            algorithm which is used
	 */
	public void initialize(Algorithm a) {
		// pCTab = new PhysicConfigTab(this);
		cTab = new ConfigurationTab(this, a);
		hTab = new HelpTab();
		pTab = new OpenGLPlayTab(this);
		toggleStatus = true;
	}

	/***
	 * toggle view, on and off
	 */
	public void toggle() {
		toggleStatus = !toggleStatus;
		if (!toggleStatus) {
			pTab.zoom = 90;
		}
	}

}
