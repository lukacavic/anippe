package com.velebit.anippe.shared.reminders;

import com.velebit.anippe.shared.AbstractRequest;

import java.util.Date;

public class ReminderRequest extends AbstractRequest {

	private static final long serialVersionUID = 1L;
	private String title;
	private String content;
	private Date remindAt;
	private String contact;
	private boolean systemGenerated = false;

	public boolean isSystemGenerated() {
		return systemGenerated;
	}

	public void setSystemGenerated(boolean systemGenerated) {
		this.systemGenerated = systemGenerated;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public ReminderRequest() {
		super();
	}

	public ReminderRequest(Integer relatedId, Integer relatedType) {
		this.setRelatedId(relatedId);
		this.setRelatedType(relatedType);
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

}
