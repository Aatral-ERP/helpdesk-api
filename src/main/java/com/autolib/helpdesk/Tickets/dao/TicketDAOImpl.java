package com.autolib.helpdesk.Tickets.dao;

import com.autolib.helpdesk.Agents.entity.Agent;
import com.autolib.helpdesk.Agents.entity.InfoDetails;
import com.autolib.helpdesk.Agents.entity.PushNotification;
import com.autolib.helpdesk.Agents.repository.AgentRepository;
import com.autolib.helpdesk.Agents.repository.InfoDetailsRepository;
import com.autolib.helpdesk.Config.aws.LocalDirectory;
import com.autolib.helpdesk.Config.aws.S3Directories;
import com.autolib.helpdesk.Institutes.model.Institute;
import com.autolib.helpdesk.Institutes.model.InstituteContact;
import com.autolib.helpdesk.Institutes.repository.InstituteContactRepository;
import com.autolib.helpdesk.Institutes.repository.InstituteRepository;
import com.autolib.helpdesk.Tickets.model.*;
import com.autolib.helpdesk.Tickets.repository.*;
import com.autolib.helpdesk.common.EmailModel;
import com.autolib.helpdesk.common.EmailSender;
import com.autolib.helpdesk.common.EnumUtils.ServiceUnder;
import com.autolib.helpdesk.common.EnumUtils.TicketPriority;
import com.autolib.helpdesk.common.EnumUtils.TicketStatus;
import com.autolib.helpdesk.common.S3StorageService;
import com.autolib.helpdesk.common.Util;
import com.autolib.helpdesk.jwt.JwtTokenUtil;
import com.autolib.helpdesk.schedulers.controller.GmailReceiveMailsScheduler;
import com.autolib.helpdesk.schedulers.model.GoogleMailAsTicket;
import com.autolib.helpdesk.schedulers.repository.GoogleMailAsTicketRepository;
import com.google.api.client.util.Base64;
import com.google.api.client.util.StringUtils;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.Gmail.Users.Messages.Attachments.Get;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePart;
import com.google.api.services.gmail.model.MessagePartBody;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Repository
public class TicketDAOImpl implements TicketDAO {

    @Autowired
    JwtTokenUtil jwtUtil;

    @Autowired
    EmailSender emailSender;

    @Autowired
    TicketRepository ticketRepo;
    @Autowired
    TicketAttachmentRepository ticketAtchRepo;
    @Autowired
    TicketReplyRepository ticketReplyRepo;
    @Autowired
    TicketRatingRepository ticketRatingRepo;
    @Autowired
    InstituteContactRepository instContactRepo;
    @Autowired
    InstituteRepository instRepo;
    @Autowired
    AgentRepository agentRepo;
    @Autowired
    TicketServiceInvoiceRepository tsiRepo;
    @Autowired
    GoogleMailAsTicketRepository gmailAsTicketRepo;
    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    CallReportRepository callRepo;

    @Autowired
    InfoDetailsRepository infoDetailRepo;

    @Autowired
    PushNotification pushNotify;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    S3StorageService s3StorageService;
    @Value("${al.ticket.view.rooturi}")
    private String viewTicketRootURI;

    @Override
    public Map<String, Object> saveTicket(Ticket ticket) {
        Map<String, Object> resp = new HashMap<>();
        try {
            ticket = ticketRepo.save(ticket);

            Set<String> emailds = new HashSet<>();
            emailds.add(ticket.getEmailId());
            String[] emailUpdates = ticket.getEmailUpdates().split(";");
            System.out.println(emailUpdates.toString() + "  " + emailUpdates.length);

            System.out.println(emailds.size());
            EmailModel emailModel = new EmailModel("Tickets");

            emailModel.setMailTo(ticket.getEmailId());
            emailModel.setMailList(emailUpdates);
            emailModel.setOtp(String.valueOf(Util.generateOTP()));
            emailModel.setMailSub("Ticket ID : #" + ticket.getTicketId() + ", "
                    + StringUtils.newStringUtf8(Base64.decodeBase64(ticket.getSubject())));

            StringBuffer sb = new StringBuffer();
            sb.append("Dear Sir/Mam, <br><br>");
            sb.append("New Ticket has been raised, Please see the Details below: <br><br>");
            sb.append("<table><tr><td>Subject</td> <td>:</td> <td>"
                    + StringUtils.newStringUtf8(Base64.decodeBase64(ticket.getSubject())) + "</td></tr>");
            sb.append("<tr><td>Institute Name</td><td>:</td><td>" + ticket.getInstitute().getInstituteName()
                    + "</td></tr>");
            sb.append("<tr><td>Product</td><td>:</td><td>" + ticket.getProduct() + "</td></tr>");
            sb.append("<tr><td>Email Id</td><td>:</td><td>" + ticket.getEmailId() + "</td></tr>");
            sb.append("<tr><td>Email Updates</td><td>:</td><td>" + ticket.getEmailUpdates() + "</td></tr>");
            sb.append("<tr><td>Service Under</td><td>:</td><td>" + ticket.getServiceUnder() + "</td></tr>");
            sb.append("<tr><td>Service Type</td><td>:</td><td>" + ticket.getServiceType() + "</td></tr>");
            sb.append("<tr><td>Priority</td><td>:</td><td>" + ticket.getPriority() + "</td></tr>");
            sb.append("<tr><td>Summary</td><td>:</td><td>"
                    + StringUtils.newStringUtf8(Base64.decodeBase64(ticket.getSummary())) + "</td></tr>");
            sb.append("<tr><td>Status</td><td>:</td><td>" + ticket.getStatus() + "</td></tr>");

            sb.append("<tr><td>View Ticket</td><td>:</td><td> <a target='_blank' href='" + viewTicketRootURI
                    + ticket.getTicketId() + "'> View Ticket </a>" + "</td></tr>");

            sb.append("</table>");

            emailModel.setMailText(sb.toString());

            emailModel.setSenderConf("Tickets");
            emailSender.sendmail(emailModel);

            resp.putAll(Util.SuccessResponse());
        } catch (Exception ex) {
            resp.putAll(Util.FailedResponse(ex.getMessage()));
            ex.printStackTrace();
        }
        resp.put("Ticket", ticket);
        return resp;
    }

    @Override
    public Map<String, Object> getTicket(Ticket ticket) {
        Map<String, Object> resp = new HashMap<>();
        List<TicketAttachment> ticketAttachments = new ArrayList<>();
        List<TicketReply> ticketReplies = new ArrayList<>();
        TicketServiceInvoice tsis = new TicketServiceInvoice();
        TicketRating ticketRating = new TicketRating();
        try {
            ticket = ticketRepo.findByTicketId(ticket.getTicketId());
            ticketAttachments = ticketAtchRepo.findByTicket(ticket);
            ticketReplies = ticketReplyRepo.findByTicket(ticket);
            tsis = tsiRepo.findByTicket(ticket);
            ticketRating = ticketRatingRepo.findByTicketId(ticket.getTicketId());
            resp.putAll(Util.SuccessResponse());
        } catch (Exception ex) {
            resp.putAll(Util.FailedResponse(ex.getMessage()));
            ex.printStackTrace();
        }
        resp.put("Ticket", ticket);
        resp.put("TicketAttachments", ticketAttachments);
        resp.put("TicketReply", ticketReplies);
        resp.put("TicketServiceInvoice", tsis);
        resp.put("TicketRating", ticketRating);

        return resp;
    }

    @Override
    public Map<String, Object> deleteTicket(Ticket ticket) {
        Map<String, Object> resp = new HashMap<>();
        try {
            if (ticketRepo.findByTicketId(ticket.getTicketId()) != null) {
                ticketRepo.delete(ticket);
                resp.putAll(Util.SuccessResponse());
            } else {
                resp.putAll(Util.FailedResponse("Invalid Ticket Id"));
            }

        } catch (Exception ex) {
            resp.putAll(Util.FailedResponse(ex.getMessage()));
            ex.printStackTrace();
        }
        return resp;
    }

    @Override
    public Map<String, Object> addTicketReply(TicketReply ticketReply) {
        Map<String, Object> resp = new HashMap<>();
        List<TicketReply> ticketReplies = new ArrayList<>();
        try {
            ticketReplies = ticketReplyRepo.findByTicket(ticketReply.getTicket());

            ticketReply = ticketReplyRepo.save(ticketReply);

            EmailModel emailModel = new EmailModel("Tickets");

            String mailList = ticketReply.getTicket().getEmailUpdates();
            mailList = ticketReply.getTicket().getAssignedTo() + ";" + mailList;

            mailList = ticketReplies.stream().map(tr -> tr.getReplyBy()).distinct().reduce(";", String::concat) + ";"
                    + mailList;

            String[] emailUpdates = mailList.split(";");

            System.out.println(mailList.toString() + "  " + emailUpdates.length);

            emailModel.setMailTo(ticketReply.getTicket().getEmailId());
            emailModel.setMailList(emailUpdates);
            emailModel.setOtp(String.valueOf(Util.generateOTP()));
            emailModel.setMailSub("Ticket ID : #" + ticketReply.getTicket().getTicketId() + ", Comment Added by "
                    + ticketReply.getReplyBy());

            StringBuffer sb = new StringBuffer();

            sb.append("<p>" + ticketReply.getReplyBy() + " - "
                    + (Util.sdfFormatter(ticketReply.getCreateddatetime(), "dd/MM/yyyy hh:mm a")) + "</p><br>");
            sb.append("<h4>" + ticketReply.getReply().replaceAll("\n", "<br>").replaceAll("\r", "<br>") + "</h4>");

            sb.append("<hr>");

            if (ticketReplies.size() > 0) {

                ticketReplies.sort((a, b) -> b.getId() - a.getId());
                sb.append("<h4>Older Conversations</h4>");
                for (TicketReply tr : ticketReplies) {
                    sb.append("<p>" + tr.getReplyBy() + " - "
                            + (Util.sdfFormatter(tr.getCreateddatetime(), "dd/MM/yyyy hh:mm a")) + "</p><br>");
                    sb.append("<span>" + tr.getReply().replaceAll("\n", "<br>").replaceAll("\r", "<br>") + "</span>");
                    sb.append("<hr>");
                }

            }
            sb.append("<table><tr><td>Ticket Id #:</td> <td>:</td> <td>" + ticketReply.getTicket().getTicketId()
                    + "</td></tr>");

            sb.append("<tr><td>Institute Name</td><td>:</td><td>"
                    + ticketReply.getTicket().getInstitute().getInstituteName() + "</td></tr>");
            sb.append("<tr><td>Product</td><td>:</td><td>" + ticketReply.getTicket().getProduct() + "</td></tr>");
            sb.append("<tr><td>Status</td><td>:</td><td>" + ticketReply.getTicket().getStatus() + "</td></tr>");

            sb.append("<tr><td>View Ticket</td><td>:</td><td> <a target='_blank' href='" + viewTicketRootURI
                    + ticketReply.getTicket().getTicketId() + "'> View Ticket </a>" + "</td></tr>");

            sb.append("</table>");

            emailModel.setMailText(sb.toString());
            emailModel.setSenderConf("Tickets");
            emailSender.sendmail(emailModel);

            resp.putAll(Util.SuccessResponse());

            pushNotify.sendTicketReplyPushNotify(ticketReply);
        } catch (Exception ex) {
            resp.putAll(Util.FailedResponse(ex.getMessage()));
            ex.printStackTrace();
        }
        resp.put("TicketReply", ticketReply);
        resp.put("TicketReplies", ticketReplies);
        return resp;
    }

    @Override
    public Map<String, Object> getAllTicketReply(TicketReply ticketReply) {
        Map<String, Object> resp = new HashMap<>();
        List<TicketReply> ticketReplies = new ArrayList<>();
        try {
            ticketReplies = ticketReplyRepo.findByTicket(ticketReply.getTicket());

            resp.putAll(Util.SuccessResponse());
        } catch (Exception ex) {
            resp.putAll(Util.FailedResponse(ex.getMessage()));
            ex.printStackTrace();
        }
        resp.put("TicketReply", ticketReplies);
        return resp;
    }

    @Override
    public Map<String, Object> addAttachment(String ticketId, MultipartFile file) {

        Map<String, Object> respMap = new HashMap<String, Object>();
        try {

            TicketAttachment ta = new TicketAttachment();
            ta.setTicket(new Ticket(Integer.parseInt(ticketId)));
            ta.setFileName(Util.getUniqueString(file.getOriginalFilename()));
            ta.setFileSize(file.getSize());
            ta.setFileType(file.getContentType());
            ta = ticketAtchRepo.save(ta);

            s3StorageService.pushToAWS(S3Directories.Tickets + ticketId, file, ta.getFileName());

            Ticket tikcetTemp = ticketRepo.findByTicketId(ta.getTicketId());

            pushNotify.sendTicketAttachmentPushNotify(tikcetTemp, ta);

            respMap.putAll(Util.SuccessResponse());
        } catch (Exception ex) {
            ex.printStackTrace();
            respMap.putAll(Util.FailedResponse());
        }
        return respMap;
    }

    @Override
    public Map<String, Object> addAllInstituteTickets(Ticket ticket) {
        Map<String, Object> resp = new HashMap<>();
        List<Ticket> tickets = new ArrayList<>();
        try {
            tickets = ticketRepo.findByInstitute(ticket.getInstitute());
            resp.putAll(Util.SuccessResponse());
        } catch (Exception ex) {
            resp.putAll(Util.FailedResponse(ex.getMessage()));
            ex.printStackTrace();
        }
        resp.put("Tickets", tickets);
        return resp;
    }

    @Override
    public Map<String, Object> updateTicket(TicketRequest ticketRequest) {
        Map<String, Object> resp = new HashMap<>();
        Ticket ticket = new Ticket();
        try {

//			Ticket tikcetTemp = ticketRepo.findByTicketId(ticketRequest.getTicket().getTicketId());

            if (ticketRequest.getAgent() == null && ticketRequest.getInstituteContact() == null) {
                resp.putAll(Util.invalidMessage("Not Authorized User to update tickets"));
            } else if (ticketRequest.getInstituteContact() != null) {

                InstituteContact ic = instContactRepo.findById(ticketRequest.getInstituteContact().getId());
                if (ic == null) {
                    resp.putAll(Util.invalidMessage("User Not Found"));
                } else if (!ic.getInstituteId().equals(ticketRequest.getInstituteContact().getInstituteId())) {
                    resp.putAll(Util
                            .invalidMessage("Ticket Not Belonging to you , You cannot make changes on this Ticket"));
                } else if (ic.getIsBlocked() > 0) {
                    resp.putAll(Util.invalidMessage("Account Blocked, You can't make changes"));
                } else {
                    ticketRequest.getTicket().setLastUpdatedBy(ic.getEmailId());
                    ticketRepo.save(ticketRequest.getTicket());

                    sendTicketStatusUpdateMail(ticketRequest);

                    pushNotify.sendTicketUpdatePushNotify(ticketRequest);

                    resp.putAll(Util.SuccessResponse());

                }

            } else if (ticketRequest.getAgent() != null) {

                Agent agent = agentRepo.findByEmailId(ticketRequest.getAgent().getEmailId());
                if (agent == null) {
                    resp.putAll(Util.invalidMessage("User Not Found"));
                } else if (agent.isBlocked()) {
                    resp.putAll(Util.invalidMessage("Agent Account Blocked, You can't make changes"));
                } else {
                    ticketRequest.getTicket().setLastUpdatedBy(agent.getEmailId());
                    ticket = ticketRepo.save(ticketRequest.getTicket());
                    resp.putAll(Util.SuccessResponse());
                    sendTicketStatusUpdateMail(ticketRequest);
                    pushNotify.sendTicketUpdatePushNotify(ticketRequest);
                }

            }
        } catch (Exception ex) {
            resp.putAll(Util.FailedResponse(ex.getMessage()));
            ex.printStackTrace();
        }
        resp.put("Tickets", ticket);
        return resp;
    }

    @Override
    public Map<String, Object> assignTicket(TicketRequest ticketRequest) {
        Map<String, Object> resp = new HashMap<>();
        Ticket ticketTemp = null;
        try {

            ticketTemp = ticketRepo.findByTicketId(ticketRequest.getTicket().getTicketId());

            if (ticketRequest.getAgent() == null) {
                resp.putAll(Util.invalidMessage("No Authority to update tickets"));
            } else if (ticketTemp.getStatus() == TicketStatus.Closed) {
                resp.putAll(Util.invalidMessage("Ticket Already Closed"));
            } else {

                Agent agent = agentRepo.findByEmailId(ticketRequest.getAgent().getEmailId());
                if (agent == null) {
                    resp.putAll(Util.invalidMessage("User Not Found"));
                } else if (agent.isBlocked()) {
                    resp.putAll(Util.invalidMessage("Agent Account Blocked, You can't make changes"));
                } else {
//					ticketRequest.getTicket().setLastUpdatedBy(agent.getEmailId());
                    ticketRequest.getTicket().setAssignedTo(agent.getEmailId());
                    ticketRequest.getTicket().setStatus(TicketStatus.Assigned);

                    ticketTemp = ticketRepo.save(ticketRequest.getTicket());
                    resp.putAll(Util.SuccessResponse());

                    sendTicketAssignedStatusMail(ticketRequest);

                    pushNotify.sendTicketAssignedPushNotify(ticketRequest.getTicket());
                }
            }
        } catch (Exception ex) {
            resp.putAll(Util.FailedResponse(ex.getMessage()));
            ex.printStackTrace();
        }
        resp.put("Tickets", ticketTemp);
        return resp;
    }

    void sendTicketAssignedStatusMail(TicketRequest ticketRequest) {

        try {
            Set<String> emailds = new HashSet<>();
            emailds.add(ticketRequest.getTicket().getEmailId());
            String[] emailUpdates = (ticketRequest.getTicket().getAssignedTo() + ";"
                    + ticketRequest.getTicket().getEmailUpdates()).split(";");
            System.out.println("Sending mail to ::" + emailds.toString());

            if (Util.validateEmailID(ticketRequest.getTicket().getEmailId())) {
                EmailModel emailModel = new EmailModel("Tickets");

                emailModel.setFromName(ticketRequest.getAgent().getFirstName());
                emailModel.setMailTo(ticketRequest.getTicket().getEmailId());
                emailModel.setMailList(emailUpdates);
                emailModel.setOtp(String.valueOf(Util.generateOTP()));
                emailModel.setMailSub("Ticket ID : #" + ticketRequest.getTicket().getTicketId()
                        + " Update, Assigned to " + ticketRequest.getAgent().getFirstName() + " - "
                        + StringUtils.newStringUtf8(Base64.decodeBase64(ticketRequest.getTicket().getSubject())));

                StringBuffer sb = new StringBuffer();
                sb.append("Dear Sir/Mam, <br><br>");
                sb.append("Ticket #" + ticketRequest.getTicket().getTicketId()
                        + " has been updated, Please see the Details below: <br><br>");
                sb.append("<table><tr><td>Subject</td> <td>:</td> <td>"
                        + StringUtils.newStringUtf8(Base64.decodeBase64(ticketRequest.getTicket().getSubject()))
                        + "</td></tr>");
                sb.append("<tr><td>Institute Name</td><td>:</td><td>"
                        + ticketRequest.getTicket().getInstitute().getInstituteName() + "</td></tr>");
                sb.append("<tr><td>Product</td><td>:</td><td>" + ticketRequest.getTicket().getProduct() + "</td></tr>");
                sb.append(
                        "<tr><td>Email Id</td><td>:</td><td>" + ticketRequest.getTicket().getEmailId() + "</td></tr>");
                sb.append("<tr><td>Email Updates</td><td>:</td><td>" + ticketRequest.getTicket().getEmailUpdates()
                        + "</td></tr>");
                sb.append("<tr><td>Service Under</td><td>:</td><td>" + ticketRequest.getTicket().getServiceUnder()
                        + "</td></tr>");
                sb.append("<tr><td>Service Type</td><td>:</td><td>" + ticketRequest.getTicket().getServiceType()
                        + "</td></tr>");
                sb.append(
                        "<tr><td>Priority</td><td>:</td><td>" + ticketRequest.getTicket().getPriority() + "</td></tr>");
                sb.append("<tr><td>Summary</td><td>:</td><td>"
                        + StringUtils.newStringUtf8(Base64.decodeBase64(ticketRequest.getTicket().getSummary()))
                        + "</td></tr>");
                sb.append("<tr><td>Status</td><td>:</td><td>" + ticketRequest.getTicket().getStatus() + "</td></tr>");

                sb.append("<tr><td>View Ticket</td><td>:</td><td> <a target='_blank' href='" + viewTicketRootURI
                        + ticketRequest.getTicket().getTicketId() + "'> View Ticket </a>" + "</td></tr>");

                sb.append("</table>");

                emailModel.setMailText(sb.toString());
                emailModel.setSenderConf("Tickets");
                emailSender.sendmail(emailModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void sendTicketStatusUpdateMail(TicketRequest ticketRequest) {

        try {
            Set<String> emailds = new HashSet<>();
            emailds.add(ticketRequest.getTicket().getEmailId());
            String[] emailUpdates = ticketRequest.getTicket().getEmailUpdates().split(";");
            System.out.println("Sending mail to ::" + emailds.toString());

            if (Util.validateEmailID(ticketRequest.getTicket().getEmailId())) {
                EmailModel emailModel = new EmailModel("Tickets");

                emailModel.setMailTo(ticketRequest.getTicket().getEmailId());
                emailModel.setMailList(emailUpdates);
                emailModel.setMailSub("Ticket ID : #" + ticketRequest.getTicket().getTicketId() + " Update : "
                        + ticketRequest.getTicket().getStatus() + " - "
                        + StringUtils.newStringUtf8(Base64.decodeBase64(ticketRequest.getTicket().getSubject())));

                StringBuffer sb = new StringBuffer();
                sb.append("Dear Sir/Mam, <br><br>");
                sb.append("Ticket #" + ticketRequest.getTicket().getTicketId()
                        + " has been updated, Please see the Details below: <br><br>");
                sb.append("<table><tr><td>Subject</td> <td>:</td> <td>"
                        + StringUtils.newStringUtf8(Base64.decodeBase64(ticketRequest.getTicket().getSubject()))
                        + "</td></tr>");
                sb.append("<tr><td>Institute Name</td><td>:</td><td>"
                        + ticketRequest.getTicket().getInstitute().getInstituteName() + "</td></tr>");
                sb.append("<tr><td>Product</td><td>:</td><td>" + ticketRequest.getTicket().getProduct() + "</td></tr>");
                sb.append(
                        "<tr><td>Email Id</td><td>:</td><td>" + ticketRequest.getTicket().getEmailId() + "</td></tr>");
                sb.append("<tr><td>Email Updates</td><td>:</td><td>" + ticketRequest.getTicket().getEmailUpdates()
                        + "</td></tr>");
                sb.append("<tr><td>Service Under</td><td>:</td><td>" + ticketRequest.getTicket().getServiceUnder()
                        + "</td></tr>");
                sb.append("<tr><td>Service Type</td><td>:</td><td>" + ticketRequest.getTicket().getServiceType()
                        + "</td></tr>");
                sb.append(
                        "<tr><td>Agent</td><td>:</td><td>" + ticketRequest.getTicket().getAssignedTo() + "</td></tr>");
                sb.append(
                        "<tr><td>Priority</td><td>:</td><td>" + ticketRequest.getTicket().getPriority() + "</td></tr>");
                sb.append("<tr><td>Summary</td><td>:</td><td>"
                        + StringUtils.newStringUtf8(Base64.decodeBase64(ticketRequest.getTicket().getSummary()))
                        + "</td></tr>");
                sb.append("<tr><td>Status</td><td>:</td><td>" + ticketRequest.getTicket().getStatus() + "</td></tr>");

                sb.append("<tr><td>View Ticket</td><td>:</td><td> <a target='_blank' href='" + viewTicketRootURI
                        + ticketRequest.getTicket().getTicketId() + "'> View Ticket </a>" + "</td></tr>");

                sb.append("</table>");

                emailModel.setMailText(sb.toString());
                emailModel.setSenderConf("Tickets");
                emailSender.sendmail(emailModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public Map<String, Object> getTicketReportData() {
        Map<String, Object> resp = new HashMap<>();
        List<Institute> institutes = new ArrayList<>();
        List<String> serviceTypes = new ArrayList<>();
        Iterable<Agent> agents = new ArrayList<>();
        try {

            institutes = instRepo.getInstituteMinAddressEmailDetails();

            agents = agentRepo.findAllMinDetails();

            serviceTypes = ticketRepo.findAllDistinctServiceType();

            resp.putAll(Util.SuccessResponse());
        } catch (Exception ex) {
            resp.putAll(Util.FailedResponse(ex.getMessage()));
            ex.printStackTrace();
        }
        resp.put("Institutes", institutes);
        resp.put("Agents", agents);
        resp.put("TicketStatus", TicketStatus.values());
        resp.put("TicketPriority", TicketPriority.values());
        resp.put("ServiceUnder", ServiceUnder.values());
        resp.put("ServiceTypes", serviceTypes);

        return resp;
    }

    @Override
    public Map<String, Object> getTicketReport(TicketReportRequest ticketRequest) {
        Map<String, Object> resp = new HashMap<>();

        try {
            String filterQuery = "";

            if (ticketRequest.getTicket().getStatus() != null) {
                filterQuery = filterQuery + " and t.status ='" + ticketRequest.getTicket().getStatus() + "'";
            }

            if (ticketRequest.getTicket().getTicketId() > 0) {
                filterQuery = filterQuery + " and t.ticketId =" + ticketRequest.getTicket().getTicketId() + "";
            }

            if (ticketRequest.getTicket().getServiceUnder() != null) {
                filterQuery = filterQuery + " and t.serviceUnder ='" + ticketRequest.getTicket().getServiceUnder()
                        + "'";
            }
            if (ticketRequest.getTicket().getServiceType() != null) {
                filterQuery = filterQuery + " and t.serviceType ='" + ticketRequest.getTicket().getServiceType() + "'";
            }
            if (ticketRequest.getTicket().getPriority() != null) {
                filterQuery = filterQuery + " and t.priority ='" + ticketRequest.getTicket().getPriority() + "' ";
            }
            if (ticketRequest.getInstitutes().size() > 0) {
                String instituteIds = "'0'";
                for (Institute inst : ticketRequest.getInstitutes()) {
                    instituteIds = instituteIds + ",'" + inst.getInstituteId() + "'";
                }
                filterQuery = filterQuery + " and t.institute in (" + instituteIds + ") ";
            }
            if (ticketRequest.getAgents().size() > 0) {
                String agents = "'0'";
                for (Agent agnt : ticketRequest.getAgents()) {
                    agents = agents + ",'" + agnt.getEmailId() + "'";
                }
                filterQuery = filterQuery + " and t.assignedTo in (" + agents + ") ";
            }
            if (ticketRequest.getCreatedAgents().size() > 0) {
                String agents = "'0'";
                for (Agent agnt : ticketRequest.getCreatedAgents()) {
                    agents = agents + ",'" + agnt.getEmailId() + "'";
                }
                filterQuery = filterQuery + " and t.createdBy in (" + agents + ") ";
            }
            if (ticketRequest.getReportingToAgents().size() > 0) {
                String agents = "'0'";
                for (Agent agnt : ticketRequest.getReportingToAgents()) {
                    agents = agents + ",'" + agnt.getEmailId() + "'";
                }
                filterQuery = filterQuery + " and t.assignedBy in (" + agents + ") ";
            }

            if (ticketRequest.getFromDate() != null || ticketRequest.getToDate() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                filterQuery = filterQuery + " and t.createddatetime between '" + sdf.format(ticketRequest.getFromDate())
                        + "' and '" + sdf.format(ticketRequest.getToDate()) + "'";
            }

            Query query = entityManager.createQuery("select t from Ticket t where 2 > 1 " + filterQuery, Ticket.class);
            resp.put("Tickets", query.getResultList());

            resp.putAll(Util.SuccessResponse());
        } catch (Exception ex) {
            resp.putAll(Util.FailedResponse(ex.getMessage()));
            ex.printStackTrace();
        }
        return resp;
    }

    @Override
    public Map<String, Object> getTicketReportV2(TicketReportRequest request) {
        Map<String, Object> resp = new HashMap<>();

        try {
            String filterQuery = "";

            if (request.getTicketStatus() != null && !request.getTicketStatus().isEmpty()) {
                String str = "'0'";
                for (String _status : request.getTicketStatus()) {
                    str = str + ",'" + _status + "'";
                }
                filterQuery = filterQuery + " and t.status in (" + str + ") ";
            }

            if (request.getCategory() != null && !request.getCategory().isEmpty()) {
                String str = "'0'";
                for (String _str : request.getCategory()) {
                    str = str + ",'" + _str + "'";
                }
                filterQuery = filterQuery + " and t.category in (" + str + ") ";
            }

            if (request.getPriority() != null && !request.getPriority().isEmpty()) {
                String str = "'0'";
                for (String _str : request.getPriority()) {
                    str = str + ",'" + _str + "'";
                }
                filterQuery = filterQuery + " and t.priority in (" + str + ") ";
            }

            if (request.getServiceUnder() != null && !request.getServiceUnder().isEmpty()) {
                String str = "'0'";
                for (String _str : request.getServiceUnder()) {
                    str = str + ",'" + _str + "'";
                }
                filterQuery = filterQuery + " and t.serviceUnder in (" + str + ") ";
            }

            if (request.getTicketId() > 0) {
                filterQuery = filterQuery + " and t.ticketId =" + request.getTicketId() + "";
            }

            if (request.getInstitutes().size() > 0) {
                String instituteIds = "'0'";
                for (Institute inst : request.getInstitutes()) {
                    instituteIds = instituteIds + ",'" + inst.getInstituteId() + "'";
                }
                filterQuery = filterQuery + " and t.institute in (" + instituteIds + ") ";
            }
            if (request.getAgents().size() > 0) {
                String agents = "'0'";
                for (Agent agnt : request.getAgents()) {
                    agents = agents + ",'" + agnt.getEmailId() + "'";
                }
                filterQuery = filterQuery + " and t.assignedTo in (" + agents + ") ";
            }
            if (request.getCreatedAgents().size() > 0) {
                String agents = "'0'";
                for (Agent agnt : request.getCreatedAgents()) {
                    agents = agents + ",'" + agnt.getEmailId() + "'";
                }
                filterQuery = filterQuery + " and t.createdBy in (" + agents + ") ";
            }
            if (request.getReportingToAgents().size() > 0) {
                String agents = "'0'";
                for (Agent agnt : request.getReportingToAgents()) {
                    agents = agents + ",'" + agnt.getEmailId() + "'";
                }
                filterQuery = filterQuery + " and t.assignedBy in (" + agents + ") ";
            }

            if (request.getFromDueDate() != null || request.getToDueDate() != null) {
                filterQuery = filterQuery + " and t.dueDateTime between '" + Util.sdfFormatter(request.getFromDueDate())
                        + "' and '" + Util.sdfFormatter(request.getToDueDate()) + " 23:59:59'";
            }

            if (request.getFromCreatedDate() != null || request.getToCreatedDate() != null) {
                filterQuery = filterQuery + " and t.createddatetime between '"
                        + Util.sdfFormatter(request.getFromCreatedDate()) + "' and '"
                        + Util.sdfFormatter(request.getToCreatedDate()) + " 23:59:59'";
            }

            if (request.getFromLastModifiedDate() != null || request.getToLastModifiedDate() != null) {
                filterQuery = filterQuery + " and t.lastupdatedatetime between '"
                        + Util.sdfFormatter(request.getFromLastModifiedDate()) + "' and '"
                        + Util.sdfFormatter(request.getToLastModifiedDate()) + " 23:59:59'";
            }

            Query query = entityManager.createQuery("select t from Ticket t where 2 > 1 " + filterQuery, Ticket.class);
            resp.put("Tickets", query.getResultList());

            resp.putAll(Util.SuccessResponse());
        } catch (Exception ex) {
            resp.putAll(Util.FailedResponse(ex.getMessage()));
            ex.printStackTrace();
        }
        return resp;
    }

    @Override
    public Map<String, Object> sendTicketInvoice(TicketServiceInvoice tsi) {
        Map<String, Object> resp = new HashMap<>();
        try {

            tsi.setInvoiceFileName(tsi.getInvoiceNo() + ".pdf");

            tsi = tsiRepo.save(tsi);

            createInvoice(tsi);

            sendInvoiceMail(tsi);

            resp.putAll(Util.SuccessResponse());
        } catch (Exception ex) {
            resp.putAll(Util.FailedResponse(ex.getMessage()));
            ex.printStackTrace();
        }
        resp.put("TicketServiceInvoice", tsi);
        return resp;
    }

    void sendInvoiceMail(TicketServiceInvoice tsi) {
        try {
            EmailModel emailModel = new EmailModel("Tickets");

            String[] emailUpdates = tsi.getTicket().getEmailUpdates().split(";");

            emailModel.setMailTo(tsi.getTicket().getEmailId());
            emailModel.setMailList(emailUpdates);
            emailModel.setOtp(String.valueOf(Util.generateOTP()));
            emailModel.setMailSub("Invoice No : #" + tsi.getInvoiceNo() + ", " + tsi.getTitle());

            StringBuffer sb = new StringBuffer();
            sb.append("Dear Sir/Mam, <br><br>");
            sb.append(
                    "Following Ticket requires payment for process the Ticket, Please see the Ticket Details below: <br><br>");
            sb.append("<br><b>Ticket Details:</b>");
            sb.append("<table><tr><td>Ticket Id</td> <td>:</td> <td><b>#" + tsi.getTicket().getTicketId()
                    + "</b></td></tr>");
            sb.append("<tr><td>Subject</td> <td>:</td> <td>"
                    + StringUtils.newStringUtf8(Base64.decodeBase64(tsi.getTicket().getSubject())) + "</td></tr>");
            sb.append("<tr><td>Institute Name</td><td>:</td><td>" + tsi.getTicket().getInstitute().getInstituteName()
                    + "</td></tr>");
            sb.append("<tr><td>Product</td><td>:</td><td>" + tsi.getTicket().getProduct() + "</td></tr>");
            sb.append("<tr><td>Email Id</td><td>:</td><td>" + tsi.getTicket().getEmailId() + "</td></tr>");
            sb.append("<tr><td>Email Updates</td><td>:</td><td>" + tsi.getTicket().getEmailUpdates() + "</td></tr>");
            sb.append("<tr><td>Service Under</td><td>:</td><td>" + tsi.getTicket().getServiceUnder() + "</td></tr>");
            sb.append("<tr><td>Service Type</td><td>:</td><td>" + tsi.getTicket().getServiceType() + "</td></tr>");
            sb.append("<tr><td>Priority</td><td>:</td><td>" + tsi.getTicket().getPriority() + "</td></tr>");
            sb.append("<tr><td>Summary</td><td>:</td><td>"
                    + StringUtils.newStringUtf8(Base64.decodeBase64(tsi.getTicket().getSummary())) + "</td></tr>");
            sb.append("<tr><td>Status</td><td>:</td><td>" + tsi.getTicket().getStatus() + "</td></tr>");
            sb.append("</table>");
            sb.append("<br><br><br>");
            sb.append("<b>Payment Details:</b>");
            sb.append("<table><tr><td>Invoice No :#</td> <td>:</td> <td>" + tsi.getInvoiceNo() + "</td></tr>");
            sb.append("<tr><td>Title</td> <td>:</td> <td>" + tsi.getTitle() + "</td></tr>");
            sb.append("<tr><td>Description</td> <td>:</td> <td>" + tsi.getDescription() + "</td></tr>");
            sb.append("<tr><td>Amount</td> <td>:</td> <td>Rs." + tsi.getAmount() + "</td></tr>");
            sb.append("<tr><td>GST(%)</td> <td>:</td> <td>" + tsi.getGST() + "%</td></tr>");
            sb.append("<tr><td>Total Payable Amount</td> <td>:</td> <td>Rs." + tsi.getTotalAmount() + "</td></tr>");
            sb.append("</table>");

            emailModel.setMailText(sb.toString());

            s3StorageService.pullFileFromS3ToLocal(S3Directories.ServicesInvoices + tsi.getInvoiceFileName(), LocalDirectory.ServicesInvoices, tsi.getInvoiceFileName());

            File directory = new File(LocalDirectory.ServicesInvoices + tsi.getInvoiceFileName());
            System.out.println(directory.getAbsolutePath());
            if (!directory.exists()) {
                createInvoice(tsi);
            }

            emailModel.setContent_path(directory.getAbsolutePath());
            emailModel.setSenderConf("Tickets");
            emailSender.sendmail(emailModel);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void createInvoice(TicketServiceInvoice tsi) {
        try {

            final InputStream stream = this.getClass().getResourceAsStream("/reports/ServiceCall_Quote.jrxml");

            final JasperReport report = JasperCompileManager.compileReport(stream);
            final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(new ArrayList<>());

            final Map<String, Object> parameters = new HashMap<>();
            parameters.put("amc_amount", "Rs." + tsi.getAmount());
            parameters.put("institute_name", tsi.getTicket().getInstitute().getInstituteName());
            parameters.put("address", tsi.getTicket().getInstitute().getCity() + ", "
                    + tsi.getTicket().getInstitute().getState() + " - " + tsi.getTicket().getInstitute().getZipcode());
            parameters.put("gst", tsi.getGST().intValue() + "%");
            parameters.put("total_amount", "Rs." + tsi.getTotalAmount());
            parameters.put("gst_amount", "Rs." + String.valueOf(tsi.getTotalAmount() - tsi.getAmount()));
            parameters.put("service_type", tsi.getTitle());
            parameters.put("title", tsi.getTitle());
            parameters.put("invoice_no", "#" + tsi.getInvoiceNo());

            final JasperPrint print = JasperFillManager.fillReport(report, parameters, source);

            File directory = new File(LocalDirectory.ServicesInvoices);
            System.out.println(directory.getAbsolutePath());
            if (!directory.exists()) {
                System.out.println("Directory created ::" + directory.getAbsolutePath());
                directory.mkdir();
            }
            final String filePath = directory.getAbsolutePath() + "/" + tsi.getInvoiceFileName();
            System.out.println(filePath);
            // Export the report to a PDF file.
            JasperExportManager.exportReportToPdfFile(print, filePath);
            s3StorageService.pushLocalFileToAWS(S3Directories.ServicesInvoices, S3Directories.ServicesInvoices + tsi.getInvoiceFileName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, Object> getTicketInvoice(TicketServiceInvoice tsi) {
        Map<String, Object> resp = new HashMap<>();
        TicketServiceInvoice tsis = new TicketServiceInvoice();
        try {

            tsis = tsiRepo.findByTicket(tsi.getTicket());
        } catch (Exception ex) {
            resp.putAll(Util.FailedResponse(ex.getMessage()));
            ex.printStackTrace();
        }
        resp.put("TicketServiceInvoice", tsis);
        return resp;
    }

    @Modifying
    @Transactional
    @Override
    public Map<String, Object> addTicketRating(TicketRating tr) {
        Map<String, Object> resp = new HashMap<>();
        try {
            if (ticketRepo.findByTicketId(tr.getTicketId()) != null) {
                tr = ticketRatingRepo.save(tr);

                Query query = entityManager
                        .createQuery("update Ticket t set t.rating = :rating where t.ticketId = :ticketId");
                query.setParameter("rating", tr.getRating());
                query.setParameter("ticketId", tr.getTicketId());

                query.executeUpdate();
                resp.putAll(Util.SuccessResponse());
            } else {
                resp.putAll(Util.FailedResponse("No a valid Ticket"));
            }
        } catch (Exception ex) {
            resp.putAll(Util.FailedResponse(ex.getMessage()));
            ex.printStackTrace();
        }
        resp.put("TicketRating", tr);
        return resp;
    }

    @Override
    public Map<String, Object> saveGmailAsTicket(GmailAsTicketRequest req) {
        Map<String, Object> resp = new HashMap<String, Object>();
        try {

            resp = saveTicket(req.getTicket());

            Ticket ticket = (Ticket) resp.get("Ticket");
            req.getGmailAsTicket().setTicketId(ticket.getTicketId());
            gmailAsTicketRepo.save(req.getGmailAsTicket());

            if (!req.getGmailAsTicket().getAttachments().isEmpty() && req.getGmailAsTicket().getAttachments() != null) {
                saveGmailAttachments(req.getGmailAsTicket(), ticket);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            resp.putAll(Util.FailedResponse(ex.getMessage()));
        }
        return resp;
    }

    void saveGmailAttachments(GoogleMailAsTicket msg, Ticket ticket) {
        try {
            GmailReceiveMailsScheduler gmailRMS = new GmailReceiveMailsScheduler();
            Gmail service = gmailRMS.getGmailService();

            Message message = service.users().messages().get("me", msg.getIdMail()).execute();

            String attachments = "";
            if (message.getPayload().getMimeType().equalsIgnoreCase("multipart/mixed")) {
                System.out.println(message.getPayload().getParts().size());

                List<MessagePart> mp2 = message.getPayload().getParts();
                for (int k = 1; mp2.size() > k; k++) {
                    MessagePart mp2s = mp2.get(k);

                    String attachId = mp2s.getBody().getAttachmentId();
                    Get mpb = service.users().messages().attachments().get("me", msg.getIdMail(), attachId);
                    MessagePartBody mpd = mpb.execute();
                    byte[] data = Base64.decodeBase64(mpd.getData());

                    File directory = new File(LocalDirectory.Tickets + msg.getTicketId() + "/");
                    System.out.println(directory.getAbsolutePath());
                    if (!directory.exists()) {
                        directory.mkdir();
                    }
                    TicketAttachment ta = new TicketAttachment();
                    ta.setTicket(ticket);
                    ta.setFileName(mp2s.getFilename());
                    ta.setFileSize(mpd.getSize());
                    ta.setFileType("");

                    File convertFile = new File(directory.getAbsoluteFile() + "/" + ta.getFileName());
                    convertFile.createNewFile();
                    FileOutputStream fout = new FileOutputStream(convertFile);
                    fout.write(data);
                    fout.close();

                    ta = ticketAtchRepo.save(ta);

                    attachments = attachments + mp2s.getFilename() + ";";
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, Object> saveCallReport(CallReport callrepo) {

        Map<String, Object> resp = new HashMap<String, Object>();
        try {
            callrepo = callRepo.save(callrepo);
        } catch (Exception ex) {
            ex.printStackTrace();
            resp.putAll(Util.FailedResponse());
        }
        resp.put("callRepo", callrepo);
        resp.putAll(Util.SuccessResponse());
        return resp;
    }

    @Override
    public Map<String, Object> getCallReport(String instituteId) {
        Map<String, Object> resp = new HashMap<>();
        Institute inst = new Institute();
        List<CallReport> callReport = new ArrayList<>();

        try {
            inst = instRepo.findByInstituteId(instituteId);
            callReport = callRepo.findByInstituteId(instituteId);

            if (callReport != null) {
                resp.put("institute", inst);
                resp.put("callReport", callReport);
                resp.putAll(Util.SuccessResponse());
            } else
                resp.putAll(Util.FailedResponse());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resp;

    }

    @Override
    public Map<String, Object> saveCallReportAttachment(MultipartFile file, int id) {
        Map<String, Object> resp = new HashMap<>();
        try {

            CallReport callReport = callRepo.findById(id);
            callReport.setFileName(callReport.getId() + ".pdf");
            callReport.setFileSize(file.getSize());
            callReport.setFileType(file.getContentType());

            s3StorageService.pushToAWS(S3Directories.CallReports + callReport.getInstituteId(), file, callReport.getFileName());

            callReport = callRepo.save(callReport);

            resp.put("CallReport", callReport);
            resp.putAll(Util.SuccessResponse());
        } catch (Exception ex) {
            ex.printStackTrace();
            resp.putAll(Util.FailedResponse());
        }
        return resp;
    }

    @Override
    public Map<String, Object> getCallReportPdfTemplate(CallReportRequest callRepoReq) {
        Map<String, Object> resp = new HashMap<>();
        CallReport cr = new CallReport();
        Institute inst = new Institute();

        try {
            InputStream stream = null;

            stream = this.getClass().getResourceAsStream("/reports/CallReport/Call_Report_Template.jrxml");

            final Map<String, Object> parameters = new HashMap<>();

            InfoDetails info = infoDetailRepo.findById(1);
            cr = callRepo.findById(callRepoReq.getId());
            callRepoReq.setCallReport(callRepo.findById(callRepoReq.getId()));
            inst = instRepo.findByInstituteId(cr.getInstituteId());
            Agent agent = agentRepo.findByEmailId(callRepoReq.getSignatureBy());

            parameters.put("cmp_name", info.getCmpName());
            parameters.put("cmp_address", info.getCompanyAddressHTML1());
            parameters.put("cmp_logo_url", info.getLogoAsFile());

            parameters.put("roundseal", callRepoReq.isAddRoundSeal() ? info.getRoundSealAsFile() : null);
            parameters.put("fullseal", callRepoReq.isAddFullSeal() ? info.getFullSealAsFile() : null);
//			parameters.put("signature",callRepoReq.isAddSign() ? agent.getSignatureAsFile() : null);
            parameters.put("for_label", "For " + info.getCmpName());
            parameters.put("designation", callRepoReq.getDesignation());

            parameters.put("reporting_in_time", "Reporting InTime : "
                    + Util.sdfFormatter(callRepoReq.getCallReport().getReportingInTime(), "dd/MM/yyyy"));
            parameters.put("reporting_out_time", "Reporting OutTime : "
                    + Util.sdfFormatter(callRepoReq.getCallReport().getReportingOutTime(), "dd/MM/yyyy"));

            parameters.put("heading_label", " Call Report");

            parameters.put("action_taken", "Action Taken : <br>" + cr.getActionTaken());
            parameters.put("customer_remarks", "Customer Remarks: <br>" + cr.getCustomerRemarks());
            parameters.put("follow_up_action", "FOR OFFICE USE - Follow up Action :<br>" + cr.getFollowUpAction());
            parameters.put("description_of_supplied",
                    "Description Of Name Supplied:<br>" + cr.getDescriptionOfNameSupplied());
            parameters.put("problems_reported", "Problems Reported :<br>" + cr.getProblemsReported());
            parameters.put("customer_name", "Customer Name : <br>" + callRepoReq.getCallReport().getCustomerName());

            System.out.println();
            parameters.put("agent_name", "Staff <br>" + agent.getFirstName());

            String billingLabel = "";

            billingLabel = billingLabel + "Customer Name and Address : <br>";
            billingLabel = billingLabel + inst.getInstituteName() + "<br>" + "";
            billingLabel = billingLabel + inst.getStreet1() + "<br>" + "";
            billingLabel = billingLabel + inst.getStreet2() + "<br>" + "";
            billingLabel = billingLabel + inst.getCity() + "<br>" + "";

            parameters.put("billing_to", billingLabel);
//			parameters.put("shipping_to", dealReq.getShippingToAddress());

            System.out.println(parameters.toString());

            List<Map<String, String>> datasource = new ArrayList<>();

            final JasperReport report = JasperCompileManager.compileReport(stream);
            final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(datasource);

            final JasperPrint print = JasperFillManager.fillReport(report, parameters, source);

            File directory = new File(LocalDirectory.CallReports + inst.getInstituteId());
            System.out.println(directory.getAbsolutePath());
            if (!directory.exists()) {
                System.out.println("Directory created ::" + directory.getAbsolutePath());
                directory.mkdirs();
            }

            System.out.println("=[==============" + cr.getId());
            callRepoReq.setFilename(cr.getId() + ".pdf");

            callRepoReq.getCallReport().setFileName(cr.getId() + ".pdf");

            final String filePath = directory.getAbsolutePath() + "/" + callRepoReq.getFilename();
            System.out.println(filePath);

            callRepo.save(callRepoReq.getCallReport());

            // Export the report to a PDF file.
            JasperExportManager.exportReportToPdfFile(print, filePath);

            s3StorageService.pushLocalFileToAWS(S3Directories.CallReports + inst.getInstituteId(),
                    S3Directories.CallReports + inst.getInstituteId() + "/" + callRepoReq.getFilename());

            resp.putAll(Util.SuccessResponse());
        } catch (Exception e) {
            resp.putAll(Util.FailedResponse(e.getMessage()));
            e.printStackTrace();
        }
        resp.put("Institutes", callRepoReq.getInstitute());
        resp.put("CallReport", callRepoReq.getCallReport());
        return resp;
    }

    @Override
    public Map<String, Object> deleteCallReport(CallReport callreport) {
        Map<String, Object> resp = new HashMap<>();

        try {
            callreport = callRepo.findById(callreport.getId());
            if (callRepo == null) {
                resp.putAll(Util.invalidMessage("callReport Not Found"));
            } else {
                callRepo.delete(callreport);
                resp.putAll(Util.SuccessResponse());
            }
        } catch (Exception Ex) {
            Ex.printStackTrace();
            resp.putAll(Util.FailedResponse(Ex.getMessage()));
        }

        return resp;
    }

    @Override
    public Map<String, Object> getTicketDashboardDetails(TicketReportRequest ticketRequest) {
        Map<String, Object> resp = new HashMap<>();
        List<Map<String, Object>> assignedCount = new ArrayList<>();

        try {
            String filterQuery = "";

            if (ticketRequest.getAgents().size() > 0) {
                String agents = "'0'";
                for (Agent agnt : ticketRequest.getAgents()) {
                    agents = agents + ",'" + agnt.getEmailId() + "'";
                }
                filterQuery = filterQuery + " and assigned_to in (" + agents + ") ";
            }

            // Query query = entityManager.createQuery("SELECT COUNT(*) AS assigned FROM
            // tickets WHERE 2>1 AND STATUS='Assigned'" + filterQuery + " and
            // DATE(createddatetime) BETWEEN CURDATE() - INTERVAL 30 DAY AND CURDATE()" ,
            // Ticket.class);

            assignedCount = jdbc.queryForList(
                    "SELECT DISTINCT STATUS,COUNT(*) as ticketCount,assigned_to FROM tickets WHERE 2>1 " + filterQuery
                            + " and DATE(createddatetime) BETWEEN CURDATE() - INTERVAL 30 DAY AND CURDATE() GROUP BY assigned_to,STATUS");
            resp.put("DashBoardData", assignedCount);

            resp.putAll(Util.SuccessResponse());
        } catch (Exception ex) {
            resp.putAll(Util.FailedResponse(ex.getMessage()));
            ex.printStackTrace();
        }
        return resp;
    }

    @Override
    public Map<String, Object> getAgentPerformanceDetails(TicketRequest request) {
        Map<String, Object> resp = new HashMap<>();
        List<Map<String, Object>> pendingTicketsStatusCounts = new ArrayList<>();
        List<Map<String, Object>> pendingTicketsPriorityCounts = new ArrayList<>();
        List<Map<String, Object>> periodicallyClosedCounts = new ArrayList<>();
        List<Map<String, Object>> ticketRatingCounts = new ArrayList<>();
        int _total_ratings = 0;
        double _final_ratings = 0;

        try {

            pendingTicketsStatusCounts = ticketRepo
                    .findAgentPendingTicketsStatusCounts(request.getAgent().getEmailId());

            pendingTicketsPriorityCounts = ticketRepo
                    .findAgentPendingTicketsPriorityCounts(request.getAgent().getEmailId());

            periodicallyClosedCounts = ticketRepo.findAgentPeriodicallyClosedCounts(request.getAgent().getEmailId());

            List<Map<String, Object>> _ticketRatingCounts = ticketRepo
                    .findAgentTicketRatingCounts(request.getAgent().getEmailId());

            _total_ratings = _ticketRatingCounts.stream()
                    .mapToInt(rating -> Integer.parseInt(String.valueOf(rating.get("no_of_ratings")))).sum();

            if (_total_ratings > 0) {

                for (int i : Arrays.asList(1, 2, 3, 4, 5)) {

                    Map<String, Object> _rating = new HashMap<>(_ticketRatingCounts.stream()
                            .filter(rating -> String.valueOf(rating.get("rating")).equalsIgnoreCase(String.valueOf(i)))
                            .findFirst().get());

                    int _no_of_rating = Integer.parseInt(_rating.get("no_of_ratings").toString());

                    _rating.put("percent", getRatingPercent(_no_of_rating, _total_ratings));

                    int _rating_percent = getRatingPercent(_no_of_rating, _total_ratings);

                    System.out.println(_total_ratings + " : " + i + " : " + _no_of_rating + " : " + _rating_percent);

                    ticketRatingCounts.add(_rating);
                }

                Query query = entityManager.createNativeQuery(
                        "SELECT IFNULL(AVG(rating),0) AS _average_rating FROM ticket_ratings " + " WHERE ticket_id IN "
                                + "(SELECT ticket_id FROM tickets WHERE assigned_to = :assignedTo AND STATUS='Closed') ");
                query.setParameter("assignedTo", request.getAgent().getEmailId());

                _final_ratings = Double.parseDouble(query.getResultList().get(0).toString());

            }

            resp.putAll(Util.SuccessResponse());
        } catch (Exception ex) {
            resp.putAll(Util.FailedResponse(ex.getMessage()));
            ex.printStackTrace();
        }
        resp.put("PendingTicketsStatusCounts", pendingTicketsStatusCounts);
        resp.put("PendingTicketsPriorityCounts", pendingTicketsPriorityCounts);
        resp.put("PeriodicallyClosedCounts", periodicallyClosedCounts);
        resp.put("TicketRatingCounts", ticketRatingCounts);
        resp.put("TotalTicketRatingCounts", _total_ratings);
        resp.put("FinalRatings", _final_ratings);

        return resp;
    }

    int getRatingPercent(int _no_of_rating, int _total_ratings) {
        int percent = 0;

        percent = (_no_of_rating * 100) / _total_ratings;

        return percent;
    }

    public Map<String, Object> getInstituteServiceReport(ServiceReportRequest ticket) {

        BCryptPasswordEncoder pwdEncrypt = new BCryptPasswordEncoder();
        Map<String, Object> resp = new HashMap<>();
        try {
            InputStream stream = null;
            Institute inst = new Institute();

            System.out.println("Institute Id" + ticket.toString());

            stream = this.getClass().getResourceAsStream("/reports/ServiceReport/Service_Report_Template.jrxml");

            final Map<String, Object> parameters = new HashMap<>();

            InfoDetails info = infoDetailRepo.findById(1);

            parameters.put("cmp_name", info.getCmpName());
            parameters.put("cmp_address", info.getCompanyAddressHTML1());
            parameters.put("cmp_logo_url", info.getLogoAsFile());

            parameters.put("From Date", Util.sdfFormatter(ticket.getFromDate(), "dd/MM/yyyy"));
            parameters.put("To Date", Util.sdfFormatter(ticket.getToDate(), "dd/MM/yyyy"));

            parameters.put("dealtype_label", " Service Report");


            if (ticket.getFromDate() != null && ticket.getToDate() != null) {
                ticket.setTicket(ticketRepo.findByInstitute(ticket.getInstitutes().get(0)));
            } else {

                ticket.setTicket(ticketRepo.findByInstitute(ticket.getInstitutes().get(0)));
            }

            inst = instRepo.findByInstituteId(ticket.getInstitutes().get(0).getInstituteId());

            parameters.put("institute_name", "Institute Name : " + inst.getInstituteName());

            List<Map<String, String>> datasource = new ArrayList<>();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            ticket.getTicket().forEach(tkt -> {
                Map<String, String> data = new HashMap<>();
                data.put("ticket_id", String.valueOf(tkt.getTicketId()));
                data.put("service_under", String.valueOf((tkt.getServiceUnder())));
                data.put("subject", StringUtils.newStringUtf8(Base64.decodeBase64(tkt.getSubject())));
                data.put("service_type", String.valueOf(tkt.getServiceType()));
                data.put("product", String.valueOf(tkt.getProduct()));
                data.put("priority", String.valueOf(tkt.getPriority()));
                data.put("created_time", sdf.format(tkt.getCreateddatetime()));
                data.put("closed_time", sdf.format(tkt.getClosedDateTime()));
                datasource.add(data);
            });

            final JasperReport report = JasperCompileManager.compileReport(stream);
            final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(datasource);

            final JasperPrint print = JasperFillManager.fillReport(report, parameters, source);

            File directory = new File(LocalDirectory.ServiceReports + ticket.getInstitutes().get(0).getInstituteId());
            if (!directory.exists()) {
                System.out.println("Directory created ::" + directory.getAbsolutePath());
                directory.mkdirs();
            }

            final String filePath = directory.getAbsolutePath() + "/" + ticket.getInstitutes().get(0).getInstituteName()
                    + ".pdf";
            System.out.println(filePath);

            // Export the report to a PDF file.
            JasperExportManager.exportReportToPdfFile(print, filePath);
            s3StorageService.pushLocalFileToAWS(
                    S3Directories.ServiceReports + ticket.getInstitutes().get(0).getInstituteId(),
                    S3Directories.ServiceReports + ticket.getInstitutes().get(0).getInstituteId()
                            + "/" + ticket.getInstitutes().get(0).getInstituteName() + ".pdf");

            resp.putAll(Util.SuccessResponse());
        } catch (Exception e) {
            resp.putAll(Util.FailedResponse(e.getMessage()));
            e.printStackTrace();
        }
        resp.put("ServiceReport", ticket.getTicket());
        resp.put("InstituteId", ticket.getInstitutes().get(0).getInstituteId());
        resp.put("InstituteName", ticket.getInstitutes().get(0).getInstituteName());
        return resp;

    }

}
