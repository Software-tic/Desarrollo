package com.zyos.alert.query.model;

/**
 * AbstractSchoolDegree entity provides the base persistence definition of the
 * SchoolDegree entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractSchoolDegree extends
		com.zyos.core.common.model.AZyosModel implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idSchooldegree;
	private Long idDegree;
	private Long idSchool;

	// Constructors

	/** default constructor */
	public AbstractSchoolDegree() {
	}

	/** minimal constructor */
	public AbstractSchoolDegree(Long idSchooldegree, Long degree,
			Long school) {
		this.idSchooldegree = idSchooldegree;
		this.idDegree = degree;
		this.idSchool = school;
	}

	/** full constructor */
	public AbstractSchoolDegree(Long idSchooldegree, Long degree,
			Long school, String dateCreation, String userCreation,
			String dateChange, String userChange, Long state) {
		this.idSchooldegree = idSchooldegree;
		this.idDegree = degree;
		this.idSchool = school;
		this.dateCreation = dateCreation;
		this.userCreation = userCreation;
		this.dateChange = dateChange;
		this.userChange = userChange;
		this.state = state;
	}

	// Property accessors

	public Long getIdSchooldegree() {
		return this.idSchooldegree;
	}

	public void setIdSchooldegree(Long idSchooldegree) {
		this.idSchooldegree = idSchooldegree;
	}

	public Long getIdDegree() {
		return idDegree;
	}

	public void setIdDegree(Long idDegree) {
		this.idDegree = idDegree;
	}

	public Long getIdSchool() {
		return idSchool;
	}

	public void setIdSchool(Long idSchool) {
		this.idSchool = idSchool;
	}

	public String getDateCreation() {
		return this.dateCreation;
	}

	public void setDateCreation(String dateCreation) {
		this.dateCreation = dateCreation;
	}

	public String getUserCreation() {
		return this.userCreation;
	}

	public void setUserCreation(String userCreation) {
		this.userCreation = userCreation;
	}

	public String getDateChange() {
		return this.dateChange;
	}

	public void setDateChange(String dateChange) {
		this.dateChange = dateChange;
	}

	public String getUserChange() {
		return this.userChange;
	}

	public void setUserChange(String userChange) {
		this.userChange = userChange;
	}

	public Long getState() {
		return this.state;
	}

	public void setState(Long state) {
		this.state = state;
	}

}