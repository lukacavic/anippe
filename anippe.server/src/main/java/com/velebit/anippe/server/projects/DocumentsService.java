package com.velebit.anippe.server.projects;

import com.velebit.anippe.shared.projects.*;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.security.ACCESS;

public class DocumentsService implements IDocumentsService {
    @Override
    public DocumentsFormData prepareCreate(DocumentsFormData formData) {
        return formData;
    }

    @Override
    public DocumentsFormData create(DocumentsFormData formData) {
        return formData;
    }

}
