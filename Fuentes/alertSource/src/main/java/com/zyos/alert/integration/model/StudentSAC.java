package com.zyos.alert.integration.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Student entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "student")
public class StudentSAC extends com.zyos.core.common.model.AZyosModel implements
		java.io.Serializable {

	// Fields

	private Long idStudent;
	private Long idZyosUser;
	private String nameResponsible;
	private String phoneResponsible;
	private String mobilePhoneResponsible;
	private String code;
	private Long idStudentSAC;

	private transient String name;
	private transient String email;
	private transient String address;
	private transient String phone;
	private transient String mobilePhone;
	private transient String secondEmail;
	private transient String documentNumber;
	private transient Long id;

	private transient boolean selected = false;
	private transient Long idStudentSubject;
	private transient Long idDegree;
	private transient String nameDegree;
	private transient String grade;
	private transient String idCalificationItemStudent;
	private transient Long idCalificationItem;
	private transient Long studentAbsent;
	private transient Double finalCalificationStudent;
	private transient Double[] studentGrade;
	private transient List<Long> idCalificationItemSudent;

	// Constructors

	/** default constructor */
	public StudentSAC() {
	}

	/** minimal constructor */
	public StudentSAC(String dateCreation, String userCreation, Long state) {

		this.dateCreation = dateCreation;
		this.userCreation = userCreation;
		this.state = state;
	}

	// Property accessors
	@Id
	@Column(name = "idStudent", nullable = false)
	public Long getIdStudent() {
		return this.idStudent;
	}

	public void setIdStudent(Long idStudent) {
		this.idStudent = idStudent;
	}

	@Column(name = "idZyosUser")
	public Long getIdZyosUser() {
		return this.idZyosUser;
	}

	public void setIdZyosUser(Long idZyosUser) {
		this.idZyosUser = idZyosUser;
	}
	
	
	@Column(name = "idStudentSAC")
	public Long getIdStudentSAC() {
		return idStudentSAC;
	}

	public void setIdStudentSAC(Long idStudentSAC) {
		this.idStudentSAC = idStudentSAC;
	}

	@Column(name = "code")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "dateCreation", nullable = false, length = 20)
	public String getDateCreation() {
		return this.dateCreation;
	}

	public void setDateCreation(String dateCreation) {
		this.dateCreation = dateCreation;
	}

	@Column(name = "userCreation", nullable = false, length = 45)
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

	@Column(name = "nameResponsible", nullable = false, length = 120)
	public String getNameResponsible() {
		return this.nameResponsible;
	}

	public void setNameResponsible(String nameResponsible) {
		this.nameResponsible = nameResponsible;
	}

	@Column(name = "phoneResponsible", nullable = false)
	public String getPhoneResponsible() {
		return this.phoneResponsible;
	}

	public void setPhoneResponsible(String phoneResponsible) {
		this.phoneResponsible = phoneResponsible;
	}

	@Column(name = "mobilePhoneResponsible", nullable = false)
	public String getMobilePhoneResponsible() {
		return mobilePhoneResponsible;
	}

	public void setMobilePhoneResponsible(String mobilePhoneResponsible) {
		this.mobilePhoneResponsible = mobilePhoneResponsible;
	}

	@Transient
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Transient
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Transient
	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	@Transient
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	@Transient
	public Long getIdStudentSubject() {
		return idStudentSubject;
	}

	public void setIdStudentSubject(Long idStudentSubject) {
		this.idStudentSubject = idStudentSubject;
	}

	@Transient
	public Long getIdDegree() {
		return idDegree;
	}

	public void setIdDegree(Long idDegree) {
		this.idDegree = idDegree;
	}

	@Transient
	public String getNameDegree() {
		return nameDegree;
	}

	public void setNameDegree(String nameDegree) {
		this.nameDegree = nameDegree;
	}

	@Transient
	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	@Transient
	public Long getIdCalificationItem() {
		return idCalificationItem;
	}

	public void setIdCalificationItem(Long idCalificationItem) {
		this.idCalificationItem = idCalificationItem;
	}

	@Transient
	public Double[] getStudentGrade() {
		return studentGrade;
	}

	public void setStudentGrade(Double[] studentGrade) {
		this.studentGrade = studentGrade;
	}

	@Transient
	public String getIdCalificationItemStudent() {
		return idCalificationItemStudent;
	}

	public void setIdCalificationItemStudent(String idCalificationItemStudent) {
		this.idCalificationItemStudent = idCalificationItemStudent;
	}

	@Transient
	public List<Long> getIdCalificationItemSudent() {
		return idCalificationItemSudent;
	}

	public void setIdCalificationItemSudent(List<Long> idCalificationItemSudent) {
		this.idCalificationItemSudent = idCalificationItemSudent;
	}

	@Transient
	public Double getFinalCalificationStudent() {
		return finalCalificationStudent;
	}

	public void setFinalCalificationStudent(Double finalCalificationStudent) {
		this.finalCalificationStudent = finalCalificationStudent;
	}

	@Transient
	public Long getStudentAbsent() {
		return studentAbsent;
	}

	public void setStudentAbsent(Long studentAbsent) {
		this.studentAbsent = studentAbsent;
	}

	public String getSecondEmail() {
		return secondEmail;
	}

	public void setSecondEmail(String secondEmail) {
		this.secondEmail = secondEmail;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}