package com.velebit.anippe.shared.reminders;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface IRemindersService extends IService {
    RemindersFormData prepareCreate(RemindersFormData formData);

    RemindersFormData create(RemindersFormData formData);

    RemindersFormData load(RemindersFormData formData);

    RemindersFormData store(RemindersFormData formData);
}
