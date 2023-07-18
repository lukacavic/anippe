package com.velebit.anippe.server.email;

import com.velebit.anippe.shared.email.EmailFormData;
import com.velebit.anippe.shared.email.IEmailService;

public class EmailService implements IEmailService {
    @Override
    public EmailFormData prepareCreate(EmailFormData formData) {
        return formData;
    }

    @Override
    public EmailFormData create(EmailFormData formData) {
        return formData;
    }

    @Override
    public EmailFormData load(EmailFormData formData) {
        return formData;
    }

    @Override
    public EmailFormData store(EmailFormData formData) {
        return formData;
    }
}
