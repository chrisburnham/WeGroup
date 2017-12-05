package tcp_bridge;

import java.util.*;
import data_types.*;

public class Login_server extends Tcp_bridge_server 
{
	public Login_server()
	{
		m_users = new ArrayList<User_login>();
		m_port_number = 1129;
		Init();
		Open_server(m_port_number);
		Start_checking_connected();
	}
	
	// Receive data. Looking for a login
	protected void Distribute_data(Base_data data)
	{
		System.out.println("login received data:" + data.toString());
		
		if(data.m_type == Tcp_message_type.Login
				&& data instanceof Login_data)
		{
			Login_data login = (Login_data)data;
			Login_response_data response = new Login_response_data();
			
			if(Valid_login(login.m_user_name, login.m_group_name, 
					login.m_password))
			{
				m_port_number++;
				response.m_accpted = true;
				response.m_port_number = m_port_number;
				Send_data(response);
				m_server_side.New_connection(login.m_user_name, 
						login.m_group_name, m_port_number);
				close_connection();
				Start_checking_connected();
			}
			else
			{
				Send_data(response);
			}
		}
		else if(data.m_type == Tcp_message_type.Add_group
				&& data instanceof Add_group_data)
		{
			Add_group_data add_group = (Add_group_data)data;
			
			Iterator<String> it = add_group.m_user_names.iterator();
			Iterator<String> pas_it = add_group.m_passwords.iterator();
			while(it.hasNext())
			{
				if(pas_it.hasNext())
				{
					Add_user(it.next(), add_group.m_group_name, pas_it.next());
				}
			}
		}
	}
	
	// Add a new user with the user, group and password
	// Will fail if any of the fields are empty or if
	// we already have that user
	public void Add_user(String user_name, String group_name, String password)
	{
		System.out.println("Creating user" + user_name + group_name + password);
		
		if(user_name != "" && group_name != "" && password != "" &&
				!Contains_user(user_name, group_name))
		{
			User_login new_user = new User_login();
			new_user.m_user_name = user_name;
			new_user.m_group_name = group_name;
			new_user.m_password = password;
			
			m_users.add(new_user);
		}
	}
	
	// Do we have that user
	public boolean Contains_user(String user_name, String group_name)
	{
		User_login user = find_user(user_name, group_name);
		if(user.m_user_name == "" || user.m_group_name == "")
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	// is the login valid for the user
	public boolean Valid_login(String user_name, String group_name, String password)
	{
		User_login user = find_user(user_name, group_name);
		if(user.m_password != "" && password == user.m_password)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	// Returns the user with the user and group name
	// Returns default User_login (empty strings) on failure
	private User_login find_user(String user_name, String group_name)
	{
		if(user_name == "" || group_name == "")
		{
			return new User_login();
		}
		
		for(int i = 0; i < m_users.size(); i++)
		{
			User_login user = m_users.get(i);
			if(user.m_user_name == user_name 
					&& user.m_group_name == group_name)
			{
				return user;
			}
		}
		
		// User not found
		return new User_login();
	}
	
	private List<User_login> m_users;
	
	private int m_port_number;
}
