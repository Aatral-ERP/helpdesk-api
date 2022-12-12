package com.autolib.helpdesk.schedulers.controller;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autolib.helpdesk.Agents.entity.Agent;
import com.autolib.helpdesk.Agents.repository.AgentRepository;
import com.autolib.helpdesk.Attendance.model.Attendance;
import com.autolib.helpdesk.Attendance.model.WorkingDay;
import com.autolib.helpdesk.Attendance.repository.AttendanceRepository;
import com.autolib.helpdesk.Attendance.repository.WorkingDayRepository;
import com.autolib.helpdesk.common.EnumUtils.WorkingStatus;

@RequestMapping("scheduler")
@Controller
@RestController
@CrossOrigin("*")
public class PrepareAttendanceScheduler {

	private final Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	WorkingDayRepository wdRepo;

	@Autowired
	AttendanceRepository attRepo;

	@Autowired
	AgentRepository agentRepo;

	@GetMapping("PrepareAttendanceScheduler")
	@Scheduled(cron = "0 0/30 * * * *")
	void execute() {
		logger.info("PrepareAttendanceScheduler stars:::");
//		LocalDate fromDate = LocalDate.now().minusDays(15);
//		LocalDate toDate = LocalDate.now().plusDays(15);
//		System.out.println(fromDate);
//		System.out.println(toDate);

//		wdRepo.findByWorkingDateBetween(fromDate, toDate);
		WorkingDay wd = wdRepo.findByWorkingDate(new Date());
		if (wd != null) {
			List<Attendance> attendances = attRepo.findByWorkingDate(wd.getWorkingDate());

			Iterable<Agent> agents = agentRepo.findAll();

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
					logger.info("attendance default added::" + attendance.getEmployeeId());
				}
			}
		}

	}

}
