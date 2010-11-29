package controllers;

import java.util.Date;
import java.util.List;

import models.Operational;

import play.mvc.Controller;

public class Reports extends Controller {
	
	public static Date date = new Date();
	public static void upcoming() {
		List<Operational> berthings = (List<Operational>) Operational.filter("bstatus =", 
				"Booking").filter("booking.eta >", date).asList();
		render(berthings);
	}
	
	public static void monthly() {
		List<Operational> monthly = (List<Operational>) Operational.filter("bstatus =", 
				"Departure").filter("bstatus =", "Final").filter("departure.atd >", date).asList();
		render(monthly);
	}

}
