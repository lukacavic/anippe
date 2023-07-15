package com.velebit.anippe.shared.documents;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface IUploadService extends IService {
    UploadFormData prepareCreate(UploadFormData formData);

    UploadFormData create(UploadFormData formData);

    UploadFormData load(UploadFormData formData);

    UploadFormData store(UploadFormData formData);
}
