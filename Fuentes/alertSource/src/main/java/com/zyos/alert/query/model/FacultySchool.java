package com.zyos.alert.query.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.zyos.core.common.model.AZyosModel;

/**
 * MdlCourse entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "faculty_school")
public class FacultySchool extends AZyosModel implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields
	private Long id;
	private transient Long faculty;
	private transient Long school;
	// Constructors

	/** default constructor */
	public FacultySchool() {
	}

	/** minimal constructor */
	public FacultySchool(Long idfacultyschool) {
		this.id = idfacultyschool;
	}

	/** full constructor */
	public FacultySchool(Long idfacultyschool, Long school, Long faculty,
			String datecreation, String datechange, String usercreation,
			String userchange, Long state) {
		this.id = idfacultyschool;
		this.school = school;
		this.setFaculty(faculty);
		this.dateCreation = datecreation;
		this.dateChange = datechange;
		this.userCreation = usercreation;
		this.userChange = userchange;
		this.state = state;
	}

	// Property accessors
	@Id
	@Column(name = "idfacultyschool", unique = true, nullable = false)
	public Long getIdfacultyschool() {
		return this.id;
	}

	public void setIdfacultyschool(Long idfacultyschool) {
		this.id = idfacultyschool;
	}

	@JoinColumn(name = "idschool")
	public Long getSchool() {
		return this.school;
	}

	public void setSchool(Long school) {
		this.school = school;
	}

	@JoinColumn(name = "idfaculty")
	public Long getFaculty() {
		return faculty;
	}

	public void setFaculty(Long faculty) {
		this.faculty = faculty;
	}

}