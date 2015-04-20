package com.zyos.core.common.api;

public interface IValidationType {

	public static Long NUMBER = Long.valueOf(1);
	public static Long EMAIL = Long.valueOf(2);
	public static Long CURRENCY = Long.valueOf(3);
	public static Long PERCENTAGE = Long.valueOf(4);
	public static Long TIME = Long.valueOf(5);
	public static Long DATE = Long.valueOf(6);
	public static Long TEXT = Long.valueOf(7);
	public static Long URL = Long.valueOf(8);
	public static Long IP = Long.valueOf(9);
	public static Long SELECT = Long.valueOf(10);
	public static Long NOT_EMPTY = Long.valueOf(11);
	
}
