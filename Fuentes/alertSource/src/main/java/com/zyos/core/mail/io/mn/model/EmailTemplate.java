package com.zyos.core.mail.io.mn.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.zyos.core.common.controller.ErrorNotificacion;
import com.zyos.core.common.model.AZyosModel;

/**
 * Emailtemplate entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "emailtemplate")
public class EmailTemplate extends AZyosModel implements java.io.Serializable {

	// Fields
	private Long id;
	private String name;
	private Long idEmailTemplateType;
	private Long idEnterprise;
	private String body;
	private String subject;
	private String analyticsCode;
	
	private transient String nameTemplateType;

	// Constructors

	/** default constructor */
	public EmailTemplate() {
	}

	/** minimal constructor */
	public EmailTemplate(Long idEmailTemplateType, String body, String subject,
			String dateCreation, String userCreation, Long state) {
		this.idEmailTemplateType = idEmailTemplateType;
		this.body = body;
		this.subject = subject;
		this.dateCreation = dateCreation;
		this.userCreation = userCreation;
		this.state = state;
	}

	/** full constructor */
	public EmailTemplate(Long id, Long idEmailTemplateType, String name,
			String analyticsCode, String body, String subject, String tokens) {
		this.idEmailTemplateType = idEmailTemplateType;
		this.body = body;
		this.subject = subject;
		this.analyticsCode = analyticsCode;
		this.name = name;
		this.id = id;
	}

	public EmailTemplate(Long id, String dateCreation, String userCreation,
			String dateChange, String userChange, Long state, String name,
			Long idEmailTemplateType, String body, String subject,
			String analyticsCode, Long idEnterprise, String nameTemplateType) {
		this.id = id;
		this.dateCreation = dateCreation;
		this.userCreation = userCreation;
		this.dateChange = dateChange;
		this.userChange = userChange;
		this.state = state;
		this.name = name;
		this.idEmailTemplateType = idEmailTemplateType;
		this.body = body;
		this.subject = subject;
		this.analyticsCode = analyticsCode;
		this.idEnterprise = idEnterprise;
		this.nameTemplateType = nameTemplateType;
	}

	public EmailTemplate(Object[] o) {
		try {
			// et.id, et.name, et.idEnterprise, et.idEmailTemplateType, et.body,
			// et.subject
			this.id = Long.valueOf(o[0].toString());
			this.name = o[1].toString();
			this.idEnterprise = Long.valueOf(o[2].toString());
			this.idEmailTemplateType = Long.valueOf(o[3].toString());
			this.body = o[4].toString();
			this.subject = o[5].toString();
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
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

	@Column(name = "idEmailTemplateType", nullable = false)
	public Long getIdEmailTemplateType() {
		return this.idEmailTemplateType;
	}

	public void setIdEmailTemplateType(Long idEmailTemplateType) {
		this.idEmailTemplateType = idEmailTemplateType;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "body", nullable = false)
	public String getBody() {
		return this.body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Column(name = "subject", nullable = false, length = 120)
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Column(name = "analyticscode", length = 45)
	public String getAnalyticsCode() {
		return this.analyticsCode;
	}

	public void setAnalyticsCode(String analyticsCode) {
		this.analyticsCode = analyticsCode;
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
	@Column(name = "state", nullable = false)
	public Long getState() {
		return this.state;
	}

	@Override
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

	@Transient
	public String getNameTemplateType() {
		return nameTemplateType;
	}

	public void setNameTemplateType(String nameTemplateType) {
		this.nameTemplateType = nameTemplateType;
	}
	
	
}