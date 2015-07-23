package com.zyos.alert.query.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

/**
 * Corte entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "corte")
public class Corte extends com.zyos.core.common.model.AZyosModel implements
		java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idcorte;
	private Long academicperiod;
	private String name;
	private String dateStart;
	private String dateEnd;

	// Constructors

	/** default constructor */
	public Corte() {
	}

	/** minimal constructor */
	public Corte(Long idcorte, Long academicperiod, String name,
			String dateStart, String dateEnd) {
		this.idcorte = idcorte;
		this.setAcademicperiod(academicperiod);
		this.name = name;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
	}

	/** full constructor */
	public Corte(Long idcorte, Long academicperiod, String name,
			String dateStart, String dateEnd, Long state, String datecreation,
			String usercreation, String datechange, String userchange) {
		this.idcorte = idcorte;
		this.setAcademicperiod(academicperiod);
		this.name = name;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.state = state;
		this.dateChange = userchange;
		this.dateCreation = datecreation;
		this.userChange = userchange;
		this.userCreation = usercreation;
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

	@JoinColumn(name = "id_academicperiod", nullable = false)
	public Long getAcademicperiod() {
		return academicperiod;
	}

	public void setAcademicperiod(Long academicperiod) {
		this.academicperiod = academicperiod;
	}

}