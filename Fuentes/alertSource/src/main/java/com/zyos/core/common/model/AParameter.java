package com.zyos.core.common.model;

import java.io.Serializable;

public abstract class AParameter extends AZyosModel implements Serializable {

	protected Long id;
	protected String name;
	protected String description;

	public abstract Long getId();

	public abstract void setId(Long id);
	
	public abstract String getName();

	public abstract void setName(String name);

	public abstract String getDescription();

	public abstract void setDescription(String description);

	public abstract String getDateCreation();

	public abstract void setDateCreation(String dateCreation);

	public abstract String getUserCreation();

	public abstract void setUserCreation(String userCreation);

	public abstract String getDateChange();

	public abstract void setDateChange(String dateChange);

	public abstract String getUserChange();

	public abstract void setUserChange(String userChange);

	public abstract Long getState();

	public abstract void setState(Long state);

}
