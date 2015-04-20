package com.zyos.alert.moodle.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MdlCourse entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "mdl_course", catalog = "moodlepre20141")
public class MdlCourse implements java.io.Serializable {

	// Fields

	private Long id;
	private Long category;
	private Long sortorder;
	private String fullname;
	private String shortname;
	private String idnumber;
	private Long startdate;
	private Boolean visible;
	private Boolean visibleold;
	private Long timecreated;
	private Long timemodified;

	// Constructors

	/** default constructor */
	public MdlCourse() {
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

	@Column(name = "category", nullable = false)
	public Long getCategory() {
		return this.category;
	}

	public void setCategory(Long category) {
		this.category = category;
	}

	@Column(name = "sortorder", nullable = false)
	public Long getSortorder() {
		return this.sortorder;
	}

	public void setSortorder(Long sortorder) {
		this.sortorder = sortorder;
	}

	@Column(name = "fullname", nullable = false, length = 254)
	public String getFullname() {
		return this.fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	@Column(name = "shortname", nullable = false)
	public String getShortname() {
		return this.shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	@Column(name = "idnumber", nullable = false, length = 100)
	public String getIdnumber() {
		return this.idnumber;
	}

	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}

	@Column(name = "startdate", nullable = false)
	public Long getStartdate() {
		return this.startdate;
	}

	public void setStartdate(Long startdate) {
		this.startdate = startdate;
	}

	@Column(name = "visible", nullable = false)
	public Boolean getVisible() {
		return this.visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	@Column(name = "visibleold", nullable = false)
	public Boolean getVisibleold() {
		return this.visibleold;
	}

	public void setVisibleold(Boolean visibleold) {
		this.visibleold = visibleold;
	}

	@Column(name = "timecreated", nullable = false)
	public Long getTimecreated() {
		return this.timecreated;
	}

	public void setTimecreated(Long timecreated) {
		this.timecreated = timecreated;
	}

	@Column(name = "timemodified", nullable = false)
	public Long getTimemodified() {
		return this.timemodified;
	}

	public void setTimemodified(Long timemodified) {
		this.timemodified = timemodified;
	}

}