package com.velebit.anippe.server.reminders;

import com.velebit.anippe.shared.reminders.IRemindersService;
import com.velebit.anippe.shared.reminders.RemindersFormData;

public class RemindersService implements IRemindersService {
    @Override
    public RemindersFormData prepareCreate(RemindersFormData formData) {
        return formData;
    }

    @Override
    public RemindersFormData create(RemindersFormData formData) {
        return formData;
    }

    @Override
    public RemindersFormData load(RemindersFormData formData) {
        return formData;
    }

    @Override
    public RemindersFormData store(RemindersFormData formData) {
        return formData;
    }
}
