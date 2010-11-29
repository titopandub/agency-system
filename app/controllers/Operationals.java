package controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import play.cache.Cache;
import play.mvc.Controller;
import models.Agent;
import models.Customer;
import models.Operational;
import models.Port;
import models.Vessel;


public class Operationals extends Controller {
	
	public static void index() {
		List<Operational> bookings = (List<Operational>) Operational.find("byBStatus", 
				"Booking").asList();
		
		List<Operational> berthings = (List<Operational>) Operational.find("byBStatus", 
		"Booking").asList();
		
		List<Operational> departures = (List<Operational>) Operational.find("byBStatus", 
		"Berthing").asList();
		
		List<Operational> finalcharges = (List<Operational>) Operational.find("byBStatus", 
		"Departure").asList();
		render(bookings, berthings, departures, finalcharges);
	}

}
