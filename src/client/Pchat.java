package client;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
//import data_types.Message_data;

public class Pchat extends JPanel implements  ActionListener
{
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	String name;
	private JTextArea message = new JTextArea();
	private static JTextArea groupfield = new JTextArea();
	private JButton btnSend = new JButton("Send");
	private JButton btnemote = new JButton("Emote");
	
	public Pchat() 
	{
		setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{501, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 189, 21, 51, 0};
		gbl_panel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		
		GridBagConstraints gbc_Groupfield = new GridBagConstraints();
		gbc_Groupfield.gridheight = 2;
		gbc_Groupfield.gridwidth = 2;
		gbc_Groupfield.insets = new Insets(0, 0, 5, 0);
		gbc_Groupfield.fill = GridBagConstraints.BOTH;
		gbc_Groupfield.gridx = 0;
		gbc_Groupfield.gridy = 0;
		panel.add(groupfield, gbc_Groupfield);
		groupfield.setEditable(false);
		
		
		GridBagConstraints gbc_btnemote = new GridBagConstraints();
		gbc_btnemote.insets = new Insets(0, 0, 5, 0);
		gbc_btnemote.gridx = 1;
		gbc_btnemote.gridy = 2;
		panel.add(btnemote, gbc_btnemote);
		
		
		GridBagConstraints gbc_message = new GridBagConstraints();
		gbc_message.insets = new Insets(0, 0, 0, 5);
		gbc_message.fill = GridBagConstraints.BOTH;
		gbc_message.gridx = 0;
		gbc_message.gridy = 3;
		panel.add(message, gbc_message);
		
		
		GridBagConstraints gbc_btnSend = new GridBagConstraints();
		gbc_btnSend.fill = GridBagConstraints.BOTH;
		gbc_btnSend.gridx = 1;
		gbc_btnSend.gridy = 3;
		panel.add(btnSend, gbc_btnSend);
		btnSend.addActionListener(this);
		
	}
	public void actionPerformed(ActionEvent evt)
	{
		if (evt.getSource() == btnSend)
		{	
			String data=message.getText().trim(); //read contents of text area  into data
			if(!data.equals("")) //verify their is anything to send
				{
					name = "john";
					ArrayList<String> send = new ArrayList<String>();
					send.add(name);
					send.add(data);
					data = name + ": " + data;
					message.setText(""); //clears out the message area	
					data = "\n" + data + "\n";
					groupfield.append(data);
					//test Gatherer.pchatmsg(send); //sends the data to the class that handles sending it off the tcp_client	
				}
		}
	}
	
	public static void msgrec(ArrayList<String> msg)
	{
		String data = msg.get(0) + ": " + msg.get(1);
		groupfield.append(data);	
	}
	
	private static void GUI()
	{
		JFrame frame = new JFrame("WeGroup *group*");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new Pchat());
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
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception useDefault) {}
				GUI();
			}
		});
	}
}
