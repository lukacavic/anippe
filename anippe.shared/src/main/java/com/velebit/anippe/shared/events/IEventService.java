package com.velebit.anippe.shared.events;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface IEventService extends IService {
    EventFormData prepareCreate(EventFormData formData);

    EventFormData create(EventFormData formData);

    EventFormData load(EventFormData formData);

    EventFormData store(EventFormData formData);

    void delete(Integer itemId);
}
