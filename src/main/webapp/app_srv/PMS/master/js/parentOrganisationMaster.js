var nameRegex='';
var nameErrorMessage='';
var contactRegex='';
var contactErrorMessage='';
messageResource.init({	  
filePath : '/PMS/resources/app_srv/PMS/global/js'
});

messageResource.load('regexValidationforjs', function(){
	  // get value corresponding  to a key from regexValidationforjs.properties  
	nameRegex= messageResource.get('name.regex', 'regexValidationforjs');
	nameErrorMessage = messageResource.get('name.regex.message', 'regexValidationforjs');
	contactRegex= messageResource.get('contact.regex', 'regexValidationforjs');
	contactErrorMessage = messageResource.get('contact.regex.message', 'regexValidationforjs');
	});

	$(document).ready(function() {
			$('#example').DataTable();
			$('#userinfo-message').delay(5000).fadeOut();	
			$('.select2Option').select2({width:"element"});
	});


	$(document).on('click','#edit',function(e){
		var resultArray = $(this).closest('tr').find('td').map( function()
				{
				return $(this).text();
				}).get();
		console.log(resultArray);
		$('#numId').val(resultArray[0].trim());
		$('#organisationName').val(resultArray[1].trim()).trigger('input');
		$('#organisationShortName').val(resultArray[2].trim()).trigger('input');
		$('#organisationAddress').val(resultArray[3].trim()).trigger('input');
		$('#contactNumber').val(resultArray[4].trim()).trigger('input');	
		alert(resultArray[5].trim());
		if(resultArray[5].trim()=='Active'){
			$('#toggle-off').removeAttr('checked');
			$('#toggle-on').attr('checked',true);

		}else{
			$('#toggle-on').removeAttr('checked');
			$('#toggle-off').attr('checked',true);
		}
		$('#save').text('Update');
	});
	
	

$(document).ready(function(){	

	$('#reset').click(function(){
		resetForm();
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
	    	  organisationName: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Organization name is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 100,
	                        message: 'The Organization name must be less than 100 characters'
	                    },
	                    regexp: {
	                        regexp: nameRegex,
	                       message: nameErrorMessage
	                    }
	              }
	          },
	          
	          organisationAddress: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Organization Address is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 250,
	                        message: 'Organization Address must be less than 250 characters'
	                    },
	              }
	          },
	          contactNumber: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Contact Number is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 20,
	                        message: 'Contact Number must be less than 20 characters'
	                    },
	                    regexp: {
	                        regexp: contactRegex,
	                       message: contactErrorMessage
	                    }
	                    
	              }
	          }	         
	      }
	  });

	$('#save').click(function(){
		var istoggle="";
	   	 if($("#toggle-on").prop("checked")==true) {	   		 
	   		 istoggle=true;
	   	}
	   	 else if($("#toggle-off").prop("checked")==true){
	   	  istoggle=false;
	   	 }
	   	 $('#valid').val(istoggle);
	   	var bootstrapValidator = $("#form1").data('bootstrapValidator');
		bootstrapValidator.validate();
	    if(bootstrapValidator.isValid()){
				document.getElementById("save").disabled = true; 
				$('#form1')[0].submit();
				return true;
			
		}else{
			console.log('Not Validated');			 
		}

	});
		
});

function resetForm(){
	$('#numId').val('0');
	$('#organisationName').val('');
	$('#organisationShortName').val('');
	$('#contactNumber').val('');
	$('#organisationAddress').val('');
	$('#save').text('Save');
}