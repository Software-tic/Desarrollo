package com.zyos.alert.characterization.model;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zyos.alert.studentReport.model.ReportStudent;
import com.zyos.core.connection.OracleBaseHibernateDAO;

/**
 * A data access object (DAO) providing persistence and search support for
 * Caracterizacion entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zyos.alert.studentReport.model.Caracterizacion
 * @author MyEclipse Persistence Tools
 */
public class CaracterizacionDAO extends OracleBaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(CaracterizacionDAO.class);

	public void save(Caracterizacion transientInstance) {
		log.debug("saving Caracterizacion instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Caracterizacion persistentInstance) {
		log.debug("deleting Caracterizacion instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Caracterizacion findById(java.lang.Long id) {
		log.debug("getting Caracterizacion instance with id: " + id);
		try {
			Caracterizacion instance = (Caracterizacion) getSession().get(
					"com.zyos.alert.studentReport.model.Caracterizacion", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Caracterizacion instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from Caracterizacion as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findAll() {
		log.debug("finding all Caracterizacion instances");
		try {
			String queryString = "from Caracterizacion";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Caracterizacion merge(Caracterizacion detachedInstance) {
		log.debug("merging Caracterizacion instance");
		try {
			Caracterizacion result = (Caracterizacion) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Caracterizacion instance) {
		log.debug("attaching dirty Caracterizacion instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/** @author ogarzonm */
	public List<ReportStudent> loadCharacterizationPollSocialAdaptationList()
			throws Exception {
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select (select idStudent from Student where idStudentSAC = cast(subt.id_estudiante as DECIMAL) and rownum = 1) as \"idStudent\", subt.countA as \"count\" from  ");
			hql.append("(select id_estudiante, count(*) countA from  ");

			hql.append("(select DISTINCT id_estudiante from  ");

			hql.append("(SELECT caracterizacion.id_estudiante ");
			hql.append("FROM zyos.caracterizacion  ");
			hql.append("WHERE caracterizacion.cod_pregunta = '3' AND caracterizacion.respuesta in ('4', '5', '6') ) t1 ");

			hql.append("union all  ");

			hql.append("(SELECT DISTINCT caracterizacion.id_estudiante ");
			hql.append("FROM zyos.caracterizacion WHERE  ");
			hql.append("caracterizacion.cod_pregunta = '14' and caracterizacion.respuesta = '1') ");

			hql.append("union all  ");

			hql.append("(select DISTINCT co.id_estudiante from  ");
			hql.append("zyos.caracterizacion_opciones co ");
			hql.append("where ");
			hql.append("co.cod_pregunta = '23' and co.cod_opcion = '4') ");

			hql.append("union all ");

			hql.append("(select DISTINCT co.id_estudiante from  ");
			hql.append("zyos.caracterizacion_opciones co ");
			hql.append("where ");
			hql.append("co.cod_pregunta = '24' and co.cod_opcion = '7' and co.respuesta = '1') ");

			hql.append("union all ");

			hql.append("(select t.id_estudiante from ");
			hql.append("(select co.id_estudiante, count(co.id_estudiante) countI from ");
			hql.append("zyos.caracterizacion_opciones co ");
			hql.append("where ");
			hql.append("co.cod_pregunta = '62' and (co.cod_opcion = '4' or co.cod_opcion = '5' or co.cod_opcion = '8') ");
			hql.append("group by co.id_estudiante order by 2) t ");
			hql.append("where  ");
			hql.append("t.countI >= 2) ");

			hql.append(") t2 group by t2.id_estudiante ) subt where subt.counta >= 3 order by counta desc ");

			Query qo = getSession().createSQLQuery(hql.toString()).setResultTransformer(Transformers.aliasToBean(ReportStudent.class));
			return qo.list();
		} catch (Exception e) {
			throw e;
		}
	}

	/** @author ogarzonm */
	public List<ReportStudent> loadCharacterizationPollHealthList()
			throws Exception {
		try {
			StringBuilder hql = new StringBuilder();

			hql.append("select  (select idStudent from Student where idStudentSAC = cast(subt.id_estudiante as DECIMAL) and rownum = 1) as \"idStudent\", subt.countA  as \"count\" from ");
			hql.append("(select id_estudiante, count(*) countA from ");

			hql.append("(select DISTINCT id_estudiante from ");

			//pregunta 53 
			hql.append("(select t.id_estudiante from ");
			hql.append("(select co.id_estudiante, count(co.id_estudiante) countI from ");
			hql.append("zyos.caracterizacion_opciones co ");
			hql.append("where ");
			hql.append("co.cod_pregunta = '53'  ");
			hql.append("group by co.id_estudiante order by 2) t ");
			hql.append("where  ");
			hql.append("t.countI = 1) t1 ");

			hql.append("union all  ");
			//pregunta 57 
			hql.append("(SELECT DISTINCT caracterizacion.id_estudiante ");
			hql.append("FROM zyos.caracterizacion ");
			hql.append("WHERE  ");
			hql.append("caracterizacion.cod_pregunta = '57' and caracterizacion.respuesta = '1') ");

			hql.append("union all  ");
			//pregunta 60 
			hql.append("(SELECT DISTINCT caracterizacion.id_estudiante ");
			hql.append("FROM zyos.caracterizacion ");
			hql.append("WHERE  ");
			hql.append("caracterizacion.cod_pregunta = '60' and caracterizacion.respuesta = '1') ");

			hql.append("union all  ");
			//pregunta 61 
			hql.append("(select t.id_estudiante from ");
			hql.append("(select co.id_estudiante, count(co.id_estudiante) countI from ");
			hql.append("zyos.caracterizacion_opciones co ");
			hql.append("where ");
			hql.append("co.cod_pregunta = '61'  ");
			hql.append("group by co.id_estudiante order by 2) t ");
			hql.append("where  ");
			hql.append("t.countI >= 1) ");

			hql.append("union all  ");
			//pregunta 62 
			hql.append("(select t.id_estudiante from ");
			hql.append("(select co.id_estudiante, count(co.id_estudiante) countI from ");
			hql.append("zyos.caracterizacion_opciones co ");
			hql.append("where ");
			hql.append("co.cod_pregunta = '62' and (co.cod_opcion = '1' or co.cod_opcion = '9' or co.cod_opcion = '2') ");
			hql.append("group by co.id_estudiante order by 2) t ");
			hql.append("where  ");
			hql.append("t.countI >= 2) ");

			hql.append("union all  ");
			//pregunta 63 
			hql.append("(select DISTINCT co.id_estudiante from  ");
			hql.append("zyos.caracterizacion_opciones co ");
			hql.append("where ");
			hql.append("co.cod_pregunta = '63' and co.cod_opcion = '7' and co.respuesta = '1') ");

			hql.append(") t2 group by t2.id_estudiante ) subt where subt.counta >= 3 order by counta desc ");
			Query qo = getSession().createSQLQuery(hql.toString()).setResultTransformer(Transformers.aliasToBean(ReportStudent.class));
			return qo.list();
		} catch (Exception e) {
			throw e;
		}
	}

	/** @author ogarzonm */
	public List<ReportStudent> loadCharacterizationPollVocationalOrientationList()
			throws Exception {
		try {
			StringBuilder hql = new StringBuilder();

			hql.append("select  (select idStudent from Student where idStudentSAC = cast(subt.id_estudiante as DECIMAL) and rownum = 1) \"idStudent\", subt.countA  as \"count\" from  ");
			hql.append("(select id_estudiante, count(*) countA from ");

			hql.append("(select DISTINCT id_estudiante from ");

			//pregunta 3 ");
			hql.append("(SELECT DISTINCT caracterizacion.id_estudiante ");
			hql.append("FROM zyos.caracterizacion ");
			hql.append("WHERE  ");
			hql.append("caracterizacion.cod_pregunta = '3' and caracterizacion.respuesta = '1') t ");

			hql.append("union all ");

			//pregunta 26 ");
			hql.append("(SELECT DISTINCT caracterizacion.id_estudiante ");
			hql.append("FROM zyos.caracterizacion ");
			hql.append("WHERE  ");
			hql.append("caracterizacion.cod_pregunta = '26' and (caracterizacion.respuesta = '3' or caracterizacion.respuesta = '3')) ");

			hql.append(") t2 group by t2.id_estudiante ) subt where subt.counta >= 1 order by counta desc ");
			Query qo = getSession().createSQLQuery(hql.toString()).setResultTransformer(Transformers.aliasToBean(ReportStudent.class));
			return qo.list();
		} catch (Exception e) {
			throw e;
		}
	}

	/** @author ogarzonm */
	public List<ReportStudent> loadCharacterizationPollAcademicList()
			throws Exception {
		try {
			StringBuilder hql = new StringBuilder();

			hql.append("select  (select idStudent from Student where idStudentSAC = cast(subt.id_estudiante as DECIMAL) and rownum = 1) as \"idStudent\", subt.countA  as \"count\" from  ");
			hql.append("(select id_estudiante, count(*) countA from ");

			hql.append("(select DISTINCT id_estudiante from ");

			//pregunta 3 ");
			hql.append("(SELECT DISTINCT caracterizacion.id_estudiante ");
			hql.append("FROM zyos.caracterizacion ");
			hql.append("WHERE  ");
			hql.append("caracterizacion.cod_pregunta = '19' and caracterizacion.respuesta = '1') t ");

			hql.append("union all  ");
			//pregunta 27 ");
			hql.append("(SELECT DISTINCT caracterizacion.id_estudiante ");
			hql.append("FROM zyos.caracterizacion ");
			hql.append("WHERE  ");
			hql.append("caracterizacion.cod_pregunta = '27' and caracterizacion.respuesta = '5') ");

			hql.append("union all  ");
			//pregunta 31 ");
			hql.append("(SELECT DISTINCT caracterizacion.id_estudiante ");
			hql.append("FROM zyos.caracterizacion ");
			hql.append("WHERE  ");
			hql.append("caracterizacion.cod_pregunta = '31' and (caracterizacion.respuesta = '5' or caracterizacion.respuesta = '4')) ");

			hql.append("union all  ");
			//pregunta 20 ");
			hql.append("(SELECT DISTINCT caracterizacion.id_estudiante ");
			hql.append("FROM zyos.caracterizacion ");
			hql.append("WHERE  ");
			hql.append("caracterizacion.cod_pregunta = '20' and caracterizacion.respuesta = '1') ");

			hql.append("union all  ");
			//pregunta 62 ");
			hql.append("(select DISTINCT co.id_estudiante from  ");
			hql.append("zyos.caracterizacion_opciones co ");
			hql.append("where ");
			hql.append("co.cod_pregunta = '63' and co.cod_opcion = '3' and co.respuesta = '1') ");

			hql.append(") t2 group by t2.id_estudiante ) subt where subt.counta >= 3 order by counta desc ");
			Query qo = getSession().createSQLQuery(hql.toString()).setResultTransformer(Transformers.aliasToBean(ReportStudent.class));
			return qo.list();
		} catch (Exception e) {
			throw e;
		}
	}

	/** @author ogarzonm */
	public List<ReportStudent> loadCharacterizationPollSupportList()
			throws Exception {
		try {
			StringBuilder hql = new StringBuilder();

			hql.append("select  (select idStudent from Student where idStudentSAC = cast(subt.id_estudiante as DECIMAL) and rownum = 1) as \"idStudent\", subt.countA  as \"count\" from  ");
			hql.append("(select id_estudiante, count(*) countA from  ");

			hql.append("(select DISTINCT id_estudiante from ");

			//pregunta 14 ");
			hql.append("(SELECT DISTINCT caracterizacion.id_estudiante ");
			hql.append("FROM zyos.caracterizacion ");
			hql.append("WHERE  ");
			hql.append("caracterizacion.cod_pregunta = '6' and caracterizacion.respuesta = '0') t ");

			hql.append("union all  ");
			//pregunta 16 ");
			hql.append("(SELECT DISTINCT caracterizacion.id_estudiante ");
			hql.append("FROM zyos.caracterizacion ");
			hql.append("WHERE  ");
			hql.append("caracterizacion.cod_pregunta = '16' and caracterizacion.respuesta in ('4', '5', '6')) ");

			hql.append("union all  ");
			//pregunta 17 ");
			hql.append("(SELECT DISTINCT caracterizacion.id_estudiante ");
			hql.append("FROM zyos.caracterizacion ");
			hql.append("WHERE  ");
			hql.append("caracterizacion.cod_pregunta = '17' and caracterizacion.respuesta in ('1','2' )) ");

			hql.append("union all  ");
			//pregunta 22 ");
			hql.append("(select DISTINCT co.id_estudiante from  ");
			hql.append("zyos.caracterizacion_opciones co ");
			hql.append("where ");
			hql.append("co.cod_pregunta = '22' and co.cod_opcion in ('1','9') ) ");

			hql.append("union all  ");
			//pregunta 32 ");
			hql.append("(SELECT DISTINCT caracterizacion.id_estudiante ");
			hql.append("FROM zyos.caracterizacion ");
			hql.append("WHERE  ");
			hql.append("caracterizacion.cod_pregunta = '6' and caracterizacion.respuesta in ('1', '3')) ");

			hql.append(") t2 group by t2.id_estudiante ) subt where subt.counta >= 3 order by counta desc ");
			Query qo = getSession().createSQLQuery(hql.toString()).setResultTransformer(Transformers.aliasToBean(ReportStudent.class));
			return qo.list();
		} catch (Exception e) {
			throw e;
		}
	}

	/** @author ogarzonm */
	public List<ReportStudent> loadCharacterizationPollEconomicList()
			throws Exception {
		try {
			StringBuilder hql = new StringBuilder();

			hql.append("select  (select idStudent from Student where idStudentSAC = cast(subt.id_estudiante as DECIMAL) and rownum = 1) as \"idStudent\", subt.countA  as \"count\" from  ");
			hql.append("(select id_estudiante, count(*) countA from  ");

			hql.append("(select DISTINCT id_estudiante from  ");

			hql.append("(select DISTINCT co.id_estudiante from  ");
			hql.append("zyos.caracterizacion_opciones co ");
			hql.append("where ");
			hql.append("co.cod_pregunta = '18' and co.cod_opcion in ('1','7','10','8') ) t ");

			hql.append(") t2 group by t2.id_estudiante ) subt where subt.counta >= 1 order by counta desc ");
			
			Query qo = getSession().createSQLQuery(hql.toString()).setResultTransformer(Transformers.aliasToBean(ReportStudent.class));
			return qo.list();
		} catch (Exception e) {
			throw e;
		}
	}
}