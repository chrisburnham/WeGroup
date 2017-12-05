package client;

import data_types.*;
import tcp_bridge.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//import java.util.*;
//import java.util.List;

import javax.swing.event.*;

public class Main_page extends JPanel implements  ActionListener, ListSelectionListener
{
	private static final long serialVersionUID = 9L;
	 JFrame frame = new JFrame("WeGroup");
	static String name;
	static String group;
	static String target;
	private int index;
	protected Tcp_client_side m_tcp;
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
	JPopupMenu menu1 = new JPopupMenu();
	JPopupMenu menu2 = new JPopupMenu();
	JPopupMenu menu3 = new JPopupMenu();
	JMenuItem pchat = new JMenuItem("private chat");
	JMenuItem opoll = new JMenuItem("open poll");
	JMenuItem vpoll = new JMenuItem("vote poll");
	JMenuItem olist = new JMenuItem("open list");

	//private ArrayList<List_edit> m_lists;
	//private ArrayList<Pchat> m_chats;
	//private ArrayList<Poll_
	
	public Main_page() 
	{
		Init();
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
		groupfield.setEditable(false);
		
		
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
		
		menu1.add(pchat);
		menu2.add(olist);
		menu3.add(opoll);
		menu3.add(vpoll);
	}
	
	public void actionPerformed(ActionEvent evt)
	{
		if (evt.getSource() == btnSend)
		{	
			String data=messages.getText().trim(); //read contents of text area  into data
			if(!data.equals("")) //verify their is anything to send
				{
					messages.setText(""); //clears out the message area	
					Message_data message_data = new Message_data();
					message_data.m_sender = name;
					message_data.m_message = data;

					data = "\n" + data + "\n";
					groupfield.append(data);
					m_tcp.Send_data(message_data);	
				}
		}
		else if (evt.getSource() == pchat)
		{
			if(members.isFocusOwner())
			{	
				Pchat pchat = new Pchat();
				pchat.GUI();
				menu1.setVisible(false);
				menu1.removeAll();
			}
			else
			{
				menu1.removeAll();
				menu1.setVisible(false);
			}
		}
		else if (evt.getSource() == olist)
		{
			if (lists.isFocusOwner())
			{
				List_edit l_edit = new List_edit();
				l_edit.GUI();
				menu2.setVisible(false);
				menu2.removeAll();
			}
			else
			{
				menu2.removeAll();
				menu2.setVisible(false);
			}
		}
		else if (evt.getSource() == vpoll)
		{
			if (polls.isFocusOwner())
			{
				Poll_vote p_vote = new Poll_vote();
				p_vote.GUI();
				menu3.setVisible(false);
				menu3.removeAll();
			}
			else
			{
				menu3.setVisible(false);
			}
		}
		else if (evt.getSource() == opoll)
		{	
			if (polls.isFocusOwner())
			{
				Poll_view p_view = new Poll_view();
				p_view.GUI();
				menu3.setVisible(false);
			}
			else
			{
				menu3.setVisible(false);
			}
		}
		else if (evt.getSource() == btnCreateList)
		{
			List_create l_create = new List_create();
			l_create.GUI();
		}
		else if (evt.getSource() == btnCreatePoll)
		{
			Poll_create p_create = new Poll_create();
			p_create.GUI();
		}
		else if (evt.getSource() == btnAttach)
		{
			
		}
		else if (evt.getSource() == btnEmote)
		{
			
		}
		else 
		{
			menu1.setVisible(false);
			menu2.setVisible(false);
			menu3.setVisible(false);
		}
	}
	
	public void valueChanged(ListSelectionEvent e)
	{
		if(members.isFocusOwner() == true)
		{
			index = members.getSelectedIndex();
			target = membermod.get(index);
			pchat.addActionListener(this);
			
			//get mouse location and set pop-up menu to location 
			double dy = MouseInfo.getPointerInfo().getLocation().getY();
			double dx = MouseInfo.getPointerInfo().getLocation().getX();
			int y = (int) dy;
			int x = (int) dx;
			menu1.setLocation(x, y);
			menu1.setVisible(true);
		}
		else if(lists.isFocusOwner() == true)
		{
			index = lists.getSelectedIndex();
			olist.addActionListener(this);
			
			//get mouse location and set pop-up menu to location 
			double dy = MouseInfo.getPointerInfo().getLocation().getY();
			double dx = MouseInfo.getPointerInfo().getLocation().getX();
			int y = (int) dy;
			int x = (int) dx;
			menu2.setLocation(x, y);
			menu2.setVisible(true);
		}
		else if(polls.isFocusOwner() == true)
		{
			index = lists.getSelectedIndex();
			opoll.addActionListener(this);
			vpoll.addActionListener(this);
			
			//get mouse location and set pop-up menu to location 
			double dy = MouseInfo.getPointerInfo().getLocation().getY();
			double dx = MouseInfo.getPointerInfo().getLocation().getX();
			int y = (int) dy;
			int x = (int) dx;
			menu3.setLocation(x, y);
			menu3.setVisible(true);
		}
	}
	public static String thetarget() {
		return target;
	}
	public static String c_user() {
		return name;
	}
	
	//populate the polls tab with the polls
	public void thepolls(Poll_data data) 
	{
		for(int i=0; i<data.m_polls_list.size(); i++)
		{
			pollmod.addElement(data.m_polls_list.get(i));
		}
	}
	
	//populate the lists tab with the lists available to client
	public void thelists(List_data data)
	{
		for(int i=0; i<data.m_lists_list.size(); i++)
		{
			listmod.addElement(data.m_lists_list.get(i));
		}
	}
	
	//populate the members tab with the members of the group
	public void theusers(Add_group_data data)
	{
		for(int i=0; i<data.m_user_names.size(); i++)
		{
			membermod.addElement(data.m_user_names.get(i));
		}
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
						Pchat pchat = new Pchat();
						pchat.msgrec(messData);
						pchat.GUI();
					}	
				}
				break;

			case List:
				List_data listData;
				if(data instanceof List_data)
				{
					listData = (List_data)data;
					List_edit l_edit = new List_edit();
					l_edit.recdata(listData);
					//l_edit.GUI();
				}
			case Poll:
				Poll_data pollData;
				if(data instanceof Poll_data)
				{
					pollData = (Poll_data)data;
					Poll_view p_view = new Poll_view();
					p_view.pollres(pollData);
					p_view.GUI();
				}	
			default:
				JOptionPane.showMessageDialog(null, "Invalid data type");
		}
	}

	public void set_tcp(Tcp_client_side tcp)
	{
		m_tcp = tcp;
	}
	
	public void Init()
	{
		m_tcp = new Tcp_client_side();
		m_tcp.Init();
	}
	public void GUI()
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable()
		{
			public void run() 
			{
				group = Login.grpname;
				name = Login.usrname;
				try 
				{
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception useDefault) {}
				frame.setTitle("WeGroup: " + group + "(" + name +")");
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame.getContentPane().add(new Main_page());
				frame.pack();
				frame.setLocationRelativeTo(null);
				//frame.setLocationByPlatform(true);
				//frame.setResizable(false);
				frame.setVisible(true);
			}
		});
	}
	public void setup_ui(String in_name, String in_group)
	{
		name = in_name;
		group = in_group;
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
	
	public static void main (String[] args)
	{
		Main_page main_page = new Main_page();
		main_page.setup_ui("happy", "gilmore");//args[1], args[0]);
	}
}
