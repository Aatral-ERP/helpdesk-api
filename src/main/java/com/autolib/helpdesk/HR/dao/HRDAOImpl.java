package com.autolib.helpdesk.HR.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.autolib.helpdesk.Attendance.repository.AttendanceRepository;
import com.autolib.helpdesk.common.Util;

@Repository
public class HRDAOImpl implements HRDAO{
	
	@Autowired
	AttendanceRepository attRepo;

	@Override
	public Map<String, Object> getHrDashboardDetails() {
		
		Map<String, Object> resp = new HashMap<>();
		try {
		      resp.put("Days", attRepo.findHrStats());
		      resp.putAll(Util.SuccessResponse());
		}
		catch(Exception ex)
		{
			resp.putAll(Util.FailedResponse(ex.getMessage()));
			ex.printStackTrace();
		}
		
		return resp;
	}

	@Override
	public Map<String, Object> getAttendanceCount() {
		
		Map<String,Object> resp= new HashMap<String,Object>();
		try
		{
			resp.put("Att", attRepo.findAttCountStats());
		      resp.putAll(Util.SuccessResponse());
		}
		catch(Exception ex)
		{
			resp.putAll(Util.FailedResponse(ex.getMessage()));
			ex.printStackTrace();
		}
		
		return resp;
	}

}
