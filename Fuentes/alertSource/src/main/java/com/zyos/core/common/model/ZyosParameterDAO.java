package com.zyos.core.common.model;

import java.util.List;

import org.hibernate.Query;

import com.zyos.alert.facultyDegree.model.FacultyDegree;
import com.zyos.core.common.model.AParameter;
import com.zyos.core.common.api.IZyosState;
import com.zyos.alert.faculty.model.Faculty;
import com.zyos.core.connection.OracleBaseHibernateDAO;

/**
 * A data access object (DAO) providing persistence and search support for
 * Parameter entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.zyos.core.common.model.Parameter
 * @author MyEclipse Persistence Tools
 */

public class ZyosParameterDAO extends OracleBaseHibernateDAO {

	public void save(ZyosParameter transientInstance) {
		try {
			getSession().save(transientInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public List<ZyosParameter> loadParameterList() {
		try {
			StringBuilder hql = new StringBuilder(
					"from ZyosParameter i where i.state = :state and globalParameter = 1 order by i.name  ");
			Query qo = null;
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE);
			hql = null;
			return qo.list();
		} catch (RuntimeException e) {
			throw e;
		}
	}

	// --------------Degree----------------------------------------------------------------------------------
	public List<FacultyDegree> loadParameterListdegree() {
		try {
			StringBuilder hql = new StringBuilder(
					"select new FacultyDegree(fd.id,f.id,f.name,d.id,d.name,d.description) "
							+ " from Degree d, Faculty f, FacultyDegree fd where fd.idFaculty=f.id AND fd.idDegree = d.id "
							+ "AND f.state = :state AND d.state = :state AND fd.state= :state");

			Query qo = null;
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE);
			hql = null;
			return qo.list();
		} catch (RuntimeException e) {
			throw e;
		}
	}

	public List<Faculty> loadFacultyByDegree(Long IdDegree) {
		try {
			StringBuilder hql = new StringBuilder(
					"SELECT new Faculty(f.id,f.name) "
							+ "FROM Degree d, Faculty f, FacultyDegree fd WHERE fd.idFaculty=f.id AND fd.idDegree = d.id "
							+ "AND f.state = :state AND d.state = :state AND fd.state= :state AND d.id= :IdDegree");

			Query qo = null;
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE);
			qo.setParameter("IdDegree", IdDegree);
			hql = null;
			return qo.list();
		} catch (RuntimeException e) {
			throw e;
		}
	}

	public List<Faculty> loadFacultyList() {
		try {
			StringBuilder hql = new StringBuilder(
					"SELECT new Faculty(f.id,f.name) FROM Faculty f WHERE f.state = :state");

			Query qo = null;
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE);
			hql = null;
			return qo.list();
		} catch (RuntimeException e) {
			throw e;
		}
	}

	// --------------Degree----------------------------------------------------------------------------------
	public List<AParameter> loadParameterList(Long idEnterprise,
			String nameClass, long globalParameter) {
		try {
			StringBuilder hql = new StringBuilder("from ");
			hql.append(nameClass);
			hql.append(" i where i.state = :state ");
			if (globalParameter == 0)
				hql.append("and idEnterprise =:idEnterprise ");
			hql.append("order by i.name  ");
			Query qo = null;
			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE);
			if (globalParameter == 0)
				qo.setParameter("idEnterprise", idEnterprise);
			hql = null;
			return qo.list();
		} catch (RuntimeException e) {
			throw e;
		}
	}

	public List<AParameter> loadParameter(Long idParameter) {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append("select id from ");
			hql.append("degree zp ");
			hql.append("where ");
			hql.append("zp.id = :idPa   ");

			qo = getSession().createQuery(hql.toString());

			qo.setParameter("idPa", idParameter);

			return qo.list();
		} catch (RuntimeException e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}

	public AParameter validateDegreeUsed(Long idDegree) throws Exception {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {

			hql.append(" SELECT distinct d");
			hql.append(" FROM ");
			hql.append(" StudentDegree sd, Degree d ");
			hql.append(" WHERE ");
			hql.append(" sd.idDegree = d.id and ");
			hql.append(" sd.idDegree IN :idDegree ");
			hql.append(" AND  sd.state =:state ");
			hql.append(" AND  d.state =:state ");

			qo = getSession().createQuery(hql.toString());
			qo.setParameter("state", IZyosState.ACTIVE);
			qo.setParameter("idDegree", idDegree);

			AParameter result = (AParameter) qo.uniqueResult();

			return result;

		} catch (Exception e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}
}