package com.zyos.core.lo.enterprise.model;

import java.util.List;

import javax.faces.model.SelectItem;

import org.hibernate.Query;

import com.zyos.core.common.api.IZyosState;
import com.zyos.core.common.controller.ErrorNotificacion;
import com.zyos.core.common.model.AZyosModel;
import com.zyos.core.connection.OracleBaseHibernateDAO;

/**
 * A data access object (DAO) providing persistence and search support for
 * Enterprise entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.zyos.core.lo.enterprise.model.Enterprise
 * @author Óscar Garzón
 */

public class EnterpriseDAO extends OracleBaseHibernateDAO {

	public void save(Enterprise transientInstance) {

		try {
			getSession().save(transientInstance);

		} catch (RuntimeException re) {

			throw re;
		}
	}

	public void delete(Enterprise persistentInstance) {

		try {
			getSession().delete(persistentInstance);

		} catch (RuntimeException re) {

			throw re;
		}
	}

	public Enterprise getDataByArea(Long idEn) {
		StringBuilder hql = new StringBuilder();
		try {
			hql.append("select ");
			hql.append("new Enterprise(a.id, a.idEnterprise,a.address, a.name, a.idBank, a.bankNumberCount) ");
			hql.append("from Enterprise a   ");
			hql.append("where a.id = :idEn ");
			Query qo = null;
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("idEn", idEn);

			hql = null;
			qo.setMaxResults(1);
			return (Enterprise) qo.uniqueResult();

		} catch (Exception e) {
			this.getSession().close();
			ErrorNotificacion.handleErrorMailNotification(e, "system");
			return null;
		} finally {
			hql = null;
		}
	}

	public List<SelectItem> loadEnterpriseList(Long active) {
		return loadLikeSelectItem("Enterprise");
	}

	public List<Enterprise> loadEnterpriseList() throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append("from Enterprise en ");
			hql.append("where en.state =:state ");

			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE);

			return qo.list();
		} catch (Exception e) {
			throw e;
		}
	}

	public Enterprise loadDataEnterprise(Long idEnterprise) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {

			hql.append("select new Enterprise(en.id, en.idEnterprise, ");
			hql.append("en.name, en.phone, en.address, en.description) ");
			hql.append("from Enterprise en  ");
			hql.append("where en.state =:state and en.id =:idEnt  ");

			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE);
			qo.setParameter("idEnt", idEnterprise);
			qo.setMaxResults(1);
			return (Enterprise) qo.uniqueResult();

		} catch (Exception e) {
			throw e;
		}
	}

	public void updateEnterprise(Enterprise enterprise) throws Exception {

		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append("update Enterprise ");
			hql.append("set state =:state, idEnterprise =:idEnterprise, name =:name, description =:description, ");
			hql.append("address =:address,  phone =:phone ");
			hql.append("where id =:idEnt");

			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE);
			qo.setParameter("idEnterprise", enterprise.getIdEnterprise());
			qo.setParameter("name", enterprise.getName());
			qo.setParameter("description", enterprise.getDescription());
			qo.setParameter("address", enterprise.getAddress());
			qo.setParameter("phone", enterprise.getPhone());
			qo.setParameter("idEnt", enterprise.getId());

			qo.executeUpdate();

		} catch (Exception e) {

			throw e;
		}

	}

	public Enterprise validateNIT(String idEnterprise) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append("select new Enterprise(en.id, en.idEnterprise, ");
			hql.append("en.name, en.phone, en.address, en.description) ");
			hql.append("from Enterprise en  ");
			hql.append("where en.state =:state and en.idEnterprise =:idEnterprise  ");

			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE);
			qo.setParameter("idEnterprise", idEnterprise);
			qo.setMaxResults(1);
			return (Enterprise) qo.uniqueResult();

		} catch (Exception e) {

			throw e;
		}
	}

	public void deleteEnterprise(Enterprise enterprise) throws Exception {

		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append("update Enterprise ");
			hql.append("set state =:state ");
			hql.append("where id =:idEnt");

			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.INACTIVE);
			qo.setParameter("idEnt", enterprise.getId());

			qo.executeUpdate();
		} catch (Exception e) {
			throw e;
		}
	}

	public List<AZyosModel> loadAZyosModelObjectList(String object, Long state)
			throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append("from " + object
					+ " o where o.state >=:state order by o.name");

			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", state);

			return qo.list();
		} catch (Exception e) {
			throw e;
		}
	}

}