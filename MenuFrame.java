<<<<<<< HEAD
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.util.regex.*;

class MenuFrame extends JFrame implements ActionListener, ListSelectionListener
{
	private JTabbedPane tp;
	private JPanel home_panel, main_search_panel, main_menu_panel, appointment_panel;
	private JButton b;
	private JSplitPane jsp;
	private JPanel search_panel, menu_panel, menu_p1, menu_p2, menu_p3;
	private JTextField txt_search_name, txt_name, txt_mno, txt_reg, txt_date;
	private JList list_name;
	private Vector <String> name_data, table_data;
	private JLabel lbl;
	private DefaultTableModel dtm;
	private JTable tbl;
	private JScrollPane sp;
	private Connection con;
	private PreparedStatement ps;
	private java.sql.Date sql_date;
	private String str_date;
	private Vector <Vector> rows_data;
	private Vector <String> col_data;
	private JTextField txt_h_name, txt_h_mno;
	private int sel_ind = -1;

	public MenuFrame()
	{

		//menu_panel

		main_menu_panel = new JPanel();
		// main_menu_panel.setBackground(Color.YELLOW);
		main_menu_panel.setLayout(null);

		//Name Search Panel

		search_panel = new JPanel();
		search_panel.setLayout(null);

		lbl = new JLabel("Search: ");
		search_panel.add(lbl);
		lbl.setBounds(10, 10, 350, 35);


		txt_search_name = new JTextField();
		search_panel.add(txt_search_name);
		txt_search_name.setBounds(10, 45, 350, 35);
		txt_search_name.addActionListener(this);
		

		name_data = new <String> Vector();
		list_name = new JList(name_data);
		main_menu_panel.add(list_name);
		list_name.setBounds(15, 100, 350, 500);
		list_name.addListSelectionListener(this);

		search_panel.setBounds(10, 10, 350, 600);


		main_menu_panel.add(search_panel);
// search_panel.setBackground(Color.RED);
		//Menu Panel code

		menu_panel = new JPanel();
		menu_panel.setLayout(null);
		// menu_panel.setBackground(Color.RED);
		menu_panel.setBounds(450, 10, 600, 600);


		lbl = new JLabel("WELCOME");
		menu_panel.add(lbl, JLabel.CENTER);
		lbl.setBounds(250, 10, 600, 35);


		//labels and textfeilds

		menu_p1 = new JPanel();
		menu_panel.add(menu_p1);
		menu_p1.setBounds(100, 50, 400, 100);

		menu_p1.setLayout(new GridLayout(4, 2, 5, 2));
		menu_p1.add(new JLabel("Date: "));
		menu_p1.add(new JLabel("Name: "));

		txt_date = new JTextField();
		menu_p1.add(txt_date);
		txt_date.setEditable(false);


		java.util.Date date = new java.util.Date();
		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
		str_date = formater.format(date);
		txt_date.setText(str_date);

		txt_name = new JTextField();
		menu_p1.add(txt_name);

		menu_p1.add(new JLabel("Mobile No: "));
		menu_p1.add(new JLabel("Register No: "));

		txt_mno = new JTextField();
		menu_p1.add(txt_mno);
		
		txt_reg = new JTextField();
		menu_p1.add(txt_reg);

		//buttons

		menu_p2 = new JPanel();
		menu_panel.add(menu_p2);
		menu_p2.setBounds(120, 170, 350, 70);

		menu_p2.setLayout(new GridLayout(2, 2, 10, 10));

		String myarr[] = {"ADD", "DELETE", "UPDATE", "CLEAR"};

		for(String item: myarr)
		{
			b= new JButton(item);
			b.addActionListener(this);
			menu_p2.add(b);
		}

		//history name

		lbl = new JLabel("Name");
		txt_h_name = new JTextField();
		txt_h_mno = new JTextField();
		b = new JButton("VIEW HISTORY");
		b.addActionListener(this);
		b.setEnabled(false);

		lbl.setBounds(100, 260, 100, 25);
		txt_h_name.setBounds(180, 260, 170, 25);
		txt_h_mno.setBounds(10080, 260, 170, 25);
		b.setBounds(380, 260, 150, 25);

		menu_panel.add(lbl);
		menu_panel.add(txt_h_name);
		menu_panel.add(txt_h_mno);
		menu_panel.add(b);

		txt_h_name.setEditable(false);
		txt_h_mno.setEditable(false);


		//table

		menu_p3 = new JPanel();
		menu_panel.add(menu_p3);
		// menu_p3.setBackground();
		menu_p3.setBounds(15, 300, 750, 250);
		menu_p3.setLayout(null);

		col_data = new Vector <String> ();
		rows_data = new Vector <Vector> ();
		col_data.add("Date");
		col_data.add("Name");
		col_data.add("Mobile No");
		col_data.add("Register No");


		dtm = new DefaultTableModel(rows_data, col_data);
		tbl = new JTable(dtm);
		tbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		ListSelectionModel sm = tbl.getSelectionModel();
		sm.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent e)
			{
				int x = tbl.getSelectedRow();
				String y = (String)tbl.getValueAt(x, 1);
				String z = (String)tbl.getValueAt(x, 2);

				txt_h_name.setText(y);
				txt_h_mno.setText(z);

				b.setEnabled(true);

			}
		});

		sp = new JScrollPane(tbl);
		menu_p3.add(sp);
		sp.setBounds(30, 10, 500, 220);


		main_menu_panel.add(menu_panel);


		this.add(main_menu_panel);



		getData();
		getTableData();

		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

	}


	public void connect() throws Exception
	{
		DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/MyClinic", "root", "super");
		// con = DriverManager.getConnection("jdbc:mysql://localhost:3306/MyClinic", "root", "Tanmay@232003");

	}

	public void getData()
	{
		name_data.clear();
		try{
			connect();
			String sql = "select * from patients";
			ps = con.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			while(rs.next())
			{
				String my_text = "";
				my_text = my_text + rs.getString("preg")+" ";
				my_text = my_text + rs.getString("pname")+" ";
				my_text = my_text + rs.getString("pmno");

				name_data.addElement(my_text);

			}

			list_name.setListData(name_data);
			rs.close();
			ps.close();

		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(this,"Get data:"+ex);
		}
	}


	public void getTableData()
	{
		rows_data.clear();
		// 
		try{
			connect();
			String sql = "select * from patients";
			ps = con.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			while(rs.next())
			{
				Vector <String> temp = new Vector<String>();
				temp.add(rs.getString("date"));
				temp.add(rs.getString("pname"));
				temp.add(rs.getString("pmno"));
				temp.add(rs.getString("preg"));

				// System.out.println(temp);

				rows_data.addElement(temp);

				// System.out.println(rows_data);

			}
// System.out.println(rows_data);
			dtm.setDataVector(rows_data, col_data);

			
			rs.close();
			ps.close();

		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(this,"Get data:"+ex);
		}
	}




	@Override
	public void actionPerformed(ActionEvent e)
	{
		String text = e.getActionCommand();
// String txt_name = "", txt_mno = "";
// 		String txt_reg = 0;
// 		String err_text = "";



		switch(text)
		{
			case "ADD": 
				if(true){

					Pattern LetterPattern = Pattern.compile("[a-z A-Z]+$");
					Pattern NumberPattern = Pattern.compile("[7-9][0-9]{9}");

					String err_text = "";
					
					String pname = txt_name.getText().trim().toUpperCase();
					String pmno = txt_mno.getText().trim();
					String preg = txt_reg.getText().trim().toUpperCase();
					

					if(!(LetterPattern.matcher(txt_name.getText()).matches()) || txt_name.getText().equals(""))
					{
						err_text = err_text + "Please enter valid Name\n";
					}
					// if(txt_mno.getText().equals(""))
					// {
					// 	err_text = err_text+"Enter valid Mobile no\n";
					// }

					if(!(NumberPattern.matcher(txt_mno.getText()).matches()) || txt_mno.getText().equals(""))
					{
						err_text = err_text + "Please enter valid Mobile Number\n";	
					}
					if(preg.length()==0)
					{
						err_text = err_text+"Register NO. Not Provided";
					}

					if(err_text.equals(""))
					{ 

						try
						{
							connect();
							String sql = "insert into patients(date, pname, pmno, preg) values(?, ?, ?, ?)";
							ps = con.prepareStatement(sql);
						
							ps.setString(1, str_date);
							ps.setString(2, pname);
							ps.setString(3, pmno);
							ps.setString(4, preg);

							int n = ps.executeUpdate();

							ps.close();
							con.close();
		
							if(n==1)
							{
								getData();
								getTableData();
								JOptionPane.showMessageDialog(this, "Patient Added Successfully..!");

							}
							else
							{
								JOptionPane.showMessageDialog(this, "Patient Not Added..!");
							}
	
	
						}
						catch(Exception ex)
						{
							JOptionPane.showMessageDialog(this, "ADD SECTION: "+ex);
						}
					}
					else{
						JOptionPane.showMessageDialog(this, err_text);
						break;
					}
				txt_name.setText("");
				txt_mno.setText("");
				txt_reg.setText("");
				txt_search_name.setText("");
				}

				break;

			case "DELETE": 
				
			String mnumber = txt_mno.getText().trim();
			if(mnumber.length() == 0)
			{
				JOptionPane.showMessageDialog(this, "Please Select First...!");
			}
			else
			{

			try
			{
				connect();
				String sql = "delete from patients where pmno = ?";
				ps = con.prepareStatement(sql);
				ps.setString(1, mnumber);

				int n = ps.executeUpdate();

				ps.close();
				con.close();
				getData();
				getTableData();
				if(n==1)
				{
					JOptionPane.showMessageDialog(this, "Deleted Successfully..!");
				}
				else
				{
					JOptionPane.showMessageDialog(this, "Cannot Delete..!");
				}


			}
			catch(Exception ex)
			{
				JOptionPane.showMessageDialog(this, "DELETE SECTION: "+ex);
			}
			}txt_name.setText("");
				txt_mno.setText("");
				txt_reg.setText("");
				txt_search_name.setText("");


				break;

			case "UPDATE": 

				String pname = txt_name.getText().trim().toUpperCase();
				String pmno = txt_mno.getText().trim();
				String preg = txt_reg.getText().trim().toUpperCase();
				String err_text = "";
				Pattern LetterPattern = Pattern.compile("[a-z A-Z]+$");
					Pattern NumberPattern = Pattern.compile("[7-9][0-9]{9}");


				if(list_name.getSelectedIndex() == -1)
				{
					JOptionPane.showMessageDialog(this, "Please Select first from list..!");

					break;
				}
	sel_ind= list_name.getSelectedIndex();		
					if(!(LetterPattern.matcher(txt_name.getText()).matches()) || txt_name.getText().equals(""))
					{
						err_text = err_text + "Please enter valid Name\n";
					}
					// if(txt_mno.getText().equals(""))
					// {
					// 	err_text = err_text+"Enter valid Mobile no\n";
					// }

					if(!(NumberPattern.matcher(txt_mno.getText()).matches()) || txt_mno.getText().equals(""))
					{
						err_text = err_text + "Please enter valid Mobile Number\n";	
					}
				if(preg.length()==0)
				{
					err_text = err_text+"Register No. Not Provided\n";
				}
				


				if(!err_text.equals(""))
				{
					JOptionPane.showMessageDialog(this, err_text);
					list_name.setSelectedIndex(sel_ind);
					break;

				}
				else
				{

					try
					{
							connect();
							String sql = "update patients set pname = ?,pmno = ? ,preg = ? where pmno = ?";
							
	
							String s_value = list_name.getSelectedValue().toString();
	
							int i = s_value.lastIndexOf(" ");
	
							String old_mno = s_value.substring(i+1, s_value.length());
	

							ps = con.prepareStatement(sql);
							ps.setString(1,pname);
							ps.setString(2,pmno);
							ps.setString(3,preg);

							ps.setString(4,old_mno);


							// System.out.println(pname);
							// System.out.println(pmno);
							// System.out.println(preg);
							// System.out.println(old_mno);
							int n = ps.executeUpdate();
							ps.close();
							con.close();
	
							if(n==1)
							{
								getData();
								getTableData();
								JOptionPane.showMessageDialog(this, "Updated Successfully..!");
							}
							else{
								JOptionPane.showMessageDialog(this, "Cannot Update..!");
							}
							
	
					}
					catch(Exception ex)
					{
						JOptionPane.showMessageDialog(this, "UPDATE SECTION: "+ex);
					}
				}
			
txt_name.setText("");
				txt_mno.setText("");
				txt_reg.setText("");
				txt_search_name.setText("");

				break;
			case "CLEAR": 
				txt_name.setText("");
				txt_mno.setText("");
				txt_reg.setText("");
				txt_search_name.setText("");

				break;

			case "VIEW HISTORY":
				JOptionPane.showMessageDialog(this, "Opening History of "+txt_h_name.getText());
				this.setVisible(false);
				MyClinic.PName = txt_h_name.getText();
				MyClinic.PMno = txt_h_mno.getText();
				new HistoryFrame();

				break;
		}


		//searh code

		// if(list_name.getSelectedIndex() != -1)
		// {
				String value = txt_search_name.getText().trim().toUpperCase();
				try{
					connect();
					String sql = "select * from patients where pname like '%"+value+"%' or pmno like '%"+value+"%' or preg like '%"+value+"%'";
					ps = con.prepareStatement(sql);
					ResultSet rs = ps.executeQuery();

					name_data.clear();

					rows_data.clear();	
			
					while(rs.next())
					{

						//list
						String my_text = "";
						my_text = my_text + rs.getString("preg")+" ";
						my_text = my_text + rs.getString("pname")+" ";
						my_text = my_text + rs.getString("pmno");
		
						name_data.addElement(my_text);

						//table
						Vector <String> temp = new Vector<String>();
						temp.add(rs.getString("date"));
						temp.add(rs.getString("pname"));
						temp.add(rs.getString("pmno"));
						temp.add(rs.getString("preg"));

						// System.out.println(temp);

						rows_data.addElement(temp);


					}

					list_name.setListData(name_data);
					dtm.setDataVector(rows_data, col_data);
					
					ps.close();
					con.close();



				}
				catch(Exception ex)
				{
				// JOptionPane.showMessageDialog(this, "SEARCH CODE: "+ex);
				}

			// }


	}

	@Override
	public void valueChanged(ListSelectionEvent e)
	{
	int index = list_name.getSelectedIndex();

	if(index != -1)
	{
		try
		{
			connect();
			String sql = "select * from patients where pmno = ?";
			ps = con.prepareStatement(sql);

			String s_value = list_name.getSelectedValue().toString();
			int i = s_value.lastIndexOf(" ");
			String old_mno = s_value.substring(i+1, s_value.length());

			ps.setString(1, old_mno);

			ResultSet rs = ps.executeQuery();

			if(rs.next())
			{
				txt_date.setText(rs.getString("date"));
				txt_name.setText(rs.getString("pname"));
				txt_mno.setText(rs.getString("pmno")); 
				txt_reg.setText(rs.getString("preg"));

			}
			rs.close();
			ps.close();
			con.close();



		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(this, "VALUE CHANGED: "+ex);
		}

	}}

=======
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.util.regex.*;

class MenuFrame extends JFrame implements ActionListener, ListSelectionListener
{
	private JTabbedPane tp;
	private JPanel home_panel, main_search_panel, main_menu_panel, appointment_panel;
	private JButton b;
	private JSplitPane jsp;
	private JPanel search_panel, menu_panel, menu_p1, menu_p2, menu_p3;
	private JTextField txt_search_name, txt_name, txt_mno, txt_reg, txt_date;
	private JList list_name;
	private Vector <String> name_data, table_data;
	private JLabel lbl;
	private DefaultTableModel dtm;
	private JTable tbl;
	private JScrollPane sp;
	private Connection con;
	private PreparedStatement ps;
	private java.sql.Date sql_date;
	private String str_date;
	private Vector <Vector> rows_data;
	private Vector <String> col_data;
	private JTextField txt_h_name, txt_h_mno;
	private int sel_ind = -1;

	public MenuFrame()
	{


		

		//menu_panel

		main_menu_panel = new JPanel();
		// main_menu_panel.setBackground(Color.YELLOW);
		main_menu_panel.setLayout(null);

		//Name Search Panel

		search_panel = new JPanel();
		search_panel.setLayout(null);

		lbl = new JLabel("Search: ");
		search_panel.add(lbl);
		lbl.setBounds(10, 10, 350, 35);


		txt_search_name = new JTextField();
		search_panel.add(txt_search_name);
		txt_search_name.setBounds(10, 45, 350, 35);
		txt_search_name.addActionListener(this);
		

		name_data = new <String> Vector();
		list_name = new JList(name_data);
		main_menu_panel.add(list_name);
		list_name.setBounds(15, 100, 350, 500);
		list_name.addListSelectionListener(this);

		search_panel.setBounds(10, 10, 350, 600);


		main_menu_panel.add(search_panel);
// search_panel.setBackground(Color.RED);
		//Menu Panel code

		menu_panel = new JPanel();
		menu_panel.setLayout(null);
		// menu_panel.setBackground(Color.RED);
		menu_panel.setBounds(450, 10, 600, 600);


		lbl = new JLabel("WELCOME");
		menu_panel.add(lbl, JLabel.CENTER);
		lbl.setBounds(250, 10, 600, 35);


		//labels and textfeilds

		menu_p1 = new JPanel();
		menu_panel.add(menu_p1);
		menu_p1.setBounds(100, 50, 400, 100);

		menu_p1.setLayout(new GridLayout(4, 2, 5, 2));
		menu_p1.add(new JLabel("Date: "));
		menu_p1.add(new JLabel("Name: "));

		txt_date = new JTextField();
		menu_p1.add(txt_date);
		txt_date.setEditable(false);


		java.util.Date date = new java.util.Date();
		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
		str_date = formater.format(date);
		txt_date.setText(str_date);

		txt_name = new JTextField();
		menu_p1.add(txt_name);

		menu_p1.add(new JLabel("Mobile No: "));
		menu_p1.add(new JLabel("Register No: "));

		txt_mno = new JTextField();
		menu_p1.add(txt_mno);
		
		txt_reg = new JTextField();
		menu_p1.add(txt_reg);

		//buttons

		menu_p2 = new JPanel();
		menu_panel.add(menu_p2);
		menu_p2.setBounds(120, 170, 350, 70);

		menu_p2.setLayout(new GridLayout(2, 2, 10, 10));

		String myarr[] = {"ADD", "DELETE", "UPDATE", "CLEAR"};

		for(String item: myarr)
		{
			b= new JButton(item);
			b.addActionListener(this);
			menu_p2.add(b);
		}

		//history name

		lbl = new JLabel("Name");
		txt_h_name = new JTextField();
		txt_h_mno = new JTextField();
		b = new JButton("VIEW HISTORY");
		b.addActionListener(this);
		b.setEnabled(false);

		lbl.setBounds(100, 260, 100, 25);
		txt_h_name.setBounds(180, 260, 170, 25);
		txt_h_mno.setBounds(10080, 260, 170, 25);
		b.setBounds(380, 260, 150, 25);

		menu_panel.add(lbl);
		menu_panel.add(txt_h_name);
		menu_panel.add(txt_h_mno);
		menu_panel.add(b);

		txt_h_name.setEditable(false);
		txt_h_mno.setEditable(false);


		//table

		menu_p3 = new JPanel();
		menu_panel.add(menu_p3);
		// menu_p3.setBackground();
		menu_p3.setBounds(15, 300, 750, 250);
		menu_p3.setLayout(null);

		col_data = new Vector <String> ();
		rows_data = new Vector <Vector> ();
		col_data.add("Date");
		col_data.add("Name");
		col_data.add("Mobile No");
		col_data.add("Register No");


		dtm = new DefaultTableModel(rows_data, col_data);
		tbl = new JTable(dtm);
		tbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		ListSelectionModel sm = tbl.getSelectionModel();
		sm.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent e)
			{
				int x = tbl.getSelectedRow();
				String y = (String)tbl.getValueAt(x, 1);
				String z = (String)tbl.getValueAt(x, 2);

				txt_h_name.setText(y);
				txt_h_mno.setText(z);

				b.setEnabled(true);

			}
		});

		sp = new JScrollPane(tbl);
		menu_p3.add(sp);
		sp.setBounds(30, 10, 500, 220);


		main_menu_panel.add(menu_panel);


		this.add(main_menu_panel);



		getData();
		getTableData();

		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

	}


	public void connect() throws Exception
	{
		DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/MyClinic", "root", "super");
		// con = DriverManager.getConnection("jdbc:mysql://localhost:3306/MyClinic", "root", "Tanmay@232003");

	}

	public void getData()
	{
		name_data.clear();
		try{
			connect();
			String sql = "select * from patients";
			ps = con.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			while(rs.next())
			{
				String my_text = "";
				my_text = my_text + rs.getString("preg")+" ";
				my_text = my_text + rs.getString("pname")+" ";
				my_text = my_text + rs.getString("pmno");

				name_data.addElement(my_text);

			}

			list_name.setListData(name_data);
			rs.close();
			ps.close();

		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(this,"Get data:"+ex);
		}
	}


	public void getTableData()
	{
		rows_data.clear();
		// 
		try{
			connect();
			String sql = "select * from patients";
			ps = con.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			while(rs.next())
			{
				Vector <String> temp = new Vector<String>();
				temp.add(rs.getString("date"));
				temp.add(rs.getString("pname"));
				temp.add(rs.getString("pmno"));
				temp.add(rs.getString("preg"));

				// System.out.println(temp);

				rows_data.addElement(temp);

				// System.out.println(rows_data);

			}
// System.out.println(rows_data);
			dtm.setDataVector(rows_data, col_data);

			
			rs.close();
			ps.close();

		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(this,"Get data:"+ex);
		}
	}




	@Override
	public void actionPerformed(ActionEvent e)
	{
		String text = e.getActionCommand();
// String txt_name = "", txt_mno = "";
// 		String txt_reg = 0;
// 		String err_text = "";



		switch(text)
		{
			case "ADD": 
				if(true){

					Pattern LetterPattern = Pattern.compile("[a-z A-Z]+$");
					Pattern NumberPattern = Pattern.compile("[7-9][0-9]{9}");

					String err_text = "";
					
					String pname = txt_name.getText().trim().toUpperCase();
					String pmno = txt_mno.getText().trim();
					String preg = txt_reg.getText().trim().toUpperCase();
					

					if(!(LetterPattern.matcher(txt_name.getText()).matches()) || txt_name.getText().equals(""))
					{
						err_text = err_text + "Please enter valid Name\n";
					}
					// if(txt_mno.getText().equals(""))
					// {
					// 	err_text = err_text+"Enter valid Mobile no\n";
					// }

					if(!(NumberPattern.matcher(txt_mno.getText()).matches()) || txt_mno.getText().equals(""))
					{
						err_text = err_text + "Please enter valid Mobile Number\n";	
					}
					if(preg.length()==0)
					{
						err_text = err_text+"Register NO. Not Provided";
					}

					if(err_text.equals(""))
					{ 

						try
						{
							connect();
							String sql = "insert into patients(date, pname, pmno, preg) values(?, ?, ?, ?)";
							ps = con.prepareStatement(sql);
						
							ps.setString(1, str_date);
							ps.setString(2, pname);
							ps.setString(3, pmno);
							ps.setString(4, preg);

							int n = ps.executeUpdate();

							ps.close();
							con.close();
		
							if(n==1)
							{
								getData();
								getTableData();
								JOptionPane.showMessageDialog(this, "Patient Added Successfully..!");

							}
							else
							{
								JOptionPane.showMessageDialog(this, "Patient Not Added..!");
							}
	
	
						}
						catch(Exception ex)
						{
							JOptionPane.showMessageDialog(this, "ADD SECTION: "+ex);
						}
					}
					else{
						JOptionPane.showMessageDialog(this, err_text);
						break;
					}
				txt_name.setText("");
				txt_mno.setText("");
				txt_reg.setText("");
				txt_search_name.setText("");
				}

				break;

			case "DELETE": 
				
			String mnumber = txt_mno.getText().trim();
			if(mnumber.length() == 0)
			{
				JOptionPane.showMessageDialog(this, "Please Select First...!");
			}
			else
			{

			try
			{
				connect();
				String sql = "delete from patients where pmno = ?";
				ps = con.prepareStatement(sql);
				ps.setString(1, mnumber);

				int n = ps.executeUpdate();

				ps.close();
				con.close();
				getData();
				getTableData();
				if(n==1)
				{
					JOptionPane.showMessageDialog(this, "Deleted Successfully..!");
				}
				else
				{
					JOptionPane.showMessageDialog(this, "Cannot Delete..!");
				}


			}
			catch(Exception ex)
			{
				JOptionPane.showMessageDialog(this, "DELETE SECTION: "+ex);
			}
			}txt_name.setText("");
				txt_mno.setText("");
				txt_reg.setText("");
				txt_search_name.setText("");


				break;

			case "UPDATE": 

				String pname = txt_name.getText().trim().toUpperCase();
				String pmno = txt_mno.getText().trim();
				String preg = txt_reg.getText().trim().toUpperCase();
				String err_text = "";
				Pattern LetterPattern = Pattern.compile("[a-z A-Z]+$");
					Pattern NumberPattern = Pattern.compile("[7-9][0-9]{9}");


				if(list_name.getSelectedIndex() == -1)
				{
					JOptionPane.showMessageDialog(this, "Please Select first from list..!");

					break;
				}
	sel_ind= list_name.getSelectedIndex();		
					if(!(LetterPattern.matcher(txt_name.getText()).matches()) || txt_name.getText().equals(""))
					{
						err_text = err_text + "Please enter valid Name\n";
					}
					// if(txt_mno.getText().equals(""))
					// {
					// 	err_text = err_text+"Enter valid Mobile no\n";
					// }

					if(!(NumberPattern.matcher(txt_mno.getText()).matches()) || txt_mno.getText().equals(""))
					{
						err_text = err_text + "Please enter valid Mobile Number\n";	
					}
				if(preg.length()==0)
				{
					err_text = err_text+"Register No. Not Provided\n";
				}
				


				if(!err_text.equals(""))
				{
					JOptionPane.showMessageDialog(this, err_text);
					list_name.setSelectedIndex(sel_ind);
					break;

				}
				else
				{

					try
					{
							connect();
							String sql = "update patients set pname = ?,pmno = ? ,preg = ? where pmno = ?";
							
	
							String s_value = list_name.getSelectedValue().toString();
	
							int i = s_value.lastIndexOf(" ");
	
							String old_mno = s_value.substring(i+1, s_value.length());
	

							ps = con.prepareStatement(sql);
							ps.setString(1,pname);
							ps.setString(2,pmno);
							ps.setString(3,preg);

							ps.setString(4,old_mno);


							// System.out.println(pname);
							// System.out.println(pmno);
							// System.out.println(preg);
							// System.out.println(old_mno);
							int n = ps.executeUpdate();
							ps.close();
							con.close();
	
							if(n==1)
							{
								getData();
								getTableData();
								JOptionPane.showMessageDialog(this, "Updated Successfully..!");
							}
							else{
								JOptionPane.showMessageDialog(this, "Cannot Update..!");
							}
							
	
					}
					catch(Exception ex)
					{
						JOptionPane.showMessageDialog(this, "UPDATE SECTION: "+ex);
					}
				}
			
txt_name.setText("");
				txt_mno.setText("");
				txt_reg.setText("");
				txt_search_name.setText("");

				break;
			case "CLEAR": 
				txt_name.setText("");
				txt_mno.setText("");
				txt_reg.setText("");
				txt_search_name.setText("");

				break;

			case "VIEW HISTORY":
				JOptionPane.showMessageDialog(this, "Opening History of "+txt_h_name.getText());
				this.setVisible(false);
				MyClinic.PName = txt_h_name.getText();
				MyClinic.PMno = txt_h_mno.getText();
				new HistoryFrame();

				break;
		}


		//searh code

		// if(list_name.getSelectedIndex() != -1)
		// {
				String value = txt_search_name.getText().trim().toUpperCase();
				try{
					connect();
					String sql = "select * from patients where pname like '%"+value+"%' or pmno like '%"+value+"%' or preg like '%"+value+"%'";
					ps = con.prepareStatement(sql);
					ResultSet rs = ps.executeQuery();

					name_data.clear();

					rows_data.clear();	
			
					while(rs.next())
					{

						//list
						String my_text = "";
						my_text = my_text + rs.getString("preg")+" ";
						my_text = my_text + rs.getString("pname")+" ";
						my_text = my_text + rs.getString("pmno");
		
						name_data.addElement(my_text);

						//table
						Vector <String> temp = new Vector<String>();
						temp.add(rs.getString("date"));
						temp.add(rs.getString("pname"));
						temp.add(rs.getString("pmno"));
						temp.add(rs.getString("preg"));

						// System.out.println(temp);

						rows_data.addElement(temp);


					}

					list_name.setListData(name_data);
					dtm.setDataVector(rows_data, col_data);
					
					ps.close();
					con.close();



				}
				catch(Exception ex)
				{
				// JOptionPane.showMessageDialog(this, "SEARCH CODE: "+ex);
				}

			// }


	}

	@Override
	public void valueChanged(ListSelectionEvent e)
	{
	int index = list_name.getSelectedIndex();

	if(index != -1)
	{
		try
		{
			connect();
			String sql = "select * from patients where pmno = ?";
			ps = con.prepareStatement(sql);

			String s_value = list_name.getSelectedValue().toString();
			int i = s_value.lastIndexOf(" ");
			String old_mno = s_value.substring(i+1, s_value.length());

			ps.setString(1, old_mno);

			ResultSet rs = ps.executeQuery();

			if(rs.next())
			{
				txt_date.setText(rs.getString("date"));
				txt_name.setText(rs.getString("pname"));
				txt_mno.setText(rs.getString("pmno")); 
				txt_reg.setText(rs.getString("preg"));

			}
			rs.close();
			ps.close();
			con.close();



		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(this, "VALUE CHANGED: "+ex);
		}

	}}

>>>>>>> 215e852dbe032c46ae537157024451a9067fae4e
}