package com.zyos.core.common.api;

/**
 * This interface defines the state in the application active and inactive
 * 
 */

public interface IZyosComplaintState {

	/**
	 * Id that represent the recorded complaint state in application
	 */
	public static final Long RECORDED = Long.valueOf(1);
	/**
	 * Id that represent the pending complaint state in application
	 */
	public static final Long PENDING = Long.valueOf(2);
	/**
	 * Id that represent the closed complaint state in application
	 */
	public static final Long CLOSED = Long.valueOf(3);

}
