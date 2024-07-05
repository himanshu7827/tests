var numericRegex='';
var numericErrorMessage='';
var nameRegex='';
var nameErrorMessage='';
var emailRegex='';
var emailErrorMessage='';
var websiteRegex='';
var websiteErrorMessage='';
var addressRegex= '';
var addressErrorMessage='';
var amountRegex='';
var amountErrorMessage='';
messageResource.init({	  
filePath : '/PMS/resources/app_srv/PMS/global/js'
});

messageResource.load('regexValidationforjs', function(){
	  // get value corresponding  to a key from regexValidationforjs.properties  
	numericRegex= messageResource.get('numeric.regex', 'regexValidationforjs');
	numericErrorMessage = messageResource.get('numeric.regex.message', 'regexValidationforjs');
	nameRegex= messageResource.get('name.regex', 'regexValidationforjs');
	nameErrorMessage = messageResource.get('name.regex.message', 'regexValidationforjs');
	emailRegex= messageResource.get('email.regex', 'regexValidationforjs');
	emailErrorMessage = messageResource.get('email.regex.message', 'regexValidationforjs');	
	websiteRegex = messageResource.get('website.regex', 'regexValidationforjs');
	websiteErrorMessage= messageResource.get('website.regex.message', 'regexValidationforjs');
	addressRegex= messageResource.get('address.regex', 'regexValidationforjs');
	addressErrorMessage= messageResource.get('address.regex.message', 'regexValidationforjs');
	amountRegex= messageResource.get('amount.regex', 'regexValidationforjs');
	amountErrorMessage=messageResource.get('amount.regex.message', 'regexValidationforjs');
	
	});

maxLengthName = 200;
maxLengthContact = 15;
maxLengthEmail = 200;
maxLengthWebsite = 200;
maxLengthAddress = 2000;

function validateAddress($address){		
	 var regex = new RegExp(addressRegex);	
	    return regex.test($address);
}

function validateName($name){		
	 var regex = new RegExp(nameRegex);
	 return regex.test($name);
}

function validateEmail($email){		
	 var regex = new RegExp(emailRegex);	 
	    return regex.test($email);
}

function validateWebsite($website){		
	 var regex = new RegExp(websiteRegex);	
	    return regex.test($website);
}

var totalRowInTable =0;

function removeRow(trId){
	console.log(trId);
	var tempId =""+trId;
	if(tempId.indexOf("_")>-1){
		
	}else{
		$("#tr"+trId).remove();
	}
	//alert(trId);
}


$(document).ready(function() {
	$('.select2Option').select2({
		width : '100%'
	});
});



$(document)
		.ready(function(){	

		$('#form1').bootstrapValidator({							
										excluded : ':disabled',
										message : 'This value is not valid',
										feedbackIcons : {
											valid : 'glyphicon glyphicon-ok',
											invalid : 'glyphicon glyphicon-remove',
											validating : 'glyphicon glyphicon-refresh'
										},
										fields : {
											moduleTypeId : {
												validators : {
													callback : {
														message : 'Please choose Module Type',
														callback : function(
																value,
																validator) {
															var options = validator
																	.getFieldElements(
																			'moduleTypeId')
																	.val();
															return (options != 0);
														}
													}
												}
											},
											
											businessTypeId : {
												validators : {
													callback : {
														message : 'Please choose Business Type',
														callback : function(
																value,
																validator) {
															var options = validator
																	.getFieldElements(
																			'businessTypeId')
																	.val();
															return (options != 0);
														}
													}
												}
											},
											projectTypeId : {
												validators : {
													callback : {
														message : 'Please choose Project Type',
														callback : function(
																value,
																validator) {
															var options = validator
																	.getFieldElements(
																			'projectTypeId')
																	.val();
															return (options != 0);
														}
													}
												}
											},
											categoryId : {
												validators : {
													callback : {
														message : 'Please choose Project Category',
														callback : function(
																value,
																validator) {
															var options = validator
																	.getFieldElements(
																			'categoryId')
																	.val();
															return (options != 0);
														}
													}
												}
											},
											

										}
									});

		$('#save')
		.click(
				function() {
					if(validateCollaborativeOrganisation()){
						
						var bootstrapValidator = $("#form1")
						.data('bootstrapValidator');
				bootstrapValidator.validate();
				if (bootstrapValidator.isValid()) {
					console.log('Validated');
					document.getElementById("save").disabled = true;
					
					 var frm = $('#form1');
					 var moduleTypeId=$("#moduleTypeId").val();
					var businessTypeId = $("#businessTypeId").val();
					var projectTypeId = $("#projectTypeId").val();
					var categoryId = $("#categoryId").val();
				
		
					 $
						.ajax({
							type : "POST",
							url : "/PMS/saveUpdateWorkflowTypeDetails",
							data : {
								"moduleTypeId" : moduleTypeId,
								"businessTypeId" : businessTypeId,
								"projectTypeId" : projectTypeId,
								"categoryId" : categoryId,
							},
							success : function(
									response) {
								
								/*console.log(data);
					        	   if(data.encNumId){
					        		   $('#next').removeClass('hidden');
					        	   }*/
					               

							},
							error : function(e) {
								alert('Error: ' + e);
							}
						});
					 
				
					return true;

				} else {
					console.log('Not Validated');
				}
					}
					
				});
				});

function validateCollaborativeOrganisation() {
	var categoryId = $('#categoryId').val();
	if (categoryId == 2) {

		var flag = 1;
		$('.colOrgName')
				.each(
						function(index) {
							var fieldId = this.id;
							var fieldValue = this.value;							
							if (!fieldValue) {
								flag = 0;
								sweetSuccess('Attention',
										'Collaborative Organisation Name is mandatory in each Row');
								return false;
							}else{
								if(fieldValue.length > maxLengthName){
									$('#'+fieldId).focus();
									flag = 0;
									sweetSuccess('Attention','Collaborative Organisation Name can cantain '+maxLengthName +' characters');
									return false;									
								}
								
								var result = validateName(fieldValue);								
								if( result == false){
									$('#'+fieldId).focus();
									flag = 0;
									sweetSuccess('Attention','Collaborative Organisation Name '+nameErrorMessage);
									return false;
								}else{
									flag = 1;
								}
							}

						});
		
		if (flag == 1) {
			$('.colOrgContact')
			.each(
					function(index) {
						var fieldId = this.id;
						var fieldValue = this.value;
						if (!fieldValue) {
							flag = 0;
							$('#'+fieldId).focus();
							sweetSuccess('Attention',
									'Collaborative Organisation Contact is mandatory in each Row');
							return false;
						}else{
							if(fieldValue.length > maxLengthContact){
								$('#'+fieldId).focus();
								flag = 0;
								sweetSuccess('Attention','Collaborative Organisation Contact can ccntain '+maxLengthContact +' characters');
								return false;									
							}else{
								flag = 1;
							}
						}

					});
		}
	
		if (flag == 1) {
			$('.colOrgEmail')
			.each(
					function(index) {
						var fieldId = this.id;
						var fieldValue = this.value;
						if (!fieldValue) {
							flag = 0;
							$('#'+fieldId).focus();
							sweetSuccess('Attention',
									'Collaborative Organisation Email is mandatory in each Row');
							return false;
						}else{
							if(fieldValue.length > maxLengthEmail){
								$('#'+fieldId).focus();
								flag = 0;
								sweetSuccess('Attention','Collaborative Organisation Email can contain '+maxLengthEmail +' characters');
								return false;									
							}
							
							var result = validateEmail(fieldValue);								
							if( result == false){
								flag = 0;
								$('#'+fieldId).focus();
								sweetSuccess('Attention',emailErrorMessage);
								return false;
							}else{
								flag = 1;
							}
						}

					});
		}

		if (flag == 1) {
			$('.colOrgAddr')
					.each(
							function(index) {
								var fieldId = this.id;
								var fieldValue = $(this).val().trim();
								console.log(fieldId + ' ' + fieldValue );
								if (!fieldValue) {
									flag = 0;
									$('#'+fieldId).focus();
									sweetSuccess('Attention',
											'Collaborative Organisation Address is mandatory in each Row');
									return false;
								}else{	
									if(fieldValue.length > maxLengthAddress){
										$('#'+fieldId).focus();
										flag = 0;
										sweetSuccess('Attention','Collaborative Organisation Address can contain '+maxLengthAddress +' characters');
										return false;									
									}
									
									var result = validateAddress(fieldValue);							
									if( result == false){
										flag = 0;
										$('#'+fieldId).focus();
										sweetSuccess('Attention',addressErrorMessage);
										return false;
									}else{
										flag = 1;
									}
								}

							});
		}		
		
		if (flag == 1) {
			$('.colOrgWeb').each(function(index) {
				var fieldId = this.id;
				var fieldValue = this.value;
				console.log(fieldValue);
				if (fieldValue) {
					var result = validateWebsite(fieldValue);								
					if( result == false){
						flag = 0;
						$('#'+fieldId).focus();
						sweetSuccess('Attention',websiteErrorMessage);
						return false;
					}else{
						if(fieldValue.length > maxLengthWebsite){
							$('#'+fieldId).focus();
							flag = 0;
							sweetSuccess('Attention','Collaborative Organisation website can contain '+maxLengthWebsite +' characters');
							return false;									
						}
						
						flag = 1;
					}
				}

			});
		}

		if (flag == 1) {
			return true;
		}

	} else {
		return true;
	}
}