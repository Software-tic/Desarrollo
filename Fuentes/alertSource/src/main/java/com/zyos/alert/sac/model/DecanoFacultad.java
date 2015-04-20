package com.zyos.alert.sac.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * DocenteMateriaGrupos entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Decano_Facultad")
public class DecanoFacultad implements java.io.Serializable {

	private Long idDecano;
	private Long idFacultad;
	private String nomDecano;
	private String mail;

	// Constructors

	/** default constructor */
	public DecanoFacultad() {
	}

	// Property accessors

	@Id
	@Column(name = "ID_DECANO")
	public Long getIdDecano() {
		return idDecano;
	}

	public void setIdDecano(Long idDecano) {
		this.idDecano = idDecano;
	}

	@Column(name = "ID_FACULTAD")
	public Long getIdFacultad() {
		return idFacultad;
	}

	public void setIdFacultad(Long idFacultad) {
		this.idFacultad = idFacultad;
	}

	@Column(name = "NOM_DECANO")
	public String getNomDecano() {
		return nomDecano;
	}

	public void setNomDecano(String nomDecano) {
		this.nomDecano = nomDecano;
	}

	@Column(name = "MAIL")
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

}