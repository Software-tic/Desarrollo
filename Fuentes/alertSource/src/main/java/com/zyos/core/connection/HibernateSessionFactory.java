package com.zyos.core.connection;

import javax.faces.application.Application;
import javax.faces.application.ProjectStage;
import javax.faces.context.FacesContext;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.zyos.core.common.controller.ErrorNotificacion;
import com.zyos.core.common.controller.ZyosController;

/**
 * Configures and provides access to Hibernate sessions, tied to the current
 * thread of execution. Follows the Thread Local Session pattern, see
 * {@link http://hibernate.org/42.html }.
 */
public class HibernateSessionFactory {

	/**
	 * Location of hibernate.cfg.xml file. Location should be on the classpath
	 * as Hibernate uses #resourceAsStream style lookup for its configuration
	 * file. The default classpath location of the hibernate config file is in
	 * the default package. Use #setConfigFile() to update the location of the
	 * configuration file for the current session.
	 */
    
	private static String CONFIG_ORACLE_FILE_LOCATION = "/com/zyos/core/connection/hibernate.cfg.oracle.xml";
	private static final ThreadLocal<Session> OracleThreadLocal = new ThreadLocal<Session>();
    private static Configuration OracleConfiguration = new Configuration();
	private static org.hibernate.SessionFactory OracleSessionFactory;
	private static String OracleConfigFile = CONFIG_ORACLE_FILE_LOCATION;
    private static ServiceRegistry OracleServiceRegistry; 

	private static String CONFIG_MYSQL_FILE_LOCATION = "/com/zyos/core/connection/hibernate.cfg.mysql.xml";
	private static final ThreadLocal<Session> MySQLThreadLocal = new ThreadLocal<Session>();
    private static Configuration MySQLConfiguration = new Configuration();
	private static org.hibernate.SessionFactory MySQLSessionFactory;
	private static String MySQLConfigFile = CONFIG_MYSQL_FILE_LOCATION;
    private static ServiceRegistry MySQLServiceRegistry;

	static {
		try {
			if (!System.getProperty("os.name").toLowerCase().startsWith("win") && !System.getProperty("os.name").toLowerCase().startsWith("mac")) {
				OracleConfigFile = "/com/zyos/core/connection/hibernate.cfg.oracle.server.xml";
				MySQLConfigFile = "/com/zyos/core/connection/hibernate.cfg.mysql.server.xml";			
			}
			
			OracleConfiguration.configure(OracleConfigFile);
			OracleServiceRegistry = new ServiceRegistryBuilder().applySettings(OracleConfiguration.getProperties()).buildServiceRegistry();
			OracleSessionFactory = OracleConfiguration.buildSessionFactory(OracleServiceRegistry);
			
				try {
					MySQLConfiguration.configure(MySQLConfigFile);
					
					String MySQL_URL = MySQLConfiguration.getProperty("connection.url");
					
					ZyosController controller = new ZyosController();
					String academicPeriodName = controller.loadCurrentAcademicPeriodName();
					
					MySQLConfiguration.setProperty("connection.url", MySQL_URL.substring(0, MySQL_URL.indexOf("moodlepre"))+ "moodlepre"+academicPeriodName);
					
					System.out.println("INFO: setting mysql moodle database "+  MySQL_URL.substring(0, MySQL_URL.indexOf("moodlepre"))+ "moodlepre"+ academicPeriodName);
					MySQLServiceRegistry = new ServiceRegistryBuilder().applySettings(MySQLConfiguration.getProperties()).buildServiceRegistry();
					
					MySQLSessionFactory = MySQLConfiguration.buildSessionFactory(MySQLServiceRegistry);
				} catch (Exception e) {
					System.err.println("%%%% Error Creating SessionFactory MOODLE %%%%");
				}
				
		} catch (Exception e) {
			System.err.println("%%%% Error Creating SessionFactory %%%%");
		}
	}

	/**
	 * ogarzonm 31/07/2014
	 * @return
	 */
	public static boolean isPostgreSQLConnection(){
		try{
			String p = getOracleConfiguration().getProperty("connection.url");
			if(p.startsWith("jdbc:postgresql"))
				return true;
		}catch(Exception e){
			System.err.println("%%%% Error Creating SessionFactory %%%%");
		}
		return false;
	}
	
	private HibernateSessionFactory() {
	}

	/**
	 * Returns the ThreadLocal Session instance. Lazy initialize the
	 * <code>SessionFactory</code> if needed.
	 * 
	 * @return Session
	 * @throws HibernateException
	 */
	public static Session getOracleSession() throws HibernateException {
		Session session = OracleThreadLocal.get();
		
		if (session == null || !session.isOpen()) {
			if (OracleSessionFactory == null) {
				rebuildOracleSessionFactory();
			}
			session = (OracleSessionFactory != null) ? OracleSessionFactory.openSession() : null;
			OracleThreadLocal.set(session);
		}
		return session;
	}

	public static Session getMySQLSession() throws HibernateException {
		Session session = MySQLThreadLocal.get();
		
		if (session == null || !session.isOpen()) {
			if (MySQLSessionFactory == null) {
				rebuildMySQLSessionFactory();
			}
			session = (MySQLSessionFactory != null) ? MySQLSessionFactory.openSession() : null;
			MySQLThreadLocal.set(session);
		}
		return session;
	}
	
	/**
	 * Rebuild hibernate session factory
	 * 
	 */
	public static void rebuildOracleSessionFactory() {
		try {
			validateProductionState();
			
			OracleConfiguration.configure(OracleConfigFile);
			OracleServiceRegistry = new ServiceRegistryBuilder().applySettings(OracleConfiguration.getProperties()).buildServiceRegistry();
			OracleSessionFactory = OracleConfiguration.buildSessionFactory(OracleServiceRegistry);
		} catch (Exception e) {
			System.err.println("%%%% Error Creating SessionFactory %%%%");
			ErrorNotificacion.handleErrorMailNotification(e, "rebuildSessionFactory");
		}
	}

	public static void rebuildMySQLSessionFactory() {
		try {
			validateProductionState();
			
			MySQLConfiguration.configure(MySQLConfigFile);
			MySQLServiceRegistry = new ServiceRegistryBuilder().applySettings(MySQLConfiguration.getProperties()).buildServiceRegistry();
			MySQLSessionFactory = MySQLConfiguration.buildSessionFactory(MySQLServiceRegistry);
		} catch (Exception e) {
			System.err.println("%%%% Error Creating SessionFactory %%%%");
			ErrorNotificacion.handleErrorMailNotification(e, "rebuildSessionFactory");
		}
	}

	private static void validateProductionState() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Application application = facesContext.getApplication();
		if (application.getProjectStage() == ProjectStage.Development) {
			OracleConfigFile = "/com/zyos/core/connection/hibernate.cfg.oracle.server.xml";
			MySQLConfigFile = "/com/zyos/core/connection/hibernate.cfg.mysql.server.xml";			
		}
	}
	
	/**
	 * Close the single hibernate session instance.
	 * 
	 * @throws HibernateException
	 */
	public static void closeOracleSession() throws HibernateException {
		Session session = OracleThreadLocal.get();
		OracleThreadLocal.set(null);

		if (session != null) {
			session.close();
		}
	}
	
	public static void closeMySQLSession() throws HibernateException {
		Session session = MySQLThreadLocal.get();
		MySQLThreadLocal.set(null);

		if (session != null) {
			session.close();
		}
	}

	/**
	 * return session factory
	 * 
	 */
	public static org.hibernate.SessionFactory getOracleSessionFactory() {
		return OracleSessionFactory;
	}

	public static org.hibernate.SessionFactory getMySQLSessionFactory() {
		return MySQLSessionFactory;
	}

	
	/**
	 * return session factory
	 * 
	 * session factory will be rebuilded in the next call
	 */
	public static void setOracleConfigFile(String configFile) {
		HibernateSessionFactory.OracleConfigFile = configFile;
		OracleSessionFactory = null;
	}
	
	public static void setMySQLConfigFile(String configFile) {
		HibernateSessionFactory.MySQLConfigFile = configFile;
		MySQLSessionFactory = null;
	}

	/**
	 * return hibernate configuration
	 * 
	 */
	public static Configuration getOracleConfiguration() {
		return OracleConfiguration;
	}
	
	public static Configuration getMySQLConfiguration() {
		return MySQLConfiguration;
	}

}