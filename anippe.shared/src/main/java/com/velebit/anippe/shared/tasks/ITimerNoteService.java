package com.velebit.anippe.shared.tasks;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface ITimerNoteService extends IService {
    TimerNoteFormData prepareCreate(TimerNoteFormData formData);

    TimerNoteFormData create(TimerNoteFormData formData);

    TimerNoteFormData load(TimerNoteFormData formData);

    TimerNoteFormData store(TimerNoteFormData formData);
}
