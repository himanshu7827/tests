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
//regex added by devesh on 28-05-24 to apply validation on outlay
var outlayRegex=/^\d+$/;
var outlayRegexMessage='Only numeric values without decimals are allowed';
//end of outlay regex
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



function gonext(appId)
{
	 var path=$(location).attr('pathname');  

	 document.getElementById("form1").action="/PMS/nextRedirectPage?path='"+path+"'&workflowTypeId=2&uniqueId="+appId;
		document.getElementById("form1").method="POST";
		document.getElementById("form1").submit();

}


$(document).ready(function() {
	  $('#dateOfSubmission').datepicker({
		   dateFormat: 'dd/mm/yy', 
		    changeMonth: true,
		    changeYear:true, 
		    onSelect: function(date){
		    	$('#dateOfSubmission').trigger('input');
		    	var dateData = date.split('/');
		    	var monthval = parseInt(dateData[1])-1;
		        var selectedDate = new Date(dateData[2],monthval,dateData[0]);       

		       //Set Minimum Date of EndDatePicker After Selected Date of StartDatePicker
		        $("#clearanceReceivedDate").datepicker( "option", "minDate", selectedDate );
		    }
	   });
	   
	   $('#clearanceReceivedDate').datepicker({
		   dateFormat: 'dd/mm/yy', 
		    changeMonth: true,
		    changeYear:true,  
	   });
	   				
	
	
					 totalRowInTable = $("#collaborativeOrgDtlTbl tr").length - 1;

					$('.select2Option option:odd').addClass('oddRow');

					$('#organisationId')
							.change(
									function() {
										var organisationId = $(
												'#organisationId').val();
										if (organisationId != 0) {
											$
													.ajax({
														type : "POST",
														url : "/PMS/mst/getGroupByOrganisationId",
														data : {"organisationId":organisationId},
														success : function(
																response) {
															var selectList = $("#groupId");
															selectList
																	.find(
																			'option:gt(0)')
																	.remove();
															for (var i = 0; i < response.length; i++) {
																var rowData = response[i];
																$('#groupId')
																		.append(
																				$(
																						"<option></option>")
																						.attr(
																								"value",
																								rowData.numId)
																						.text(
																								rowData.groupName));
															}

														},
														error : function(e) {
															alert('Error: ' + e);
														}
													});
										} else {
											var selectList = $("#groupId");
											selectList.find('option:gt(0)')
													.remove();
										}
									});

					$('#categoryId').change(function() {
						var categoryId = $('#categoryId').val();
						if (categoryId == 2) {
							$('#collaborativeOrgDtlDiv').removeClass('hidden');
						} else {
							$('#collaborativeOrgDtlDiv').addClass('hidden');
						}
					});
				});

$(document).ready(function() {
	$('.select2Option').select2({
		width : '100%'
	});
	
	$('#reset').click(function(){
		resetForm();
		 $('#form1').data('bootstrapValidator').resetForm(true);

	});
	
});

$(document)
		.ready(
				function() {
					$('#addRow')
							.on(
									"click",
									function() {
										$(".select2Option").select2("destroy");
										var $template = $('#optionTemplate');
										//var i = $("#collaborativeOrgDtlTbl tr").length - 1;
										var i = totalRowInTable;
										totalRowInTable+=1;
										var temp = 'collaborativeOrg[' + i
												+ ']';
										var trId= 'tr'+i;
										var tableRow = "<tr id="+trId+">";
										tableRow += "<td><input type='text'  class='input-field colOrgName' name='collaborativeOrg["+i+"].organisationName' id='collaborativeOrg["
												+ i
												+ "].organisationName' /></td>";
										tableRow += "<td><input type='text' class='input-field colOrgContact' name='collaborativeOrg["+i+"].contactNumber' id='collaborativeOrg["
												+ i
												+ "].contactNumber' /> </td>";
										tableRow += "<td><input type='text' class='input-field colOrgEmail' name='collaborativeOrg["+i+"].email' id='collaborativeOrg["
												+ i + "].email'/> </td>";
										tableRow += "<td><input type='text' class='input-field colOrgWeb' name='collaborativeOrg["+i+"].website' id='collaborativeOrg["
												+ i + "].website' /> </td>";
										tableRow += "<td><textarea  class='input-field colOrgAddr' name='collaborativeOrg["+i+"].organisationAddress' id='collaborativeOrg["
												+ i
												+ "].organisationAddress' ></textarea>  <a class='red' onclick='removeRow("+i+")'> <i class='fa fa-times '></i> </a>	</td>";

										tableRow += "</tr>";

										$('#collaborativeOrgDtlTbl').append(
												tableRow);

										$('.select2Option').select2({
											width : '100%'
										});

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
		/*									businessTypeId : {
												validators : {
													callback : {
														message : 'Please choose Business Type',
														callback : function(
																value,
																validator) {
															var options = validator
																	.getFieldElements(
																			'businessTypeId').val();
															return (options != 0);
														}
													}
												}
											},*/
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
											organisationId : {
												validators : {
													callback : {
														message : 'Please choose Organisation',
														callback : function(
																value,
																validator) {
															var options = validator
																	.getFieldElements(
																			'organisationId')
																	.val();
															return (options != 0);
														}
													}
												}
											},
											groupId : {
												validators : {
													callback : {
														message : 'Please choose Group',
														callback : function(
																value,
																validator) {
															var options = validator
																	.getFieldElements(
																			'groupId')
																	.val();
															return (options != 0);
														}
													}
												}
											},
											clientId:{
									        	  validators: {
									                  callback: {
									                      message: 'Please choose Client ',
									                      callback: function(value, validator) {
									                          // Get the selected options
									                          var options = validator.getFieldElements('clientId').val();
									                          return (options != 0);
									                      }
									                  }
									              }
									          },
									      /*    endUserId:{
									        	  validators: {
									                  callback: {
									                      message: 'Please choose End User',
									                      callback: function(value, validator) {
									                          // Get the selected options
									                          var options = validator.getFieldElements('endUserId').val();
									                          return (options != 0);
									                      }
									                  }
									              }
									          },*/
									          
									          contactPersonId:{
									        	  validators: {
									                  callback: {
									                      message: 'Please choose Contact Person ',
									                      callback: function(value, validator) {
									                          // Get the selected options
									                          var options = validator.getFieldElements('contactPersonId').val();
									                          return (options != 0);
									                      }
									                  }
									              }
									          },
									          proposalTitle: {
									              group: '.col-md-12',
									              validators: {
									                  notEmpty: {
									                      message: 'Proposal Title is required and cannot be empty'
									                  },
									                  stringLength: {
									                        max: 300,
									                        message: 'Proposal Title name must be less than 300 characters'
									                    },
									                    regexp: {
									                        regexp: nameRegex,
									                       message: nameErrorMessage
									                    }
									              }
									          },
									          thrustAreaId:{
									        	  validators: {
									                  callback: {
									                      message: 'Please choose Thrust Area ',
									                      callback: function(value, validator) {
									                          // Get the selected options
									                          var options = validator.getFieldElements('thrustAreaId').val();
									                          return (options != 0);
									                      }
									                  }
									              }
									          },
									           
											totalProposalCost : {
												validators : {
													notEmpty : {
														message : 'Total proposal cost is required and cannot be empty'
													},
													stringLength : {
														max : 20,
														message : 'The total proposal cost must be less than 20 digits'
													},
													regexp : {
														/*regexp : amountRegex,
														message : amountErrorMessage*/
														//Modified by devesh on 28-05-24 to not allow decimal values
														regexp : outlayRegex,
														message : outlayRegexMessage
														//End of code
													},
													// CallBack function for not allowing the String value '0' in the field
													callback: {
									                      message: 'Total proposal cost should be greater than 0',
									                      callback: function(value, validator) {
									                          var options = validator.getFieldElements('totalProposalCost').val();
									                          return options !== '0';
									                      }
									                  }
													
												}
											},
										
											
											dateOfSubmission: {
												  validators: {
												notEmpty: {
								                      message: 'Date of Submission to Corporate is required and cannot be empty'
								                  },
											date:{
									        	  format: 'DD/MM/YYYY'
									        	  }
											
												  }
											},
											clearanceReceivedDate: {
												  validators: {
												notEmpty: {
								                      message: 'Clearance Received Date is required and cannot be empty'
								                  },
											date:{
									        	  format: 'DD/MM/YYYY'
									        	  }
											
												  }
											
											}
										}
									});

		$('#save').click(function() {
					if($('#corporateCheck').is(":checked")) {
					   	 $('#form1').bootstrapValidator('enableFieldValidators', 'dateOfSubmission',true);
					   	 $('#form1').bootstrapValidator('enableFieldValidators', 'clearanceReceivedDate',true);


					}else{
					   	 $('#form1').bootstrapValidator('enableFieldValidators', 'dateOfSubmission',false);
					   	 $('#form1').bootstrapValidator('enableFieldValidators', 'clearanceReceivedDate',false);

					}
					
					if(validateCollaborativeOrganisation()){
						
						var bootstrapValidator = $("#form1")
						.data('bootstrapValidator');
				bootstrapValidator.validate();
				if (bootstrapValidator.isValid()) {
					console.log('Validated');
					document.getElementById("save").disabled = true;
					 	
					 var frm = $('#form1');
				/*	var businessTypeId = $("#businessTypeId").val();
					var projectTypeId = $("#projectTypeId").val();
					var categoryId = $("#categoryId").val();
					var organisationId = $("#organisationId").val();
					var groupId = $("#groupId").val();
					var totalProposalCost = $("#totalProposalCost").val();
					var thrustAreaId=$("#thrustAreaId").val();
					var clientId=$("#clientId").val();
					var contactPersonId=$("#contactPerson").val();*/
					 console.log($("#form1").serialize());
					 console.log($('#form1 :input').filter(function(i) { return ($(this).val().length != 0); }).serialize() );
					 $
						.ajax({
							type : "POST",
							url : "/PMS/saveUpdateApplicationBasicDetails",
							data: $('#form1 :input').filter(function(i) { return ($(this).val().length != 0); }).serialize(),							
							success : function(
									response) {
								//console.log(response.numId +"=="+response.moduleTypeId+"=="+response.businessTypeId+"=="+response.projectTypeId+"=="+response.categoryId);
								if(response.numId >0 && (response.errorMessage ==null || response.errorMessage ==undefined)){
									$('#encNumId').val(response.encNumId);
									startWorkFlow(response.numId,1,response.collaborative);
								}	
								else
									document.getElementById("save").disabled = false;
					               

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

function abc() {
	//alert("gfgtff");
	var clientNum = $('#clientId').val();
	$('#contactPerson').find('option').remove().end();
	$.ajax({

		type : "POST",
		url : "/PMS/mst/getContactByClient",
		data : "clientId=" + clientNum,
		success : function(response) {
			$('#contactPerson').append(
					$('<option/>').attr("value", -1).text("Select"));
			for ( var i = 0; i < response.length; ++i) {
				//alert("+response[i].numCaseId===" + i + "i"+ response[i].groupId);
				$('#contactPerson').append(
						$('<option/>').attr("value", response[i].contactPersonId)
								.text(response[i].contactPerson));
				
			}
		},
		error : function(e) {
			alert('Error: ' + e);
		}
	});
}

function resetForm(){
	$('#numId').val(0);
	$('#encNumId').val('');
	$('#businessTypeId').val("0").trigger('change');
	$('#projectTypeId').val("0").trigger('change');
	$('#categoryId').val("0").trigger('change');
	$('#organisationId').val("0").trigger('change');
	$('#groupId').val("0").trigger('change');
	$('#thrustAreaId').val("0").trigger('change');
	$('#clientId').val("0").trigger('change');
	$('#contactPerson').val("0").trigger('change');
	$('#totalProposalCost').val('');
	$('#proposalTitle').val('');
	$('#save').text('Save');
}

$(document).ready(function() {
	if($('#corporateCheck').is(":checked"))
    $('#corporateApprovalDiv1').show();
    else
    $('#corporateApprovalDiv1').hide();
	$('#corporateCheck').click(function () {
        if ($(this).is(":checked")) {
        $('#corporateApprovalDiv1').show();
	   	$('#form1').bootstrapValidator('enableFieldValidators', 'dateOfSubmission',true);
	   	 $('#form1').bootstrapValidator('enableFieldValidators', 'clearanceReceivedDate',true);

	}
    else
    	{
        $('#corporateApprovalDiv1').hide();
	   	 $('#form1').bootstrapValidator('enableFieldValidators', 'dateOfSubmission',false);
	   	 $('#form1').bootstrapValidator('enableFieldValidators', 'clearanceReceivedDate',false);

    	}
	})
	
})

function startWorkFlow(applicationId,moduleTypeId,collaborative){
	var numCollaborative=1;
	if(collaborative)
		numCollaborative=2;
openWindowWithPost('POST','/PMS/startWorkflow',{"numId":applicationId,"moduleTypeId":moduleTypeId,"categoryId":numCollaborative},'_self');
}

function validateAmount(amount){		
	//amountRegex = new RegExp(numericRegex);
	 let testOutlayRegex = /^\d+$/;	//Modified by devesh on 28-05-24 to not allow decimal values in CDAC outlay		
	 if (testOutlayRegex.test(amount)) {  
	    return (true)  ;
	  }else{
		  sweetSuccess("Attention","Decimal values are not allowed. Please enter the value in INR.");
		  return (false)  ; 
	  }		
}

//Added by devesh on 04-10-23 to store intial totalproposalcost

var initialTotalProposalCost = $("#totalProposalCost").val();

//End of storing totalproposalcost

function saveField(fieldId){
	var fieldValue = $('#'+fieldId).val();	
	var proposalId=$('#numId').val();
	var validationFlag= true;	
	//Added by devesh on 03-10-23 to add validation on total cost
	var totalCost=$("#totalProposalCost").val();
	var proposalCost=$("#proposalCost").val();
	var categoryValue = $('#categoryId').val();
	
	if ($("#"+fieldId).hasClass("validateAmt")) {
		/*-------  Skip If the Category ID match with internal project id [02-02-2024] ----*/
		var categoryValue = $('#categoryId').val();
				if(categoryValue!=7 && !validateAmount(fieldValue)){
					//$('#'+fieldId).val(0);
					//Modified by devesh on 28-05-24 to not allow decimal values in Total outlay
					$('#'+fieldId).val(initialTotalProposalCost).trigger('change');
					$('#form1').bootstrapValidator('revalidateField', $('#totalProposalCost').prop('name'));
					sweetSuccess("Attention","Decimal values are not allowed. Please enter the value in INR.");
					//End of code
					validationFlag= false;
					return false;
				}
	 
	 }
	
	if(categoryValue!=7 && parseFloat(totalCost)<parseFloat(proposalCost)){
		sweetSuccess("Attention","CDAC Noida Outlay Share can not be greater than Total Proposal Cost");
		$("#" + fieldId).val(initialTotalProposalCost);
		return false;
	}
	else{
		initialTotalProposalCost = totalCost;
	}
	//End of validation
	
	
	
	
	$.ajaxRequest.ajax({
		type : "POST",
		url : "/PMS/saveUpdateApplicationBasicDetails",
		data :$('#form1').serialize(),
		async:false,
		success : function(response) {			
			if(response.numId > 0){
				$('#numId').val(response.numId);
				$('#encNumId').val(response.encNumId);
			}
		}, error: function(data) {
	 	  
	   }
	});

	
}

function openChildWindow(url){
	var encApplicationId = $('#encNumId').val();
	if(encApplicationId){
		viewDetails(url+"?encNumId="+encApplicationId);
	}else{
		viewDetails(url);
	}
}

//Added by devesh on 04-10-23 to set a flag if total proposal cost changes
var initialTotalProposalCost1 = $("#totalProposalCost").val();
document.addEventListener('DOMContentLoaded', function() {
    const totalProposalCostInput = document.getElementById('totalProposalCost');
    const ApplicationId = $('#numId').val();
    
    totalProposalCostInput.addEventListener('blur', function() {
      const currentValue = totalProposalCostInput.value;
      const previousValue = localStorage.getItem(ApplicationId+'previousTotalProposalCost');

      /*console.log("currentValue.."+currentValue);
      console.log("initialTotalProposalCost1.."+initialTotalProposalCost1);*/
      if(previousValue!=null){
      	if (currentValue !== previousValue) {
            // Value has changed, set a flag in localStorage
            localStorage.setItem(ApplicationId+'totalProposalCostFlag', 'true');
          }
      	else{
      		localStorage.setItem(ApplicationId+'totalProposalCostFlag', 'false');
      	}
      }
      else{
    	  localStorage.setItem(ApplicationId+'previousTotalProposalCost', initialTotalProposalCost1);
    	  if (currentValue !== initialTotalProposalCost1) {
              // Value has changed, set a flag in localStorage
              localStorage.setItem(ApplicationId+'totalProposalCostFlag', 'true');
            }
    	  else{
        		localStorage.setItem(ApplicationId+'totalProposalCostFlag', 'false');
        	}
      }
      
    });
  });
//End of setting flag

//Added by devesh on 02-02-24 to disable bootstrap validation for internal Projects
$(document).ready(function () {

	  var categoryValue = $('#categoryId').val();
	  if(categoryValue==7){
		  $('#totalProposalCost').prop('disabled', true);
		  $('#totalProposalCost').val(0);
	  }
	  else{
		  $('#totalProposalCost').prop('disabled', false);
	  }

	  $('#categoryId').on('change', function () {
	    var fieldValue = $(this).val();

	    if (fieldValue == 7){
	    	$('#totalProposalCost').prop('disabled', true);
	    	$('#totalProposalCost').val(0);
	    }
	    else{
	    	$('#totalProposalCost').prop('disabled', false);
	    }

	  });
	});
//End of function