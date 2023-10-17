package com.velebit.anippe.shared.notes;

import com.velebit.anippe.shared.attachments.Attachment;
import org.eclipse.scout.rt.platform.util.CollectionUtility;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Note implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer relatedId;
    private String related;
    private Integer relatedTypeId;
    private String relatedType;
    private String note;
    private boolean priority;
    private Integer userId;
    private String user;
    private String title;
    private Date createdAt;
    private List<Attachment> attachments = new ArrayList<>();

    public boolean isPriority() {
        return priority;
    }

    public void setPriority(boolean priority) {
        this.priority = priority;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

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

    public Integer getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(Integer relatedId) {
        this.relatedId = relatedId;
    }

    public String getRelated() {
        return related;
    }

    public void setRelated(String related) {
        this.related = related;
    }

    public Integer getRelatedTypeId() {
        return relatedTypeId;
    }

    public void setRelatedTypeId(Integer relatedTypeId) {
        this.relatedTypeId = relatedTypeId;
    }

    public String getRelatedType() {
        return relatedType;
    }

    public void setRelatedType(String relatedType) {
        this.relatedType = relatedType;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean hasAttachment() {
        return !CollectionUtility.isEmpty(attachments);
    }

}
