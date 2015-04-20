package com.zyos.core.lo.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.zyos.core.common.model.AZyosModel;

/**
 * Zyosusergroup entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "zyosUserGroup")
public class ZyosUserGroup extends AZyosModel implements java.io.Serializable {
	
	// Fields
	private Long id;
	private Long idGroup;
	private Long idZyosUser;

	// Constructors

	/** default constructor */
	public ZyosUserGroup() {
	}

	/** minimal constructor */
	public ZyosUserGroup(Long idZyosUser, Long idGroup, String userCreation,
			boolean isNew) {
		this.idGroup = idGroup;
		this.idZyosUser = idZyosUser;
		initializing(userCreation, isNew);
	}

	/** full constructor */
	public ZyosUserGroup(Long idGroup, Long idZyosUser, String dateCreation,
			String userCreation, String dateChange, String userChange,
			Long state) {
		this.idGroup = idGroup;
		this.idZyosUser = idZyosUser;
		this.dateCreation = dateCreation;

		this.userCreation = userCreation;
		this.dateChange = dateChange;

		this.userChange = userChange;
		this.state = state;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, precision = 65535, scale = 65531)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "idGroup", nullable = false, precision = 65535, scale = 65531)
	public Long getIdGroup() {
		return this.idGroup;
	}

	public void setIdGroup(Long idRole) {
		this.idGroup = idRole;
	}

	@Column(name = "idZyosUser", nullable = false, precision = 65535, scale = 65531)
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

	@Column(name = "state", nullable = false, precision = 65535, scale = 65531)
	public Long getState() {
		return this.state;
	}

	public void setState(Long state) {
		this.state = state;
	}

}