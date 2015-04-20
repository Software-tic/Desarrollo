package com.zyos.core.common.util.auth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.zyos.core.common.model.AZyosModel;

@Entity
@Table(name = "ZyosAuth")
public class ZyosAuth extends AZyosModel {

	private Long id;
	private Long idEnterprise;
	private String ProviderUrl;
	private String SecurityAuthentication;
	private String AuthDomain;

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "idEnterprise")
	public Long getIdEnterprise() {
		return idEnterprise;
	}

	public void setIdEnterprise(Long idEnterprise) {
		this.idEnterprise = idEnterprise;
	}

	@Column(name = "ProviderUrl")
	public String getProviderUrl() {
		return ProviderUrl;
	}

	public void setProviderUrl(String providerUrl) {
		ProviderUrl = providerUrl;
	}

	@Column(name = "SecurityAuthentication")
	public String getSecurityAuthentication() {
		return SecurityAuthentication;
	}

	public void setSecurityAuthentication(String securityAuthentication) {
		SecurityAuthentication = securityAuthentication;
	}

	@Column(name = "AuthDomain")
	public String getAuthDomain() {
		return AuthDomain;
	}

	public void setAuthDomain(String authDomain) {
		AuthDomain = authDomain;
	}

	@Column(name = "dateCreation", nullable = false, length = 20)
	public String getDateCreation() {
		return this.dateCreation;
	}

	public void setDateCreation(String dateCreation) {
		this.dateCreation = dateCreation;
	}

	@Column(name = "userCreation", nullable = false, length = 15)
	public String getUserCreation() {
		return this.userCreation;
	}

	public void setUserCreation(String userCreation) {
		this.userCreation = userCreation;
	}

	@Column(name = "dateChange", length = 20)
	public String getDateChange() {
		return this.dateChange;
	}

	public void setDateChange(String dateChange) {
		this.dateChange = dateChange;
	}

	@Column(name = "userChange", length = 15)
	public String getUserChange() {
		return this.userChange;
	}

	public void setUserChange(String userChange) {
		this.userChange = userChange;
	}

	@Column(name = "state", nullable = false)
	public Long getState() {
		return this.state;
	}

	public void setState(Long state) {
		this.state = state;
	}
}
