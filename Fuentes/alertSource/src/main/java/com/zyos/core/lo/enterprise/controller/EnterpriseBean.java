package com.zyos.core.lo.enterprise.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.zyos.core.common.controller.ErrorNotificacion;
import com.zyos.core.common.controller.ZyosBackingBean;
import com.zyos.core.lo.enterprise.model.Enterprise;

@ViewScoped
@ManagedBean
public class EnterpriseBean extends ZyosBackingBean {
	private boolean selectAllItems;
	private boolean showListEnterprise = true;
	private boolean showViewEnterprise;
	private boolean showEditEnterprise;
	private boolean showAddEnterprise;

	private String idEnterpriseBean;

	private String enterpriseSelectedList;

	private List<Enterprise> enterpriseLocalList = new ArrayList<Enterprise>();
	private List<Enterprise> enterpriseLocalListSelected = new ArrayList<Enterprise>();

	private EnterpriseController controller = new EnterpriseController();
	private Enterprise enterprise = new Enterprise();
	private Enterprise isFreeNIT = new Enterprise();

	public EnterpriseBean() throws Exception {
		try {
			setEnterpriseLocalList(controller.loadEnterpriseList());
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}

	}

	public void handleSelectenterpriseLocalList() {
		try {
			if (selectAllItems)
				for (Enterprise en : enterpriseLocalList)
					en.setSelected(true);
			else
				for (Enterprise en : enterpriseLocalList)
					en.setSelected(false);
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public void detailEnterprise() {
		try {
			if (enterprise != null && enterprise.getId() != null
					&& enterprise.getId() > 0) {
				Long idEnterprise = this.enterprise.getId();

				setEnterprise(controller.loadDataEnterprise(idEnterprise));

				setShowListEnterprise(false);
				setShowViewEnterprise(true);
				setShowEditEnterprise(false);
				setShowAddEnterprise(false);

				setPanelView("en/detailEnterprise",
						"Detalles de la empresa", "enterpriseBean");

			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public void goEditEnterprise() {
		try {
			if (enterprise != null && enterprise.getId() != null
					&& enterprise.getId() > 0) {
				Long idEnterprise = this.enterprise.getId();

				setEnterprise(controller.loadDataEnterprise(idEnterprise));
				setIdEnterpriseBean(enterprise.getIdEnterprise());

				setShowListEnterprise(false);
				setShowEditEnterprise(true);
				setPanelView("en/editEnterprise", "Editar empresa",
						"enterpriseBean");
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public void editEnterprise() {
		try {

			if (enterprise.getId() != null && enterprise.getId() > 0) {
				enterprise.setIdEnterprise(getIdEnterpriseBean());
				if (enterprise.getIdEnterprise().isEmpty()) {
					addWarn( "Editar empresa",
							"Es necesario que ingrese el NIT de la empresa.");
					return;
				}

				if (enterprise.getName().isEmpty()) {
					addWarn( "Editar empresa",
							"Es necesario que ingrese el nombre de la empresa.");
					return;
				}
				controller.updateEnterprise(enterprise, getUserSession());

				addInfo( "Editar empresa",
						"Se han guardado exitosamente los datos editados de la empresa.");
				detailEnterprise();

			}

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public void deleteEnterprise() {
		try {

			for (int i = 0; i < enterpriseLocalList.size(); i++) {
				Enterprise en = enterpriseLocalList.get(i);
				if (en.isSelected())
					enterpriseLocalListSelected.add(en);
			}

			controller.deleteEnterprise(enterpriseLocalListSelected,
					getUserSession());

			addInfo( "Eliminar empresa",
					"Se ha eliminado satisfacoriamente las siguientes empresas: "
							+ getSelectedenterpriseLocalList().toString());

			setEnterpriseLocalList(controller.loadEnterpriseList());

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public void handleDeleteEnterprise() {
		try {
			enterpriseSelectedList = getSelectedenterpriseLocalList();
			if (enterpriseSelectedList != null
					&& !enterpriseSelectedList.isEmpty()) {
				getRequestContext().execute("delEnterprisePopup.show();");
			} else {
				addWarn( "Eliminar empresa",
						"Debe seleccionar por lo menos una empresa.");
			}

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	private String getSelectedenterpriseLocalList() {
		try {
			StringBuilder el = new StringBuilder();
			for (int i = 0; i < enterpriseLocalList.size(); i++) {
				Enterprise en = enterpriseLocalList.get(i);
				if (en.isSelected())
					el.append(en.getName() + ",  ");
			}

			return el.toString();

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
			return null;
		}

	}

	public void goCreateEnterprise() {
		try {
			setShowListEnterprise(false);
			setShowAddEnterprise(false);
			setShowAddEnterprise(true);
			enterprise = new Enterprise();

			setPanelView("en/addEnterprise", "Crear empresa",
					"enterpriseBean");
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public void addEnterprise() {
		try {
			enterprise.setIdEnterprise(getIdEnterpriseBean());
			if (enterprise.getIdEnterprise().isEmpty()) {
				addWarn( "Crear emrpesa",
						"Es necesario que ingrese el NIT de la empresa.");
				return;
			}
			if (enterprise.getName().isEmpty()) {
				addWarn( "Crear empresa",
						"Es necesario que ingrese el nombre de la empresa.");
				return;
			}

			if (enterprise.getDescription().isEmpty()) {
				addWarn( "Crear empresa",
						"Es necesario que ingrese la descripción de la empresa.");
				return;
			}

			controller.saveEnterprise(enterprise, getUserSession());
			goBack();
			addInfo( "Crear empresa",
					"Se ha creado exitosamente la empresa.");
			detailEnterprise();

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
			addError( "Crear empresa",
					"Hubo un error al crear la empresa.");
			detailEnterprise();
		}
	}

	public void goBack() {
		try {
			setShowListEnterprise(true);
			setShowViewEnterprise(false);
			setShowEditEnterprise(false);
			setShowAddEnterprise(false);
			enterprise = new Enterprise();
			setIdEnterpriseBean(null);
			setEnterpriseLocalList(controller.loadEnterpriseList());

			setPanelView("en/listEnterprise", "Lista de empresas",
					"enterpriseBean");

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public void validateNIT() {
		try {

			deleteBadChar();

			if (enterprise.getId() != null && enterprise.getId() > 0) {
				if (enterprise.getIdEnterprise().equals(idEnterpriseBean)) {
					return;
				}
			}

			isFreeNIT = controller.validateNIT(idEnterpriseBean);

			if (isFreeNIT != null && isFreeNIT.getId() != null
					&& isFreeNIT.getId() > 0) {
				addError( "NIT invalido",
						"El  NIT al que hace referencia ya se encuentra registrado con la emrpesa: "
								+ isFreeNIT.getName());

				setIdEnterpriseBean(null);
			}
			isFreeNIT = new Enterprise();

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public void deleteBadChar() {
		try {

			idEnterpriseBean = idEnterpriseBean.replace("*", "");
			idEnterpriseBean = idEnterpriseBean.replace("+", "");
			idEnterpriseBean = idEnterpriseBean.replace("-", "");
			idEnterpriseBean = idEnterpriseBean.replace("/", "");
			idEnterpriseBean = idEnterpriseBean.replace(",", "");
			idEnterpriseBean = idEnterpriseBean.replace(".", "");
			idEnterpriseBean = idEnterpriseBean.replace(" ", "");
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public Enterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	public boolean isSelectAllItems() {
		return selectAllItems;
	}

	public void setSelectAllItems(boolean selectAllItems) {
		this.selectAllItems = selectAllItems;
	}

	public String getEnterpriseSelectedList() {
		return enterpriseSelectedList;
	}

	public void setEnterpriseSelectedList(String enterpriseSelectedList) {
		this.enterpriseSelectedList = enterpriseSelectedList;
	}

	public boolean isShowListEnterprise() {
		return showListEnterprise;
	}

	public void setShowListEnterprise(boolean showListEnterprise) {
		this.showListEnterprise = showListEnterprise;
	}

	public boolean isShowViewEnterprise() {
		return showViewEnterprise;
	}

	public void setShowViewEnterprise(boolean showViewEnterprise) {
		this.showViewEnterprise = showViewEnterprise;
	}

	public boolean isShowEditEnterprise() {
		return showEditEnterprise;
	}

	public void setShowEditEnterprise(boolean showEditEnterprise) {
		this.showEditEnterprise = showEditEnterprise;
	}

	public boolean isShowAddEnterprise() {
		return showAddEnterprise;
	}

	public void setShowAddEnterprise(boolean showAddEnterprise) {
		this.showAddEnterprise = showAddEnterprise;
	}

	public String getIdEnterpriseBean() {
		return idEnterpriseBean;
	}

	public void setIdEnterpriseBean(String idEnterpriseBean) {
		this.idEnterpriseBean = idEnterpriseBean;
	}

	public List<Enterprise> getEnterpriseLocalList() {
		return enterpriseLocalList;
	}

	public void setEnterpriseLocalList(List<Enterprise> enterpriseLocalList) {
		this.enterpriseLocalList = enterpriseLocalList;
	}

}
