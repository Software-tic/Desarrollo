package com.zyos.alert.inscriptionStudent.model;

import java.util.List;

import org.hibernate.Query;

import com.zyos.core.common.api.IZyosState;
import com.zyos.core.connection.OracleBaseHibernateDAO;
import com.zyos.core.lo.user.model.Profession;

public class InscriptionStudentDAO extends OracleBaseHibernateDAO {

	public List<Profession> loadProfessionList(Long idEnterprise) {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append(" FROM Profession p ");
			hql.append(" WHERE p.idEnterprise = :idEnterprise ");
			hql.append(" AND p.state = :state ");

			qo = getSession().createQuery(hql.toString());
			qo.setParameter("idEnterprise", idEnterprise);
			qo.setParameter("state", IZyosState.ACTIVE);
			return qo.list();
			
		} catch (RuntimeException re) {
			throw re;
		}
	}

}
