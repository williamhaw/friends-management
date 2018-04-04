package com.williamhaw.friendmanagement.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.williamhaw.friendmanagement.actions.ActionHandler;
import com.williamhaw.friendmanagement.actions.ActionHandler.Actions;

/**
 * Handles POST requests and returns responses from ActionHandler
 * @author williamhaw
 *
 */
public class FriendManagementServlet extends HttpServlet {
	
	private static final long serialVersionUID = -6927739718297725033L;

	private JSONParser jsonParser = new JSONParser();
	
	private ActionHandler handler;
	private Actions action;
	
	public FriendManagementServlet(Actions action, ActionHandler handler) {
		this.handler = handler;
		this.action = action;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
		try {
			JSONObject jsonObject = (JSONObject)jsonParser.parse(req.getReader());
			JSONObject ret = handler.handle(action, jsonObject);
			
			resp.getOutputStream().println(ret.toJSONString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
}
