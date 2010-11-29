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


public class Berthings extends Controller {
	
	public static void index() {
		List<Operational> berthings = (List<Operational>) Operational.find("byBStatus", 
				"Berthing").asList();
		render(berthings);
	}
	
	public static void form(Long id) {
		
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
	
	public static void save(Long id, Date ata, Date etd, Double berthTugIn, 
			String cargo, int cargoWeight) throws ParseException {
		
		Operational berthing;
		if(params.get("calculate") != null) {
			berthing = Operational.findById(id);
			berthing.oBerthing(ata, etd, berthTugIn, cargo, cargoWeight);
			Cache.set("berthing_" + id, berthing, "1mn");
			form(id);
		} else if(params.get("save") !=null) {
			berthing = Operational.findById(id);
			berthing.oBerthing(ata, etd, berthTugIn, cargo, cargoWeight);
			berthing.save();
			form(id);
		}
	}
}
