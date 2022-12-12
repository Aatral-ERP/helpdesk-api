package com.autolib.helpdesk.Sales.service;

import java.util.Map;

import com.autolib.helpdesk.Sales.model.AMCVisit.AMCVisitRequest;

public interface AMCVisitService {

	Map<String, Object> saveAMCVisit(AMCVisitRequest request);

	Map<String, Object> deleteAMCVisit(AMCVisitRequest request);

	Map<String, Object> getAMCVisits(int dealId);

}
