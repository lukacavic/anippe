package com.velebit.anippe.server.reminders;

import java.util.Date;

public class ReminderDto {
	private Integer id;
	private Integer userCreatedId;
	private String userCreatedFirstName;
	private String userCreatedLastName;

	private Integer userId;
	private String userFirstName;
	private String userLastName;

	private String title;
	private String content;
	private Date remindAt;
	private Date createdAt;
	private boolean systemGenerated;
	private Integer organisationId;
	private Integer relatedId;
	private Integer relatedType;
	private boolean sendEmail;
	private Date completedAt;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public Date getCompletedAt() {
		return completedAt;
	}

	public void setCompletedAt(Date completedAt) {
		this.completedAt = completedAt;
	}

	public boolean isSendEmail() {
		return sendEmail;
	}

	public void setSendEmail(boolean sendEmail) {
		this.sendEmail = sendEmail;
	}

	public Integer getOrganisationId() {
		return organisationId;
	}

	public void setOrganisationId(Integer organisationId) {
		this.organisationId = organisationId;
	}

	public Integer getRelatedId() {
		return relatedId;
	}

	public void setRelatedId(Integer relatedId) {
		this.relatedId = relatedId;
	}

	public Integer getRelatedType() {
		return relatedType;
	}

	public void setRelatedType(Integer relatedType) {
		this.relatedType = relatedType;
	}

	public boolean isSystemGenerated() {
		return systemGenerated;
	}

	public void setSystemGenerated(boolean systemGenerated) {
		this.systemGenerated = systemGenerated;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserCreatedId() {
		return userCreatedId;
	}

	public void setUserCreatedId(Integer userCreatedId) {
		this.userCreatedId = userCreatedId;
	}

	public String getUserCreatedFirstName() {
		return userCreatedFirstName;
	}

	public void setUserCreatedFirstName(String userCreatedFirstName) {
		this.userCreatedFirstName = userCreatedFirstName;
	}

	public String getUserCreatedLastName() {
		return userCreatedLastName;
	}

	public void setUserCreatedLastName(String userCreatedLastName) {
		this.userCreatedLastName = userCreatedLastName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getRemindAt() {
		return remindAt;
	}

	public void setRemindAt(Date remindAt) {
		this.remindAt = remindAt;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}


}
