package data_types;

import java.util.*;

public class Poll_vote_data extends Base_data 
{
	// Default constructor
	public Poll_vote_data()
	{
		super();
		m_type = Tcp_message_type.Poll_vote;
		
		m_poll_id = new UUID(0,0);
		m_vote = -1;
	}
	
	// Required for Serializable
	private static final long serialVersionUID = 42L;
	
	// ID of the poll
	public UUID m_poll_id;
	
	//name of poll
	public String m_question;
	
	// What poll option the user wants
	public int m_vote;

	// Prints out the contents of the poll vote
	// TODO Test this
	public String toString()
	{
		String rep = super.toString();
		
		rep += "\n  Poll ID: " + m_poll_id.toString();
		rep += "\n  Vote: " + m_vote;
		
		return rep;
	}
}
