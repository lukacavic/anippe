package com.velebit.anippe.shared.tickets;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface ITicketService extends IService {
    TicketFormData prepareCreate(TicketFormData formData);

    TicketFormData create(TicketFormData formData);

    TicketFormData load(TicketFormData formData);

    TicketFormData store(TicketFormData formData);
}
