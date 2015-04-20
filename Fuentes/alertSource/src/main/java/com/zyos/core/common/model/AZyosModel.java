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

}
