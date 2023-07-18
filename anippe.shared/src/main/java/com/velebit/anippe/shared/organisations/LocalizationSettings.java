package com.velebit.anippe.shared.organisations;

public class LocalizationSettings implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String dateFormat;
	private String timeFormat;
	private Long languageId;

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String getTimeFormat() {
		return timeFormat;
	}

	public void setTimeFormat(String timeFormat) {
		this.timeFormat = timeFormat;
	}

}
