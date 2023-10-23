package com.velebit.anippe.server.projects.settings.support;

import com.velebit.anippe.server.AbstractService;
import com.velebit.anippe.shared.projects.settings.support.DepartmentFormData;
import com.velebit.anippe.shared.projects.settings.support.IDepartmentService;
import org.eclipse.scout.rt.mail.imap.ImapHelper;
import org.eclipse.scout.rt.mail.imap.ImapServerConfig;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Store;
import java.util.List;

public class DepartmentService extends AbstractService implements IDepartmentService {
    @Override
    public DepartmentFormData prepareCreate(DepartmentFormData formData) {
        return formData;
    }

    @Override
    public DepartmentFormData create(DepartmentFormData formData) {
        StringBuffer varname1 = new StringBuffer();
        varname1.append("INSERT INTO ticket_departments ");
        varname1.append("            (project_id, ");
        varname1.append("             NAME, ");
        varname1.append("             imap_import_host, ");
        varname1.append("             imap_import_email, ");
        varname1.append("             imap_import_password, ");
        varname1.append("             imap_import_encryption, ");
        varname1.append("             imap_import_folder, ");
        varname1.append("             created_at, ");
        varname1.append("             active, ");
        varname1.append("             imap_import_deleted_after, ");
        varname1.append("             organisation_id) ");
        varname1.append("VALUES      (:projectId, ");
        varname1.append("             :Name, ");
        varname1.append("             :EmailImapHost, ");
        varname1.append("             :EmailImapEmail, ");
        varname1.append("             :EmailImapPassword, ");
        varname1.append("             :EmailImapEncryptionGroup, ");
        varname1.append("             :Folders, ");
        varname1.append("             Now(), ");
        varname1.append("             :Active, ");
        varname1.append("             :DeleteAfterImport, ");
        varname1.append("             :organisationId)");
        SQL.insert(varname1.toString(), formData, new NVPair("organisationId", getCurrentOrganisationId()));
        return formData;
    }

    @Override
    public DepartmentFormData load(DepartmentFormData formData) {
        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT project_id, ");
        varname1.append("       name, ");
        varname1.append("       active, ");
        varname1.append("       imap_import_enabled, ");
        varname1.append("       imap_import_host, ");
        varname1.append("       imap_import_email, ");
        varname1.append("       imap_import_password, ");
        varname1.append("       imap_import_encryption, ");
        varname1.append("       imap_import_folder ");
        varname1.append("FROM   ticket_departments ");
        varname1.append("WHERE  id = :departmentId ");
        varname1.append("INTO   :projectId, :Name, :Active, :EmailImapEnabled, :EmailImapHost, ");
        varname1.append(":EmailImapEmail, :EmailImapPassword, :EmailImapEncryptionGroup, ");
        varname1.append("       :Folders");
        SQL.selectInto(varname1.toString(), formData);

        return formData;
    }

    @Override
    public DepartmentFormData store(DepartmentFormData formData) {

        StringBuffer varname1 = new StringBuffer();
        varname1.append("UPDATE ticket_departments SET ");
        varname1.append("       name = :Name, ");
        varname1.append("       active = :Active, ");
        varname1.append("       imap_import_enabled = :EmailImapEnabled, ");
        varname1.append("       imap_import_host = :EmailImapHost, ");
        varname1.append("       imap_import_email = :EmailImapEmail, ");
        varname1.append("       imap_import_password = :EmailImapPassword, ");
        varname1.append("       imap_import_encryption = :EmailImapEncryptionGroup, ");
        varname1.append("       imap_import_folder = :Folders ");
        varname1.append("WHERE  id = :departmentId ");
        SQL.update(varname1.toString(), formData);

        return formData;
    }

    @Override
    public List<String> fetchImapFolders(DepartmentFormData formData) {
        Store store = null;
        List<String> folders = CollectionUtility.emptyArrayList();
        try {
            ImapServerConfig config = BEANS.get(ImapServerConfig.class);
            config.withHost(formData.getEmailImapHost().getValue())
                    .withUsername(formData.getEmailImapEmail().getValue())
                    .withPassword(formData.getEmailImapPassword().getValue()).withUseSsl(true);

            ImapHelper helper = BEANS.get(ImapHelper.class);
            store = helper.connect(config);

            Folder[] imapFolders = store.getDefaultFolder().list("*");

            for (Folder folder : imapFolders) {
                folders.add(folder.getName());
            }

            store.close();
        } catch (Exception e) {
            throw new VetoException(TEXTS.get("ErrorGeneratingFolders"));
        }

        return folders;
    }

    @Override
    public boolean checkImapConnection(DepartmentFormData formData) {
        boolean isConnected = false;
        Store store = null;
        try {
            ImapServerConfig config = BEANS.get(ImapServerConfig.class);
            config.withHost(formData.getEmailImapHost().getValue())
                    .withUsername(formData.getEmailImapEmail().getValue())
                    .withPassword(formData.getEmailImapPassword().getValue()).withUseSsl(true);

            ImapHelper helper = BEANS.get(ImapHelper.class);
            store = helper.connect(config);

            isConnected = store.isConnected();
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (store != null && store.isConnected()) {
                    store.close();
                }
            } catch (MessagingException e) {
            }
        }

        return isConnected;
    }

    @Override
    public void delete(Integer departmentId) {
        SQL.update("UPDATE ticket_departments SET deleted_at = now() WHERE id = :ticketDepartmentId", new NVPair("ticketDepartmentId", departmentId));
    }
}
