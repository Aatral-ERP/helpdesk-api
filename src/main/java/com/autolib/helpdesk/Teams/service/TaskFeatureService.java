package com.autolib.helpdesk.Teams.service;

import java.util.Map;

import com.autolib.helpdesk.Teams.model.TaskFeatureRequest;

public interface TaskFeatureService {

	Map<String, Object> createTaskFeature(TaskFeatureRequest featureReq);

	Map<String, Object> getTaskFeature(int taskFeatureId);

	Map<String, Object> deleteTaskFeature(TaskFeatureRequest featureReq);

	Map<String, Object> getTeamTaskFeatures(int teamId);

}
