package com.williamhaw.friendmanagement;

import java.util.Map;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.williamhaw.friendmanagement.actions.ActionHandler;
import com.williamhaw.friendmanagement.actions.ActionHandler.Actions;
import com.williamhaw.friendmanagement.persistence.UserPersistence;
import com.williamhaw.friendmanagement.servlets.FriendManagementServlet;
import com.williamhaw.friendmanagement.util.PropertiesHelper;

/**
 * Sets up routes and servlets according to config
 * @author williamhaw
 *
 */
public class FriendManagementServer {
	
	public final static String SERVER_PORT = "server.port";
	public final static String CONTEXT_PATH = "context.path";
	public final static String ROUTE_PREFIX = "route.";
	public final static String PERSISTENCE_CLASS = "persistence.class";
	
	PropertiesHelper props;

	public FriendManagementServer(PropertiesHelper props) {
		this.props = props;
	}

	public void start() {
		try {
			Server jettyServer = new Server(props.getInt(SERVER_PORT, 8080));

			ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
			context.setContextPath(props.getString(CONTEXT_PATH, "/api/v1/"));
			jettyServer.setHandler(context);
			
			Map<String, String> routeMap = props.getStrings(ROUTE_PREFIX);
			
			UserPersistence persistence = (UserPersistence) Class.forName(props.getString(PERSISTENCE_CLASS, "com.williamhaw.friendmanagement.persistence.MongoDBUserPersistence")).newInstance();
			persistence.initialise(props);
			
			ActionHandler handler = new ActionHandler(persistence);
			
			for(String routeConfig : routeMap.keySet()) {
				String route = routeConfig.replace(ROUTE_PREFIX, "");
				try {
					Actions action = Actions.valueOf(routeMap.get(routeConfig));
					ServletHolder holder = new ServletHolder();
					FriendManagementServlet servlet = new FriendManagementServlet(action, handler);
					holder.setServlet(servlet);
					context.addServlet(holder, "/" + route);
				} catch (IllegalArgumentException e) { //catches config error where value is not in the ActionHandler.Actions enum and continues setting up other servlets
					e.printStackTrace();
				}				
			}
			
			jettyServer.start();
			jettyServer.join();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
