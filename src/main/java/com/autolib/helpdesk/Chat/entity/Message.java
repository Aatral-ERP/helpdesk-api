package com.autolib.helpdesk.Chat.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "chats")
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	private int id;
	
	@Column(name = "chat_id", length = 45, nullable = false)
	public String chatId;
	
	@Column(name = "user_email_id", length = 64, nullable = true)
	public String userMailId;
	
	@Column(name = "agent_email_id", length = 64, nullable = true)
	private String agentMailId;
	
	@Column(length = 4096, nullable = false)
	@Lob
	private String message;
	@Column(name = "message_by", length = 45, nullable = false)
	private String messageBy;
	@Column(name = "is_read",length = 8, nullable = false)
	private int isRead = 0;
	@Column(name = "is_deleted",length = 8, nullable = false)
	private int isDeleted = 0;
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "message_datetime", nullable = false)
	private Calendar messageDateTime;

	@Transient
	private String messageByName;
	@Transient
	private String agentName;
	
	@Column(name = "user_employee_id", length = 64, nullable = true)
	public String userEmployeeId;
	
	@Column(name = "agent_employee_id", length = 64, nullable = true)
	private String agentEmployeeId;
	
	@Column(name = "chat_employee_id", length = 64, nullable = true)
	private String chatEmployeeId;
	
	@Column(name = "institute_id", length = 64, nullable = true)
	private String instituteId;
	
	@Column(name = "chat_type", length = 64, nullable = true)
	private String chatType;

	public String getChatType() {
		return chatType;
	}

	public void setChatType(String chatType) {
		this.chatType = chatType;
	}

	public String getInstituteId() {
		return instituteId;
	}

	public void setInstituteId(String instituteId) {
		this.instituteId = instituteId;
	}

	public Message() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getChatId() {
		return chatId;
	}

	public void setChatId(String chatId) {
		this.chatId = chatId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessageBy() {
		return messageBy;
	}

	public void setMessageBy(String messageBy) {
		this.messageBy = messageBy;
	}

	public int isDeleted() {
		return isDeleted;
	}

	public void setDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Calendar getMessageDateTime() {
		return messageDateTime;
	}

	public void setMessageDateTime(Calendar messageDateTime) {
		this.messageDateTime = messageDateTime;
	}

	public String getMessageByName() {
		return messageByName;
	}

	public void setMessageByName(String messageByName) {
		this.messageByName = messageByName;
	}

	public String getAgentMailId() {
		return agentMailId;
	}

	public void setAgentMailId(String agentMailId) {
		this.agentMailId = agentMailId;
	}

	public int getIsRead() {
		return isRead;
	}

	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}

	public int getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getUserMailId() {
		return userMailId;
	}

	public void setUserMailId(String userMailId) {
		this.userMailId = userMailId;
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
		return "Message [id=" + id + ", chatId=" + chatId + ", userMailId=" + userMailId + ", agentMailId="
				+ agentMailId + ", message=" + message + ", messageBy=" + messageBy + ", isRead=" + isRead
				+ ", isDeleted=" + isDeleted + ", messageDateTime=" + messageDateTime + ", messageByName="
				+ messageByName + ", agentName=" + agentName + ", userEmployeeId=" + userEmployeeId
				+ ", agentEmployeeId=" + agentEmployeeId + ", chatEmployeeId=" + chatEmployeeId + ", instituteId="
				+ instituteId + ", chatType=" + chatType + "]";
	}

	
	
}
