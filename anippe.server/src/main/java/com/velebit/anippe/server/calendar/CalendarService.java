package com.velebit.anippe.server.calendar;

import com.velebit.anippe.server.AbstractService;
import com.velebit.anippe.shared.calendar.ICalendarService;
import com.velebit.anippe.shared.events.Event;
import org.eclipse.scout.rt.platform.holders.BeanArrayHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;

import java.util.Date;
import java.util.List;

public class CalendarService extends AbstractService implements ICalendarService {

    @Override
    public List<Event> fetchEvents(Date minDate, Date maxDate) {
        BeanArrayHolder<Event> holder = new BeanArrayHolder<>(Event.class);

        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT e.id, ");
        varname1.append("       e.NAME, ");
        varname1.append("       e.description, ");
        varname1.append("       e.start_at, ");
        varname1.append("       e.ends_at ");
        varname1.append("FROM   events e, ");
        varname1.append("       users u ");
        varname1.append("WHERE  e.user_id = u.id ");
        varname1.append("AND    e.organisation_id = :organisationId ");
        varname1.append("AND    e.deleted_at IS NULL ");
        varname1.append("AND    (e.user_id = :userId OR e.public_event IS TRUE ) ");
        varname1.append("AND    e.start_at >= :startDate ");
        varname1.append("AND    e.ends_at <= :endDate ");
        varname1.append("into   :{holder.id}, ");
        varname1.append("       :{holder.name}, ");
        varname1.append("       :{holder.description}, ");
        varname1.append("       :{holder.startAt}, ");
        varname1.append("       :{holder.endsAt} ");
        SQL.selectInto(varname1.toString(),
                new NVPair("holder", holder),
                new NVPair("userId", getCurrentUserId()),
                new NVPair("organisationId", getCurrentOrganisationId()),
                new NVPair("startDate", minDate),
                new NVPair("endDate", maxDate));

        return CollectionUtility.arrayList(holder.getBeans());
    }
}
