package com.zyos.alert.query.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * School entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "school")
public class School extends AbstractSchool implements java.io.Serializable {

	// Constructors

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** default constructor */
	public School() {
	}

	/** minimal constructor */
	public School(Long idschool, String nameSchool) {
		super(idschool, nameSchool);
	}

	/** full constructor */
	public School(Long idschool, String nameSchool, String datecreation,
			String usercreation, String datechange, String userchange,
			Long state) {
		super(idschool, nameSchool, datecreation, usercreation, datechange,
				userchange, state);
	}

}
