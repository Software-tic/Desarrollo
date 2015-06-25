package com.zyos.alert.query.model;

<<<<<<< HEAD
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

=======
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * School entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "school")
public class School extends com.zyos.core.common.model.AZyosModel 
	implements java.io.Serializable {

	// Fields

	private Long idschool;
	private String nameSchool;
	private String datecreation;
	private String usercreation;
	private String datechange;
	private String userchange;
	private Long state;	
>>>>>>> origin/master

	// Constructors

	/** default constructor */
	public School() {
	}

	/** minimal constructor */
	public School(Long idschool, String nameSchool) {
<<<<<<< HEAD
		this.id = idschool;
		this.name = nameSchool;
=======
		this.idschool = idschool;
		this.nameSchool = nameSchool;
>>>>>>> origin/master
	}

	/** full constructor */
	public School(Long idschool, String nameSchool, String datecreation,
			String usercreation, String datechange, String userchange,
			Long state) {
<<<<<<< HEAD
		this.id = idschool;
		this.name = nameSchool;
		this.dateCreation = datecreation;
		this.userCreation = usercreation;
		this.dateChange = datechange;
		this.userChange = userchange;
=======
		this.idschool = idschool;
		this.nameSchool = nameSchool;
		this.datecreation = datecreation;
		this.usercreation = usercreation;
		this.datechange = datechange;
		this.userchange = userchange;
>>>>>>> origin/master
		this.state = state;
	}

	// Property accessors
	@Id
	@Column(name = "idschool", unique = true, nullable = false)
	public Long getIdschool() {
<<<<<<< HEAD
		return this.id;
	}

	public void setIdschool(Long idschool) {
		this.id = idschool;
=======
		return this.idschool;
	}

	public void setIdschool(Long idschool) {
		this.idschool = idschool;
>>>>>>> origin/master
	}

	@Column(name = "name_school", nullable = false, length = 200)
	public String getNameSchool() {
<<<<<<< HEAD
		return this.name;
	}

	public void setNameSchool(String nameSchool) {
		this.name = nameSchool;
	}
	
=======
		return this.nameSchool;
	}

	public void setNameSchool(String nameSchool) {
		this.nameSchool = nameSchool;
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
>>>>>>> origin/master
}