package client;

import java.awt.event.*;
import javax.swing.*;

import data_types.*;
import tcp_bridge.*;

public class Login extends JPanel implements  ActionListener
{
	private static final long serialVersionUID = 1L;
	private JTextField groupname;
	private JTextField username;
	private JTextField pwd;
	public static String grpname;
	public static String usrname;
	private JButton btnOk = new JButton("OK");
	private JButton btnCancel = new JButton("Cancel");
	private Main_page m_main_page = null;
	protected Tcp_client_side m_tcp;
	
	public Login() 
	{
		Init();

		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
			
		JLabel grouplabel = new JLabel("Group Name:");
		springLayout.putConstraint(SpringLayout.NORTH, grouplabel, 35, SpringLayout.NORTH, this);
		add(grouplabel);
		
		JLabel userlabel = new JLabel("User Name:");
		springLayout.putConstraint(SpringLayout.WEST, grouplabel, 0, SpringLayout.WEST, userlabel);
		add(userlabel);
		
		JLabel passlabel = new JLabel("Password:");
		springLayout.putConstraint(SpringLayout.WEST, passlabel, 0, SpringLayout.WEST, userlabel);
		add(passlabel);
		
		groupname = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, groupname, -3, SpringLayout.NORTH, grouplabel);
		springLayout.putConstraint(SpringLayout.WEST, groupname, 6, SpringLayout.EAST, grouplabel);
		add(groupname);
		groupname.setColumns(10);
		
		username = new JTextField();
		springLayout.putConstraint(SpringLayout.WEST, username, 122, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, userlabel, -12, SpringLayout.WEST, username);
		springLayout.putConstraint(SpringLayout.NORTH, userlabel, 3, SpringLayout.NORTH, username);
		add(username);
		username.setColumns(10);
		
		pwd = new JTextField();
		springLayout.putConstraint(SpringLayout.WEST, pwd, 17, SpringLayout.EAST, passlabel);
		springLayout.putConstraint(SpringLayout.NORTH, passlabel, 3, SpringLayout.NORTH, pwd);
		springLayout.putConstraint(SpringLayout.NORTH, pwd, 84, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.SOUTH, username, -6, SpringLayout.NORTH, pwd);
		pwd.setColumns(10);
		add(pwd);
		
		
		springLayout.putConstraint(SpringLayout.EAST, btnOk, -28, SpringLayout.EAST, this);
		add(btnOk);
		btnOk.addActionListener(this);
		
		
		springLayout.putConstraint(SpringLayout.NORTH, btnCancel, 23, SpringLayout.SOUTH, passlabel);
		springLayout.putConstraint(SpringLayout.WEST, btnCancel, 28, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.NORTH, btnOk, 0, SpringLayout.NORTH, btnCancel);
		add(btnCancel);
		btnCancel.addActionListener(this);
	
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
	
	public void recauth(Login_response_data data) 
	{
		if(data.m_accpted) 
		{
			m_main_page = new Main_page();
			m_main_page.setup_ui(usrname, grpname);
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
