package com.zyos.core.login.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.zyos.core.common.model.AZyosModel;

/**
 * Zyoslogin entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "zyosLogin")
public class ZyosLogin extends AZyosModel implements java.io.Serializable {

	// Fields
	private Long id;
	private Long idZyosUser;
	private String userLogin;
	private String authMode;
	private String password;
	private String passwordMD5;
	private Integer firstLogin = Integer.valueOf(1);
	private String deadLine;

	private transient String rePassword;

	// Constructors

	/** default constructor */
	public ZyosLogin() {
	}

	/** minimal constructor */
	public ZyosLogin(Long idZyosUser, String UserLogin, String password,
			String passwordmd5, Integer firstlogin, String dateCreation,
			String userCreation, Long state) {
		this.idZyosUser = idZyosUser;
		this.userLogin = UserLogin;
		this.password = password;
		this.passwordMD5 = passwordmd5;
		this.firstLogin = firstlogin;
		this.dateCreation = dateCreation;

		this.userCreation = userCreation;
		this.state = state;
	}

	/** full constructor */
	public ZyosLogin(Long idZyosUser, String UserLogin, String password,
			String passwordmd5, Integer firstlogin, String dateCreation,
			String userCreation, String dateChange, String userChange,
			Long state) {
		this.idZyosUser = idZyosUser;
		this.userLogin = UserLogin;
		this.password = password;
		this.passwordMD5 = passwordmd5;
		this.firstLogin = firstlogin;
		this.dateCreation = dateCreation;

		this.userCreation = userCreation;
		this.dateChange = dateChange;

		this.userChange = userChange;
		this.state = state;
	}

	/**
	 * Constructor for load additional information for user list
	 * 
	 * @param userLogin
	 * @param password
	 * @param passwordMD5
	 * @param deadLine
	 */
	public ZyosLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "idZyosUser", nullable = false)
	public Long getIdZyosUser() {
		return this.idZyosUser;
	}

	public void setIdZyosUser(Long idZyosUser) {
		this.idZyosUser = idZyosUser;
	}

	@Column(name = "userLogin", nullable = false, length = 45)
	public String getUserLogin() {
		return this.userLogin;
	}

	public void setUserLogin(String UserLogin) {
		this.userLogin = UserLogin;
	}

	@Column(name = "password", nullable = false, length = 45)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "passwordMD5", nullable = false, length = 120)
	public String getPasswordMD5() {
		return this.passwordMD5;
	}

	public void setPasswordMD5(String passwordmd5) {
		this.passwordMD5 = passwordmd5;
	}

	@Column(name = "firstLogin", nullable = false)
	public Integer getFirstLogin() {
		return this.firstLogin;
	}

	public void setFirstLogin(Integer firstlogin) {
		this.firstLogin = firstlogin;
	}

	@Column(name = "deadLine")
	public String getDeadLine() {
		return deadLine;
	}

	public void setDeadLine(String deadLine) {
		this.deadLine = deadLine;
	}

	@Column(name = "authMode")
	public String getAuthMode() {
		return authMode;
	}

	public void setAuthMode(String authMode) {
		this.authMode = authMode;
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

	@Transient
	public String getRePassword() {
		return rePassword;
	}

	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}
}