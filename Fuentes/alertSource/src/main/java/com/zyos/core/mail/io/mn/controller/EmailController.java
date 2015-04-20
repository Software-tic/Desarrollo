package com.zyos.core.mail.io.mn.controller;

import com.zyos.core.common.controller.ErrorNotificacion;
import com.zyos.core.common.controller.ZyosController;
import com.zyos.core.mail.io.mn.model.EmailTemplate;
import com.zyos.core.mail.io.mn.model.EmailTemplateDAO;

public class EmailController extends ZyosController {

	/**
	 * Method for saving a new template
	 * 
	 * @param et
	 * @return
	 */
	public boolean saveTemplate(EmailTemplate et) {
		EmailTemplateDAO dao = new EmailTemplateDAO();
		try {
			dao.save(et);
			dao.getSession().beginTransaction().commit();
			dao.getSession().close();
			return true;
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
			return false;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	/**
	 * Method for editing a old template
	 * 
	 * @param et
	 * @return
	 */
	public boolean editTemplate(EmailTemplate et) {
		EmailTemplateDAO dao = new EmailTemplateDAO();
		try {
			dao.editTemplate(et);
			dao.getSession().beginTransaction().commit();
			return true;
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
			return false;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}
}
