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

		JPanel p = new JPanel();
		p.setBounds(250, 150, 800, 300);

		p.setLayout(new GridLayout(1, 2, 10, 10));

		b = new JButton("MENU");
		p.add(b);
		b.addActionListener(this);

		b = new JButton("Appointment");
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
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e){

	}
}
