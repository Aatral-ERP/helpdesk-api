package com.autolib.helpdesk.Institutes.model;

import java.util.List;

public class InstituteImportReq {
	
	private List<Institute> institute;
	private String instituteId;

	public List<Institute> getInstitute() {
		return institute;
	}

	public void setInstitute(List<Institute> institute) {
		this.institute = institute;
	}

	public String getInstituteId() {
		return instituteId;
	}

	public void setInstituteId(String instituteId) {
		this.instituteId = instituteId;
	}

	@Override
	public String toString() {
		return "InstituteImportReq [institute=" + institute + ", instituteId=" + instituteId + "]";
	}
}
