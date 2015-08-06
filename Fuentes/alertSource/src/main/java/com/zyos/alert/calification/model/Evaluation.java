package com.zyos.alert.calification.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * Evaluation entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "evaluation")
public class Evaluation extends com.zyos.core.common.model.AZyosModel implements
		java.io.Serializable, Cloneable {

	// Fields

	private Long idEvaluation;
	private Long idSubject;
	private String name;
	private Double percentage;
	private Long idGroupSubject;
	private Long idMoodle;
	private Long idMoodleCourse;
	private Long idAcademicPeriod;

	// Constructors

	/** default constructor */
	public Evaluation() {
	}

	/** minimal constructor */
	public Evaluation(String dateCreation, Long state) {
		this.dateCreation = dateCreation;
		this.state = state;
	}

	/** full constructor */
	public Evaluation(Long idSubject, String name, Double percentage,
			String dateCreation, String userCreation, String dateChange,
			String userChange, Long state, Long idGroupSubject) {
		this.idSubject = idSubject;
		this.name = name;
		this.percentage = percentage;
		this.dateCreation = dateCreation;
		this.userCreation = userCreation;
		this.dateChange = dateChange;
		this.userChange = userChange;
		this.state = state;	
		this.idGroupSubject = idGroupSubject;

	}

	public Evaluation(Long idEvaluation, Long idSubject, Long idGroupSubject,
			String name, Double percentage) {
		this.idEvaluation = idEvaluation;
		this.idSubject = idSubject;
		this.name = name;
		this.percentage = percentage;
		this.idGroupSubject = idGroupSubject;

	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "idEvaluation", nullable = false)
	public Long getIdEvaluation() {
		return this.idEvaluation;
	}

	public void setIdEvaluation(Long idEvaluation) {
		this.idEvaluation = idEvaluation;
	}

	@Column(name = "idSubject")
	public Long getIdSubject() {
		return this.idSubject;
	}

	public void setIdSubject(Long idSubject) {
		this.idSubject = idSubject;
	}

	@Column(name = "idMoodle")
	public Long getIdMoodle() {
		return idMoodle;
	}

	public void setIdMoodle(Long idMoodle) {
		this.idMoodle = idMoodle;
	}

	@Column(name = "idAcademicPeriod")
	public Long getIdAcademicPeriod() {
		return idAcademicPeriod;
	}

	public void setIdAcademicPeriod(Long idAcademicPeriod) {
		this.idAcademicPeriod = idAcademicPeriod;
	}

	@Column(name = "idMoodleCourse")
	public Long getIdMoodleCourse() {
		return idMoodleCourse;
	}

	public void setIdMoodleCourse(Long idMoodleCourse) {
		this.idMoodleCourse = idMoodleCourse;
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

	@Column(name = "idGroupSubject", nullable = true)
	public Long getIdGroupSubject() {
		return idGroupSubject;
	}

	public void setIdGroupSubject(Long idGroupSubject) {
		this.idGroupSubject = idGroupSubject;
	}

	@Override
	@Transient
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}