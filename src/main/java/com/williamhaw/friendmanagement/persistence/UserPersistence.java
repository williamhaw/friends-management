package com.williamhaw.friendmanagement.persistence;

import com.williamhaw.friendmanagement.user.User;
import com.williamhaw.friendmanagement.util.Initialisable;

/**
 * Create, Read, Update and Remove Users from persistence implementations
 * @author williamhaw
 *
 */
public interface UserPersistence extends Initialisable{

	/**
	 * @param user
	 * @return true if add operation succeeds, else returns false
	 */
	public boolean add(User user) throws PersistenceException;
	
	/**
	 * @param email
	 * @return User instance if it exists, else returns null
	 */
	public User lookup(String email) throws PersistenceException;
	
	/**
	 * @param user
	 * @return true if update operation succeeds, else returns false
	 */
	public boolean update(User user) throws PersistenceException;
	
	/**
	 * @param email
	 * @return true if remove operation succeeds, else returns false
	 */
	public boolean remove(String email) throws PersistenceException;
	
	/**
	 * @param user
	 * @return true if remove operation succeeds, else returns false
	 */
	public boolean remove(User user) throws PersistenceException;
	
}
