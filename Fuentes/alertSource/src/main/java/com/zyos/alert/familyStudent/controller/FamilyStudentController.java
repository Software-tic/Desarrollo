package com.zyos.alert.familyStudent.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.hibernate.Transaction;

import com.zyos.alert.familyStudent.model.FamilyStudent;
import com.zyos.alert.familyStudent.model.FamilyStudentDAO;
import com.zyos.alert.studentReport.model.RiskFactorCategory;
import com.zyos.alert.studentReport.model.RiskFactorDAO;
import com.zyos.core.common.controller.ZyosController;

public class FamilyStudentController extends ZyosController {

	/** jhernandez*/
	public ArrayList<SelectItem> loadRelationshipTypeList() throws Exception {
		FamilyStudentDAO dao = new FamilyStudentDAO();
		try {
			return dao.loadRelationshipTypeList();
		} catch (Exception e) {
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}

	/** jhernandez*/
	public void saveFamilyStudent(FamilyStudent familyStudent,
			String documentNumber) {
		FamilyStudentDAO dao = new FamilyStudentDAO();

		Transaction tx = dao.getSession().beginTransaction();
		try {
			familyStudent.initializing(documentNumber, true);
			dao.save(familyStudent);
			tx.commit();
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			dao.getSession().close();
			dao = null;
		}
	}
	
	/** jhernandez*/
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

}
