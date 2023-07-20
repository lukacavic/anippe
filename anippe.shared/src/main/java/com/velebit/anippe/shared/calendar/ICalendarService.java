package com.velebit.anippe.shared.calendar;

import com.velebit.anippe.shared.events.Event;
import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

import java.util.Date;
import java.util.List;

@TunnelToServer
public interface ICalendarService extends IService {
    List<Event> fetchEvents(Date minDate, Date maxDate);
}
