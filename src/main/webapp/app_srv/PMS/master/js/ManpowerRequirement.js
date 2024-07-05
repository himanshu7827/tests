var numericRegex='';
var numericErrorMessage='';
var amountRegex ='';
var amountRegexMessage='';
messageResource.init({	  
filePath : '/PMS/resources/app_srv/PMS/global/js'
});

messageResource.load('regexValidationforjs', function(){	  
	numericRegex= messageResource.get('numeric.regex', 'regexValidationforjs');
	numericErrorMessage = messageResource.get('numeric.regex.message', 'regexValidationforjs');
	amountRegex= messageResource.get('amount.regex', 'regexValidationforjs');
	amountRegexMessage = messageResource.get('amount.regex.message', 'regexValidationforjs');
});

$(document).ready(function() {
	$('.select2Option').select2({width:"element"});
		$('#example').DataTable();
		 $('#userinfo-message').delay(5000).fadeOut();
		 
		 var table = $('#datatable').DataTable();
			table.destroy();
			manpowerDetails($("#projectid").val());
			
		 $(document).on('change','#projectId',function(e){
				var projectId = $(this).find(":selected").val();
				manpowerDetails(projectId);
			}); 
			 
		 
			 	  });

function  manpowerDetails(projectId){	
	var tableData = "";
	
	var role = $('#roleId').val();
	$.ajaxRequest.ajax({
				 type: "POST",
				 url: "/PMS/mst/ManpowerData",
				 data: "projectId="+ projectId,
				 success: function(data) {
				 console.log("Hiiiii");
				    console.log(role);
					 console.log(data);
						for(var i =0;i<data.length;i++){
							var row = data[i];
							var recordStatus= '';
							var deputedAt='';
							var strRolesName='';
							if(row.valid==true){
								recordStatus='Active';
									}else{recordStatus='Inactive';
										}
							deputedAt=row.deputedAt;
							strRolesName=row.strRolesName;
							if(deputedAt==null){
								deputedAt='';
							}
							if(deputedAt==0){
								deputedAt='CDAC Noida';
							}
							if(deputedAt==1){
								deputedAt='Client Site';
							}
							if(strRolesName==null){
								strRolesName='';
							}
							
							required=row.requiredType;
							if(!required){
								required='';
							}
							if(required==0){
								required=' ';
							}
							if(required==1){
								required='Additional Requirement';
							}
							if(strRolesName==null){
								strRolesName='';
							}
							/*Added by devesh on 27-12-23 to display blank when purpose is null*/
							var purpose = row.purpose;
							if(purpose == null) purpose='';
							/*End of setting purpose*/
							
							/*alert(row.numProjectRoles);*/
							tableData+="<tr id=tr_"+row.numId+"><td><input type='checkbox' class='CheckBox' id='CheckBox' name='idCheck' value="+row.numId+" /></td>";
							tableData+="<td>"+row.designationName+"</td> <td>"+purpose+"</td> <td>"+row.ratePerManMonth+"</td>";
							tableData+="<td>"+row.count+"</td><td>"+row.involvement+"</td><td>"+row.startDate+"</td> ";
							/*tableData+="<td>"+row.endDate+"</td><td>"+row.strDescription+"</td><td>"+deputedAt+"</td><td>"+required+"</td> <td>"+recordStatus+"</td><td class='hidden'>"+row.numProjectRoles+"</td><td>"+strRolesName+"</td>";*/
							tableData+="<td>"+row.endDate+"</td><td class='hidden'>"+row.strDescription+"</td><td>"+deputedAt+"</td><td>"+required+"</td> <td>"+recordStatus+"</td><td class='hidden'>"+row.numProjectRoles+"</td><td>"+strRolesName+"</td>";
							/*if(role==5){
							tableData+="<td><i class='fa fa-pencil-square-o btn btn-primary a-btn-slide-text' id='edit' /><i class='fa fa-trash btn btn-primary a-btn-slide-text' id='delete' /></td>";
							}
							
							
							else if((role!=5) && (required==0)){
							tableData+="<td><i class='fa fa-pencil-square-o btn btn-primary a-btn-slide-text' id='edit' /><i class='fa fa-trash btn btn-primary a-btn-slide-text' id='delete' /></td>";
							}*/
							/*Added by devesh on 16-11-23 to edit Manpower Requirements Details*/
							if(role==5){
								tableData+="<td><i class='fa fa-pencil-square-o btn btn-primary a-btn-slide-text' id='editManpower' /></i><i class='fa fa-trash btn btn-primary a-btn-slide-text' id='delete' /></i></td>";
								}
						    else if((role!=5) && (required==0)){
								tableData+="<td><i class='fa fa-pencil-square-o btn btn-primary a-btn-slide-text' id='editManpower' /></i><i class='fa fa-trash btn btn-primary a-btn-slide-text' id='delete' /></i></td>";
							}
							/*End of Edit*/
							else 
							{
							tableData+="<td></td>";
							}
							
							tableData+="<td class='hidden'>"+row.projectId+"</td><td class='hidden'>"+row.designationId+"</td><td class='hidden'>"+row.deputedAt+"</td><td class='hidden'>"+row.numId+"</td> <td class='hidden'>"+row.actualRatePerManMonth+"</td></tr> ";									
						}
						tableData+='</tbody>';
						$('#datatable tbody').empty();						
						$('#datatable').append(tableData);		
						
					},				
					error : function(e) {
						//$('#RecurringManpowerAmt').val(0);
						alert('Error: ' + e);
				 	}
			 });
			 
			
		 	}
$(document).on('click','#delete',function(e){
	var resultArray = $(this).closest('tr').find('td').map( function()
			{
			return $(this).text();
			}).get();
	console.log(resultArray);
	
	swal({
		  title: "Are you sure?",
		  text: "Do you want to delete?",
      
		  buttons: [					               
	                'No',
	                'Yes'
	              ],
	              
	    }).then(function(isConfirm) {
	      if (isConfirm) {
	    	  deleteRequirement(resultArray[18].trim());	    
	      }
	    });
});

function deleteRequirement(numId){
	$.ajax({
		 type: "POST",
		 url: "/PMS/mst/deleteManpowerReq",
		 data: {"numId":numId},
		 success: function(data) { 	
			 if(data==1){
				 swal("Attention","Cannot Delete!Manpower is Mapped against selected Requirement");
			 }
			 else{
				 swal("success","Manpower is deleted successfully");
				 $('#tr_'+numId).remove();
 
			 }
		 }
	
	})
}
	$(document).on('click','#edit',function(e){
		
		 $('#form1').data('bootstrapValidator').resetForm(true);
		var resultArray = $(this).closest('tr').find('td').map( function()
				{
				return $(this).text();
				}).get();
		/*console.log(resultArray);*/
		
		$('#numId').val(resultArray[18].trim());
		$('#purpose').val(resultArray[2].trim());
		$('#ratePerManMonth').val(convertINRToAmount(resultArray[3].trim()));
		$('#count').val(resultArray[4].trim());
		$('#involvement').val(resultArray[5].trim());
		$('#startDate').val(resultArray[6].trim()).trigger('change');	
		$('#endDate').val(resultArray[7].trim()).trigger('change');	
		$('#projectId').val(resultArray[15].trim());	
		$('#designationId').val(resultArray[16].trim()).trigger('change');	
		$('#strDescription').val(resultArray[8].trim());
		$('#deputedAt').val(resultArray[17].trim()).trigger('change');	
		
		var require = $.trim(resultArray[10]);
					
					if(require == "Additional Requirement")
					{
					$('#required').attr("checked","checked");
					$('#requiredType').val("1");
					}
					else if(require == " "){
					$('#required').removeAttr("checked");
					$('#requiredType').val("0");
					}
		if(resultArray[11].trim()=='Active'){
			$('#toggle-off').removeAttr('checked');
			$('#toggle-on').attr('checked',true);

		}else{
			$('#toggle-on').removeAttr('checked');
			$('#toggle-off').attr('checked',true);
		}
		$('#numProjectRoles').val(resultArray[12].trim()).trigger('change');
		$('#save').text('Update');
	});
	
	


$(document).ready(function(){	
/*	$('#toggle-on').attr('checked',true);
	//$('input:radio[name="status"][value="true"]').prop('checked', true);
	$('#toggle-off').attr('checked',false);*/
	
	
		$('#toggle-off').removeAttr('checked');
		$('#toggle-on').attr('checked',true);

	$('#reset').click(function(){
		resetForm();
		 $('#form1').data('bootstrapValidator').resetForm(true);

	});
	
	$('#form1').bootstrapValidator({
//	      live: 'disabled',
		//excluded: ':disabled',
	      message: 'This value is not valid',
	      feedbackIcons: {
	          valid: 'glyphicon glyphicon-ok',
	          invalid: 'glyphicon glyphicon-remove',
	          validating: 'glyphicon glyphicon-refresh'
	      },
	      fields: {
	    	  startDate: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Start Date is required and cannot be empty'
	                  }                 
	              }
	          },
	          endDate: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'End Date is required and cannot be empty'
	                  }	                 
	              }
	          },
	          
	          count: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Count is required and cannot be empty'
	                  },
	                  regexp: {
	                        regexp: numericRegex,
	                       message: numericErrorMessage,
	                    }	                  
	              }
	          },
	          involvement: {
	              group: '.col-md-12',
	              validators: {	            	  
	                  notEmpty: {
	                      message: 'Involvement is required and cannot be empty'
	                  },
	                  regexp: {
	                        regexp: numericRegex,
	                       message: numericErrorMessage,
	                  },
	                  between: {
	                        min: 1,
	                        max: 100,
	                        message: 'Value Between 1 to 100 is allowed'
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
	          strDescription: {
	              group: '.col-md-12',
	              validators: {	                  
	                  stringLength: {
	                        max: 2000,
	                        message: 'The Description must be less than 2000 characters'
	                    }	                   
	              }
	          },
	          designationId:{
	        	  group: '.col-md-12',
	        	  validators: {
	                  callback: {
	                      message: 'Please choose Role Name',
	                      callback: function(value, validator) {	                         
	                          var options = validator.getFieldElements('designationId').val();
	                          return (options != 0);
	                      }
	                  }
	        	  }
	          },
	          purpose:{
	        	  validators: {	            	  
	                  notEmpty: {
	                      message: 'Involvement is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 200,
	                        message: 'The Purpose must be less than or equals to 200 characters'
	                    }              
	              }
	        	  
	          },ratePerManMonth:{
	        	  validators: {	            	  
	                  notEmpty: {
	                      message: 'Involvement is required and cannot be empty'
	                  },
	                  regexp: {
	                        regexp: amountRegex,
	                       message: amountRegexMessage,
	                  }                 
	              }
	          }, numProjectRoles:{
	        	  group: '.col-md-12',
	        	  validators: {
	                  callback: {
	                      message: 'Please choose Project Based Role',
	                      callback: function(value, validator) {	                         
	                          var options = validator.getFieldElements('numProjectRoles').val();
	                          return (options != 0);
	                      }
	                  }
	        	  }
	          }
	          
		      
		         
	      }
	  }); 
	
	$('#save').click(function(){
	
		var istoggle="";
		var deputedAt= $("#deputedAt").val();
		var numProjectRoles= $("#numProjectRoles").val();
		if(deputedAt==3){
			sweetSuccess("Attention","Please select Deputed At");	
			return false;
		}
	if(numProjectRoles){
		 if(numProjectRoles==0){
			sweetSuccess("Attention","Please select Project Based Roles");	
			return false;
		}
		}else{
			sweetSuccess("Attention","Please select Project Based Roles");	
			return false;
		}
	   	 if($("#toggle-on").prop("checked")==true) {	   		 
	   		 istoggle=true;
	   		 }
	   	 else if($("#toggle-off").prop("checked")==true){
	   	  istoggle=false;
	   	 }
	   	 $('#valid').val(istoggle);
	   	 
	   	 
	   	  if($('input[name=required]').is(":checked") == true && $('#required').is(":checked") == true)
    		{
    			$('#requiredType').val(1);
    		}
    	else
    		{
    			$('#requiredType').val(0);
    		}
	   	
	 	var bootstrapValidator = $("#form1").data('bootstrapValidator');
		bootstrapValidator.validate();
	    if(bootstrapValidator.isValid()){
				document.getElementById("save").disabled = true; 
				$('#form1')[0].submit();
				return true;
			
		}else{
			console.log('Not Validated');			 
		}
		  
	});
		
});

function resetForm(){
	$('#numId').val(0);
	$('#designationId').val("0").trigger('change');
	/*$('#deputedAt').val("0").trigger('change');*/
	/*$('#deputedAt').val('0').trigger("liszt:updated");*/
	$("#deputedAt")[0].selectedIndex = 0;
	$('#required').removeAttr("checked").trigger('change');
	$('#requiredType').val(0);
	$('#actualCost').text(0);
	/*$('#projectId').val("0").trigger('change');
	$('#designationId').val("0").trigger('change');
	$('#deputedAt').val("0").trigger('change');
	$('#count').val(0);
	$('#involvement').val(0);
	$('#startDate').val('');
	$('#endDate').val('');
	$('#strDescription').val('');
	$('#actualCost').text(0);
	$('#actualRatePerManMonth').val('0');
	$('#required').removeAttr("checked").trigger('change');*/
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

$(document).ready(function(){
	
	$('#designationId').change(function(){
		var designationId= $('#designationId').val();
		var categoryId = $('#categoryId').val();
		
		if(designationId == 0){
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
					 console.log(data);
					 if(data.cost){
						 $('#actualCost').text(data.cost);
							$('#actualRatePerManMonth').val(data.cost);
					 }
				 }
			
			})
		}
	});
	
});
function redirectPage(path){
	var encProjectId = $('#encProjectId').val();
	//alert(projectId);
	openWindowWithPost('GET',path,{"encProjectId" : encProjectId},'_self');
}

$(document).ready(function(){
	var projectStartDate = $('#projectStartDate').text().trim();
	var projectEndDate = $('#projectEndDate').text().trim();
	
	var minDate;
	var maxDate;
	if(projectStartDate){
		minDate = new Date(projectStartDate);
	}
	if(projectEndDate){
		maxDate = new Date(projectEndDate);
	}
	   $('#startDate').datepicker({
		   dateFormat: 'dd/mm/yy', 
		    changeMonth: true,
		    changeYear:true,
		    minDate: minDate,
		    maxDate:maxDate,
		    onSelect: function(date){
		    	$('#startDate').trigger('input');
		    	var dateData = date.split('/');
		    	var monthval = parseInt(dateData[1])-1;
		        var selectedDate = new Date(dateData[2],monthval,dateData[0]);       

		       //Set Minimum Date of EndDatePicker After Selected Date of StartDatePicker
		        $("#endDate").datepicker( "option", "minDate", selectedDate );
		    }
	   });
	   
	   $('#endDate').datepicker({
		   dateFormat: 'dd/mm/yy', 
		    changeMonth: true,
		    changeYear:true, 
		    minDate: minDate,
		    maxDate:maxDate,
		    onSelect: function(date){
		    	$('#endDate').trigger('input');		    	
		    }
	   });
	   
});

/*Added by devesh on 15-11-23 to add multiple rows for Manpower Requirement*/
var totalRowInTable = 4;
var editmode=1;

function hideAddButton() {
    $('.addRowButton').addClass('hidden');
    $('.addRowButton:last').removeClass('hidden');
}

$('.statusColumn').addClass('hidden');


$(document).ready(function() {
	var startDate = $('#projectStartDate').text().trim();
	var endDate = $('#projectEndDate').text().trim();
	
	var minDate;
	var maxDate;
	if(projectStartDate){
		selectedDate = new Date(startDate);
	}
	if(projectEndDate){
		selectedEndDate = new Date(endDate);
	}
	
	if (startDate === "" || endDate === "") {

    }
	else{
		/*for (var i = 0; i < totalRowInTable; i++) {
	        var tableRow = $("<tr id=" + i + ">");
	        $("#startDate_" + i).datepicker({
	            dateFormat: 'dd/mm/yy',
	            changeMonth: true,
	            changeYear: true,
	            minDate: selectedDate,
	            maxDate: selectedEndDate,
	            onSelect: function(date){
			    	$('#startDate'+i).trigger('input');
			    	var dateData = date.split('/');
			    	var monthval = parseInt(dateData[1])-1;
			        var selectedDate = new Date(dateData[2],monthval,dateData[0]);       

			       //Set Minimum Date of EndDatePicker After Selected Date of StartDatePicker
			        $("#endDate"+i).datepicker( "option", "minDate", selectedDate );
			    }
	        });
	        $("#endDate_" + i).datepicker({
	            dateFormat: 'dd/mm/yy',
	            changeMonth: true,
	            changeYear: true,
	            minDate: selectedDate,
	            maxDate: selectedEndDate
	        });
	    }*/
		$("#startDate_" + 0).datepicker({
            dateFormat: 'dd/mm/yy',
            changeMonth: true,
            changeYear: true,
            minDate: selectedDate,
            maxDate: selectedEndDate,
            onSelect: function(date){
		    	$('#startDate_'+0).trigger('input');
		    	var dateData = date.split('/');
		    	var monthval = parseInt(dateData[1])-1;
		        var selectedDate = new Date(dateData[2],monthval,dateData[0]);       

		       //Set Minimum Date of EndDatePicker After Selected Date of StartDatePicker
		        $("#endDate_"+0).datepicker( "option", "minDate", selectedDate );
		        saveField(0);
		    }
        });
        $("#endDate_" + 0).datepicker({
            dateFormat: 'dd/mm/yy',
            changeMonth: true,
            changeYear: true,
            minDate: selectedDate,
            maxDate: selectedEndDate
        });
        $("#startDate_" + 1).datepicker({
            dateFormat: 'dd/mm/yy',
            changeMonth: true,
            changeYear: true,
            minDate: selectedDate,
            maxDate: selectedEndDate,
            onSelect: function(date){
		    	$('#startDate_'+1).trigger('input');
		    	var dateData = date.split('/');
		    	var monthval = parseInt(dateData[1])-1;
		        var selectedDate = new Date(dateData[2],monthval,dateData[0]);       

		       //Set Minimum Date of EndDatePicker After Selected Date of StartDatePicker
		        $("#endDate_"+1).datepicker( "option", "minDate", selectedDate );
		        saveField(1);
		    }
        });
        $("#endDate_" + 1).datepicker({
            dateFormat: 'dd/mm/yy',
            changeMonth: true,
            changeYear: true,
            minDate: selectedDate,
            maxDate: selectedEndDate
        });
        $("#startDate_" + 2).datepicker({
            dateFormat: 'dd/mm/yy',
            changeMonth: true,
            changeYear: true,
            minDate: selectedDate,
            maxDate: selectedEndDate,
            onSelect: function(date){
		    	$('#startDate_'+2).trigger('input');
		    	var dateData = date.split('/');
		    	var monthval = parseInt(dateData[1])-1;
		        var selectedDate = new Date(dateData[2],monthval,dateData[0]);       

		       //Set Minimum Date of EndDatePicker After Selected Date of StartDatePicker
		        $("#endDate_"+2).datepicker( "option", "minDate", selectedDate );
		        saveField(2);
		    }
        });
        $("#endDate_" + 2).datepicker({
            dateFormat: 'dd/mm/yy',
            changeMonth: true,
            changeYear: true,
            minDate: selectedDate,
            maxDate: selectedEndDate
        });
        $("#startDate_" + 3).datepicker({
            dateFormat: 'dd/mm/yy',
            changeMonth: true,
            changeYear: true,
            minDate: selectedDate,
            maxDate: selectedEndDate,
            onSelect: function(date){
		    	$('#startDate_'+3).trigger('input');
		    	var dateData = date.split('/');
		    	var monthval = parseInt(dateData[1])-1;
		        var selectedDate = new Date(dateData[2],monthval,dateData[0]);       

		       //Set Minimum Date of EndDatePicker After Selected Date of StartDatePicker
		        $("#endDate_"+3).datepicker( "option", "minDate", selectedDate );
		        saveField(3);
		    }
        });
        $("#endDate_" + 3).datepicker({
            dateFormat: 'dd/mm/yy',
            changeMonth: true,
            changeYear: true,
            minDate: selectedDate,
            maxDate: selectedEndDate
        });
	}	
	
	hideAddButton();
	
	$(document).on("click", '.startDate', function() {
		if (startDate === "" && endDate === "") {
	        // Display a warning message
			swal({
		        title: "Warning",
		        text: "Project Start Date and End Date are not available. Please set them before entering the Manpower Start Date of the Payment Schedule.",
		        icon: "warning",
		        button: "OK",
		    });
	    }
		else if (startDate === "" && endDate !== "") {
	        // Display a warning message
			swal({
		        title: "Warning",
		        text: "Project Start Date is not available. Please set it before entering the Manpower Start Date of the Payment Schedule.",
		        icon: "warning",
		        button: "OK",
		    });
	    }
		else if (startDate !== "" && endDate === "") {
	        // Display a warning message
			swal({
		        title: "Warning",
		        text: "Project End Date is not available. Please set it before entering the Manpower Start Date of the Payment Schedule.",
		        icon: "warning",
		        button: "OK",
		    });
	    }
	});
	
	$(document).on("click", '.endDate', function() {
		if (startDate === "" && endDate === "") {
	        // Display a warning message
			swal({
		        title: "Warning",
		        text: "Project Start Date and End Date are not available. Please set them before entering the Manpower End Date of the Payment Schedule.",
		        icon: "warning",
		        button: "OK",
		    });
	    }
		else if (startDate === "" && endDate !== "") {
	        // Display a warning message
			swal({
		        title: "Warning",
		        text: "Project Start Date is not available. Please set it before entering the Manpower End Date of the Payment Schedule.",
		        icon: "warning",
		        button: "OK",
		    });
	    }
		else if (startDate !== "" && endDate === "") {
	        // Display a warning message
			swal({
		        title: "Warning",
		        text: "Project End Date is not available. Please set it before entering the Manpower End Date of the Payment Schedule.",
		        icon: "warning",
		        button: "OK",
		    });
	    }
	});
	
	/*---------------- (+) Click the plus button ----------------*/
    $(document).on("click", '.addRowButton', function() {
    	
    	var validation=true;
    	for(var i=0;i<totalRowInTable;i++){
    		var ManpowerId = $.trim($("input[id='ManpowerId_" + (i) + "']").val());
    		var numProjectRoles = $("#numProjectRoles_" + (i)).val();
    		var designationId = $("#designationId_" + (i)).val();
    		var deputedAt = $("#deputedAt_" + (i)).val();
    		var purpose = $.trim($("input[id='purpose_" + (i) + "']").val());
    		var actualCost = $("#actualCost_" + (i)).val();
    	    var ratePerManMonth = $.trim($("input[id='ratePerManMonth_" + (i) + "']").val());
    	    var count = $.trim($("input[id='count_" + (i) + "']").val());
    	    var involvement = $.trim($("input[id='involvement_" + (i) + "']").val());
    	    var manpowerStartDate = $.trim($("input[id='startDate_" + (i) + "']").val());
    	    var manpowerEndDate = $.trim($("input[id='endDate_" + (i) + "']").val());
    	    var required = $.trim($("input[id='required_" + (i) + "']").val());
    	    var ManpowerValid = $("#validField_" + (i)).val();

    	    if(ManpowerValid == ''){
    	    	$('.numProjectRoles_'+ (i)).text("");
        	    $('.designationId_'+ (i)).text("");
        	    $('.deputedAt_'+ (i)).text("");
        	    /*$('.purpose_'+ (i)).text("");*/
        	    $('.ratePerManMonth_'+ (i)).text("");
        	    $('.count_'+ (i)).text("");
        	    $('.involvement_'+ (i)).text("");
        	    $('.startDate_'+ (i)).text("");
        	    $('.endDate_'+ (i)).text("");
        	    
        	    if(numProjectRoles == 0){
        	    	$('.numProjectRoles_'+ (i)).text("Project Based Roles is required and cannot be empty");
        	    	validation=false;
        	    }
        	    if(designationId == 0){
        	    	$('.designationId_'+ (i)).text("Designation is required and cannot be empty");
        	    	validation=false;
        	    }
        	    if(deputedAt == 3){
        	    	$('.deputedAt_'+ (i)).text("Deputed at is required and cannot be empty");
        	    	validation=false;
        	    }
        	    /*if(purpose == ''){
        	    	$('.purpose_'+ (i)).text("Purpose is required and cannot be empty");
        	    	validation=false;
        	    }*/    
        	    if (ratePerManMonth == '') {
        	    	$('.ratePerManMonth_'+ (i)).text("Rate per Man Month is required and cannot be empty");
        	    	validation=false;
        	    }
        	    if (count == 0) {
        	    	$('.count_'+ (i)).text("Count is required and cannot be empty");
        	    	validation=false;
        	    }
        	    if (involvement == 0) {
        	    	$('.involvement_'+ (i)).text("Involvement is required and cannot be empty");
        	    	validation=false;
        	    }
        	    if (manpowerStartDate == '') {
        	    	$('.startDate_'+ (i)).text("Start Date is required and cannot be empty");
        	    	validation=false;
        	    }
        	    if (manpowerEndDate == '') {
        	    	$('.endDate_'+ (i)).text("End Date is required and cannot be empty");
        	    	validation=false;
        	    }
        	    /*if(numProjectRoles == 0 && designationId == 0 && deputedAt == 3 && purpose == '' && ratePerManMonth == '' && count == 0 && involvement == 0 && manpowerStartDate == '' && manpowerEndDate == ''){
        	    	$('.numProjectRoles_'+ (i)).text("");
            	    $('.designationId_'+ (i)).text("");
            	    $('.deputedAt_'+ (i)).text("");
            	    $('.purpose_'+ (i)).text("");
            	    $('.ratePerManMonth_'+ (i)).text("");
            	    $('.count_'+ (i)).text("");
            	    $('.involvement_'+ (i)).text("");
            	    $('.startDate_'+ (i)).text("");
            	    $('.endDate_'+ (i)).text("");
        	        validation=false;
        	    }*/
    	    }
    	    
	        
    	}
        
        if (validation) {
        	// Get combo list as an array
        	var ProjectRolesArray = [];
            var designationListArray = [];

            // Iterate through the options within the select element
            $("#numProjectRoles_0 option").each(function() {
                var value = $(this).val();
                var text = $(this).text();

                // Add the option to the combo list array
                ProjectRolesArray.push({
                    value: value,
                    text: text
                });
            });
            $("#designationId_0 option").each(function() {
                var value = $(this).val();
                var text = $(this).text();

                // Add the option to the combo list array
                designationListArray.push({
                    value: value,
                    text: text
                });
            });

            var i = totalRowInTable;
            totalRowInTable += 1;
            // Create a new row element
            var newRow = $("<tr id="+i+'row'+"></tr>");

            // Append the cells to the row
            newRow.append("<td >" +
                "<input class='hidden' id='validField_"+i+"'/>" +
                "<input class='hidden' id='ManpowerId_"+i+"'/>" +
                "<div class='input-container-manpower'>" +
                "<select id='numProjectRoles_"+i+"' class='select2Option' style='width:120px' onchange='saveField("+ i +")'>" +
                "</select>" +
                "</div>" +
                "<div class='alert-danger text-center numProjectRoles_"+i+" errorMessageClass' role='alert'></div>" +
                "</td>");

            newRow.append("<td >" +
                "<div class='input-container-manpower'>" +
                "<select id='designationId_"+i+"' class='select2Option' style='width:120px' onchange='saveField("+ i +")'>" +
                "</select>" +
                "</div>" +
                "<div class='alert-danger text-center designationId_"+i+" errorMessageClass' role='alert'></div>" +
                "</td>");

            newRow.append("<td >" +
                "<div class='input-container-manpower'>" +
                "<select id='deputedAt_"+i+"' class='select2Option' style='width:120px' onchange='saveField("+ i +")'>" +
                "<option value='3'> -- Select -- </option>" +
                "<option value='0'>CDAC Noida </option>" +
                "<option value='1'>Client Site</option>" +
                "</select>" +
                "</div>" +
                "<div class='alert-danger text-center deputedAt_"+i+" errorMessageClass' role='alert'></div>" +
                "</td>");

            newRow.append("<td >" +
                "<div class='input-container-manpower'>" +
                /*"<i class='fa fa-user icon_manpower'></i>" +*/
                "<input class='input-field' id='purpose_"+i+"' onblur='saveField("+ i +")' style='width:200px'/>" +
                "</div>" +
                "<div class='alert-danger text-center purpose_"+i+" errorMessageClass' role='alert'></div>" +
                "</td>");

            newRow.append("<td class='bold' id='actualCost_"+i+"'></td>");

            newRow.append("<td >" +
                "<div class='input-container-manpower'>" +
                /*"<i class='fa fa-user icon_manpower'></i>" +*/
                "<input class='input-field' id='ratePerManMonth_"+i+"' onblur='saveField("+ i +")' style='width:90px'/>" +
                "</div>" +
                "<div class='alert-danger text-center ratePerManMonth_"+i+" errorMessageClass' role='alert'></div>" +
                "</td>");

            newRow.append("<td >" +
                "<div class='input-container-manpower'>" +
                /*"<i class='fa fa-user icon_manpower'></i>" +*/
                "<input class='input-field' id='count_"+i+"' onblur='saveField("+ i +")'/>" +
                "</div>" +
                "<div class='alert-danger text-center count_"+i+" errorMessageClass' role='alert'></div>" +
                "</td>");

            newRow.append("<td >" +
                "<div class='input-container-manpower'>" +
                /*"<i class='fa fa-th-large icon_manpower'></i>" +*/
                "<input class='input-field' id='involvement_"+i+"' onblur='saveField("+ i +")'/>" +
                "</div>" +
                "<div class='alert-danger text-center involvement_"+i+" errorMessageClass' role='alert'></div>" +
                "</td>");

            newRow.append("<td >" +
                "<div class='input-container-manpower'>" +
                /*"<i class='fa fa-calendar icon_manpower'></i>" +*/
                "<input class='input-field startDate' readonly='true' id='startDate_"+i+"' onchange='saveField("+ i +")' style='width:90px'/>" +
                "</div>" +
                "<div class='alert-danger text-center startDate_"+i+" errorMessageClass' role='alert'></div>" +
                "</td>");

            newRow.append("<td >" +
                "<div class='input-container-manpower'>" +
                /*"<i class='fa fa-calendar icon_manpower'></i>" +*/
                "<input class='input-field endDate' readonly='true' id='endDate_"+i+"' onchange='saveField("+ i +")' style='width:90px'/>" +
                "</div>" +
                "<div class='alert-danger text-center endDate_"+i+" errorMessageClass' role='alert'></div>" +
                "</td>");

            var role = $('#roleId').val();
            if(role==5){
            	newRow.append("<td class='col-md-6 col-lg-6 col-sm-6 col-xs-6'>" +
                        "<div class='input-container-manpower center'>" +
                        "<input type='checkbox' id='required_"+i+"' name='required_"+i+"' onclick='saveField("+ i +")'/>" +
                        "<input class='hidden' name='requiredType_"+i+"' id='requiredType_"+i+"' value='1'/>" +
                        "</div>" +
                        "<div class='alert-danger text-center required_"+i+" errorMessageClass' role='alert'></div>" +
                        "</td>");
            }

            newRow.append("<td class='col-md-2 col-lg-2 col-sm-4 col-xs-12 statusColumn'>" +
                "<input type='radio' name='valid_"+ i +"' value='true' id='toggle-on_"+ i +"' style='width:70px' onchange='saveField("+ i +")' checked/>" +
                "<label for='toggle-on' class='btn inline  zero round no-pad'><span>Active</span></label>" +
                "<input type='radio' name='valid_"+ i +"' value='false' id='toggle-off_"+ i +"' style='width:70px' onchange='saveField("+ i +")'/>" +
                "<label for='toggle-off' class='btn round inline zero no-pad'><span class=''>Inactive</span></label>" +
                "</td>");

            newRow.append("<td class='ActionColumn'>" +
                "<div align='center' class='pad-top pad-bottom' style='display: inline-flex'>" +
                "<button type='button' class='btn_manpower btn-primary addRowButton'>" +
                "<i class='fa fa-plus-circle btn_manpower btn-primary'></i>" +
                "</button>&nbsp;&nbsp;" +
                "<button type='button' class='btn_manpower btn-danger deleteRowButton' onclick='deleteManpowerDetails("+i+")'>" +
                "<i class='fa fa-minus-circle btn_manpower btn-danger'></i>" +
                "</button>" +
                "</div>" +
                "</td>");
            
            $("#manpowerMultipleData").append(newRow);
            for(var j = 0; j < ProjectRolesArray.length; j++) {
            	$('#numProjectRoles_'+i).append(
    					$('<option/>').attr("value", ProjectRolesArray[j].value)
    							.text(ProjectRolesArray[j].text));
                // Append the new row to the table
            }
            for(var j = 0; j < designationListArray.length; j++) {
            	$('#designationId_'+i).append(
    					$('<option/>').attr("value", designationListArray[j].value)
    							.text(designationListArray[j].text));
                // Append the new row to the table
            }
            $("#numProjectRoles_" + i).select2({width:"element"});
            $("#designationId_" + i).select2({width:"element"});
            $("#deputedAt_" + i).select2({width:"element"});

            hideAddButton();
            $('.statusColumn').addClass('hidden');
            
            $("#startDate_" + i).datepicker({
	            dateFormat: 'dd/mm/yy',
	            changeMonth: true,
	            changeYear: true,
	            minDate: selectedDate,
	            maxDate: selectedEndDate,
	            onSelect: function(date){
			    	$('#startDate_'+i).trigger('input');
			    	var dateData = date.split('/');
			    	var monthval = parseInt(dateData[1])-1;
			        var selectedDate = new Date(dateData[2],monthval,dateData[0]);       

			       //Set Minimum Date of EndDatePicker After Selected Date of StartDatePicker
			        $("#endDate_"+i).datepicker( "option", "minDate", selectedDate );
			        saveField(i);
			    }
	        });
	        $("#endDate_" + i).datepicker({
	            dateFormat: 'dd/mm/yy',
	            changeMonth: true,
	            changeYear: true,
	            minDate: selectedDate,
	            maxDate: selectedEndDate
	        });
        } 
    });
    
    $(document).on('click','#editManpower',function(e){ 
		var resultArray = $(this).closest('tr').find('td').map( function(){
			return $(this).text();}).get();
		EditManpowerDetails(resultArray);
    });
    
    $(document).on('click','#saveManpower',function(e){ 
    	var validation=true;
    	var isallblank=true;
    	for(var i=0;i<totalRowInTable;i++){
    		var ManpowerId = $.trim($("input[id='ManpowerId_" + (i) + "']").val());
    		var numProjectRoles = $("#numProjectRoles_" + (i)).val();
    		var designationId = $("#designationId_" + (i)).val();
    		var deputedAt = $("#deputedAt_" + (i)).val();
    		var purpose = $.trim($("input[id='purpose_" + (i) + "']").val());
    		var actualCost = $("#actualCost_" + (i)).text();
    	    var ratePerManMonth = $.trim($("input[id='ratePerManMonth_" + (i) + "']").val());
    	    var count = $.trim($("input[id='count_" + (i) + "']").val());
    	    var involvement = $.trim($("input[id='involvement_" + (i) + "']").val());
    	    var manpowerStartDate = $.trim($("input[id='startDate_" + (i) + "']").val());
    	    var manpowerEndDate = $.trim($("input[id='endDate_" + (i) + "']").val());
    	    var required = $.trim($("input[id='required_" + (i) + "']").val());
    	    var ManpowerValid = $("#validField_" + (i)).val();
    	    var requiredType = 0;
    	    var Status = document.querySelector('input[name="valid_' + i + '"]:checked').value;

    	    $('.numProjectRoles_'+ (i)).text("");
    	    $('.designationId_'+ (i)).text("");
    	    $('.deputedAt_'+ (i)).text("");
    	    /*$('.purpose_'+ (i)).text("");*/
    	    $('.ratePerManMonth_'+ (i)).text("");
    	    $('.count_'+ (i)).text("");
    	    $('.involvement_'+ (i)).text("");
    	    $('.startDate_'+ (i)).text("");
    	    $('.endDate_'+ (i)).text("");
    	    
    	    if(numProjectRoles == 0){
    	    	$('.numProjectRoles_'+ (i)).text("Project Based Roles is required and cannot be empty");
    	    }
    	    if(designationId == 0){
    	    	$('.designationId_'+ (i)).text("Designation is required and cannot be empty");
    	    }
    	    if(deputedAt == 3){
    	    	$('.deputedAt_'+ (i)).text("Deputed at is required and cannot be empty");
    	    }
    	    /*if(purpose == ''){
    	    	$('.purpose_'+ (i)).text("Purpose is required and cannot be empty");
    	    }*/    
    	    if (ratePerManMonth == '') {
    	    	$('.ratePerManMonth_'+ (i)).text("Rate per Man Month is required and cannot be empty");
    	    }
    	    if (count == 0) {
    	    	$('.count_'+ (i)).text("Count is required and cannot be empty");
    	    }
    	    if (involvement == 0) {
    	    	$('.involvement_'+ (i)).text("Involvement is required and cannot be empty");
    	    }
    	    if (manpowerStartDate == '') {
    	    	$('.startDate_'+ (i)).text("Start Date is required and cannot be empty");
    	    }
    	    if (manpowerEndDate == '') {
    	    	$('.endDate_'+ (i)).text("End Date is required and cannot be empty");
    	    }
    	    if(numProjectRoles == 0 && designationId == 0 && deputedAt == 3 && purpose == '' && ratePerManMonth == '' && count == 0 && involvement == 0 && manpowerStartDate == '' && manpowerEndDate == ''){
    	    	$('.numProjectRoles_'+ (i)).text("");
        	    $('.designationId_'+ (i)).text("");
        	    $('.deputedAt_'+ (i)).text("");
        	    /*$('.purpose_'+ (i)).text("");*/
        	    $('.ratePerManMonth_'+ (i)).text("");
        	    $('.count_'+ (i)).text("");
        	    $('.involvement_'+ (i)).text("");
        	    $('.startDate_'+ (i)).text("");
        	    $('.endDate_'+ (i)).text("");
    	    }
    	    if(numProjectRoles != 0 || designationId != 0 || deputedAt != 3 || purpose != '' || ratePerManMonth != '' || count != 0 || involvement != 0 || manpowerStartDate != '' || manpowerEndDate != ''){
    	    	isallblank=false;
    	    }
    	    if(!(numProjectRoles != 0 && designationId != 0 && deputedAt != 3 && ratePerManMonth != '' && count != 0 && involvement != 0 && manpowerStartDate != '' && manpowerEndDate != '')&&!(numProjectRoles == 0 && designationId == 0 && deputedAt == 3 && purpose == '' && ratePerManMonth == '' && count == 0 && involvement == 0 && manpowerStartDate == '' && manpowerEndDate == '')){
    	    	validation=false;
    	    }

    	}
    	if(editmode==1 && !isallblank){
	    	if(validation){
		    	for(var i=0;i<totalRowInTable;i++){
		    		ManpowerId = $.trim($("input[id='ManpowerId_" + (i) + "']").val());
		    		numProjectRoles = $("#numProjectRoles_" + (i)).val();
		    		designationId = $("#designationId_" + (i)).val();
		    		deputedAt = $("#deputedAt_" + (i)).val();
		    		purpose = $.trim($("input[id='purpose_" + (i) + "']").val());
		    		actualCost = $("#actualCost_" + (i)).text();
		    	    ratePerManMonth = $.trim($("input[id='ratePerManMonth_" + (i) + "']").val());
		    	    count = $.trim($("input[id='count_" + (i) + "']").val());
		    	    involvement = $.trim($("input[id='involvement_" + (i) + "']").val());
		    	    manpowerStartDate = $.trim($("input[id='startDate_" + (i) + "']").val());
		    	    manpowerEndDate = $.trim($("input[id='endDate_" + (i) + "']").val());
		    	    required = $.trim($("input[id='required_" + (i) + "']").val());
		    	    ManpowerValid = $("#validField_" + (i)).val();
		    	    Status = document.querySelector('input[name="valid_' + i + '"]:checked').value;
		    	    
		    	    if($('input[name=required_' + i + ']').is(":checked") == true && $('#required_'+ (i)).is(":checked") == true)
		    		{
		    			$('#requiredType_'+ (i)).val(1);
		    			requiredType = 1;
		    		}
		    		else
		    		{
		    			$('#requiredType_'+ (i)).val(0);
		    			requiredType = 0;
		    		}
		
			        validation=false;
			        if((numProjectRoles == 0 || designationId == 0 || deputedAt == 3 || purpose == '' || ratePerManMonth == '' || count == 0 || involvement == 0 || manpowerStartDate == '' || manpowerEndDate == '') && ManpowerValid == ''){
			        	saveManpower(ManpowerId,numProjectRoles,designationId,deputedAt,purpose,actualCost,ratePerManMonth,count,involvement,manpowerStartDate,manpowerEndDate,requiredType,Status,'Multiple');
			        	validation=true;
			        }
		    	}
		    	location.reload();
	    	}  		
    	}
    	else if(isallblank){
    		swal("Please enter atleast one Manpower Requirement.");
    	}
    	else{
	    	if(validation){
			    	for(var i=0;i<totalRowInTable;i++){
			    		ManpowerId = $.trim($("input[id='ManpowerId_" + (i) + "']").val());
			    		numProjectRoles = $("#numProjectRoles_" + (i)).val();
			    		designationId = $("#designationId_" + (i)).val();
			    		deputedAt = $("#deputedAt_" + (i)).val();
			    		purpose = $.trim($("input[id='purpose_" + (i) + "']").val());
			    		actualCost = $("#actualCost_" + (i)).text();
			    	    ratePerManMonth = $.trim($("input[id='ratePerManMonth_" + (i) + "']").val());
			    	    count = $.trim($("input[id='count_" + (i) + "']").val());
			    	    involvement = $.trim($("input[id='involvement_" + (i) + "']").val());
			    	    manpowerStartDate = $.trim($("input[id='startDate_" + (i) + "']").val());
			    	    manpowerEndDate = $.trim($("input[id='endDate_" + (i) + "']").val());
			    	    required = $.trim($("input[id='required_" + (i) + "']").val());
			    	    ManpowerValid = $("#validField_" + (i)).val();
			    	    Status = document.querySelector('input[name="valid_' + i + '"]:checked').value;
			    	    
			    	    if($('input[name=required_' + i + ']').is(":checked") == true && $('#required_'+ (i)).is(":checked") == true)
			    		{
			    			$('#requiredType_'+ (i)).val(1);
			    			requiredType = 1;
			    		}
			    		else
			    		{
			    			$('#requiredType_'+ (i)).val(0);
			    			requiredType = 0;
			    		}
			
				        validation=false;
				        if((numProjectRoles != 0 && designationId != 0 && deputedAt != 3 && ratePerManMonth != '' && count != 0 && involvement != 0 && manpowerStartDate != '' && manpowerEndDate != '') && ManpowerValid == ''){
				        	saveManpower(ManpowerId,numProjectRoles,designationId,deputedAt,purpose,actualCost,ratePerManMonth,count,involvement,manpowerStartDate,manpowerEndDate,requiredType,Status,'Multiple');
				        	validation=true;
				        }
			    	}
			    	location.reload();
	    	}
    	}
    	
    });
});

function saveManpower(ManpowerId,numProjectRoles,designationId,deputedAt,purpose,actualCost,ratePerManMonth,count,involvement,manpowerStartDate,manpowerEndDate,requiredType,Status,callByMethod)
{
	if(ManpowerId == ''){
		ManpowerId = 0;
	}
	var project_id = $("#projectId").val();
	
	$.ajaxRequest.ajax({
		type : "POST",
		url : "/PMS/mst/saveManpowerRequirementMaster",
		data : {
			projectId: project_id,
			numId:ManpowerId,
			numProjectRoles: numProjectRoles,
			designationId: designationId,
			deputedAt: deputedAt,
			purpose: purpose,
			actualRatePerManMonth: actualCost,
			ratePerManMonth: ratePerManMonth,
			count: count,
			involvement: involvement,
			startDate: manpowerStartDate,
			endDate: manpowerEndDate,
			requiredType: requiredType,
			valid: Status,
	    },
		async:false,
		success : function(response) {	
			editmode = 1;
			$('.statusColumn').addClass('hidden');
		}, error: function(data) {
	 	  
	   }
	});
}

function EditManpowerDetails(resultArray)
{
	isProgrammaticChange = true;
	$('.ActionColumn').addClass("hidden");
	$('.statusColumn').removeClass('hidden');
	$('#saveManpower').text('Update');

	if(editmode==1){
		for(var i=0;i<totalRowInTable;i++){
			let j=i+1;
			/*$('#milestoneId_'+j).val("");*/
			$("#numProjectRoles_" + (i)).val("0").trigger('change');
			$("#designationId_" + (i)).val("0").trigger('change');
			$("#deputedAt_" + (i)).val("3").trigger('change');
			$("#purpose_" + (i)).val("");
			$('#actualCost_'+i).text(0);
		    $("#ratePerManMonth_" + (i)).val("");
		    $("#count_" + (i)).val("0");
		    $("#involvement_" + (i)).val("0");
		    $("#startDate_" + (i)).val("");
		    $("#endDate_" + (i)).val("");
		    $("#required_" + (i)).val("");
			$('#'+j+"row").addClass("hidden");
			$("#validField_" + (j)).val("True");
			var ManpowerId = $('#ManpowerId_'+i+'').val();
			/*console.log("milestoneId..."+milestoneId);*/
			if(ManpowerId != ""){
				$.ajaxRequest.ajax({
					type : "POST",
					url : "/PMS/mst/deleteManpowerReq",
					data: {"numId":ManpowerId},
					 success: function(data) { 	
						 if(data==1){
							 /*swal("Attention","Cannot Delete!Manpower is Mapped against selected Requirement");*/
						 }
						 else{
							 /*swal("success","Manpower is deleted successfully");*/

						 }
					 }
				});
			}
		}
		//End of loop
	}
	
	//console.log(resultArray);
	$('#ManpowerId_0').val(resultArray[18].trim());
	$('#purpose_0').val(resultArray[2].trim());
	$('#actualCost_0').text(convertINRToAmount(resultArray[19].trim()));
	$('#ratePerManMonth_0').val(convertINRToAmount(resultArray[3].trim()));
	$('#count_0').val(resultArray[4].trim());
	$('#involvement_0').val(resultArray[5].trim());
	$('#startDate_0').val(resultArray[6].trim()).trigger('change');	
	$('#endDate_0').val(resultArray[7].trim()).trigger('change');	
	$('#projectId_0').val(resultArray[15].trim());	
	$('#designationId_0').val(resultArray[16].trim()).trigger('change');	
	$('#strDescription_0').val(resultArray[8].trim());
	$('#deputedAt_0').val(resultArray[17].trim()).trigger('change');	
	
	var require = $.trim(resultArray[10]);
				
				if(require == "Additional Requirement")
				{
				$('#required_0').attr("checked","checked");
				$('#requiredType_0').val("1");
				}
				else if(require == " "){
				$('#required_0').removeAttr("checked");
				$('#requiredType_0').val("0");
				}
	if(resultArray[11].trim()=='Active'){
		$('#toggle-off_0').removeAttr('checked');
		$('#toggle-on_0').attr('checked',true);

	}else{
		$('#toggle-on_0').removeAttr('checked');
		$('#toggle-off_0').attr('checked',true);
	}
	$('#numProjectRoles_0').val(resultArray[12].trim()).trigger('change');
	isProgrammaticChange = false;
	editmode=0;
}

var isProgrammaticChange = false;
function deleteManpowerDetails(i)
{
	isProgrammaticChange = true;
	var ManpowerId = $('#ManpowerId_'+i+'').val();
	if ($('#'+i+"row").is(':last-child')) {
		$('#ManpowerId_'+i).val("");
		$("#numProjectRoles_" + (i)).val("0").trigger('change');
		$("#designationId_" + (i)).val("0").trigger('change');
		$("#deputedAt_" + (i)).val("3").trigger('change');
		$("#purpose_" + (i)).val("");
		$('#actualCost_'+i).text(0);
	    $("#ratePerManMonth_" + (i)).val("");
	    $("#count_" + (i)).val("0");
	    $("#involvement_" + (i)).val("0");
	    $("#startDate_" + (i)).val("");
	    $("#endDate_" + (i)).val("");
	    $("#required_" + (i)).val("");
	} else {
		$('#ManpowerId_'+i).val("");
		$("#numProjectRoles_" + (i)).val("0").trigger('change');
		$("#designationId_" + (i)).val("0").trigger('change');
		$("#deputedAt_" + (i)).val("3").trigger('change');
		$("#purpose_" + (i)).val("");
		$('#actualCost_'+i).text(0);
	    $("#ratePerManMonth_" + (i)).val("");
	    $("#count_" + (i)).val("0");
	    $("#involvement_" + (i)).val("0");
	    $("#startDate_" + (i)).val("");
	    $("#endDate_" + (i)).val("");
	    $("#required_" + (i)).val("");
		$('#'+i+"row").addClass("hidden");
		$("#validField_" + (i)).val("True");
	}
	
	$('.numProjectRoles_'+ (i)).text("");
    $('.designationId_'+ (i)).text("");
    $('.deputedAt_'+ (i)).text("");
    /*$('.purpose_'+ (i)).text("");*/
    $('.ratePerManMonth_'+ (i)).text("");
    $('.count_'+ (i)).text("");
    $('.involvement_'+ (i)).text("");
    $('.startDate_'+ (i)).text("");
    $('.endDate_'+ (i)).text("");

	if(ManpowerId != ""){
		$.ajaxRequest.ajax({
			type : "POST",
			url : "/PMS/mst/deleteManpowerReq",
			data: {"numId":ManpowerId},
			 success: function(data) { 	
				 if(data==1){
					 /*swal("Attention","Cannot Delete!Manpower is Mapped against selected Requirement");*/
				 }
				 else{
					 /*swal("success","Manpower is deleted successfully");*/

				 }
			 }
		});
	}
	isProgrammaticChange = false;
	
}

function saveField(i){
	if (!isProgrammaticChange) {
		/*console.log("In save field function");*/
		var ManpowerId = $.trim($("input[id='ManpowerId_" + (i) + "']").val());
		var numProjectRoles = $("#numProjectRoles_" + (i)).val();
		var designationId = $("#designationId_" + (i)).val();
		var deputedAt = $("#deputedAt_" + (i)).val();
		var purpose = $.trim($("input[id='purpose_" + (i) + "']").val());
		var actualCost = 0;
	    var ratePerManMonth = $.trim($("input[id='ratePerManMonth_" + (i) + "']").val());
	    var count = $.trim($("input[id='count_" + (i) + "']").val());
	    var involvement = $.trim($("input[id='involvement_" + (i) + "']").val());
	    var manpowerStartDate = $.trim($("input[id='startDate_" + (i) + "']").val());
	    var manpowerEndDate = $.trim($("input[id='endDate_" + (i) + "']").val());
	    var required = $.trim($("input[id='required_" + (i) + "']").val());
	    var ManpowerValid = $("#validField_" + (i)).val();
	    var requiredType = 0;
	    var validation=true;
	    var Status = document.querySelector('input[name="valid_' + i + '"]:checked').value;
	    var categoryId = $('#categoryId').val();
	    if($('input[name=required_' + i + ']').is(":checked") == true && $('#required_'+ (i)).is(":checked") == true)
		{
			$('#requiredType_'+ (i)).val(1);
			requiredType = 1;
		}
		else
		{
			$('#requiredType_'+ (i)).val(0);
			requiredType = 0;
		}
	    console.log("requiredType.."+requiredType);
	    if(designationId == 0){
			$('#actualCost_'+i).text(0);
			actualCost = 0;
			$('.numProjectRoles_'+ (i)).text("");
		    $('.designationId_'+ (i)).text("");
		    $('.deputedAt_'+ (i)).text("");
		    /*$('.purpose_'+ (i)).text("");*/
		    $('.ratePerManMonth_'+ (i)).text("");
		    $('.count_'+ (i)).text("");
		    $('.involvement_'+ (i)).text("");
		    $('.startDate_'+ (i)).text("");
		    $('.endDate_'+ (i)).text("");
		    
		    if(numProjectRoles == 0){
		    	$('.numProjectRoles_'+ (i)).text("Project Based Roles is required and cannot be empty");
		    	validation=false;
		    }
		    if(designationId == 0){
		    	$('.designationId_'+ (i)).text("Designation is required and cannot be empty");
		    	validation=false;
		    }
		    if(deputedAt == 3){
		    	$('.deputedAt_'+ (i)).text("Deputed at is required and cannot be empty");
		    	validation=false;
		    }
		    /*if(purpose == ''){
		    	$('.purpose_'+ (i)).text("Purpose is required and cannot be empty");
		    	validation=false;
		    }*/    
		    if (ratePerManMonth == '') {
		    	$('.ratePerManMonth_'+ (i)).text("Rate per Man Month is required and cannot be empty");
		    	validation=false;
		    }
		    if (count == 0) {
		    	$('.count_'+ (i)).text("Count is required and cannot be empty");
		    	validation=false;
		    }
		    if (involvement == 0) {
		    	$('.involvement_'+ (i)).text("Involvement is required and cannot be empty");
		    	validation=false;
		    }
		    if (manpowerStartDate == '') {
		    	$('.startDate_'+ (i)).text("Start Date is required and cannot be empty");
		    	validation=false;
		    }
		    if (manpowerEndDate == '') {
		    	$('.endDate_'+ (i)).text("End Date is required and cannot be empty");
		    	validation=false;
		    }
		    if(numProjectRoles == 0 && designationId == 0 && deputedAt == 3 && purpose == '' && ratePerManMonth == '' && count == 0 && involvement == 0 && manpowerStartDate == '' && manpowerEndDate == ''){
		    	$('.numProjectRoles_'+ (i)).text("");
			    $('.designationId_'+ (i)).text("");
			    $('.deputedAt_'+ (i)).text("");
			    $('.purpose_'+ (i)).text("");
			    $('.ratePerManMonth_'+ (i)).text("");
			    $('.count_'+ (i)).text("");
			    $('.involvement_'+ (i)).text("");
			    $('.startDate_'+ (i)).text("");
			    $('.endDate_'+ (i)).text("");
		        validation=true;
		    }   

		    if (validation) {

		    	if(ManpowerId == ''){
		    		ManpowerId = 0;
		    	}
		    	var project_id = $("#projectId").val();
		    	$.ajaxRequest.ajax({
		    		type : "POST",
		    		url : "/PMS/mst/saveManpowerRequirementMaster",
		    		data : {
		    			projectId: project_id,
		    			numId:ManpowerId,
		    			numProjectRoles: numProjectRoles,
		    			designationId: designationId,
		    			deputedAt: deputedAt,
		    			purpose: purpose,
		    			actualRatePerManMonth: actualCost,
		    			ratePerManMonth: ratePerManMonth,
		    			count: count,
		    			involvement: involvement,
		    			startDate: manpowerStartDate,
		    			endDate: manpowerEndDate,
		    			requiredType: requiredType,
		    			valid: Status
		    	    },
		    		async:false,
		    		success : function(response) {
		    	            var userinfoMessageDiv = $(response).find('#userinfo-message');
		    	            if (userinfoMessageDiv.length > 0) {
		    	                var successMessage = userinfoMessageDiv.find('p.success_msg').text();
		    	                var idMatch = successMessage.match(/Id (\d+)/);
		    	                if (idMatch) {
		    	                    var id = idMatch[1];
		    	                    var elementId = '#ManpowerId_' + (i);
		    	                    $(elementId).val(id);
		    	                }
		    	            }
		    		}, error: function(data) {
		    			
		    	   }
		    	})
		    	}
		}else{
			//Added by devesh on 02-05-24 to match deputedAt value with project_category_designation_mapping table
			var numDeputedAt = 0;
			if(categoryId == 8){
				numDeputedAt = parseInt(deputedAt, 10) + 1;
			}
			else{
				numDeputedAt = 0;
			}
			
			$.ajax({
				 type: "POST",
				 url: "/PMS/categoryDesignationCost",
				 data: {"projectCategoryId":categoryId,
					 "designationId":designationId,
					 "numDeputedAt": numDeputedAt,
				 },
				 success: function(data) {
					 if(data.cost){
						$('#actualCost_'+i).text(data.cost);
					 	actualCost = data.cost;
					 }
					 else{
						$('#actualCost_'+i).text(0);
					 	actualCost = 0;
					 }					 
					 
					$('.numProjectRoles_'+ (i)).text("");
				    $('.designationId_'+ (i)).text("");
				    $('.deputedAt_'+ (i)).text("");
				    /*$('.purpose_'+ (i)).text("");*/
				    $('.ratePerManMonth_'+ (i)).text("");
				    $('.count_'+ (i)).text("");
				    $('.involvement_'+ (i)).text("");
				    $('.startDate_'+ (i)).text("");
				    $('.endDate_'+ (i)).text("");
				    
				    if(numProjectRoles == 0){
				    	$('.numProjectRoles_'+ (i)).text("Project Based Roles is required and cannot be empty");
				    	validation=false;
				    }
				    if(designationId == 0){
				    	$('.designationId_'+ (i)).text("Designation is required and cannot be empty");
				    	validation=false;
				    }
				    if(deputedAt == 3){
				    	$('.deputedAt_'+ (i)).text("Deputed at is required and cannot be empty");
				    	validation=false;
				    }
				    /*if(purpose == ''){
				    	$('.purpose_'+ (i)).text("Purpose is required and cannot be empty");
				    	validation=false;
				    }*/    
				    if (ratePerManMonth == '') {
				    	$('.ratePerManMonth_'+ (i)).text("Rate per Man Month is required and cannot be empty");
				    	validation=false;
				    }
				    if (count == 0) {
				    	$('.count_'+ (i)).text("Count is required and cannot be empty");
				    	validation=false;
				    }
				    if (involvement == 0) {
				    	$('.involvement_'+ (i)).text("Involvement is required and cannot be empty");
				    	validation=false;
				    }
				    if (manpowerStartDate == '') {
				    	$('.startDate_'+ (i)).text("Start Date is required and cannot be empty");
				    	validation=false;
				    }
				    if (manpowerEndDate == '') {
				    	$('.endDate_'+ (i)).text("End Date is required and cannot be empty");
				    	validation=false;
				    }
				    if(numProjectRoles == 0 && designationId == 0 && deputedAt == 3 && purpose == '' && ratePerManMonth == '' && count == 0 && involvement == 0 && manpowerStartDate == '' && manpowerEndDate == ''){
				    	$('.numProjectRoles_'+ (i)).text("");
					    $('.designationId_'+ (i)).text("");
					    $('.deputedAt_'+ (i)).text("");
					    $('.purpose_'+ (i)).text("");
					    $('.ratePerManMonth_'+ (i)).text("");
					    $('.count_'+ (i)).text("");
					    $('.involvement_'+ (i)).text("");
					    $('.startDate_'+ (i)).text("");
					    $('.endDate_'+ (i)).text("");
				        validation=true;
				    }   
	
				    if (validation) {
	
				    	if(ManpowerId == ''){
				    		ManpowerId = 0;
				    	}
				    	var project_id = $("#projectId").val();
				    	console.log("actualCost.."+actualCost);
				    	$.ajaxRequest.ajax({
				    		type : "POST",
				    		url : "/PMS/mst/saveManpowerRequirementMaster",
				    		data : {
				    			projectId: project_id,
				    			numId:ManpowerId,
				    			numProjectRoles: numProjectRoles,
				    			designationId: designationId,
				    			deputedAt: deputedAt,
				    			purpose: purpose,
				    			actualRatePerManMonth: actualCost,
				    			ratePerManMonth: ratePerManMonth,
				    			count: count,
				    			involvement: involvement,
				    			startDate: manpowerStartDate,
				    			endDate: manpowerEndDate,
				    			requiredType: requiredType,
				    			valid: Status
				    	    },
				    		async:false,
				    		success : function(response) {
				    	            var userinfoMessageDiv = $(response).find('#userinfo-message');
				    	            if (userinfoMessageDiv.length > 0) {
				    	                var successMessage = userinfoMessageDiv.find('p.success_msg').text();
				    	                var idMatch = successMessage.match(/Id (\d+)/);
				    	                if (idMatch) {
				    	                    var id = idMatch[1];
				    	                    var elementId = '#ManpowerId_' + (i);
				    	                    $(elementId).val(id);
				    	                }
				    	            }
				    		}, error: function(data) {
				    			
				    	   }
				    	})
				    	}
				 }
			
			})
		}
	    // Log or use the selected value
	    /*console.log("selectedValue.."+Status);

	    console.log("ManpowerId.."+ManpowerId);
	    console.log("numProjectRoles.."+numProjectRoles);
	    console.log("designationId.."+designationId);
	    console.log("deputedAt.."+deputedAt);
	    console.log("purpose.."+purpose);
	    console.log("actualCost.."+actualCost);
	    console.log("ratePerManMonth.."+ratePerManMonth);
	    console.log("count.."+count);
	    console.log("involvement.."+involvement);
	    console.log("manpowerStartDate.."+manpowerStartDate);
	    console.log("manpowerEndDate.."+manpowerEndDate);
	    console.log("required.."+required);
	    console.log("ManpowerValid.."+ManpowerValid);
	    console.log("ManpowerId.."+ManpowerId);*/
    }
	
    
    
}
//End of Functions

//Bhavesh(22-02-23) hide the delete buttton from first row
$(document).ready(function(){
	
	$(".deleteRowButton_0").addClass("hidden");
	
	
	
});