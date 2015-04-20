package com.zyos.alert.integration.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Subject entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "subject")
public class SubjectSAC extends com.zyos.core.common.model.AZyosModel implements
		java.io.Serializable {

	// Fields

	private Long idSubject;
	private String name;
	private Long moreThanOneTeacher;
	private Long hours;
	private transient String groupName;
	private transient Long idGroupSubject;

	// Constructors

	/** default constructor */
	public SubjectSAC() {
	}

	/** minimal constructor */
	public SubjectSAC(String dateCreation, Long state) {
		this.dateCreation = dateCreation;
		this.state = state;
	}

	// Property accessors
	@Id
	@Column(name = "idSubject", nullable = false)
	public Long getIdSubject() {
		return this.idSubject;
	}

	public void setIdSubject(Long idSubject) {
		this.idSubject = idSubject;
	}

	@Column(name = "name", length = 200)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "moreThanOneTeacher")
	public Long getMoreThanOneTeacher() {
		return this.moreThanOneTeacher;
	}

	public void setMoreThanOneTeacher(Long moreThanOneTeacher) {
		this.moreThanOneTeacher = moreThanOneTeacher;
	}

	@Column(name = "hours", nullable = false)
	public Long getHours() {
		return hours;
	}

	public void setHours(Long hours) {
		this.hours = hours;
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

	@Transient
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Transient
	public Long getIdGroupSubject() {
		return idGroupSubject;
	}

	public void setIdGroupSubject(Long idGroupSubject) {
		this.idGroupSubject = idGroupSubject;
	}
}