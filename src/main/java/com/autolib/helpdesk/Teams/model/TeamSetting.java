package com.autolib.helpdesk.Teams.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "team_settings", indexes = { @Index(name = "team_id_idx", columnList = "team_id") })
public class TeamSetting {

	public TeamSetting() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "team_id", nullable = false, unique = true)
	private int teamId;

	@Column(name = "admin_can_create_tasks", nullable = false)
	private boolean adminCanCreateTasks = false;

	@Column(name = "admin_can_modify_others_tasks", nullable = false)
	private boolean adminCanModifyOthersTasks = false;

	@Column(name = "members_can_create_tasks", nullable = false)
	private boolean membersCanCreateTasks = false;

	@Column(name = "members_can_close_tasks_of_own", nullable = false)
	private boolean membersCanCloseTaskOfOwn = false;

	@Column(name = "members_can_view_others_tasks", nullable = false)
	private boolean membersCanViewOthersTasks = false;

	@Column(name = "members_can_comment_others_tasks", nullable = false)
	private boolean membersCanCommentOthersTasks = false;

	@Column(name = "viewer_can_comment_tasks", nullable = false)
	private boolean viewerCanCommentTasks = false;

	@Column(name = "last_updated_by")
	private String lastUpdatedBy;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date lastupdatedatetime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	public boolean isAdminCanCreateTasks() {
		return adminCanCreateTasks;
	}

	public void setAdminCanCreateTasks(boolean adminCanCreateTasks) {
		this.adminCanCreateTasks = adminCanCreateTasks;
	}

	public boolean isAdminCanModifyOthersTasks() {
		return adminCanModifyOthersTasks;
	}

	public void setAdminCanModifyOthersTasks(boolean adminCanModifyOthersTasks) {
		this.adminCanModifyOthersTasks = adminCanModifyOthersTasks;
	}

	public boolean isMembersCanCreateTasks() {
		return membersCanCreateTasks;
	}

	public void setMembersCanCreateTasks(boolean membersCanCreateTasks) {
		this.membersCanCreateTasks = membersCanCreateTasks;
	}

	public boolean isMembersCanCloseTaskOfOwn() {
		return membersCanCloseTaskOfOwn;
	}

	public void setMembersCanCloseTaskOfOwn(boolean membersCanCloseTaskOfOwn) {
		this.membersCanCloseTaskOfOwn = membersCanCloseTaskOfOwn;
	}

	public boolean isMembersCanViewOthersTasks() {
		return membersCanViewOthersTasks;
	}

	public void setMembersCanViewOthersTasks(boolean membersCanViewOthersTasks) {
		this.membersCanViewOthersTasks = membersCanViewOthersTasks;
	}

	public boolean isMembersCanCommentOthersTasks() {
		return membersCanCommentOthersTasks;
	}

	public void setMembersCanCommentOthersTasks(boolean membersCanCommentOthersTasks) {
		this.membersCanCommentOthersTasks = membersCanCommentOthersTasks;
	}

	public boolean isViewerCanCommentTasks() {
		return viewerCanCommentTasks;
	}

	public void setViewerCanCommentTasks(boolean viewerCanCommentTasks) {
		this.viewerCanCommentTasks = viewerCanCommentTasks;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getLastupdatedatetime() {
		return lastupdatedatetime;
	}

	public void setLastupdatedatetime(Date lastupdatedatetime) {
		this.lastupdatedatetime = lastupdatedatetime;
	}

	@Override
	public String toString() {
		return "TeamSetting [id=" + id + ", teamId=" + teamId + ", adminCanCreateTasks=" + adminCanCreateTasks
				+ ", adminCanModifyOthersTasks=" + adminCanModifyOthersTasks + ", membersCanCreateTasks="
				+ membersCanCreateTasks + ", membersCanCloseTaskOfOwn=" + membersCanCloseTaskOfOwn
				+ ", membersCanViewOthersTasks=" + membersCanViewOthersTasks + ", membersCanCommentOthersTasks="
				+ membersCanCommentOthersTasks + ", viewerCanCommentTasks=" + viewerCanCommentTasks + ", lastUpdatedBy="
				+ lastUpdatedBy + ", lastupdatedatetime=" + lastupdatedatetime + "]";
	}

}
