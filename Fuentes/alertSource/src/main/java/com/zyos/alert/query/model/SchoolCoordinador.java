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
@Table(name = "school_coordinador")
public class SchoolCoordinador extends AZyosModel implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields
	private Long id;
	private transient Long zyosuser;
	private transient Long school;

	// Constructors

	/** default constructor */
	public SchoolCoordinador() {
	}

	/** minimal constructor */
	public SchoolCoordinador(Long idschoolcoord) {
		this.id = idschoolcoord;
	}

	/** full constructor */
	public SchoolCoordinador(Long idschoolcoord, Long zyosuser,
			Long school, String datecreation, String usercreation,
			String datechange, String userchange, Long state) {
		this.id = idschoolcoord;
		this.zyosuser = zyosuser;
		this.school = school;
		this.dateCreation = datecreation;
		this.userCreation = usercreation;
		this.dateCreation = datechange;
		this.userCreation = userchange;
		this.state = state;
	}

	// Property accessors
	@Id
	@Column(name = "idschoolcoord", unique = true, nullable = false)
	public Long getIdschoolcoord() {
		return this.id;
	}

	public void setIdschoolcoord(Long idschoolcoord) {
		this.id = idschoolcoord;
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