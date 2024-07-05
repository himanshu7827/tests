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


$(document).ready(function() {
	$('#approvedOn').datepicker({
		   dateFormat: 'dd/mm/yy', 
		   changeMonth: true,
		   changeYear:true,
		   onSelect: function(date){
		    	$('#approvedOn').trigger('input');
		    	}
		  
	});
});

$('#form1').bootstrapValidator({
	excluded: ':disabled',
      message: 'This value is not valid',
      feedbackIcons: {
          valid: 'glyphicon glyphicon-ok',
          invalid: 'glyphicon glyphicon-remove',
          validating: 'glyphicon glyphicon-refresh'
      },
      fields: {
    	  groupId:{
		         validators: {
	                  callback: {
	                      message: 'Please choose Group',
	                      callback: function(value, validator) {
	                          // Get the selected options
	                          var options = validator.getFieldElements('groupId').val();
	                          return (options != 0);
	                      }
	                  }
	              }
	          },
	          projectId:{
			         validators: {
		                  callback: {
		                      message: 'Please choose Project',
		                      callback: function(value, validator) {
		                          // Get the selected options
		                          var options = validator.getFieldElements('projectId').val();
		                          return (options != 0);
		                      }
		                  }
		              }
		          },
		          empId:{
				         validators: {
			                  callback: {
			                      message: 'Please choose Employee Name',
			                      callback: function(value, validator) {
			                          // Get the selected options
			                          var options = validator.getFieldElements('empId').val();
			                          return (options != 0);
			                      }
			                  }
			              }
			          },
			          jobID:{
					         validators: {
				                  callback: {
				                      message: 'Please choose Job Title',
				                      callback: function(value, validator) {
				                          // Get the selected options
				                          var options = validator.getFieldElements('jobID').val();
				                          return (options != 0);
				                      }
				                  }
				              }
				          },
          approvedOn:{
        	  validators: {
                    notEmpty: {
                        message: 'Approved On date is required and cannot be empty'
                    },
        	  date:{
        	  format: 'DD/MM/YYYY'
        	  }
          }
          },
       
          
          
      }
  });





	
	function getEmpDetails(numGroupId){
var encGroupId=numGroupId;
$.ajax({
	type : "POST",
	url : "/PMS/mst/ViewEmployeeONGroupID",
	data : "groupId=" + encGroupId,
	success : function(response) {
		console.log(response);
		var seloption = "";
			for (var i = 0; i < response.length; i++) {
				seloption += "<option  value="+response[i].numId+">"
					+ response[i].employeeName +" </option>";
		}	        
			$('#empId').append(seloption);
			//$("#empId").trigger("change"); 	
	},
	error : function(e) {
		alert('Error: ' + e);			
	}
});	
}

function getProjectDetails(){
	
	var numGroupId=	$("#groupId").val();
	
	$.ajax({
		type : "POST",
		url : "/PMS/mst/getProjectNameByGroupId",
		data : "numGroupId=" + numGroupId,
		success : function(response) {
			console.log(response);
			var seloption = "";
				for (var i = 0; i < response.length; i++) {
					seloption += "<option  value="+response[i].numId+">"
						+ response[i].strProjectName +" </option>";
			}	        
				$('#projectId').append(seloption);
				//$("#projectId").trigger("change"); 
				
		},
		error : function(e) {
			alert('Error: ' + e);			
		}
	});
	getEmpDetails(numGroupId);
	}


function getJobDetails(){
	var projectId=	$("#projectId").val();
	
	$.ajax({
		type : "POST",
		url : "/PMS/getApprovedJobDetailsCreated",
		data : "projectId=" + projectId,
		success : function(response) {
			console.log(response);
			var seloption = "";
				for (var i = 0; i < response.length; i++) {
					seloption += "<option  value="+response[i].numId+">"
						+ response[i].jobCode +" </option>";
			}	        
				$('#jobID').append(seloption);
			//	$("#jobID").trigger("change"); 	
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
    
	
    $('#reset').click(function(){
		resetForm();
		 $('#form1').data('bootstrapValidator').resetForm(true);
	});
    
    function resetForm(){
    	$('#groupId').val(0).trigger('change');
    	$('#projectId').val(0).trigger('change');
    	$('#empId').val(0).trigger('change');
    	$('#jobID').val(0).trigger('change');
    	$('#approvedOn').val('');
    	
    	$('#save').text('Save');
    }
    
    $('#form1').bootstrapValidator({
		excluded: ':disabled',
	      message: 'This value is not valid',
	      feedbackIcons: {
	          valid: 'glyphicon glyphicon-ok',
	          invalid: 'glyphicon glyphicon-remove',
	          validating: 'glyphicon glyphicon-refresh'
	      },
	      fields: {
	
	    
	      }
	  });

   
   
  
   
   $('#save').click(function(){	
	  
	
	var bootstrapValidator = $("#form1").data('bootstrapValidator');
	bootstrapValidator.validate();
    if(bootstrapValidator.isValid()){	
		document.getElementById("save").disabled = true; 
		$('#form1')[0].submit();
		return true;
    }
			 
	   
		
   });
   
});

function resetForm(){
	
	$('#groupId').val(0).trigger('change');
	$('#projectId').val(0).trigger('change');
	$('#designationId').val(0).trigger('change');
	$("#details").empty();
	//$('#approvedDetails').empty();
}



var inputArray = [];
$('#save').click(function(){
$('#form1').submit();
	
});