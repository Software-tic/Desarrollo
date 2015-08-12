package com.zyos.core.common.list;

import java.io.File;
import java.io.Serializable;
import java.security.Key;
import java.security.KeyPair;
import java.security.Security;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.zyos.alert.executionsHistorical.api.IExecutionHistorialType;
import com.zyos.alert.executionsHistorical.model.ExecutionsHistorical;
import com.zyos.alert.moodle.controller.MoodleController;
import com.zyos.alert.studentReport.model.Relationship;
import com.zyos.alert.studentReport.model.RiskFactor;
import com.zyos.alert.studentReport.model.RiskFactorCategory;
import com.zyos.alert.studentReport.model.Stage;
import com.zyos.alert.studentReport.model.StatusReportStudent;
import com.zyos.alert.studentReport.model.Subject;
import com.zyos.core.common.controller.ErrorNotificacion;
import com.zyos.core.common.model.DocumentType;
import com.zyos.core.common.model.ZyosParameter;
import com.zyos.core.common.util.ManageDate;
import com.zyos.core.common.util.auth.ZyosAuth;
import com.zyos.core.common.util.resource.ManageProperties;
import com.zyos.core.common.util.security.RSA;
import com.zyos.core.connection.HibernateSessionFactory;
import com.zyos.core.lo.enterprise.model.Enterprise;
import com.zyos.core.lo.user.model.Funcionality;
import com.zyos.core.lo.user.model.Profession;
import com.zyos.core.lo.user.model.ZyosGroup;
import com.zyos.core.lo.user.model.ZyosUser;
import com.zyos.core.login.model.ZyosGroupTree;
import com.zyos.core.mail.io.mn.model.EmailTemplate;
import com.zyos.core.mail.io.mn.model.EmailTemplateType;

/**
 * Class that contains all list enables in the application
 * 
 * @author Zyos-Home
 * 
 */
@ManagedBean(eager = true)
@ApplicationScoped
public class BeanList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long milliseg;

	private static KeyPair privateKey;
	private static Key publicKey;

	public static ZyosAuth zyosAuth = null;
	private static Timer updateCalificationMoodle = null;
	private static Timer updateGradesSAC = null;
	private static ExecutionsHistorical execution;
	private static ControllerList controller = new ControllerList();
	private static MoodleController moodleController = new MoodleController();
	/**
	 * pre list that contains Permanent reference by enterprise
	 */
	private static ArrayList<ControllerEnterpriseList> controllerCDEnterpriseList = new ArrayList<ControllerEnterpriseList>();

	private static List<DocumentType> documentTypeList = null;
	private static List<RiskFactor> riskFactorList = null;
	private static List<RiskFactorCategory> riskFactorCategoryList = null;
	private static List<Relationship> relationshipTypeList = null;
	private static List<Stage> stageList = null;
	private static List<Stage> stagePermissionList = null;
	private static List<StatusReportStudent> statusReportStudentList;

	private static List<Funcionality> funcionalityList;
	private static List<ZyosGroup> zyosGroupList;

	private static List<Enterprise> enterpriseList = null;
	public static ManageProperties properties = new ManageProperties();

	// global lists
	private static List<ZyosParameter> zyosParameterList = null;
	private static List<Subject> subjectList;

	static {
		initializeZyosList();
	}

	public static void reloadControllerList() {
		documentTypeList = null;
		enterpriseList = null;
		zyosParameterList = null;
		riskFactorList = null;
		riskFactorCategoryList = null;
		zyosAuth = null;
		stageList = null;
		stagePermissionList = null;
		controllerCDEnterpriseList = new ArrayList<ControllerEnterpriseList>();

		initializeZyosList();
	}

	private static void createControllerEnterpriseList() {
		try {
			loadEnterpriseList();

			ControllerEnterpriseList cel = null;
			loadEmailTemplateTypeList(cel);
			loadEmailTemplateList(cel);
			loadSettingsByEnterprise(cel);
			loadMenuAndSubmenuByEnterprise(cel);
			loadZyosUserListByEnterprise(cel);
			loadProfessionListByEnterprise(cel);
			loadZyosGroupFunctionalityListByEnterprise(cel);


		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "ControllerList - initializeLists - createControllerEnterpriseList");
		}
	}

	/**
	 * Created by: jhernandez
	 */
	private static void startUpdateCalificationFromMoodle() {
		try {
			if (updateCalificationMoodle == null && HibernateSessionFactory.getMySQLSession() != null && HibernateSessionFactory.getMySQLSession().isConnected()) {
				
				updateCalificationMoodle = new Timer("ZYOS_ROBOTMOODLE");


				Calendar midnigth = Calendar.getInstance();

				midnigth.set(Calendar.HOUR_OF_DAY, 23);
				midnigth.set(Calendar.MINUTE, 58);
				midnigth.set(Calendar.SECOND, 58);

				Long toStart = midnigth.getTimeInMillis() - System.currentTimeMillis();

				updateCalificationMoodle.schedule(new TimerTask() {

					private void updateCalificationMoodle() throws Exception {

						Long start = System.currentTimeMillis();

						/** loadCalification from moodle */
						// loading academic period
						Long idAcademicPeriod = controller.loadCurrentAcademicPeriod();
						moodleController.migrateEvaluationFromMoodle(idAcademicPeriod);
						moodleController.migrateCalificationItemFromMoodle(idAcademicPeriod);
						moodleController.migrateCalificationFromMoodle(idAcademicPeriod);
						System.out.println("INFO: Execution update moodle calification... ");

						Long end = System.currentTimeMillis();
						Long finaltime = end - start;
						Date executeTime = parseDate(finaltime);
						String execute = ManageDate.formatDate(executeTime, ManageDate.HH_MM_SS);

						/* saveExecute */
						execution = new ExecutionsHistorical();
						execution.initializing("autoMoodle", true);
						execution.setIdExecutionHistoricalType(IExecutionHistorialType.MOODLE);
						execution.setInformation("Registrando notas cargadas de Moodle de forma automatica");
						execution.setExecutionTime(execute);
						controller.saveExecution(execution);

					}

					@Override
					public void run() {
						try {

							System.out.println("INFO: Validation Robot Moodle Califications...");
							String day = ManageDate.getDay(ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD));
							if ((day.equals("07") || day.equals("14") || day.equals("21") || day.equals("28"))) {
								updateCalificationMoodle();
							}
						} catch (Exception e) {
							ErrorNotificacion.handleErrorMailNotification(e, this);
						}

					}
				}, toStart, 86400000);
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "ControllerList - initializeList - startNovelityProgrammer");
		}
	}

	/** @autor jhernandez */
	private static Date parseDate(Long milisecond) throws Exception {
		try {
			if (milisecond == null)
				return ManageDate.formatDate("00:00:00", ManageDate.HH_MM_SS);
			int seconds = milisecond.intValue() / 1000;
			int h = seconds / 3600;
			int m = (seconds % 3600) / 60;
			int s = ((seconds % 3600) % 60);

			return ManageDate.formatDate((h > 9 ? "" : "0") + h + ":" + (m > 9 ? "" : "0") + m + ":" + (s > 9 ? "" : "0") + s, ManageDate.HH_MM_SS);
		} catch (Exception e) {
			throw e;
		}
	}
	
	private static void startUpdateNotasFromSAC() {
		if (updateGradesSAC == null && HibernateSessionFactory.getOracleSession() != null && HibernateSessionFactory.getOracleSession().isConnected()) {
			updateGradesSAC = new Timer("USTA_ROBOTGRADESAC");

			Calendar midnigth = Calendar.getInstance();

			midnigth.set(Calendar.HOUR_OF_DAY, 23);
			midnigth.set(Calendar.MINUTE, 58);
			midnigth.set(Calendar.SECOND, 58);

			Long toStart = midnigth.getTimeInMillis() - System.currentTimeMillis();

			updateGradesSAC.schedule(new TimerTask() {
				
				private void updateGrades() throws Exception{
					Long idAcademicPeriod = controller.loadCurrentAcademicPeriod();
					int Corte =controller.loadCurrentCorte();
					controller.updateStudentGradesTunjaFromSAC(idAcademicPeriod);
					controller.reportStudent(idAcademicPeriod, Corte);
				}

				@Override
				public void run() {
					try {

						System.out.println("INFO: Validation Robot SAC Grades...");
						String day = ManageDate.getDay(ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD));
						if ((day.equals("07") || day.equals("14") || day.equals("21") || day.equals("28"))) {
							updateGrades();
						}
					} catch (Exception e) {
						ErrorNotificacion.handleErrorMailNotification(e, this);
					}

				}
			}, toStart, 86400000);
		}
	}

	public static ControllerEnterpriseList getCEL(ControllerEnterpriseList cel, Long idE) {
		if (cel == null || (cel != null && !cel.getIdEnterprise().equals(idE)))
			cel = getControllerEnterpriseList(idE);
		return cel;
	}

	public static ArrayList<ControllerEnterpriseList> getControllerEnterpriseList() {
		return controllerCDEnterpriseList;
	}

	/**
	 * Method that locate the controller cd list for enterprise
	 * 
	 * @param idE
	 * @return ControllerCDEnterpriseList or null if not found
	 */
	public static ControllerEnterpriseList getControllerEnterpriseList(Long idE) {
		try {
			for (ControllerEnterpriseList pret : controllerCDEnterpriseList)
				if (idE != null && pret.getIdEnterprise().equals(idE))
					return pret;

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "getControllerEnterpriseList");
		}
		return null;
	}

	/**
	 * Method that load and initialize all list
	 */
	public static void initializeZyosList() {
		System.out.println("INFO - initialize global list for Alert...");
		loadPrivateAndPublicKeys();
		loadZyosAuthList();
		loadEnterpriseList();
		loadDocumentTypeList();
		loadParameterList();
		loadZyosGroupList();
		loadFuncionalityList();
		loadRiskFactorList();
		loadRiskFactorCategoryList();
		loadRelationshipTypeList();
		loadSubjectList();
		loadStageList();
		loadStatusReportStudentList();
		createControllerEnterpriseList();
		startUpdateCalificationFromMoodle(); /* robot moodle */
		startUpdateNotasFromSAC(); /* robot para notas del SAC */
		// loadStagePermissionList();


		System.out.println("INFO - initialize Zyos List was completed...");
	}

	/**
	 * @author Hogar 21/08/2014 13:02:28
	 */
	private static void loadPrivateAndPublicKeys() {
		try {
			Security.addProvider(new BouncyCastleProvider());
			String uri = "/opt/zyos/private/";
			if (System.getProperty("os.name").toLowerCase().startsWith("win"))
				uri = "C:" + uri;
			privateKey = RSA.readKeyPair(new File(uri + "private_key.pem"));
			publicKey = RSA.readPublicKey(new File(uri + "public_key.pem"));
			System.out.println("INFO: private key pair loaded");
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "ERROR: validate if the private key is located in /opt/zyos/private/private_key.pem");
		}
	}

	private static void loadZyosAuthList() {
		try {
			if (zyosAuth == null)
				zyosAuth = controller.loadZyosAuthList();

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "ControllerList - initializeLists - loadRiskFactorList");
		}
	}

	// ****** general list **********

	private static void loadRiskFactorList() {
		try {
			if (riskFactorList == null)
				riskFactorList = controller.loadRiskFactorList();

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "ControllerList - initializeLists - loadRiskFactorList");
		}
	}

	private static void loadRiskFactorCategoryList() {
		try {
			if (riskFactorCategoryList == null)
				riskFactorCategoryList = controller.loadRiskFactorCategoryList();

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "ControllerList - initializeLists - loadRiskFactorList");
		}
	}

	private static void loadRelationshipTypeList() {
		try {
			if (relationshipTypeList == null)
				relationshipTypeList = controller.loadRelationshipTypeList();
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "ControllerList - initializeLists - loadRelationshipTypeList");
		}
	}

	private static void loadStageList() {
		try {
			if (stageList == null) {
				stageList = controller.loadStageList();
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "ControllerList - initializeLists - loadRelationshipTypeList");
		}

	}

	private static void loadStagePermissionList() {
		try {
			if (stagePermissionList == null) {
				stagePermissionList = controller.loadStagePermissionList();
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "ControllerList - initializeLists - loadRelationshipTypeList");
		}

	}

	private static void loadStatusReportStudentList() {
		try {
			if (statusReportStudentList == null) {
				statusReportStudentList = controller.loadStatusReportStudentList();
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "ControllerList - initializeLists - loadRelationshipTypeList");
		}

	}

	private static void loadEnterpriseList() {
		try {
			if (enterpriseList == null) {
				enterpriseList = controller.loadEnterpriseList();
				for (Enterprise e : enterpriseList) {
					getControllerEnterpriseList().add(new ControllerEnterpriseList(e));
					controller.validateFileStructure(e.getId());
					ControllerEnterpriseList cel = getCEL(null, e.getId());
					if (cel != null)
						cel.clean();
				}
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "ControllerList - initializeLists - loadEnterpriseList");
		}
	}

	private static void loadFuncionalityList() {
		try {
			if (funcionalityList == null) {
				funcionalityList = controller.loadFuncionalityList();
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "ControllerList - initializeLists - loadFuncionalityList");
		}
	}

	private static void loadDocumentTypeList() {
		try {
			if (documentTypeList == null) {
				documentTypeList = controller.loadDocumentTypeList();
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "ControllerList - initializeLists - loadDocumentTypeList");
		}
	}

	private static void loadZyosGroupList() {
		try {
			if (zyosGroupList == null) {
				zyosGroupList = controller.loadZyosGroupList();
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "ControllerList - initializeLists - zyosgroupList");
		}
	}

	// ****** enterprise list *********

	private static void loadMenuAndSubmenuByEnterprise(ControllerEnterpriseList cel) {
		try {
			controller.loadMenuAndSubmenuByEnterprise(cel);
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "ControllerList - initializeLists - loadMenuAndSubmenuByEnterprise");
		}
	}

	private static void loadParameterList() {
		try {
			if (zyosParameterList == null)
				zyosParameterList = controller.loadParameterList();
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "ControllerList - initializeLists - loadParameterList");
		}
	}

	private static void loadSettingsByEnterprise(ControllerEnterpriseList cel) {
		try {
			controller.loadSettingsByEnterprise(cel);
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "ControllerList - initializeLists - loadSettingsByEnterprise");
		}
	}

	private static void loadZyosGroupFunctionalityListByEnterprise(ControllerEnterpriseList cel) {
		try {
			List<ZyosGroupTree> spl = controller.loadZyosGroupFunctionalityListByEnterprise();
			for (ZyosGroupTree si : spl) {
				if (si != null && si.getId() != null) {
					cel = getCEL(cel, si.getIdEnterprise());
					if (cel != null)
						cel.getZyosGroupFuntionalityList().add(si);
				}
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "ControllerList - initializeLists - loadZyosUserListByEnterprise");
		}
	}

	private static void loadProfessionListByEnterprise(ControllerEnterpriseList cel) throws Exception {
		try {
			List<Profession> spl = controller.loadProfessionList();
			for (Profession si : spl) {
				if (si != null && si.getId() != null) {
					cel = getCEL(cel, si.getIdEnterprise());
					if (cel != null)
						cel.getProfessionList().add(si);
				}
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "ControllerList - initializeLists - loadZyosUserListByEnterprise");
		}
	}

	private static void loadZyosUserListByEnterprise(ControllerEnterpriseList cel) throws Exception {
		try {
			List<ZyosUser> spl = controller.loadZyosUserList();
			for (ZyosUser si : spl) {
				if (si != null && si.getIdZyosUser() != null) {
					cel = getCEL(cel, si.getIdEnterprise());
					if (cel != null)
						cel.getZyosUserList().add(si);
				}
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "ControllerList - initializeLists - loadZyosUserListByEnterprise");
		}
	}

	private static void loadEmailTemplateList(ControllerEnterpriseList cel) {
		try {
			List<EmailTemplate> spl = controller.loadEmailTemplateList();
			for (EmailTemplate si : spl) {
				if (si != null && si.getId() != null) {
					cel = getCEL(cel, si.getIdEnterprise());
					if (cel != null)
						cel.getEmailTemplateList().add(si);
				}
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "ControllerList - initializeLists - loadEmailTemplateList");
		}
	}

	private static void loadEmailTemplateTypeList(ControllerEnterpriseList cel) {
		try {
			List<EmailTemplateType> spl = controller.loadEmailTemplateTypeList();
			for (EmailTemplateType si : spl) {
				if (si != null && si.getId() != null) {
					cel = getCEL(cel, si.getIdEnterprise());
					if (cel != null)
						cel.getEmailTemplateTypeList().add(si);
				}
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "ControllerList - initializeLists - loadEmailTemplateTypeList");
		}
	}

	public static void loadSubjectList() {
		try {
			subjectList = controller.loadSubjectList();
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "ControllerList - initializeLists - loadSubjectList");

		}
	}

	public static List<DocumentType> getDocumentTypeList() {
		return documentTypeList;
	}

	public static void setDocumentTypeList(List<DocumentType> documentTypeList) {
		BeanList.documentTypeList = documentTypeList;
	}

	public static List<Enterprise> getEnterpriseList() {
		return enterpriseList;
	}

	public static void setEnterpriseList(List<Enterprise> enterpriseList) {
		BeanList.enterpriseList = enterpriseList;
	}

	public static List<ZyosParameter> getZyosParameterList() {
		return zyosParameterList;
	}

	public static void setZyosParameterList(List<ZyosParameter> zyosParameterList) {
		BeanList.zyosParameterList = zyosParameterList;
	}

	public static List<ZyosGroup> getZyosGroupList() {
		return zyosGroupList;
	}

	public static void setZyosGroupList(List<ZyosGroup> zyosgroupList) {
		BeanList.zyosGroupList = zyosgroupList;
	}

	public static List<Funcionality> getFuncionalityList() {
		return funcionalityList;
	}

	public static void setFuncionalityList(List<Funcionality> funcionalityList) {
		BeanList.funcionalityList = funcionalityList;
	}

	public static List<Subject> getSubjectList() {
		return subjectList;
	}

	public static List<RiskFactor> getRiskFactorList() {
		return riskFactorList;
	}

	public static List<RiskFactorCategory> getRiskFactorListCategory() {
		return riskFactorCategoryList;
	}


	public static List<Relationship> getRelationshipTypeList() {
		return relationshipTypeList;
	}

	public static List<Stage> getStageList() {
		return stageList;
	}

	public static List<Stage> getStagePermissionList() {
		return stagePermissionList;
	}

	public static List<StatusReportStudent> getStatusReportStudentList() {
		return statusReportStudentList;
	}

	public Long getMilliseg() {
		return milliseg;
	}

	public void setMilliseg(Long milliseg) {
		this.milliseg = milliseg;
	}

	public static KeyPair getPrivateKey() {
		return privateKey;
	}

	public static void setPrivateKey(KeyPair privateKey) {
		BeanList.privateKey = privateKey;
	}

	public static Key getPublicKey() {
		return publicKey;
	}

	public static void setPublicKey(Key publicKey) {
		BeanList.publicKey = publicKey;
	}

	public static Timer getUpdateGradesSAC() {
		return updateGradesSAC;
	}

	public static void setUpdateGradesSAC(Timer updateGradesSAC) {
		BeanList.updateGradesSAC = updateGradesSAC;
	}



}
