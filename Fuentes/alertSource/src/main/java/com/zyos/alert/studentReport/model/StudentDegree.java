package com.zyos.alert.studentReport.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * Studentdegree entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "studentdegree")
public class StudentDegree extends com.zyos.core.common.model.AZyosModel
		implements java.io.Serializable {

	// Fields

	private Long idStudentDegree;
	private Long idDegree;
	private Long idStudent;

	// Constructors

	/** default constructor */
	public StudentDegree() {
	}

	/** minimal constructor */
	public StudentDegree(String dateCreation, Long state) {
		this.dateCreation = dateCreation;
		this.state = state;
	}

	/** full constructor */
	public StudentDegree(String dateCreation, String userCreation,
			String dateChange, String userChange, Long state, Long idDegree,
			Long idStudent) {
		this.dateCreation = dateCreation;
		this.userCreation = userCreation;
		this.dateChange = dateChange;
		this.userChange = userChange;
		this.state = state;
		this.idDegree = idDegree;
		this.idStudent = idStudent;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "idStudentDegree", nullable = false)
	public Long getIdStudentDegree() {
		return this.idStudentDegree;
	}

	public void setIdStudentDegree(Long idStudentDegree) {
		this.idStudentDegree = idStudentDegree;
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

	@Column(name = "idDegree")
	public Long getIdDegree() {
		return this.idDegree;
	}

	public void setIdDegree(Long idDegree) {
		this.idDegree = idDegree;
	}

	@Column(name = "idStudent")
	public Long getIdStudent() {
		return this.idStudent;
	}

	public void setIdStudent(Long idStudent) {
		this.idStudent = idStudent;
	}

}