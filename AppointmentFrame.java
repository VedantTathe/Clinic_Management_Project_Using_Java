<<<<<<< HEAD
import java.awt.*;
import javax.swing.*;
import java.util.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.regex.*;


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
	private JRadioButton r1,r2;
	private String gender, err_msg;

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
		p1.setBounds(100, 150, 350, 220);
		p1.setLayout(null);
		// p1.setBackground(Color.PINK);

		lbl = new JLabel("Enter Name");
		p1.add(lbl);
		lbl.setBounds(10,10,150,35);

		txt_name = new JTextField();
		p1.add(txt_name);
		txt_name.setBounds(150,15,150,25);
		
		lbl = new JLabel("Enter Mobile No.");
		p1.add(lbl);
		lbl.setBounds(10,55,150,35);

		txt_mno = new JTextField();
		p1.add(txt_mno);
		txt_mno.setBounds(150,60,150,25);

		lbl = new JLabel("Enter Age");
		p1.add(lbl);
		lbl.setBounds(10,95,150,35);

		txt_age = new JTextField();
		p1.add(txt_age);
		txt_age.setBounds(150,100,150,25);

		lbl = new JLabel("Select Gender");
		p1.add(lbl);
		lbl.setBounds(10,135,150,35);

		r1 = new JRadioButton("MALE");
		r1.addActionListener(this);
		r2 = new JRadioButton("FEMALE");
		r2.addActionListener(this);
		r1.setBounds(150,140,70,25);
		r2.setBounds(230,140,70,25);

		ButtonGroup bg = new ButtonGroup();
		bg.add(r1);
		bg.add(r2);

		p1.add(r1);
		p1.add(r2);

		lbl = new JLabel("Place");
		p1.add(lbl);
		lbl.setBounds(10,170,150,35);

		txt_place = new JTextField();
		p1.add(txt_place);
		txt_place.setBounds(150,175,150,25);

		b = new JButton("SUBMIT");
		left_panel.add(b);
		b.addActionListener(this);
		b.setBounds(150, 370, 100, 35);


		b = new JButton("CLEAR");
		left_panel.add(b);
		b.addActionListener(this);
		b.setBounds(300, 370, 100, 35);



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
		case "MALE":
			gender = "MALE";
			break;

		case "FEMALE":
			gender = "FEMALE";
			break;


		case "SUBMIT":

		Pattern LetterPattern = Pattern.compile("[a-zA-Z]+$");
		Pattern NumberPattern = Pattern.compile("[7-9][0-9]{9}");

		err_msg = ""; 

		if(!(LetterPattern.matcher(txt_name.getText()).matches()) || txt_name.getText().equals(""))
		{
			err_msg = err_msg + "Please enter valid Name\n";
		}

		// if(!(LetterPattern.matcher(txt_gender.getText()).matches()) || txt_gender.getText().equals(""))
		// {
		// 	err_msg = err_msg + "Please enter valid Gender\n";

		// }

		if(!(LetterPattern.matcher(txt_place.getText()).matches()) || txt_place.getText().equals(""))
		{
			err_msg = err_msg + "Please enter valid Place\n";
		}



		try{
			int myknown_err = Integer.parseInt(txt_age.getText().trim());
		}
		catch(NumberFormatException ex)
		{
			err_msg = err_msg + "Please enter valid Age\n";	
		}

		if(!(NumberPattern.matcher(txt_mno.getText()).matches()) || txt_mno.getText().equals(""))
		{
			err_msg = err_msg + "Please enter valid Mobile Number\n";	
		}

		

		if(err_msg.length() == 0)
		{
			Vector <String> temp = new Vector <String>();

				temp.add(txt_name.getText().trim().toUpperCase());
				temp.add(txt_mno.getText().trim().toUpperCase());
				temp.add(txt_age.getText().trim().toUpperCase());
				temp.add(gender);
				temp.add(txt_place.getText().trim().toUpperCase());

			
			rows.add(temp);

			dtm.setDataVector(rows, col);


			txt_name.setText("");
			txt_mno.setText("");
			txt_age.setText("");
			// txt_gender.setText("");
			r1.setSelected(false);
			r2.setSelected(false);
			txt_place.setText("");			
		}
		else
		JOptionPane.showMessageDialog(this,err_msg);	

	err_msg = "";

			break;


		case "CLEAR":
			txt_name.setText("");
			txt_mno.setText("");
			txt_age.setText("");
			r1.setSelected(false);
			r2.setSelected(false);
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

=======
import java.awt.*;
import javax.swing.*;
import java.util.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.regex.*;


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
	private JRadioButton r1,r2;
	private String gender, err_msg;

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
		p1.setBounds(100, 150, 350, 220);
		p1.setLayout(null);
		// p1.setBackground(Color.PINK);

		lbl = new JLabel("Enter Name");
		p1.add(lbl);
		lbl.setBounds(10,10,150,35);

		txt_name = new JTextField();
		p1.add(txt_name);
		txt_name.setBounds(150,15,150,25);
		
		lbl = new JLabel("Enter Mobile No.");
		p1.add(lbl);
		lbl.setBounds(10,55,150,35);

		txt_mno = new JTextField();
		p1.add(txt_mno);
		txt_mno.setBounds(150,60,150,25);

		lbl = new JLabel("Enter Age");
		p1.add(lbl);
		lbl.setBounds(10,95,150,35);

		txt_age = new JTextField();
		p1.add(txt_age);
		txt_age.setBounds(150,100,150,25);

		lbl = new JLabel("Select Gender");
		p1.add(lbl);
		lbl.setBounds(10,135,150,35);

		r1 = new JRadioButton("MALE");
		r1.addActionListener(this);
		r2 = new JRadioButton("FEMALE");
		r2.addActionListener(this);
		r1.setBounds(150,140,70,25);
		r2.setBounds(230,140,70,25);

		ButtonGroup bg = new ButtonGroup();
		bg.add(r1);
		bg.add(r2);

		p1.add(r1);
		p1.add(r2);

		lbl = new JLabel("Place");
		p1.add(lbl);
		lbl.setBounds(10,170,150,35);

		txt_place = new JTextField();
		p1.add(txt_place);
		txt_place.setBounds(150,175,150,25);

		b = new JButton("SUBMIT");
		left_panel.add(b);
		b.addActionListener(this);
		b.setBounds(150, 370, 100, 35);


		b = new JButton("CLEAR");
		left_panel.add(b);
		b.addActionListener(this);
		b.setBounds(300, 370, 100, 35);



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
		case "MALE":
			gender = "MALE";
			break;

		case "FEMALE":
			gender = "FEMALE";
			break;


		case "SUBMIT":

		Pattern LetterPattern = Pattern.compile("[a-zA-Z]+$");
		Pattern NumberPattern = Pattern.compile("[7-9][0-9]{9}");

		err_msg = ""; 

		if(!(LetterPattern.matcher(txt_name.getText()).matches()) || txt_name.getText().equals(""))
		{
			err_msg = err_msg + "Please enter valid Name\n";
		}

		// if(!(LetterPattern.matcher(txt_gender.getText()).matches()) || txt_gender.getText().equals(""))
		// {
		// 	err_msg = err_msg + "Please enter valid Gender\n";

		// }

		if(!(LetterPattern.matcher(txt_place.getText()).matches()) || txt_place.getText().equals(""))
		{
			err_msg = err_msg + "Please enter valid Place\n";
		}

		try{
			int myknown_err = Integer.parseInt(txt_age.getText().trim());
		}
		catch(NumberFormatException ex)
		{
			err_msg = err_msg + "Please enter valid Age\n";	
		}

		if(!(NumberPattern.matcher(txt_mno.getText()).matches()) || txt_mno.getText().equals(""))
		{
			err_msg = err_msg + "Please enter valid Mobile Number\n";	
		}

		

		if(err_msg.length() == 0)
		{
			Vector <String> temp = new Vector <String>();

				temp.add(txt_name.getText().trim().toUpperCase());
				temp.add(txt_mno.getText().trim().toUpperCase());
				temp.add(txt_age.getText().trim().toUpperCase());
				temp.add(gender);
				temp.add(txt_place.getText().trim().toUpperCase());

			
			rows.add(temp);

			dtm.setDataVector(rows, col);


			txt_name.setText("");
			txt_mno.setText("");
			txt_age.setText("");
			// txt_gender.setText("");
			r1.setSelected(false);
			r2.setSelected(false);
			txt_place.setText("");			
		}
		else
		JOptionPane.showMessageDialog(this,err_msg);	

	err_msg = "";

			break;


		case "CLEAR":
			txt_name.setText("");
			txt_mno.setText("");
			txt_age.setText("");
			r1.setSelected(false);
			r2.setSelected(false);
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

>>>>>>> 215e852dbe032c46ae537157024451a9067fae4e
}