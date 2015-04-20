package com.zyos.alert.moodle.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * MdlUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "mdl_user", catalog = "moodlepre20141", uniqueConstraints = @UniqueConstraint(columnNames = {
		"mnethostid", "username" }))
public class MdlUser implements java.io.Serializable {

	// Fields

	private Long id;
	private String auth;
	private Boolean confirmed;
	private Boolean policyagreed;
	private Boolean deleted;
	private Boolean suspended;
	private Long mnethostid;
	private String username;
	private String password;
	private String idnumber;
	private String firstname;
	private String lastname;
	private String email;
	private Boolean emailstop;
	private String icq;
	private String skype;
	private String yahoo;
	private String aim;
	private String msn;
	private String phone1;
	private String phone2;
	private String institution;
	private String department;
	private String address;
	private String city;
	private String country;
	private String lang;
	private String theme;
	private String timezone;
	private Long firstaccess;
	private Long lastaccess;
	private Long lastlogin;
	private Long currentlogin;
	private String lastip;
	private String secret;
	private Long picture;
	private String url;
	private String description;
	private Short descriptionformat;
	private Boolean mailformat;
	private Boolean maildigest;
	private Short maildisplay;
	private Boolean htmleditor;
	private Boolean autosubscribe;
	private Boolean trackforums;
	private Long timecreated;
	private Long timemodified;
	private Long trustbitmask;
	private String imagealt;

	// Constructors

	/** default constructor */
	public MdlUser() {
	}

	/** minimal constructor */
	public MdlUser(String auth, Boolean confirmed, Boolean policyagreed,
			Boolean deleted, Boolean suspended, Long mnethostid,
			String username, String password, String idnumber,
			String firstname, String lastname, String email, Boolean emailstop,
			String icq, String skype, String yahoo, String aim, String msn,
			String phone1, String phone2, String institution,
			String department, String address, String city, String country,
			String lang, String theme, String timezone, Long firstaccess,
			Long lastaccess, Long lastlogin, Long currentlogin, String lastip,
			String secret, Long picture, String url, Short descriptionformat,
			Boolean mailformat, Boolean maildigest, Short maildisplay,
			Boolean htmleditor, Boolean autosubscribe, Boolean trackforums,
			Long timecreated, Long timemodified, Long trustbitmask) {
		this.auth = auth;
		this.confirmed = confirmed;
		this.policyagreed = policyagreed;
		this.deleted = deleted;
		this.suspended = suspended;
		this.mnethostid = mnethostid;
		this.username = username;
		this.password = password;
		this.idnumber = idnumber;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.emailstop = emailstop;
		this.icq = icq;
		this.skype = skype;
		this.yahoo = yahoo;
		this.aim = aim;
		this.msn = msn;
		this.phone1 = phone1;
		this.phone2 = phone2;
		this.institution = institution;
		this.department = department;
		this.address = address;
		this.city = city;
		this.country = country;
		this.lang = lang;
		this.theme = theme;
		this.timezone = timezone;
		this.firstaccess = firstaccess;
		this.lastaccess = lastaccess;
		this.lastlogin = lastlogin;
		this.currentlogin = currentlogin;
		this.lastip = lastip;
		this.secret = secret;
		this.picture = picture;
		this.url = url;
		this.descriptionformat = descriptionformat;
		this.mailformat = mailformat;
		this.maildigest = maildigest;
		this.maildisplay = maildisplay;
		this.htmleditor = htmleditor;
		this.autosubscribe = autosubscribe;
		this.trackforums = trackforums;
		this.timecreated = timecreated;
		this.timemodified = timemodified;
		this.trustbitmask = trustbitmask;
	}

	/** full constructor */
	public MdlUser(String auth, Boolean confirmed, Boolean policyagreed,
			Boolean deleted, Boolean suspended, Long mnethostid,
			String username, String password, String idnumber,
			String firstname, String lastname, String email, Boolean emailstop,
			String icq, String skype, String yahoo, String aim, String msn,
			String phone1, String phone2, String institution,
			String department, String address, String city, String country,
			String lang, String theme, String timezone, Long firstaccess,
			Long lastaccess, Long lastlogin, Long currentlogin, String lastip,
			String secret, Long picture, String url, String description,
			Short descriptionformat, Boolean mailformat, Boolean maildigest,
			Short maildisplay, Boolean htmleditor, Boolean autosubscribe,
			Boolean trackforums, Long timecreated, Long timemodified,
			Long trustbitmask, String imagealt) {
		this.auth = auth;
		this.confirmed = confirmed;
		this.policyagreed = policyagreed;
		this.deleted = deleted;
		this.suspended = suspended;
		this.mnethostid = mnethostid;
		this.username = username;
		this.password = password;
		this.idnumber = idnumber;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.emailstop = emailstop;
		this.icq = icq;
		this.skype = skype;
		this.yahoo = yahoo;
		this.aim = aim;
		this.msn = msn;
		this.phone1 = phone1;
		this.phone2 = phone2;
		this.institution = institution;
		this.department = department;
		this.address = address;
		this.city = city;
		this.country = country;
		this.lang = lang;
		this.theme = theme;
		this.timezone = timezone;
		this.firstaccess = firstaccess;
		this.lastaccess = lastaccess;
		this.lastlogin = lastlogin;
		this.currentlogin = currentlogin;
		this.lastip = lastip;
		this.secret = secret;
		this.picture = picture;
		this.url = url;
		this.description = description;
		this.descriptionformat = descriptionformat;
		this.mailformat = mailformat;
		this.maildigest = maildigest;
		this.maildisplay = maildisplay;
		this.htmleditor = htmleditor;
		this.autosubscribe = autosubscribe;
		this.trackforums = trackforums;
		this.timecreated = timecreated;
		this.timemodified = timemodified;
		this.trustbitmask = trustbitmask;
		this.imagealt = imagealt;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "auth", nullable = false, length = 20)
	public String getAuth() {
		return this.auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	@Column(name = "confirmed", nullable = false)
	public Boolean getConfirmed() {
		return this.confirmed;
	}

	public void setConfirmed(Boolean confirmed) {
		this.confirmed = confirmed;
	}

	@Column(name = "policyagreed", nullable = false)
	public Boolean getPolicyagreed() {
		return this.policyagreed;
	}

	public void setPolicyagreed(Boolean policyagreed) {
		this.policyagreed = policyagreed;
	}

	@Column(name = "deleted", nullable = false)
	public Boolean getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	@Column(name = "suspended", nullable = false)
	public Boolean getSuspended() {
		return this.suspended;
	}

	public void setSuspended(Boolean suspended) {
		this.suspended = suspended;
	}

	@Column(name = "mnethostid", nullable = false)
	public Long getMnethostid() {
		return this.mnethostid;
	}

	public void setMnethostid(Long mnethostid) {
		this.mnethostid = mnethostid;
	}

	@Column(name = "username", nullable = false, length = 100)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password", nullable = false, length = 32)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "idnumber", nullable = false)
	public String getIdnumber() {
		return this.idnumber;
	}

	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}

	@Column(name = "firstname", nullable = false, length = 100)
	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	@Column(name = "lastname", nullable = false, length = 100)
	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@Column(name = "email", nullable = false, length = 100)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "emailstop", nullable = false)
	public Boolean getEmailstop() {
		return this.emailstop;
	}

	public void setEmailstop(Boolean emailstop) {
		this.emailstop = emailstop;
	}

	@Column(name = "icq", nullable = false, length = 15)
	public String getIcq() {
		return this.icq;
	}

	public void setIcq(String icq) {
		this.icq = icq;
	}

	@Column(name = "skype", nullable = false, length = 50)
	public String getSkype() {
		return this.skype;
	}

	public void setSkype(String skype) {
		this.skype = skype;
	}

	@Column(name = "yahoo", nullable = false, length = 50)
	public String getYahoo() {
		return this.yahoo;
	}

	public void setYahoo(String yahoo) {
		this.yahoo = yahoo;
	}

	@Column(name = "aim", nullable = false, length = 50)
	public String getAim() {
		return this.aim;
	}

	public void setAim(String aim) {
		this.aim = aim;
	}

	@Column(name = "msn", nullable = false, length = 50)
	public String getMsn() {
		return this.msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	@Column(name = "phone1", nullable = false, length = 20)
	public String getPhone1() {
		return this.phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	@Column(name = "phone2", nullable = false, length = 20)
	public String getPhone2() {
		return this.phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	@Column(name = "institution", nullable = false, length = 40)
	public String getInstitution() {
		return this.institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	@Column(name = "department", nullable = false, length = 30)
	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@Column(name = "address", nullable = false, length = 70)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "city", nullable = false, length = 120)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "country", nullable = false, length = 2)
	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Column(name = "lang", nullable = false, length = 30)
	public String getLang() {
		return this.lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	@Column(name = "theme", nullable = false, length = 50)
	public String getTheme() {
		return this.theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	@Column(name = "timezone", nullable = false, length = 100)
	public String getTimezone() {
		return this.timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	@Column(name = "firstaccess", nullable = false)
	public Long getFirstaccess() {
		return this.firstaccess;
	}

	public void setFirstaccess(Long firstaccess) {
		this.firstaccess = firstaccess;
	}

	@Column(name = "lastaccess", nullable = false)
	public Long getLastaccess() {
		return this.lastaccess;
	}

	public void setLastaccess(Long lastaccess) {
		this.lastaccess = lastaccess;
	}

	@Column(name = "lastlogin", nullable = false)
	public Long getLastlogin() {
		return this.lastlogin;
	}

	public void setLastlogin(Long lastlogin) {
		this.lastlogin = lastlogin;
	}

	@Column(name = "currentlogin", nullable = false)
	public Long getCurrentlogin() {
		return this.currentlogin;
	}

	public void setCurrentlogin(Long currentlogin) {
		this.currentlogin = currentlogin;
	}

	@Column(name = "lastip", nullable = false, length = 45)
	public String getLastip() {
		return this.lastip;
	}

	public void setLastip(String lastip) {
		this.lastip = lastip;
	}

	@Column(name = "secret", nullable = false, length = 15)
	public String getSecret() {
		return this.secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	@Column(name = "picture", nullable = false)
	public Long getPicture() {
		return this.picture;
	}

	public void setPicture(Long picture) {
		this.picture = picture;
	}

	@Column(name = "url", nullable = false)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "description")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "descriptionformat", nullable = false)
	public Short getDescriptionformat() {
		return this.descriptionformat;
	}

	public void setDescriptionformat(Short descriptionformat) {
		this.descriptionformat = descriptionformat;
	}

	@Column(name = "mailformat", nullable = false)
	public Boolean getMailformat() {
		return this.mailformat;
	}

	public void setMailformat(Boolean mailformat) {
		this.mailformat = mailformat;
	}

	@Column(name = "maildigest", nullable = false)
	public Boolean getMaildigest() {
		return this.maildigest;
	}

	public void setMaildigest(Boolean maildigest) {
		this.maildigest = maildigest;
	}

	@Column(name = "maildisplay", nullable = false)
	public Short getMaildisplay() {
		return this.maildisplay;
	}

	public void setMaildisplay(Short maildisplay) {
		this.maildisplay = maildisplay;
	}

	@Column(name = "htmleditor", nullable = false)
	public Boolean getHtmleditor() {
		return this.htmleditor;
	}

	public void setHtmleditor(Boolean htmleditor) {
		this.htmleditor = htmleditor;
	}

	@Column(name = "autosubscribe", nullable = false)
	public Boolean getAutosubscribe() {
		return this.autosubscribe;
	}

	public void setAutosubscribe(Boolean autosubscribe) {
		this.autosubscribe = autosubscribe;
	}

	@Column(name = "trackforums", nullable = false)
	public Boolean getTrackforums() {
		return this.trackforums;
	}

	public void setTrackforums(Boolean trackforums) {
		this.trackforums = trackforums;
	}

	@Column(name = "timecreated", nullable = false)
	public Long getTimecreated() {
		return this.timecreated;
	}

	public void setTimecreated(Long timecreated) {
		this.timecreated = timecreated;
	}

	@Column(name = "timemodified", nullable = false)
	public Long getTimemodified() {
		return this.timemodified;
	}

	public void setTimemodified(Long timemodified) {
		this.timemodified = timemodified;
	}

	@Column(name = "trustbitmask", nullable = false)
	public Long getTrustbitmask() {
		return this.trustbitmask;
	}

	public void setTrustbitmask(Long trustbitmask) {
		this.trustbitmask = trustbitmask;
	}

	@Column(name = "imagealt")
	public String getImagealt() {
		return this.imagealt;
	}

	public void setImagealt(String imagealt) {
		this.imagealt = imagealt;
	}

}