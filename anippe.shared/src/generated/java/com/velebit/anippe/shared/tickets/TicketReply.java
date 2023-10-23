package com.velebit.anippe.shared.tickets;

import com.velebit.anippe.shared.beans.User;
import com.velebit.anippe.shared.clients.Contact;

import java.util.Date;

public class TicketReply implements java.io.Serializable {
    private Integer id;
    private Ticket ticket;
    private String content;
    private Date createdAt;
    private Contact contact;
    private User user;

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
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


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
