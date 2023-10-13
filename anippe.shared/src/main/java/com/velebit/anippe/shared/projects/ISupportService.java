package com.velebit.anippe.shared.projects;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface ISupportService extends IService {
    SupportFormData prepareCreate(SupportFormData formData);

    SupportFormData create(SupportFormData formData);
}
