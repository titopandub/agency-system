package controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.code.morphia.query.OrBuilder;
import com.google.code.morphia.query.Query;

import play.cache.Cache;
import play.mvc.Controller;
import models.Agent;
import models.Customer;
import models.Operational;
import models.Port;
import models.Vessel;


public class Operationals extends Controller {
	
	public static void index() {
		Query q1 = Operational.createQuery(); // create a Query
	    OrBuilder or1 = q1.or(); // get Or query builder
	    or1.add().field("bstatus").contains("New"); // select bstatus contains "New"
	    or1.add().field("bstatus").contains("Booking Rejected"); // or bstatus contains "Booking Rejected"
	    List<Operational> l1 = new ArrayList<Operational>();
	    l1.addAll(q1.asList());
	    List<Operational> bookings = l1; // sort the result by postedAt desc order and fetch at most 10 items
		
	    Query q2 = Operational.createQuery();
	    OrBuilder or2 = q2.or();
	    or2.add().field("bstatus").contains("Berthing");
	    or2.add().field("bstatus").contains("Booking Approved");
	    or2.add().field("bstatus").contains("Berthing Rejected");
	    List<Operational> l2 = new ArrayList<Operational>();
	    l2.addAll(q2.asList());
		List<Operational> berthings = l2;
		
		Query q3 = Operational.createQuery();
	    OrBuilder or3 = q3.or();
	    or3.add().field("bstatus").contains("Departure");
	    or3.add().field("bstatus").contains("Berthing Approved");
	    or3.add().field("bstatus").contains("Departure Rejected");
	    List<Operational> l3 = new ArrayList<Operational>();
	    l3.addAll(q3.asList());
		List<Operational> departures = l3;
		
		Query q4 = Operational.createQuery();
	    OrBuilder or4 = q4.or();
	    or4.add().field("bstatus").contains("Final");
	    or4.add().field("bstatus").contains("Departure Approved");
	    or4.add().field("bstatus").contains("Final Rejected");
	    List<Operational> l4 = new ArrayList<Operational>();
	    l4.addAll(q4.asList());
		List<Operational> finals = l4;
		
		render(bookings, berthings, departures, finals);
	}

}
