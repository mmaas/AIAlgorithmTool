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

import javax.swing.JFrame;

import control.Algorithm;
/***
 * ConfigurationTab for the algorithm
 * 
 * @author Marco Maas
 * @version 1.0
 */
public class ConfigurationTab {

	protected View v;
	protected Algorithm a;
	protected JFrame f;
/***
 * Simple constructor
 * @param view the view to which this instance belongs
 * @param a A algorithm
 */
	public ConfigurationTab(View view, Algorithm a) {
		this.v = view;
		this.a = a;
		initialize();
	}

	/***
	 * Creates window, sets options and adds the panel from the algorithm
	 */
	private void initialize() {
		// TODO Auto-generated method stub
		f = new JFrame("Configuration");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(1000, 220);
		final Toolkit toolkit = Toolkit.getDefaultToolkit();
		final Dimension screenSize = toolkit.getScreenSize();
		final int x = (screenSize.width - f.getWidth()) / 2;
		final int y = (screenSize.height - f.getHeight()) / 2;
		if (screenSize.width < 1920 || screenSize.height < 1080)
			f.setLocation(x, y);
		else
			f.setLocation(1290, y-250);

		f.add(a.p);
		f.pack();
		f.setResizable(false);
		f.setVisible(true);
	}

}
