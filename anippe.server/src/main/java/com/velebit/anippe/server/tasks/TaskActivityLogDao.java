package com.velebit.anippe.server.tasks;

import com.velebit.anippe.shared.tasks.TaskActivityLog;
import org.eclipse.scout.rt.platform.Bean;
import org.eclipse.scout.rt.platform.holders.BeanArrayHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.modelmapper.ModelMapper;

import java.util.List;

@Bean
public class TaskActivityLogDao {

    public List<TaskActivityLog> get(Integer taskId, boolean withSystemLog) {
        BeanArrayHolder<TaskActivityLogDto> dto = new BeanArrayHolder<TaskActivityLogDto>(TaskActivityLogDto.class);

        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT   tal.id, tal.task_id, ");
        varname1.append("         tal.content, ");
        varname1.append("         u.id, ");
        varname1.append("         u.first_name, ");
        varname1.append("         u.last_name, ");
        varname1.append("         tal.created_at ");
        varname1.append("FROM     task_activity_log tal, ");
        varname1.append("         users u ");
        varname1.append("WHERE    tal.user_id = u.id ");
        varname1.append("AND      tal.deleted_at IS NULL ");

        if (!withSystemLog) {
            varname1.append("AND      system_created IS FALSE ");
        }

        varname1.append("AND      tal.task_id = :taskId ");
        varname1.append("ORDER BY tal.created_at DESC ");
        varname1.append("into     :{holder.id}, ");
        varname1.append("         :{holder.taskId}, ");
        varname1.append("         :{holder.content}, ");
        varname1.append("         :{holder.userCreatedId}, ");
        varname1.append("         :{holder.userCreatedFirstName}, ");
        varname1.append("         :{holder.userCreatedLastName}, ");
        varname1.append("         :{holder.createdAt} ");

        SQL.selectInto(varname1.toString(),  new NVPair("taskId", taskId), new NVPair("holder", dto));
        List<TaskActivityLogDto> dtos = CollectionUtility.arrayList(dto.getBeans());

        List<TaskActivityLog> taskActivityLogs = CollectionUtility.emptyArrayList();

        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(new TaskMap());
        dtos.forEach(item -> taskActivityLogs.add(mapper.map(item, TaskActivityLog.class)));


        return taskActivityLogs;
    }
}
