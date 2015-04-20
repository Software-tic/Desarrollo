package com.zyos.alert.sac.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MateriasGrupos entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Materias_Grupo")
public class MateriasGrupo implements java.io.Serializable {

	// Fields

	private String idMateria;
	private Long idGrupo;

	// Constructors

	/** default constructor */
	public MateriasGrupo() {
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
}