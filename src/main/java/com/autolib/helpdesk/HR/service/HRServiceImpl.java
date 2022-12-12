package com.autolib.helpdesk.HR.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autolib.helpdesk.HR.dao.HRDAO;

@Service
public class HRServiceImpl implements HRService{
	
	@Autowired
	HRDAO hrDAO;

	@Override
	public Map<String, Object> getHrDashboardDetails() 
	{		
		return hrDAO.getHrDashboardDetails();
	}

	@Override
	public Map<String, Object> getAttendanceCount() {
		
		return hrDAO.getAttendanceCount();
	}

}
