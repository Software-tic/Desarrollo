package com.zyos.alert.calification.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * Calificationitem entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "calificationitem")
public class CalificationItem extends com.zyos.core.common.model.AZyosModel
		implements java.io.Serializable {

	// Fields

	private Long idCalificationItem;
	private Long idEvaluation;
	private String name;
	private Double percentage;
	private Long idMoodle;
	private Long idMoodleEvaluation;
	private Long idAcademicPeriod;

	private transient Long calificationNumber;

	// Constructors

	/** default constructor */
	public CalificationItem() {
	}

	/** minimal constructor */
	public CalificationItem(String dateCreation, Long state) {
		this.dateCreation = dateCreation;
		this.state = state;
	}

	/** full constructor */
	public CalificationItem(Long idEvaluation, String name, Double percentage,
			String dateCreation, String userCreation, String dateChange,
			String userChange, Long state) {
		this.idEvaluation = idEvaluation;
		this.name = name;
		this.percentage = percentage;
		this.dateCreation = dateCreation;
		this.userCreation = userCreation;
		this.dateChange = dateChange;
		this.userChange = userChange;
		this.state = state;
	}

	/** @author jhernandez */
	public CalificationItem(Long idCalificationItem, Long idEvaluation,
			String name, Double percentage) {
		this.idCalificationItem = idCalificationItem;
		this.idEvaluation = idEvaluation;
		this.name = name;
		this.percentage = percentage;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "idCalificationItem", nullable = false)
	public Long getIdCalificationItem() {
		return this.idCalificationItem;
	}

	public void setIdCalificationItem(Long idCalificationItem) {
		this.idCalificationItem = idCalificationItem;
	}

	@Column(name = "idEvaluation")
	public Long getIdEvaluation() {
		return this.idEvaluation;
	}

	public void setIdEvaluation(Long idEvaluation) {
		this.idEvaluation = idEvaluation;
	}

	@Column(name = "idMoodle")
	public Long getIdMoodle() {
		return idMoodle;
	}

	public void setIdMoodle(Long idMoodle) {
		this.idMoodle = idMoodle;
	}

	@Column(name = "idMoodleEvaluation")
	public Long getIdMoodleEvaluation() {
		return idMoodleEvaluation;
	}

	public void setIdMoodleEvaluation(Long idMoodleEvaluation) {
		this.idMoodleEvaluation = idMoodleEvaluation;
	}

	@Column(name = "idAcademicPeriod")
	public Long getIdAcademicPeriod() {
		return idAcademicPeriod;
	}

	public void setIdAcademicPeriod(Long idAcademicPeriod) {
		this.idAcademicPeriod = idAcademicPeriod;
	}

	@Column(name = "name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "percentage", precision = 17, scale = 17)
	public Double getPercentage() {
		return this.percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

	@Column(name = "dateCreation", nullable = false, length = 20)
	public String getDateCreation() {
		return this.dateCreation;
	}

	public void setDateCreation(String dateCreation) {
		this.dateCreation = dateCreation;
	}

	@Column(name = "userCreation", length = 45)
	public String getUserCreation() {
		return this.userCreation;
	}

	public void setUserCreation(String userCreation) {
		this.userCreation = userCreation;
	}

	@Column(name = "dateChange", length = 20)
	public String getDateChange() {
		return this.dateChange;
	}

	public void setDateChange(String dateChange) {
		this.dateChange = dateChange;
	}

	@Column(name = "userChange", length = 45)
	public String getUserChange() {
		return this.userChange;
	}

	public void setUserChange(String userChange) {
		this.userChange = userChange;
	}

	@Column(name = "state", nullable = false)
	public Long getState() {
		return this.state;
	}

	public void setState(Long state) {
		this.state = state;
	}

	@Transient
	public Long getCalificationNumber() {
		return calificationNumber;
	}

	public void setCalificationNumber(Long calificationNumber) {
		this.calificationNumber = calificationNumber;
	}

}