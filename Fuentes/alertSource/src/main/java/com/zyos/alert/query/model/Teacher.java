package com.zyos.alert.query.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.zyos.core.common.model.AZyosModel;

/**
 * MdlCourse entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "teacher")
public class Teacher extends AZyosModel implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields
	private Long id;
	private transient Long school;
	private transient Long zyosuser;
	
	// Constructors

	/** default constructor */
	public Teacher() {
	}

	/** minimal constructor */
	public Teacher(Long idteacher) {
		this.id = idteacher;
	}

	/** full constructor */
	public Teacher(Long idteacher, Long zyosuser, Long school,
			String datecreation, String usercreation, String datechange,
			String userchange, Long state) {
		this.id = idteacher;
		this.zyosuser = zyosuser;
		this.school = school;
		this.dateCreation = datecreation;
		this.userCreation = usercreation;
		this.dateChange = datechange;
		this.userChange = userchange;
		this.state = state;
	}

	// Property accessors
	@Id
	@Column(name = "idteacher", unique = true, nullable = false)
	public Long getIdteacher() {
		return this.id;
	}

	public void setIdteacher(Long idteacher) {
		this.id = idteacher;
	}

	@JoinColumn(name = "idzyosuser")
	public Long getZyosuser() {
		return this.zyosuser;
	}

	public void setZyosuser(Long zyosuser) {
		this.zyosuser = zyosuser;
	}

	@JoinColumn(name = "idschool")
	public Long getSchool() {
		return this.school;
	}

	public void setSchool(Long school) {
		this.school = school;
	}
	
}