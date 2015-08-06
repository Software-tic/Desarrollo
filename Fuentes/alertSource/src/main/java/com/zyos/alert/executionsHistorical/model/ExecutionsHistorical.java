package com.zyos.alert.executionsHistorical.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "executionsHistorical")
public class ExecutionsHistorical extends com.zyos.core.common.model.AZyosModel
		implements java.io.Serializable {

	private Long idExecutionsHistorical;
	private Long idExecutionHistoricalType;
	private int reportStudentNumber;
	private Long idRiskFactor;
	private String executionTime;
	private String information;
	
	private transient String zyosGroupName;
	private transient String zyosUserName;
	private transient String zyosUserLastName;
	private transient String riskFactorName;

	// Constructors

	/** default constructor */
	public ExecutionsHistorical() {
	}

	public ExecutionsHistorical(Long idExecutionsHistorical,
			int reportStudentNumber, String executionTime, Long idRiskFactor) {
		this.idExecutionsHistorical = idExecutionsHistorical;
		this.reportStudentNumber = reportStudentNumber;
		this.executionTime = executionTime;
		this.idRiskFactor = idRiskFactor;
	}

	public ExecutionsHistorical(Long idExecutionsHistorical,
			int reportStudentNumber, String executionTime, Long idRiskFactor,
			String dateCreation, String userCreation, Long state) {
		this.idExecutionsHistorical = idExecutionsHistorical;
		this.reportStudentNumber = reportStudentNumber;
		this.executionTime = executionTime;
		this.idRiskFactor = idRiskFactor;
		this.dateCreation = dateCreation;
		this.userCreation = userCreation;
		this.state = state;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "idExecutionsHistorical", nullable = false)
	public Long getIdExecutionsHistorical() {
		return this.idExecutionsHistorical;
	}

	public void setIdExecutionsHistorical(Long idExecutionsHistorical) {
		this.idExecutionsHistorical = idExecutionsHistorical;
	}

	@Column(name = "reportStudentNumber", nullable = true)
	public int getReportStudentNumber() {
		return reportStudentNumber;
	}

	@Column(name = "idExecutionHistoricalType", nullable = true)
	public Long getIdExecutionHistoricalType() {
		return idExecutionHistoricalType;
	}

	public void setIdExecutionHistoricalType(Long idExecutionHistoricalType) {
		this.idExecutionHistoricalType = idExecutionHistoricalType;
	}

	@Column(name = "information", nullable = true)
	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public void setReportStudentNumber(int reportStudentNumber) {
		this.reportStudentNumber = reportStudentNumber;
	}

	@Column(name = "executionTime", nullable = true)
	public String getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(String executionTime) {
		this.executionTime = executionTime;
	}

	@Column(name = "idRiskFactor", nullable = true)
	public Long getIdRiskFactor() {
		return idRiskFactor;
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

	@Transient
	public String getZyosGroupName() {
		return zyosGroupName;
	}

	public void setZyosGroupName(String zyosGroupName) {
		this.zyosGroupName = zyosGroupName;
	}

	@Transient
	public String getZyosUserName() {
		return zyosUserName;
	}

	public void setZyosUserName(String zyosUserName) {
		this.zyosUserName = zyosUserName;
	}

	@Transient
	public String getRiskFactorName() {
		return riskFactorName;
	}

	public void setRiskFactorName(String riskFactorName) {
		this.riskFactorName = riskFactorName;
	}

	@Transient
	public String getZyosUserLastName() {
		return zyosUserLastName;
	}

	public void setZyosUserLastName(String zyosUserLastName) {
		this.zyosUserLastName = zyosUserLastName;
	}
	
	
	
	
	
	
	

}
