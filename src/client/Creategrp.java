package client;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.*;
import javax.swing.*;
import data_types.*;
import tcp_bridge.*;

public class Creategrp extends JPanel implements  ActionListener
{
	private static final long serialVersionUID = 4L;
	private JPanel panel;
	private JScrollPane scroll1 = new JScrollPane();
	private final JLabel lblTitle = new JLabel("Title:");
	private final JButton btnCancel = new JButton("Cancel");
	private final JButton btnCreate = new JButton("Create");
	
	protected Tcp_client_side m_tcp;
	private final JScrollPane scrollPane = new JScrollPane();
	private final JLabel lblUsers = new JLabel("Users");
	private final JLabel lblPass = new JLabel("Passwords");
	private final JTextArea userf = new JTextArea();
	private final JTextArea passf = new JTextArea();
	private final JTextField titlef = new JTextField();
	
	public Creategrp() 
	{
		titlef.setColumns(10);
		Init();
		setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{216, 227, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 189, 21, 51, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
				
				GridBagConstraints gbc_lblTitle = new GridBagConstraints();
				gbc_lblTitle.insets = new Insets(0, 0, 5, 5);
				gbc_lblTitle.anchor = GridBagConstraints.EAST;
				gbc_lblTitle.gridx = 0;
				gbc_lblTitle.gridy = 0;
				panel.add(lblTitle, gbc_lblTitle);
		
		GridBagConstraints gbc_titlef = new GridBagConstraints();
		gbc_titlef.insets = new Insets(0, 0, 5, 0);
		gbc_titlef.fill = GridBagConstraints.HORIZONTAL;
		gbc_titlef.gridx = 1;
		gbc_titlef.gridy = 0;
		panel.add(titlef, gbc_titlef);
		
		GridBagConstraints gbc_lblUsers = new GridBagConstraints();
		gbc_lblUsers.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsers.gridx = 0;
		gbc_lblUsers.gridy = 1;
		panel.add(lblUsers, gbc_lblUsers);
		
		GridBagConstraints gbc_lblPass = new GridBagConstraints();
		gbc_lblPass.insets = new Insets(0, 0, 5, 0);
		gbc_lblPass.gridx = 1;
		gbc_lblPass.gridy = 1;
		panel.add(lblPass, gbc_lblPass);
		
		
		GridBagConstraints gbc_Groupfield = new GridBagConstraints();
		gbc_Groupfield.gridheight = 2;
		gbc_Groupfield.insets = new Insets(0, 0, 5, 5);
		gbc_Groupfield.fill = GridBagConstraints.BOTH;
		gbc_Groupfield.gridx = 0;
		gbc_Groupfield.gridy = 2;
		panel.add(scroll1, gbc_Groupfield);
		scroll1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		scroll1.setViewportView(userf);
		
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 2;
		panel.add(scrollPane, gbc_scrollPane);
		
		scrollPane.setViewportView(passf);
		
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancel.gridx = 0;
		gbc_btnCancel.gridy = 5;
		panel.add(btnCancel, gbc_btnCancel);
		
		GridBagConstraints gbc_btnCreate = new GridBagConstraints();
		gbc_btnCreate.gridx = 1;
		gbc_btnCreate.gridy = 5;
		panel.add(btnCreate, gbc_btnCreate);
		
	}
	public void actionPerformed(ActionEvent evt)
	{
		System.out.println("action event");

		if (evt.getSource() == btnCreate)
		{	
			System.out.println("create clicked");

			Add_group_data group = new Add_group_data();
			String setup[] = userf.getText().split("\\r?\\n");
			String setup2[] = passf.getText().split("\\r?\\n");
			Vector<String> userlist = new Vector<String>(Arrays.asList(setup));
			Vector<String> passlist = new Vector<String>(Arrays.asList(setup2));
			group.m_group_name = titlef.getText();
			group.m_user_names.addAll(userlist);
			group.m_passwords.addAll(passlist);

			System.out.println("Requesting group creation:" + group.toString());

			m_tcp.Send_data(group);
		}
	}

	// Initialize this class
	public void Init()
	{
		m_tcp = new Tcp_client_side();
		m_tcp.Init();
	}

	public static void GUI()
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable()
		{
			public void run() 
			{
				try 
				{
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //attempt to use system look for UI
				} catch (Exception useDefault) {}
				JFrame frame = new JFrame("Create new WeGroup");
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame.getContentPane().add(new Creategrp());
				frame.pack();
				frame.setLocationByPlatform(true);
				frame.setVisible(true);
			}
		});
	}
	public static void main(String[] args)
	{
		GUI();
	}
}
