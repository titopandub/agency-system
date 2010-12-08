package controllers;

import java.util.Date;
import java.util.List;

import models.Operational;

import play.modules.morphia.Model.MorphiaQuery;
import play.mvc.Controller;

public class Reports extends Controller {
	
	public static Date date = new Date();
	public static void upcoming() {
//		List<Operational> berthings = Operational.find("bystatus", "Berthing").asList();
//		berthings.addAll((List<Operational>) Operational.find("bystatus", "Booking Approved").asList());
//		berthings.addAll((List<Operational>) Operational.find("bystatus", "Berthing Rejected").asList());
//		render(berthings);
		
		MorphiaQuery q2 = Operational.find(); // create a Query
	    q2.or(q2.criteria("status").contains("Berthing"), 
	    		q2.criteria("status").contains("Booking Approved"), 
	    		q2.criteria("status").contains("Berthing Rejected"));
	    List<Operational> berthings = q2.asList();
	    render(berthings);
	}
	
	public static void monthly() {
		List<Operational> monthly = Operational.filter("status =", 
				"Departure").filter("status =", "Final Approved").asList();
		render(monthly);
	}

}
