package server;

import tcp_bridge.Tcp_server_side;

import java.util.List;
import java.util.Timer;

import data_types.*;

public class Database_manager 
{
	
	// Initialize this class
	
	
	public void Init()
	{
		m_tcp = new Tcp_server_side();
		m_tcp.Init();
		m_tcp.Register_reciver(this);
	}
	
	
	// Data received from TCP
	public void Data_received(Base_data data)
	{
		//enum type = data.Tcp_message_type(); //this line
		Tcp_message_type type = data.getm_Type();
		String user = data.getm_User_Id();
		String groupName = data.getm_Group_Id();
		
		// get the actual groupType based off string being passed in.
		Group_element group = null;			// ONLY NULL TEMPORARY
		for (Group_element name : storedGroups) {
			if (name.getGroupName() == groupName) {
				group = name;
			}
		}
		
		if(group == null) {
			addGroup(groupName);
		}
		
		switch (type) {
			case Message:	// MESSAGE CASE
				Message_data messData;
				if(data instanceof Message_data) {
					messData = (Message_data)data;
					boolean pvtMsg = messData.getPrivate();
					String sender = messData.getSender();
					String message = messData.getMessage();
					List<String> recip = messData.getRecipients();
					
					for(String usr : recip) {
						
					}
					
					if(pvtMsg) {
						//String message, List<String> target, String user
						addPrivateMessage(message, recip, sender);
					}
					else if(!pvtMsg) {
						manageGroupMessage(message, recip, sender, group);
					}
				}
				
				
				break;
				
			case Poll:		// POLL CASE
				Poll_data pollData;
				if(data instanceof Poll_data) {
					pollData = (Poll_data)data;
					String question = pollData.getm_Poll_Question();
					String creator = pollData.getm_Poll_Creator();
					List<String> options = pollData.getm_Poll_Options();
					List<Integer> votes = pollData.getm_Poll_Votes();
					
					managePoll(question, options, votes, creator, group, user);
				}
				break;
				
			case List:		// LIST CASE
				List_data listData;
				if(data instanceof List_data) {
					listData = (List_data)data;
					String title = listData.getm_List_Title();
					List<String> contents = listData.getm_List_Contents();
					List<String> users = listData.getm_List_Users();
					
					manageList(title, contents, users, group, user);
				}
				break;
				
			case Update:
				update(user, group);
				break;
				
			default:		// OTHER/DEFAULT CASE
				break;
		}
	}
	
	// Class to send TCP
	protected Tcp_server_side m_tcp;
	
	
	
// Riker's Stuff
	
	
	
	public void sendGroupMessage(Message_server messageData, String user, Group_element group) {

		Message_data messData = new Message_data();
		messData.setm_Group_Id(group.getGroupName());
		messData.setm_User_Id(user);
		messData.setPrivate(false);
		messData.setMessage(messageData.getMessage());
		messData.setSender(messageData.getSender());
		
		m_tcp.Send_data(messData);
	}
	
	public void sendPvtMessage(Message_server messageData, String user, Group_element group) {
		
		Message_data messData = new Message_data();
		messData.setm_Group_Id(group.getGroupName());
		messData.setm_User_Id(user);
		messData.setPrivate(true);
		messData.setMessage(messageData.getMessage());
		messData.setSender(messageData.getSender());
		
		m_tcp.Send_data(messData);
	}
	
	
	
	public void sendList(List_server listServData, String user, Group_element group) {
		
		List_data listData = new List_data();
		listData.setm_Group_Id(group.getGroupName());		//Base_data
		listData.setm_User_Id(user);						//Base_data
		
		listData.m_title = listServData.getID();				//List_data
		listData.m_contents = listServData.getContent();			//List_data
		
		m_tcp.Send_data(listData);
		
	}
	
	
	public void sendPoll(Poll_server pollServData, String user, Group_element group) {
		
		Poll_data pollData = new Poll_data();
		pollData.setm_Group_Id(group.getGroupName());		//Base_data
		pollData.setm_User_Id(user);						//Base_data
		
		pollData.setPoll_Question(pollServData.getID());
		pollData.setPoll_Creator(pollServData.getCreator());
		pollData.setPoll_Options(pollServData.getOptions());
		pollData.setPoll_Votes(pollServData.getVotes());
		
		m_tcp.Send_data(pollData);
			
	}
	
	
	
	public void update(String user, Group_element group) {
		for(Message_server messageData : group.storedGM) {			// Group Message
			if(messageData.userAsTarget(user)) {					// If the user is a target...
				sendGroupMessage(messageData, user, group);			// Send the message
			}
		}
		
		for(Message_server pvtMessData : storedPrivateMessages) {	// Private Message
			if(pvtMessData.userAsTarget(user)) {					// If the user is the target...
				sendPvtMessage(pvtMessData, user, group);			// Send the message
			}
		}
		
		
	}
	
	
	
	
	
	
	
// THE FOLLOWING IS HANDLING FOR GROUPS
	
	public List<Group_element> storedGroups;
	
	public void addGroup(String group) {
		Group_element newGroup = new Group_element(group);
		storedGroups.add(newGroup);
	}
	
	public void addUserToGroup(String user, Group_element group) {
		if(!group.users.contains(user)) {
			group.addUser(user);
		}
	}
	

	
	
	
	
	
// THE FOLLOWING IS HANDLING FOR POLLS

	/**
	 * Creates a new Poll with name and adds to the database			may be unused
	 * @param name	the name for the poll
	 */
	public void newPoll(String name, String userID, Group_element group) {
		group.addPoll(name, userID);
	}
	
	/**
	 * 		COULD USE A LOT OF OPTIMIZATION, but would require a rewrite of poll_server
	 * 
	 * 	Will check a poll, and make sure it is up to date.  Will create a new poll if need be. 
	 * 	for now, a UUID is not implemented. 
	 * @param question	The question/title of the poll
	 * @param creator	The creator of the poll (currently unused)
	 * @param options	A list of all the options
	 * @param votes		A list of the votes... votes[i] matches with options[i]
	 * @param groupName	The name of the group
	 * @param user		The name of the user acting (current unused. would be used if creator was used)
	 */
	public void managePoll(String question, List<String> options, List<Integer> votes, String creator, Group_element groupName, String user) {
		if(groupName.pollExists(question)) {
			Poll_server poll = groupName.getPoll(question);
			int votesIndex = 0;		// used for getting index of List, Votes.
			for(String op : options) {
				Poll_element ele = poll.get_element(op);
				
				for(int j = 0; j <= votes.get(votesIndex); j++)	// add a vote for however big votes says
					ele.addVote();
				votesIndex++;		// increment the index of votes
			}
		}
		else {
			groupName.addPoll(question, user);
			managePoll(question, options, votes, creator, groupName, user);	// recursive call to manage with new poll;
		}
	}
	
	
	
// THE FOLLOWING IS HANDLING FOR LISTS
	
	/**
	 * Creates a new list with name and adds to the Group database			may be unused
	 * @param name	the name for the list
	 */
	public void newList(String name, Group_element group) {
		group.addList(name);
	}
	
	/**
	 * 	Will check a list, and make sure its up to date.  Will create a new list if need be
	 * 	For now, UUID is not implemented.
	 * @param title		The title of the list
	 * @param contents	The contents to the list
	 * @param users		The users able to view the list
	 * @param groupName	The name of the group
	 * @param user		The user acting (unused. not needed if client side handles properly. Could put in a check that user is in list.users)
	 */
	public void manageList(String title, List<String> contents, List<String> users, Group_element groupName, String user) {
		if(groupName.listExists(title)){
			List_server list = groupName.getList(title);
			for(String usr : users) {
				if(!list.userExists(usr)) {
					list.add_user(usr);
				}
			}
			for(String con : contents) {
				if(!list.elementExists(con)) {
					list.add_item(con);
				}
			}
		}
		else {
			groupName.addList(title);
			manageList(title, contents, users, groupName, user);
		}
	}
	
	
	
	
// THE FOLLOWING IS HANDLING FOR MESSAGES
	public List<Message_server> storedPrivateMessages;
	
	/**
	 * Creates a new message type based of the message, target, and if private.  Will be added to stored messages
	 * 		Will be changed if done on client side
	 * @param message	the message to be created
	 * @param target	list of recipients 
	 * @param pvt		tag to check if it is a private message or not
	 */
	public void addPrivateMessage(String message, List<String> target, String user) {
		Message_server msg = new Message_server(message, target, user);
		msg.setPvt(true);
		storedPrivateMessages.add(msg);
	}
	
	
	public Message_server getPrivateMessage(Message_server msg, String recip, String sender) {
		for(Message_server mess : storedPrivateMessages) {
			if(mess.isPvtRecip(recip)) {
				storedPrivateMessages.remove(mess);
				return mess;
			}
		}
		return null;
	}
	

	public void manageGroupMessage(String message, List<String> recip, String sender, Group_element groupName) {
		if(!groupName.groupMessageExists(message)) {
			groupName.addMessage(message, recip, sender);	
		}
	}
	
	
	
	
	// Testing for update on a timer.
	public static void printsomething() {
		System.out.println("hello");
	}
	
	public static void main(String[] args) {
		while (true) {
			printsomething();
			try {
				
				// For every group, and every user IN group, update that user.
				//for(Group_element grp : storedGroups) {
					//for(String user : grp.users) {
						//update(user, grp);
					//}
				//}
				
				
				Thread.sleep(5000);		// NOT THE BEST WAY TO DO A TIMER LOOP
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
