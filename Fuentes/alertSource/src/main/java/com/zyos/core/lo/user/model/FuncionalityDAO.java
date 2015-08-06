package com.zyos.core.lo.user.model;

import java.util.List;

import org.hibernate.Query;

import com.zyos.core.connection.OracleBaseHibernateDAO;

/**
 * A data access object (DAO) providing persistence and search support for
 * Funcionality entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zyos.core.lo.ExecutionHistoricalType.model.Funcionality
 * @author MyEclipse Persistence Tools
 */

public class FuncionalityDAO extends OracleBaseHibernateDAO {

	public void save(Funcionality transientInstance) {

		try {
			getSession().save(transientInstance);

		} catch (RuntimeException re) {

			throw re;
		}
	}

	public void delete(Funcionality persistentInstance) {
		try {
			getSession().delete(persistentInstance);

		} catch (RuntimeException re) {

			throw re;
		}
	}

	public List<Funcionality> findAll() {
		try {
			String queryString = "from Funcionality where state > 0 ";
			Query qo = getSession().createQuery(queryString);
			return qo.list();
		} catch (RuntimeException re) {
			throw re;
		}
	}

}