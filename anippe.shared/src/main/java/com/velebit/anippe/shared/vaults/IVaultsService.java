package com.velebit.anippe.shared.vaults;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

import java.util.List;

@TunnelToServer
public interface IVaultsService extends IService {
    void delete(Integer vaultId);

    List<VaultsFormData.VaultsTable.VaultsTableRowData> fetchVaults(Integer relatedId, Integer relatedType);
}
