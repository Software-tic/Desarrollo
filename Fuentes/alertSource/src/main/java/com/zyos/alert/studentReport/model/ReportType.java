package com.zyos.alert.studentReport.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * Reporttype entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "reporttype")
public class ReportType extends com.zyos.core.common.model.AParameter implements
		java.io.Serializable {

	// Fields

	// Constructors

	/** default constructor */
	public ReportType() {
	}

	/** minimal constructor */
	public ReportType(String dateCreation, Long state) {
		this.dateCreation = dateCreation;
		this.state = state;
	}

	/** full constructor */
	public ReportType(String name, String description, String dateCreation,
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
	@Column(name = "id", nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long idDayCalendar) {
		this.id = idDayCalendar;
	}

	@Column(name = "name", length = 120)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "description", length = 256)
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

	@Column(name = "userCreation", length = 45)
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