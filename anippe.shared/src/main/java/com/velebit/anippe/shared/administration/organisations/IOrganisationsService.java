package com.velebit.anippe.shared.administration.organisations;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@TunnelToServer
public interface IOrganisationsService extends IService {
    OrganisationsTablePageData getOrganisationsTableData(SearchFilter filter);
}
