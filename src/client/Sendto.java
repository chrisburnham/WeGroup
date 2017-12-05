package client;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import data_types.List_data;
import tcp_bridge.Tcp_client_side;

import java.util.*;

public class Sendto extends JPanel implements  ActionListener
{
	private static final long serialVersionUID = 4L;
	private JPanel panel;
	private JScrollPane scroll1 = new JScrollPane();
	private JScrollPane scroll2 = new JScrollPane();
	private final JTextField additemf = new JTextField();
	private DefaultListModel<String> listmod = new DefaultListModel<String>();
	private final JLabel lblAddItem = new JLabel("add members:");
	private final JButton btnAdd = new JButton("add");
	private final JLabel lblmembers = new JLabel("Send to:");
	private final JList<String> list = new JList<String>(listmod);
	private final JButton btnCancel = new JButton("Cancel");
	private final JButton btnOk = new JButton("OK");
	ArrayList<String> list2 = new ArrayList<String>();
	ArrayList<String> list3 = new ArrayList<String>();
	JFrame frame = new JFrame("Send to");
	protected Tcp_client_side m_tcp;
	
	public Sendto() 
	{
		Init();
		setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{91, 212, 61, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 189, 21, 51, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		GridBagConstraints gbc_lblmembers = new GridBagConstraints();
		gbc_lblmembers.insets = new Insets(0, 0, 5, 5);
		gbc_lblmembers.anchor = GridBagConstraints.WEST;
		gbc_lblmembers.gridx = 0;
		gbc_lblmembers.gridy = 0;
		panel.add(lblmembers, gbc_lblmembers);
		
		
		GridBagConstraints gbc_Groupfield = new GridBagConstraints();
		gbc_Groupfield.gridwidth = 3;
		gbc_Groupfield.gridheight = 2;
		gbc_Groupfield.insets = new Insets(0, 0, 5, 0);
		gbc_Groupfield.fill = GridBagConstraints.BOTH;
		gbc_Groupfield.gridx = 0;
		gbc_Groupfield.gridy = 1;
		panel.add(scroll1, gbc_Groupfield);
		scroll1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		scroll1.setViewportView(list);
		GridBagConstraints gbc_lblAddItem = new GridBagConstraints();
		gbc_lblAddItem.anchor = GridBagConstraints.EAST;
		gbc_lblAddItem.insets = new Insets(0, 0, 5, 5);
		gbc_lblAddItem.gridx = 0;
		gbc_lblAddItem.gridy = 3;
		panel.add(lblAddItem, gbc_lblAddItem);
		additemf.setColumns(10);
		
		
		GridBagConstraints gbc_message = new GridBagConstraints();
		gbc_message.fill = GridBagConstraints.HORIZONTAL;
		gbc_message.insets = new Insets(0, 0, 5, 5);
		gbc_message.gridx = 1;
		gbc_message.gridy = 3;
		panel.add(scroll2, gbc_message);
		scroll2.setViewportView(additemf);
		
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.insets = new Insets(0, 0, 5, 0);
		gbc_btnAdd.gridx = 2;
		gbc_btnAdd.gridy = 3;
		panel.add(btnAdd, gbc_btnAdd);
		btnAdd.addActionListener(this);
		
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancel.gridx = 0;
		gbc_btnCancel.gridy = 4;
		panel.add(btnCancel, gbc_btnCancel);
		btnCancel.addActionListener(this);
		
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.gridx = 2;
		gbc_btnOk.gridy = 4;
		panel.add(btnOk, gbc_btnOk);
		
	}
	public void actionPerformed(ActionEvent evt)
	{
		if (evt.getSource() == btnOk)
		{	
			List_data l_data = new List_data();
			l_data.m_users.addAll(list2);
			int i = list3.size() - 1;
			l_data.m_title = list3.get(i);
			list3.remove(i);
			l_data.m_contents.addAll(list3);
			
			m_tcp.Send_data(l_data);
		}
		else if(evt.getSource() == btnAdd)
		{
			if(additemf != null)
			{
				listmod.addElement(additemf.getText());
				list2.add(additemf.getText());
				
				//ensure item is visible on UI
				int index = list2.size();
				list.setSelectedIndex(index);
				list.ensureIndexIsVisible(index);
				
				//remove entry from text field
				additemf.requestFocusInWindow();
				additemf.setText("");
			}
		}
	}
	public void Init()
	{
		m_tcp = new Tcp_client_side();
		m_tcp.Init();
	}
	public void GUI(ArrayList<String> data)
	{
		list3 = data;
		javax.swing.SwingUtilities.invokeLater(new Runnable()
		{
			public void run() 
			{
				try 
				{
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //attempt to use system look for UI
				} catch (Exception useDefault) {}
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame.getContentPane().add(new Sendto());
				frame.pack();
				frame.setLocationByPlatform(true);
				frame.setVisible(true);
			}
		});
		
	}
}
