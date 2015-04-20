package com.zyos.alert.moodle.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * MdlGradeGrades entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "mdl_grade_grades", catalog = "moodlepre20141", uniqueConstraints = @UniqueConstraint(columnNames = {
		"userid", "itemid" }))
public class MdlGradeGrades implements java.io.Serializable {

	// Fields

	private Long id;
	private Long itemid;
	private Long userid;
	private Double rawgrade;
	private Double rawgrademax;
	private Double rawgrademin;
	private Long rawscaleid;
	private Long usermodified;
	private Double finalgrade;
	private Long hidden;
	private Long locked;
	private Long locktime;
	private Long overridden;
	private Long excluded;
	private String feedback;
	private Long feedbackformat;
	private String information;
	private Long informationformat;
	private Long timecreated;
	private Long timemodified;

	// Constructors

	/** default constructor */
	public MdlGradeGrades() {
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

	@Column(name = "itemid", nullable = false)
	public Long getItemid() {
		return this.itemid;
	}

	public void setItemid(Long itemid) {
		this.itemid = itemid;
	}

	@Column(name = "userid", nullable = false)
	public Long getUserid() {
		return this.userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	@Column(name = "rawgrade", precision = 10, scale = 5)
	public Double getRawgrade() {
		return this.rawgrade;
	}

	public void setRawgrade(Double rawgrade) {
		this.rawgrade = rawgrade;
	}

	@Column(name = "rawgrademax", nullable = false, precision = 10, scale = 5)
	public Double getRawgrademax() {
		return this.rawgrademax;
	}

	public void setRawgrademax(Double rawgrademax) {
		this.rawgrademax = rawgrademax;
	}

	@Column(name = "rawgrademin", nullable = false, precision = 10, scale = 5)
	public Double getRawgrademin() {
		return this.rawgrademin;
	}

	public void setRawgrademin(Double rawgrademin) {
		this.rawgrademin = rawgrademin;
	}

	@Column(name = "rawscaleid")
	public Long getRawscaleid() {
		return this.rawscaleid;
	}

	public void setRawscaleid(Long rawscaleid) {
		this.rawscaleid = rawscaleid;
	}

	@Column(name = "usermodified")
	public Long getUsermodified() {
		return this.usermodified;
	}

	public void setUsermodified(Long usermodified) {
		this.usermodified = usermodified;
	}

	@Column(name = "finalgrade", precision = 10, scale = 5)
	public Double getFinalgrade() {
		return this.finalgrade;
	}

	public void setFinalgrade(Double finalgrade) {
		this.finalgrade = finalgrade;
	}

	@Column(name = "hidden", nullable = false)
	public Long getHidden() {
		return this.hidden;
	}

	public void setHidden(Long hidden) {
		this.hidden = hidden;
	}

	@Column(name = "locked", nullable = false)
	public Long getLocked() {
		return this.locked;
	}

	public void setLocked(Long locked) {
		this.locked = locked;
	}

	@Column(name = "locktime", nullable = false)
	public Long getLocktime() {
		return this.locktime;
	}

	public void setLocktime(Long locktime) {
		this.locktime = locktime;
	}

	@Column(name = "overridden", nullable = false)
	public Long getOverridden() {
		return this.overridden;
	}

	public void setOverridden(Long overridden) {
		this.overridden = overridden;
	}

	@Column(name = "excluded", nullable = false)
	public Long getExcluded() {
		return this.excluded;
	}

	public void setExcluded(Long excluded) {
		this.excluded = excluded;
	}

	@Column(name = "feedback")
	public String getFeedback() {
		return this.feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	@Column(name = "feedbackformat", nullable = false)
	public Long getFeedbackformat() {
		return this.feedbackformat;
	}

	public void setFeedbackformat(Long feedbackformat) {
		this.feedbackformat = feedbackformat;
	}

	@Column(name = "information")
	public String getInformation() {
		return this.information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	@Column(name = "informationformat", nullable = false)
	public Long getInformationformat() {
		return this.informationformat;
	}

	public void setInformationformat(Long informationformat) {
		this.informationformat = informationformat;
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