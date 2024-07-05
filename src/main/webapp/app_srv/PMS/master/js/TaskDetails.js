var amountRegex='';
var amountRegexMessage='';
var nameRegex='';
var nameErrorMessage='';

messageResource.init({	  
filePath : '/PMS/resources/app_srv/PMS/global/js'
});

messageResource.load('regexValidationforjs', function(){
	  // get value corresponding  to a key from regexValidationforjs.properties  
	nameRegex= messageResource.get('name.regex', 'regexValidationforjs');
	nameErrorMessage = messageResource.get('name.regex.message', 'regexValidationforjs');
	amountRegex = messageResource.get('amount.regex', 'regexValidationforjs'); 
	amountRegexMessage = messageResource.get('amount.regex.message', 'regexValidationforjs');
	});

$(document).ready(function() {
	
		$('#example').DataTable();
		$('.select2Option').select2({width:"100%"});
		 $('#userinfo-message').delay(5000).fadeOut();
		
	});

	$(document).on('click','#edit',function(e){
		 $('#form1').data('bootstrapValidator').resetForm(true);
		var resultArray = $(this).closest('tr').find('td').map( function()
				{
				return $(this).text();
				}).get();
		//alert(resultArray[11]);
		$('#numId').val(resultArray[0].trim());
		$('#taskName').val(resultArray[1].trim());
		$('#projectId').val(resultArray[2].trim()).trigger('change');
		$('#expectedTime').val(resultArray[4].trim());
		$('#priority').val(resultArray[5].trim()).trigger('change');
		$('#taskDescription').val(resultArray[6].trim());
//		$('#milestoneId').val(resultArray[8].trim()).trigger('change');
//		$('#activityRadioValue').val(resultArray[11].trim()).trigger('change');

	/*	if(resultArray[11].trim()!=1){
			$('#toggle-off2').removeAttr('checked');
			$('#toggle-on1').attr('checked',true);

		}else{
			$('#toggle-on1').removeAttr('checked');
			$('#toggle-off2').attr('checked',true);
		}*/
		if(resultArray[7].trim()==1){
			$('#toggle-off2').removeAttr('checked');
			$('#toggle-on1').attr('checked',true);

		}else{
			$('#toggle-on1').removeAttr('checked');
			$('#toggle-off2').attr('checked',true);
		}
		if(resultArray[13].trim()=='Active'){
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
	    	  taskName: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Task name is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 250,
	                        message: 'Task name must be less than 50 characters'
	                    },
	                    regexp: {
	                        regexp: nameRegex,
	                       message: nameErrorMessage
	                    }
	                    
	              }
	          },
	      
	      projectId:{
	        	 
        	  validators: {
                  callback: {
                      message: 'Please choose Project Name',
                      callback: function(value, validator) {
                          // Get the selected options
                          var options = validator.getFieldElements('projectId').val();
                          return (options != 0);
                      }
                  }
              }
	      },
          taskDescription: {
              group: '.col-md-12',
              validators: {
                  notEmpty: {
                      message: 'Task description is required and cannot be empty'
                  },
                  stringLength: {
                        max: 2000,
                        message: 'Task name must be less than 2000 characters'
                    },
                    
                       }
          },
          expectedTime: {
              group: '.col-md-12',
              validators: {
                  notEmpty: {
                      message: 'Expected Time is required and cannot be empty'
                  },
                  stringLength: {
                      max: 5,
                      message: 'Expected time must be less than 4 digits'
                  },
                  regexp: {
                      regexp: amountRegex,
                     message: amountRegexMessage
                  }
                  
                       }
          },
          priority: {
        	  validators: {
                  callback: {
                      message: 'Please choose Priority',
                      callback: function(value, validator) {
                          // Get the selected options
                          var options = validator.getFieldElements('priority').val();
                          return (options != 0);
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

         var radioValue = $("input[name='idCheck']:checked").val();
         if(radioValue){
        	 $("#activityRadioValue").val(radioValue);
         }
        
         
	   	var bootstrapValidator = $("#form1").data('bootstrapValidator');
		bootstrapValidator.validate();
		if(bootstrapValidator.isValid()){
			document.getElementById("save").disabled = true; 
			$('#form1')[0].submit();
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
	$('#taskName').val('');
	$('#projectId').val(0).trigger('change');
	$('#taskDescription').val('');
	$('#expectedTime').val(0.0);
	$('#priority').val('').trigger('change');
	$('#save').text('Save');
}

$(document).ready(function() {
	//$('#example').DataTable();		
	$("input[name='withMilestone']").change(function(){
	  var selectedValue = $("input[name='withMilestone']:checked").val();
	  if(selectedValue == 1){
		  $('#milestoneDiv').removeClass('hidden');	
		  $('#milestoneTable').show();
		  $('#form1').bootstrapValidator('enableFieldValidators', 'milestoneId', true);
		     
	  }else{
		  $('#milestoneDiv').addClass('hidden');
		  $('#milestoneTable').hide();
		  $('#form1').bootstrapValidator('enableFieldValidators', 'milestoneId', false);
	  }
	});	
});


		 $(document).on('change','#milestoneId',function(e){
				var milestoneId = $(this).find(":selected").val();
				//swal('Please select atlest one activity');
				activityDetails(milestoneId);
			}); 
			 
			 	  

function  activityDetails(Id){	
	var tableData = "";
			 $.ajax({
				 type: "POST",
				 url: "/PMS/mst/activityData",
				 data: "milestoneId="+ Id,
				 success: function(data) { 	  
						for(var i =0;i<data.length;i++){
							var row = data[i];
							
							tableData+="<tr><td><input type='radio' class='radio' id='radio' name='idCheck' value="+row.numId+" /></td> row.numId" +
									"<td>"+row.description+"</td><td>"+row.strModuleName+"</td></tr>"
									
						}
						tableData+='</tbody>';
						$('#milestoneTable tbody').empty();						
						$('#milestoneTable').append(tableData);		
						
					},				
					error : function(e) {
						//$('#RecurringManpowerAmt').val(0);
						alert('Error: ' + e);
				 	}
			 });
			 
			 $('#milestoneTable').removeClass('hidden');
			
		 	}
function openWindowWithPost(verb, url, data, target) {
	  var form = document.createElement("form");
	  form.action = url;
	  form.method = verb;
	  form.target = target || "_self";
	  if (data) {
	    for (var key in data) {
	      var input = document.createElement("textarea");
	      input.name = key;
	      input.value = typeof data[key] === "object" ? JSON.stringify(data[key]) : data[key];
	      form.appendChild(input);
	    }
	  }
	  form.style.display = 'none';
	  document.body.appendChild(form);
	  form.submit();
}
function downloadDocument(documentId){
	openWindowWithPost('POST','/PMS/mst/downloadTaskFile',{"encDocumentId" : documentId},'_blank');
}
