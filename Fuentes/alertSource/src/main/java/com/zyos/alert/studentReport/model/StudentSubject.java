package com.zyos.alert.studentReport.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * Studentsubject entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "studentsubject")
public class StudentSubject extends com.zyos.core.common.model.AZyosModel
		implements java.io.Serializable {

	// Fields

	private Long idStudentSubject;
	private Long idStudent;
	private Long idSubject;
	private Long idAcademicPeriod;
	private Long idGroupSubject;
	private String idSAC;

	// Constructors

	/** default constructor */
	public StudentSubject() {
	}

	/** minimal constructor */
	public StudentSubject(String dateCreation, Long state) {
		this.dateCreation = dateCreation;
		this.state = state;
	}

	/** full constructor */
	public StudentSubject(Long idStudentSubject, Long idSubject,
			Long idAcademicPeriod, String dateCreation, String userCreation,
			String dateChange, String userChange, Long state) {
		this.idStudentSubject = idStudentSubject;
		this.idSubject = idSubject;
		this.idAcademicPeriod = idAcademicPeriod;
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
	@Column(name = "idStudentSubject", nullable = false)
	public Long getIdStudentSubject() {
		return this.idStudentSubject;
	}

	public void setIdStudentSubject(Long idStudentSubject) {
		this.idStudentSubject = idStudentSubject;
	}

	@Column(name = "idStudent")
	public Long getIdStudent() {
		return this.idStudent;
	}

	public void setIdStudent(Long idStudent) {
		this.idStudent = idStudent;
	}

	@Column(name = "idSAC")
	public String getIdSAC() {
		return idSAC;
	}

	public void setIdSAC(String idSAC) {
		this.idSAC = idSAC;
	}

	@Column(name = "idSubject")
	public Long getIdSubject() {
		return this.idSubject;
	}

	public void setIdSubject(Long idSubject) {
		this.idSubject = idSubject;
	}

	@Column(name = "idGroupSubject")
	public Long getIdGroupSubject() {
		return idGroupSubject;
	}

	public void setIdGroupSubject(Long idGroupSubject) {
		this.idGroupSubject = idGroupSubject;
	}

	@Column(name = "idAcademicPeriod")
	public Long getIdAcademicPeriod() {
		return this.idAcademicPeriod;
	}

	public void setIdAcademicPeriod(Long idAcademicPeriod) {
		this.idAcademicPeriod = idAcademicPeriod;
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