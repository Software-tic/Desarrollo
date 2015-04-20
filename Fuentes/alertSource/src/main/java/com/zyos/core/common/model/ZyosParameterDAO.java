package com.zyos.core.common.model;

import java.util.List;

import org.hibernate.Query;

import com.zyos.core.common.api.IZyosState;
import com.zyos.core.connection.OracleBaseHibernateDAO;

/**
 * A data access object (DAO) providing persistence and search support for
 * Parameter entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.zyos.core.common.model.Parameter
 * @author MyEclipse Persistence Tools
 */

public class ZyosParameterDAO extends OracleBaseHibernateDAO {

	public void save(ZyosParameter transientInstance) {
		try {
			getSession().save(transientInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public List<ZyosParameter> loadParameterList() {
		try {
			StringBuilder hql = new StringBuilder(
					"from ZyosParameter i where i.state = :state and globalParameter = 1 order by i.name  ");
			Query qo = null;
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE);
			hql = null;
			return qo.list();
		} catch (RuntimeException e) {
			throw e;
		}
	}

	public List<AParameter> loadParameterList(Long idEnterprise,
			String nameClass, int globalParameter) {
		try {
			StringBuilder hql = new StringBuilder("from ");
			hql.append(nameClass);
			hql.append(" i where i.state = :state ");
			if (globalParameter == 0)
				hql.append("and idEnterprise =:idEnterprise ");
			hql.append("order by i.name  ");
			Query qo = null;
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE);
			if (globalParameter == 0)
				qo.setParameter("idEnterprise", idEnterprise);
			hql = null;
			return qo.list();
		} catch (RuntimeException e) {
			throw e;
		}
	}

}