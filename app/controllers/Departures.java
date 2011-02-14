package controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import play.cache.Cache;
import play.mvc.Controller;
import play.mvc.With;
import models.Additional;
import models.Agent;
import models.Customer;
import models.Operational;
import models.Port;
import models.User;
import models.Vessel;

@With(Secure.class)
public class Departures extends Controller {
	
	public static void form(Long id) {
		
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
	
	public static void save(Long id, Date atd, int quay, Double departTugOut, 
			String cargo, int cargoWeight, List<Additional> additional) throws ParseException {
		
		Operational departure;
		if(params.get("calculate") != null) {
			departure = Operational.findById(id);
			departure.oDeparture(atd, quay, departTugOut, cargo, cargoWeight);
			int i = 0;
			while(i < additional.size()) {
				departure.booking.addAdditional(additional.get(i).name, additional.get(i).cost);
				i++;
			}
			Cache.set("departure_" + id, departure, "1mn");
			form(id);
		} else if(params.get("save") !=null) {
			departure = Operational.findById(id);
			departure.oDeparture(atd, quay, departTugOut, cargo, cargoWeight);
			int i = 0;
			while(i < additional.size()) {
				departure.booking.addAdditional(additional.get(i).name, additional.get(i).cost);
				i++;
			}
			departure.save();
			User sender = User.find("byUsername", Security.connected()).first();
			List<User> receivers = User.filter("isManager", true).asList();
			int j = 0;
			while(j < receivers.size()) {
				Mails.operationalApproval(sender, receivers.get(j));
			}
			form(id);
		}
	}
}
