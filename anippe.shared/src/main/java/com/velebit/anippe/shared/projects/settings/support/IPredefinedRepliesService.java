package com.velebit.anippe.shared.projects.settings.support;

import com.velebit.anippe.shared.projects.settings.PredefinedRepliesTablePageData;
import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@TunnelToServer
public interface IPredefinedRepliesService extends IService {
    PredefinedRepliesTablePageData getPredefinedRepliesTableData(SearchFilter filter, Integer projectId);

    void delete(Integer predefinedReplyId);
}
