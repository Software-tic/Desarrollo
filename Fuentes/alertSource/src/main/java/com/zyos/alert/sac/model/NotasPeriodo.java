package com.zyos.alert.sac.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * NotasPeriodo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "notas_periodo")
public class NotasPeriodo implements java.io.Serializable {

	// Fields

	private NotasPeriodoId id;
	private String perCorte;
	private String sdoCorte;
	private String terCorte;
	private String NFinal;

	// Constructors

	/** default constructor */
	public NotasPeriodo() {
	}

	/** minimal constructor */
	public NotasPeriodo(NotasPeriodoId id) {
		this.id = id;
	}

	/** full constructor */
	public NotasPeriodo(NotasPeriodoId id, String perCorte, String sdoCorte,
			String terCorte, String NFinal) {
		this.id = id;
		this.perCorte = perCorte;
		this.sdoCorte = sdoCorte;
		this.terCorte = terCorte;
		this.NFinal = NFinal;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "idEstudiante", column = @Column(name = "id_estudiante", nullable = false)),
			@AttributeOverride(name = "idMateria", column = @Column(name = "id_materia", nullable = false)),
			@AttributeOverride(name = "periodo", column = @Column(name = "periodo", nullable = false, length = 10)) })
	public NotasPeriodoId getId() {
		return this.id;
	}

	public void setId(NotasPeriodoId id) {
		this.id = id;
	}

	@Column(name = "per_corte", length = 5)
	public String getPerCorte() {
		return this.perCorte;
	}

	public void setPerCorte(String perCorte) {
		this.perCorte = perCorte;
	}

	@Column(name = "sdo_corte", length = 5)
	public String getSdoCorte() {
		return this.sdoCorte;
	}

	public void setSdoCorte(String sdoCorte) {
		this.sdoCorte = sdoCorte;
	}

	@Column(name = "ter_corte", length = 5)
	public String getTerCorte() {
		return this.terCorte;
	}

	public void setTerCorte(String terCorte) {
		this.terCorte = terCorte;
	}

	@Column(name = "n_final", length = 5)
	public String getNFinal() {
		return this.NFinal;
	}

	public void setNFinal(String NFinal) {
		this.NFinal = NFinal;
	}

}