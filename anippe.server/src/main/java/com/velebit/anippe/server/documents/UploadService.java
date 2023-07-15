package com.velebit.anippe.server.documents;

import com.velebit.anippe.shared.documents.*;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.security.ACCESS;

public class UploadService implements IUploadService {
    @Override
    public UploadFormData prepareCreate(UploadFormData formData) {
        if (!ACCESS.check(new CreateUploadPermission())) {
            throw new VetoException(TEXTS.get("AuthorizationFailed"));
        }
// TODO [lukacavic] add business logic here.
        return formData;
    }

    @Override
    public UploadFormData create(UploadFormData formData) {
        if (!ACCESS.check(new CreateUploadPermission())) {
            throw new VetoException(TEXTS.get("AuthorizationFailed"));
        }
// TODO [lukacavic] add business logic here.
        return formData;
    }

    @Override
    public UploadFormData load(UploadFormData formData) {
        if (!ACCESS.check(new ReadUploadPermission())) {
            throw new VetoException(TEXTS.get("AuthorizationFailed"));
        }
// TODO [lukacavic] add business logic here.
        return formData;
    }

    @Override
    public UploadFormData store(UploadFormData formData) {
        if (!ACCESS.check(new UpdateUploadPermission())) {
            throw new VetoException(TEXTS.get("AuthorizationFailed"));
        }
// TODO [lukacavic] add business logic here.
        return formData;
    }
}
