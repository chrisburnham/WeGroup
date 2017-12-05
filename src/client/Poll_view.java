package client;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import data_types.Poll_data;

public class Poll_view extends JPanel implements  ActionListener
{
	private static final long serialVersionUID = 7L;
	 JPanel panel;
	 JTextField additemf = new JTextField();
	 JButton btnCancel = new JButton("Cancel");
	JFrame frame = new JFrame("View Poll");
	JTextArea options = new JTextArea();
	JTextArea result = new JTextArea();
	JLabel lblResult = new JLabel("Result");
	JLabel question = new JLabel("question");
	
	public Poll_view() 
	{
		setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{210, 181, 0};
		gbl_panel.rowHeights = new int[]{60, 189, 51, 0};
		gbl_panel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		additemf.setColumns(10);
		
		GridBagConstraints gbc_question = new GridBagConstraints();
		gbc_question.insets = new Insets(0, 0, 5, 5);
		gbc_question.gridx = 0;
		gbc_question.gridy = 0;
		panel.add(question, gbc_question);
		
		GridBagConstraints gbc_lblResult = new GridBagConstraints();
		gbc_lblResult.insets = new Insets(0, 0, 5, 0);
		gbc_lblResult.gridx = 1;
		gbc_lblResult.gridy = 0;
		panel.add(lblResult, gbc_lblResult);
		
		GridBagConstraints gbc_options = new GridBagConstraints();
		gbc_options.insets = new Insets(0, 0, 5, 5);
		gbc_options.fill = GridBagConstraints.BOTH;
		gbc_options.gridx = 0;
		gbc_options.gridy = 1;
		panel.add(options, gbc_options);
		
		GridBagConstraints gbc_result = new GridBagConstraints();
		gbc_result.insets = new Insets(0, 0, 5, 0);
		gbc_result.fill = GridBagConstraints.BOTH;
		gbc_result.gridx = 1;
		gbc_result.gridy = 1;
		panel.add(result, gbc_result);
		
		
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancel.anchor = GridBagConstraints.WEST;
		gbc_btnCancel.gridx = 0;
		gbc_btnCancel.gridy = 2;
		panel.add(btnCancel, gbc_btnCancel);
		btnCancel.addActionListener(this);
	}
	public void actionPerformed(ActionEvent evt)
	{
		if (evt.getSource() == btnCancel)
		{	
			frame.dispose();
		}
	}
	public void pollres(Poll_data data)
	{
		for(int i=0; i < data.m_poll_options.size(); i++)
		{
			options.append(data.m_poll_options.get(i) + "\n");
			result.append(data.m_poll_votes.get(i) + "\n");
			question = new JLabel(data.m_poll_question);
		}
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
				frame.getContentPane().add(new Poll_view());
				frame.pack();
				frame.setLocationByPlatform(true);
				frame.setVisible(true);
			}
		});
	}

}
