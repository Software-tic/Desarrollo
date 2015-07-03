package com.zyos.alert.query.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;

/**
 * AbstractTeacher entity provides the base persistence definition of the
 * Teacher entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractTeacher extends
		com.zyos.core.common.model.AZyosModel implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idteacher;
	private Long idZyosUser;
	private Long idSchool;
	private String datecreation;
	private String usercreation;
	private String datechange;
	private String userchange;
	private Long state;

	// Constructors

	/** default constructor */
	public AbstractTeacher() {
	}

	/** minimal constructor */
	public AbstractTeacher(Long idteacher) {
		this.idteacher = idteacher;
	}

	/** full constructor */
	public AbstractTeacher(Long idteacher, Long zyosuser, Long school,
			String datecreation, String usercreation, String datechange,
			String userchange, Long state) {
		this.idteacher = idteacher;
		this.idZyosUser = zyosuser;
		this.idSchool = school;
		this.datecreation = datecreation;
		this.usercreation = usercreation;
		this.datechange = datechange;
		this.userchange = userchange;
		this.state = state;
	}

	// Property accessors
	@Id
	@Column(name = "idteacher", unique = true, nullable = false)
	public Long getIdteacher() {
		return this.idteacher;
	}

	public void setIdteacher(Long idteacher) {
		this.idteacher = idteacher;
	}

	@JoinColumn(name = "idzyosuser")
	public Long getZyosuser() {
		return this.idZyosUser;
	}

	public void setZyosuser(Long zyosuser) {
		this.idZyosUser = zyosuser;
	}

	@JoinColumn(name = "idschool")
	public Long getSchool() {
		return this.idSchool;
	}

	public void setSchool(Long school) {
		this.idSchool = school;
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