package com.velebit.anippe.shared.projects;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

import java.util.Map;

@TunnelToServer
public interface IOverviewService extends IService {

    Map<String, Integer> fetchLeadsByStatus(Integer projectId);

    Map<String, Integer> fetchTicketsByStatus(Integer projectId);

    Map<String, Integer> fetchTicketsByAssignedUser(Integer projectId);

    Map<String, Integer> fetchTicketsByMonth(Integer projectId);
}
