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
		super(datecreation, usercreation, datechange, userchange, state);
		this.idteacher = idteacher;
		this.idZyosUser = zyosuser;
		this.idSchool = school;
	}
	
	public AbstractTeacher(Long idteacher, Long zyosuser, Long school,
			String datecreation, String usercreation) {
		super(datecreation, usercreation);
		this.idteacher = idteacher;
		this.idZyosUser = zyosuser;
		this.idSchool = school;
	}

	public AbstractTeacher(Long idteacher, Long idZyosUser, Long idSchool) {
		super();
		this.idteacher = idteacher;
		this.idZyosUser = idZyosUser;
		this.idSchool = idSchool;
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

	@Column(name = "datecreation", length = 20)
	@Override
	public String getDateCreation() {
		// TODO Auto-generated method stub
		return super.getDateCreation();
	}

	@Override
	public void setDateCreation(String dateCreation) {
		// TODO Auto-generated method stub
		super.setDateCreation(dateCreation);
	}

	@Column(name = "usercreation", length = 45)
	@Override
	public String getUserCreation() {
		// TODO Auto-generated method stub
		return super.getUserCreation();
	}

	@Override
	public void setUserCreation(String userCreation) {
		// TODO Auto-generated method stub
		super.setUserCreation(userCreation);
	}

	@Column(name = "datechange", length = 20)
	@Override
	public String getDateChange() {
		// TODO Auto-generated method stub
		return super.getDateChange();
	}

	@Override
	public void setDateChange(String dateChange) {
		// TODO Auto-generated method stub
		super.setDateChange(dateChange);
	}

	@Column(name = "userchange", length = 45)
	@Override
	public String getUserChange() {
		// TODO Auto-generated method stub
		return super.getUserChange();
	}

	@Override
	public void setUserChange(String userChange) {
		// TODO Auto-generated method stub
		super.setUserChange(userChange);
	}

	@Column(name = "state")
	@Override
	public Long getState() {
		// TODO Auto-generated method stub
		return super.getState();
	}

	@Override
	public void setState(Long state) {
		// TODO Auto-generated method stub
		super.setState(state);
	}

}