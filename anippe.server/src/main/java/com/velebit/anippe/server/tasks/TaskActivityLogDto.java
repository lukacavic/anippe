package com.velebit.anippe.server.tasks;

import java.util.Date;

public class TaskActivityLogDto implements java.io.Serializable {
    private Integer id;
    private String content;
    private Date createdAt;
    private Integer userCreatedId;
    private String userCreatedFirstName;
    private String userCreatedLastName;
    private Integer taskId;

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
