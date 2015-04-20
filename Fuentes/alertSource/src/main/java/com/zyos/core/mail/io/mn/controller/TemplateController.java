package com.zyos.core.mail.io.mn.controller;

import java.util.List;

import com.zyos.core.common.api.IZyosState;
import com.zyos.core.common.controller.ZyosController;
import com.zyos.core.mail.io.mn.model.EmailTemplate;
import com.zyos.core.mail.io.mn.model.EmailTemplateDAO;

public class TemplateController extends ZyosController {

	public List<EmailTemplate> loadEmailTemplateList() {
		EmailTemplateDAO dao = new EmailTemplateDAO();
		try {
			return dao.loadEmailTemplateList();
		} catch (RuntimeException e) {
			dao.getSession().beginTransaction().rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	public void saveEmailTemplate(EmailTemplate selectedEmailTemplate)
			throws Exception {
		EmailTemplateDAO dao = new EmailTemplateDAO();
		try {
			dao.save(selectedEmailTemplate);
			dao.getSession().beginTransaction().commit();
		} catch (Exception e) {
			dao.getSession().beginTransaction().rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	public void editEmailTemplate(EmailTemplate selectedEmailTemplate)
			throws Exception {
		EmailTemplateDAO dao = new EmailTemplateDAO();
		try {
			dao.getSession().saveOrUpdate(selectedEmailTemplate);
			dao.getSession().beginTransaction().commit();
		} catch (Exception e) {
			dao.getSession().beginTransaction().rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	public void deleteEmailTemplate(EmailTemplate[] selectedEmailTemplateList,
			String documentNumber) throws Exception {
		EmailTemplateDAO dao = new EmailTemplateDAO();
		try {
			StringBuilder uil = new StringBuilder();
			for (EmailTemplate nl : selectedEmailTemplateList) {
				uil.append(nl.getId());
				uil.append(",");
			}
			dao.changeStateZyosModel("EmailTemplate", uil.toString() + "0",
					documentNumber, IZyosState.INACTIVE);

			dao.getSession().beginTransaction().commit();
		} catch (Exception e) {
			dao.getSession().beginTransaction().rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

}
