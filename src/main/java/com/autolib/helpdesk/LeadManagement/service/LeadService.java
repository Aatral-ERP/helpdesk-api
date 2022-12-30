package com.autolib.helpdesk.LeadManagement.service;

import java.util.List;
import java.util.Map;

import com.autolib.helpdesk.LeadManagement.model.LeadActivityReportRequest;
import com.autolib.helpdesk.LeadManagement.model.LeadDashboardRequest;
import com.autolib.helpdesk.LeadManagement.model.LeadRequest;
import com.autolib.helpdesk.LeadManagement.model.LeadSearchRequest;
import com.autolib.helpdesk.LeadManagement.model.LeadUploadDetail;

public interface LeadService {

	Map<String, Object> createLead(LeadRequest leadReq);

	Map<String, Object> updateLead(LeadRequest leadReq);

	Map<String, Object> getLead(int leadId);

	Map<String, Object> deleteLead(LeadRequest leadReq);

	Map<String, Object> searchLeads(LeadSearchRequest request);

	Map<String, Object> saveLeadTask(LeadRequest leadReq);

	Map<String, Object> getLeadTasks(int leadId);

	Map<String, Object> deleteLeadTask(LeadRequest leadReq);

	Map<String, Object> saveLeadMeeting(LeadRequest leadReq);

	Map<String, Object> getLeadMeetings(int leadId);

	Map<String, Object> deleteLeadMeeting(LeadRequest leadReq);

	Map<String, Object> createMultipleLead(List<LeadUploadDetail> leads);

	Map<String, Object> getDashboardData(LeadDashboardRequest request);

	Map<String, Object> getLeadActivityReport(LeadActivityReportRequest req);

}
