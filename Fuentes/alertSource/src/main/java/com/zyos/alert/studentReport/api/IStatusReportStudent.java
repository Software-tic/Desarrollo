package com.zyos.alert.studentReport.api;

public interface IStatusReportStudent {
	public static final Long APPROVED = Long.valueOf(1);
	public static final Long DEPRECATED = Long.valueOf(2);
	public static final Long CLOSED_SUCCESSFULL = Long.valueOf(3);
	public static final Long START_INTERVENTION = Long.valueOf(4);
	public static final Long PROGRESS_INTERVENTION = Long.valueOf(5);
	public static final Long CLOSED_FAULT = Long.valueOf(6);
	public static final Long REGISTER = Long.valueOf(7);
	public static final Long REPORT = Long.valueOf(8);
}
