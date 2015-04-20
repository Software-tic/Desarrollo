package com.zyos.core.mail.io.mn.model;




import com.zyos.core.connection.OracleBaseHibernateDAO;

/**
 * A data access object (DAO) providing persistence and search support for
 * TypeNotification entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zyos.core.NotificationType.mn.model.TypeNotification
 * @author Óscar Garzón
 */

public class NotificationTypeDAO extends OracleBaseHibernateDAO {

	public void save(NotificationType transientInstance) {
		try {
			getSession().save(transientInstance);
			
		} catch (RuntimeException re) {
			
			throw re;
		}
	}

	public void delete(NotificationType persistentInstance) {
		try {
			getSession().delete(persistentInstance);
			
		} catch (RuntimeException re) {
			
			throw re;
		}
	}

}