package com.autolib.helpdesk.Teams.repository.Tasks;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.autolib.helpdesk.Teams.model.Tasks.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {

	@Query("SELECT DISTINCT t.label FROM Task t")
	List<String> findDistinctLabel();

	@Query("SELECT DISTINCT t.label FROM Task t where t.teamId = ?1 and t.label != null")
	List<String> findDistinctLabelByTeam(int teamId);

	@Query("SELECT DISTINCT t.instituteId FROM Task t where t.teamId = ?1 and t.instituteId != 0")
	List<String> findDistinctInstituteIdsByTeam(int teamId);

	@Query("SELECT DISTINCT t.watchers FROM Task t where t.teamId = ?1 and t.watchers != null ")
	List<String> findDistinctWatchersByTeam(int teamId);

	Task findByTaskId(int taskId);

}
