package com.zyos.alert.moodle.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MdlGradeItems entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "mdl_grade_items", catalog = "moodlepre20141")
public class MdlGradeItems implements java.io.Serializable {

	// Fields

	private Long id;
	private Long courseid;
	private Long categoryid;
	private String itemname;
	private String itemtype;
	private String itemmodule;
	private Long iteminstance;
	private Long itemnumber;
	private String iteminfo;
	private String idnumber;
	private String calculation;
	private Short gradetype;
	private Double grademax;
	private Double grademin;
	private Long scaleid;
	private Long outcomeid;
	private Double gradepass;
	private Double multfactor;
	private Double plusfactor;
	private Double aggregationcoef;
	private Long sortorder;
	private Long display;
	private Boolean decimals;
	private Long hidden;
	private Long locked;
	private Long locktime;
	private Long needsupdate;
	private Long timecreated;
	private Long timemodified;

	// Constructors

	/** default constructor */
	public MdlGradeItems() {
	}

	/** minimal constructor */
	public MdlGradeItems(String itemtype, Short gradetype, Double grademax,
			Double grademin, Double gradepass, Double multfactor,
			Double plusfactor, Double aggregationcoef, Long sortorder,
			Long display, Long hidden, Long locked, Long locktime,
			Long needsupdate) {
		this.itemtype = itemtype;
		this.gradetype = gradetype;
		this.grademax = grademax;
		this.grademin = grademin;
		this.gradepass = gradepass;
		this.multfactor = multfactor;
		this.plusfactor = plusfactor;
		this.aggregationcoef = aggregationcoef;
		this.sortorder = sortorder;
		this.display = display;
		this.hidden = hidden;
		this.locked = locked;
		this.locktime = locktime;
		this.needsupdate = needsupdate;
	}

	/** full constructor */
	public MdlGradeItems(Long courseid, Long categoryid, String itemname,
			String itemtype, String itemmodule, Long iteminstance,
			Long itemnumber, String iteminfo, String idnumber,
			String calculation, Short gradetype, Double grademax,
			Double grademin, Long scaleid, Long outcomeid, Double gradepass,
			Double multfactor, Double plusfactor, Double aggregationcoef,
			Long sortorder, Long display, Boolean decimals, Long hidden,
			Long locked, Long locktime, Long needsupdate, Long timecreated,
			Long timemodified) {
		this.courseid = courseid;
		this.categoryid = categoryid;
		this.itemname = itemname;
		this.itemtype = itemtype;
		this.itemmodule = itemmodule;
		this.iteminstance = iteminstance;
		this.itemnumber = itemnumber;
		this.iteminfo = iteminfo;
		this.idnumber = idnumber;
		this.calculation = calculation;
		this.gradetype = gradetype;
		this.grademax = grademax;
		this.grademin = grademin;
		this.scaleid = scaleid;
		this.outcomeid = outcomeid;
		this.gradepass = gradepass;
		this.multfactor = multfactor;
		this.plusfactor = plusfactor;
		this.aggregationcoef = aggregationcoef;
		this.sortorder = sortorder;
		this.display = display;
		this.decimals = decimals;
		this.hidden = hidden;
		this.locked = locked;
		this.locktime = locktime;
		this.needsupdate = needsupdate;
		this.timecreated = timecreated;
		this.timemodified = timemodified;
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
		return this.courseid;
	}

	public void setCourseid(Long courseid) {
		this.courseid = courseid;
	}

	@Column(name = "categoryid")
	public Long getCategoryid() {
		return this.categoryid;
	}

	public void setCategoryid(Long categoryid) {
		this.categoryid = categoryid;
	}

	@Column(name = "itemname")
	public String getItemname() {
		return this.itemname;
	}

	public void setItemname(String itemname) {
		this.itemname = itemname;
	}

	@Column(name = "itemtype", nullable = false, length = 30)
	public String getItemtype() {
		return this.itemtype;
	}

	public void setItemtype(String itemtype) {
		this.itemtype = itemtype;
	}

	@Column(name = "itemmodule", length = 30)
	public String getItemmodule() {
		return this.itemmodule;
	}

	public void setItemmodule(String itemmodule) {
		this.itemmodule = itemmodule;
	}

	@Column(name = "iteminstance")
	public Long getIteminstance() {
		return this.iteminstance;
	}

	public void setIteminstance(Long iteminstance) {
		this.iteminstance = iteminstance;
	}

	@Column(name = "itemnumber")
	public Long getItemnumber() {
		return this.itemnumber;
	}

	public void setItemnumber(Long itemnumber) {
		this.itemnumber = itemnumber;
	}

	@Column(name = "iteminfo")
	public String getIteminfo() {
		return this.iteminfo;
	}

	public void setIteminfo(String iteminfo) {
		this.iteminfo = iteminfo;
	}

	@Column(name = "idnumber")
	public String getIdnumber() {
		return this.idnumber;
	}

	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}

	@Column(name = "calculation")
	public String getCalculation() {
		return this.calculation;
	}

	public void setCalculation(String calculation) {
		this.calculation = calculation;
	}

	@Column(name = "gradetype", nullable = false)
	public Short getGradetype() {
		return this.gradetype;
	}

	public void setGradetype(Short gradetype) {
		this.gradetype = gradetype;
	}

	@Column(name = "grademax", nullable = false, precision = 10, scale = 5)
	public Double getGrademax() {
		return this.grademax;
	}

	public void setGrademax(Double grademax) {
		this.grademax = grademax;
	}

	@Column(name = "grademin", nullable = false, precision = 10, scale = 5)
	public Double getGrademin() {
		return this.grademin;
	}

	public void setGrademin(Double grademin) {
		this.grademin = grademin;
	}

	@Column(name = "scaleid")
	public Long getScaleid() {
		return this.scaleid;
	}

	public void setScaleid(Long scaleid) {
		this.scaleid = scaleid;
	}

	@Column(name = "outcomeid")
	public Long getOutcomeid() {
		return this.outcomeid;
	}

	public void setOutcomeid(Long outcomeid) {
		this.outcomeid = outcomeid;
	}

	@Column(name = "gradepass", nullable = false, precision = 10, scale = 5)
	public Double getGradepass() {
		return this.gradepass;
	}

	public void setGradepass(Double gradepass) {
		this.gradepass = gradepass;
	}

	@Column(name = "multfactor", nullable = false, precision = 10, scale = 5)
	public Double getMultfactor() {
		return this.multfactor;
	}

	public void setMultfactor(Double multfactor) {
		this.multfactor = multfactor;
	}

	@Column(name = "plusfactor", nullable = false, precision = 10, scale = 5)
	public Double getPlusfactor() {
		return this.plusfactor;
	}

	public void setPlusfactor(Double plusfactor) {
		this.plusfactor = plusfactor;
	}

	@Column(name = "aggregationcoef", nullable = false, precision = 10, scale = 5)
	public Double getAggregationcoef() {
		return this.aggregationcoef;
	}

	public void setAggregationcoef(Double aggregationcoef) {
		this.aggregationcoef = aggregationcoef;
	}

	@Column(name = "sortorder", nullable = false)
	public Long getSortorder() {
		return this.sortorder;
	}

	public void setSortorder(Long sortorder) {
		this.sortorder = sortorder;
	}

	@Column(name = "display", nullable = false)
	public Long getDisplay() {
		return this.display;
	}

	public void setDisplay(Long display) {
		this.display = display;
	}

	@Column(name = "decimals")
	public Boolean getDecimals() {
		return this.decimals;
	}

	public void setDecimals(Boolean decimals) {
		this.decimals = decimals;
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

	@Column(name = "needsupdate", nullable = false)
	public Long getNeedsupdate() {
		return this.needsupdate;
	}

	public void setNeedsupdate(Long needsupdate) {
		this.needsupdate = needsupdate;
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