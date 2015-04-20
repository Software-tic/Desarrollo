package com.zyos.alert.sac.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Materiass entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FACULTADES")
public class Facultades implements java.io.Serializable {

	// Fields

	private Long identificador;
	private String facultad;

	// Constructors

	/** default constructor */
	public Facultades() {
	}

	// Property accessors
	@Id
	@Column(name = "IDENTIFICADOR")
	public Long getIdentificador() {
		return identificador;
	}

	public void setIdentificador(Long identificador) {
		this.identificador = identificador;
	}

	@Column(name = "FACULTAD")
	public String getFacultad() {
		return facultad;
	}

	public void setFacultad(String facultad) {
		this.facultad = facultad;
	}

}