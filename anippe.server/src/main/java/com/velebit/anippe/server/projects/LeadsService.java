package com.velebit.anippe.server.projects;

import com.velebit.anippe.server.leads.LeadDao;
import com.velebit.anippe.shared.beans.User;
import com.velebit.anippe.shared.leads.Lead;
import com.velebit.anippe.shared.leads.LeadRequest;
import com.velebit.anippe.shared.projects.ILeadsService;
import com.velebit.anippe.shared.projects.LeadsFormData;
import com.velebit.anippe.shared.projects.LeadsFormData.LeadsTable.LeadsTableRowData;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;

import java.util.List;
import java.util.Optional;

public class LeadsService implements ILeadsService {

    @Override
    public List<LeadsTableRowData> fetchLeads(Integer projectId, Long statusId, Long sourceId, Long assignedUserId) {
        LeadRequest request = new LeadRequest();
        request.setProjectId(projectId);
        request.setStatusId(statusId);
        request.setSourceId(sourceId);
        request.setAssignedUserId(assignedUserId);

        List<Lead> leads = BEANS.get(LeadDao.class).get(request);

        List<LeadsTableRowData> rows = CollectionUtility.emptyArrayList();

        if (CollectionUtility.isEmpty(leads)) return rows;

        for (Lead lead : leads) {
            LeadsTableRowData row = new LeadsTableRowData();
            row.setLead(lead);
            row.setName(lead.getName());
            row.setAssigned(Optional.ofNullable(lead.getAssigned()).map(User::getFullName).orElse(null));
            row.setCompany(lead.getCompany());
            row.setEmail(lead.getEmail());
            row.setCreatedAt(lead.getCreatedAt());
            row.setLastContact(lead.getLastContactAt());
            row.setPhone(lead.getPhone());
            row.setSource(lead.getSource().getId().longValue());
            row.setStatus(lead.getStatus().getId().longValue());
            rows.add(row);
        }

        return rows;
    }

}
