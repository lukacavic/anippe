package com.velebit.anippe.server.tickets;

import java.util.Date;

public class TicketDto {
    private Integer id;
    private String code;
    private String subject;
    private Date createdAt;
    private Integer statusId;
    private Integer priorityId;
    private Date lastReplyAt;

    //Department
    private Integer departmentId;
    private String departmentName;

    //Contact
    private Integer contactId;
    private String contactFirstName;
    private String contactLastName;

    //Assigned user
    private Integer assignedUserId;
    private String assignedUserFirstName;
    private String assignedUserLastName;

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Date getLastReplyAt() {
        return lastReplyAt;
    }

    public void setLastReplyAt(Date lastReplyAt) {
        this.lastReplyAt = lastReplyAt;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getAssignedUserId() {
        return assignedUserId;
    }

    public void setAssignedUserId(Integer assignedUserId) {
        this.assignedUserId = assignedUserId;
    }

    public String getAssignedUserFirstName() {
        return assignedUserFirstName;
    }

    public void setAssignedUserFirstName(String assignedUserFirstName) {
        this.assignedUserFirstName = assignedUserFirstName;
    }

    public String getAssignedUserLastName() {
        return assignedUserLastName;
    }

    public void setAssignedUserLastName(String assignedUserLastName) {
        this.assignedUserLastName = assignedUserLastName;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Integer getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(Integer priorityId) {
        this.priorityId = priorityId;
    }

    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    public String getContactFirstName() {
        return contactFirstName;
    }

    public void setContactFirstName(String contactFirstName) {
        this.contactFirstName = contactFirstName;
    }

    public String getContactLastName() {
        return contactLastName;
    }

    public void setContactLastName(String contactLastName) {
        this.contactLastName = contactLastName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
