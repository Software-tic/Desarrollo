package com.zyos.alert.facultyDegree.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

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
	private transient String Faculty,Degree,DegreeDescription;
	

	public FacultyDegree() {
	}	

	public FacultyDegree(Long idFacultyDegree, Long idFaculty, Long idDegree) {
		this.idFacultyDegree = idFacultyDegree;
		this.idFaculty = idFaculty;
		this.idDegree = idDegree;
	}
	
	public FacultyDegree(Long idFacultyDegree, Long idFaculty, String namef, Long idDegree, String named, String descriptiond) {
		this.idFacultyDegree = idFacultyDegree;
		this.idFaculty = idFaculty;
		this.idDegree = idDegree;
		this.Faculty=namef;
		this.Degree=named;
		this.DegreeDescription=descriptiond;
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

	@Override
	@Column(name = "dateCreation", nullable = false, length = 20)
	public String getDateCreation() {
		return this.dateCreation;
	}

	@Override
	public void setDateCreation(String dateCreation) {
		this.dateCreation = dateCreation;
	}

	@Override
	@Column(name = "userCreation", length = 45)
	public String getUserCreation() {
		return this.userCreation;
	}

	@Override
	public void setUserCreation(String userCreation) {
		this.userCreation = userCreation;
	}

	@Override
	@Column(name = "dateChange", length = 20)
	public String getDateChange() {
		return this.dateChange;
	}

	@Override
	public void setDateChange(String dateChange) {
		this.dateChange = dateChange;
	}

	@Override
	@Column(name = "userChange", length = 45)
	public String getUserChange() {
		return this.userChange;
	}

	@Override
	public void setUserChange(String userChange) {
		this.userChange = userChange;
	}

	@Override
	@Column(name = "state", nullable = false)
	public Long getState() {
		return this.state;
	}

	@Override
	public void setState(Long state) {
		this.state = state;
	}
	
	@Transient
	public String getFaculty() {
		return Faculty;
	}

	public void setFaculty(String faculty) {
		Faculty = faculty;
	}
	
	@Transient
	public String getDegree() {
		return Degree;
	}

	public void setDegree(String degree) {
		Degree = degree;
	}
	
	@Transient
	public String getDegreeDescription() {
		return DegreeDescription;
	}

	public void setDegreeDescription(String degreeDescription) {
		DegreeDescription = degreeDescription;
	}

}
