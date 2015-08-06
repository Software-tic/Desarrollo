package com.zyos.core.mail.io.mn.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.zyos.core.common.model.AZyosModel;

/**
 * Notification entity.
 * 
 * @author Óscar Garzón
 */
@Entity
@Table(name = "notification")
public class Notification extends AZyosModel implements java.io.Serializable {

	// Fields
	private Long id;
	private Long idCutOffTime;
	private Long idNotificationType;
	private Long idZyosUser;

	// Constructors

	/** default constructor */
	public Notification() {
	}

	/** minimal constructor */
	public Notification(Long idCutOffTime, Long idFormatNotification,
			Long idZyosUser, String user, boolean isNew) {
		this.idCutOffTime = idCutOffTime;
		this.idNotificationType = idFormatNotification;
		this.idZyosUser = idZyosUser;
		this.initializing(user, isNew);
	}

	/** full constructor */
	public Notification(Long idCutOffTime, Long idFormatNotification,
			Long idZyosUser, String dateCreation, String userCreation,
			String dateChange, String userChange, Long state) {
		this.idCutOffTime = idCutOffTime;
		this.idNotificationType = idFormatNotification;
		this.idZyosUser = idZyosUser;
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

	@Column(name = "idCutOffTime", nullable = false)
	public Long getIdCutOffTime() {
		return this.idCutOffTime;
	}

	public void setIdCutOffTime(Long idCutOffTime) {
		this.idCutOffTime = idCutOffTime;
	}

	@Column(name = "idNotificationType", nullable = false)
	public Long getIdTypeNotification() {
		return this.idNotificationType;
	}

	public void setIdTypeNotification(Long idFormatNotification) {
		this.idNotificationType = idFormatNotification;
	}

	@Column(name = "idZyosUser", nullable = false)
	public Long getIdZyosUser() {
		return this.idZyosUser;
	}

	public void setIdZyosUser(Long idZyosUser) {
		this.idZyosUser = idZyosUser;
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
	@Column(name = "userCreation", nullable = false, length = 45)
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
	@Column(name = "state", nullable = false, length = 1)
	public Long getState() {
		return this.state;
	}

	@Override
	public void setState(Long state) {
		this.state = state;
	}

}