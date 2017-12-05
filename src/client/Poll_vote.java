package client;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import data_types.*;
import tcp_bridge.*;

public class Poll_vote extends JPanel implements  ActionListener
{
	private static final long serialVersionUID = 4L;
	protected Tcp_client_side m_tcp;
	private JPanel panel;
	 JLabel lblTitle = new JLabel("*Poll Title*");
	 JButton btnCancel = new JButton("Cancel");
	 JButton btnVote = new JButton("Vote");
	JFrame frame = new JFrame("Poll Vote");
	private DefaultComboBoxModel<String> boxmod = new DefaultComboBoxModel<String>();
	JComboBox<String> comboBox = new JComboBox<String>(boxmod);
	
	public Poll_vote() 
	{
		Init();
		setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{91, 140, 61, 0};
		gbl_panel.rowHeights = new int[]{0, 47, 51, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitle.gridx = 1;
		gbc_lblTitle.gridy = 0;
		panel.add(lblTitle, gbc_lblTitle);
		
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 1;
		panel.add(comboBox, gbc_comboBox);
		
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancel.gridx = 0;
		gbc_btnCancel.gridy = 2;
		panel.add(btnCancel, gbc_btnCancel);
		
		GridBagConstraints gbc_btnVote = new GridBagConstraints();
		gbc_btnVote.gridx = 2;
		gbc_btnVote.gridy = 2;
		panel.add(btnVote, gbc_btnVote);
		
	}
	public void actionPerformed(ActionEvent evt)
	{
		if (evt.getSource() == btnVote)
		{	
			if(boxmod.getSelectedItem() != null)
			{
				Poll_vote_data p_vote = new Poll_vote_data();
				//String vote = boxmod.getSelectedItem().toString();
				p_vote.m_vote = (boxmod.getIndexOf(boxmod.getSelectedItem()));
				p_vote.m_question = lblTitle.toString();
				
				m_tcp.Send_data(p_vote);
			}
		}
		else if (evt.getSource() == btnCancel)
		{
			frame.dispose();
		}
	}
	
	public void pollvote(Poll_data data)
	{
		for(int i=0; i<data.m_poll_options.size(); i++)
		{
			lblTitle = new JLabel(data.m_poll_question);
			boxmod.addElement(data.m_poll_options.get(i));
		}
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
				try 
				{
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //attempt to use system look for UI
				} catch (Exception useDefault) {}
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame.getContentPane().add(new Poll_vote());
				frame.pack();
				frame.setLocationByPlatform(true);
				frame.setVisible(true);
			}
		});
	}
}
