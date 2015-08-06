package com.zyos.core.login.model;

import com.zyos.core.connection.OracleBaseHibernateDAO;

/**
 * A data access object (DAO) providing persistence and search support for
 * TreeEnterprise entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zyos.core.login.model.TreeEnterprise
 * @author MyEclipse Persistence Tools
 */

public class TreeEnterpriseDAO extends OracleBaseHibernateDAO {

	public void save(TreeEnterprise transientInstance) {
		try {
			getSession().save(transientInstance);

		} catch (RuntimeException re) {

			throw re;
		}
	}

	public void delete(TreeEnterprise persistentInstance) {
		try {
			getSession().delete(persistentInstance);

		} catch (RuntimeException re) {

			throw re;
		}
	}

}