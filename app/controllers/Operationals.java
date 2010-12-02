package controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
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
		List<Operational> bookings = (List<Operational>) Operational.find("byBstatus", "New").asList();
		bookings.addAll((List<Operational>) Operational.find("byBstatus", "Booking Rejected").asList());
		
		List<Operational> berthings = (List<Operational>) Operational.find("byBstatus", "Berthing").asList();
		berthings.addAll((List<Operational>) Operational.find("byBstatus", "Booking Approved").asList());
		berthings.addAll((List<Operational>) Operational.find("byBstatus", "Berthing Rejected").asList());
		
		List<Operational> departures = (List<Operational>) Operational.find("byBstatus", "Departure").asList();
		departures.addAll((List<Operational>) Operational.find("byBstatus", "Berthing Approved").asList());
		departures.addAll((List<Operational>) Operational.find("byBstatus", "Departure Rejected").asList());
		
		List<Operational> finals = (List<Operational>) Operational.find("byBstatus", "Final").asList();
		finals.addAll((List<Operational>) Operational.find("byBstatus", "Departure Approved").asList());
		finals.addAll((List<Operational>) Operational.find("byBstatus", "Final Rejected").asList());
		
		render(bookings, berthings, departures, finals);
	}

}
