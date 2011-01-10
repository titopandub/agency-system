package controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.google.code.morphia.query.Query;

import play.cache.Cache;
import play.modules.morphia.Model.MorphiaQuery;
import play.mvc.Controller;
import models.Agent;
import models.Customer;
import models.Operational;
import models.Port;
import models.Vessel;


public class Operationals extends Controller {
	
	public static void index() {

//	    MorphiaQuery q1 = Operational.createQuery(); // create a Query
//	    q1.or(q1.criteria("status").contains("New"), q1.criteria("status").contains("Booking Rejected"));
//	    List<Operational> bookings = q1.asList();
//	    
//	    MorphiaQuery q2 = Operational.createQuery(); // create a Query
//	    q2.or(q2.criteria("status").equal("Berthing"), q2.criteria("status").contains("Booking Approved"), q2.criteria("status").contains("Berthing Rejected"));
//	    List<Operational> berthings = q2.asList();
//	    
//	    MorphiaQuery q3 = Operational.createQuery(); // create a Query
//	    q3.or(q3.criteria("status").equal("Departure"), q3.criteria("status").contains("Berthing Approved"),  q3.criteria("status").contains("Departure Rejected"));
//	    List<Operational> departures = q3.asList();
//	    
//	    MorphiaQuery q4 = Operational.createQuery(); // create a Query
//	    q4.or(q4.criteria("status").equal("Final"), q4.criteria("status").contains("Departure Approved"), q4.criteria("status").contains("Final Rejected"));
//	    List<Operational> finals = q4.asList();
	    
	    List<Operational> operations = Operational.find().asList();
		
		render(operations);
	}

}
