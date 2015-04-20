package com.zyos.core.lo.enterprise.model;

import java.util.List;

import javax.faces.model.SelectItem;



import org.hibernate.Query;

import com.zyos.core.common.api.IZyosState;
import com.zyos.core.common.controller.ErrorNotificacion;
import com.zyos.core.connection.OracleBaseHibernateDAO;

/**
 * A data access object (DAO) providing persistence and search support for
 * ZyosUserEnterprise entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zyos.core.lo.enterprise.model.ZyosUserEnterprise
 * @author Óscar Garzón
 */

public class ZyosUserEnterpriseDAO extends OracleBaseHibernateDAO {
	
			

	public void save(ZyosUserEnterprise transientInstance) {
		
		try {
			getSession().save(transientInstance);
			
		} catch (RuntimeException re) {
			
			throw re;
		}
	}

	public void delete(ZyosUserEnterprise persistentInstance) {
		
		try {
			getSession().delete(persistentInstance);
			
		} catch (RuntimeException re) {
			
			throw re;
		}
	}

	public List<SelectItem> getListEnterpriseByUser(Long idZU) {
		StringBuilder hql = new StringBuilder();
		try {
			hql.append("select distinct new javax.faces.model.SelectItem(e.id, e.name) ");
			hql.append("from ZyosUserEnterprise zue, Enterprise e where ");
			hql.append("zue.idZyosUser = :idZU and zue.idEnterprise = e.id and e.state = :state order by e.name asc");
			Query qo = null;
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE);
			qo.setParameter("idZU", idZU);

			hql = null;
			List<SelectItem> list = qo.list();
			qo = null;

			return list;
		} catch (Exception e) {
			this.getSession().close();
			ErrorNotificacion.handleErrorMailNotification(e, "system");
			return null;
		} finally {
			hql = null;
		}
	}
}