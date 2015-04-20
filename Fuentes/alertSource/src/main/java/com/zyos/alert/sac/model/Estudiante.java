package com.zyos.alert.sac.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Estudiantes entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Estudiante")
public class Estudiante implements java.io.Serializable {

	// Fields

	private Long id;
	private String documento;
	private String nombres;
	private String apellidos;
	private String correoAlternativo;
	private String telefono;
	private String celular;
	private String direccion;

	// Constructors

	/** default constructor */
	public Estudiante() {
	}
	@Id
	@Column(name = "ID", precision = 11, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "DOCUMENTO", length = 12)
	public String getDocumento() {
		return this.documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	@Column(name = "NOMBRES", length = 101)
	public String getNombres() {
		return this.nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	@Column(name = "APELLIDOS", length = 61)
	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	@Column(name = "CORREO_ALTERNATIVO", length = 100)
	public String getCorreoAlternativo() {
		return this.correoAlternativo;
	}

	public void setCorreoAlternativo(String correoAlternativo) {
		this.correoAlternativo = correoAlternativo;
	}

	@Column(name = "TELEFONO", length = 30)
	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@Column(name = "CELULAR", length = 20)
	public String getCelular() {
		return this.celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	@Column(name = "DIRECCION", length = 50)
	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

}