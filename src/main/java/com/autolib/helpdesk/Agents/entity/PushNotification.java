package com.autolib.helpdesk.Agents.entity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autolib.helpdesk.Agents.repository.AgentRepository;
import com.autolib.helpdesk.Agents.repository.AgentTokenRepository;
import com.autolib.helpdesk.HR.entity.LeaveApplied;
import com.autolib.helpdesk.Reminder.model.Reminder;
import com.autolib.helpdesk.Teams.model.TeamMembers;
import com.autolib.helpdesk.Teams.model.Teams;
import com.autolib.helpdesk.Teams.model.Tasks.TaskRequest;
import com.autolib.helpdesk.Tickets.model.Ticket;
import com.autolib.helpdesk.Tickets.model.TicketAttachment;
import com.autolib.helpdesk.Tickets.model.TicketReply;
import com.autolib.helpdesk.Tickets.model.TicketRequest;
import com.autolib.helpdesk.common.GlobalAccessUtil;
import com.google.api.client.util.Base64;
import com.google.api.client.util.StringUtils;

@Component
public class PushNotification {

	@Autowired
	AgentTokenRepository tokenRepo;

	@Autowired
	AgentRepository agentRepo;

	public int sentNotification(String tokenId, Map data, Map notification)
			throws UnsupportedEncodingException, MalformedURLException {
		Map<String, Object> testNotification = new HashMap<>();

//		notification.put("title",data.get("notification_type").toString());
//		notification.put("body",data.get("title").toString());
		notification.put("sound", "default");
//		notification.put("click_action", "OPEN_ACTIVITY_1");
		testNotification.put("to", tokenId);
		testNotification.put("content_available", true);
		testNotification.put("mutable_content", true);
		testNotification.put("data", data);
		testNotification.put("notification", notification);

		String result = "";
		String jsonValue = ""; // this string stors json value.
		try {

			URL url = new URL("https://fcm.googleapis.com/fcm/send");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(20000);
			conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			conn.setRequestProperty("Authorization",
					"key=AAAAcssJRHg:APA91bH1b1Mang36SNBQcUPPQCi02iQ6_-zyDeuRqQKvQ6kE6U2V1nElhlr1Fv6B7eJ3UJxeEiDdf70q13c103gJa4SJtxZHD2_ZWYrK4FpHS5Od58Bv3wbKd9r7uHpX5ToUk6nqBIZr");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			OutputStream os = conn.getOutputStream();
			jsonValue = GlobalAccessUtil.toJson(testNotification);
			os.write(jsonValue.getBytes("UTF-8")); // here json object (json format);
			os.close();
			System.out.println("Notification response code========" + conn.getResponseMessage());
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseMessage());
			} else {
				// read the response
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

				result = br.readLine();
				conn.disconnect();
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}

	public void sendTicketReplyPushNotify(TicketReply reply) {
		try {

			List<String> emailIds = new ArrayList<String>(
					Arrays.asList(reply.getTicket().getAssignedBy(), reply.getTicket().getAssignedTo(),
							reply.getTicket().getEmailId(), reply.getTicket().getCreatedBy()));

			System.out.println("sendTicketReplyPushNotify::" + emailIds.toString());
			emailIds.removeAll(Arrays.asList(null, reply.getReplyBy()));
			System.out.println(emailIds.toString());

			List<AgentToken> token = tokenRepo.findByEmployeeEmailIdIn(emailIds);

			if (!token.isEmpty()) {

				token.forEach(tkn -> {
					if (!tkn.getToken().isEmpty()) {
						Map<String, String> notification = new HashMap<>();

						Map<String, Object> data = new HashMap<>();

						data.put("id", reply.getId());
						data.put("ticket_id", reply.getTicket().getTicketId());
						data.put("ticket_reply_id", reply.getId());

						data.put("action", "VIEW_TICKET_REPLY");

						data.put("notification_type", "VIEW_TICKET_REPLY_NOTIFICATION");

						notification.put("title", "#" + reply.getTicket().getTicketId() + "- Reply - "
								+ StringUtils.newStringUtf8(Base64.decodeBase64(reply.getTicket().getSubject())));

						notification.put("body", reply.getReply());

						try {
							sentNotification(tkn.getToken(), data, notification);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});

			}

		} catch (Exception Ex) {
			Ex.printStackTrace();
		}
	}

	public void sendTicketUpdatePushNotify(TicketRequest ticketReq) {

		List<String> emailIds = new ArrayList<String>(
				Arrays.asList(ticketReq.getTicket().getAssignedBy(), ticketReq.getTicket().getAssignedTo(),
						ticketReq.getTicket().getEmailId(), ticketReq.getTicket().getCreatedBy()));

		System.out.println("sendTicketUpdatePushNotify::" + emailIds.toString());
		emailIds.removeAll(Arrays.asList(null, ticketReq.getTicket().getLastUpdatedBy()));
		System.out.println(emailIds.toString());

		List<AgentToken> token = tokenRepo.findByEmployeeEmailIdIn(emailIds);

		if (!token.isEmpty()) {

			token.forEach(tkn -> {
				if (!tkn.getToken().isEmpty()) {
					Map<String, String> notification = new HashMap<>();

					Map<String, Object> data = new HashMap<>();

					data.put("id", ticketReq.getTicket().getTicketId());
					data.put("ticket_id", ticketReq.getTicket().getTicketId());

					data.put("action", "VIEW_TICKET");

					data.put("notification_type", "VIEW_TICKET_NOTIFICATION");

					notification.put("title", "Ticket #" + ticketReq.getTicket().getTicketId() + "- Updated - "
							+ StringUtils.newStringUtf8(Base64.decodeBase64(ticketReq.getTicket().getSubject())));

					notification.put("body",
							StringUtils.newStringUtf8(Base64.decodeBase64(ticketReq.getTicket().getSubject())));

					try {
						sentNotification(tkn.getToken(), data, notification);
					} catch (Exception Ex) {
						Ex.printStackTrace();
					}
				}
			});

		}
	}

	public void sendTicketAttachmentPushNotify(Ticket ticket, TicketAttachment ta) {

		List<String> emailIds = new ArrayList<String>(Arrays.asList(ticket.getAssignedBy(), ticket.getAssignedTo(),
				ticket.getEmailId(), ticket.getCreatedBy()));

		System.out.println("sendTicketAttachmentPushNotify::" + emailIds.toString());
		emailIds.removeAll(Arrays.asList(null, ticket.getLastUpdatedBy()));
		System.out.println(emailIds.toString());

		List<AgentToken> token = tokenRepo.findByEmployeeEmailIdIn(emailIds);

		if (!token.isEmpty()) {

			token.forEach(tkn -> {
				if (!tkn.getToken().isEmpty()) {
					Map<String, String> notification = new HashMap<>();

					Map<String, Object> data = new HashMap<>();

					data.put("id", ticket.getTicketId());
					data.put("ticket_id", ticket.getTicketId());
					data.put("ticket_attachment_id", ta.getId());

					data.put("action", "VIEW_TICKET_ATTACHMENT");

					data.put("notification_type", "VIEW_TICKET_ATTACHMENT_NOTIFICATION");

					notification.put("title", "Ticket #" + ticket.getTicketId() + " - New Attachment Added");

					notification.put("body", ta.getFileName());

					try {
						sentNotification(tkn.getToken(), data, notification);
					} catch (Exception Ex) {
						Ex.printStackTrace();
					}
				}
			});

		}
	}

	public void sendTicketAssignedPushNotify(Ticket ticket) {

		List<String> emailIds = new ArrayList<String>(Arrays.asList(ticket.getAssignedBy(), ticket.getAssignedTo(),
				ticket.getEmailId(), ticket.getCreatedBy()));

		System.out.println("sendTicketAssignedPushNotify::" + emailIds.toString());
		emailIds.removeAll(Arrays.asList(null, ticket.getLastUpdatedBy()));
		System.out.println(emailIds.toString());

		List<AgentToken> token = tokenRepo.findByEmployeeEmailIdIn(emailIds);

		if (!token.isEmpty()) {

			token.forEach(tkn -> {
				if (!tkn.getToken().isEmpty()) {
					Map<String, String> notification = new HashMap<>();

					Map<String, Object> data = new HashMap<>();

					data.put("id", ticket.getTicketId());
					data.put("ticket_id", ticket.getTicketId());

					data.put("action", "VIEW_TICKET_ASSIGNED");

					data.put("notification_type", "VIEW_TICKET_ASSIGNED_NOTIFICATION");

					notification.put("title",
							"Ticket #" + ticket.getTicketId() + " - Ticket Assigned to " + ticket.getAssignedTo());

					notification.put("body", StringUtils.newStringUtf8(Base64.decodeBase64(ticket.getSubject())));

					try {
						sentNotification(tkn.getToken(), data, notification);
					} catch (Exception Ex) {
						Ex.printStackTrace();
					}
				}
			});

		}
	}

	public void sendTicketAssignedPushNotify(TaskRequest request, Set<String> emailIds) {

		List<AgentToken> token = tokenRepo.findByEmployeeEmailIdIn(new ArrayList<>(emailIds));

		if (!token.isEmpty()) {

			token.forEach(tkn -> {
				if (!tkn.getToken().isEmpty()) {
					Map<String, String> notification = new HashMap<>();

					Map<String, Object> data = new HashMap<>();

					data.put("task_id", request.getTask().getTaskId());
					data.put("team_id", request.getTask().getTeamId());

					data.put("action", "VIEW_TASK");

					data.put("notification_type", "VIEW_TASK_NOTIFICATION");

					notification.put("title", "New Task Created - #" + request.getTask().getTaskId());

					notification.put("body", request.getTask().getSubject());

					try {
						sentNotification(tkn.getToken(), data, notification);
					} catch (Exception Ex) {
						Ex.printStackTrace();
					}
				}
			});

		}
	}

	public void sendUpdateTaskPushNotification(TaskRequest request, Set<String> emailIds) {

		List<AgentToken> token = tokenRepo.findByEmployeeEmailIdIn(new ArrayList<>(emailIds));

		if (!token.isEmpty()) {

			token.forEach(tkn -> {
				if (!tkn.getToken().isEmpty()) {
					Map<String, String> notification = new HashMap<>();

					Map<String, Object> data = new HashMap<>();

					data.put("task_id", request.getTask().getTaskId());
					data.put("team_id", request.getTask().getTeamId());
					data.put("action", "VIEW_UPDATE_TASK");
					data.put("notification_type", "VIEW_UPDATE_TASK_NOTIFICATION");

					notification.put("title",
							"Task Updated- #" + request.getTask().getTaskId() + " - " + request.getTask().getSubject());

					if (request.getTaskHistory() != null)
						notification.put("body",
								request.getTaskHistory().getField() + " : " + request.getTaskHistory().getHistoryTo());

					try {
						sentNotification(tkn.getToken(), data, notification);
					} catch (Exception Ex) {
						Ex.printStackTrace();
					}
				}
			});

		}
	}

	public void sendTeamsWelcomeNotification(Teams team, TeamMembers member) {

		AgentToken tkn = tokenRepo.findByEmployeeEmailId(member.getMemberEmailId());

		if (tkn != null) {

			if (!tkn.getToken().isEmpty()) {
				Map<String, String> notification = new HashMap<>();

				Map<String, Object> data = new HashMap<>();

				data.put("team_id", member.getTeamId());
				data.put("action", "VIEW_TEAM_WELCOME");
				data.put("notification_type", "VIEW_TEAM_WELCOME_NOTIFICATION");

				notification.put("title", team.getName());
				notification.put("body", "You are added as '" + member.getMemberRole() + "'");

				try {
					sentNotification(tkn.getToken(), data, notification);
				} catch (Exception Ex) {
					Ex.printStackTrace();
				}
			}
		}
	}

	public void sendRawMaterialRequestNotification(RawMaterialRequest request) {

		AgentToken tkn = tokenRepo.findByEmployeeEmailId(request.getRequestTo());

		if (tkn != null) {

			if (!tkn.getToken().isEmpty()) {
				Map<String, String> notification = new HashMap<>();

				Map<String, Object> data = new HashMap<>();

				data.put("request_id", request.getId());
				data.put("action", "VIEW_RAW_MATERIAL_REQUEST");
				data.put("notification_type", "VIEW_RAW_MATERIAL_REQUEST_NOTIFICATION");

				notification.put("title", request.getSubject());
				notification.put("body",
						request.getRequestBy() + " requested for raw materials '" + request.getProductName() + "'");

				try {
					sentNotification(tkn.getToken(), data, notification);
				} catch (Exception Ex) {
					Ex.printStackTrace();
				}
			}

		}
	}

	public void sendLeaveApproveRejectNotification(LeaveApplied leaveApplied) {

		AgentToken tkn = tokenRepo.findByEmployeeEmailId(leaveApplied.getAgentEmailId());

		if (tkn != null) {

			if (!tkn.getToken().isEmpty()) {
				Map<String, String> notification = new HashMap<>();

				Map<String, Object> data = new HashMap<>();

				data.put("request_id", leaveApplied.getId());
				data.put("action", "VIEW_LEAVE_REQUEST");
				data.put("notification_type", "VIEW_LEAVE_REQUEST_NOTIFICATION");

				notification.put("title", leaveApplied.getLeaveType() + " - " + leaveApplied.getStatus());
				notification.put("body", leaveApplied.getReason());

				try {
					sentNotification(tkn.getToken(), data, notification);
				} catch (Exception Ex) {
					Ex.printStackTrace();
				}
			}

		}
	}
	
	
	
	public void sendAppliedNotification(LeaveApplied leaveApplied) {

		List<Agent> agents = agentRepo.findAllHRAdmins();

		List<String> emailIds = agents.stream().map(Agent::getEmailId).collect(Collectors.toList());
		emailIds.add(leaveApplied.getAgentEmailId());

		System.out.println(emailIds);

		List<AgentToken> tokens = tokenRepo.findByEmployeeEmailIdIn(emailIds);
		

		tokens.forEach(tkn -> {
			if (!tkn.getToken().isEmpty()) {
				Map<String, String> notification = new HashMap<>();

				Map<String, Object> data = new HashMap<>();

				data.put("request_id", leaveApplied.getId());
				data.put("action", "VIEW_LEAVE_REQUEST");
				data.put("notification_type", "VIEW_LEAVE_REQUEST_NOTIFICATION");

				notification.put("title", leaveApplied.getLeaveType() + " - " + leaveApplied.getStatus());
				notification.put("body", leaveApplied.getReason());

				try {
					sentNotification(tkn.getToken(), data, notification);
				} catch (Exception Ex) {
					Ex.printStackTrace();
				}
			}
		});
	}

	public void sendProductsRawMaterialRequestNotify(ProductRequest productReq) {

		AgentToken tkn = tokenRepo.findByEmployeeEmailId(productReq.getRawMaterialRequest().getRequestBy());

		if (tkn != null) {

			if (!tkn.getToken().isEmpty()) {
				Map<String, String> notification = new HashMap<>();

				Map<String, Object> data = new HashMap<>();

				data.put("request_id", productReq.getRawMaterialRequest().getId());
				data.put("action", "VIEW_RAW_MATERIAL_REQUEST");
				data.put("notification_type", "VIEW_RAW_MATERIAL_REQUEST_NOTIFICATION");

				notification.put("title", "Raw Material Request #" + productReq.getRawMaterialRequest().getId());

				notification.put("body",
						productReq.getRawMaterialRequest().getProductName() + " - "
								+ productReq.getRawMaterialRequest().getStatus() + " by "
								+ productReq.getRawMaterialRequest().getRequestTo());

				try {
					sentNotification(tkn.getToken(), data, notification);
				} catch (Exception Ex) {
					Ex.printStackTrace();
				}
			}
		}
	}

	public void sendProductsRawMaterialRequestNotify(Reminder reminder) {

		AgentToken tkn = tokenRepo.findByEmployeeEmailId(reminder.getAgentEmailId());

		if (tkn != null) {

			if (!tkn.getToken().isEmpty()) {
				Map<String, String> notification = new HashMap<>();

				Map<String, Object> data = new HashMap<>();

				data.put("reminder_id", reminder.getId());
				data.put("action", "VIEW_REMINDER");
				data.put("notification_type", "VIEW_REMINDER_NOTIFICATION");

				notification.put("title", "Reminder :" + reminder.getSubject());

				notification.put("body", reminder.getDescription());

				try {
					sentNotification(tkn.getToken(), data, notification);
				} catch (Exception Ex) {
					Ex.printStackTrace();
				}
			}
		}
	}

	public void sendAgentLedgerCreditNotify(AgentLedger ledger) {

		AgentToken tkn = tokenRepo.findByEmployeeEmailId(ledger.getAgentEmailId());
		
		

		if (tkn != null) {
			if (!tkn.getToken().isEmpty()) {
				Map<String, String> notification = new HashMap<>();

				Map<String, Object> data = new HashMap<>();

				data.put("ledger_id", ledger.getId());
				data.put("action", "VIEW_AGENT_LEDGER");
				data.put("notification_type", "VIEW_AGENT_LEDGER_NOTIFICATION");

				notification.put("title", "Credit : Rs." + ledger.getCredit() + " - " + ledger.getAgentEmailId());

				notification.put("body", ledger.getSubject());

				try {
					sentNotification(tkn.getToken(), data, notification);
				} catch (Exception Ex) {
					Ex.printStackTrace();
				}
			}
		}
	}

	public void sendAgentLedgerDebitNotify(AgentLedger ledger) {

		List<Agent> agents = agentRepo.findAllAccountsAdmins();

		List<String> emailIds = agents.stream().map(Agent::getEmailId).collect(Collectors.toList());
		emailIds.add(ledger.getAgentEmailId());

		System.out.println(emailIds);

		List<AgentToken> tokens = tokenRepo.findByEmployeeEmailIdIn(emailIds);

		tokens.forEach(tkn -> {
			if (!tkn.getToken().isEmpty()) {
				Map<String, String> notification = new HashMap<>();

				Map<String, Object> data = new HashMap<>();

				data.put("ledger_id", ledger.getId());
				data.put("action", "VIEW_AGENT_LEDGER");
				data.put("notification_type", "VIEW_AGENT_LEDGER_NOTIFICATION");

				notification.put("title", "Debit : Rs." + ledger.getDebit() + " - " + ledger.getAgentEmailId());

				notification.put("body", ledger.getSubject());

				try {
					sentNotification(tkn.getToken(), data, notification);
				} catch (Exception Ex) {
					Ex.printStackTrace();
				}
			}
		});

//		if (tkn != null) {
//			if (!tkn.getToken().isEmpty()) {
//				Map<String, String> notification = new HashMap<>();
//
//				Map<String, Object> data = new HashMap<>();
//
//				data.put("ledger_id", ledger.getId());
//				data.put("action", "VIEW_AGENT_LEDGER");
//				data.put("notification_type", "VIEW_AGENT_LEDGER_NOTIFICATION");
//
//				notification.put("title", "Credit : " + ledger.getCredit());
//
//				notification.put("body", "Reminder :" + ledger.getSubject());
//
//				try {
//					sentNotification(tkn.getToken(), data, notification);
//				} catch (Exception Ex) {
//					Ex.printStackTrace();
//				}
//			}
//		}
	}
}
