package com.zyos.core.lo.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.zyos.core.common.controller.ErrorNotificacion;
import com.zyos.core.common.model.AZyosModel;
import com.zyos.core.login.model.ZyosLogin;

/**
 * Zyosuser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "zyosUser")
public class ZyosUser extends AZyosModel implements java.io.Serializable {
	
	private Long idZyosUser;
	private Long idProfession = Long.valueOf(0);
	private Long idDocumentType = Long.valueOf(1l);
	private Long idZyosGroup;
	private Long idEnterprise;
	private Long idRelationShip;
	private String address;
	private String documentNumber;
	private String name;
	private String lastName;
	private String phone;
	private String mobilePhone;
	private String email;
	private String secondEmail;

	private Long idStudent;
	private String idDegree;
	private String code;
	
	private transient String documentNumberOld;
	private transient ZyosLogin zyosLogin;
	private transient String zyosGroup, documentType, professionName,
			nameDegree, authMode;

	// Constructors

	/** default constructor */
	public ZyosUser() {
		this.zyosLogin = new ZyosLogin();
	}

	/**
	 * Constructor for load initial list in zyos user manage backing bean
	 * 
	 * @param id
	 * @param name
	 * @param lastName
	 * @param documentNumber
	 * @param idZyosGroup
	 * @param nameZyosGroup
	 * @param email
	 */
	/* loadUserListByEnterprise */
	public ZyosUser(Long id, String name, String lastName,
			String documentNumber, Long idZyosGroup, String nameZyosGroup,
			String email, String userLogin, String phone,
			String mobilePhone, String secondEmail, Long idDocumentType, Long state, String authMode) {
		this.idZyosUser = id;
		this.name = name;
		this.lastName = lastName;
		this.documentNumber = documentNumber;
		this.idZyosGroup = idZyosGroup;
		this.zyosGroup = nameZyosGroup;
		this.email = email;		
		this.phone = phone;
		this.mobilePhone = mobilePhone;		
		this.idDocumentType = idDocumentType;
		this.secondEmail = secondEmail;
		this.state = state;
		zyosLogin = new ZyosLogin(userLogin);
		zyosLogin.setAuthMode(authMode);
		
	}

	public ZyosUser(Long id, Long idDocumentType, String documentTypeName,
			String documentNumber, String name, String lastName, String phone,
			String mobilePhone, String secondEmail, String email) {
		this.idZyosUser = id;
		this.name = name;
		this.lastName = lastName;
		this.documentNumber = documentNumber;
		this.idDocumentType = idDocumentType;
		this.documentType = documentTypeName;
		this.phone = phone;
		this.mobilePhone = mobilePhone;
		this.secondEmail = secondEmail;
		this.email = email;

	}

	public ZyosUser(Long id, String documentNumber, String name,
			String lastName, String email) {
		this.idZyosUser = id;
		this.name = name;
		this.lastName = lastName;
		this.documentNumber = documentNumber;
		this.email = email;
	}

	public ZyosUser(Long id, String documentNumber, String name, String email,
			Long idZyosGroup) {
		this.idZyosUser = id;
		this.name = name;
		this.documentNumber = documentNumber;
		this.email = email;
		this.idZyosGroup = idZyosGroup;
	}

	public ZyosUser(String secondEmail, String phone, String mobilePhone,
			Long idDocumentType, Long idProfession, String dateCreation,
			String userCreation, String userLogin, Long state) {
		try {
			this.secondEmail = secondEmail;
			this.phone = phone;
			this.mobilePhone = mobilePhone;
			this.idDocumentType = idDocumentType;
			this.documentType = idDocumentType.toString();
			this.idProfession = idProfession;
			this.professionName = idProfession.toString();
			this.zyosLogin = new ZyosLogin(userLogin);
			this.state = state;
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	/** @autor jhernandez */
	public ZyosUser(Long idZyosUser, String name, String lastName, String documentNumber,
			String email, String phone, String mobilePhone, String address,
			Long idZyosGroup, String nameDegree) {
		try {

			this.phone = phone;
			this.mobilePhone = mobilePhone;
			this.idZyosUser = idZyosUser;
			this.documentNumber = documentNumber;
			this.email = email;
			this.nameDegree = nameDegree;
			this.address = address;
			this.idZyosGroup = idZyosGroup;
			this.name = name;
			this.lastName = lastName;

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public ZyosUser(Long id, String name, String lastName,
			String documentNumber, Long idZyosGroup, String nameZyosGroup,
			String email, Long state) {
		this.idZyosUser = id;
		this.name = name;
		this.lastName = lastName;
		this.documentNumber = documentNumber;
		this.idZyosGroup = idZyosGroup;
		this.zyosGroup = nameZyosGroup;
		this.email = email;
		this.state = state;
		zyosLogin = new ZyosLogin();
	}

	public ZyosUser(Long idZyosUser,String documentNumber, String name, 
			String lastName, String phone, String mobilePhone, String email, 
			String secondEmail) {
		this.idZyosUser = idZyosUser;
		this.documentNumber = documentNumber;
		this.name = name;
		this.lastName = lastName;
		this.phone = phone;
		this.mobilePhone = mobilePhone;
		this.email = email;
		this.secondEmail = secondEmail;
	}

	/** {@link loadResponsibleListByRole(Long)} */
	public ZyosUser(Long id, String name, String lastName) {
		this.idZyosUser = id;
		this.name = name;
		this.lastName = lastName;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "idZyosUser", unique = true, nullable = false, precision = 65535, scale = 65531)
	public Long getIdZyosUser() {
		return this.idZyosUser;
	}

	public void setIdZyosUser(Long id) {
		this.idZyosUser = id;
	}

	@Column(name = "idEnterprise")
	public Long getIdEnterprise() {
		return idEnterprise;
	}

	public void setIdEnterprise(Long idEnterprise) {
		this.idEnterprise = idEnterprise;
	}

	@Column(name = "idZyosGroup")
	public Long getIdZyosGroup() {
		return idZyosGroup;
	}

	public void setIdZyosGroup(Long idZyosGroup) {
		this.idZyosGroup = idZyosGroup;
	}

	@Column(name = "idProfession", precision = 65535, scale = 65531)
	public Long getIdProfession() {
		return this.idProfession;
	}

	public void setIdProfession(Long idPost) {
		this.idProfession = idPost;
	}

	@Column(name = "idDocumentType", nullable = false, precision = 65535, scale = 65531)
	public Long getIdDocumentType() {
		return this.idDocumentType;
	}

	public void setIdDocumentType(Long idDocumentType) {
		this.idDocumentType = idDocumentType;
	}

	@Column(name = "documentNumber", nullable = false, length = 80)
	public String getDocumentNumber() {
		return this.documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	@Column(name = "name", nullable = false, length = 45)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "lastName", nullable = false, length = 45)
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "phone")
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "mobilePhone", precision = 65535, scale = 65531, nullable = true)
	public String getMobilePhone() {
		return this.mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	@Column(name = "email", length = 100)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "secondEmail", length = 100)
	public String getSecondEmail() {
		return this.secondEmail;
	}

	public void setSecondEmail(String secondEmail) {
		this.secondEmail = secondEmail;
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
	@Column(name = "userCreation", nullable = false, length = 45)
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

	@Column(name = "idRelationShip")
	public Long getIdRelationShip() {
		return idRelationShip;
	}

	public void setIdRelationShip(Long idRelationShip) {
		this.idRelationShip = idRelationShip;
	}

	@Column(name = "address")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Transient
	public String getZyosGroup() {
		return zyosGroup;
	}

	public void setZyosGroup(String nameZyosGroup) {
		this.zyosGroup = nameZyosGroup;
	}

	@Transient
	public ZyosLogin getZyosLogin() {
		return zyosLogin;
	}

	public void setZyosLogin(ZyosLogin zyosLogin) {
		this.zyosLogin = zyosLogin;
	}

	@Transient
	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	@Transient
	public String getProfessionName() {
		return professionName;
	}

	public void setProfessionName(String professionName) {
		this.professionName = professionName;
	}

	public boolean validateZyosUser() {
		if (idDocumentType != null && idDocumentType.longValue() != 0
				&& zyosLogin.getPassword() != null
				&& zyosLogin.getPasswordMD5() != null
				&& zyosLogin.getPassword().equals(zyosLogin.getPasswordMD5())) {
			return true;
		}
		return false;
	}

	@Transient
	public String getNameDegree() {
		return nameDegree;
	}

	public void setNameDegree(String nameDegree) {
		this.nameDegree = nameDegree;
	}

	@Transient
	public Long getIdStudent() {
		return idStudent;
	}

	public void setIdStudent(Long idStudent) {
		this.idStudent = idStudent;
	}

	@Transient
	public String getIdDegree() {
		return idDegree;
	}

	public void setIdDegree(String idDegree) {
		this.idDegree = idDegree;
	}

	@Transient
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Transient
	public String getDocumentNumberOld() {
		return documentNumberOld;
	}

	public void setDocumentNumberOld(String documentNumberOld) {
		this.documentNumberOld = documentNumberOld;
	}

	@Transient
	public String getAuthMode() {
		return authMode;
	}

	public void setAuthMode(String authMode) {
		this.authMode = authMode;
	}
	
	
	
	

}