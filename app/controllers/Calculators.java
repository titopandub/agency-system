package controllers;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import models.Additional;
import models.Operational;
import models.Port;
import models.Vessel;
import play.cache.Cache;
import play.mvc.Controller;

public class Calculators extends Controller {
	
	public static void listVessel(String term) {
		Pattern regExp = Pattern.compile(term + ".*", Pattern.CASE_INSENSITIVE);
		List<Vessel> vessels = Vessel.filter("name", regExp).asList();
		renderJSON(vessels);
	}
	
	public static void form(Long id) {
		
		List<Port> ports = Port.find().asList();
		
		Operational booking = Cache.get("booking_" + id, Operational.class);
		if(booking == null) {
			if (id != null) {
				booking = Operational.findById(id);
				render(booking, ports);
			}
			render(ports);
		} else {
			render(booking, ports);
		}
	}
	
	public static void calculate(Long id, String name, int grt, String voyage, Long portId, 
			Date eta, Date etd, int quay, Double bookTugIn, Double bookTugOut, 
			String cargo, int cargoWeight, List<Additional> additional) {
		
		Port port = Port.findById(portId);
		Operational booking;
		Vessel vessel = Vessel.find("byName", name).first();
		
		if(params.get("calculate") != null) {
			if(vessel == null) {
				vessel = new Vessel(name, grt);
				vessel.save();
			}
			if(id != null) {
				booking = Operational.findById(id);
				booking.vessel = vessel;
				booking.port = port;
				booking.voyage = voyage;
				booking.oBooking(eta, etd, quay, bookTugIn, bookTugOut, cargo, cargoWeight);
				int i = 0;
				while(i < additional.size()) {
					booking.booking.addAdditional(additional.get(i).name, additional.get(i).cost);
					i++;
				}
			} else {
				booking = new Operational(vessel, port);
				booking.voyage = voyage;
				booking.oBooking(eta, etd, quay, bookTugIn, bookTugOut, cargo, cargoWeight);
				int i = 0;
				while(i < additional.size()) {
					booking.booking.addAdditional(additional.get(i).name, additional.get(i).cost);
					i++;
				}
			}
			Cache.set("booking_" + id, booking, "1mn");
			form(id);
		} else if(params.get("save") !=null) {
			if(vessel == null) {
				vessel = new Vessel(name, grt);
				vessel.save();
			}
			if(id != null) {
				booking = Operational.findById(id);
				booking.vessel = vessel;
				booking.port = port;
				booking.voyage = voyage;
				booking.oBooking(eta, etd, quay, bookTugIn, bookTugOut, cargo, cargoWeight);
				int i = 0;
				while(i < additional.size()) {
					booking.booking.addAdditional(additional.get(i).name, additional.get(i).cost);
					i++;
				}
			} else {
				booking = new Operational(vessel, port);
				booking.voyage = voyage;
				booking.oBooking(eta, etd, quay, bookTugIn, bookTugOut, cargo, cargoWeight);
				int i = 0;
				while(i < additional.size()) {
					booking.booking.addAdditional(additional.get(i).name, additional.get(i).cost);
					i++;
				}
			}
			booking.save();
			Cache.delete("booking_" + id);
			redirect(request.controller + ".form", booking._key());
		}
	}
}
