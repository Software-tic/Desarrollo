package com.zyos.alert.studentReport.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * Subject entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "subject")
public class Subject extends com.zyos.core.common.model.AZyosModel implements
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
	public Subject() {
	}

	/** minimal constructor */
	public Subject(String dateCreation, Long state) {
		this.dateCreation = dateCreation;
		this.state = state;
	}

	/** full constructor */
	public Subject(String name, Long moreThanOneTeacher, String dateCreation,
			String userCreation, String dateChange, String userChange,
			Long state) {
		this.name = name;
		this.moreThanOneTeacher = moreThanOneTeacher;
		this.dateCreation = dateCreation;
		this.userCreation = userCreation;
		this.dateChange = dateChange;
		this.userChange = userChange;
		this.state = state;
	}

	public Subject(Long idSubject, String name) {
		this.idSubject = idSubject;
		this.name = name;
	}

	public Subject(Long id, String name, Long moreThanOneTeacher,
			String groupName, Long idGroupSubject) {
		this.idSubject = id;
		this.name = name;
		this.moreThanOneTeacher = moreThanOneTeacher;
		this.groupName = groupName;
		this.idGroupSubject = idGroupSubject;
	}
	
	public Subject(Long id, String name, Long moreThanOneTeacher) {
		this.idSubject = id;
		this.name = name;
		this.moreThanOneTeacher = moreThanOneTeacher;
		
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
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
	public Long getHours() {
		return hours;
	}

	public void setHours(Long hours) {
		this.hours = hours;
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