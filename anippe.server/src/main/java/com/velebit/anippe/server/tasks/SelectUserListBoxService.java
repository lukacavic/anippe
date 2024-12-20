package com.velebit.anippe.server.tasks;

import com.velebit.anippe.shared.tasks.*;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.security.ACCESS;

public class SelectUserListBoxService implements ISelectUserListBoxService {
    @Override
    public SelectUserListBoxFormData prepareCreate(SelectUserListBoxFormData formData) {
        if (!ACCESS.check(new CreateSelectUserListBoxPermission())) {
            throw new VetoException(TEXTS.get("AuthorizationFailed"));
        }
// TODO [lukacavic] add business logic here.
        return formData;
    }

    @Override
    public SelectUserListBoxFormData create(SelectUserListBoxFormData formData) {
        if (!ACCESS.check(new CreateSelectUserListBoxPermission())) {
            throw new VetoException(TEXTS.get("AuthorizationFailed"));
        }
// TODO [lukacavic] add business logic here.
        return formData;
    }

    @Override
    public SelectUserListBoxFormData load(SelectUserListBoxFormData formData) {
        if (!ACCESS.check(new ReadSelectUserListBoxPermission())) {
            throw new VetoException(TEXTS.get("AuthorizationFailed"));
        }
// TODO [lukacavic] add business logic here.
        return formData;
    }

    @Override
    public SelectUserListBoxFormData store(SelectUserListBoxFormData formData) {
        if (!ACCESS.check(new UpdateSelectUserListBoxPermission())) {
            throw new VetoException(TEXTS.get("AuthorizationFailed"));
        }
// TODO [lukacavic] add business logic here.
        return formData;
    }
}
