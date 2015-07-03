package com.zyos.alert.query.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * FacultySchool entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "facultyschool")
public class FacultySchool extends AbstractFacultySchool implements
		java.io.Serializable {

	// Constructors

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** default constructor */
	public FacultySchool() {
	}

	/** minimal constructor */
	public FacultySchool(Long idfacultyschool) {
		super(idfacultyschool);
	}

	/** full constructor */
	public FacultySchool(Long idfacultyschool, Long school, Long faculty,
			String datecreation, String datechange, String usercreation,
			String userchange, Long state) {
		super(idfacultyschool, school, faculty, datecreation, datechange,
				usercreation, userchange, state);
	}

}
