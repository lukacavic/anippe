package com.velebit.anippe.server.config;

import org.eclipse.scout.rt.platform.config.AbstractStringConfigProperty;

public class AppFileRootDirectoryConfigProperty extends AbstractStringConfigProperty {

	@Override
	public String getKey() {
		return "app.fileRootDirectory";
	}

	@Override
	public String description() {
		return "File Root Directory";
	}
}
