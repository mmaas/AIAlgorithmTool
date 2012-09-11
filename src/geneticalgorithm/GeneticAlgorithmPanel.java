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

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/***
 * Class which creates the view element of the genetic algorithm
 * 
 * @author Marco Maas
 * @version 1.0
 */
public class GeneticAlgorithmPanel {
	GeneticAlgorithm g;
	JButton b2, b3;

	/***
	 * simple constructor
	 * 
	 * @param g
	 *            genetic algorithm
	 */
	public GeneticAlgorithmPanel(GeneticAlgorithm g) {
		this.g = g;
	}

	/***
	 * Creates a panel which represents the customizable settings of the
	 * algorithm and some standard buttons
	 * 
	 * @return view component of genetic algorithm
	 */
	@SuppressWarnings({ "rawtypes" })
	public JTabbedPane createPanel() {
		final CrossoverOperator cross = (CrossoverOperator) g.cross;
		final LinesAlgorithmDataFactory factory = (LinesAlgorithmDataFactory)g.fac;
		
		// TODO Auto-generated method stub
		JTabbedPane tabbedpane = new JTabbedPane();
		tabbedpane.setAlignmentX(Component.LEFT_ALIGNMENT);

		JPanel panel1 = new JPanel();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.PAGE_AXIS));
		panel1.setAlignmentX(Component.LEFT_ALIGNMENT);
		;
		Box box1 = new Box(BoxLayout.Y_AXIS);
		Box box2 = new Box(BoxLayout.Y_AXIS);

		JPanel panel2 = new JPanel();
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.PAGE_AXIS));
		panel2.setAlignmentX(Component.LEFT_ALIGNMENT);
		;
		JPanel panel3 = new JPanel();
		panel3.setLayout(new BoxLayout(panel3, BoxLayout.PAGE_AXIS));
		panel3.setAlignmentX(Component.LEFT_ALIGNMENT);
		;
		Box box3 = new Box(BoxLayout.Y_AXIS);
		Box box4 = new Box(BoxLayout.Y_AXIS);

		JLabel sliderlabel1 = new JLabel("Maximum length", JLabel.LEFT);
		sliderlabel1.setToolTipText("How long should the cars be?");
		JSlider slider1 = new JSlider(JSlider.HORIZONTAL, 1, 8,
				(int) factory.maxLength);
		slider1.setToolTipText("How long should the cars be?");
		slider1.setMinorTickSpacing(1);
		slider1.setMajorTickSpacing(7);
		slider1.setPaintTicks(true);
		slider1.setPaintLabels(true);
		slider1.setSnapToTicks(true);
		slider1.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				JSlider source = (JSlider) e.getSource();
				if (!g.started) {
					factory.maxLength = source.getValue();
					g.changed = true;
				} else {
					source.setValue((int) factory.maxLength);
				}
			}
		});

		JLabel sliderlabel2 = new JLabel("Maximum height", JLabel.LEFT);
		sliderlabel2.setToolTipText("How high should the cars be?");
		JSlider slider2 = new JSlider(JSlider.HORIZONTAL, 1, 10,
				(int) factory.maxHeight);
		slider2.setToolTipText("How high should the cars be?");
		slider2.setMinorTickSpacing(1);
		slider2.setMajorTickSpacing(9);
		slider2.setPaintTicks(true);
		slider2.setPaintLabels(true);
		slider2.setSnapToTicks(true);
		slider2.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				JSlider source = (JSlider) e.getSource();
				if (!g.started) {
					factory.maxHeight = source.getValue();
					g.changed = true;
				} else {
					source.setValue((int) factory.maxHeight);
				}
			}
		});

		JLabel sliderlabel3 = new JLabel("Maximum line length", JLabel.LEFT);
		sliderlabel3.setToolTipText("How long can the perpendicular lines be?");
		JSlider slider3 = new JSlider(JSlider.HORIZONTAL, 1, 10,
				(int) factory.maxLineLength);
		slider3.setToolTipText("How long can the perpendicular lines be?");
		slider3.setMinorTickSpacing(1);
		slider3.setMajorTickSpacing(9);
		slider3.setPaintTicks(true);
		slider3.setPaintLabels(true);
		slider3.setSnapToTicks(true);
		slider3.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				JSlider source = (JSlider) e.getSource();
				if (!g.started) {
					factory.maxLineLength = source.getValue();
					g.changed = true;
				} else {
					source.setValue((int) factory.maxLineLength);
				}
			}
		});

		JLabel sliderlabel4 = new JLabel("Line number", JLabel.LEFT);
		sliderlabel4.setToolTipText("How many lines should create a car?");
		JSlider slider4 = new JSlider(JSlider.HORIZONTAL, 2, 10,
				(int) factory.lineNumber);
		slider4.setToolTipText("How many lines should create a car?");
		slider4.setMinorTickSpacing(1);
		slider4.setMajorTickSpacing(8);
		slider4.setPaintTicks(true);
		slider4.setPaintLabels(true);
		slider4.setSnapToTicks(true);
		slider4.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				JSlider source = (JSlider) e.getSource();
				if (!g.started) {
					factory.lineNumber = source.getValue();
					g.changed = true;
				} else {
					source.setValue(factory.lineNumber);
				}
			}
		});

		JLabel sliderlabel6 = new JLabel("Mutation rate in percent",
				JLabel.LEFT);
		final JSlider slider6 = new JSlider(JSlider.HORIZONTAL, 1, 100,
				(int) g.mutation);
		slider6.setMinorTickSpacing(1);
		slider6.setMajorTickSpacing(99);
		slider6.setPaintTicks(true);
		slider6.setPaintLabels(true);
		slider6.setSnapToTicks(true);
		slider6.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				JSlider source = (JSlider) e.getSource();
				g.mutation = source.getValue();
			}
		});

		JLabel sliderlabel5 = new JLabel("Population size", JLabel.LEFT);

		JSlider slider5 = new JSlider(JSlider.HORIZONTAL, 10, 250,
				(int) g.population);
		slider5.setMinorTickSpacing(10);
		slider5.setMajorTickSpacing(240);
		slider5.setPaintTicks(true);
		slider5.setPaintLabels(true);
		slider5.setSnapToTicks(true);
		slider5.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				JSlider source = (JSlider) e.getSource();
				if (!g.started) {
					g.population = source.getValue();
					slider6.setValue(g.mutation);
					g.changed = true;
				} else {
					source.setValue(g.population);
				}
			}
		});

		JLabel sliderlabel7 = new JLabel("Crossover rate in percent",
				JLabel.LEFT);
		JSlider slider7 = new JSlider(JSlider.HORIZONTAL, 0, 100,
				(int) cross.crossover);
		slider7.setMinorTickSpacing(5);
		slider7.setMajorTickSpacing(100);
		slider7.setPaintTicks(true);
		slider7.setPaintLabels(true);
		slider7.setSnapToTicks(true);
		slider7.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				JSlider source = (JSlider) e.getSource();
				cross.crossover = source.getValue();
			}
		});

		b2 = new JButton("Show latest best car");
		ActionListener al2 = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				g.showBestcar = true;
				g.showLatest = true;
			}
		};
		b2.setEnabled(false);

		b3 = new JButton("Show overall best car");
		ActionListener al3 = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				g.showBestcar = true;
				g.showOverall = true;
			}
		};
		b3.setEnabled(false);

		JButton b1 = new JButton("Start the Simulation");
		ActionListener al1 = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (!g.started) {
					g.started = true;
				}
				g.c.registeredViews.get(0).pTab.pause = !g.c.registeredViews
						.get(0).pTab.pause;
				JButton button = (JButton) arg0.getSource();
				if (g.c.registeredViews.get(0).pTab.pause)
					button.setText("Start the Simulation");
				else
					button.setText("Pause the Simulation");
			}
		};

		JCheckBox cb1 = new JCheckBox("Fixed length?");
		cb1.setToolTipText("Should the car length be fixed?");
		cb1.setSelected(factory.fixedLength);
		cb1.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				JCheckBox cb = (JCheckBox) e.getSource();
				if (!g.started) {
					factory.fixedLength = cb.isSelected();
					g.changed = true;
				} else
					cb.setSelected(factory.fixedLength);
			}
		});

		JCheckBox cb2 = new JCheckBox("All-wheel drive?");
		cb2.setToolTipText("Should every wheel have an engine?");
		cb2.setSelected(factory.allWheel);
		cb2.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				JCheckBox cb = (JCheckBox) e.getSource();
				if (!g.started) {
					factory.allWheel = cb.isSelected();
					g.c.p.factory.setAllWheel(cb.isSelected());
					g.changed = true;
				} else
					cb.setSelected(factory.allWheel);
			}
		});

		JCheckBox cb3 = new JCheckBox("Fixed Bitmask?");
		cb3.setToolTipText("Should the crossover bitmask be fixed?");
		
		cb3.setSelected(cross.fixedMask);
		cb3.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				JCheckBox cb = (JCheckBox) e.getSource();
				cross.fixedMask = cb.isSelected();
			}
		});
		JLabel choicelabel1 = new JLabel("What should be crossovered?",
				JLabel.LEFT);
		choicelabel1
				.setToolTipText("Pick the genom part which should be crossovered");
		JComboBox choice1 = new JComboBox();
		choice1.setToolTipText("Pick the genom part which should be crossovered");
		choice1.setEditable(false);
		choice1.addItem("1 whole point (30 bits)");
		choice1.addItem("1 point x coordinate (10 bits)");
		choice1.addItem("1 point y coordinate (10 bits)");
		choice1.addItem("1 point length (10 bits)");
		choice1.addItem("Whole genome");
		choice1.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				JComboBox selectedChoice = (JComboBox) e.getSource();
				if (selectedChoice.getSelectedItem().equals(
						"1 whole point (30 bits)")) {
					g.crType = 0;
				}
				if (selectedChoice.getSelectedItem().equals(
						"1 point x coordinate (10 bits)")) {
					g.crType = 1;
				}
				if (selectedChoice.getSelectedItem().equals(
						"1 point y coordinate (10 bits)")) {
					g.crType = 2;
				}
				if (selectedChoice.getSelectedItem().equals(
						"1 point length (10 bits)")) {
					g.crType = 3;
				}
				if (selectedChoice.getSelectedItem().equals("Whole genome")) {
					g.crType = 4;
				}
			}

		});

		JLabel choicelabel2 = new JLabel("How should it be crossovered?",
				JLabel.LEFT);
		choicelabel2.setToolTipText("Pick a crossover algorithm");
		JComboBox choice2 = new JComboBox();
		choice2.setToolTipText("Pick a crossover algorithm");
		choice2.setEditable(false);
		choice2.addItem("2 Point");
		choice2.addItem("1 Point");
		choice2.addItem("Uniform");
		choice2.addItem("Random");

		choice2.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				JComboBox selectedChoice = (JComboBox) e.getSource();
				if (selectedChoice.getSelectedItem().equals("2 Point")) {
					g.crAlgorithm = 0;
				}
				if (selectedChoice.getSelectedItem().equals("1 Point")) {
					g.crAlgorithm = 1;
				}
				if (selectedChoice.getSelectedItem().equals("Uniform")) {
					// Einkommentieren wenn Uniform vorhanden
					// g.cross.crAlgorithm = 2;
					g.crAlgorithm = 0;
					selectedChoice.setSelectedIndex(0);
				}
				if (selectedChoice.getSelectedItem().equals("Random")) {
					g.crAlgorithm = 3;
				}

			}

		});

		b1.addActionListener(al1);
		b2.addActionListener(al2);
		b3.addActionListener(al3);

		TitledBorder tb1 = new TitledBorder("Car size:");
		TitledBorder tb2 = new TitledBorder("Other options:");
		box1.add(sliderlabel1);
		box1.add(slider1);
		box1.add(sliderlabel2);
		box1.add(slider2);
		box1.add(sliderlabel4);
		box1.add(slider4);
		box1.add(sliderlabel3);
		box1.add(slider3);
		box1.add(cb1);
		box1.setBorder(tb1);

		box2.add(cb2);
		box2.setBorder(tb2);
		panel1.add(box1);
		panel1.add(box2);
		tabbedpane.addTab("Car Configuration", panel1);
		tabbedpane.setToolTipTextAt(0, "Options for the car");

		TitledBorder tb3 = new TitledBorder("General options:");
		TitledBorder tb4 = new TitledBorder("Crossover options:");

		box3.add(sliderlabel5);
		box3.add(slider5);
		box3.add(sliderlabel6);
		box3.add(slider6);
		box3.setBorder(tb3);
		box4.add(sliderlabel7);
		box4.add(slider7);
		box4.add(choicelabel1);
		box4.add(choice1);
		box4.add(choicelabel2);
		box4.add(choice2);
		box4.add(cb3);
		box4.setBorder(tb4);
		panel2.add(box3);
		panel2.add(box4);
		tabbedpane.addTab("Genetic Algorithm Configuration", panel2);
		tabbedpane.setToolTipTextAt(1, "Options for the Genetic Algorithm");

		panel3.add(b2);
		panel3.add(b3);
		panel3.add(b1);
		tabbedpane.addTab("Simulation Options", panel3);
		tabbedpane.setToolTipTextAt(2, "Start here");
		tabbedpane.setSelectedIndex(2);
		return tabbedpane;
	}

}
