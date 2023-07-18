package com.velebit.anippe.shared.organisations;

public class EmailSettings implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    protected Integer organisationId;
    protected String smtpServer;
    protected String smtpUsername;
    protected Integer smtpPort;
    protected String smtpPassword;
    protected Long smtpProtocol;
    protected String smtpEmail;
    protected String globalBcc;

    public Integer getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(Integer organisationId) {
        this.organisationId = organisationId;
    }

    public String getGlobalBcc() {
        return globalBcc;
    }

    public void setGlobalBcc(String globalBcc) {
        this.globalBcc = globalBcc;
    }

    public String getSmtpServer() {
        return smtpServer;
    }

    public void setSmtpServer(String smtpServer) {
        this.smtpServer = smtpServer;
    }

    public String getSmtpUsername() {
        return smtpUsername;
    }

    public void setSmtpUsername(String smtpUsername) {
        this.smtpUsername = smtpUsername;
    }

    public Integer getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(Integer smtpPort) {
        this.smtpPort = smtpPort;
    }

    public String getSmtpPassword() {
        return smtpPassword;
    }

    public void setSmtpPassword(String smtpPassword) {
        this.smtpPassword = smtpPassword;
    }

    public Long getSmtpProtocol() {
        return smtpProtocol;
    }

    public void setSmtpProtocol(Long smtpProtocol) {
        this.smtpProtocol = smtpProtocol;
    }

    public String getSmtpEmail() {
        return smtpEmail;
    }

    public void setSmtpEmail(String smtpEmail) {
        this.smtpEmail = smtpEmail;
    }

    public boolean isEmailSettingsValid() {
        return this.smtpEmail != null && this.smtpPassword != null && this.smtpPort != null && this.smtpProtocol != null && this.smtpUsername != null && this.smtpServer != null;
    }
}
