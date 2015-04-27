package com.zyos.alert.facultyDegree.model;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

public class FacultyDegreeModel extends ListDataModel<FacultyDegree> implements
		SelectableDataModel<FacultyDegree>, Serializable {

	public FacultyDegreeModel(List<FacultyDegree> FacultyDegreeList) {
		super(FacultyDegreeList);
	}

	@Override
	public FacultyDegree getRowData(String rowKey) {
		List<FacultyDegree> list = (List<FacultyDegree>) getWrappedData();

		for (FacultyDegree de : list) {
			if (de.getIdFaculty().toString().equals(rowKey))
				return de;
		}

		return null;
	}

	@Override
	public Object getRowKey(FacultyDegree de) {
		return de.getIdFaculty();
	}

}