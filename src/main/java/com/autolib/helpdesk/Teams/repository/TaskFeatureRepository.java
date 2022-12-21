package com.autolib.helpdesk.Teams.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autolib.helpdesk.Teams.model.Tasks.TaskFeature;

public interface TaskFeatureRepository extends JpaRepository<TaskFeature, Integer> {

	TaskFeature findByName(String name);

	TaskFeature getByFeatureId(int taskFeatureId);

	List<TaskFeature> getByTeamId(int teamId);

}
