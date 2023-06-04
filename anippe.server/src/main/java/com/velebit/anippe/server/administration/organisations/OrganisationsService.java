package com.velebit.anippe.server.administration.organisations;

import com.velebit.anippe.shared.administration.organisations.IOrganisationsService;
import com.velebit.anippe.shared.administration.organisations.OrganisationsTablePageData;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

public class OrganisationsService implements IOrganisationsService {
    @Override
    public OrganisationsTablePageData getOrganisationsTableData(SearchFilter filter) {
        OrganisationsTablePageData pageData = new OrganisationsTablePageData();
// TODO [lukacavic] fill pageData.
        return pageData;
    }
}
