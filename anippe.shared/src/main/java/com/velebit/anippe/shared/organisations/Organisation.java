package com.velebit.anippe.shared.organisations;

import com.velebit.anippe.shared.country.Country;

import java.util.Date;

public class Organisation implements java.io.Serializable{
	private static final long serialVersionUID = 5L;
	private Integer id;
	private String name;
	private String subdomain;
	private String email;
	private String phone;
	private String website;
	private OrganisationSetting organisationSettings = new OrganisationSetting();
	private Date createdAt;
	private Country country;
	private String street;
	private String city;
	private String postalCode;

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getAddress() {
		return "address";
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubdomain() {
		return subdomain;
	}

	public void setSubdomain(String subdomain) {
		this.subdomain = subdomain;
	}

	public OrganisationSetting getOrganisationSettings() {
		return organisationSettings;
	}

	public void setOrganisationSettings(OrganisationSetting organisationSettings) {
		this.organisationSettings = organisationSettings;
	}
}
