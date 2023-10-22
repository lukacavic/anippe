package com.velebit.anippe.shared.tickets;

public class TicketDepartment implements java.io.Serializable {

    private Integer id;
    private String name;
    private Integer projectId;
    private Integer organisationId;

    private boolean imapImportEnabled;
    private String imapImportHost;
    private String imapImportEmail;
    private String imapImportPassword;
    private String imapImportEncryption;
    private String imapImportFolder;
    private boolean imapImportDeletedAfter;

    public Integer getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(Integer organisationId) {
        this.organisationId = organisationId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isImapImportEnabled() {
        return imapImportEnabled;
    }

    public void setImapImportEnabled(boolean imapImportEnabled) {
        this.imapImportEnabled = imapImportEnabled;
    }

    public String getImapImportHost() {
        return imapImportHost;
    }

    public void setImapImportHost(String imapImportHost) {
        this.imapImportHost = imapImportHost;
    }

    public String getImapImportEmail() {
        return imapImportEmail;
    }

    public void setImapImportEmail(String imapImportEmail) {
        this.imapImportEmail = imapImportEmail;
    }

    public String getImapImportPassword() {
        return imapImportPassword;
    }

    public void setImapImportPassword(String imapImportPassword) {
        this.imapImportPassword = imapImportPassword;
    }

    public String getImapImportEncryption() {
        return imapImportEncryption;
    }

    public void setImapImportEncryption(String imapImportEncryption) {
        this.imapImportEncryption = imapImportEncryption;
    }

    public String getImapImportFolder() {
        return imapImportFolder;
    }

    public void setImapImportFolder(String imapImportFolder) {
        this.imapImportFolder = imapImportFolder;
    }

    public boolean isImapImportDeletedAfter() {
        return imapImportDeletedAfter;
    }

    public void setImapImportDeletedAfter(boolean imapImportDeletedAfter) {
        this.imapImportDeletedAfter = imapImportDeletedAfter;
    }

    public boolean isSslEnabled() {
        return imapImportEncryption != null && (imapImportEncryption.equalsIgnoreCase("SSL") || imapImportEncryption.equalsIgnoreCase("TLS"));
    }
}
