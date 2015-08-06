package com.zyos.alert.studentReport.model;

import com.zyos.core.connection.OracleBaseHibernateDAO;
import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import static org.hibernate.criterion.Example.create;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 	* A data access object (DAO) providing persistence and search support for Reporttype entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.zyos.alert.studentReport.model.ReportType
  * @author MyEclipse Persistence Tools 
 */
public class ReportTypeDAO extends OracleBaseHibernateDAO  {
	     private static final Logger log = LoggerFactory.getLogger(ReportTypeDAO.class);
	

    
    public void save(ReportType transientInstance) {
        log.debug("saving Reporttype instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(ReportType persistentInstance) {
        log.debug("deleting Reporttype instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public ReportType findById( java.lang.Long id) {
        log.debug("getting Reporttype instance with id: " + id);
        try {
            ReportType instance = (ReportType) getSession()
                    .get("com.zyos.alert.studentReport.model.Reporttype", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List<ReportType> findByExample(ReportType instance) {
        log.debug("finding Reporttype instance by example");
        try {
            List<ReportType> results = getSession()
                    .createCriteria("com.zyos.alert.studentReport.model.Reporttype")
                    .add( create(instance) )
            .list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }    
    
    public List findByProperty(String propertyName, Object value) {
      log.debug("finding Reporttype instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from Reporttype as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}


	public List findAll() {
		log.debug("finding all Reporttype instances");
		try {
			String queryString = "from Reporttype";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public ReportType merge(ReportType detachedInstance) {
        log.debug("merging Reporttype instance");
        try {
            ReportType result = (ReportType) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(ReportType instance) {
        log.debug("attaching dirty Reporttype instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(ReportType instance) {
        log.debug("attaching clean Reporttype instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}