package com.zyos.alert.sac.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * Degree entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Integra")
public class Integra implements java.io.Serializable {

	// Fields

	private Long id;
	private Long idAcademicPeriod;
	private Long idCourseMoodle;
	private Long idSubject;
	private Long idGroupSuject;
	private String groupName;
	private String shortname;

	// Constructors

	/** default constructor */
	public Integra() {
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long idDayCalendar) {
		this.id = idDayCalendar;
	}

	@Column(name = "idCourseMoodle")
	public Long getIdCourseMoodle() {
		return idCourseMoodle;
	}

	public void setIdCourseMoodle(Long idCourseMoodle) {
		this.idCourseMoodle = idCourseMoodle;
	}

	@Column(name = "idSubject")
	public Long getIdSubject() {
		return idSubject;
	}

	public void setIdSubject(Long idSubject) {
		this.idSubject = idSubject;
	}

	@Column(name = "idGroupSuject")
	public Long getIdGroupSuject() {
		return idGroupSuject;
	}

	public void setIdGroupSuject(Long idGroupSuject) {
		this.idGroupSuject = idGroupSuject;
	}

	@Column(name = "groupName")
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Column(name = "idAcademicPeriod")
	public Long getIdAcademicPeriod() {
		return idAcademicPeriod;
	}

	public void setIdAcademicPeriod(Long idAcademicPeriod) {
		this.idAcademicPeriod = idAcademicPeriod;
	}

	@Column(name = "shortname")
	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

}