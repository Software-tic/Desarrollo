package com.zyos.core.mail.io.mn.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * Emailconfiguration entity. @author Óscar Garzón
 */
@Entity
@Table(name = "emailConfiguration")
public class EmailConfiguration extends com.zyos.core.common.model.AZyosModel
		implements java.io.Serializable {

	// Fields
	private Long id;
	private String smtpHost;
	private Boolean tlsEnable;
	private Long port;
	private String mailUser;
	private String mailPassword;
	private Boolean auth;

	// Constructors

	/** default constructor */
	public EmailConfiguration() {
	}

	public EmailConfiguration(Long id, Long idEnterprise, String smtpHost,
			Boolean tlsEnable, Long port, String mailUser, String mailPassword,
			Boolean auth) {
		this.smtpHost = smtpHost;
		this.tlsEnable = tlsEnable;
		this.port = port;
		this.mailUser = mailUser;
		this.mailPassword = mailPassword;
		this.auth = auth;
	}

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

	@Column(name = "smtphost", nullable = false, length = 100)
	public String getSmtpHost() {
		return this.smtpHost;
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	@Column(name = "tlsenable", nullable = false)
	public Boolean getTlsEnable() {
		return this.tlsEnable;
	}

	public void setTlsEnable(Boolean tlsEnable) {
		this.tlsEnable = tlsEnable;
	}

	@Column(name = "port", nullable = false)
	public Long getPort() {
		return this.port;
	}

	public void setPort(Long port) {
		this.port = port;
	}

	@Column(name = "mailuser", nullable = false, length = 120)
	public String getMailUser() {
		return this.mailUser;
	}

	public void setMailUser(String mailUser) {
		this.mailUser = mailUser;
	}

	@Column(name = "mailpassword", nullable = false, length = 30)
	public String getMailPassword() {
		return this.mailPassword;
	}

	public void setMailPassword(String mailPassword) {
		this.mailPassword = mailPassword;
	}

	@Column(name = "auth", nullable = false)
	public Boolean getAuth() {
		return this.auth;
	}

	public void setAuth(Boolean auth) {
		this.auth = auth;
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