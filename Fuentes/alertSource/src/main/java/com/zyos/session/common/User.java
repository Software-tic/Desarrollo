package com.zyos.session.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.zyos.core.common.controller.ErrorNotificacion;
import com.zyos.core.lo.enterprise.model.Enterprise;

/**
 * Class to use for load the user info
 * 
 * @author Oscar Garzon
 * 
 */
@SuppressWarnings("serial")
public class User implements Serializable {

	/**
	 * user id
	 */
	private Long id;
	/**
	 * User document number
	 */
	private String documentNumber;
	/**
	 * User name
	 */
	private String nameUser;
	/**
	 * user last name
	 */
	private String lastName;
	/**
	 * User login date
	 */
	private String dateLogin;
	/**
	 * user hour login
	 */
	private String hourLogin;
	/**
	 * String that contains the role name
	 */
	private String groupName;
	/**
	 * List with all groups that belongs an user
	 */
	private List<Long> groupList = new ArrayList<Long>();
	/**
	 * List with all enterprise that belong an user.
	 */
	private List<Enterprise> enterpriseList = new ArrayList<Enterprise>();
	private Long defaultEnterprise;
	private String nameEnterprise;

	/**
	 * List with all sp that belongs an user
	 */
	private List<Long> listSP = new ArrayList<Long>();;
	/**
	 * Boolean that indicate if it's necessary show the list for select the
	 * enterprise. only when the user is associated to multiples enterprises.
	 */
	private boolean selectEnterprise = false;
	/**
	 * String with id session assigned for the server application
	 */
	private String idSession;
	/**
	 * Boolean that indicate if the first login of the user
	 */
	private Integer firstLogin = 0;
	/**
	 * String that contains the email of the user.
	 */
	private String email;

	/**
	 * Default Constructor long, string, string, string, boolean, string,
	 * string, string, long
	 * 
	 */
	public User(Long id, String documentNumber, String nameUser,
			String lastName, Integer firstLogin, String email, Long idGroup,
			String groupName, String passwordMD5, Long defaultEnterprise,
			String password, String nameEnterprise) {
		try {
			this.id = id;
			this.documentNumber = documentNumber;
			this.nameUser = nameUser;
			this.lastName = lastName;
			this.groupName = groupName;
			this.firstLogin = firstLogin;
			this.email = email;
			this.defaultEnterprise = defaultEnterprise;
			this.nameEnterprise = nameEnterprise;
			this.enterpriseList.add(new Enterprise(defaultEnterprise,
					nameEnterprise));
			this.groupList = new ArrayList<Long>();
			this.groupList.add(idGroup);
			this.idSession = password != null && !password.isEmpty() ? ""
					: null;
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "ZyosSession-User");
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDateLogin() {
		return dateLogin;
	}

	public void setDateLogin(String dateLogin) {
		this.dateLogin = dateLogin;
	}

	public String getHourLogin() {
		return hourLogin;
	}

	public void setHourLogin(String hourLogin) {
		this.hourLogin = hourLogin;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public String getNameUser() {
		return nameUser;
	}

	public void setNameUser(String nameUser) {
		this.nameUser = nameUser;
	}

	public List<Long> getListGroup() {
		return groupList;
	}

	public void setListGroup(List<Long> listGroup) {
		this.groupList = listGroup;
	}

	public String getIdSession() {
		return idSession;
	}

	public void setIdSession(String idSession) {
		this.idSession = idSession;
	}

	public Integer getFirstLogin() {
		return firstLogin;
	}

	public void setFirstLogin(Integer firstLogin) {
		this.firstLogin = firstLogin;
	}

	/**
	 * Method that return the default group id that belong the user
	 * 
	 * @return
	 */
	public Long getDefaultGroup() {
		if (this.getListGroup() != null && !this.getListGroup().isEmpty()) {
			return this.getListGroup().get(0);
		}
		return null;
	}

	/**
	 * Method that return the user full name.
	 * 
	 * @return
	 */
	public String getFullName() {
		return this.nameUser + " " + this.lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<Long> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<Long> groupList) {
		this.groupList = groupList;
	}

	public Long getDefaultEnterprise() {
		return defaultEnterprise;
	}

	public void setDefaultEnterprise(Long defaultEnterprise) {
		this.defaultEnterprise = defaultEnterprise;
	}

	public List<Enterprise> getListEnterprise() {
		return enterpriseList;
	}

	public void setListEnterprise(List<Enterprise> listEnterprise) {
		this.enterpriseList = listEnterprise;
	}

	public boolean isSelectEnterprise() {
		return selectEnterprise;
	}

	public void setSelectEnterprise(boolean selectEnterprise) {
		this.selectEnterprise = selectEnterprise;
	}

	/**
	 * Method that return the first SP id that belongs the user
	 * 
	 * @return
	 */
	public Long getDefaulSP() {
		if (!this.listSP.isEmpty()) {
			return this.listSP.get(0);
		}
		return null;
	}

	public String getNameEnterprise() {
		return nameEnterprise;
	}

	public void setNameEnterprise(String nameEnterprise) {
		this.nameEnterprise = nameEnterprise;
	}

}
