package com.zyos.alert.integration.controller;

import java.util.List;

import com.zyos.alert.absent.model.DayClass;
import com.zyos.alert.absent.model.DayClassDAO;
import com.zyos.alert.executionsHistorical.model.ExecutionsHistorical;
import com.zyos.alert.executionsHistorical.model.ExecutionsHistoricalDAO;
import com.zyos.alert.faculty.model.Faculty;
import com.zyos.alert.facultyCoordinator.model.FacultyCoordinator;
import com.zyos.alert.facultyCoordinator.model.FacultyCoordinatorDAO;
import com.zyos.alert.facultyDegree.model.FacultyDegree;
import com.zyos.alert.facultyDegree.model.FacultyDegreeDAO;
import com.zyos.alert.integration.model.DegreeSAC;
import com.zyos.alert.integration.model.GroupSubjectSAC;
import com.zyos.alert.integration.model.SubjectSAC;
import com.zyos.alert.moodle.model.MdlCourse;
import com.zyos.alert.moodle.model.MdlCourseDAO;
import com.zyos.alert.query.controller.EncryptMD5;
import com.zyos.alert.query.model.GradesPeriodSubject;
import com.zyos.alert.query.model.GradesPeriodSubjectDAO;
import com.zyos.alert.sac.model.CambioIdentificacionEstudiante;
import com.zyos.alert.sac.model.CambioIdentificacionEstudianteDAO;
import com.zyos.alert.sac.model.CarrerasDAO;
import com.zyos.alert.sac.model.DocenteDAO;
import com.zyos.alert.sac.model.EstudianteDAO;
import com.zyos.alert.sac.model.FacultadesDAO;
import com.zyos.alert.sac.model.GrupoDAO;
import com.zyos.alert.sac.model.Integra;
import com.zyos.alert.sac.model.IntegraDAO;
import com.zyos.alert.sac.model.TemporalMoodle;
import com.zyos.alert.sac.model.TemporalMoodleDAO;
import com.zyos.alert.studentReport.model.Student;
import com.zyos.alert.studentReport.model.StudentDegree;
import com.zyos.alert.studentReport.model.StudentDegreeDAO;
import com.zyos.alert.studentReport.model.StudentSubject;
import com.zyos.alert.studentReport.model.StudentSubjectDAO;
import com.zyos.alert.studentReport.model.SubjectDAO;
import com.zyos.alert.studentReport.model.TeacherSubject;
import com.zyos.alert.studentReport.model.TeacherSubjectDAO;
import com.zyos.core.common.api.IZyosGroup;
import com.zyos.core.common.controller.ErrorNotificacion;
import com.zyos.core.common.controller.ZyosController;
import com.zyos.core.connection.OracleBaseHibernateDAO;
import com.zyos.core.lo.enterprise.model.ZyosUserEnterprise;
import com.zyos.core.lo.user.model.ZyosUser;
import com.zyos.core.lo.user.model.ZyosUserGroup;
import com.zyos.core.login.model.ZyosLogin;

/** @author ogarzonm */
public class IntegrationController extends ZyosController {

	/** @author ogarzonm */
	public int migrateStudentListFromSAC() {
		EstudianteDAO dao = new EstudianteDAO();
		try {
			List<ZyosUser> userList = dao.migrateStudentUserListFromSAC();

			int i = 0;
			if (userList != null && !userList.isEmpty()) {

				for (ZyosUser zu : userList) {
					zu.setIdEnterprise(1L);
					zu.setIdZyosGroup(IZyosGroup.STUDENT);
					zu.setIdDocumentType(1l);
					zu.initializing(zu.getUserCreation(), true);
					dao.getSession().save(zu);

					if (i % 10 == 0) {
						dao.getSession().flush();
						dao.getSession().clear();
					}
					i++;

				}

				createLogin(userList, dao);

				dao.getSession().beginTransaction().commit();


				dao.loadEmailFromUserSACInfo();
				dao.getSession().beginTransaction().commit();
			}
			
			i = 0;
			List<Student> sList = dao.migrateStudentListFromSAC();
			if (sList != null && !sList.isEmpty()) {
				for (Student s : sList) {
					s.initializing("systemFromSAC", true);
					dao.getSession().save(s);
					if (i % 10 == 0) {
						dao.getSession().flush();
						dao.getSession().clear();
					}
					i++;
				}
				dao.getSession().beginTransaction().commit();
			}

			return userList.size();

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		} finally {
			dao.getSession().close();
			dao = null;
		}
		return -1;
	}

	/**
	 * @author ogarzonm
	 * @param dao
	 * */
	private void createLogin(List<ZyosUser> userList, OracleBaseHibernateDAO dao) {
		try {
			int i = 0;
			for (ZyosUser zu : userList) {
				ZyosLogin zl = new ZyosLogin();
				zl.setAuthMode("ldap");
				zl.initializing("systemFromSAC", true);
				zl.setIdZyosUser(zu.getIdZyosUser());
				zl.setUserLogin(zu.getDocumentNumber());
				zl.setPassword("123456");
				zl.setPasswordMD5(EncryptMD5.encrypt(zl.getPassword()));//RSA.encrypt(zl.getPassword()));
				zl.setDeadLine("2020/12/12");
				dao.getSession().save(zl);

				dao.getSession().save(new ZyosUserEnterprise(zu.getIdZyosUser(), zu.getIdEnterprise(), zu.getDateCreation(), true));
				dao.getSession().save(new ZyosUserGroup(zu.getIdZyosUser(), zu.getIdZyosGroup(), zu.getDateCreation(), true));

				if (i % 10 == 0) {
					dao.getSession().flush();
					dao.getSession().clear();
				}
				i++;
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		}
	}

	/** @author ogarzonm */
	public int migrateDegreeListFromSAC() {
		CarrerasDAO dao = new CarrerasDAO();
		try {
			List<DegreeSAC> dl = dao.migrateDegreeListFromSAC();

			if (dl != null && !dl.isEmpty()) {
				int i = 0;
				for (DegreeSAC d : dl) {
					d.initializing("systemFromSAC", true);
					d.setDescription(d.getName());
					dao.getSession().save(d);

					if (i % 10 == 0) {
						dao.getSession().flush();
						dao.getSession().clear();
					}
					i++;
				}
				dao.getSession().beginTransaction().commit();
				return dl.size();
			}

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		} finally {
			dao.getSession().close();
			dao = null;
		}
		return -1;
	}

	/** @author ogarzonm */
	public int migrateDocentListFromSAC() {
		DocenteDAO dao = new DocenteDAO();
		try {
			List<ZyosUser> userList = dao.migrateDocentListFromSAC();
			List<ZyosUserGroup> dupicateRoleUserList = dao.loadDuplicateRoleUserList();

			if (userList != null && !userList.isEmpty()) {
				int i = 0;
				for (ZyosUser zu : userList) {
					zu.setIdEnterprise(1L);
					zu.setIdDocumentType(1l);
					zu.setIdZyosGroup(IZyosGroup.TEACHER);
					zu.initializing("systemFromSAC", true);
					dao.getSession().save(zu);

					if (i % 10 == 0) {
						dao.getSession().flush();
						dao.getSession().clear();
					}
					i++;
				}

				createLogin(userList, dao);

				// creating new role when is student and teacher at the same time
				if (dupicateRoleUserList != null && !dupicateRoleUserList.isEmpty()) {
					for (ZyosUserGroup zug : dupicateRoleUserList) {
						zug.initializing("systemFromSAC", true);
						zug.setIdGroup(IZyosGroup.TEACHER);
						dao.getSession().save(zug);
					}
				}

				dao.getSession().beginTransaction().commit();
				return userList.size();
			}

		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		} finally {
			dao.getSession().close();
			dao = null;
		}
		return -1;
	}

	/** @author ogarzonm */
	public int migrateFacultyListFromSAC() {
		FacultadesDAO dao = new FacultadesDAO();
		try {
			List<Faculty> sl = dao.migrateFacultyListFromSAC();

			if (sl != null && !sl.isEmpty()) {
				int i = 0;
				for (Faculty d : sl) {
					d.initializing("systemFromSAC", true);
					dao.getSession().save(d);

					if (i % 10 == 0) {
						dao.getSession().flush();
						dao.getSession().clear();
					}
					i++;
				}
				dao.getSession().beginTransaction().commit();
				return sl.size();
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		} finally {
			dao.getSession().close();
			dao = null;
		}
		return -1;
	}

	/** @author ogarzonm */
	public int migrateSubjectListFromSAC() {
		SubjectDAO dao = new SubjectDAO();
		try {
			List<SubjectSAC> sl = dao.migrateSubjectListFromSAC();

			if (sl != null && !sl.isEmpty()) {
				int i = 0;
				for (SubjectSAC d : sl) {
					d.initializing("systemFromSAC", true);
					dao.getSession().save(d);

					if (i % 10 == 0) {
						dao.getSession().flush();
						dao.getSession().clear();
					}
					i++;
				}
				dao.getSession().beginTransaction().commit();
				return sl.size();
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		} finally {
			dao.getSession().close();
			dao = null;
		}
		return -1;
	}

	/** @author ogarzonm */
	public int migrateTeacherSubjectListFromSAC(Long idAcademicPeriod) {
		TeacherSubjectDAO dao = new TeacherSubjectDAO();
		try {
			List<TeacherSubject> sl = dao.migrateTeacherSubjectListFromSAC(idAcademicPeriod);

			if (sl != null && !sl.isEmpty()) {
				int i = 0;
				for (TeacherSubject d : sl) {
					d.initializing("systemFromSAC", true);
					d.setIdAcademicPeriod(idAcademicPeriod);
					dao.getSession().save(d);

					if (i % 10 == 0) {
						dao.getSession().flush();
						dao.getSession().clear();
					}
					i++;
				}
				dao.getSession().beginTransaction().commit();
				return sl.size();
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		} finally {
			dao.getSession().close();
			dao = null;
		}
		return -1;
	}

	/** @author ogarzonm */
	public int migrateStudentSubjectListFromSAC(Long idAcademicPeriod) {
		StudentSubjectDAO dao = new StudentSubjectDAO();
		int i = 0;
		try {
			List<StudentSubject> sl = dao.migrateStudentSubjectListFromSAC(idAcademicPeriod);

			if (sl != null && !sl.isEmpty()) {
				saveStudentSubject(sl, i, idAcademicPeriod, dao);
				return sl.size();
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		} finally {
			dao.getSession().close();
			dao = null;
		}
		return -1;
	}

	/**
	 * 
	 * @author Hogar 04/08/2014 21:49:28
	 * @param sl
	 * @param i
	 * @param idAcademicPeriod
	 * @param dao
	 * @throws Exception
	 */
	public void saveStudentSubject(List<StudentSubject> sl, int i, Long idAcademicPeriod, StudentSubjectDAO dao) throws Exception {
		try {
			i = 0;
			//System.out.println(sl.size());
			for (StudentSubject d : sl) {
				d.initializing("systemFromSAC", true);
				d.setIdAcademicPeriod(idAcademicPeriod);
				dao.getSession().save(d);

				if (i % 10 == 0) {
					dao.getSession().flush();
					dao.getSession().clear();
				}
				i++;
				this.createGradesTableStudent(idAcademicPeriod, d.getIdStudentSubject());

				if (i % 8000 == 0 && i != 0) {
					dao.getSession().beginTransaction().commit();
					dao.getSession().close();

					dao = new StudentSubjectDAO();
					saveStudentSubject(sl.subList(i, sl.size()), i, idAcademicPeriod, dao);
					sl = null;
					break;
				}
			}
			if (sl != null && i > 0) {
				dao.getSession().beginTransaction().commit();
			}
		} catch (Exception e) {
			throw e;
		}

	}

	/** @author ogarzonm */
	public int migrateStudentDegreeFromSAC() {
		StudentDegreeDAO dao = new StudentDegreeDAO();
		try {
			List<StudentDegree> sl = dao.migrateStudentDegreeFromSAC();

			if (sl != null && !sl.isEmpty()) {
				int i = 0;
				for (StudentDegree d : sl) {
					d.initializing("systemFromSAC", true);
					dao.getSession().save(d);

					if (i % 10 == 0) {
						dao.getSession().flush();
						dao.getSession().clear();
					}
					i++;
				}
				dao.getSession().beginTransaction().commit();
				return sl.size();
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		} finally {
			dao.getSession().close();
			dao = null;
		}
		return -1;
	}

	/** @author ogarzonm */
	public int migrateGroupListFromSAC(Long idAcademicPeriod) {
		GrupoDAO dao = new GrupoDAO();
		try {
			List<GroupSubjectSAC> dl = dao.migrateGroupListFromSAC(idAcademicPeriod);

			if (dl != null && !dl.isEmpty()) {
				int i = 0;
				for (GroupSubjectSAC d : dl) {
					d.initializing("systemFromSAC", true);
					d.setDescription(d.getName());
					d.setIdAcademicPeriod(idAcademicPeriod);
					dao.getSession().save(d);

					if (i % 10 == 0) {
						dao.getSession().flush();
						dao.getSession().clear();
					}
					i++;
				}
				dao.getSession().beginTransaction().commit();
				return dl.size();
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		} finally {
			dao.getSession().close();
			dao = null;
		}
		return -1;
	}

	/** @author ogarzonm */
	public void loadMoodleDatabaseCourse() {
		MdlCourseDAO dao = new MdlCourseDAO();
		TemporalMoodleDAO dao2 = null;
		try {
			List<MdlCourse> dl = dao.loadMoodleDatabaseCourse();
			if (dl != null && !dl.isEmpty()) {
				int i = 0;
				dao2 = new TemporalMoodleDAO();
				dao2.cleanTemporalMoodle();
				for (MdlCourse c : dl) {
					String[] d = c.getShortname().split("_");
					// d = PERIODNAME_IDDEGREE_IDSUBJECT_GROUPNAME
					if (d.length > 3) {
						try {
							dao2.getSession().save(new TemporalMoodle(c.getId(), d[0], d[2], d[3], c.getShortname()));

							if (i % 10 == 0) {
								dao2.getSession().flush();
								dao2.getSession().clear();
							}
							i++;
						} catch (Exception e) {
							System.out.println("WARN: can't save " + c.getId() + " " + c.getShortname());
						}
					}
				}
				dao2.getSession().beginTransaction().commit();
			}

			dao2 = new TemporalMoodleDAO();
			dao2.setIdGroupListToTemporalMoodle();
			dao2.getSession().beginTransaction().commit();
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		} finally {
			if (dao != null)
				dao.getSession().close();
			if (dao2 != null)
				dao2.getSession().close();
			dao = null;
			dao2 = null;
		}
	}

	/** @author ogarzonm */
	public void migrateIntegraDatabase() {
		IntegraDAO dao = new IntegraDAO();
		try {
			List<Integra> dl = dao.migrateIntegraDatabase();

			if (dl != null && !dl.isEmpty()) {
				int i = 0;
				for (Integra d : dl) {
					dao.getSession().save(d);

					if (i % 10 == 0) {
						dao.getSession().flush();
						dao.getSession().clear();
					}
					i++;
				}
				dao.getSession().beginTransaction().commit();
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	/** @author ogarzonm */
	public int migrateFacultyDegreeListFromSAC() {
		FacultyDegreeDAO dao = new FacultyDegreeDAO();
		try {
			List<FacultyDegree> dl = dao.migrateFacultyDegreeListFromSAC();

			if (dl != null && !dl.isEmpty()) {
				int i = 0;
				for (FacultyDegree d : dl) {
					d.initializing("systemFromSAC", true);
					dao.getSession().save(d);

					if (i % 10 == 0) {
						dao.getSession().flush();
						dao.getSession().clear();
					}
					i++;
				}
				dao.getSession().beginTransaction().commit();
				return dl.size();
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		} finally {
			dao.getSession().close();
			dao = null;
		}
		return -1;
	}

	/** @author ogarzonm */
	public int migrateFacultyCoordinatorFromSAC() {
		FacultyCoordinatorDAO dao = new FacultyCoordinatorDAO();

		try {
			dao.clearFacultyCoordinatorTable();
			dao.getSession().beginTransaction().commit();

			// get exist user list
			List<String> idList = dao.validateUserExist();

			// create not exist user list
			if (idList != null && !idList.isEmpty()) {
				List<ZyosUser> listUser = dao.createZyosUser(idList);
				for (ZyosUser zu : listUser) {
					zu.initializing("systemFromSAC", true);
					zu.setIdEnterprise(1l);
					zu.setLastName(zu.getName());
					zu.setIdZyosGroup(IZyosGroup.DECAN_FACULTY);
					dao.getSession().save(zu);
				}
				createLogin(listUser, dao);

				dao.getSession().beginTransaction().commit();
			}
			
			// create realationship
			List<FacultyCoordinator> fcl = dao.migrateFacultyCoordinatorFromSAC();
			for (FacultyCoordinator fc : fcl) {
				fc.initializing("systemFromSAC", true);
				dao.getSession().save(fc);
			}

			dao.getSession().beginTransaction().commit();
			return fcl.size();
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		} finally {
			dao.getSession().close();
			dao = null;
		}
		return -1;
	}

	/** @author ogarzonm */
	public int migrateStudentSubjectDayClassFromSAC(Long idAcademicPeriod) {
		DayClassDAO dao = new DayClassDAO();
		int i = 0;
		try {
			// Select * from t1 where not exists (select 1 from t2 where t2.id = t1.id)
			List<DayClass> dl = dao.migrateDayClassFromSAC();
			if (dl != null && !dl.isEmpty()) {

				for (DayClass d : dl) {
					d.setIdAcademicPeriod(idAcademicPeriod);
					d.initializing("systemFromSAC", true);
					System.out.println(i);
					dao.getSession().save(d);
					
					if (i % 10 == 0 && i != 0) {
						dao.getSession().flush();
						dao.getSession().clear();
					}
					i++;
				}
				dao.getSession().beginTransaction().commit();
				return dl.size();
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		} finally {
			dao.getSession().close();
			dao = null;
		}
		return -1;
	}

	/** @author jhernandez */
	public void changeDocumentStudent() {
		CambioIdentificacionEstudianteDAO dao = new CambioIdentificacionEstudianteDAO();
		try {
			List<CambioIdentificacionEstudiante> sl = dao.loadChangeDocumentStudentFromSAC();

			if (sl != null && !sl.isEmpty()) {
				int i = 0;
				for (CambioIdentificacionEstudiante d : sl) {

					dao.updateDocumentStudent(d);
					dao.updateUserLogin(d);

					if (i % 10 == 0) {
						dao.getSession().flush();
						dao.getSession().clear();
					}
					i++;
				}

				dao.getSession().beginTransaction().commit();
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	public boolean validateMoodleConnection() {

		MdlCourseDAO dao = new MdlCourseDAO();
		try {
			return dao.validateMoodleConnection();
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		} finally {
			if (dao != null)
				dao.getSession().close();
			dao = null;
		}
		return false;
	}

	/** @author jhernandez */
	public List<ExecutionsHistorical> loadExecutionIntegrationList() throws Exception {
		ExecutionsHistoricalDAO dao = new ExecutionsHistoricalDAO();
		try {
			return dao.loadExecutionIntegrationList();
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}
	/** SIAT-TUNJA */
	public void createGradesTableStudent(Long idAcademicPeriod, Long idStudentSubject) {
		GradesPeriodSubjectDAO dao = new GradesPeriodSubjectDAO();
		try {
			Long aux=dao.selectId();
			GradesPeriodSubject gps = new GradesPeriodSubject((1+aux),idStudentSubject, idAcademicPeriod);
			gps.initializing("systemFromSAC", true);
			dao.getSession().save(gps);
			dao.getSession().beginTransaction().commit();
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	/** SIAT-TUNJA */
	/*public int migrateNotEstudentCorteTunjaFromSAC(Long idAcademicPeriod) {
		GradesPeriodSubjectDAO dao = new GradesPeriodSubjectDAO();
		int i = 0;
		try {
			List<GradesPeriodSubject> dl = dao.migrateGradesPeriodSubjectFromSAC();
			if (dl != null && !dl.isEmpty()) {
				Long aux=dao.selectId();
				for (GradesPeriodSubject d : dl) {
					aux++;
					d.setIdgradesPeriodSubject((aux));
					d.initializing("systemFromSAC", true);
					dao.getSession().save(d);

					if (i % 10 == 0 && i != 0) {
						dao.getSession().flush();
						dao.getSession().clear();
					}
					i++;
				}
				dao.getSession().beginTransaction().commit();
				return dl.size();
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, this);
		} finally {
			dao.getSession().close();
			dao = null;
		}
		return -1;
	}*/
	
}
