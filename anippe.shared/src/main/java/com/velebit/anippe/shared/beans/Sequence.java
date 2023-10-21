package com.velebit.anippe.shared.beans;

public class Sequence implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	protected String identifier;
	protected Integer sequence;
	protected String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

}
