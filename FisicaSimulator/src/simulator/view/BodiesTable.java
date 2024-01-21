package simulator.view;


import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.table.TableColumnModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import simulator.control.Controller;

@SuppressWarnings("serial")
public class BodiesTable extends JPanel {

	BodiesTable(Controller ctrl) {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Bodies", TitledBorder.LEFT, TitledBorder.TOP));
		setBackground(Color.white);
		
		this.setPreferredSize(new Dimension(700, 200));
		BodiesTableModel model=ctrl.getModel();
		
		JTable table=new JTable();
		table.setModel(model);
		TableColumnModel col = table.getColumnModel();
		
		this.add(table);
		this.add(new JScrollPane(new JTable(model)));
		
	}
}
