package com.zyos.alert.sac.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * EstudiantesMateriasGrupos entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "NotRecordStudentSubject")
public class NotRecordStudentSubject implements java.io.Serializable {

	// Fields
	private String idSAC;
	private String idSubject;
	private Long idStudent;
	private Long idGroupSubject;
	
	// Constructors

	/** default constructor */
	public NotRecordStudentSubject() {
	}

	// Property accessors
	@Id
	@Column(name = "idSAC")
	public String getIdSAC() {
		return this.idSAC;
	}

	public void setIdSAC(String idSAC) {
		this.idSAC = idSAC;
	}

	@Column(name = "idSubject")
	public String getIdSubject() {
		return idSubject;
	}

	public void setIdSubject(String idSubject) {
		this.idSubject = idSubject;
	}

	@Column(name = "idStudent")
	public Long getIdStudent() {
		return idStudent;
	}

	public void setIdStudent(Long idStudent) {
		this.idStudent = idStudent;
	}

	@Column(name = "idGroupSubject")
	public Long getIdGroupSubject() {
		return idGroupSubject;
	}

	public void setIdGroupSubject(Long idGroupSubject) {
		this.idGroupSubject = idGroupSubject;
	}

	
}