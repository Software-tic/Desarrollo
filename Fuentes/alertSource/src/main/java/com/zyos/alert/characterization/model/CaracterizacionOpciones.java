package com.zyos.alert.characterization.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Degree entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Caracterizacion_Opciones")
public class CaracterizacionOpciones implements java.io.Serializable {

	// Fields
	private String id;
	private String idEstudiante;
	private String codPregunta;
	private String codOpcion;
	private String respuesta;

	// Constructors

	/** default constructor */
	public CaracterizacionOpciones() {
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

	@Column(name = "COD_OPCION")
	public String getCodOpcion() {
		return codOpcion;
	}

	public void setCodOpcion(String codOpcion) {
		this.codOpcion = codOpcion;
	}

	@Column(name = "RESPUESTA")
	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

}