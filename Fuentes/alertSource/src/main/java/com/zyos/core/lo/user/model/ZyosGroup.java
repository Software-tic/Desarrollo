package com.zyos.core.lo.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.zyos.core.common.model.AParameter;

/**
 * Zyosgroup entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "zyosgroup")
public class ZyosGroup extends AParameter implements java.io.Serializable {
	private Long id;
	// Constructors

	
	/** default constructor */
	public ZyosGroup() {
	}
	
	public ZyosGroup(String name, String description) {
		this.name = name;
		this.description = description;
	}

	/** minimal constructor */
	public ZyosGroup(String name, String dateCreation, String userCreation,
			Long state) {
		this.name = name;
		this.dateCreation = dateCreation;
		this.userCreation = userCreation;
		this.state = state;
	}

	/** full constructor */
	public ZyosGroup(String name, String description, String dateCreation,
			String userCreation, String dateChange, String userChange,
			Long state) {
		this.name = name;
		this.description = description;
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

	@Column(name = "name", nullable = false, length = 45)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "description", length = 150)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	@Column(name = "state", nullable = false, length = 1)
	public Long getState() {
		return this.state;
	}

	public void setState(Long state) {
		this.state = state;
	}

}