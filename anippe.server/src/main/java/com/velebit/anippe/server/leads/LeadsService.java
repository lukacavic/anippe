package com.velebit.anippe.server.leads;

import com.velebit.anippe.shared.leads.ILeadsService;
import com.velebit.anippe.shared.leads.Lead;
import com.velebit.anippe.shared.leads.LeadRequest;
import com.velebit.anippe.shared.leads.LeadsTablePageData;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import java.util.List;
import java.util.Optional;

public class LeadsService implements ILeadsService {
	@Override
	public LeadsTablePageData getLeadsTableData(SearchFilter filter) {
		LeadsTablePageData pageData = new LeadsTablePageData();

		LeadRequest request = new LeadRequest();

		List<Lead> leads = BEANS.get(LeadDao.class).get(request);

		if (CollectionUtility.isEmpty(leads)) return pageData;

		for (Lead lead : leads) {
			LeadsTablePageData.LeadsTableRowData row = pageData.addRow();
			row.setLead(lead);
			row.setName(lead.getName());
			row.setAssigned(Optional.ofNullable(lead.getAssigned()).map(m -> m.getFullName()).orElse(null));
			row.setCompany(lead.getCompany());
			row.setEmail(lead.getEmail());
			row.setCreatedAt(lead.getCreatedAt());
			row.setLastContact(lead.getLastContactAt());
			row.setPhone(lead.getPhone());
			row.setSource(Optional.ofNullable(lead.getSource()).map(m -> m.getName()).orElse(null));
			row.setStatus(Optional.ofNullable(lead.getStatus()).map(m -> m.getName()).orElse(null));
		}

		return pageData;
	}
}
