package com.zyos.alert.sac.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "cambio_ident_est")
public class CambioIdentificacionEstudiante implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String numIdentificacion;
	private String fecha;
	private String numIdentificacionAntigua;
	private Long id;

	
	@Column(name = "num_identificacion", nullable = true)
	public String getNumIdentificacion() {
		return numIdentificacion;
	}

	public void setNumIdentificacion(String numIdentificacion) {
		this.numIdentificacion = numIdentificacion;
	}

	@Column(name = "fecha", nullable = false)
	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	@Column(name = "num_ident_antigua", nullable = false)
	public String getNumIdentificacionAntigua() {
		return numIdentificacionAntigua;
	}

	public void setNumIdentificacionAntigua(String numIdentificacionAntigua) {
		this.numIdentificacionAntigua = numIdentificacionAntigua;
	}

	@Id
	@Column(name = "id", nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



}
