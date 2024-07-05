$(document).ready(function() {
		//$('#example').DataTable();		
		$("input[name='mimeType']").change(function(){
		  var selectedValue = $("input[name='mimeType']:checked").val();
		  if(selectedValue == 1){
			  $('#mimeDiv').removeClass('hidden');			
			  $('#form1').bootstrapValidator('enableFieldValidators', 'mimeTypes', true);
			     
		  }else{
			  $('#mimeDiv').addClass('hidden');
			  $('#form1').bootstrapValidator('enableFieldValidators', 'mimeTypes', false);
		  }
		});	
});

$(document).ready(function(){
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
	    	  formatName: {	             
	              validators: {
	            	  notEmpty: {
	                      message: 'Document Format is required and cannot be empty'
	                  }, stringLength: {
	                        max: 50,
	                        message: 'Document Format must be less than 50 characters'
	                    }
	                    
	              }
	          }, mimeType: {
	                validators: {
	                    notEmpty: {
	                        message: 'Mime Type Applicable is required'
	                    }
	                }
	            },mimeTypes:{	        	  
	        	  validators: {
	        		  notEmpty: {
	                        message: 'Mime Types are required'
	                    }
	              }
	          }
	          
	      
	         
	      }
	  });
	$('#save').click(function(){
		var bootstrapValidator = $("#form1").data('bootstrapValidator');
			bootstrapValidator.validate();
		    if(bootstrapValidator.isValid()){
		    	console.log('Validated');
					document.getElementById("save").disabled = true; 					
					 $( "#form1")[0].submit();
					return true;
				
			}else{
				console.log('Not Validated');
				 //return false;
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
	$('#formatName').val(resultArray[1].trim()).trigger('input');
	
	if(resultArray[2].trim() == 'Yes'){
		$('#mimeDiv').removeClass('hidden');
		$('#mimeTypes').val(resultArray[3].trim()).trigger('input');
	}
	$('#icon').val(resultArray[3].trim());
	
	if(resultArray[4].trim()=='Active'){
		$('#toggle-off').removeAttr('checked');
		$('#toggle-on').attr('checked',true);

	}else{
		$('#toggle-on').removeAttr('checked');
		$('#toggle-off').attr('checked',true);
	}
	
	$('#save').text('Update');
});
