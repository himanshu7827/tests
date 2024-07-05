var nameRegex='';
var nameErrorMessage='';
var numericRegex='';
var numericErrorMessage='';
messageResource.init({	  
filePath : '/PMS/resources/app_srv/PMS/global/js'
});

messageResource.load('regexValidationforjs', function(){
	  // get value corresponding  to a key from regexValidationforjs.properties  
	nameRegex= messageResource.get('name.regex', 'regexValidationforjs');
	nameErrorMessage = messageResource.get('name.regex.message', 'regexValidationforjs');
	numericRegex= messageResource.get('numeric.regex', 'regexValidationforjs');
	numericErrorMessage = messageResource.get('numeric.regex.message', 'regexValidationforjs');
	});



$(document).ready(function() {
$('#toggle-off').removeAttr('Checked');
	$('#toggle-on').attr('checked',true);
		$('#example').DataTable();
	});

	$(document).on('click','#edit',function(e){
		 $('#form1').data('bootstrapValidator').resetForm(true);

		var resultArray = $(this).closest('tr').find('td').map( function()
				{
				return $(this).text();
				}).get();
		console.log(resultArray);
		$('#numId').val(resultArray[0].trim());
		$('#strEmpTypeName').val(resultArray[1].trim()).trigger('input');
		
		$('#empShortName').val(resultArray[2].trim());
		$('#bgColor').val(resultArray[3].trim());
		$('#hierarchy').val(resultArray[4].trim());
		
		if(resultArray[5].trim()=='Active'){
			$('#toggle-off').attr('checked',false);
			$('#toggle-on').attr('checked',true);

		}else{
			$('#toggle-on').attr('checked',false);
			$('#toggle-off').attr('checked',true);
		}
		$('#save').text('Update');
	});
	
	

$(document).ready(function(){	

	$('#reset').click(function(){
		resetForm();
		 //$('#form1').data('bootstrapValidator').resetForm(true);
	});
	

	$('#form1').bootstrapValidator({
//	      live: 'disabled',
		/*excluded: ':disabled',*/
	      message: 'This value is not valid',
	      feedbackIcons: {
	          valid: 'glyphicon glyphicon-ok',
	          invalid: 'glyphicon glyphicon-remove',
	          validating: 'glyphicon glyphicon-refresh'
	      },
	      fields: {
	    	  strEmpTypeName: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Employee Type name is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 150,
	                        message: 'The Employee Type name must be less than 150 characters'
	                    },
	                    regexp: {
	                        regexp: nameRegex,
	                       message: nameErrorMessage
	                    }
	              }
	          },
	          bgColor: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Employee Type color is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 10,
	                        message: 'Employee Type color must be less than 10 characters'
	                    },
	              }
	          },
	          hierarchy: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Hierarchy is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 2,
	                        message: 'Hierarchy must be less than 2 digits'
	                    }, 
	                    regexp: {
	                        regexp: numericRegex,
		                       message: numericErrorMessage
		                    },
	                    
	                                }
	          },
	          empShortName:{
	        	  group: '.col-md-12',
	        	  validators: {
	        		  stringLength: {
	        			  	min:0,
	                        max: 20,
	                        message: 'Employee Short Name must be less than 20 characters'
	                    },
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
				/*document.form1.submit();*/
				 $( "#form1")[0].submit();
				return true;
			
		}else{
			console.log('Not Validated');
			 //return false;
		}
	});
		
});

function resetForm(){
	$('#numId').val('');
	$('#strEmpTypeName').val('');
	$('#bgColor').val('');
	$('#hierarchy').val('');
	$('#empShortName').val('');
	$("#toggle-off").attr("checked", false);
	$("#toggle-on").attr("checked", true);
	$('#save').text('Save');
}
