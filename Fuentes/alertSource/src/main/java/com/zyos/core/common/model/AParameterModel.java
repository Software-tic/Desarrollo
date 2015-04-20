package com.zyos.core.common.model;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

public class AParameterModel extends ListDataModel<AParameter> implements
		SelectableDataModel<AParameter> {

	public AParameterModel(List<AParameter> zyosUserList) {
		super(zyosUserList);
	}

	@Override
	public AParameter getRowData(String rowKey) {
		List<AParameter> AParameters = (List<AParameter>) getWrappedData();

		for (AParameter zu : AParameters) {
			if (zu.getId().toString().equals(rowKey))
				return zu;
		}

		return null;
	}

	@Override
	public Object getRowKey(AParameter zyosUser) {
		return zyosUser.getId();
	}

}
