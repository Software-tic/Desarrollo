package com.zyos.core.mail.io.mn.model;

import org.hibernate.LockMode;
import org.hibernate.Query;

import com.zyos.core.connection.OracleBaseHibernateDAO;

/**
 * A data access object (DAO) providing persistence and search support for
 * Notification entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zyos.core.io.mn.model.Notification
 * @author Óscar Garzón
 */

public class NotificationDAO extends OracleBaseHibernateDAO {

	public void save(Notification transientInstance) {
		try {
			getSession().save(transientInstance);

		} catch (RuntimeException re) {

			throw re;
		}
	}

	public void delete(Notification persistentInstance) {
		try {
			getSession().delete(persistentInstance);

		} catch (RuntimeException re) {

			throw re;
		}
	}

	public int removeNotificationByUser(Long idZU, Long idCutOffTime)
			throws Exception {
		StringBuilder hql = new StringBuilder();

		hql.append(" delete    ");
		hql.append(" from ");
		hql.append(" Notification n   ");
		hql.append(" where ");
		hql.append(" n.idCutOffTime = :idCOT ");
		hql.append(" and  n.idZyosUser = :idZU ");

		Query qo = null;
		qo = getSession().createQuery(hql.toString());
		qo.setParameter("idCOT", idCutOffTime);
		qo.setParameter("idZU", idZU);
		hql = null;
		return qo.executeUpdate();
	}
}