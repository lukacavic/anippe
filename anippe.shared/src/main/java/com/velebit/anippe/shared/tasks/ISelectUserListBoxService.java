package com.velebit.anippe.shared.tasks;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface ISelectUserListBoxService extends IService {
    SelectUserListBoxFormData prepareCreate(SelectUserListBoxFormData formData);

    SelectUserListBoxFormData create(SelectUserListBoxFormData formData);

    SelectUserListBoxFormData load(SelectUserListBoxFormData formData);

    SelectUserListBoxFormData store(SelectUserListBoxFormData formData);
}
