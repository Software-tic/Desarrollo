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
		this.dateCreation = datecreation;
		this.dateChange = datechange;
		this.userCreation = usercreation;
		this.userChange = userchange;
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

	@Column(name = "datecreation", length = 20)
	public String getDatecreation() {
		return this.dateCreation;
	}

	public void setDatecreation(String datecreation) {
		this.dateCreation = datecreation;
	}

	@Column(name = "datechange", length = 20)
	public String getDatechange() {
		return this.dateChange;
	}

	public void setDatechange(String datechange) {
		this.dateChange = datechange;
	}

	@Column(name = "usercreation", length = 45)
	public String getUsercreation() {
		return this.userCreation;
	}

	public void setUsercreation(String usercreation) {
		this.userCreation = usercreation;
	}

	@Column(name = "userchange", length = 45)
	public String getUserchange() {
		return this.userChange;
	}

	public void setUserchange(String userchange) {
		this.userChange = userchange;
	}

	@Override
	@Column(name = "state")
	public Long getState() {
		return this.state;
	}

	@Override
	public void setState(Long state) {
		this.state = state;
	}
	
	@JoinColumn(name = "idschool")
	public Long getIdSchool() {
		return idSchool;
	}

	public void setIdSchool(Long idSchool) {
		this.idSchool = idSchool;
	}

	@JoinColumn(name = "idfaculty")
	public Long getIdFaculty() {
		return idFaculty;
	}

	public void setIdFaculty(Long idFaculty) {
		this.idFaculty = idFaculty;
	}

}