package com.zyos.core.mail.io.mn.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;


import com.zyos.core.common.controller.ErrorNotificacion;
import com.zyos.core.common.controller.ZyosBackingBean;
import com.zyos.core.common.list.BeanList;
import com.zyos.core.common.model.ColumnModel;
import com.zyos.core.common.model.RowModel;
import com.zyos.core.common.util.ZyosFieldValidator;
import com.zyos.core.common.util.excel.ZyosExcel;
import com.zyos.core.mail.io.mn.api.IEmailTemplateType;
import com.zyos.core.mail.io.mn.aws.SMTPEmail;
import com.zyos.core.mail.io.mn.model.EmailTemplate;

@ManagedBean
@ViewScoped
public class EmailBean extends ZyosBackingBean {

	private static final long serialVersionUID = 1L;

	private boolean showTokenList;
	private boolean showTableList;

	private Long idEmailTemplate;
	private String subject;
	private String body;
	private String variableLikeString = "";

	private List<RowModel> fileValueList;
	private List<ColumnModel> simpleColumns = new ArrayList<ColumnModel>();

	private EmailTemplate et = new EmailTemplate();
	private EmailController controller = new EmailController();

	private StreamedContent file;

	/**
	 * Default constructor
	 * 
	 * @throws Exception
	 */
	public EmailBean() throws Exception {
		try {
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	/**
	 * Method for sending the email to selected contacts or uploaded contact
	 * list (excel file validate with parameters)
	 */
	public void sendEmail() {
		try {
			Double q = null;
			if (simpleColumns != null && simpleColumns.size() > 2) {
				// has got parameters
				SMTPEmail smtp = new SMTPEmail();

				for (int i = 0; i < fileValueList.size(); i++) {
					String email = fileValueList.get(i).getValueRowAsIndex(0);
					if (email != null && ZyosFieldValidator.isEmail(email)) {
						String b = new String(body);
						ArrayList<String> params = fileValueList.get(i)
								.getValueList();
						if (params != null) {
							b = BeanList.properties.formatMessage(b,
									params.toArray());

							if (et.getAnalyticsCode() != null)
								b = EmailHandler.addGoogleAnalytics(
										et.getAnalyticsCode(), b);

							if (ZyosFieldValidator.isEmail(email)) {
								smtp.addMessage(getProperty("mail.smtp.user"),
										subject, b, email);
							}
						}

					}
				}
				smtp.sendMultipleEmail();

			} else {
				// simple send mail (a lot of contact for unique message)
				SMTPEmail smtp = new SMTPEmail();
				int l = 0;
				StringBuilder el = new StringBuilder();
				if (fileValueList != null) {
					for (RowModel o : fileValueList) {
						String email = o.getValueRowAsIndex(0);
						if (email != null && ZyosFieldValidator.isEmail(email)) {
							el.append(email + ",");
						}
						if (l != 0 && l % 5 == 0 && !el.toString().isEmpty()) {
							smtp.addMessage(getProperty("mail.smtp.user"),
									subject, body, el.toString());
							el = new StringBuilder();
						}
						l++;
					}
					if (el != null && !el.toString().isEmpty()) {
						smtp.addMessage(getProperty("mail.smtp.user"), subject,
								body, el.toString());
					}
					smtp.sendMultipleEmail();

				} else {
					addWarn("Envío correo electrónico",
							"No se ha encontrado el archivo adjunto con los datos, valide e intente de nuevo");
					return;
				}

			}
			addInfo(
					"Envío de correo",
					"Se está realizando el envío de los correos electrónicos, actualmente tiene disponible una cuota de envío de correos de "
							+ q);
			clearData();
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	/**
	 * Method for uploading the excel file with the email list and parameters
	 * 
	 * @param event
	 */
	public void handleFileUpload(FileUploadEvent event) {
		try {
			UploadedFile fileInfo = event.getFile();
			if (fileInfo != null
					&& (fileInfo.getFileName().endsWith(".xls") || fileInfo
							.getFileName().endsWith(".xlsx"))) {
				this.variableLikeString = "";
				this.simpleColumns = new ArrayList<ColumnModel>();
				this.fileValueList = new ArrayList<RowModel>();
				if (fileInfo.getFileName().endsWith(".xlsx")) {
					getHeaderListAndValueList(ZyosExcel.readXLSXFile(fileInfo
							.getInputstream()));
				} else {
					getHeaderListAndValueList(ZyosExcel.readXLSFile(fileInfo
							.getInputstream()));
				}
				showTableList = true;
			} else {
				addInfo( "Formato",
						"Aseguresé de usar un formato valido xls ó xlsx");
				showTableList = false;
				fileInfo = null;
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e,
					getUserSession() == null ? "system" : getUserSession()
							.getFullName());
		}
	}

	/**
	 * Method for getting the headers of current uploaded file and after
	 * creating the value list with the email's and another parameters for
	 * sending the email
	 * 
	 * @param readExcelFile
	 */
	private void getHeaderListAndValueList(Workbook readExcelFile) {
		try {
			if (readExcelFile != null) {
				Iterator<Cell> ci = readExcelFile.getSheetAt(0).getRow(0)
						.cellIterator();
				int i = 0;
				StringBuilder sb = new StringBuilder();
				for (Iterator<Cell> iterator = ci; iterator.hasNext(); i++) {
					String name = getValueCell(iterator);
					if (name != null && !name.isEmpty()) {
						simpleColumns.add(new ColumnModel(name
								+ (i != 0 ? (" {" + (i - 1) + "}") : ""),
								"value" + i));
						if (i != 0) {
							sb.append(name + " = {" + i + "} - ");
						}
					}
				}

				this.variableLikeString = sb.toString();
				Iterator<Row> ri = readExcelFile.getSheetAt(0).rowIterator();
				ri.next();// jump head
				while (ri != null && ri.hasNext()) {
					Row r = ri.next();
					Iterator<Cell> ci2 = r.cellIterator();
					int j = 0;
					RowModel vl = new RowModel();
					for (Iterator<Cell> i2 = ci2; i2.hasNext(); j++) {
						String v = getValueCell(i2);
						if (v != null && !v.isEmpty() && j < 10) {
							if (j == 0)
								fileValueList.add(vl);
							vl.setValueRow(j, v);
						} else
							j--;
					}
				}
			}

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	/**
	 * Method for getting the cell value
	 * 
	 * @param iterator
	 * @return
	 */
	private String getValueCell(Iterator<Cell> iterator) {
		Cell c = iterator.next();
		int ct = c.getCellType();
		String value = null;
		double v;
		if (ct == Cell.CELL_TYPE_NUMERIC) {
			v = c.getNumericCellValue();
			value = String.valueOf(Double.valueOf(v).longValue());
		} else if (ct == Cell.CELL_TYPE_STRING)
			value = c.getStringCellValue();
		return value;
	}

	/**
	 * Method for cleaning the data
	 */
	public void clearData() {
		try {
			this.variableLikeString = null;
			this.body = "";
			this.idEmailTemplate = null;
			this.subject = "";
			this.fileValueList = null;
			this.et = new EmailTemplate();
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	/**
	 * Method for selecting a new email template
	 */
	public void selectEmailTemplateList() {
		try {
			if (idEmailTemplate != null
					&& !idEmailTemplate.equals(Long.valueOf(0))) {
				EmailTemplate etTemp = getEmailTemplateFromList(idEmailTemplate);
				body = etTemp.getBody();
				subject = etTemp.getSubject();
				et = etTemp;
			} else {
				clearData();
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	/**
	 * Method for saving a template
	 */
	public void saveTemplate() {
		try {
			if (et != null && body != null && subject != null
					&& et.getName() != null) {
				if (et.getId() != null) {
					// edit
					et.initializing(getUserSession().getDocumentNumber(), false);
					et.setBody(body);
					et.setSubject(subject);
					if (controller.editTemplate(et)) {
						addInfo(
								"Administración plantillas",
								"La plantilla se guardo exitosamente");
						clearData();
						return;
					}
				} else {
					et.setIdEmailTemplateType(IEmailTemplateType.CUSTOM);
					et.initializing(getUserSession().getDocumentNumber(), true);
					if (controller.saveTemplate(et)) {
						BeanList.getControllerEnterpriseList(
								getUserSession().getDefaultEnterprise())
								.getEmailTemplateList().add(0, et);
						addInfo(
								"Administración plantillas",
								"La plantilla se guardo exitosamente");
						clearData();
						return;
					}
				}
				addError(
						"Administración plantillas",
						"Se presento un error al guardar la plantilla, consulte con el administrador");
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	/**
	 * Method for getting a email template from a current selected idET
	 * 
	 * @param id
	 * @return
	 */
	private EmailTemplate getEmailTemplateFromList(Long id) {
		try {
			return EmailHandler.getEmailTemplate(getUserSession()
					.getDefaultEnterprise(), id);
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
			return null;
		}
	}

	public Long getIdEmailTemplate() {
		return idEmailTemplate;
	}

	public void setIdEmailTemplate(Long idEmailTemplate) {
		this.idEmailTemplate = idEmailTemplate;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public boolean isShowTokenList() {
		return showTokenList;
	}

	public void setShowTokenList(boolean showTokenList) {
		this.showTokenList = showTokenList;
	}

	public boolean isShowTableList() {
		return showTableList;
	}

	public void setShowTableList(boolean showTableList) {
		this.showTableList = showTableList;
	}

	public List<RowModel> getFileValueList() {
		return fileValueList;
	}

	public void setFileValueList(ArrayList<RowModel> fileValueList) {
		this.fileValueList = fileValueList;
	}

	public List<ColumnModel> getSimpleColumns() {
		return simpleColumns;
	}

	public void setSimpleColumns(List<ColumnModel> simpleColumns) {
		this.simpleColumns = simpleColumns;
	}

	public EmailTemplate getEt() {
		return et;
	}

	public void setEt(EmailTemplate et) {
		this.et = et;
	}

	public String getVariableLikeString() {
		return variableLikeString;
	}

	public void setVariableLikeString(String variableLikeString) {
		this.variableLikeString = variableLikeString;
	}

	public StreamedContent getFile() {
		try {
			file = new DefaultStreamedContent(this.getClass()
					.getResourceAsStream("estructura.xls"), "application/xls",
					"estructura.xls");
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
		return file;
	}

	public void setFile(StreamedContent file) {
		this.file = file;
	}

}
