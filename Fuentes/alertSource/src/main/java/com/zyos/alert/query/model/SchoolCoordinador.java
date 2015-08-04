package com.zyos.alert.query.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * SchoolCoordinador entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "school_coordinador")
public class SchoolCoordinador extends AbstractSchoolCoordinador implements
		java.io.Serializable {

	// Constructors

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** default constructor */
	public SchoolCoordinador() {
	}

	/** minimal constructor */
	public SchoolCoordinador(Long idschoolcoord) {
		super(idschoolcoord);
	}

	/** full constructor */
	public SchoolCoordinador(Long idschoolcoord, Long zyosuser,
			Long school, String datecreation, String usercreation,
			String datechange, String userchange, Long state) {
		super(idschoolcoord, zyosuser, school, datecreation, usercreation,
				datechange, userchange, state);
	}
	public SchoolCoordinador(Long idSchoolCoord, Long idZyosuser, Long idSchool) {
		super(idSchoolCoord,  idZyosuser, idSchool);
	}
}
