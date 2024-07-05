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
		 $('#userinfo-message').delay(5000).fadeOut();
		$('#delete').click(function(){
			BudgetDelete();
		});
	});

	$(document).on('click','#edit',function(e){
		
		var resultArray = $(this).closest('tr').find('td').map( function()
				{
				return $(this).text();
				}).get();
		console.log(resultArray);
		
		$('#numId').val(resultArray[0].trim());
		$('#strBudgetHeadName').val(resultArray[1].trim());
		$('#strDescription').val(resultArray[2].trim());	
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
	    	  strBudgetHeadName: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Budget Head name is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 100,
	                        message: 'The Budget Head name must not be greater than 100 characters'
	                    },
	                    regexp: {
	                        regexp: nameRegex,
	                       message: nameErrorMessage
	                    }
	              }
	          },
	          strDescription: {
	              group: '.col-md-12',
	              validators: {
	                 
	                  stringLength: {
	                	  	min:0,
	                        max: 1000,
	                        message: 'Description must be less than 1000 characters'
	                    },
	              }
	          },
	         
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
				document.getElementById("form1").submit();
				resetForm();
		   }
	});
		
});

function resetForm(){
	$('#numId').val('');
	$('#strBudgetHeadName').val('');
	$('#strDescription').val('');
	$('#shortCode').val('');
	$('#save').text('Save');
}


function BudgetDelete(){
	
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
		      dangerMode: true,
		    }).then(function(isConfirm) {
		      if (isConfirm) {
			    	  $("#numIds").val(selected);
			  		  submit_form_delete();
			      }
	  	 /* if(confirm("Are you sure you want to delete the record"))
		  {
	  		  
	       $("#userinfo-message").show().delay(3000).fadeOut();
	    }*/
			      else{
			    	  
			      return false;
	    }
    });
    }
    else{
        swal("Please select at least one Budget to delete");  
}
 
}

function submit_form_delete()
{
document.form1.action = "/PMS/mst/deleteBudgetHeadMaster";
document.getElementById("form1").submit();
}

