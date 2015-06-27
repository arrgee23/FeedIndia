package rahul.feedindia;

import java.io.IOException;

import com.google.appengine.api.datastore.Key;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

//TODO set method to doPost
//TODO delete throwsIOException
public class RegisterServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		String name = checkNull(req.getParameter("fullName"));
		String email = checkNull(req.getParameter("email"));
		String pwd = checkNull(req.getParameter("hashedPassword"));
		String city = checkNull(req.getParameter("city"));
		String state = checkNull(req.getParameter("state"));
		String zipcode = checkNull(req.getParameter("zipcode"));
		String address = checkNull(req.getParameter("address"));
		String phone = checkNull(req.getParameter("phone"));
		String userType = checkNull(req.getParameter("userType"));
		
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		 PrintWriter out = resp.getWriter();
		
		try {
			Key ukey = KeyFactory.createKey("User", email);
			datastore.get(ukey);
			//TODO send proper response
			JSONObject obj = new JSONObject();
			obj.put("success", false);
			obj.put("key", KeyFactory.keyToString(ukey));
			obj.put("user","");
			out.println(obj);
			//datastore.get
		} 
		catch (JSONException e3){
			e3.printStackTrace();
		}
		catch (Exception e) {
			// add user
			Entity user = new Entity("User",email); // email is the id of user
			user.setProperty("fullName", name);
			user.setProperty("hashedPassword", pwd);
			user.setProperty("city", city);
			user.setProperty("zipcode", zipcode);
			user.setProperty("state", state);
			user.setProperty("address",address);
			user.setProperty("phone", phone);
			Date currdate = new Date();
			user.setProperty("registeredOn",currdate );

			if(userType.equals("donor")){
				user.setProperty("isDonor", true);
				user.setProperty("isVolunteer", false);
			}else{ // its a volunteer
				user.setProperty("isDonor", false);
				user.setProperty("isVolunteer", true);
			}
			Key k = datastore.put(user);
			//TODO send proper response and delete print statement
			
			
				JSONObject obj = new JSONObject();
				try {
					obj.put("success", true);
					obj.put("key", KeyFactory.keyToString(k));
					
					JSONObject usr = new JSONObject();
					usr.put("fullName", name);
					usr.put("email", email);
					usr.put("hashedPassword", pwd);
					usr.put("city", city);
					usr.put("state", state);
					usr.put("address",address);
					usr.put("phone", phone);
					usr.put("zipcode", zipcode);
					usr.put("registeredOn",currdate );
					if(userType.equals("donor")){
						usr.put("isDonor", true);
						usr.put("isVolunteer", false);
					}else{ // its a volunteer
						usr.put("isDonor", false);
						usr.put("isVolunteer", true);
					}
					obj.put("user", usr);
					
					out.println(obj);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
		}
		
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		String name = checkNull(req.getParameter("fullName"));
		String email = checkNull(req.getParameter("email"));
		String pwd = checkNull(req.getParameter("hashedPassword"));
		String city = checkNull(req.getParameter("city"));
		String state = checkNull(req.getParameter("state"));
		String zipcode = checkNull(req.getParameter("zipcode"));
		String address = checkNull(req.getParameter("address"));
		String phone = checkNull(req.getParameter("phone"));
		String userType = checkNull(req.getParameter("userType"));
		
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		 PrintWriter out = resp.getWriter();
		
		try {
			Key ukey = KeyFactory.createKey("User", email);
			datastore.get(ukey);
			//TODO send proper response
			JSONObject obj = new JSONObject();
			obj.put("success", false);
			obj.put("key", KeyFactory.keyToString(ukey));
			obj.put("user","");
			out.println(obj);
			//datastore.get
		} 
		catch (JSONException e3){
			e3.printStackTrace();
		}
		catch (Exception e) {
			// add user
			Entity user = new Entity("User",email); // email is the id of user
			user.setProperty("fullName", name);
			user.setProperty("hashedPassword", pwd);
			user.setProperty("city", city);
			user.setProperty("zipcode", zipcode);
			user.setProperty("state", state);
			user.setProperty("address",address);
			user.setProperty("phone", phone);
			Date currdate = new Date();
			user.setProperty("registeredOn",currdate );

			if(userType.equals("donor")){
				user.setProperty("isDonor", true);
				user.setProperty("isVolunteer", false);
			}else{ // its a volunteer
				user.setProperty("isDonor", false);
				user.setProperty("isVolunteer", true);
			}
			Key k = datastore.put(user);
			//TODO send proper response and delete print statement
			
			
				JSONObject obj = new JSONObject();
				try {
					obj.put("success", true);
					obj.put("key", KeyFactory.keyToString(k));
					
					JSONObject usr = new JSONObject();
					usr.put("fullName", name);
					usr.put("email", email);
					usr.put("hashedPassword", pwd);
					usr.put("city", city);
					usr.put("state", state);
					usr.put("address",address);
					usr.put("phone", phone);
					usr.put("zipcode", zipcode);
					usr.put("registeredOn",currdate );
					if(userType.equals("donor")){
						usr.put("isDonor", true);
						usr.put("isVolunteer", false);
					}else{ // its a volunteer
						usr.put("isDonor", false);
						usr.put("isVolunteer", true);
					}
					obj.put("user", usr);
					
					out.println(obj);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
		}
		
	}
	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
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
