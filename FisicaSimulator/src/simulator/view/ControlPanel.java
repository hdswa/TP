package simulator.view;

import java.util.List;
import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.InputStream;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.Box;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JToolBar;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SpinnerNumberModel;


public class ControlPanel extends JPanel implements SimulatorObserver {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
// ...
	private Controller _ctrl;
	private JSpinner steps;
	private JTextField time;
	private boolean _stopped;
	private JFileChooser fileChooser;
	JButton openFileButton;
	JButton phisycsButton;
	JButton exitButton;
	JButton stopButton;
	JButton runButton;

	ControlPanel(Controller ctrl) {
		_ctrl = ctrl;
		_stopped = true;
		_ctrl.addObserver(this);
		initGUI();
	}

	private void initGUI() {
		
		//----------------------------------
		//Buttons
		//----------------------------------
		
		this.openFileButton = new JButton();
		this.phisycsButton = new JButton();
		this.exitButton = new JButton();
		this.stopButton = new JButton();
		this.runButton = new JButton();
		
		//Open File Button
		JToolBar toolBar = new JToolBar();
		openFileButton.setIcon(new ImageIcon(loadImage("resources/icons/open.png")));
		openFileButton.setPreferredSize(new Dimension(50, 50));

		//Phisycs Button
		phisycsButton.setIcon(new ImageIcon(loadImage("resources/icons/physics.png")));
		phisycsButton.setPreferredSize(new Dimension(50, 50));

		//Exit Button
		exitButton.setIcon(new ImageIcon(loadImage("resources/icons/exit.png")));
		exitButton.setPreferredSize(new Dimension(50, 50));

		//Stop Button
		stopButton.setIcon(new ImageIcon(loadImage("resources/icons/stop.png")));
		stopButton.setPreferredSize(new Dimension(50, 50));

		//Steps
		this.steps = new JSpinner(new SpinnerNumberModel(5, 0, _ctrl.getSim().getSteps(), 100));
		this.steps.setToolTipText("pasos a ejecutar: 1-10000");
		this.steps.setMaximumSize(new Dimension(1000, 50));
		this.steps.setMinimumSize(new Dimension(70, 50));
		this.steps.setPreferredSize(new Dimension(100,50));
		this.steps.setValue(_ctrl.getSim().getSteps());

		//Time
		this.time = new JTextField(Integer.toString((int)_ctrl.getSim().getDT()), 8);
		this.time.setToolTipText("Tiempo actual");
		this.time.setMaximumSize(new Dimension(1000, 50));
		this.time.setMinimumSize(new Dimension(70, 50));
		this.time.setPreferredSize(new Dimension(100,50));
		this.time.setEditable(true);

		//Run Button
		runButton.setIcon(new ImageIcon(loadImage("resources/icons/run.png")));
		runButton.setPreferredSize(new Dimension(50, 50));
		
		
		//----------------------------------
		//functionality
		//----------------------------------
		
		//run
		runButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (Double.parseDouble(time.getText()) > 0) {
						_ctrl.setDeltaTime(Double.parseDouble(time.getText()));
						int n = Integer.parseInt(steps.getValue().toString());
						phisycsButton.setEnabled(false);
						openFileButton.setEnabled(false);
						exitButton.setEnabled(false);
						_stopped = false;
					
						run_sim(n);
					} else
						showMessageDialog("Time value must be a possitive number >:( ");
				} catch (Exception ex) {
					showMessageDialog(ex.getMessage());
				}

			}
		});
		
		//open
		openFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadFile();
			}
		});
		
		//phisycs
		phisycsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new PhysicsTable(_ctrl);
			}
		});
		
		//exit
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				exit();
			}
		});
		
		//stop
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				phisycsButton.setEnabled(true);
				openFileButton.setEnabled(true);
				exitButton.setEnabled(true);
				_stopped = true;
			}
		});
		
		//adds
		toolBar.add(openFileButton);
		toolBar.addSeparator();
		toolBar.add(phisycsButton);
		toolBar.addSeparator();
		toolBar.add(runButton);
		toolBar.add(stopButton);
		toolBar.add(new JLabel(" Steps: "));
		toolBar.add(steps);
		toolBar.add(new JLabel(" Delta-Time: "));
		toolBar.add(time);
		toolBar.add(Box.createHorizontalStrut(75));
		toolBar.add(exitButton);
		this.add(toolBar);
	}
	
	//other methods
	private void run_sim(int n) {
		if ( n>0 && !_stopped ) {
			try {
				_ctrl.run(1);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, " invalid number");
				openFileButton.setEnabled(true);
				phisycsButton.setEnabled(true);
				exitButton.setEnabled(true);
				_stopped = true;
				return;
			}
			SwingUtilities.invokeLater( new Runnable() {
				@Override
				public void run() {
					run_sim(n-1);
				}
			});
		} else {
			_stopped = true;
			openFileButton.setEnabled(true);
			phisycsButton.setEnabled(true);
			exitButton.setEnabled(true);
		}
	}
	
	private void loadFile() {
		fileChooser =  new JFileChooser();
		fileChooser.setCurrentDirectory(new File("./resources/examples"));
		int returnVal = this.fileChooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = this.fileChooser.getSelectedFile();
			try {
				InputStream input = new FileInputStream(file);
				this._ctrl.reset();
				this._ctrl.loadBodies(input);
			} catch (FileNotFoundException e) {
				this.showMessageDialog("Error while loading file: " + e.getMessage());
			}
		}
	}

	public void showMessageDialog(String string) {
		JOptionPane.showMessageDialog(null, string);
	}

	public void exit() {
		int n = JOptionPane.showOptionDialog(new JFrame(), "Are sure you want to quit?", "Quit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
		if (n == 0) {
			this._ctrl.exit();
		}
	}
		
	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
		this._ctrl.setDeltaTime(dt);
	}
		
	@Override
	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
	}
		
	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
	}
		
	@Override
	public void onAdvance(List<Body> bodies, double time) {
	repaint();
	}
		
	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub
		time.setText(Double.toString(dt));
	}
		
	@Override
	public void onForceLawsChanged(String fLawsDesc) {
		// TODO Auto-generated method stub
	}
		
	public Image loadImage(String path) {
		return Toolkit.getDefaultToolkit().createImage(path);
	}

	}