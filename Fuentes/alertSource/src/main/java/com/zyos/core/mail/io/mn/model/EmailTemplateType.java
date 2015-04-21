package com.zyos.core.mail.io.mn.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.zyos.core.common.model.AParameter;

/**
 * Typeemailtemplate entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "emailtemplateType")
public class EmailTemplateType extends AParameter implements
		java.io.Serializable {
	private Long id;
	// Fields
	private Long idEnterprise;

	// Constructors

	/** default constructor */
	public EmailTemplateType() {
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

	@Column(name = "name", nullable = false, length = 45)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "description", length = 120)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	@Column(name = "idEnterprise", nullable = false)
	public Long getIdEnterprise() {
		return idEnterprise;
	}

	public void setIdEnterprise(Long idEnterprise) {
		this.idEnterprise = idEnterprise;
	}

	@Override
	public Long getIdFacultad() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setIdFacultad(Long IdFacultad) {
		// TODO Auto-generated method stub
		
	}

}