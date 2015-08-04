package com.zyos.alert.query.model;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * AbstractCorte entity provides the base persistence definition of the Corte
 * entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractCorte extends
		com.zyos.core.common.model.AZyosModel implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idcorte;
	private Long idAcademicPeriod;
	private String name;
	private String dateStart;
	private String dateEnd;
	private Long state;
	private String datecreation;
	private String usercreation;
	private String datechange;
	private String userchange;

	// Constructors

	/** default constructor */
	public AbstractCorte() {
	}

	/** minimal constructor */
	public AbstractCorte(Long idcorte, Long academicperiod,
			String name, String dateStart, String dateEnd) {
		this.idcorte = idcorte;
		this.idAcademicPeriod = academicperiod;
		this.name = name;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
	}

	/** full constructor */
	public AbstractCorte(Long idcorte, Long academicperiod,
			String name, String dateStart, String dateEnd, Long state,
			String datecreation, String usercreation, String datechange,
			String userchange) {
		this.idcorte = idcorte;
		this.idAcademicPeriod = academicperiod;
		this.name = name;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.state = state;
		this.datecreation = datecreation;
		this.usercreation = usercreation;
		this.datechange = datechange;
		this.userchange = userchange;
	}

	// Property accessors
	@Id
	@Column(name = "idcorte", unique = true, nullable = false)
	public Long getIdcorte() {
		return this.idcorte;
	}

	public void setIdcorte(Long idcorte) {
		this.idcorte = idcorte;
	}

	@Column(name = "name", nullable = false, length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "date_start", nullable = false, length = 20)
	public String getDateStart() {
		return this.dateStart;
	}

	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}

	@Column(name = "date_end", nullable = false, length = 20)
	public String getDateEnd() {
		return this.dateEnd;
	}

	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}

	@Column(name = "state")
	public Long getState() {
		return this.state;
	}

	public void setState(Long state) {
		this.state = state;
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
	
	@Column(name="id_academicperiod")
	public Long getIdAcademicPeriod() {
		return idAcademicPeriod;
	}

	public void setIdAcademicPeriod(Long idAcademicPeriod) {
		this.idAcademicPeriod = idAcademicPeriod;
	}
}