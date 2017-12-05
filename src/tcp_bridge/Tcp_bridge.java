package tcp_bridge;

import java.io.*;
import java.net.*;
import java.util.*;

import data_types.Base_data;

// https://www.tutorialspoint.com/java/java_serialization.htm
// how to serialize/ deserialize

public abstract class Tcp_bridge 
{
	public void Init()
	{
		m_incoming_data = new Stack<Base_data>();
		Clear();
	}
	
	// Clear the class data
	public void Clear()
	{
		m_socket = null;
		m_os = null;
		m_is = null;
		m_new_data = false;
		m_check_data_timer = null;
		m_data_in_thread = null;
		m_incoming_data.clear();
	}
	
	// Close existing connection
	protected boolean close_connection()
	{
		System.out.println("closing connection");
		
		if(m_socket == null)
			return true;
		
		try
		{
			m_socket.close();
			Clear();
			return true;
		}
		catch(IOException e)
		{
			System.out.println("IO exception closing connection");
			return false;
		}
	}
	
	// Sends a constructed data message
	// Returns whether the message sent
	public boolean Send_data(Base_data data)
	{
		if(!Is_connected())
		{
			System.out.println("socket not connectd");
			return false;
		}
		
		try
		{
			//m_os.flush();
			m_os.writeObject(data);
			//m_os.flush();
			return true;
		}
		catch(IOException e)
		{
			System.out.println("problem sending message");
			return false;
		}
	}
	
	// returns if we have a active connection
	public boolean Is_connected()
	{
		if(m_socket == null)
			return false;
		
		return m_socket.isConnected();
	}
	
	// Starts our receiver thread and timer to check if we have data
	public void Start_checking_data()
	{
		m_data_in_thread = new Thread(new receiver());
		m_data_in_thread.start();
		
		if(m_check_data_timer != null)
			m_check_data_timer = null;
		
		m_check_data_timer = new java.util.Timer();
		m_check_data_timer.schedule
		(
			new java.util.TimerTask()
			{
				public void run()
				{
					check_data();
				}
			}, 0, 100
		);
	}
	
	// Check if we have data
	protected void check_data()
	{
		if(m_new_data)
		{
			synchronized(this)
			{
				while(!m_incoming_data.empty())
				{
					Base_data data = m_incoming_data.pop();
					Distribute_data(data);
				}
				m_new_data = false;
			}
		}
	}
	
	// Send data to listener
	abstract void Distribute_data(Base_data data);
	
	// Wrapper around readObject in its own thread
	class receiver implements Runnable
	{
		public void run() {
			m_running = true;
			
			while(m_running)
			{
				Base_data data = null;
				try
				{
					data = (Base_data)m_is.readObject();
				}
				catch(ClassNotFoundException e)
				{
					System.out.println("class not found" + e);
				}
				catch(IOException e)
				{
					System.out.println("IO exception reading object: " + e);
				}
				
				if(data != null)
				{
					synchronized(Tcp_bridge.this)
					{
						m_new_data = true;
						m_incoming_data.push(data);
					}
				}
				
				try
				{
					Thread.sleep(100);
				}
				catch(InterruptedException e)
				{
					m_running = false;
				}
			}
		}
		
		public void Stop()
		{
			m_running = false;
		}
		
		private boolean m_running;
	}
	
	protected Socket m_socket;
	
	protected ObjectOutputStream m_os;
	
	protected ObjectInputStream m_is;
	
	protected Timer m_check_data_timer;
	
	protected Thread m_data_in_thread;
	
	protected boolean m_new_data;
	
	protected Stack<Base_data> m_incoming_data;
}
