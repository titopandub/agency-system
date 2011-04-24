package controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import play.cache.Cache;
import play.modules.morphia.Model.MorphiaQuery;
import play.mvc.Controller;
import play.mvc.With;
import models.*;

@Check("isManager")
@With(Secure.class)
public class Approvals extends Controller {
	
	public static void index() {
		MorphiaQuery q1 = Operational.createQuery(); // create a Query
	    q1.or(q1.criteria("status").equal("Prospect"), q1.criteria("status").equal("New"), q1.criteria("status").equal("Berthing"), 
	    		q1.criteria("status").equal("Departure"), q1.criteria("status").equal("Final"));
	    List<Operational> operations = q1.asList();
	    
		render(operations);
	}
	
	public static void master() {
		List<Agent> agents = Agent.filter("isApproved", false).asList();
	    List<Customer> customers = Customer.filter("isApproved", false).asList();
	    List<Owner> owners = Owner.filter("isApproved", false).asList();
	    List<Port> ports = Port.filter("isApproved", false).asList();
	    List<Vessel> vessels = Vessel.filter("isApproved", false).asList();
	    
	    render(agents, customers, owners, ports, vessels);
	}
	
	public static void formAgent(Long id) {
		Agent agent = Agent.findById(id);
		render(agent);
	}
	
	public static void formCustomer(Long id) {
		Customer customer = Customer.findById(id);
		render(customer);
	}
	
	public static void formOwner(Long id) {
		Owner own = Owner.findById(id);
		render(own);
	}
	
	public static void formPort(Long id) {
		Port port = Port.findById(id);
		render(port);
	}
	
	public static void formVessel(Long id) {
		Vessel vessel = Vessel.findById(id);
		render(vessel);
	}
	
	public static void formCalculate(Long id) {
		Operational booking = Cache.get("booking_" + id, Operational.class);
		if(booking == null) {
			if (id != null) {
				booking = Operational.findById(id);
				render(booking);
			}
			render(booking);
		} else {
			render(booking);
		}
	}
	
	public static void formBooking(Long id) {
		Operational booking = Cache.get("booking_" + id, Operational.class);
		if(booking == null) {
			if (id != null) {
				booking = Operational.findById(id);
				render(booking);
			}
			render(booking);
		} else {
			render(booking);
		}
	}
	
	public static void formBerthing(Long id) {
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
	
	public static void formDeparture(Long id) {
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
	
	public static void formFinal(Long id) {
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
	
	public static void approval(Long id, String status) {
		Operational operational = Operational.findById(id);
		System.out.println(status);
		if(status.equals("Prospect")) {
			if(params.get("approve") != null) {
				operational.approvalCalculate(true);
				Mails.operationalToOwner("agency@cosco-ogs.com", operational.vessel.owner.email, operational);
			} else {
				operational.approvalCalculate(false);
			}
		} else if(status.equals("New")) {
			if(params.get("approve") != null) {
				operational.approvalBooking(true);
			} else {
				operational.approvalBooking(false);
			}
		} else if(status.equals("Berthing")) {
			if(params.get("approve") != null) {
				operational.approvalBerthing(true);
			} else {
				operational.approvalBerthing(false);
			}
		} else if(status.equals("Departure")) {
			if(params.get("approve") != null) {
				operational.approvalDeparture(true);
			} else {
				operational.approvalDeparture(false);
			}
		} else if(status.equals("Final")) {
			if(params.get("approve") != null) {
				operational.approvalFinal(true);
			} else {
				operational.approvalFinal(false);
			}
		}
		operational.save();
		User sender = User.find("byUsername", Security.connected()).first();
		Mails.operationalApproved(sender, "agency@cosco-ogs.com", operational);
		index();
	}
	
	public static void approvalBerthingDeparture(Long id) {
		Operational operational = Operational.findById(id);
		if(operational.status.equals("Berthing")) {
			operational.approvalBerthing(true);
		} else if(operational.status.equals("Departure")) {
			operational.approvalDeparture(true);
		}
		operational.save();
		User sender = User.find("byUsername", Security.connected()).first();
		Mails.operationalApproved(sender, "agency@cosco-ogs.com", operational);
		redirect("Operationals.index");
	}
	
	public static void approvalAgent(Long id) {
		Agent agent = Agent.findById(id);
		if(params.get("approve") != null) {
			agent.approve(true);
		} else {
			agent.approve(false);
		}
		agent.save();
		master();
	}
	
	public static void approvalCustomer(Long id) {
		Customer customer = Customer.findById(id);
		if(params.get("approve") != null) {
			customer.approve(true);
		} else {
			customer.approve(false);
		}
		customer.save();
		master();
	}
	
	public static void approvalOwner(Long id) {
		Owner owner = Owner.findById(id);
		if(params.get("approve") != null) {
			owner.approve(true);
		} else {
			owner.approve(false);
		}
		owner.save();
		master();
	}
	
	public static void approvalPort(Long id) {
		Port port = Port.findById(id);
		if(params.get("approve") != null) {
			port.approve(true);
		} else {
			port.approve(false);
		}
		port.save();
		master();
	}
	
	public static void approvalVessel(Long id) {
		Vessel vessel = Vessel.findById(id);
		if(params.get("approve") != null) {
			vessel.approve(true);
		} else {
			vessel.approve(false);
		}
		vessel.save();
		master();
	}

}
