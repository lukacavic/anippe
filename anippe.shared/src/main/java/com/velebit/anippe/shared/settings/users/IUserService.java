package com.velebit.anippe.shared.settings.users;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface IUserService extends IService {
    UserFormData prepareCreate(UserFormData formData);

    UserFormData create(UserFormData formData);

    UserFormData load(UserFormData formData);

    UserFormData store(UserFormData formData);

    boolean isUsernameValid(String rawValue, Integer integer);

    void resetPassword(int userId);
}
