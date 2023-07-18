package com.velebit.anippe.shared.organisations;

public class OrganisationSetting implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private EmailSettings emailSettings = new EmailSettings();
    private LocalizationSettings localizationSettings = new LocalizationSettings();

    public LocalizationSettings getLocalizationSettings() {
        return localizationSettings;
    }

    public void setLocalizationSettings(LocalizationSettings localizationSettings) {
        this.localizationSettings = localizationSettings;
    }

    public EmailSettings getEmailSettings() {
        return emailSettings;
    }

    public void setEmailSettings(EmailSettings emailSettings) {
        this.emailSettings = emailSettings;
    }
}
