package com.zyos.alert.sac.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * NotasPeriodoId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class NotasPeriodoId implements java.io.Serializable {

	// Fields

	private Long idEstudiante;
	private Long idMateria;
	private String periodo;

	// Constructors

	/** default constructor */
	public NotasPeriodoId() {
	}

	/** full constructor */
	public NotasPeriodoId(Long idEstudiante, Long idMateria, String periodo) {
		this.idEstudiante = idEstudiante;
		this.idMateria = idMateria;
		this.periodo = periodo;
	}

	// Property accessors

	@Column(name = "id_estudiante", nullable = false)
	public Long getIdEstudiante() {
		return this.idEstudiante;
	}

	public void setIdEstudiante(Long idEstudiante) {
		this.idEstudiante = idEstudiante;
	}

	@Column(name = "id_materia", nullable = false)
	public Long getIdMateria() {
		return this.idMateria;
	}

	public void setIdMateria(Long idMateria) {
		this.idMateria = idMateria;
	}

	@Column(name = "periodo", nullable = false, length = 10)
	public String getPeriodo() {
		return this.periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof NotasPeriodoId))
			return false;
		NotasPeriodoId castOther = (NotasPeriodoId) other;

		return ((this.getIdEstudiante() == castOther.getIdEstudiante()) || (this
				.getIdEstudiante() != null
				&& castOther.getIdEstudiante() != null && this
				.getIdEstudiante().equals(castOther.getIdEstudiante())))
				&& ((this.getIdMateria() == castOther.getIdMateria()) || (this
						.getIdMateria() != null
						&& castOther.getIdMateria() != null && this
						.getIdMateria().equals(castOther.getIdMateria())))
				&& ((this.getPeriodo() == castOther.getPeriodo()) || (this
						.getPeriodo() != null && castOther.getPeriodo() != null && this
						.getPeriodo().equals(castOther.getPeriodo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getIdEstudiante() == null ? 0 : this.getIdEstudiante()
						.hashCode());
		result = 37 * result
				+ (getIdMateria() == null ? 0 : this.getIdMateria().hashCode());
		result = 37 * result
				+ (getPeriodo() == null ? 0 : this.getPeriodo().hashCode());
		return result;
	}

}