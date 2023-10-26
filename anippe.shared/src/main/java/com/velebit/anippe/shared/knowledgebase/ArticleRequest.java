package com.velebit.anippe.shared.knowledgebase;

import com.velebit.anippe.shared.AbstractRequest;

public class ArticleRequest extends AbstractRequest {

    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
