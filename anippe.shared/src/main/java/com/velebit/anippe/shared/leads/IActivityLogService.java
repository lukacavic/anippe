package com.velebit.anippe.shared.leads;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface IActivityLogService extends IService {
    ActivityLogFormData prepareCreate(ActivityLogFormData formData);

    ActivityLogFormData create(ActivityLogFormData formData);

    ActivityLogFormData load(ActivityLogFormData formData);

    ActivityLogFormData store(ActivityLogFormData formData);
}
