package com.autolib.helpdesk.Attendance.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.autolib.helpdesk.Agents.entity.Agent;
import com.autolib.helpdesk.Agents.repository.AgentRepository;
import com.autolib.helpdesk.Attendance.model.Attendance;
import com.autolib.helpdesk.Attendance.model.AttendanceRequest;
import com.autolib.helpdesk.Attendance.model.SiteAttendance;
import com.autolib.helpdesk.Attendance.model.SiteAttendanceRequest;
import com.autolib.helpdesk.Attendance.model.WorkingDay;
import com.autolib.helpdesk.Attendance.model.WorkingDayRequest;
import com.autolib.helpdesk.Attendance.repository.AttendanceRepository;
import com.autolib.helpdesk.Attendance.repository.SiteAttendanceRepository;
import com.autolib.helpdesk.Attendance.repository.WorkingDayRepository;
import com.autolib.helpdesk.Institutes.model.Institute;
import com.autolib.helpdesk.common.EnumUtils.WorkingStatus;
import com.autolib.helpdesk.common.Util;

@Repository
public class AttendanceDAOImpl implements AttendanceDAO {

	@Autowired
	WorkingDayRepository wdRepo;
	@Autowired
	AttendanceRepository attRepo;
	@Autowired
	AgentRepository agentRepo;
	@Autowired
	SiteAttendanceRepository saRepo;
	@Autowired
	EntityManager em;

	@Override
	public Map<String, Object> addWorkingDay(WorkingDayRequest workingDayRequest) {
		Map<String, Object> resp = new HashMap<String, Object>();
		int failedCount = 0;
		try {

			for (WorkingDay wd : workingDayRequest.getWorkingDays()) {
				try {
					wdRepo.save(wd);
				} catch (Exception e) {
					failedCount = failedCount + 1;
					System.err.println("Already in the working date ::" + e.getMessage());
				}
			}

			resp.putAll(Util.SuccessResponse());
		} catch (Exception Ex) {
			resp.putAll(Util.FailedResponse(Ex.getMessage()));
			Ex.printStackTrace();
		}
		resp.put("failedCount", failedCount);
		return resp;
	}

	@Override
	public Map<String, Object> getWorkingDay(WorkingDayRequest workingDay) {
		Map<String, Object> resp = new HashMap<String, Object>();
		List<WorkingDay> workingdays = new ArrayList<>();
		try {

			workingdays = wdRepo.findByWorkingDateBetween(workingDay.getFromDate(), workingDay.getToDate());

			resp.putAll(Util.SuccessResponse());
		} catch (Exception Ex) {
			resp.putAll(Util.FailedResponse(Ex.getMessage()));
			Ex.printStackTrace();
		}
		resp.put("WorkingDays", workingdays);
		return resp;
	}

	@Override
	public Map<String, Object> deleteWorkingDay(WorkingDayRequest workingDay) {
		Map<String, Object> resp = new HashMap<String, Object>();
		List<WorkingDay> workingdays = new ArrayList<>();
		try {

			wdRepo.deleteInBatch(workingDay.getWorkingDays());

			resp.putAll(Util.SuccessResponse());
		} catch (Exception Ex) {
			resp.putAll(Util.FailedResponse(Ex.getMessage()));
			Ex.printStackTrace();
		}
		resp.put("WorkingDays", workingdays);
		return resp;
	}

	@Override
	public Map<String, Object> markAttendance(Attendance att) {
		Map<String, Object> resp = new HashMap<String, Object>();
		try {

			att.setStartTime(new Date());

			att = attRepo.save(att);

			resp.putAll(Util.SuccessResponse());
		} catch (Exception Ex) {
			resp.putAll(Util.FailedResponse(Ex.getMessage()));
			Ex.printStackTrace();
		}
		resp.put("Attendance", att);
		return resp;
	}

	@Override
	public Map<String, Object> prepareAttendance(WorkingDayRequest workingDay) {
		Map<String, Object> resp = new HashMap<String, Object>();
		List<WorkingDay> workingdays = new ArrayList<>();
		try {

			Iterable<Agent> agents = agentRepo.findAll();

			for (WorkingDay wd : workingDay.getWorkingDays()) {
				List<Attendance> attendances = attRepo.findByWorkingDate(wd.getWorkingDate());

				for (Agent agent : agents) {
					boolean _attendance_available = false;
					for (Attendance att : attendances) {
						if (att.getEmployeeId().equalsIgnoreCase(agent.getEmployeeId())) {
							_attendance_available = true;
						}
					}

					if (!_attendance_available) {
						Attendance attendance = new Attendance();
						attendance.setEmployeeId(agent.getEmployeeId());
						attendance.setWorkingDate(wd.getWorkingDate());
						attendance.setWorkingStatus(WorkingStatus.NONE);

						attRepo.save(attendance);
					}
				}

			}

			resp.putAll(Util.SuccessResponse());
		} catch (Exception Ex) {
			resp.putAll(Util.FailedResponse(Ex.getMessage()));
			Ex.printStackTrace();
		}
		resp.put("WorkingDays", workingdays);
		return resp;
	}

	@Override
	public Map<String, Object> saveAllAttendance(AttendanceRequest att) {
		Map<String, Object> resp = new HashMap<String, Object>();
		List<WorkingDay> workingdays = new ArrayList<>();
		try {
			System.out.println(att.getAttendances().size());
			attRepo.saveAll(att.getAttendances());

			resp.putAll(Util.SuccessResponse());
		} catch (Exception Ex) {
			resp.putAll(Util.FailedResponse(Ex.getMessage()));
			Ex.printStackTrace();
		}
		resp.put("WorkingDays", workingdays);
		return resp;
	}

	@Override
	public Map<String, Object> getAllAttendance(AttendanceRequest att) {
		Map<String, Object> resp = new HashMap<String, Object>();
		List<Attendance> attendances = new ArrayList<>();
		List<WorkingDay> workingdays = new ArrayList<>();
		List<Agent> agents = new ArrayList<>();
		try {

			workingdays = wdRepo.findByWorkingDateBetween(att.getFromDate(), att.getToDate());

			System.out.println(att.getAgents().stream().map(agent -> agent.getEmployeeId()).collect(Collectors.toList())
					.toString());

			if (att.getAgents().size() > 0) {
				attendances = attRepo.findByWorkingDateBetweenAndEmployeeIdIn(att.getFromDate(), att.getToDate(),
						att.getAgents().stream().map(agent -> agent.getEmployeeId()).collect(Collectors.toList()));
			} else {
				attendances = attRepo.findByWorkingDateBetween(att.getFromDate(), att.getToDate());
			}

			Set<String> empIds = new HashSet<>();
			for (Attendance attnd : attendances) {
				empIds.add(attnd.getEmployeeId());
			}

			if (empIds.size() > 0)
				agents = agentRepo.findMinByEmployeeIdIn(empIds);

			resp.putAll(Util.SuccessResponse());
		} catch (Exception Ex) {
			resp.putAll(Util.FailedResponse(Ex.getMessage()));
			Ex.printStackTrace();
		}
		resp.put("WorkingDays", workingdays);
		resp.put("Attendances", attendances);
		resp.put("Agents", agents);

		return resp;
	}

	@Override
	public Map<String, Object> markSiteAttendance(SiteAttendance sa) {

		Map<String, Object> resp = new HashMap<String, Object>();
		try {

			sa = saRepo.save(sa);

			resp.putAll(Util.SuccessResponse());
		} catch (Exception Ex) {
			resp.putAll(Util.FailedResponse(Ex.getMessage()));
			Ex.printStackTrace();
		}
		resp.put("SiteAttendance", sa);
		return resp;

	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getAllSiteAttendance(SiteAttendanceRequest sa) {
		List<SiteAttendance> siteAttendances = new ArrayList<>();
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			String filterQuery = "";

			if (sa.getInstitutes() != null && sa.getInstitutes().size() > 0) {
				String instituteIds = "0";
				for (Institute inst : sa.getInstitutes()) {
					instituteIds = instituteIds + "," + inst.getInstituteId();
				}
				filterQuery = filterQuery + " and s.institute in (" + instituteIds + ") ";
			}
			if (sa.getAgents() != null && sa.getAgents().size() > 0) {
				String agents = "'0'";
				for (Agent agnt : sa.getAgents()) {
					agents = agents + ",'" + agnt.getEmployeeId() + "'";
				}
				filterQuery = filterQuery + " and s.agent in (" + agents + ") ";
			}
			if (sa.getFromDate() != null && sa.getToDate() != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				filterQuery = filterQuery + " and s.startTime between '" + sdf.format(sa.getFromDate())
						+ " 00:00:00' and '" + sdf.format(sa.getToDate()) + " 23:59:59'";
			}

			Query query = em
					.createQuery("select s from SiteAttendance s INNER JOIN FETCH s.institute i "
							+ "INNER JOIN FETCH s.agent a where 2 > 1 " + filterQuery, SiteAttendance.class)
					.setFirstResult(sa.getSetFrom()).setMaxResults(sa.getNoOfRecords());

			siteAttendances = query.getResultList();

			resp.putAll(Util.SuccessResponse());
		} catch (Exception Ex) {
			resp.putAll(Util.FailedResponse(Ex.getMessage()));
			Ex.printStackTrace();
		}
		resp.put("SiteAttendances", siteAttendances);
		return resp;

	}

}
