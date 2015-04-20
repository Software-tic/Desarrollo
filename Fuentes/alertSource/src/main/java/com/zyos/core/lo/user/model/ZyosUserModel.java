package com.zyos.core.lo.user.model;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

public class ZyosUserModel extends ListDataModel<ZyosUser> implements
		SelectableDataModel<ZyosUser>, Serializable {

	public ZyosUserModel(List<ZyosUser> zyosUserList) {
		super(zyosUserList);
	}

	@Override
	public ZyosUser getRowData(String rowKey) {
		List<ZyosUser> ZyosUsers = (List<ZyosUser>) getWrappedData();

		for (ZyosUser zu : ZyosUsers) {
			if (zu.getIdZyosUser().toString().equals(rowKey))
				return zu;
		}

		return null;
	}

	@Override
	public Object getRowKey(ZyosUser zyosUser) {
		return zyosUser.getIdZyosUser();
	}

}
