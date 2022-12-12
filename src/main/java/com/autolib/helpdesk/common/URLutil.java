package com.autolib.helpdesk.common;

public class URLutil {

	public static final String viewTeamURL = "/teams/view/";
	public static final String viewTeamTaskURL = "/teams/view/1/dashboard?action=view-task&action-id=";

	public static String getViewTeamTaskURL(String contentPath, int taskId) {
		return contentPath + viewTeamTaskURL + String.valueOf(taskId);
	}

}
