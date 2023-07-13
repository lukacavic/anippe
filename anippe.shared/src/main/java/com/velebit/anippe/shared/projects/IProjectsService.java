package com.velebit.anippe.shared.projects;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import java.util.List;

@TunnelToServer
public interface IProjectsService extends IService {
    ProjectsTablePageData getProjectsTableData(SearchFilter filter);

    List<Project> findForUser(Integer userId);
}
