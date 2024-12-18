package com.velebit.anippe.server.projects;

import com.velebit.anippe.server.leads.LeadDao;
import com.velebit.anippe.shared.leads.Lead;
import com.velebit.anippe.shared.leads.LeadRequest;
import com.velebit.anippe.shared.leads.LeadStatus;
import com.velebit.anippe.shared.projects.ILeadsService;
import com.velebit.anippe.shared.projects.LeadsFormData.LeadsTable.LeadsTableRowData;
import com.velebit.anippe.shared.projects.settings.leads.ILeadStatusService;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;

import java.util.List;

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

        if (projectId != null) {
            List<LeadStatus> statuses = BEANS.get(ILeadStatusService.class).fetchStatuses(projectId);
            for (LeadStatus leadStatus : statuses) {

                if(leads.stream().noneMatch(l -> l.getStatus().getId().equals(leadStatus.getId()))) continue;

                LeadsTableRowData row = new LeadsTableRowData();
                row.setPrimaryID("STATUS_" + leadStatus.getId());
                row.setName(leadStatus.getName());
                row.setLead(new Lead());
                rows.add(row);
            }

        }

        for (Lead lead : leads) {
            LeadsTableRowData row = new LeadsTableRowData();
            row.setLead(lead);
            row.setPrimaryID(lead.getId().toString());

            if (projectId != null) {
                row.setParentID("STATUS_" + lead.getStatus().getId());
            }

            row.setName(lead.getName());
            row.setAssigned(lead.getAssigned() != null ? lead.getAssigned().getId().longValue() : null);
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
