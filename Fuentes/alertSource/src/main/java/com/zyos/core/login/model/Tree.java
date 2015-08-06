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
 * Tree entity. @author MyEclipse Persistence Tools
 */
/**
 * @author Zyos-Home
 * 
 */
@Entity
@Table(name = "tree")
public class Tree extends com.zyos.core.common.model.AZyosModel implements
		java.io.Serializable {

	// Fields
	private Long id;
	private String label;
	private String description;
	private String icon;
	private String displayPanel;
	private Integer isBranch;
	private Integer isAdmin;
	private Integer isDefault;
	private Integer expanded;
	private Long orderTree;
	private Long treeLevel;
	private String bean;
	
	private transient Long idBranch;
	private transient Long idEnteprise;
	private transient Long idZyosGroup;
	private transient List<Tree> leafList = null;

	// Constructors

	/** default constructor */
	public Tree() {
	}

	public Tree(Long id, String label, String description, String displayPanel,
			String icon, Integer expanded, Integer isBranch) {
		this.id = id;
		this.label = label;
		this.description = description;
		this.displayPanel = displayPanel;
		this.icon = icon;
		this.expanded = expanded;
		this.isBranch = isBranch;
	}

	public Tree(Long id, String label, String description, String displayPanel,
			String icon, Long idBranch, Integer isBranch, Integer expanded) {
		this.id = id;
		this.label = label;
		this.description = description;
		this.displayPanel = displayPanel;
		this.icon = icon;
		this.idBranch = idBranch;
		this.isBranch = isBranch;
		this.expanded = expanded;
	}

	/** minimal constructor */
	public Tree(Tree t) {
		this.idEnteprise = t.getIdEnteprise();
		this.idZyosGroup = t.getIdZyosGroup();
		this.idBranch = t.getIdBranch();
		this.id = t.getId();
		this.label = t.getLabel();
		this.description = t.getDescription();
		this.icon = t.getIcon();
		this.displayPanel = t.getDisplayPanel();
		this.isBranch = t.getIsBranch();
		this.expanded = t.getExpanded();
	}

	/** tree group */
	public Tree(Long idEnteprise, Long idZyosGroup, Integer isBranch,
			Long idBranch, Long id, String label, String description,
			String icon, String displayPanel, Integer expanded, Long orderTree,
			Long level, Integer isAdmin) {
		this.idEnteprise = idEnteprise;
		this.idZyosGroup = idZyosGroup;
		this.idBranch = idBranch;
		this.id = id;
		this.label = label;
		this.description = description;
		this.icon = icon;
		this.displayPanel = displayPanel;
		this.isBranch = isBranch;
		this.expanded = expanded;
		this.orderTree = orderTree;
		this.treeLevel = level;
		this.isAdmin = isAdmin;
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

	@Column(name = "label", nullable = false, length = 128)
	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Column(name = "description", length = 512)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "icon", length = 128)
	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Column(name = "isDefault")
	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

	@Column(name = "treeLevel")
	public Long getTreeLevel() {
		return treeLevel;
	}

	public void setTreeLevel(Long level) {
		this.treeLevel = level;
	}

	@Column(name = "displayPanel", nullable = false, length = 512)
	public String getDisplayPanel() {
		return this.displayPanel;
	}

	public void setDisplayPanel(String displayPanel) {
		this.displayPanel = displayPanel;
	}

	@Column(name = "isAdmin")
	public Integer getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}

	@Column(name = "isBranch")
	public Integer getIsBranch() {
		return this.isBranch;
	}

	public void setIsBranch(Integer isBranch) {
		this.isBranch = isBranch;
	}

	@Column(name = "expanded")
	public Integer getExpanded() {
		return this.expanded;
	}

	public void setExpanded(Integer expanded) {
		this.expanded = expanded;
	}

	@Column(name = "orderTree")
	public Long getOrderTree() {
		return orderTree;
	}

	public void setOrderTree(Long orderTree) {
		this.orderTree = orderTree;
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
	public Long getIdBranch() {
		return idBranch;
	}

	public void setIdBranch(Long idBranch) {
		this.idBranch = idBranch;
	}

	@Transient
	public List<Tree> getLeafList() {
		if (leafList == null)
			leafList = new ArrayList<Tree>();
		return leafList;
	}

	public void setLeafList(List<Tree> leafList) {
		this.leafList = leafList;
	}

	@Transient
	public Long getIdEnteprise() {
		return idEnteprise;
	}

	public void setIdEnteprise(Long idEnteprise) {
		this.idEnteprise = idEnteprise;
	}

	@Transient
	public Long getIdZyosGroup() {
		return idZyosGroup;
	}

	public void setIdZyosGroup(Long idZyosGroup) {
		this.idZyosGroup = idZyosGroup;
	}

	@Transient
	public int getColumnSize() {
		if (leafList != null && leafList.size() > 8) {
			return 8;
		} else {
			return leafList.size();
		}
	}

	public String getBean() {
		return bean;
	}

	public void setBean(String bean) {
		this.bean = bean;
	}

	
}