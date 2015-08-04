package com.zyos.alert.query.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Teacher entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "teacher")
public class Teacher extends AbstractTeacher implements java.io.Serializable {

	// Constructors

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** default constructor */
	public Teacher() {
	}

	/** minimal constructor */
	public Teacher(Long idteacher) {
		super(idteacher);
	}

	/** full constructor */
	public Teacher(Long idteacher, Long zyosuser, Long school,
			String datecreation, String usercreation, String datechange,
			String userchange, Long state) {
		super(idteacher, zyosuser, school, datecreation, usercreation,
				datechange, userchange, state);
	}

	public Teacher(Long idteacher, Long idZyosUser, Long idSchool) {
		super(idteacher, idZyosUser, idSchool);
		// TODO Auto-generated constructor stub
	}

	public Teacher(Long idteacher, Long zyosuser, Long school,
			String datecreation, String usercreation) {
		super(idteacher, zyosuser, school, datecreation, usercreation);
		// TODO Auto-generated constructor stub
	}

	
}
