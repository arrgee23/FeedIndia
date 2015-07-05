package rahul.feedindia;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import rahul.feedindia.shared.FieldVerifier;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class RequestServlet extends HttpServlet {
	private static final long serialVersionUID = -2013971842842981279L;
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		String qty = FieldVerifier.checkNull(FieldVerifier.escapeHtml(req.getParameter("foodQuantity")));
		int foodQuantity; // number of people food asked for
		if(qty.equals(""))
			foodQuantity = 0;
		else
			foodQuantity = Integer.parseInt(qty);
		
		String name = FieldVerifier.checkNull(FieldVerifier.escapeHtml(req.getParameter("fullName")));
		String city = FieldVerifier.checkNull(FieldVerifier.escapeHtml(req.getParameter("city")));
		String state = FieldVerifier.checkNull(FieldVerifier.escapeHtml(req.getParameter("state")));
		String zipcode = FieldVerifier.checkNull(FieldVerifier.escapeHtml(req.getParameter("zipcode")));
		String address = FieldVerifier.checkNull(FieldVerifier.escapeHtml(req.getParameter("address")));
		String phone = FieldVerifier.checkNull(FieldVerifier.escapeHtml(req.getParameter("phone")));
		String requesterType = FieldVerifier.checkNull(FieldVerifier.escapeHtml((req.getParameter("userType"))));
		Date requestedOn = new Date();
		Date requestedForDate = null;
		Key volunteer = null; // the volunteer who will collect the donation
		Date responsibilityTakenOn = null; // the day volunteer accepts the responsibility
		Date handledOn = null;	// the date food is donated													
		boolean isOrganization = false;
		if(requesterType.equals("organization"))
			isOrganization = true;
		
		// saving to database
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Entity request = new Entity("Request");
		
		request.setProperty("name", name);
		request.setProperty("city", city);
		request.setProperty("state", state);
		request.setProperty("zipcode", zipcode);
		request.setProperty("address", address);
		request.setProperty("phone", phone);
		request.setProperty("isOrganization", isOrganization);
		request.setProperty("requestedOn", requestedOn);
		request.setProperty("requestedForDate", requestedForDate);
		request.setProperty("foodQuantity", foodQuantity);
		request.setProperty("handledBy", volunteer);
		request.setProperty("responsibilityTakenOn", responsibilityTakenOn);
		request.setProperty("isHandled", false);
		request.setProperty("isBeingHandled", false);
		request.setProperty("handledOn", handledOn);
		
		datastore.put(request);
		
		// make and print json object
		PrintWriter out = resp.getWriter();
		JSONObject obj = new JSONObject();
		
		try {
			obj.put("success", true);
			JSONObject r = new JSONObject();
			r.put("name", name);
			r.put("city", city);
			r.put("state", state);
			r.put("zipcode", zipcode);
			r.put("address", address);
			r.put("phone", phone);
			r.put("isOrganization", isOrganization);
			r.put("requestedOn", requestedOn);
			r.put("requestedForDate", requestedForDate);
			r.put("foodQuantity", foodQuantity);
			r.put("handledBy", volunteer);
			r.put("responsibilityTakenOn", responsibilityTakenOn);
			r.put("isHandled", false);
			r.put("isBeingHandled", false);
			r.put("handledOn", handledOn);
			obj.put("request", r);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		out.print(obj);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		String qty = FieldVerifier.checkNull(FieldVerifier.escapeHtml(req.getParameter("foodQuantity")));
		int foodQuantity; // number of people food asked for
		if(qty.equals(""))
			foodQuantity = 0;
		else
			foodQuantity = Integer.parseInt(qty);
		
		String name = FieldVerifier.checkNull(FieldVerifier.escapeHtml(req.getParameter("fullName")));
		String city = FieldVerifier.checkNull(FieldVerifier.escapeHtml(req.getParameter("city")));
		String state = FieldVerifier.checkNull(FieldVerifier.escapeHtml(req.getParameter("state")));
		String zipcode = FieldVerifier.checkNull(FieldVerifier.escapeHtml(req.getParameter("zipcode")));
		String address = FieldVerifier.checkNull(FieldVerifier.escapeHtml(req.getParameter("address")));
		String phone = FieldVerifier.checkNull(FieldVerifier.escapeHtml(req.getParameter("phone")));
		String requesterType = FieldVerifier.checkNull(FieldVerifier.escapeHtml((req.getParameter("userType"))));
		Date requestedOn = new Date();
		Date requestedForDate = null;
		Key volunteer = null; // the volunteer who will collect the donation
		Date responsibilityTakenOn = null; // the day volunteer accepts the responsibility
		Date handledOn = null;	// the date food is donated													
		boolean isOrganization = false;
		if(requesterType.equals("organization"))
			isOrganization = true;
		
		// saving to database
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		Entity request = new Entity("Request");
		
		request.setProperty("name", name);
		request.setProperty("city", city);
		request.setProperty("state", state);
		request.setProperty("zipcode", zipcode);
		request.setProperty("address", address);
		request.setProperty("phone", phone);
		request.setProperty("isOrganization", isOrganization);
		request.setProperty("requestedOn", requestedOn);
		request.setProperty("requestedForDate", requestedForDate);
		request.setProperty("foodQuantity", foodQuantity);
		request.setProperty("handledBy", volunteer);
		request.setProperty("responsibilityTakenOn", responsibilityTakenOn);
		request.setProperty("isHandled", false);
		request.setProperty("isBeingHandled", false);
		request.setProperty("handledOn", handledOn);
		
		datastore.put(request);
		
		// make and print json object
		PrintWriter out = resp.getWriter();
		JSONObject obj = new JSONObject();
		
		try {
			obj.put("success", true);
			JSONObject r = new JSONObject();
			r.put("name", name);
			r.put("city", city);
			r.put("state", state);
			r.put("zipcode", zipcode);
			r.put("address", address);
			r.put("phone", phone);
			r.put("isOrganization", isOrganization);
			r.put("requestedOn", requestedOn);
			r.put("requestedForDate", requestedForDate);
			r.put("foodQuantity", foodQuantity);
			r.put("handledBy", volunteer);
			r.put("responsibilityTakenOn", responsibilityTakenOn);
			r.put("isHandled", false);
			r.put("isBeingHandled", false);
			r.put("handledOn", handledOn);
			obj.put("request", r);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		out.print(obj);
	}

}
