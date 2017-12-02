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

		
		switch (type) {
			case Message:	// MESSAGE CASE
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
				
			default:		// OTHER/DEFAULT CASE
				break;
		}
	}
	
	// Class to send TCP
	protected Tcp_server_side m_tcp;
	
	
	
// Riker's Stuff
	
	//TODO: implement
	public void update(String user) {
		// Check if user has messages to be sent. -Trevor
			// Arraylists are handled slightly different than standard arrays.
		for(int i=0; i < storedMessages.size(); i++){
			checkMessage(storedMessages.get(i),user);
		}
		   
		//Need some functionality to update Polls and Lists back to the user -Trevor    
		    	// Worrying about that later -Riker
	}
	
	
	
// THE FOLLOWING IS HANDLING FOR GROUPS
	
	public List<Group_element> storedGroups;
	
	public void addGroup(String group) {
		Group_element newGroup = new Group_element(group);
		storedGroups.add(newGroup);
	}
	
	public void addUserToGroup(String user, Group_element group) {
		group.addUser(user);
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
	public List<Message_server> storedMessages;
	
	/**
	 * Creates a new message type based of the message, target, and if private.  Will be added to stored messages
	 * 		Will be changed if done on client side
	 * @param message	the message to be created
	 * @param target	list of recipients 
	 * @param pvt		tag to check if it is a private message or not
	 */
	public void newMessage(String message, List<String> target, boolean pvt) {
		Message_server msg = new Message_server(message, target, pvt);
		storedMessages.add(msg);
	}
	
	/**
	 * Checks if the message has the user as a recipient
	 * @param messageData	the message being checked
	 * @param user			the user being checked
	 */
	public void checkMessage(Message_server messageData, String user) {
		if(messageData.userAsTarget(user)) {
			messageData.getMessage();
			messageData.isPvt();
			messageData.removeRecip(user);
		}
	}
	
	/**
	 * Removes the message from the stored messages if there are no users left to receive
	 * @param messageData	the message being checked
	 */
	public void checkRecipMessage(Message_server messageData) {
		if(messageData.recipEmpty()) {
			storedMessages.remove(messageData);
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
				Thread.sleep(5000);		// NOT THE BEST WAY TO DO A TIMER LOOP
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
