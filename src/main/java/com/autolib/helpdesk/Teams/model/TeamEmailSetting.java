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

import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "team_email_settings", uniqueConstraints = {
		@UniqueConstraint(name = "team_action", columnNames = { "action", "team_id" }) }, indexes = {
				@Index(name = "team_id_idx", columnList = "team_id"),
				@Index(name = "action_idx", columnList = "action") })
public class TeamEmailSetting {

	public TeamEmailSetting() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "team_id", nullable = false)
	private int teamId;

	@Column(name = "action", nullable = false)
	private String action;

	@Column(name = "lead", nullable = false)
	private boolean lead = false;

	@Column(name = "reporter", nullable = false)
	private boolean reporter = false;

	@Column(name = "assignee", nullable = false)
	private boolean assignee = false;

	@Column(name = "institute", nullable = false)
	private boolean institute = false;

	@Column(name = "watchers", nullable = false)
	private boolean watchers = false;

	@Column(name = "viewers", nullable = false)
	private boolean viewers = false;

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

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public boolean isLead() {
		return lead;
	}

	public void setLead(boolean lead) {
		this.lead = lead;
	}

	public boolean isReporter() {
		return reporter;
	}

	public void setReporter(boolean reporter) {
		this.reporter = reporter;
	}

	public boolean isAssignee() {
		return assignee;
	}

	public void setAssignee(boolean assignee) {
		this.assignee = assignee;
	}

	public boolean isInstitute() {
		return institute;
	}

	public void setInstitute(boolean institute) {
		this.institute = institute;
	}

	public boolean isWatchers() {
		return watchers;
	}

	public void setWatchers(boolean watchers) {
		this.watchers = watchers;
	}

	public boolean isViewers() {
		return viewers;
	}

	public void setViewers(boolean viewers) {
		this.viewers = viewers;
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
		return "TeamEmailSetting [id=" + id + ", teamId=" + teamId + ", action=" + action + ", lead=" + lead
				+ ", reporter=" + reporter + ", assignee=" + assignee + ", institute=" + institute + ", watchers="
				+ watchers + ", viewers=" + viewers + ", lastUpdatedBy=" + lastUpdatedBy + ", lastupdatedatetime="
				+ lastupdatedatetime + "]";
	}

}
