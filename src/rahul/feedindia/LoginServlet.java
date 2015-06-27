package rahul.feedindia;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapIterator;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class LoginServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		String email = checkNull(escapeHtml(req.getParameter("email")));
		String pwd = checkNull(escapeHtml(req.getParameter("hashedPassword")));
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		PrintWriter out = resp.getWriter();
		
		Key ukey = KeyFactory.createKey("User", email);
		JSONObject obj = new JSONObject();
		try {
			 Entity user = datastore.get(ukey);
			 String pass = (String) user.getProperty("hashedPassword");
			
			 if(pass.equals(pwd)){
				 
				 obj.put("success", true);
				 obj.put("key", KeyFactory.keyToString(ukey));
				 
				 JSONObject usr = new JSONObject();
				 usr.put("fullName",(String)user.getProperty("fullName"));
				 usr.put("email",(String)user.getKey().getName());
				 usr.put("address",(String)user.getProperty("address"));
				 usr.put("city",(String)user.getProperty("city"));
				 usr.put("hashedPassword",pass);
				 usr.put("isDonor",(boolean)user.getProperty("isDonor"));
				 usr.put("isVolunteer",(boolean)user.getProperty("isVolunteer"));
				 usr.put("phone",(String)user.getProperty("phone"));
				 usr.put("registeredOn",(Date)user.getProperty("registeredOn"));
				 usr.put("state",(String)user.getProperty("state"));
				 usr.put("zipcode",(String)user.getProperty("zipcode"));
				 
				 obj.put("user",user);
				 
			 }else{
				obj.put("success", false);
				obj.put("key", KeyFactory.keyToString(ukey));
				obj.put("user","");
			}
			 out.print(obj);
			 
		} catch (EntityNotFoundException e) {
			
			try {
				obj.put("success", false);
				obj.put("key", KeyFactory.keyToString(ukey));
				obj.put("user","");
				out.print(obj);
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			
		}catch(JSONException e2){
			e2.printStackTrace(out);
		}
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
