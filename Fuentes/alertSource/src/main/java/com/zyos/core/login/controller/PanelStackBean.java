package com.zyos.core.login.controller;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "panelStack")
@SessionScoped
public class PanelStackBean implements Serializable {

	// default panel name
	private String selectedPanel = "";
	private String selectedTitle = "";
	private Object temporalVariable = "";

	private boolean enableDefault;

	private String nameDefaultPortfolio;
	private String nameDefaultProject;
	private String nameDefaultBuildingWork;

	private Long idDefaultPortfolio;
	private Long idDefaultProject;
	private Long idDefaultBuildingWork;

	public void clear() {
		selectedPanel = "";
		selectedTitle = "";
		temporalVariable = "";
	}

	public String getSelectedPanel() {
		return selectedPanel;
	}

	public void setSelectedPanel(String selectedPanel) {
		this.selectedPanel = selectedPanel;
	}

	public void setSelectedPanelAndTitle(String selectedPanel, String title) {
		setSelectedPanel(selectedPanel);
		setSelectedTitle(title);
	}

	public void setDefaultPortforlioAndProject(Long idDefaultPortfolio,
			Long idDefaultProject, Long idDefaulBuildingWork,
			boolean enableDefault, String namePortfolio, String nameProject,
			String nameBuildingWork) {
		this.idDefaultPortfolio = idDefaultPortfolio;
		this.idDefaultProject = idDefaultProject;
		this.idDefaultBuildingWork = idDefaulBuildingWork;
		this.enableDefault = enableDefault;
		this.nameDefaultPortfolio = namePortfolio;
		this.nameDefaultProject = nameProject;
		this.nameDefaultBuildingWork = nameBuildingWork;
	}

	public String getSelectedTitle() {
		return selectedTitle;
	}

	public void setSelectedTitle(String selectedTitle) {
		if (selectedTitle != null)
			this.selectedTitle = selectedTitle;
	}

	public Object getTemporalVariable() {
		return temporalVariable;
	}

	public void setTemporalVariable(Object temporalVariable) {
		this.temporalVariable = temporalVariable;
	}

	public Long getIdDefaultPortfolio() {
		return idDefaultPortfolio;
	}

	public void setIdDefaultPortfolio(Long idDefaultPortfolio) {
		this.idDefaultPortfolio = idDefaultPortfolio;
	}

	public Long getIdDefaultProject() {
		return idDefaultProject;
	}

	public void setIdDefaultProject(Long idDefaultProject) {
		this.idDefaultProject = idDefaultProject;
	}

	public Long getIdDefaultBuildingWork() {
		return idDefaultBuildingWork;
	}

	public void setIdDefaultBuildingWork(Long idDefaultBuildingWork) {
		this.idDefaultBuildingWork = idDefaultBuildingWork;
	}

	public boolean isEnableDefault() {
		return enableDefault;
	}

	public void setEnableDefault(boolean enableDefault) {
		this.enableDefault = enableDefault;
	}

	public String getNameDefaultPortfolio() {
		return nameDefaultPortfolio;
	}

	public void setNameDefaultPortfolio(String nameDefaultPortfolio) {
		this.nameDefaultPortfolio = nameDefaultPortfolio;
	}

	public String getNameDefaultProject() {
		return nameDefaultProject;
	}

	public void setNameDefaultProject(String nameDefaultProject) {
		this.nameDefaultProject = nameDefaultProject;
	}

	public String getNameDefaultBuildingWork() {
		return nameDefaultBuildingWork;
	}

	public void setNameDefaultBuildingWork(String nameDefaultBuildingWork) {
		this.nameDefaultBuildingWork = nameDefaultBuildingWork;
	}

}
