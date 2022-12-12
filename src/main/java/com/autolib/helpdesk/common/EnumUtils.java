package com.autolib.helpdesk.common;

public class EnumUtils {

	public enum ServiceUnder {
		NotInAnyService, Warranty, AMC, ServiceCall
	}

	public enum TicketStatus {
		Raised, Assigned, Rejected, Marked_As_Completed, Closed, Hold, Waiting_For_Client_Reply, ReOpened
	}

	public enum TicketPriority {
		NotPreferred, High, Medium, Low
	}

	public enum WorkingStatus {
		W, L, OD, NONE
	}

}
