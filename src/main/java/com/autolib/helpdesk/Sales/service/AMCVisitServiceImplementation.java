package com.autolib.helpdesk.Sales.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autolib.helpdesk.Sales.dao.AMCVisitDAO;
import com.autolib.helpdesk.Sales.model.AMCVisit.AMCVisitRequest;

@Service
public class AMCVisitServiceImplementation implements AMCVisitService {

	@Autowired
	AMCVisitDAO amcVisitDAO;

	@Override
	public Map<String, Object> saveAMCVisit(AMCVisitRequest request) {
		return amcVisitDAO.saveAMCVisit(request);
	}

	@Override
	public Map<String, Object> deleteAMCVisit(AMCVisitRequest request) {
		return amcVisitDAO.deleteAMCVisit(request);
	}
	
	@Override
	public Map<String, Object> getAMCVisits(int dealId) {
		return amcVisitDAO.getAMCVisits(dealId);
	}
}
