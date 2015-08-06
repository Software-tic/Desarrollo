package com.zyos.core.common.controller;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import com.zyos.alert.studentReport.model.Relationship;
import com.zyos.alert.studentReport.model.RiskFactor;
import com.zyos.alert.studentReport.model.RiskFactorCategory;
import com.zyos.alert.studentReport.model.Stage;
import com.zyos.alert.studentReport.model.StatusReportStudent;
import com.zyos.alert.studentReport.model.Subject;
import com.zyos.core.common.list.BeanList;
import com.zyos.core.common.model.AZyosModel;
import com.zyos.core.common.model.DocumentType;
import com.zyos.core.common.model.ZyosParameter;
import com.zyos.core.common.util.ManageDate;
import com.zyos.core.lo.enterprise.model.Enterprise;
import com.zyos.core.lo.user.model.Funcionality;
import com.zyos.core.lo.user.model.Profession;
import com.zyos.core.lo.user.model.ZyosGroup;
import com.zyos.core.login.controller.LoginBean;
import com.zyos.core.login.controller.PanelStackBean;
import com.zyos.core.login.model.ZyosGroupTree;
import com.zyos.core.mail.io.mn.model.EmailTemplate;
import com.zyos.core.mail.io.mn.model.EmailTemplateType;
import com.zyos.session.ZyosSession;
import com.zyos.session.common.User;

/**
 * @author Garzón
 * 
 */
public abstract class ZyosBackingBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private int rowCountVar = 10;
	private boolean validateLogin = false;
	private boolean invalidUser = false;
	private boolean uniqueLogin = false;
	private boolean cancelSession = false;
	private boolean initializedGeneral = false;
	private Long idEnterprise;
	private String currentTime = "";

	private User userSession = null;
	private PanelStackBean panelStackBean;

	public ZyosBackingBean() throws Exception {
		try {
			if (!initializedGeneral) {
				initializedGeneral = true;
				getResponse().setHeader("P3P", "CP='IDC DSP COR ADM DEVi TAIi PSA PSD IVAi IVDi CONi HIS OUR IND CNT'");
			}
			getValidateExpiredSession();
			if (getUserSession() != null) {
				idEnterprise = getUserSession().getDefaultEnterprise();
				validateLogin = true;
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "ZyosBackingBean");
			redirectToLogin();
		}
	}

	public boolean getValidateUserRole(Long idFunctionality, Long idTree) throws Exception {
		try {
			if (getUserSession() != null && idFunctionality != null) {
				if (idFunctionality == 0)
					return false;
				String className = this.getClass().getName().substring(this.getClass().getName().lastIndexOf(".") + 1);
				if (className == null)
					return true;
				for (ZyosGroupTree zgt : getZyosGroupFuntionalityList()) {
					if (zgt.getClassName() != null && className.equalsIgnoreCase(zgt.getClassName())
						&& zgt.getIdZyosGroup().equals(getUserSession().getDefaultGroup()) && zgt.getFunctionalityList() != null
						&& zgt.getIdTree().equals(idTree)) {
						String[] fl = zgt.getFunctionalityList().split(",");
						for (String idF : fl) {
							if (idF.equals(idFunctionality.toString()))
								return false;
						}
					}
				}
			}


		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
		return true;
	}

	public boolean getValidateExpiredSession() {
		try {
			String idSession = ZyosUtil.getCookieByName("JSESSIONID");

			if (getSession(false) != null && idSession != null && idSession != "") {
				String currentSession = getSession(false).getId();
				if (currentSession.startsWith(idSession)) {
					User userA = (User) getSession(false).getAttribute("user");
					User userZ = ZyosSession.getUserBySession(currentSession);
					if (userA == null && userZ == null) {
						redirectToLogin();
						return false;
					}
					if (userZ != null) {
						setUserSession(userA != null && userA.getId().equals(userZ.getId()) ? userZ : null);
						return true;
					} else if (userA != null) {
						if (getUserSession() != null)
							this.cancelSession = true;
						this.setUserSession(null);
						this.setUniqueLogin(false);
						redirectToLogin();
					} else {
						redirectToLogin();
					}
					ZyosSession.delLoginSessionId(idSession, null);
					return false;
				}
				redirectToLogin();
			} else {
				if (getUserSession() != null)
					this.cancelSession = true;
				this.setUserSession(null);
				this.setUniqueLogin(false);
				redirectToLogin();
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
			redirectToLogin();
		}
		return false;
	}

	/**
	 * Method for reloading the general lists
	 */
	public void reloadControllerList() {
		try {
			System.out.println("RELOADING list");
			BeanList.reloadControllerList();
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public static void redirectToLogin() {
		try {
			String requestURL =
				(String) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get(RequestDispatcher.FORWARD_REQUEST_URI);
			if (!requestURL.toString().endsWith("login")) {
				System.out.println("INFO: saving redirect " + requestURL);
				redirectURL("portal/login");
				getSession(false).setAttribute("requestPath", requestURL);
			}
		} catch (Exception e) {
		}
	}

	public void redirectErrorPage() throws Exception {
		String path = BeanList.properties.getProperty("errorPath");
		getSession(false);
		redirectURL(path);
	}

	public String getZyosUserSession() throws Exception {
		User user = (User) getSession(false).getAttribute("user");
		if (user != null) {
			return user.getDocumentNumber();
		}
		return null;
	}

	public static void redirectURL(String path) {
		String URL = null;
		try {
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			Object requestPath = getSession(false).getAttribute("requestPath");

			if (requestPath != null && !requestPath.toString().endsWith("login") && !path.endsWith("login")) {
				getSession(false).setAttribute("requestPath", null);
				context.redirect(requestPath.toString());
				return;
			}

			String scheme = getRequest().getScheme();
			String serverName = getRequest().getServerName();
			String contextPath = getRequest().getContextPath();
			int serverPort = getRequest().getServerPort();

			URL = scheme + "://" + serverName + ":" + serverPort + contextPath;
			context.redirect(URL + "/" + path);
		} catch (Exception e) {
			System.out.println("INFO: Redirect URL Path fail " + path);
		}
	}

	public static RequestContext getRequestContext() {
		return RequestContext.getCurrentInstance();
	}

	public static FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	public static ServletContext getServletContext() {
		return (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
	}

	public static HttpServletResponse getResponse() {
		return (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	}

	public static HttpSession getSession(boolean isNew) {
		if (FacesContext.getCurrentInstance().getExternalContext() != null) {
			return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(isNew);
		}
		return null;
	}

	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	/**
	 * Method for getting the current file path as the current SO
	 * 
	 * @return C:/ if is win2 or / is if a linux SO
	 */
	public static String getPathBySO() {
		return System.getProperty("os.name").toLowerCase().startsWith("win") ? "C:/" : "/";
	}

	/**
	 * methods for add a message in the faces context
	 * 
	 * @param title
	 * @param description
	 */
	public static void addError(String title, String description) {
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, title, description));
	}

	public static void addWarn(String title, String description) {
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, title, description));
	}

	public static void addInfo(String title, String description) {
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, title, description));
	}

	/**
	 * Method for updating multiples components with only one a method
	 * 
	 * @param clientID
	 */
	public static void update(String... clientID) {
		try {
			for (String id : clientID) {
				getRequestContext().update(id);
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "ZyosBackingBean - update");
		}
	}

	/**
	 * Method for executing a javascript on the server
	 * 
	 * @param javascript
	 */
	public void execute(String... javascript) {
		try {
			for (String js : javascript) {
				getRequestContext().execute(js);
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "ZyosBackingBean - execute");
		}
	}

	/**
	 * Method for getting a message about the key
	 * 
	 * @param key
	 * @return
	 */
	public static String getMessage(String key) {
		try {
			return BeanList.properties.getMessage(key);
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "system -backingBean");
			return "";
		}
	}

	/**
	 * Method for getting a property about the key
	 * 
	 * @param key
	 * @return
	 */
	public static String getProperty(String key) {
		try {
			return BeanList.properties.getProperty(key);
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "system -backingBean");
			return "";
		}
	}

	/**
	 * Method for getting the label or name for a list. The object must be have a AZyosMode or SelectItem
	 * 
	 * @author ogarzonm 19/11/2014 12:12:11
	 * @param list
	 * @param idValue
	 * @return
	 */
	public String getNameLabelList(List<?> list, Long idValue) {
		try {
			if (idValue != null && list != null && !list.isEmpty()) {
				for (Object si : list) {
					Method method;
					Class<?> enclosingClass = si.getClass();
					if (enclosingClass != null) {
						method = enclosingClass.getDeclaredMethod(si instanceof AZyosModel ? "getId" : "getValue");
						Object v = method.invoke(si);
						if (v != null && v.toString().equals(idValue.toString())) {
							method = enclosingClass.getDeclaredMethod(si instanceof AZyosModel ? "getName" : "getLabel");
							v = method.invoke(si);
							return v != null ? v.toString() : "N/A";
						}
					}
				}
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, getUserSession() == null ? "system" : getUserSession().getFullName());
		}
		return null;
	}

	public String getToday() {
		return ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD);
	}

	public ZyosController zyosController = new ZyosController();

	public static ZyosBackingBean getBean(String beanName) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		try {
			ZyosBackingBean c =
				(ZyosBackingBean) facesContext.getApplication().getExpressionFactory()
					.createValueExpression(facesContext.getELContext(), "#{" + beanName + "}", ZyosBackingBean.class)
					.getValue(facesContext.getELContext());
			return c;
		} catch (Exception e) {
			return null;
		} finally {
			facesContext = null;
		}
	}

	/**
	 * Method for searching the deleted items in a general list and return the sub list that
	 * contains the deleted elements
	 * 
	 * @param itemsOriginal
	 * @param list
	 * @return
	 */
	public List<String> filterDelItemToList(List<SelectItem> itemsOriginal, List<SelectItem> list) {
		try {
			List<String> spList = new ArrayList<String>();
			for (Object si : list)
				for (SelectItem io : itemsOriginal)
					if (si.equals(io.getValue().toString())) {
						spList.add(io.getValue().toString());
						break;
					}
			return spList.isEmpty() ? null : spList;
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
			return null;
		}
	}

	public String sessionClose() throws Exception {
		try {
			getSession(false).setMaxInactiveInterval(20 * 60);
			if (getUserSession() != null) {
				ZyosSession.delLoginSessionId(getUserSession().getIdSession(), null);
				System.out.println("INFO: ZM-session close for user with id " + getUserSession().getId());
			}
		} catch (Exception e) {
			System.out.println("ERROR: Fail close session try again");
		} finally {
			removeAllAttributesOfSession();
			initializedGeneral = false;
			this.validateLogin = false;
			this.uniqueLogin = false;
			setUserSession(null);
		}

		redirectURL("portal/login");

		return null;
	}

	@SuppressWarnings("unchecked")
	private void removeAllAttributesOfSession() {
		try {
			HttpSession session = getSession(false);
			ErrorNotificacion.validateHibernateCloseSession();
			session.removeAttribute("user");
			ZyosUtil.addCookie("presence", null, 0, "user", true);
			session = null;
			getPanelStackBean().clear();
			validateSession();
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, getUserSession() == null ? "system" : getUserSession().getFullName());
			System.out.println("ERROR: Fail remove all attributes ");
		}
	}

	protected boolean updateAceptLicense(Long id, String documentNumber) {
		try {
			return zyosController.updateAceptLicense(id, documentNumber);
		} catch (Exception e) {
			return false;
		}
	}

	public String getCurrentTime() {
		currentTime = com.zyos.core.common.util.ManageDate.getCurrentDate(com.zyos.core.common.util.ManageDate.YYYY_MM_DD) + " 00:00";
		return currentTime;
	}

	public PanelStackBean getPanelStackBean() {
		if (panelStackBean == null) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			panelStackBean =
				(PanelStackBean) facesContext.getApplication().getExpressionFactory()
					.createValueExpression(facesContext.getELContext(), "#{panelStack}", PanelStackBean.class).getValue(facesContext.getELContext());
		}
		return panelStackBean;
	}

	public void setPanelStackBean(PanelStackBean panelStackBean) {
		this.panelStackBean = panelStackBean;
	}

	/**
	 * Method for changing the current selected panel stack
	 * 
	 * @param beanName
	 * @param page folder/page.xhtml or /folder/page.xhtml
	 * @param title String
	 */
	public void setPanelView(String page, String title, String beanName) {
		try {
			String prefix = null;
			if (page.startsWith("/"))
				prefix = "/WEB-INF/includes";
			else
				prefix = "/WEB-INF/includes/";
			getPanelStackBean().setSelectedPanelAndTitle(prefix + page, title);
			if (getRequestContext() != null)
				getRequestContext().update("panelStack");
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	/**
	 * Method that validate the user session
	 * 
	 * @return
	 */
	public boolean validateSession() {
		try {
			if (getUserSession() != null && ZyosSession.getUserBySession(getUserSession().getIdSession()) != null) {
				return true;
			}
			this.uniqueLogin = true;
			this.setUserSession(null);
			initializedGeneral = false;
			getLoginBean(false);
			return false;
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
			return false;
		}
	}

	public void getLoginBean(boolean cancelSession) {
		LoginBean uslbb = (LoginBean) getBean("loginBean");
		if (uslbb != null) {
			if (!cancelSession) {
				uslbb.setUserSession(null);
				uslbb.setCancelSession(false);
				uslbb.setUniqueLogin(false);
			} else {
				uslbb.setCancelSession(cancelSession);
			}
		}
	}

	/**
	 * Method that load a parameter
	 */
	public String getParameter(String name, boolean deleteParameter) {
		try {
			if (getSession(false) != null) {
				Object value = getSession(false).getAttribute(name);
				if (deleteParameter)
					getSession(false).removeAttribute(name);
				return value != null ? value.toString() : null;
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
		return null;
	}

	public String getValueByRequest(String idCookie) {
		try {
			String tempValue = getRequest().getParameter(idCookie);
			if (tempValue != null && !tempValue.isEmpty()) {
				return tempValue;
			}
			return tempValue == null || tempValue.isEmpty() ? ZyosUtil.getCookieByName(idCookie) : "";
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, getUserSession() == null ? "system" : getUserSession().getFullName());
			return null;
		}
	}

	// default atributtes

	public void setCurrentTime(String c) {
		currentTime = c;
	}

	public boolean getValidateUserRole(Long idFunctionality) {
		try {
			if (getUserSession() != null && idFunctionality != null) {
				if (idFunctionality == 0)
					return false;
				String className = this.getClass().getName().substring(this.getClass().getName().lastIndexOf(".") + 1);
				if (className == null)
					return true;
				for (ZyosGroupTree zgt : getZyosGroupFuntionalityList()) {
					if (zgt.getClassName() != null && className.equalsIgnoreCase(zgt.getClassName())
						&& zgt.getIdZyosGroup().equals(getUserSession().getDefaultGroup()) && zgt.getFunctionalityList() != null) {
						String[] fl = zgt.getFunctionalityList().split(",");
						for (String idF : fl) {
							if (idF.equals(idFunctionality.toString()))
								return false;
						}
					}
				}
			}

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
		return true;
	}

	public boolean isValidateLogin() {
		return validateLogin;
	}

	public void setValidateLogin(boolean validateLogin) {
		this.validateLogin = validateLogin;
	}

	public boolean isInitialized() {
		return initializedGeneral;
	}

	public void setInitialized(boolean initialized) {
		this.initializedGeneral = initialized;
	}

	public User getUserSession() {
		return userSession;
	}

	public void setUserSession(User userSession) {
		this.userSession = userSession;
	}

	public boolean isInvalidUser() {
		return invalidUser;
	}

	public void setInvalidUser(boolean invalidUser) {
		this.invalidUser = invalidUser;
	}

	public boolean isUniqueLogin() {
		return uniqueLogin;
	}

	public void setUniqueLogin(boolean uniqueLogin) {
		this.uniqueLogin = uniqueLogin;
	}

	public TimeZone getTimeZone() {
		return java.util.TimeZone.getDefault();
	}

	public int getRowCountVar() {
		return rowCountVar;
	}

	public void setRowCountVar(int rowCountVar) {
		this.rowCountVar = rowCountVar;
	}

	public boolean isCancelSession() {
		return cancelSession;
	}

	public void setCancelSession(boolean cancelSession) {
		this.cancelSession = cancelSession;
	}

	// genery list method

	public List<Profession> getProfessionList() {
		if (getUserSession() != null && getUserSession().getDefaultEnterprise() != null)
			return BeanList.getControllerEnterpriseList(idEnterprise).getProfessionList();
		return null;
	}

	public List<ZyosGroup> getGroupList(boolean withEqualByRole) {
		return zyosController.getGroupList(idEnterprise, getUserSession().getListGroup().get(0), withEqualByRole);
	}

	public List<EmailTemplateType> getEmailTemplateTypeList() {
		if (getUserSession() != null && getUserSession().getDefaultEnterprise() != null)
			return BeanList.getControllerEnterpriseList(idEnterprise).getEmailTemplateTypeList();
		return null;
	}

	public List<EmailTemplate> getEmailTemplateList() {
		if (getUserSession() != null && getUserSession().getDefaultEnterprise() != null)
			return BeanList.getControllerEnterpriseList(idEnterprise).getEmailTemplateList();
		return null;
	}

	public List<ZyosGroupTree> getZyosGroupFuntionalityList() {
		if (getUserSession() != null && getUserSession().getDefaultEnterprise() != null)
			return BeanList.getControllerEnterpriseList(idEnterprise).getZyosGroupFuntionalityList();
		return null;
	}

	public List<ZyosGroupTree> getZyosDoctorViewIndicatorList() {
		if (getUserSession() != null && getUserSession().getDefaultEnterprise() != null)
			return BeanList.getControllerEnterpriseList(idEnterprise).getZyosGroupFuntionalityList();
		return null;
	}

	public List<DocumentType> getDocumentTypeList() {
		return BeanList.getDocumentTypeList();
	}

	public List<Enterprise> getEnterpriseList() {
		return BeanList.getEnterpriseList();
	}

	public List<Funcionality> getFuncionalityList() {
		return BeanList.getFuncionalityList();
	}

	public List<ZyosParameter> getZyosParameterList() {
		return BeanList.getZyosParameterList();
	}

	public List<ZyosGroup> getZyosGroupList() {
		return BeanList.getZyosGroupList();
	}

	public List<Subject> getSubjectList() {
		return BeanList.getSubjectList();
	}

	public static List<RiskFactor> getRiskFactorList() {
		List<RiskFactor> result = BeanList.getRiskFactorList();
		return result;
	}

	public static List<RiskFactorCategory> getRiskFactorListCategory() {
		List<RiskFactorCategory> result = BeanList.getRiskFactorListCategory();

		return result;

	}

	public List<Relationship> getRelationshipTypeList() {
		return BeanList.getRelationshipTypeList();
	}

	public List<Stage> getStageList() {
		return BeanList.getStageList();
	}

	public List<StatusReportStudent> getStatusReportStudentList() {
		return BeanList.getStatusReportStudentList();
	}


	public List<Stage> getStagePermissionList() {
		return BeanList.getStagePermissionList();
	}

}
