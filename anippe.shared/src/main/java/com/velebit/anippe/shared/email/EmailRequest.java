package com.velebit.anippe.shared.email;

import java.util.List;

public class EmailRequest implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String subject;
    private String message;
    private String senderName;
    private String senderEmail;
    private List<String> receivers;
    private List<String> bccReceivers;
    private List<String> ccReceivers;
    private Integer clientId;
    private Integer relatedId;
    private Integer relatedType;
    private Integer organisationId;
    private Integer userId;
    private String language = "hr";
    private Integer statusId;

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(Integer organisationId) {
        this.organisationId = organisationId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<String> getCcReceivers() {
        return ccReceivers;
    }

    public void setCcReceivers(List<String> ccReceivers) {
        this.ccReceivers = ccReceivers;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public List<String> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<String> receivers) {
        this.receivers = receivers;
    }

    public List<String> getBccReceivers() {
        return bccReceivers;
    }

    public void setBccReceivers(List<String> bccReceivers) {
        this.bccReceivers = bccReceivers;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
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

}
