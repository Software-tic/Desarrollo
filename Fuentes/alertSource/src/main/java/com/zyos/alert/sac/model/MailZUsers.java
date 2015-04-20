package com.zyos.alert.sac.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Degree entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "MAIL_Z_USERS")
public class MailZUsers implements java.io.Serializable {

	// Fields
	
	private Long id;
	private Long documento;
	private String priNombre;
	private String segNombre;
	private String priApellido;
	private String segApellido;
	private String mail;
	private Long idSede;
	private String codUnidad;
	private String nomUnidad;
	private String tipoUsuario;
	private Long estado;

	// Constructors

	/** default constructor */
	public MailZUsers() {
	}

	// Property accessors
	@Id
	@Column(name = "id", nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "documento")
	public Long getDocumento() {
		return documento;
	}

	public void setDocumento(Long documento) {
		this.documento = documento;
	}

	@Column(name = "priNombre")
	public String getPriNombre() {
		return priNombre;
	}

	public void setPriNombre(String priNombre) {
		this.priNombre = priNombre;
	}

	@Column(name = "segNombre")
	public String getSegNombre() {
		return segNombre;
	}

	public void setSegNombre(String segNombre) {
		this.segNombre = segNombre;
	}

	@Column(name = "priApellido")
	public String getPriApellido() {
		return priApellido;
	}

	public void setPriApellido(String priApellido) {
		this.priApellido = priApellido;
	}

	@Column(name = "segApellido")
	public String getSegApellido() {
		return segApellido;
	}

	public void setSegApellido(String segApellido) {
		this.segApellido = segApellido;
	}

	@Column(name = "mail")
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	@Column(name = "id_Sede")
	public Long getIdSede() {
		return idSede;
	}

	public void setIdSede(Long idSede) {
		this.idSede = idSede;
	}

	@Column(name = "cod_Unidad")
	public String getCodUnidad() {
		return codUnidad;
	}

	public void setCodUnidad(String codUnidad) {
		this.codUnidad = codUnidad;
	}

	@Column(name = "nom_Unidad")
	public String getNomUnidad() {
		return nomUnidad;
	}

	public void setNomUnidad(String nomUnidad) {
		this.nomUnidad = nomUnidad;
	}

	@Column(name = "tipo_Usuario")
	public String getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	@Column(name = "estado")
	public Long getEstado() {
		return estado;
	}

	public void setEstado(Long estado) {
		this.estado = estado;
	}

}