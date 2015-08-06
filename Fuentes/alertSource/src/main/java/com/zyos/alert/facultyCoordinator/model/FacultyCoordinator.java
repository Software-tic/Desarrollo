package com.zyos.alert.facultyCoordinator.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "facultycoordinator")
public class FacultyCoordinator extends com.zyos.core.common.model.AZyosModel
		implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idFacultyCoordinator;
	private Long idZyosUser;
	private Long idFaculty;

	public FacultyCoordinator() {

	}

	public FacultyCoordinator(Long idFacultyCoordinator, Long idZyosUser,
			Long idFaculty) {
		this.idFacultyCoordinator = idFacultyCoordinator;
		this.idZyosUser = idZyosUser;
		this.idFaculty = idFaculty;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "idFacultyCoordinator", nullable = false)
	public Long getIdFacultyCoordinator() {
		return this.idFacultyCoordinator;
	}

	public void setIdFacultyCoordinator(Long idFacultyCoordinator) {
		this.idFacultyCoordinator = idFacultyCoordinator;
	}

	@Column(name = "idZyosUser", nullable = true)
	public Long getIdZyosUser() {
		return idZyosUser;
	}

	public void setIdZyosUser(Long idZyosUser) {
		this.idZyosUser = idZyosUser;
	}

	@Column(name = "idFaculty", nullable = true)
	public Long getIdFaculty() {
		return idFaculty;
	}

	public void setIdFaculty(Long idFaculty) {
		this.idFaculty = idFaculty;
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
	@Column(name = "userCreation", length = 45)
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

}
