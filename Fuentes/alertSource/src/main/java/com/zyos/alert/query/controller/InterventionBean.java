package com.zyos.alert.query.controller;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.poi.ss.usermodel.BorderFormatting;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.primefaces.model.StreamedContent;

import com.ocpsoft.pretty.PrettyContext;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.zyos.alert.faculty.model.Faculty;
import com.zyos.alert.studentReport.api.IReportType;
import com.zyos.alert.studentReport.api.IStatusReportStudent;
import com.zyos.alert.studentReport.model.Degree;
import com.zyos.alert.studentReport.model.GraphicData;
import com.zyos.alert.studentReport.model.Observation;
import com.zyos.alert.studentReport.model.ReportStudent;
import com.zyos.alert.studentReport.model.ReportType;
import com.zyos.alert.studentReport.model.RiskFactor;
import com.zyos.alert.studentReport.model.RiskFactorCategory;
import com.zyos.alert.studentReport.model.Stage;
import com.zyos.alert.studentReport.model.StatusReportStudent;
import com.zyos.alert.studentReport.model.Student;
import com.zyos.core.common.api.IZyosGroup;
import com.zyos.core.common.controller.ErrorNotificacion;
import com.zyos.core.common.controller.ZyosBackingBean;
import com.zyos.core.common.util.ManageDate;
import com.zyos.core.common.util.excel.ZyosExcel;
import com.zyos.core.lo.user.model.ZyosGroup;
import com.zyos.core.lo.user.model.ZyosUser;
import com.zyos.core.mail.io.mn.api.IEmailTemplate;
import com.zyos.core.mail.io.mn.aws.MailGeneratorFunction;
import com.zyos.core.mail.io.mn.aws.SMTPEmail;
import com.zyos.core.mail.io.mn.model.EmailTemplate;

@ManagedBean
@ViewScoped
@URLMapping(id = "interventionBean", pattern = "/portal/RegistroIntervenciones", viewId = "/pages/interventions/index.jspx")
public class InterventionBean extends ZyosBackingBean {

	/**
	 * SIAT TUNJA
	 */
	private static final long serialVersionUID = 1L;

	private Student studentDataSelected;
	private ReportStudent reportStudentSelected;
	private ReportStudent reportStudentData;
	private ReportStudent reportSearch;
	private Stage stageSelected;
	private String chartCategoryReport = "";
	private String chartSeriesReport = "";
	private String headerDialog = "";
	private Long graphicToShow;
	private Long totalStudent;
	private Long idStageSelected;
	private Long idRiskFactorCategorySelected;
	private Long idRiskFactorCategoryDegree;
	private Long idDegreeSelected;
	private Long idFacultySelected;
	private Long idRiskFactorCategoryFaculty;
	private Long totalStudentReport;
	private Date dateFrom;
	private Date dateTo;

	private List<Degree> degreeStudentList;
	private List<Faculty> facultyStudentList;
	private List<ReportStudent> reportStudentRiskList;
	private List<ZyosUser> responsibleList;
	private List<Observation> observationList;
	private List<ReportStudent> reportStudentList;
	private List<ReportStudent> reportStudentListFiltered;
	private List<RiskFactor> reporStudentRiskFactorList;
	private List<Degree> degreeList;
	private List<Faculty> reportFacultyStudentList;
	private List<RiskFactorCategory> reportRiskFactorCategoryList;
	private List<ZyosGroup> reportZyosGroupList;
	private List<StatusReportStudent> reportStatusList;
	private List<ReportType> reportStudentReportType;

	private boolean detailRiskFactor = false;
	private boolean detailRiskDegree = false;
	private boolean detailRiskFaculty = false;
	private boolean detailRiskFactorDegree = false;
	private boolean detailRiskFactorFaculty = false;
	private boolean showButtonGiveProcess = false;

	private boolean showGraphic, showReport, showRisk, showActiveStudent,
			showManual, showRiskDegree, showRiskFaculty;

	private boolean showStudentList, showStudentSelected, showProcess;
	private int disableData = 0;
	private StreamedContent errorFile;
	private StreamedContent errorFileReportStudent;

	private InterventionController controller = new InterventionController();
	
	private List<ZyosUser> TeacherListByFaculty;

	public InterventionBean() throws Exception {
		try {
			this.getUserSession().getDefaultEnterprise();
			this.getUserSession().getId();
			
			
			
			if (reportStudentList == null) {
				reportSearch = new ReportStudent();
				this.studentDataSelected = new Student();
				this.degreeList = controller.loadDegreeList();
				//loadReportStudentList();
				loadReportStudentListTunja();
				loadReportSearchList();
				// validateStagePermission();
			}
			showStudentList = true;

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}
	
	/**SIAT TUNJA*/
	public void goTeacherAsign() {
		try {
			//studentDataSelected = controller.loadDataStudentReport(reportStudentSelected);
			TeacherListByFaculty = controller.loadDataTeacherByFaculty(getUserSession().getId());//loadDataStudentReport(reportStudentSelected);
			
			headerDialog = "Asignar Docente";
			ZyosBackingBean.update("riskStudentForm:asignTeacherCase");
			ZyosBackingBean.getRequestContext().execute("asignTeacherCaseWV.show();");

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}
	
	/**SIAT TUNJA*/
	public void asignTeacherCaseStudent(){
		
	}

	/** jhernandez */
	public void loadReportSearchList() {
		try {

			reportFacultyStudentList = controller
					.loadReportFacultyStudentList();
			reportRiskFactorCategoryList = controller
					.loadReportRiskFactorCategoryList();
			reportZyosGroupList = controller.loadReportZyosGroupList();
			reportStatusList = controller.loadReportStatusList();
			reportStudentReportType = controller.loadReporStudentReportType();

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	/** jhernandez */
	public void loadRiskFactorReportStudent() {
		try {
			if (reportSearch.getIdRiskFactorCategory() != null
					&& !reportSearch.getIdRiskFactorCategory().equals(0L)) {
				reporStudentRiskFactorList = controller
						.loadReporStudentRiskFactorList(reportSearch
								.getIdRiskFactorCategory());
			}

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	/** jhernandez */
	public void loadReportStudentList() {
		try {
			reportStudentList = controller.loadReportStudentList(
					getUserSession().getDefaultGroup(), getUserSession()
							.getId(), reportSearch);
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}
	
	public void loadReportStudentListTunja() {
		try {
			reportStudentList = controller.loadReportStudentListTunja(getUserSession().getDefaultGroup(), getUserSession().getId(), reportSearch);
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	/** jhernandez */
	public void searchReportStudentList() {
		try {
			if (reportSearch != null) {
				if (dateFrom != null) {
					reportSearch.setDateFrom(ManageDate.formatDate(dateFrom,
							ManageDate.YYYY_MM_DD_HH_MM_SS));
				}
				if (dateTo != null) {
					reportSearch.setDateTo(ManageDate.formatDate(dateTo,
							ManageDate.YYYY_MM_DD_HH_MM_SS));
				}
				dateFrom = null;
				dateTo = null;
				if (validateSearch()) {
					reportStudentList = controller.loadSearchReportStudentList(
							getUserSession().getDefaultGroup(),
							getUserSession().getId(), reportSearch);

					if (reportStudentList == null
							|| reportStudentList.size() == 0) {
						addWarn("Filtrar Casos ",
								"No se encontrarón resultados con los criterios de búsqueda.");
					}
				} else {
					addWarn("Filtrar Casos ",
							"No ha ingresado ningun criterio de busqueda.");
				}
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public boolean validateSearch() {
		try {
			if (reportSearch != null) {
				if (reportSearch.getDateFrom() == null
						&& reportSearch.getDateTo() == null
						&& reportSearch.getIdSolicitor().equals(0L)
						&& reportSearch.getIdStatusReportStudent().equals(0L)
						&& reportSearch.getIdFaculty().equals(0L)
						&& reportSearch.getIdReportType().equals(0L)
						&& reportSearch.getIdRiskFactorCategory().equals(0L)
						&& (reportSearch.getCode() == null || reportSearch
								.getCode().isEmpty())) {
					return false;
				}
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
			return false;
		}
	}

	public void generateReportStudentList() {
		try {

			if (reportStudentList != null && reportStudentList.size() > 0) {

				ZyosExcel ze = new ZyosExcel();

				XSSFWorkbook wb = new XSSFWorkbook();
				Sheet sheet = wb.createSheet();

				CellStyle headerStyle = wb.createCellStyle();
				headerStyle.setFillForegroundColor(IndexedColors.YELLOW
						.getIndex());
				headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

				int i = 0;
				// header

				Row r = sheet.createRow(i);
				Cell cell = r.createCell(0);
				cell.setCellStyle(headerStyle);
				cell.setCellValue("Primer Apellido");

				cell = r.createCell(1);
				cell.setCellStyle(headerStyle);
				cell.setCellValue("Segundo Apellido");

				cell = r.createCell(2);
				cell.setCellStyle(headerStyle);
				cell.setCellValue("Nombres");

				cell = r.createCell(3);
				cell.setCellStyle(headerStyle);
				cell.setCellValue("Código");

				cell = r.createCell(4);
				cell.setCellStyle(headerStyle);
				cell.setCellValue("Identificación");

				cell = r.createCell(5);
				cell.setCellStyle(headerStyle);
				cell.setCellValue("Teléfono");

				cell = r.createCell(6);
				cell.setCellStyle(headerStyle);
				cell.setCellValue("Celular");

				cell = r.createCell(7);
				cell.setCellStyle(headerStyle);
				cell.setCellValue("Correo");

				cell = r.createCell(8);
				cell.setCellStyle(headerStyle);
				cell.setCellValue("Tipo de Solicitud");

				cell = r.createCell(9);
				cell.setCellStyle(headerStyle);
				cell.setCellValue("Fecha");

				cell = r.createCell(10);
				cell.setCellStyle(headerStyle);
				cell.setCellValue("Categoria de Riesgo ");

				cell = r.createCell(11);
				cell.setCellStyle(headerStyle);
				cell.setCellValue("Factor de Riesgo");

				cell = r.createCell(12);
				cell.setCellStyle(headerStyle);
				cell.setCellValue("Solicitado por");

				cell = r.createCell(13);
				cell.setCellStyle(headerStyle);
				cell.setCellValue("Estado");

				cell = r.createCell(14);
				cell.setCellStyle(headerStyle);
				cell.setCellValue("Observación");

				i++;

				for (ReportStudent c : reportStudentList) {
					r = sheet.createRow(i);
					String[] lastNameSplit = null;
					String secondLastName = null;

					for (int k = 0; k <= 14; k++) {

						cell = r.createCell(k);

						if (k == 0) { // firtsLastName

							if (c.getZyosUserLastName() != null) {
								lastNameSplit = c.getZyosUserLastName().split(
										" ");
								cell.setCellValue(lastNameSplit[0]);
							} else {
								cell.setCellValue("");
							}
						}
						if (k == 1) { // secondLasName

							if (c.getZyosUserLastName() != null) {

								if (lastNameSplit.length > 1) {

									for (int j = 1; j < lastNameSplit.length; j++) {
										secondLastName = lastNameSplit[j] + " ";
									}
									cell.setCellValue(secondLastName);

								} else {
									cell.setCellValue(" ");
								}
							} else {
								cell.setCellValue(" ");
							}

						}
						if (k == 2) { // name

							if (c.getZyosUserName() != null
									&& c.getZyosUserLastName() != null) {
								cell.setCellValue(c.getZyosUserName() + " "
										+ c.getZyosUserLastName());
							} else {
								if (c.getZyosUserName() != null
										&& c.getZyosUserLastName() == null) {
									cell.setCellValue(c.getZyosUserName());
								}

								else {
									if (c.getZyosUserName() == null
											&& c.getZyosUserLastName() != null) {
										cell.setCellValue(c
												.getZyosUserLastName());
									}
								}
							}

						}
						if (k == 3) {

							cell.setCellValue(c.getCode() != null ? c.getCode()
									: " ");

						}
						if (k == 4) {

							cell.setCellValue(c.getDocumentNumber() != null ? c
									.getDocumentNumber() : " ");
						}
						if (k == 5) {
							cell.setCellValue(c.getPhone() != null ? c
									.getPhone() : " ");
						}
						if (k == 6) {
							cell.setCellValue(c.getMobilePhone() != null ? c
									.getMobilePhone() : " ");
						}
						if (k == 7) {
							cell.setCellValue(c.getEmailStudent() != null ? c
									.getEmailStudent() : " ");
						}
						if (k == 8) {

							cell.setCellValue(c.getIdReportType() != null
									&& !c.getIdReportType().equals(0L) ? getNameLabelList(
									getReportStudentReportType(),
									c.getIdReportType()) : " ");
						}
						if (k == 9) {
							cell.setCellValue(c.getDateCreation() != null ? c
									.getDateCreation() : " ");
						}
						if (k == 10) {
							cell.setCellValue(c.getIdReportType() != null
									&& !c.getIdRiskFactorCategory().equals(0L) ? getNameLabelList(
									getReportRiskFactorCategoryList(),
									c.getIdRiskFactorCategory()) : " ");
						}
						if (k == 11) {
							cell.setCellValue(c.getRiskFactorName() != null ? c
									.getRiskFactorName() : " ");
						}
						if (k == 12) {

							String nameSolicitor = null;

							if (c.getIdReportType().equals(
									IReportType.AUTOMATIC)) {
								nameSolicitor = "Reporte Automatico";
							} else {

								if (c.getZyosGroupName() == null) {
									nameSolicitor = "Reporte Externo";
								} else {
									nameSolicitor = c.getZyosGroupName();
								}
							}
							cell.setCellValue(nameSolicitor);
						}

						if (k == 13) {

							cell.setCellValue(!c.getIdStatusReportStudent()
									.equals(null)
									&& !c.getIdStatusReportStudent().equals(0L) ? getNameLabelList(
									getReportStatusList(),
									c.getIdStatusReportStudent()) : " ");
						}

						if (k == 14) {

							cell.setCellValue(c.getDetailReport());
						}
					}

					i++;
				}

				wb.getSheetAt(0).autoSizeColumn(0);
				wb.getSheetAt(0).autoSizeColumn(1);
				wb.getSheetAt(0).autoSizeColumn(2);
				wb.getSheetAt(0).autoSizeColumn(3);
				wb.getSheetAt(0).autoSizeColumn(4);
				wb.getSheetAt(0).autoSizeColumn(5);
				wb.getSheetAt(0).autoSizeColumn(6);
				wb.getSheetAt(0).autoSizeColumn(7);
				wb.getSheetAt(0).autoSizeColumn(8);
				wb.getSheetAt(0).autoSizeColumn(9);
				wb.getSheetAt(0).autoSizeColumn(10);
				wb.getSheetAt(0).autoSizeColumn(11);
				wb.getSheetAt(0).autoSizeColumn(12);
				wb.getSheetAt(0).autoSizeColumn(13);
				wb.getSheetAt(0).autoSizeColumn(14);

				File file = new File(".");
				System.out.println(file.getAbsolutePath());
				ze.closeFile(wb, file.getAbsolutePath()
						+ "/reporteEstudiantes.xlsx");
			} else {
				ZyosBackingBean
						.addWarn("Generar reporte",
								" No cuenta con estudiantes reportados en el listado para generar reporte");

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void handleDataExporter(Object o) {
		try {
			Workbook wb = (Workbook) o;
			wb.setSheetName(0, "Reporte Observaciones");
			CellStyle cs = wb.createCellStyle();
			cs.setBorderBottom(BorderFormatting.BORDER_THIN);
			cs.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			cs.setBorderTop(BorderFormatting.BORDER_THIN);
			cs.setTopBorderColor(IndexedColors.BLACK.getIndex());
			cs.setBorderLeft(BorderFormatting.BORDER_THIN);
			cs.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			cs.setBorderRight(BorderFormatting.BORDER_THIN);
			cs.setRightBorderColor(IndexedColors.BLACK.getIndex());
			cs.setBorderRight(BorderFormatting.BORDER_THIN);
			cs.setRightBorderColor(IndexedColors.BLACK.getIndex());

			CellStyle hs = wb.createCellStyle();
			hs.cloneStyleFrom(cs);
			hs.setFillForegroundColor(IndexedColors.AQUA.getIndex());
			hs.setFillPattern(CellStyle.SOLID_FOREGROUND);

			List<String> labelTableList = new ArrayList<String>(5);
			labelTableList.add(ZyosBackingBean
					.getMessage("page.riskStudentBean.labelDate"));
			labelTableList.add(ZyosBackingBean
					.getMessage("page.riskStudentBean.labelDays"));
			labelTableList.add(ZyosBackingBean
					.getMessage("page.riskStudentBean.labelRole"));
			labelTableList.add(ZyosBackingBean
					.getMessage("page.riskStudentBean.labelResponsible"));
			labelTableList.add(ZyosBackingBean
					.getMessage("page.riskStudentBean.labelObservation"));

			for (Row r : wb.getSheetAt(0)) {
				int i = 0;
				for (Cell c : r) {
					if (c.getRowIndex() == 0) {
						{
							c.setCellValue(labelTableList.get(i));
							c.setCellStyle(hs);
							i++;
						}
					} else
						c.setCellStyle(cs);
				}
			}
			wb.getSheetAt(0).autoSizeColumn(0);
			wb.getSheetAt(0).autoSizeColumn(1);
			wb.getSheetAt(0).autoSizeColumn(2);
			wb.getSheetAt(0).autoSizeColumn(3);
			wb.getSheetAt(0).autoSizeColumn(4);
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	/** @autor jhernandez */
	public void detailRiskFactorCategory() {
		try {
			if (idRiskFactorCategorySelected.equals(0L)) {
				detailRiskFactor = false;
				graphicToShow = 2L;
				graphData();
			} else {
				detailRiskFactor = true;
				graphicToShow = 2L;
				graphData();
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	/** jhernandez */
	/*
	 * public void validateStagePermission() { try { for (Stage s :
	 * getStagePermissionList()) { if
	 * (getUserSession().getDefaultGroup().equals(s.getIdStage())) {
	 * showButtonGiveProcess = true; } } } catch (Exception e) {
	 * ErrorNotificacion.handleErrorMailNotification(e, this); } }
	 */

	/** @autor jhernandez */
	public void detailDegreeReport() {
		try {
			if (idDegreeSelected.equals(0L)) {
				detailRiskDegree = true;
				graphicToShow = 4L;
				graphData();
			} else {
				detailRiskFactor = true;
				graphDataDetailDegree();
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public void cleanSearch() {
		loadReportStudentList();
		reportSearch = new ReportStudent();
		dateFrom = null;
		dateTo = null;
	}

	/** @author jhernandez */
	public void graphData() {
		try {
			JSONArray series = new JSONArray();
			chartCategoryReport = "";
			chartSeriesReport = "";
			totalStudentReport = 0L;
			totalStudentReport = controller.loadCountReportStudent();
			switch (graphicToShow.intValue()) {
			case 0:
				// Pie Graphic
				Object[] obj = controller.loadReportStudentData();
				chartSeriesReport = "[['Manualmente'," + obj[0]
						+ "],['Automaticamente'," + obj[1] + "]]";
				getRequest().setAttribute("generalReport", chartSeriesReport);
				cleanView();
				showReport = true;
				showGraphic = true;
				break;
			case 1:
				// Bar Graphic
				chartCategoryReport = "Compañero~Usuario externo~Docente~Por si mismo";
				List<BigDecimal> resultList = new ArrayList<BigDecimal>(3);

				resultList.add(0,
						controller.loadReportStudentManualDataClassMate());
				resultList.add(1,
						controller.loadReportStudentManualDataFreeUser());
				resultList.add(2,
						controller.loadReportStudentManualDataTeacher());
				resultList.add(3, controller.loadReportStudentManualDataSelf());

				// object[0] = objj;

				String[] tmpCategory = chartCategoryReport.split("~");
				for (int i = 0; i < resultList.size(); i++) {
					ArrayList<BigDecimal> list = new ArrayList<BigDecimal>();
					JSONObject seriePrimera = new JSONObject();
					list.add(resultList.get(i));
					seriePrimera.put("type", "column");
					seriePrimera.put("name", tmpCategory[i]);
					seriePrimera.put("data", list);
					series.add(seriePrimera);
				}
				chartCategoryReport = "Estudiantes reportados";
				chartSeriesReport = series.toJSONString();
				getRequest().setAttribute("manualReport", chartSeriesReport);
				cleanView();
				showManual = true;
				showGraphic = true;
				break;
			case 2:
				if (detailRiskFactor) {

					chartSeriesReport = "[";
					series = new JSONArray();
					HashMap<String, Float> datailPieData = controller
							.loadDetailRiskFactor(idRiskFactorCategorySelected);
					int count = 0;
					for (String key : datailPieData.keySet()) {
						chartSeriesReport += "['" + key + "',"
								+ datailPieData.get(key) + "]";
						count++;
						chartSeriesReport += datailPieData.size() == count ? ""
								: ",";
					}
					chartSeriesReport += "]";
					getRequest().setAttribute("riskData", chartSeriesReport);
					cleanView();
					showRisk = true;
					showGraphic = true;

				} else {
					// Pie Graphic
					// chartSeriesReport = "[";
					series = new JSONArray();
					List<GraphicData> tmpPieData = controller
							.loadStudentByRiskData();

					chartSeriesReport = "[['Academico',"
							+ tmpPieData.get(0).getAcademicReports()
							+ "],['Socioeconomico',"
							+ tmpPieData.get(0).getSocioeconomicReports() + "]"
							+ " ,['Institucionales',"
							+ tmpPieData.get(0).getInstitutionalReports() + "]"
							+ ",['Personales',"
							+ tmpPieData.get(0).getPersonalReports() + "]]";
					// getRequest().setAttribute("generalReport",
					// chartSeriesReport);

					getRequest().setAttribute("riskData", chartSeriesReport);
					cleanView();
					showRisk = true;
					showGraphic = true;
				}
				break;
			case 3:
				String currentDate = ManageDate
						.getCurrentDate(ManageDate.YYYY_MM_DD);
				String currentHour = ManageDate
						.getCurrentHour(ManageDate.HH_MM);
				Long currentDay = Long.valueOf(getDayOfTheWeek(Calendar
						.getInstance().getTime()));
				totalStudent = controller.loadTotalStudent(currentDate,
						currentHour, currentDay);
				cleanView();
				showActiveStudent = true;
				break;
			case 4:
				if (detailRiskFactorDegree) {
					chartSeriesReport = "[";
					series = new JSONArray();
					HashMap<String, Float> studentReportDegree = controller
							.loadDetailStudentReportDegree(idRiskFactorCategoryDegree);
					degreeStudentList = controller
							.loadStudentReportDetailDegree(idRiskFactorCategoryDegree);
					detailRiskDegree = true;
					int count = 0;
					for (String key : studentReportDegree.keySet()) {
						chartSeriesReport += "['" + key + "',"
								+ studentReportDegree.get(key) + "]";
						count++;
						chartSeriesReport += studentReportDegree.size() == count ? ""
								: ",";
					}
					chartSeriesReport += "]";
					getRequest().setAttribute("riskDataDegree",
							chartSeriesReport);
					cleanView();
					showRiskDegree = true;
					showGraphic = true;

				} else {
					chartSeriesReport = "[";
					series = new JSONArray();
					HashMap<String, Float> studentReportDegree = controller
							.loadStudentReportDegree();
					int count = 0;
					for (String key : studentReportDegree.keySet()) {
						chartSeriesReport += "['" + key + "',"
								+ studentReportDegree.get(key) + "]";
						count++;
						chartSeriesReport += studentReportDegree.size() == count ? ""
								: ",";
					}
					chartSeriesReport += "]";
					getRequest().setAttribute("riskDataDegree",
							chartSeriesReport);
					cleanView();
					showRiskDegree = true;
					showGraphic = true;
				}
				break;
			case 5:

				if (detailRiskFactorFaculty) {
					chartSeriesReport = "[";
					series = new JSONArray();
					HashMap<String, Float> studentReportFaculty = controller
							.loadDetailStudentReportFaculty(idRiskFactorCategoryFaculty);
					facultyStudentList = controller
							.loadStudentReportDetailFaculty(idRiskFactorCategoryFaculty);

					int count = 0;
					for (String key : studentReportFaculty.keySet()) {
						chartSeriesReport += "['" + key + "',"
								+ studentReportFaculty.get(key) + "]";
						count++;
						chartSeriesReport += studentReportFaculty.size() == count ? ""
								: ",";
					}
					chartSeriesReport += "]";
					getRequest().setAttribute("riskDataFaculty",
							chartSeriesReport);
					cleanView();
					showRiskFaculty = true;
					showGraphic = true;

				} else {

					chartSeriesReport = "[";
					series = new JSONArray();
					HashMap<String, Float> studentReportFaculty = controller
							.loadDetailStudentReportFaculty();
					int count = 0;
					for (String key : studentReportFaculty.keySet()) {
						chartSeriesReport += "['" + key + "',"
								+ studentReportFaculty.get(key) + "]";
						count++;
						chartSeriesReport += studentReportFaculty.size() == count ? ""
								: ",";
					}
					chartSeriesReport += "]";
					getRequest().setAttribute("riskDataFaculty",
							chartSeriesReport);
					cleanView();
					showRiskFaculty = true;
					showGraphic = true;
				}
				break;
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public void graphDataDetailDegree() {
		try {
			JSONArray series = new JSONArray();
			chartCategoryReport = "";
			chartSeriesReport = "";

			chartSeriesReport = "[";
			series = new JSONArray();
			HashMap<String, Float> studentReportDegree = controller
					.loadDetailDegreeList(idRiskFactorCategoryDegree,
							idDegreeSelected);
			int count = 0;
			for (String key : studentReportDegree.keySet()) {
				chartSeriesReport += "['" + key + "',"
						+ studentReportDegree.get(key) + "]";
				count++;
				chartSeriesReport += studentReportDegree.size() == count ? ""
						: ",";
			}
			chartSeriesReport += "]";
			getRequest().setAttribute("riskDataDegree", chartSeriesReport);

			cleanView();
			showRiskDegree = true;
			showGraphic = true;
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public void graphDataDetailFaculty() {
		try {
			JSONArray series = new JSONArray();
			chartCategoryReport = "";
			chartSeriesReport = "";
			chartSeriesReport = "[";
			series = new JSONArray();
			HashMap<String, Float> studentReportFaculty = controller
					.loadDetailFacultyList(idRiskFactorCategoryFaculty,
							idFacultySelected);
			int count = 0;
			for (String key : studentReportFaculty.keySet()) {
				chartSeriesReport += "['" + key + "',"
						+ studentReportFaculty.get(key) + "]";
				count++;
				chartSeriesReport += studentReportFaculty.size() == count ? ""
						: ",";
			}
			chartSeriesReport += "]";
			getRequest().setAttribute("riskDataFaculty", chartSeriesReport);

			cleanView();
			showRiskFaculty = true;
			showGraphic = true;

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public static int getDayOfTheWeek(Date d) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(d);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/** @autor jhernandez */
	public void detailFacultyReport() {
		try {
			if (idFacultySelected.equals(0L)) {
				detailRiskFaculty = true;
				graphicToShow = 5L;
				graphData();
			} else {
				detailRiskFactor = true;
				graphDataDetailFaculty();
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	private void cleanView() {
		showGraphic = false;
		showReport = false;
		showRisk = false;
		showRiskDegree = false;
		showRiskFaculty = false;
		showManual = false;
		showActiveStudent = false;
	}

	/** @autor jhernandez */
	public void detailRiskFactorDegree() {
		try {
			if (idRiskFactorCategoryDegree.equals(0L)) {
				detailRiskFactorDegree = false;
				detailRiskDegree = false;
				idDegreeSelected = 0L;
				graphicToShow = 4L;
				graphData();

			} else {
				detailRiskFactorDegree = true;
				detailRiskDegree = true;
				graphicToShow = 4L;
				idDegreeSelected = 0L;
				graphData();
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	/** @autor jhernandez */
	public void detailRiskFactorFaculty() {
		try {
			if (idRiskFactorCategoryFaculty.equals(0L)) {
				detailRiskFactorFaculty = false;
				detailRiskFaculty = false;
				graphicToShow = 5L;
				idFacultySelected = 0L;
				graphData();

			} else {
				detailRiskFactorFaculty = true;
				detailRiskFaculty = true;
				graphicToShow = 5L;
				idFacultySelected = 0L;
				graphData();
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}

	}

	public void goGiveProcess() {
		try {
			reportStudentData = new ReportStudent();
			cleanViewRisk();
			showStudentSelected = true;
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}

	}

	/** jhernandez */
	public void goDeleteReportStudentDialog() {
		try {
			ZyosBackingBean.getRequestContext().execute(
					"delReportStudentWV.show();");
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}

	}

	/** @autor jhernandez */
	public void deleteReportStudent() {
		try {
			if (reportStudentSelected == null) {

				ZyosBackingBean.addWarn("Eliminar reporte",
						" Error al intentar eliminar reporte seleccionado");

			} else {

				controller.deleteReportStudent(reportStudentSelected,
						getUserSession().getDocumentNumber());

				ZyosBackingBean.addInfo("Eliminar Reporte",
						"Reporte eliminado con éxito!");

				reportStudentList.remove(reportStudentSelected);
				ZyosBackingBean.getRequestContext().execute(
						"delReportStudentWV.hide();");
				update("riskStudentForm:reportStudentDataTable");

			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}	

	/** @autor jhernandez */
	public void goDetailStudentReport() {
		try {
			studentDataSelected = controller
					.loadDataStudentReport(reportStudentSelected);
			disableData = 1;
			headerDialog = "Datos Estudiante";
			ZyosBackingBean.update("riskStudentForm:updateDataStudentDialog");
			ZyosBackingBean.getRequestContext().execute(
					"updateDataStudentDialog.show();");

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public void loadResponsibleListByRole() {
		try {
			if (reportStudentSelected.getIdStage() != null) {
				responsibleList = controller
						.loadResponsibleListByRole(reportStudentSelected
								.getIdStage());
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	/** @author MTorres 19/06/2014 14:21:17 */
	public void goReportStudentList() {
		try {
			showStudentList = true;
			setPanelView("riskStudentList", "Lista Factores Riesgo",
					"ReportStudentBean");
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	/** @author oortiz */
	public void giveProcessToReportStudent() {
		try {

			Observation observation = new Observation();
			observation.setDateIntervention(ManageDate
					.getCurrentDate(ManageDate.YYYY_MM_DD));
			observation.setDetailObservation(reportStudentData
					.getDetailReport());
			observation.setIdReportStudent(reportStudentSelected
					.getIdReportStudent());
			observation.setIdStatusReportStudent(reportStudentSelected
					.getIdStatusReportStudent());
			observation.setIdAdviser(getUserSession().getId());
			observation.setIdStage(getUserSession().getDefaultGroup());

			reportStudentSelected.setDetailReport(reportStudentData
					.getDetailReport());
			controller.saveObservation(observation, getUserSession()
					.getDocumentNumber());
			controller.updateReportStudent(reportStudentSelected,
					getUserSession().getDocumentNumber());

			cleanViewRisk();
			loadReportStudentList();
			showStudentList = true;
			addInfo("Dar trámite",
					"Se ha creado una nueva observación al proceso");
			if (reportStudentSelected.getIdSolicitor() == null
					&& getUserSession().getDefaultGroup().equals(
							IZyosGroup.ADMINISTRATOR)
					&& reportStudentSelected.getIdStatusReportStudent().equals(
							IStatusReportStudent.START_INTERVENTION)) {
				threadMailToFamily();
			}
			/*
			 * if (this.reportStudentSelected.getIdAdviser() != null &&
			 * !this.reportStudentSelected.getIdAdviser().equals(0L) &&
			 * this.reportStudentSelected.getIdStatusReportStudent
			 * ().equals(IStatusReportStudent.START_INTERVENTION))
			 * threadMailAdviserNotification();
			 */

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	/** @author MTorres 19/06/2014 16:08:20 */
	private void threadMailAdviserNotification() throws Exception {
		try {
			final ReportStudent rs = this.reportStudentSelected;
			final ZyosUser adviser = this.controller.loadAdvisorById(rs
					.getIdAdviser());

			new Thread(new Runnable() {
				public void run() {
					try {
						sendEmailAdviserNotification(rs, adviser);
					} catch (Exception e) {
						ErrorNotificacion.handleErrorMailNotification(e, this);
					}
				}
			}).start();
		} catch (Exception e) {
			throw e;
		}
	}

	/** @author MTorres 19/06/2014 16:08:20 */
	private void sendEmailAdviserNotification(final ReportStudent rs,
			final ZyosUser a) throws Exception {
		try {
			EmailTemplate t = MailGeneratorFunction.getEmailTemplate(
					IEmailTemplate.ADVISER_NOTIFICATION, Long.valueOf(1));
			SMTPEmail e = new SMTPEmail();
			e.sendProcessEmail(
					null,
					t.getSubject(),
					MailGeneratorFunction.createGenericMessage(
							t.getBody(),
							t.getAnalyticsCode(),
							a.getName() + " " + a.getLastName(),
							rs.getZyosUserName() + " "
									+ rs.getZyosUserLastName()), a.getEmail());
		} catch (Exception e) {
			throw e;
		}
	}

	/** @author MTorres 19/06/2014 15:24:47 */
	private void threadMailToFamily() throws Exception {
		try {
			final ReportStudent rs = this.reportStudentSelected;
			new Thread(new Runnable() {
				public void run() {
					try {
						sendEmailToFamilyStudent(rs);
					} catch (Exception e) {
						ErrorNotificacion.handleErrorMailNotification(e, this);
					}
				}
			}).start();

		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * @author jhernandez edit MTorres
	 * */
	public void sendEmailToFamilyStudent(final ReportStudent rs) {
		try {
			EmailTemplate t = MailGeneratorFunction.getEmailTemplate(
					IEmailTemplate.REGISTER_REPORTSTUDENT, Long.valueOf(1));
			if (t != null) {
				SMTPEmail e = new SMTPEmail();
				e.sendProcessEmail(null, t.getSubject(), MailGeneratorFunction
						.createGenericMessage(t.getBody(),
								t.getAnalyticsCode(), rs.getZyosUserName(),
								rs.getZyosUserLastName()), rs
						.getEmailSolicitor());
			} else {
				ZyosBackingBean
						.addWarn("Alerta",
								"Error al enviar correo eléctronico al usuario externo. ");
			}
		} catch (Exception e) {

			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public void goProcess() {
		try {
			showProcess = false;
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public void goHistoricalProcess() {
		try {
			showProcess = true;
			observationList = controller
					.loadObservationByStudent(reportStudentSelected
							.getIdReportStudent());
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public void closeProcess() {
		try {
			controller.closeProcess(reportStudentSelected.getIdReportStudent());
			redirectURL("portal/estudiantesRiesgo");
			addInfo("Cerrar proceso", "El proceso se a cerrado correctamente.");
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	private void cleanViewRisk() {
		showStudentList = false;
		showStudentSelected = false;
	}

	public void redirectToReportStudent() {
		try {
			redirectURL("portal/registroEstudiante");
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public void goBack() {
		try {
			redirectURL("portal/estudiantesRiesgo");
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	public void goStudentRegister() {
		loadResponsibleListByRole();
		reportStudentData = new ReportStudent();
		cleanViewRisk();
		showStudentSelected = true;
	}

	public Student getStudentDataSelected() {
		return studentDataSelected;
	}

	public void setStudentDataSelected(Student studentDataSelected) {
		this.studentDataSelected = studentDataSelected;
	}

	public List<Degree> getDegreeList() {
		return degreeList;
	}

	public void setDegreeList(List<Degree> degreeList) {
		this.degreeList = degreeList;
	}

	public List<Faculty> getReportFacultyStudentList() {
		return reportFacultyStudentList;
	}

	public void setReportFacultyStudentList(
			List<Faculty> reportFacultyStudentList) {
		this.reportFacultyStudentList = reportFacultyStudentList;
	}

	public List<RiskFactorCategory> getReportRiskFactorCategoryList() {
		return reportRiskFactorCategoryList;
	}

	public void setReportRiskFactorCategoryList(
			List<RiskFactorCategory> reportRiskFactorCategoryList) {
		this.reportRiskFactorCategoryList = reportRiskFactorCategoryList;
	}

	public List<ZyosGroup> getReportZyosGroupList() {
		return reportZyosGroupList;
	}

	public void setReportZyosGroupList(List<ZyosGroup> reportZyosGroupList) {
		this.reportZyosGroupList = reportZyosGroupList;
	}

	public List<StatusReportStudent> getReportStatusList() {
		return reportStatusList;
	}

	public void setReportStatusList(List<StatusReportStudent> reportStatusList) {
		this.reportStatusList = reportStatusList;
	}

	public List<ReportType> getReportStudentReportType() {
		return reportStudentReportType;
	}

	public void setReportStudentReportType(
			List<ReportType> reportStudentReportType) {
		this.reportStudentReportType = reportStudentReportType;
	}

	public boolean isShowStudentList() {
		return showStudentList;
	}

	public void setShowStudentList(boolean showStudentList) {
		this.showStudentList = showStudentList;
	}

	public boolean isShowStudentSelected() {
		return showStudentSelected;
	}

	public void setShowStudentSelected(boolean showStudentSelected) {
		this.showStudentSelected = showStudentSelected;
	}

	public boolean isShowProcess() {
		return showProcess;
	}

	public void setShowProcess(boolean showProcess) {
		this.showProcess = showProcess;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public ReportStudent getReportSearch() {
		return reportSearch;
	}

	public void setReportSearch(ReportStudent reportSearch) {
		this.reportSearch = reportSearch;
	}

	public List<RiskFactor> getReporStudentRiskFactorList() {
		return reporStudentRiskFactorList;
	}

	public void setReporStudentRiskFactorList(
			List<RiskFactor> reporStudentRiskFactorList) {
		this.reporStudentRiskFactorList = reporStudentRiskFactorList;
	}

	public List<ReportStudent> getReportStudentList() {
		return reportStudentList;
	}

	public void setReportStudentList(List<ReportStudent> reportStudentList) {
		this.reportStudentList = reportStudentList;
	}

	public List<ReportStudent> getReportStudentListFiltered() {
		return reportStudentListFiltered;
	}

	public void setReportStudentListFiltered(
			List<ReportStudent> reportStudentListFiltered) {
		this.reportStudentListFiltered = reportStudentListFiltered;
	}

	public ReportStudent getReportStudentSelected() {
		return reportStudentSelected;
	}

	public void setReportStudentSelected(ReportStudent reportStudentSelected) {
		this.reportStudentSelected = reportStudentSelected;
	}

	public ReportStudent getReportStudentData() {
		return reportStudentData;
	}

	public void setReportStudentData(ReportStudent reportStudentData) {
		this.reportStudentData = reportStudentData;
	}

	public Stage getStageSelected() {
		return stageSelected;
	}

	public void setStageSelected(Stage stageSelected) {
		this.stageSelected = stageSelected;
	}

	public String getChartCategoryReport() {
		return chartCategoryReport;
	}

	public void setChartCategoryReport(String chartCategoryReport) {
		this.chartCategoryReport = chartCategoryReport;
	}

	public String getChartSeriesReport() {
		return chartSeriesReport;
	}

	public void setChartSeriesReport(String chartSeriesReport) {
		this.chartSeriesReport = chartSeriesReport;
	}

	public String getHeaderDialog() {
		return headerDialog;
	}

	public void setHeaderDialog(String headerDialog) {
		this.headerDialog = headerDialog;
	}

	public Long getGraphicToShow() {
		return graphicToShow;
	}

	public void setGraphicToShow(Long graphicToShow) {
		this.graphicToShow = graphicToShow;
	}

	public Long getTotalStudent() {
		return totalStudent;
	}

	public void setTotalStudent(Long totalStudent) {
		this.totalStudent = totalStudent;
	}

	public Long getIdStageSelected() {
		return idStageSelected;
	}

	public void setIdStageSelected(Long idStageSelected) {
		this.idStageSelected = idStageSelected;
	}

	public Long getIdRiskFactorCategorySelected() {
		return idRiskFactorCategorySelected;
	}

	public void setIdRiskFactorCategorySelected(
			Long idRiskFactorCategorySelected) {
		this.idRiskFactorCategorySelected = idRiskFactorCategorySelected;
	}

	public Long getIdRiskFactorCategoryDegree() {
		return idRiskFactorCategoryDegree;
	}

	public void setIdRiskFactorCategoryDegree(Long idRiskFactorCategoryDegree) {
		this.idRiskFactorCategoryDegree = idRiskFactorCategoryDegree;
	}

	public Long getIdDegreeSelected() {
		return idDegreeSelected;
	}

	public void setIdDegreeSelected(Long idDegreeSelected) {
		this.idDegreeSelected = idDegreeSelected;
	}

	public Long getIdFacultySelected() {
		return idFacultySelected;
	}

	public void setIdFacultySelected(Long idFacultySelected) {
		this.idFacultySelected = idFacultySelected;
	}

	public Long getIdRiskFactorCategoryFaculty() {
		return idRiskFactorCategoryFaculty;
	}

	public void setIdRiskFactorCategoryFaculty(Long idRiskFactorCategoryFaculty) {
		this.idRiskFactorCategoryFaculty = idRiskFactorCategoryFaculty;
	}

	public Long getTotalStudentReport() {
		return totalStudentReport;
	}

	public void setTotalStudentReport(Long totalStudentReport) {
		this.totalStudentReport = totalStudentReport;
	}

	public List<Degree> getDegreeStudentList() {
		return degreeStudentList;
	}

	public void setDegreeStudentList(List<Degree> degreeStudentList) {
		this.degreeStudentList = degreeStudentList;
	}

	public List<Faculty> getFacultyStudentList() {
		return facultyStudentList;
	}

	public void setFacultyStudentList(List<Faculty> facultyStudentList) {
		this.facultyStudentList = facultyStudentList;
	}

	public List<ReportStudent> getReportStudentRiskList() {
		return reportStudentRiskList;
	}

	public void setReportStudentRiskList(
			List<ReportStudent> reportStudentRiskList) {
		this.reportStudentRiskList = reportStudentRiskList;
	}

	public List<ZyosUser> getResponsibleList() {
		return responsibleList;
	}

	public void setResponsibleList(List<ZyosUser> responsibleList) {
		this.responsibleList = responsibleList;
	}

	public List<Observation> getObservationList() {
		return observationList;
	}

	public void setObservationList(List<Observation> observationList) {
		this.observationList = observationList;
	}

	public InterventionController getController() {
		return controller;
	}

	public void setController(InterventionController controller) {
		this.controller = controller;
	}

	public int getDisableData() {
		return disableData;
	}

	public void setDisableData(int disableData) {
		this.disableData = disableData;
	}

	public StreamedContent getErrorFile() {
		return errorFile;
	}

	public void setErrorFile(StreamedContent errorFile) {
		this.errorFile = errorFile;
	}

	public StreamedContent getErrorFileReportStudent() {
		return errorFileReportStudent;
	}

	public void setErrorFileReportStudent(StreamedContent errorFileReportStudent) {
		this.errorFileReportStudent = errorFileReportStudent;
	}

	public boolean isDetailRiskFactor() {
		return detailRiskFactor;
	}

	public void setDetailRiskFactor(boolean detailRiskFactor) {
		this.detailRiskFactor = detailRiskFactor;
	}

	public boolean isDetailRiskDegree() {
		return detailRiskDegree;
	}

	public void setDetailRiskDegree(boolean detailRiskDegree) {
		this.detailRiskDegree = detailRiskDegree;
	}

	public boolean isDetailRiskFaculty() {
		return detailRiskFaculty;
	}

	public void setDetailRiskFaculty(boolean detailRiskFaculty) {
		this.detailRiskFaculty = detailRiskFaculty;
	}

	public boolean isDetailRiskFactorDegree() {
		return detailRiskFactorDegree;
	}

	public void setDetailRiskFactorDegree(boolean detailRiskFactorDegree) {
		this.detailRiskFactorDegree = detailRiskFactorDegree;
	}

	public boolean isDetailRiskFactorFaculty() {
		return detailRiskFactorFaculty;
	}

	public void setDetailRiskFactorFaculty(boolean detailRiskFactorFaculty) {
		this.detailRiskFactorFaculty = detailRiskFactorFaculty;
	}

	public boolean isShowButtonGiveProcess() {
		return showButtonGiveProcess;
	}

	public void setShowButtonGiveProcess(boolean showButtonGiveProcess) {
		this.showButtonGiveProcess = showButtonGiveProcess;
	}

	public boolean isShowGraphic() {
		return showGraphic;
	}

	public void setShowGraphic(boolean showGraphic) {
		this.showGraphic = showGraphic;
	}

	public boolean isShowReport() {
		return showReport;
	}

	public void setShowReport(boolean showReport) {
		this.showReport = showReport;
	}

	public boolean isShowRisk() {
		return showRisk;
	}

	public void setShowRisk(boolean showRisk) {
		this.showRisk = showRisk;
	}

	public boolean isShowActiveStudent() {
		return showActiveStudent;
	}

	public void setShowActiveStudent(boolean showActiveStudent) {
		this.showActiveStudent = showActiveStudent;
	}

	public boolean isShowManual() {
		return showManual;
	}

	public void setShowManual(boolean showManual) {
		this.showManual = showManual;
	}

	public boolean isShowRiskDegree() {
		return showRiskDegree;
	}

	public void setShowRiskDegree(boolean showRiskDegree) {
		this.showRiskDegree = showRiskDegree;
	}

	public boolean isShowRiskFaculty() {
		return showRiskFaculty;
	}

	public void setShowRiskFaculty(boolean showRiskFaculty) {
		this.showRiskFaculty = showRiskFaculty;
	}
}
