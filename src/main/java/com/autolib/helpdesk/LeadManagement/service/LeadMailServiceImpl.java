package com.autolib.helpdesk.LeadManagement.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autolib.helpdesk.LeadManagement.dao.LeadMailDAO;
import com.autolib.helpdesk.LeadManagement.model.LeadMailSentSearchRequest;
import com.autolib.helpdesk.LeadManagement.model.LeadMailTemplate;
import com.autolib.helpdesk.LeadManagement.model.LeadMailTemplateRequest;
import com.autolib.helpdesk.LeadManagement.model.LeadMailTemplateSearchRequest;

@Service
public class LeadMailServiceImpl implements LeadMailService {

	@Autowired
	private LeadMailDAO mailDAO;

	@Override
	public Map<String, Object> saveLeadMailTemplate(LeadMailTemplateRequest template) {
		return mailDAO.saveLeadMailTemplate(template);
	}

	@Override
	public Map<String, Object> getLeadMailTemplate(int templateId) {
		return mailDAO.getLeadMailTemplate(templateId);
	}

	@Override
	public Map<String, Object> deleteLeadMailTemplate(LeadMailTemplate template) {
		return mailDAO.deleteLeadMailTemplate(template);
	}

	@Override
	public Map<String, Object> searchLeadMailTemplates(LeadMailTemplateSearchRequest request) {
		return mailDAO.searchLeadMailTemplates(request);
	}

	@Override
	public Map<String, Object> searchLeadMailSentStatus(LeadMailSentSearchRequest request) {
		return mailDAO.searchLeadMailSentStatus(request);
	}

	@Override
	public Map<String, Object> sentLeadMailByTemplate(int leadId, int templateId) {
		return mailDAO.searchLeadMailSentStatus(leadId, templateId);
	}

}
