package com.velebit.anippe.server.tickets;

import com.velebit.anippe.shared.tickets.*;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.security.ACCESS;

public class QuickNoteService implements IQuickNoteService {
    @Override
    public QuickNoteFormData prepareCreate(QuickNoteFormData formData) {
        return formData;
    }

    @Override
    public QuickNoteFormData create(QuickNoteFormData formData) {
        return formData;
    }

    @Override
    public QuickNoteFormData load(QuickNoteFormData formData) {
        return formData;
    }

    @Override
    public QuickNoteFormData store(QuickNoteFormData formData) {
        return formData;
    }
}
