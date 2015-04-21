package com.zyos.core.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * City entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "zyosParameter")
public class ZyosParameter extends com.zyos.core.common.model.AParameter
		implements java.io.Serializable {

	// Fields
	private Long id;
	private Integer globalParameter;
	private String className;

	// Constructors

	/** default constructor */
	public ZyosParameter() {
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

	@Column(name = "globalParameter", nullable = false)
	public Integer getGlobalParameter() {
		return globalParameter;
	}

	public void setGlobalParameter(Integer globalParameter) {
		this.globalParameter = globalParameter;
	}

	@Column(name = "className", nullable = false)
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	@Column(name = "name", nullable = false)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "description")
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

	@Column(name = "state", nullable = false)
	public Long getState() {
		return this.state;
	}

	public void setState(Long state) {
		this.state = state;
	}

	@Override
	public Long getIdFacultad() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setIdFacultad(Long IdFacultad) {
		// TODO Auto-generated method stub
		
	}

}