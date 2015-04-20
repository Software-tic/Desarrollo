package com.zyos.core.login.controller;

import com.zyos.core.common.controller.ZyosController;
import com.zyos.core.login.model.ZyosLoginDAO;
import com.zyos.session.common.User;

public class LoginController extends ZyosController {

	public synchronized User validateUser(String _idUser, String _password,
			String idS, Long idZyosGroup) throws Exception {
		ZyosLoginDAO dao = null;
		try {
			dao = new ZyosLoginDAO();
			return dao.validateUser(_idUser, _password, idS, idZyosGroup);
		} catch (Exception e) {
			dao.getSession().beginTransaction().rollback();
			System.out.println("INFO: UserController - validateUser " + e);
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}
}
