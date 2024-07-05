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
    var table = $('#example').DataTable( {
    	"paging":   false,
        rowReorder: true
    } );
} );


	$(document).on('click','#edit',function(e){
		var resultArray = $(this).closest('tr').find('td').map( function()
				{
				return $(this).text();
				}).get();
		console.log(resultArray);
		
		$('#numId').val(resultArray[0].trim());		
		$('#designationName').val(resultArray[1].trim()).trigger('input');		
		$('#designationShortCode').val(resultArray[2].trim());
		$('#desription').val(resultArray[3].trim());			
		$('#save').text('Update');		
	});
	
	

$(document).ready(function(){	

	$('#reset').click(function(){
		resetForm();
		 $('#form1').data('bootstrapValidator').resetForm(true);
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
	    	  designationName: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Designation name is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 100,
	                        message: 'The Designation name must be less than 100 characters'
	                    },
	                    regexp: {
	                        regexp: nameRegex,
	                       message: nameErrorMessage
	                    }
	              }
	          },
	          desription: {
	              group: '.col-md-12',
	              validators: {
	                 
	                  stringLength: {
	                	  	min:0,
	                        max: 500,
	                        message: 'Designation Description must be less than 500 characters'
	                    },
	              }
	          },designationShortCode:{
	        	  group: '.col-md-12',
	        	  validators: {
	        		  stringLength: {	        			  
	                        max: 20,
	                        message: 'Designation Short Code must be less than 20 characters'
	                    },
	              }
	          }	         
	      }
	  });
	
	
	
	$('#save').click(function(){
		
	   	 
		var bootstrapValidator = $("#form1").data('bootstrapValidator');
		bootstrapValidator.validate();
	    if(bootstrapValidator.isValid()){				
				document.getElementById("save").disabled = true;				
				$( "#form1")[0].submit();
				return true;
			
		}else{
			console.log('Not Validated');
			 //return false;
		}
	    
	    
	    
	});
		
});

function resetForm(){
	$('#numId').val('0');
	$('#designationName').val('');	
	$('#designationShortCode').val('');
	$('#desription').val('');	
	$('#save').text('Save');
}
