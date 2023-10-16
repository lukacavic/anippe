package com.velebit.anippe.client.config;

import org.eclipse.scout.rt.platform.config.AbstractStringConfigProperty;

public class AppFileRootDirectoryOrganisationsConfigProperty extends AbstractStringConfigProperty {

	@Override
	public String getKey() {
		return "app.fileRootDirectoryOrganisations";
	}

	@Override
	public String description() {
		return "Organisations File Root Directory";
	}
}
