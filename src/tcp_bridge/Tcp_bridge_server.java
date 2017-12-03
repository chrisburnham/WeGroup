package tcp_bridge;

import data_types.*;
import java.util.*;
import java.net.*;
import java.io.*;

// This may become abstract as well and pass along
// handeling Distribute_data to children
public class Tcp_bridge_server extends Tcp_bridge 
{
	// Initialize the server bridge
	public void Init()
	{
		super.Init();
		
		m_connected = false;
		m_check_connected_timer = null;
		m_accept_thread = null;
	}
	
	// Send out the data we have received
	protected void Distribute_data(Base_data data) 
	{
		System.out.println("Server received data:");
		System.out.println(data.toString());
		// TODO Pass up data to send to the server
		
		
		// The following is just here for testing
		Poll_data poll_data = new Poll_data();
		poll_data.m_poll_question = "What is the anwser";
		poll_data.m_poll_options.add("42");
		poll_data.m_poll_options.add("not 42");
		System.out.println(poll_data);
		
		if(!Send_data(poll_data))
		{
			System.out.println("poll data failed to send");
		}
	}
	
	// Open up port to receive client connection
	public boolean Open_server(int port)
	{
		try
		{
			m_server = new ServerSocket(port);
		}
		catch(IOException e)
		{
			System.out.println("Error creating server:" + e);
			System.out.println(port);
			return false;
		}
		
		m_accept_thread = new Thread(new accepter());
		m_accept_thread.start();
		
		return true;
	}
	
	// Starts our timer to check if we are connected
	public void Start_checking_connected()
	{
		if(m_check_connected_timer != null)
			m_check_connected_timer = null;
		
		m_check_connected_timer = new java.util.Timer();
		m_check_connected_timer.schedule
		(
			new java.util.TimerTask()
			{
				public void run()
				{
					check_connected();
				}
			}, 100, 100
		);
	}
	
	// Check if we have accepted a connection
	protected void check_connected()
	{
		System.out.println("checking if connected");
		
		if(m_connected)
		{
			m_check_connected_timer = null;
			Start_checking_data();
		}
	}
	
	
	// Wrapper around accept in its own thread
	class accepter implements Runnable
	{
		public void run() 
		{
			m_connected = false;
			System.out.println("waiting to accept");
			
			try
			{
				m_socket = m_server.accept();
			}
			catch(BindException e)
			{
				System.out.println("error binding");
				return;
			}
			catch(IOException e)
			{
				System.out.println("IO error");
				return;
			}
			
			try
			{
				System.out.println("server open input stream");
				m_is = new ObjectInputStream(m_socket.getInputStream());
				System.out.println("server open output stream");
				m_os = new ObjectOutputStream(m_socket.getOutputStream());
				System.out.println("server streams opened");
			}
			catch(IOException e)
			{
				System.out.println("io exception on server stream open");
				return;
			}
			
			System.out.println("server accepted connection");
			m_connected = true;
		}
	}

	protected ServerSocket m_server;
	
	protected Timer m_check_connected_timer;
	
	protected Thread m_accept_thread;
	
	protected boolean m_connected;
}
