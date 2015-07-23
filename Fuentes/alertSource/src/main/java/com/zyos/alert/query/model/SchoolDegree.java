package com.zyos.alert.query.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

/**
 * SchoolDegree entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "school_degree")
public class SchoolDegree extends com.zyos.core.common.model.AZyosModel
		implements java.io.Serializable {

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
	public SchoolDegree() {
	}

	/** minimal constructor */
	public SchoolDegree(Long idSchooldegree, Long degree, Long school) {
		this.idSchooldegree = idSchooldegree;
		this.idDegree = degree;
		this.idSchool = school;
	}

	/** full constructor */
	public SchoolDegree(Long idSchooldegree, Long degree, Long school,
			String datecreation, String usercreation, String datechange,
			String userchange, Long state) {
		this.idSchooldegree = idSchooldegree;
		this.idDegree = degree;
		this.idSchool = school;
		this.dateCreation = datecreation;
		this.userCreation = usercreation;
		this.dateChange = datechange;
		this.userChange = userchange;
		this.state = state;
	}

	// Property accessors
	@Id
	@Column(name = "id_schooldegree", unique = true, nullable = false)
	public Long getIdSchooldegree() {
		return this.idSchooldegree;
	}

	public void setIdSchooldegree(Long idSchooldegree) {
		this.idSchooldegree = idSchooldegree;
	}	

	@Column(name = "datecreation", length = 20)
	public String getDatecreation() {
		return this.dateCreation;
	}

	public void setDatecreation(String datecreation) {
		this.dateCreation = datecreation;
	}

	@Column(name = "usercreation", length = 45)
	public String getUsercreation() {
		return this.userCreation;
	}

	public void setUsercreation(String usercreation) {
		this.userCreation = usercreation;
	}

	@Column(name = "datechange", length = 20)
	public String getDatechange() {
		return this.dateChange;
	}

	public void setDatechange(String datechange) {
		this.dateChange = datechange;
	}

	@Column(name = "userchange", length = 45)
	public String getUserchange() {
		return this.userChange;
	}

	public void setUserchange(String userchange) {
		this.userChange = userchange;
	}

	@Column(name = "state")
	public Long getState() {
		return this.state;
	}

	public void setState(Long state) {
		this.state = state;
	}

	@JoinColumn(name = "id_school", nullable = false)
	public Long getIdSchool() {
		return idSchool;
	}

	public void setIdSchool(Long idSchool) {
		this.idSchool = idSchool;
	}
	
	@JoinColumn(name = "id_degree", nullable = false)
	public Long getIdDegree() {
		return idDegree;
	}

	public void setIdDegree(Long idDegree) {
		this.idDegree = idDegree;
	}

}