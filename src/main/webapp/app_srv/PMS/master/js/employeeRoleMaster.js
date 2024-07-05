var nameRegex='';
var nameErrorMessage='';
var amountRegex='';
var amountErrorMessage='';
var intRegex='';
var intErrorMessage='';
messageResource.init({	  
filePath : '/PMS/resources/app_srv/PMS/global/js'
});

messageResource.load('regexValidationforjs', function(){
	  // get value corresponding  to a key from regexValidationforjs.properties  
	nameRegex= messageResource.get('name.regex', 'regexValidationforjs');
	nameErrorMessage = messageResource.get('name.regex.message', 'regexValidationforjs');
	amountRegex = messageResource.get('amount.regex', 'regexValidationforjs'); 
	amountErrorMessage = messageResource.get('amount.regex.message', 'regexValidationforjs');
	intRegex = messageResource.get('integer.regex', 'regexValidationforjs');
	intErrorMessage = messageResource.get('integer.regex.message', 'regexValidationforjs');
	});





$(document).ready(function() {
	
	
		var table = $('#datatable').DataTable();
		table.destroy();	
		
		$("#dtStartDate").datepicker({ 
			dateFormat: 'dd/mm/yy', 
	    changeMonth: true,
	    changeYear:true,           
	    maxDate: '+5y',
	    onSelect: function(date){
	    	$('#dtStartDate').trigger('input');
	    	var dateData = date.split('/');
	    	var monthval = parseInt(dateData[1])-1;
	        var selectedDate = new Date(dateData[2],monthval,dateData[0]);       

	        $("#dtEndDate").datepicker( "option", "minDate", selectedDate ); 
	    }
	});
		$("#additionalStartDate").datepicker({ 
			dateFormat: 'dd/mm/yy', 
	    changeMonth: true,
	    changeYear:true,           
	    maxDate: '+5y',
	    onSelect: function(date){
	    	$('#additionalStartDate').trigger('input');
	    	var dateData = date.split('/');
	    	var monthval = parseInt(dateData[1])-1;
	        var selectedDate = new Date(dateData[2],monthval,dateData[0]);       

	       //Set Minimum Date of EndDatePicker After Selected Date of StartDatePicker
	        $("#additionalEndDate").datepicker( "option", "minDate", selectedDate );       

	    }
	});

	$("#dtEndDate").datepicker({ 
		dateFormat: 'dd/mm/yy', 
	    changeMonth: true,
	    changeYear:true,
	    onSelect: function(date){
	    	$('#dtEndDate').trigger('input');
	    }
	});
	$("#additionalEndDate").datepicker({ 
		dateFormat: 'dd/mm/yy', 
	    changeMonth: true,
	    changeYear:true,
	    onSelect: function(date){
	    	$('#additionalEndDate').trigger('input');
	    }
	});
		
		
	});


$('.select2Option').select2({
	 width: '100%'
});


	$(document).on('click','#edit',function(e){
		//$('#frm').removeClass('hidden');
		$('#wholeForm').removeClass('hidden');
		$.scrollTo($('#wholeForm'), 1);
		$('#wholeForm')[0].scrollIntoView(true);
		var role=$('#userRole').val();
		var resultArray = $(this).closest('tr').find('td').map( function()
				{
				return $(this).text();
				}).get();
		console.log(resultArray);
		if(role!=3 && role!=5 && role!=2 && role!=4){
		$('#numId').val(resultArray[0].trim());
		//$('#numEmpId').val(resultArray[9].trim());
		$('#numRoleId').val(resultArray[10].trim()).trigger('change');
		$('#numOrganisationId').val(resultArray[11].trim()).trigger('change');
		$('#numGroupId').val(resultArray[12].trim()).trigger('change');
		$('#dtStartDate').val(resultArray[6].trim()).trigger('input');
		$('#dtEndDate').val(resultArray[7].trim());
		$('#numProjectId').val(resultArray[13].trim()).trigger('change');
		$('#numManReqId').val(resultArray[14].trim()).trigger('change');
		$('#involvement').val(resultArray[15].trim()).trigger('input')
		}
		else{
			$('#numId').val(resultArray[0].trim());
			//$('#numEmpId').val(resultArray[7].trim());
			$('#numRoleId').val(resultArray[10].trim()).trigger('change');
			$('#numOrganisationId').val(resultArray[9].trim()).trigger('change');
			$('#numGroupId').val(resultArray[10].trim()).trigger('change');
			$('#numProjectId').val(resultArray[11].trim()).trigger('change');
			$('#numManReqId').val(resultArray[12].trim()).trigger('change');
			$('#involvement').val(resultArray[13].trim()).trigger('input');
			$('#dtStartDate').val(resultArray[4].trim()).trigger('input');
			$('#dtEndDate').val(resultArray[5].trim()).trigger('input');
		}
		//Added by devesh on 06-09-23 to add validation on End Date of resourses mapped to project.
		var date1= $('#dtStartDate').val();
		var dateData1 = date1.split('/');
    	var monthval1 = parseInt(dateData1[1])-1;
        var selectedDate1 = new Date(dateData1[2],monthval1,dateData1[0])
        $("#dtEndDate").datepicker( "option", "minDate", selectedDate1);
        //End of Validation
		$('#save').text('Update');
	});


$(document).ready(function(){
	function showcheckbox(){
	if($('#numProjectId').val()!=0 && $('#numRoleId').val()!=0 && $('#numEmpId').val()!=0 && $('#involvement').val()!='' && $('#dtStartDate').val()!=''){
		$('#additionalRequirement').removeClass('hidden');	
	}else{
		$('#additionalRequirement').addClass('hidden');
	}
	
	}
setInterval(showcheckbox, 5);
	
	
	
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
	    	  strStartDate:{
	        	  validators: {
	                    notEmpty: {
	                        message: 'Start Date is required and cannot be left empty'
	                    }
	                }
	          },
	          numEmpId:{
	        	  group: '.col-md-12',
	        	  validators: {
	                  callback: {
	                      message: 'Please choose Employee Name',
	                      callback: function(value, validator) {
	                          // Get the selected options
	                          var options = validator.getFieldElements('numEmpId').val();
	                          return (options != 0);
	                      }
	                  }
	              }
	          },
	          numRoleId:{
	        	  group: '.col-md-12',
	        	  validators: {
	                  callback: {
	                      message: 'Please choose Role Name',
	                      callback: function(value, validator) {
	                          // Get the selected options
	                          var options = validator.getFieldElements('numRoleId').val();
	                          return (options != 0);
	                      }
	                  }
	              }
	          },
	    	  /*numOrganisationId:{
	        	  group: '.col-md-12',
	        	  validators: {
	                  callback: {
	                      message: 'Please choose Organisation Name',
	                      callback: function(value, validator) {
	                          // Get the selected options
	                          var options = validator.getFieldElements('numOrganisationId').val();
	                          return (options != 0);
	                      }
	                  }
	              }
	          },*/ /*numGroupId:{
	        	  group: '.col-md-12',
	        	  validators: {
	                  callback: {
	                      message: 'Please choose Group Name',
	                      callback: function(value, validator) {
	                          // Get the selected options
	                          var options = validator.getFieldElements('numGroupId').val();
	                          return (options != 0);
	                      }
	                  }
	              }
	          },*/
	          numProjectId:{
	        	  group: '.col-md-12',
	        	  validators: {
	                  callback: {
	                      message: 'Please choose Project Name',
	                      callback: function(value, validator) {
	                          // Get the selected options
	                          var options = validator.getFieldElements('numProjectId').val();
	                          return (options != 0);
	                      }
	                  }
	              }
	          },
	          numManReqId:{
	        	
	        	  validators: {
	                  callback: {
	                      message: 'Please choose Requirement',
	                      callback: function(value, validator) {
	                          // Get the selected options
	                          var options = validator.getFieldElements('numManReqId').val();
	                          return (options != 0);
	                      }
	                  }
	              }
	          },
	          
	          involvement: {	             
	              validators: {
	                  notEmpty: {
	                      message: 'Involvement is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 5,
	                        message: 'Involvement must be less than 6 digits'
	                    },
	                  regexp: {
	                        regexp: intRegex,
	                       message: intErrorMessage
	                    }
	              }
	          }         
	      }
	  });

	
	$('#saveAdditionalRequirement').click(function(){
		var involvement = $('#Additionalinvolvement').val();
		if($('#projectRole').val()==0){
			sweetSuccess('Attention','Please select Project Role.');
			return false;
		}
		else if($('#projectDesignations').val()==0){
			sweetSuccess('Attention','Please select designation');
			return false;
		}
		else if($('#deputedVal').val()==3){
			sweetSuccess('Attention','Please select deputed at');
			return false;
		}
		else if(!involvement){
			sweetSuccess('Attention','Please enter involvement for requirement');
			return false;
		}
		else if($('#additionalStartDate').val()==null||$('#additionalStartDate').val()==''){
			sweetSuccess('Attention','Please enter start date');
			return false;
		}
		else if($('#additionalEndDate').val()==null||$('#additionalEndDate').val()==''){
			sweetSuccess('Attention','Please enter end date');
			return false;
		}
		else if(!$.isNumeric(involvement)){
				sweetSuccess('Attention','Only Numeric value is allowed in Involvement');
				return false;
		}
		else if(parseFloat(involvement)> 100){
					sweetSuccess('Attention','involvement cannot be more than 100%');
					return false;
		}
		else if(involvement<=0){
			sweetSuccess('Attention','involvement must be more than 0');
			return false;
		}
		else if ($('#ratePerManMonth').val()==null||$('#ratePerManMonth').val()==''){
			sweetSuccess('Attention','Please enter Rate Per Man');
			return false;
		}else{
			var endDate=$('#dtEndDate').val();
			//alert(endDate);
			if(!endDate){				
				endDate=$('#projectEndDate').val();				
			}
			//alert(endDate);
			$.ajaxRequest.ajax({
		        type: "POST",
		        url: "/PMS/mst/saveAdditionalManpowerRequirement",
		        data: {"count":1,
		        	"projectId":$('#numProjectId').val(),
		        	"startDate":$('#additionalStartDate').val(),
		        	"endDate":$('#additionalEndDate').val(),
		    		"deputedAt":$('#deputedVal').val(),
		    		"strDescription":$('#strDescription').val(),
		    		"numProjectRoles":$('#projectRole').val(),
		    		"designationId":$('#projectDesignations').val(),
		    		"actualRatePerManMonth":$('#ratePerManMonth').val(),
		    		"requiredType":1,
		    		"valid":"true",
		    		"involvement":$('#Additionalinvolvement').val()
		        	} ,			
				success : function(data) {
					if(data>0){
						 swal({
						      title: "Additional requirement created successfuly!",
						      icon: "success",
						     
						      dangerMode: true,
						    }).then(function(isConfirm) {
						      if (isConfirm) {
						    	  //sweetSuccess('Success','Additional Requirement added successfully');	
									$('#closeModal').click();
									var projectId=$('#numProjectId').val();
									$('#numProjectId').change();
									$('#numManReqId').val(data).trigger('change'); 
							      }
					  	
							      else{
							    	
							      return false;
					    }
				    });
						
					}
					
				}
			});
		}
		
	});
	
	$('#resetAdditionalRequirement').click(function(){
		$('#projectRole').val(0).trigger('change');;
		$('#projectDesignations').val(0).trigger('change');;
		$('#deputedVal').val(3).trigger('change');;
		$('#strDescription').val('');	
		$('#additionalEndDate').val('');
		$('#additionalEndDate').trigger('input');
		$('#additionalStartDate').val('');
		$('#additionalStartDate').trigger('input');
		$('#Additionalinvolvement').val(100);
		$('#ratePerManMonth').val(0);
	});
	/*$('#numManReqId').click(function(){
		if($("#numManReqId").val()=='addRequirement'){
			 
			 $("#Additionalinvolvement").val(100);
			 $('#modalAnchor').click();
		 }
	});*/
	
	$('#save').click(function(){	 	
			var bootstrapValidator = $("#form1").data('bootstrapValidator');
			var numManReqId = $('#numManReqId').val();
			var flag= true;
			
			var requirement = $( "#numManReqId option:selected" ).text();
			if(numManReqId !='addRequirement'){
				requirement=requirement.split("@");
				var requirementt=requirement[1].split("%");
				requirementt=parseInt(requirementt[0]);
			}
			if(numManReqId && numManReqId !=0){
				var involvement = $('#involvement').val();
				if(involvement){
					if(!$.isNumeric(involvement)){
						flag = false;
						sweetSuccess('Attention','Only Numeric value is allowed in Involvement');
						return false;
					}else{
						involvement = parseFloat(involvement);
						if(involvement> 100){
							flag = false;
							sweetSuccess('Attention','involvement cannot be more than 100%');
							return false;
						}else{
							
							if(involvement<=0){
								flag = false;
								sweetSuccess('Attention','involvement must be more than 0');
								return false;
							}
						}
					}
				}
				/*if(requirementt<involvement){
					flag = false;
					sweetSuccess('Attention','involvement cannot be more than '+requirementt+'');
					return false;	
				}*/
				if(numManReqId=='addRequirement'){
					flag = false;
					sweetSuccess('Attention','Please select requirement mapping.');
					return false;
				}
				if($("#joinDate").val() && $("#dtStartDate").val()){
					var JoinDate=$("#joinDate").val().split("/");
					var j= new Date(JoinDate[2],JoinDate[1],JoinDate[0]);
					var StartDate=$("#dtStartDate").val().split("/");
					var s= new Date(StartDate[2],StartDate[1],StartDate[0]);
					if(j>s){
						flag = false;
						sweetSuccess('Attention','Start date cannot be before'+$("#joinDate").val()+'');
						return false;	
					}
				}
				
			}
			if(!($('#numEmpId').val())){
				$('#numEmpId').val(0).select2({
					width:'100%'
				});
				flag = false;
				sweetSuccess('Attention','Please select Employee');
				return false;
				
			}
			
			bootstrapValidator.validate();
			if(bootstrapValidator.isValid() && flag == true){				
					document.getElementById("save").disabled = true;					
					 $( "#form1")[0].submit();
					return true;
				
			}else{
				console.log('Not Validated');				 
			}
		   	 
		 
		
		});
	
		
});

function resetForm(){
	
	$('#save').text('Save');
	$('#numEmpId').val(0).trigger('change');
	
	$('#numProjectId').val(0).select2({
		 width: '100%'
	});
	
	$('#numRoleId').val(0).select2({
		 width: '100%'
	});
	$('#numManReqId').val(0).select2({
		 width: '100%'
	});
	
	$('#involvement').val(0);
	
}


function loadActualCost(){

	var designationId= $('#projectDesignations').val();
	var categoryId = $('#categoryId').val();
	
	if(designationId == 0 || !categoryId){
		$('#actualCost').text(0);
		$('#actualRatePerManMonth').val(0);
	}else{
		
		$.ajax({
			 type: "POST",
			 url: "/PMS/categoryDesignationCost",
			 data: {"projectCategoryId":categoryId,
				 "designationId":designationId
			 },
			 success: function(data) { 	
				 if(data.cost){
					 $('#actualCost').text(data.cost);
						$('#actualRatePerManMonth').val(data.cost);
				 }
				 else{
					 $('#actualCost').text(0); 
				 }
			 }
		
		})
	}
}

$('#projectDesignations').change(function(){
	loadActualCost();
});

$('#categoryId').change(function(){	
	loadActualCost();
});
$(document).on('change','#numEmpId',function(e){
	var numEmpId = $('#numEmpId').val();
	$('#wholeForm').addClass('hidden');
	var tableData = "";
	if(numEmpId == 0){
		//$('#applicationDiv').addClass('hidden');
		if(role==5||role==3 || role==2 ||role==4){
			$('#groupDiv').addClass('hidden');
		}
		$('#organisationDiv').addClass('hidden');
		//$('#frm').addClass('hidden');
		$('#datatable').addClass('hidden');
		$('#stagedetails').addClass('hidden');		
		$('#datatable tbody').empty();
	}else{
	var role = $('#roleId').val();
		$.ajaxRequest.ajax({
	        type: "POST",
	        url: "/PMS/mst/getEmployeeRoleMasterByEmpId",
	        data: {"numEmpId":numEmpId} ,			
			success : function(data) {	
				for(var i =0;i<data.length;i++){
					var row = data[i];
					console.log(row);
					
					tableData+="<tr><td class='hidden'>"+row.numId+"</td>" ;
					tableData+=	"<td class='hidden'>"+row.strEmpName+"</td> <td>"+row.strRoleName+"</td>";
					
					  if(row.strProjectName && row.strProjectName != 'null'){
							tableData+=	"<td>"+row.strProjectName+"</td>";	

				     }
				     else{
							tableData+="<td></td> ";

				     }
					if(role!=3 && role!=5 && role!=2 && role!=4){
					if(row.strGroupName && row.strGroupName != 'null'){
						tableData+=	"<td>"+row.strGroupName+"</td>";	
					}
					else{
						tableData+="<td></td> ";

					}
					}
					
					if(role!=3 && role!=5 && role!=2 && role!=4){
					if(row.strOrganisationName && row.strOrganisationName !='null'){
						tableData+=	"<td>"+row.strOrganisationName+"</td>";
					}else{
						tableData+=	"<td></td>";
					}
					}
					
					tableData+="<td>"+row.strStartDate+"</td> ";
					if(row.strEndDate){
						tableData+="<td>"+row.strEndDate+"</td> ";
					}else{
						tableData+="<td></td> ";
					}
					tableData+=	"<td>"+row.strManReqDetails+"</td>";	
					tableData+=	"<td class='hidden'>"+row.numEmpId+"</td><td class='hidden'>"+row.numRoleId+"</td> <td class='hidden'>"+row.numOrganisationId+"</td> <td class='hidden'>"+row.numGroupId+"</td>";
					tableData+=	"<td class='hidden'>"+row.numProjectId+"</td><td class='hidden'>"+row.numManReqId+"</td><td class='text-right'>"+row.involvement+"</td><td><i class='fa fa-pencil-square-o btn btn-primary a-btn-slide-text' id='edit' /></td> </tr>"
							
				};
				tableData+='</tbody>';
				$('#datatable tbody').empty();
				$('#datatable').append(tableData);
				
			}
		
		});
		if(numEmpId!=0){
			$.ajaxRequest.ajax({
		        type: "POST",
		        url: "/PMS/mst/EmployeeData",
		        data: {"searchEmployeeId":numEmpId} ,			
				success : function(data) {
					$('#joinDate').val(data.dateOfJoining);
					$('#ProjectGrp').val(data.numGroupId);
					$('#ProjectOrg').val(data.organisationId);
					$("#numGroupId").val(data.groupId);
					$("#numGroupId").trigger('change');
					$("#numOrganisationId").val(data.organisationId);			
					
					$("#projectDesignations option").each(function(){						
					      if($(this).text().trim() == data.strDesignation){					    	
					    	  $('#projectDesignations').select2('destroy');
					    	  $('#projectDesignations').val($(this).val()).select2({
					    	  	 width: '100%'
					    	  });
					    	  
					    	  $('#projectDesignations').trigger('change');
					    	  return;
					      }
					});
					
				}
				});
		}
		
		
		
		$('#datatable').removeClass('hidden');
		$('#stagedetails').removeClass('hidden');
		
	}
});
				




				$(document).on('change','#numRoleId',function(e){	
					var numRoleId = $('#numRoleId').val();
					
					if(numRoleId == 0){
						//$('#applicationDiv').addClass('hidden');
						if($('#userRole').val()==5||$('#userRole').val()==3){
						$('#groupDiv').addClass('hidden');
						}
						$('#organisationDiv').addClass('hidden');
						$('#frm').addClass('hidden');
						/*$('#datatable').addClass('hidden');
						$('#stagedetails').addClass('hidden');*/		
					
					}else{
				$.ajaxRequest.ajax({
			        type: "POST",
			        url: "/PMS/mst/getRoleMasterById",
			        data: {"numRoleId":numRoleId} ,			
					success : function(data) {	
				
				
			var accessLevel=data.accessLevel;
			
				if(accessLevel=='All'){
					
					//$('#applicationDiv').addClass('hidden');
					$('#manpowerDiv').addClass('hidden');
					$('#involvementDiv').addClass('hidden');
					$('#groupDiv').addClass('hidden');
					$('#organisationDiv').addClass('hidden');
					$('#frm').removeClass('hidden');
					$('#form1').bootstrapValidator('enableFieldValidators', 'numGroupId', false);
					$('#form1').bootstrapValidator('enableFieldValidators', 'numProjectId', false);
					$('#form1').bootstrapValidator('enableFieldValidators', 'numManReqId', false);
					$('#form1').bootstrapValidator('enableFieldValidators', 'involvement', false);
					/*$('#datatable').removeClass('hidden');
					$('#stagedetails').removeClass('hidden');*/
				} else if(accessLevel=='Application'){
					$('#applicationDiv').removeClass('hidden');
					if($('#userRole').val()!=3 && $('#userRole').val()!=5){
					//$('#groupDiv').removeClass('hidden');
					//$('#organisationDiv').removeClass('hidden');
					}
					$('#frm').removeClass('hidden');
				}else if(accessLevel=='Organisation'){
					//$('#applicationDiv').addClass('hidden');
					$('#manpowerDiv').addClass('hidden');
					$('#involvementDiv').addClass('hidden');
					$('#frm').removeClass('hidden');
					$('#form1').bootstrapValidator('enableFieldValidators', 'numGroupId', false);
					$('#form1').bootstrapValidator('enableFieldValidators', 'numProjectId', false);
					$('#form1').bootstrapValidator('enableFieldValidators', 'numManReqId', false);
					$('#form1').bootstrapValidator('enableFieldValidators', 'involvement', false);
					/*$('#datatable').removeClass('hidden');
					$('#stagedetails').removeClass('hidden');*/
					
				}
				
				if(accessLevel=='Group'){
					
				//	$('#applicationDiv').addClass('hidden');
					$('#manpowerDiv').addClass('hidden');
					$('#involvementDiv').addClass('hidden');
					$('#frm').removeClass('hidden');
					$('#form1').bootstrapValidator('enableFieldValidators', 'numGroupId', true);
					$('#form1').bootstrapValidator('enableFieldValidators', 'numProjectId', false);
					$('#form1').bootstrapValidator('enableFieldValidators', 'numManReqId', false);
					$('#form1').bootstrapValidator('enableFieldValidators', 'involvement', false);
					/*$('#datatable').removeClass('hidden');
					$('#stagedetails').removeClass('hidden');*/
				}
				
				
			},				
			error : function(e) {
				/*alert('Error: ' + e);*/
				
			}
		});
		
	}
	
				});	



$(document).on('change','#numOrganisationId',function(e){	
	var organisationId = $('#numOrganisationId').val();
	if(organisationId=='' || organisationId==null){
		organisationId=0;
	}
	if(organisationId == 0){
		$('#numGroupId').val(0);
	}else{
		$.ajaxRequest.ajax({
	        type: "POST",
	        url: "/PMS/mst/getGroupByOrganisationId",
	        async:false,
	        data: {"organisationId":organisationId} ,			
			success : function(data) {
				var options= '<option value="0"> -- Select Group -- </option>';
				for (var i=0;i<data.length;i++){
					var rowData = data[i];
					options += '<option value="'+rowData.numId+'">'+rowData.groupName+'</option>';	
					}
				
			   $('#numGroupId').empty();
				$('#numGroupId').append(options);
			}
			

		});
		}	
	
	
	});


$(document).on('change','#numGroupId',function(e){	
	var groupId = $('#numGroupId').val();
	$.ajaxRequest.ajax({
        type: "POST",
        url: "/PMS/mst/getProjectNameByGroupId",
        async:false,
        data: {"numGroupId":groupId} ,			
		success : function(data) {
			var options= '<option class="font_16" value="0"> -- Select Project -- </option>';
			for (var i=0;i<data.length;i++){
				var rowData = data[i];
				options += '<option class="font_16" value="'+rowData.numId+'">'+rowData.strProjectName+'</option>';	
				}
			
		    $('#numProjectId').empty();
			$('#numProjectId').append(options);
			}
			
		});				

	});
	

$(document).on('change','#numProjectId',function(e){
	
	var projectId = $('#numProjectId').val();
	var projectName=$( "#numProjectId option:selected" ).text();
	loadProjectCategoryId(projectId);
	$('#projectName').html(projectName);
	var userRole=$('#userRole').val();
	$.ajaxRequest.ajax({
        type: "POST",
        url: "/PMS/mst/getManpowerRequiermentDetails",
        async:false,
        data: {"numProjectId":projectId},			
		success : function(data) {
			var optionExist = false;
			var inputData = JSON.parse(data);
			var options= '<option class="font_16" value="0"> -- Select -- </option>';
			
			jQuery(inputData).each(function(j, manpowerDetails){

					if(manpowerDetails.reqType!=1){
						options+='<option class="font_16" data-hovertext="hi" value='+manpowerDetails.reqNumId+'> '+manpowerDetails.reqDetails+'</option>';
						optionExist = true;
					}else{
						options+='<option class="red font_16"  value='+manpowerDetails.reqNumId+'> '+manpowerDetails.reqDetails+'</option>';
						optionExist = true;
					}

					
				});
			if (userRole==5 && $('#numProjectId').val()!=0){
			options+='<option class="blue font_16"   value="addRequirement">Create Additional Requirement</option>';
			}
			$('#numManReqId').empty();
			$('#numManReqId').append(options);
			$('#manpowerDiv').removeClass('hidden');
			$('#involvementDiv').removeClass('hidden');
			$('#form1').bootstrapValidator('enableFieldValidators', 'numManReqId', true);
			$('#form1').bootstrapValidator('enableFieldValidators', 'involvement', true);
			
			}
			
		});
	if(projectId!=0){
	$.ajaxRequest.ajax({
        type: "POST",
        url: "/PMS/mst/getProjectDetails",
        async:false,
        data: {"numProjectId":projectId},			
		success : function(data) {
			if(data!=null){
				//alert(data.startDate);
				$('#projectEndDate').val(data.endDate);
				$('#additionalEndDate').val(data.endDate);
				$('#additionalEndDate').trigger('input');
				$('#additionalStartDate').val(data.startDate);
				$('#additionalStartDate').trigger('input');
				var dateData = data.startDate.split('/');
		    	var monthval = parseInt(dateData[1])-1;
		        var selectedDate = new Date(dateData[2],monthval,dateData[0]);       
		        var dateDatae = data.endDate.split('/');
		    	var monthvale = parseInt(dateDatae[1])-1;
		        var selectedDatee = new Date(dateDatae[2],monthvale,dateDatae[0]);
		       //Set Minimum Date of EndDatePicker After Selected Date of StartDatePicker
		        $("#dtEndDate").datepicker( "option", "minDate", selectedDate);
		        $("#additionalEndDate").datepicker( "option", "minDate", selectedDate);
		        $("#dtEndDate").datepicker( "option", "maxDate", selectedDatee);
		        $("#additionalEndDate").datepicker( "option", "maxDate", selectedDatee);
		        $("#dtStartDate").datepicker( "option", "minDate", selectedDate);
		        $("#dtStartDate").datepicker( "option", "maxDate", selectedDatee);
		        $("#additionalStartDate").datepicker( "option", "minDate", selectedDate);
		        $("#additionalStartDate").datepicker( "option", "maxDate", selectedDatee);
			}
			}
			
		});
	}
	

	});

$(document).ready(function() {
	/*$('#numManReqId option').each(function(){
	    title.push($(this).attr('title'));
	});*/
	var role=$("#userRole").val();
	$(document).on('click','#showForm',function(e){
		
		
		$('#wholeForm').removeClass('hidden');
		$.scrollTo($('#wholeForm'), 1);
		$('#wholeForm')[0].scrollIntoView(true);
		//resetForm();
		//$('#form1').data('bootstrapValidator').resetForm(true);
		
	});
	if(role ==5||role==3){
	$("#numGroupId").prop("selectedIndex", 1);
	$("#numGroupId").trigger('change');
	}
	
	$("#numOrganisationId").prop("selectedIndex", 0);
	$("#numOrganisationId").trigger('change');
	
	var role=$("#userRole").val();
	if(role!=5 && role!=3 && role!=2 && role!=4){
		//$('#applicationDiv').removeClass('hidden');	
	//	$('#groupDiv').removeClass('hidden');
	}
	
});
$(document).on('change','#numManReqId',function(e){	
	var e = document.getElementById("numManReqId");
	//alert(inputData);
	var projectId = $('#numProjectId').val();
	
	 if($("#numManReqId").val()=='addRequirement'){
		 
		 $("#Additionalinvolvement").val(100);
		 $('#modalAnchor').click();
	 }
	 else {var reqText = e.options[e.selectedIndex].text;
	  $("#strManReqDetails").val(reqText);
	 }
		$.ajaxRequest.ajax({
	        type: "POST",
	        url: "/PMS/mst/getManpowerRequiermentDetails",
	        async:false,
	        data: {"numProjectId":projectId},			
			success : function(data) {
				//alert('test');
				var inputData = JSON.parse(data);
				jQuery(inputData).each(function(j, manpowerDetails){
					if (manpowerDetails.reqNumId==$("#numManReqId").val()){
						$('#involvement').val(manpowerDetails.reqInvolvement);
						var start=manpowerDetails.reqStartDate.split("-");
						var startDate=start[2]+'/'+start[1]+'/'+start[0];
						$('#dtStartDate').val(startDate);
						$('#dtStartDate').trigger('input');
						var end=manpowerDetails.reqEndDate.split("-");
						var endDate=end[2]+'/'+end[1]+'/'+end[0];
						$('#dtEndDate').val(endDate);
						$('#dtEndDate').trigger('input');
						}
				});
			}
		});
					
});


$(function(){
    $(".select2").select2({
    	width:"100%",
    	dropdownCssClass: "myFont" ,
    	placeholder: "Select Employee",
        allowClear: true,
        /*matcher: matchCustom,
*/        formatResult : formatCustom
		
    });
})

function stringMatch(term, candidate) {
    return candidate && candidate.toLowerCase().indexOf(term.toLowerCase()) >= 0;
}

function matchCustom(params, data) {
    // If there are no search terms, return all of the data
    if ($.trim(params.term) === '') {
        return data;
    }
    // Do not display the item if there is no 'text' property
    if (typeof data.text === 'undefined') {
        return null;
    }
    // Match text of option
    if (stringMatch(params.term, data.text)) {
        return data;
    }
    // Match attribute "data-foo" of option
    if (stringMatch(params.term, $(data.element).attr('data-foo'))) {
        return data;
    }
    // Return `null` if the term should not be displayed
    return null;
}

function formatCustom(state) {
 
	 return $(
		        '<div><div><span style="font-size:18px;"><strong> <i class="fa fa-arrow-circle-right pad-right-half"></i>' + state.text + '</strong></span></div><div class="foo pad-left-25" style="color:#808080"> <strong>'
		            + $(state.element).attr('data-foo')
		            + '</strong></div></div>');
}

function loadProjectCategoryId(numProjectId){
	$.ajaxRequest.ajax({
        type: "POST",
        url: "/PMS/mst/projectCategory",
        data:{"numProjectId":numProjectId},
        async:false,       		
		success : function(data) {
			$('#categoryId').val(data).trigger('change');
		}
		});
}