package com.zyos.alert.query.model;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * AbstractSchoolCoordinador entity provides the base persistence definition of
 * the SchoolCoordinador entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractSchoolCoordinador extends
		com.zyos.core.common.model.AZyosModel implements java.io.Serializable {

	// Fields

	private Long idSchoolCoord;
	private Long idZyosuser;
	private Long idSchool;
	private String datecreation;
	private String usercreation;
	private String datechange;
	private String userchange;
	private Long state;

	// Constructors

	/** default constructor */
	public AbstractSchoolCoordinador() {
	}

	/** minimal constructor */
	public AbstractSchoolCoordinador(Long idschoolcoord) {
		this.idSchoolCoord = idschoolcoord;
	}

	/** full constructor */
	public AbstractSchoolCoordinador(Long idschoolcoord, Long zyosuser,
			Long school, String datecreation, String usercreation,
			String datechange, String userchange, Long state) {
		this.idSchoolCoord = idschoolcoord;
		this.setIdZyosuser(zyosuser);
		this.setIdSchool(school);
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
		return this.idSchoolCoord;
	}

	public void setIdschoolcoord(Long idschoolcoord) {
		this.idSchoolCoord = idschoolcoord;
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
	
	@JoinColumn(name = "idzyosuser")
	public Long getIdZyosuser() {
		return idZyosuser;
	}

	public void setIdZyosuser(Long idZyosuser) {
		this.idZyosuser = idZyosuser;
	}
	
	@JoinColumn(name = "idschool")
	public Long getIdSchool() {
		return idSchool;
	}

	public void setIdSchool(Long idSchool) {
		this.idSchool = idSchool;
	}

}