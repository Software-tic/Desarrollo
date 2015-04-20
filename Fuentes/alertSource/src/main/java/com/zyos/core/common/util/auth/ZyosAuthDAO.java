package com.zyos.core.common.util.auth;

import java.util.List;

import org.hibernate.Query;

import com.zyos.core.connection.OracleBaseHibernateDAO;
import com.zyos.core.lo.user.model.Profession;

/**
 * A data access object (DAO) providing persistence and search support for
 * ZyosAuth entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.zyos.core.lo.ZyosAuth.model.ZyosAuth
 * @author Óscar Garzón
 */

public class ZyosAuthDAO extends OracleBaseHibernateDAO {

	public void save(ZyosAuth transientInstance) {
		try {
			getSession().save(transientInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public void delete(ZyosAuth persistentInstance) {
		try {
			getSession().delete(persistentInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public ZyosAuth findAll() {
		try {
			String queryString = "from ZyosAuth where state > 0 order by idEnterprise";
			Query queryObject = getSession().createQuery(queryString);
			return (ZyosAuth) queryObject.uniqueResult();
		} catch (RuntimeException re) {
			throw re;
		}
	}

}