package data_types;


public class Base_data implements java.io.Serializable 
{
	// Default constructor
	public Base_data()
	{
		m_type = Tcp_message_type.Other;
		m_user_id = "";
		m_group_id = "";
	}

	// Required for Serializable
	private static final long serialVersionUID = 1L;
	
	// Type of message that this is
	public Tcp_message_type m_type;

	// Who is this message from/ going too
	// This field is used to communicate with the server
	// UI can ignore this field
	public transient String m_user_id;
	
	// What group are we in
	// This field is used to communicate with the server
	// UI can ignore this field
	public transient String m_group_id;
	
	// Prints out the contents of the message
	public String toString()
	{
		String rep = super.toString();
		
		rep += ":\n  Type: " + m_type.toString();
		rep += " \n  id: " + m_user_id;
		
		return rep;
	}
	
	// Returns the type of the data -Riker
	public Tcp_message_type getm_Type() {
		return m_type;
	}
	
	public String getm_User_Id() {
		return m_user_id;
	}
	
	public String getm_Group_Id() {
		return m_group_id;
	}
	
	public void setm_Type(Tcp_message_type type) {
		m_type = type;
	}
	
	public void setm_User_Id(String user_id) {
		m_user_id = user_id;
	}
	
	public void setm_Group_Id(String group_id) {
		m_group_id = group_id;
	}
	
}
