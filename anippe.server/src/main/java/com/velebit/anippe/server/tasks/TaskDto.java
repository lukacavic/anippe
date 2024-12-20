package com.velebit.anippe.server.tasks;

import com.velebit.anippe.server.settings.users.UserDto;
import org.eclipse.scout.rt.platform.util.CollectionUtility;

import java.util.Date;
import java.util.List;

public class TaskDto {
    private Integer id;
    private String name;
    private String description;
    private Integer userCreatedId;
    private String userCreatedFirstName;
    private String userCreatedLastName;
    private Date startAt;
    private Date deadlineAt;
    private Date completedAt;
    private Integer priorityId;
    private Integer statusId;
    private Integer relatedId;
    private Integer relatedType;
    private Date archivedAt;
    private Integer attachmentsCount;

    // Assigned users
    private List<UserDto> assignedUsers = CollectionUtility.emptyArrayList();

    public Integer getAttachmentsCount() {
        return attachmentsCount;
    }

    public void setAttachmentsCount(Integer attachmentsCount) {
        this.attachmentsCount = attachmentsCount;
    }

    public List<UserDto> getAssignedUsers() {
        return assignedUsers;
    }

    public void setAssignedUsers(List<UserDto> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }

    public Date getArchivedAt() {
        return archivedAt;
    }

    public void setArchivedAt(Date archivedAt) {
        this.archivedAt = archivedAt;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Date getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
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
}
