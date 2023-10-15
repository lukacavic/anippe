package com.velebit.anippe.shared.work;

import com.velebit.anippe.shared.work.TodoFormData.TodoTable.TodoTableRowData;
import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

import java.util.List;

@TunnelToServer
public interface ITodoService extends IService {
    void createTodo(String value);

    void updateTodo(Integer todoId, String content, Boolean completed);

    void deleteTodo(List<Integer> todoIds);

    List<TodoTableRowData> fetchTodos();

    void delete(List<Integer> todoIds);
}
