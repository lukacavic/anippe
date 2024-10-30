package com.velebit.anippe.server.leads;

import java.util.Date;

public class LeadDto {
    private Integer id;
    private Integer clientId;
    private String name;
    private String company;
    private String description;
    private String address;
    private String city;
    private String postalCode;
    private Integer countryId;
    private String countryName;
    private Integer statusId;
    private String statusName;
    private Integer sourceId;
    private String sourceName;
    private Date createdAt;
    private String website;
    private String phone;
    private String email;
    private boolean lost;
    private boolean junk;
    private Date lastContactAt;
    private Integer projectId;
    private String position;
    private Integer assignedUserId;
    private String assignedUserFirstName;
    private String assignedUserLastName;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Date getLastContactAt() {
        return lastContactAt;
    }

    public void setLastContactAt(Date lastContactAt) {
        this.lastContactAt = lastContactAt;
    }

    public Integer getAssignedUserId() {
        return assignedUserId;
    }

    public void setAssignedUserId(Integer assignedUserId) {
        this.assignedUserId = assignedUserId;
    }

    public String getAssignedUserFirstName() {
        return assignedUserFirstName;
    }

    public void setAssignedUserFirstName(String assignedUserFirstName) {
        this.assignedUserFirstName = assignedUserFirstName;
    }

    public String getAssignedUserLastName() {
        return assignedUserLastName;
    }

    public void setAssignedUserLastName(String assignedUserLastName) {
        this.assignedUserLastName = assignedUserLastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isLost() {
        return lost;
    }

    public void setLost(boolean lost) {
        this.lost = lost;
    }

    public boolean isJunk() {
        return junk;
    }

    public void setJunk(boolean junk) {
        this.junk = junk;
    }
}
