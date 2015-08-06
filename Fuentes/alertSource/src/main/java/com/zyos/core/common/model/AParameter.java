package com.zyos.core.common.model;

public abstract class AParameter extends com.zyos.core.common.model.AZyosModel implements java.io.Serializable  {

	protected Long id;
	protected String name;
	protected String description;
	
	public abstract Long getId();

	public abstract void setId(Long id);
	
	public abstract String getName();

	public abstract void setName(String name);

	public abstract String getDescription();

	public abstract void setDescription(String description);

	@Override
	public abstract String getDateCreation();

	@Override
	public abstract void setDateCreation(String dateCreation);

	@Override
	public abstract String getUserCreation();

	@Override
	public abstract void setUserCreation(String userCreation);

	@Override
	public abstract String getDateChange();

	@Override
	public abstract void setDateChange(String dateChange);

	@Override
	public abstract String getUserChange();

	@Override
	public abstract void setUserChange(String userChange);

	@Override
	public abstract Long getState();

	@Override
	public abstract void setState(Long state);

}
