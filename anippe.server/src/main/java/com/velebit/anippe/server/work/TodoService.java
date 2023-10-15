package com.velebit.anippe.server.work;

import com.velebit.anippe.server.AbstractService;
import com.velebit.anippe.server.ServerSession;
import com.velebit.anippe.shared.work.ITodoService;
import com.velebit.anippe.shared.work.TodoFormData.TodoTable.TodoTableRowData;
import org.eclipse.scout.rt.platform.holders.BeanArrayHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;

import java.util.Date;
import java.util.List;

public class TodoService extends AbstractService implements ITodoService {
    @Override
    public void createTodo(String content) {
        StringBuffer varname1 = new StringBuffer();
        varname1.append("INSERT INTO todos ");
        varname1.append("            (user_id, ");
        varname1.append("             content) ");
        varname1.append("VALUES      (:userId, ");
        varname1.append("             :content)");
        SQL.insert(varname1.toString(), new NVPair("userId", ServerSession.get().getCurrentUser().getId()), new NVPair("content", content));
    }

    @Override
    public void updateTodo(Integer todoId, String content, Boolean completed) {
        StringBuffer varname1 = new StringBuffer();
        varname1.append("UPDATE todos ");
        varname1.append("SET    completed_at = :completedAt, ");
        varname1.append("       content = :content ");
        varname1.append("WHERE  id = :todoId");
        SQL.update(varname1.toString(), new NVPair("completedAt", completed ? new Date() : null), new NVPair("todoId", todoId), new NVPair("content", content));
    }

    @Override
    public void deleteTodo(List<Integer> todoIds) {
        StringBuffer varname1 = new StringBuffer();
        varname1.append("UPDATE todos ");
        varname1.append("SET    deleted_at = now() ");
        varname1.append("WHERE  id = :todoIds");
        SQL.update(varname1.toString(), new NVPair("todoIds", todoIds));

    }

    @Override
    public List<TodoTableRowData> fetchTodos() {
        BeanArrayHolder<TodoTableRowData> holder = new BeanArrayHolder<>(TodoTableRowData.class);

        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT   id, ");
        varname1.append("         content, ");
        varname1.append("         CASE WHEN completed_at IS NULL THEN false ELSE true END ");
        varname1.append("FROM     todos ");
        varname1.append("WHERE    user_id = :userId ");
        varname1.append("AND      deleted_at IS NULL ");
        varname1.append("ORDER BY completed_at DESC, ");
        varname1.append("         created_at ASC ");
        varname1.append("LIMIT 20 ");
        varname1.append("INTO     :{rows.TodoId}, ");
        varname1.append("         :{rows.Name}, ");
        varname1.append("         :{rows.Completed}");
        SQL.selectInto(varname1.toString(), new NVPair("userId", getCurrentUserId()), new NVPair("rows", holder));

        return CollectionUtility.arrayList(holder.getBeans());
    }

    @Override
    public void delete(List<Integer> todoIds) {
        SQL.update("UPDATE todos SET deleted_at = now() WHERE id = :todoIds", new NVPair("todoIds", todoIds));
    }
}
