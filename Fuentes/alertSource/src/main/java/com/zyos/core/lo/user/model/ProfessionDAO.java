package com.zyos.core.lo.user.model;

import java.util.List;

import javax.faces.model.SelectItem;

import org.hibernate.Query;

import com.zyos.alert.studentReport.model.StatusReportStudent;
import com.zyos.core.connection.OracleBaseHibernateDAO;

/**
 * A data access object (DAO) providing persistence and search support for
 * Profession entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.zyos.core.lo.user.model.Profession
 * @author MyEclipse Persistence Tools
 */

public class ProfessionDAO extends OracleBaseHibernateDAO {

	// property constants
	public void save(Profession transientInstance) {
		try {
			getSession().save(transientInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public void delete(Profession persistentInstance) {
		try {
			getSession().delete(persistentInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public List<SelectItem> loadProfessionList() {
		return loadLikeSelectItem("Profession");
	}

	public List<Profession> findAll() {
		try {
			String queryString = "from Profession where state > 0 order by idEnterprise";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			throw re;
		}
	}

}