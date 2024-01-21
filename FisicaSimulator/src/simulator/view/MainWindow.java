package simulator.view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BoxLayout;

import simulator.control.Controller;

public class MainWindow extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// ...
	Controller _ctrl;

	public MainWindow(Controller ctrl) {
		super("Physics Simulator");
		_ctrl = ctrl;
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		this.setLocation(600, 50);
		this.setSize(new Dimension(700, 900));
		this.setContentPane(mainPanel);
		
		JPanel menu = new ControlPanel(_ctrl);
		mainPanel.add(menu);
		BodiesTable bodies = new BodiesTable(_ctrl);
		this.setPreferredSize(new Dimension(700,300));
		mainPanel.add(bodies);
		Viewer viewer = new Viewer(_ctrl);
		this.setPreferredSize(new Dimension(700,800));
		mainPanel.add(viewer);
		mainPanel.setBackground(Color.white);
		StatusBar status = new StatusBar(_ctrl);
		mainPanel.add(status);
		this.pack();
		this.setVisible(true);
	}

	private void setPreferredSize(int i, int j) {
		// TODO Auto-generated method stub
		
	}
}