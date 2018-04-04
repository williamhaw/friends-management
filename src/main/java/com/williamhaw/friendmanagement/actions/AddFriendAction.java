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
				if(toAddFriends == null) {
					return false;
				}
				for (String email : toBeFriendsEmails) {
					User newFriend = persistence.lookup(email);
					if(newFriend != null && newFriend.getBlocked().contains(toAddFriends.getEmail())) { //don't add connection if other user has blocked toAddFriends
						continue;
					}
					if(!toAddFriends.getEmail().equals(email) && //don't add user to its own friends list
							!toAddFriends.getBlocked().contains(email)) { //don't add friend if blocked
						toAddFriends.addFriend(email);
					}
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
