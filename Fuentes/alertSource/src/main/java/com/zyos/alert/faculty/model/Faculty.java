package com.zyos.alert.faculty.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "faculty")
public class Faculty extends com.zyos.core.common.model.AZyosModel implements
		java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idFaculty;
	private String name;

	public Faculty() {

	}

	public Faculty(Long idFaculty) {
		this.idFaculty = idFaculty;
	}
	
	public Faculty(Long idFaculty, String name) {
		this.idFaculty = idFaculty;
		this.name = name;
	}

	// Property accessors
	@Id
	@Column(name = "idFaculty", nullable = false)
	public Long getIdFaculty() {
		return idFaculty;
	}

	public void setIdFaculty(Long idFaculty) {
		this.idFaculty = idFaculty;
	}

	@Column(name = "name", nullable = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

}
