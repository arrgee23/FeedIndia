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
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.appengine.api.datastore.Entity;

public class DonateServlet extends HttpServlet {
	private static final long serialVersionUID = -3460564523203863759L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		PrintWriter out = resp.getWriter();

		// Create a session object if it is already not created.
		HttpSession session = req.getSession(true);

		// user key is pushed onto the session
		// check to see if user key is there or the user is logged in
		Key ukey;
		JSONObject obj = new JSONObject();
		if (((Key) session.getAttribute("key")) != null) {
			ukey = (Key) session.getAttribute("key");
			
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			//Entity user = datastore.get(ukey);
			
			Entity donation = new Entity("Donation");
			// type of donation argument
			String foodType = checkNull(escapeHtml(req.getParameter("foodType")));
			
			String  qty = checkNull(escapeHtml(req.getParameter("foodQuantity")));
			int foodQuantity;
			if(qty.equals(""))
				foodQuantity = 0;
			else
				foodQuantity = Integer.parseInt(qty);
			
			int money;
			String m = checkNull(escapeHtml(req.getParameter("money")));
			if(m.equals(""))
				money = 0;
			else
				money = Integer.parseInt(m);
			
			donation.setProperty("foodType",foodType );
			donation.setProperty("foodQuantity",foodQuantity);
			donation.setProperty("money",money);
			
			// make donation for the user that is logged in
			donation.setProperty("donatedBy",ukey);
			Date donatedOn = new Date();
			donation.setProperty("donatedOn",donatedOn);
			
			// the volunteer who will collect the donation
			Key volunteer = null;
			donation.setProperty("handledBy",volunteer);
			Date responsibilityTakenOn = null; // the day volunteer accepts the task of collectinng the donation
			donation.setProperty("responsibilityTakenOn",responsibilityTakenOn);
			donation.setProperty("isHandled",false);
			donation.setProperty("isBeingHandled",false);
			Date handledOn = null;
			donation.setProperty("handledOn",handledOn);
			datastore.put(donation);
			try {
				
				obj.put(Strings.SUCCESS,true);
				obj.put(Strings.MESSAGE, Strings.SUCCESS);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		// The user has not logged in
		else {
			try {
				// TODO error msg
				obj.put("success", false);
				obj.put(Strings.MESSAGE, Strings.NOT_LOGGED_IN);
				
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
		out.print(obj);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		PrintWriter out = resp.getWriter();

		// Create a session object if it is already not created.
		HttpSession session = req.getSession(true);

		// user key is pushed onto the session
		// check to see if user key is there or the user is logged in
		Key ukey;
		JSONObject obj = new JSONObject();
		if (((Key) session.getAttribute("key")) != null) {
			ukey = (Key) session.getAttribute("key");
			
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			//Entity user = datastore.get(ukey);
			
			Entity donation = new Entity("Donation");
			// type of donation argument
			String foodType = checkNull(escapeHtml(req.getParameter("foodType")));
			
			String  qty = checkNull(escapeHtml(req.getParameter("foodQuantity")));
			int foodQuantity;
			if(qty.equals(""))
				foodQuantity = 0;
			else
				foodQuantity = Integer.parseInt(qty);
			
			int money;
			String m = checkNull(escapeHtml(req.getParameter("money")));
			if(m.equals(""))
				money = 0;
			else
				money = Integer.parseInt(m);
			
			donation.setProperty("foodType",foodType );
			donation.setProperty("foodQuantity",foodQuantity);
			donation.setProperty("money",money);
			
			// make donation for the user that is logged in
			donation.setProperty("donatedBy",ukey);
			Date donatedOn = new Date();
			donation.setProperty("donatedOn",donatedOn);
			
			// the volunteer who will collect the donation
			Key volunteer = null;
			donation.setProperty("handledBy",volunteer);
			Date responsibilityTakenOn = null; // the day volunteer accepts the task of collectinng the donation
			donation.setProperty("responsibilityTakenOn",responsibilityTakenOn);
			donation.setProperty("isHandled",false);
			donation.setProperty("isBeingHandled",false);
			Date handledOn = null;
			donation.setProperty("handledOn",handledOn);
			datastore.put(donation);
			try {
				
				obj.put(Strings.SUCCESS,true);
				obj.put(Strings.MESSAGE, Strings.SUCCESS);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		// The user has not logged in
		else {
			try {
				// TODO error msg
				obj.put("success", false);
				obj.put(Strings.MESSAGE, Strings.NOT_LOGGED_IN);
				
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
		out.print(obj);
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
