package com.velebit.anippe.shared.reminders;

import com.velebit.anippe.shared.reminders.AbstractRemindersGroupBoxData.RemindersTable.RemindersTableRowData;
import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import java.util.List;

@TunnelToServer
public interface IRemindersService extends IService {
    List<RemindersTableRowData> fetchReminders(Integer relatedId, Integer relatedType);
}
