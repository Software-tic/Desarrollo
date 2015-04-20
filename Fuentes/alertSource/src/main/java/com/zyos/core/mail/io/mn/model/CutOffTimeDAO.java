package com.zyos.core.mail.io.mn.model;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;



import org.hibernate.Query;

import com.zyos.core.common.api.IZyosState;
import com.zyos.core.common.controller.ErrorNotificacion;
import com.zyos.core.connection.OracleBaseHibernateDAO;

/**
 * A data access object (DAO) providing persistence and search support for
 * CutOffTime entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.zyos.core.io.mn.model.CutOffTime
 * @author Óscar Garzón
 */

public class CutOffTimeDAO extends OracleBaseHibernateDAO {
	

	// property constants

	public void save(CutOffTime transientInstance) {
		try {
			getSession().save(transientInstance);

		} catch (RuntimeException re) {

			throw re;
		}
	}

	public void delete(CutOffTime persistentInstance) {
		
		try {
			getSession().delete(persistentInstance);

		} catch (RuntimeException re) {

			throw re;
		}
	}

	public ArrayList<Long> getListCutOffTimeByUser(Long idZU) {
		StringBuilder hql = new StringBuilder();
		try {
			hql.append(" select ");
			hql.append(" n.idCutOffTime   ");
			hql.append(" from ");
			hql.append(" Notification n   ");
			hql.append(" where ");
			hql.append(" n.idZyosUser = :idZU  ");
			hql.append(" and  n.state = :state   ");

			Query qo = null;
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE);
			qo.setParameter("idZU", idZU);
			hql = null;
			List list = null;
			list = qo.list();
			qo = null;
			return (ArrayList<Long>) list;

		} catch (Exception e) {
			this.getSession().close();
			ErrorNotificacion.handleErrorMailNotification(e, "system");
			return new ArrayList<Long>();
		}
	}

	public List<SelectItem> loadCutOffTimeList() {
		return loadLikeSelectItem("CutOffTime");
	}
}