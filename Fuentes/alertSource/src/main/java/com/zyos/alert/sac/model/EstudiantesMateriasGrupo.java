package com.zyos.alert.sac.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * EstudiantesMateriasGrupos entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Estudiantes_Materias_Grupo")
public class EstudiantesMateriasGrupo implements java.io.Serializable {

	// Fields
	private String idMateria;
	private Long idGrupo;
	private Long idEstudiante;

	// Constructors

	/** default constructor */
	public EstudiantesMateriasGrupo() {
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

	@Column(name = "ID_GRUPO", precision = 11, scale = 0)
	public Long getIdGrupo() {
		return this.idGrupo;
	}

	public void setIdGrupo(Long idGrupo) {
		this.idGrupo = idGrupo;
	}

	@Column(name = "ID_ESTUDIANTE", precision = 11, scale = 0)
	public Long getIdEstudiante() {
		return this.idEstudiante;
	}

	public void setIdEstudiante(Long idEstudiante) {
		this.idEstudiante = idEstudiante;
	}

}