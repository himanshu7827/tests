var amountRegex='';
messageResource.init({	  
filePath : '/PMS/resources/app_srv/PMS/global/js'
});

messageResource.load('regexValidationforjs', function(){
	  // get value corresponding  to a key from regexValidationforjs.properties  
	amountRegex= messageResource.get('email.regex', 'regexValidationforjs');

	});

$(document).ready(function() {
	
		$('#example').DataTable();
		 $('#userinfo-message').delay(5000).fadeOut();
		$('#delete').click(function(){
			RoleDelete();
		});
		
		$('.select2Option').select2({
			 width: '100%'
		});
		
	});

	$(document).on('click','#edit',function(e){
		$('#form1').data('bootstrapValidator').resetForm(true);
		
		var resultArray = $(this).closest('tr').find('td').map( function()
				{
				return $(this).text();
				}).get();
		//console.log(resultArray);
		
		$('#numId').val(resultArray[0].trim());
		$('#roleName').val(resultArray[1].trim());
		$('#roleShortName').val(resultArray[2].trim());	
		$('#bgColor').val(resultArray[5].trim());
		if(resultArray[3].trim()=='Active'){
			$('#toggle-off').removeAttr('checked');
			$('#toggle-on').attr('checked',true);

		}else{
			$('#toggle-on').removeAttr('checked');
			$('#toggle-off').attr('checked',true);
		}
		
		$('#accessLevel').val(resultArray[4].trim()).trigger('change');
		
		$('#save').text('Update');
	});
	
	
	


$(document).ready(function(){
	
	

	$('#reset').click(function(){
		resetForm();
		$('#form1').data('bootstrapValidator').resetForm(true);

	});
	console.log(amountRegex);
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
	    	  roleName: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Role name is required and cannot be empty'
	                  }
	              }
	          },
	          bgColor: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Role color is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 10,
	                        message: 'Role color must be less than 10 characters'
	                    },
	              }
	          },
	          roleShortName: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Role Short Name is required and cannot be empty'
	                  }
	              }
	          },accessLevel:{
	        	  group: '.col-md-12',
	        	  validators: {
	                  callback: {
	                      message: 'Please choose access level',
	                      callback: function(value, validator) {
	                          // Get the selected options
	                          var options = validator.getFieldElements('accessLevel').val();
	                          return (options != 0);
	                      }
	                  }
	              }
	          }
	          
	      
	         
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
		
		/*$('#roleShortName').validatebox({
		    required: true,
		    validType: ['minLength[1]','maxLength[20]']
		});
		
		
		$('#roleName').validatebox({
		    required: true,
		    validType: ['minLength[1]','maxLength[50]']
		}); 
		
		
		var istoggle="";
	   	 if($("#toggle-on").prop("checked")==true) {	   		 
	   		 istoggle=true;
	   		 }
	   	 else if($("#toggle-off").prop("checked")==true){
	   	  istoggle=false;
	   	 }
	   	 $('#valid').val(istoggle);
		
		   if($('#form1').form('validate')){				
				document.getElementById("form1").submit();
				resetForm();
		   }*/
		
		
	});
		
});

function resetForm(){
	$('#numId').val('0');
	$('#roleName').val('');
	$('#roleShortName').val('');
	$('#bgColor').val('');
	$('#accessLevel').val(0).trigger('change');
	$('#save').text('Save');
}


function RoleDelete(){
    var chkArray = [];
     
    $(".CheckBox:checked").each(function() {
        chkArray.push($(this).val());
    });
    
    var selected;
    selected = chkArray.join(','); 
   
    if(selected.length >= 1){
    	
    	
    	 swal({
		      title: "Are you sure you want to delete the record?",
		      icon: "warning",
		      buttons: [
		                'No',
		                'Yes'
		              ],
		      
		    }).then(function(isConfirm) {
		      if (isConfirm) {
			    	  $("#numIds").val(selected);
			  		  submit_form_delete();
			      }
			      else{
			    	  
			      return false;
	    }
    });
    }
    else{
    
        swal("Please select at least one Role to delete");  
}
 
}

function submit_form_delete()
{
document.form1.action = "/PMS/mst/deleteRoleMaster";
document.getElementById("form1").submit();
}

