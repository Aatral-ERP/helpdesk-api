package com.autolib.helpdesk.Teams.model;

import com.autolib.helpdesk.Agents.entity.Agent;
import com.autolib.helpdesk.Teams.model.Tasks.TaskFeature;

public class TaskFeatureRequest {

    private Agent agent;

    private TaskFeature feature;

    private String directoryName;

    public String getDirectoryName() {
        return directoryName;
    }

    public void setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
    }

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
