package com.zyos.alert.integration.controller;

import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.zyos.alert.executionsHistorical.api.IExecutionHistorialType;
import com.zyos.alert.executionsHistorical.model.ExecutionsHistorical;
import com.zyos.core.common.api.IZyosGroup;
import com.zyos.core.common.controller.ErrorNotificacion;
import com.zyos.core.common.controller.ZyosBackingBean;
import com.zyos.core.common.util.ManageDate;

@ManagedBean
@ViewScoped
@URLMapping(id = "IntegrationBean", pattern = "/portal/integracion", viewId = "/pages/integration/integration.jspx")
public class IntegrationBean extends ZyosBackingBean {

	private List<ExecutionsHistorical> executionsHistoricalListFiltered;
	private List<ExecutionsHistorical> executionsHistoricalList;

	private IntegrationController controller = new IntegrationController();


	/** @author ogarzonm */
	public IntegrationBean() throws Exception {
		try {
			loadExecutionIntegrationList();
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	/** @author jhernandez 1/08/2014 12:49:01 */
	public void loadExecutionIntegrationList() {
		try {
			executionsHistoricalList = controller.loadExecutionIntegrationList();
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}

	}


	/** @author ogarzonm */
	public void migrateListFromSAC() {
		try {

			if (getUserSession() != null && getUserSession().getDefaultGroup().equals(IZyosGroup.ADMINISTRATOR)) {

				System.out.println("INFO: START INTEGRATION MODULE " + ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD_HH_MM_SS));

				Long start = System.currentTimeMillis();

				printMessage("INFO: Start changeDocumentStudent database integration ");
				controller.changeDocumentStudent();
				printMessage("INFO: End changeDocumentStudent database integration ");

				/*printMessage("INFO: Start loadMoodleDatabaseCourse database integration ");

				boolean isMoodleEnable = controller.validateMoodleConnection();
				if (isMoodleEnable) {
					controller.loadMoodleDatabaseCourse();
				} else {
					printMessage("ERROR: MySQL Moodle database is not connected ");
				}

				printMessage("INFO: End loadMoodleDatabaseCourse database integration ");
				*/
				int studentSize = 0, degreeSize = 0, teacherList = 0, subjectSize = 0, StudentDegreeSize = 0, FacultySize = 0, FacultyDegreeSize = 0, FacultyCoordinatorSize =
					0, GroupSize = 0, TeacherSubjectSize = 0, StudentSubjectSize = 0, StudentSubject = 0, StudentSubjectDay = 0;

				printMessage("INFO: Start migrateStudentListFromSAC database integration ");
				
				//is necessary create a while because the first query not gets all data
				while (true) {
					int s = controller.migrateStudentListFromSAC();
					studentSize += s;
					System.out.println("recording =" + s);
					if (s <= 0)
						break;
				}
				printMessage("INFO: End migrateStudentListFromSAC database integration ");

				printMessage("INFO: Start migrateDegreeListFromSAC database integration ");
				while (true) {
					int s = controller.migrateDegreeListFromSAC();
					degreeSize += s;
					System.out.println("recording =" + s);
					if (s <= 0)
						break;
				}
				printMessage("INFO: End migrateDegreeListFromSAC database integration ");

				printMessage("INFO: Start migrateDocentListFromSAC database integration ");
				while (true) {
					int s = controller.migrateDocentListFromSAC();
					teacherList += s;
					
					System.out.println("recording =" + s);
					if (s <= 0)
						break;
				}
				printMessage("INFO: End migrateDocentListFromSAC database integration ");

				printMessage("INFO: Start migrateSubjectListFromSAC database integration ");
				while (true) {
					int s = controller.migrateSubjectListFromSAC();
					subjectSize += s;
					System.out.println("recording =" + s);
					if (s <= 0)
						break;
				}
				printMessage("INFO: End migrateSubjectListFromSAC database integration ");

				printMessage("INFO: Start migrateStudentDegreeFromSAC database integration ");
				while (true) {
					int s = controller.migrateStudentDegreeFromSAC();
					StudentDegreeSize += s;
					System.out.println("recording =" + s);
					if (s <= 0)
						break;
				}
				printMessage("INFO: End migrateStudentDegreeFromSAC database integration ");

				printMessage("INFO: Start migrateFacultyListFromSAC database integration ");
				while (true) {
					int s = controller.migrateFacultyListFromSAC();
					FacultySize += s;
					System.out.println("recording =" + s);
					if (s <= 0)
						break;
				}
				printMessage("INFO: End migrateFacultyListFromSAC database integration ");

				printMessage("INFO: Start migrateFacultyDegreeListFromSAC database integration ");
				while (true) {
					int s = controller.migrateFacultyDegreeListFromSAC();
					FacultyDegreeSize += s;
					System.out.println("recording =" + s);
					if (s <= 0)
						break;
				}
				printMessage("INFO: End migrateFacultyDegreeListFromSAC database integration ");

				printMessage("INFO: Start migrateFacultyCoordinatorFromSAC database integration ");
				FacultyCoordinatorSize = controller.migrateFacultyCoordinatorFromSAC();
				System.out.println("recording =" + FacultyCoordinatorSize);
				printMessage("INFO: End migrateFacultyCoordinatorFromSAC database integration ");

				// Integra DB is the moodle and SAC connector
				/*printMessage("INFO: Start migrateIntegraDatabase database integration ");
				if (isMoodleEnable) {
					controller.migrateIntegraDatabase();
				} else {
					printMessage("ERROR: MySQL Moodle database is not connected ");
				}
				printMessage("INFO: End migrateIntegraDatabase database integration ");
				*/
				// loading academic period
				printMessage("INFO: Start migrateGroupListFromSAC database integration ");
				Long idAcademicPeriod = controller.loadCurrentAcademicPeriod();
				while (true) {
					int s = controller.migrateGroupListFromSAC(idAcademicPeriod);
					GroupSize += s;
					System.out.println("recording =" + s);
					if (s <= 0)
						break;
				}
				printMessage("INFO: End migrateGroupListFromSAC database integration ");

				printMessage("INFO: Start migrateTeacherSubjectListFromSAC database integration ");
				while (true) {
					int s = controller.migrateTeacherSubjectListFromSAC(idAcademicPeriod);
					TeacherSubjectSize += s;
					System.out.println("recording =" + s);
					if (s <= 0)
						break;
				}
				printMessage("INFO: End migrateTeacherSubjectListFromSAC database integration ");

				printMessage("INFO: Start migrateStudentSubjectListFromSAC database integration ");
				while (true) {
					int s = controller.migrateStudentSubjectListFromSAC(idAcademicPeriod);
					StudentSubjectSize += s;
					System.out.println("recording =" + s);
					if (s <= 0)
						break;
				}
				printMessage("INFO: End migrateStudentSubjectListFromSAC database integration ");

				printMessage("INFO: Start migratetudentSubjectDayClassFromSAC database integration ");
				while (true) {
					int s = controller.migrateStudentSubjectDayClassFromSAC(idAcademicPeriod);
					StudentSubjectDay += s;
					System.out.println("recording =" + s);
					if (s <= 0)
						break;
				}
				printMessage("INFO: End migratetudentSubjectDayClassFromSAC database integration ");
				
				//-----Integración SIAT-TUNJA vista notas de estudiante
				/*int NotasEstudent=0;
				printMessage("INFO: Start migrateNotStudentCorteTunjaFromSAC database integration ");
				while (true) {
					int s = controller.migrateNotEstudentCorteTunjaFromSAC(idAcademicPeriod);
					NotasEstudent += s;
					System.out.println("recording =" + s);
					if (s <= 0)
						break;
				}
				printMessage("INFO: End migrateNotStudentCorteTunjaFromSAC database integration ");*/
				//--------------------------------------------------------

				ExecutionsHistorical eh = new ExecutionsHistorical();
				eh.initializing("integrationAuto", true);
				eh.setIdExecutionHistoricalType(IExecutionHistorialType.INTEGRATION);
				eh.setInformation("Registrando elementos nuevos de semestre de forma automática");

				Long end = System.currentTimeMillis();
				Long finaltime = end - start;

				Date executeTime = parseDate(finaltime);
				String execute = ManageDate.formatDate(executeTime, ManageDate.HH_MM_SS);
				eh.setExecutionTime(execute);

				controller.saveExecution(eh);
				executionsHistoricalList.add(eh);

				System.out.println("INFO: END INTEGRATION MODULE " + ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD_HH_MM_SS));

			}

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	/** @autor jhernandez */
	private Date parseDate(Long milisecond) throws Exception {
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

	private void printMessage(String string) {
		System.out.println(string + ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD_HH_MM_SS));
	}

	public List<ExecutionsHistorical> getExecutionsHistoricalListFiltered() {
		return executionsHistoricalListFiltered;
	}

	public void setExecutionsHistoricalListFiltered(List<ExecutionsHistorical> executionsHistoricalListFiltered) {
		this.executionsHistoricalListFiltered = executionsHistoricalListFiltered;
	}

	public List<ExecutionsHistorical> getExecutionsHistoricalList() {
		return executionsHistoricalList;
	}

	public void setExecutionsHistoricalList(List<ExecutionsHistorical> executionsHistoricalList) {
		this.executionsHistoricalList = executionsHistoricalList;
	}

	public void threadIntegration() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					migrateListFromSAC();
				} catch (Exception e) {
					ErrorNotificacion.handleErrorMailNotification(e, this);
				}
			}
		}).start();
	}

}
