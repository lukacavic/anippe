package com.velebit.anippe.shared.tickets;

import com.velebit.anippe.shared.AbstractRequest;
import com.velebit.anippe.shared.constants.Constants.Priority;
import com.velebit.anippe.shared.constants.Constants.TicketStatus;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.platform.util.CollectionUtility;

import java.util.List;

public class TicketRequest extends AbstractRequest {

    private String code;
    private String subject;
    private String content;
    private Integer statusId = TicketStatus.CREATED;
    private Integer priorityId = Priority.NORMAL;
    private Integer projectId;
    private Integer contactId;
    private Integer userId;
    private Integer departmentId;
    private List<BinaryResource> attachments = CollectionUtility.emptyArrayList();

    public TicketRequest(Integer ticketId) {
        super();
        setId(ticketId);
    }

    public TicketRequest() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSubject() {
        return subject;
    }

    public List<BinaryResource> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<BinaryResource> attachments) {
        this.attachments = attachments;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    @Override
    public Integer getProjectId() {
        return projectId;
    }

    @Override
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }
}
