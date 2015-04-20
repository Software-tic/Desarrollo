package com.zyos.alert.sac.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Materiass entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Materias")
public class Materias implements java.io.Serializable {

	// Fields

	private String id;
	private String nombre;
	private Integer semestre;
	private String fechaInicio;
	private String fechaFin;
	private Long intHoraria;
	// Constructors

	/** default constructor */
	public Materias() {
	}

	// Property accessors
	@Id
	@Column(name = "ID", length = 5)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "NOMBRE")
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "SEMESTRE")
	public Integer getSemestre() {
		return this.semestre;
	}

	public void setSemestre(Integer semestre) {
		this.semestre = semestre;
	}

	@Column(name = "FECHA_INICIO", length = 10)
	public String getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	@Column(name = "FECHA_FIN", length = 10)
	public String getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	@Column(name = "INT_HORARIA")
	public Long getIntHoraria() {
		return intHoraria;
	}

	public void setIntHoraria(Long intHoraria) {
		this.intHoraria = intHoraria;
	}

	
}