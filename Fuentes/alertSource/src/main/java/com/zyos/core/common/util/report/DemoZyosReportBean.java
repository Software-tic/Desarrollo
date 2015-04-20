package com.zyos.core.common.util.report;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import net.sf.jasperreports.engine.JRException;

import com.zyos.core.common.controller.ZyosBackingBean;
import com.zyos.core.common.util.ManageDate;
import com.zyos.core.lo.user.controller.UserController;
import com.zyos.core.lo.user.model.ZyosUser;

@ManagedBean(name = "demoReportBean")
@ViewScoped
public class DemoZyosReportBean extends ZyosBackingBean {

	private List<ZyosUser> userList = new ArrayList<ZyosUser>();

	private ZyosReport zyosReport;
	private UserController controller = new UserController();

	public DemoZyosReportBean() throws Exception {
		userList = controller.loadUserListByEnterprise(getUserSession()
				.getDefaultEnterprise(), getUserSession().getDefaultGroup());
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("reportName", "My example report");
		params.put("reportDate",
				"" + ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD_HH_MM_SS));
		params.put("reportEntepriseName", "My enterprise [Foster]");
		zyosReport = new ZyosReport("/report/demo/demo.jasper", params,
				userList);
	}

	public void PDF() throws JRException, IOException {
		zyosReport.PDF("reporte");
	}

	public void ODT() throws JRException, IOException {
		zyosReport.ODT("reporte");
	}

	public void XLSX() throws JRException, IOException {
		zyosReport.XLSX("reporte");
	}

	public void DOCX() throws JRException, IOException {
		zyosReport.DOCX("reporte");
	}

	public void PPT() throws JRException, IOException {
		zyosReport.PPTX("reporte");
	}

	public List<ZyosUser> getUserList() {
		return userList;
	}

	public void setUserList(List<ZyosUser> userList) {
		this.userList = userList;
	}

}
