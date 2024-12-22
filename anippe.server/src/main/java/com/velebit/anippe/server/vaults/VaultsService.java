package com.velebit.anippe.server.vaults;

import com.velebit.anippe.server.AbstractService;
import com.velebit.anippe.shared.vaults.IVaultsService;
import com.velebit.anippe.shared.vaults.VaultsFormData;
import org.eclipse.scout.rt.platform.holders.BeanArrayHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;

import java.util.List;

public class VaultsService extends AbstractService implements IVaultsService {
    @Override
    public void delete(Integer vaultId) {
        SQL.update("UPDATE vaults SET deleted_at = now() WHERE id = :vaultId", new NVPair("vaultId", vaultId));
    }

    @Override
    public List<VaultsFormData.VaultsTable.VaultsTableRowData> fetchVaults(Integer relatedId, Integer relatedType) {
        BeanArrayHolder<VaultsFormData.VaultsTable.VaultsTableRowData> holder = new BeanArrayHolder<>(VaultsFormData.VaultsTable.VaultsTableRowData.class);

        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT v.id, ");
        varname1.append("       v.NAME, ");
        varname1.append("       v.visibility_id, ");
        varname1.append("       v.user_id, ");
        varname1.append("       u.first_name ");
        varname1.append("              || ' ' ");
        varname1.append("              || u.last_name, ");
        varname1.append("       v.created_at, ");
        varname1.append("       v.updated_at ");
        varname1.append("FROM   vaults v, ");
        varname1.append("       users u ");
        varname1.append("WHERE  v.user_id = u.id ");
        varname1.append("AND    v.deleted_at IS NULL ");
        varname1.append("AND    v.organisation_id = :organisationId ");
        varname1.append("AND    v.related_id = :relatedId ");
        varname1.append("AND    v.related_type = :relatedType ");
        varname1.append("into   :{rows.VaultId}, ");
        varname1.append("       :{rows.Name}, ");
        varname1.append("       :{rows.Visibility}, ");
        varname1.append("       :{rows.CreatedById}, ");
        varname1.append("       :{rows.CreatedBy}, ");
        varname1.append("       :{rows.CreatedAt}, ");
        varname1.append("       :{rows.UpdatedAt}");
        SQL.selectInto(varname1.toString(),
                new NVPair("rows", holder),
                new NVPair("organisationId", getCurrentOrganisationId()),
                new NVPair("relatedId", relatedId),
                new NVPair("relatedType", relatedType));



        return CollectionUtility.arrayList(holder.getBeans());
    }
}
