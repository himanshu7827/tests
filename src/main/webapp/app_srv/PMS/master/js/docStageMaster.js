var nameRegex='';
var nameErrorMessage='';
messageResource.init({	  
filePath : '/PMS/resources/app_srv/PMS/global/js'
});

messageResource.load('regexValidationforjs', function(){
	  // get value corresponding  to a key from regexValidationforjs.properties  
	nameRegex= messageResource.get('name.regex', 'regexValidationforjs');
	nameErrorMessage = messageResource.get('name.regex.message', 'regexValidationforjs');
	});


$(document).ready(function() {
		$('#example').DataTable();
	});

	$(document).on('click','#edit',function(e){
		var resultArray = $(this).closest('tr').find('td').map( function()
				{
				return $(this).text();
				}).get();
		console.log(resultArray);
		$('#numId').val(resultArray[0].trim());
		$('#strStageName').val(resultArray[1].trim()).trigger('input');
		
		$('#strStageDescription').val(resultArray[2].trim());
			
		
		if(resultArray[3].trim()=='Active'){
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
	    	  strStageName: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Document Stage name is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 150,
	                        message: 'The Document Stage name must be less than 150 characters'
	                    },
	                    regexp: {
	                        regexp: nameRegex,
	                       message: nameErrorMessage
	                    }
	              }
	          },strStageDescription:{
	        	  group: '.col-md-12',
	        	  validators: {
	        		  stringLength: {
	        			  	min:0,
	                        max: 500,
	                        message: 'Document Stage Description must be less than 500 characters'
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
	$('#strStageName').val('');
	
	
	$('#strStageDescription').val('');
	$('#save').text('Save');
}
