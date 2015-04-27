package com.zyos.core.mail.io.mn.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.zyos.core.common.model.AParameter;

/**
 * CutOffTime entity.
 * 
 * @author �scar Garz�n
 */
@Entity
@Table(name = "cutOffTime")
public class CutOffTime extends AParameter implements java.io.Serializable {

	// Fields
	private Long id;
	private String cutHour;

	// Constructors

	/** default constructor */
	public CutOffTime() {
	}

	/** minimal constructor */
	public CutOffTime(String name, String hourCut, String dateCreation,
			String userCreation, Long state) {
		this.name = name;
		this.cutHour = hourCut;
		this.dateCreation = dateCreation;

		this.userCreation = userCreation;
		this.state = state;
	}

	/** full constructor */
	public CutOffTime(String name, String hourCut, String dateCreation,
			String userCreation, String dateChange, String userChange,
			Long state) {
		this.name = name;
		this.cutHour = hourCut;
		this.dateCreation = dateCreation;

		this.userCreation = userCreation;
		this.dateChange = dateChange;

		this.userChange = userChange;
		this.state = state;
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

	@Column(name = "description", length = 150)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "cutHour", nullable = false, length = 20)
	public String getCutHour() {
		return this.cutHour;
	}

	public void setCutHour(String hourCut) {
		this.cutHour = hourCut;
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

	@Column(name = "state", nullable = false, length = 1)
	public Long getState() {
		return this.state;
	}

	public void setState(Long state) {
		this.state = state;
	}

}