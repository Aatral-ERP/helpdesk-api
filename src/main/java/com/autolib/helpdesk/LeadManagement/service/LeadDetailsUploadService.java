package com.autolib.helpdesk.LeadManagement.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.autolib.helpdesk.LeadManagement.model.LeadUploadDetail;

public interface LeadDetailsUploadService {

	Map<String, Object> validateLeadDetailExcel(File file) throws FileNotFoundException, IOException;

	List<LeadUploadDetail> leadMapper(File file);

}
