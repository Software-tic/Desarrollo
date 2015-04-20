
package com.zyos.core.login.controller;

import java.util.ArrayList;
import java.util.List;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuElement;
import org.primefaces.model.menu.MenuModel;

import com.zyos.core.common.controller.ErrorNotificacion;
import com.zyos.core.common.controller.ZyosController;
import com.zyos.core.common.list.BeanList;
import com.zyos.core.common.list.ControllerEnterpriseList;
import com.zyos.core.login.model.Tree;
import com.zyos.core.login.model.ZyosGroupTree;
import com.zyos.core.login.model.ZyosGroupTreeDAO;
import com.zyos.core.login.model.ZyosMenu;

public class ZyosMenuController extends ZyosController {

	/**
	 * Default method to create branch's and leaf's
	 * 
	 * @param idZG
	 *            Long id of Zyos Group
	 * @return return tree
	 */ 
	public void loadMenuAndSubmenuByEnterprise() {
		ZyosGroupTreeDAO groupTreeDAO = new ZyosGroupTreeDAO();
		try {
			List<Tree> treeListByGroup = groupTreeDAO.getTreeListByGroup(false);
			ControllerEnterpriseList cel = null;

			List<DefaultSubMenu> subMenuList = new ArrayList<DefaultSubMenu>();

			ZyosMenu currentZM = null;
			ZyosGroupTree currentZGR = null;

			Tree currentT = null;
			DefaultSubMenu currentSm = null;

			if (treeListByGroup != null) {
				for(ControllerEnterpriseList c : BeanList.getControllerEnterpriseList()){
					c.setZyosMenuList(null);
					c.setZyosGroupTreeList(null);
					c.setZyosGroupFuntionalityList(null);
				}
				for (int j = 0; j < treeListByGroup.size(); j++) {
					Tree t = treeListByGroup.get(j);
					cel = BeanList.getCEL(cel, t.getIdEnteprise());
					if (cel != null) {
						if (currentZGR == null
								|| (currentZGR != null
										&& currentZGR.getIdZyosGroup() != null && !currentZGR
										.getIdZyosGroup().equals(
												t.getIdZyosGroup()))) {
							if (t.getIsBranch() != null
									&& t.getIsBranch().equals(1)) {
								subMenuList.clear();
								currentZGR = new ZyosGroupTree(
										t.getIdZyosGroup());
								currentZM = new ZyosMenu(t.getIdEnteprise(),
										t.getIdZyosGroup());
								cel.getZyosMenuList().add(currentZM);
								cel.getZyosGroupTreeList().add(currentZGR);
								currentT = null;
								currentSm = null;
							} else {
								currentZGR = null;
							}
						}
						processMenu(subMenuList, currentZM, currentZGR, t,
								currentT, currentSm);
					}
				}
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
			ErrorNotificacion.handleErrorMailNotification(e,
					"ZyosController - getMenuAndSubmenu");
		} finally {
			groupTreeDAO.getSession().close();
			groupTreeDAO = null;
		}
	}

	private void processMenu(List<DefaultSubMenu> subMenuList,
			ZyosMenu currentZM, ZyosGroupTree currentZGR, Tree t,
			Tree currentT, DefaultSubMenu currentSm) {
		if (currentZGR != null && t.getTreeLevel() != null) {
			if (t.getTreeLevel().intValue() == 1) {
				currentT = new Tree(t);
				currentSm = createSubmenu(t);
				currentZGR.getTreeList().add(currentT);
				currentZM.getMenuList().addElement(currentSm);
			} else if (t.getTreeLevel().intValue() == 2) {
				if (t.getIsBranch() != null && t.getIsBranch().equals(1)) {
					DefaultSubMenu sm = getMenu(currentZM.getMenuList(),
							t.getIdBranch());
					if (sm != null) {
						DefaultSubMenu sm2 = createSubmenu(t);
						sm.getElements().add(sm2);
						subMenuList.add(sm2);
					}
				} else {
					DefaultSubMenu sm = getMenu(currentZM.getMenuList(),
							t.getIdBranch());
					if (sm != null) {
						sm.getElements().add(createMenuItem(t));
					}
					currentT = getCurrentTree(currentZGR, t.getIdBranch());
					if (currentT != null)
						currentT.getLeafList().add(t);
				}
			} else if (t.getTreeLevel().intValue() == 3) {
				DefaultSubMenu sm = getSubMenu(subMenuList, t.getIdBranch());
				if (sm != null) {
					sm.getElements().add(createMenuItem(t));
				}
			}
		}
	}

	private Tree getCurrentTree(ZyosGroupTree currentZGR, Long idBranch) {
		for (Tree t : currentZGR.getTreeList()) {
			if (t.getId().equals(idBranch)) {
				return t;
			}
		}
		return null;
	}

	private DefaultSubMenu getSubMenu(List<DefaultSubMenu> subMenuList,
			Long idBranch) {
		for (DefaultSubMenu sm : subMenuList) {
			if (sm.getId().equals("submenu" + idBranch))
				return sm;
		}
		return null;
	}

	private DefaultSubMenu getMenu(MenuModel menuList, Long idBranch) {
		for (MenuElement c : menuList.getElements()) {
			DefaultSubMenu sm = (DefaultSubMenu) c;
			if (sm.getId().equals("submenu" + idBranch))
				return sm;
		}
		return null;
	}

	private DefaultSubMenu createSubmenu(Tree o) {
		try {
			DefaultSubMenu submenu = new DefaultSubMenu();
			submenu.setId("submenu" + o.getId());
			submenu.setLabel(o.getLabel());
			return submenu;
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
		return null;
	}

	private DefaultMenuItem createMenuItem(Tree o) {
		try {
			DefaultMenuItem item = new DefaultMenuItem();
			item.setValue(o.getLabel());
			item.setId("item" + o.getId());
			item.setAjax(true);
			item.setPartialSubmit(true);
			item.setUrl(""+o.getDisplayPanel());
			return item;
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
		return null;
	}

	public static MenuModel getTreeByZyosGroup(Long idZG, Long idEnterprise,
			int count) {
		try {
			ControllerEnterpriseList cel = BeanList
					.getControllerEnterpriseList(idEnterprise);
			if (cel != null) {
				for (ZyosMenu zt : cel.getZyosMenuList())
					if (zt.getIdZyosGroup().equals(idZG))
						return zt.getMenuList();
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e,
					"ZyosMenu - getTreeByZyosGroup");
		}
		if (count > 2)
			return null;
		count++;
		return getTreeByZyosGroup(idZG, Long.valueOf(0), count);
	}

	public static List<Tree> getZyosGroupTree(Long idZG, Long idEnterprise) {
		try {
			ControllerEnterpriseList cel = BeanList
					.getControllerEnterpriseList(idEnterprise);
			if (cel != null)
				for (ZyosGroupTree zt : cel.getZyosGroupTreeList())
					if (zt.getIdZyosGroup().equals(idZG))
						return zt.getTreeList();
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "getZyosGroupTree");
		}
		return null;
	}
}
