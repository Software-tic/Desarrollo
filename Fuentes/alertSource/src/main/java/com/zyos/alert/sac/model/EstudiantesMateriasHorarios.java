package com.zyos.alert.sac.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * EstudiantesMateriasGrupos entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Estudiantes_Materias_Horarios")
public class EstudiantesMateriasHorarios implements java.io.Serializable {

	// Fields
	private String idMateria;
	private Long idDiaSemana;
	private Long idEstudiante;
	private String horaInicio;
	private String horaFin;

	
	/** default constructor */
	public EstudiantesMateriasHorarios() {
	}

	// Property accessors
	@Id
	@Column(name = "ID_MATERIA")
	public String getIdMateria() {
		return this.idMateria;
	}

	public void setIdMateria(String idMateria) {
		this.idMateria = idMateria;
	}

	@Column(name = "ID_ESTUDIANTE")
	public Long getIdEstudiante() {
		return this.idEstudiante;
	}

	public void setIdEstudiante(Long idEstudiante) {
		this.idEstudiante = idEstudiante;
	}

	@Column(name = "ID_DIA_SEMANA")
	public Long getIdDiaSemana() {
		return idDiaSemana;
	}

	public void setIdDiaSemana(Long idDiaSmena) {
		this.idDiaSemana = idDiaSmena;
	}

	@Column(name = "HORA_INICIO")
	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	@Column(name = "HORA_FIN")
	public String getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}

	  
}