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
public class FinalCharges extends Controller {
	
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
				finalcharge.booking.addAdditional(additional.get(i).name, additional.get(i).cost);
				i++;
			}
			Cache.set("finalcharge_" + id, finalcharge, "1mn");
			form(id);
		} else if(params.get("save") !=null) {
			finalcharge = Operational.findById(id);
			finalcharge.oFinalCharge(actualTugIn, actualTugOut, cargo, cargoWeight);
			int i = 0;
			while(i < additional.size()) {
				finalcharge.booking.addAdditional(additional.get(i).name, additional.get(i).cost);
				i++;
			}
			finalcharge.save();
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
