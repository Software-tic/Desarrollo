package com.zyos.alert.query.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * GradesPeriodSubject entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "grades_period_subject")
public class GradesPeriodSubject extends com.zyos.core.common.model.AZyosModel
		implements java.io.Serializable {

	// Fields

	private Long idgradesPeriodSubject;
	private Long studentsubject;
	private String firstcorte;
	private String secondcorte;
	private String thirdcorte;
	private String finalgrade;
	
	//Transient
	private transient Long idEstudent;
	private transient Long idSubject;
	private transient Long idPeriodo;

	// Constructors

	/** default constructor */
	public GradesPeriodSubject() {
	}

	/** minimal constructor */
	public GradesPeriodSubject(Long idgradesPeriodSubject,
			Long studentsubject, String usercreation, Long state) {
		this.idgradesPeriodSubject = idgradesPeriodSubject;
		this.studentsubject = studentsubject;
		this.userCreation = usercreation;
		this.state = state;
	}

	/** full constructor */
	public GradesPeriodSubject(Long idgradesPeriodSubject,
			Long studentsubject, String firstcorte,
			String secondcorte, String thirdcorte, String finalgrade,
			String datecreation, String usercreation, String datechange,
			String userchange, Long state) {
		this.idgradesPeriodSubject = idgradesPeriodSubject;
		this.studentsubject = studentsubject;
		this.firstcorte = firstcorte;
		this.secondcorte = secondcorte;
		this.thirdcorte = thirdcorte;
		this.finalgrade = finalgrade;
		this.dateCreation = datecreation;
		this.userCreation = usercreation;
		this.dateChange = datechange;
		this.userChange = userchange;
		this.state = state;
	}
	
	public GradesPeriodSubject(Long idEstudent, Long idSubject, String firstcorte,
			String secondcorte, String thirdcorte, String finalgrade,
			  Long idPeriodo, Long studentsubject) {
		super();
		this.studentsubject = studentsubject;
		this.firstcorte = firstcorte;
		this.secondcorte = secondcorte;
		this.thirdcorte = thirdcorte;
		this.finalgrade = finalgrade;
		this.idEstudent = idEstudent;
		this.idSubject = idSubject;
		this.idPeriodo = idPeriodo;
	}

	public GradesPeriodSubject(Long idgradesPeriodSubject, Long studentsubject, Long idPeriodo) {
		super();
		this.idgradesPeriodSubject = idgradesPeriodSubject;
		this.studentsubject = studentsubject;
		this.idPeriodo = idPeriodo;
	}
	
	public GradesPeriodSubject(Long idgradesPeriodSubject, Long studentsubject) {
		super();
		this.idgradesPeriodSubject = idgradesPeriodSubject;
		this.studentsubject = studentsubject;
	}
	
	public GradesPeriodSubject(Long idgradesPeriodSubject, Long studentsubject, Long idEstudent, Long idPeriodo) {
		super();
		this.idgradesPeriodSubject = idgradesPeriodSubject;
		this.studentsubject = studentsubject;
		this.idPeriodo = idEstudent;
	}


	// Property accessors
	@Id
	@Column(name = "idgrades_period_subject", unique = true, nullable = false)
	public Long getIdgradesPeriodSubject() {
		return this.idgradesPeriodSubject;
	}

	public void setIdgradesPeriodSubject(Long idgradesPeriodSubject) {
		this.idgradesPeriodSubject = idgradesPeriodSubject;
	}

	@Column(name = "firstcorte", length = 5)
	public String getFirstcorte() {
		return this.firstcorte;
	}

	public void setFirstcorte(String firstcorte) {
		this.firstcorte = firstcorte;
	}

	@Column(name = "secondcorte", length = 5)
	public String getSecondcorte() {
		return this.secondcorte;
	}

	public void setSecondcorte(String secondcorte) {
		this.secondcorte = secondcorte;
	}

	@Column(name = "thirdcorte", length = 5)
	public String getThirdcorte() {
		return this.thirdcorte;
	}

	public void setThirdcorte(String thirdcorte) {
		this.thirdcorte = thirdcorte;
	}

	@Column(name = "finalgrade", length = 5)
	public String getFinalgrade() {
		return this.finalgrade;
	}

	public void setFinalgrade(String finalgrade) {
		this.finalgrade = finalgrade;
	}

	@Column(name = "datecreation", length = 20)
	public String getDatecreation() {
		return this.dateCreation;
	}

	public void setDatecreation(String datecreation) {
		this.dateCreation = datecreation;
	}

	@Column(name = "usercreation", nullable = false, length = 45)
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
	@Column(name = "state", nullable = false)
	public Long getState() {
		return this.state;
	}

	@Override
	public void setState(Long state) {
		this.state = state;
	}
	
	@Column(name = "idstudentsubject", nullable = false)
	public Long getStudentsubject() {
		return studentsubject;
	}
	
	public void setStudentsubject(Long studentsubject) {
		this.studentsubject = studentsubject;
	}
	
	@Transient
	public Long getIdEstudent() {
		return idEstudent;
	}
	
	public void setIdEstudent(Long idEstudent) {
		this.idEstudent = idEstudent;
	}
	
	@Transient
	public Long getIdSubject() {
		return idSubject;
	}
	
	public void setIdSubject(Long idSubject) {
		this.idSubject = idSubject;
	}

	@Transient
	public Long getIdPeriodo() {
		return idPeriodo;
	}

	public void setIdPeriodo(Long idPeriodo) {
		this.idPeriodo = idPeriodo;
	}

}