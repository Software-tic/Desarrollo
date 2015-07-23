package com.zyos.core.lo.user.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.zyos.core.common.api.IZyosGroup;
import com.zyos.core.common.controller.ErrorNotificacion;
import com.zyos.core.common.controller.ZyosBackingBean;
import com.zyos.core.common.util.ZyosFieldValidator;
import com.zyos.core.lo.user.model.ZyosGroup;
import com.zyos.core.lo.user.model.ZyosUser;
import com.zyos.core.lo.user.model.ZyosUserModel;

/**
 * @class ManageUserBackingBean
 * 
 *        Class that controller all actions for manage user bean.
 * 
 * @author Oscar Garzon
 * 
 */
@ManagedBean(name = "userBean")
@ViewScoped
@URLMapping(id = "user", pattern = "/portal/usuarios", viewId = "/pages/user/user.jspx")
public class UserBean extends ZyosBackingBean {

	private boolean showListUser = true, showAddUser, showEditUser,
			showContactUser, showDetailUser;

	private boolean loginUserExist;
	private boolean documentUserExist;

	private String userSelectedNameList, userName, userLastname, userEmail,
			userDocument, userLogin;

	private List<ZyosUser> filteredUserList;
	private List<ZyosUser> userList;
	private List<Object> selectedZyosGroup;

	private ZyosUser[] selectedZyosUserList;

	private ZyosUserModel zyosUserModel;
	private List <ZyosGroup> zyosUserGroupList;

	private ZyosUser selectedZyosUser;
	private UserController controller = new UserController();

	public UserBean() throws Exception {
		try {
			
			userList = controller.loadUserListByEnterprise(getUserSession().getDefaultEnterprise(), getUserSession().getDefaultGroup());
			zyosUserModel = new ZyosUserModel(userList);
			
			
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	// Handle and validate methods

	public void validateUserDocument() {
		try {
			if (isShowEditUser()
					&& userDocument
							.equals(selectedZyosUser.getDocumentNumber()))
				return;
			ZyosUser zu = controller.validateUserDocument(userDocument);
			documentUserExist = false;
			if (zu != null && zu.getIdZyosUser() != null)
				documentUserExist = true;

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public void validateUserLogin() {
		try {
			if (isShowEditUser() && userLogin.equals(selectedZyosUser.getZyosLogin().getUserLogin()))
				return;
			loginUserExist = false;
			
			if (controller.validateUserLogin(userLogin)){
				loginUserExist = true;
				execute("userButton.disable()");
			}else{
				execute("userButton.enable()");
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	// end handle method

	// go Method

	public void goAddUser() {
		try {
			selectedZyosUser = new ZyosUser();
			clearData();
			setShowAddUser(true);
			setPanelView("addUser", "Agregar Usuario", "userBean");
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public void goEditUser() {
		try {
			userName = selectedZyosUser.getName();
			userLogin = selectedZyosUser.getZyosLogin().getUserLogin();
			userLastname = selectedZyosUser.getLastName();
			userEmail = selectedZyosUser.getEmail();
			userDocument = selectedZyosUser.getDocumentNumber();
			
			selectedZyosGroup = controller
					.loadZyosGroupListByUser(selectedZyosUser.getIdZyosUser());

			setShowEditUser(true);
			setPanelView("editUser", "Editar Usuario", "UserBean");
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public void goDeleteUser() {
		try {
			if (selectedZyosUserList != null && selectedZyosUserList.length > 0) {
				userSelectedNameList = null;
				StringBuilder userList = new StringBuilder();
				for (ZyosUser zu : selectedZyosUserList) {
					userList.append(zu.getName() + " " + zu.getLastName());
					userList.append(", ");
				}
				userSelectedNameList = userList.toString();
				getRequestContext().execute("delUserPopup.show();");
			} else {
				addWarn( "Borrar Usuarios",
						"Debe seleccionar por lo menos un usuario para continuar.");
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public void goDetailUser() {
		try {
			selectedZyosGroup = controller.loadZyosGroupListByUser(selectedZyosUser.getIdZyosUser());
			userSelectedNameList = "";
			for (Object o : selectedZyosGroup) {
				userSelectedNameList += getNameLabelList(getGroupList(),Long.valueOf(o.toString()))+ " ";
			}

			setShowDetailUser(true);
			setPanelView("editUser", "Editar Usuario", "UserBean");
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public void goBack() {
		try {
			clearData();
			setShowListUser(true);
			setPanelView("addUser", "Agregar Usuario", "userBean");
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	// end go method

	// action, save, edit or delete methods

	private void clearData() {
		loginUserExist = documentUserExist = false;
		userSelectedNameList = userDocument = userEmail = userLastname = userLogin = userName = null;
		selectedZyosGroup = null;
	}

	public boolean validateUser() {
		try {
			if (selectedZyosUser != null && userDocument != null && ZyosFieldValidator.isEmail(userEmail) && ZyosFieldValidator.isText(userName + userLastname)
					&& selectedZyosUser.validateZyosUser()) {
				return true;
			}
			return false;
		} catch (RuntimeException e) {
			throw e;
		}
	}

	public void saveUser() {
		saveOrEditProcess(true);
	}

	public void editUser() {
		saveOrEditProcess(false);
	}

	private void saveOrEditProcess(boolean isNew) throws RuntimeException {
		try {
			if (validateUser()) {
				selectedZyosUser.setName(userName);
				selectedZyosUser.setLastName(userLastname);
				selectedZyosUser.setDocumentNumber(userDocument);
				selectedZyosUser.setEmail(userEmail);
				selectedZyosUser.getZyosLogin().setUserLogin(userLogin);
				selectedZyosUser.initializing(getUserDocument(), isNew);

				if (isNew) {
					selectedZyosUser.setIdEnterprise(getUserSession().getDefaultEnterprise());
					controller.saveUser(selectedZyosUser, selectedZyosGroup);
					//userList.add(selectedZyosUser);
					((List<ZyosUser>)zyosUserModel.getWrappedData()).add(selectedZyosUser);
					
				} else {
					controller.editUser(selectedZyosUser, selectedZyosGroup, getUserSession().getDocumentNumber());
				}

				selectedZyosUser.setDocumentType(getNameLabelList(getDocumentTypeList(),selectedZyosUser.getIdDocumentType()));
				selectedZyosUser.setZyosGroup(getNameLabelList(getGroupList(),selectedZyosUser.getIdZyosGroup()));

				addInfo( "Guardar Usuario",
						"El usuario fue guardado exitosamente");
				setShowListUser(true);
				setPanelView("userList", "Listar Usuarios", "UserBean");
			} else {
				addError( "Guardar Usuario", "Se presento un error en la validación de usuario, valide los campos e intente de nuevo");
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
			addError("Editar Usuario", "Se presento un error al guardar el usuario, por favor contacte al administrador");
		}
	}

	public void deleteUser() {
		try {
			if (selectedZyosUserList != null && selectedZyosUserList.length > 0) {
				controller.deleteUser(selectedZyosUserList, getUserSession()
						.getDocumentNumber());
				userList.remove(selectedZyosUserList);
				addInfo( "Eliminar Usuario",
						"Se han deshabilitado el/los usuario(s) seleccionado(s) correctamente");
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
			addError(
					"Eliminar Usuario",
					"Se presento un error al eliminar el/los usuario(s), por favor contacte al administrador");
		}
	}

	// show methods

	public boolean isShowListUser() {
		return showListUser;
	}

	public void setShowListUser(boolean showListUser) {
		this.showListUser = showListUser;
		showAddUser = showContactUser = showDetailUser = showEditUser = false;
	}

	public boolean isShowAddUser() {
		return showAddUser;
	}

	public void setShowAddUser(boolean showAddUser) {
		this.showAddUser = showAddUser;
		showContactUser = showDetailUser = showEditUser = showListUser = false;
	}

	public boolean isShowEditUser() {
		return showEditUser;
	}

	public void setShowEditUser(boolean showEditUser) {
		this.showEditUser = showEditUser;
		showAddUser = showContactUser = showDetailUser = showListUser = false;
	}

	public boolean isShowContactUser() {
		return showContactUser;
	}

	public void setShowContactUser(boolean showContactUser) {
		this.showContactUser = showContactUser;
		showAddUser = showDetailUser = showEditUser = showListUser = false;
	}

	public boolean isShowDetailUser() {
		return showDetailUser;
	}

	public void setShowDetailUser(boolean showDetailUser) {
		this.showDetailUser = showDetailUser;
		showAddUser = showContactUser = showEditUser = showListUser = false;
	}

	// end

	public boolean isLoginUserExist() {
		return loginUserExist;
	}

	public void setLoginUserExist(boolean loginUserExist) {
		this.loginUserExist = loginUserExist;
	}

	public boolean isDocumentUserExist() {
		return documentUserExist;
	}

	public void setDocumentUserExist(boolean documentUserExist) {
		this.documentUserExist = documentUserExist;
	}

	public List<ZyosUser> getUserList() {
		return userList;
	}

	public void setUserList(List<ZyosUser> userList) {
		this.userList = userList;
	}

	public List<ZyosUser> getFilteredUserList() {
		return filteredUserList;
	}

	public void setFilteredUserList(List<ZyosUser> filteredUserList) {
		this.filteredUserList = filteredUserList;
	}

	public ZyosUser[] getSelectedZyosUserList() {
		return selectedZyosUserList;
	}

	public void setSelectedZyosUserList(ZyosUser[] selectedZyosUserList) {
		this.selectedZyosUserList = selectedZyosUserList;
	}

	public ZyosUserModel getZyosUserModel() {
		return zyosUserModel;
	}

	public void setZyosUserModel(ZyosUserModel zyosUserModel) {
		this.zyosUserModel = zyosUserModel;
	}

	public ZyosUser getSelectedZyosUser() {
		return selectedZyosUser;
	}

	public void setSelectedZyosUser(ZyosUser selectedZyosUser) {
		this.selectedZyosUser = selectedZyosUser;
	}

	public String getUserSelectedNameList() {
		return userSelectedNameList;
	}

	public void setUserSelectedNameList(String userSelectedNameList) {
		this.userSelectedNameList = userSelectedNameList;
	}

	public UserController getController() {
		return controller;
	}

	public void setController(UserController controller) {
		this.controller = controller;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserLastname() {
		return userLastname;
	}

	public void setUserLastname(String userLastname) {
		this.userLastname = userLastname;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserDocument() {
		return userDocument;
	}

	public void setUserDocument(String userDocument) {
		this.userDocument = userDocument;
	}

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public List<Object> getSelectedZyosGroup() {
		return selectedZyosGroup;
	}

	public void setSelectedZyosGroup(List<Object> selectedZyosGroup) {
		this.selectedZyosGroup = selectedZyosGroup;
	}

	public List<ZyosGroup> getGroupList() {
		return getGroupList(getUserSession().getDefaultGroup().equals(
				IZyosGroup.ADMINISTRATOR) ? true : false);
	}

	public List<ZyosGroup> getZyosUserGroupList() {
		return zyosUserGroupList;
	}

	public void setZyosUserGroupList(List<ZyosGroup> zyosUserGroupList) {
		this.zyosUserGroupList = zyosUserGroupList;
	}


	
	
}
