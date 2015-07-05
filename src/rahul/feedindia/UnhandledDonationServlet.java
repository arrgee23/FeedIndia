package rahul.feedindia;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import rahul.feedindia.shared.Strings;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.appengine.api.datastore.Transaction;

public class UnhandledDonationServlet extends HttpServlet{
	private static final long serialVersionUID = 8596694958449558673L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		PrintWriter out = resp.getWriter();
	
		HttpSession session = req.getSession(true);
		Key ukey;
		JSONObject jo = new JSONObject();
		if ((ukey =((Key) session.getAttribute("key"))) != null) {
			try {//TODO msg

				Key dKey = KeyFactory.stringToKey(req.getParameter(Strings.KEY));
				
				DatastoreService datastore = DatastoreServiceFactory
						.getDatastoreService();
				Transaction txn = datastore.beginTransaction();
				try {
					Entity donation = datastore.get(dKey);
					// check if responsibility has already been taken
					if((boolean)donation.getProperty("isBeingHandled")){
						txn.rollback(); // rollback
						jo.put(Strings.SUCCESS, false);
						jo.put(Strings.MESSAGE, Strings.ALREADY_HANDLED);
					}
					else{ // proceed to make changes
						donation.setProperty("responsibilityTakenOn", new Date()); // mark the responsibility taken today
						donation.setProperty("isBeingHandled", true);
						donation.setProperty("handledBy", ukey);
						datastore.put(donation);
						jo.put("success", true);
						txn.commit();
					}
					
				} catch (EntityNotFoundException e){
					jo.put(Strings.SUCCESS, false);
					jo.put(Strings.MESSAGE, Strings.ENTITY_NOT_FOUND);
				}
				finally{
					if(txn.isActive())
						txn.rollback();
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		} else { // the user is not logged in
			// TODO not loggedin
			try {
				jo.put(Strings.SUCCESS, false);
				jo.put(Strings.MESSAGE, Strings.NOT_LOGGED_IN);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		out.println(jo);
		
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		PrintWriter out = resp.getWriter();
	
		HttpSession session = req.getSession(true);
		Key ukey;
		JSONObject jo = new JSONObject();
		if ((ukey =((Key) session.getAttribute("key"))) != null) {
			try {//TODO msg

				Key dKey = KeyFactory.stringToKey(req.getParameter(Strings.KEY));
				
				DatastoreService datastore = DatastoreServiceFactory
						.getDatastoreService();
				Transaction txn = datastore.beginTransaction();
				try {
					Entity donation = datastore.get(dKey);
					// check if responsibility has already been taken
					if((boolean)donation.getProperty("isBeingHandled")){
						txn.rollback(); // rollback
						jo.put(Strings.SUCCESS, false);
						jo.put(Strings.MESSAGE, Strings.ALREADY_HANDLED);
					}
					else{ // proceed to make changes
						donation.setProperty("responsibilityTakenOn", new Date()); // mark the responsibility taken today
						donation.setProperty("isBeingHandled", true);
						donation.setProperty("handledBy", ukey);
						datastore.put(donation);
						jo.put("success", true);
						txn.commit();
					}
					
				} catch (EntityNotFoundException e){
					jo.put(Strings.SUCCESS, false);
					jo.put(Strings.MESSAGE, Strings.ENTITY_NOT_FOUND);
				}
				finally{
					if(txn.isActive())
						txn.rollback();
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		} else { // the user is not logged in
			// TODO not loggedin
			try {
				jo.put(Strings.SUCCESS, false);
				jo.put(Strings.MESSAGE, Strings.NOT_LOGGED_IN);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		out.println(jo);
		
	}

}
