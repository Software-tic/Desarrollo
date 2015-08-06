package com.zyos.core.lo.user.permission.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.zyos.alert.studentReport.model.Stage;
import com.zyos.core.common.api.IZyosState;
import com.zyos.core.common.controller.ErrorNotificacion;
import com.zyos.core.common.controller.ZyosBackingBean;
import com.zyos.core.lo.user.model.ZyosGroup;
import com.zyos.core.login.model.ZyosGroupTree;

@ManagedBean
@ViewScoped
@URLMapping(id = "PermissionBean", pattern = "/portal/permisos", viewId = "/pages/security/security.jspx")
public class PermissionBean extends ZyosBackingBean {

	private boolean showTreePanel, showFuncionalityPanel;
	private boolean showGiveProcessPermissionPanel = false;
	private Long idZyosGroup;
	private Long idZyosGroupPermission;

	private List<Object> selectedFuncionalityList;

	private TreeNode root;
	private TreeNode selectedNode;
	private Stage stagePermission;
	private List <ZyosGroup> zyosUserGroupList;
	private PermissionController controller = new PermissionController();

	public PermissionBean() throws Exception {
		idZyosGroup = 1l;
		zyosUserGroupList =  getZyosGroupList();
		handleGroupChange();			
	}
					
		/** @author jhernandez */
		public void handleStagePermission() {
			try {
				
				if(stagePermission != null)
				{
					controller.updateStagePermission(getUserSession().getDocumentNumber(), stagePermission);					
					addInfo( "Cambiar Permiso",
						"Permiso a perfil cambiado correctamente");
					reloadControllerList();
				}
				
			} catch (Exception e) {
				ErrorNotificacion.handleErrorMailNotification(e, this);
			}
		}
	

	public void handleGroupChange() {
		try {
			showTreePanel = false;
			showFuncionalityPanel = false;
			if (idZyosGroup != null && idZyosGroup != 0) {
				root = new DefaultTreeNode("Root", null);
				addLeftToTreeNode(root, 1L, null);
				showTreePanel = true;
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}
	
	public void handleLock(ZyosGroupTree zgt){
		try {
			zgt.setUserChange(getUserSession().getDocumentNumber());
			zgt.setState(zgt.getState().equals(IZyosState.ACTIVE) ? IZyosState.INACTIVE : IZyosState.ACTIVE);
			controller.handleZyosGroupLock(zgt);
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	private void addLeftToTreeNode(TreeNode rootSelectedNode, Long level,
			Long idSelectedTree) throws Exception {
		List<ZyosGroupTree> zgtList = controller.loadTreeByZyosGroup(
				getUserSession().getDefaultEnterprise(), level, idSelectedTree,
				idZyosGroup);
		if (!zgtList.isEmpty()) {
			for (ZyosGroupTree zgt : zgtList) {
				new DefaultTreeNode(zgt, rootSelectedNode);
			}
		} else {
			addError( "Cargar Árbol",
					"No existen elementos asociados para el grupo seleccionado");
		}
	}

	// START TREE HANDLE EVENTS

	public void onNodeExpand(NodeExpandEvent event) {
		try {
			if (event.getTreeNode().getChildCount() < 0
					&& !event.getTreeNode().isExpanded()) {
				ZyosGroupTree zgt = (ZyosGroupTree) event.getTreeNode()
						.getData();
				addLeftToTreeNode(event.getTreeNode(), zgt.getLevel() + 1,
						zgt.getIdTree());
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public void onNodeSelect(NodeSelectEvent event) {
		try {
			ZyosGroupTree zgt = (ZyosGroupTree) event.getTreeNode().getData();
			selectedNode = event.getTreeNode();
			if (!event.getTreeNode().isExpanded()) {
				selectedFuncionalityList = new ArrayList<Object>();
				if (zgt.getIsBranch() == 0) {
					// is leaf
					showFuncionalityPanel = true;
					if (zgt.getFunctionalityList() != null) {
						String i[] = zgt.getFunctionalityList().split(",");
						if (i != null && i.length > 0) {
							for (String string : i) {
								selectedFuncionalityList.add(string);
							}
						}

					}
				} else {
					// is branch so loading leafs
					showFuncionalityPanel = false;
					addLeftToTreeNode(event.getTreeNode(), zgt.getLevel() + 1,
							zgt.getIdTree());
					event.getTreeNode().setExpanded(true);
				}
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	// END TREE HANDLE EVENTS

	public void saveNewPermissionList() {
		try {
			if (selectedNode != null) {
				ZyosGroupTree zgt = (ZyosGroupTree) selectedNode.getData();
				if (selectedFuncionalityList.isEmpty()) {
					zgt.setFunctionalityList("");
				} else {
					zgt.setFunctionalityList(selectedFuncionalityList
							.toString());
					zgt.setFunctionalityList(zgt.getFunctionalityList()
							.replace(" ", "").replace("[", "").replace("]", ""));
				}

				controller.updateFuncionalityList(zgt.getId(),
						zgt.getFunctionalityList(), getUserSession());
				for (ZyosGroupTree gt : getZyosGroupFuntionalityList()) {
					if (zgt.getId().equals(gt.getId())) {
						gt.setFunctionalityList(zgt.getFunctionalityList());
						break;
					}
				}
				addInfo( "Guardar Funcionalidad",
						"Las funcionalides fueron guardadas exitosamente");
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
			addError( "Guardar Funcionalidad",
					"Se presento un error al guardar, intente de nuevo o contacte al administrador");
		}
	}

	public Long getIdZyosGroup() {
		return idZyosGroup;
	}

	public void setIdZyosGroup(Long idZyosGroup) {
		this.idZyosGroup = idZyosGroup;
	}

	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	public List<Object> getSelectedFuncionalityList() {
		return selectedFuncionalityList;
	}

	public void setSelectedFuncionalityList(
			List<Object> selectedFuncionalityList) {
		this.selectedFuncionalityList = selectedFuncionalityList;
	}

	public boolean isShowFuncionalityPanel() {
		return showFuncionalityPanel;
	}

	public void setShowFuncionalityPanel(boolean showFuncionalityPanel) {
		this.showFuncionalityPanel = showFuncionalityPanel;
	}

	public boolean isShowTreePanel() {
		return showTreePanel;
	}

	public void setShowTreePanel(boolean showTreePanel) {
		this.showTreePanel = showTreePanel;
	}

	public Long getIdZyosGroupPermission() {
		return idZyosGroupPermission;
	}

	public void setIdZyosGroupPermission(Long idZyosGroupPermission) {
		this.idZyosGroupPermission = idZyosGroupPermission;
	}

	public Stage getStagePermission() {
		return stagePermission;
	}

	public void setStagePermission(Stage stagePermission) {
		this.stagePermission = stagePermission;
	}

	public boolean isShowGiveProcessPermissionPanel() {
		return showGiveProcessPermissionPanel;
	}

	public void setShowGiveProcessPermissionPanel(boolean showGiveProcessPermissionPanel) {
		this.showGiveProcessPermissionPanel = showGiveProcessPermissionPanel;
	}

	public List<ZyosGroup> getZyosUserGroupList() {
		return zyosUserGroupList;
	}

	public void setZyosUserGroupList(List<ZyosGroup> zyosUserGroupList) {
		this.zyosUserGroupList = zyosUserGroupList;
	}
	
	
	
	
	
	
	
	

}
