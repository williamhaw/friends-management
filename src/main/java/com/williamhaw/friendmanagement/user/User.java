package com.williamhaw.friendmanagement.user;

import java.util.Set;

/**
 * Defines common methods for all User classes
 * @author williamhaw
 *
 */
public interface User {
	public String getEmail();
	
	public void setEmail(String email);
	
	public Set<String> getFriends();
	
	public void addFriends(Set<String> friends);
}
