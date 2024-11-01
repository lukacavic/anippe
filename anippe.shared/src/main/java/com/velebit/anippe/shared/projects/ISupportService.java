package com.velebit.anippe.shared.projects;

import com.velebit.anippe.shared.projects.SupportFormData.TicketsTable.TicketsTableRowData;
import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

import java.util.List;

@TunnelToServer
public interface ISupportService extends IService {
    List<TicketsTableRowData> fetchTickets(Integer projectId, Integer clientId);

    SupportFormData prepareCreate(SupportFormData formData);

    SupportFormData create(SupportFormData formData);
}
