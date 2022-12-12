package com.autolib.helpdesk.Chat.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "chats_last_messages")
public class LastMessageDetails {

	@Id
	@Column(name = "chat_id", length = 45, nullable = false)
	public String chatId;
	
	@Column(name= "user_email_id" ,length = 64, nullable = true)
	public String userMailId;
	
	@Column(nullable = false)
	public int lastmsgid = 0;
	
	@Column(name = "agent_email_id", length = 45, nullable = true)
	private String agentMailId;
	
	@Column(length = 4096, nullable = false)
	@Lob
	private String last_message;
	
	@Column(name = "last_message_by", length = 45, nullable = false)
	private String lastMessageBy;
	
	@Column(name = "is_read_by_agent", length = 8, nullable = false)
	private int isReadByAgent = 0;
	
	@Column(name = "is_read_by_user", length = 8, nullable = false)
	private int isReadByUser = 0;
	
	@Column(name = "last_message_datetime", nullable = false)
	private Calendar lastMessageDateTime;

	
	@Column(name = "user_employee_id", length = 64, nullable = true)
	public String userEmployeeId;
	
	@Column(name = "agent_employee_id", length = 64, nullable = true)
	private String agentEmployeeId;
	
	@Column(name = "chat_employee_id", length = 64, nullable = true)
	private String chatEmployeeId;
	
	@Column(name = "institute_id", length = 64, nullable = true)
	private String instituteId;
	
	public String getInstituteId() {
		return instituteId;
	}

	public void setInstituteId(String instituteId) {
		this.instituteId = instituteId;
	}

	public LastMessageDetails() {
		super();
	}

	public String getChatId() {
		return chatId;
	}

	public void setChatId(String chatId) {
		this.chatId = chatId;
	}

	public String getUserMailId() {
		return userMailId;
	}

	public void setUserMailId(String userMailId) {
		this.userMailId = userMailId;
	}

	public int getLastmsgid() {
		return lastmsgid;
	}

	public void setLastmsgid(int lastmsgid) {
		this.lastmsgid = lastmsgid;
	}

	public String getAgentMailId() {
		return agentMailId;
	}

	public void setAgentMailId(String agentMailId) {
		this.agentMailId = agentMailId;
	}

	public String getLast_message() {
		return last_message;
	}

	public void setLast_message(String last_message) {
		this.last_message = last_message;
	}

	public String getLastMessageBy() {
		return lastMessageBy;
	}

	public void setLastMessageBy(String lastMessageBy) {
		this.lastMessageBy = lastMessageBy;
	}

	public int getIsReadByAgent() {
		return isReadByAgent;
	}

	public void setIsReadByAgent(int isReadByAgent) {
		this.isReadByAgent = isReadByAgent;
	}

	public int getIsReadByUser() {
		return isReadByUser;
	}

	public void setIsReadByUser(int isReadByUser) {
		this.isReadByUser = isReadByUser;
	}

	public Calendar getLastMessageDateTime() {
		return lastMessageDateTime;
	}

	public void setLastMessageDateTime(Calendar lastMessageDateTime) {
		this.lastMessageDateTime = lastMessageDateTime;
	}

	public String getUserEmployeeId() {
		return userEmployeeId;
	}

	public void setUserEmployeeId(String userEmployeeId) {
		this.userEmployeeId = userEmployeeId;
	}

	public String getAgentEmployeeId() {
		return agentEmployeeId;
	}

	public void setAgentEmployeeId(String agentEmployeeId) {
		this.agentEmployeeId = agentEmployeeId;
	}

	public String getChatEmployeeId() {
		return chatEmployeeId;
	}

	public void setChatEmployeeId(String chatEmployeeId) {
		this.chatEmployeeId = chatEmployeeId;
	}

	@Override
	public String toString() {
		return "LastMessageDetails [chatId=" + chatId + ", userMailId=" + userMailId + ", lastmsgid=" + lastmsgid
				+ ", agentMailId=" + agentMailId + ", last_message=" + last_message + ", lastMessageBy=" + lastMessageBy
				+ ", isReadByAgent=" + isReadByAgent + ", isReadByUser=" + isReadByUser + ", lastMessageDateTime="
				+ lastMessageDateTime + ", userEmployeeId=" + userEmployeeId + ", agentEmployeeId=" + agentEmployeeId
				+ ", chatEmployeeId=" + chatEmployeeId + ", instituteId=" + instituteId + "]";
	}

	

}
