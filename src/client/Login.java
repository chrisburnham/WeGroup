package client;

import java.awt.event.*;
import javax.swing.*;

import data_types.*;
import tcp_bridge.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class Login extends JPanel implements  ActionListener
{
	private static final long serialVersionUID = 1L;
	private JTextField groupname;
	private JTextField username;
	private JTextField pwd;
	static String[] setup;
	public static String grpname;
	public static String usrname;
	private JButton btnOk = new JButton("OK");
	private JButton btnCancel = new JButton("Cancel");
	JButton btnCreateGroup = new JButton("Create Group");
	private Main_page m_main_page = null;
	protected Tcp_client_side m_tcp;
	
	public Login() 
	{
		Init();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{89, 88, 166, 0};
		gridBagLayout.rowHeights = new int[]{32, 20, 20, 20, 23, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel grouplabel = new JLabel("Group Name:");
		GridBagConstraints gbc_grouplabel = new GridBagConstraints();
		gbc_grouplabel.anchor = GridBagConstraints.EAST;
		gbc_grouplabel.insets = new Insets(0, 0, 5, 5);
		gbc_grouplabel.gridx = 0;
		gbc_grouplabel.gridy = 1;
		add(grouplabel, gbc_grouplabel);
		
		groupname = new JTextField();
		GridBagConstraints gbc_groupname = new GridBagConstraints();
		gbc_groupname.fill = GridBagConstraints.HORIZONTAL;
		gbc_groupname.anchor = GridBagConstraints.NORTH;
		gbc_groupname.insets = new Insets(0, 0, 5, 5);
		gbc_groupname.gridx = 1;
		gbc_groupname.gridy = 1;
		add(groupname, gbc_groupname);
		groupname.setColumns(10);
		
		JLabel userlabel = new JLabel("User Name:");
		GridBagConstraints gbc_userlabel = new GridBagConstraints();
		gbc_userlabel.anchor = GridBagConstraints.EAST;
		gbc_userlabel.insets = new Insets(0, 0, 5, 5);
		gbc_userlabel.gridx = 0;
		gbc_userlabel.gridy = 2;
		add(userlabel, gbc_userlabel);
		
		username = new JTextField();
		GridBagConstraints gbc_username = new GridBagConstraints();
		gbc_username.fill = GridBagConstraints.HORIZONTAL;
		gbc_username.anchor = GridBagConstraints.NORTH;
		gbc_username.insets = new Insets(0, 0, 5, 5);
		gbc_username.gridx = 1;
		gbc_username.gridy = 2;
		add(username, gbc_username);
		username.setColumns(10);
		
		JLabel passlabel = new JLabel("Password:");
		GridBagConstraints gbc_passlabel = new GridBagConstraints();
		gbc_passlabel.anchor = GridBagConstraints.EAST;
		gbc_passlabel.insets = new Insets(0, 0, 5, 5);
		gbc_passlabel.gridx = 0;
		gbc_passlabel.gridy = 3;
		add(passlabel, gbc_passlabel);
		
		pwd = new JTextField();
		pwd.setColumns(10);
		GridBagConstraints gbc_pwd = new GridBagConstraints();
		gbc_pwd.fill = GridBagConstraints.HORIZONTAL;
		gbc_pwd.anchor = GridBagConstraints.NORTH;
		gbc_pwd.insets = new Insets(0, 0, 5, 5);
		gbc_pwd.gridx = 1;
		gbc_pwd.gridy = 3;
		add(pwd, gbc_pwd);
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnCancel.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancel.gridx = 0;
		gbc_btnCancel.gridy = 4;
		add(btnCancel, gbc_btnCancel);
		btnCancel.addActionListener(this);
		
		
		GridBagConstraints gbc_btnCreateGroup = new GridBagConstraints();
		gbc_btnCreateGroup.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnCreateGroup.insets = new Insets(0, 0, 0, 5);
		gbc_btnCreateGroup.gridx = 1;
		gbc_btnCreateGroup.gridy = 4;
		add(btnCreateGroup, gbc_btnCreateGroup);
		btnCreateGroup.addActionListener(this);
		
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnOk.gridx = 2;
		gbc_btnOk.gridy = 4;
		add(btnOk, gbc_btnOk);
		btnOk.addActionListener(this);
	
	}

	// Initialize this class
	public void Init()
	{
		m_tcp = new Tcp_client_side();
		m_tcp.Init();
		m_tcp.Register_reciver(this);
	}
	
	public void actionPerformed(ActionEvent evt)
	{
		if(evt.getSource() == btnOk)
		{
			String data= pwd.getText().trim();
			grpname = groupname.getText().trim();
			usrname = username.getText().trim();
			if(!data.equals("") & !grpname.equals("") & !usrname.equals("")) //verify their is anything to send
			{
				Login_data login_data = new Login_data();
				login_data.m_user_name = usrname;
				login_data.m_group_name = grpname;
				login_data.m_password = data;

				m_tcp.Send_data(login_data);

				groupname.setText(""); //clears out the field area
				username.setText("");
				pwd.setText("");
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Missing Login information");
			}
		}
		else if (evt.getSource() == btnCreateGroup)
		{
			Creategrp c_grp = new Creategrp();
			c_grp.GUI();
		}
		else if (evt.getSource() == btnCancel)
		{
			System.exit(0);
		}
	}

	public void Data_received(Base_data data)
	{
		if(m_main_page != null)
		{
			m_main_page.Data_received(data);
		}
		else
		{
			if(data.m_type == Tcp_message_type.Login_response 
				&& data instanceof Login_response_data)
			{
				Login_response_data login_data = (Login_response_data)data;
				recauth(login_data);
			}
		}
	}
	
	public String getuser() {
		return usrname;
	}
	public String getgroup() {
		return grpname;
	}
	public void recauth(Login_response_data data) 
	{
		if(data.m_accpted) 
		{
			m_main_page = new Main_page();
			m_main_page.GUI();
			//m_main_page.setup_ui(usrname, grpname);
			m_main_page.set_tcp(m_tcp);
		}
	}
	
	public static void GUI()
	{
		JFrame frame = new JFrame("WeGroup Login");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		frame.getContentPane().add(new Login());
		
		frame.pack();
		frame.setSize(285,190);
		frame.setLocationRelativeTo(null);
		//frame.setLocationByPlatform(true);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	public static void main (String[] args)
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable()
		{
			public void run() 
			{
				try 
				{
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception useDefault) {}
				GUI();
			}
		});
	}
}
