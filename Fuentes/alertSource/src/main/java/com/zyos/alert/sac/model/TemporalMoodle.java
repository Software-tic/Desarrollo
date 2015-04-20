package com.zyos.alert.sac.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MdlCourse entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TemporalMoodle")
public class TemporalMoodle implements java.io.Serializable {

	// Fields

	private Long id;
	private String shortname;
	private Long idSubject;
	private Long idGroup;
	private String groupName;
	private String academicPeriodName;

	// Constructors

	/** default constructor */
	public TemporalMoodle() {
	}

	/** @author ogarzonm*/
	public TemporalMoodle(Long id, String academicPeriod, String idSubject,
			String groupName, String shortname) throws Exception {
		try {
			this.shortname = shortname;
			this.id = id;
			this.academicPeriodName = academicPeriod;
			this.idSubject = Long.valueOf(idSubject);
			this.groupName = groupName;
		} catch (Exception e) {
			throw e;
		}
	}

	// Property accessors
	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "shortname", nullable = false)
	public String getShortname() {
		return this.shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	@Column(name = "idSubject")
	public Long getIdSubject() {
		return idSubject;
	}

	public void setIdSubject(Long idSubject) {
		this.idSubject = idSubject;
	}

	@Column(name = "groupName")
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Column(name = "academicPeriodName")
	public String getAcademicPeriodName() {
		return academicPeriodName;
	}

	public void setAcademicPeriodName(String academicPeriodName) {
		this.academicPeriodName = academicPeriodName;
	}

	@Column(name = "idGroup")
	public Long getIdGroup() {
		return idGroup;
	}

	public void setIdGroup(Long idGroup) {
		this.idGroup = idGroup;
	}

}