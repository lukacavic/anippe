package com.velebit.anippe.server.tasks;

import com.velebit.anippe.server.ServerSession;
import com.velebit.anippe.shared.tasks.Task;
import com.velebit.anippe.shared.tasks.TaskRequest;
import org.eclipse.scout.rt.platform.Bean;
import org.eclipse.scout.rt.platform.holders.BeanArrayHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.modelmapper.ModelMapper;

import java.util.List;

@Bean
public class TaskDao {
    public List<Task> get(TaskRequest request) {
        BeanArrayHolder<TaskDto> dto = new BeanArrayHolder<TaskDto>(TaskDto.class);

        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT t.id, ");
        varname1.append("       t.NAME, ");
        varname1.append("       t.description, ");
        varname1.append("       uc.id, ");
        varname1.append("       uc.first_name, ");
        varname1.append("       uc.last_name, ");
        varname1.append("       t.related_id, ");
        varname1.append("       t.related_type, ");
        varname1.append("       t.priority_id, ");
        varname1.append("       t.status_id, ");
        varname1.append("       t.start_at, ");
        varname1.append("       t.deadline_at, ");
        varname1.append("       t.completed_at ");
        varname1.append("FROM   tasks t, ");
        varname1.append("       users uc ");
        varname1.append("WHERE  t.user_id = uc.id ");
        varname1.append("       AND t.organisation_id = :organisationId ");

        if (request.getRelatedId() != null && request.getRelatedType() != null) {
            varname1.append(" AND related_id = :{request.relatedId} AND related_type = :{request.relatedType} ");
        }

        varname1.append("ORDER  BY t.created_at ");
        varname1.append("INTO   :{dto.id}, ");
        varname1.append("       :{dto.name}, ");
        varname1.append("       :{dto.description}, ");
        varname1.append("       :{dto.userCreatedId}, ");
        varname1.append("       :{dto.userCreatedFirstName}, ");
        varname1.append("       :{dto.userCreatedLastName}, ");
        varname1.append("       :{dto.relatedId}, ");
        varname1.append("       :{dto.relatedType}, ");
        varname1.append("       :{dto.priorityId}, ");
        varname1.append("       :{dto.statusId}, ");
        varname1.append("       :{dto.startAt}, ");
        varname1.append("       :{dto.deadlineAt}, ");
        varname1.append("       :{dto.completedAt} ");
        SQL.selectInto(varname1.toString(), new NVPair("dto", dto), new NVPair("organisationId", ServerSession.get().getCurrentOrganisation().getId()), new NVPair("dto", dto), new NVPair("request", request));
        List<TaskDto> dtos = CollectionUtility.arrayList(dto.getBeans());

        List<Task> tasks = CollectionUtility.emptyArrayList();

        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(new TaskMap());
        dtos.forEach(item -> tasks.add(mapper.map(item, Task.class)));

        return tasks;
    }
}
