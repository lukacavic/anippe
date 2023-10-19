package com.velebit.anippe.server.reminders;

import com.velebit.anippe.shared.reminders.AbstractRemindersGroupBoxData.RemindersTable.RemindersTableRowData;
import com.velebit.anippe.shared.reminders.IRemindersService;
import com.velebit.anippe.shared.reminders.Reminder;
import com.velebit.anippe.shared.reminders.ReminderRequest;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;

import java.util.ArrayList;
import java.util.List;

public class RemindersService implements IRemindersService {

    @Override
    public List<RemindersTableRowData> fetchReminders(Integer relatedId, Integer relatedType) {
        ReminderRequest request = new ReminderRequest();
        request.setRelatedId(relatedId);
        request.setRelatedType(relatedType);

        List<Reminder> reminders = BEANS.get(ReminderDao.class).getByRequest(request);

        List<RemindersTableRowData> rowData = new ArrayList<>();

        if (CollectionUtility.isEmpty(reminders))
            return rowData;

        for (Reminder reminder : reminders) {
            RemindersTableRowData row = new RemindersTableRowData();
            row.setReminder(reminder);
            row.setRemindAt(reminder.getRemindAt());
            row.setCreatedAt(reminder.getCreatedAt());
            row.setUserCreated(reminder.getUserCreated().getFullName());
            row.setTitle(reminder.getTitle());
            row.setUser(reminder.getUser().getFullName());
            row.setContent(reminder.getContent());
            row.setSendEmail(reminder.isSendEmail());
            rowData.add(row);
        }

        return rowData;
    }
}
