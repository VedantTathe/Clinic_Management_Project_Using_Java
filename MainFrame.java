// import javax.swing.*;
// import java.awt.*;
// import java.awt.event.*;
// import java.util.*;
// import java.sql.*;
// import java.text.*;
// import javax.swing.event.*;
// import javax.swing.table.*;


// class MainFrame extends JFrame implements ActionListener, ListSelectionListener
// { 
// 	private JButton b;


// 	public MainFrame()
// 	{
// 		super("Home Page");

// 		this.setLayout(null);

// 		JPanel p = new JPanel();
// 		p.setBounds(250, 150, 800, 300);

// 		p.setLayout(new GridLayout(1, 2, 10, 10));

// 		b = new JButton("MENU");
// 		p.add(b);
// 		b.addActionListener(this);

// 		b = new JButton("APPOINTMENT");
// 		p.add(b);
// 		b.addActionListener(this);

// 		this.add(p);

// 		this.setVisible(true);
// 		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

// 		this.setExtendedState(JFrame.MAXIMIZED_BOTH);	
// 	}

// 	@Override
// 	public void actionPerformed(ActionEvent e)
// 	{
// 		String text = e.getActionCommand();

// 		switch(text)
// 		{
// 			case "MENU":
// 				new MenuFrame();
// 				break;
// 			case "APPOINTMENT":
// 				new AppointmentFrame();
// 				break;
// 		}
// 	}

// 	@Override
// 	public void valueChanged(ListSelectionEvent e){

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


class MainFrame extends JFrame implements ActionListener, ListSelectionListener
{ 
	private JButton b;


	public MainFrame()
	{
		super("Home Page");

		this.setLayout(null);

		JPanel p1 = new JPanel();
		this.add(p1);
		p1.setBounds(100, 50, 1050, 100);

		JLabel lbl = new JLabel("WELCOME AKSHAY MAHORE...!");
		lbl.setFont(new Font("calibri", Font.BOLD, 30));
		p1.add(lbl);

		JPanel p = new JPanel();
		p.setBounds(250, 150, 800, 300);

		p.setLayout(new GridLayout(1, 2, 10, 10));

		ImageIcon menu_icon = new ImageIcon("Hippocrates_icon.jpg");
		Image i = menu_icon.getImage();
		Image new_menu_icon = i.getScaledInstance(420,300,Image.SCALE_SMOOTH);
		menu_icon = new ImageIcon(new_menu_icon);
		b = new JButton("MENU",menu_icon);
		p.add(b);
		b.addActionListener(this);

		ImageIcon appointment_icon = new ImageIcon("appointment_icon.jpg");
		Image j = appointment_icon.getImage();
		Image new_appointment_icon = j.getScaledInstance(420,400,Image.SCALE_SMOOTH);
		appointment_icon = new ImageIcon(new_appointment_icon);		
		b = new JButton("APPOINTMENT",appointment_icon);
		p.add(b);
		b.addActionListener(this);

		this.add(p);

		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		this.setExtendedState(JFrame.MAXIMIZED_BOTH);	
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		String text = e.getActionCommand();

		switch(text)
		{
			case "MENU":
				new MenuFrame();
				break;
			case "APPOINTMENT":
				new AppointmentFrame();
				break;
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e){

	}
}