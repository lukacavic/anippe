package com.velebit.anippe.shared.beans;

import com.velebit.anippe.shared.organisations.Organisation;
import org.eclipse.scout.rt.platform.util.StringUtility;

import java.util.Date;

public class User implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String firstName;
	private String lastName;
	private Organisation organisation = new Organisation();
	private String username;
	private String password;
	private boolean administrator;
	private boolean active;
	private boolean superAdministrator;
	private Date lastLoginAt;
	private Date createdAt;
	private String email;

	public String getFullName() {
		return StringUtility.join(" ", firstName, lastName);
	}

	public Date getLastLoginAt() {
		return lastLoginAt;
	}

	public void setLastLoginAt(Date lastLoginAt) {
		this.lastLoginAt = lastLoginAt;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isSuperAdministrator() {
		return superAdministrator;
	}

	public void setSuperAdministrator(boolean superAdministrator) {
		this.superAdministrator = superAdministrator;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAdministrator() {
		return administrator;
	}

	public void setAdministrator(boolean administrator) {
		this.administrator = administrator;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Organisation getOrganisation() {
		return organisation;
	}

	public void setOrganisation(Organisation organisation) {
		this.organisation = organisation;
	}
}
