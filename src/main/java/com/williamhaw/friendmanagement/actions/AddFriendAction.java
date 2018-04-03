package com.williamhaw.friendmanagement.actions;

import java.util.Collection;

import com.williamhaw.friendmanagement.persistence.PersistenceException;
import com.williamhaw.friendmanagement.persistence.UserPersistence;
import com.williamhaw.friendmanagement.user.User;

/**
 * Given list of emails, find users and adds all other users in list as friends
 * @author williamhaw
 *
 */
public class AddFriendAction {
	
	private UserPersistence persistence;
	
	public AddFriendAction(UserPersistence persistence) {
		this.persistence = persistence;
	}

	public boolean addFriends(Collection<String> toBeFriendsEmails) {
		
		try {
			for (String lookupEmail : toBeFriendsEmails) {
				User toAddFriends = persistence.lookup(lookupEmail);
				for (String email : toBeFriendsEmails) {
					toAddFriends.addFriend(email);
				}
				persistence.update(toAddFriends);
			}
		} catch (PersistenceException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

}
