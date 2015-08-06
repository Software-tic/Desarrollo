package com.zyos.alert.facultyDegree.model;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zyos.core.common.api.IZyosState;
import com.zyos.core.common.util.ManageDate;
import com.zyos.core.connection.OracleBaseHibernateDAO;

/**
 * A data access object (DAO) providing persistence and search support for
 * FacultyDegree entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.zyos.alert.absent.model.FacultyDegree
 * @author MyEclipse Persistence Tools
 */
public class FacultyDegreeDAO extends OracleBaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(FacultyDegreeDAO.class);

	public void save(FacultyDegree transientInstance) {
		log.debug("saving FacultyDegree instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(FacultyDegree persistentInstance) {
		log.debug("deleting FacultyDegree instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public List<FacultyDegree> migrateFacultyDegreeListFromSAC() throws Exception {
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select DISTINCT g.idFacultad as idFaculty,  cast(g.idCarrera as long) as idDegree ");
			hql.append("from ");
			hql.append("CarrerasDocentesFacultades g ");
			hql.append("where ");
			hql.append("(g.idFacultad || '_' || g.idCarrera) not in (select (gs.idFaculty || '_' || cast(gs.idDegree as string)) from FacultyDegree gs ) ");
			
			Query qo = getSession().createQuery(hql.toString()).setResultTransformer(Transformers.aliasToBean(FacultyDegree.class)); 
			
			return  qo.list();
		} catch (Exception e) {
			throw e;
		}
	}
	
	public List<FacultyDegree> loadFacultyDegreeList(){
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append("select fd.id,f.id, d.id "
					+ "from Degree d, Faculty f, FacultyDegree fd "
					+ "where "
					+ "(f.id || '_' || d.id || '_' || fd.id) in (select (gs.idFaculty || '_' || gs.idDegree || '_' || gs.id) from FacultyDegree gs where gs.state = :state) "
					+ "AND f.state = :state AND d.state = :state");

			qo = getSession().createQuery(hql.toString());		
			qo.setParameter("state", IZyosState.ACTIVE);
			
			List<FacultyDegree> selectedFacultyDegreeList = qo.list();
			
			for (FacultyDegree fd : selectedFacultyDegreeList) {
				System.out.println(fd.getUserChange());
			}
			return qo.list();
				
		} catch (RuntimeException re) {
			throw re;
		} finally {
			hql = null;
			qo = null;
		}
	}
	
	public void changeStateFacultyDegree(String idFacultyDegreeList, String documentNumber,Long state) {
		StringBuilder hql = new StringBuilder();
		Query qo = null;
		try {
			hql.append("update FacultyDegree ");
			hql.append("set state = :state ,");
			hql.append("userChange = :userChange ,");
			hql.append("dateChange = :newdateChange ");
			hql.append("where idfaculty in (");
			hql.append(idFacultyDegreeList);
			hql.append(" )");

			qo = getSession().createQuery(hql.toString());

			qo.setParameter("state", state);
			qo.setParameter("userChange", documentNumber);
			qo.setParameter("newdateChange",ManageDate.getCurrentDate(ManageDate.YYYY_MM_DD_HH_MM_SS));

			qo.executeUpdate();
		} catch (RuntimeException e) {
			throw e;
		} finally {
			hql = null;
			qo = null;
		}
	}
	
}