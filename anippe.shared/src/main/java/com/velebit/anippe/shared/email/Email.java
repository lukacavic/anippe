/*
 * Copyright (c) 2022. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.velebit.anippe.shared.email;

import com.velebit.anippe.shared.beans.User;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.platform.util.CollectionUtility;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Email implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Date createdAt;
	private User sender;
	private String subject;
	private String receivers;
	private String ccReceivers;
	private String bccReceivers;
	private String content;
	private Integer attachmentsCount;
	private Integer reminderId;
	private String senderName;
	private String senderEmail;
	private Integer organisationId;
	List<BinaryResource> attachments = CollectionUtility.emptyArrayList();
	private Integer clientId;
	private Integer userId;
    private Integer statusId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public String getSenderEmail() {
		return senderEmail;
	}

	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}

	public Integer getOrganisationId() {
		return organisationId;
	}

	public void setOrganisationId(Integer organisationId) {
		this.organisationId = organisationId;
	}

	public List<BinaryResource> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<BinaryResource> attachments) {
		this.attachments = attachments;
	}

	public Integer getAttachmentsCount() {
		return attachmentsCount;
	}

	public void setAttachmentsCount(Integer attachmentsCount) {
		this.attachmentsCount = attachmentsCount;
	}

	public String getBccReceivers() {
		return bccReceivers;
	}

	public void setBccReceivers(String bccReceivers) {
		this.bccReceivers = bccReceivers;
	}

	public String getCcReceivers() {
		return ccReceivers;
	}

	public void setCcReceivers(String ccReceivers) {
		this.ccReceivers = ccReceivers;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getReceivers() {
		return receivers;
	}

	public void setReceivers(String receivers) {
		this.receivers = receivers;
	}

	public Integer getReminderId() {
		return reminderId;
	}

	public void setReminderId(Integer reminderId) {
		this.reminderId = reminderId;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public List<String> formattedReceivers() {
        return this.receivers != null ? Arrays.asList(this.receivers.split(",")) : null;
    }

    public List<String> formattedCCReceivers() {
        return this.ccReceivers != null ? Arrays.asList(this.ccReceivers.split(",")) : null;
    }

    public List<String> formattedBCCReceivers() {
        return this.bccReceivers != null ? Arrays.asList(this.bccReceivers.split(",")) : null;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }
}
