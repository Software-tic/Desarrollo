package com.zyos.core.lo.enterprise.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.zyos.core.common.model.AZyosModel;

/**
 * Zyosuserenterprise entity. @author Óscar Garzón
 */
@Entity
@Table(name = "zyosUserEnterprise")
public class ZyosUserEnterprise extends AZyosModel implements
		java.io.Serializable {

	// Fields
	private Long id;
	private Long idEnterprise;
	private Long idZyosUser;

	// Constructors

	/** default constructor */
	public ZyosUserEnterprise() {
	}

	/** minimal constructor */
	public ZyosUserEnterprise(Long idZyosUser, Long idEnterprise,
			String userCreation, boolean isNew) {
		this.idEnterprise = idEnterprise;
		this.idZyosUser = idZyosUser;
		initializing(userCreation, isNew);
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

	public void setIdEnterprise(Long idEnterprise) {
		this.idEnterprise = idEnterprise;
	}

	@Column(name = "idZyosUser", nullable = false)
	public Long getIdZyosUser() {
		return this.idZyosUser;
	}

	public void setIdZyosUser(Long idZyosUser) {
		this.idZyosUser = idZyosUser;
	}

	@Column(name = "dateCreation", nullable = false, length = 20)
	public String getDateCreation() {
		return this.dateCreation;
	}

	public void setDateCreation(String dateCreation) {
		this.dateCreation = dateCreation;
	}

	@Column(name = "userCreation", nullable = false, length = 45)
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

	@Column(name = "userChange", length = 45)
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