package com.zyos.core.common.model;

import java.util.List;

import org.hibernate.Query;

import com.zyos.core.common.api.IZyosState;
import com.zyos.core.connection.OracleBaseHibernateDAO;

/**
 * A data access object (DAO) providing persistence and search support for
 * DocumentType entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zyos.core.common.model.DocumentType
 * @author MyEclipse Persistence Tools
 */

public class DocumentTypeDAO extends OracleBaseHibernateDAO {

	public void save(DocumentType transientInstance) {
		try {
			getSession().save(transientInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public List<DocumentType> loadDocumentTypeList() throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append("from DocumentType en ");
			hql.append("where en.state =:state ");

			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE);

			return qo.list();
		} catch (Exception e) {
			throw e;
		}
	}
}