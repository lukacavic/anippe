package com.velebit.anippe.shared.projects;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface IPredefinedReplyService extends IService {
    PredefinedReplyFormData prepareCreate(PredefinedReplyFormData formData);

    PredefinedReplyFormData create(PredefinedReplyFormData formData);

    PredefinedReplyFormData load(PredefinedReplyFormData formData);

    PredefinedReplyFormData store(PredefinedReplyFormData formData);
}
