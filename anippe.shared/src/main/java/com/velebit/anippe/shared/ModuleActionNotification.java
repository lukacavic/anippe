package com.velebit.anippe.shared;

public class ModuleActionNotification implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private Class<?> type;
    private Integer organisationId;
    private Object data;
    private Object source;
    private int changeStatus;
    private String dekstopNotificationContent;

    public ModuleActionNotification(Class<?> type, Object source, int changeStatus, Integer organisationId, Object data) {
        super();
        this.type = type;
        this.organisationId = organisationId;
        this.source = source;
        this.data = data;
        this.changeStatus = changeStatus;
    }

    public String getDekstopNotificationContent() {
        return dekstopNotificationContent;
    }

    public void setDekstopNotificationContent(String dekstopNotificationContent) {
        this.dekstopNotificationContent = dekstopNotificationContent;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public Object getSource() {
        return source;
    }

    public void setSource(Object source) {
        this.source = source;
    }

    public int getChangeStatus() {
        return changeStatus;
    }

    public void setChangeStatus(int changeStatus) {
        this.changeStatus = changeStatus;
    }


    public Integer getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(Integer organisationId) {
        this.organisationId = organisationId;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
