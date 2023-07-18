package com.velebit.anippe.server.projects;

import com.velebit.anippe.shared.projects.*;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.security.ACCESS;

public class SupportService implements ISupportService {
    @Override
    public SupportFormData loadClients(SupportFormData formData) {
        return formData;
    }
}
