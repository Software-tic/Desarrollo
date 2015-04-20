package com.zyos.core.common.list;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.zyos.core.lo.enterprise.model.Enterprise;
import com.zyos.core.lo.enterprise.model.Settings;
import com.zyos.core.lo.user.model.Profession;
import com.zyos.core.lo.user.model.ZyosUser;
import com.zyos.core.login.model.ZyosGroupTree;
import com.zyos.core.login.model.ZyosMenu;
import com.zyos.core.mail.io.mn.model.EmailTemplate;
import com.zyos.core.mail.io.mn.model.EmailTemplateType;

public class ControllerEnterpriseList implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long idEnterprise = null;
	private Enterprise enterprise;
	// lists for zyos pring
	/**
	 * List that contains the all social areas
	 */

	private List<Profession> professionList = null;
	private List<ZyosUser> zyosUserList = null;
	private List<ZyosGroupTree> zyosGroupTreeList = null;
	private List<ZyosGroupTree> zyosGroupFuntionalityList = null;
	private List<ZyosMenu> zyosMenuList = null;
	private List<EmailTemplateType> emailTemplateTypeList;
	private List<EmailTemplate> emailTemplateList;

	private Properties emailConfiguration = null;
	private Settings settings;

	private ControllerList controller = new ControllerList();

	public void clean() {
		zyosMenuList = null;
		zyosUserList = null;
		professionList = null;
		zyosGroupTreeList = null;
		emailTemplateTypeList = null;
		emailTemplateList = null;
		zyosGroupFuntionalityList = null;
	}

	public ControllerEnterpriseList(Enterprise e) {
		this.enterprise = e;
		this.idEnterprise = e.getId();
	}

	public Long getIdEnterprise() {
		return idEnterprise;
	}

	public void setIdEnterprise(Long idEnterprise) {
		this.idEnterprise = idEnterprise;
	}

	public List<ZyosUser> getZyosUserList() {
		if (zyosUserList == null)
			zyosUserList = new ArrayList<ZyosUser>();
		return zyosUserList;
	}

	public Properties getEmailConfiguration() {
		return emailConfiguration;
	}

	public void setEmailConfiguration(Properties emailConfiguration) {
		this.emailConfiguration = emailConfiguration;
	}

	public long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<Profession> getProfessionList() {
		if (professionList == null)
			professionList = new ArrayList<Profession>();
		return professionList;
	}

	public void setProfessionList(List<Profession> professionList) {
		this.professionList = professionList;
	}

	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
	}

	public List<ZyosGroupTree> getZyosGroupTreeList() {
		if (zyosGroupTreeList == null)
			zyosGroupTreeList = new ArrayList<ZyosGroupTree>();
		return zyosGroupTreeList;
	}

	public void setZyosGroupTreeList(List<ZyosGroupTree> zyosGroupTreeList) {
		this.zyosGroupTreeList = zyosGroupTreeList;
	}

	public List<ZyosMenu> getZyosMenuList() {
		if (zyosMenuList == null)
			zyosMenuList = new ArrayList<ZyosMenu>();
		return zyosMenuList;
	}

	public void setZyosMenuList(List<ZyosMenu> zyosMenuList) {
		this.zyosMenuList = zyosMenuList;
	}

	public List<EmailTemplate> getEmailTemplateList() {
		if (emailTemplateList == null)
			emailTemplateList = new ArrayList<EmailTemplate>();
		return emailTemplateList;
	}

	public void setEmailTemplateList(List<EmailTemplate> emailTemplateList) {
		this.emailTemplateList = emailTemplateList;
	}

	public List<EmailTemplateType> getEmailTemplateTypeList() {
		if (emailTemplateTypeList == null)
			emailTemplateTypeList = new ArrayList<EmailTemplateType>();
		return emailTemplateTypeList;
	}

	public void setEmailTemplateTypeList(
			List<EmailTemplateType> emailTemplateTypeList) {
		this.emailTemplateTypeList = emailTemplateTypeList;
	}

	public void setZyosUserList(List<ZyosUser> zyosUserList) {
		if (zyosUserList == null)
			zyosUserList = new ArrayList<ZyosUser>();
		this.zyosUserList = zyosUserList;
	}

	public List<ZyosGroupTree> getZyosGroupFuntionalityList() {
		if (zyosGroupFuntionalityList == null)
			zyosGroupFuntionalityList = new ArrayList<ZyosGroupTree>();
		return zyosGroupFuntionalityList;
	}

	public void setZyosGroupFuntionalityList(
			List<ZyosGroupTree> zyosGroupFuntionalityList) {
		this.zyosGroupFuntionalityList = zyosGroupFuntionalityList;
	}

}
