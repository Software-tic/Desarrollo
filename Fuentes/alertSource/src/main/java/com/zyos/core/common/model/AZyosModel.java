package com.zyos.core.common.model;

import java.io.Serializable;

import com.zyos.core.common.api.IZyosState;
import com.zyos.core.common.util.ManageDate;

public abstract class AZyosModel implements Serializable {

	private static final long serialVersionUID = -3001640245490541815L;
	// Fields
	protected String dateCreation;
	protected String userCreation;
	protected String dateChange;
	protected String userChange;
	protected Long state;

	/**
	 * Method that initializes a object the type AZyosModel is depends of if the
	 * object is new or already exists in the database.
	 * 
	 * @param user
	 *            String with the user name
	 * @param isNew
	 *            boolean that indicates if is a new object
	 */
	public void initializing(String user, boolean isNew) {
		if (isNew) {
			this.userCreation = user;
			this.dateCreation = ManageDate
					.getCurrentDate(ManageDate.YYYY_MM_DD_HH_MM_SS);
			this.userChange = null;
			this.state = IZyosState.ACTIVE;
		} else {
			this.userChange = user;
			this.dateChange = ManageDate
					.getCurrentDate(ManageDate.YYYY_MM_DD_HH_MM_SS);
			this.state = IZyosState.ACTIVE;
		}
	}

	public AZyosModel(String dateCreation, String userCreation) {
		super();
		this.dateCreation = dateCreation;
		this.userCreation = userCreation;
	}

	public AZyosModel() {
		super();
	}

	public AZyosModel(String dateCreation, String userCreation,
			String dateChange, String userChange, Long state) {
		super();
		this.dateCreation = dateCreation;
		this.userCreation = userCreation;
		this.dateChange = dateChange;
		this.userChange = userChange;
		this.state = state;
	}

	public String getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(String dateCreation) {
		this.dateCreation = dateCreation;
	}

	public String getUserCreation() {
		return userCreation;
	}

	public void setUserCreation(String userCreation) {
		this.userCreation = userCreation;
	}

	public String getDateChange() {
		return dateChange;
	}

	public void setDateChange(String dateChange) {
		this.dateChange = dateChange;
	}

	public String getUserChange() {
		return userChange;
	}

	public void setUserChange(String userChange) {
		this.userChange = userChange;
	}

	public Long getState() {
		return state;
	}

	public void setState(Long state) {
		this.state = state;
	}

}
