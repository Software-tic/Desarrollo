package com.zyos.alert.calification.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * Calification entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "calification")
public class Calification extends com.zyos.core.common.model.AZyosModel
		implements java.io.Serializable {

	// Fields

	private Long idCalification;
	private String observation;
	private Long idCalificationItem;
	private Long idStudent;
	private double grade;
	private Long idMoodle;
	private Long idMoodleItem;
	private Long idAcademicPeriod;

	// Constructors

	/** default constructor */
	public Calification() {
	}

	/** minimal constructor */
	public Calification(String dateCreation, Long state) {
		this.dateCreation = dateCreation;
		this.state = state;
	}

	/** full constructor */
	public Calification(String observation, String dateCreation,
			String userCreation, String dateChange, String userChange,
			Long state, Long idCalificationItem, Long idStudent) {
		this.observation = observation;
		this.dateCreation = dateCreation;
		this.userCreation = userCreation;
		this.dateChange = dateChange;
		this.userChange = userChange;
		this.state = state;
		this.idCalificationItem = idCalificationItem;
		this.idStudent = idStudent;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "idCalification", nullable = false)
	public Long getIdCalification() {
		return this.idCalification;
	}

	public void setIdCalification(Long idCalification) {
		this.idCalification = idCalification;
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

	@Column(name = "observation")
	public String getObservation() {
		return this.observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	@Column(name = "idMoodleItem")
	public Long getIdMoodleItem() {
		return idMoodleItem;
	}

	public void setIdMoodleItem(Long idMoodleItem) {
		this.idMoodleItem = idMoodleItem;
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

	@Column(name = "idCalificationItem")
	public Long getIdCalificationItem() {
		return this.idCalificationItem;
	}

	public void setIdCalificationItem(Long idCalificationItem) {
		this.idCalificationItem = idCalificationItem;
	}

	@Column(name = "idStudent")
	public Long getIdStudent() {
		return this.idStudent;
	}

	public void setIdStudent(Long idStudent) {
		this.idStudent = idStudent;
	}

	@Column(name = "grade", precision = 17, scale = 17)
	public double getGrade() {
		return grade;
	}

	public void setGrade(double grade) {
		this.grade = grade;
	}

}