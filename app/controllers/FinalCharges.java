package controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import play.cache.Cache;
import play.mvc.Controller;
import models.Additional;
import models.Agent;
import models.Customer;
import models.Operational;
import models.Port;
import models.Vessel;


public class FinalCharges extends Controller {
	
	public static void index() {
		List<Operational> finalcharges = (List<Operational>) Operational.find("byBStatus", 
				"Final").asList();
		render(finalcharges);
	}
	
	public static void form(Long id) {
		
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
	
	public static void save(Long id, Double actualTugIn, Double actualTugOut,
			String cargo, int cargoWeight, List<Additional> additional) throws ParseException {
		
		Operational finalcharge;
		if(params.get("calculate") != null) {
			finalcharge = Operational.findById(id);
			finalcharge.oFinalCharge(actualTugIn, actualTugOut, cargo, cargoWeight);
			int i = 0;
			while(i < additional.size()) {
				finalcharge.booking.addAdditional(additional.get(i).name, 
						additional.get(i).date, additional.get(i).cost);
				i++;
			}
			Cache.set("finalcharge_" + id, finalcharge, "1mn");
			form(id);
		} else if(params.get("save") !=null) {
			finalcharge = Operational.findById(id);
			finalcharge.oFinalCharge(actualTugIn, actualTugOut, cargo, cargoWeight);
			int i = 0;
			while(i < additional.size()) {
				finalcharge.booking.addAdditional(additional.get(i).name, 
						additional.get(i).date, additional.get(i).cost);
				i++;
			}
			finalcharge.save();
			form(id);
		}
	}
}
