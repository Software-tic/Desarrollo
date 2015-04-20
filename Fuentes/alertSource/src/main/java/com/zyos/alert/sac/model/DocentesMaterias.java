package com.zyos.alert.sac.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * DocentesMateriass entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Docentes_Materias")
public class DocentesMaterias implements java.io.Serializable {

	// Fields

	private Long idDocente;
	private String idMateria;

	// Constructors

	/** default constructor */
	public DocentesMaterias() {
	}
	
	@Id
	@Column(name = "ID_DOCENTE", precision = 11, scale = 0)
	public Long getIdDocente() {
		return this.idDocente;
	}

	public void setIdDocente(Long idDocente) {
		this.idDocente = idDocente;
	}

	@Column(name = "ID_MATERIA", length = 5)
	public String getIdMateria() {
		return this.idMateria;
	}

	public void setIdMateria(String idMateria) {
		this.idMateria = idMateria;
	}

}