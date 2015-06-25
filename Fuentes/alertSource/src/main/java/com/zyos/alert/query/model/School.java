package com.zyos.alert.query.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * School entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "school")
public class School implements java.io.Serializable {

	// Fields

	private Long idschool;
	private String nameSchool;
	private String datecreation;
	private String usercreation;
	private String datechange;
	private String userchange;
	private Long state;
	private Set<Teacher> teachers = new HashSet<Teacher>(0);
	private Set<SchoolCoordinador> schoolCoordinadors = new HashSet<SchoolCoordinador>(
			0);
	private Set<FacultySchool> facultySchools = new HashSet<FacultySchool>(0);

	// Constructors

	/** default constructor */
	public School() {
	}

	/** minimal constructor */
	public School(Long idschool, String nameSchool) {
		this.idschool = idschool;
		this.nameSchool = nameSchool;
	}

	/** full constructor */
	public School(Long idschool, String nameSchool, String datecreation,
			String usercreation, String datechange, String userchange,
			Long state, Set<Teacher> teachers,
			Set<SchoolCoordinador> schoolCoordinadors,
			Set<FacultySchool> facultySchools) {
		this.idschool = idschool;
		this.nameSchool = nameSchool;
		this.datecreation = datecreation;
		this.usercreation = usercreation;
		this.datechange = datechange;
		this.userchange = userchange;
		this.state = state;
		this.teachers = teachers;
		this.schoolCoordinadors = schoolCoordinadors;
		this.facultySchools = facultySchools;
	}

	// Property accessors
	@Id
	@Column(name = "idschool", unique = true, nullable = false)
	public Long getIdschool() {
		return this.idschool;
	}

	public void setIdschool(Long idschool) {
		this.idschool = idschool;
	}

	@Column(name = "name_school", nullable = false, length = 200)
	public String getNameSchool() {
		return this.nameSchool;
	}

	public void setNameSchool(String nameSchool) {
		this.nameSchool = nameSchool;
	}

	@Column(name = "datecreation", length = 20)
	public String getDatecreation() {
		return this.datecreation;
	}

	public void setDatecreation(String datecreation) {
		this.datecreation = datecreation;
	}

	@Column(name = "usercreation", length = 45)
	public String getUsercreation() {
		return this.usercreation;
	}

	public void setUsercreation(String usercreation) {
		this.usercreation = usercreation;
	}

	@Column(name = "datechange", length = 20)
	public String getDatechange() {
		return this.datechange;
	}

	public void setDatechange(String datechange) {
		this.datechange = datechange;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "school")
	public Set<Teacher> getTeachers() {
		return this.teachers;
	}

	public void setTeachers(Set<Teacher> teachers) {
		this.teachers = teachers;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "school")
	public Set<SchoolCoordinador> getSchoolCoordinadors() {
		return this.schoolCoordinadors;
	}

	public void setSchoolCoordinadors(Set<SchoolCoordinador> schoolCoordinadors) {
		this.schoolCoordinadors = schoolCoordinadors;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "school")
	public Set<FacultySchool> getFacultySchools() {
		return this.facultySchools;
	}

	public void setFacultySchools(Set<FacultySchool> facultySchools) {
		this.facultySchools = facultySchools;
	}

}