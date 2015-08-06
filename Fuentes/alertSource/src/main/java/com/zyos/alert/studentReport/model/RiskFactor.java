package com.zyos.alert.studentReport.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * Riskfactor entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "riskfactor")
public class RiskFactor extends com.zyos.core.common.model.AZyosModel implements
		java.io.Serializable {

	// Fields
	private Long idRiskFactor;
	private String name;
	private String description;
	private Long idRiskFactorCategory;
	
	private transient String nameRiskFactorCategory;
	
	// Constructors

	/** default constructor */
	public RiskFactor() {
	}

	/** minimal constructor */
	public RiskFactor(String dateCreation, Long state) {
		this.dateCreation = dateCreation;
		this.state = state;
	}

	/** full constructor */
	public RiskFactor(String name, String description, Long idRiskfactorCategory, String dateCreation,
			String userCreation, String dateChange, String userChange,
			Long state) {
		this.name = name;
		this.description = description;
		this.dateCreation = dateCreation;
		this.userCreation = userCreation;
		this.dateChange = dateChange;
		this.userChange = userChange;
		this.state = state;
		this.idRiskFactorCategory = idRiskfactorCategory;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "idRiskFactor", nullable = false)
	public Long getIdRiskFactor() {
		return this.idRiskFactor;
	}
 
	public void setIdRiskFactor(Long idRiskFactor) {
		this.idRiskFactor = idRiskFactor;
	}

	@Column(name = "name", length = 120)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "description", length = 256)
	public String getDescription() {
		return this.description;
	}

	public void setIdRiskFactorCategory(Long idRiskFactorCategory) {
		this.idRiskFactorCategory = idRiskFactorCategory;
	}
	
	@Column(name = "idRiskFactorCategory", nullable = false )
	public Long getIdRiskFactorCategory() {
		return this.idRiskFactorCategory;
	}

	public void setDescription(String description) {
		this.description = description;
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
	public String getNameRiskFactorCategory() {
		return nameRiskFactorCategory;
	}

	public void setNameRiskFactorCategory(String nameRiskFactorCategory) {
		this.nameRiskFactorCategory = nameRiskFactorCategory;
	}
	
	

}