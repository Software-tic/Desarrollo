package com.zyos.core.lo.user.model;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;


public class ZyosGroupModel extends ListDataModel<ZyosGroup> implements
		SelectableDataModel<ZyosGroup> {

	public ZyosGroupModel(List<ZyosGroup> ZyosGroupList) {
		super(ZyosGroupList);
	}

	@Override
	public ZyosGroup getRowData(String rowKey) {
		List<ZyosGroup> ZyosGroups = (List<ZyosGroup>) getWrappedData();

		for (ZyosGroup zu : ZyosGroups) {
			if (zu.getId().toString().equals(rowKey))
				return zu;
		}

		return null;
	}

	@Override
	public Object getRowKey(ZyosGroup ZyosGroup) {
		return ZyosGroup.getId();
	}

}

