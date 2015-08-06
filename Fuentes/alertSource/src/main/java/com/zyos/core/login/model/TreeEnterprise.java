package com.zyos.core.login.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * Treeenterprise entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "treeenterprise")
public class TreeEnterprise extends com.zyos.core.common.model.AZyosModel
		implements java.io.Serializable {

	// Fields
	private Long id;
	private Long idEnterprise;
	private Long idTree;

	// Constructors

	/** default constructor */
	public TreeEnterprise() {
	}

	/** minimal constructor */
	public TreeEnterprise(Long idEnterprise, Long idTree, String dateCreation,
			String userCreation, Long state) {
		this.idEnterprise = idEnterprise;
		this.idTree = idTree;
		this.dateCreation = dateCreation;

		this.userCreation = userCreation;
		this.state = state;
	}

	/** full constructor */
	public TreeEnterprise(Long idEnterprise, Long idTree, String dateCreation,
			String userCreation, String dateChange, String userChange,
			Long state) {
		this.idEnterprise = idEnterprise;
		this.idTree = idTree;
		this.dateCreation = dateCreation;

		this.userCreation = userCreation;
		this.dateCreation = dateCreation;

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

	@Column(name = "idEnterprise", nullable = false)
	public Long getIdEnterprise() {
		return this.idEnterprise;
	}

	public void setIdEnterprise(Long idEnterprise) {
		this.idEnterprise = idEnterprise;
	}

	@Column(name = "idtree", nullable = false)
	public Long getIdTree() {
		return this.idTree;
	}

	public void setIdTree(Long idTree) {
		this.idTree = idTree;
	}

	@Override
	@Column(name = "dateCreation", nullable = false, length = 20)
	public String getDateCreation() {
		return this.dateCreation;
	}

	@Override
	public void setDateCreation(String dateCreation) {
		this.dateCreation = dateCreation;
	}

	@Override
	@Column(name = "userCreation", nullable = false, length = 45)
	public String getUserCreation() {
		return this.userCreation;
	}

	@Override
	public void setUserCreation(String userCreation) {
		this.userCreation = userCreation;
	}

	@Override
	@Column(name = "dateChange", length = 20)
	public String getDateChange() {
		return this.dateChange;
	}

	@Override
	public void setDateChange(String dateChange) {
		this.dateChange = dateChange;
	}

	@Override
	@Column(name = "userChange", length = 45)
	public String getUserChange() {
		return this.userChange;
	}

	@Override
	public void setUserChange(String userChange) {
		this.userChange = userChange;
	}

	@Override
	@Column(name = "state", nullable = false)
	public Long getState() {
		return this.state;
	}

	@Override
	public void setState(Long state) {
		this.state = state;
	}

}