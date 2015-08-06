package com.zyos.alert.absent.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * Absent entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "absent")
public class Absent extends com.zyos.core.common.model.AZyosModel implements
		java.io.Serializable {

	// Fields
	
	private Long idAbsent;
	private Long idZyosUser;
	private Long idStudentSubject;
	private String dateAbsent;
	private String observationAbsent;
	
	private transient String name;
	private transient String lastName;
	private transient String documentNumber;
	private transient String code;

	// Constructors

	/** default constructor */
	public Absent() {
	}

	/** minimal constructor */
	public Absent(String dateAbsent, String dateCreation, Long state) {
		this.dateAbsent = dateAbsent;
		this.dateCreation = dateCreation;
		this.state = state;
	}

	/** full constructor */
	public Absent(Long idZyosUser, Long idStudentSubject, String dateAbsent,
			String dateCreation, String userCreation, String dateChange,
			String userChange, Long state) {
		this.idZyosUser = idZyosUser;
		this.idStudentSubject = idStudentSubject;
		this.dateAbsent = dateAbsent;
		this.dateCreation = dateCreation;
		this.userCreation = userCreation;
		this.dateChange = dateChange;
		this.userChange = userChange;
		this.state = state;
	}
	
	
	public Absent(Long idAbsent, Long idZyosUser, Long idStudentSubject, String dateAbsent,
			String name, String lastName, String documentNumber, String code) {
		
		this.idZyosUser = idZyosUser;
		this.idStudentSubject = idStudentSubject;
		this.dateAbsent = dateAbsent;
		this.idAbsent = idAbsent;
		this.name = name;
		this.documentNumber = documentNumber;
		this.code = code;
		this.lastName = lastName;

	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "idAbsent", nullable = false)
	public Long getIdAbsent() {
		return this.idAbsent;
	}

	public void setIdAbsent(Long idAbsent) {
		this.idAbsent = idAbsent;
	}

	@Column(name = "idZyosUser")
	public Long getIdZyosUser() {
		return this.idZyosUser;
	}

	public void setIdZyosUser(Long idZyosUser) {
		this.idZyosUser = idZyosUser;
	}

	@Column(name = "idStudentSubject")
	public Long getIdStudentSubject() {
		return this.idStudentSubject;
	}

	public void setIdStudentSubject(Long idStudentSubject) {
		this.idStudentSubject = idStudentSubject;
	}

	@Column(name = "dateAbsent", nullable = false, length = 20)
	public String getDateAbsent() {
		return this.dateAbsent;
	}

	public void setDateAbsent(String dateAbsent) {
		this.dateAbsent = dateAbsent;
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

	@Column(name = "observationAbsent", nullable = true)
	public String getObservationAbsent() {
		return observationAbsent;
	}

	public void setObservationAbsent(String observationAbsent) {
		this.observationAbsent = observationAbsent;
	}

	@Transient
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Transient
	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	@Transient
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Transient
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	
	
	
	
	
	
	


	

}