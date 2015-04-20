package com.zyos.core.mail.io.mn.model;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zyos.core.common.api.IZyosState;
import com.zyos.core.common.controller.ErrorNotificacion;
import com.zyos.core.connection.OracleBaseHibernateDAO;

/**
 * A data access object (DAO) providing persistence and search support for
 * EmailFrom entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.zyos.mail.io.mn.model.EmailFrom
 * @author MyEclipse Persistence Tools
 */

public class EmailFromDAO extends OracleBaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(EmailFromDAO.class);

	public void save(EmailFrom transientInstance) {
		log.debug("saving EmailFrom instance");
		try {
			getSession().save(transientInstance);

		} catch (RuntimeException re) {

			throw re;
		}
	}

	public void delete(EmailFrom persistentInstance) {
		log.debug("deleting EmailFrom instance");
		try {
			getSession().delete(persistentInstance);

		} catch (RuntimeException re) {

			throw re;
		}
	}

	public List<EmailFrom> loadEmailFromList() {
		try {
			StringBuilder hql = new StringBuilder(
					"from EmailFrom i where i.state = :state order by i.email  ");
			Query qo = null;
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE);
			hql = null;
			return qo.list();
		} catch (Exception e) {
			getSession().cancelQuery();
			this.getSession().close();
			ErrorNotificacion.handleErrorMailNotification(e, "system");
			return null;
		}
	}

	public boolean removeEmailFrom(Long id) throws Exception {
		try {
			StringBuilder hql = new StringBuilder(
					"delete EmailFrom where id = :id ");
			Query qo = null;
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("id", id);
			hql = null;
			return qo.executeUpdate() > 0 ? true : false;
		} catch (Exception e) {
			this.getSession().close();
			ErrorNotificacion.handleErrorMailNotification(e, "system");
			throw e;
		}
	}

	public boolean enableEmailFrom(Long id) throws Exception {
		try {
			StringBuilder hql = new StringBuilder(
					"update EmailFrom set validate = true where id = :id ");
			Query qo = null;
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("id", id);
			hql = null;
			return qo.executeUpdate() > 0 ? true : false;
		} catch (Exception e) {
			this.getSession().close();
			ErrorNotificacion.handleErrorMailNotification(e, "system");
			throw e;
		}
	}
}