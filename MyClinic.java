import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;
import java.text.*;

class MainFrame extends JFrame implements ActionListener
{ 
	private JMenuBar mb;
	private JMenu m;
	private JMenuItem mi;
	private JButton b;
	private JSplitPane jsp;
	private JPanel search_panel, menu_panel, menu_p1, menu_p2, menu_p3;
	private JTextField txt_search_name, txt_name, txt_mno, txt_reg, txt_date;
	private JList list_name;
	private Vector <String> name_data;
	private JLabel lbl;
	private JTable tbl;
	private JScrollPane sp;
	private Connection con;
	private PreparedStatement ps;
	private java.sql.Date sql_date;
	private String str_date;


	public MainFrame()
	{
		super("Home Page");

		mb = new JMenuBar();
		this.setJMenuBar(mb);

		m = new JMenu("File");
		mb.add(m);
		
		String arr[] = {"Open","Exit"};

		for(int i=0;i<arr.length;i++)
		{
			mi = new JMenuItem(arr[i]);
			m.add(mi);
			m.addActionListener(this);
		}


		//Name Search Panel

		search_panel = new JPanel();
		search_panel.setLayout(null);

		lbl = new JLabel("Search: ");
		search_panel.add(lbl);
		lbl.setBounds(10, 10, 350, 35);


		txt_search_name = new JTextField();
		search_panel.add(txt_search_name);
		txt_search_name.setBounds(10, 45, 350, 35);

		name_data = new <String> Vector();
		list_name = new JList(name_data);
		this.add(list_name);
		list_name.setBounds(15, 100, 350, 500);

		this.add(search_panel);
		search_panel.setBounds(10, 10, 350, 600);

		//Menu Panel code

		menu_panel = new JPanel();
		menu_panel.setLayout(null);
		// menu_panel.setBackground(Color.RED);
		this.add(menu_panel);
		menu_panel.setBounds(450, 10, 600, 600);


		lbl = new JLabel("WELCOME");
		menu_panel.add(lbl, JLabel.CENTER);
		lbl.setBounds(250, 10, 600, 35);


		//labels and textfeilds

		menu_p1 = new JPanel();
		menu_panel.add(menu_p1);
		menu_p1.setBounds(100, 100, 400, 100);

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
		menu_p2.setBounds(120, 220, 350, 70);

		menu_p2.setLayout(new GridLayout(2, 2, 2, 2));

		String myarr[] = {"ADD", "DELETE", "UPDATE", "CLEAR"};

		for(String item: myarr)
		{
			b= new JButton(item);
			b.addActionListener(this);
			menu_p2.add(b);
		}

		//table

		menu_p3 = new JPanel();
		menu_panel.add(menu_p3);
		menu_p3.setBounds(25, 300, 550, 250);

		tbl = new JTable();
		sp = new JScrollPane(tbl);
		menu_p3.add(sp);


		getData();
		this.setLayout(null);
		this.setVisible(true);	
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
					
	}

	public void connect() throws Exception
	{
		DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/MyClinic", "root", "super");

	}

	public void getData()
	{

		
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		String text = e.getActionCommand();

		switch(text)
		{
			case "ADD": 

					String pname = txt_name.getText().trim().toUpperCase();
					String pmno = txt_mno.getText().trim();
					String preg = txt_reg.getText().trim().toUpperCase();
					String err_text = "";

					if(pname.length()==0)
					{
						err_text = err_text+"Name Not Provided";
					}
					if(pmno.length()==0)
					{
						err_text = err_text+"Mobile No. Not Provided";
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
					else
						JOptionPane.showMessageDialog(this, err_text);

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
		}
				break;
			case "UPDATE": 
				break;
			case "CLEAR": 
				break;
		}
	}
}
	


class LoginFrame extends JFrame implements ActionListener 
{
	private JLabel lbl;
	private JTextField txt_username;
	private TextField txt_password;
	private JButton b;

	public LoginFrame()
	{	
		this.setLayout(null);
		this.setTitle("LOGIN TO CLINIC");
		lbl = new JLabel("LOG IN HERE!");
		lbl.setFont(new Font("Calibric",Font.BOLD,18));
		this.add(lbl);

		lbl.setBounds(140,10,150,35);

		lbl = new JLabel("Enter UserName:");
		this.add(lbl);
		lbl.setBounds(50,45,150,35);

		txt_username = new JTextField();
		this.add(txt_username);
		txt_username.setBounds(160,50,200,25);

		lbl = new JLabel("Enter Password:");
		this.add(lbl);
		lbl.setBounds(50,90,150,35);

		txt_password = new TextField();
		this.add(txt_password);
		txt_password.setBounds(160,90,200,25);
		txt_password.setEchoChar('*');

		b = new JButton("LOGIN");
		this.add(b);
		b.addActionListener(this);
		b.setBounds(80,130,100,25);

		b = new JButton("CLEAR");
		this.add(b);
		b.addActionListener(this);

		b.setBounds(200,130,100,25);

		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setSize(400,220);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{	
		String text = e.getActionCommand();
		String err = "";
		
		switch(text)
		{
			case "LOGIN":
				if(txt_username.getText().trim().equals("abc") && txt_password.getText().trim().equals("abc"))
				{
					JOptionPane.showMessageDialog(this,"Login Successful!!");
					
					this.setVisible(false);
					MainFrame a =  new MainFrame();
				}
				else
				{
					if(txt_username.getText().trim() != "abc")
					{
						if(txt_username.getText().trim().isEmpty())
							err += "Enter Username\n";
						else
							err += "Enter valid Username\n";
					}

					if(txt_password.getText().trim() != "abc")
					{
						if(txt_password.getText().trim().isEmpty())
							err += "Enter Password\n";
						else
							err += "Enter valid Password\n";
					}
					JOptionPane.showMessageDialog(this,err);
				}

				break;

			case "CLEAR":
				try{
					if(txt_username.getText().length()!=0 || txt_password.getText().length()!=0)
					{
						txt_username.setText("");
						txt_password.setText("");
					}
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
				break;	
		}
	}
}

class MyClinic
{
	public static void main(String[] args)
	{
		new LoginFrame();
		// new MainFrame();
	}
}