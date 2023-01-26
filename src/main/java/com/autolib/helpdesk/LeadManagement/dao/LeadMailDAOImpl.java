package com.autolib.helpdesk.LeadManagement.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.autolib.helpdesk.Config.aws.S3Directories;
import com.autolib.helpdesk.common.S3StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.autolib.helpdesk.LeadManagement.model.LeadMailSentSearchRequest;
import com.autolib.helpdesk.LeadManagement.model.LeadMailSentStatusResponse;
import com.autolib.helpdesk.LeadManagement.model.LeadMailTemplate;
import com.autolib.helpdesk.LeadManagement.model.LeadMailTemplateRequest;
import com.autolib.helpdesk.LeadManagement.model.LeadMailTemplateSearchRequest;
import com.autolib.helpdesk.LeadManagement.repository.LeadContactRepository;
import com.autolib.helpdesk.LeadManagement.repository.LeadMailTemplateRepository;
import com.autolib.helpdesk.LeadManagement.repository.LeadRepository;
import com.autolib.helpdesk.common.Util;
import com.autolib.helpdesk.schedulers.controller.SendLeadMailsScheduler;
import com.autolib.helpdesk.schedulers.model.LeadMailSentStatus;

@Repository
public class LeadMailDAOImpl implements LeadMailDAO {

    @Autowired
    LeadMailTemplateRepository mailTemplateRepo;

    @Autowired
    LeadRepository leadRepo;

    @Autowired
    LeadContactRepository leadContactRepo;

    @Autowired
    SendLeadMailsScheduler leadMailScheduler;

    @Autowired
    EntityManager em;

    @Value("${al.ticket.content-path}")
    private String contentPath;

    @Autowired
    S3StorageService s3StorageService;

    @Override
    public Map<String, Object> saveLeadMailTemplate(LeadMailTemplateRequest templateRequest) {
        Map<String, Object> resp = new HashMap<>();
        try {

            templateRequest.setLeadMailTemplate(mailTemplateRepo.save(templateRequest.getLeadMailTemplate()));

            if (templateRequest.getLeadMailTemplate().getFiles() != null
                    && templateRequest.getLeadMailTemplate().getFiles().length() > 0) {

                List<String> filenames = Arrays.asList(templateRequest.getLeadMailTemplate().getFiles().split(";"));

                filenames.parallelStream()
                        .filter(filename -> filename.length() > 0)
                        .forEach(filename -> {
                            s3StorageService.pushLocalFileToAWS(
                                    S3Directories.LeadMailTemplate + templateRequest.getLeadMailTemplate().getId(),
                                    S3Directories.LeadMailTemplate + templateRequest.getDirectoryName() + "/" + filename);
                        });
            }

            resp.putAll(Util.SuccessResponse());
        } catch (Exception ex) {
            resp.putAll(Util.FailedResponse(ex.getMessage()));
            ex.printStackTrace();
        }
        resp.put("LeadMailTemplate", templateRequest.getLeadMailTemplate());
        return resp;
    }

    @Override
    public Map<String, Object> getLeadMailTemplate(int templateId) {
        Map<String, Object> resp = new HashMap<>();
        LeadMailTemplate template = null;
        try {

            template = mailTemplateRepo.findById(templateId);

            resp.putAll(Util.SuccessResponse());
        } catch (Exception ex) {
            resp.putAll(Util.FailedResponse(ex.getMessage()));
            ex.printStackTrace();
        }
        resp.put("LeadMailTemplate", template);
        return resp;
    }

    @Override
    public Map<String, Object> deleteLeadMailTemplate(LeadMailTemplate template) {
        Map<String, Object> resp = new HashMap<>();
        try {

            mailTemplateRepo.delete(template);

            resp.putAll(Util.SuccessResponse());
        } catch (Exception ex) {
            resp.putAll(Util.FailedResponse(ex.getMessage()));
            ex.printStackTrace();
        }
        resp.put("LeadMailTemplate", template);
        return resp;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> searchLeadMailTemplates(LeadMailTemplateSearchRequest request) {
        Map<String, Object> resp = new HashMap<>();
        List<LeadMailTemplate> templates = new ArrayList<>();
        try {

            String filterQuery = "";

            if (request.getTitle() != null && !request.getTitle().isEmpty())
                filterQuery = filterQuery + " and t.title like '%" + request.getTitle() + "%' ";
            if (request.getSubject() != null && !request.getSubject().isEmpty())
                filterQuery = filterQuery + " and t.subject like '%" + request.getSubject() + "%' ";
            if (request.getMessage() != null && !request.getMessage().isEmpty())
                filterQuery = filterQuery + " and t.message like '%" + request.getMessage() + "%' ";

            if (request.getStates() != null && request.getStates().size() > 0) {
                String _states = "'0'";
                for (String states : request.getStates()) {
                    _states = _states + ",'" + states + "'";
                }
                filterQuery = filterQuery + " and t.state in (" + _states + ") ";
            }

            if (request.getFrequency() != null && request.getFrequency().size() > 0) {
                String _frequency = "'0'";
                for (String frequency : request.getFrequency()) {
                    _frequency = _frequency + ",'" + frequency + "'";
                }
                filterQuery = filterQuery + " and t.frequency in (" + _frequency + ") ";
            }

            if (request.getStatus() != null && request.getStatus().size() > 0) {
                String _status = "'0'";
                for (String status : request.getStatus()) {
                    _status = _status + ",'" + status + "'";
                }
                filterQuery = filterQuery + " and t.status in (" + _status + ") ";
            }

            if (request.getCategories() != null && request.getCategories().size() > 0) {
                String _category = "'0'";
                for (String category : request.getCategories()) {
                    _category = _category + ",'" + category + "'";
                }
                filterQuery = filterQuery + " and t.category in (" + _category + ") ";
            }

            if (request.getIndustryTypes() != null && request.getIndustryTypes().size() > 0) {
                String _ind_types = "'0'";
                for (String ind_types : request.getIndustryTypes()) {
                    _ind_types = _ind_types + ",'" + ind_types + "'";
                }
                filterQuery = filterQuery + " and t.industryType in (" + _ind_types + ") ";
            }

            Query query = em.createQuery("select t from LeadMailTemplate t where 2>1 " + filterQuery,
                    LeadMailTemplate.class);

            templates = query.getResultList();

            resp.putAll(Util.SuccessResponse());
        } catch (Exception ex) {
            resp.putAll(Util.FailedResponse(ex.getMessage()));
            ex.printStackTrace();
        }
        resp.put("LeadMailTemplates", templates);
        return resp;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> searchLeadMailSentStatus(LeadMailSentSearchRequest request) {
        Map<String, Object> resp = new HashMap<>();
        List<LeadMailSentStatus> status = new ArrayList<>();
        try {

            String filterQuery = "";

            if (request.getLeadId() != null && !request.getLeadId().isEmpty())
                filterQuery = filterQuery + " and ss.leadId = '" + request.getLeadId() + "'";
            if (request.getTemplateId() != null && !request.getTemplateId().isEmpty())
                filterQuery = filterQuery + " and ss.templateId = '" + request.getTemplateId() + "'";
            if (request.getMailId() != null && !request.getMailId().isEmpty())
                filterQuery = filterQuery + " and ss.mailId = '" + request.getMailId() + "'";
            if (request.getMailCC() != null && !request.getMailCC().isEmpty())
                filterQuery = filterQuery + " and ss.mailCC = '" + request.getMailCC() + "'";
            if (request.getLeadCompany() != null && !request.getLeadCompany().isEmpty())
                filterQuery = filterQuery + " and l.company = '" + request.getLeadCompany() + "'";
            if (request.getLeadTitle() != null && !request.getLeadTitle().isEmpty())
                filterQuery = filterQuery + " and l.title = '" + request.getLeadTitle() + "'";

            if (request.getSendDateFrom() != null && request.getSendDateTo() != null) {
                filterQuery = filterQuery + " and l.createddatetime between '"
                        + Util.sdfFormatter(request.getSendDateFrom()) + "' and '"
                        + Util.sdfFormatter(request.getSendDateTo()) + " 23:59:59'";
            }

            if (request.getLeadMessage() != null && !request.getLeadMessage().isEmpty())
                filterQuery = filterQuery + " and ss.message = '" + request.getLeadMessage() + "'";

            if (request.getLeadSubject() != null && !request.getLeadSubject().isEmpty())
                filterQuery = filterQuery + " and ss.subject = '" + request.getLeadSubject() + "'";

            if (request.getLeadStatus() != null && !request.getLeadStatus().isEmpty()) {
                String _leadStatus = "'0'";
                for (String leadStatus : request.getLeadStatus()) {
                    _leadStatus = _leadStatus + ",'" + leadStatus + "'";
                }
                filterQuery = filterQuery + " and ss.status in (" + _leadStatus + ") ";
            }

            if (request.getLeadSources() != null && !request.getLeadSources().isEmpty()) {
                String _elements = "'0'";
                for (String element : request.getLeadSources()) {
                    _elements = _elements + ",'" + element + "'";
                }
                filterQuery = filterQuery + " and l.leadSource in (" + _elements + ") ";
            }

            if (request.getLeadCatogories() != null && !request.getLeadCatogories().isEmpty()) {
                String _elements = "'0'";
                for (String element : request.getLeadCatogories()) {
                    _elements = _elements + ",'" + element + "'";
                }
                filterQuery = filterQuery + " and l.category in (" + _elements + ") ";
            }

            if (request.getLeadStates() != null && !request.getLeadStates().isEmpty()) {
                String _elements = "'0'";
                for (String element : request.getLeadStates()) {
                    _elements = _elements + ",'" + element + "'";
                }
                filterQuery = filterQuery + " and l.state in (" + _elements + ") ";
            }

            if (request.getIndustryTypes() != null && !request.getIndustryTypes().isEmpty()) {
                String _elements = "'0'";
                for (String element : request.getIndustryTypes()) {
                    _elements = _elements + ",'" + element + "'";
                }
                filterQuery = filterQuery + " and l.industryType in (" + _elements + ") ";
            }

            Query query = em.createQuery(
                    "select new com.autolib.helpdesk.LeadManagement.model.LeadMailSentStatusResponse(l,ss) "
                            + "from LeadMailSentStatus ss left join fetch Lead l on (ss.leadId = l.id) where 2>1 "
                            + filterQuery,
                    LeadMailSentStatusResponse.class);

            status = query.getResultList();

            resp.putAll(Util.SuccessResponse());
        } catch (Exception ex) {
            resp.putAll(Util.FailedResponse(ex.getMessage()));
            ex.printStackTrace();
        }
        resp.put("LeadMailSentStatus", status);
        return resp;
    }

    @Override
    public Map<String, Object> searchLeadMailSentStatus(int leadId, int templateId) {
        Map<String, Object> resp = new HashMap<>();
        LeadMailSentStatus sendMailStatus = null;
        try {

            sendMailStatus = leadMailScheduler.sendMail(leadRepo.findById(leadId), leadContactRepo.findByLeadId(leadId),
                    mailTemplateRepo.findById(templateId));

            resp.putAll(Util.SuccessResponse());
        } catch (Exception ex) {
            resp.putAll(Util.FailedResponse(ex.getMessage()));
            ex.printStackTrace();
        }
        resp.put("LeadMailSentStatus", sendMailStatus);
        return resp;
    }

}
