package com.velebit.anippe.server.tasks;

import com.velebit.anippe.server.AbstractDao;
import com.velebit.anippe.server.ServerSession;
import com.velebit.anippe.shared.beans.User;
import com.velebit.anippe.shared.tasks.Task;
import com.velebit.anippe.shared.tasks.TaskRequest;
import org.eclipse.scout.rt.platform.Bean;
import org.eclipse.scout.rt.platform.holders.BeanArrayHolder;
import org.eclipse.scout.rt.platform.holders.IntegerHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.modelmapper.ModelMapper;

import java.util.Date;
import java.util.List;

@Bean
public class TaskDao extends AbstractDao {
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
        varname1.append("       AND t.deleted_at is null ");

        if (request.getRelatedId() != null && request.getRelatedType() != null) {
            varname1.append(" AND related_id = :{request.relatedId} AND related_type = :{request.relatedType} ");
        }

        if (!CollectionUtility.isEmpty(request.getStatusIds())) {
            varname1.append(" AND t.status_id = :{request.statusIds} ");
        }

        if (!CollectionUtility.isEmpty(request.getPriorityIds())) {
            varname1.append(" AND t.priority_id = :{request.priorityIds} ");
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

    public Task find(Integer taskId) {
        TaskDto dto = new TaskDto();

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
        varname1.append("       t.completed_at, ");
        varname1.append("       t.archived_at ");
        varname1.append("FROM   tasks t, ");
        varname1.append("       users uc ");
        varname1.append("WHERE  t.user_id = uc.id ");
        varname1.append("       AND t.id = :taskId ");
        varname1.append("       AND t.deleted_at is null ");
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
        varname1.append("       :{dto.completedAt}, ");
        varname1.append("       :{dto.archivedAt} ");
        SQL.selectInto(varname1.toString(), new NVPair("dto", dto), new NVPair("taskId", taskId), new NVPair("dto", dto));

        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(new TaskMap());

        Task task = mapper.map(dto, Task.class);
        task.setAssignedUsers(fetchAssignedUsers(task.getId()));

        return task;
    }

    private List<User> fetchAssignedUsers(Integer taskId) {
        BeanArrayHolder<User> holder = new BeanArrayHolder<>(User.class);

        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT u.id, u.first_name, u.last_name ");
        varname1.append("FROM users u, link_task_users ltu ");
        varname1.append("WHERE u.id = ltu.user_id ");
        varname1.append("AND u.deleted_at IS NULL ");
        varname1.append("AND ltu.task_id = :taskId ");
        varname1.append("INTO :{holder.id}, :{holder.firstName}, :{holder.lastName}");
        SQL.selectInto(varname1.toString(), new NVPair("taskId", taskId), new NVPair("holder", holder));

        return CollectionUtility.arrayList(holder.getBeans());
    }

    public void updateTaskDates(Integer itemId, Date startAt, Date endAt) {
        SQL.update("UPDATE tasks SET start_at = :startAt, deadline_at = :endAt WHERE id = :taskId",
                new NVPair("taskId", itemId),
                new NVPair("startAt", startAt),
                new NVPair("endAt", endAt)
        );
    }

    public void stopTimer(Integer timerId) {
        SQL.update("UPDATE task_timers SET end_at = now() WHERE id = :timerId", new NVPair("timerId", timerId));
    }

    public Integer startTimer(Integer taskId) {
        IntegerHolder holder = new IntegerHolder();

        SQL.selectInto("INSERT INTO task_timers (start_at, task_id, user_id) VALUES (now(), :taskId, :userId) RETURNING id INTO :holder",
                new NVPair("taskId", taskId),
                new NVPair("userId", getCurrentUserId()),
                new NVPair("holder", holder)
        );

        return holder.getValue();
    }
}
