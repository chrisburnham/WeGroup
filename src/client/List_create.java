package client;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

public class List_create extends JPanel implements  ActionListener, ListSelectionListener
{
	private static final long serialVersionUID = 5L;
	private JPanel panel;
	private DefaultListModel<String> listmod = new DefaultListModel<String>();
	private JScrollPane scroll1 = new JScrollPane();
	private JScrollPane scroll2 = new JScrollPane();
	private final JTextField additemf = new JTextField();
	private final JLabel lblAddItem = new JLabel("add item:");
	private final JButton btnAdd = new JButton("add");
	private final JTextField titlef = new JTextField();
	private final JLabel lblTitle = new JLabel("Title:");
	private final JList<String> list = new JList<String>(listmod);
	private final JButton btnCancel = new JButton("Cancel");
	private final JButton btnCreate = new JButton("Create");
	ArrayList<String> list2 = new ArrayList<String>();
	
	public List_create() 
	{
		titlef.setColumns(10);
		
		panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{91, 212, 61, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 189, 21, 51, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
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
		GridBagConstraints gbc_lblAddItem = new GridBagConstraints();
		gbc_lblAddItem.anchor = GridBagConstraints.EAST;
		gbc_lblAddItem.insets = new Insets(0, 0, 5, 5);
		gbc_lblAddItem.gridx = 0;
		gbc_lblAddItem.gridy = 4;
		panel.add(lblAddItem, gbc_lblAddItem);
		additemf.setColumns(10);
		
		
		GridBagConstraints gbc_message = new GridBagConstraints();
		gbc_message.fill = GridBagConstraints.HORIZONTAL;
		gbc_message.insets = new Insets(0, 0, 5, 5);
		gbc_message.gridx = 1;
		gbc_message.gridy = 4;
		panel.add(scroll2, gbc_message);
		scroll2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		scroll2.setViewportView(additemf);
		
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.insets = new Insets(0, 0, 5, 0);
		gbc_btnAdd.gridx = 2;
		gbc_btnAdd.gridy = 4;
		panel.add(btnAdd, gbc_btnAdd);
		btnAdd.addActionListener(this);
		
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancel.gridx = 0;
		gbc_btnCancel.gridy = 5;
		panel.add(btnCancel, gbc_btnCancel);
		btnCancel.addActionListener(this);
		
		GridBagConstraints gbc_btnCreate = new GridBagConstraints();
		gbc_btnCreate.gridx = 2;
		gbc_btnCreate.gridy = 5;
		panel.add(btnCreate, gbc_btnCreate);
		btnCreate.addActionListener(this);
		
		//setup defaults for Jlist behavior
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedIndex(0);
		list.setVisibleRowCount(10);
		list.addListSelectionListener(this);
		
	}
	public void actionPerformed(ActionEvent evt)
	{
		if (evt.getSource() == btnAdd)
		{	
			if(additemf != null)
			{
				listmod.addElement(additemf.getText());
				list2.add(additemf.getText());
				
				//ensure item is visible on UI
				int index = list2.size() - 1;
				list.setSelectedIndex(index);
				list.ensureIndexIsVisible(index);
				
				//remove entry from text field
				additemf.requestFocusInWindow();
				additemf.setText("");
			}	
		}
		else if(evt.getSource() == btnCreate)
		{
			if(!titlef.getText().equals(""))
			{
				list2.add(titlef.getText());
				Sendto.main(list2);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Missing a title");
			}
		}
		else if(evt.getSource() == btnCancel)
		{
			System.exit(0);
		}
	}
	public void valueChanged(ListSelectionEvent e)
	{
		//this method had nothing to do, but is required
	}

	private static void GUI()
	{
		JFrame frame = new JFrame("List Creation");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new List_create());
		frame.pack();
		frame.setLocationByPlatform(true);
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
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //attempt to use system look for UI
				} catch (Exception useDefault) {}
				GUI();
			}
		});
	}
}
