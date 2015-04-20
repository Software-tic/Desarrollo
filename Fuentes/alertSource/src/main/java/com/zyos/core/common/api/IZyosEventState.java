package com.zyos.core.common.api;

/**
 * This interface defines the state in the application active and inactive
 * 
 */

public interface IZyosEventState {

	/**
	 * Id that represent the active state in application
	 */
	public static final Long PAYED = Long.valueOf(2);
	/**
	 * Id that represent the inactive state in application
	 */
	public static final Long RESERVED = Long.valueOf(1);


}
