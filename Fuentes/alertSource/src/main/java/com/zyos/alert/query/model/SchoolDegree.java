package com.zyos.alert.query.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * School_Degree entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "school_degree")
public class SchoolDegree extends AbstractSchoolDegree implements
		java.io.Serializable {

	// Constructors

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** default constructor */
	public SchoolDegree() {
	}

	/** minimal constructor */
	public SchoolDegree(Long idSchooldegree, Long idDegree, Long idSchool) {
		super(idSchooldegree, idDegree, idSchool);
		// TODO Auto-generated constructor stub
	}

	/** full constructor */
	public SchoolDegree(Long idSchooldegree, Long degree, Long school,
			String datecreation, String usercreation, String datechange,
			String userchange, Long state) {
		super(idSchooldegree, degree, school, datecreation, usercreation,
				datechange, userchange, state);
		// TODO Auto-generated constructor stub
	}

}
