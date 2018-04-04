package com.williamhaw.friendmanagement.actions;

import java.util.HashSet;
import java.util.Set;

import com.williamhaw.friendmanagement.persistence.PersistenceException;
import com.williamhaw.friendmanagement.persistence.UserPersistence;
import com.williamhaw.friendmanagement.user.User;

/**
 * Gets subscribers according to the three specified conditions 
 * and filters out those that have blocked updates
 * @author williamhaw
 *
 */
public class GetRecipientsAction {

	private UserPersistence persistence;

	public GetRecipientsAction(UserPersistence persistence) {
		this.persistence = persistence;
	};
	
	public Set<String> handle(String sender, String text){
		try {
			Set<String> ret = new HashSet<>();
			
			User senderUser = persistence.lookup(sender);
			
			if(senderUser == null)
				return new HashSet<>();
			
			//has friend connection
			ret.addAll(senderUser.getFriends());
			
			//has subscribed to updates
			ret.addAll(senderUser.getSubscribers());
			
			//has been mentioned in text
			String[] tokens = text.split("\\s+");
			for (String token : tokens) {
				if(token.contains("@"))
					ret.add(token);
			}
			
			//build Set of users that have blocked updates from sender
			Set<String> blocked = new HashSet<>();
			for (String recipient : ret) {
				User checkIfBlocked = persistence.lookup(recipient);
				if(checkIfBlocked != null && checkIfBlocked.getBlocked().contains(senderUser.getEmail()))
					blocked.add(recipient);
			}
			//filter out those that have blocked sender
			ret.removeAll(blocked);
			
			return ret;
			
		} catch (PersistenceException e) {
			e.printStackTrace();
			return new HashSet<>();
		}
	}
	
}
