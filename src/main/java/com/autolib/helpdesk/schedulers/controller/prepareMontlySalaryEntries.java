//package com.autolib.helpdesk.schedulers.controller;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//import javax.persistence.EntityManager;
//import javax.persistence.Query;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.autolib.helpdesk.Agents.entity.Agent;
//import com.autolib.helpdesk.Agents.repository.AgentRepository;
//import com.autolib.helpdesk.HR.entity.SalaryDetailProperty;
//import com.autolib.helpdesk.HR.entity.SalaryDetails;
//import com.autolib.helpdesk.HR.entity.SalaryEntries;
//import com.autolib.helpdesk.HR.entity.SalaryEntriesProperty;
//import com.autolib.helpdesk.HR.entity.SalaryEntriesRequest;
//import com.autolib.helpdesk.HR.repository.SalaryDetailPropertyRepository;
//import com.autolib.helpdesk.HR.repository.SalaryDetailsRepository;
//import com.autolib.helpdesk.HR.repository.SalaryEntriesRepository;
//import com.autolib.helpdesk.HR.service.SalaryService;
//import com.autolib.helpdesk.common.Util;
//
//@Controller
//@RestController
//@CrossOrigin("*")
//public class prepareMontlySalaryEntries {
//
//	private final Logger logger = LogManager.getLogger(this.getClass());
//
//	@Autowired
//	EntityManager em;
//
//	@Autowired
//	AgentRepository agentRepo;
//
//	@Autowired
//	SalaryDetailsRepository sdRepo;
//
//	@Autowired
//	SalaryService salaryService;
//
//	@Autowired
//	SalaryDetailPropertyRepository sdpService;
//
//	@Autowired
//	SalaryEntriesRepository seRepo;
//
//	@SuppressWarnings("unchecked")
//	@Scheduled(cron = "0 11 2 3 * *")
//	@GetMapping("prepareMontlySalaryEntries")
//	void execute() {
//
//		logger.info("prepareMontlySalaryEntries stars:::" + new Date());
//
//		Query query = em.createQuery("select a.employeeId from Agent a where a.workingStatus='working'", String.class);
//
//		List<String> agents = query.getResultList();
//
//		System.out.println(agents);
//
//		List<SalaryDetails> salaryDetails = sdRepo.findByEmployeeIdIn(agents);
//
//		Calendar calendar = Calendar.getInstance();
//		calendar.add(Calendar.MONTH, -2);
//
//		String month = new SimpleDateFormat("MMMM").format(calendar.getTime());
//		int year = Integer.parseInt(new SimpleDateFormat("YYYY").format(calendar.getTime()));
//
//		for (SalaryDetails sd : salaryDetails) {
//
//			Agent agent = agentRepo.findByEmployeeId(sd.getEmployeeId());
//
//			SalaryEntries se = seRepo.findByEmployeeIdAndSalaryMonthAndSalaryYear(sd.getEmployeeId(), month, year);
//
//			List<SalaryDetailProperty> sdps = sdpService.findByEmployeeId(sd.getEmployeeId());
//
//			SalaryEntries seTemp = new SalaryEntries();
//
//			if (se != null) {
//				seTemp.setId(se.getId());
//			}
//
//			seTemp.setEmployeeId(sd.getEmployeeId());
//			seTemp.setEmployeeName(agent.getFirstName() + " " + agent.getLastName());
//			seTemp.setDesignation(agent.getDesignation());
//			seTemp.setDoj(Util.sdfFormatter(agent.getDateOfJoining(), "dd/MM/YYYY"));
//
//			seTemp.setPfNumber(sd.getPfNumber());
//			seTemp.setPanNumber(sd.getPanNumber());
//			seTemp.setUanNumber(sd.getUanNumber());
//			seTemp.setEsicNumber(sd.getEsicNumber());
//
//			seTemp.setBankName(sd.getBankName());
//			seTemp.setAccountNumber(sd.getAccountNumber());
//
//			seTemp.setSalaryMonth(month);
//			seTemp.setSalaryYear(year);
//			seTemp.setModeOfPayment(sd.getModeOfPayment());
//			seTemp.setStatus("Generated");
//
//			List<SalaryEntriesProperty> seps = new ArrayList<>();
//			for (SalaryDetailProperty sdp : sdps) {
//				seps.add(new SalaryEntriesProperty(sdp));
//			}
//
//			// Calculating Casual Leave and LOP starts
//
//			List<Map<String, Object>> workingCounts = seRepo.getEmployeeWorkingAndLeaveDays(sd.getEmployeeId(), month,
//					year);
//
//			int nooFDaysLeave = workingCounts.stream()
//					.filter(count -> !String.valueOf(count.get("working_status")).equalsIgnoreCase("w"))
//					.mapToInt(count -> Integer.parseInt(count.get("cnt").toString())).sum();
//
//			System.out.println("noFoDaysLeave:::" + nooFDaysLeave);
//
//			seTemp.setNoOfDaysLeave(nooFDaysLeave);
//
//			int lopDays = nooFDaysLeave - sd.getCasualLeave();
//
//			if (lopDays > 0 && sd.getLopPerDay() > 0) {
//				SalaryEntriesProperty sep = new SalaryEntriesProperty();
//				sep.setEmployeeId(sd.getEmployeeId());
//				sep.setProperty("LOP");
//				sep.setPropertyType("deduction");
//				sep.setAmount(sd.getLopPerDay() * lopDays);
//				seps.add(sep);
//			}
//
//			// Calculating Casual Leave and LOP ends
//
//			Double totalEarnings = seps.stream().filter(_sep -> _sep.getPropertyType().equalsIgnoreCase("earning"))
//					.mapToDouble(SalaryEntriesProperty::getAmount).sum();
//
//			Double totalDeductions = seps.stream().filter(_sep -> _sep.getPropertyType().equalsIgnoreCase("deduction"))
//					.mapToDouble(SalaryEntriesProperty::getAmount).sum();
//
//			seTemp.setTotalEarnings(totalEarnings);
//			seTemp.setTotalDeductions(totalDeductions);
//			seTemp.setNetPay(totalEarnings - totalDeductions);
//
//			salaryService.generateSalarayEntry(new SalaryEntriesRequest(seTemp, seps));
//
//		}
//
//	}
//
//}
