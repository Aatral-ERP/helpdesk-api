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
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "team_members", uniqueConstraints = {
		@UniqueConstraint(name = "team_member", columnNames = { "member_email_id", "team_id" }) }, indexes = {
				@Index(name = "team_id_idx", columnList = "team_id"),
				@Index(name = "member_role_idx", columnList = "member_role"),
				@Index(name = "member_email_id_idx", columnList = "member_email_id") })
public class TeamMembers {

	public TeamMembers() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "team_id", nullable = false)
	private int teamId;

	@Column(name = "member_email_id")
	private String memberEmailId;

	@Column(name = "member_role")
	private String memberRole;

	@Column(name = "open_tasks", nullable = false, columnDefinition = "int(11) default 0")
	private int openTasks;

	@Column(name = "closed_tasks", nullable = false, columnDefinition = "int(11) default 0")
	private int closedTasks;

	@Column(name = "rating", nullable = false, columnDefinition = "int(11) default 0")
	private int rating;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	private Date createddatetime;

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

	public String getMemberEmailId() {
		return memberEmailId;
	}

	public void setMemberEmailId(String memberEmailId) {
		this.memberEmailId = memberEmailId;
	}

	public String getMemberRole() {
		return memberRole;
	}

	public void setMemberRole(String memberRole) {
		this.memberRole = memberRole;
	}

	public int getOpenTasks() {
		return openTasks;
	}

	public void setOpenTasks(int openTasks) {
		this.openTasks = openTasks;
	}

	public int getClosedTasks() {
		return closedTasks;
	}

	public void setClosedTasks(int closedTasks) {
		this.closedTasks = closedTasks;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public Date getCreateddatetime() {
		return createddatetime;
	}

	public void setCreateddatetime(Date createddatetime) {
		this.createddatetime = createddatetime;
	}

	@Override
	public String toString() {
		return "TeamMembers [id=" + id + ", teamId=" + teamId + ", memberEmailId=" + memberEmailId + ", memberRole="
				+ memberRole + ", openTasks=" + openTasks + ", closedTasks=" + closedTasks + ", rating=" + rating
				+ ", createddatetime=" + createddatetime + "]";
	}

}
