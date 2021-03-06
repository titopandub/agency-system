package controllers;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import models.Additional;
import models.Agent;
import models.Customer;
import models.Operational;
import models.Owner;
import models.Port;
import models.User;
import models.Vessel;
import play.cache.Cache;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class Calculators extends Controller {
	
	public static void listVessel(String term) {
		Pattern regExp = Pattern.compile(term + ".*", Pattern.CASE_INSENSITIVE);
		List<Vessel> vessels = Vessel.filter("name", regExp).asList();
		renderJSON(vessels);
	}
	
	public static void listOwner(String term) {
		Pattern regExp = Pattern.compile(term + ".*", Pattern.CASE_INSENSITIVE);
		List<Owner> owners = Owner.filter("name", regExp).asList();
		renderJSON(owners);
	}
	
	public static void form(Long id) {
		
		List<Port> ports = Port.find().asList();
		List<Customer> customers = Customer.find().asList();
		List<Agent> agents = Agent.find().asList();
		
		Operational booking = Cache.get("booking_" + id, Operational.class);
		if(booking == null) {
			if (id != null) {
				booking = Operational.findById(id);
				render(booking, ports, customers, agents);
			}
			render(ports, customers, agents);
		} else {
			render(booking, ports, customers, agents);
		}
	}
	
	public static void calculate(Long id, String name, int grt, String voyage, Long portId, 
			String ownerName, String ownerPIC, String ownerEmail, Long customerId, Long agentId, 
			String statusAgent, Long subAgentId, Date eta, Date etd, int quay, Double bookTugIn, 
			Double bookTugOut, String cargo, int cargoWeight, List<Additional> additional) {
		
		Port port = Port.findById(portId);
		Customer customer = Customer.findById(customerId);
		Agent agent = Agent.findById(agentId), subAgent = Agent.findById(subAgentId);
		Operational booking;
		Vessel vessel = Vessel.find("byName", name).first();
		Owner owner = Owner.find("byName", ownerName).first();
		User sender = User.find("byUsername", Security.connected()).first();
		
		if(params.get("calculate") != null) {
			if(vessel == null && ownerName == null) {
				owner = new Owner(ownerName, ownerPIC, ownerEmail);
				owner.save();
				vessel = new Vessel(name, grt, owner);
				vessel.save();
			} else if(vessel != null && owner == null) {
				owner = new Owner(ownerName, ownerPIC, ownerEmail);
				owner.save();
				vessel.owner = owner;
				vessel.save();
			} else if(vessel == null && ownerName != null) {
				vessel = new Vessel(name, grt, owner);
				vessel.save();
			} else if(vessel != null && ownerName != null) {
				vessel.owner = owner;
				vessel.save();
			}
			if(id != null) {
				booking = Operational.findById(id);
				booking.vessel = vessel;
				booking.port = port;
				booking.voyage = voyage;
				booking.customer = customer;
				booking.agent = agent;
				booking.statusAgent = statusAgent;
				booking.subAgent = subAgent;
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
			booking.status = "Prospect";
			Cache.set("booking_" + id, booking, "1mn");
			form(id);
		} else if(params.get("save") !=null) {
			if(vessel == null && ownerName == null) {
				owner = new Owner(ownerName, ownerPIC, ownerEmail);
				owner.save();
				vessel = new Vessel(name, grt, owner);
				vessel.save();
			} else if(vessel != null && owner == null) {
				owner = new Owner(ownerName, ownerPIC, ownerEmail);
				owner.save();
				vessel.owner = owner;
				vessel.save();
			} else if(vessel == null && ownerName != null) {
				vessel = new Vessel(name, grt, owner);
				vessel.save();
			} else if(vessel != null && ownerName != null) {
				vessel.owner = owner;
				vessel.save();
			}
			if(id != null) {
				booking = Operational.findById(id);
				booking.vessel = vessel;
				booking.port = port;
				booking.voyage = voyage;
				booking.customer = customer;
				booking.agent = agent;
				booking.statusAgent = statusAgent;
				booking.subAgent = subAgent;
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
			booking.createdBy = sender.username;
			booking.status = "Prospect";
			booking.save();
			Cache.delete("booking_" + id);
			List<User> receivers = User.filter("isManager", true).asList();
			int i = 0;
			while(i < receivers.size()) {
				Mails.operationalApproval(sender, receivers.get(i), booking);
				i++;
			}
			redirect(request.controller + ".form", booking._key());
		}
	}
	
	public static void delete(Long id) {
		Operational booking = Operational.findById(id);
		booking.delete();
		redirect("Operationals.index");
	}
}
