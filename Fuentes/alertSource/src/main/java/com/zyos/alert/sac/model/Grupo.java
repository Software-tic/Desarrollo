package com.zyos.alert.sac.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Grupos entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Grupo")
public class Grupo implements java.io.Serializable {

	// Fields

	private Long idGrupo;
	private String nombreGrupo;
	private Long aula;

	// Constructors

	/** default constructor */
	public Grupo() {
	}

	// Property accessors
	@Id
	@Column(name = "ID_GRUPO", precision = 11, scale = 0)
	public Long getIdGrupo() {
		return this.idGrupo;
	}

	public void setIdGrupo(Long idGrupo) {
		this.idGrupo = idGrupo;
	}

	@Column(name = "NOMBRE_GRUPO", length = 5)
	public String getNombreGrupo() {
		return this.nombreGrupo;
	}

	public void setNombreGrupo(String nombreGrupo) {
		this.nombreGrupo = nombreGrupo;
	}

	@Column(name = "AULA", precision = 11, scale = 0)
	public Long getAula() {
		return this.aula;
	}

	public void setAula(Long aula) {
		this.aula = aula;
	}

}