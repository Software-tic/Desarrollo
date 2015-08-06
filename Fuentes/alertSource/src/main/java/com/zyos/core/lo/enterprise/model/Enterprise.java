package com.zyos.core.lo.enterprise.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.zyos.core.common.model.AParameter;

/**
 * Enterprise entity. @author Óscar Garzón
 */
@Entity
@Table(name = "enterprise")
public class Enterprise extends AParameter implements java.io.Serializable {

	// Fields
	private Long id;
	private String idEnterprise;
	private String nameLogo;
	private String address;
	private Long phone;

	private transient Long idCountry;
	private transient String nameCountry;
	private transient String nameCity;
	private transient boolean selected;

	// Constructors

	/** default constructor */
	public Enterprise() {
	}

	/**
	 * Session constructor
	 * 
	 * @param id
	 * @param name
	 */
	public Enterprise(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	/** minimal constructor */
	public Enterprise(Long id, String idEnterprise, String address,
			String name, String idBank, String bankNumberCount) {
		this.id = id;
		this.idEnterprise = idEnterprise;
		this.name = name;
		this.address = address;
	}

	/** full constructor */
	public Enterprise(String idarea, String name, String description,
			String address, String idbank, String banknumbercount,
			String dateCreation, String userCreation, String dateChange,
			String userChange, Long state) {
		this.idEnterprise = idarea;
		this.name = name;
		this.description = description;
		this.address = address;
		this.dateCreation = dateCreation;

		this.userCreation = userCreation;
		this.dateChange = dateChange;

		this.userChange = userChange;
		this.state = state;
	}

	/** constructor for loadEnterpriseList() in EnterpriseDAO */
	public Enterprise(Long id, String idEnterprise, String name, Long phone,
			String address, String description) {
		this.id = id;
		this.idEnterprise = idEnterprise;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.description = description;

	}

	// Property accessors
	@Override
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "idEnterprise", nullable = false, length = 15)
	public String getIdEnterprise() {
		return this.idEnterprise;
	}

	public void setIdEnterprise(String nit) {
		this.idEnterprise = nit;
	}

	@Override
	@Column(name = "name", nullable = false, length = 45)
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	@Column(name = "description", length = 128)
	public String getDescription() {
		return this.description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "nameLogo", length = 128)
	public String getNameLogo() {
		return nameLogo;
	}

	public void setNameLogo(String nameLogo) {
		this.nameLogo = nameLogo;
	}

	@Column(name = "address", length = 128)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "phone")
	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
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
	@Column(name = "userCreation", nullable = false, length = 15)
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
	@Column(name = "userChange", length = 15)
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
	public Long getIdCountry() {
		return idCountry;
	}

	public void setIdCountry(Long idCountry) {
		this.idCountry = idCountry;
	}

	@Transient
	public String getNameCountry() {
		return nameCountry;
	}

	public void setNameCountry(String nameCountry) {
		this.nameCountry = nameCountry;
	}

	@Transient
	public String getNameCity() {
		return nameCity;
	}

	public void setNameCity(String nameCity) {
		this.nameCity = nameCity;
	}

	@Transient
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}