package com.zyos.core.lo.user.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.zyos.alert.studentReport.model.Stage;
import com.zyos.core.common.controller.ErrorNotificacion;
import com.zyos.core.common.controller.ZyosBackingBean;
import com.zyos.core.lo.user.model.ZyosGroup;
import com.zyos.core.lo.user.model.ZyosGroupModel;

@ManagedBean
@ViewScoped
@URLMapping(id = "ZyosGroupBean", pattern = "/portal/grupos", viewId = "/pages/group/group.jspx")
public class ZyosGroupBean extends ZyosBackingBean {
	private boolean showList = true, showDetail, showAdd, showEdit;

	private String code, name;
	private String zyosgroupSelectedNameList;

	private ZyosGroup[] selectedZyosGroupList;

	private ZyosGroupModel zyosgroupModel;
	private ZyosGroup zyosgroup;
	

	private ZyosGroupController controller = new ZyosGroupController();

	public ZyosGroupBean() throws Exception {
		try {

			setZyosgroupModel(new ZyosGroupModel(getZyosGroupList()));
			

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}


	public void clean() {
		code = name = null;
		zyosgroup = null;
	}

	/** start GO method */

	public void goAddZyosGroup() {
		try {
			clean();
			setShowAdd(true);
			setPanelView("addZyosGroup", "Agregar Zyos Grupo", "ZyosGroupBean");
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}

	}

	public void goEditZyosGroup() {
		try {
			if (zyosgroup != null) {
				code = zyosgroup.getDescription();
				name = zyosgroup.getName();
				setShowEdit(true);
				setPanelView("addZyosGroup", "Editar Zyos Grupo", "ZyosGroupBean");
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public void goDetailZyosGroup() {
		try {
			if (zyosgroup != null) {
				setShowDetail(true);
				setPanelView("detailZyosGroup", "Detallar Zyos Grupo", "ZyosGroupBean");
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public void goDeleteZyosGroup() {
		try {
			if (selectedZyosGroupList != null && selectedZyosGroupList.length > 0) {

				boolean deleteZyosGroup = true;

				for (ZyosGroup z : selectedZyosGroupList) {

					for (Stage s : getStageList()) {
						if (z.getId().equals(s.getId())) {
							deleteZyosGroup = false;
						}
					}
				}
				if (deleteZyosGroup == true) {
					zyosgroupSelectedNameList = "";
					int i = 1;
					for (ZyosGroup c : selectedZyosGroupList) {
						zyosgroupSelectedNameList += c.getName() + (i < selectedZyosGroupList.length ? ", " : ".");
						i++;
					}
					execute("delCompPopup.show()");
				}

				else {
					addError("Eliminar Perfil",
						" Uno o varios de los perfiles seleccionados no se pueden eliminar debido a que es un rol interventor.");

				}
			} else {
				addError("Eliminar Perfil", "Debe seleccionar por lo menos un grupo");

			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}

	}

	public void goBack() {
		try {
			clean();
			setShowList(true);
			setPanelView("zyosgroupList", "Listar Zyos Grupo", "zyosgroupBean");
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	/** end action method */

	/** start action methods */
	public void addZyosGroup() {
		try {
			if (validateZyosGroup()) {
				ZyosGroup c = new ZyosGroup(name, code);
				

				c.initializing(getUserSession().getDocumentNumber(), true);

				controller.addZyosGroup(c, getUserSession().getDefaultEnterprise());

				

				getZyosGroupList().add(c);

				addInfo("Agregar Perfil", "Perfil fue agregado exitosamente");
				goBack();
				return;
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
		addError("Agregar Perfil", "Valide los campos e intente de nuevo");
	}

	public void editZyosGroup() {
		try {
			if (validateZyosGroup()) {
				zyosgroup.initializing(getUserSession().getDocumentNumber(), false);

				zyosgroup.setName(name);
				zyosgroup.setDescription(code);

				/*
				 * Stage stage = new Stage();
				 * stage.initializing(getUserSession().getDocumentNumber(), false);
				 * stage.setName(code); stage.setDescription(name);
				 * stage.setIdStage(zyosgroup.getId());
				 */


				controller.editZyosGroup(zyosgroup);
				// controller.editStage(getUserSession().getDocumentNumber(), stage);
				addInfo("Editar perfil", "Perfil editado correctamente");
				goBack();
				return;
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
		addError("Editar Perfil", "Valide los campos e intente de nuevo");
	}

	public void deleteZyosGroup() {
		try {
			controller.deleteZyosGroup(getUserSession().getDocumentNumber(), selectedZyosGroupList);

			/*
			 * List<Long> idStageList = new ArrayList <Long>(); for (ZyosGroup c :
			 * selectedZyosGroupList) { idStageList.add(c.getId()); }
			 * controller.deleteStage(getUserSession().getDocumentNumber(), idStageList);
			 */
			for (ZyosGroup c : selectedZyosGroupList) {
				getZyosGroupList().remove(c);
			}
			addInfo("Borrar Categoria", "Las Categorias fueron borradas exitosamente");
			return;
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
		addError("Borrar Categorias", "Se presento un error al borrar las categorias por favor intente de nuevo o contacte al administrador");

	}

	/** end action methods */

	/*** validate function */

	public boolean validateZyosGroup() {
		if (code != null && !code.isEmpty() && name != null && !name.isEmpty()) {
			return true;
		}
		return false;
	}

	public boolean isShowList() {
		return showList;
	}

	public void setShowList(boolean showList) {
		this.showList = showList;
		this.showAdd = showDetail = showEdit = false;
	}

	public boolean isShowDetail() {
		return showDetail;
	}

	public void setShowDetail(boolean showDetail) {
		this.showDetail = showDetail;
		this.showAdd = showEdit = showList = false;
	}

	public boolean isShowAdd() {
		return showAdd;
	}

	public void setShowAdd(boolean showAdd) {
		this.showAdd = showAdd;
		this.showDetail = showList = showDetail = false;
	}

	public boolean isShowEdit() {
		return showEdit;
	}

	public void setShowEdit(boolean showEdit) {
		this.showEdit = showEdit;
		this.showAdd = showDetail = showList = false;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getZyosgroupSelectedNameList() {
		return zyosgroupSelectedNameList;
	}

	public void setZyosgroupSelectedNameList(String zyosgroupSelectedNameList) {
		this.zyosgroupSelectedNameList = zyosgroupSelectedNameList;
	}

	public ZyosGroup[] getSelectedZyosGroupList() {
		return selectedZyosGroupList;
	}

	public void setSelectedZyosGroupList(ZyosGroup[] selectedZyosGroupList) {
		this.selectedZyosGroupList = selectedZyosGroupList;
	}

	public ZyosGroupModel getZyosgroupModel() {
		return zyosgroupModel;
	}

	public void setZyosgroupModel(ZyosGroupModel zyosgroupModel) {
		this.zyosgroupModel = zyosgroupModel;
	}

	public ZyosGroup getZyosgroup() {
		return zyosgroup;
	}

	public void setZyosgroup(ZyosGroup zyosgroup) {
		this.zyosgroup = zyosgroup;
	}

	public ZyosGroupController getController() {
		return controller;
	}

	public void setController(ZyosGroupController controller) {
		this.controller = controller;
	}



	
	

}
