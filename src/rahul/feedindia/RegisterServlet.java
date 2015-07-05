package rahul.feedindia;

import java.io.IOException;

import com.google.appengine.api.datastore.Key;

import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rahul.feedindia.shared.Strings;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

//TODO set method to doPost
//TODO delete throwsIOException
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 4527085618846172530L;
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		resp.setContentType("application/json");
		
		String name = checkNull(escapeHtml(req.getParameter("fullName")));
		String email = checkNull(escapeHtml(req.getParameter("email")));
		String pwd = checkNull(escapeHtml(req.getParameter("hashedPassword")));
		String city = checkNull(escapeHtml(req.getParameter("city")));
		String state = checkNull(escapeHtml(req.getParameter("state")));
		String zipcode = checkNull(escapeHtml(req.getParameter("zipcode")));
		String address = checkNull(escapeHtml(req.getParameter("address")));
		String phone = checkNull(escapeHtml(req.getParameter("phone")));
		String userType = checkNull((req.getParameter("userType")));
		
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		PrintWriter out = resp.getWriter();
		Key ukey = KeyFactory.createKey("User", email);
		Date currdate = new Date();
		try {
			
			datastore.get(ukey);
			//TODO send proper response
			
			JSONObject obj = new JSONObject();
			obj.put(Strings.SUCCESS, false);
			obj.put(Strings.MESSAGE, Strings.USER_EXISTS);
			obj.put(Strings.USER,new JSONObject());
			
			out.print(obj);
			
			//datastore.get
		} 
		catch (JSONException e3){
			e3.printStackTrace();
		}
		catch (Exception e) { // entity not found so add user
			// add user
			Transaction txn = datastore.beginTransaction();
			try {
				datastore.get(ukey); // object already exists
				txn.rollback();
				
			} catch (EntityNotFoundException e2) { // now save the entity
				
				Entity user = new Entity("User",email); // email is the id of user
				user.setProperty("fullName", name);
				user.setProperty("hashedPassword", pwd);
				user.setProperty("city", city);
				user.setProperty("zipcode", zipcode);
				user.setProperty("state", state);
				user.setProperty("address",address);
				user.setProperty("phone", phone);
				user.setProperty("registeredOn",currdate );

				if(userType.equals("donor")){
					user.setProperty("isDonor", true);
					user.setProperty("isVolunteer", false);
				}else{ // its a volunteer
					user.setProperty("isDonor", false);
					user.setProperty("isVolunteer", true);
				}
				datastore.put(user);
				txn.commit();
			} finally{
				if(txn.isActive())
					txn.rollback();
			}
			
			//TODO send proper response and delete print statement
			
			
				JSONObject obj = new JSONObject();
				try {
					obj.put(Strings.SUCCESS, true);
					obj.put(Strings.MESSAGE, Strings.SUCCESS);
					
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
		resp.setContentType("application/json");
		
		String name = checkNull(escapeHtml(req.getParameter("fullName")));
		String email = checkNull(escapeHtml(req.getParameter("email")));
		String pwd = checkNull(escapeHtml(req.getParameter("hashedPassword")));
		String city = checkNull(escapeHtml(req.getParameter("city")));
		String state = checkNull(escapeHtml(req.getParameter("state")));
		String zipcode = checkNull(escapeHtml(req.getParameter("zipcode")));
		String address = checkNull(escapeHtml(req.getParameter("address")));
		String phone = checkNull(escapeHtml(req.getParameter("phone")));
		String userType = checkNull((req.getParameter("userType")));
		
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		PrintWriter out = resp.getWriter();
		Key ukey = KeyFactory.createKey("User", email);
		Date currdate = new Date();
		try {
			
			datastore.get(ukey);
			//TODO send proper response
			
			JSONObject obj = new JSONObject();
			obj.put(Strings.SUCCESS, false);
			obj.put(Strings.MESSAGE, Strings.USER_EXISTS);
			obj.put(Strings.USER,new JSONObject());
			
			out.print(obj);
			
			//datastore.get
		} 
		catch (JSONException e3){
			e3.printStackTrace();
		}
		catch (Exception e) { // entity not found so add user
			// add user
			Transaction txn = datastore.beginTransaction();
			try {
				datastore.get(ukey); // object already exists
				txn.rollback();
				
			} catch (EntityNotFoundException e2) { // now save the entity
				
				Entity user = new Entity("User",email); // email is the id of user
				user.setProperty("fullName", name);
				user.setProperty("hashedPassword", pwd);
				user.setProperty("city", city);
				user.setProperty("zipcode", zipcode);
				user.setProperty("state", state);
				user.setProperty("address",address);
				user.setProperty("phone", phone);
				user.setProperty("registeredOn",currdate );

				if(userType.equals("donor")){
					user.setProperty("isDonor", true);
					user.setProperty("isVolunteer", false);
				}else{ // its a volunteer
					user.setProperty("isDonor", false);
					user.setProperty("isVolunteer", true);
				}
				datastore.put(user);
				txn.commit();
			} finally{
				if(txn.isActive())
					txn.rollback();
			}
			
			//TODO send proper response and delete print statement
			
			
				JSONObject obj = new JSONObject();
				try {
					obj.put(Strings.SUCCESS, true);
					obj.put(Strings.MESSAGE, Strings.SUCCESS);
					
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
