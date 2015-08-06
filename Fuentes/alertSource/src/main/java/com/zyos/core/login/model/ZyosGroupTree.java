package com.zyos.core.login.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * Zyosgrouptree entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "zyosGroupTree")
public class ZyosGroupTree extends com.zyos.core.common.model.AZyosModel
		implements java.io.Serializable {

	// Fields
	private Long id;
	private Long idTree;
	private Long idZyosGroup;
	private String functionalityList;

	private transient Integer isBranch;
	private transient Long idEnterprise;
	private transient Long level;
	private transient String label;
	private transient String className;
	private transient List<Tree> treeList;

	// Constructors

	/** default constructor */
	public ZyosGroupTree() {
	}

	/** minimal constructor */
	public ZyosGroupTree(Long idZyosGroup) {
		this.idZyosGroup = idZyosGroup;
	}

	/** loadAndBuildTreeEnterpriseStructure - @author ogarzonm */
	public ZyosGroupTree(Long idTree, Long state) {
		this.idTree = idTree;
		this.state = state;
	}

	public ZyosGroupTree(Long idTree, int isDefault) {
		this.idTree = idTree;
	}

	/** loadZyosGroupFunctionalityListByEnterprise - @author ogarzonm */
	public ZyosGroupTree(Long id, Long idTree, Long idEnterprise,
			Long idZyosGroup, String funtionalityList, String className) {
		// t.id, te.idTree, te.idEnterprise, t.idZyosGroup, t.functionalityList, t.className
		this.id = id;
		this.idTree = idTree;
		this.className = className;
		this.idEnterprise = idEnterprise;
		this.idZyosGroup = idZyosGroup;
		this.functionalityList = funtionalityList;
	}

	/**
	 * {@link ZyosGroupTreeDAO#loadTreeByZyosGroup(Long, Long, Long, Long)}
	 * 
	 * @author ogarzonm
	 */
	public ZyosGroupTree(Long id, Long idTree, String label, Long level,
			Integer isBranch, String functionalityList, Long state) {
		// zgt.id, t.label, t.isBranch, zgt.functionalityList
		this.id = id;
		this.idTree = idTree;
		this.level = level;
		this.label = label;
		this.isBranch = isBranch;
		this.functionalityList = functionalityList;
		this.state = state;
	}

	public ZyosGroupTree(String label, String string2, String string3) {
		this.label = label;
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

	@Column(name = "idTree", nullable = false)
	public Long getIdTree() {
		return this.idTree;
	}

	public void setIdTree(Long idTree) {
		this.idTree = idTree;
	}

	@Column(name = "idZyosGroup", nullable = false)
	public Long getIdZyosGroup() {
		return this.idZyosGroup;
	}

	public void setIdZyosGroup(Long idZyosGroup) {
		this.idZyosGroup = idZyosGroup;
	}

	@Column(name = "functionalityList")
	public String getFunctionalityList() {
		return functionalityList;
	}

	public void setFunctionalityList(String functionalityList) {
		this.functionalityList = functionalityList;
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

	@Transient
	public List<Tree> getTreeList() {
		if (treeList == null)
			treeList = new ArrayList<Tree>();
		return treeList;
	}

	public void setTreeList(List<Tree> treeList) {
		this.treeList = treeList;
	}

	@Transient
	public Long getIdEnterprise() {
		return idEnterprise;
	}

	public void setIdEnterprise(Long idEnterprise) {
		this.idEnterprise = idEnterprise;
	}

	@Transient
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Transient
	public Integer getIsBranch() {
		return isBranch;
	}

	public void setIsBranch(Integer isBranch) {
		this.isBranch = isBranch;
	}

	@Transient
	public Long getLevel() {
		return level;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

	@Transient
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

}