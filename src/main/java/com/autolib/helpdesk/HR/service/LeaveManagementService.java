package com.autolib.helpdesk.HR.service;

import java.util.Map;

import com.autolib.helpdesk.Agents.entity.Agent;
import com.autolib.helpdesk.HR.entity.Holiday;
import com.autolib.helpdesk.HR.entity.LeaveApplied;
import com.autolib.helpdesk.HR.entity.LeaveAppliedSearchRequest;
import com.autolib.helpdesk.HR.entity.LeaveMaster;


public interface LeaveManagementService {

	Map<String, Object> saveLeaveMaster(LeaveMaster leaveMaster);

	Map<String, Object> getLeaveMaster(LeaveMaster leaveMaster);

	Map<String, Object> deleteLeaveMaster(LeaveMaster leaveMaster);

	Map<String, Object> getAllLeaveMasters();

	Map<String, Object> saveLeaveApplied(LeaveApplied leaveApplied);

	Map<String, Object> getMyAllLeaveApplied(Agent agent);

	Map<String, Object> searchLeaveApplied(LeaveAppliedSearchRequest request);

	Map<String, Object> getAllLeaveBalances();

	Map<String, Object> saveHoliday(Holiday holiday);

	Map<String, Object> deleteHoliday(Holiday holiday);

	Map<String, Object> viewHolidays();

}
