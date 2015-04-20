package com.zyos.core.common.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import com.zyos.alert.executionsHistorical.model.ExecutionsHistorical;
import com.zyos.alert.executionsHistorical.model.ExecutionsHistoricalDAO;
import com.zyos.alert.studentReport.model.AcademicPeriodDAO;
import com.zyos.core.common.list.BeanList;
import com.zyos.core.common.util.security.RSA;
import com.zyos.core.lo.user.model.ZyosGroup;
import com.zyos.core.lo.user.model.ZyosUser;
import com.zyos.core.lo.user.model.ZyosUserDAO;
import com.zyos.core.login.model.ZyosLoginDAO;
import com.zyos.core.mail.io.mn.api.IEmailTemplate;
import com.zyos.core.mail.io.mn.aws.SMTPEmail;
import com.zyos.core.mail.io.mn.controller.EmailHandler;
import com.zyos.core.mail.io.mn.model.EmailTemplate;

public class ZyosController implements Serializable {

	public List<ZyosGroup> getGroupList(Long idEnterprise, Long idZG, boolean withEqualByRole) {
		try {
			List<ZyosGroup> groups = BeanList.getZyosGroupList();
			if (idZG == 1)
				return groups;
			for (int i = 0; i < groups.size(); i++) {
				ZyosGroup group = groups.get(i);
				if (withEqualByRole) {
					if (group.getId() <= idZG) {
						groups.remove(i);
						i--;
					}
				} else {
					if (group.getId() < idZG) {
						groups.remove(i);
						i--;
					}
				}
			}
			return groups;
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
			return null;
		}
	}

	public static String clearCharacters(String _character) {
		String cleanCaracter = _character;
		cleanCaracter = cleanCaracter.replaceAll("'", "");
		cleanCaracter = cleanCaracter.replaceAll("--", "");
		cleanCaracter = cleanCaracter.trim();
		return cleanCaracter;
	}

	public ZyosUser queryUser(ZyosUser zyosUser) {
		ZyosUserDAO zyosUserDAO = new ZyosUserDAO();
		try {
			ZyosUser zut = zyosUserDAO.findById(zyosUser.getIdZyosUser());

			if (zut != null && zyosUser != null) {
				zyosUser.setSecondEmail(zut.getSecondEmail());
				zyosUser.setPhone(zut.getPhone());
				zyosUser.setMobilePhone(zut.getMobilePhone());
				zyosUser.setIdDocumentType(zut.getIdDocumentType());
				zyosUser.setDateCreation(zut.getDateCreation());
				zyosUser.setUserCreation(zut.getUserCreation());
				zyosUser.setZyosLogin(zut.getZyosLogin());
				zyosUser.setDocumentType(zut.getIdDocumentType().toString());
				zyosUser.setIdProfession(zut.getIdProfession());
			}

			zut = null;
			return zyosUser;
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
			return null;
		} finally {
			zyosUserDAO.getSession().close();
			zyosUserDAO = null;
		}
	}

	/**
	 * Method that find and load the validate user for the SP selected
	 * 
	 * @param idSP Long id of SP
	 * @param idOZ
	 * @return list with the user found
	 */
	public ArrayList<SelectItem> loadListUserBySP(Long idSP, Long idOZ, Long idZG) {
		ZyosUserDAO userDAO = null;
		try {
			userDAO = new ZyosUserDAO();
			ArrayList<SelectItem> usersList = userDAO.findBySP(idSP, idOZ, idZG);
			if (!usersList.isEmpty()) {
				return usersList;
			}
			return null;
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
			return null;
		} finally {
			userDAO.getSession().close();
			userDAO = null;
		}
	}

	public boolean validateForgotPassword(final String userMail, String userName, final String pass) {
		ZyosUserDAO dao = null;
		try {
			dao = new ZyosUserDAO();
			String passwordMD5 = RSA.encrypt(pass);
			String nameUser = dao.validateForgotPassword(userMail, userName, passwordMD5);
			if (nameUser != null) {
				dao.getSession().close();
				try {
					EmailTemplate et = EmailHandler.getEmailTemplate(Long.valueOf(1), IEmailTemplate.RESET_PASSWORD);

					SMTPEmail smtp = new SMTPEmail();
					smtp.sendProcessEmail(ZyosBackingBean.getProperty("mail.smtp.user"), et.getSubject(),
						EmailHandler.createGenericMessage(et.getBody(), et.getAnalyticsCode(), nameUser, userMail, pass), userMail);

				} catch (Exception e) {
					ErrorNotificacion.handleErrorMailNotification(e, "system");
				}
				return true;
			}

			return false;
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "ERROR-validate password");
			return false;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	public boolean updateAceptLicense(Long id, String documentNumber) {
		ZyosLoginDAO userDAO = null;
		try {
			userDAO = new ZyosLoginDAO();
			if (userDAO.updateAceptLicense(id, documentNumber)) {
				userDAO.getSession().close();
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		} finally {
			userDAO.getSession().close();
			userDAO = null;
		}
	}

	public List<SelectItem> queryUserByEnterprise(Long idSP, Long idOZ, Long idZG) {
		ZyosUserDAO zyosUserDAO = new ZyosUserDAO();
		try {
			return zyosUserDAO.findBySP(idSP, idOZ, idZG);
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
			return null;
		} finally {
			zyosUserDAO.getSession().close();
			zyosUserDAO = null;
		}
	}

	/** @author ogarzonm */
	public Long loadCurrentAcademicPeriod() throws Exception {
		AcademicPeriodDAO dao = new AcademicPeriodDAO();
		try {
			return dao.loadCurrentAcademicPeriod();
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	/** @author ogarzonm */
	public String loadCurrentAcademicPeriodName() throws Exception {
		AcademicPeriodDAO dao = new AcademicPeriodDAO();
		try {
			return dao.loadCurrentAcademicPeriodName();
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	/** @author ogarzonm */
	public String loadCurrentAcademicPeriodDescription() throws Exception {
		AcademicPeriodDAO dao = new AcademicPeriodDAO();
		try {
			return dao.loadCurrentAcademicPeriodDescription();
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	/** @author ogarzonm */
	public void saveExecution(ExecutionsHistorical eh) throws Exception {
		ExecutionsHistoricalDAO dao = new ExecutionsHistoricalDAO();
		try {
			dao.save(eh);
			dao.getSession().beginTransaction().commit();
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}


}
