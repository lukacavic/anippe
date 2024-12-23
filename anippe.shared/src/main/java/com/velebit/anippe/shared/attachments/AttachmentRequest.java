package com.velebit.anippe.shared.attachments;

import com.velebit.anippe.shared.AbstractRequest;

public class AttachmentRequest extends AbstractRequest {

    private static final long serialVersionUID = 1L;

    public AttachmentRequest() {
        super();
    }

    public AttachmentRequest(Integer relatedType, Integer relatedId) {
        this.setRelatedType(relatedType);
        this.setRelatedId(relatedId);
    }
}
