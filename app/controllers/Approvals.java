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
		List<Operational> bookings = (List<Operational>) Operational.find("byBstatus", "New").asList();
		
		List<Operational> berthings = (List<Operational>) Operational.find("byBstatus", "Berthing").asList();
		
		List<Operational> departures = (List<Operational>) Operational.find("byBstatus", "Departure").asList();
		
		List<Operational> finals = (List<Operational>) Operational.find("byBstatus", "Final").asList();
		
		render(bookings, berthings, departures, finals);
	}
	
	public static void approval(Long id, String bstatus) {
		Operational operational = Operational.findById(id);
		if(bstatus == "New") {
			if(params.get("approve") != null) {
				operational.approvalBooking(true);
			} else {
				operational.approvalBooking(false);
			}
		} else if(bstatus == "Berthing") {
			if(params.get("approve") != null) {
				operational.approvalBerthing(true);
			} else {
				operational.approvalBerthing(false);
			}
		} else if(bstatus == "Departure") {
			if(params.get("approve") != null) {
				operational.approvalDeparture(true);
			} else {
				operational.approvalDeparture(false);
			}
		} else {
			if(params.get("approve") != null) {
				operational.approvalFinal(true);
			} else {
				operational.approvalFinal(false);
			}
		}
		operational.save();
	}

}
