package com.autolib.helpdesk.Teams.repository.Tasks;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autolib.helpdesk.Teams.model.Tasks.TaskHistory;

public interface TaskHistoryRepository extends JpaRepository<TaskHistory, Integer> {

	List<TaskHistory> findByTaskId(int taskId);

}
