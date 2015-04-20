package com.zyos.core.mail.io.mn.model;

import java.util.List;



import org.hibernate.Query;

import com.zyos.core.common.api.IZyosState;
import com.zyos.core.connection.OracleBaseHibernateDAO;

/**
 * A data access object (DAO) providing persistence and search support for
 * EmailConfiguration entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zyos.core.mail.io.mn.model.EmailConfiguration
 * @author Óscar Garzón
 */

public class EmailConfigurationDAO extends OracleBaseHibernateDAO {
	
			

	public void save(EmailConfiguration transientInstance) {
		
		try {
			getSession().save(transientInstance);
			
		} catch (RuntimeException re) {
			
			throw re;
		}
	}

	public void delete(EmailConfiguration persistentInstance) {
		
		try {
			getSession().delete(persistentInstance);
			
		} catch (RuntimeException re) {
			
			throw re;
		}
	}

	public List<EmailConfiguration> loadEmailConfigurationList() throws Exception {
		StringBuilder hql = new StringBuilder();
		try {
			hql.append("select new EmailConfiguration(id, idEnterprise, smtpHost, tlsEnable, port, mailUser, mailPassword, auth) from EmailConfiguration where state = :state order by idEnterprise  ");
			Query qo = null;
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE);
			hql = null;
			return qo.list();
		} catch (Exception e) {
			throw e;
		}
	}
}