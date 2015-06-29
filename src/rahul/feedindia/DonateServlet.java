package rahul.feedindia;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
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
			int foodQuantity = Integer.parseInt(checkNull(escapeHtml(req.getParameter("foodQuantity"))));
			int money = Integer.parseInt(checkNull(escapeHtml(req.getParameter("money"))));
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
			Date responsibiltyTakenOn = null; // the day volunteer accepts the task of collectinng the donation
			donation.setProperty("responsibiltyTakenOn",responsibiltyTakenOn);
			donation.setProperty("isHandled",false);
			Date handledOn = null;
			donation.setProperty("handledOn",handledOn);
		}
		// The user has not logged in
		else {
			try {
				// TODO error msg
				obj.put("success", false);
				obj.put("user", "");
			} catch (JSONException e) {
				e.printStackTrace();
			}

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
