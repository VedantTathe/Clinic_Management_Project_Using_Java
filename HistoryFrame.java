// import javax.swing.*;
// import java.awt.*;
// import java.awt.event.*;
// import java.util.*;
// import java.sql.*;
// import java.text.*;
// import javax.swing.event.*;
// import javax.swing.table.*;
// import java.io.*;

// class HistoryFrame extends JFrame implements ActionListener, ListSelectionListener
// {
// 	private JTabbedPane tp;
// 	private JPanel home_panel, main_search_panel, main_menu_panel, appointment_panel;
// 	private JButton b;
// 	private JSplitPane jsp;
// 	private JPanel search_panel, menu_panel, menu_p1, menu_p2, menu_p3;
// 	private JTextField txt_search_name, txt_search_name_history, txt_name, txt_mno, txt_reg, txt_date;
// 	private JList list_name, list_name_history;
// 	private Vector <String> name_data, table_data, name_data_history;
// 	private JLabel lbl;
// 	private DefaultTableModel dtm;
// 	private JTable tbl;
// 	private JScrollPane sp;
// 	private Connection con;
// 	private PreparedStatement ps;
// 	private java.sql.Date sql_date;
// 	private String str_date;
// 	private Vector <Vector> rows_data;
// 	private Vector <String> col_data;
// 	private JLabel lbl_upload;
// 	private JPanel main_panel,right_panel,left_panel,left_p1,left_p2,right_p1,right_p2,right_p3,right_p4,right_p5;
// 	private JTextField txt_search,txt_browse,txt_history, txt_pname, txt_pmno;
// 	private JTextArea txt_area_1,txt_area_2;
// 	private JList search_list;
// 	private JFileChooser jfc;

// 	public HistoryFrame()
// 	{

// 		main_panel = new JPanel();
// 		main_panel.setLayout(null);
// 		this.add(main_panel, BorderLayout.CENTER);


// 		right_panel = new JPanel();
// 		right_panel.setLayout(null);
// 		main_panel.add(right_panel);

// 		right_panel.setBounds(350,10,900,630);
// 		// right_panel.setBackground(Color.PINK);

// 		lbl = new JLabel("Name: ");
// 		txt_pname = new JTextField();
// 		txt_pmno = new JTextField();
// 		lbl.setBounds(250, 10, 50, 35);
// 		txt_pname.setBounds(300, 15, 150, 25);
// 		txt_pmno.setBounds(350, 45, 50, 35);
// 		right_panel.add(lbl);
// 		right_panel.add(txt_pname);
// 		right_panel.add(txt_pmno);
// 		txt_pmno.setVisible(false);

// 		txt_pname.setEditable(false);
// 		txt_pname.setText(MyClinic.PName+" "+MyClinic.PMno);
// 		txt_pmno.setText(MyClinic.PMno);


// 		right_p1 = new JPanel();
// 		right_panel.add(right_p1);
// 		right_p1.setLayout(null);
// 		right_p1.setBounds(10,10,800,650);

// 		right_p2 = new JPanel();
// 		right_p1.add(right_p2);
// 		right_p2.setLayout(null);
// 		right_p2.setBounds(30,10,750,150);
// 			// p.setBackground(Color.RED);

// 		lbl = new JLabel("Upload previous history file");
// 		right_p2.add(lbl);
// 		lbl.setBounds(10,10,200,35);

// 		txt_history = new JTextField();
// 		right_p2.add(txt_history);
// 		txt_history.setBounds(10,45,550,25);

// 		b = new JButton("BROWSE");
// 		right_p2.add(b);
// 		b.setBounds(10,75,150,25);
// 		b.addActionListener(this);
		
// 		b = new JButton("UPLOAD");
// 		right_p2.add(b);
// 		b.setBounds(270,75,150,25);
// 		b.addActionListener(this);

// 		b = new JButton("OPEN_FILE");
// 		right_p2.add(b);
// 		b.setBounds(10,105,150,25);
// 		b.addActionListener(this);
		
// 		b = new JButton("CLEAR");
// 		right_p2.add(b);
// 		b.setBounds(270,105,150,25);
// 		b.addActionListener(this);	

// 		b = new JButton("ADD");
// 		right_p2.add(b);
// 		b.setBounds(370,105,150,25);
// 		b.addActionListener(this);

// 		// p1.setBackground(Color.RED);		

// 		right_p3 = new JPanel();
// 		right_p1.add(right_p3);
// 		right_p3.setLayout(null);
// 		right_p3.setBounds(10,160,830,500);
// 		// p2.setBackground(Color.PINK);	

// 		right_p4 = new JPanel();
// 		right_p3.add(right_p4);
// 		right_p4.setLayout(null);

// 		right_p4.setBounds(10,5,380,480);
// 		// p3.setBackground(Color.red);

// 		lbl = new JLabel("Write here new history");
// 		right_p4.add(lbl);
// 		lbl.setBounds(120,0,150,35);

// 		txt_area_1 = new JTextArea();
// 		txt_area_1.setFont(new Font("calibri", Font.PLAIN, 16));
// 		sp = new JScrollPane(txt_area_1);
// 		right_p4.add(sp);
// 		sp.setBounds(20,30,330,400);

// 		right_p5 = new JPanel();
// 		right_p3.add(right_p5);
// 		right_p5.setLayout(null);

// 		right_p5.setBounds(420,5,380,480);
// 		// right_p4.setBackground(Color.PINK);

// 		lbl = new JLabel("Previous history");
// 		right_p5.add(lbl);
// 		lbl.setBounds(130,0,150,35);

// 		txt_area_2 = new JTextArea();
// 		txt_area_2.setFont(new Font("calibri", Font.PLAIN, 16));
// 		sp = new JScrollPane(txt_area_2);
// 		right_p5.add(sp);
// 		sp.setBounds(20,30,330,400);
// 		// right_p5.setBackground(Color.PINK);

// 		getFileData();

// 		this.setVisible(true);
// 		this.setSize(800, 790);
// 		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
// 	}

// 	public void getFileData()
// 	{
// 		try
// 		{
// 			File f = new File("D:/MyDatabase/"+txt_pmno.getText()+".txt");

// 			FileInputStream fin = new FileInputStream(f);

// 			String mytext = "";
// 			while(true)
// 			{
// 				int c = fin.read();
// 				if(c == -1)
// 					break;

// 				mytext = mytext + (char)c;
// 			}

// 			txt_area_2.setText(mytext);

// 			// JOptionPane.showMessageDialog(this, "path "+"D:/MyDatabase/"+txt_pmno.getText()+".txt");
// 			// JOptionPane.showMessageDialog(this, mytext);


// 			fin.close();
// 		}catch(Exception ex)
// 		{
// 			// JOptionPane.showMessageDialog(this, ex);
// 		}
// 	}

// 	@Override
// 	public void actionPerformed(ActionEvent e)
// 	{
// 		String text = e.getActionCommand();

// 		switch(text)
// 		{
// 			case "ADD":
				
// 				try
// 				{
// 				File f = new File("D:/MyDatabase/"+txt_pmno.getText()+".txt");

// 				FileOutputStream fout = new FileOutputStream(f);

// 				java.util.Date mydate  = new java.util.Date();
// 				String x = "\n-----------------------------\n"+mydate+"\n-----------------------------\n";
			

// 				String mytext = txt_area_2.getText();
// 				mytext = mytext + x;
// 				mytext = mytext + txt_area_1.getText();

// 				java.util.Date date = new java.util.Date();

// 				byte arr[] = mytext.getBytes();
				
// 				fout.write(arr);

// 				JOptionPane.showMessageDialog(this, "path "+"D:/MyDatabase/"+txt_pmno.getText()+".txt");
// 				JOptionPane.showMessageDialog(this, mytext);


// 				fout.close();
// 			}catch(Exception ex)
// 			{
// 				JOptionPane.showMessageDialog(this, ex);
// 			}

// 			getFileData();
// 				break;
// 		}
// 	}


// 	@Override
// 	public void valueChanged(ListSelectionEvent e)
// 	{
		
// 	}
// }


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.io.*;

class HistoryFrame extends JFrame implements ActionListener, ListSelectionListener
{
	private JTabbedPane tp;
	private JPanel home_panel, main_search_panel, main_menu_panel, appointment_panel;
	private JButton b;
	private JSplitPane jsp;
	private JPanel search_panel, menu_panel, menu_p1, menu_p2, menu_p3;
	private JTextField txt_search_name, txt_search_name_history, txt_name, txt_mno, txt_reg, txt_date;
	private JList list_name, list_name_history;
	private Vector <String> name_data, table_data, name_data_history;
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
	private JLabel lbl_upload;
	private JPanel main_panel,right_panel,left_panel,left_p1,left_p2,right_p1,right_p2,right_p3,right_p4,right_p5;
	private JTextField txt_search,txt_browse,txt_history, txt_pname, txt_pmno;
	private JTextArea txt_area_1,txt_area_2;
	private JList search_list;
	private JFileChooser jfc;

	public HistoryFrame()
	{

		main_panel = new JPanel();
		main_panel.setLayout(null);
		this.add(main_panel, BorderLayout.CENTER);


		right_panel = new JPanel();
		right_panel.setLayout(null);
		main_panel.add(right_panel);

		right_panel.setBounds(50,10,900,630);
		// right_panel.setBackground(Color.PINK);

		lbl = new JLabel("Name: ");
		txt_pname = new JTextField();
		txt_pmno = new JTextField();
		lbl.setBounds(100, 10, 50, 35);
		txt_pname.setBounds(150, 15, 150, 25);
		txt_pmno.setBounds(350, 45, 50, 35);
		right_panel.add(lbl);
		right_panel.add(txt_pname);
		right_panel.add(txt_pmno);
		txt_pmno.setVisible(false);

		txt_pname.setEditable(false);
		txt_pname.setText(MyClinic.PName+" "+MyClinic.PMno);
		txt_pmno.setText(MyClinic.PMno);


		right_p1 = new JPanel();
		right_panel.add(right_p1);
		right_p1.setLayout(null);
		right_p1.setBounds(10,10,850,800);
		// right_p1.setBackground(Color.PINK);

		right_p2 = new JPanel();
		right_p1.add(right_p2);
		right_p2.setLayout(null);
		right_p2.setBounds(30,10,750,30);
			// right_p2.setBackground(Color.RED);

		// lbl = new JLabel("Upload previous history file");
		// right_p2.add(lbl);
		// lbl.setBounds(10,10,200,35);

		// txt_history = new JTextField();
		// right_p2.add(txt_history);
		// txt_history.setBounds(10,45,550,25);

		// b = new JButton("BROWSE");
		// right_p2.add(b);
		// b.setBounds(10,75,150,25);
		// b.addActionListener(this);
		
		// b = new JButton("UPLOAD");
		// right_p2.add(b);
		// b.setBounds(270,75,150,25);
		// b.addActionListener(this);

		// b = new JButton("OPEN_FILE");
		// right_p2.add(b);
		// b.setBounds(10,105,150,25);
		// b.addActionListener(this);
		
		// b = new JButton("CLEAR");
		// right_p3.add(b);
		// b.setBounds(20,420,150,25);
		// b.addActionListener(this);	

		// b = new JButton("ADD");
		// right_p3.add(b);
		// b.setBounds(20,420,150,25);
		// b.addActionListener(this);

		// right_p2.setBackground(Color.RED);		

		right_p3 = new JPanel();
		right_p1.add(right_p3);
		right_p3.setLayout(null);
		right_p3.setBounds(10,50,830,500);
		// p2.setBackground(Color.PINK);	

		right_p4 = new JPanel();
		right_p3.add(right_p4);
		right_p4.setLayout(null);

		right_p4.setBounds(10,5,380,480);
		// right_p3.setBackground(Color.red);

		lbl = new JLabel("Write here new history");
		right_p4.add(lbl);
		lbl.setBounds(120,0,150,35);

		txt_area_1 = new JTextArea();
		txt_area_1.setFont(new Font("calibri", Font.PLAIN, 16));
		sp = new JScrollPane(txt_area_1);
		right_p4.add(sp);
		sp.setBounds(20,30,330,400);

		b = new JButton("CLEAR");
		right_p4.add(b);
		b.setBounds(200,440,150,25);
		b.addActionListener(this);	

		b = new JButton("ADD");
		right_p4.add(b);
		b.addActionListener(this);
		b.setBounds(20,440,150,25);

		right_p5 = new JPanel();
		right_p3.add(right_p5);
		right_p5.setLayout(null);

		right_p5.setBounds(420,5,380,480);
		// right_p4.setBackground(Color.PINK);

		lbl = new JLabel("Previous history");
		right_p5.add(lbl);
		lbl.setBounds(130,0,150,35);

		txt_area_2 = new JTextArea();
		// txt_area_2.setEditable(false);
		txt_area_2.setFont(new Font("calibri", Font.PLAIN, 16));
		sp = new JScrollPane(txt_area_2);
		right_p5.add(sp);
		sp.setBounds(20,30,330,400);
		// right_p5.setBackground(Color.PINK);

		getFileData();

		this.setVisible(true);
		this.setSize(1000, 600);
		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
	}

	public void getFileData()
	{
		try
		{
			// File f = new File("D:/MyDatabase/"+txt_pmno.getText()+".txt");
			File f = new File("D:/MyDatabase/"+txt_pmno.getText()+".txt");
			FileInputStream fin = new FileInputStream(f);

			String mytext = "";
			while(true)
			{
				int c = fin.read();
				if(c == -1)
					break;

				mytext = mytext + (char)c;
			}

			txt_area_2.setText(mytext);

			// JOptionPane.showMessageDialog(this, "path "+"D:/MyDatabase/"+txt_pmno.getText()+".txt");
			// JOptionPane.showMessageDialog(this, mytext);


			fin.close();
		}catch(Exception ex)
		{
			JOptionPane.showMessageDialog(this, ex);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		
		String text = e.getActionCommand();

		switch(text)
		{
			case "ADD":

				if(txt_area_1.getText().trim().length() == 0)
					break;	
				
				try
				{
				File f = new File("D:/MyDatabase/"+txt_pmno.getText()+".txt");

				FileOutputStream fout = new FileOutputStream(f);

				java.util.Date mydate  = new java.util.Date();
				String x = "\n-----------------------------\n"+mydate+"\n-----------------------------\n";
			

				String mytext = txt_area_2.getText();
				mytext = mytext + x;
				mytext = mytext + txt_area_1.getText();

				java.util.Date date = new java.util.Date();

				byte arr[] = mytext.getBytes();
				
				fout.write(arr);

				// JOptionPane.showMessageDialog(this, "path "+"D:/MyDatabase/"+txt_pmno.getText()+".txt");
				// JOptionPane.showMessageDialog(this, mytext);


				fout.close();
			}catch(Exception ex)
			{
				JOptionPane.showMessageDialog(this, ex);
			}

			getFileData();
				break;

			case "CLEAR":
				txt_area_1.setText("");
				break;
		}
	}


	@Override
	public void valueChanged(ListSelectionEvent e)
	{
		
	}
}