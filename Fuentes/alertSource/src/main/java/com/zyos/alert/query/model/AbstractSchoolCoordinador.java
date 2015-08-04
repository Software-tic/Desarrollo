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

	// Constructors

	/** default constructor */
	public AbstractSchoolCoordinador() {
	}

	/** minimal constructor */
	public AbstractSchoolCoordinador(Long idschoolcoord) {
		this.setIdSchoolCoord(idschoolcoord);
	}

	public AbstractSchoolCoordinador(Long idSchoolCoord, Long idZyosuser,
			Long idSchool) {
		this.setIdSchoolCoord(idSchoolCoord);
		this.idZyosuser = idZyosuser;
		this.idSchool = idSchool;
	}

	/** full constructor */
	public AbstractSchoolCoordinador(Long idschoolcoord, Long zyosuser,
			Long school, String datecreation, String usercreation,
			String datechange, String userchange, Long state) {
		this.setIdSchoolCoord(idschoolcoord);
		this.setIdZyosuser(zyosuser);
		this.setIdSchool(school);
		this.dateCreation = datecreation;
		this.userCreation = usercreation;
		this.dateChange = datechange;
		this.userChange = userchange;
		this.state = state;
	}

	// Property accessors
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
	
	@Id
	@Column(name = "idschoolcoord", unique = true, nullable = false)
	public Long getIdSchoolCoord() {
		return idSchoolCoord;
	}

	public void setIdSchoolCoord(Long idSchoolCoord) {
		this.idSchoolCoord = idSchoolCoord;
	}

}