package com.autolib.helpdesk.Teams.model;

import com.autolib.helpdesk.Agents.entity.Agent;
import com.autolib.helpdesk.Teams.model.Tasks.TaskFeature;

public class TaskFeatureRequest {

	private Agent agent;

	private TaskFeature feature;

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public TaskFeature getFeature() {
		return feature;
	}

	public void setFeature(TaskFeature feature) {
		this.feature = feature;
	}

}
