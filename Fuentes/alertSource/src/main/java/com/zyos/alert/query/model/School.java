package com.zyos.alert.query.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.zyos.core.common.model.AZyosModel;

/**
 * MdlCourse entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "school")
public class School extends AZyosModel implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields
	private Long id;
	private String name;


	// Constructors

	/** default constructor */
	public School() {
	}

	/** minimal constructor */
	public School(Long idschool, String nameSchool) {
		this.id = idschool;
		this.name = nameSchool;
	}

	/** full constructor */
	public School(Long idschool, String nameSchool, String datecreation,
			String usercreation, String datechange, String userchange,
			Long state) {
		this.id = idschool;
		this.name = nameSchool;
		this.dateCreation = datecreation;
		this.userCreation = usercreation;
		this.dateChange = datechange;
		this.userChange = userchange;
		this.state = state;
	}

	// Property accessors
	@Id
	@Column(name = "idschool", unique = true, nullable = false)
	public Long getIdschool() {
		return this.id;
	}

	public void setIdschool(Long idschool) {
		this.id = idschool;
	}

	@Column(name = "name_school", nullable = false, length = 200)
	public String getNameSchool() {
		return this.name;
	}

	public void setNameSchool(String nameSchool) {
		this.name = nameSchool;
	}
	
}