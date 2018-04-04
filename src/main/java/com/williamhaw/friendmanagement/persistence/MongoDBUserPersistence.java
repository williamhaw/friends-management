package com.williamhaw.friendmanagement.persistence;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.williamhaw.friendmanagement.user.DefaultUser;
import com.williamhaw.friendmanagement.user.User;
import com.williamhaw.friendmanagement.util.PropertiesHelper;

/**
 * User Persistence using MongoDB
 * @author williamhaw
 *
 */
public class MongoDBUserPersistence implements UserPersistence {
	
	private static final String KEY_URI = "db.uri";
	private static final String KEY_DB_NAME = "db.name";
	private static final String KEY_DB_COLLECTION_NAME = "db.collection.name";
	
	MongoCollection<DefaultUser> dbCollection;
	
	@Override
	public void initialise(PropertiesHelper props) {
		CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		MongoClientURI uri = new MongoClientURI(props.getString(KEY_URI, null));
		MongoClient client = new MongoClient(uri);
		MongoDatabase db = client.getDatabase(props.getString(KEY_DB_NAME, "friend-management-app")).withCodecRegistry(pojoCodecRegistry);
		dbCollection = db.getCollection(props.getString(KEY_DB_COLLECTION_NAME, "users"), DefaultUser.class);
		System.out.println("MongoDBUserPersistence initialised");
	}

	@Override
	public boolean add(User user) throws PersistenceException {
		if(user instanceof DefaultUser) {
			try {
				if(lookup(user.getEmail()) == null)
					dbCollection.insertOne((DefaultUser)user);
				else
					update(user);
				return true;
			}catch (MongoException e) {
				throw new PersistenceException(e);
			}
		}else {
			return false;
		}
	}

	@Override
	public User lookup(String email) throws PersistenceException {
		try {
			return dbCollection.find(Filters.eq("_id", email)).first();
		}catch (MongoException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public boolean update(User user) throws PersistenceException {
		if(user instanceof DefaultUser) {
			try {
				dbCollection.findOneAndReplace(Filters.eq("_id", user.getEmail()), (DefaultUser) user);
				return true;
			}catch (MongoException e) {
				throw new PersistenceException(e);
			}
		}else {
			return false;
		}
	}

	@Override
	public boolean remove(String email) throws PersistenceException {
		try {
			dbCollection.deleteOne(Filters.eq("_id", email));
			return true;
		}catch (MongoException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public boolean remove(User user) throws PersistenceException {
		return remove(user.getEmail());
	}
}
