var addressRegex = '';
var addressErrorMessage = '';
var numericRegex = '';
var numericRegexMessage = '';
var amountRegex = '';
var amountRegexMessage = '';
var nameRegex='';
var nameErrorMessage='';
var mobileRegex='';
var mobileErrorMessage='';
var contactRegex='';
var contactErrorMessage='';

messageResource.init({
	filePath : '/PMS/resources/app_srv/PMS/global/js'
});

messageResource.load('regexValidationforjs',
		function() {
			numericRegex = messageResource.get('numeric.regex',
					'regexValidationforjs');
			numericRegexMessage = messageResource.get('numeric.regex.message',
					'regexValidationforjs');

			addressRegex = messageResource.get('address.regex',
					'regexValidationforjs');
			addressErrorMessage = messageResource.get('address.regex.message',
					'regexValidationforjs');

			amountRegex = messageResource.get('amount.regex',
					'regexValidationforjs');
			amountRegexMessage = messageResource.get('amount.regex.message',
					'regexValidationforjs');
			
			nameRegex= messageResource.get('name.regex', 'regexValidationforjs');
			nameErrorMessage = messageResource.get('name.regex.message', 'regexValidationforjs');
			
			mobileRegex= messageResource.get('mobileno.regex', 'regexValidationforjs');
			mobileErrorMessage = messageResource.get('mobileno.regex.message', 'regexValidationforjs');
			
			contactRegex= messageResource.get('contact.regex', 'regexValidationforjs');
			contactErrorMessage = messageResource.get('contact.regex.message', 'regexValidationforjs');
		});

$(document).ready(function() {
$('#toggle-off').removeAttr('Checked');
	$('#toggle-on').attr('checked',true);
		$('#example').DataTable({
			 "columnDefs": [
			                {
			                    "targets": [ 2 ],
			                    "visible": false,
			                    "searchable": true
			                }
			            ]
		});
		
		$('#form1').bootstrapValidator({
//		      live: 'disabled',
			excluded: ':disabled',
		      message: 'This value is not valid',
		      feedbackIcons: {
		          valid: 'glyphicon glyphicon-ok',
		          invalid: 'glyphicon glyphicon-remove',
		          validating: 'glyphicon glyphicon-refresh'
		      },
		      fields: {
		    	           
		          
		    	  clientName: {
		              group: '.col-md-12',
		              validators: {
		            	  notEmpty: {
		                      message: 'End User Name is required and cannot be empty'
		                  },
		                 
		                  stringLength: {
		                        max: 500,
		                        message: 'The End User Name must be less than 500 characters'
		                    },
		                    regexp: {
		                        regexp: nameRegex,
		                       message: nameErrorMessage
		                    }
		              }
		          },
		          contactNumber: {
		              group: '.col-md-12',
		              validators: {
		                  notEmpty: {
		                      message: 'Contact Number is required and cannot be empty'
		                  },
		                  regexp: {
		                        regexp: contactRegex,
		                       message: contactErrorMessage
		                    }
		                    
		              }
		          },
		          clientAddress: {
		              group: '.col-md-12',
		              validators: {
		                  notEmpty: {
		                      message: 'End User Address is required and cannot be empty'
		                  },stringLength: {
		                        max: 500,
		                        message: 'The End User Address must be less than 500 characters'
		                    },
		                    regexp: {
		                        regexp: addressRegex,
		                       message: addressErrorMessage
		                    }
		              }
		          },
		          
		         
		      }
		  });
		
	});

	$(document).on('click','#edit',function(e){
		var resultArray = $(this).closest('tr').find('td').map( function()
				{
				return $(this).text();
				}).get();
		console.log(resultArray);
		$('#numId').val(resultArray[0].trim());
		$('#clientName').val(resultArray[1].trim());
		$('#clientAddress').val(resultArray[2].trim());
		$('#contactNumber').val(resultArray[3].trim());		
		$('#shortCode').val(resultArray[4].trim());
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
		 $('#form1').data('bootstrapValidator').resetForm(true);

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
			 //return false;
		}
		
		   /*if($('#form1').form('validate')){				
				document.getElementById("form1").submit();
				resetForm();
		   }*/
	});
		
});

function resetForm(){
	$('#numId').val(0);
	$('#clientName').val('');
	$('#contactNumber').val('');
	$('#clientAddress').val('');
	$('#shortCode').val('');
	/*$('#toggle-on').attr('checked',true);*/
$("#toggle-off").attr("checked", false);
	$("#toggle-on").attr("checked", true);
	$('#save').text('Save');
}

$(document).ready(function(){
	$('#backPage').click(function(){
		var referrerValue = $('#referrerValue').val();
		if(referrerValue){
			window.location.href = referrerValue;
		}
	});
});