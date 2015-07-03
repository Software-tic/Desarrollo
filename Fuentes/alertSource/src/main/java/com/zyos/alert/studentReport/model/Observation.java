package com.zyos.alert.studentReport.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.zyos.core.common.util.ManageDate;

/**
 * Observation entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "observation")
public class Observation extends com.zyos.core.common.model.AZyosModel
		implements java.io.Serializable {

	// Fields

	private Long idObservation;
	private Long idStatusReportStudent;
	private Long idReportStudent;
	private String dateIntervention;
	private Long idAdviser;
	private String detailObservation;
	private Long idStage;
	
	//SIAT -TUNJA
	
	private Long privacy;
	private String timestart;
	private String timefinish;
	//private String timeexecuteintervenction;
	private String agreements;
	

	//Transients
	
	private String responsible;
	private String role;
	private String days;
	
	// Constructors

	/** default constructor */
	public Observation() {
	}

	/** minimal constructor */
	public Observation(String dateCreation, Long state) {
		this.dateCreation = dateCreation;
		this.state = state;
	}
	
	public Observation(Long id, String dateIntervention, String role,
			String responsible, String detailObservation, String timestart, String timefinish) {
		this.idObservation = id;
		this.dateIntervention = dateIntervention;
		this.detailObservation = detailObservation;
		this.timestart = timestart;
		this.timefinish = timefinish;
		this.responsible = responsible;
		this.role = role;
		double f= Long.parseLong(timefinish);
		double s= Long.parseLong(timefinish);
		//this.timeexecuteintervenction=String.valueOf(f-s);
	}

	/** full constructor */
	public Observation(Long idStatusReportStudent, Long idReportStudent,
			String dateIntervention, String dateCreation, String userCreation,
			String dateChange, String userChange, Long state) {
		this.idStatusReportStudent = idStatusReportStudent;
		this.idReportStudent = idReportStudent;
		this.dateIntervention = dateIntervention;
		this.dateCreation = dateCreation;
		this.userCreation = userCreation;
		this.dateChange = dateChange;
		this.userChange = userChange;
		this.state = state;
	}

	/**@author oortiz {@link loadObservationByStudent(Long)}*/
	public Observation(Long id, String dateIntervention, String role,
			String responsible, String detailObservation) {
		this.idObservation = id;
		this.dateIntervention = dateIntervention;
		this.role = role;
		this.responsible = responsible;
		this.detailObservation = detailObservation;
		if (dateIntervention != null) {
		Date startDate = ManageDate.formatDate(dateIntervention, ManageDate.YYYY_MM_DD);
		Long days = (new Date().getTime() - startDate.getTime());
		days = (days / 1000 / 3600 / 24) + 1;
		this.days = days.toString(); 
		}
	}
	
	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "idObservation", nullable = false)
	public Long getIdObservation() {
		return this.idObservation;
	}

	public void setIdObservation(Long idObservation) {
		this.idObservation = idObservation;
	}

	@Column(name = "idStatusReportStudent")
	public Long getIdStatusReportStudent() {
		return this.idStatusReportStudent;
	}

	public void setIdStatusReportStudent(Long idStatusReportStudent) {
		this.idStatusReportStudent = idStatusReportStudent;
	}

	@Column(name = "idReportStudent")
	public Long getIdReportStudent() {
		return this.idReportStudent;
	}

	public void setIdReportStudent(Long idReportStudent) {
		this.idReportStudent = idReportStudent;
	}

	@Column(name = "dateIntervention", length = 20)
	public String getDateIntervention() {
		return this.dateIntervention;
	}

	public void setDateIntervention(String dateIntervention) {
		this.dateIntervention = dateIntervention;
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

	@Column(name = "idAdviser")
	public Long getIdAdviser() {
		return idAdviser;
	}

	public void setIdAdviser(Long idAdviser) {
		this.idAdviser = idAdviser;
	}

	@Column(name = "detailObservation")
	public String getDetailObservation() {
		return detailObservation;
	}

	public void setDetailObservation(String detailObservation) {
		this.detailObservation = detailObservation;
	}

	@Column(name = "idStage")
	public Long getIdStage() {
		return idStage;
	}

	public void setIdStage(Long idStage) {
		this.idStage = idStage;
	}

	@Transient
	public String getResponsible() {
		return responsible;
	}

	public void setResponsible(String responsible) {
		this.responsible = responsible;
	}

	@Transient
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Transient
	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	//SIAT - TUNJA
	
	public Long getPrivacy() {
		return privacy;
	}

	public void setPrivacy(Long privacy) {
		this.privacy = privacy;
	}

	public String getTimestart() {
		return timestart;
	}

	public void setTimestart(String timestart) {
		this.timestart = timestart;
	}

	public String getTimefinish() {
		return timefinish;
	}

	public void setTimefinish(String timefinish) {
		this.timefinish = timefinish;
	}

	/*public String getTimeexecuteintervenction() {
		return timeexecuteintervenction;
	}

	public void setTimeexecuteintervenction(String timeexecuteintervenction) {
		this.timeexecuteintervenction = timeexecuteintervenction;
	}*/

	public String getAgreements() {
		return agreements;
	}

	public void setAgreements(String agreements) {
		this.agreements = agreements;
	}

	
}