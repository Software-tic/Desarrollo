package com.zyos.alert.sac.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * EstudiantesMateriasGrupos entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "NotRecordStudentSubjectDay") 
public class NotRecordStudentSubjectDay implements java.io.Serializable {

	// Fields
	private String idSAC;
	private String idSubject;
	private Long idStudent;
	private Long idDayCalendar;

	private String hourStart;
	private String hourEnd;

	// Constructors

	/** default constructor */
	public NotRecordStudentSubjectDay() {
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

	@Column(name = "idDayCalendar")
	public Long getIdDayCalendar() {
		return idDayCalendar;
	}

	public void setIdDayCalendar(Long idDayCalendar) {
		this.idDayCalendar = idDayCalendar;
	}

	@Column(name = "hourStart")
	public String getHourStart() {
		return hourStart;
	}

	public void setHourStart(String hourStart) {
		this.hourStart = hourStart;
	}

	@Column(name = "hourEnd")
	public String getHourEnd() {
		return hourEnd;
	}

	public void setHourEnd(String hourEnd) {
		this.hourEnd = hourEnd;
	}

}