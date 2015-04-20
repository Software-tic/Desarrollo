package com.zyos.core.login.model;

import java.io.Serializable;

import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuModel;

public class ZyosMenu implements Serializable {

	private static final long serialVersionUID = -4272286259584560793L;

	private Long idZyosGroup;
	private Long idEnterprise;
	private MenuModel menuList = new DefaultMenuModel();

	/**
	 * Default constructor
	 * 
	 * @param idZG
	 *            idZyosGroup
	 */
	public ZyosMenu(Long idCurrentEnterprise, Long idZG) {
		this.idEnterprise = idCurrentEnterprise;
		this.idZyosGroup = idZG;
	}

	public Long getIdZyosGroup() {
		return idZyosGroup;
	}

	public void setIdZyosGroup(Long idZyosGroup) {
		this.idZyosGroup = idZyosGroup;
	}

	public Long getIdEnterprise() {
		return idEnterprise;
	}

	public void setIdEnterprise(Long idEnterprise) {
		this.idEnterprise = idEnterprise;
	}

	public MenuModel getMenuList() {
		return menuList;
	}

	public void setMenuList(MenuModel menuList) {
		this.menuList = menuList;
	}

}
