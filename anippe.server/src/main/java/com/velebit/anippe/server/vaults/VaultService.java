package com.velebit.anippe.server.vaults;

import com.velebit.anippe.server.AbstractService;
import com.velebit.anippe.shared.vaults.IVaultService;
import com.velebit.anippe.shared.vaults.VaultFormData;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;

public class VaultService extends AbstractService implements IVaultService {
    @Override
    public VaultFormData prepareCreate(VaultFormData formData) {
        return formData;
    }

    @Override
    public VaultFormData create(VaultFormData formData) {
        StringBuffer varname1 = new StringBuffer();
        varname1.append("INSERT INTO vaults ");
        varname1.append("            (NAME, ");
        varname1.append("             content, ");
        varname1.append("             related_id, ");
        varname1.append("             related_type, ");
        varname1.append("             user_id, ");
        varname1.append("             organisation_id, ");
        varname1.append("             visibility_id) ");
        varname1.append("VALUES      (:Name, ");
        varname1.append("             :Content, ");
        varname1.append("             :relatedId, ");
        varname1.append("             :relatedType, ");
        varname1.append("             :userId, ");
        varname1.append("             :organisationId, ");
        varname1.append("             :VisibilityModeSelector)");
        SQL.insert(varname1.toString(), formData, new NVPair("userId", getCurrentUserId()), new NVPair("organisationId", getCurrentOrganisationId()));

        return formData;
    }

    @Override
    public VaultFormData load(VaultFormData formData) {
        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT name, content, visibility_id ");
        varname1.append("FROM vaults ");
        varname1.append("WHERE id = :vaultId ");
        varname1.append("INTO :Name, :Content, :VisibilityModeSelector");
        SQL.selectInto(varname1.toString(), formData);

        return formData;
    }

    @Override
    public VaultFormData store(VaultFormData formData) {
        StringBuffer varname1 = new StringBuffer();
        varname1.append("UPDATE vaults ");
        varname1.append("SET    NAME = :Name, ");
        varname1.append("       content = :Content, ");
        varname1.append("       visibility_id = :VisibilityModeSelector, ");
        varname1.append("       updated_at = Now() ");
        varname1.append("WHERE  id = :vaultId");
        SQL.update(varname1.toString(), formData);

        return formData;
    }
}
