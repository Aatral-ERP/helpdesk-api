package com.autolib.helpdesk.Teams.repository.Tasks;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autolib.helpdesk.Teams.model.Tasks.TaskComments;

public interface TaskCommentRepository extends JpaRepository<TaskComments, Integer> {

	List<TaskComments> findByTaskId(int taskId);

}
