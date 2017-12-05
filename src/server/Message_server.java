package server;

import java.util.List;

public class Message_server 
{

	//String msg_id;
	String message;
	String sender;
	List<String> recipients;
	boolean pvt;
	
	/**
	 * Creates a message, with recipients and a flag for checking if private
	 * @param msg 	the message to be sent
	 * @param target	the recipients for the message. if 1 user, send in a list still
	 * @param pvt	privateMessage flag. If True, the message is intended for that 1 user.
	 */
	public Message_server(String msg, List<String> target, String sender) {
		this.message = msg;
		this.recipients = target;
		this.sender = sender;
		this.pvt = false;
	}
	
	/**
	 * Checks if the message is private or not
	 * @return	True if the message is private
	 */
	/*
	public boolean isPvt() {
		return pvt;
	}*/
	
	
	public String getSender() {
		return sender;
	}
	
	
	/**
	 * removes a user from the recipient list
	 * @param target	the target being removed from the list
	 */
	public void removeRecip(String target) {
		recipients.remove(target);
	}
	
	/*
	 * sets the value of private to true or false
	 */
	public void setPvt(Boolean isPvt) {
		pvt = isPvt;
	}
	
	/**
	 * returns the message as a string
	 * @return	The message
	 */
	public String getMessage() {
		return message;
	}
	
	
	public boolean isPvtRecip(String recip) {
		if(recipients.get(0) == recip) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * Checks if the list of recipients has the user
	 * @param user	the user being checked
	 * @return	true if user is in the list, false if not
	 */
	public boolean userAsTarget(String user) {
		if(recipients.contains(user))
			return true;
		else
			return false;
	}
	
	/**
	 * Checks if the recipient list is empty
	 * @return	true if empty, false if not.
	 */
	public boolean recipEmpty() {
		if(recipients.isEmpty()) 
			return true;
		else
			return false;
	}
	
	
	public boolean userExists(String name) {
		for (String usr : recipients) {
			if(usr == name) {
				return true;
			}
		}
		return false;
	}
	
}

