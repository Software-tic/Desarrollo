package com.zyos.alert.sac.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * DocenteMateriaGrupos entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Docente_Materia_Grupo")
public class DocenteMateriaGrupo implements java.io.Serializable {

	private Long idDocente;
	private String idMateria;
	private Long idGrupo;

	// Constructors

	/** default constructor */
	public DocenteMateriaGrupo() {
	}

	// Property accessors
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

	@Column(name = "ID_GRUPO", precision = 11, scale = 0)
	public Long getIdGrupo() {
		return this.idGrupo;
	}

	public void setIdGrupo(Long idGrupo) {
		this.idGrupo = idGrupo;
	}

}