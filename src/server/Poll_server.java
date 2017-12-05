package server;

import java.util.ArrayList;
import java.util.List;

// NEW -RB
// TO DO: 	
//		
//		3 - Add a timer for ending poll

//	Current Implementation will allow for only one vote per user. Should support a user revoting/changing vote.
//			how to? add a user list<String> field in poll element, and keep the users who voted for that in there
public class Poll_server 
{
		
	String poll_id;
	List<Poll_element> poll_contents;
	List<String> votedUsers;
	String creatorId;
	
	/**
	 * Creates a Poll with a name, and creates an empty content list.
	 * @param name	the id/name/title of the Poll
	 */
	public Poll_server(String name, String createrId) 
	{
		this.poll_id = name;
		this.poll_contents = new ArrayList<Poll_element>();
		this.votedUsers = new ArrayList<String>();
		this.creatorId = createrId;
	}
	
	/**
	 * Adds an string item into the poll option.
	 * 		Will create a poll element to keep track of votes for the item
	 * @param item	the item being added to the poll
	 */
	public void add_item(String item) 
	{
		Poll_element element = new Poll_element(item);
		this.poll_contents.add(element);
	}

	public void remove_item(String item, String user)
	{
		if(user.equals(creatorId))
		{
			this.poll_contents.remove(get_element(item));
		}
	}
	
	public Poll_element get_element(String name) {
		for(Poll_element ele : poll_contents) {
			if(ele.getItem() == name)
				return ele;
		}
		add_item(name);		// if it doesnt exist, add it
		get_element(name);	// recursive call to get the new element
		return null;		// shouldnt ever reach this state.
	}

	
	/**
	 * Casts a vote to the item being voted on
	 * @param item	the item a vote is being added to
	 */
	public void add_vote(String item, String userId) 
	{
		/* Currently does nothing
		 * 
		 * if(checkUserVoted(userId))
		{
			get_element(item).removeVote();
			removeUserVoted(userId);
		}
		*/
		
		for(Poll_element element : poll_contents) 
		{
			
			if(item == element.getItem()) 
			{
				element.addVote();
				addUserVoted(userId);
				break;
			}
		}
	}
	
	
	/**
	 * Gets the id/name/title of the poll
	 * @return	the id/name/title of the poll
	 */
	public String getID() 
	{
		return poll_id;
	}
	
	public String getCreator() {
		return creatorId;
	}
	
	
	
	/**
	 * Gets the list of all the elements of the poll. 
	 * @return	the list of poll elements, as poll_element
	 */
	public List<Poll_element> getContent() 
	{
		return poll_contents;
	}

	public List<String> getOptions(){
		List<String> options = new ArrayList<String>();
		for(Poll_element op : poll_contents) {
			options.add(op.getItem());
		}
		return options;
	}
	
	public List<Integer> getVotes(){
		List<Integer> votes = new ArrayList<Integer>();
		for(Poll_element vt : poll_contents) {
			votes.add(vt.getVotes());
		}
		return votes;
	}



	public void addUserVoted(String user)
	{
		votedUsers.add(user);
	}

	public boolean checkUserVoted(String user)
	{
		return (votedUsers.contains(user));
	}

	public void removeUserVoted(String user)
	{
		votedUsers.remove(new String(user));
	}
}
