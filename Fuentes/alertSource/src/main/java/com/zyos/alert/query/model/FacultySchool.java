package com.zyos.alert.query.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.zyos.alert.faculty.model.Faculty;

/**
<<<<<<< HEAD
 * MdlCourse entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "faculty_school")
public class FacultySchool implements java.io.Serializable {
=======
 * FacultySchool entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "faculty_school")
public class FacultySchool extends com.zyos.core.common.model.AZyosModel 
	implements java.io.Serializable {

	// Fields
>>>>>>> origin/master

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
<<<<<<< HEAD
	// Fields
	private Long idfacultyschool;
	private Faculty faculty;
=======
	private Long idfacultyschool;
>>>>>>> origin/master
	private String datecreation;
	private String datechange;
	private String usercreation;
	private String userchange;
	private Long state;
	
	private transient Long school;
<<<<<<< HEAD
=======
	private transient Long faculty;

>>>>>>> origin/master
	// Constructors

	/** default constructor */
	public FacultySchool() {
	}

	/** minimal constructor */
	public FacultySchool(Long idfacultyschool) {
		this.idfacultyschool = idfacultyschool;
	}

	/** full constructor */
<<<<<<< HEAD
	public FacultySchool(Long idfacultyschool, Long school, Faculty faculty,
=======
	public FacultySchool(Long idfacultyschool, Long school, Long faculty,
>>>>>>> origin/master
			String datecreation, String datechange, String usercreation,
			String userchange, Long state) {
		this.idfacultyschool = idfacultyschool;
		this.school = school;
		this.faculty = faculty;
		this.datecreation = datecreation;
		this.datechange = datechange;
		this.usercreation = usercreation;
		this.userchange = userchange;
		this.state = state;
	}

	// Property accessors
	@Id
	@Column(name = "idfacultyschool", unique = true, nullable = false)
	public Long getIdfacultyschool() {
		return this.idfacultyschool;
	}

	public void setIdfacultyschool(Long idfacultyschool) {
		this.idfacultyschool = idfacultyschool;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idschool")
	public Long getSchool() {
		return this.school;
	}

	public void setSchool(Long school) {
		this.school = school;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idfaculty")
<<<<<<< HEAD
	public Faculty getFaculty() {
		return this.faculty;
	}

	public void setFaculty(Faculty faculty) {
=======
	public Long getFaculty() {
		return this.faculty;
	}

	public void setFaculty(Long faculty) {
>>>>>>> origin/master
		this.faculty = faculty;
	}

	@Column(name = "datecreation", length = 20)
	public String getDatecreation() {
		return this.datecreation;
	}

	public void setDatecreation(String datecreation) {
		this.datecreation = datecreation;
	}

	@Column(name = "datechange", length = 20)
	public String getDatechange() {
		return this.datechange;
	}

	public void setDatechange(String datechange) {
		this.datechange = datechange;
	}

	@Column(name = "usercreation", length = 45)
	public String getUsercreation() {
		return this.usercreation;
	}

	public void setUsercreation(String usercreation) {
		this.usercreation = usercreation;
	}

	@Column(name = "userchange", length = 45)
	public String getUserchange() {
		return this.userchange;
	}

	public void setUserchange(String userchange) {
		this.userchange = userchange;
	}

	@Column(name = "state")
	public Long getState() {
		return this.state;
	}

	public void setState(Long state) {
		this.state = state;
	}

}