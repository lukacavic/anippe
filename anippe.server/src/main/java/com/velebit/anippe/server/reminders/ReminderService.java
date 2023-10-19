package com.velebit.anippe.server.reminders;

import com.velebit.anippe.server.AbstractService;
import com.velebit.anippe.shared.reminders.IReminderService;
import com.velebit.anippe.shared.reminders.Reminder;
import com.velebit.anippe.shared.reminders.ReminderFormData;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.holders.StringHolder;
import org.eclipse.scout.rt.server.jdbc.SQL;

import java.util.Date;
import java.util.List;

public class ReminderService extends AbstractService implements IReminderService {
    @Override
    public ReminderFormData prepareCreate(ReminderFormData formData) {
        return formData;
    }

    @Override
    public ReminderFormData create(ReminderFormData formData) {

        StringBuffer varname1 = new StringBuffer();
        varname1.append("INSERT INTO reminders ");
        varname1.append("            ( ");
        varname1.append("                        created_at, ");
        varname1.append("                        organisation_id, ");
        varname1.append("                        user_created_id, ");
        varname1.append("                        user_id, ");
        varname1.append("                        send_email, ");
        varname1.append("                        title, ");
        varname1.append("                        content, ");
        varname1.append("                        remind_at, ");
        varname1.append("                        related_type, ");
        varname1.append("                        related_id ");
        varname1.append("            ) ");
        varname1.append("            VALUES ");
        varname1.append("            ( ");
        varname1.append("                        now(), ");
        varname1.append("                        :organisationId, ");
        varname1.append("                        :userId, ");
        varname1.append("                        :User, ");
        varname1.append("                        :SendEmailForReminder, ");
        varname1.append("                        :Title, ");
        varname1.append("                        :Content, ");
        varname1.append("                        :RemindAt, ");
        varname1.append("                        :relatedType, ");
        varname1.append("                        :relatedId ");
        varname1.append("            )");
        SQL.insert(varname1.toString(), formData, new NVPair("organisationId", getCurrentOrganisationId()), new NVPair("userId", getCurrentUserId()));

        return formData;
    }

    @Override
    public ReminderFormData load(ReminderFormData formData) {
        String stmt = "SELECT r.remind_at, r.title, r.content, r.user_id, r.user_created_id FROM reminders r WHERE r.id = :reminderId INTO :RemindAt, :Title, :Content, :User, :UserCreated";
        SQL.selectInto(stmt, formData);

        return formData;
    }

    @Override
    public ReminderFormData store(ReminderFormData formData) {
        String stmt = "UPDATE reminders SET title = :Title, content = :Content, remind_at = :RemindAt, updated_at = now(), user_id = :User, send_email = :SendEmailForReminder WHERE id = :reminderId";
        SQL.update(stmt, formData);

        return formData;
    }

    @Override
    public void delete(List<Integer> reminderIds) {
        for (Integer reminderId : reminderIds) {
            BEANS.get(ReminderDao.class).delete(reminderId);
        }
    }

    @Override
    public Reminder findReminderToShow() {
        Reminder reminder = new Reminder();

        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT   r.id, ");
        varname1.append("         r.remind_at, ");
        varname1.append("         r.title, ");
        varname1.append("         r.content, ");
        varname1.append("         r.send_email ");
        varname1.append("FROM     reminders r ");
        varname1.append("WHERE    remind_at < now() - INTERVAL '1 MINUTE' ");
        varname1.append("AND      deleted_at IS NULL ");
        varname1.append("AND      notified_at IS NULL ");
        varname1.append("AND      user_id = :userId ");
        varname1.append("ORDER BY remind_at DESC LIMIT 1 ");
        varname1.append("INTO     :{reminder.id}, ");
        varname1.append("         :{reminder.remindAt}, ");
        varname1.append("         :{reminder.title}, ");
        varname1.append("         :{reminder.content}, ");
        varname1.append("         :{reminder.sendEmail} ");
        SQL.selectInto(varname1.toString(), new NVPair("reminder", reminder), new NVPair("userId", getCurrentUserId()));

        if (reminder.getId() != null && reminder.isSendEmail()) {
            sendEmailForReminder(reminder);
        }

        return reminder.getId() != null ? reminder : null;
    }

    private void sendEmailForReminder(Reminder reminder) {
        //TODO:: Implement sending email
    }

    @Override
    public ReminderFormData loadView(ReminderFormData formData) {
        String stmt = "SELECT r.remind_at, r.title, r.content, r.user_id, r.user_created_id FROM reminders r WHERE r.id = :reminderId INTO :RemindAt, :Title, :Content, :User, :UserCreated";
        SQL.selectInto(stmt, formData);

        return formData;
    }

    @Override
    public void snoozeReminder(Integer reminderId, Date remindAt) {
        SQL.update("UPDATE reminders SET remind_at = :remindAt WHERE id = :reminderId", new NVPair("reminderId", reminderId), new NVPair("remindAt", remindAt));
    }

    @Override
    public void markAsNotified(Integer reminderId) {
        SQL.update("UPDATE reminders SET notified_at = now() WHERE id = :reminderId", new NVPair("reminderId", reminderId));
    }

    @Override
    public String findUserById(Long userId) {
        StringHolder holder = new StringHolder();

        SQL.selectInto("SELECT first_name || ' ' || last_name FROM users WHERE id = :userId INTO :holder", new NVPair("holder", holder), new NVPair("userId", userId));

        return holder.getValue();
    }
}
