package com.zyos.core.mail.io.mn.controller;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import com.zyos.core.mail.io.mn.model.EmailTemplate;

public class EmailTemplateModel extends ListDataModel<EmailTemplate> implements
		SelectableDataModel<EmailTemplate> {

	public EmailTemplateModel(List<EmailTemplate> NewsletterList) {
		super(NewsletterList);
	}

	@Override
	public EmailTemplate getRowData(String rowKey) {
		List<EmailTemplate> EmailTemplate = (List<EmailTemplate>) getWrappedData();

		for (EmailTemplate zu : EmailTemplate) {
			if (zu.getId().toString().equals(rowKey))
				return zu;
		}

		return null;
	}

	@Override
	public Object getRowKey(EmailTemplate Newsletter) {
		return Newsletter.getId();
	}

}
