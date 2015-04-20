package com.zyos.core.login.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.menu.MenuModel;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.zyos.core.common.controller.ErrorNotificacion;
import com.zyos.core.common.controller.ZyosBackingBean;
import com.zyos.core.common.controller.ZyosUtil;
import com.zyos.core.lo.user.model.ZyosGroup;
import com.zyos.core.login.model.Tree;
import com.zyos.session.ZyosSession;
import com.zyos.session.common.User;

@ManagedBean(name = "loginBean")
@SessionScoped
@URLMappings(mappings = {@URLMapping(id = "login", pattern = "/portal/login", viewId = "/pages/login.jspx"),
	@URLMapping(id = "start", pattern = "/portal/inicio", viewId = "/pages/alert.jspx"),
	@URLMapping(id = "loginMobile", pattern = "/mobile/login", viewId = "/pages/mobile/login/login.jspx"),
	@URLMapping(id = "inicioMobile", pattern = "/mobile/inicio", viewId = "/pages/mobile/login/content.jspx")})
public class LoginBean extends ZyosBackingBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String passwordMD5 = "";
	private String idUser = "";
	private Long idZyosGroup = null;

	private boolean mobile;
	private boolean initialized = false;
	private boolean isLogin = false;
	private boolean uniqueEnter = false;
	private boolean changePassword = false;
	private boolean showDeadLine = false;
	private boolean showLicence = false;

	private List<User> listUserSession = null;

	private LoginController loginController = null;
	private MenuModel zyosMenu;
	private List<Tree> treeList;

	/**
	 * Default constructor
	 * 
	 * @throws Exception
	 */
	public LoginBean() throws Exception {
		try {
			if (!initialized && getSession(false) != null) {
				System.out.println("INFO: ZM-UL - Login with session " + getSession(false).getId());
				initialized = true;
				setUniqueEnter(false);
				resetFields();
			} else if (getUserSession() != null) {
				redirectToJSP(getUserSession());
			} else {
				redirectToLogin();
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "UserLoginBean");
			redirectToLogin();
		}
	}

	/**
	 * Method for validate the user login. Enable the user for user the system
	 * 
	 * @return null
	 */
	public String validateUser(boolean isMobile) {
		try {
			mobile = isMobile;
			if (idZyosGroup != null) {
				int index = getUserSession().getListGroup().indexOf(idZyosGroup);
				getUserSession().getListGroup().set(index, getUserSession().getListGroup().get(0));
				getUserSession().getListGroup().set(0, idZyosGroup);
				zyosMenu = null;
				redirectToJSP(getUserSession());

			} else if (!showLicence && !isUniqueEnter() && getUserSession() == null) {
				if (getUserSession() == null) {
					String idS = getSession(false).getId();
					loginController = new LoginController();
					setUserSession(loginController.validateUser(idUser.trim(), passwordMD5.trim(), idS, idZyosGroup));
					if (getUserSession() != null) {
						addUserCookies();
						zyosMenu = null;
						if (getUserSession().getIdSession() == null && idS != null)
							changePassword = true;
						if (getUserSession().getFirstLogin().equals(1)) {
							this.showLicence = true;
							getRequestContext().addCallbackParam("showLicense", true);
							return "login";
						}
						this.showLicence = false;
						setUniqueEnter(true);
						getUserSession().setIdSession(idS);
						resetFields();
						listUserSession = ZyosSession.getLoginUser(getUserSession().getId().toString());
						if (validateAnotherPopups()) {
							getRequestContext().update("loginPopup");
							return null;
						}
						if (listUserSession == null) {
							ZyosSession.addLoginUser(getUserSession());
							setUniqueLogin(false);
						}
					} else {
						passwordMD5 = "";
						addFailLoginMessage();
						getRequestContext().execute("statusDialog.hide()");
						clearData(false);
						update("loginForm:loginPanel");
						return null;
					}
					idZyosGroup = getUserSession().getDefaultGroup();
					getSession(false).setAttribute("user", getUserSession());
				}
				redirectToJSP(getUserSession());
			} else if (getUserSession() != null && !uniqueEnter && !showLicence) {
				redirectToJSP(getUserSession());
			} else if (showLicence) {
				getRequestContext().addCallbackParam("showLicense", true);
				getRequestContext().update("loginPopup");
			} else if (isUniqueLogin()) {
				getRequestContext().addCallbackParam("uniqueLogin", true);
				getRequestContext().update("loginPopup");
			} else {
				getRequestContext().update("loginForm:loginTable");
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "UserLoginBean/validateUser");
			addFailLoginMessage();
		}
		return null;
	}

	private void addUserCookies() {
		try {
			ZyosUtil.addCookie("uID", getUserSession().getId().toString(), 30, "user", true);
			ZyosUtil.addCookie("locate", "es_co", 30, "user", true);
			ZyosUtil.addCookie("presence", ForgotPasswordBean.getPassword(20), 1, "user", true);
			ZyosUtil.addCookie("css", "layout", 30, "user", true);
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	/**
	 * Method for add a fail login message
	 */
	private void addFailLoginMessage() {
		addWarn("Fallo de validación", "La información de nombre de usuario o contraseña introducida no es correcta.");
	}

	private boolean validateAnotherPopups() {
		if (listUserSession != null) {
			// already another logins with this count
			setUniqueLogin(true);
			getRequestContext().addCallbackParam("uniqueLogin", true);
			getRequestContext().update("loginForm:userListTable");
			return true;
		}
		return false;
	}

	public synchronized void continueLogin() {
		try {
			if (this.isUniqueLogin()) {
				managePopup();
				listUserSession = null;
				if (validateAnotherPopups())
					return;
				getUserSession().setIdSession(getSession(false).getId());
				ZyosSession.addLoginUser(getUserSession());
				getSession(false).setAttribute("user", getUserSession());
				redirectToJSP(getUserSession());
			}
		} catch (Exception e) {
			System.out.println("failed continue login");
			ErrorNotificacion.validateHibernateCloseSession();
		}
	}

	public void continueLoginAceptLicense() {
		try {
			if (showLicence && this.updateAceptLicense(getUserSession().getId(), getUserSession().getDocumentNumber())) {
				showLicence = false;
				if (validateAnotherPopups())
					return;
				getUserSession().setIdSession(getSession(false).getId());
				ZyosSession.addLoginUser(getUserSession());
				getSession(false).setAttribute("user", getUserSession());
				redirectToJSP(getUserSession());
			}
		} catch (Exception e) {
			System.out.println("failed continue login");
			ErrorNotificacion.validateHibernateCloseSession();
		}
	}

	public void cancelLoginAceptLicense() {
		clearData(true);
	}

	public synchronized String continueDeadLine() {
		return null;
	}

	public int getSizeListUserSession() {
		if (getListUserSession() == null)
			return 0;
		return getListUserSession().size();
	}

	public String managePopup() {
		this.setUniqueLogin(!this.isUniqueLogin());
		return null;
	}

	public String managePopupDeadLine() {
		clearData(true);
		return null;
	}

	public String managePopupCancelSession() {
		this.setCancelSession(false);
		clearData(true);
		return null;
	}

	private User selectedUserSession = null;

	public synchronized void anulateUserSession() {
		try {
			if (selectedUserSession != null) {
				ZyosSession.delLoginSessionId(selectedUserSession.getIdSession(), null);
				listUserSession.remove(selectedUserSession);
			}
		} catch (Exception e) {
			System.out.println(e + "failed anulate session");
			ErrorNotificacion.validateHibernateCloseSession();
		} finally {
			getRequestContext().update("formLoginPopup:sessionDetail");
		}
	}

	/**
	 * method that redirects the request to main page
	 * 
	 * @param user
	 * @return
	 */
	private void redirectToJSP(User user) {
		try {
			if (user != null) {
				if (zyosMenu == null) {
					// TODO is necessary create a zyos tree for every enterprise
					// for now all uses 0
					loadMenu(user);
					treeList = ZyosMenuController.getZyosGroupTree(user.getDefaultGroup(), Long.valueOf(1));
				}
				getPanelStackBean().setDefaultPortforlioAndProject(null, null, null, false, null, null, null);
				this.setUserSession(user);
				this.resetFields();
				validateRedirectTo(user);
			}
		} catch (Exception e) {
			ErrorNotificacion.validateHibernateCloseSession();
			ErrorNotificacion.handleErrorMailNotification(e, getUserSession() == null ? "system" : getUserSession().getFullName());
		} finally {
			clearData(false);
		}
	}

	private void validateRedirectTo(User user) {
		if (changePassword) {
			changePassword = false;
			redirectURL("portal/password");
		}
		if (mobile)
			redirectURL("mobile/inicio");
		else {
			redirectURL("portal/inicio");
		}
	}

	public void redirectToFamilyStudentReport() {

		//redirectURL("portal/reporteFamiliar?faces-redirect=true");

	}

	public void loadMenu(User user) throws Exception {
		zyosMenu = ZyosMenuController.getTreeByZyosGroup(user.getDefaultGroup(), Long.valueOf(1), 0);
	}

	private void clearData(boolean fullClear) {
		passwordMD5 = null;
		idUser = null;
		if (fullClear) {
			this.listUserSession = null;
			this.setUserSession(null);
			this.zyosMenu = null;
			this.showLicence = false;
			this.uniqueEnter = false;
		}
	}

	private void resetFields() {
		passwordMD5 = "";
		// idUser = "";
	}

	// get and set methods

	public String getIdUser() {
		if (getUserSession() != null) {
			User user = ZyosSession.getUserBySession(getUserSession().getIdSession());
			if (user != null && !isUniqueLogin()) {
				validateRedirectTo(user);
			}
		} else {
			this.uniqueEnter = false;
		}
		return idUser;
	}

	@Override
	public String sessionClose() throws Exception {
		idZyosGroup = null;
		return super.sessionClose();
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public List<ZyosGroup> getGroupList() {
		List<ZyosGroup> groups = new ArrayList<ZyosGroup>();
		for (Long id : getUserSession().getGroupList()) {
			for (ZyosGroup zg : getZyosGroupList()) {
				if (zg.getId().equals(id)) {
					groups.add(zg);
					break;
				}
			}
		}
		return groups;
	}

	public Long getIdZyosGroup() {
		return idZyosGroup;
	}

	public void setIdZyosGroup(Long idZyosGroup) {
		this.idZyosGroup = idZyosGroup;
	}

	public String getPasswordMD5() {
		return passwordMD5;
	}

	public void setPasswordMD5(String passwordMD5) {
		this.passwordMD5 = passwordMD5;
	}

	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	public boolean isUniqueEnter() {
		return uniqueEnter;
	}

	public void setUniqueEnter(boolean uniqueEnter) {
		this.uniqueEnter = uniqueEnter;
	}

	public List<User> getListUserSession() {
		return listUserSession;
	}

	public void setListUserSession(List<User> listUserSession) {
		this.listUserSession = listUserSession;
	}

	public boolean isShowDeadLine() {
		return showDeadLine;
	}

	public void setShowDeadLine(boolean showDeadLine) {
		this.showDeadLine = showDeadLine;
	}

	public MenuModel getZyosMenu() {
		return zyosMenu;
	}

	public void setZyosMenu(MenuModel zyosMenu) {
		this.zyosMenu = zyosMenu;
	}

	public User getSelectedUserSession() {
		return selectedUserSession;
	}

	public void setSelectedUserSession(User selectedUserSession) {
		this.selectedUserSession = selectedUserSession;
	}

	public List<Tree> getTreeList() {
		return treeList;
	}

	public void setTreeList(List<Tree> treeList) {
		this.treeList = treeList;
	}

	public boolean isMobile() {
		return mobile;
	}

	public void setMobile(boolean mobile) {
		this.mobile = mobile;
	}

}
