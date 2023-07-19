package com.velebit.anippe.server.vaults;

import com.velebit.anippe.shared.vaults.IVaultsService;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;

public class VaultsService implements IVaultsService {
    @Override
    public void delete(Integer vaultId) {
        SQL.update("UPDATE vaults SET deleted_at = now() WHERE id = :vaultId", new NVPair("vaultId", vaultId));
    }
}
