import java.awt.*;
import javax.swing.*;
import java.util.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.awt.event.*;


class AppointmentFrame extends JFrame implements ActionListener
{
	private JLabel lbl;
	private JPanel left_panel, right_panel, p1, p2;
	private JTextField txt_name, txt_age, txt_mno, txt_gender, txt_place;
	private JButton b;
	private JTable tbl;
	private DefaultTableModel dtm;
	private Vector <Vector> name_data;
	private Vector <Vector> rows;
	private Vector <String> col;
	private int selected_index = -1;
	private AppointmentFrame me = this;



	public AppointmentFrame()
	{
		this.setLayout(new GridLayout(1,2));

		left_panel = new JPanel();
		right_panel = new JPanel();
		this.add(left_panel);
		this.add(right_panel);

//left
		left_panel.setLayout(null);

		lbl = new JLabel("APPOINTMENT SECTION");
		left_panel.add(lbl);
		lbl.setFont(new Font("calibri", Font.BOLD, 25));
		lbl.setBounds(130, 10, 300, 45);

		p1 = new JPanel();
		left_panel.add(p1);
		p1.setBounds(100, 150, 350, 170);
		p1.setLayout(new GridLayout(5, 2));

		lbl = new JLabel("Name");
		p1.add(lbl);

		txt_name = new JTextField();
		p1.add(txt_name);

		lbl = new JLabel("Mobile No.");
		p1.add(lbl);

		txt_mno = new JTextField();
		p1.add(txt_mno);

			lbl = new JLabel("Age");
		p1.add(lbl);

		txt_age = new JTextField();
		p1.add(txt_age);

			lbl = new JLabel("Gender");
		p1.add(lbl);

		txt_gender = new JTextField();
		p1.add(txt_gender);

			lbl = new JLabel("Place");
		p1.add(lbl);

		txt_place = new JTextField();
		p1.add(txt_place);

		b = new JButton("SUBMIT");
		left_panel.add(b);
		b.addActionListener(this);
		b.setBounds(200, 340, 100, 35);


//right

right_panel.setLayout(null);

lbl = new JLabel("LIST");
		right_panel.add(lbl);
		lbl.setFont(new Font("calibri", Font.BOLD, 25));
		lbl.setBounds(150, 10, 250, 45);


		col = new Vector <String> ();
		rows = new Vector <Vector> ();
		col.add("Name");
		col.add("Mobile No");
		col.add("Age");
		col.add("Gender");
		col.add("Place");


		dtm = new DefaultTableModel(rows, col);
		tbl = new JTable(dtm);
		tbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		ListSelectionModel sm = tbl.getSelectionModel();
		sm.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent e)
			{

				selected_index = tbl.getSelectedRow();

				// JOptionPane.showMessageDialog(me, selected_index);
			}
		});

		JScrollPane sp = new JScrollPane(tbl);
		
		right_panel.add(sp);
		sp.setBounds(0, 100, 400, 280);

		b = new JButton("REMOVE");
		right_panel.add(b);
		b.setBounds(150, 400, 100, 40);
		b.addActionListener(this);



		this.setVisible(true);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);	
		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
	}


	@Override
	public void actionPerformed(ActionEvent e)
	{
		String text  = e.getActionCommand();
		// JOptionPane.showMessageDialog(this, text);

		switch(text)
		{
		case "SUBMIT":
			// JOptionPane.showMessageDialog(this, "hi");
			Vector <String> temp = new Vector <String>();
			temp.add(txt_name.getText().trim().toUpperCase());
			temp.add(txt_mno.getText().trim().toUpperCase());
			temp.add(txt_age.getText().trim().toUpperCase());
			temp.add(txt_gender.getText().trim().toUpperCase());
			temp.add(txt_place.getText().trim().toUpperCase());
			
			rows.add(temp);

			dtm.setDataVector(rows, col);


			txt_name.setText("");
			txt_mno.setText("");
			txt_age.setText("");
			txt_gender.setText("");
			txt_place.setText("");

			break;


		case "REMOVE":
			if(selected_index >=0)
			{
				// JOptionPane.showMessageDialog(this, selected_index);

				rows.remove(selected_index);			
				dtm.setDataVector(rows, col);
			}
			else{
				selected_index = 0;
				
				rows.remove(selected_index);			
				dtm.setDataVector(rows, col);
			}
			
			break;
		}


	}

}