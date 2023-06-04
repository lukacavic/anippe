package com.velebit.anippe.shared;

public class AbstractRequest implements java.io.Serializable{
    private Integer id;

    private Integer clientId;

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
