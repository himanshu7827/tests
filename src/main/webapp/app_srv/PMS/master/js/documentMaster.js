var nameRegex='';
var nameErrorMessage='';
var numericRegex='';
var numericRegexMessage='';
messageResource.init({	  
filePath : '/PMS/resources/app_srv/PMS/global/js'
});

messageResource.load('regexValidationforjs', function(){
	  // get value corresponding  to a key from regexValidationforjs.properties  
	nameRegex= messageResource.get('name.regex', 'regexValidationforjs');
	nameErrorMessage = messageResource.get('name.regex.message', 'regexValidationforjs');
	numericRegex= messageResource.get('integer.regex', 'regexValidationforjs');
	numericRegexMessage = messageResource.get('integer.regex.message', 'regexValidationforjs');
	
	});


$(document).ready(function() {
	$('#toggle-off').removeAttr('Checked');
	$('#toggle-on').attr('checked',true);
		$('#example').DataTable();
		$('.select2Option').select2({width:"100%"});
		$('#userinfo-message').delay(5000).fadeOut();
		$('#delete').click(function(){
			DocumentMasterDelete();
		});
	});

	$(document).on('click','#edit',function(e){
		 $('#form_document_mst').data('bootstrapValidator').resetForm(true);

		var resultArray = $(this).closest('tr').find('td').map( function()
				{
				return $(this).text();
				}).get();
		console.log(resultArray);
		//alert(resultArray[3]);
		$('#numId').val(resultArray[0].trim());
		$('#docTypeName').val(resultArray[1].trim());
		$('#description').val(resultArray[2].trim());
		$('#shortCode').val(resultArray[3].trim());
		$('#icon').val(resultArray[4].trim());
		$('#displaySeq').val(resultArray[5].trim());
		$('#docTypeFormatId').val(resultArray[9].trim().split(',')).trigger('change');	

		if(resultArray[6].trim()=='Yes'){
			$('#showOnDashboard1').attr('checked',true);

		}else{
			$('#showOnDashboard1').attr('checked',false);
		}
		if(resultArray[7].trim()=='Active'){
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
	    $('#form_document_mst').data('bootstrapValidator').resetForm(true);
	});	
	
	 
	$('#form_document_mst').bootstrapValidator({
//	      live: 'disabled',
		excluded: ':disabled',
	      message: 'This value is not valid',
	      feedbackIcons: {
	          valid: 'glyphicon glyphicon-ok',
	          invalid: 'glyphicon glyphicon-remove',
	          validating: 'glyphicon glyphicon-refresh'
	      },
	      fields: {
	    	  docTypeName: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Document name is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 100,
	                        message: 'The Document name must be less than 100 characters'
	                    },
	                    regexp: {
	                        regexp: nameRegex,
	                       message: nameErrorMessage
	                    }
	              }
	          },
	          shortCode: {
	              group: '.col-md-12',
	              validators: {
	                  stringLength: {
	                        max: 10,
	                        message: 'Short Code must be less than 10 characters'
	                    },
	              }
	          },
	          
	          description: {
	              group: '.col-md-12',
	              validators: {
	                 
	                  stringLength: {
	                        max: 1000,
	                        message: 'The Document description must be less than 1000 characters'
	                    },
	                    regexp: {
	                        regexp: nameRegex,
	                       message: nameErrorMessage
	                    }
	              }
	          },
	          docTypeFormatId:{
	        	  group: '.col-md-12',
	        	  validators: {
	                  callback: {
	                      message: 'Please choose Document Format',
	                      callback: function(value, validator) {
	                          // Get the selected options
	                          /*var options = validator.getFieldElements('thrustAreaData').val();
	                          return (options != 0);*/
	                    	  var options  = validator.getFieldElements('docTypeFormatId').val();
                          return (options.length >= 1);
	                      }
	                  }
	              }
	          }         ,
	          displaySeq: {
	              group: '.col-md-12',
	              validators: {
	                 
	                  stringLength: {
	                        max: 10,
	                        message: 'Display sequence must be less than 10 characters'
	                    },
	                    regexp: {
	                        regexp: numericRegex,
	                       message: numericRegexMessage
	                    }
	              }
	          },
	          icon: {	             
	              validators: {	                 
	                  stringLength: {
	                        max: 100,
	                        message: 'The Icon must be less than 100 characters'
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
	   	var bootstrapValidator = $("#form_document_mst").data('bootstrapValidator');
		bootstrapValidator.validate();
	    if(bootstrapValidator.isValid()){
	    	console.log(' Validated');
	    	document.getElementById("save").disabled = true; 
	    	$('#form_document_mst')[0].submit();
			return true;
				
			
		}else{
			console.log('Not Validated');
			 
		}
		
	});
		
	
	
});

function resetForm(){
	$('#numId').val('');
	$('#docTypeName').val('');
	$('#description').val('');
	$('#shortCode').val('');
	$('#save').text('Save');
}

//Delete
function DocumentMasterDelete(){
	//alert("dfsff");
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
		    	 // alert("inside confirm");
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
        swal("Please select at least one Document to delete");  
}
 
}

function submit_form_delete()
{
	alert("inside submit_form_delete ");
document.form_document_mst.action = "/PMS/mst/deleteDocument";
document.getElementById("form_document_mst").submit();
}