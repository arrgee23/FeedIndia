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
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class VolunteerViewServlet extends HttpServlet {
	private static final long serialVersionUID = 8730444127442927240L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		// Create a session object if it is already not created.
		HttpSession session = req.getSession(true);

		// user key is pushed onto the session
		// check to see if user key is there meaning the user is logged in
		PrintWriter out = null;
		try {
			out = resp.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Key ukey;
		JSONObject jo = new JSONObject();
		if (((Key) session.getAttribute("key")) != null) {
			ukey = (Key) session.getAttribute("key");

			// show volunteer the unhandled requests as an array of unhandled
			// requests
			// that are in the same city or state
			JSONArray ja = checkoutDonations(ukey);
			JSONArray ja2 = unhandledDonations(ukey);
			JSONArray ja3 = unhandledRequests(ukey);
			JSONArray ja4 = checkoutRequests(ukey);
			
			
				try {
					jo.put("success", true);
					jo.put(Strings.UNHANDLED_REQUESTS, ja3);
					jo.put(Strings.UNHANDLED_DONATIONS, ja2);
					jo.put(Strings.UNCHECKED_REQUESTS, ja4);
					jo.put(Strings.UNCHECKED_DONATIONS, ja);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			
			
			
		} else { // the user is not logged in
			// TODO not loggedin
			try {
				jo.put("success", false);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		out.print(jo);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		// Create a session object if it is already not created.
		HttpSession session = req.getSession(true);

		// user key is pushed onto the session
		// check to see if user key is there meaning the user is logged in
		PrintWriter out = null;
		try {
			out = resp.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Key ukey;
		JSONObject jo = new JSONObject();
		if (((Key) session.getAttribute("key")) != null) {
			ukey = (Key) session.getAttribute("key");

			// show volunteer the unhandled requests as an array of unhandled
			// requests
			// that are in the same city or state
			JSONArray ja = checkoutDonations(ukey);
			JSONArray ja2 = unhandledDonations(ukey);
			JSONArray ja3 = unhandledRequests(ukey);
			JSONArray ja4 = checkoutRequests(ukey);
			
			
				try {
					jo.put("success", true);
					jo.put(Strings.UNHANDLED_REQUESTS, ja3);
					jo.put(Strings.UNHANDLED_DONATIONS, ja2);
					jo.put(Strings.UNCHECKED_REQUESTS, ja4);
					jo.put(Strings.UNCHECKED_DONATIONS, ja);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			
			
			
		} else { // the user is not logged in
			// TODO not loggedin
			try {
				jo.put("success", false);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		out.print(jo);
	}

	/**
	 * returns the list of requests in the same city and in case city is not
	 * provided, in same state. As JSONArray with request json objects
	 * 
	 * @param key
	 * @return JSONArray
	 */
	JSONArray unhandledRequests(Key key) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		JSONArray ja = new JSONArray();

		// make a filter for unhandled requests
		Filter unhandledRequestFilter = new FilterPredicate("isBeingHandled",
				FilterOperator.EQUAL, false);

		Entity volunteer = null;
		try {
			volunteer = datastore.get(key);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}

		String volunteerCity = (String) volunteer.getProperty("city");
		String volunteerState = (String) volunteer.getProperty("state");

		// make a filter for same city
		Filter sameCityFilter = new FilterPredicate("city",
				FilterOperator.EQUAL, volunteerCity);
		// null city
		Filter nullCityFilter = new FilterPredicate("city",
				FilterOperator.EQUAL, "");
		// same state
		Filter sameStateFilter = new FilterPredicate("state",
				FilterOperator.EQUAL, volunteerState);

		// Use CompositeFilter to combine multiple filters
		// null city samestate
		Filter nullCitySameState = CompositeFilterOperator.and(nullCityFilter,
				sameStateFilter);

		// null city same state or matched city
		Filter sameCityOrNullcity = CompositeFilterOperator.or(
				nullCitySameState, sameCityFilter);

		// filter for unhandled AND (same city OR (nullcity AND same state)
		Filter finalFilter = CompositeFilterOperator.and(
				unhandledRequestFilter, sameCityOrNullcity);

		// Use class Query to assemble a query
		Query q = new Query("Request").setFilter(finalFilter);

		// Use PreparedQuery interface to retrieve results
		PreparedQuery pq = datastore.prepare(q);

		for (Entity result : pq.asIterable()) {
			JSONObject obj = new JSONObject();

			try {
				for (int i = 0; i < Strings.REQUEST_STRINGS.length; i++)
					obj.put(Strings.REQUEST_STRINGS[i], (String) result
							.getProperty(Strings.REQUEST_STRINGS[i]));

				for (int i = 0; i < Strings.REQUEST_DATES.length; i++)
					obj.put(Strings.REQUEST_DATES[i],
							(Date) result.getProperty(Strings.REQUEST_DATES[i]));

				for (int i = 0; i < Strings.REQUEST_BOOLEANS.length; i++)
					obj.put(Strings.REQUEST_BOOLEANS[i], (boolean) result
							.getProperty(Strings.REQUEST_BOOLEANS[i]));

				for (int i = 0; i < Strings.REQUEST_KEYS.length; i++)
					obj.put(Strings.REQUEST_KEYS[i],
							(Key) result.getProperty(Strings.REQUEST_KEYS[i]));
				//TODO 
				obj.put(Strings.URL, Strings.UNHANDLED_REQUEST_URL);
				obj.put(Strings.PARAM_NAME,Strings.KEY);
				obj.put(Strings.PARAM_VALUE, KeyFactory.keyToString(result.getKey()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			ja.put(obj);
		}
		return ja;
	}
	
	JSONArray checkoutRequests(Key key) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		JSONArray ja = new JSONArray();

		// make a filter for handled requests
		Filter handledRequestFilter = new FilterPredicate("isBeingHandled",
				FilterOperator.EQUAL, true);
		// make a filter for requestes that has not been finished
		Filter inclompleteFilter = new FilterPredicate("isHandled",
				FilterOperator.EQUAL, false);
		// name can be misleading :p
		Filter unhandledRequestFilter = CompositeFilterOperator.and(handledRequestFilter,
				inclompleteFilter);
		
		Entity volunteer = null;
		try {
			volunteer = datastore.get(key);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}

		String volunteerCity = (String) volunteer.getProperty("city");
		String volunteerState = (String) volunteer.getProperty("state");

		// make a filter for same city
		Filter sameCityFilter = new FilterPredicate("city",
				FilterOperator.EQUAL, volunteerCity);
		// null city
		Filter nullCityFilter = new FilterPredicate("city",
				FilterOperator.EQUAL, "");
		// same state
		Filter sameStateFilter = new FilterPredicate("state",
				FilterOperator.EQUAL, volunteerState);

		// Use CompositeFilter to combine multiple filters
		// null city samestate
		Filter nullCitySameState = CompositeFilterOperator.and(nullCityFilter,
				sameStateFilter);

		// null city same state or matched city
		Filter sameCityOrNullcity = CompositeFilterOperator.or(
				nullCitySameState, sameCityFilter);

		// filter for unhandled AND (same city OR (nullcity AND same state)
		Filter finalFilter = CompositeFilterOperator.and(
				unhandledRequestFilter, sameCityOrNullcity);

		// Use class Query to assemble a query
		Query q = new Query("Request").setFilter(finalFilter);

		// Use PreparedQuery interface to retrieve results
		PreparedQuery pq = datastore.prepare(q);

		for (Entity result : pq.asIterable()) {
			JSONObject obj = new JSONObject();

			try {
				for (int i = 0; i < Strings.REQUEST_STRINGS.length; i++)
					obj.put(Strings.REQUEST_STRINGS[i], (String) result
							.getProperty(Strings.REQUEST_STRINGS[i]));

				for (int i = 0; i < Strings.REQUEST_DATES.length; i++)
					obj.put(Strings.REQUEST_DATES[i],
							(Date) result.getProperty(Strings.REQUEST_DATES[i]));

				for (int i = 0; i < Strings.REQUEST_BOOLEANS.length; i++)
					obj.put(Strings.REQUEST_BOOLEANS[i], (boolean) result
							.getProperty(Strings.REQUEST_BOOLEANS[i]));

				for (int i = 0; i < Strings.REQUEST_KEYS.length; i++)
					obj.put(Strings.REQUEST_KEYS[i],
							(Key) result.getProperty(Strings.REQUEST_KEYS[i]));
				
				//TODO 
				obj.put("url", Strings.UNCHECKED_REQUEST_URL);
				obj.put(Strings.PARAM_NAME,Strings.KEY);
				obj.put(Strings.PARAM_VALUE, KeyFactory.keyToString(result.getKey()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ja.put(obj);
		}
		return ja;
	}
	
	/**
	 * returns the list of Donations in the same city and in case city is not
	 * provided, in same state. As JSONArray with Donation json objects
	 * 
	 * @param key
	 * @return JSONArray
	 */
	JSONArray unhandledDonations(Key key) {

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		JSONArray ja = new JSONArray();
		Entity volunteer = null;
		try {
			volunteer = datastore.get(key);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}

		// make a filter for unhandled requests
		Filter unhandledDonationFilter = new FilterPredicate("isBeingHandled",
				FilterOperator.EQUAL, false);

		Query q = new Query("Donation").setFilter(unhandledDonationFilter);
		PreparedQuery pq = datastore.prepare(q);

		String volunteerCity = (String) volunteer.getProperty("city");
		String volunteerState = (String) volunteer.getProperty("state");
		
		for (Entity result : pq.asIterable()) {
			// get the donor city and state
			Key donorKey = (Key)result.getProperty("donatedBy");
			Entity donor = null;
			try {
				donor = datastore.get(donorKey);
			} catch (EntityNotFoundException e) {
				e.printStackTrace();
			}
			
			String donorCity = (String) donor.getProperty("city");
			String donorState = (String) donor.getProperty("state");
			
			
			if (volunteerCity.equals(donorCity)
					|| (donorCity.equals("") && volunteerState
							.equals(donorState))) {
				try {
					JSONObject obj = new JSONObject();
					
					for (int i = 0; i < Strings.DONATION_STRINGS.length; i++)
						obj.put(Strings.DONATION_STRINGS[i], (String) result
								.getProperty(Strings.DONATION_STRINGS[i]));
					
					for (int i = 0; i < Strings.DONATION_INTS.length; i++)
						obj.put(Strings.DONATION_INTS[i], (long) result
								.getProperty(Strings.DONATION_INTS[i]));

					for (int i = 0; i < Strings.DONATION_DATES.length; i++)
						obj.put(Strings.DONATION_DATES[i], (Date) result
								.getProperty(Strings.DONATION_DATES[i]));

					for (int i = 0; i < Strings.DONATION_BOOLEANS.length; i++)
						obj.put(Strings.DONATION_BOOLEANS[i], (boolean) result
								.getProperty(Strings.DONATION_BOOLEANS[i]));

					for (int i = 0; i < Strings.DONATION_KEYS.length; i++)
						obj.put(Strings.DONATION_KEYS[i], (Key) result
								.getProperty(Strings.DONATION_KEYS[i]));
					
					//TODO 
					obj.put("url", (String)Strings.UNHANDLED_DONATE_URL);
					obj.put(Strings.PARAM_NAME,Strings.KEY);
					obj.put(Strings.PARAM_VALUE, KeyFactory.keyToString(result.getKey()));
					
					ja.put(obj);
				
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}
		return ja;
	}

	JSONArray checkoutDonations(Key key) {

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		JSONArray ja = new JSONArray();
		Entity volunteer = null;
		try {
			volunteer = datastore.get(key);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}

		// make a filter for handled requests
		Filter beingHandledDonation = new FilterPredicate("isBeingHandled",
				FilterOperator.EQUAL, true);
		// handled but not checked out
		Filter notFullyHandledDonation = new FilterPredicate("isHandled",
				FilterOperator.EQUAL, false);
		
		Filter finalFilter = CompositeFilterOperator.and(notFullyHandledDonation,
				beingHandledDonation);

		Query q = new Query("Donation").setFilter(finalFilter);
		PreparedQuery pq = datastore.prepare(q);

		String volunteerCity = (String) volunteer.getProperty("city");
		String volunteerState = (String) volunteer.getProperty("state");
		
		for (Entity result : pq.asIterable()) {
			// get the donor city and state
			Key donorKey = (Key)result.getProperty("donatedBy");
			Entity donor = null;
			try {
				donor = datastore.get(donorKey);
			} catch (EntityNotFoundException e) {
				e.printStackTrace();
			}
			
			String donorCity = (String) donor.getProperty("city");
			String donorState = (String) donor.getProperty("state");
			
			
			if (volunteerCity.equals(donorCity)
					|| (donorCity.equals("") && volunteerState
							.equals(donorState))) {
				try {
					JSONObject obj = new JSONObject();
					
					for (int i = 0; i < Strings.DONATION_STRINGS.length; i++)
						obj.put(Strings.DONATION_STRINGS[i], (String) result
								.getProperty(Strings.DONATION_STRINGS[i]));
					
					for (int i = 0; i < Strings.DONATION_INTS.length; i++)
						obj.put(Strings.DONATION_INTS[i], (long) result
								.getProperty(Strings.DONATION_INTS[i]));

					for (int i = 0; i < Strings.DONATION_DATES.length; i++)
						obj.put(Strings.DONATION_DATES[i], (Date) result
								.getProperty(Strings.DONATION_DATES[i]));

					for (int i = 0; i < Strings.DONATION_BOOLEANS.length; i++)
						obj.put(Strings.DONATION_BOOLEANS[i], (boolean) result
								.getProperty(Strings.DONATION_BOOLEANS[i]));

					for (int i = 0; i < Strings.DONATION_KEYS.length; i++)
						obj.put(Strings.DONATION_KEYS[i], (Key) result
								.getProperty(Strings.DONATION_KEYS[i]));
					//TODO 
					obj.put("url", Strings.UNCHECKED_DONATE_URL);
					obj.put(Strings.PARAM_NAME,Strings.KEY);
					obj.put(Strings.PARAM_VALUE, KeyFactory.keyToString(result.getKey()));
					ja.put(obj);
				
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}
		return ja;
	}

}
