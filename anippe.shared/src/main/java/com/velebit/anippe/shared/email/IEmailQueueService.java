package com.velebit.anippe.shared.email;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import java.util.List;

@TunnelToServer
public interface IEmailQueueService extends IService {
    EmailQueueTablePageData getEmailQueueTableData(SearchFilter filter);

    void deleteQueuedEmails(List<Integer> values);

    void deleteAllNotSentPendingEmails();
}
