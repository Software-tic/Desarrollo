package com.zyos.alert.sac.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * DocenteMateriaGrupos entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Carreras_Docentes_Facultades")
public class CarrerasDocentesFacultades implements java.io.Serializable {

	private Long idDocente;
	private String idCarrera;
	private Long idFacultad;

	// Constructors

	/** default constructor */
	public CarrerasDocentesFacultades() {
	}

	// Property accessors
	@Id
	@Column(name = "ID_DOCENTE")
	public Long getIdDocente() {
		return this.idDocente;
	}

	public void setIdDocente(Long idDocente) {
		this.idDocente = idDocente;
	}

	@Column(name = "ID_CARRERA")
	public String getIdCarrera() {
		return idCarrera;
	}

	public void setIdCarrera(String idCarrera) {
		this.idCarrera = idCarrera;
	}

	@Column(name = "ID_FACULTAD")
	public Long getIdFacultad() {
		return idFacultad;
	}

	public void setIdFacultad(Long idFacultad) {
		this.idFacultad = idFacultad;
	}

}