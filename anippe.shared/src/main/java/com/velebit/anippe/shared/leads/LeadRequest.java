package com.velebit.anippe.shared.leads;

import com.velebit.anippe.shared.AbstractRequest;

public class LeadRequest extends AbstractRequest {

    private Long statusId;
    private Long sourceId;
    private Long assignedUserId;

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public Long getAssignedUserId() {
        return assignedUserId;
    }

    public void setAssignedUserId(Long assignedUserId) {
        this.assignedUserId = assignedUserId;
    }
}
