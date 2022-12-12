package com.autolib.helpdesk.HR.dao;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.autolib.helpdesk.Agents.entity.Agent;
import com.autolib.helpdesk.Agents.entity.InfoDetails;
import com.autolib.helpdesk.Agents.repository.AgentRepository;
import com.autolib.helpdesk.Agents.repository.InfoDetailsRepository;
import com.autolib.helpdesk.HR.entity.SalaryDetailProperty;
import com.autolib.helpdesk.HR.entity.SalaryDetails;
import com.autolib.helpdesk.HR.entity.SalaryDetailsRequest;
import com.autolib.helpdesk.HR.entity.SalaryDetailsResponse;
import com.autolib.helpdesk.HR.entity.SalaryEntries;
import com.autolib.helpdesk.HR.entity.SalaryEntriesProperty;
import com.autolib.helpdesk.HR.entity.SalaryEntriesRequest;
import com.autolib.helpdesk.HR.repository.LeaveAppliedDatesRepository;
import com.autolib.helpdesk.HR.repository.LeaveAppliedRepository;
import com.autolib.helpdesk.HR.repository.LeaveBalanceRepository;
import com.autolib.helpdesk.HR.repository.LeaveMasterRepository;
import com.autolib.helpdesk.HR.repository.SalaryDetailPropertyRepository;
import com.autolib.helpdesk.HR.repository.SalaryDetailsRepository;
import com.autolib.helpdesk.HR.repository.SalaryEntriesPropertyRepository;
import com.autolib.helpdesk.HR.repository.SalaryEntriesRepository;
import com.autolib.helpdesk.common.EmailModel;
import com.autolib.helpdesk.common.EmailSender;
import com.autolib.helpdesk.common.Util;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Repository
@Transactional

public class SalaryDAOImpl implements SalaryDAO {

	@Autowired
	SalaryDetailsRepository salaryRepo;

	@Autowired
	SalaryDetailPropertyRepository salaryPropRepo;

	@Autowired
	AgentRepository agentRepo;

	@Autowired
	SalaryDetailsRepository sdRepo;

	@Autowired
	SalaryDetailPropertyRepository sdpService;

	@Autowired
	SalaryEntriesRepository seRepo;

	@Autowired
	SalaryEntriesPropertyRepository sepRepo;
	@Autowired
	InfoDetailsRepository infoDetailRepo;

	@Autowired
	LeaveAppliedRepository laRepo;

	@Autowired
	LeaveBalanceRepository leaveBalanceRepo;

	@Autowired
	LeaveAppliedDatesRepository leaveAppDatedRepo;

	@Autowired
	LeaveMasterRepository lmRepo;

	@Autowired
	EntityManager em;

	@Autowired
	EmailSender emailSender;

	@Value("${al.ticket.content-path}")
	private String contentPath;

	private final Logger logger = LogManager.getLogger(this.getClass());

	@Override
	public List<String> bankname() {
		logger.info("BankName DAOImpl starts::");
		List<String> bankname = new ArrayList<>();
		try {
			bankname = salaryRepo.findBankName();
		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
		}
		logger.info("BankName DAOImpl ends::");
		return bankname;
	}

	@Override
	public Map<String, Object> saveSalaryDetails(SalaryDetailsRequest salary) {

		logger.info("saveSalaryDetails DAOImpl starts:::::");
		Map<String, Object> resp = new HashMap<String, Object>();
		try {

			salary.setSalaryDetail(salaryRepo.save(salary.getSalaryDetail()));

			salaryPropRepo.deleteAllByEmployeeId(salary.getSalaryDetail().getEmployeeId());

			salary.setSalaryDetailProperty(salaryPropRepo.saveAll(salary.getSalaryDetailProperty()));

			resp.putAll(Util.SuccessResponse());
		} catch (Exception ex) {
			ex.printStackTrace();
			resp.putAll(Util.FailedResponse(ex.getMessage()));
		}
		resp.put("SalaryDetail", salary.getSalaryDetail());
		resp.put("SalaryDetailProperties", salary.getSalaryDetailProperty());
		return resp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getStaffDetails() {

		logger.info("Get StaffDetails DAOImpl Starts:::::::");
		Map<String, Object> resp = new HashMap<>();
		List<SalaryDetailsResponse> salaryDetails = new ArrayList<>();
		try {

			Query query = em.createQuery(
					"select new com.autolib.helpdesk.HR.entity.SalaryDetailsResponse(sd)"
							+ " from SalaryDetails sd left join Agent a on (sd.employeeId = a.employeeId) ",
					SalaryDetailsResponse.class);

			salaryDetails = query.getResultList();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		resp.put("salaryDetails", salaryDetails);
		logger.info("Get StaffDetails DAOImpl Ends:::::::");
		return resp;
	}

	@Override
	public Map<String, Object> getStaffDetailsEdit(String sid) {
		logger.info("Get StaffDetails DAOImpl Starts:::::::" + sid);
		Map<String, Object> resp = new HashMap<>();
		SalaryDetails sd = new SalaryDetails();
		List<SalaryDetailProperty> sdps = new ArrayList<>();
		try {

			sd = salaryRepo.findByEmployeeId(sid);

			sdps = salaryPropRepo.findByEmployeeId(sd.getEmployeeId());

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("Salarydetails", sd);
		resp.put("SalaryDetailProperties", sdps);

		return resp;
	}

	@Override
	public Map<String, Object> deleteStaffDetails(SalaryDetails salaryDetail) {

		logger.info("deleteStaffDetails DAOImpl starts:::::");
		Map<String, Object> resp = new HashMap<String, Object>();
		try {

			salaryRepo.delete(salaryDetail);

			salaryPropRepo.deleteAllByEmployeeId(salaryDetail.getEmployeeId());

			resp.putAll(Util.SuccessResponse());
		} catch (Exception ex) {
			ex.printStackTrace();
			resp.putAll(Util.FailedResponse(ex.getMessage()));
		}
		return resp;
	}

	@Override
	public Map<String, Object> generateSalarayEntry(SalaryEntriesRequest req) {

		logger.info("saveSalaryDetails DAOImpl starts:::::");
		Map<String, Object> resp = new HashMap<String, Object>();
		try {

			req.setSalaryEntry(seRepo.save(req.getSalaryEntry()));

			sepRepo.deleteAllBySalaryEntryId(req.getSalaryEntry().getId());

			req.getSalaryEntriesProperties().stream()
					.forEach(sep -> sep.setSalaryEntryId(req.getSalaryEntry().getId()));

			req.setSalaryEntriesProperties(sepRepo.saveAll(req.getSalaryEntriesProperties()));

			resp.putAll(Util.SuccessResponse());
		} catch (Exception ex) {
			ex.printStackTrace();
			resp.putAll(Util.FailedResponse(ex.getMessage()));
		}
		resp.put("SalaryEntries", req.getSalaryEntry());
		resp.put("SalaryEntriesProperties", req.getSalaryEntriesProperties());
		return resp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getSalaryEntries(SalaryEntriesRequest request) {

		logger.info("getSalaryEntries DAOImpl starts:::::");
		Map<String, Object> resp = new HashMap<String, Object>();
		List<SalaryEntries> entries = new ArrayList<>();
		List<SalaryEntriesProperty> properties = new ArrayList<>();
		List<Map<String, Object>> rowDatasEarnings = new ArrayList<>();
		List<Map<String, Object>> rowDatasDeductions = new ArrayList<>();

		try {

			String filterQuery = "";

			if (request.getAgents() != null && request.getAgents().size() > 0) {
				String agents = "'0'";
				for (Agent agnt : request.getAgents()) {
					agents = agents + ",'" + agnt.getEmployeeId() + "'";
				}
				filterQuery = filterQuery + " and se.employeeId in (" + agents + ") ";
			}

			if (request.getSalaryMonth() != null && !request.getSalaryMonth().isEmpty()) {
				filterQuery = filterQuery + " and se.salaryMonth = '" + request.getSalaryMonth() + "'";
			}

			if (request.getSalaryYear() != null && !request.getSalaryYear().isEmpty()) {
				filterQuery = filterQuery + " and se.salaryYear = '" + request.getSalaryYear() + "'";
			}

			if (request.getStatus() != null && !request.getStatus().isEmpty()) {
				filterQuery = filterQuery + " and se.status = '" + request.getStatus() + "'";
			}

			Query query = em.createQuery("select se from SalaryEntries se where 2 > 1 " + filterQuery,
					SalaryEntries.class);

			entries = query.getResultList();

			List<Integer> ids = entries.stream().map(se -> se.getId()).collect(Collectors.toList());

			properties = sepRepo.findAllBySalaryEntryIdIn(ids);

			rowDatasEarnings = pivotTableEarningRowDatas(entries, properties);

			rowDatasDeductions = pivotTableDeductionRowDatas(entries, properties);

			resp.putAll(Util.SuccessResponse());
		} catch (Exception ex) {
			ex.printStackTrace();
			resp.putAll(Util.FailedResponse(ex.getMessage()));
		}
		resp.put("SalaryEntries", entries);
		resp.put("SalaryEntriesProperties", properties);
		resp.put("rowDatasEarnings", rowDatasEarnings);
		resp.put("rowDatasDeductions", rowDatasDeductions);

		return resp;
	}

	List<Map<String, Object>> pivotTableEarningRowDatas(List<SalaryEntries> ses, List<SalaryEntriesProperty> seps) {
		List<Map<String, Object>> rowDatas = new ArrayList<>();
		try {
			Set<String> _e_props = seps.parallelStream().filter(se -> se.getPropertyType().equalsIgnoreCase("earning"))
					.map(se -> se.getProperty()).collect(Collectors.toSet());
//			Set<String> _d_props = seps.parallelStream()
//					.filter(se -> se.getPropertyType().equalsIgnoreCase("deduction")).map(se -> se.getProperty())
//					.collect(Collectors.toSet());

			System.out.println(_e_props);
//			System.out.println(_d_props);

			rowDatas = ses.parallelStream().map(se -> {
				Map<String, Object> rowData = new HashMap<>();
				rowData.put("salaryEntryId", se.getId());

				_e_props.stream().forEach(props -> {
					rowData.put(props, 0);
				});

//				_d_props.stream().forEach(props -> {
//					rowData.put(props, 0);
//				});

				System.out.println(rowData);
				seps.stream().filter(sep -> sep.getSalaryEntryId() == se.getId()
						&& sep.getPropertyType().equalsIgnoreCase("earning")).forEach(sep -> {
							rowData.put(sep.getProperty(), sep.getAmount());
						});
				System.out.println(rowData);

				return rowData;
			}).collect(Collectors.toList());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return rowDatas;
	}

	List<Map<String, Object>> pivotTableDeductionRowDatas(List<SalaryEntries> ses, List<SalaryEntriesProperty> seps) {
		List<Map<String, Object>> rowDatas = new ArrayList<>();
		try {
//			Set<String> _e_props = seps.parallelStream().filter(se -> se.getPropertyType().equalsIgnoreCase("earning"))
//					.map(se -> se.getProperty()).collect(Collectors.toSet());
			Set<String> _d_props = seps.parallelStream()
					.filter(se -> se.getPropertyType().equalsIgnoreCase("deduction")).map(se -> se.getProperty())
					.collect(Collectors.toSet());

//			System.out.println(_e_props);
			System.out.println(_d_props);

			rowDatas = ses.parallelStream().map(se -> {
				Map<String, Object> rowData = new HashMap<>();
				rowData.put("salaryEntryId", se.getId());

//				_e_props.stream().forEach(props -> {
//					rowData.put(props, 0);
//				});

				_d_props.stream().forEach(props -> {
					rowData.put(props, 0);
				});

				System.out.println(rowData);
				seps.stream().filter(sep -> sep.getSalaryEntryId() == se.getId()
						&& sep.getPropertyType().equalsIgnoreCase("deduction")).forEach(sep -> {
							rowData.put(sep.getProperty(), sep.getAmount());
						});
				System.out.println(rowData);

				return rowData;
			}).collect(Collectors.toList());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return rowDatas;
	}

	@Override
	public Map<String, Object> generateSalaryEntries(SalaryDetailsRequest request) {

		Map<String, Object> resp = new HashMap<String, Object>();
		List<SalaryEntries> entries = new ArrayList<>();
		try {

			List<String> agents = request.getSalaryDetails().stream().map(SalaryDetails::getEmployeeId)
					.collect(Collectors.toList());

			System.out.println(agents);

			List<SalaryDetails> salaryDetails = sdRepo.findByEmployeeIdIn(agents);

			for (SalaryDetails sd : salaryDetails) {

				Agent agent = agentRepo.findByEmployeeId(sd.getEmployeeId());

				SalaryEntries se = seRepo.findByEmployeeIdAndSalaryMonthAndSalaryYear(sd.getEmployeeId(),
						request.getSalaryMonth(), request.getSalaryYear());

				List<SalaryDetailProperty> sdps = sdpService.findByEmployeeId(sd.getEmployeeId());

				SalaryEntries seTemp = new SalaryEntries();

				if (se != null) {
					seTemp.setId(se.getId());
				}

				seTemp.setEmployeeId(sd.getEmployeeId());
				seTemp.setEmployeeName(agent.getFirstName() + " " + agent.getLastName());
				seTemp.setDesignation(agent.getDesignation());
				seTemp.setDoj(Util.sdfFormatter(agent.getDateOfJoining(), "dd/MM/YYYY"));

				seTemp.setPfNumber(sd.getPfNumber());
				seTemp.setPanNumber(sd.getPanNumber());
				seTemp.setUanNumber(sd.getUanNumber());
				seTemp.setEsicNumber(sd.getEsicNumber());

				seTemp.setBankName(sd.getBankName());
				seTemp.setAccountNumber(sd.getAccountNumber());

				seTemp.setSalaryMonth(request.getSalaryMonth());
				seTemp.setSalaryYear(request.getSalaryYear());
				seTemp.setModeOfPayment(sd.getModeOfPayment());

				seTemp.setSalaryCreditedDate(request.getSalaryCreditedDate());

				if (request.getIsSalaryCredited())
					seTemp.setStatus("Credited");
				else
					seTemp.setStatus("Generated");

				List<SalaryEntriesProperty> seps = new ArrayList<>();
				for (SalaryDetailProperty sdp : sdps) {
					seps.add(new SalaryEntriesProperty(sdp));
				}

				// Calculating Casual Leave and LOP starts

				List<Map<String, Object>> workingCounts = seRepo.getEmployeeWorkingAndLeaveDays(sd.getEmployeeId(),
						request.getSalaryMonth(), request.getSalaryYear());

				int noOFDaysLeave = workingCounts.stream()
						.filter(count -> !String.valueOf(count.get("working_status")).equalsIgnoreCase("w"))
						.mapToInt(count -> Integer.parseInt(count.get("cnt").toString())).sum();

				int noOfWorkingDays = workingCounts.stream()
						.mapToInt(count -> Integer.parseInt(count.get("cnt").toString())).sum();

				System.out.println("noFoDaysLeave:::" + noOFDaysLeave);

				seTemp.setNoOfDaysLeave(noOFDaysLeave);
				seTemp.setNoOfWorkingDays(noOfWorkingDays);

//				int lopDays = noOFDaysLeave - sd.getCasualLeave();

				double lop = leaveAppDatedRepo.getLOPForMonthYear(request.getSalaryMonth(), request.getSalaryYear(),
						agent.getEmployeeId(), agent.getEmailId());

//				double lop = leaveAppDatedRepo.getLOPForMonthYear(request.getSalaryMonth(), request.getSalaryYear());

				if (lop > 0.00) {
					SalaryEntriesProperty sep = new SalaryEntriesProperty();
					sep.setEmployeeId(sd.getEmployeeId());
					sep.setProperty("LOP");
					sep.setPropertyType("deduction");
					sep.setAmount(lop);
					seps.add(sep);
				}

				// Calculating Casual Leave and LOP ends

				Double totalEarnings = seps.stream().filter(_sep -> _sep.getPropertyType().equalsIgnoreCase("earning"))
						.mapToDouble(SalaryEntriesProperty::getAmount).sum();

				Double totalDeductions = seps.stream()
						.filter(_sep -> _sep.getPropertyType().equalsIgnoreCase("deduction"))
						.mapToDouble(SalaryEntriesProperty::getAmount).sum();

				seTemp.setTotalEarnings(totalEarnings);
				seTemp.setTotalDeductions(totalDeductions);
				seTemp.setNetPay(totalEarnings - totalDeductions);

				Map<String, Object> generateSalarayEntryResp = generateSalarayEntry(
						new SalaryEntriesRequest(seTemp, seps));

				if (generateSalarayEntryResp.get("StatusCode").toString().equalsIgnoreCase("00")) {
					entries.add((SalaryEntries) generateSalarayEntryResp.get("SalaryEntries"));
				}
			}
			resp.putAll(Util.SuccessResponse());
		} catch (Exception ex) {
			ex.printStackTrace();
			resp.putAll(Util.FailedResponse(ex.getMessage()));
		}
		resp.put("SalaryEntries", entries);
		resp.put("GeneratedSalaryEntriesCount", entries.size());
		return resp;
	}

//	int getAgentLOPDays(Agent agent, String month, int year) {
//		System.out.println("Started getAgentLOPDays:" + agent.getEmailId());
//		int lop_days = 0;
//
//		try {
//
//			List<LeaveApplied> _leaves_applied = laRepo.findByAgentEmailIdAndStatus(agent.getEmailId(), "Approved");
//
//			System.out.println("_leaves_applied size ::" + _leaves_applied.size());
//			if (_leaves_applied.isEmpty()) {
//				lop_days = 0;
//			} else {
//				LeaveBalance balance = leaveBalanceRepo.findByEmailId(agent.getEmailId());
//
//				Date date = new SimpleDateFormat("MMMM").parse(month);
//				Calendar cal = Calendar.getInstance();
//				cal.setTime(date);
//				cal.set(year, cal.get(Calendar.MONTH), 1);
//				int _month_number = cal.get(Calendar.MONTH);
//
//				double annualLeaveLOPDays = 0.00;
//				double casualLeaveLOPDays = 0.00;
//				double sickLeaveLOPDays = 0.00;
//				double otherLeaveLOPDays = 0.00;
//
//				if (balance.getAnnualLeave() < 0) {
//
//					_leaves_applied.stream().filter(_la -> !_la.getLeaveType().equalsIgnoreCase("Permission")
//							&& !_la.getLeaveType().equalsIgnoreCase("Half Day")).forEach(_la -> {
//								Set<Date> _annual_dates = new HashSet<>();
//
//								_annual_dates.add(_la.getLeaveFrom());
//								_annual_dates.add(_la.getLeaveTo());
//
//								Calendar _cal = Calendar.getInstance();
//								_cal.setTime(_la.getLeaveFrom());
//								while (_cal.getTime().before(_la.getLeaveTo())) {
//									_cal.add(Calendar.DATE, 1);
//									_annual_dates.add(_cal.getTime());
//								}
//								System.out.println(_annual_dates);
//								System.out.println(_annual_dates.size());
//
//								List<Date> _salary_month_dates = _annual_dates.stream().filter(_sla -> {
//									Calendar _s_cal = Calendar.getInstance();
//									_s_cal.setTime(_sla);
//
//									return _s_cal.get(Calendar.MONTH) == _month_number
//											&& _cal.get(Calendar.YEAR) == year;
//								}).collect(Collectors.toList());
//
////								annualLeaveLOPDays = annualLeaveLOPDays + _salary_month_dates.size();
//
//							});
//				}
//
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		System.out.println("Ends getAgentLOPDays:" + lop_days);
//		return lop_days;
//
//	}

//	double calculateAnnualLeaveLOPDays(List<LeaveApplied> _leaves_applied, int _month_number, int _year) {
//		double annualLeaveLOPDays = 0.00;
//
//		// Calculating Full Day Leaves
//		annualLeaveLOPDays = annualLeaveLOPDays + _leaves_applied.stream()
//				.filter(_la -> !_la.getLeaveType().equalsIgnoreCase("Permission")
//						&& _la.getLeaveType().equalsIgnoreCase("Annual Leave")
//						&& !_la.getLeaveType().equalsIgnoreCase("Half Day"))
//				.mapToDouble(_la -> {
//
//					double _annualLeaveLOPDays_1 = 0.00;
//
//					Set<Date> _annual_dates = new HashSet<>();
//
//					_annual_dates.add(_la.getLeaveFrom());
//					_annual_dates.add(_la.getLeaveTo());
//
//					Calendar _cal = Calendar.getInstance();
//					_cal.setTime(_la.getLeaveFrom());
//					while (_cal.getTime().before(_la.getLeaveTo())) {
//						_cal.add(Calendar.DATE, 1);
//						_annual_dates.add(_cal.getTime());
//					}
//					System.out.println(_annual_dates);
//					System.out.println(_annual_dates.size());
//
//					List<Date> _salary_month_dates = _annual_dates.stream().filter(_sla -> {
//						Calendar _s_cal = Calendar.getInstance();
//						_s_cal.setTime(_sla);
//
//						return _s_cal.get(Calendar.MONTH) == _month_number && _cal.get(Calendar.YEAR) == _year;
//					}).collect(Collectors.toList());
//
//					_annualLeaveLOPDays_1 = _salary_month_dates.size();
//
//					return _annualLeaveLOPDays_1;
//				}).sum();
//
//		return annualLeaveLOPDays;
//
//	}

//	int getAgentLOPDays(Agent agent, String month, int year) {
//		System.out.println("Started getAgentLOPDays:" + agent.getEmailId());
//		int lop_days = 0;
//
//		try {
//
//			List<LeaveApplied> _leaves_applied = laRepo.findByAgentEmailIdAndStatus(agent.getEmailId(), "Approved");
//
//			System.out.println("_leaves_applied size ::" + _leaves_applied.size());
//			if (_leaves_applied.isEmpty()) {
//				lop_days = 0;
//			} else {
//				LeaveMaster lm = lmRepo.findByAgentEmailId(agent.getEmailId());
//
//				int totalEligibleLeaves = lm.getAnnualLeave() + lm.getSickLeave() + lm.getCasualLeave()
//						+ lm.getOtherLeave();
//
//				Set<Date> dates = new HashSet<>();
//
//				_leaves_applied.stream().filter(_la -> !_la.getLeaveType().equalsIgnoreCase("Permission"))
//						.forEach(_la -> {
//
//							System.out.println("::::::::::");
//							System.out.println(_la.getLeaveFrom());
//							System.out.println(_la.getLeaveTo());
//
//							dates.add(_la.getLeaveFrom());
//							dates.add(_la.getLeaveTo());
//
//							Calendar cal = Calendar.getInstance();
//							cal.setTime(_la.getLeaveFrom());
//							while (cal.getTime().before(_la.getLeaveTo())) {
//								cal.add(Calendar.DATE, 1);
//								dates.add(cal.getTime());
//							}
//						});
//				System.out.println(dates);
//				System.out.println(dates.size());
//
//				Date date = new SimpleDateFormat("MMMM").parse(month);
//				Calendar cal = Calendar.getInstance();
//				cal.setTime(date);
//				cal.set(year, cal.get(Calendar.MONTH), 1);
//				int _month_number = cal.get(Calendar.MONTH);
//
//				List<Date> _b4_salary_month_dates = dates.stream().filter(_la -> _la.before(cal.getTime()))
//						.collect(Collectors.toList());
//
//				List<Date> _salary_month_dates = dates.stream().filter(_la -> {
//					Calendar _cal = Calendar.getInstance();
//					_cal.setTime(_la);
//
//					return _cal.get(Calendar.MONTH) == _month_number && _cal.get(Calendar.YEAR) == year;
//				}).collect(Collectors.toList());
//
//				System.out.println("_month_year_date:::" + cal.getTime());
//				System.out.println(_b4_salary_month_dates);
//				System.out.println(_salary_month_dates);
//
//				System.out.println(_b4_salary_month_dates.size());
//				System.out.println(_salary_month_dates.size());
//
//				System.out.println("totalEligibleLeaves::" + totalEligibleLeaves);
//				System.out.println("_b4_salary_month_leaves::" + _b4_salary_month_dates.size());
//
//				if (totalEligibleLeaves >= _b4_salary_month_dates.size() + _salary_month_dates.size()) {
//					lop_days = 0;
//				} else {
//					int _salary_month_month_eligible = totalEligibleLeaves - _b4_salary_month_dates.size();
//
//					System.out.println("_salary_month_month_eligible::" + _salary_month_month_eligible);
//
//					if (_salary_month_month_eligible >= _salary_month_dates.size()) {
//						lop_days = 0;
//					} else if (_salary_month_month_eligible < 1) {
//						lop_days = _salary_month_dates.size();
//					} else {
//						lop_days = _salary_month_month_eligible - _salary_month_dates.size();
//					}
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		System.out.println("Ends getAgentLOPDays:" + lop_days);
//		return lop_days;
//
//	}

	@Override
	public Map<String, Object> getStaffEntriesEdit(String sid) {
		logger.info("Get StaffDetails DAOImpl Starts:::::::" + sid);
		Map<String, Object> resp = new HashMap<>();
		SalaryEntries se = new SalaryEntries();
		List<SalaryEntriesProperty> seps = new ArrayList<>();
		try {

			se = seRepo.findById(Integer.parseInt(sid));

			seps = sepRepo.findBySalaryEntryId(se.getId());

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			resp.putAll(Util.FailedResponse(e.getMessage()));
			e.printStackTrace();
		}
		resp.put("SalaryEntries", se);
		resp.put("SalaryEntriesProperties", seps);

		return resp;
	}

	@Override
	public Map<String, Object> deleteStaffEntries(SalaryEntries salaryEntry) {
		Map<String, Object> resp = new HashMap<String, Object>();
		try {

			seRepo.delete(salaryEntry);

			sepRepo.deleteAllBySalaryEntryId(salaryEntry.getId());

			resp.putAll(Util.SuccessResponse());
		} catch (Exception ex) {
			ex.printStackTrace();
			resp.putAll(Util.FailedResponse(ex.getMessage()));
		}
		return resp;
	}

	@Override
	public Map<String, Object> updateSalaryEntries(SalaryEntriesRequest request) {
		Map<String, Object> resp = new HashMap<String, Object>();
		try {

			seRepo.saveAll(request.getSalaryEntries());

			resp.putAll(Util.SuccessResponse());
		} catch (Exception ex) {
			ex.printStackTrace();
			resp.putAll(Util.FailedResponse(ex.getMessage()));
		}
		return resp;
	}

	@Override
	public Map<String, Object> generateSalaryEntryPayslip(SalaryEntries se) {
		Map<String, Object> resp = new HashMap<String, Object>();
		List<SalaryEntriesProperty> seps = new ArrayList<>();
		try {

			se = seRepo.findById(se.getId());

			seps = sepRepo.findBySalaryEntryId(se.getId());

			final Map<String, Object> parameters = new HashMap<>();
			InfoDetails info = infoDetailRepo.findById(1);

			System.out.println(info.toString());

			parameters.put("cmp_name", info.getCmpName());
			parameters.put("cmp_address", info.getCompanyAddress());
			parameters.put("logo", info.getLogoAsFile());

			parameters.put("month_year_header",
					"Payslip for " + se.getSalaryMonth().toUpperCase() + " " + se.getSalaryYear());

			parameters.put("employee_id", se.getEmployeeId());
			parameters.put("employee_name", se.getEmployeeName());
			parameters.put("designation", se.getDesignation());
			parameters.put("doj", se.getDoj());

			parameters.put("bank_name", se.getBankName());
			parameters.put("bank_acc_no", se.getAccountNumber());
			parameters.put("pf_number", se.getPfNumber());
			parameters.put("pan_number", se.getPanNumber());
			parameters.put("uan_number", se.getUanNumber());
			parameters.put("esic_number", se.getEsicNumber());

			parameters.put("working_days", String.valueOf(se.getNoOfWorkingDays()));
			parameters.put("leave_days", String.valueOf(se.getNoOfDaysLeave()));

			parameters.put("gross_earning", "Rs." + Util.decimalFormatter(se.getTotalEarnings()));
			parameters.put("gross_deduction", "Rs." + Util.decimalFormatter(se.getTotalDeductions()));
			parameters.put("net_pay", "Rs." + Util.decimalFormatter(se.getNetPay()));
			parameters.put("amount_in_words", Util.EnglishNumberToWords(se.getNetPay()));

			List<SalaryEntriesProperty> earningdatasource = seps.stream()
					.filter(sep -> sep.getPropertyType().equalsIgnoreCase("earning")).collect(Collectors.toList());
			List<SalaryEntriesProperty> deductionsource = seps.stream()
					.filter(sep -> sep.getPropertyType().equalsIgnoreCase("deduction")).collect(Collectors.toList());

			List<Map<String, String>> datasource = new ArrayList<>();

			int rows = earningdatasource.size() > deductionsource.size() ? earningdatasource.size()
					: deductionsource.size();
			// 1 4 2
			for (int i = 0; i < rows; i++) {
				Map<String, String> data = new HashMap<>();
				System.out.println(i + " " + earningdatasource.size() + " " + deductionsource.size() + " "
						+ String.valueOf(earningdatasource.size() > i) + " "
						+ String.valueOf(deductionsource.size() > i));

				if (earningdatasource.size() > i) {
					SalaryEntriesProperty _se = earningdatasource.get(i);
					data.put("earning_property", _se.getProperty());
					data.put("earning_amount", Util.decimalFormatter(_se.getAmount()));
				} else {
					data.put("earning_property", "");
					data.put("earning_amount", "");
				}

				if (deductionsource.size() > i) {
					SalaryEntriesProperty _se = deductionsource.get(i);
					data.put("deduction_property", _se.getProperty());
					data.put("deduction_amount", Util.decimalFormatter(_se.getAmount()));
				} else {
					data.put("deduction_property", "");
					data.put("deduction_amount", "");
				}
				datasource.add(data);
			}

			System.out.println(datasource);
			System.out.println(parameters);

			InputStream stream = this.getClass().getResourceAsStream("/reports/Payslip.jrxml");

			final JasperReport report = JasperCompileManager.compileReport(stream);
			final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(datasource);
//
			final JasperPrint print = JasperFillManager.fillReport(report, parameters, source);

			File directory = new File(contentPath + "/Payslips/" + se.getEmployeeId());
			System.out.println(directory.getAbsolutePath());
			if (!directory.exists()) {
				System.out.println("Directory created ::" + directory.getAbsolutePath());
				directory.mkdirs();
			}

			String filename = "Payslip_" + se.getSalaryMonth().toUpperCase() + "_" + se.getSalaryYear() + "_"
					+ se.getEmployeeId() + "_" + String.valueOf(Util.generateRandomPassword()) + ".pdf";

			se.setFilename(filename);
			final String filePath = directory.getAbsolutePath() + "/" + se.getFilename();
			System.out.println(filePath);

			seRepo.save(se);

			// Export the report to a PDF file.
			JasperExportManager.exportReportToPdfFile(print, filePath);

			resp.putAll(Util.SuccessResponse());

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		resp.put("SalaryEntry", se);
		return resp;
	}

	@Override
	public Map<String, Object> generatePayslips(SalaryEntriesRequest request) {
		Map<String, Object> resp = new HashMap<String, Object>();

		int successCounts = 0;
		int failedcounts = 0;

		try {

			for (SalaryEntries se : request.getSalaryEntries()) {
				Map<String, Object> _res = generateSalaryEntryPayslip(se);
				if (_res.get("StatusCode").toString().equals("00"))
					successCounts++;
				else
					failedcounts++;
			}

			resp.putAll(Util.SuccessResponse());
		} catch (Exception ex) {
			ex.printStackTrace();
			resp.putAll(Util.FailedResponse(ex.getMessage()));
		}
		resp.put("SuccessCounts", successCounts);
		resp.put("FailedCounts", failedcounts);
		return resp;
	}

	@Override
	public Map<String, Object> sendPayslipsMail(SalaryEntriesRequest request) {
		Map<String, Object> resp = new HashMap<>();
		try {
			List<Integer> ids = request.getSalaryEntries().stream().map(se -> se.getId()).collect(Collectors.toList());

			request.setSalaryEntries(seRepo.findAllById(ids));

			request.getSalaryEntries().stream().parallel()
					.filter(se -> se.getFilename() != null && !se.getFilename().isEmpty()).forEach(se -> {
						se = seRepo.findById(se.getId());

						Agent agent = agentRepo.findMinDetailsByEmployeeId(se.getEmployeeId());

						EmailModel emailModel = new EmailModel("Common");

						emailModel.setMailTo(agent.getEmailId());
						emailModel.setOtp(String.valueOf(Util.generateOTP()));
						emailModel.setMailSub("Payslip for " + se.getSalaryMonth() + " - " + se.getSalaryYear());

						emailModel.setMailText("Hi " + agent.getFirstName() + "<br><br>Find the attached payslip.");

						File directory = new File(
								contentPath + "/Payslips/" + se.getEmployeeId() + "/" + se.getFilename());
						System.out.println(directory.getAbsolutePath());

						emailModel.setContent_path(directory.getAbsolutePath());

						emailSender.sendmail(emailModel);
					});

			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			e.printStackTrace();
			resp.putAll(Util.FailedResponse(e.getMessage()));
		}
		return resp;
	}

}
