package com.zyos.alert.query.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * AbstractSchool entity provides the base persistence definition of the School
 * entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractSchool extends
		com.zyos.core.common.model.AZyosModel implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idschool;
	private String nameSchool;

	// Constructors

	/** default constructor */
	public AbstractSchool() {
	}

	/** minimal constructor */
	public AbstractSchool(Long idschool, String nameSchool) {
		this.idschool = idschool;
		this.nameSchool = nameSchool;
	}

	/** full constructor */
	public AbstractSchool(Long idschool, String nameSchool,
			String datecreation, String usercreation, String datechange,
			String userchange, Long state) {
		this.idschool = idschool;
		this.nameSchool = nameSchool;
		this.dateCreation = datecreation;
		this.userCreation = usercreation;
		this.dateChange = datechange;
		this.userChange = userchange;
		this.state = state;
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

	@Override
	@Column(name = "state")
	public Long getState() {
		return this.state;
	}

	@Override
	public void setState(Long state) {
		this.state = state;
	}

}