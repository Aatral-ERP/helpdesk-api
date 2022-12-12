/**
 * 
 */
package com.autolib.helpdesk.Chat.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Kannadasan
 *
 */
@Entity
@Table(name = "chat_setting")
public class ChatSettings {

	/**
	 * 
	 */
	public ChatSettings() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@Column(name = "employee_id", nullable = false)
	private String employeeId;
	
	@Column(name = "agent_email_id", nullable = false)
	private String agentMailId;
	
	@Column(name = "chat_enable", columnDefinition = "tinyint(1)")
	public int chatEnable;
	@Column(name = "enable_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date enableDate;
	@Column(name = "last_enable_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastEnableDate;
	@Column(name = "last_disable_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastDisableDate;

	public int isChatEnable() {
		return chatEnable;
	}

	public void setChatEnable(int chatEnable) {
		this.chatEnable = chatEnable;
	}

	public Date getEnableDate() {
		return enableDate;
	}

	public void setEnableDate(Date enableDate) {
		this.enableDate = enableDate;
	}

	public Date getLastEnableDate() {
		return lastEnableDate;
	}

	public void setLastEnableDate(Date lastEnableDate) {
		this.lastEnableDate = lastEnableDate;
	}

	public Date getLastDisableDate() {
		return lastDisableDate;
	}

	public void setLastDisableDate(Date lastDisableDate) {
		this.lastDisableDate = lastDisableDate;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getAgentMailId() {
		return agentMailId;
	}

	public void setAgentMailId(String agentMailId) {
		this.agentMailId = agentMailId;
	}

	public int getChatEnable() {
		return chatEnable;
	}

	@Override
	public String toString() {
		return "ChatSettings [employeeId=" + employeeId + ", agentMailId=" + agentMailId + ", chatEnable=" + chatEnable
				+ ", enableDate=" + enableDate + ", lastEnableDate=" + lastEnableDate + ", lastDisableDate="
				+ lastDisableDate + "]";
	}	

}
