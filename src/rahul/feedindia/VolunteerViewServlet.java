package rahul.feedindia;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query.FilterOperator;

public class VolunteerViewServlet extends HttpServlet {
	private static final long serialVersionUID = 8730444127442927240L;
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		// Create a session object if it is already not created.
		HttpSession session = req.getSession(true);

		// user key is pushed onto the session
		// check to see if user key is there meaning the user is logged in
		PrintWriter out=null;
		try {
			out = resp.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Key ukey;
		if (((Key) session.getAttribute("key")) != null) {
			ukey = (Key) session.getAttribute("key");
			
			// show volunteer the unhandled requests as an array of unhandled requests
			// that are in the same city or state
			 makeUnhandledRequests(ukey,resp);
		}
		else {	// the user is not logged in
			//TODO not loggedin
			out.println("not logged in");
		}
	}
	
	void makeUnhandledRequests(Key key,HttpServletResponse resp){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		// make a filter for unhandled requests
		Filter unhandledRequestFilter = new FilterPredicate("isBeingHandled",
										FilterOperator.EQUAL,false);
		
		Entity volunteer = null;
		try {
			volunteer = datastore.get(key);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}
		
		String volunteerCity = (String)volunteer.getProperty("city");
		
		// make a filter for same city
		Filter sameCityFilter = new FilterPredicate("city",
								FilterOperator.EQUAL,volunteerCity);
		
		//Use CompositeFilter to combine multiple filters
		Filter unhandledAndSameCityFilter = CompositeFilterOperator.
				and(unhandledRequestFilter, sameCityFilter);
		
		// Use class Query to assemble a query
		Query q = new Query("Request").setFilter(unhandledAndSameCityFilter);

		// Use PreparedQuery interface to retrieve results
		PreparedQuery pq = datastore.prepare(q);
		
		PrintWriter out=null;
		try {
			out = resp.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (Entity result : pq.asIterable()) {
			String firstName = (String) result.getProperty("name");		
			out.println(firstName);

		}
	}
}
