package client;

import data_types.*;
import tcp_bridge.Tcp_client_side;
/*
public class Main_page 
{

	// Initialize this class
	public void Init()
	{
		m_tcp = new Tcp_client_side();
		m_tcp.Init();
		m_tcp.Register_reciver(this);
	}
	
	// Data received from TCP
	public void Data_received(Base_data data)
	{
		
	}
	
	// Class to send TCP
	protected Tcp_client_side m_tcp;
}
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.util.List;

import javax.swing.event.*;

public class Main_page extends JPanel implements  ActionListener, ListSelectionListener
{
	private static final long serialVersionUID = 9L;
	private static JFrame frame = new JFrame("WeGroup");
	private static String name;
	private static String group;
	public  static String target;
	private int index;
	private String[] setup;
	private DefaultListModel<String> membermod = new DefaultListModel<String>();
	private DefaultListModel<String> listmod = new DefaultListModel<String>();
	private DefaultListModel<String> pollmod = new DefaultListModel<String>();
	private JPanel panel = new JPanel();
	private JList<String> lists = new JList<String>(listmod);
	private JList<String> polls = new JList<String>(pollmod);
	private JList<String> members = new JList<String>(membermod);
	private JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private JScrollPane scrollPane = new JScrollPane();
	private JScrollPane scrollPane_1 = new JScrollPane();
	private JTextArea groupfield = new JTextArea();
	private JTextArea messages = new JTextArea();
	private final JButton btnSend = new JButton("Send");
	private final JButton btnEmote = new JButton("emote");
	private final JButton btnCreateList = new JButton("Create list");
	private final JButton btnCreatePoll = new JButton("create poll");
	private final JButton btnAttach = new JButton("attach");
	private JPopupMenu menu = new JPopupMenu();
	private JMenuItem item = new JMenuItem();
	private JMenuItem item2 = new JMenuItem();
	private JMenuItem item3 = new JMenuItem();

	private ArrayList<List_edit> m_lists;
	private ArrayList<Pchat> m_chats;
	//private ArrayList<Poll_
	
	public Main_page() 
	{
		setLayout(new BorderLayout(0, 0));
		
		
		add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{120, 139, 351, 69, 0};
		gbl_panel.rowHeights = new int[]{308, 58, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.gridwidth = 2;
		gbc_tabbedPane.gridheight = 4;
		gbc_tabbedPane.insets = new Insets(0, 0, 5, 5);
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 0;
		panel.add(tabbedPane, gbc_tabbedPane);
		
		
		tabbedPane.addTab("Members", null, members, null);
		
		
		tabbedPane.addTab("Lists", null, lists, null);
		
		
		tabbedPane.addTab("Polls", null, polls, null);
		
		
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 2;
		gbc_scrollPane.gridy = 0;
		panel.add(scrollPane, gbc_scrollPane);
		
		
		scrollPane.setViewportView(groupfield);
		
		
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.gridheight = 3;
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 2;
		gbc_scrollPane_1.gridy = 1;
		panel.add(scrollPane_1, gbc_scrollPane_1);
		
		
		scrollPane_1.setViewportView(messages);
		
		GridBagConstraints gbc_btnEmote = new GridBagConstraints();
		gbc_btnEmote.insets = new Insets(0, 0, 5, 0);
		gbc_btnEmote.gridx = 3;
		gbc_btnEmote.gridy = 1;
		panel.add(btnEmote, gbc_btnEmote);
		btnEmote.addActionListener(this);
		
		GridBagConstraints gbc_btnSend = new GridBagConstraints();
		gbc_btnSend.insets = new Insets(0, 0, 5, 0);
		gbc_btnSend.gridx = 3;
		gbc_btnSend.gridy = 2;
		panel.add(btnSend, gbc_btnSend);
		btnSend.addActionListener(this);
		
		lists.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lists.setSelectedIndex(0);
		lists.setVisibleRowCount(10);
		lists.addListSelectionListener(this);
		listmod.addElement("Johnny");
		
		members.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		members.setSelectedIndex(0);
		members.setVisibleRowCount(10);
		members.addListSelectionListener(this);
		membermod.addElement("Johnny");
		
		polls.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		polls.setSelectedIndex(0);
		polls.setVisibleRowCount(10);
		
		
		polls.addListSelectionListener(this);
		pollmod.addElement("Johnny");
		
		GridBagConstraints gbc_btnAttach = new GridBagConstraints();
		gbc_btnAttach.insets = new Insets(0, 0, 5, 0);
		gbc_btnAttach.gridx = 3;
		gbc_btnAttach.gridy = 3;
		panel.add(btnAttach, gbc_btnAttach);
		btnAttach.addActionListener(this);
		
		GridBagConstraints gbc_btnCreateList = new GridBagConstraints();
		gbc_btnCreateList.insets = new Insets(0, 0, 0, 5);
		gbc_btnCreateList.gridx = 0;
		gbc_btnCreateList.gridy = 4;
		panel.add(btnCreateList, gbc_btnCreateList);
		btnCreateList.addActionListener(this);
		
		GridBagConstraints gbc_btnCreatePoll = new GridBagConstraints();
		gbc_btnCreatePoll.insets = new Insets(0, 0, 0, 5);
		gbc_btnCreatePoll.gridx = 1;
		gbc_btnCreatePoll.gridy = 4;
		panel.add(btnCreatePoll, gbc_btnCreatePoll);
		btnCreatePoll.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent evt)
	{
		if (evt.getSource() == btnSend)
		{	
			String data=messages.getText().trim(); //read contents of text area  into data
			if(!data.equals("")) //verify their is anything to send
				{
					name = "john";
					messages.setText(""); //clears out the message area	
					Message_data message_data = new Message_data();
					message_data.m_sender = name;
					message_data.m_message = data;

					data = "\n" + data + "\n";
					groupfield.append(data);
					//test Gatherer.pchatmsg(send); //sends the data to the class that handles sending it off the tcp_client	
				}
		}
		else if (evt.getSource() == item)
		{
			if(members.isFocusOwner())
			{	
				Pchat.main(setup);
				menu.setVisible(false);
			}
		}
		else if (evt.getSource() == item2)
		{
			if (lists.isFocusOwner())
			{
				List_edit.main(setup);
				menu.setVisible(false);
			}
			else if (polls.isFocusOwner())
			{
				Poll_view.main(setup);
				menu.setVisible(false);
			}
		}
		else if (evt.getSource() == item3)
		{
			if (polls.isFocusOwner())
			{
				Poll_vote.main(setup);
				menu.setVisible(false);
			}
		}
		else if (evt.getSource() == btnCreateList)
		{
			List_create.main(setup);
			menu.setVisible(false);
		}
		else if (evt.getSource() == btnCreatePoll)
		{
			Poll_create.main(setup);
			menu.setVisible(false);
		}
		else if (evt.getSource() == btnAttach)
		{
			
		}
		else if (evt.getSource() == btnEmote)
		{
			
		}
	}
	
	public void valueChanged(ListSelectionEvent e)
	{
		if(members.isFocusOwner() == true)
		{
			item = new JMenuItem("Private chat");
			index = members.getSelectedIndex();
			target = membermod.get(index);
			item.addActionListener(this);
			menu.add(item);
			
			//get mouse location and set pop-up menu to location 
			double dy = MouseInfo.getPointerInfo().getLocation().getY();
			double dx = MouseInfo.getPointerInfo().getLocation().getX();
			int y = (int) dy;
			int x = (int) dx;
			menu.setLocation(x, y);
			
			menu.setVisible(true);
		}
		else if(lists.isFocusOwner() == true)
		{
			item2 = new JMenuItem("open list");
			index = lists.getSelectedIndex();
			item2.addActionListener(this);
			menu.add(item);
			menu.add(item2);
			
			//get mouse location and set pop-up menu to location 
			double dy = MouseInfo.getPointerInfo().getLocation().getY();
			double dx = MouseInfo.getPointerInfo().getLocation().getX();
			int y = (int) dy;
			int x = (int) dx;
			menu.setLocation(x, y);
			
			menu.setVisible(true);
		}
		else if(polls.isFocusOwner() == true)
		{
			item2 = new JMenuItem("open poll");
			item3 = new JMenuItem("vote poll");
			index = lists.getSelectedIndex();
			item2.addActionListener(this);
			item3.addActionListener(this);
			menu.add(item);
			menu.add(item2);
			menu.add(item3);
			
			//get mouse location and set pop-up menu to location 
			double dy = MouseInfo.getPointerInfo().getLocation().getY();
			double dx = MouseInfo.getPointerInfo().getLocation().getX();
			int y = (int) dy;
			int x = (int) dx;
			menu.setLocation(x, y);
			
			menu.setVisible(true);
		}
	}
	public static String thetarget() {
		return target;
	}
	private static void GUI()
	{
		frame.setTitle("WeGroup: " + group + "(" + name +")");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.getContentPane().add(new Main_page());
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		//frame.setLocationByPlatform(true);
		//frame.setResizable(false);
		frame.setVisible(true);
	}
	/* just need to add the input data type
	//populate the polls tab with the polls
	public static void thepolls() 
	{
		index = 
		for(int i=0; i<index; i++)
		{
			pollmod.addElement();
		}
	}
	
	//populate the lists tab with the lists available to client
	public static void thelists()
	{
		index = 
		for(int i=0; i<index; i++)
		{
			listmod.addElement();
		}
	}
	
	//populate the members tab with the members of the group
	public static void theusers()
	{
		index = 
		for(int i=0; i<index; i++)
		{
			membermod.addElement();
		}
	}*/
	
	// Initialize this class
	public void Init()
	{
		m_tcp = new Tcp_client_side();
		m_tcp.Init();
		m_tcp.Register_reciver(this);
	}
	
	// Data received from TCP
	public void Data_received(Base_data data)
	{
		Tcp_message_type type = data.getm_Type();
		//String user = data.getm_User_Id();
		//String groupName = data.getm_Group_Id();
		switch (type) 
		{
			case Message:	// MESSAGE CASE
				Message_data messData;
				if(data instanceof Message_data)
				{
					messData = (Message_data)data;
					boolean pvtMsg = messData.getPrivate();
					if (pvtMsg)
					{
						Pchat.msgrec(messData);
					}	
				}
				break;

			case List:
				List_data listData;
				if(data instanceof List_data)
				{
					listData = (List_data)data;

				}

			default:
				JOptionPane.showMessageDialog(null, "Invalid data type");
		}
	}
	
	// Class to send TCP
	protected Tcp_client_side m_tcp;
	
	
	
	public static void main (String[] args)
	{
		//name = args[1];
		//group = args[0]; remove comments when intending to run from login
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
