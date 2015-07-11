package rahul.feedindia;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import rahul.feedindia.shared.Strings;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = -5728549237300194024L;
	private final int SESSION_TIMEOUT = 300; // seconds

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String email = checkNull(escapeHtml(req.getParameter("email")));
		String pwd = checkNull(escapeHtml(req.getParameter("hashedPassword")));

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		
		PrintWriter out = resp.getWriter();

		// Create a session object if it is already not created.
		HttpSession session = req.getSession(true);
		
		
		// user key is pushed onto the session
		// check to see if user key is there or the user is logged in
		Key ukey;
		JSONObject obj = null;
		if (((Key) session.getAttribute("key")) != null) {
			ukey = (Key) session.getAttribute("key");
			//out.println("From Session");
		} 
		// The user is logging in
		else {
			//out.println("First time login");
			ukey = KeyFactory.createKey("User", email); // creates key from his email	
		}
		
		Entity user;
		try {
			user = datastore.get(ukey);
			String pass = (String) user.getProperty("hashedPassword");
			if(pass.equals(pwd)){ // password match valid login
				obj = encodeUserObject(datastore,true,user);
				
				// put key in session
				session.setAttribute("key", ukey);
				//session.setMaxInactiveInterval(SESSION_TIMEOUT); // set timeout
			}
			else{ // wrong password
				obj = encodeUserObject(datastore,false,user);
				obj.put(Strings.MESSAGE, Strings.WRONG_PASSWORD);
			}
			
		} catch (EntityNotFoundException e) { // user doesnt exist
			obj = encodeUserObject(datastore,false,null);
			try {
				obj.put(Strings.MESSAGE, Strings.ENTITY_NOT_FOUND);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.print(obj);
	}
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String email = checkNull(escapeHtml(req.getParameter("email")));
		String pwd = checkNull(escapeHtml(req.getParameter("hashedPassword")));

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		
		PrintWriter out = resp.getWriter();

		// Create a session object if it is already not created.
		HttpSession session = req.getSession(true);
		
		
		// user key is pushed onto the session
		// check to see if user key is there or the user is logged in
		Key ukey;
		JSONObject obj = null;
		if (((Key) session.getAttribute("key")) != null) {
			ukey = (Key) session.getAttribute("key");
			//out.println("From Session");
		} 
		// The user is logging in
		else {
			//out.println("First time login");
			ukey = KeyFactory.createKey("User", email); // creates key from his email	
		}
		
		Entity user;
		try {
			user = datastore.get(ukey);
			String pass = (String) user.getProperty("hashedPassword");
			if(pass.equals(pwd)){ // password match valid login
				obj = encodeUserObject(datastore,true,user);
				
				// put key in session
				session.setAttribute("key", ukey);
				session.setMaxInactiveInterval(SESSION_TIMEOUT); // set timeout
			}
			else{ // wrong password
				obj = encodeUserObject(datastore,false,user);
				obj.put(Strings.MESSAGE, Strings.WRONG_PASSWORD);
			}
			
		} catch (EntityNotFoundException e) { // user doesnt exist
			obj = encodeUserObject(datastore,false,null);
			try {
				obj.put(Strings.MESSAGE, Strings.ENTITY_NOT_FOUND);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.print(obj);
	}
	
	
	private JSONObject encodeUserObject(DatastoreService datastore,boolean isSuccesfulLogin,Entity user){
		JSONObject obj = new JSONObject();
		try {
			// TODO error msg
			if (isSuccesfulLogin) {

				obj.put(Strings.SUCCESS, true);

				JSONObject usr = new JSONObject();
				usr.put("fullName", (String) user.getProperty("fullName"));
				usr.put("email", (String) user.getKey().getName());
				usr.put("address", (String) user.getProperty("address"));
				usr.put("city", (String) user.getProperty("city"));
				//usr.put("hashedPassword", pass);
				usr.put("isDonor", (boolean) user.getProperty("isDonor"));
				usr.put("isVolunteer",
						(boolean) user.getProperty("isVolunteer"));
				usr.put("phone", (String) user.getProperty("phone"));
				usr.put("registeredOn",
						(Date) user.getProperty("registeredOn"));
				usr.put("state", (String) user.getProperty("state"));
				usr.put("zipcode", (String) user.getProperty("zipcode"));

				obj.put(Strings.USER, usr);

			} else {
				obj.put(Strings.SUCCESS, false);
				//obj.put("key", KeyFactory.keyToString(ukey));
				obj.put(Strings.USER, new JSONObject());
			}
			//out.println(obj);

		}catch (JSONException e2) {
			e2.printStackTrace();
		}
		return obj;
	}
	
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}

	private String checkNull(String s) {
		if (s == null) {
			return "";
		}
		return s;
	}
}
