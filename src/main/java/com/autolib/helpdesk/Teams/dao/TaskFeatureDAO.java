package com.autolib.helpdesk.Teams.dao;

import java.util.Map;

import com.autolib.helpdesk.Teams.model.TaskFeatureRequest;

public interface TaskFeatureDAO {

	Map<String, Object> createTaskFeature(TaskFeatureRequest featureReq);

	Map<String, Object> getTaskFeature(int taskFeatureId);

	Map<String, Object> deleteTaskFeature(TaskFeatureRequest featureReq);

	Map<String, Object> getTeamTaskFeatures(int teamId);

}
