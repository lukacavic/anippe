package com.velebit.anippe.shared.tickets;

import com.velebit.anippe.shared.beans.User;
import com.velebit.anippe.shared.clients.Contact;
import org.eclipse.scout.rt.platform.util.CollectionUtility;

import java.util.Date;
import java.util.List;

public class Ticket implements java.io.Serializable {
    private Integer id;
    private String subject;
    private Contact contact;
    private Date createdAt;
    private TicketDepartment ticketDepartment;
    private List<TicketReply> replies = CollectionUtility.emptyArrayList();
    private Integer priorityId;
    private Integer statusId;
    private Date lastReply;
    private User assignedUser;
    private String code;

    public TicketDepartment getTicketDepartment() {
        return ticketDepartment;
    }

    public void setTicketDepartment(TicketDepartment ticketDepartment) {
        this.ticketDepartment = ticketDepartment;
    }

    public User getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(User assignedUser) {
        this.assignedUser = assignedUser;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getLastReply() {
        return lastReply;
    }

    public void setLastReply(Date lastReply) {
        this.lastReply = lastReply;
    }

    public Integer getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(Integer priorityId) {
        this.priorityId = priorityId;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public List<TicketReply> getReplies() {
        return replies;
    }

    public void setReplies(List<TicketReply> replies) {
        this.replies = replies;
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
}
