package com.velebit.anippe.server.tasks;

import com.velebit.anippe.shared.tasks.ITimerNoteService;
import com.velebit.anippe.shared.tasks.TimerNoteFormData;
import org.eclipse.scout.rt.server.jdbc.SQL;

public class TimerNoteService implements ITimerNoteService {
    @Override
    public TimerNoteFormData prepareCreate(TimerNoteFormData formData) {
        return formData;
    }

    @Override
    public TimerNoteFormData create(TimerNoteFormData formData) {
        SQL.update("UPDATE task_timers SET note = :note WHERE id = :taskTimerId", formData);

        return formData;
    }

    @Override
    public TimerNoteFormData load(TimerNoteFormData formData) {
        return formData;
    }

    @Override
    public TimerNoteFormData store(TimerNoteFormData formData) {
        return formData;
    }
}
