package controllers;

import groovy.lang.Singleton;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
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
public class Bookings extends Controller {
		
	public static void form(Long id) {
		List<Vessel> vessels = Vessel.find().asList();
		List<Port> ports = Port.find().asList();
		List<Customer> customers = Customer.find().asList();
		List<Agent> agents = Agent.find().asList();
		
		Operational booking = Cache.get("booking_" + id, Operational.class);
		if(booking == null) {
			if (id != null) {
				booking = Operational.findById(id);
				render(booking, vessels, ports, customers, agents);
			}
			render(vessels, ports, customers, agents);
		} else {
			render(booking, vessels, ports, customers, agents);
		}
	}
	
	public static void save(Long id, Long vesselId, String voyage, Long portId,
			Long customerId, Long agentId, String statusAgent, 
			Long subAgentId, Date eta, Date etd, int quay, Double bookTugIn, 
			Double bookTugOut, String cargo, int cargoWeight, List<Additional> additional) 
	throws ParseException {
		
		Vessel vessel = Vessel.findById(vesselId);
		Port port = Port.findById(portId);
		Customer customer = Customer.findById(customerId);
		Agent agent = Agent.findById(agentId), subAgent = Agent.findById(subAgentId);
		Operational booking;
		if(params.get("calculate") != null) {
			if(id != null) {
				booking = Operational.findById(id);
				booking.vessel = vessel;
				booking.voyage = voyage;
				booking.port = port;
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
				booking = new Operational(vessel, voyage, port, customer, 
						agent, statusAgent, subAgent);
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
			if(id != null) {
				booking = Operational.findById(id);
				booking.vessel = vessel;
				booking.voyage = voyage;
				booking.port = port;
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
				booking = new Operational(vessel, voyage, port, customer, 
						agent, statusAgent, subAgent);
				booking.oBooking(eta, etd, quay, bookTugIn, bookTugOut, cargo, cargoWeight);
				
				int i = 0;
				while(i < additional.size()) {
					booking.booking.addAdditional(additional.get(i).name, additional.get(i).cost);
					i++;
				}
			}
			booking.save();
			User sender = User.find("byUsername", Security.connected()).first();
			List<User> receivers = User.filter("isManager", true).asList();
			int i = 0;
			while(i < receivers.size()) {
				Mails.operationalApproval(sender, receivers.get(i));
			}
			Operationals.index();
		}
	}
}
