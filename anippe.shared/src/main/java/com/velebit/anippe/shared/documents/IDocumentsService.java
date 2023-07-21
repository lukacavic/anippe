package com.velebit.anippe.shared.documents;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface IDocumentsService extends IService {
    DocumentsFormData prepareCreate(DocumentsFormData formData);

    DocumentsFormData create(DocumentsFormData formData);

}
