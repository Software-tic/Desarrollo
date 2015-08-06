package com.zyos.alert.studentReport.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * Riskfactorreportstudent entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "riskfactorreportstudent")
public class RiskFactorReportStudent extends
		com.zyos.core.common.model.AZyosModel implements java.io.Serializable {

	// Fields

	private Long idRiskFactorReportStudent;
	private Long idReportStudent;
	private Long idRiskFactor;

	// Constructors

	/** default constructor */
	public RiskFactorReportStudent() {
	}

	/** minimal constructor */
	public RiskFactorReportStudent(String dateCreation, Long state) {
		this.dateCreation = dateCreation;
		this.state = state;
	}

	/** full constructor */
	public RiskFactorReportStudent(Long idReportStudent, Long idRiskFactor,
			String dateCreation, String userCreation, String dateChange,
			String userChange, Long state) {
		this.idReportStudent = idReportStudent;
		this.idRiskFactor = idRiskFactor;
		this.dateCreation = dateCreation;
		this.userCreation = userCreation;
		this.dateChange = dateChange;
		this.userChange = userChange;
		this.state = state;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "idRiskFactorReportStudent", nullable = false)
	public Long getIdRiskFactorReportStudent() {
		return this.idRiskFactorReportStudent;
	}

	public void setIdRiskFactorReportStudent(Long idRiskFactorReportStudent) {
		this.idRiskFactorReportStudent = idRiskFactorReportStudent;
	}

	@Column(name = "idReportStudent")
	public Long getIdReportStudent() {
		return this.idReportStudent;
	}

	public void setIdReportStudent(Long idReportStudent) {
		this.idReportStudent = idReportStudent;
	}

	@Column(name = "idRiskFactor")
	public Long getIdRiskFactor() {
		return this.idRiskFactor;
	}

	public void setIdRiskFactor(Long idRiskFactor) {
		this.idRiskFactor = idRiskFactor;
	}

	@Override
	@Column(name = "dateCreation", nullable = false, length = 20)
	public String getDateCreation() {
		return this.dateCreation;
	}

	@Override
	public void setDateCreation(String dateCreation) {
		this.dateCreation = dateCreation;
	}

	@Override
	@Column(name = "userCreation", length = 45)
	public String getUserCreation() {
		return this.userCreation;
	}

	@Override
	public void setUserCreation(String userCreation) {
		this.userCreation = userCreation;
	}

	@Override
	@Column(name = "dateChange", length = 20)
	public String getDateChange() {
		return this.dateChange;
	}

	@Override
	public void setDateChange(String dateChange) {
		this.dateChange = dateChange;
	}

	@Override
	@Column(name = "userChange", length = 45)
	public String getUserChange() {
		return this.userChange;
	}

	@Override
	public void setUserChange(String userChange) {
		this.userChange = userChange;
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

}