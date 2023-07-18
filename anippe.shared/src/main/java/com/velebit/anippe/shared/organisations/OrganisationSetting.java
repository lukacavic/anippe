package com.velebit.anippe.shared.organisations;

public class OrganisationSetting implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private EmailSettings emailSettings = new EmailSettings();

    public EmailSettings getEmailSettings() {
        return emailSettings;
    }

    public void setEmailSettings(EmailSettings emailSettings) {
        this.emailSettings = emailSettings;
    }
}
