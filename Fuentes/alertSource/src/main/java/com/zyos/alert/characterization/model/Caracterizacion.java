package com.zyos.alert.characterization.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Degree entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Caracterizacion")
public class Caracterizacion implements java.io.Serializable {

	// Fields
	private String id;
	private String idEstudiante;
	private String codPregunta;
	private String respuesta;
	private String fecha;

	// Constructors

	/** default constructor */
	public Caracterizacion() {
	}

	// Property accessors
	@Id
	@Column(name = "ID", nullable = false)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "ID_ESTUDIANTE")
	public String getIdEstudiante() {
		return idEstudiante;
	}

	public void setIdEstudiante(String idEstudiante) {
		this.idEstudiante = idEstudiante;
	}

	@Column(name = "COD_PREGUNTA")
	public String getCodPregunta() {
		return codPregunta;
	}

	public void setCodPregunta(String codPregunta) {
		this.codPregunta = codPregunta;
	}

	@Column(name = "RESPUESTA")
	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	@Column(name = "FECHA")
	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	

}