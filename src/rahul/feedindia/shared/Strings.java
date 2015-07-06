package rahul.feedindia.shared;

import java.util.Collection;

public class Strings {
	public final static String USER = "user";
	public final static String SUCCESS = "success";
	public final static String URL = "url";
	public final static String KEY = "key";
	public final static String PARAM_NAME = "param_name";
	public final static String PARAM_VALUE = "param_value";
	public final static String MESSAGE = "message";
	public final static String UNHANDLED_REQUESTS= "unhandled_requests";
	public final static String UNHANDLED_DONATIONS= "unhandled_donations";
	public final static String UNCHECKED_REQUESTS = "unchecked_requests";
	public final static String UNCHECKED_DONATIONS = "unchecked_donations";
	
	//ENTITYNAME_DATATYPE =  {"colname_for_that_datatype1","colname2"}
	
	// Request entity
	public final static String[] REQUEST_STRINGS = {"address","city","name","phone","state","zipcode"};
	public final static String[] REQUEST_DATES = {"handledOn","requestedForDate","requestedOn","responsibilityTakenOn" };
	public final static String[] REQUEST_BOOLEANS = {"isBeingHandled","isHandled","isOrganization"};
	public final static String[] REQUEST_KEYS = {"handledBy"};
	public final static String[] REQUEST_INTS = {"foodQuantity"};

	// Donation entity
	public final static String[] DONATION_STRINGS = {"foodType"};
	public final static String[] DONATION_DATES = {"handledOn","responsibilityTakenOn"};
	public final static String[] DONATION_BOOLEANS = {"isHandled","isBeingHandled"};
	public final static String[] DONATION_KEYS = {"donatedBy","handledBy",};
	public final static String[] DONATION_INTS = {"foodQuantity","money"};
	
	// User entity
	public final static String[] USER_STRINGS = {"address","city","fullName","hashedPassword","phone","state","zipcode"};
	public final static String[] USER_DATES = {"registeredOn"};
	public final static String[] USER_BOOLEANS = {"isDonor","isVolunteer","isOrganization"};

	// some urls to reference
	public final static String UNHANDLED_REQUEST_URL = "http://feedindia-api/unhandledrequest";
	public final static String UNCHECKED_REQUEST_URL = "http://feedindia-api/uncheckedrequest";
	public final static String UNHANDLED_DONATE_URL = "http://feedindia-api/unhandleddonation";
	public final static String UNCHECKED_DONATE_URL = "http://feedindia-api/uncheckeddonation";

	// some message strings for error and success message
	public final static String NOT_LOGGED_IN = "User not logged in";
	public final static String ENTITY_NOT_FOUND = "Entity not found";
	public final static String USER_EXISTS = "User with same email already exists";
	public final static String WRONG_PASSWORD = "Wrong password";
	public static final String ALREADY_HANDLED = "This is already handled by another user";
}
