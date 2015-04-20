package com.zyos.core.mail.io.mn.model;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zyos.core.common.api.IZyosState;
import com.zyos.core.connection.OracleBaseHibernateDAO;
import com.zyos.core.lo.user.model.ZyosGroup;

/**
 * A data access object (DAO) providing persistence and search support for
 * TypeEmailTemplate entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zyos.EmailTemplateType.io.mn.model.TypeEmailTemplate
 * @author MyEclipse Persistence Tools
 */

public class EmailTemplateTypeDAO extends OracleBaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(EmailTemplateTypeDAO.class);

	public void save(EmailTemplateType transientInstance) {
		log.debug("saving TypeEmailTemplate instance");
		try {
			getSession().save(transientInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public void delete(EmailTemplateType persistentInstance) {
		log.debug("deleting TypeEmailTemplate instance");
		try {
			getSession().delete(persistentInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public List<EmailTemplateType> loadEmailTemplateTypeList() {
		StringBuilder hql = new StringBuilder();
		try {
			hql.append("from ");
			hql.append("EmailTemplateType  ");
			hql.append("where ");
			hql.append("state = :state ");
			hql.append("order by name");

			Query qo = null;
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE);

			return qo.list();
		} catch (RuntimeException e) {
			throw e;
		} finally {
			hql = null;
		}
	}

	public List<EmailTemplateType> findAll() {
		try {
			String queryString = "from EmailTemplateType where state > 0 order by idEnterprise";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
}