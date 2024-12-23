package com.velebit.anippe.shared;

import org.eclipse.scout.rt.platform.util.CollectionUtility;

import java.util.List;

public class AbstractRequest implements java.io.Serializable {
    private Integer id;

    private Integer clientId;
    private Integer relatedId;
    private Integer relatedType;
    private List<Integer> excludeIds = CollectionUtility.emptyArrayList();

    private Integer projectId;

    public AbstractRequest() {
    }

    public AbstractRequest(Integer relatedId, Integer relatedType) {
        this.relatedId = relatedId;
        this.relatedType = relatedType;
    }

    public List<Integer> getExcludeIds() {
        return excludeIds;
    }

    public void setExcludeIds(List<Integer> excludeIds) {
        this.excludeIds = excludeIds;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
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

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
