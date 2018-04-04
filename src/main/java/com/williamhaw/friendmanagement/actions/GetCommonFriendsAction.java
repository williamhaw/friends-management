package com.williamhaw.friendmanagement.actions;

import java.util.HashSet;
import java.util.Set;

import com.williamhaw.friendmanagement.persistence.PersistenceException;
import com.williamhaw.friendmanagement.persistence.UserPersistence;
import com.williamhaw.friendmanagement.user.User;

/**
 * Returns the intersection of the two users' friend lists
 * @author williamhaw
 *
 */
public class GetCommonFriendsAction {

	private UserPersistence persistence;
	
	public GetCommonFriendsAction(UserPersistence persistence) {
		this.persistence = persistence;
	}
	
	public Set<String> handle(String firstEmail, String secondEmail){
		try {
		User user1 = persistence.lookup(firstEmail);
		User user2 = persistence.lookup(secondEmail);
		
		if(user1 == null || user2 == null)
			return new HashSet<>();
		
		Set<String> ret = new HashSet<>(user1.getFriends());
		ret.retainAll(user2.getFriends());
		
		return ret;
		
		}catch (PersistenceException e) {
			e.printStackTrace();
			return new HashSet<>();
		}
	}
	
}
