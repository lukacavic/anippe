package com.velebit.anippe.shared.projects;

import com.velebit.anippe.shared.beans.User;
import com.velebit.anippe.shared.clients.Client;
import org.eclipse.scout.rt.platform.util.CollectionUtility;

import java.util.Date;
import java.util.List;

public class Project implements java.io.Serializable {
    private Integer id;
    private String name;
    private Client client;
    private Date createdAt;
    private Date startAt;
    private Date deadlineAt;
    private Integer typeId;
    private List<User> members = CollectionUtility.emptyArrayList();

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getStartAt() {
        return startAt;
    }

    public void setStartAt(Date startAt) {
        this.startAt = startAt;
    }

    public Date getDeadlineAt() {
        return deadlineAt;
    }

    public void setDeadlineAt(Date deadlineAt) {
        this.deadlineAt = deadlineAt;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
