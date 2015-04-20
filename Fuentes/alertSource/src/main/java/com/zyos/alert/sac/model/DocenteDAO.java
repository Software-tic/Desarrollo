package com.zyos.alert.sac.model;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zyos.core.common.api.IZyosGroup;
import com.zyos.core.connection.OracleBaseHibernateDAO;
import com.zyos.core.lo.user.model.ZyosUser;
import com.zyos.core.lo.user.model.ZyosUserGroup;

/**
 * A data access object (DAO) providing persistence and search support for
 * Docente entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.zyos.alert.sac.model.Docente
 * @author MyEclipse Persistence Tools
 */
public class DocenteDAO extends OracleBaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(DocenteDAO.class);

	public void save(Docente transientInstance) {
		log.debug("saving Docente instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Docente persistentInstance) {
		log.debug("deleting Docente instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public List<ZyosUser> migrateDocentListFromSAC() throws Exception {
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select e.apellidos as lastName,   ");
			hql.append("e.celular as mobilePhone, e.correoAlternativo as secondEmail,  ");
			hql.append("e.direccion as address, e.documento as documentNumber, e.nombres as name, e.telefono as phone ");
			hql.append("from Docente e ");
			hql.append("where  ");
			hql.append("e.documento not in (select documentNumber from ZyosUser where documentNumber != null) ");

			Query qo = getSession().createQuery(hql.toString()).setResultTransformer(Transformers.aliasToBean(ZyosUser.class));
			return qo.list();
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * @author Ogarzonm 09/08/2014
	 * @return
	 * @throws Exception 
	 */
	public List<ZyosUserGroup> loadDuplicateRoleUserList() throws Exception {
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select e.id as idZyosUser ");
			hql.append("from ZyosUser e ");
			hql.append("where  ");
			hql.append("e.documentNumber in (select documento from Docente ) ");
			hql.append("and e.idZyosUser not in (select zug.idZyosUser from ZyosUserGroup zug where zug.idGroup =:idZyosGroup) ");

			Query qo = getSession().createQuery(hql.toString()).setResultTransformer(Transformers.aliasToBean(ZyosUserGroup.class));
			qo.setParameter("idZyosGroup", IZyosGroup.TEACHER);
			return qo.list();
		} catch (Exception e) {
			throw e;
		}
	}

}