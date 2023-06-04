package com.velebit.anippe.shared.auth;

import com.velebit.anippe.shared.organisations.Organisation;
import com.velebit.anippe.shared.beans.User;
import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface ILoginService extends IService {
	User getUser(String username, String s, String subdomain);

	User getUserByUsername(String userId);

	Organisation getCurrentOrganisation(Integer organisationId);
}
