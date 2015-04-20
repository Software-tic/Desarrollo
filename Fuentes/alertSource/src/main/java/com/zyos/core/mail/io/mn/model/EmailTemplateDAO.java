package com.zyos.core.mail.io.mn.model;

import java.util.List;

import org.hibernate.Query;

import com.zyos.core.common.api.IZyosState;
import com.zyos.core.common.controller.ErrorNotificacion;
import com.zyos.core.connection.OracleBaseHibernateDAO;

/**
 * A data access object (DAO) providing persistence and search support for
 * EmailTemplate entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zyos.mail.io.mn.model.EmailTemplate
 * @author MyEclipse Persistence Tools
 */

public class EmailTemplateDAO extends OracleBaseHibernateDAO {

	public void save(EmailTemplate transientInstance) {
		try {
			getSession().save(transientInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public void delete(EmailTemplate persistentInstance) {
		try {
			getSession().delete(persistentInstance);
		} catch (RuntimeException re) {

			throw re;
		}
	}

	public List<EmailTemplate> loadEmailTemplateList() {
		StringBuilder hql = new StringBuilder();
		try {
			hql.append("from ");
			hql.append("EmailTemplate ");
			hql.append("where ");
			hql.append("state = :state ");
			hql.append("order by name");

			Query qo = null;
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE);

			return qo.list();
		} catch (RuntimeException e) {
			throw e;
		} finally {
			hql = null;
		}
	}

	public boolean editTemplate(EmailTemplate et) {
		StringBuilder hql = new StringBuilder();
		try {
			hql.append("update EmailTemplate e set e.body =:body, e.subject =:subject, e.name=:name, e.dateChange =:dateChange, e.userChange=:userChange where e.id = :idET");
			Query qo = null;
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("idET", et.getId());
			qo.setParameter("body", et.getBody());
			qo.setParameter("name", et.getName());
			qo.setParameter("subject", et.getSubject());
			qo.setParameter("dateChange", et.getDateChange());
			qo.setParameter("userChange", et.getUserChange());

			return qo.executeUpdate() > 0 ? true : false;
		} catch (Exception e) {
			this.getSession().close();
			ErrorNotificacion.handleErrorMailNotification(e, "system");
			return false;
		} finally {
			hql = null;
		}
	}

	public List<EmailTemplate> findAll() throws Exception {
		StringBuilder hql = new StringBuilder();
		try {
			hql.append(" select new EmailTemplate(p.id, p.dateCreation, p.userCreation, ");
			hql.append(" p.dateChange, p.userChange, p.state, p.name, ");
			hql.append(" p.idEmailTemplateType, p.body, p.subject, ");
			hql.append(" p.analyticsCode, p.idEnterprise, et.name) ");
			hql.append(" from EmailTemplate p , EmailTemplateType et where ");
			hql.append(" p.state = :state and p.idEmailTemplateType = et.id order by p.idEnterprise, p.name asc");
			Query qo = null;
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE);
			
			hql = null;
			return qo.list();
		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
		}
	}
}