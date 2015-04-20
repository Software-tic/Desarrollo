package com.zyos.core.login.model;

import java.util.ArrayList;



import org.hibernate.Query;

import com.zyos.core.common.api.IZyosState;
import com.zyos.core.connection.OracleBaseHibernateDAO;

/**
 * A data access object (DAO) providing persistence and search support for Tree
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.zyos.pring.lo.mu.login.model.Tree
 * @author MyEclipse Persistence Tools
 */

public class TreeDAO extends OracleBaseHibernateDAO {
	

	// property constants
	public void save(Tree transientInstance) {
		
		try {
			getSession().save(transientInstance);

		} catch (RuntimeException re) {

			throw re;
		}
	}

	public ArrayList<Tree> loadLeaf(Long id, Long idZG, Long idE) {
		StringBuilder hql = new StringBuilder();
		try {
			hql.append("select ");
			hql.append("distinct   t     ");
			hql.append("from ");
			hql.append("Tree t, ");
			hql.append("Leaf l, ");
			hql.append("ZyosGroupTree zgt, ");
			hql.append("TreeEnterprise te ");
			hql.append("where ");
			hql.append("l.idBranch =  :idB   ");
			hql.append("and l.state = :state ");
			hql.append("and l.idLeaf = t.id ");
			hql.append("and t.state =:state ");
			hql.append("and t.id = te.idTree ");
			hql.append("and te.idEnterprise = :idE ");
			hql.append("and te.state =:state ");
			hql.append("and t.id = zgt.idTree ");
			hql.append("and zgt.idZyosGroup = :idZG ");
			hql.append("order by ");
			hql.append("t.label asc ");

			Query qo = null;
			qo = getSession().createQuery(hql.toString());
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE);
			qo.setParameter("idB", id);
			qo.setParameter("idZG", idZG);
			qo.setParameter("idE", idE);

			hql = null;
			ArrayList<Tree> list = (ArrayList<Tree>) qo.list();
			qo = null;
			if (list.size() > 0) {
				return list;
			} else {
				return null;
			}

		} catch (Exception e) {
			return null;
		}
	}
}