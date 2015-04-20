package com.zyos.core.common.api;

/**
 * This interface defines the user types (role) of the application.
 */

public interface IZyosGroup {

	public static final Long ADMINISTRATOR = Long.valueOf(1);
	
	public static final Long STUDENT = Long.valueOf(5);
	
	public static final Long TEACHER = Long.valueOf(2);
	
	public static final Long CLASS_MATE = Long.valueOf(3);
	
	public static final Long UDIES = Long.valueOf(6);
	
	public static final Long ADVISER = Long.valueOf(7);
	
	public static final Long BIENESTAR = Long.valueOf(8);
	
	public static final Long PSYCHOLOGIST = Long.valueOf(9);
	
	public static final Long MONITOR = Long.valueOf(10);
	
	public static final Long TUTOR = Long.valueOf(11);
	
	public static final Long PASTORAL = Long.valueOf(12);
	
	public static final Long DIVISION_DECAN = Long.valueOf(13);
	
	public static final Long DECAN_FACULTY = Long.valueOf(14);
	
	public static final Long PROGRAM_COORDINATOR = Long.valueOf(15);
	
	public static final Long SECRETARY_DIVISION = Long.valueOf(16);
	
}
