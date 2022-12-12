package com.autolib.helpdesk.HR.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autolib.helpdesk.Agents.entity.Agent;
import com.autolib.helpdesk.HR.dao.LeaveManagementDAO;
import com.autolib.helpdesk.HR.entity.Holiday;
import com.autolib.helpdesk.HR.entity.LeaveApplied;
import com.autolib.helpdesk.HR.entity.LeaveAppliedSearchRequest;
import com.autolib.helpdesk.HR.entity.LeaveMaster;


@Service
public class LeaveManagementServiceImpl implements LeaveManagementService {

	@Autowired
	private LeaveManagementDAO lmDAO;

	@Override
	public Map<String, Object> saveLeaveMaster(LeaveMaster leaveMaster) {
		return lmDAO.saveLeaveMaster(leaveMaster);
	}

	@Override
	public Map<String, Object> getLeaveMaster(LeaveMaster leaveMaster) {
		return lmDAO.getLeaveMaster(leaveMaster);
	}

	@Override
	public Map<String, Object> deleteLeaveMaster(LeaveMaster leaveMaster) {
		return lmDAO.deleteLeaveMaster(leaveMaster);
	}

	@Override
	public Map<String, Object> getAllLeaveMasters() {
		return lmDAO.getAllLeaveMasters();
	}

	@Override
	public Map<String, Object> saveLeaveApplied(LeaveApplied leaveApplied) {
		return lmDAO.saveLeaveApplied(leaveApplied);
	}

	@Override
	public Map<String, Object> getMyAllLeaveApplied(Agent agent) {
		return lmDAO.getMyAllLeaveApplied(agent);
	}

	@Override
	public Map<String, Object> searchLeaveApplied(LeaveAppliedSearchRequest request) {
		return lmDAO.searchLeaveApplied(request);
	}

	@Override
	public Map<String, Object> getAllLeaveBalances() {
		return lmDAO.getAllLeaveBalances();
	}

	@Override
	public Map<String, Object> saveHoliday(Holiday holiday) {
		return lmDAO.saveHoliday(holiday);
	}

	@Override
	public Map<String, Object> deleteHoliday(Holiday holiday) {
		return lmDAO.deleteHoliday(holiday);
	}

	@Override
	public Map<String, Object> viewHolidays() {
		return lmDAO.viewHolidays();
	}

}
