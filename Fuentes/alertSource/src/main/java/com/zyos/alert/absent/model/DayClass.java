package com.zyos.alert.absent.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * Dayclass entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "dayclass")
public class DayClass extends com.zyos.core.common.model.AZyosModel implements
		java.io.Serializable {

	// Fields

	private Long idDayClass;
	private Long idDayCalendar;
	private Long idStudentSubject;
	private String hourStart;
	private String hourEnd;
	private String idSAC;
	private Long idAcademicPeriod;

	private transient String dayClass;
	private transient Long idStudent;

	// Constructors

	/** default constructor */
	public DayClass() {
	}

	/** minimal constructor */
	public DayClass(String dateCreation, Long state) {
		this.dateCreation = dateCreation;
		this.state = state;
	}

	/** full constructor */
	public DayClass(Long idDayCalendar, Long idStudentSubject,
			String dateCreation, String userCreation, String dateChange,
			String userChange, Long state) {
		this.idDayCalendar = idDayCalendar;
		this.idStudentSubject = idStudentSubject;
		this.dateCreation = dateCreation;
		this.userCreation = userCreation;
		this.dateChange = dateChange;
		this.userChange = userChange;
		this.state = state;
	}

	public DayClass(Long idDayClass, Long idDayCalendar, String hourStart,
			String hourEnd, Long idStudent) {
		this.idDayClass = idDayClass;
		this.idDayCalendar = idDayCalendar;
		this.hourStart = hourStart;
		this.hourEnd = hourEnd;
		this.idStudent = idStudent;
	}

	public DayClass(Long idDayClass, Long idDayCalendar, String hourStart,
			String hourEnd) {
		this.idDayClass = idDayClass;
		this.idDayCalendar = idDayCalendar;
		this.hourStart = hourStart;
		this.hourEnd = hourEnd;

	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "idDayClass", nullable = false)
	public Long getIdDayClass() {
		return this.idDayClass;
	}

	public void setIdDayClass(Long idDayClass) {
		this.idDayClass = idDayClass;
	}

	@Column(name = "idDayCalendar")
	public Long getIdDayCalendar() {
		return this.idDayCalendar;
	}

	public void setIdDayCalendar(Long idDayCalendar) {
		this.idDayCalendar = idDayCalendar;
	}

	@Column(name = "idStudentSubject")
	public Long getIdStudentSubject() {
		return this.idStudentSubject;
	}

	public void setIdStudentSubject(Long idStudentSubject) {
		this.idStudentSubject = idStudentSubject;
	}

	@Column(name = "idSAC")
	public String getIdSAC() {
		return idSAC;
	}

	public void setIdSAC(String idSAC) {
		this.idSAC = idSAC;
	}

	@Column(name = "idAcademicPeriod")
	public Long getIdAcademicPeriod() {
		return idAcademicPeriod;
	}

	public void setIdAcademicPeriod(Long idAcademicPeriod) {
		this.idAcademicPeriod = idAcademicPeriod;
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

	@Column(name = "hourstart", nullable = false)
	public String getHourStart() {
		return hourStart;
	}

	public void setHourStart(String hourStart) {
		this.hourStart = hourStart;
	}

	@Column(name = "hourend", nullable = false)
	public String getHourEnd() {
		return hourEnd;
	}

	public void setHourEnd(String hourEnd) {
		this.hourEnd = hourEnd;
	}

	@Transient
	public String getDayClass() {
		return dayClass;
	}

	public void setDayClass(String dayClass) {
		this.dayClass = dayClass;
	}

	@Transient
	public Long getIdStudent() {
		return idStudent;
	}

	public void setIdStudent(Long idStudent) {
		this.idStudent = idStudent;
	}

}