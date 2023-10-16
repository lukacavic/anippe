package com.velebit.anippe.client.config;

import org.eclipse.scout.rt.platform.config.AbstractStringConfigProperty;

public class AppBaseURLConfigProperty extends AbstractStringConfigProperty {

	@Override
	public String getKey() {
		return "app.baseURL";
	}

	@Override
	public String description() {
		return "Base App URL";
	}
}
