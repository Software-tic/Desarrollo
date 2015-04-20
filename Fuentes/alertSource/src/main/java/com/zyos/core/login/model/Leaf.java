package com.zyos.core.login.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * Sheet entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "leaf")
public class Leaf extends com.zyos.core.common.model.AZyosModel implements
		java.io.Serializable {

	// Fields

	private Long id;
	private Long idLeaf;
	private Long idBranch;
	private Long orderLeaf;

	// Constructors

	/** default constructor */
	public Leaf() {
	}

	/** minimal constructor */
	public Leaf(Long idSheet, Long idBranch, String dateCreation,
			String userCreation, Long state) {
		this.idLeaf = idSheet;
		this.idBranch = idBranch;
		this.dateCreation = dateCreation;

		this.userCreation = userCreation;
		this.state = state;
	}

	/** full constructor */
	public Leaf(Long idSheet, Long idBranch, String dateCreation,
			String userCreation, String dateChange, String userChange,
			Long state) {
		this.idLeaf = idSheet;
		this.idBranch = idBranch;
		this.dateCreation = dateCreation;

		this.userCreation = userCreation;
		this.dateChange = dateChange;

		this.userChange = userChange;
		this.state = state;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "idLeaf", nullable = false)
	public Long getIdLeaf() {
		return this.idLeaf;
	}

	public void setIdLeaf(Long idSheet) {
		this.idLeaf = idSheet;
	}

	@Column(name = "idBranch", nullable = false)
	public Long getIdBranch() {
		return this.idBranch;
	}

	public void setIdBranch(Long idBranch) {
		this.idBranch = idBranch;
	}

	@Column(name = "orderLeaf")
	public Long getOrderLeaf() {
		return orderLeaf;
	}

	public void setOrderLeaf(Long orderLeaf) {
		this.orderLeaf = orderLeaf;
	}

	@Column(name = "dateCreation", nullable = false, length = 20)
	public String getDateCreation() {
		return this.dateCreation;
	}

	public void setDateCreation(String dateCreation) {
		this.dateCreation = dateCreation;
	}

	@Column(name = "userCreation", nullable = false, length = 45)
	public String getUserCreation() {
		return this.userCreation;
	}

	public void setUserCreation(String userCreation) {
		this.userCreation = userCreation;
	}

	@Column(name = "dateChange", length = 20)
	public String getDateChange() {
		return this.dateChange;
	}

	public void setDateChange(String dateChange) {
		this.dateChange = dateChange;
	}

	@Column(name = "userChange", length = 45)
	public String getUserChange() {
		return this.userChange;
	}

	public void setUserChange(String userChange) {
		this.userChange = userChange;
	}

	@Column(name = "state", nullable = false)
	public Long getState() {
		return this.state;
	}

	public void setState(Long state) {
		this.state = state;
	}

}