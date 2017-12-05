package tcp_bridge;

import client.Login;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

import data_types.*;

// This will do any Client Specific actions we decide 
// we need for the bridge
public class Tcp_client_side extends Tcp_bridge 
{
	// Initialize the client
	public void Init()
	{
		super.Init();

		System.out.println("opening connection");
		if(open_connection(m_server_ip, 1129))
		{
			//System.out.println("connection opened");
			
			Start_checking_data();
			//System.out.println("starting to listen for data");
			
			//Message_data data = new Message_data();
			//data.m_message = "hello world";
			//System.out.println(data);
			
			//if(!Send_data(data))
			//{
				//System.out.println("data failed to send");
			//}
		}
		else
		{
			System.out.println("connection failed to open");
		}
	}
	
	// Open a new connection
	protected boolean open_connection(String host, int port)
	{
		if(m_socket == null)
		{
			if(!close_connection())
				return false;
		}
		
		try
		{
			m_socket = new Socket(host, port);
		}
		catch(UnknownHostException e)
		{
			System.out.print("bad host name ");
			System.out.println(host);
			return false;
		}
		catch(IOException e)
		{
			System.out.println("couldn't get I/O connection for:");
			System.out.println(host);
			System.out.println(port);
			return false;
		}
		
		return open_streams();
	}
	
	// Open the streams for our new connection;
	private boolean open_streams()
	{
		//System.out.println("opening streams");
		
		try
		{
			System.out.println("open output stream");
			m_os = new ObjectOutputStream(m_socket.getOutputStream());
			
			//m_os.defaultWriteObject();
			//m_os.flush();
			
			System.out.println("open input stream");
			m_is = new ObjectInputStream(m_socket.getInputStream());	
			
			System.out.println("both streams opened");
			return true;
		}
		catch(IOException e)
		{
			System.out.println("io exception on stream open");
			return false;
		}
	}

	// Register a class to receive all the data that comes from the server
	public void Register_reciver(Login callback)
	{
		m_callback_class = callback;
	}
	
	// Send Data to listener
	protected void Distribute_data(Base_data data)
	{
		System.out.println("Client received data:");
		System.out.println(data.toString());
		
		if(data.m_type == Tcp_message_type.Login_response
				&& data instanceof Login_response_data)
		{
			Login_response_data response = (Login_response_data)data;
			if(response.m_accpted && response.m_port_number != 0)
			{
				// Have return here we could use
				close_connection();
				
				if(open_connection(m_server_ip, response.m_port_number))
				{	
					Start_checking_data();
				}
			}
			
			// TODO: What to do in fail case?
		}
		
		m_callback_class.Data_received(data);
	}
	
	// Sends messages to this class with Data_received(Base_data data)
	private Login m_callback_class;
	
	private String m_server_ip = "192.168.1.5";
}
 