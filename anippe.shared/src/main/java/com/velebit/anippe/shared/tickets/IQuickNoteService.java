package com.velebit.anippe.shared.tickets;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface IQuickNoteService extends IService {
    QuickNoteFormData prepareCreate(QuickNoteFormData formData);

    QuickNoteFormData create(QuickNoteFormData formData);

    QuickNoteFormData load(QuickNoteFormData formData);

    QuickNoteFormData store(QuickNoteFormData formData);
}
