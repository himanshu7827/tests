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
	$('#toggle-off').removeAttr('Checked');
	$('#toggle-on').attr('checked',true);
		$('#example').DataTable();
		 $('#userinfo-message').delay(5000).fadeOut();
		$('#delete').click(function(){
			ThrustDelete();
		});
	});

	$(document).on('click','#edit',function(e){
		
		var resultArray = $(this).closest('tr').find('td').map( function()
				{
				return $(this).text();
				}).get();
		console.log(resultArray);
		
		$('#numId').val(resultArray[0].trim());
		$('#strThrustAreaName').val(resultArray[1].trim());	
		if(resultArray[2].trim()=='Active'){
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
		excluded: ':disabled',
	      message: 'This value is not valid',
	      feedbackIcons: {
	          valid: 'glyphicon glyphicon-ok',
	          invalid: 'glyphicon glyphicon-remove',
	          validating: 'glyphicon glyphicon-refresh'
	      },
	      fields: {
	    	  strThrustAreaName: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Thrust Area name is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 200,
	                        message: 'Thrust Area name must be less than 200 characters'
	                    },
	                    regexp: {
	                        regexp: nameRegex,
	                       message: nameErrorMessage
	                    }
	              }
	          }
	      }
	  });
	
	$('#save').click(function(){
		
		/*$('#strThrustAreaName').validatebox({
		    required: true,
		    validType: ['minLength[1]','maxLength[50]']
		}); */
		
		
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
		}
	
	});
		
});

function resetForm(){
	$('#numId').val('0');
	$('#strThrustAreaName').val('');
	/*$('#toggle-on').attr('checked',true);*/
	/*$('#toggle-on').prop('checked', true);*/
	$("#toggle-off").attr("checked", false);
	$("#toggle-on").attr("checked", true);
	$('#save').text('Save');
}


function ThrustDelete(){
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
	  	
			      else{
			    	  
			      return false;
	    }
    });
    }
    else{
        swal("Please select at least one Thrust Area to delete");  
}
 
}

function submit_form_delete()
{
document.form1.action = "/PMS/mst/deleteThrustAreaMaster";
document.getElementById("form1").submit();
}

