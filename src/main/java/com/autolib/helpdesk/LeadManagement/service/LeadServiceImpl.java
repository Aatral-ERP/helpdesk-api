package com.autolib.helpdesk.LeadManagement.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autolib.helpdesk.LeadManagement.dao.LeadDAO;
import com.autolib.helpdesk.LeadManagement.model.LeadDashboardRequest;
import com.autolib.helpdesk.LeadManagement.model.LeadRequest;
import com.autolib.helpdesk.LeadManagement.model.LeadSearchRequest;
import com.autolib.helpdesk.LeadManagement.model.LeadUploadDetail;

@Service
public class LeadServiceImpl implements LeadService {

	@Autowired
	private LeadDAO leadDAO;

	@Override
	public Map<String, Object> createLead(LeadRequest leadReq) {
		return leadDAO.createLead(leadReq);
	}

	@Override
	public Map<String, Object> updateLead(LeadRequest leadReq) {
		return leadDAO.updateLead(leadReq);
	}

	@Override
	public Map<String, Object> getLead(int leadId) {
		return leadDAO.getLead(leadId);
	}

	@Override
	public Map<String, Object> deleteLead(LeadRequest leadReq) {
		return leadDAO.deleteLead(leadReq);
	}

	@Override
	public Map<String, Object> searchLeads(LeadSearchRequest request) {
		return leadDAO.searchLeads(request);
	}

	@Override
	public Map<String, Object> saveLeadTask(LeadRequest leadReq) {
		return leadDAO.saveLeadTask(leadReq);
	}

	@Override
	public Map<String, Object> getLeadTasks(int leadId) {
		return leadDAO.getLeadTasks(leadId);
	}

	@Override
	public Map<String, Object> deleteLeadTask(LeadRequest leadReq) {
		return leadDAO.deleteLeadTask(leadReq);
	}

	@Override
	public Map<String, Object> saveLeadMeeting(LeadRequest leadReq) {
		return leadDAO.saveLeadMeeting(leadReq);
	}

	@Override
	public Map<String, Object> getLeadMeetings(int leadId) {
		return leadDAO.getLeadMeetings(leadId);
	}

	@Override
	public Map<String, Object> deleteLeadMeeting(LeadRequest leadReq) {
		return leadDAO.deleteLeadMeeting(leadReq);
	}

	@Override
	public Map<String, Object> createMultipleLead(List<LeadUploadDetail> leadDetails) {
		return leadDAO.createMultipleLead(leadDetails);
	}

	@Override
	public Map<String, Object> getDashboardData(LeadDashboardRequest request) {
		return leadDAO.getDashboardData(request);
	}

}
