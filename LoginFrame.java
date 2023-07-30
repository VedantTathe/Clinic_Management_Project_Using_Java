import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import javax.swing.event.*;
import javax.swing.table.*;


class LoginFrame extends JFrame implements ActionListener
{
	private JLabel lbl;
	private JTextField txt_username;
	private JPasswordField txt_password;
	private JButton b;
	private JCheckBox cb;

	public LoginFrame()
	{	
		this.setLayout(null);
		this.setTitle("LOGIN TO CLINIC");
		lbl = new JLabel("LOG IN HERE!");
		lbl.setFont(new Font("Calibric",Font.BOLD,18));
		this.add(lbl);

		lbl.setBounds(140,10,150,35);

		lbl = new JLabel("Enter Username:");
		this.add(lbl);
		lbl.setBounds(50,45,100,35);

		txt_username = new JTextField();
		this.add(txt_username);
		txt_username.setBounds(160,50,170,25);

		lbl = new JLabel("Enter Password:");
		this.add(lbl);
		lbl.setBounds(50,90,100,35);

		txt_password = new JPasswordField();
		this.add(txt_password);
		txt_password.setBounds(160,90,170,25);

		// cb = new JCheckBox("Show Password");
		// this.add(cb);
		// cb.setBounds(160,120,200,25);

		b = new JButton("LOGIN");
		this.add(b);
		b.addActionListener(this);
		b.setBounds(80,160,100,25);

		b = new JButton("CLEAR");
		this.add(b);
		b.addActionListener(this);

		b.setBounds(200,160,100,25);

		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setSize(400,250);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{	
		String text = e.getActionCommand();
		String err = "";
		
		// if(cb.isSelected())
		// {
		// 	txt_password.setTextVisible(true);
		// }

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