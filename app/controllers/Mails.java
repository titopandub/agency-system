package controllers;

import models.*;
import play.mvc.Mailer;

public class Mails extends Mailer {
	
	public static void masterApproval(User user, String masterData) {
		setFrom("Agency <agency@cosco-ogs.com>");
		setSubject("Approval for " + masterData);
		addRecipient(user.email);
		send(user);
	}
	
	public static void operationalApproval(User sender, User receiver, Operational ops) {
		setFrom(sender.email);
		setSubject("Approval for Operational Activity");
		addRecipient(receiver.email);
		send(receiver, ops);
	}
	
	public static void operationalApproved(User sender, String receiver) {
		setFrom(sender.email);
		setSubject("Your Operational Activity has been Approved");
		addRecipient(receiver);
		send(receiver);
	}
	
	public static void operationalToOwner(String sender, String receiver, Operational operational) {
		setFrom(sender);
		setSubject("Estimate Port Disbursement");
		addRecipient(receiver);
		addCc("agency@cosco-ogs.com");
		send(receiver, operational);
	}
	
	public static void operationalRejected(User sender, User receiver) {
		setFrom(sender.email);
		setSubject("Your Operational Activity has been Rejected");
		addRecipient(receiver.email);
		send(receiver);
	}

}
