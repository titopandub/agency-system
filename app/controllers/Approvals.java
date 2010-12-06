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


public class Approvals extends Controller {
	
	public static void index() {
		List<Operational> bookings = (List<Operational>) Operational.find("byStatus", "New").asList();
		
		List<Operational> berthings = (List<Operational>) Operational.find("byStatus", "Berthing").asList();
		
		List<Operational> departures = (List<Operational>) Operational.find("byStatus", "Departure").asList();
		
		List<Operational> finals = (List<Operational>) Operational.find("byStatus", "Final").asList();
		
		render(bookings, berthings, departures, finals);
	}
	
	public static void formBooking(Long id) {
		Operational booking = Cache.get("booking_" + id, Operational.class);
		if(booking == null) {
			if (id != null) {
				booking = Operational.findById(id);
				render(booking);
			}
			render(booking);
		} else {
			render(booking);
		}
	}
	
	public static void formBerthing(Long id) {
		Operational berthing = Cache.get("berthing_" + id, Operational.class);
		if(berthing == null) {
			if (id != null) {
				berthing = Operational.findById(id);
				render(berthing);
			}
			render(berthing);
		} else {
			render(berthing);
		}
	}
	
	public static void formDeparture(Long id) {
		Operational departure = Cache.get("departure_" + id, Operational.class);
		if(departure == null) {
			if (id != null) {
				departure = Operational.findById(id);
				render(departure);
			}
			render(departure);
		} else {
			render(departure);
		}
	}
	
	public static void formFinal(Long id) {
		Operational finalcharge = Cache.get("finalcharge_" + id, Operational.class);
		if(finalcharge == null) {
			if (id != null) {
				finalcharge = Operational.findById(id);
				render(finalcharge);
			}
			render(finalcharge);
		} else {
			render(finalcharge);
		}
	}
	
	public static void approval(Long id, String status) {
		Operational operational = Operational.findById(id);
		System.out.println(status);
		if(status.equals("New")) {
			if(params.get("approve") != null) {
				operational.approvalBooking(true);
			} else {
				operational.approvalBooking(false);
			}
		} else if(status.equals("Berthing")) {
			if(params.get("approve") != null) {
				operational.approvalBerthing(true);
			} else {
				operational.approvalBerthing(false);
			}
		} else if(status.equals("Departure")) {
			if(params.get("approve") != null) {
				operational.approvalDeparture(true);
			} else {
				operational.approvalDeparture(false);
			}
		} else if(status.equals("Final")) {
			if(params.get("approve") != null) {
				operational.approvalFinal(true);
			} else {
				operational.approvalFinal(false);
			}
		}
		operational.save();
		index();
	}

}
