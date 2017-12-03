package server;

import java.util.List;

public class Group_element {

	public String groupName;
	public List<String> users;
	
	public List<Poll_server> storedPolls;
	public List<List_server> storedLists;
	public List<Message_server> storedGM;
	
	
	
	public Group_element(String name) {
		this.groupName = name;
	}
	
// GENERAL GROUP STUFF
	public void addUser(String user) {
		users.add(user);
	}
	
	
	public String getGroupName() {
		return groupName;
	}
	
	/*
	public boolean containsUser(String user) {
		if(users.contains(user)) 
			return true;
		else
			return false;
	}*/
	
	
	public void addGroupMessage(String message, List<String> recipients, String sender) {
		Message_server msg = new Message_server(message, recipients, sender);
		storedGM.add(msg);
	}
	
	
	
	
	
// LIST STUFF
	public void addList(String name) {
		List_server list = new List_server(name);
		storedLists.add(list);
	}
	
	public boolean listExists(String name) {
		for(List_server list : storedLists) {
			if(list.getID() == name) {
				return true;
			}
		}
		return false;
	}
	
	public List_server getList(String name) {
		for(List_server list : storedLists) {
			if(list.getID() == name) {
				return list;
			}
		}
		return null;
	}
	
	
	
	
// POLL STUFF
	public void addPoll(String name, String userID) {
		if(users.contains(userID)) {
			Poll_server poll = new Poll_server(name, userID);
			storedPolls.add(poll);
		}
	}
	
	
	public boolean pollExists(String name) {
		for(Poll_server poll : storedPolls) {
			if (poll.getID() == name) {
				return true;
			}
		}
		return false;
	}
	
	public Poll_server getPoll(String name) {
		for(Poll_server poll : storedPolls) {
			if (poll.getID() == name) {
				return poll;
			}
		}
		return null;
	}
	
	
// MESSAGE STUFF
	public void addMessage(String message, List<String> recip, String sender) {
		if(users.contains(sender)) {
			Message_server msg = new Message_server(message, recip, sender);
			storedGM.add(msg);
		}
	}
	
	public boolean groupMessageExists(String message) {
		for(Message_server mess : storedGM) {
			if(mess.getMessage() == message) {
				return true;
			}
		}
		return false;
	}
	
	public Message_server getMessage(String message) {
		for(Message_server mess : storedGM) {
			if(mess.getMessage() == message){
				return mess;
			}
		}
		return null;
	}
	

	
}
