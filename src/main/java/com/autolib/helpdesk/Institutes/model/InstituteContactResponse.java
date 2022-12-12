package com.autolib.helpdesk.Institutes.model;

public class InstituteContactResponse {

	private Institute institutes;

	private InstituteContact instituteContact;

	public InstituteContactResponse(Institute institutes, InstituteContact instituteContact) {
		super();
		this.institutes = institutes;
		this.instituteContact = instituteContact;
	}

	public Institute getInstitutes() {
		return institutes;
	}

	public void setInstitutes(Institute institutes) {
		this.institutes = institutes;
	}

	public InstituteContact getInstituteContact() {
		return instituteContact;
	}

	public void setInstituteContact(InstituteContact instituteContact) {
		this.instituteContact = instituteContact;
	}

}
