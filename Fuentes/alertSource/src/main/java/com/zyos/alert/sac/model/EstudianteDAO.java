package com.zyos.alert.sac.model;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zyos.alert.studentReport.model.Student;
import com.zyos.core.common.api.IZyosGroup;
import com.zyos.core.connection.OracleBaseHibernateDAO;
import com.zyos.core.lo.user.model.ZyosUser;

/**
 * A data access object (DAO) providing persistence and search support for
 * Estudiantes entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zyos.alert.sac.model.Estudiante
 * @author MyEclipse Persistence Tools
 */
public class EstudianteDAO extends OracleBaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(EstudianteDAO.class);

	public void save(Estudiante transientInstance) {
		log.debug("saving Estudiantes instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Estudiante persistentInstance) {
		log.debug("deleting Estudiantes instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/** @autor jhernandez **/
	public List<ZyosUser> migrateStudentUserListFromSAC() throws Exception {
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select t1.id as idStudent, t1.apellidos as lastName,     ");
			hql.append("t1.celular as mobilePhone, t1.documento as documentNumber, t1.correoAlternativo as secondEmail,    ");
			hql.append("t1.direccion as address, t1.documento as userCreation, t1.nombres as name, t1.telefono as phone from Estudiante t1 where not exists ");
			hql.append("(select 1 from ZyosUser t2 where t2.documentNumber = t1.documento and idZyosGroup =:idZyosGroup and documentNumber is not null) ");
			
			Query qo = getSession().createQuery(hql.toString()).setResultTransformer(Transformers.aliasToBean(ZyosUser.class));
			qo.setParameter("idZyosGroup", IZyosGroup.STUDENT);
			return qo.list();
			
		} catch (Exception e) {
			throw e;
		}

	}

	
	/** @author ogarzonm*/
	public List<Student> migrateStudentListFromSAC() throws Exception {
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select distinct e.id as idStudentSAC, zu.idZyosUser as idZyosUser, ce.codAlumno as code ");
			hql.append("from  Estudiante e, CarrerasEstudiantes ce, ");
			hql.append("ZyosUser zu ");
			hql.append("where  ");
			hql.append("e.id = ce.idEstudiante ");
			hql.append("and e.documento = zu.documentNumber ");
			hql.append("and zu.idZyosUser not in (select s.idZyosUser from Student s)  ");

			Query qo = getSession().createQuery(hql.toString()).setResultTransformer(Transformers.aliasToBean(Student.class));
			return qo.list();
			
		} catch (Exception e) {
			throw e;
		}		

	}

	/** @author ogarzonm - 31/03/2014*/
	public void loadEmailFromUserSACInfo() throws Exception {
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("update zyos.ZyosUser zu set zu.email = (select mzu.mail from zyos.MAIL_Z_USERS mzu where mzu.documento = zu.documentNumber and rownum = 1)  where zu.email is null");
			
			Query qo = getSession().createSQLQuery(hql.toString());
			qo.executeUpdate();
		} catch (Exception e) {
			throw e;
		}
	}

}