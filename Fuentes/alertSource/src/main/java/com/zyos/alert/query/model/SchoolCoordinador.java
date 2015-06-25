package com.zyos.alert.query.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.zyos.core.lo.user.model.ZyosUser;

/**
 * SchoolCoordinador entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "school_coordinador")
public class SchoolCoordinador implements java.io.Serializable {

	// Fields

	private Long idschoolcoord;
	private ZyosUser zyosuser;
	private School school;
	private String datecreation;
	private String usercreation;
	private String datechange;
	private String userchange;
	private Long state;

	// Constructors

	/** default constructor */
	public SchoolCoordinador() {
	}

	/** minimal constructor */
	public SchoolCoordinador(Long idschoolcoord) {
		this.idschoolcoord = idschoolcoord;
	}

	/** full constructor */
	public SchoolCoordinador(Long idschoolcoord, ZyosUser zyosuser,
			School school, String datecreation, String usercreation,
			String datechange, String userchange, Long state) {
		this.idschoolcoord = idschoolcoord;
		this.zyosuser = zyosuser;
		this.school = school;
		this.datecreation = datecreation;
		this.usercreation = usercreation;
		this.datechange = datechange;
		this.userchange = userchange;
		this.state = state;
	}

	// Property accessors
	@Id
	@Column(name = "idschoolcoord", unique = true, nullable = false)
	public Long getIdschoolcoord() {
		return this.idschoolcoord;
	}

	public void setIdschoolcoord(Long idschoolcoord) {
		this.idschoolcoord = idschoolcoord;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idzyosuser")
	public ZyosUser getZyosuser() {
		return this.zyosuser;
	}

	public void setZyosuser(ZyosUser zyosuser) {
		this.zyosuser = zyosuser;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idschool")
	public School getSchool() {
		return this.school;
	}

	public void setSchool(School school) {
		this.school = school;
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

}