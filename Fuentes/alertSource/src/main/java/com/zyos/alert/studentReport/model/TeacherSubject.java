package com.zyos.alert.studentReport.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * Teachersubject entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "teachersubject")
public class TeacherSubject extends com.zyos.core.common.model.AZyosModel
		implements java.io.Serializable {

	// Fields

	private Long idTeacherSubject;
	private Long idSubject;
	private Long idAcademicPeriod;
	private Long idZyosUser;
	private Long idGroupSubject;
	private String idSAC;

	// Constructors

	/** default constructor */
	public TeacherSubject() {
	}

	/** minimal constructor */
	public TeacherSubject(String dateCreation, Long state) {
		this.dateCreation = dateCreation;
		this.state = state;
	}

	/** full constructor */
	public TeacherSubject(String dateCreation, String userCreation,
			String dateChange, String userChange, Long state, Long idSubject,
			Long idAcademicPeriod, Long idZyosUser) {
		this.dateCreation = dateCreation;
		this.userCreation = userCreation;
		this.dateChange = dateChange;
		this.userChange = userChange;
		this.state = state;
		this.idSubject = idSubject;
		this.idAcademicPeriod = idAcademicPeriod;
		this.idZyosUser = idZyosUser;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "idTeacherSubject", nullable = false)
	public Long getIdTeacherSubject() {
		return this.idTeacherSubject;
	}

	public void setIdTeacherSubject(Long idTeacherSubject) {
		this.idTeacherSubject = idTeacherSubject;
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

	@Column(name = "idGroupSubject")
	public Long getIdGroupSubject() {
		return idGroupSubject;
	}

	public void setIdGroupSubject(Long idGroupSubject) {
		this.idGroupSubject = idGroupSubject;
	}

	@Column(name = "idSAC")
	public String getIdSAC() {
		return idSAC;
	}

	public void setIdSAC(String idSAC) {
		this.idSAC = idSAC;
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

	@Column(name = "idSubject")
	public Long getIdSubject() {
		return this.idSubject;
	}

	public void setIdSubject(Long idSubject) {
		this.idSubject = idSubject;
	}

	@Column(name = "idAcademicPeriod")
	public Long getIdAcademicPeriod() {
		return this.idAcademicPeriod;
	}

	public void setIdAcademicPeriod(Long idAcademicPeriod) {
		this.idAcademicPeriod = idAcademicPeriod;
	}

	@Column(name = "idZyosUser")
	public Long getIdZyosUser() {
		return this.idZyosUser;
	}

	public void setIdZyosUser(Long idZyosUser) {
		this.idZyosUser = idZyosUser;
	}

}