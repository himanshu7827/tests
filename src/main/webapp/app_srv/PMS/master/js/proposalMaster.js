var nameRegex='';
var nameErrorMessage='';

var amtRegex='';
var amtErrorMessage='';

var ftsRegex='';
var ftsErrorMessage='';

var numericRegex = '';
var numericRegexMessage = '';

//regex added by devesh on 28-05-24 to apply validation on outlay
var outlayRegex=/^\d+$/;
var outlayRegexMessage='Only numeric values without decimals are allowed';
//end of outlay regex

messageResource.init({	  
filePath : '/PMS/resources/app_srv/PMS/global/js'
});

messageResource.load('regexValidationforjs', function(){
	  // get value corresponding  to a key from regexValidationforjs.properties  
	nameRegex= messageResource.get('name.regex', 'regexValidationforjs');
	nameErrorMessage = messageResource.get('name.regex.message', 'regexValidationforjs');
	
	
	amtRegex= messageResource.get('amount.regex', 'regexValidationforjs');
	amtErrorMessage = messageResource.get('amount.regex.message', 'regexValidationforjs');
	
	ftsRegex= messageResource.get('fileno.regex', 'regexValidationforjs');
	ftsErrorMessage = messageResource.get('fts.fileno.regex.message', 'regexValidationforjs');
	
	numericRegex = messageResource.get('numeric.regex',
	'regexValidationforjs');
	numericRegexMessage = messageResource.get('numeric.regex.message',
	'regexValidationforjs');
	});





$(document).ready(function() {
    $('.select2Option').select2({
    	 width: '100%'
    });
    $('#example').DataTable();
    
  /* $('#dateOfSubmission').datepicker({
	
			dateFormat: 'dd/mm/yy', 
	    changeMonth: true,
	    changeYear:true,           
	    maxDate: '+5y',
	    onSelect: function(date){
	    	$('#dateOfSubmission').trigger('input');
	    },
	    onClose: function () {
	    	$('#dateOfSubmission').trigger('blur');
	    }
   });*/
    /*Bhavesh(19-07-23) Restrict selectable date up to current date*/
    $('#dateOfSubmission').datepicker({
        dateFormat: 'dd/mm/yy', 
        changeMonth: true,
        changeYear: true,
        maxDate: 0, // Restrict selectable date up to today (current date)
        onSelect: function(date) {
            $('#dateOfSubmission').trigger('input');
        },
        onClose: function() {
            $('#dateOfSubmission').trigger('blur');
        }
    });

   $('#userinfo-message').delay(5000).fadeOut();
	$('#delete').click(function(){
		ProposalMasterDelete();
	});

	 
	 
	 
		$('#reset').click(function(){
			resetForm();
			  $('#form_proposal').data('bootstrapValidator').resetForm(true);
		});
	 
	$('#form_proposal').bootstrapValidator({
//	      live: 'disabled',
		excluded: ':disabled',
	      message: 'This value is not valid',
	      feedbackIcons: {
	          valid: 'glyphicon glyphicon-ok',
	          invalid: 'glyphicon glyphicon-remove',
	          validating: 'glyphicon glyphicon-refresh'
	      },
	      fields: {
	    	           
	          
	         	    duration: {
	              group: '.col-md-12',
	              validators: {
	            	  notEmpty: {
	                      message: 'Duration is required and cannot be empty'
	                  },
	                 
	                  stringLength: {
	                        max: 2,
	                        message: 'The Duration must be less than 2 characters'
	                    },
	                    regexp: {
	                        regexp: numericRegex,
	                       message: numericRegexMessage
	                    }
	              }
	          },
	          /*Bhavesh(19-07-23) added the validation for date of submission*/
	         dateOfSubmission: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Date of Submission is required and cannot be empty'
	                  },
	                    
	              }
	          },
	         


	          proposalCost: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Proposal Cost is required and cannot be empty'
	                  },
	                    regexp: {
	                        /*regexp: amtRegex,
	                       message: amtErrorMessage*/
	                       //Modified by devesh on 28-05-24 to not allow decimal values
							regexp : outlayRegex,
							message : outlayRegexMessage
							//End of code
	                    },
	                    /*-------  CallBack function for not allowing the String value '0' in the field [25-01-2024] ----*/
						callback: {
	                      message: 'C-DAC Outlay Should be greater than 0',
	                      callback: function(value) {
	                    	  var categoryValue = $('#proposalCategoryId').val();
	                    	  if(categoryValue==7){
	                    		  return true;
	                    	  }
	                          return value !== '0';
	                      }
		                }
	              }
	          },
	          objectives: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Objective is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 2000,
	                        message: 'Objective name must be less than 2000 characters'
	                    },
	                    /*regexp: {
	                        regexp: nameRegex,
	                       message: nameErrorMessage
	                    }*/
	              }
	          },
	          summary: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Description is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 6000,
	                        message: 'Description must be less than 6000 characters'
	                    },
	                   
	              }
	          },
/*	          proposalRefNo: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Proposal Reference No. is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 30,
	                        message: 'Proposal Reference No. name must be less than 30 characters'
	                    },
	                   
	              }
	          },
	          */
	          background: {
	              group: '.col-md-12',
	              validators: {	                 
	                  stringLength: {
	                        max: 2000,
	                        message: 'Background name must be less than 2000 characters'
	                    }
	                   
	              }
	          },
	          submittedBy: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Submitted By is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 2000,
	                        message: 'Submitted By name must be less than 500 characters'
	                    },
	                    regexp: {
	                        regexp: nameRegex,
	                       message: nameErrorMessage
	                    }
	              }
	          }, 
	        
	         
	      }
	  });
	
	
   $('#save').click(function(){	
	   var  istoggle=true;
	   	 /*if($("#toggle-on").prop("checked")==true) {	   		 
	   		 istoggle=true;
	   	}
	   	 else if($("#toggle-off").prop("checked")==true){
	   	  istoggle=false;
	   	 }*/
	   	 $('#valid').val(istoggle);
	   var bootstrapValidator = $("#form_proposal").data('bootstrapValidator');
		bootstrapValidator.validate();
	    if(bootstrapValidator.isValid()){
	    	console.log(' Validated');
	    	document.getElementById("save").disabled = true; 
			 console.log($("#form_proposal").serialize());
			 console.log($('#form_proposal :input').filter(function(i) { return ($(this).val().length != 0); }).serialize() );

			 $
				.ajax({
					type : "POST",
					url : "/PMS/mst/saveUpdateProposalMaster",
					data: $('#form_proposal :input').filter(function(i) { return ($(this).val().length != 0); }).serialize(),							
					success : function(
							response) {
						console.log(response.numId);
						console.log(response.numHasError);
						if(response.numId > 0 && response.numHasError==0)
						//gonext();
					    	document.getElementById("save").disabled = false; 			               

						else
					    	document.getElementById("save").disabled = false; 			               

					},
					error : function(e) {
						alert('Error: ' + e);
					}
				});
			 
				
			
		}else{
			console.log('Not Validated');
			 
		}
/*	    $('#form_proposal').data('bootstrapValidator').resetForm(true);
*/	   
		
   });
   
});

//Delete
function ProposalMasterDelete(){
	//alert("dfsff");
    var chkArray = [];
     
    $(".CheckBox:checked").each(function() {
        chkArray.push($(this).val());
    });
    
    var selected;
    selected = chkArray.join(','); 
    if(selected.length >= 1){
    	
    	
    	 swal({
		      title: "Are you sure you want to delete the record?",
		      icon: "warning",
		      buttons: [
		                'No',
		                'Yes'
		              ],
		      dangerMode: true,
		    }).then(function(isConfirm) {
		      if (isConfirm) {
		    	 // alert("inside confirm");
			    	  $("#numIds").val(selected);
			  		  submit_form_delete();
			      }
	  	 /* if(confirm("Are you sure you want to delete the record"))
		  {
	  		  
	       $("#userinfo-message").show().delay(3000).fadeOut();
	    }*/
			      else{
			    	  
			      return false;
	    }
    });
    }
    else{
        swal("Please select at least one Proposal to delete");  
}
 
}

function submit_form_delete()
{
	//alert("inside submit_form_delete ");
document.form_proposal.action = "/PMS/mst/deleteProposal";
document.getElementById("form_proposal").submit();
}


/*$('#clientId').change(function() {alert("gfgtff"); 


});*/

function abc() {
	var clientNum = $('#clientId').val();
alert("clientNum"+clientNum);
	$('#contactPerson').find('option').remove().end();
	$.ajax({

		type : "POST",
		url : "/PMS/mst/getContactByClient",
		data : "clientId=" + clientNum,
		success : function(response) {
alert("response.length"+response);
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
function fetchgroup() {
	var orgNum = $('#organisationId').val();
//alert("orgNum"+orgNum);
	$('#groupId').find('option').remove().end();
	$.ajax({

		type : "POST",
		url : "/PMS/mst/getGroupByOrgId",
		data : "organisationId=" + orgNum,
		success : function(response) {
//alert("response.length"+response);
			$('#groupId').append(
					$('<option/>').attr("value", -1).text("Select"));
			for ( var i = 0; i < response.length; ++i) {
				//alert("+response[i].numCaseId===" + i + "i"+ response[i].groupId);
				$('#groupId').append(
						$('<option/>').attr("value", response[i].groupId)
								.text(response[i].groupName));
				// $('#frm').bootstrapValidator('revalidateField','strOffDistrcit');
			}
		},
		error : function(e) {
			alert('Error: ' + e);
		}
	});
}


function goprev()
{
	 var path=$(location).attr('pathname');  

	 document.getElementById("form_proposal").action="/PMS/prevRedirectPage?path='"+path+"'&moduleTypeId=1&uniqueId="+$("#applicationId").val();
		document.getElementById("form_proposal").method="POST";
		document.getElementById("form_proposal").submit();

}



function gonext()
{
	 var  istoggle=true;
   	 /*if($("#toggle-on").prop("checked")==true) {	   		 
   		 istoggle=true;
   	}
   	 else if($("#toggle-off").prop("checked")==true){
   	  istoggle=false;
   	 }*/
   	 $('#valid').val(istoggle);
   var bootstrapValidator = $("#form_proposal").data('bootstrapValidator');
	bootstrapValidator.validate();
    if(bootstrapValidator.isValid()){
	 var path=$(location).attr('pathname');  
	 document.getElementById("form_proposal").action="/PMS/nextRedirectPage?path='"+path+"'&moduleTypeId=1&uniqueId="+$("#applicationId").val();
		document.getElementById("form_proposal").method="POST";
		document.getElementById("form_proposal").submit();
    }
}

function resetForm(){
	$('#numId').val('0');
	$('#proposalTitle').val('');
	$('#dateOfSubmission').val('');
	$('#duration').val('');
	$('#proposalCost').val('');
	$('#objectives').val('');
	$('#summary').val('');
	$('#background').val('');
	$('#submittedBy').val('');
	$('#proposalStatus').val('');
	$('#save').text('Save');
}


function validateNumeric(amount){		
	numericRegex = new RegExp(numericRegex);			
	 if (numericRegex.test(amount)) {  
	    return (true)  ;
	  }else{
		  sweetSuccess("Attention",numericRegexMessage);
		  return (false)  ; 
	  }		
}

function validateAmount(amount){		
	//amountRegex = new RegExp(numericRegex);
	let testOutlayRegex = /^\d+$/; //Modified by devesh on 28-05-24 to not allow decimal values in CDAC outlay			
	 if (testOutlayRegex.test(amount)) {  
	    return (true)  ;
	  }else{
		  //sweetSuccess("Attention",amtErrorMessage);
		  sweetSuccess("Attention","Decimal values are not allowed. Please enter the value in INR.");
		  return (false)  ; 
	  }		
}

function validateName(value){		
	nameRegex = new RegExp(nameRegex);			
	 if (nameRegex.test(value)) {  
	    return (true)  ;
	  }else{
		  sweetSuccess("Attention",nameErrorMessage);
		  return (false)  ; 
	  }		
}

var previousSavedProposalCost = $("#proposalCost").val(); // Added by devesh on 29-05-24 to set initial proposal cost

function saveField(fieldId){
	var totalCost=$("#totalCost").val();
	var proposalCost=$("#proposalCost").val();
	var fieldValue = $('#'+fieldId).val();	
	var proposalId=$('#numId').val();
	var validationFlag= true;
	
	if(parseFloat(totalCost)<parseFloat(proposalCost)){
		sweetSuccess("Attention","CDAC Noida Outlay Share can not be greater than Total Proposal Cost");
		//Added by devesh on 29-05-24 to set previous cost
		$('#proposalCost').val(previousSavedProposalCost);
		$('#form_proposal').bootstrapValidator('revalidateField', $('#proposalCost').prop('name'));
		//End of code
		return false;
	}
	if ($("#"+fieldId).hasClass("validateDuration")) {
		 
				if(!validateNumeric(fieldValue)){
					$('#'+fieldId).val('0');
					sweetSuccess("Attention",numericRegexMessage);
					validationFlag= false;
					return false;
				}
				if(fieldValue.length>2){
					sweetSuccess('Attention','Duration must be less than 2 characters');
					validationFlag= false;
					return false;
				} 
	 }
	else if ($("#"+fieldId).hasClass("validateAmt")) {
		var categoryValue = $('#proposalCategoryId').val();
				if(categoryValue!=7 && !validateAmount(fieldValue)){
					/*$('#'+fieldId).val('');*/
					//sweetSuccess("Attention",amtErrorMessage);
					//Modified by devesh on 28-05-24 to not allow decimal values in CDAC outlay
					$('#'+fieldId).val(previousSavedProposalCost);
					$('#form_proposal').bootstrapValidator('revalidateField', $('#proposalCost').prop('name'));
					sweetSuccess("Attention","Decimal values are not allowed. Please enter the value in INR."); 
					//End of code
					validationFlag= false;
					return false;
				}
	 
	 }
	else if ($("#"+fieldId).hasClass("validateSubmittedBy")) {
		 
				if(!validateName(fieldValue)){
					$('#'+fieldId).val('');
					sweetSuccess("Attention",nameErrorMessage);
					validationFlag= false;
					return false;
				}
				if(fieldValue.length>2000){
					sweetSuccess('Attention','Submitted By must be less than 2000 characters');
					validationFlag= false;
					return false;
				}

	 }
	
	else if ($("#"+fieldId).hasClass("validateTextArea")) {
		
			if(fieldValue.length>2000){
		
			sweetSuccess('Attention','This Field must be less than 2000 characters');
			validationFlag= false;
			return false;
		}
			
	else if ($("#"+fieldId).hasClass("proposalRefNo")) {
				
				if(fieldValue.length>30){
			
				sweetSuccess('Attention','This Field must be less than 30 characters');
				validationFlag= false;
				return false;
			}
		
	}
	}
	
	previousSavedProposalCost = $("#proposalCost").val() // Added by devesh on 29-05-24 to update initial proposal cost
	
	$.ajaxRequest.ajax({
		type : "POST",
		url : "/PMS/mst/saveProposalMasterDetails",
		data :$('#form_proposal').serialize(),
		async:false,
		success : function(response) {
			if(response > 0){
				$('#numId').val(response);
			}
		}, error: function(data) {
	 	  
	   }
	});

	
}

//Added by devesh on 04-10-23 to set a flag if total proposal cost changes
var initialProposalCost = $("#proposalCost").val();
var initialDuration = $("#duration").val();
var initialobjectives = $("#objectives").val();
document.addEventListener('DOMContentLoaded', function() {
    const ProposalCostInput = document.getElementById('proposalCost');
    const ApplicationId = $('#applicationId').val();
    const durationInput = document.getElementById('duration');
    const objectivesInput = document.getElementById('objectives');

    ProposalCostInput.addEventListener('blur', function() {
      const currentValue = ProposalCostInput.value;
      const previousValue = localStorage.getItem(ApplicationId+'previousProposalCost');

      if(previousValue!=null){
    	  if (currentValue !== previousValue) {
    	        // Value has changed, set a flag in localStorage
    	        localStorage.setItem(ApplicationId+'ProposalCostFlag', 'true');
    	      }
    	  else{
    		  localStorage.setItem(ApplicationId+'ProposalCostFlag', 'false');
    	  }
      }
      else{
    	  localStorage.setItem(ApplicationId+'previousProposalCost', initialProposalCost);
    	  if (currentValue !== initialProposalCost) {
    	        // Value has changed, set a flag in localStorage
    	        localStorage.setItem(ApplicationId+'ProposalCostFlag', 'true');
    	      }
    	  else{
    		  localStorage.setItem(ApplicationId+'ProposalCostFlag', 'false');
    	  }
      }
    });
    durationInput.addEventListener('blur', function() {
        const currentDurationValue = durationInput.value;
        const previousDurationValue = localStorage.getItem(ApplicationId+'previousDuration');

        if(previousDurationValue!=null){
      	  if (currentDurationValue !== previousDurationValue) {
      	        // Value has changed, set a flag in localStorage
      	        localStorage.setItem(ApplicationId+'DurationFlag', 'true');
      	      }
      	  else{
      		  localStorage.setItem(ApplicationId+'DurationFlag', 'false');
      	  }
        }
        else{
      	  localStorage.setItem(ApplicationId+'previousDuration', initialDuration);
      	  if (currentDurationValue !== initialDuration) {
      	        // Value has changed, set a flag in localStorage
      	        localStorage.setItem(ApplicationId+'DurationFlag', 'true');
      	      }
      	  else{
      		  localStorage.setItem(ApplicationId+'DurationFlag', 'false');
      	  }
        }
      });
    objectivesInput.addEventListener('blur', function() {
        const currentObjectivesValue = objectivesInput.value;
        const previousObjectivesValue = localStorage.getItem(ApplicationId+'previousObjectives');

        if(previousObjectivesValue!=null){
      	  if (currentObjectivesValue !== previousObjectivesValue) {
      	        // Value has changed, set a flag in localStorage
      	        localStorage.setItem(ApplicationId+'ObjectivesFlag', 'true');
      	      }
      	  else{
      		  localStorage.setItem(ApplicationId+'ObjectivesFlag', 'false');
      	  }
        }
        else{
      	  localStorage.setItem(ApplicationId+'previousObjectives', initialobjectives);
      	  if (currentObjectivesValue !== initialobjectives) {
      	        // Value has changed, set a flag in localStorage
      	        localStorage.setItem(ApplicationId+'ObjectivesFlag', 'true');
      	      }
      	  else{
      		  localStorage.setItem(ApplicationId+'ObjectivesFlag', 'false');
      	  }
        }
      });
  });
//End of setting flag

//Added by devesh on 02-02-24 to disable bootstrap validation for internal Projects
$(document).ready(function () {

	  var categoryValue = $('#proposalCategoryId').val();
	  var totalCost=$("#totalCost").val();
	  var proposalCost=$("#proposalCost").val();
	  if(categoryValue==7){
		  $('#proposalCost').val(0);
		  saveField('proposalCost');
		  $('#proposalCost').prop('disabled', true);
	  }
	  else{
		  if(proposalCost==0){
			  $('#proposalCost').val(totalCost);
			  saveField('proposalCost');
		  }
		  $('#proposalCost').prop('disabled', false);
	  }
	});
//End of function