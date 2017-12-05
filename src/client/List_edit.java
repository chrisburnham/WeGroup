package client;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.event.*;
import data_types.*;

public class List_edit extends JPanel implements  ActionListener, ListSelectionListener
{
	static final long serialVersionUID = 6L;
	JPanel panel;
	int index;
	 DefaultListModel<String> listmod = new DefaultListModel<String>();
	 JScrollPane scroll1 = new JScrollPane();
	 JLabel lblAddItem = new JLabel("add item:");
	 JButton btnAdd = new JButton("add");
	 JTextField titlef = new JTextField();
	 JLabel lblTitle = new JLabel("Title:");
	 JList<String> list = new JList<String>(listmod);
	JButton btnCancel = new JButton("Cancel");
	 JButton btnAccept = new JButton("Accept");
	 JLabel lbldeleteitem = new JLabel("Delete item:");
	 JTextField deletef = new JTextField();
	 JButton btnDelete = new JButton("Delete");
	 JTextField additemf = new JTextField();
	ArrayList<String> list2 = new ArrayList<String>();
	JFrame frame = new JFrame("Edit list");
	
	public List_edit() 
	{
		additemf.setColumns(10);
		titlef.setColumns(10);
		setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{91, 212, 61, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 189, 0, 21, 51, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitle.anchor = GridBagConstraints.EAST;
		gbc_lblTitle.gridx = 0;
		gbc_lblTitle.gridy = 1;
		panel.add(lblTitle, gbc_lblTitle);
		
		GridBagConstraints gbc_titlef = new GridBagConstraints();
		gbc_titlef.insets = new Insets(0, 0, 5, 5);
		gbc_titlef.fill = GridBagConstraints.HORIZONTAL;
		gbc_titlef.gridx = 1;
		gbc_titlef.gridy = 1;
		panel.add(titlef, gbc_titlef);
		
		
		GridBagConstraints gbc_Groupfield = new GridBagConstraints();
		gbc_Groupfield.gridwidth = 3;
		gbc_Groupfield.gridheight = 2;
		gbc_Groupfield.insets = new Insets(0, 0, 5, 0);
		gbc_Groupfield.fill = GridBagConstraints.BOTH;
		gbc_Groupfield.gridx = 0;
		gbc_Groupfield.gridy = 2;
		panel.add(scroll1, gbc_Groupfield);
		scroll1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		scroll1.setViewportView(list);
		
		GridBagConstraints gbc_lbldeleteitem = new GridBagConstraints();
		gbc_lbldeleteitem.anchor = GridBagConstraints.EAST;
		gbc_lbldeleteitem.insets = new Insets(0, 0, 5, 5);
		gbc_lbldeleteitem.gridx = 0;
		gbc_lbldeleteitem.gridy = 4;
		panel.add(lbldeleteitem, gbc_lbldeleteitem);
		
		GridBagConstraints gbc_deletef = new GridBagConstraints();
		gbc_deletef.insets = new Insets(0, 0, 5, 5);
		gbc_deletef.fill = GridBagConstraints.HORIZONTAL;
		gbc_deletef.gridx = 1;
		gbc_deletef.gridy = 4;
		deletef.setColumns(10);
		panel.add(deletef, gbc_deletef);
		
		GridBagConstraints gbc_btnDelete = new GridBagConstraints();
		gbc_btnDelete.insets = new Insets(0, 0, 5, 0);
		gbc_btnDelete.gridx = 2;
		gbc_btnDelete.gridy = 4;
		panel.add(btnDelete, gbc_btnDelete);
		btnDelete.addActionListener(this);
		
		GridBagConstraints gbc_lblAddItem = new GridBagConstraints();
		gbc_lblAddItem.anchor = GridBagConstraints.EAST;
		gbc_lblAddItem.insets = new Insets(0, 0, 5, 5);
		gbc_lblAddItem.gridx = 0;
		gbc_lblAddItem.gridy = 5;
		panel.add(lblAddItem, gbc_lblAddItem);
		
		GridBagConstraints gbc_additemf = new GridBagConstraints();
		gbc_additemf.insets = new Insets(0, 0, 5, 5);
		gbc_additemf.fill = GridBagConstraints.HORIZONTAL;
		gbc_additemf.gridx = 1;
		gbc_additemf.gridy = 5;
		panel.add(additemf, gbc_additemf);
		
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.insets = new Insets(0, 0, 5, 0);
		gbc_btnAdd.gridx = 2;
		gbc_btnAdd.gridy = 5;
		panel.add(btnAdd, gbc_btnAdd);
		btnAdd.addActionListener(this);
		
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancel.gridx = 0;
		gbc_btnCancel.gridy = 6;
		panel.add(btnCancel, gbc_btnCancel);
		btnCancel.addActionListener(this);
		
		GridBagConstraints gbc_btnAccept = new GridBagConstraints();
		gbc_btnAccept.gridx = 2;
		gbc_btnAccept.gridy = 6;
		btnAccept.addActionListener(this);
		panel.add(btnAccept, gbc_btnAccept);
		
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedIndex(0);
		list.setVisibleRowCount(10);
		list.addListSelectionListener(this);
		
	}
	public void actionPerformed(ActionEvent evt)
	{
		if (evt.getSource() == btnAdd)
		{
			if(additemf != null) //verifies the additemf text field is not empty
			{
				listmod.addElement(additemf.getText());
				list2.add(additemf.getText());
				
				//make the new list item show on UI
				int index = list2.size() - 1;
				list.setSelectedIndex(index);
				list.ensureIndexIsVisible(index);
				
				//empty the text field
				additemf.requestFocusInWindow(); 
				additemf.setText("");
			}	
		}
		else if (evt.getSource() == btnDelete)
		{
			
			listmod.removeElementAt(index);
			deletef.requestFocusInWindow();
			deletef.setText("");
		}
		else if (evt.getSource() == btnAccept)
		{	
			if(!titlef.getText().equals(""))
			{
				list2.add(titlef.getText());
				//send list2 to server for update
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Missing a title");
			}
		}
		else if (evt.getSource() == btnCancel)
		{
			frame.dispose();
		}
	}
	public void valueChanged(ListSelectionEvent e)
	{
		//sets the value to the edit field
		index = list.getSelectedIndex();
		deletef.setText(list.getSelectedValue());
	}
	
	public void recdata(List_data data)
	{
		int index = data.m_contents.size(); 
		titlef.setText(data.m_title);
		data.m_contents.remove(index);
		for(int i=0; i < index; i++)
		{
			listmod.addElement(data.m_contents.get(i));
			list2.add(data.m_contents.get(i));
			list.setSelectedIndex(i);
			list.ensureIndexIsVisible(i);
		}
	}
	
	public void send_data(List_data data)
	{
		data.m_title=titlef.getText();
		data.m_contents.addAll(list2);
		
	}
	public void GUI()
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable()
		{
			public void run() 
			{
				try 
				{
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //attempt to use system look for UI
				} catch (Exception useDefault) {}
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame.getContentPane().add(new List_edit());
				frame.pack();
				frame.setLocationByPlatform(true);
				frame.setVisible(true);
			}
		});	
	}
}
