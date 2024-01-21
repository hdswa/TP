package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

@SuppressWarnings("serial")
public class BodiesTableModel extends AbstractTableModel implements SimulatorObserver {


	private List<Body> _bodies;
	static private final String[] column = {"Id", "Mass", "Position", "Velocity", "Force"};

	public BodiesTableModel(Controller ctrl) {
		_bodies = new ArrayList<>();
		ctrl.addObserver(this);
	}

	@Override
	public int getRowCount() {
		return _bodies.size();
	}

	@Override
	public int getColumnCount() {
		return column.length;
	}

	@Override
	public String getColumnName(int column) {
		return BodiesTableModel.column[column].toString();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
	// TODO complete
		Body x =_bodies.get(rowIndex);
		Object y = null;
		if (column[columnIndex].equalsIgnoreCase("Id")) {
			y = x.getId();
		}
		else if (column[columnIndex].equalsIgnoreCase("Mass")) {
			y = x.getMass();
		}
		else if (column[columnIndex].equalsIgnoreCase("Position")) {
			y = x.getPos();
		}
		else if (column[columnIndex].equalsIgnoreCase("Velocity")) {
			y = x.getVel();
		}
		else if (column[columnIndex].equalsIgnoreCase("Force")) {
			y = x.getForce();
		}
		return y;
	}

	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) {
		this._bodies = bodies;
		fireTableStructureChanged();
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {
		this._bodies = bodies;
		fireTableStructureChanged();
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		this._bodies = bodies;
		fireTableStructureChanged();
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		this._bodies = bodies;
		fireTableStructureChanged();
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
	}

	@Override
	public void onForceLawsChanged(String fLawsDesc) {
	}

}
