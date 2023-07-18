package com.velebit.anippe.client.email;

import org.eclipse.scout.rt.dataobject.DoEntity;
import org.eclipse.scout.rt.dataobject.DoValue;
import org.eclipse.scout.rt.dataobject.TypeName;

import javax.annotation.Generated;

@TypeName("EmailPredefinedHeaderFooterDoEntity")
public class EmailPredefinedHeaderFooterDoEntity extends DoEntity {

	public DoValue<String> header() {
		return doValue("header");
	}

	public DoValue<String> footer() {
		return doValue("footer");
	}

	/*
	 * **************************************************************************
	 * GENERATED CONVENIENCE METHODS
	 *************************************************************************/

	@Generated("DoConvenienceMethodsGenerator")
	public EmailPredefinedHeaderFooterDoEntity withHeader(String header) {
		header().set(header);
		return this;
	}

	@Generated("DoConvenienceMethodsGenerator")
	public String getHeader() {
		return header().get();
	}

	@Generated("DoConvenienceMethodsGenerator")
	public EmailPredefinedHeaderFooterDoEntity withFooter(String footer) {
		footer().set(footer);
		return this;
	}

	@Generated("DoConvenienceMethodsGenerator")
	public String getFooter() {
		return footer().get();
	}
}
