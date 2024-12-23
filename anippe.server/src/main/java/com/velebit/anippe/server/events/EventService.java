package com.velebit.anippe.server.events;

import com.velebit.anippe.server.AbstractService;
import com.velebit.anippe.shared.events.EventFormData;
import com.velebit.anippe.shared.events.IEventService;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;

import java.util.Date;

public class EventService extends AbstractService implements IEventService {
    @Override
    public EventFormData prepareCreate(EventFormData formData) {
        return formData;
    }

    @Override
    public EventFormData create(EventFormData formData) {

        StringBuffer varname1 = new StringBuffer();
        varname1.append("INSERT INTO events ");
        varname1.append("            (NAME, ");
        varname1.append("             description, ");
        varname1.append("             full_day, ");
        varname1.append("             user_id, ");
        varname1.append("             organisation_id, ");
        varname1.append("             start_at, ");
        varname1.append("             ends_at, ");
        varname1.append("             public_event, ");
        varname1.append("             type_id) ");
        varname1.append("VALUES      (:Name, ");
        varname1.append("             :Description, ");
        varname1.append("             :FullDay, ");
        varname1.append("             :userId, ");
        varname1.append("             :organisationId, ");
        varname1.append("             :StartAt, ");
        varname1.append("             :EndAt, ");
        varname1.append("             :Public, ");
        varname1.append("             :Type)");
        SQL.insert(varname1.toString(), formData, new NVPair("userId", getCurrentUserId()), new NVPair("organisationId", getCurrentOrganisationId()));
        return formData;
    }

    @Override
    public EventFormData load(EventFormData formData) {
        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT name, ");
        varname1.append("       description, ");
        varname1.append("       full_day, ");
        varname1.append("       start_at, ");
        varname1.append("       ends_at, ");
        varname1.append("       public_event, ");
        varname1.append("       type_id ");
        varname1.append("FROM   events ");
        varname1.append("WHERE  id = :eventId ");
        varname1.append("INTO   :Name, :Description, :FullDay, :StartAt, :EndAt, :Public, :Type");
        SQL.selectInto(varname1.toString(), formData);

        return formData;
    }

    @Override
    public EventFormData store(EventFormData formData) {
        StringBuffer varname1 = new StringBuffer();
        varname1.append("UPDATE events ");
        varname1.append("SET    NAME = :Name, ");
        varname1.append("       description = :Description, ");
        varname1.append("       full_day = :FullDay, ");
        varname1.append("       start_at = :StartAt, ");
        varname1.append("       ends_at = :EndAt, ");
        varname1.append("       public_event = :Public, ");
        varname1.append("       type_id = :Type ");
        varname1.append("WHERE  id = :eventId");
        SQL.update(varname1.toString(), formData);

        return formData;
    }

    @Override
    public void delete(Integer itemId) {
        SQL.update("UPDATE events SET deleted_at = now() WHERE id = :eventId", new NVPair("eventId", itemId));
    }

    @Override
    public void updateEventDate(Integer itemId, Date fromDate, Date toDate) {
        StringBuffer varname1 = new StringBuffer();
        varname1.append("UPDATE events ");
        varname1.append("SET    start_at = :StartAt, ");
        varname1.append("       ends_at = :EndAt ");
        varname1.append("WHERE  id = :eventId");
        SQL.update(varname1.toString(), new NVPair("StartAt", fromDate), new NVPair("EndAt", toDate), new NVPair("eventId", itemId));
    }
}
