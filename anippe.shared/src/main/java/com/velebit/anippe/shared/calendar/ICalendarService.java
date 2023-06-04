package com.velebit.anippe.shared.calendar;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface ICalendarService extends IService {
    CalendarFormData prepareCreate(CalendarFormData formData);

    CalendarFormData create(CalendarFormData formData);

    CalendarFormData load(CalendarFormData formData);

    CalendarFormData store(CalendarFormData formData);
}
