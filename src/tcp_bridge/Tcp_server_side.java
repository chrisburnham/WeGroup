package tcp_bridge;

import java.util.*;

import server.Database_manager;

import data_types.*;

// Server side of the TCP connection
// Manages logins
public class Tcp_server_side 
{
		
	// Initialize the server
	public void Init()
	{
		m_login_server = new Login_server();
		m_login_server.Register_server_side(this);
	}
	
	// Register a class to receive all the data that comes from the server
	public void Register_reciver(Database_manager callback)
	{
		m_callback_class = callback;
	}
	
	// Send a data message
	public void Send_data(Base_data data)
	{
		User_login user = new User_login();
		user.m_user_name = data.m_user_id;
		user.m_group_name = data.m_group_id;
		
		Tcp_server_connection connection = m_client_connections.get(user);
		connection.Send_data(data);
	}
	
	// Check if a user is connected
	public boolean Is_user_connected(String user_name, String group_name)
	{
		User_login user = new User_login();
		user.m_user_name = user_name;
		user.m_group_name = group_name;
		
		if(!m_client_connections.containsKey(user))
		{
			return false;
		}
		else
		{
			Tcp_server_connection client_tcp = m_client_connections.get(user);
			return client_tcp.Is_connected();
		}
	}
	
	// Get new login from login server
	public void New_connection(String user_name, String group_name, int port)
	{
		User_login user = new User_login();
		Tcp_server_connection connection = new Tcp_server_connection();
		
		user.m_user_name = user_name;
		user.m_group_name = group_name;
		
		connection.Register_server_side(this);
		connection.Set_user_info(user_name, group_name);
		connection.Set_port(port);
		
		// This has return values if needed
		m_client_connections.put(user, connection);
	}
	
	// Receive client data coming in from a client
	public void Client_data(Base_data data)
	{
		m_callback_class.Data_received(data);
	}
	
	private Login_server m_login_server;
	
	// Don't actually use the password in user login
	private Map<User_login, Tcp_server_connection> m_client_connections;

	// Sends messages to this class with Data_received(Base_data data)
	private Database_manager m_callback_class;
}
