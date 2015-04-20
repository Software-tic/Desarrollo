
package com.zyos.core.common.list;

import java.io.File;
import java.util.List;

import com.zyos.alert.executionsHistorical.model.ExecutionsHistorical;
import com.zyos.alert.executionsHistorical.model.ExecutionsHistoricalDAO;
import com.zyos.alert.studentReport.model.Relationship;
import com.zyos.alert.studentReport.model.RelationshipDAO;
import com.zyos.alert.studentReport.model.RiskFactor;
import com.zyos.alert.studentReport.model.RiskFactorCategory;
import com.zyos.alert.studentReport.model.RiskFactorDAO;
import com.zyos.alert.studentReport.model.Stage;
import com.zyos.alert.studentReport.model.StageDAO;
import com.zyos.alert.studentReport.model.StatusReportStudent;
import com.zyos.alert.studentReport.model.StatusReportStudentDAO;
import com.zyos.alert.studentReport.model.Subject;
import com.zyos.alert.studentReport.model.SubjectDAO;
import com.zyos.core.common.controller.ZyosController;
import com.zyos.core.common.model.AZyosModel;
import com.zyos.core.common.model.DocumentType;
import com.zyos.core.common.model.DocumentTypeDAO;
import com.zyos.core.common.model.ZyosParameter;
import com.zyos.core.common.model.ZyosParameterDAO;
import com.zyos.core.common.util.auth.ZyosAuth;
import com.zyos.core.common.util.auth.ZyosAuthDAO;
import com.zyos.core.lo.enterprise.model.Enterprise;
import com.zyos.core.lo.enterprise.model.EnterpriseDAO;
import com.zyos.core.lo.enterprise.model.Settings;
import com.zyos.core.lo.enterprise.model.SettingsDAO;
import com.zyos.core.lo.user.model.Funcionality;
import com.zyos.core.lo.user.model.FuncionalityDAO;
import com.zyos.core.lo.user.model.Profession;
import com.zyos.core.lo.user.model.ProfessionDAO;
import com.zyos.core.lo.user.model.ZyosGroup;
import com.zyos.core.lo.user.model.ZyosGroupDAO;
import com.zyos.core.lo.user.model.ZyosUser;
import com.zyos.core.lo.user.model.ZyosUserDAO;
import com.zyos.core.login.controller.ZyosMenuController;
import com.zyos.core.login.model.ZyosGroupTree;
import com.zyos.core.mail.io.mn.model.EmailTemplate;
import com.zyos.core.mail.io.mn.model.EmailTemplateDAO;
import com.zyos.core.mail.io.mn.model.EmailTemplateType;
import com.zyos.core.mail.io.mn.model.EmailTemplateTypeDAO;

public class ControllerList extends ZyosController {

	private static final long serialVersionUID = 1L;

	public List<Enterprise> loadEnterpriseList() throws Exception {
		EnterpriseDAO dao = new EnterpriseDAO();
		try {
			return dao.loadEnterpriseList();
		} catch (Exception e) {
			dao.getSession().cancelQuery();
			dao.getSession().beginTransaction().rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	public List<AZyosModel> loadAZyosModelObjectList(String object, Long state)
			throws Exception {
		EnterpriseDAO dao = new EnterpriseDAO();
		try {
			return dao.loadAZyosModelObjectList(object, state);
		} catch (Exception e) {
			dao.getSession().cancelQuery();
			dao.getSession().beginTransaction().rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	public void loadSettingsByEnterprise(ControllerEnterpriseList cel) {
		SettingsDAO dao = new SettingsDAO();
		try {
			List<Settings> l = dao.loadSettingsByEnterprise();
			for (Settings s : l) {
				Long idE = s.getIdEnterprise();
				if (idE != null) {
					cel = BeanList.getCEL(cel, idE);
					if (cel != null)
						cel.setSettings(s);
				}
			}
		} catch (Exception e) {
			dao.getSession().cancelQuery();
			dao.getSession().close();

		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	public void loadMenuAndSubmenuByEnterprise(ControllerEnterpriseList cel) {
		ZyosMenuController controller = new ZyosMenuController();
		controller.loadMenuAndSubmenuByEnterprise();
	}

	public void validateFileStructure(Object value) {
		try {
			if (value != null) {
				createDirectory(value, "");
			}
		} catch (Exception e) {
			System.out.println("ERROR: Fail create defaul folder structure");
		}
	}

	public void createDirectory(Object value, String internalPath) throws Exception {
		try {
			File f = new File(BeanList.properties.getProperty("generalPath") + value + "/" + internalPath);
			if (!f.exists())
				f.mkdir();
		} catch (Exception e) {
			throw e;
		}
	}

	public List<ZyosParameter> loadParameterList() {
		ZyosParameterDAO dao = new ZyosParameterDAO();
		try {
			return dao.loadParameterList();
		} catch (RuntimeException e) {
			dao.getSession().beginTransaction().rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	public List<DocumentType> loadDocumentTypeList() throws Exception {
		DocumentTypeDAO dao = new DocumentTypeDAO();
		try {
			return dao.loadDocumentTypeList();
		} catch (RuntimeException e) {
			dao.getSession().beginTransaction().rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	public List<ZyosUser> loadZyosUserList() throws Exception {
		ZyosUserDAO dao = new ZyosUserDAO();
		try {
			return dao.loadZyosUserList();
		} catch (RuntimeException e) {
			dao.getSession().beginTransaction().rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	public List<ZyosGroup> loadZyosGroupList() {
		ZyosGroupDAO dao = new ZyosGroupDAO();
		try {
			return dao.findAll();
		} catch (RuntimeException e) {
			dao.getSession().beginTransaction().rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	public List<EmailTemplate> loadEmailTemplateList() throws Exception {
		EmailTemplateDAO dao = new EmailTemplateDAO();
		try {
			return dao.findAll();
		} catch (RuntimeException e) {
			dao.getSession().beginTransaction().rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	public List<EmailTemplateType> loadEmailTemplateTypeList() {
		EmailTemplateTypeDAO dao = new EmailTemplateTypeDAO();
		try {
			return dao.findAll();
		} catch (RuntimeException e) {
			dao.getSession().beginTransaction().rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	public List<ZyosGroupTree> loadZyosGroupFunctionalityListByEnterprise()
			throws Exception {
		ZyosGroupDAO dao = new ZyosGroupDAO();
		try {
			return dao.loadZyosGroupFunctionalityListByEnterprise();
		} catch (RuntimeException e) {
			dao.getSession().beginTransaction().rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	public List<Funcionality> loadFuncionalityList() {
		FuncionalityDAO dao = new FuncionalityDAO();
		try {
			return dao.findAll();
		} catch (RuntimeException e) {
			dao.getSession().beginTransaction().rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	public List<Subject> loadSubjectList() {
		SubjectDAO dao = new SubjectDAO();
		try {
			return dao.findAll();
		} catch (RuntimeException e) {
			dao.getSession().beginTransaction().rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	public List<RiskFactor> loadRiskFactorList() throws Exception {
		RiskFactorDAO dao=new RiskFactorDAO();
		try {
			return dao.findAll();
		} catch (RuntimeException re) {
			dao.getSession().beginTransaction().rollback();
			throw re;
		}finally{
			dao.getSession().close();
			dao=null;
		}
	}
	
	public List<RiskFactorCategory> loadRiskFactorCategoryList() throws Exception {
		RiskFactorDAO dao=new RiskFactorDAO();
		try {
			return dao.findAllRiskFactorCategory();
		} catch (RuntimeException re) {
			dao.getSession().beginTransaction().rollback();
			throw re;
		}finally{
			dao.getSession().close();
			dao=null;
		}
	}
	
	public List<Relationship> loadRelationshipTypeList() {
		RelationshipDAO dao=new RelationshipDAO();
		try {
			return dao.findAll();
		} catch (RuntimeException re) {
			dao.getSession().beginTransaction().rollback();
			throw re;
		}finally{
			dao.getSession().close();
			dao=null;
		}
	}

	public List<Stage> loadStageList() throws Exception {
		StageDAO dao = new StageDAO();
		try {
			return dao.findAll();
		} catch (RuntimeException re) {
			dao.getSession().beginTransaction().rollback();
			throw re;
		}finally{
			dao.getSession().close();
			dao = null;
		}
	}
	
	public List<Stage> loadStagePermissionList() throws Exception {
		StageDAO dao = new StageDAO();
		try {
			return dao.loadStagePermissionList();
		} catch (RuntimeException re) {
			dao.getSession().beginTransaction().rollback();
			throw re;
		}finally{
			dao.getSession().close();
			dao = null;
		}
	}
	
	public List<StatusReportStudent> loadStatusReportStudentList() {
		StatusReportStudentDAO dao = new StatusReportStudentDAO();
		try {
			return dao.findAll();
		} catch (RuntimeException re) {
			dao.getSession().beginTransaction().rollback();
			throw re;
		}finally{
			dao.getSession().close();
			dao = null;
		}
	}
	/** ogarzonm */
	public List<Profession> loadProfessionList() {
		ProfessionDAO dao = new ProfessionDAO();
		try {
			return dao.findAll();
		} catch (RuntimeException re) {
			dao.getSession().beginTransaction().rollback();
			throw re;
		}finally{
			dao.getSession().close();
			dao = null;
		}
	}

	/** ogarzonm */
	public ZyosAuth loadZyosAuthList() {
		ZyosAuthDAO dao = new ZyosAuthDAO();
		try {
			return dao.findAll();
		} catch (RuntimeException re) {
			dao.getSession().beginTransaction().rollback();
			throw re;
		}finally{
			dao.getSession().close();
			dao = null;
		}
	}
	
	/** jhernandez*/
	public ExecutionsHistorical loadLastExecution() throws Exception {
		ExecutionsHistoricalDAO dao = new ExecutionsHistoricalDAO();
		try {
			return dao.loadLastExecution();
		} catch (Exception e) {
			dao.getSession().beginTransaction().rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

}