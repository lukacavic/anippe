package com.velebit.anippe.shared.tasks;

import com.velebit.anippe.shared.attachments.Attachment;
import com.velebit.anippe.shared.beans.User;
import org.eclipse.scout.rt.platform.util.CollectionUtility;

import java.util.Date;
import java.util.List;

public class TaskActivityLog implements java.io.Serializable {
    private Integer id;
    private User user;
    private Integer taskId;
    private String content;
    private Date createdAt;
    private List<Attachment> attachments = CollectionUtility.emptyArrayList();

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
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
}
