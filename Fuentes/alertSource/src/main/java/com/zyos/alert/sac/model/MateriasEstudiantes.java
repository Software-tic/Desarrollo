package com.zyos.alert.sac.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MateriasEstudiantess entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Materias_Estudiantes")
public class MateriasEstudiantes implements java.io.Serializable {

	// Fields
	private String idMateria;
	private Long idEstudiante;

	// Constructors

	/** default constructor */
	public MateriasEstudiantes() {
	}

	// Property accessors
	@Id
	@Column(name = "ID_MATERIA", length = 5)
	public String getIdMateria() {
		return this.idMateria;
	}

	public void setIdMateria(String idMateria) {
		this.idMateria = idMateria;
	}

	@Column(name = "ID_ESTUDIANTE", precision = 11, scale = 0)
	public Long getIdEstudiante() {
		return this.idEstudiante;
	}

	public void setIdEstudiante(Long idEstudiante) {
		this.idEstudiante = idEstudiante;
	}
	
}