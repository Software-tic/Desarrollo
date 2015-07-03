package com.zyos.alert.query.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;

/**
 * AbstractFacultySchool entity provides the base persistence definition of the
 * FacultySchool entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractFacultySchool extends
		com.zyos.core.common.model.AZyosModel implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idfacultyschool;
	private Long idSchool;
	private Long idFaculty;
	private String datecreation;
	private String datechange;
	private String usercreation;
	private String userchange;
	private Long state;

	// Constructors

	/** default constructor */
	public AbstractFacultySchool() {
	}

	/** minimal constructor */
	public AbstractFacultySchool(Long idfacultyschool) {
		this.idfacultyschool = idfacultyschool;
	}

	/** full constructor */
	public AbstractFacultySchool(Long idfacultyschool, Long school,
			Long faculty, String datecreation, String datechange,
			String usercreation, String userchange, Long state) {
		this.idfacultyschool = idfacultyschool;
		this.idSchool = school;
		this.idFaculty = faculty;
		this.datecreation = datecreation;
		this.datechange = datechange;
		this.usercreation = usercreation;
		this.userchange = userchange;
		this.state = state;
	}

	// Property accessors
	@Id
	@Column(name = "idfacultyschool", unique = true, nullable = false)
	public Long getIdfacultyschool() {
		return this.idfacultyschool;
	}

	public void setIdfacultyschool(Long idfacultyschool) {
		this.idfacultyschool = idfacultyschool;
	}

	@JoinColumn(name = "idschool")
	public Long getSchool() {
		return this.idSchool;
	}

	public void setSchool(Long school) {
		this.idSchool = school;
	}

	@JoinColumn(name = "idfaculty")
	public Long getFaculty() {
		return this.idFaculty;
	}

	public void setFaculty(Long faculty) {
		this.idFaculty = faculty;
	}

	@Column(name = "datecreation", length = 20)
	public String getDatecreation() {
		return this.datecreation;
	}

	public void setDatecreation(String datecreation) {
		this.datecreation = datecreation;
	}

	@Column(name = "datechange", length = 20)
	public String getDatechange() {
		return this.datechange;
	}

	public void setDatechange(String datechange) {
		this.datechange = datechange;
	}

	@Column(name = "usercreation", length = 45)
	public String getUsercreation() {
		return this.usercreation;
	}

	public void setUsercreation(String usercreation) {
		this.usercreation = usercreation;
	}

	@Column(name = "userchange", length = 45)
	public String getUserchange() {
		return this.userchange;
	}

	public void setUserchange(String userchange) {
		this.userchange = userchange;
	}

	@Column(name = "state")
	public Long getState() {
		return this.state;
	}

	public void setState(Long state) {
		this.state = state;
	}

}