package com.velebit.anippe.server.leads;

import com.velebit.anippe.server.AbstractService;
import com.velebit.anippe.shared.leads.ILeadsService;
import com.velebit.anippe.shared.leads.Lead;
import com.velebit.anippe.shared.leads.LeadRequest;
import com.velebit.anippe.shared.leads.LeadsTablePageData;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import java.util.List;

public class LeadsService extends AbstractService implements ILeadsService {
	@Override
	public LeadsTablePageData getLeadsTableData(SearchFilter filter) {
		LeadsTablePageData pageData = new LeadsTablePageData();

		LeadRequest request = new LeadRequest();
		request.setAssignedUserId(getCurrentUserId().longValue());

		List<Lead> leads = BEANS.get(LeadDao.class).get(request);

		if (CollectionUtility.isEmpty(leads)) return pageData;

		for (Lead lead : leads) {
			LeadsTablePageData.LeadsTableRowData row = pageData.addRow();
			row.setLead(lead);
			row.setName(lead.getName());
			row.setAssigned(lead.getAssigned() != null ? lead.getAssigned().getId().longValue() : null);
			row.setCompany(lead.getCompany());
			row.setEmail(lead.getEmail());
			row.setCreatedAt(lead.getCreatedAt());
			row.setLastContact(lead.getLastContactAt());
			row.setPhone(lead.getPhone());
			row.setSource(lead.getSource().getId().longValue());
			row.setStatus(lead.getStatus().getId().longValue());
		}

		return pageData;
	}

	@Override
	public void delete(Integer leadId) {
		SQL.update("UPDATE leads SET deleted_at = now() WHERE id = :leadId", new NVPair("leadId", leadId));
	}

	@Override
	public void changeStatus(Integer leadId, Long value) {
		SQL.update("UPDATE leads SET status_id = :statusId WHERE id = :leadId", new NVPair("leadId", leadId), new NVPair("statusId", value));
	}

	@Override
	public void changeSource(Integer leadId, Long sourceId) {
		SQL.update("UPDATE leads SET source_id = :sourceId WHERE id = :leadId", new NVPair("leadId", leadId), new NVPair("sourceId", sourceId));
	}
}
