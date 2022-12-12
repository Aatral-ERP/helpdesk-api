package com.autolib.helpdesk.Sales.dao;

import java.util.Map;

import com.autolib.helpdesk.Sales.model.AMCVisit.AMCVisitRequest;

public interface AMCVisitDAO {

	Map<String, Object> saveAMCVisit(AMCVisitRequest request);

	Map<String, Object> deleteAMCVisit(AMCVisitRequest request);

	Map<String, Object> getAMCVisits(int dealId);

}
