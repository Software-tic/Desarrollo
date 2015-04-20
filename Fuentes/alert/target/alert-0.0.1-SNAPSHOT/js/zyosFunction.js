var browser = navigator.appName;
var chartEarnedValue = '';

/**
 * Method for locating the growl about the screen size
 * 
 * @param left
 */
function changeGrowl(left) {
	if (typeof style == 'undefined') {
		var append = true;
		style = document.createElement('style');
	} else {
		while (style.hasChildNodes()) {
			style.removeChild(style.firstChild);
		}
	}
	var head = document.getElementsByTagName('head')[0];
	var rules = document.createTextNode('.ui-growl { left: ' + left + 'px;}');

	style.type = 'text/css';
	if (style.styleSheet) {
		style.styleSheet.cssText = rules.nodeValue;
	} else {
		style.appendChild(rules);
	}
	if (append === true)
		head.appendChild(style);
}

changeGrowl(($(window).width() / 2) - 150);

/**
 * Method for deleting the empty spaces (trim) in a string
 * 
 * @param str
 * @returns
 */
function trim(str) {
	return str.replace(/^\s*|\s*$/g, "");
}

function isNumber(n) {
	return !isNaN(parseFloat(n)) && isFinite(n);
}

/**
 * function for validating a date
 * 
 * @param dat
 * @returns
 */
function isDate(dat) {
	var t = new Date(dat);
	return isNaN(t.valueOf());
}

/**
 * Method for adding date validation capabilities
 * 
 * @param fieldID
 *            id Field
 * @param compulsory
 *            is compulsory
 * @returns {LiveValidation}
 */
function addDateCapabilities(fieldID, compulsory) {
	var lv = new LiveValidation(fieldID, {
		onlyOnSubmit : compulsory,
		validMessage : " ",
		wait : 500
	});
	if (isDate(document.getElementById(fieldID).value))
		lv.add(Validate.Format, {
			pattern : /live/i
		});
	lv.add(Validate.Presence);
	return lv;
}
/**
 * Method for adding a present capabilities
 * 
 * @param fieldID
 *            id field
 * @param compulsory
 * @returns {LiveValidation}
 */
function addPresentCapabilities(fieldID, compulsory) {
	var lv = new LiveValidation(fieldID, {
		onlyOnSubmit : compulsory,
		validMessage : " ",
		wait : 500
	});
	lv.add(Validate.Presence);
	return lv;
}
/**
 * Method for adding validate description text capabilities
 * 
 * @param fieldID
 * @param compulsory
 * @param presence
 * @param minLength
 * @param maxLength
 * @returns {LiveValidation}
 */
function addDescriptionCapabilities(fieldID, compulsory, presence, minLength,
		maxLength) {
	var lv = new LiveValidation(fieldID, {
		onlyOnSubmit : compulsory,
		validMessage : " ",
		wait : 500
	});
	lv.add(Validate.Exclusion, {
		within : [ "--" ],
		allowNull : false,
		partialMatch : true,
		caseSensitive : false,
		failureMessage : lmIC
	});
	lv.add(Validate.Length, {
		minimum : minLength,
		maximum : maxLength
	});
	if (presence) {
		lv.add(Validate.Presence);
	}
	return lv;
}
/**
 * Method for validating a text inside an input
 * 
 * @param fieldID
 * @param compulsory
 * @param presence
 * @param minLength
 * @param maxLength
 * @returns {LiveValidation}
 */
function addTextCapabilities(fieldID, compulsory, presence, minLength,
		maxLength) {
	var lv = new LiveValidation(fieldID, {
		onlyOnSubmit : compulsory,
		validMessage : " ",
		wait : 500
	});
	lv.add(Validate.Exclusion, {
		within : [ "--" ],
		allowNull : false,
		partialMatch : true,
		caseSensitive : false,
		failureMessage : lmIC
	});
	lv.add(Validate.Length, {
		minimum : minLength,
		maximum : maxLength
	});
	if (presence) {
		lv.add(Validate.Presence);
	}
	return lv;
}
function addElement(divName, text, addAstk) {
	var ni = document.getElementById(divName);
	if (ni != null) {
		var newdiv = document.createElement(divName + 'LV');
		newdiv.setAttribute('id', divName + 'LV');
		newdiv.innerHTML = (addAstk ? '* ' : '') + text;
		ni.appendChild(newdiv);
	}
}

function removeElement(divName) {
	var d = document.getElementById(divName);
	var olddiv = document.getElementById(divName + 'LV');
	if (olddiv != null)
		d.removeChild(olddiv);
}

function validateAddParameter(form) {

	nameParameter = addTextCapabilities(form + ":nameParameter", true, true, 2,
			45);
	valid = LiveValidation
			.massValidate([ nameParameter]);

	if (valid) {
		statusDialog.show();
		PrimeFaces.ab({
			formId : form,
			source : form + ':buttonAddParameter',
			process : '@all',
			update: '@form'
		});
		return false;
	}
}

/**
 * Method for adding a validate select
 * 
 * @param fieldID
 * @param divName
 * @returns {Boolean} true is the index value selected is not 0
 */
function addSelectCapabilities(fieldID, divName) {
	removeElement(divName);
	if (document.getElementById(fieldID) != null
			&& document.getElementById(fieldID).selectedIndex == 0) {
		addElement(divName, lmMustSelectOne, true);
		return false;
	}
	return true;
}
/**
 * Method for validating if the PFE editor (CEK editor) is empty or not
 * 
 * @param editorWV
 * @param divName
 * @returns {Boolean}
 */
function addEditorCapabilities(editorWV, divName) {
	removeElement(divName);
	if (editorWV.instance.getData() != null
			&& editorWV.instance.getData() == "") {
		addElement(divName, lmPresence, true);
		return false;
	}
	return true;
}

/**
 * Method for validating if the user selected or not some item of the list
 * 
 * @param fieldID
 * @param divName
 * @returns {Boolean}
 */
function addSelectEditableCapabilities(fieldID, divName) {
	removeElement(divName);
	alert(document.getElementById(fieldID).value);
	if (document.getElementById(fieldID).value == '') {
		addElement(divName, lmMustSelectOne, true);
		return false;
	}
	return true;
}

/**
 * Function
 * 
 * @param fieldID
 *            client ID
 * @param divName
 *            div ID
 * @param minQuantity
 *            min selected quantity
 * @returns {Boolean} true if the selected quantity is equal to the minQuantity
 *          else false
 */
function addSelectCheckBoxMenuCapabilities(fieldID, divName, minQuantity) {
	removeElement(divName);
	count = 0;
	hits = 0;
	while (true) {
		eb = document.getElementById(fieldID + ":" + count);
		if (eb != null) {
			if (eb.checked) {
				hits++;
			}
		} else {
			break;
		}
		count++;
	}
	if (hits < minQuantity) {
		addElement(divName, lmMustSelectOne, true);
		return false;
	}
	return true;
}

function addPasswordCapabilities(fieldID, fieldIDTwo) {
	var lv = new LiveValidation(fieldID, {
		onlyOnSubmit : true,
		validMessage : " ",
		wait : 500
	});
	lv.add(Validate.Confirmation, {
		match : fieldIDTwo,
		failureMessage : lmNotMatch
	});
	lv.add(Validate.Exclusion, {
		within : [ "--" ],
		allowNull : false,
		partialMatch : true,
		caseSensitive : false,
		failureMessage : lmIC
	});
	lv.add(Validate.Length, {
		minimum : 6
	});
	return lv;
}

function addPercentageCapabilities(fieldID, compulsory, presence) {
	var lv = new LiveValidation(fieldID, {
		onlyOnSubmit : compulsory,
		validMessage : " ",
		wait : 500
	});
	lv.add(Validate.Numericality);
	lv.add(Validate.Length, {
		minimum : 1,
		maximum : 4
	});
	lv.add(Validate.Numericality, {
		maximum : 100
	});
	if (presence)
		lv.add(Validate.Presence);
	return lv;
}

function addNumberCapabilities(fieldID, compulsory, minLength, maxLength,
		presence) {
	var lv = new LiveValidation(fieldID, {
		onlyOnSubmit : compulsory,
		validMessage : " ",
		wait : 500
	});
	lv.add(Validate.Numericality, {
		onlyInteger : false
	});
	lv.add(Validate.Numericality);
	lv.add(Validate.Length, {
		minimum : minLength,
		maximum : maxLength
	});
	if (presence)
		lv.add(Validate.Presence);
	return lv;
}

function addIntegerCapabilities(fieldID, compulsory, presence, likeInteger) {
	var lv = new LiveValidation(fieldID, {
		onlyOnSubmit : compulsory,
		validMessage : " ",
		wait : 500
	});
	lv.add(Validate.Numericality, {
		onlyInteger : likeInteger
	});
	if (presence)
		lv.add(Validate.Presence);
	return lv;
}

function addCheckCapabilities(cformSP) {
	var field = new LiveValidation(cformSP, {
		onlyOnSubmit : true,
		validMessage : " ",
		wait : 500
	});
	field.add(Validate.Acceptance);
	return field;
}

function addEmailCapabilities(fieldID, compulsory, presence) {
	var lv = new LiveValidation(fieldID, {
		onlyOnSubmit : compulsory,
		validMessage : " ",
		wait : 500
	});
	lv.add(Validate.Email);
	lv.add(Validate.Exclusion,
			{
				within : [ "!", "\xb7", "#", "$", "%", "&", "/", "(", ")", "=",
						"?", "\xbf", "*", "+", "\xe7", ",", ";", ":", "<", ">",
						"\xba", "|" ],
				allowNull : false,
				partialMatch : true,
				caseSensitive : false,
				failureMessage : lmIC
			});
	if (presence) {
		lv.add(Validate.Presence);
	}
	return lv;
}

function addCurrencyCapabilities(fieldID, compulsory) {
	var lv = new LiveValidation(fieldID, {
		onlyOnSubmit : compulsory,
		validMessage : " ",
		wait : 500
	});
	lv.add(Validate.Format, {
		pattern : /$/i
	});
	if (compulsory) {
		lv.add(Validate.Presence);
	}
	return lv;
}

function validateEnter(g, isMobile) {
	var a = null;
	if (window.event) {
		a = window.event.keyCode;
	} else {
		if (g) {
			a = g.which;
		}
	}
	if (a == 13) {
		try {
			loginValidation(isMobile);
		} catch (d) {
		}
	}
	return;
}

function addLoginValidation() {
	userID = addTextCapabilities('loginForm:idUser', true, true, 3, 45);
	password = addTextCapabilities('loginForm:password', true, true, 5, 178 );
	
	return LiveValidation.massValidate([ userID, password ]);
}

function loginValidation(isMobile) {
	if (addLoginValidation()) {
		var encrypt = new JSEncrypt();
		encrypt.setPublicKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCax1rtbpOXX+N4jxZ8oT0o3oQx\n\N0hpAcJnPYFdFo9nIrdrvLyJCH04NGnkTmjyzVhYli2PLQYEMkhXK41ztHJi5oPAKNVbhnhgwgDUXvPglN4VKEDdLMjl14g++OaNfomw4copRLZshvNGwatoUdxa6MmrAc85uYpnmWlgeNa1xQIDAQAB");
		document.getElementById("loginForm:password").value = encrypt.encrypt(document.getElementById("loginForm:password").value);
		
		if (!isMobile)
			statusDialog.show();
		PrimeFaces.ab({
			formId : 'loginForm',
			partialSubmit : true,
			source : 'loginForm:loginButton',
			process : 'loginForm',
			update : 'loginForm:loginTable growl',
			oncomplete : function(xhr, status, args) {
				handleLoginRequest(xhr, status, args);
			}
		});
		return false;
	}
}

function validateForgotPassword() {
	userID = addTextCapabilities('forgotPasswordForm:userField', true, true, 3, 45);
	email = addEmailCapabilities("forgotPasswordForm:mailField", true, true);
	valid = LiveValidation.massValidate([ userID, email ]);
	if (valid) {
		statusDialog.show();
		PrimeFaces.ab({
			formId : 'forgotPasswordForm',
			partialSubmit : true,
			source : 'forgotPasswordForm:sendButton',
			process : '@all',
			update : 'growl forgotPasswordForm:passwordPanel'
		});
		return false;
	}
}

/**
 * OJGM
 * 
 * @param buttonL
 *            button name
 * @returns {Boolean}
 */
function validateAddUser(buttonL) {
	documentNumber = addTextCapabilities("formAddUser:documentNumber", true,true, 5, 45);
	nameUser = addTextCapabilities("formAddUser:nameUser", true, true, 3, 45);
	lastNameUser = addTextCapabilities("formAddUser:lastNameUser", true, true,3, 45);
	phoneNumber = addNumberCapabilities("formAddUser:phoneNumber", false, 7, 15,false);
	cellPhone = addNumberCapabilities("formAddUser:cellPhone", false, 10, 20,false);
	email = addEmailCapabilities("formAddUser:email", true, true);
	secondEmail = addEmailCapabilities("formAddUser:secondEmail", false, false);

	dt = addSelectCapabilities("formAddUser:documentType_input", "dtDiv");
	roleF = addSelectCheckBoxMenuCapabilities("formAddUser:roleF", "rDiv", 1);
	authMode = addSelectCapabilities("formAddUser:authMode_input", "authModeDiv");
	
	userLogin = addTextCapabilities("formAddUser:userLogin", true, true,3, 45);
	
	var passwordV = false;
	if (buttonL == 'addButtonU') {
		passwordV = LiveValidation.massValidate([ addPasswordCapabilities("formAddUser:password", "formAddUser:rePassword") ]);
	} else {
		if (document.getElementById('formAddUser:password').value != '') {
			passwordV = LiveValidation.massValidate([ addPasswordCapabilities( "formAddUser:password", "formAddUser:rePassword") ]);
		} else {
			passwordV = true;
		}
	}

	valid = LiveValidation.massValidate([ documentNumber, nameUser, cellPhone,lastNameUser, phoneNumber, email, secondEmail, userLogin ]);
	if (valid && dt && roleF && passwordV && authMode) {
		statusDialog.show();
		PrimeFaces.ab({
			formId : 'formAddUser',
			partialSubmit : true,
			source : 'formAddUser:' + buttonL,
			process : '@all'
		});
		return false;
	}
}

function validateObservationHistorical() {
	observationHistorical = addTextCapabilities("subjectForm:observationDeleteAbsentField", true,true, 2, 60);
		
	valid = LiveValidation.massValidate([ observationHistorical ]);
	if (valid ) {
		statusDialog.show();
		PrimeFaces.ab({
			formId : 'subjectForm',
			partialSubmit : true,
			source : 'subjectForm:deleteAbsentButton',
			process : '@this'
		});
		return false;
	}
}

function validateCreateDoctor() {

	VCDlastName = addTextCapabilities("medicalPanelForm:lastName", true, true,
			3, 50);
	VCDname = addTextCapabilities("medicalPanelForm:name", true, true, 3, 50);

	VCDhobbie = addTextCapabilities("medicalPanelForm:hobbie", true, true, 0,
			50);

	VCDinstitution = addTextCapabilities("medicalPanelForm:institution", true,
			true, 3, 50);
	VCDposition = addTextCapabilities("medicalPanelForm:position", true, true,
			3, 50);
	VCDaddress = addTextCapabilities("medicalPanelForm:address", true, true, 3,
			50);
	VCDneighborhood = addTextCapabilities("medicalPanelForm:neighborhood",
			true, true, 3, 50);
	VCDcontact = addNumberCapabilities("medicalPanelForm:contactNumber", true,
			1, 2, true);

	valid = LiveValidation.massValidate([ VCDlastName, VCDname, VCDinstitution,
			VCDposition, VCDaddress, VCDneighborhood, VCDcontact ]);

	if (valid) {
		statusDialog.show();
		PrimeFaces.ab({
			formId : "medicalPanelForm",
			source : "medicalPanelForm" + ':' + "submitCreateDoctor",
			process : '@all'
		});
		return false;
	}
}

function validateUpdateDoctor() {

	VUDlastName = addTextCapabilities("updateDoctorForm:lastName", true, true,
			3, 50);
	VUDname = addTextCapabilities("updateDoctorForm:name", true, true, 3, 50);

	VUDhobbie = addTextCapabilities("updateDoctorForm:hobbie", true, true, 0,
			50);

	VUDidentification = addTextCapabilities("updateDoctorForm:identification",
			true, true, 0, 15);

	VUDinstitution = addTextCapabilities("updateDoctorForm:institution", true,
			true, 3, 50);
	VUDposition = addTextCapabilities("updateDoctorForm:position", true, true,
			3, 50);
	VUDaddress = addTextCapabilities("updateDoctorForm:address", true, true, 3,
			50);
	VUDneighborhood = addTextCapabilities("updateDoctorForm:neighborhood",
			true, true, 3, 50);
	VUDcontact = addNumberCapabilities("updateDoctorForm:contactNumber", true,
			1, 2, true);

	valid = LiveValidation.massValidate([ VUDlastName, VUDname,
			VUDidentification, VUDinstitution, VUDposition, VUDaddress,
			VUDneighborhood, VUDcontact ]);

	if (valid) {
		statusDialog.show();
		PrimeFaces.ab({
			formId : "updateDoctorForm",
			source : "updateDoctorForm" + ':' + "submitCreateDoctor",
			process : '@all'
		});
		return false;
	}
}

function validateCreateReport() {

	observation = addTextCapabilities("subjectForm:txtObservationStudent", true, true, 2, 1000);	

	valid = LiveValidation.massValidate([ observation ]);

	if (valid) {
		statusDialog.show();
		PrimeFaces.ab({
			formId : "subjectForm",
			source : "subjectForm" + ':' + "btnReportStudent",
			process : '@all'
		});
		return false;
	}
	return true;
}

function validateCreateEvaluationItem() {

	nameEvaluationItem = addTextCapabilities("subjectForm:nameEvaluationItem", true, true, 1, 25);	
	percentageEvaluationItem = addTextCapabilities("subjectForm:percentageEvaluationItem_input", true, true, 1, 3);

	valid = LiveValidation.massValidate([ nameEvaluationItem, percentageEvaluationItem ]);

	if (valid) {
		statusDialog.show();
		PrimeFaces.ab({
			formId : "subjectForm",
			source : "subjectForm" + ':' + "addEvaluationItemButton",
			process : '@all',
			partialSubmit: 'true'
				
		});
		return false;
	}
	return true;
}

function validateUpdateDataReportStudent() {

	nameStudent = addTextCapabilities("riskStudentForm:nameStudent", true, true, 4, 50);
	phoneStudent = addTextCapabilities("riskStudentForm:phoneStudent", true,
			false, 7, 10);
	mobilePhoneStudent = addTextCapabilities("riskStudentForm:mobilePhoneStudent", true, false,
			10, 13);

	secondEmail = addEmailCapabilities("riskStudentForm:secondEmail", true, false);

	address = addTextCapabilities("riskStudentForm:address", true, true, 3,
			50);
	
	nameFamily = addTextCapabilities("riskStudentForm:nameFamily", true, true, 4, 50);
	phoneFamily = addTextCapabilities("riskStudentForm:phoneFamily", true,
			false, 7, 10);
	mobilePhoneFamily = addTextCapabilities("riskStudentForm:mobilePhoneFamily", true, false,
			7, 10);

	valid = LiveValidation.massValidate([ nameStudent, phoneStudent, mobilePhoneStudent, secondEmail, address, nameFamily, phoneFamily, mobilePhoneFamily    ]);

	if (valid) {
		statusDialog.show();
		PrimeFaces.ab({
			formId : "riskStudentForm",
			source : "riskStudentForm" + ':' + "updateDataReportStudenButton",
			process : '@all',
			partialSubmit: 'true'
				
		});
		return false;
	}
	return true;
}

function validateCreateCalificationItem() {

	nameCalificationItem = addTextCapabilities("subjectForm:nameCalificationItem", true, true, 1, 25);	
	percentageCalificationItem = addTextCapabilities("subjectForm:percentageCalificationItem_input", true, true, 1, 5);

	valid = LiveValidation.massValidate([ nameCalificationItem, percentageCalificationItem ]);

	if (valid) {
		statusDialog.show();
		PrimeFaces.ab({
			formId : "subjectForm",
			source : "subjectForm" + ':' + "addCalificationItemButton",
			process : '@all',
			partialSubmit: 'true'
				
		});
		return false;
	}
	return true;
}

function validateEditEvaluationItem() {

	nameEditEvaluationItem = addTextCapabilities("subjectForm:nameEditEvaluationItem", true, true, 1, 25);	
	percentageEditEvaluationItem = addTextCapabilities("subjectForm:percentageEditEvaluationItem_input", true, true, 1, 3);

	valid = LiveValidation.massValidate([ nameEditEvaluationItem, percentageEditEvaluationItem ]);

	if (valid) {
		statusDialog.show();
		PrimeFaces.ab({
			formId : "subjectForm",
			source : "subjectForm" + ':' + "EditEvaluationItemButton",
			process : '@all',
			partialSubmit: 'true'
				
		});
		return false;
	}
	return true;
}

function validateCreateReportFamily() {
	
	
	nameFamily = addTextCapabilities("inscriptionRegisterFamilyForm:nameFamily", true, true, 2, 60);	
	
	relationship = addSelectCapabilities("inscriptionRegisterFamilyForm:relationshipTypeSelect_input", "Div1");

	documentFamily = addTextCapabilities("inscriptionRegisterFamilyForm:documentFamily", true, true, 6, 11);

	email = addEmailCapabilities("inscriptionRegisterFamilyForm:emailFamily", true, true);

	telephone = addTextCapabilities("inscriptionRegisterFamilyForm:TelephoneFamily", true,
			false, 7, 10);
	celphone = addTextCapabilities("inscriptionRegisterFamilyForm:mobilePhone", true, false,
			10, 13);
	address = addTextCapabilities("inscriptionRegisterFamilyForm:address", true, true, 3,
			110);
	
	/*nameStudent = addTextCapabilities("inscriptionRegisterFamilyForm:nameStudent",
			true, true, 2, 60);
	documentStudent = addTextCapabilities("inscriptionRegisterFamilyForm:documentStudent",
			true, true, 6, 50);
	emailstudent = addEmailCapabilities("inscriptionRegisterFamilyForm:emailStudent", true, true);*/
	
	observation = addTextCapabilities("inscriptionRegisterFamilyForm:txtObservationStudent",
			true, true, 2, 1000);
	

	valid = LiveValidation.massValidate([ nameFamily, observation, documentFamily, email, address, telephone, celphone]);

	if (valid && relationship ) {
		statusDialog.show();
		PrimeFaces.ab({
			formId : "inscriptionRegisterFamilyForm",
			source : "inscriptionRegisterFamilyForm" + ':' + "btnFamilyStudentReport",
			process : '@all',
			partialSubmit: 'true'
		});
		return false;
	}
	return true;
}

function validateCreatePharmacy() {

	VCPname = addTextCapabilities("pharmacyPanelForm:name", true, true, 3, 50);
	VCPphone = addNumberCapabilities("pharmacyPanelForm:phone", true, 7, 12,
			true);
	VCPmayorChanel = addTextCapabilities("pharmacyPanelForm:mayorChanel", true,
			true, 4, 25);
	VCPaddress = addTextCapabilities("pharmacyPanelForm:address", true, true,
			4, 10);

	VCPneighborhood = addTextCapabilities("pharmacyPanelForm:neighborhood",
			true, true, 3, 25);
	VCPbrick = addTextCapabilities("pharmacyPanelForm:brick", true, true, 3, 25);
	VCPcontact = addNumberCapabilities("pharmacyPanelForm:contactNumber", true,
			1, 3, true);

	valid = LiveValidation.massValidate([ VCPname, VCPphone, VCPaddress,
			VCPneighborhood, VCPmayorChanel, VCPbrick, VCPcontact ]);

	if (valid) {
		statusDialog.show();
		PrimeFaces.ab({
			formId : "pharmacyPanelForm",
			source : "pharmacyPanelForm" + ':' + "btnCreatePharmacy",
			process : '@all'
		});
		return false;
	}
	return true;
}

function validateCreateAutoReport() {

	observation = addTextCapabilities("autoReporForm:txtObservationAutoStudent", true, true, 2, 1000);	

	valid = LiveValidation.massValidate([ observation ]);

	if (valid) {
		statusDialog.show();
		PrimeFaces.ab({
			formId : "autoReporForm",
			source : "autoReporForm" + ':' + "btnAutoReportStudent",
			process : '@all',
			partialSubmit: 'true'
		});
		return false;
	}
	return true;
}

function validateUpdatePharmacy() {

	VCPname = addTextCapabilities("pharmacyEditForm:name", true, true, 3, 50);
	VCPphone = addNumberCapabilities("pharmacyEditForm:phone", true, 7, 12,
			true);
	VCPmayorChanel = addTextCapabilities("pharmacyEditForm:mayorChanel", true,
			true, 4, 25);
	VCPaddress = addTextCapabilities("pharmacyEditForm:address", true, true, 4,
			10);

	VCPneighborhood = addTextCapabilities("pharmacyEditForm:neighborhood",
			true, true, 3, 25);
	VCPbrick = addTextCapabilities("pharmacyEditForm:brick", true, true, 3, 25);
	VCPcontact = addNumberCapabilities("pharmacyEditForm:contactNumber", true,
			1, 3, true);

	valid = LiveValidation.massValidate([ VCPname, VCPphone, VCPaddress,
			VCPneighborhood, VCPmayorChanel, VCPbrick, VCPcontact ]);

	if (valid) {
		statusDialog.show();
		PrimeFaces.ab({
			formId : "pharmacyEditForm",
			source : "pharmacyEditForm" + ':' + "btnCreatePharmacy",
			process : '@all'
		});
		return false;
	}
	return true;
}

function validateAddContact() {
	VCP1name = addTextCapabilities("pharmacyPanelForm:administratorName", true,
			true, 3, 50);
	VCP1lastName = addTextCapabilities(
			"pharmacyPanelForm:administratorLastName", true, true, 3, 50);
	VCP1document = addTextCapabilities(
			"pharmacyPanelForm:administratorDocument", true, true, 3, 50);

	valid = LiveValidation
			.massValidate([ VCP1name, VCP1lastName, VCP1document ]);

	if (valid) {
		statusDialog.show();
		PrimeFaces.ab({
			formId : "pharmacyPanelForm",
			source : "pharmacyPanelForm" + ':' + "btnAddContact",
			process : '@all'
		});
		return false;
	}
	return true;

}

function validateAddRiskFactor() {
	alert('asdasd');
	name = addTextCapabilities("riskFactorForm:nameRiskFactor", true, true, 2, 100);
	description = addTextCapabilities("riskFactorForm:descriptionRiskFactor", true, true, 2, 100);
	category = addSelectCapabilities("riskFactorForm:riskFactorCategorySelect_input", "selectCategory");

	valid = LiveValidation
			.massValidate([ name, description]);

	if (valid && category) {
		statusDialog.show();
		PrimeFaces.ab({
			formId : "riskFactorForm",
			source : "riskFactorForm" + ':' + "buttonAddRiskFactor",
			process : '@all'
		});
		return false;
	}
	return true;

}

function validateAddContactUpdate() {
	VCP1name = addTextCapabilities("pharmacyEditForm:administratorName", true,
			true, 3, 50);
	VCP1lastName = addTextCapabilities(
			"pharmacyEditForm:administratorLastName", true, true, 3, 50);
	VCP1document = addTextCapabilities(
			"pharmacyEditForm:administratorDocument", true, true, 3, 50);

	valid = LiveValidation
			.massValidate([ VCP1name, VCP1lastName, VCP1document ]);

	if (valid) {
		statusDialog.show();
		PrimeFaces.ab({
			formId : "pharmacyEditForm",
			source : "pharmacyEditForm" + ':' + "btnAddContact",
			process : '@all'
		});
		return false;
	}
	return true;
}

function addDoctor() {
	alert("ALERTAQ");
}

function aaalert() {
	alert("ALERTAQ");
}

function validateAddDependenceUser() {
	userD = addSelectCapabilities("dependenceForm:userList_input", "userDiv");

	if (userD) {
		statusDialog.show();
		PrimeFaces.ab({
			formId : "dependenceForm",
			source : "dependenceForm:addUser",
			process : '@all'
		});
		return false;
	}
	return true;
}
/**
 * @author mtorres
 */
function valRegisterStudent() {

	var form = "registerStudentForm";

	observation = addTextCapabilities(form + ":txtObservationStudent", true,
			true, 5, 2000);

	valid = LiveValidation.massValidate([ observation ]);
	if (valid) {
		statusDialog.show();
		PrimeFaces.ab({
			formId : form,
			source : form + ':' + "btnRegisterStudent",
			process : '@all',
			partialsubmit : true
		});
		return false;
	}
}
function validateSearchUser() {
	var form = "registerStudentForm";
	txtSearch = addTextCapabilities(form + ":txtSearchUser", true, true, 7, 12);
	valid = LiveValidation.massValidate([ txtSearch ]);
	if (valid) {
		statusDialog.show();
		PrimeFaces.ab({
			formId : form,
			source : form + ':' + "btnSearchUser",
			process: '@form',
			update: 'registerStudentForm:notExistUserMessage',
			partialsubmit : true
		});
		return false;
	}
}

function valRegisterTeacher() {

	var form = "inscriptionTeacherForm";
	nameTeacher = addTextCapabilities(form + ":nameUser", true, true, 5,
			100);
	documentTeacher = addTextCapabilities(form + ":document", true,
			true, 7, 12);
	emailTeacher = addEmailCapabilities(form + ":email", true, true);
	
	passTeacher = addTextCapabilities(form + ":password", true,
			true, 6, 20);

	valid = LiveValidation.massValidate([ nameTeacher, documentTeacher,
	                                      emailTeacher, passTeacher]);
	if (valid) {
		statusDialog.show();
		PrimeFaces.ab({
			formId : form,
			source : form + ':' + "buttonInsTeacherForm",
			process : '@all',
			partialsubmit : true
		});
		return false;
	}
}

function valRegisterClassmate() {

	var form = "inscriptionClassmateForm";
	nameClassmate = addTextCapabilities(form + ":nameUser", true, true, 5,
			100);
	documentClassmate  = addTextCapabilities(form + ":document", true,
			true, 7, 12);
	emailClassmate  = addEmailCapabilities(form + ":email", true, true);
	
	passClassmate  = addTextCapabilities(form + ":password", true,
			true, 6, 20);

	valid = LiveValidation.massValidate([ nameClassmate , documentClassmate ,
	                                      emailClassmate , passClassmate ]);
	if (valid) {
		statusDialog.show();
		PrimeFaces.ab({
			formId : form,
			source : form + ':' + "buttonInsClassmForm",
			process : '@all',
			partialsubmit : true
		});
		return false;
	}
}

function validateDataProcess() {
	stage = addSelectCapabilities("riskStudentForm:stage_input",
	"stageDiv");
	responsible = addSelectCapabilities("riskStudentForm:menuResponsible_input",
	"responsibleDiv");
	state = addSelectCapabilities("riskStudentForm:state_input",
	"stateDiv");
	observation = addDescriptionCapabilities("riskStudentForm:observationArea", true,
			true, 1, 999);

	
	valid = LiveValidation.massValidate([ observation ]);
	if (valid && stage && responsible && state) {
		statusDialog.show();
		PrimeFaces.ab({
			formId : "riskStudentForm",
			source : "riskStudentForm" + ':' + "buttonProcess",
			process : '@all',
			partialsubmit : true,
		});
		PF('giveProcessWV').show();
		return false;
	}

}
function validateAddRiskFactor(){
	
	var form = "riskFactorForm";
	valNameRf = addTextCapabilities(form+":nameParameter", true, true, 1, 120);
	valDescriptionRf = addTextCapabilities(form+":descriptionParameter", true, true, 1, 120);
	valid = LiveValidation.massValidate([ valNameRf,  valDescriptionRf]);
	
	if (valid) {
		statusDialog.show();
		PrimeFaces.ab({
			formId : form,
			source : form + ':' + "buttonAddParameter",
			process : '@all',
			partialsubmit : true
		});
		return false;
	}
}

function validateAddZyosGroup(action) {
	codeCa = addTextCapabilities("addZyosGroupForm:code", true, true, 1, 50);
	nameCa = addTextCapabilities("addZyosGroupForm:nameCa", true, true, 1, 100);

	valid = LiveValidation.massValidate([ codeCa, nameCa ]);

	if (valid) {
		statusDialog.show();
		PrimeFaces.ab({
			formId : 'addZyosGroupForm',
			partialSubmit : true,
			source : 'addZyosGroupForm:' + action,
			process : '@all'
		});
		return false;
	}
}