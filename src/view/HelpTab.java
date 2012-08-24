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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JTextArea;

/***
 * HelpTab
 * 
 * @author Marco Maas
 * @version 1.0
 */
public class HelpTab {
	JFrame f;
	final int x;
	final int y;

	/***
	 * Simple Constructor. Creates window, sets options and adds the help text.
	 * 
	 */
	public HelpTab() {
		f = new JFrame("Help and About");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final Toolkit toolkit = Toolkit.getDefaultToolkit();
		final Dimension screenSize = toolkit.getScreenSize();
		x = (screenSize.width - f.getWidth()) / 2;
		y = (screenSize.height - f.getHeight()) / 2;
		if (screenSize.width < 1920 || screenSize.height < 1080)
			f.setLocation(x, y);
		else
			f.setLocation(0, y + 370);

		String help = new String(
				"Choose your options before the first start. After the first start only the mutation and crossover options can be changed.\n"
						+ "Shortcuts:\nt\ttoggle view\na\tshow algorithm information\nd\tdebug view\n+ and -\tzoom\nb\ttransparency(experimental)\n"
						+ "\nCreated by Marco Maas, in the year 2012, during his bachelor thesis within AGKI (University Koblenz).");

		JTextArea text = new JTextArea();
		Font font = new Font("Verdana", Font.BOLD, 12);
		text.setFont(font);
		text.setEnabled(false);
		text.setDisabledTextColor(Color.black);
		text.setText(help);

		f.add(text);
		f.pack();
		f.setResizable(false);
		f.setVisible(true);
	}

}
