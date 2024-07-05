
$(document).ready(function() {
	
		$('#example').DataTable();
		 $('#userinfo-message').delay(5000).fadeOut();
			$('.select2Option').select2({width:"100%"});

	});

$(document).ready(function(){	
	
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
	    	  employeeId:{
	        	 
	        	  validators: {
	                  callback: {
	                      message: 'Please choose Employee Name',
	                      callback: function(value, validator) {
	                          // Get the selected options
	                          var options = validator.getFieldElements('employeeId').val();
	                          return (options != 0);
	                      }
	                  }
	              }
	          },
	          roles:{
	        	  group: '.col-md-12',
	        	  validators: {
	                  callback: {
	                      message: 'Please choose Roles between 1 to 30',
	                      callback: function(value, validator) {
	                          // Get the selected options
	                          var options = validator.getFieldElements('roles').val();
	  	                      return (options.length >= 1 && options.length<=30);
	                      }
	                  }
	              }
	          },
	      }
	    	 
	      });
	$('#save').click(function(){
		
		
	   	var bootstrapValidator = $("#form1").data('bootstrapValidator');
		bootstrapValidator.validate();
	    if(bootstrapValidator.isValid()){
				document.getElementById("save").disabled = true; 
				document.form1.submit();
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

$(document).on('change','#employeeId',function(e){
	var numId = $('#employeeId').val();	
	empDetails(numId);
}); 


function empDetails(numId){

	$.ajaxRequest.ajax({
	 type: "POST",
	 url: "/PMS/mst/EmployeeDatabyempId",
	 data: "employeeId="+ numId,
	 success: function(response) { 		 
	 $('#roles').val(response.roles).trigger('change');
	 
		 
	 }
	});
}

function resetForm(){
	$('#employeeId').val(0).trigger('change');
	$('#roles').val('').trigger('change');
	$('#save').text('Save');
}