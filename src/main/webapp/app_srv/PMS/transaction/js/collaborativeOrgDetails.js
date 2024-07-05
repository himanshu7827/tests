var nameRegex='';
var nameErrorMessage='';
var emailRegex='';
var emailErrorMessage='';
var contactRegex='';
var contactErrorMessage='';
var websiteRegex='';
var websiteErrorMessage='';
var addressRegex= '';
var addressErrorMessage='';

var validationName =1;
var validateContact =1;
var validateEmail = 1;
var validateWebsite =1;
var validateAddress=1;


messageResource.init({	  
filePath : '/PMS/resources/app_srv/PMS/global/js'
});

messageResource.load('regexValidationforjs', function(){
	  // get value corresponding  to a key from regexValidationforjs.properties  
	nameRegex= messageResource.get('nonRestrictname.regex', 'regexValidationforjs');
	nameErrorMessage = messageResource.get('nonRestrictname.regex.message', 'regexValidationforjs');
	emailRegex = messageResource.get('email.regex', 'regexValidationforjs');
	emailErrorMessage = messageResource.get('email.regex.message', 'regexValidationforjs');
	contactRegex = messageResource.get('contact.regex', 'regexValidationforjs');
	contactErrorMessage = messageResource.get('contact.regex.message', 'regexValidationforjs');
	websiteRegex = messageResource.get('website.regex', 'regexValidationforjs');
	websiteErrorMessage= messageResource.get('website.regex.message', 'regexValidationforjs');
	addressRegex= messageResource.get('address.regex', 'regexValidationforjs');
	addressErrorMessage= messageResource.get('address.regex.message', 'regexValidationforjs');
	});


var totalRowInTable =0;

$(document)
		.ready(
				function() {
					$('#addRow')
							.on(
									"click",
									function() {
										$(".select2Option").select2("destroy");
										var $template = $('#optionTemplate');
										var i = totalRowInTable;
										totalRowInTable+=1;
										var temp = 'collaborativeOrg[' + i
												+ ']';
										var trId= 'tr_'+i;
										var tableRow = "<tr id="+trId+"> ";
										tableRow += "<td><input type='hidden' value='0'/><input type='text'  class='input-field colOrgName' id='organisationName_"+i+"' /></td>";
										tableRow += "<td><input type='text' class='input-field colOrgContact'  id='contactNumber_"+i+ "' /> </td>";
										tableRow += "<td><input type='text' class='input-field colOrgEmail'  id='email_"+i+"'/> </td>";
										tableRow += "<td><input type='text' class='input-field colOrgWeb'  id='website_"+i+"' /> </td>";
										tableRow += "<td><textarea  class='input-field colOrgAddr'  id='organisationAddress_"+i+"' ></textarea> </td>";

										tableRow += "<td><a class='red' onclick='removeNewRow("+i+")'> <i class='fa fa-times '></i> </a> </td> </tr>";

										$('#collaborativeOrgDtlTbl').append(
												tableRow);

										$('.select2Option').select2({
											width : '100%'
										});

									});
});


$(document).ready(function() {
	populateData();
		
});


function removeNewRow(trId){	
		$("#tr_"+trId).remove();	
}

function removeOldRow(trId){	
	//$("#tr_"+trId).remove();	
	 swal({
		  title: "Attention",
		  text: "Are you sure to remove Organisation Details?",
		  type: 'success',						  
	      buttons: [					               
	                'No',
	                'Delete'
	              ],	     
	    }).then(function(isConfirm) {
	      if (isConfirm) {
			 
	    	  $.ajaxRequest.ajax({
	    			type : "POST",
	    			url : "/PMS/deleteCollaborativeOrgDetails",
	    			data : {
	    				"encId" : trId				
	    				},
	    			success : function(response) {
	    				console.log(response);
	    					if(response>0){
	    						sweetSuccess("Success","Record Deleted Sucessfully");
	    						$("#tr_"+trId).remove();
	    					}else{
	    						sweetSuccess("Error","Something went wrong");
	    					}	
	    			}, error: function(data) {
	    		 	  
	    		   }
	    	});
	      }
	    });
	
}

$(document).ready(function() {
	
	$('#save').click(function(){
		var numOfOrganisations = $('.colOrgName').length;
		
		if(numOfOrganisations == 0){
			sweetSuccess("Warning","Please add atleast one Organisation Details");
			return false;
		}
		
		var applicationId = $('#applicationId').val();
		if(!applicationId){
			sweetSuccess("Warning","Something went wrong. Contact Admin");
			return false;
		}
		
		var validationFlag = validateFormFields();
		if(validationFlag == true){
			prepareDataAndSaveCollaborationDetails();
		}
	});
	
});


function validateName(fieldId,fieldValue){		
	var regex = new RegExp(nameRegex);			
	 if (regex.test(fieldValue)) {  
	    return (true)  ;
	  }else{
		  validationName=0;
		  $('#'+fieldId).val('').focus();
		  sweetSuccess("Attention",nameErrorMessage);
		  return (false); 
	  }		
}

function validateEmailFun(fieldId,fieldValue){		
	var regex = new RegExp(emailRegex);			
	 if (regex.test(fieldValue)) {  
	    return (true)  ;
	  }else{
		  validateEmail=0;
		  $('#'+fieldId).val('').focus();
		  sweetSuccess("Attention",emailErrorMessage);
		  return (false)  ; 
	  }		
}

function validateContactFun(fieldId,fieldValue){		
	var regex = new RegExp(contactRegex);			
	 if (regex.test(fieldValue)) {  
	    return (true)  ;
	  }else{
		  validateContact=0;
		  $('#'+fieldId).val('').focus();
		  sweetSuccess("Attention",contactErrorMessage);
		  return (false)  ; 
	  }		
}

function validateAddressFun(fieldId,fieldValue){		
	 var regex = new RegExp(addressRegex);	
	 if (regex.test(fieldValue)) {  
		    return (true)  ;
		  }else{
			  validateAddress=0;
			  $('#'+fieldId).text('').focus();
			  sweetSuccess("Attention",addressErrorMessage);
			  return (false)  ; 
		  }	
}

function validateWebsiteFun(fieldId,fieldValue){		
	 var regex = new RegExp(websiteRegex);	
	 if (regex.test(fieldValue)) {  
		    return (true)  ;
		  }else{
			  validateWebsite=0;
			  $('#'+fieldId).val('').focus();
			  sweetSuccess("Attention",websiteErrorMessage);
			  return (false)  ; 
		  }	
}

function validateFormFields(){
	var addressFlag = false;
	//Organisation Name Validation Start
	$('.colOrgName').each(function(){
		var fieldId = $(this).attr('id');
		var fieldValue =  $(this).val().trim();
		
		if(!fieldValue){
			validationName=0;
			sweetSuccess("Attention","Organisation Name is required in each Row");
			$('#'+fieldId).focus();
			return false;
		}else{
			var dataLength = fieldValue.length;
			if(dataLength>200){
				validationName=0;
				sweetSuccess("Attention","Organisation Name can not be greater than 200 characters");
				$('#'+fieldId).val('').focus();
				return false;
			}else{
				validationName=1;
			}				
			return validateName(fieldId,fieldValue);
		}			
	});	
	//Organisation Name Validation End
	
	
	
	//Contact Number Validation Start
	$('.colOrgContact').each(function(){
		var fieldId = $(this).attr('id');
		var fieldValue =  $(this).val().trim();
		
		if(!fieldValue){
			validateContact=0;
			sweetSuccess("Attention","Contact Number is required in each Row");
			$('#'+fieldId).focus();
			return false;
		}else{
			var dataLength = fieldValue.length;
			if(dataLength>20){
				validateContact=0;
				sweetSuccess("Attention","Contact Number can not be greater than 20 characters");
				$('#'+fieldId).val('').focus();
				return false;
			}else{
				validateContact=1;
			}				
			return validateContactFun(fieldId,fieldValue);
		}			
	});
	//Contact Number Validation End
	
	//Email Validation Start
	$('.colOrgEmail').each(function(){
		var fieldId = $(this).attr('id');
		var fieldValue =  $(this).val().trim();
		
		if(!fieldValue){
			validateEmail=0;
			sweetSuccess("Attention","Email is required in each Row");
			$('#'+fieldId).focus();
			return false;
		}else{
			var dataLength = fieldValue.length;
			if(dataLength>100){
				validateEmail=0;
				sweetSuccess("Attention","Email can not be greater than 100 characters");
				$('#'+fieldId).val('').focus();
				return false;
			}else{
				validateEmail=1;
			}				
			return validateEmailFun(fieldId,fieldValue);
		}			
	});
	//Email Validation End
	
	//Website Validation Start
	$('.colOrgWeb').each(function(){
		var fieldId = $(this).attr('id');
		var fieldValue =  $(this).val().trim();
		
		if(fieldValue){
			var dataLength = fieldValue.length;
			if(dataLength>150){
				validateWebsite=0;
				sweetSuccess("Attention","Website can not be greater than 150 characters");
				$('#'+fieldId).val('').focus();
				return false;
			}else{
				validateWebsite=1;
			}				
			return validateWebsiteFun(fieldId,fieldValue);
		}			
	});
	//Website Validation End
	
	//Address Validation Start
	$('.colOrgAddr').each(function(){
		var fieldId = $(this).attr('id');
		var fieldValue =  $(this).val().trim();
		
		if(!fieldValue){
			validateAddress=0;
			sweetSuccess("Attention","Address is required in each Row");
			$('#'+fieldId).focus();
			return false;
		}else{
			var dataLength = fieldValue.length;
			if(dataLength>2000){
				validateAddress=0;
				sweetSuccess("Attention","Address can not be greater than 2000 characters");
				$('#'+fieldId).val('').focus();
				return false;
			}else{
				validateAddress=1;
			}				
			return validateAddressFun(fieldId,fieldValue);
		}			
	});
	//Address Validation End
	
	if(validationName ==1 && validateAddress==1 && validateWebsite == 1 && validateEmail==1 && validateContact==1){
		return true;
	}else{
		return false;
	}	
}

function prepareDataAndSaveCollaborationDetails(){
	var applicationId = $('#applicationId').val();
	var finalArray = [];
	$('.colOrgName').each(function(){
		var rowObject = new Object();
		
		var fieldId = $(this).attr('id');		
		var inputArray = $('#'+fieldId).closest('tr').find('td').find('input').map( function()
			{
				return $(this).val();
			}).get();
		
		var textArray = $('#'+fieldId).closest('tr').find('td').find('textarea').map( function()
				{
					return $(this).val();
				}).get();
		
		rowObject.numId = inputArray[0].trim();
		rowObject.organisationName = inputArray[1].trim();
		rowObject.contactNumber= inputArray[2].trim();
		rowObject.email = inputArray[3].trim();
		rowObject.website = inputArray[4].trim();
		rowObject.address = textArray[0].trim();
		
		console.log(rowObject);
		
		finalArray.push(rowObject);
					
	});
	
	console.log(finalArray);
	
	$.ajaxRequest.ajax({
        type: "POST",
        url: "/PMS/saveUpdateCollaborativeOrgDetails",
        data:{"applicationId":applicationId,        	
        	"inputData":JSON.stringify(finalArray)},
        async:false,       		
		success : function(data) {
			if(data.length>0){
				sweetSuccess('Success',"Records saved/updated successfully");
				previousOrg=data;
				populateData();
			}else{
				sweetSuccess('Error',"Something went wrong");
			}			
		},error : function(data) {
			
		}
			
	});
	
}

function populateData(){
	
	previousOrg = previousOrg.replace(/\\n/g, "\\n")  
    .replace(/\\'/g, "\\'")
    .replace(/\\"/g, '\\"')
    .replace(/\\&/g, "\\&")
    .replace(/\\r/g, "\\r")
    .replace(/\\t/g, "\\t")
    .replace(/\\b/g, "\\b")
    .replace(/\\f/g, "\\f");
//remove non-printable and other non-valid JSON chars
	previousOrg = previousOrg.replace(/[\u0000-\u0019]+/g,"");
	
	
	$('#collaborativeOrgDtlTbl').find("tr:gt(0)").remove();
	var inputData = JSON.parse(previousOrg);
	
	jQuery(inputData).each(function(i, item){
		
		var rowId = "tr_"+item.encNumId;
		
			var tableRow = "<tr id='"+rowId+"'> ";
			
			tableRow+= "<td> <input type='hidden' value="+item.numId+" /> <input type='text'  class='input-field colOrgName' id='organisationName_"+i+"' value = '"+item.organisationName+"' />";
			tableRow+= "<td> <input type='text' class='input-field colOrgContact' id='contactNumber_"+i+"'  value = "+item.contactNumber+" />";
			tableRow+= "<td> <input type='text' class='input-field colOrgEmail' id='email_"+i+"' value = "+item.email+" />";
			tableRow+= "<td> <input type='text' class='input-field colOrgWeb' id='website_"+i+"' value = '"+item.website+"' />";
		
			tableRow+="<td> <textarea  class='input-field colOrgAddr' id='organisationAddress_"+i+"'>"+item.organisationAddress+"</textarea> </td>";
			tableRow+=" <td> <input type='hidden' /> <a class='red' onclick=removeOldRow('"+item.encNumId+"') > <i class='fa fa-times'></i> </a> </td>";					
						
			tableRow +="</tr>";
			
			$('#collaborativeOrgDtlTbl').append(tableRow);
			totalRowInTable+=1;
	});
}

function goprev()
{
	 var path=$(location).attr('pathname');  

	 document.getElementById("form1").action="/PMS/prevRedirectPage?path='"+path+"'&moduleTypeId=1&uniqueId="+$("#applicationId").val();
		document.getElementById("form1").method="POST";
		document.getElementById("form1").submit();

}



function gonext()
{
	 var path=$(location).attr('pathname');  
	 document.getElementById("form1").action="/PMS/nextRedirectPage?path='"+path+"'&moduleTypeId=1&uniqueId="+$("#applicationId").val();
		document.getElementById("form1").method="POST";
		document.getElementById("form1").submit();
    
}
