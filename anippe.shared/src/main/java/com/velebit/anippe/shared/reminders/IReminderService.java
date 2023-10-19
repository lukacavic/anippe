package com.velebit.anippe.shared.reminders;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

import java.util.Date;
import java.util.List;

@TunnelToServer
public interface IReminderService extends IService {
    ReminderFormData prepareCreate(ReminderFormData formData);

    ReminderFormData create(ReminderFormData formData);

    ReminderFormData load(ReminderFormData formData);

    ReminderFormData store(ReminderFormData formData);

    void delete(List<Integer> reminderIds);

    Reminder findReminderToShow();

    ReminderFormData loadView(ReminderFormData formData);

    void snoozeReminder(Integer reminderId, Date remindAt);

    void markAsNotified(Integer id);

    String findUserById(Long userId);
}
