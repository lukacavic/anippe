package com.velebit.anippe.server.reminders;

import com.velebit.anippe.shared.reminders.IReminderService;
import com.velebit.anippe.shared.reminders.ReminderFormData;

public class ReminderService implements IReminderService {
    @Override
    public ReminderFormData prepareCreate(ReminderFormData formData) {
        return formData;
    }

    @Override
    public ReminderFormData create(ReminderFormData formData) {
        return formData;
    }

    @Override
    public ReminderFormData load(ReminderFormData formData) {
        return formData;
    }

    @Override
    public ReminderFormData store(ReminderFormData formData) {
        return formData;
    }
}
