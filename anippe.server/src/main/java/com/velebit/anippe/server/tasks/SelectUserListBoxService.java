package com.velebit.anippe.server.tasks;

import com.velebit.anippe.shared.tasks.ISelectUserListBoxService;
import com.velebit.anippe.shared.tasks.SelectUserListBoxFormData;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;

public class SelectUserListBoxService implements ISelectUserListBoxService {
    @Override
    public SelectUserListBoxFormData prepareCreate(SelectUserListBoxFormData formData) {
        return formData;
    }

    @Override
    public SelectUserListBoxFormData create(SelectUserListBoxFormData formData) {
        //Izbrisi sve djelatnike...
        SQL.delete("DELETE FROM link_task_users WHERE task_id = :taskId", formData);

        if (formData.getUsersListBox().getValue().isEmpty()) return formData;

        for (Long userId : formData.getUsersListBox().getValue()) {
            SQL.insert("INSERT INTO link_task_users (task_id, user_id) VALUES (:taskId, :userId)", formData, new NVPair("userId", userId));
        }

        return formData;
    }

    @Override
    public SelectUserListBoxFormData load(SelectUserListBoxFormData formData) {
        return formData;
    }

    @Override
    public SelectUserListBoxFormData store(SelectUserListBoxFormData formData) {
        return formData;
    }
}
