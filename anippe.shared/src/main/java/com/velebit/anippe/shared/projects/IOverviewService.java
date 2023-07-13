package com.velebit.anippe.shared.projects;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface IOverviewService extends IService {
    OverviewFormData prepareCreate(OverviewFormData formData);

    OverviewFormData create(OverviewFormData formData);

    OverviewFormData load(OverviewFormData formData);

    OverviewFormData store(OverviewFormData formData);
}
