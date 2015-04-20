package com.zyos.alert.sac.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Degree entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Carreras_Estudiantes")
public class CarrerasEstudiantes implements java.io.Serializable {

	// Fields

	private Long idEstudiante;
	private String idCarrera;
	private String codAlumno;

	// Constructors

	/** default constructor */
	public CarrerasEstudiantes() {
	}

	// Property accessors
	@Id
	@Column(name = "id_estudiante", nullable = false)
	public Long getIdEstudiante() {
		return idEstudiante;
	}

	public void setIdEstudiante(Long id_estudiante) {
		this.idEstudiante = id_estudiante;
	}

	@Column(name = "id_carrera")
	public String getIdCarrera() {
		return idCarrera;
	}

	public void setIdCarrera(String id_carrera) {
		this.idCarrera = id_carrera;
	}

	@Column(name = "cod_alumno")
	public String getCodAlumno() {
		return codAlumno;
	}

	public void setCodAlumno(String codAlumno) {
		this.codAlumno = codAlumno;
	}

}