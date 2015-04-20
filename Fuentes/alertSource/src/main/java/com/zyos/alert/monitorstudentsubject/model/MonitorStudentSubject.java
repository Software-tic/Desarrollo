package com.zyos.alert.monitorstudentsubject.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * MonitorStudentSubject entity. @author jhernandez
 */

@Entity
@Table(name = "monitorstudentsubject")
public class MonitorStudentSubject extends com.zyos.core.common.model.AZyosModel
		implements java.io.Serializable {

	// Fields

	private Long idMonitorStudentSubject;
	private Long idStudentSubject;
	
	private transient String name;
	private transient String lastName;

	// Constructors

	/** default constructor */
	public MonitorStudentSubject() {
	}

	public MonitorStudentSubject(Long idMonitorStudentSubject, Long idStudentSubject) {
		this.idMonitorStudentSubject = idMonitorStudentSubject;
		this.idStudentSubject = idStudentSubject;				
	}

	public MonitorStudentSubject(Long idMonitorStudentSubject, Long idStudentSubject, String dateCreation,
			String userCreation, Long state) {

		this.idMonitorStudentSubject = idMonitorStudentSubject;
		this.idStudentSubject = idStudentSubject;
		this.dateCreation = dateCreation;
		this.userCreation = userCreation;
		this.state = state;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "idMonitorStudentSubject", nullable = false)
	public Long getIdMonitorStudentSubject() {
		return this.idMonitorStudentSubject;
	}

	public void setIdMonitorStudentSubject(Long idMonitorStudentSubject) {
		this.idMonitorStudentSubject = idMonitorStudentSubject;
	}	

	@Column(name = "idStudentSubject", nullable = true)
	public Long getIdStudentSubject() {
		return idStudentSubject;
	}

	public void setIdStudentSubject(Long idStudentSubject) {
		this.idStudentSubject = idStudentSubject;
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
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Transient
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	
	
	

	

}
