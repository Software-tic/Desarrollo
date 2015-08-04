package com.zyos.alert.query.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Corte entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "corte")
public class Corte extends AbstractCorte implements java.io.Serializable {

	// Constructors

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** default constructor */
	public Corte() {
	}

	/** minimal constructor */
	public Corte(Long idcorte, Long academicperiod, String name,
			String dateStart, String dateEnd) {
		super(idcorte, academicperiod, name, dateStart, dateEnd);
	}

	/** full constructor */
	public Corte(Long idcorte, Long academicperiod, String name,
			String dateStart, String dateEnd, Long state, String datecreation,
			String usercreation, String datechange, String userchange) {
		super(idcorte, academicperiod, name, dateStart, dateEnd, state,
				datecreation, usercreation, datechange, userchange);
	}

}
