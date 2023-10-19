package com.velebit.anippe.server.documents;

import com.velebit.anippe.shared.documents.*;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.security.ACCESS;

public class UploadService implements IUploadService {
    @Override
    public UploadFormData prepareCreate(UploadFormData formData) {
        return formData;
    }

    @Override
    public UploadFormData create(UploadFormData formData) {
        return formData;
    }

    @Override
    public UploadFormData load(UploadFormData formData) {
        return formData;
    }

    @Override
    public UploadFormData store(UploadFormData formData) {
        return formData;
    }
}
