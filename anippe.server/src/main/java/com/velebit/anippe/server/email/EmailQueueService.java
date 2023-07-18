package com.velebit.anippe.server.email;

import com.velebit.anippe.server.ServerSession;
import com.velebit.anippe.shared.constants.Constants;
import com.velebit.anippe.shared.email.EmailQueueSearchFormData;
import com.velebit.anippe.shared.email.EmailQueueTablePageData;
import com.velebit.anippe.shared.email.IEmailQueueService;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import java.util.List;

public class EmailQueueService implements IEmailQueueService {
    @Override
    public EmailQueueTablePageData getEmailQueueTableData(SearchFilter filter) {
        EmailQueueTablePageData pageData = new EmailQueueTablePageData();

        EmailQueueSearchFormData formData = (EmailQueueSearchFormData) filter.getFormData();

        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT e.id, ");
        varname1.append("       (SELECT Count(0) ");
        varname1.append("        FROM   email_attachments ");
        varname1.append("        WHERE  email_id = e.id), ");
        varname1.append("       e.created_at, ");
        varname1.append("       e.subject, ");
        varname1.append("       e.message, ");
        varname1.append("       e.sender_name, ");
        varname1.append("       e.receivers, ");
        varname1.append("       e.cc_receivers, ");
        varname1.append("       e.bcc_receivers, ");
        varname1.append("       e.status_id, ");
        varname1.append("       u.last_name ");
        varname1.append("       || ' ' ");
        varname1.append("       || u.first_name, ");
        varname1.append("       e.error_message ");
        varname1.append("FROM   emails e ");
        varname1.append("       LEFT OUTER JOIN users u ");
        varname1.append("                    ON u.id = e.user_id ");
        varname1.append("WHERE  e.organisation_id = :organisationId ");
        varname1.append("AND    e.status_id = :StatusBox ");
        varname1.append("AND    e.deleted_at IS NULL ");

        if (formData.getUser().getValue() != null) {
            varname1.append(" AND e.user_id = :User ");
        }

        if (formData.getHasAttachments().getValue().booleanValue()) {
            varname1.append(" AND (SELECT Count(0) FROM email_attachments WHERE  email_id = e.id) > 0 ");
        }

        if (formData.getPeriodFrom().getValue() != null) {
            varname1.append(" AND e.created_at >= :PeriodFrom ");
        }

        if (formData.getPeriodTo().getValue() != null) {
            varname1.append(" AND e.created_at <= :PeriodTo ");
        }

        varname1.append("ORDER BY e.id DESC ");
        varname1.append(" INTO ");
        varname1.append(":{rows.EmailQueueId}, :{rows.AttachmentsCount}, :{rows.CreatedAt}, :{rows.Subject}, :{rows.Content}, :{rows.Sender}, ");
        varname1.append(":{rows.Receivers}, :{rows.CCReceivers}, :{rows.BCCReceivers}, :{rows.Status}, :{rows.User}, :{rows.Error} ");
        SQL.selectInto(varname1.toString(), formData, new NVPair("rows", pageData), new NVPair("organisationId", ServerSession.get().getCurrentOrganisation().getId()));

        return pageData;
    }

    @Override
    public void deleteQueuedEmails(List<Integer> values) {
        String stmt = "UPDATE emails SET deleted_at = now() WHERE id = :emailIds";
        SQL.update(stmt, new NVPair("emailIds", values));
    }

    @Override
    public void deleteAllNotSentPendingEmails() {
        String stmt = "UPDATE emails SET deleted_at = now() WHERE status_id = :statusIds AND organisation_id = :organisationId";
        SQL.update(stmt, new NVPair("statusIds", CollectionUtility.arrayList(Constants.EmailStatus.ERROR, Constants.EmailStatus.PREPARE_SEND)), new NVPair("organisationId", ServerSession.get().getCurrentOrganisation().getId()));

    }
}
