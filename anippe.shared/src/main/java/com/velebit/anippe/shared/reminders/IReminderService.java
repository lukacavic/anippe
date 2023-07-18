package com.velebit.anippe.shared.reminders;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface IReminderService extends IService {
    ReminderFormData prepareCreate(ReminderFormData formData);

    ReminderFormData create(ReminderFormData formData);

    ReminderFormData load(ReminderFormData formData);

    ReminderFormData store(ReminderFormData formData);
}
