package com.velebit.anippe.server.leads;

import com.velebit.anippe.server.ServerSession;
import com.velebit.anippe.shared.leads.Lead;
import com.velebit.anippe.shared.leads.LeadRequest;
import org.eclipse.scout.rt.platform.Bean;
import org.eclipse.scout.rt.platform.holders.BeanArrayHolder;
import org.eclipse.scout.rt.platform.holders.IntegerHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.modelmapper.ModelMapper;

import java.util.List;

@Bean
public class LeadDao {

    public List<Lead> get(LeadRequest request) {
        BeanArrayHolder<LeadDto> dto = new BeanArrayHolder<LeadDto>(LeadDto.class);

        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT          l.id, ");
        varname1.append("                l.name, ");
        varname1.append("                pr.id, ");
        varname1.append("                pr.name, ");
        varname1.append("                l.company, ");
        varname1.append("                l.description, ");
        varname1.append("                l.address, ");
        varname1.append("                l.city, ");
        varname1.append("                l.postal_code, ");
        varname1.append("                l.country_id, ");
        varname1.append("                cr.name, ");
        varname1.append("                l.email, ");
        varname1.append("                l.phone, ");
        varname1.append("                l.assigned_user_id, ");
        varname1.append("                au.first_name, ");
        varname1.append("                au.last_name, ");
        varname1.append("                lsou.id, ");
        varname1.append("                lsou.name, ");
        varname1.append("                lsta.id, ");
        varname1.append("                lsta.name, ");
        varname1.append("                l.last_contact_at, ");
        varname1.append("                l.created_at, ");
        varname1.append("                l.client_id ");
        varname1.append("FROM            leads l ");
        varname1.append("LEFT OUTER JOIN countries cr ");
        varname1.append("ON              cr.id = l.country_id ");
        varname1.append("LEFT OUTER JOIN projects pr ");
        varname1.append("ON              pr.id = l.project_id ");
        varname1.append("LEFT OUTER JOIN users au ");
        varname1.append("ON              au.id = l.assigned_user_id ");
        varname1.append("LEFT OUTER JOIN lead_sources lsou ");
        varname1.append("ON              lsou.id = l.source_id ");
        varname1.append("LEFT OUTER JOIN lead_statuses lsta ");
        varname1.append("ON              lsta.id = l.status_id ");
        varname1.append("WHERE           l.deleted_at IS NULL ");
        varname1.append("AND             l.organisation_id = :organisationId ");

        if (request.getProjectId() != null) {
            varname1.append(" AND l.project_id = :{request.projectId} ");
        }

        if (request.getStatusId() != null) {
            varname1.append(" AND l.status_id = :{request.statusId} ");
        }

        if (request.getSourceId() != null) {
            varname1.append(" AND l.source_id = :{request.sourceId} ");
        }

        if (request.getAssignedUserId() != null) {
            varname1.append(" AND l.assigned_user_id = :{request.assignedUserId} ");
        }
        varname1.append("ORDER BY        l.NAME ");
        varname1.append("INTO            :{holder.id}, ");
        varname1.append("                :{holder.name}, ");
        varname1.append("                :{holder.projectId}, ");
        varname1.append("                :{holder.projectName}, ");
        varname1.append("                :{holder.company}, ");
        varname1.append("                :{holder.description}, ");
        varname1.append("                :{holder.address}, ");
        varname1.append("                :{holder.city}, ");
        varname1.append("                :{holder.postalCode}, ");
        varname1.append("                :{holder.countryId}, ");
        varname1.append("                :{holder.countryName}, ");
        varname1.append("                :{holder.email}, ");
        varname1.append("                :{holder.phone}, ");
        varname1.append("                :{holder.assignedUserId}, ");
        varname1.append("                :{holder.assignedUserFirstName}, ");
        varname1.append("                :{holder.assignedUserLastName}, ");
        varname1.append("                :{holder.sourceId}, ");
        varname1.append("                :{holder.sourceName}, ");
        varname1.append("                :{holder.statusId}, ");
        varname1.append("                :{holder.statusName}, ");
        varname1.append("                :{holder.lastContactAt}, ");
        varname1.append("                :{holder.createdAt}, ");
        varname1.append("                :{holder.clientId} ");
        SQL.selectInto(varname1.toString(), new NVPair("holder", dto),
                new NVPair("request", request),
                new NVPair("organisationId", ServerSession.get().getCurrentOrganisation().getId()));
        List<LeadDto> dtos = CollectionUtility.arrayList(dto.getBeans());

        List<Lead> leads = CollectionUtility.emptyArrayList();

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setAmbiguityIgnored(true);
        mapper.addMappings(new LeadMap());
        dtos.forEach(item -> leads.add(mapper.map(item, Lead.class)));

        return leads;
    }

    public void addActivityLog(Integer leadId, String content) {

        StringBuffer varname1 = new StringBuffer();
        varname1.append("INSERT INTO lead_activity_log ");
        varname1.append("            (content, ");
        varname1.append("             user_id, ");
        varname1.append("             organisation_id, ");
        varname1.append("             lead_id, ");
        varname1.append("             created_at) ");
        varname1.append("VALUES      (:content, ");
        varname1.append("             :userId, ");
        varname1.append("             :organisationId, ");
        varname1.append("             :leadId, ");
        varname1.append("             Now())");
        SQL.insert(varname1.toString(),
                new NVPair("content", content),
                new NVPair("leadId", leadId),
                new NVPair("organisationId", ServerSession.get().getCurrentOrganisation().getId()),
                new NVPair("userId", ServerSession.get().getCurrentUser().getId())
        );

    }

    public Lead find(Integer leadId) {
        LeadDto dto = new LeadDto();

        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT          l.id, ");
        varname1.append("                l.name, ");
        varname1.append("                l.position, ");
        varname1.append("                l.lost, ");
        varname1.append("                l.company, ");
        varname1.append("                l.description, ");
        varname1.append("                l.address, ");
        varname1.append("                l.city, ");
        varname1.append("                l.postal_code, ");
        varname1.append("                l.country_id, ");
        varname1.append("                cr.name, ");
        varname1.append("                l.email, ");
        varname1.append("                l.phone, ");
        varname1.append("                l.assigned_user_id, ");
        varname1.append("                au.first_name, ");
        varname1.append("                au.last_name, ");
        varname1.append("                lsou.id, ");
        varname1.append("                lsou.name, ");
        varname1.append("                lsta.id, ");
        varname1.append("                lsta.name, ");
        varname1.append("                l.last_contact_at, ");
        varname1.append("                l.created_at, ");
        varname1.append("                l.client_id ");
        varname1.append("FROM            leads l ");
        varname1.append("LEFT OUTER JOIN countries cr ");
        varname1.append("ON              cr.id = l.country_id ");
        varname1.append("LEFT OUTER JOIN users au ");
        varname1.append("ON              au.id = l.assigned_user_id ");
        varname1.append("LEFT OUTER JOIN lead_sources lsou ");
        varname1.append("ON              lsou.id = l.source_id ");
        varname1.append("LEFT OUTER JOIN lead_statuses lsta ");
        varname1.append("ON              lsta.id = l.status_id ");
        varname1.append("WHERE           l.deleted_at IS NULL ");
        varname1.append("AND             l.id = :leadId ");
        varname1.append("ORDER BY        l.NAME ");
        varname1.append("INTO            :{holder.id}, ");
        varname1.append("                :{holder.name}, ");
        varname1.append("                :{holder.position}, ");
        varname1.append("                :{holder.lost}, ");
        varname1.append("                :{holder.company}, ");
        varname1.append("                :{holder.description}, ");
        varname1.append("                :{holder.address}, ");
        varname1.append("                :{holder.city}, ");
        varname1.append("                :{holder.postalCode}, ");
        varname1.append("                :{holder.countryId}, ");
        varname1.append("                :{holder.countryName}, ");
        varname1.append("                :{holder.email}, ");
        varname1.append("                :{holder.phone}, ");
        varname1.append("                :{holder.assignedUserId}, ");
        varname1.append("                :{holder.assignedUserFirstName}, ");
        varname1.append("                :{holder.assignedUserLastName}, ");
        varname1.append("                :{holder.sourceId}, ");
        varname1.append("                :{holder.sourceName}, ");
        varname1.append("                :{holder.statusId}, ");
        varname1.append("                :{holder.statusName}, ");
        varname1.append("                :{holder.lastContactAt}, ");
        varname1.append("                :{holder.createdAt}, ");
        varname1.append("                :{holder.clientId} ");
        SQL.selectInto(varname1.toString(), new NVPair("holder", dto),
                new NVPair("leadId", leadId)
        );

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setAmbiguityIgnored(true);
        mapper.addMappings(new LeadMap());

        return mapper.map(dto, Lead.class);
    }

    public Integer findStatusConverted(Lead lead) {
        IntegerHolder holder = new IntegerHolder();

        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT id ");
        varname1.append("FROM   lead_statuses ");
        varname1.append("WHERE  deleteable IS FALSE ");
        varname1.append("       AND organisation_id = :organisationId ");
        varname1.append("       AND deleted_at IS NULL ");

        if (lead.getProject() != null) {
            varname1.append(" AND project_id = :projectId ");
        }
        varname1.append("INTO   :holder");

        SQL.selectInto(varname1.toString(),
                new NVPair("projectId", lead.getProject().getId()),
                new NVPair("organisationId", ServerSession.get().getCurrentOrganisation().getId()),
                new NVPair("holder", holder));

        return holder.getValue();
    }

    public int countLeadsByStatus(Integer statusId) {
        IntegerHolder holder = new IntegerHolder();

        SQL.selectInto("SELECT COUNT(0) FROM leads WHERE status_id = :statusId AND deleted_at IS NULL INTO :holder", new NVPair("holder", holder), new NVPair("statusId", statusId));

        return holder.getValue();
    }
}
