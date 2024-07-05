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
	
		var table = $('#datatable').DataTable();
		table.destroy();		
		/*$('#frm').addClass('hidden');*/		
		$('#addnewrecord').addClass('hidden');
		/*$('#datatable').addClass('hidden');*/
		getModulesData();
	});



$(document).on('click','#addnewrecord',function(e){
	
	$('#addnewrecord').addClass('hidden');});





$('.select2Option').select2({
	 width: '100%'
});
	$(document).on('click','#edit',function(e){
		$('#frm').removeClass('hidden');
		$('#addnewrecord').addClass('hidden');
		var resultArray = $(this).closest('tr').find('td').map( function()
				{
				return $(this).text();
				}).get();
		console.log(resultArray);
		$('#numId').val(resultArray[0].trim());
		$('#strModuleName').val(resultArray[1].trim()).trigger('input');
		
		$('#strModuleDescription').val(resultArray[2].trim()).trigger('input');
		
		
	/*	if(resultArray[3].trim()=='Active'){
			$('#toggle-off').removeAttr('checked');
			$('#toggle-on').attr('checked',true);

		}else{
			$('#toggle-on').removeAttr('checked');
			$('#toggle-off').attr('checked',true);
		}*/
		$('#save').text('Update');
	});
	
	
	$('#new_record').click(function(){	
		
		$('#frm').removeClass('hidden');
	
	});

	
	

	
	$('#projectId').change(function(){
		getModulesData();
	});
	function getModulesData(){
		var projectId = $('#projectId').val();
		var tableData = "";
		if(projectId == 0){
			$('#frm').addClass('hidden');
			$('#datatable').addClass('hidden');
			$('stagedetails').addClass('hidden');			
			$('#addnewrecord').addClass('hidden');
		}else{
			$.ajaxRequest.ajax({
    	        type: "POST",
    	        url: "/PMS/mst/getProjectModuleByProjectId",
    	        data: "projectId=" + projectId,			
				success : function(data) {		
					console.log(data);
					for(var i =0;i<data.length;i++){
						var row = data[i];
						/*var recordStatus= '';
						if(row.valid==true){
							recordStatus='Active';
								}else{recordStatus='Inactive';
									}*/
						tableData+="<tr><td><input type='checkbox' class='CheckBox' id='CheckBox' name='idCheck' value="+row.numId+" />"+row.numId+"</td> row.numId" +
								"<td>"+row.strModuleName+"</td>  <td>"+row.strModuleDescription+"</td>" +
								" <td><i class='fa fa-pencil-square-o btn btn-primary a-btn-slide-text' id='edit' /></td> </tr>"
								
					}
					tableData+='</tbody>';
					$('#datatable tbody').empty();
					$('#datatable').append(tableData);
					
				},				
				error : function(e) {
					$('#RecurringManpowerAmt').val(0);
					
					alert('Error: ' + e);
					
				}
			});
			
			
			$('#datatable').removeClass('hidden');
			$('#stagedetails').removeClass('hidden');
			$('#addnewrecord').removeClass('hidden');		
		}
		
	}
	
	

	
	

$(document).ready(function(){
	
	$('#reset').click(function(){
		resetForm();
		$('#form1').data('bootstrapValidator').resetForm(true);
	});
	
	
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
	    	  strModuleName: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Module name is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 250,
	                        message: 'The Module name must be less than 250 characters'
	                    },
	                    regexp: {
	                        regexp: nameRegex,
	                       message: nameErrorMessage
	                    }
	              }
	          },
	          projectId:{
	        	  group: '.col-md-12',
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
	          strModuleDescription:{
	        	  group: '.col-md-12',
	        	  validators: {
	        		  notEmpty: {
	                      message: 'Module Description is required and cannot be empty'
	                  },
	        		  stringLength: {
	                        max: 2500,
	                        message: 'Module Description must be less than 2500 characters'
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
			
		/*var istoggle="";
	   	 if($("#toggle-on").prop("checked")==true) {	   		 
	   		 istoggle=true;
	   	}
	   	 else if($("#toggle-off").prop("checked")==true){
	   	  istoggle=false;
	   	 }
	   	 $('#valid').val(istoggle);*/
		
	  /* if($('#form1').form('validate')){				
				document.getElementById("form1").submit();
				resetForm();
		   }
		   */
		   
			var bootstrapValidator = $("#form1").data('bootstrapValidator');
			bootstrapValidator.validate();
		    if(bootstrapValidator.isValid()){				
					document.getElementById("save").disabled = true; 
					$( "#form1")[0].submit();
					return true;
				
			}else{
				console.log('Not Validated');
			}
		   
	});
		
});

function resetForm(){
	$('#numId').val('0');
	$('#projectId').val('0').trigger('change');
	$('#strModuleName').val('');
	$('#strModuleDescription').val('');
	

	$('#save').text('Save');
}

function goprev()
{
	 var path=$(location).attr('pathname');  

	 document.getElementById("form1").action="/PMS/prevRedirectPage?path='"+path+"'&workflowTypeId=3&uniqueId="+$("#projectid").val();
		document.getElementById("form1").method="POST";
		document.getElementById("form1").submit();

}



function gonext()
{
	 var path=$(location).attr('pathname');  

	 document.getElementById("form1").action="/PMS/nextRedirectPage?path='"+path+"'&workflowTypeId=3&uniqueId="+$("#projectid").val();
		document.getElementById("form1").method="POST";
		document.getElementById("form1").submit();

}

function redirectPage(path){
	var encProjectId = $('#encProjectId').val();
	openWindowWithPost('GET',path,{"encProjectId" : encProjectId},'_self');
}