package com.velebit.anippe.server.documents;

import com.velebit.anippe.shared.documents.DocumentsFormData;
import com.velebit.anippe.shared.documents.IDocumentsService;

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
