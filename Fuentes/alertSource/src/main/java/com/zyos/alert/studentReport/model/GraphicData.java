package com.zyos.alert.studentReport.model;

public class GraphicData {
	
	private double studentReports;
	private double academicReports;
	private double socioeconomicReports;
	private double institutionalReports;
	private double personalReports;
	
	public GraphicData(double studentReports, double academicReports, double socioeconomicReports, double institutionalReports, double personalReports) {
		super();
		this.studentReports = studentReports;
		this.academicReports = academicReports;
		this.socioeconomicReports = socioeconomicReports;
		this.institutionalReports = institutionalReports;
		this.personalReports = personalReports;
	}
	
	public GraphicData(){
			}
	
	
	
	public double getStudentReports() {
		return studentReports;
	}
	public void setStudentReports(double studentReports) {
		this.studentReports = studentReports;
	}
	public double getAcademicReports() {
		return academicReports;
	}
	public void setAcademicReports(double academicReports) {
		this.academicReports = academicReports;
	}
	public double getSocioeconomicReports() {
		return socioeconomicReports;
	}
	public void setSocioeconomicReports(double socioeconomicReports) {
		this.socioeconomicReports = socioeconomicReports;
	}
	public double getInstitutionalReports() {
		return institutionalReports;
	}
	public void setInstitutionalReports(double institutionalReports) {
		this.institutionalReports = institutionalReports;
	}
	public double getPersonalReports() {
		return personalReports;
	}
	public void setPersonalReports(double personalReports) {
		this.personalReports = personalReports;
	}
	
	
	
	

}
