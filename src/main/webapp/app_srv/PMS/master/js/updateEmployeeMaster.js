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
    $('.select2Option').select2({
    	 width: '100%'
    });
    
    $('#empExistDiv').hide();
    $('#reset').click(function(){
		resetForm();
		 $('#form1').data('bootstrapValidator').resetForm(true);
	});
    

    
    $('#form1').bootstrapValidator({
//	      live: 'disabled',
		excluded: ':disabled',
	      message: 'This value is not valid',
	      feedbackIcons: {
	          valid: 'glyphicon glyphicon-ok',
	          invalid: 'glyphicon glyphicon-remove',
	          validating: 'glyphicon glyphicon-refresh'
	      },
	      fields: {
	    	  
	    	 

	          
	          alternateEmail: {
	              group: '.col-md-12',
	              validators: { 
	                  stringLength: {
	                        max: 100,
	                        message: 'Alternate Email must be less than 100 characters'
	                    },
	                  regexp: {
	                        regexp: emailRegex,
	                       message: emailErrorMessage
	                    }
	              }
	          },
	          mobileNumber: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Mobile Number is required and cannot be empty'
	                  },  
	                  regexp: {
	                        regexp: mobileRegex,
	                       message: mobileErrorMessage
	                    }
	              }
	          },
	          contactNumber:{
	        	  group: '.col-md-12',
	        	  validators: {
	        	  stringLength: {
                      max: 15,
                      message: 'Contact Number must be less than 15 digits'
                  }
	        	  }
	          },
	          
	         
	          
	         
	      }
	  });

    
   $('#dateOfBirth').datepicker().datepicker('disable');
   

   $('#dateOfJoining').datepicker().datepicker('disable');
	       
  
   
   
   $('#dateOfResignation').datepicker().datepicker('disable');
	
   
   $('#dateOfRelease').datepicker().datepicker('disable');
	
   
   
   $('#save').click(function(){	

	   	 $('#valid').val(true);
	   	 

	   
	   var bootstrapValidator = $("#form1").data('bootstrapValidator');
		bootstrapValidator.validate();
	    if(bootstrapValidator.isValid()){
				document.getElementById("save").disabled = true; 
				$('#form1')[0].submit();
				return true;
			
		}else{
			console.log('Not Validated');
			 //return false;
		}
	   
		/*if($('#form1').form('validate')){				
				document.getElementById("form1").submit();
				resetForm();
		   }*/
   });
   
});



$(document).ready(function() {


function validateNumber($num){			
	 var regex = new RegExp(numericRegex);	
	    return regex.test($num); 
}

  


$(document).ready(function() {
	
	if('${profileFlag}' =='1'){
   	 $('#numId').prop("readonly","readonly");	
	}
	
});

 
});
