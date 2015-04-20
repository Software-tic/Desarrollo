package com.zyos.alert.moodle.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MdlGradeGrades entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "mdl_grade_categories", catalog = "moodlepre20141")
public class MdlGradeCategories implements java.io.Serializable {

	// Fields

	private Long id;
	private Long courseid;
	private String fullname;
	private Long hidden;
	private Long timecreated;
	private Long timemodified;

	// Constructors

	/** default constructor */
	public MdlGradeCategories() {
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "courseid")
	public Long getCourseid() {
		return courseid;
	}

	public void setCourseid(Long courseid) {
		this.courseid = courseid;
	}

	@Column(name = "fullname")
	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	@Column(name = "hidden")
	public Long getHidden() {
		return hidden;
	}

	public void setHidden(Long hidden) {
		this.hidden = hidden;
	}

	@Column(name = "timecreated")
	public Long getTimecreated() {
		return this.timecreated;
	}

	public void setTimecreated(Long timecreated) {
		this.timecreated = timecreated;
	}

	@Column(name = "timemodified")
	public Long getTimemodified() {
		return this.timemodified;
	}

	public void setTimemodified(Long timemodified) {
		this.timemodified = timemodified;
	}

}