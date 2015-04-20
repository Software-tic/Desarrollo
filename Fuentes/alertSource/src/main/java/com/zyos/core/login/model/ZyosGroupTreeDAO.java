package com.zyos.core.login.model;

import java.util.List;

import org.hibernate.Query;

import com.zyos.core.common.api.IZyosState;
import com.zyos.core.common.util.ManageDate;
import com.zyos.core.connection.OracleBaseHibernateDAO;
import com.zyos.session.common.User;

/**
 * A data access object (DAO) providing persistence and search support for
 * ZyosGroupTree entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zyos.pring.lo.mu.login.model.ZyosGroupTree
 * @author MyEclipse Persistence Tools
 */

public class ZyosGroupTreeDAO extends OracleBaseHibernateDAO {

	public void save(ZyosGroupTree transientInstance) {
		try {
			getSession().save(transientInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public void delete(ZyosGroupTree persistentInstance) {
		try {
			getSession().delete(persistentInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public List<Tree> getTreeListByGroup(boolean isAdminTree) {
		StringBuilder hql = new StringBuilder();
		try {
			hql.append("select new Tree( ");
			hql.append("te.idEnterprise, zgt.idZyosGroup, ");
			hql.append("t.isBranch,l.idBranch, t.id, ");
			hql.append("t.label, t.description, t.icon, ");
			hql.append("t.displayPanel, t.expanded, t.orderTree, t.treeLevel, t.isAdmin) ");
			hql.append("from TreeEnterprise te,");
			hql.append("ZyosGroupTree zgt, Tree t, Leaf l ");
			hql.append("where ");
			hql.append("te.idTree = t.id ");
			hql.append("and zgt.idTree = t.id ");
			hql.append("and zgt.state = :state ");
			hql.append("and t.state = :state ");
			hql.append("and t.id = l.idLeaf ");
			if (isAdminTree)
				hql.append("and t.isAdmin = 1 ");
			hql.append("order by ");
			hql.append("te.idEnterprise, zgt.idZyosGroup, t.orderTree, l.idBranch, l.orderLeaf ");

			Query qo = null;
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE);

			hql = null;
			List<Tree> list = qo.list();
			qo = null;
			if (list.size() > 0)
				return list;
			else
				return null;

		} catch (Exception e) {
			this.getSession().close();
			return null;
		}
	}

	public List<ZyosGroupTree> loadTreeByZyosGroup(Long idEnterprise,
			Long level, Long idSelectedTree, Long idZyosGroup) throws Exception {
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select distinct new ZyosGroupTree(zgt.id, t.id, t.label, t.treeLevel,  t.isBranch, zgt.functionalityList, zgt.state) ");
			hql.append("from ");
			hql.append("ZyosGroupTree zgt, Tree t, TreeEnterprise te ");
			hql.append("where ");
			hql.append("zgt.idZyosGroup =:idZyosGroup ");
			hql.append("and zgt.idTree = te.idTree ");
			hql.append("and te.idEnterprise =:idEnterprise ");
			hql.append("and te.idTree = t.id ");
			hql.append("and t.treeLevel =:level ");
			hql.append("and t.state = :state ");
			if (idSelectedTree != null)
				hql.append("and t.id in (select l.idLeaf from Tree t, Leaf l where t.id =:idSelectedTree and t.id = l.idBranch) ");
			hql.append("order by t.id");
			Query qo = null;
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE);
			qo.setParameter("idZyosGroup", idZyosGroup);
			if (idSelectedTree != null)
				qo.setParameter("idSelectedTree", idSelectedTree);
			qo.setParameter("level", level);
			qo.setParameter("idEnterprise", idEnterprise);
			
			return qo.list();
		} catch (Exception e) {
			throw e;
		}
	}

	public void updateFuncionalityList(Long idZGT, String funcionalityList,
			User userSession) throws Exception {
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("update ZyosGroupTree set ");
			hql.append("dateChange = :datechange, ");
			hql.append("userChange = :userchange, ");
			hql.append("functionalityList = :functionalityList ");
			hql.append("where ");
			hql.append("id = :idZGT ");
			Query qo = null;
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("idZGT", idZGT);
			qo.setParameter("datechange", ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD_HH_MM_SS));
			qo.setParameter("userchange", userSession.getDocumentNumber());
			qo.setParameter("functionalityList", funcionalityList);
			
			qo.executeUpdate();
		} catch (Exception e) {
			throw e;
		}
	}

	public void handleZyosGroupLock(ZyosGroupTree zgt) throws Exception {
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("update ZyosGroupTree set ");
			hql.append("dateChange = :datechange, ");
			hql.append("userChange = :userchange, ");
			hql.append("state = :state ");
			hql.append("where ");
			hql.append("id = :idZGT ");
			Query qo = null;
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("idZGT", zgt.getId());
			qo.setParameter("datechange", ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD_HH_MM_SS));
			qo.setParameter("userchange", zgt.getUserChange());
			qo.setParameter("state", zgt.getState());
			
			qo.executeUpdate();
		} catch (Exception e) {
			throw e;
		}
	}
}