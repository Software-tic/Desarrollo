package com.zyos.core.connection;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.hibernate.Query;
import org.hibernate.Session;

import com.zyos.core.common.api.IZyosState;
import com.zyos.core.common.controller.ErrorNotificacion;
import com.zyos.core.common.util.ManageDate;

/**
 * Data access object (DAO) for domain model
 * 
 * @author MyEclipse Persistence Tools
 */
public class OracleBaseHibernateDAO implements IOracleBaseHibernateDAO {

	public Session getSession() {
		return HibernateSessionFactory.getOracleSession();
	}

	/**
	 * Method for creating select item for adding a new zyos select item
	 * 
	 * @param o
	 * @param si
	 */
	public void createSelectItem(Object[] o, List<SelectItem> si) {
		try {
			if (o[2] != null && o[3] != null) {
				si.add(new SelectItem(o[2], o[3].toString(), o.length > 4
						&& o[4] != null && o[4] != "" ? o[4].toString() : null));
			}
		} catch (Exception e) {
			ErrorNotificacion.handleErrorMailNotification(e, "system");
		}
	}

	/**
	 * Method for loading the basic select items
	 * 
	 * @param object
	 * @return
	 */
	public List<SelectItem> loadLikeSelectItem(String object) {
		try {
			StringBuilder hql = new StringBuilder(
					"select distinct new javax.faces.model.SelectItem(i.id, i.name, i.description) from ");
			hql.append(object);
			hql.append(" i where i.state = :state order by i.name  ");
			Query qo = null;
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE);
			hql = null;
			return qo.list();
		} catch (Exception e) {
			getSession().beginTransaction().rollback();
			getSession().cancelQuery();
			this.getSession().close();
			return new ArrayList<SelectItem>();
		}
	}

	/**
	 * Method for loading the basic select items order by Id
	 * 
	 * @param object
	 * @return
	 */
	public List<SelectItem> loadLikeSelectItemOrderById(String object) {
		try {
			StringBuilder hql = new StringBuilder(
					"select distinct new javax.faces.model.SelectItem(i.id, i.name) from "
							+ object
							+ " i where i.state = :state order by i.id  ");
			Query qo = null;
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE);
			hql = null;
			return qo.list();
		} catch (Exception e) {
			getSession().beginTransaction().rollback();
			getSession().cancelQuery();
			this.getSession().close();
			return new ArrayList<SelectItem>();
		}
	}

	/**
	 * Method for changing the state of a lot of elements as the selected zyos
	 * model
	 * 
	 * @param className
	 * @param idZyosModelListLikeString
	 *            the format is (idOne,idTwo, ... , idN)
	 * @param documentNumber
	 * @param state
	 */
	public void changeStateZyosModel(String className,
			String idZyosModelListLikeString, String documentNumber, Long state) {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append("update ");
			hql.append(className);
			hql.append(" set state = :state ,");
			hql.append("userChange = :userChange ,");
			hql.append("dateChange = :newdateChange ");
			hql.append("where id in (");
			hql.append(idZyosModelListLikeString);
			hql.append(" )");

			qo = getSession().createQuery(hql.toString());

			qo.setParameter("state", state);
			qo.setParameter("userChange", documentNumber);
			qo.setParameter("newdateChange",
					ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD));

			qo.executeUpdate();
		} catch (RuntimeException e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}

	public static Boolean booleanOf(Object object) {
		try {
			return Boolean.valueOf(object.toString());
		} catch (Exception e) {
			return false;
		}
	}

	public static String strOf(Object object) {
		try {
			return object.toString();
		} catch (Exception e) {
			return "";
		}
	}

	public static Long longOf(Object object) {
		try {
			return Long.valueOf(object.toString());
		} catch (Exception e) {
			return 0L;
		}
	}

	public static Integer intOf(Object object) {
		try {
			return Integer.valueOf(object.toString());
		} catch (Exception e) {
			return 0;
		}
	}

	public static Double doubleOf(Object object) {
		try {
			return Double.valueOf(object.toString());
		} catch (Exception e) {
			return 0.0;
		}
	}

}