package com.velebit.anippe.shared.utilities.announcements;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@TunnelToServer
public interface IAnnouncementsService extends IService {
    AnnouncementsTablePageData getAnnouncementsTableData(SearchFilter filter);

    void delete(Integer announcementId);
}
