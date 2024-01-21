package simulator.view;

import org.json.JSONArray;
import org.json.JSONObject;
import simulator.control.Controller;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;

import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class PhysicsTable extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<JSONObject> forceList;
	private Controller _ctrl;
	private JLabel info;
	private LawsTable table;
	private DefaultComboBoxModel comboBox;
	private JSONObject forcelawsinfo;

	public PhysicsTable(Controller ctrl) {
		super();
		_ctrl = ctrl;
		initGUI();
	}

	private void initGUI() {
		
		//-----------------------------
		// Table
		//-----------------------------
		
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		this.setLocation(600, 300);
		this.setSize(700, 450);
		
		
		//-----------------------------
		// Info Panel
		//-----------------------------
		
		info = new JLabel();
		info.setAlignmentX(Component.CENTER_ALIGNMENT);
		info.setText("Select a force law and provide values for the parametes in the Value column(default values if no value given).");
		getContentPane().add(info);
		
		
		//-----------------------------
		// ComboBox Panel
		//-----------------------------
		
		JPanel forcePanel = new JPanel();
		JLabel forceLabel = new JLabel("Force Law: ");
		forcePanel.add(forceLabel);
		forceList = _ctrl.getForceLawsInfo();
		comboBox = new DefaultComboBoxModel<>();
		for (JSONObject jo : forceList) {
			comboBox.addElement(jo.getString("desc"));
		}
		
		LawsTableModel tableModel = new LawsTableModel(_ctrl);
		table = new LawsTable(_ctrl, tableModel);
		add(table, BorderLayout.CENTER);
		JComboBox<String> comboBoxf = new JComboBox<>(comboBox);
		comboBoxf.setSelectedItem(0);
		JSONObject data = (JSONObject) forceList.get(0).get("data");
		tableModel.updateTable(data);
		forcePanel.add(comboBoxf);
		getContentPane().add(forcePanel);

		
		//-----------------------------
		//Buttons
		//-----------------------------
		
		JPanel pButton = new JPanel();
		pButton.setLayout(new BoxLayout(pButton, BoxLayout.X_AXIS));
		
		JButton cancel = new JButton("Cancel");
		JButton ok = new JButton("OK");
		
		
		//-----------------------------
		//functionality
		//-----------------------------
		
		ActionListener comboBoxListener = new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				tableModel.reset();
						JSONObject f = forceList.get(comboBoxf.getSelectedIndex());
						JSONObject data = (JSONObject) f.get("data");
						tableModel.updateTable(data);
			}
		};
		
		ActionListener okListener = new ActionListener() {
			@Override 
			public void actionPerformed(ActionEvent e) {
				JSONObject law = new JSONObject();
				String type = (forceList.get(comboBoxf.getSelectedIndex()).getString("type"));
				law.put("type",type);
				law.put("data", tableModel.createData(type));
				law.put("desc", forceList.get(comboBoxf.getSelectedIndex()).getString("desc"));
				_ctrl.setForceLaws(law);
				PhysicsTable.this.setVisible(false);
			}
		};
		
		ActionListener cancelListener = new ActionListener() {
			@Override 
			public void actionPerformed(ActionEvent e) {
				PhysicsTable.this.setVisible(false);
			}
		};
		
		
		//-----------------------------
		//adds
		//-----------------------------
		
		comboBoxf.addActionListener(comboBoxListener);
		cancel.addActionListener(cancelListener);
		ok.addActionListener(okListener);
		pButton.add(cancel);
		pButton.add(Box.createHorizontalStrut(20));
		pButton.add(ok);
		getContentPane().add(pButton);
		this.setVisible(true);
	}
	
	private class LawsTable extends JPanel {
		private JTable lawTable;
		LawsTable(Controller ctrl, LawsTableModel lawsTableModel) {
			lawTable = new JTable(lawsTableModel);
			JScrollPane scrollPane = new JScrollPane(lawTable);
			setLayout(new BorderLayout());
			this.add(scrollPane, BorderLayout.CENTER);
		}
	}

	private class Row {
		private String key;
		private String value;
		private String description;

		public Row(String key, String value, String description) {
			this.key = key;
			this.value = value;
			this.description = description;
		}

		public String getKey() {
			return key;
		}

		public String getValue() {
			return value;
		}

		public String getDesc() {
			return description;
		}
	}
	
	private class LawsTableModel extends AbstractTableModel {
		private List<Row> data;
		private String columns[] = { "Key", "Value", "Description" };

		LawsTableModel(Controller ctrl) {
			data = new ArrayList<>();
		}

		private void updateTable(JSONObject jo) {
			for(String key: jo.keySet()) {
				data.add(new Row(key, "", jo.getString(key)));
			}
			this.fireTableStructureChanged();
		}

		@Override
		public int getRowCount() {
			if (this.data == null)
				return 0;
			else
				return this.data.size();
		}

		@Override
		public int getColumnCount() {
			return columns.length;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			switch (columnIndex) {
			case 0:
				return data.get(rowIndex).getKey();
			case 1:
				return data.get(rowIndex).getValue();
			case 2:
				return data.get(rowIndex).getDesc();
			}
			return "";
		}

		@Override
		public String getColumnName(int col) {
			if (this.columns == null)
				return "";
			else
				return this.columns[col];
		}
		
		

		@Override
		public void setValueAt(Object value, int row, int col) {
			Row r = data.get(row);
			data.set(row, new Row(r.getKey(), value.toString(), r.getDesc()));
			fireTableDataChanged();
		}
		
		@Override
		public boolean isCellEditable(int row, int colum) {
			return colum == 1;
		}
		
		public JSONObject createData(String type) {
			JSONObject data = new JSONObject();
			for (int i = 0; i < getRowCount(); i++) {
				if(getValueAt(i, 1) != "") {
					try {
						checkData(type, data);
					}catch(NumberFormatException n) {
						JOptionPane.showMessageDialog(null,"Invalid number");
						break;
					}catch (Exception e) {
						JOptionPane.showMessageDialog(null,"Invalid center. default value [0.0,0.0]");
						break;
					}
					
		
				}
			}
			return data;
		}
		
		public void reset() {
			data.clear();
		}

		private void checkData(String type, JSONObject data) throws NumberFormatException, Exception {
			switch(type) {
				case "mtfp": {
					JSONArray array = new JSONArray();
					String valor = getValueAt(0,1).toString();
					String valorC;
					valor = valor.replace("[", "");
					valor = valor.replace("]", "");
					String[] valorJ = valor.split(",");
					if(valorJ.length==2 || valor.length()==0) {
						for(int l = 0; l< valorJ.length; l ++){
				            valorC = valorJ[1];
				            if(!valorC.isEmpty()) {
				            	try {	
				            		array.put(Double.parseDouble(String.valueOf(valorC)));
				            	}catch (NumberFormatException n){
				            		throw n;
				            	}
				            }
				        }
					}else
						throw new Exception();	
				    
					data.put((String) getValueAt(0, 0), array);
					
					String gravity = getValueAt(1,1).toString();
					if(!gravity.isEmpty())
						data.put((String) getValueAt(1, 0), Double.parseDouble(getValueAt(1,1).toString()));
				}break;
				case "nlug": {
					if(getValueAt(0,0)!="") {
						data.put((String) getValueAt(0, 0), Double.parseDouble(getValueAt(0,1).toString()));
					}else {
						try {
						data.put((String) getValueAt(0, 0), Double.parseDouble(getValueAt(0,1).toString()));
						}catch (NumberFormatException e) {
							throw e;
						}
					}
	
				}break;
				case "ng":{
				}
			}
		}
	}
}