package com.zyos.alert.moodle.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MdlRoleAssignments entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "mdl_role_assignments", catalog = "moodlepre20141")
public class MdlRoleAssignments implements java.io.Serializable {

	// Fields

	private Long id;
	private Long roleid;
	private Long contextid;
	private Long userid;
	private Long timemodified;
	private String component;
	private Long itemid;

	// Constructors

	/** default constructor */
	public MdlRoleAssignments() {
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

	@Column(name = "roleid", nullable = false)
	public Long getRoleid() {
		return this.roleid;
	}

	public void setRoleid(Long roleid) {
		this.roleid = roleid;
	}

	@Column(name = "contextid", nullable = false)
	public Long getContextid() {
		return this.contextid;
	}

	public void setContextid(Long contextid) {
		this.contextid = contextid;
	}

	@Column(name = "userid", nullable = false)
	public Long getUserid() {
		return this.userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	@Column(name = "timemodified", nullable = false)
	public Long getTimemodified() {
		return this.timemodified;
	}

	public void setTimemodified(Long timemodified) {
		this.timemodified = timemodified;
	}

	@Column(name = "component", nullable = false, length = 100)
	public String getComponent() {
		return this.component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	@Column(name = "itemid", nullable = false)
	public Long getItemid() {
		return this.itemid;
	}

	public void setItemid(Long itemid) {
		this.itemid = itemid;
	}

}