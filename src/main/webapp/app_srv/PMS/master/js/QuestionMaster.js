
$(document).ready(function() {
	
		$('#example').DataTable();
		 $('#userinfo-message').delay(5000).fadeOut();
		
	});

	$(document).on('click','#edit',function(e){
		
		var resultArray = $(this).closest('tr').find('td').map( function()
				{
				return $(this).text();
				}).get();
		console.log(resultArray);
		
		$('#numId').val(resultArray[0].trim());
		$('#strQuestions').val(resultArray[1].trim());	
		$('#answerId').val(resultArray[3].trim().split(',')).trigger('change');	

		$('#save').text('Update');
	});
	
	$(document).ready(function() {
		$('#example').DataTable();
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
	    	  strQuestions: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Question is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 100,
	                        message: 'Question must be less than 50 characters'
	                    },
	                   
	              }
	          },
	      
	          answerId:{
        	  group: '.col-md-12',
        	  validators: {
                  callback: {
                      message: 'Please choose Answer',
                      callback: function(value, validator) {
                    	  var options  = validator.getFieldElements('answerId').val();
                      return (options != 0);
                      }
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
				document.form1.submit();
				return true;
			
		}else{
			console.log('Not Validated');
			 //return false;
		}
		
		  /* if($('#form1').form('validate')){				
				document.getElementById("form1").submit();
				resetForm();
		   }*/
	});
		
});

function resetForm(){
	$('#numId').val('0');
	$('#strAnswer').val('');
	$('#answerId').val('').trigger('change');
	$('#save').text('Save');
}



