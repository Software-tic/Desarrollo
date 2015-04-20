package com.zyos.alert.familyStudent.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * FamilyStudent entity. @author jhernandez
 */

@Entity
@Table(name = "familystudent")
public class FamilyStudent extends com.zyos.core.common.model.AZyosModel
		implements java.io.Serializable {

	// Fields

	private Long idFamilyStudent;
	private String name;
	private String document;
	private String email;
	private Long phone;
	private Long mobilePhone;
	private String address;
	private Long idStudent;
	private Long idRelationship;
	private Long idRiskFactor;

	// Constructors

	/** default constructor */
	public FamilyStudent() {
	}

	public FamilyStudent(Long idFamilyStudent, String name, String document,
			String email, Long phone, Long mobilePhone, String address,
			Long idStudent, Long idRelationship, Long idRiskFactor) {
		this.idFamilyStudent = idFamilyStudent;
		this.name = name;
		this.document = document;
		this.email = email;
		this.phone = phone;
		this.mobilePhone = mobilePhone;
		this.address = address;
		this.idStudent = idStudent;
		this.idRelationship = idRelationship;
		this.idRiskFactor = idRiskFactor;
				
	}

	public FamilyStudent(String name, String document, String email,
			Long phone, Long mobilePhone, String address, String dateCreation,
			String userCreation, Long state) {

		this.name = name;
		this.document = document;
		this.email = email;
		this.phone = phone;
		this.mobilePhone = mobilePhone;
		this.address = address;
		this.dateCreation = dateCreation;
		this.userCreation = userCreation;
		this.state = state;

	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "idFamilyStudent", nullable = false)
	public Long getIdFamilyStudent() {
		return this.idFamilyStudent;
	}

	public void setIdFamilyStudent(Long idFamilyStudent) {
		this.idFamilyStudent = idFamilyStudent;
	}

	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "document", nullable = false)
	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	@Column(name = "email", nullable = true)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "phone", nullable = true)
	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}

	@Column(name = "mobilePhone", nullable = true)
	public Long getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(Long mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	@Column(name = "address", nullable = true)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "idStudent", nullable = false)
	public Long getIdStudent() {
		return idStudent;
	}

	public void setIdStudent(Long idStudent) {
		this.idStudent = idStudent;
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

	@Column(name = "idRelationship", nullable = false)
	public Long getIdRelationship() {
		return idRelationship;
	}

	public void setIdRelationship(Long idRelationship) {
		this.idRelationship = idRelationship;
	}

	@Column(name = "idRiskFactor", nullable = true)
	public Long getIdRiskFactor() {
		return idRiskFactor;
	}

	public void setIdRiskFactor(Long idRiskFactor) {
		this.idRiskFactor = idRiskFactor;
	}
	
	
	
	

}
