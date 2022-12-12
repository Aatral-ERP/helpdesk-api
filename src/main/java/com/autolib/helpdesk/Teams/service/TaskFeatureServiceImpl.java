package com.autolib.helpdesk.Teams.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autolib.helpdesk.Teams.dao.TaskFeatureDAO;
import com.autolib.helpdesk.Teams.model.TaskFeatureRequest;

@Service
public class TaskFeatureServiceImpl implements TaskFeatureService {

	@Autowired
	TaskFeatureDAO featureDAO;

	@Override
	public Map<String, Object> createTaskFeature(TaskFeatureRequest featureReq) {
		// TODO Auto-generated method stub
		return featureDAO.createTaskFeature(featureReq);
	}

	@Override
	public Map<String, Object> getTaskFeature(int taskFeatureId) {
		// TODO Auto-generated method stub
		return featureDAO.getTaskFeature(taskFeatureId);
	}

	@Override
	public Map<String, Object> deleteTaskFeature(TaskFeatureRequest featureReq) {
		// TODO Auto-generated method stub
		return featureDAO.deleteTaskFeature(featureReq);
	}

	@Override
	public Map<String, Object> getTeamTaskFeatures(int teamId) {
		// TODO Auto-generated method stub
		return featureDAO.getTeamTaskFeatures(teamId);
	}

}
