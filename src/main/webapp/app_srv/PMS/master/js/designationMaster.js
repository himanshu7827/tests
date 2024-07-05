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



/*$(document).ready(function() {
	 var table =  $('#example').DataTable( {
	        "paging":   false,
	        "ordering": false,
	        "info":     false,
	        "processing": true,
	    } );
	});*/

$(document).ready(function() {
    var table = $('#example').DataTable( {
    	"paging":   false,
        rowReorder: true
    } );
} );
$(document).ready(function() {
	$('.select2Option').select2({width:"100%"});
});

	$(document).on('click','#edit',function(e){
		var resultArray = $(this).closest('tr').find('td').map( function()
				{
				return $(this).text();
				}).get();
		console.log(resultArray);
		
		$('#numId').val(resultArray[1].trim());
		
		$('#strDesignationName').val(resultArray[2].trim()).trigger('input');
		
		$('#strDesignationShortCode').val(resultArray[3].trim());
		$('#strDesription').val(resultArray[4].trim());	
/*		$('#hierarchy').val(resultArray[7].trim());	
*/		if(resultArray[5].trim()=="Yes")
		$('#isOrganisationSpecific').val(1).trigger('change');
		else
			$('#isOrganisationSpecific').val(2).trigger('change');
		if(resultArray[6].trim()=="Yes")
			$('#isGroupSpecific').val(1).trigger('change');	
		else
			$('#isGroupSpecific').val(2).trigger('change');	
		if(resultArray[7].trim()=="Technical")
			$('#numProjectSpecific').val(1).trigger('change');	
		else if(resultArray[7].trim()=="Non-Technical")
			$('#numProjectSpecific').val(2).trigger('change');	
		else
			$('#numProjectSpecific').val(0).trigger('change');	


		if(resultArray[8].trim()=="Yes")
			$('#isThirdPartySpecific').val(1).trigger('change');	
		else
			$('#isThirdPartySpecific').val(0).trigger('change');	

		if(resultArray[9].trim()=='Active'){
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
	    	  strDesignationName: {
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
	          strDesription: {
	              group: '.col-md-12',
	              validators: {
	                 
	                  stringLength: {
	                	  	min:0,
	                        max: 500,
	                        message: 'Designation Description must be less than 500 characters'
	                    },
	              }
	          },strDesignationShortCode:{
	        	  group: '.col-md-12',
	        	  validators: {
	        		  stringLength: {
	        			  	min:0,
	                        max: 20,
	                        message: 'Designation Short Code must be less than 20 characters'
	                    },
	              }
	          },
	          
	          isGroupSpecific:{
	        	  group: '.col-md-12',
	        	  validators: {
	                  callback: {
	                      message: 'Please choose Group Specific',
	                      callback: function(value, validator) {
	                          // Get the selected options
	                          var options = validator.getFieldElements('isGroupSpecific').val();
	                          return (options != 0);
	                      }
	                  }
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
	          isOrganisationSpecific:{
	        	  group: '.col-md-12',
	        	  validators: {
	                  callback: {
	                      message: 'Please choose Organization Specific',
	                      callback: function(value, validator) {
	                          // Get the selected options
	                          var options = validator.getFieldElements('isOrganisationSpecific').val();
	                          return (options != 0);
	                      }
	                  }
	              }
	          },
	          numProjectSpecific:{
	        	  group: '.col-md-12',
	        	  validators: {
	                  callback: {
	                      message: 'Please choose Project Specific',
	                      callback: function(value, validator) {
	                          // Get the selected options
	                          var options = validator.getFieldElements('numProjectSpecific').val();
	                          return (options != 0);
	                      }
	                  }
	              }
	          },
	          isThirdPartySpecific:{
	        	  group: '.col-md-12',
	        	  validators: {
	                  callback: {
	                      message: 'Please choose if Designation is specific to Third Party',
	                      callback: function(value, validator) {
	                          // Get the selected options
	                          var options = validator.getFieldElements('isThirdPartySpecific').val();
	                          return (options != -1);
	                      }
	                  }
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
	$('#numId').val('0');
	$('#strDesignationName').val('');
	$('#strDesignationShortCode').val('');
	$('#save').text('Save');
	$('#strDesription').val('');
	$('#isOrganisationSpecific').val('0').trigger('change');
	$('#numProjectSpecific').val('0').trigger('change');
	$('#isGroupSpecific').val('0').trigger('change');
	$('#isThirdPartySpecific').val('-1').trigger('change');
	$('#save').text('Save');
}

$(document).ready(function() {
	$('#isGroupSpecific').change(function(){
		  if($(this).val() == '1'){ 
			$('#isOrganisationSpecific').val('1').trigger('change'); 
		    $('#form1').bootstrapValidator('enableFieldValidators', 'isOrganisationSpecific', true);

		  }	  
		});	
		});

$(document).ready(function() {
	$('#isOrganisationSpecific').change(function(){
		  if($(this).val() == '2'){ 
			$('#isGroupSpecific').val('2').trigger('change'); 
		    $('#form1').bootstrapValidator('enableFieldValidators', 'isGroupSpecific', true);

		  }	  
		});	
		});

var inputArray = [];
$('.saveHierarchy').click(function(){
	$('.designationIds').each(
			function(index) {
				var designationValue=this.value;
				var rowObject = new Object();
					rowObject.designation=designationValue;
					rowObject.hierarchy = index+1;
					//console.log( rowObject.hierarchy);

				  inputArray.push(rowObject);
			}
	);
	
	
		 $.ajaxRequest.ajax({
		        type: "POST",
		        url: "/PMS/mst/saveDesignationData",
		        data:{
		        	  "designationDetail":JSON.stringify(inputArray)},
		        async:false,       		
				success : function(data) {
					if(data>0){
						sweetSuccess('Success'," Record saved/updated successfully");
					}else{
						sweetSuccess('Error',"Something went wrong");
					}
					
					
				},error : function(data) {
					
				}
					
			});
		
});

