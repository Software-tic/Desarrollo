package com.zyos.alert.studentReport.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.zyos.core.common.util.ManageDate;

/**
 * Reportstudent entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "reportstudent")
public class ReportStudent extends com.zyos.core.common.model.AZyosModel
		implements java.io.Serializable {

	// Fields

	private Long idReportStudent;
	private Long idReportType;
	private Long idStatusReportStudent;
	private BigDecimal idStudent;
	private Long idStage;
	private Long idSolicitor;
	private Long idAdviser;
	private String detailReport;
	private Long idRiskFactor;
	private Long firstIntervention;
	private Long idZyosUserAdviserFaculty;
	private Long idZyosGroup;
	
	//Transients
	
	private transient String nameResponsible;
	private transient String mobilePhoneResponsible;
	private transient Long phoneResponsible;
	private transient String zyosUserName;
	private transient String zyosUserLastName;
	private transient String nameSolicitor;
	private transient String zyosGroupName;
	private transient String emailSolicitor;
	private transient String riskFactorName;
	private transient Long idZyosUser;
	private transient Long isButton;
	private transient Long isButtonCase;
	private transient String code;
	private transient String documentNumber;
	private BigDecimal count;
	private transient String phone;
	private transient String mobilePhone;
	
	
	// fields for search
	private transient String dateFrom;
	private transient String dateTo;
	private transient String emailStudent;
	private transient Long idFaculty;
	private transient Long idRiskFactorCategory;
	
	
	// Constructors

	/** default constructor */
	public ReportStudent() {
	}

	/** minimal constructor */
	public ReportStudent(String dateCreation, Long state) {
		this.dateCreation = dateCreation;
		this.state = state;
	}

	/**@autor jhernandez */
	public ReportStudent(Long idReportType, Long idStatusReportStudent,
			Long idStudent, Long idStage, Long idSolicitor, Long idAdviser,
			String detailReport, Long idRiskFactor)
	{
		this.idReportType = idReportType;
		this.idStatusReportStudent = idStatusReportStudent;
		this.idStudent = BigDecimal.valueOf(idStudent);
		this.idStage = idStage;
		this.idSolicitor = idSolicitor;
		this.idAdviser = idAdviser;	
		this.detailReport = detailReport;
		this.idRiskFactor = idRiskFactor;
		
	}
	
	
	/**@autor jhernandez */
	public ReportStudent(Long idReportStudent, Long idReportType, Long idStatusReportStudent,
			BigDecimal idStudent, Long idStage, Long idSolicitor, Long idAdviser,
			String detailReport, Long idRiskFactor)
	{
		this.idReportType = idReportType;
		this.idStatusReportStudent = idStatusReportStudent;
		this.idStudent = idStudent;
		this.idStage = idStage;
		this.idSolicitor = idSolicitor;
		this.idAdviser = idAdviser;	
		this.detailReport = detailReport;
		this.idRiskFactor = idRiskFactor;
		this.idReportStudent = idReportStudent;
		
	}	

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "idReportStudent", nullable = false)
	public Long getIdReportStudent() {
		return this.idReportStudent;
	}

	public void setIdReportStudent(Long idReportStudent) {
		this.idReportStudent = idReportStudent;
	}

	@Column(name = "idReportType")
	public Long getIdReportType() {
		return this.idReportType;
	}

	public void setIdReportType(Long idReportType) {
		this.idReportType = idReportType;
	}

	@Column(name = "idStatusReportStudent")
	public Long getIdStatusReportStudent() {
		return this.idStatusReportStudent;
	}

	public void setIdStatusReportStudent(Long idStatusReportStudent) {
		this.idStatusReportStudent = idStatusReportStudent;
	}

	@Column(name = "IDSTUDENT")
	public BigDecimal getIdStudent() {
		return idStudent;
	}

	public void setIdStudent(BigDecimal idStudent) {
		this.idStudent = idStudent;
	}

	@Column(name = "idStage")
	public Long getIdStage() {
		return this.idStage;
	}

	public void setIdStage(Long idStage) {
		this.idStage = idStage;
	}

	@Column(name = "idSolicitor")
	public Long getIdSolicitor() {
		return this.idSolicitor;
	}

	public void setIdSolicitor(Long idSolicitor) {
		this.idSolicitor = idSolicitor;
	}

	@Column(name = "idAdviser")
	public Long getIdAdviser() {
		return this.idAdviser;
	}

	public void setIdAdviser(Long idAdviser) {
		this.idAdviser = idAdviser;
	}

	@Column(name = "detailReport")
	public String getDetailReport() {
		return this.detailReport;
	}

	public void setDetailReport(String detailReport) {
		this.detailReport = detailReport;
	}

	@Column(name = "dateCreation", nullable = false, length = 20)
	public String getDateCreation() {
		return this.dateCreation;
	}

	public void setDateCreation(String dateCreation) {
		if(dateCreation != null){
			Date d = ManageDate.formatDate(dateCreation, ManageDate.YYYY_MM_DD);
			dateCreation = ManageDate.formatDate(d, ManageDate.YYYY_MM_DD);
		}
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
	
	@Column(name = "idRiskFactor")
	public Long getIdRiskFactor() {
		return idRiskFactor;
	}
	
	public void setIdRiskFactor(Long idRiskFactor) {
		this.idRiskFactor = idRiskFactor;
	}
	
		
	@Column(name = "firstIntervention")
	public Long getFirstIntervention() {
		return firstIntervention;
	}

	public void setFirstIntervention(Long firstIntervention) {
		this.firstIntervention = firstIntervention;
	}
		
	@Column(name = "idZyosUserAdviserFaculty")
	public Long getIdZyosUserAdviserFaculty() {
		return idZyosUserAdviserFaculty;
	}

	public void setIdZyosUserAdviserFaculty(Long idZyosUserAdviserFaculty) {
		this.idZyosUserAdviserFaculty = idZyosUserAdviserFaculty;
	}
	
	@Column(name = "idZyosGroup", nullable=true)
	public Long getIdZyosGroup() {
		return idZyosGroup;
	}

	public void setIdZyosGroup(Long idZyosGroup) {
		this.idZyosGroup = idZyosGroup;
	}

	@Transient
	public String getNameResponsible() {
		return nameResponsible;
	}

	public void setNameResponsible(String nameResponsible) {
		this.nameResponsible = nameResponsible;
	}	


	@Transient
	public String getMobilePhoneResponsible() {
		return mobilePhoneResponsible;
	}

	public void setMobilePhoneResponsible(String mobilePhoneResponsible) {
		this.mobilePhoneResponsible = mobilePhoneResponsible;
	}

	@Transient
	public Long getPhoneResponsible() {
		return phoneResponsible;
	}

	public void setPhoneResponsible(Long phoneResponsible) {
		this.phoneResponsible = phoneResponsible;
	}

	@Transient
	public String getZyosUserName() {
		return zyosUserName;
	}

	public void setZyosUserName(String zyosUserName) {
		this.zyosUserName = zyosUserName;
	}

	@Transient
	public String getZyosUserLastName() {
		return zyosUserLastName;
	}

	public void setZyosUserLastName(String zyosUserLastName) {
		this.zyosUserLastName = zyosUserLastName;
	}

	@Transient
	public String getZyosGroupName() {
		return zyosGroupName;
	}

	public void setZyosGroupName(String zyosGroupName) {
		this.zyosGroupName = zyosGroupName;
	}

	@Transient
	public String getRiskFactorName() {
		return riskFactorName;
	}

	public void setRiskFactorName(String riskFactorName) {
		this.riskFactorName = riskFactorName;
	}
	
	@Transient
	public String getNameSolicitor() {
		return nameSolicitor;
	}
	
	public void setNameSolicitor(String nameSolicitor) {
		this.nameSolicitor = nameSolicitor;
	}
	
	
	@Transient
	public Long getIdZyosUser() {
		return idZyosUser;
	}

	public void setIdZyosUser(Long idZyosUser) {
		this.idZyosUser = idZyosUser;
	}
		
	@Transient
	public String getEmailSolicitor() {
		return emailSolicitor;
	}

	public void setEmailSolicitor(String emailSolicitor) {
		this.emailSolicitor = emailSolicitor;
	}

	@Transient
	public Long getIsButton() {
		return isButton;
	}

	public void setIsButton(Long isButton) {
		
		this.isButton = isButton;
	}

	@Transient
	public Long getIsButtonCase() {
		return isButtonCase;
	}

	public void setIsButtonCase(Long isButtonCase) {
		this.isButtonCase = isButtonCase;
	}
	
	
	public void setSolicitorData(String solicitorData) {
		if (solicitorData != null) {
			String[] splited = solicitorData.split(";");
			if(splited.length == 2){
				this.nameSolicitor = splited[0];
				this.zyosGroupName = splited[1];
			}
			 if (splited.length == 3) {
				this.nameResponsible = splited[0];
				this.phoneResponsible = Long.valueOf(splited[1]);
				this.mobilePhoneResponsible = splited[2];
			}
			 if (splited.length == 4) {
					this.nameResponsible = splited[0];
					this.phoneResponsible = Long.valueOf(splited[1]);
					this.mobilePhoneResponsible = splited[2];
					this.emailSolicitor = splited[3];
				}
		}
	} 
	
	//descomentariar cuando sea postgres
	/* 	public void setSolicitorData(String solicitorData) {
		if (solicitorData != null) {
			solicitorData = solicitorData.replace("\"", "").replace("(","").replace(")","").replace("{","").replace("}","").replace("\\", "");
			String[] splited = solicitorData.split(",");
			if(splited.length == 2){
				this.nameSolicitor = splited[0];
				this.zyosGroupName = splited[1];
			}
			 if (splited.length == 3) {
				this.nameResponsible = splited[0];
				this.phoneResponsible = Long.valueOf(splited[1]);
				this.mobilePhoneResponsible = splited[2];
			}
			 if (splited.length == 4) {
					this.nameResponsible = splited[0];
					this.phoneResponsible = Long.valueOf(splited[1]);
					this.mobilePhoneResponsible = splited[2];
					this.emailSolicitor = splited[3];
				}
	}
	}*/
	

	@Transient
	public BigDecimal getCount() {
		return count;
	}

	public void setCount(BigDecimal count) {
		this.count = count;
	}

	@Transient
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Transient
	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	@Transient
	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	@Transient
	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	@Transient
	public String getEmailStudent() {
		return emailStudent;
	}

	public void setEmailStudent(String emailStudent) {
		this.emailStudent = emailStudent;
	}

	@Transient
	public Long getIdFaculty() {
		return idFaculty;
	}

	public void setIdFaculty(Long idFaculty) {
		this.idFaculty = idFaculty;
	}

	@Transient
	public Long getIdRiskFactorCategory() {
		return idRiskFactorCategory;
	}

	public void setIdRiskFactorCategory(Long idRiskFactorCategory) {
		this.idRiskFactorCategory = idRiskFactorCategory;
	}

	@Transient
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Transient
	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	
	
	
	
	
	

	
	
	
	
	
	
	
}