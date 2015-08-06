package com.zyos.alert.sac.model;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zyos.core.common.api.IZyosState;
import com.zyos.core.common.util.ManageDate;
import com.zyos.core.connection.OracleBaseHibernateDAO;


public class CambioIdentificacionEstudianteDAO extends OracleBaseHibernateDAO {


	/**
	 * A data access object (DAO) providing persistence and search support for
	 * CambioIdentificacionEstudiante entities. Transaction control of the save(), update() and
	 * delete() operations can directly support Spring container-managed transactions or they can be
	 * augmented to handle user-managed Spring transactions. Each of these methods provides
	 * additional information for how to configure it for the desired type of transaction control.
	 * 
	 * @see com.zyos.alert.sac.model.CambioIdentificacionEstudiante
	 * @author MyEclipse Persistence Tools
	 */
	private static final Logger log = LoggerFactory.getLogger(CarrerasDAO.class);

	public void save(CambioIdentificacionEstudiante transientInstance) {
		log.debug("saving CambioIdentificacionEstudiante instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(CambioIdentificacionEstudiante persistentInstance) {
		log.debug("deleting CambioIdentificacionEstudiante instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public List findAll() {
		log.debug("finding all CambioIdentificacionEstudiante instances");
		try {
			String queryString = "from CambioIdentificacionEstudiante";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/** @author jhernandez */
	public List<CambioIdentificacionEstudiante> loadChangeDocumentStudentFromSAC() throws Exception {
		try {
			StringBuilder hql = new StringBuilder();
			hql.append(" select  ");
			hql.append(" c.id  as id, c.num_identificacion as numIdentificacion, c.fecha as fecha, c.num_ident_Antigua as numIdentificacionAntigua ");
			hql.append(" from ");
			hql.append(" cambio_ident_est c ");

			Query qo =
				getSession().createSQLQuery(hql.toString()).setResultTransformer(Transformers.aliasToBean(CambioIdentificacionEstudiante.class));

			qo =
				getSession().createSQLQuery(hql.toString()).addScalar("id", StandardBasicTypes.LONG)
					.addScalar("numIdentificacion", StandardBasicTypes.STRING).addScalar("fecha", StandardBasicTypes.STRING)
					.addScalar("numIdentificacionAntigua", StandardBasicTypes.STRING)
					.setResultTransformer(Transformers.aliasToBean(CambioIdentificacionEstudiante.class));

			return qo.list();
		} catch (Exception e) {
			throw e;
		}
	}


	/**
	 * @author jhernandez, ogarzonm
	 * @throws Exception
	 */
	public void updateDocumentStudent(CambioIdentificacionEstudiante zyosUser) throws Exception {
		StringBuilder sql = new StringBuilder();
		Query qo = null;
		try {
			sql.append(" UPDATE zyos.ZyosUser zu set documentNumber=:documentNumber, ");
			sql.append(" dateChange=:dateChange, userChange=:userChange ");
			sql.append(" Where ");
			sql.append(" zu.documentNumber =:documentNumberOld AND ");
			sql.append(" zu.state = :state ");

			qo = getSession().createSQLQuery(sql.toString());
			qo.setParameter("documentNumber", zyosUser.getNumIdentificacion());
			qo.setParameter("documentNumberOld", zyosUser.getNumIdentificacionAntigua());
			qo.setParameter("dateChange", ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD_HH_MM_SS));
			qo.setParameter("userChange", "systemFromSAC");
			qo.setParameter("state", IZyosState.ACTIVE);

			qo.executeUpdate();


		} catch (Exception e) {
			throw e;
		} finally {
			sql = null;
			qo = null;
		}
	}


	/**
	 * @author ogarzonm 05/08/2014
	 * @throws Exception
	 */
	public void updateUserLogin(CambioIdentificacionEstudiante zyosUser) throws Exception {
		StringBuilder sql = new StringBuilder();
		Query qo = null;
		try {
			sql.append(" UPDATE zyos.ZyosLogin zu set userLogin=:documentNumber, ");
			sql.append(" dateChange=:dateChange, userChange=:userChange ");
			sql.append(" Where ");
			sql.append(" zu.userLogin =:documentNumberOld AND ");
			sql.append(" zu.state = :state ");

			qo = getSession().createSQLQuery(sql.toString());
			qo.setParameter("documentNumber", zyosUser.getNumIdentificacion());
			qo.setParameter("documentNumberOld", zyosUser.getNumIdentificacionAntigua());
			qo.setParameter("dateChange", ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD_HH_MM_SS));
			qo.setParameter("userChange", "systemFromSAC");
			qo.setParameter("state", IZyosState.ACTIVE);

			qo.executeUpdate();


		} catch (Exception e) {
			throw e;
		} finally {
			sql = null;
			qo = null;
		}
	}
}
