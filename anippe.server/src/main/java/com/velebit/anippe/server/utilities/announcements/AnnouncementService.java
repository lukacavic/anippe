package com.velebit.anippe.server.utilities.announcements;

import com.velebit.anippe.server.AbstractService;
import com.velebit.anippe.shared.utilities.announcements.Announcement;
import com.velebit.anippe.shared.utilities.announcements.AnnouncementFormData;
import com.velebit.anippe.shared.utilities.announcements.IAnnouncementService;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.util.ChangeStatus;
import org.eclipse.scout.rt.server.jdbc.SQL;

public class AnnouncementService extends AbstractService implements IAnnouncementService {
    @Override
    public AnnouncementFormData prepareCreate(AnnouncementFormData formData) {
        return formData;
    }

    @Override
    public AnnouncementFormData create(AnnouncementFormData formData) {

        StringBuffer varname1 = new StringBuffer();
        varname1.append("INSERT INTO announcements ");
        varname1.append("            (subject, ");
        varname1.append("             content, ");
        varname1.append("             user_id, ");
        varname1.append("             organisation_id) ");
        varname1.append("VALUES      (:Subject, ");
        varname1.append("             :Content, ");
        varname1.append("             :userId, ");
        varname1.append("             :organisationId) ");
        varname1.append("RETURNING id INTO :announcementId");
        SQL.selectInto(varname1.toString(), formData,
                new NVPair("organisationId", getCurrentOrganisationId()),
                new NVPair("userId", getCurrentUserId())
        );

        Announcement announcement = new Announcement();
        announcement.setId(formData.getAnnouncementId());
        emitModuleEvent(Announcement.class, announcement, ChangeStatus.INSERTED);

        return formData;
    }

    @Override
    public AnnouncementFormData load(AnnouncementFormData formData) {

        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT subject, ");
        varname1.append("       content ");
        varname1.append("FROM   announcements ");
        varname1.append("WHERE  id = :announcementId ");
        varname1.append("INTO   :Subject, :Content");
        SQL.selectInto(varname1.toString(), formData);

        return formData;
    }

    @Override
    public AnnouncementFormData store(AnnouncementFormData formData) {

        StringBuffer varname1 = new StringBuffer();
        varname1.append("UPDATE announcements ");
        varname1.append("SET    subject = :Subject, ");
        varname1.append("       content = :Content ");
        varname1.append("WHERE  id = :announcementId");
        SQL.update(varname1.toString(), formData);

        return formData;
    }

    @Override
    public AnnouncementFormData preview(AnnouncementFormData formData) {
        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT subject, ");
        varname1.append("       content ");
        varname1.append("FROM   announcements ");
        varname1.append("WHERE  id = :announcementId ");
        varname1.append("INTO   :Subject, :Content ");
        SQL.selectInto(varname1.toString(), formData);

        return formData;
    }

    @Override
    public void markAsRead(Integer announcementId) {
        String stmt = "INSERT INTO link_dismissed_announcements (announcement_id, user_id, created_at) VALUES (:announcementId, :userId, now())";
        SQL.insert(stmt, new NVPair("announcementId", announcementId), new NVPair("userId", getCurrentUserId()));
    }
}
