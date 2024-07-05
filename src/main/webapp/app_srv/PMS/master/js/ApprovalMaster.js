var numericRegex='';
var numericErrorMessage='';
var nameRegex='';
var nameErrorMessage='';
var mobileRegex='';
var mobileErrorMessage='';
var emailRegex='';
var emailErrorMessage='';
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
	mobileRegex= messageResource.get('mobileno.regex', 'regexValidationforjs');
	mobileErrorMessage = messageResource.get('mobileno.regex.message', 'regexValidationforjs');
	});


function otherChangeActionRole(){
	$('#actionId').val(0);
	$("#actionId").trigger('change');

	$('#roleId').val(0).trigger('change');
}


function otherChangeAction(){
	$('#actionId').val(0).trigger('change');
	
}


function getApprovalDetails(){
	
var workshopId=$("#workshop").val();
var roleId=$("#roleId").val();
var actionId=$("#actionId").val();


if(workshopId==0){
	sweetSuccess("Attention","Kindly select Workshop");
	$('#actionId').val(0).trigger('change');	
	return false;
}
else if(roleId==0){
	sweetSuccess("Attention","Kindly select Role");
	$('#actionId').val(0).trigger('change');	
	return false;
}
$.ajax({
	type : "POST",
	url : "/PMS/mst/getApprovalDetails",
	 data: {
		 	"workshopId":workshopId,
		 	"roleId":roleId,
		 	"actionId":actionId
		 	},
	success : function(response) {
		
		if(response){
	
		if(response.numApprovalId!=0){
			
			$("#numApprovalId").val(response.numApprovalId);
			$('#save').text('Update');
		}
		if(response.numRoleActionId!=0){
			$("#numRoleActionId").val(response.numRoleActionId);
		}
		 $("#nextRoleId").find("option[value="+response.nextRoleId+"]").prop("selected","selected");
		 $("#nextRoleId").trigger("change");
		 $("#transactionImpact").find("option[value="+response.transactionImpact+"]").prop("selected","selected");
		 $("#transactionImpact").trigger("change");
		 $("#firstPageAction").val(response.firstPageAction).change();
		 $("#secondPageAction").val(response.secondPageAction).change();
		 $("#statusTobeUpdated").val(response.statusTobeUpdated);
		 
		 if(response.copyTobeCreated==1){
				$("#isfiledy").prop("checked", true);
				
			}
			if(response.copyTobeCreated==0){
				$("#isfiledn").prop("checked", true);
			}
		}
	},
	error : function(e) {
		alert('Error: ' + e);			
	}
});	
}



$(document).ready(function() {
    $('.select2Option').select2({
    	 width: '100%'
    });
    
	$('#result').hide();
    $('#empExistDiv').hide();
    
   
});


function resetForm(){
	$('#save').text('Update');
	var radioButton = document.getElementById("isfiledy"); 
    radioButton.checked = false; 
  
	$('#workshop').val('');
	$('#workshop').val(0).trigger('change');	
	
	$('#actionId').val('');
	$('#actionId').val(0).trigger('change');
	
	$('#roleId').val('');
	$('#roleId').val(0).trigger('change');
	
	$('#nextRoleId').val('');
  	$('#nextRoleId').val(0).trigger('change');
  	
  	

	
	$('#statusTobeUpdated').val('');
	 var radioButton = document.getElementById("isfiledn"); 
     radioButton.checked = true; 
   
     
     $('#statusTobeUpdated').val('');	
     
     
 	
 	$('#transactionImpact').val('');
 	$('#transactionImpact').val(-1).trigger('change');	
 	
 	
 	
 	
 	/* $(".select2-input").val('');*/
	// $("#secondPageAction").val('');
    
	/*$('#firstPageAction').val('');
	$('#firstPageAction').val(0).trigger('change');	
	
	$('#secondPageAction').val('');
	$('#secondPageAction').val(0).trigger('change');	*/
	
	
}

function detailsSave(){
	
	if($("#workshop").val()==0){
		sweetSuccess("Attention","Kindly select Workshop");
		return false;
	}
	else if($("#roleId").val()==0){
		sweetSuccess("Attention","Kindly select Role");
		return false;
	}
	else if($("#actionId").val()==0){
		sweetSuccess("Attention","Kindly select Action");
		return false;
	}

	else if($("#nextRoleId").val()==0){
		sweetSuccess("Attention","Kindly select Next Role Action");
		return false;
	}
	else if($('#statusTobeUpdated').val()==''){
		sweetSuccess("Attention","Status to be updated in main table is mandatory");
		return false;
	}
	else if($('#firstPageAction').val()==0){
		sweetSuccess("Attention","Action First Page is mandatory");
		return false;
	}
	else if($('#secondPageAction').val()==0){
		sweetSuccess("Attention","Action Second Page is mandatory");
		return false;
	}
	/*document.getElementById("save").disabled = true; 
	document.form1[0].submit();
	return true;*/
	document.getElementById("form1").action="/PMS/mst/saveApprovalMaster";
	document.getElementById("form1").method="POST";
	document.getElementById("form1").submit();
}



