package com.zyos.alert.sac.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Degree entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Carreras")
public class Carreras implements java.io.Serializable {

	// Fields

	private String id;
	private String nombre;
	private Long facultad;

	// Constructors

	/** default constructor */
	public Carreras() {
	}

	// Property accessors
	@Id
	@Column(name = "id", nullable = false)
	public String getId() {
		return this.id;
	}

	public void setId(String idDayCalendar) {
		this.id = idDayCalendar;
	}

	@Column(name = "nombre")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "facultad")
	public Long getFacultad() {
		return facultad;
	}

	public void setFacultad(Long facultad) {
		this.facultad = facultad;
	}

}