package com.zyos.core.common.api;

/**
 * This interface defines the state in the application active and inactive
 * 
 */

public interface IZyosState {

	/**
	 * Id that represent the active state in application
	 */
	public static final Long ACTIVE = Long.valueOf(1);
	
	/**
	 * Id that represent the inactive state in application
	 */
	public static final Long INACTIVE = Long.valueOf(0);
	
	/**
	 * Id that represent the disabled state in application
	 */
	public static final Long DISABLED = Long.valueOf(2);


}
