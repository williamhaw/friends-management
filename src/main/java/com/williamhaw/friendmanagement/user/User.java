package com.williamhaw.friendmanagement.user;

import java.util.Set;

/**
 * Defines contract for all User classes
 * @author williamhaw
 *
 */
public interface User {
	/**
	 * @return null if email is not set
	 */
	public String getEmail();
	
	/**
	 * @param email
	 */
	public void setEmail(String email);
	
	/**
	 * @return empty Set if user has no friends
	 */
	public Set<String> getFriends();
	
	/**
	 * @param friendEmail
	 */
	public void addFriend(String friendEmail);
	
	/**
	 * @param friends
	 */
	public void addFriends(Set<String> friends);
}
