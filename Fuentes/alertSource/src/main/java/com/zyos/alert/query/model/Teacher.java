package com.zyos.alert.query.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.zyos.core.lo.user.model.ZyosUser;

/**
 * Teacher entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "teacher")
public class Teacher extends com.zyos.core.common.model.AZyosModel
	implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idteacher;
	private School school;
	private String datecreation;
	private String usercreation;
	private String datechange;
	private String userchange;
	private Long state;
	
	private transient String zyosUserName;
	private transient String zyosUserLastName;
	private transient Long idZyosUser;

	// Constructors

	/** default constructor */
	public Teacher() {
	}

	/** minimal constructor */
	public Teacher(Long idteacher) {
		this.idteacher = idteacher;
	}

	/** full constructor */
	public Teacher(Long idteacher, ZyosUser zyosuser, School school,
			String datecreation, String usercreation, String datechange,
			String userchange, Long state) {
		this.idteacher = idteacher;
		this.school = school;
		this.datecreation = datecreation;
		this.usercreation = usercreation;
		this.datechange = datechange;
		this.userchange = userchange;
		this.state = state;
	}
	
	public Teacher(Long idteacher, Long zyosuser, String Name, String LastName) {
		this.idteacher = idteacher;
		this.idZyosUser = zyosuser;
		this.zyosUserName = Name;
		this.zyosUserLastName = LastName;
	}

	// Property accessors
	@Id
	@Column(name = "idteacher", unique = true, nullable = false)
	public Long getIdteacher() {
		return this.idteacher;
	}

	public void setIdteacher(Long idteacher) {
		this.idteacher = idteacher;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idzyosuser")
	public Long getZyosuser() {
		return this.idZyosUser;
	}

	public void setZyosuser(Long zyosuser) {
		this.idZyosUser = zyosuser;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idschool")
	public School getSchool() {
		return this.school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	@Column(name = "datecreation", length = 20)
	public String getDatecreation() {
		return this.datecreation;
	}

	public void setDatecreation(String datecreation) {
		this.datecreation = datecreation;
	}

	@Column(name = "usercreation", length = 45)
	public String getUsercreation() {
		return this.usercreation;
	}

	public void setUsercreation(String usercreation) {
		this.usercreation = usercreation;
	}

	@Column(name = "datechange", length = 20)
	public String getDatechange() {
		return this.datechange;
	}

	public void setDatechange(String datechange) {
		this.datechange = datechange;
	}

	@Column(name = "userchange", length = 45)
	public String getUserchange() {
		return this.userchange;
	}

	public void setUserchange(String userchange) {
		this.userchange = userchange;
	}

	@Column(name = "state")
	public Long getState() {
		return this.state;
	}

	public void setState(Long state) {
		this.state = state;
	}

	public String getZyosUserLastName() {
		return zyosUserLastName;
	}

	public void setZyosUserLastName(String zyosUserLastName) {
		this.zyosUserLastName = zyosUserLastName;
	}

	public String getZyosUserName() {
		return zyosUserName;
	}

	public void setZyosUserName(String zyosUserName) {
		this.zyosUserName = zyosUserName;
	}

}