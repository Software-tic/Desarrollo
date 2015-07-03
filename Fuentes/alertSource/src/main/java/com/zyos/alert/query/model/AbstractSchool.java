package com.zyos.alert.query.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

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
	private String datecreation;
	private String usercreation;
	private String datechange;
	private String userchange;
	private Long state;

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
		this.datecreation = datecreation;
		this.usercreation = usercreation;
		this.datechange = datechange;
		this.userchange = userchange;
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