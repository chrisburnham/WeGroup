package client;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import data_types.*;
import tcp_bridge.*;

public class Pchat extends JPanel implements  ActionListener
{
	private static String name = Main_page.thetarget(); //will grab name of user from main_page
	private static String target;
	private static JFrame frame = new JFrame("WeGroup");
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JTextArea message = new JTextArea();
	private static JTextArea groupfield = new JTextArea();
	private JButton btnSend = new JButton("Send");
	private JButton btnemote = new JButton("Emote");
	private final JButton btnAttach = new JButton("Attach");
	protected Tcp_client_side m_tcp;
	
	public Pchat() 
	{
		setLayout(new BorderLayout(0, 0));
		Init();
		
		panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{501, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 189, 21, 51, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
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
		gbc_message.gridheight = 2;
		gbc_message.insets = new Insets(0, 0, 0, 5);
		gbc_message.fill = GridBagConstraints.BOTH;
		gbc_message.gridx = 0;
		gbc_message.gridy = 3;
		panel.add(message, gbc_message);
		
		
		GridBagConstraints gbc_btnSend = new GridBagConstraints();
		gbc_btnSend.insets = new Insets(0, 0, 5, 0);
		gbc_btnSend.fill = GridBagConstraints.BOTH;
		gbc_btnSend.gridx = 1;
		gbc_btnSend.gridy = 3;
		panel.add(btnSend, gbc_btnSend);
		btnSend.addActionListener(this);
		
		GridBagConstraints gbc_btnAttach = new GridBagConstraints();
		gbc_btnAttach.gridx = 1;
		gbc_btnAttach.gridy = 4;
		panel.add(btnAttach, gbc_btnAttach);
		btnAttach.addActionListener(this);
		
	}

	public void actionPerformed(ActionEvent evt)
	{
		if (evt.getSource() == btnSend)
		{	
			String data=message.getText().trim(); //read contents of text area  into data
			if(!data.equals("")) //verify their is anything to send
				{
					/*ArrayList<String> send = new ArrayList<String>();
					send.add(name);
					send.add(data);
					*/data = name + ": " + data;
					Message_data message_data = new Message_data();
					message_data.m_sender = name;
					message_data.m_message = data;
					message_data.m_private = true;
					message_data.m_recipients.add(target);
	
					message.setText(""); //clears out the message area	
					data = "\n" + data + "\n";
					groupfield.append(data);
					m_tcp.Send_data(message_data);
				}
		}
		else if (evt.getSource() == btnAttach)
		{
			
		}
		else if (evt.getSource() == btnemote)
		{
			
		}
	}
	public void Init()
	{
		m_tcp = new Tcp_client_side();
		m_tcp.Init();
	}
	public void msgrec(Message_data receive)
	{
		String user = receive.getSender();
		String msg = receive.getMessage();
		String data = user + ": " + msg;
		groupfield.append(data);	
		frame.setTitle(name + "(" + target + ")");
	}

	public String talking_to()
	{
		return target;
	}
	
	public void GUI()
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable()
		{
			public void run() 
			{
				try 
				{
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception useDefault) {}
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame.getContentPane().add(new Pchat());
				frame.pack();
				frame.setLocationByPlatform(true);
				frame.setVisible(true);
			}
		});
	}
	
	public static void main (String[] args)
	{
		
	}
}
