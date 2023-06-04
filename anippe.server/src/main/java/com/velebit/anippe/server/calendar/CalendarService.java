package com.velebit.anippe.server.calendar;

import com.velebit.anippe.shared.calendar.*;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.security.ACCESS;

public class CalendarService implements ICalendarService {
    @Override
    public CalendarFormData prepareCreate(CalendarFormData formData) {
        if (!ACCESS.check(new CreateCalendarPermission())) {
            throw new VetoException(TEXTS.get("AuthorizationFailed"));
        }
// TODO [lukacavic] add business logic here.
        return formData;
    }

    @Override
    public CalendarFormData create(CalendarFormData formData) {
        if (!ACCESS.check(new CreateCalendarPermission())) {
            throw new VetoException(TEXTS.get("AuthorizationFailed"));
        }
// TODO [lukacavic] add business logic here.
        return formData;
    }

    @Override
    public CalendarFormData load(CalendarFormData formData) {
        if (!ACCESS.check(new ReadCalendarPermission())) {
            throw new VetoException(TEXTS.get("AuthorizationFailed"));
        }
// TODO [lukacavic] add business logic here.
        return formData;
    }

    @Override
    public CalendarFormData store(CalendarFormData formData) {
        if (!ACCESS.check(new UpdateCalendarPermission())) {
            throw new VetoException(TEXTS.get("AuthorizationFailed"));
        }
// TODO [lukacavic] add business logic here.
        return formData;
    }
}
