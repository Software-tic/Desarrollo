package com.zyos.alert.facultyDegree.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "facultydegree")
public class FacultyDegree extends com.zyos.core.common.model.AZyosModel
		implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idFacultyDegree;
	private Long idFaculty;
	private Long idDegree;
	

	public FacultyDegree() {	
	}	

	public FacultyDegree(Long idFacultyDegree, Long idFaculty, Long idDegree) {
		this.idFacultyDegree = idFacultyDegree;
		this.idFaculty = idFaculty;
		this.idDegree = idDegree;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "idFacultyDegree", nullable = false)
	public Long getIdFacultyDegree() {
		return idFacultyDegree;
	}

	public void setIdFacultyDegree(Long idFacultyDegree) {
		this.idFacultyDegree = idFacultyDegree;
	}

	@Column(name = "idFaculty", nullable = true)
	public Long getIdFaculty() {
		return idFaculty;
	}

	public void setIdFaculty(Long idFaculty) {
		this.idFaculty = idFaculty;
	}

	@Column(name = "idDegree", nullable = true)
	public Long getIdDegree() {
		return idDegree;
	}

	public void setIdDegree(Long idDegree) {
		this.idDegree = idDegree;
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

}
