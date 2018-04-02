package com.williamhaw.friendmanagement.persistence;

import java.util.HashMap;
import java.util.Map;

import com.williamhaw.friendmanagement.user.User;

/**
 * Implements in-memory storage of User instances
 * @author williamhaw
 *
 */
public class HashMapUserPersistence implements UserPersistence{
	
	Map<String, User> users = new HashMap<>();

	@Override
	public synchronized boolean add(User user) throws PersistenceException {
		users.put(user.getEmail(), user);
		return true;
	}

	@Override
	public synchronized User lookup(String email) throws PersistenceException {
		return users.get(email);
	}

	@Override
	public synchronized boolean update(User user) throws PersistenceException {
		return add(user);
	}

	@Override
	public synchronized boolean remove(String email) throws PersistenceException {
		users.remove(email);
		return true;
	}

	@Override
	public synchronized boolean remove(User user) throws PersistenceException {
		users.remove(user.getEmail());
		return true;
	}

}
