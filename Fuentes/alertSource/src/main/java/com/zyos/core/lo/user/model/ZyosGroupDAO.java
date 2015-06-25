package com.zyos.core.lo.user.model;

import java.util.List;

import javax.faces.model.SelectItem;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zyos.core.common.api.IZyosState;
import com.zyos.core.connection.OracleBaseHibernateDAO;
import com.zyos.core.login.model.ZyosGroupTree;
import com.zyos.core.login.model.ZyosGroupTreeDAO;

/**
 * A data access object (DAO) providing persistence and search support for
 * ZyosGroup entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.zyos.core.lo.user.model.ZyosGroup
 * @author MyEclipse Persistence Tools
 */

public class ZyosGroupDAO extends OracleBaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(ZyosGroupDAO.class);
	private static final Logger logTree = LoggerFactory
			.getLogger(ZyosGroupTreeDAO.class);

	public void save(ZyosGroup transientInstance) {
		log.debug("saving ZyosGroup instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ZyosGroup persistentInstance) {
		log.debug("deleting ZyosGroup instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public List<SelectItem> loadZyosGroupList(Long active) {
		return loadLikeSelectItem("ZyosGroup");
	}

	public List<ZyosGroup> findAll() {
		try {
			String queryString = "from ZyosGroup where state > 0 order by id";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public List<ZyosGroupTree> loadAndBuildTreeEnterpriseStructure(
			Long idEnterprise) throws Exception {
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select new ZyosGroupTree(t.id, t.state) ");
			hql.append("from ");
			hql.append("Tree t, TreeEnterprise te ");
			hql.append("where ");
			hql.append("t.state = :state ");
			hql.append("and t.id = te.idTree ");
			hql.append("and te.idEnterprise =:idEnterprise ");

			Query qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE);
			qo.setParameter("idEnterprise", idEnterprise);
			return qo.list();
		} catch (Exception e) {
			throw e;
		}
	}

	public List<ZyosGroupTree> loadZyosGroupFunctionalityListByEnterprise()
			throws Exception {
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select new ZyosGroupTree(t.id, te.idTree, te.idEnterprise, t.idZyosGroup, t.functionalityList, tr.bean) ");
			hql.append("from ");
			hql.append("ZyosGroupTree t, TreeEnterprise te, Tree tr ");
			hql.append("where ");
			hql.append("tr.state = :state ");
			hql.append("and t.idTree = te.idTree ");
			hql.append("and t.idTree = tr.id ");
			hql.append("order by te.idEnterprise ");

			Query qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE);
			return qo.list();
		} catch (Exception e) {
			throw e;
		}
	}

	public void save(ZyosGroupTree transientInstance) {
		logTree.debug("saving ZyosGroupTree instance");
		try {
			getSession().save(transientInstance);
			logTree.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
}