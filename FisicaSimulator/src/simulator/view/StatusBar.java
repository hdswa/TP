package simulator.view;

import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

@SuppressWarnings("serial")
public class StatusBar extends JPanel implements SimulatorObserver {

	private JLabel _currTime; 
	private JLabel _currLaws; 
	private JLabel _numOfBodies; 

	StatusBar(Controller ctrl) {
		this._currTime = new JLabel("Time: " + 0.0); //current time
		this._numOfBodies = new JLabel("Bodies: " + 0); //current number of bodies
		this._currLaws = new JLabel("Laws: "+ ctrl.getForceLaws().toString() ); //current value of default force law
		initGUI();
		ctrl.addObserver(this);
	}

	private void initGUI() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBorder(BorderFactory.createBevelBorder(1));
		this.add(_currTime);
		this.add(Box.createHorizontalStrut(55));
		this.add(_numOfBodies);
		this.add(Box.createHorizontalStrut(55));
		this.add(_currLaws);
	}

	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) {
		this._numOfBodies.setText("Bodies: " + String.valueOf(bodies.size()));
		this._currTime.setText("Time: " + time);
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {
		this._numOfBodies.setText(String.valueOf(0));
		this._currTime.setText(String.valueOf("Time: " + time));
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		this._numOfBodies.setText("Bodies: " + String.valueOf(bodies.size()));
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		this._currTime.setText("Time: " + String.valueOf(time));
	}

	@Override
	public void onDeltaTimeChanged(double dt) {

	}

	@Override
	public void onForceLawsChanged(String fLawsDesc) {
		// TODO Auto-generated method stub
		_currLaws.setText("Laws: " + fLawsDesc);
	}

}
