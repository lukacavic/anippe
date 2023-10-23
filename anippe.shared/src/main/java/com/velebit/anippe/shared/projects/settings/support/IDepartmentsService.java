package com.velebit.anippe.shared.projects.settings.support;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@TunnelToServer
public interface IDepartmentsService extends IService {
    DepartmentsTablePageData getDepartmentsTableData(SearchFilter filter, Integer projectId);
}
