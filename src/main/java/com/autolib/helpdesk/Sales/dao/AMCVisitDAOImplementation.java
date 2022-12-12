package com.autolib.helpdesk.Sales.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.autolib.helpdesk.Sales.model.AMCVisit.AMCVisit;
import com.autolib.helpdesk.Sales.model.AMCVisit.AMCVisitRequest;
import com.autolib.helpdesk.Sales.repository.AMCVisitRepository;
import com.autolib.helpdesk.common.Util;

@Repository
public class AMCVisitDAOImplementation implements AMCVisitDAO {

	@Autowired
	AMCVisitRepository amcVisitRepo;

	@Override
	public Map<String, Object> saveAMCVisit(AMCVisitRequest request) {
		Map<String, Object> resp = new HashMap<>();
		try {
			request.setAmcVisits(amcVisitRepo.saveAll(request.getAmcVisits()));
			
			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			e.printStackTrace();
			resp.putAll(Util.FailedResponse());
		}

		resp.put("AMCVisits", request.getAmcVisits());
		return resp;
	}

	@Override
	public Map<String, Object> deleteAMCVisit(AMCVisitRequest request) {
		Map<String, Object> resp = new HashMap<>();
		try {
			amcVisitRepo.deleteAll(request.getAmcVisits());
			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			e.printStackTrace();
			resp.putAll(Util.FailedResponse());
		}
		return resp;
	}

	@Override
	public Map<String, Object> getAMCVisits(int dealId) {
		Map<String, Object> resp = new HashMap<>();
		List<AMCVisit> amcVisits = new ArrayList<>();
		try {
			amcVisits = amcVisitRepo.findByDealId(dealId);
			resp.putAll(Util.SuccessResponse());
		} catch (Exception e) {
			e.printStackTrace();
			resp.putAll(Util.FailedResponse());
		}
		resp.put("AMCVisits", amcVisits);
		return resp;
	}

}
