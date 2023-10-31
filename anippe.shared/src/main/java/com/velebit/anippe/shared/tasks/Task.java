package com.velebit.anippe.shared.tasks;

import com.velebit.anippe.shared.beans.User;
import com.velebit.anippe.shared.constants.Constants;
import org.eclipse.scout.rt.platform.util.CollectionUtility;

import java.util.Date;
import java.util.List;

public class Task implements java.io.Serializable{
    private Integer id;
    private User creator;
    private Date createdAt;
    private Date startAt;
    private Date deadlineAt;
    private Integer statusId;
    private String title;
    private String description;
    private List<Task> childTasks = CollectionUtility.emptyArrayList();
    private List<User> assignedUsers = CollectionUtility.emptyArrayList();
    private Integer priorityId;
    private Integer relatedType;
    private Integer relatedId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
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

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Task> getChildTasks() {
        return childTasks;
    }

    public void setChildTasks(List<Task> childTasks) {
        this.childTasks = childTasks;
    }

    public List<User> getAssignedUsers() {
        return assignedUsers;
    }

    public void setAssignedUsers(List<User> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }

    public Integer getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(Integer priorityId) {
        this.priorityId = priorityId;
    }

    public Integer getRelatedType() {
        return relatedType;
    }

    public void setRelatedType(Integer relatedType) {
        this.relatedType = relatedType;
    }

    public Integer getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(Integer relatedId) {
        this.relatedId = relatedId;
    }

    public boolean isCompleted() {
        return statusId.equals(Constants.TaskStatus.COMPLETED);
    }

    public boolean isOverdue() {
        return !isCompleted() && (getDeadlineAt() != null && getDeadlineAt().before(new Date()));
    }

    public boolean isUserAssigned(Integer userId) {
        if (getAssignedUsers().isEmpty()) return false;

        return getAssignedUsers().stream().anyMatch(u -> u.getId().equals(userId));
    }
}
