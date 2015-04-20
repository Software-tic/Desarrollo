package com.zyos.alert.sac.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Horarioss entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Horarios")
public class Horarios implements java.io.Serializable {

	// Fields

	private String materia;
	private String diaDeClase;
	private Long grupo;

	// Constructors

	/** default constructor */
	public Horarios() {
	}

	// Property accessors
	@Id
	@Column(name = "MATERIA", length = 5)
	public String getMateria() {
		return this.materia;
	}

	public void setMateria(String materia) {
		this.materia = materia;
	}

	@Column(name = "DIA_DE_CLASE", length = 9)
	public String getDiaDeClase() {
		return this.diaDeClase;
	}

	public void setDiaDeClase(String diaDeClase) {
		this.diaDeClase = diaDeClase;
	}

	@Column(name = "GRUPO", precision = 11, scale = 0)
	public Long getGrupo() {
		return this.grupo;
	}

	public void setGrupo(Long grupo) {
		this.grupo = grupo;
	}

}