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
		this.dateCreation = datecreation;
		this.userCreation = usercreation;
		this.dateChange = datechange;
		this.userChange = userchange;
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

	@Column(name = "datecreation", length = 20)
	public String getDatecreation() {
		return this.dateCreation;
	}

	public void setDatecreation(String datecreation) {
		this.dateCreation = datecreation;
	}

	@Column(name = "usercreation", length = 45)
	public String getUsercreation() {
		return this.userCreation;
	}

	public void setUsercreation(String usercreation) {
		this.userCreation = usercreation;
	}

	@Column(name = "datechange", length = 20)
	public String getDatechange() {
		return this.dateChange;
	}

	public void setDatechange(String datechange) {
		this.dateChange = datechange;
	}

	@Column(name = "userchange", length = 45)
	public String getUserchange() {
		return this.userChange;
	}

	public void setUserchange(String userchange) {
		this.userChange = userchange;
	}

	@Column(name = "state")
	public Long getState() {
		return this.state;
	}

	public void setState(Long state) {
		this.state = state;
	}
	
	@JoinColumn(name = "idzyosuser")
	public Long getIdZyosUser() {
		return idZyosUser;
	}

	public void setIdZyosUser(Long idZyosUser) {
		this.idZyosUser = idZyosUser;
	}
	
	@JoinColumn(name = "idschool")
	public Long getIdSchool() {
		return idSchool;
	}

	public void setIdSchool(Long idSchool) {
		this.idSchool = idSchool;
	}

}