package com.zyos.alert.controlPanel.model;

	import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

	/**
	 * Controlpanel entity. @author MyEclipse Persistence Tools
	 */
	@Entity
	@Table(name = "controlpanel")
	public class ControlPanel extends com.zyos.core.common.model.AZyosModel
			implements java.io.Serializable {

		// Fields

		private Long idControlPanel;
		private Double percentageRiskFactor;
		private Double percentageAssistance;
		private int marginHour;


		// Constructors

		/** default constructor */
		public ControlPanel() {
		}

		/** full constructor */
		public ControlPanel(Long idControlPanel, Double percentageRiskFactor, Double percentageAssistance,  int marginHour,
				String dateCreation, String userCreation, String dateChange,
				String userChange, Long state) {
			this.idControlPanel = idControlPanel;
			this.percentageRiskFactor = percentageRiskFactor;
			this.percentageAssistance = percentageAssistance;
			this.marginHour = marginHour;
			this.dateCreation = dateCreation;
			this.userCreation = userCreation;
			this.dateChange = dateChange;
			this.userChange = userChange;
			this.state = state;
		}
		
		
		public ControlPanel(Long idControlPanel, int marginHour, Double percentageRiskFactor, Double percentageAssistance ) {
			this.idControlPanel = idControlPanel;
			this.percentageRiskFactor = percentageRiskFactor;
			this.percentageAssistance = percentageAssistance;
			this.marginHour = marginHour;
	
		}
		
	
		// Property accessors
		@GenericGenerator(name = "generator", strategy = "increment")
		@Id
		@GeneratedValue(generator = "generator")
		@Column(name = "idControlPanel", nullable = false)
		public Long getIdControlPanel() {
			return this.idControlPanel;
		}

		public void setIdControlPanel(Long idControlPanel) {
			this.idControlPanel = idControlPanel;
		}

		@Column(name = "percentageRiskFactor", precision = 17, scale = 17)
		public Double getPercentageRiskFactor() {
			return this.percentageRiskFactor;
		}

		public void setPercentageRiskFactor(Double percentageRiskFactor) {
			this.percentageRiskFactor = percentageRiskFactor;
		}
		
		@Column(name = "percentageAssistance", precision = 17, scale = 17)
		public Double getPercentageAssistance() {
			return this.percentageAssistance;
		}

		public void setPercentageAssistance(Double percentageAssistance) {
			this.percentageAssistance = percentageAssistance;
		}

		@Column(name = "marginhour")
		public int getMarginHour() {
			return this.marginHour;
		}

		public void setMarginHour(int marginHour) {
			this.marginHour = marginHour;
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
