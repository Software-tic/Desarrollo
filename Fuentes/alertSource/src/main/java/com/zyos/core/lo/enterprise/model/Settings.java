package com.zyos.core.lo.enterprise.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * Settings entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "settings")
public class Settings extends com.zyos.core.common.model.AZyosModel implements
		java.io.Serializable {

	// Fields
	private Long id;
	private Long idEnterprise;
	private Long users;
	private String modal;
	private String css;
	private String url;
	private String urladdcd;
	private Integer showexpirecd;
	private Long storage;
	private String version;
	private String dateStart;
	private Integer afterDueNotice;
	private String dateExpiration;
	private Integer incrementSetting;

	// Constructors

	/** default constructor */
	public Settings() {
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

	@Column(name = "idEnterprise", nullable = false)
	public Long getIdEnterprise() {
		return this.idEnterprise;
	}

	public void setidEnterprise(Long idEnterprise) {
		this.idEnterprise = idEnterprise;
	}

	@Column(name = "users")
	public Long getUsers() {
		return this.users;
	}

	public void setUsers(Long users) {
		this.users = users;
	}

	@Column(name = "modal", length = 15)
	public String getModal() {
		return this.modal;
	}

	public void setModal(String modal) {
		this.modal = modal;
	}

	@Column(name = "css", length = 20)
	public String getCss() {
		return this.css;
	}

	public void setCss(String css) {
		this.css = css;
	}

	@Column(name = "url", length = 512)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "urladdcd", length = 512)
	public String getUrladdcd() {
		return this.urladdcd;
	}

	public void setUrladdcd(String urladdcd) {
		this.urladdcd = urladdcd;
	}

	@Column(name = "showexpirecd")
	public Integer getShowexpirecd() {
		return this.showexpirecd;
	}

	public void setShowexpirecd(Integer showexpirecd) {
		this.showexpirecd = showexpirecd;
	}

	@Column(name = "storage")
	public Long getStorage() {
		return this.storage;
	}

	public void setStorage(Long storage) {
		this.storage = storage;
	}

	@Column(name = "version", length = 20)
	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Column(name = "dateStart", length = 20)
	public String getDateStart() {
		return this.dateStart;
	}

	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}

	@Column(name = "afterDueNotice")
	public Integer getafterDueNotice() {
		return this.afterDueNotice;
	}

	public void setafterDueNotice(Integer afterDueNotice) {
		this.afterDueNotice = afterDueNotice;
	}

	@Column(name = "dateExpiration", length = 20)
	public String getdateExpiration() {
		return this.dateExpiration;
	}

	public void setdateExpiration(String dateExpiration) {
		this.dateExpiration = dateExpiration;
	}

	@Override
	@Column(name = "dateCreation", nullable = false, length = 20)
	public String getDateCreation() {
		return this.dateCreation;
	}

	@Override
	public void setDateCreation(String dateCreation) {
		this.dateCreation = dateCreation;
	}

	@Override
	@Column(name = "userCreation", nullable = false, length = 45)
	public String getUserCreation() {
		return this.userCreation;
	}

	@Override
	public void setUserCreation(String userCreation) {
		this.userCreation = userCreation;
	}

	@Override
	@Column(name = "dateChange", length = 20)
	public String getDateChange() {
		return this.dateChange;
	}

	@Override
	public void setDateChange(String dateChange) {
		this.dateChange = dateChange;
	}

	@Override
	@Column(name = "userChange", length = 45)
	public String getUserChange() {
		return this.userChange;
	}

	@Override
	public void setUserChange(String userChange) {
		this.userChange = userChange;
	}

	@Override
	@Column(name = "state", nullable = false)
	public Long getState() {
		return this.state;
	}

	@Override
	public void setState(Long state) {
		this.state = state;
	}

	@Column(name = "increment")
	public Integer getIncrement() {
		return this.incrementSetting;
	}

	public void setIncrement(Integer increment) {
		this.incrementSetting = increment;
	}

}