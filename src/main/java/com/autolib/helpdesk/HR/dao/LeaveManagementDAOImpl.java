package com.autolib.helpdesk.HR.dao;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.autolib.helpdesk.Agents.entity.Agent;
import com.autolib.helpdesk.Agents.entity.InfoDetails;
import com.autolib.helpdesk.Agents.entity.PushNotification;
import com.autolib.helpdesk.Agents.repository.AgentRepository;
import com.autolib.helpdesk.Agents.repository.InfoDetailsRepository;
import com.autolib.helpdesk.HR.entity.Holiday;
import com.autolib.helpdesk.HR.entity.LeaveApplied;
import com.autolib.helpdesk.HR.entity.LeaveAppliedDates;
import com.autolib.helpdesk.HR.entity.LeaveAppliedSearchRequest;
import com.autolib.helpdesk.HR.entity.LeaveBalance;
import com.autolib.helpdesk.HR.entity.LeaveMaster;
import com.autolib.helpdesk.HR.entity.SalaryDetails;
import com.autolib.helpdesk.HR.repository.HolidayRepository;
import com.autolib.helpdesk.HR.repository.LeaveAppliedDatesRepository;
import com.autolib.helpdesk.HR.repository.LeaveAppliedRepository;
import com.autolib.helpdesk.HR.repository.LeaveBalanceRepository;
import com.autolib.helpdesk.HR.repository.LeaveMasterRepository;
import com.autolib.helpdesk.HR.repository.SalaryDetailsRepository;
import com.autolib.helpdesk.common.EmailModel;
import com.autolib.helpdesk.common.EmailSender;
import com.autolib.helpdesk.common.Util;


@Repository
@Transactional
public class LeaveManagementDAOImpl implements LeaveManagementDAO {

	@Autowired
	private LeaveMasterRepository lmRepo;

	@Autowired
	private LeaveAppliedRepository laRepo;

	@Autowired
	private LeaveAppliedDatesRepository laDatesRepo;

	@Autowired
	private LeaveBalanceRepository leaveBalanceRepo;

	@Autowired
	private AgentRepository agentRepo;

	@Autowired
	private SalaryDetailsRepository sdRepo;

	@Autowired
	private EntityManager em;

	@Autowired
	private EmailSender emailSender;
	
	@Autowired
	private AgentRepository agenRepo;
	
	@Autowired
	private InfoDetailsRepository infoRepo;
	
	@Autowired
	private HolidayRepository holidayRepo;
	
	@Autowired
	private PushNotification pushNotification;
	
	@Autowired
	private JdbcTemplate jdbcTemp;
	
	
	
	@Override
	public Map<String, Object> saveLeaveMaster(LeaveMaster leaveMaster) {

		Map<String, Object> resp = new HashMap<>();
		try {

			leaveMaster = lmRepo.save(leaveMaster);

			resp.putAll(Util.SuccessResponse());

		} catch (Exception ex) {
			resp.putAll(Util.FailedResponse(ex.getMessage()));
			ex.printStackTrace();
		}

		resp.put("LeaveMaster", leaveMaster);
		return resp;
	}

	@Override
	public Map<String, Object> getLeaveMaster(LeaveMaster leaveMaster) {

		Map<String, Object> resp = new HashMap<>();
		try {

			leaveMaster = lmRepo.findById(leaveMaster.getId());

			resp.putAll(Util.SuccessResponse());

		} catch (Exception ex) {
			resp.putAll(Util.FailedResponse(ex.getMessage()));
			ex.printStackTrace();
		}

		resp.put("LeaveMaster", leaveMaster);
		return resp;
	}

	@Transactional
	@Override
	public Map<String, Object> deleteLeaveMaster(LeaveMaster leaveMaster) {

		Map<String, Object> resp = new HashMap<>();
		try {

			List<Agent> agents = agentRepo.findByLeaveMasterId(leaveMaster.getId());

			if (agents.size() > 0) {
				resp.putAll(Util.invalidMessage(
						"Agents associated with this Leave Master, Unassociate the Agents first then try delete."));
			} else {
				lmRepo.delete(leaveMaster);

				resp.putAll(Util.SuccessResponse());

			}

		} catch (Exception ex) {
			resp.putAll(Util.FailedResponse(ex.getMessage()));
			ex.printStackTrace();
		}

		resp.put("LeaveMaster", leaveMaster);
		return resp;
	}

	@Override
	public Map<String, Object> getAllLeaveMasters() {

		Map<String, Object> resp = new HashMap<>();
		List<LeaveMaster> leaveMasters = new ArrayList<>();

		try {

			leaveMasters = lmRepo.findAll();

			resp.putAll(Util.SuccessResponse());

		} catch (Exception ex) {
			resp.putAll(Util.FailedResponse(ex.getMessage()));
			ex.printStackTrace();
		}

		resp.put("LeaveMasters", leaveMasters);
		return resp;
	}

	@org.springframework.transaction.annotation.Transactional
	@Override
	public Map<String, Object> saveLeaveApplied(LeaveApplied leaveApplied) {

		Map<String, Object> resp = new HashMap<>();
		List<LeaveAppliedDates> _la_dates = new ArrayList<>();

		try {

			leaveApplied = laRepo.save(leaveApplied);

			// Sending Approved Rejected
			
			
			if (leaveApplied.getStatus().equalsIgnoreCase("Applied")) {
				
				sendLeaveAppliedMailAction(leaveApplied);
		
			pushNotification.sendAppliedNotification(leaveApplied);
			}

			if (leaveApplied.getStatus().equalsIgnoreCase("Approved")
					|| leaveApplied.getStatus().equalsIgnoreCase("Rejected")) {
				
				
				sendLeaveApprovedRejectedMailAction(leaveApplied);

				pushNotification.sendLeaveApproveRejectNotification(leaveApplied);
			}

			// Updating Leave Balance and Leave Dates

			LeaveBalance balance = leaveBalanceRepo.findByEmailId(leaveApplied.getAgentEmailId());

			SalaryDetails sd = sdRepo.findByEmployeeEmailId(leaveApplied.getAgentEmailId());

			if (leaveApplied.getStatus().equalsIgnoreCase("Approved")) {

				if (leaveApplied.getLeaveType().equalsIgnoreCase("Permission")) {
					balance.setPermissions(balance.getPermissions() - 1);
				} else if (leaveApplied.getLeaveType().equalsIgnoreCase("Half day")) {

					if (balance.getCasualLeave() >= 0.5) {
						balance.setCasualLeave(balance.getCasualLeave() - 0.5);
					} else if (balance.getAnnualLeave() >= 0.5) {
						balance.setAnnualLeave(balance.getAnnualLeave() - 0.5);
					} else {
						balance.setAnnualLeave(balance.getAnnualLeave() - 0.5);

						LeaveAppliedDates _la_date = new LeaveAppliedDates();

						_la_date.setLeaveDate(leaveApplied.getLeaveFrom());
						_la_date.setLeaveAppliedId(leaveApplied.getId());
						_la_date.setAgentEmailId(leaveApplied.getAgentEmailId());
						_la_date.setLop(sd.getLopPerDay() / 2);

						_la_dates.add(_la_date);
					}

				} else {

					LocalDate start = LocalDate.parse(Util.sdfFormatter(leaveApplied.getLeaveFrom()));
					LocalDate end = LocalDate.parse(Util.sdfFormatter(leaveApplied.getLeaveTo()));
					List<LocalDate> totalDates = new ArrayList<>();
					while (!start.isAfter(end)) {
						totalDates.add(start);
						start = start.plusDays(1);
					}
					LeaveAppliedDates _la_date = null;
					for (LocalDate _ld : totalDates) {

						_la_date = new LeaveAppliedDates();

						_la_date.setLeaveDate(Date.from(_ld.atStartOfDay(ZoneId.systemDefault()).toInstant()));
						_la_date.setLeaveAppliedId(leaveApplied.getId());
						_la_date.setAgentEmailId(leaveApplied.getAgentEmailId());
						_la_date.setLop(0.00);

						_la_dates.add(_la_date);

					}

					if (leaveApplied.getLeaveType().equalsIgnoreCase("Annual Leave")) {
						if (balance.getAnnualLeave() >= leaveApplied.getNoOfDays()) {

						} else if (balance.getAnnualLeave() <= 0) {
							_la_dates.forEach(_lad -> _lad.setLop(sd.getLopPerDay()));
						} else {
							System.out.println("3333333333333");

							// if it comes to this block LOP will be applied based on leave balance

							Collections.sort(_la_dates, (x, y) -> y.getLeaveDate().compareTo(x.getLeaveDate()));

							double _lop = (double) leaveApplied.getNoOfDays() - balance.getAnnualLeave();

							for (LeaveAppliedDates _lad : _la_dates) {
								if (_lop > 0.5) {
									_lad.setLop(sd.getLopPerDay());
									_lop = _lop - 1;
								} else if (_lop == 0.5) {
									_lad.setLop(sd.getLopPerDay() / 2);
									_lop = _lop - 0.5;
								}
							}
						}

						balance.setAnnualLeave(balance.getAnnualLeave() - leaveApplied.getNoOfDays());

					} else if (leaveApplied.getLeaveType().equalsIgnoreCase("Casual Leave")) {
						if (balance.getCasualLeave() >= leaveApplied.getNoOfDays()) {

						} else if (balance.getCasualLeave() <= 0) {
							_la_dates.forEach(_lad -> _lad.setLop(sd.getLopPerDay()));
						} else {
							System.out.println("3333333333333");

							// if it comes to this block LOP will be applied based on leave balance

							Collections.sort(_la_dates, (x, y) -> y.getLeaveDate().compareTo(x.getLeaveDate()));

							double _lop = (double) leaveApplied.getNoOfDays() - balance.getCasualLeave();

							for (LeaveAppliedDates _lad : _la_dates) {
								if (_lop > 0.5) {
									_lad.setLop(sd.getLopPerDay());
									_lop = _lop - 1;
								} else if (_lop == 0.5) {
									_lad.setLop(sd.getLopPerDay() / 2);
									_lop = _lop - 0.5;
								}
							}
						}
						balance.setCasualLeave(balance.getCasualLeave() - leaveApplied.getNoOfDays());
					} else if (leaveApplied.getLeaveType().equalsIgnoreCase("Sick Leave")) {
						if (balance.getSickLeave() >= leaveApplied.getNoOfDays()) {

						} else if (balance.getSickLeave() <= 0) {
							_la_dates.forEach(_lad -> _lad.setLop(sd.getLopPerDay()));
						} else {
							System.out.println("3333333333333");

							// if it comes to this block LOP will be applied based on leave balance

							Collections.sort(_la_dates, (x, y) -> y.getLeaveDate().compareTo(x.getLeaveDate()));

							double _lop = (double) leaveApplied.getNoOfDays() - balance.getSickLeave();

							for (LeaveAppliedDates _lad : _la_dates) {
								if (_lop > 0.5) {
									_lad.setLop(sd.getLopPerDay());
									_lop = _lop - 1;
								} else if (_lop == 0.5) {
									_lad.setLop(sd.getLopPerDay() / 2);
									_lop = _lop - 0.5;
								}
							}
						}
						balance.setSickLeave(balance.getSickLeave() - leaveApplied.getNoOfDays());
					} else if (leaveApplied.getLeaveType().equalsIgnoreCase("Other Leave")) {
						if (balance.getOtherLeave() >= leaveApplied.getNoOfDays()) {

						} else if (balance.getOtherLeave() <= 0) {
							_la_dates.forEach(_lad -> _lad.setLop(sd.getLopPerDay()));
						} else {
							System.out.println("3333333333333");

							// if it comes to this block LOP will be applied based on leave balance

							Collections.sort(_la_dates, (x, y) -> y.getLeaveDate().compareTo(x.getLeaveDate()));

							double _lop = (double) leaveApplied.getNoOfDays() - balance.getOtherLeave();

							for (LeaveAppliedDates _lad : _la_dates) {
								if (_lop > 0.5) {
									_lad.setLop(sd.getLopPerDay());
									_lop = _lop - 1;
								} else if (_lop == 0.5) {
									_lad.setLop(sd.getLopPerDay() / 2);
									_lop = _lop - 0.5;
								}
							}
						}
						balance.setOtherLeave(balance.getOtherLeave() - leaveApplied.getNoOfDays());
					}
				}

				// Saving Leave Dates

				Collections.sort(_la_dates, (x, y) -> x.getLeaveDate().compareTo(y.getLeaveDate()));
				laDatesRepo.saveAll(_la_dates);

				// Saving Balance
				leaveBalanceRepo.save(balance);
				
			}

			resp.putAll(Util.SuccessResponse());

		} catch (Exception ex) {
			resp.putAll(Util.FailedResponse(ex.getMessage()));
			ex.printStackTrace();
		}

		resp.put("LeaveApplied", leaveApplied);
		return resp;
	}

//	void deductLeaveFromBalance(LeaveApplied leaveApplied) {
//
//		LeaveBalance balance = leaveBalanceRepo.findByEmailId(leaveApplied.getAgentEmailId());
//
//		if (balance != null && !leaveApplied.getLeaveType().equalsIgnoreCase("permissions")) {
//
//			if (leaveApplied.getLeaveType().equalsIgnoreCase("Annual Leave"))
//				balance.setAnnualLeave(balance.getAnnualLeave() - leaveApplied.getNoOfDays());
//
//			else if (leaveApplied.getLeaveType().equalsIgnoreCase("Casual Leave"))
//				balance.setCasualLeave(balance.getCasualLeave() - leaveApplied.getNoOfDays());
//
//			else if (leaveApplied.getLeaveType().equalsIgnoreCase("Sick Leave"))
//				balance.setSickLeave(balance.getSickLeave() - leaveApplied.getNoOfDays());
//
//			else if (leaveApplied.getLeaveType().equalsIgnoreCase("Other Leave"))
//				balance.setOtherLeave(balance.getOtherLeave() - leaveApplied.getNoOfDays());
//
//			else if (leaveApplied.getLeaveType().equalsIgnoreCase("Half Day")) {
//
//				if (balance.getCasualLeave() > 0) {
//					balance.setCasualLeave(balance.getCasualLeave() - leaveApplied.getNoOfDays());
//				} else {
//					balance.setAnnualLeave(balance.getAnnualLeave() - leaveApplied.getNoOfDays());
//				}
//			}
//
//			else if (leaveApplied.getLeaveType().equalsIgnoreCase("Permissions"))
//				balance.setPermissions(balance.getPermissions() - 1);
//
//			leaveBalanceRepo.save(balance);
//		}
//
//	}

	void sendLeaveApprovedRejectedMailAction(LeaveApplied leaveApplied) {
		try {

			EmailModel emailModel = new EmailModel("Common");
			
			

			emailModel.setMailTo(leaveApplied.getAgentEmailId());
			if (leaveApplied.getLeaveType().equalsIgnoreCase("permission"))
				emailModel.setMailSub("Permission - " + leaveApplied.getStatus());
			else
				emailModel.setMailSub("Leave - " + leaveApplied.getStatus());

			StringBuffer sb = new StringBuffer();

			sb.append("Hi,<br><br>");
			sb.append("<h3>Your " + leaveApplied.getLeaveType() + " is " + leaveApplied.getStatus() + "</h3>");

			sb.append("<table>");
			sb.append("<tr>");
			sb.append("<td>Leave Type</td>");
			sb.append("<td> : </td>");
			sb.append("<td> " + leaveApplied.getLeaveType() + " </td>");
			sb.append("</tr>");

			sb.append("<tr>");
			sb.append("<td> " + leaveApplied.getLeaveType() + "Date</td>");
			sb.append("<td> : </td>");
			if (leaveApplied.getLeaveType().equalsIgnoreCase("permission")) {
				sb.append("<td>" + Util.sdfFormatter(leaveApplied.getLeaveFrom(), "dd/MM/yyyy") + "("
						+ Util.sdfFormatter(leaveApplied.getLeaveFrom(), "hh:mm a") + " - "
						+ Util.sdfFormatter(leaveApplied.getLeaveTo(), "hh:mm a") + ")</td>");
			} else {
				sb.append("<td>" + Util.sdfFormatter(leaveApplied.getLeaveFrom(), "dd/MM/yyyy") + " - "
						+ Util.sdfFormatter(leaveApplied.getLeaveTo(), "dd/MM/yyyy") + " ( "
						+ leaveApplied.getNoOfDays() + "days(s) )</td>");
			}

			sb.append("</tr>");

			sb.append("<tr>");
			sb.append("<td>Applied Date</td>");
			sb.append("<td> : </td>");
			sb.append("<td> " + Util.sdfFormatter(leaveApplied.getCreateddatetime(), "dd/MM/yyyy hh:mm a") + " </td>");
			sb.append("</tr>");

			sb.append("<tr>");
			sb.append("<td>Reason</td>");
			sb.append("<td> : </td>");
			if (leaveApplied.getReason() != null)
				sb.append("<td> " + leaveApplied.getReason().replaceAll("\n", "<br>").replaceAll("\r", "<br>")
						+ " </td>");
			else
				sb.append("<td> - </td>");
			sb.append("</tr>");
			sb.append("<table>");

			sb.append("<h6>" + leaveApplied.getStatus() + " by " + leaveApplied.getApprovedRejectedBy() + " at "
					+ Util.sdfFormatter(leaveApplied.getLeaveTo(), "dd/MM/yyyy hh:mm a") + "</h6>");

			emailModel.setMailText(sb.toString());

			emailSender.sendmail(emailModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	void sendLeaveAppliedMailAction(LeaveApplied leaveApplied) {
		try {

			EmailModel emailModel = new EmailModel("Common");
			
			Agent agent = agentRepo.findByEmailId(leaveApplied.getAgentEmailId());
			InfoDetails info = infoRepo.findById(1);

			emailModel.setMailTo(info.getCmpEmail());
			
			
			if (leaveApplied.getLeaveType().equalsIgnoreCase("permission"))
				emailModel.setMailSub("Permission - " + leaveApplied.getStatus());
			else
				emailModel.setMailSub("Leave - " + leaveApplied.getStatus());

			StringBuffer sb = new StringBuffer();

			sb.append("Hi,<br><br>");
			sb.append("<h3>"+agent.getFirstName()+" "+agent.getLastName()+" "+leaveApplied.getStatus()+" a "+leaveApplied.getLeaveType() + " "+"</h3>");

			sb.append("<table>");
			sb.append("<tr>");
			sb.append("<td>Leave Type</td>");
			sb.append("<td> : </td>");
			sb.append("<td> " + leaveApplied.getLeaveType() + " </td>");
			sb.append("</tr>");

			sb.append("<tr>");
			sb.append("<td> " + leaveApplied.getLeaveType() + "Date</td>");
			sb.append("<td> : </td>");
			if (leaveApplied.getLeaveType().equalsIgnoreCase("permission")) {
				sb.append("<td>" + Util.sdfFormatter(leaveApplied.getLeaveFrom(), "dd/MM/yyyy") + "("
						+ Util.sdfFormatter(leaveApplied.getLeaveFrom(), "hh:mm a") + " - "
						+ Util.sdfFormatter(leaveApplied.getLeaveTo(), "hh:mm a") + ")</td>");
			} else {
				sb.append("<td>" + Util.sdfFormatter(leaveApplied.getLeaveFrom(), "dd/MM/yyyy") + " - "
						+ Util.sdfFormatter(leaveApplied.getLeaveTo(), "dd/MM/yyyy") + " ( "
						+ leaveApplied.getNoOfDays() + "days(s) )</td>");
			}

			sb.append("</tr>");

			sb.append("<tr>");
			sb.append("<td>Applied Date</td>");
			sb.append("<td> : </td>");
			sb.append("<td> " + Util.sdfFormatter(leaveApplied.getCreateddatetime(), "dd/MM/yyyy hh:mm a") + " </td>");
			sb.append("</tr>");

			sb.append("<tr>");
			sb.append("<td>Reason</td>");
			sb.append("<td> : </td>");
			if (leaveApplied.getReason() != null)
				sb.append("<td> " + leaveApplied.getReason().replaceAll("\n", "<br>").replaceAll("\r", "<br>")
						+ " </td>");
			else
				sb.append("<td> - </td>");
			sb.append("</tr>");
			sb.append("<table>");

			sb.append("<h6>" + leaveApplied.getStatus() + " by " +agent.getFirstName()+" "+agent.getLastName()+ " at "
					+ Util.sdfFormatter(leaveApplied.getLeaveTo(), "dd/MM/yyyy hh:mm a") + "</h6>");

			emailModel.setMailText(sb.toString());

			emailSender.sendmail(emailModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Map<String, Object> getMyAllLeaveApplied(Agent agent) {

		Map<String, Object> resp = new HashMap<>();
		List<LeaveApplied> leavesApplied = new ArrayList<>();
		LeaveMaster leaveMaster = null;
		LeaveBalance leaveBalance = null;

		try {

			leaveMaster = lmRepo.findByAgentEmailId(agent.getEmailId());

			leavesApplied = laRepo.findByAgentEmailId(agent.getEmailId());

			leaveBalance = leaveBalanceRepo.findByEmailId(agent.getEmailId());

			resp.putAll(Util.SuccessResponse());

		} catch (Exception ex) {
			resp.putAll(Util.FailedResponse(ex.getMessage()));
			ex.printStackTrace();
		}

		resp.put("LeavesApplied", leavesApplied);
		resp.put("LeaveMaster", leaveMaster);
		resp.put("LeaveBalance", leaveBalance);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> searchLeaveApplied(LeaveAppliedSearchRequest request) {

		Map<String, Object> resp = new HashMap<>();
		List<LeaveApplied> leavesApplied = new ArrayList<>();

		try {

			String filterQuery = "";

			if (request.getLeaveType() != null && !request.getLeaveType().isEmpty())
				filterQuery = filterQuery + " and la.leaveType = '" + request.getLeaveType() + "' ";

			if (request.getStatus() != null && !request.getStatus().isEmpty())
				filterQuery = filterQuery + " and la.status = '" + request.getStatus() + "' ";

			if (request.getReason() != null && !request.getReason().isEmpty())
				filterQuery = filterQuery + " and la.reason like '" + request.getReason() + "' ";

			if (request.getAppliedBy() != null && !request.getAppliedBy().isEmpty()) {
				String agents = "'0'";
				for (Agent agnt : request.getAppliedBy()) {
					agents = agents + ",'" + agnt.getEmailId() + "'";
				}
				filterQuery = filterQuery + " and la.agentEmailId in (" + agents + ") ";
			}

			if (request.getApprovedBy() != null && !request.getApprovedBy().isEmpty()) {
				String agents = "'0'";
				for (Agent agnt : request.getApprovedBy()) {
					agents = agents + ",'" + agnt.getEmailId() + "'";
				}
				filterQuery = filterQuery + " and la.approvedRejectedBy in (" + agents + ") ";
			}

			if (request.getNoOfDays() != null && !request.getNoOfDays().isEmpty()
					&& request.getNoOfDays().equalsIgnoreCase("0"))
				filterQuery = filterQuery + " and la.noOfDays = '" + request.getNoOfDays() + "' ";

			if (request.getAppliedDateFrom() != null && request.getAppliedDateTo() != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				filterQuery = filterQuery + " and la.createddatetime between '"
						+ sdf.format(request.getAppliedDateFrom()) + "' and '" + sdf.format(request.getAppliedDateTo())
						+ " 23:59:59' ";
			}

			if (request.getLeaveFrom() != null && request.getLeaveTo() != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				filterQuery = filterQuery + " and la.leaveFrom = '" + sdf.format(request.getLeaveFrom())
						+ "' and la.leaveTo = '" + sdf.format(request.getLeaveTo()) + "' ";
			}

			if (request.getApprovedDateFrom() != null && request.getApprovedDateTo() != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				filterQuery = filterQuery + " and la.approvedRejectedDate between '"
						+ sdf.format(request.getApprovedDateFrom()) + "' and '"
						+ sdf.format(request.getApprovedDateTo()) + " 23:59:59' ";
			}

			Query query = em.createQuery("select la from LeaveApplied la where 2 > 1 " + filterQuery,
					LeaveApplied.class);

			leavesApplied = query.getResultList();

			resp.putAll(Util.SuccessResponse());

		} catch (Exception ex) {
			resp.putAll(Util.FailedResponse(ex.getMessage()));
			ex.printStackTrace();
		}

		resp.put("LeavesApplied", leavesApplied);
		return resp;
	}

	@Override
	public Map<String, Object> getAllLeaveBalances() {

		Map<String, Object> resp = new HashMap<>();
		List<LeaveBalance> balances = new ArrayList<>();
		try {

			balances = leaveBalanceRepo.findAll();

			resp.putAll(Util.SuccessResponse());

		} catch (Exception ex) {
			resp.putAll(Util.FailedResponse(ex.getMessage()));
			ex.printStackTrace();
		}

		resp.put("LeaveBalances", balances);
		return resp;
	}

	public Map<String, Object> saveHoliday(Holiday holiday) {
			
		Map<String,Object> resp = new HashMap<>();
		try {			
			
			holiday = holidayRepo.save(holiday);
			resp.putAll(Util.SuccessResponse());
			resp.put("holiday",holiday);			
		}catch(Exception ex) {
			resp.putAll(Util.FailedResponse(ex.getMessage()));
			ex.printStackTrace();
		}
		return resp;
	}

	@Override
	public Map<String, Object> deleteHoliday(Holiday holiday) {
		Map<String,Object> resp = new HashMap<>();
		
		try {
			holidayRepo.delete(holiday);
			resp.putAll(Util.SuccessResponse());
		}catch(Exception ex){
			resp.putAll(Util.FailedResponse(ex.getMessage()));
			ex.printStackTrace();
		}
		return resp;
	}

	@Override
	public Map<String, Object> viewHolidays() {
		Map<String,Object> resp = new HashMap<>();
		
		List<Holiday> holidays = new ArrayList<>();
		
		try {
			holidays = holidayRepo.findAll();
			resp.putAll(Util.SuccessResponse());
			resp.put("holidays",holidays);
		}catch(Exception ex){
			resp.putAll(Util.FailedResponse(ex.getMessage()));
			ex.printStackTrace();
		}
		return resp;
	}

}
