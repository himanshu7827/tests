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
	
		/*$('#example1').DataTable();*/
		$('#example1').DataTable({
	        // Specify the default sorting by the first column (column index 0)
	        order: [[0, 'asc']]
	    });
		 $('#userinfo-message').delay(5000).fadeOut();
		$('#delete').click(function(){
			ProjectMilestoneDelete();
		});
		
		$('#reset').click(function(){
			resetForm();
			 $('#form1').data('bootstrapValidator').resetForm(true);
				$("#expectedStartDate").datepicker("enable");
		});


});
function resetForm(){
	$('#milestoneName').val('');
	$('#expectedStartDate').val('');
	$('#save').text('Save');
} 

$(document).on('click','#edit',function(e){
	
	var resultArray = $(this).closest('tr').find('td').map( function()
			{
			return $(this).text();
			}).get();
   	var allowDate= $('#allowDateEdit').val();

	if(allowDate==0){
	$("#expectedStartDate").datepicker("option", "disabled", true);
	}
	else{
		$("#expectedStartDate").datepicker("enable");
	}
	
	$('#numId').val(resultArray[0].trim());
	$('#milestoneName').val(resultArray[1].trim());	
	//$('#expectedStartDate').val(resultArray[2].trim());
	$('#milestoneTypeId').val(resultArray[2].trim()).trigger('change');
	$('#expectedStartDate').val(resultArray[4]);
	$('#strDesription').val(resultArray[5]);

	if(resultArray[6].trim()=='Active'){
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
	    	  milestoneName: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Milestone name is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 200,
	                        message: 'The Milestone name must be less than 200 characters'
	                    },
	                    regexp: {
	                        regexp: nameRegex,
	                       message: nameErrorMessage
	                    }
	              }
	          },
	          
	          expectedStartDate: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Expected Start Date is required and cannot be empty'
	                  },
	                  strDesription: {
	    	              group: '.col-md-12',
	    	              validators: {
	    	                  stringLength: {
	    	                        max: 2000,
	    	                        message: 'Description must be less than 2000 characters'
	    	                    },
	    	                    
	    	              }
	    	          },
	    	          
	                 
	              }
	          },
	          
	          /*expectedCompletionDate: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Expected Completion Date is required and cannot be empty'
	                  },
	                 
	              }
	          },
	          myClass:  {
	              selector: '.form-control',
	              validators: {
	                  notEmpty: {
	                      message: 'Description is required'
	                  }
	              }
	          },
*/
	          /*projectId:{
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
	          }
*/	          
	      
	         
	      }
	  });

	//var startDate=$("#startProjDate").val();
	/*$("#expectedStartDate").datepicker({ 
		
		dateFormat: 'dd/mm/yy', 
	    changeMonth: true,
	    changeYear:true,	 
	    
	    
	});*/
	var startDate=$("#startProjDate").val();
	var endDate=$("#endProjDate").val();
	
	/*alert(startDate);
	alert(endDate);*/
	var dateData = startDate.split('/');
	var monthval = parseInt(dateData[1])-1;
	var selectedDate = new Date(dateData[2],monthval,dateData[0]);	
	
	var enddateData = endDate.split('/');
	var endMonthval = parseInt(enddateData[1])-1;
	var selectedEndDate = new Date(enddateData[2],endMonthval,enddateData[0]);	
	
	$("#expectedStartDate").datepicker({ 
	dateFormat: 'dd/mm/yy', 
    changeMonth: true,
    changeYear:true,           
    minDate: selectedDate,
    maxDate:selectedEndDate
   /* onSelect: function(date){
    	$('#expectedStartDate').trigger('input');
    	      
        alert("here "+selectedDate);
       //Set Minimum Date of EndDatePicker After Selected Date of StartDatePicker
        $("#expectedStartDate").datepicker( "option", "minDate", selectedDate );
       // $("#dtEndDate").datepicker( "option", "maxDate", '+5y' );
        

    }*/
});
	
	/*var minDate = new Date($("#startProjDate").val());
 	//alert("minDate = "+minDate);

 	var yearEnd = minDate.getFullYear();
 	var monthEnd = minDate.getMonth();
 	var dayEnd = minDate.getDate();
 	
 	$datepicker_start_input = $("#expectedStartDate").pickadate({
		onStart: function ()
	            {			            			            
	                var datexEnd = new Date(maxDateStringEnd);
	                //alert("datexEnd = "+datexEnd);
	                this.set('select', [datexEnd.getFullYear(), datexEnd.getMonth(), datexEnd.getDate()+1]);
	            },
		            min:[yearEnd,monthEnd,dayEnd+1],							
					selectMonths: true,
					selectYears: true,
				    weekdaysShort: ['S', 'M', 'T', 'W', 'T', 'F', 'S']
	 });
*/
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
	   	var allowDate= $('#allowDateEdit').val();
	   	if(allowDate ==0){
		swal({
			  title: "Attention",
			  text: "Once saved,you would not able to modify Baseline Date. Do you want to save?",
			  type: 'success',						  
		      buttons: [					               
		                'No',
		                'Yes'
		              ],	     
		    }).then(function(isConfirm) {
		      if (isConfirm) {
		    	  if(bootstrapValidator.isValid()){
						document.getElementById("save").disabled = true; 
						$('#form1')[0].submit();
						return true;					
				}else{
					console.log('Not Validated');
				}
		      } 
		    });
	   	}else{
	   	 if(bootstrapValidator.isValid()){
				document.getElementById("save").disabled = true; 
				$('#form1')[0].submit();
				return true;					
		}else{
			console.log('Not Validated');
		}
	   	}
		
	   /* if(bootstrapValidator.isValid()){
				document.getElementById("save").disabled = true; 
				$('#form1')[0].submit();
				return true;
			
		}else{
			console.log('Not Validated');
			 //return false;
		}*/
	   
	});
		
});



function resetForm(){
	$('#numId').val('0');
	$('#milestoneName').val('');
	$('#expectedStartDate').val('');
	$('#expectedCompletionDate').val('');
	$('#actualStartDate').val('');
	$('#actualCompletionDate').val('');
	$('#strDesription').val('');
	$('#milestoneTypeId').val('0').trigger('change');
	$('#save').text('Save');
}

$(document).on('change','#projectId',function(e){
	var projId = $(this).find(":selected").val();
	moduledetail(projId);
   });  

function moduledetail(projectId){
	 
	$.ajaxRequest.ajax({
    type: "POST",
    url: "/PMS/mst/moduleDetailsByProjectId",
    data: "numId="+ projectId,
    success: function(response) {
    	 
    	$('#moduleSelect0').find('option').remove();
    	var seloption = "";
  	seloption += "<option value='0'>Select Module</option>";

  	for(var i=0;i<response.length;i++){
  		var rowData = response[i];
  		seloption += "<option value="+rowData.numId+">"+rowData.strModuleName+"</option>";
  	}
     	$('#moduleSelect0').append(seloption);
       	$("#moduleSelect0").trigger("liszt:updated");
    }
});

}

function ProjectMilestoneDelete(){
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
        swal("Please select at least one Project Milestone to delete");  
}
 
}

function submit_form_delete()
{
document.form1.action = "/PMS/mst/deleteProjectMilestone";
document.getElementById("form1").submit();
}
/*
$('.datePicker').datepicker();*/
$(document).ready(function(){
	   $('.select2Option').select2({
	    	 width: '100%'
	    });
	$('#addRow').on("click",function(){		
		var rowCount = $('#example >tbody >tr').length;				
		selectedModule=[];
		$('.moduleClass').each(function(){						
			if($(this).val() == "0"){
			}else{			 			
			 	if($(this).val()){
			 		selectedModule.push($(this).val());					 				
			 		}
			 }		
		});
		
		var allModules = "<select name='temp'>";
		
		allModules = allModules+ $('#moduleSelect0').html();
		
		allModules =allModules+ "</select>";
		
		var $allModules =$(allModules);
		for(var i=0;i<selectedModule.length;i++){
			$allModules.find("option[value='"+selectedModule[i]+"']").remove().text();			
		}
		var radioDivId = 'radioDiv'+rowCount;
		var selectDivId= 'selectDiv'+rowCount;
		var radioId0 = 'moduleRadio0_'+rowCount;
		var radioId1 = 'moduleRadio1_'+rowCount;
		var moduleSelectId = 'moduleSelect'+rowCount;
		
		
		var rowData= "<tr> <td><textarea class='form-control' rows='3' name='milestoneActivityModelList["+rowCount+"].description'></textarea> </td>";
		rowData+="<td> <div id="+radioDivId+"> <input type='radio' class='moduleRadio' id="+radioId0+" name='milestoneActivityModelList["+rowCount+"].withModule' value='1'> Yes  <input type='radio' class='moduleRadio' id="+radioId1+" name='milestoneActivityModelList["+rowCount+"].withModule' value='-1'> No </div> ";
		rowData+="<div id="+selectDivId+" class='hidden'><select class='select2Option moduleClass' id="+moduleSelectId+" name='milestoneActivityModelList["+rowCount+"].moduleId'>"+$allModules.html()+" </select> </div></td>";
		rowData+="<td > <input class='input-field datePicker' name='milestoneActivityModelList["+rowCount+"].startDate'/></td>";
		rowData+="<td > <input class='input-field datePicker' name='milestoneActivityModelList["+rowCount+"].endDate'/></td></tr>";
			
	$('#example').append(rowData);
	$('select.select2Option').select2({width: '100%'});
	});
});

$(document).on('click', '.moduleRadio', function () {
	var radioBtnId = this.id;
	
	 var idWithRow= radioBtnId.substring(11);
	 if(idWithRow.indexOf('_') != -1){
		 var idAndRow = idWithRow.split('_');		
		 if(idAndRow[0] == '0'){
			 //Show Select
			 $('#selectDiv'+idAndRow[1]).removeClass('hidden');
		 }else{
			 //Hide Select
			 $('#moduleSelect'+idAndRow[1]).val('0').trigger('change');
			 $('#selectDiv'+idAndRow[1]).addClass('hidden');
		 }
	 }
	 
 
});
function goprev()
{
	 var path=$(location).attr('pathname');  

	 document.getElementById("form1").action="/PMS/prevRedirectPage?path='"+path+"'&workflowTypeId=3&uniqueId="+$("#projectId").val();
		document.getElementById("form1").method="POST";
		document.getElementById("form1").submit();

}



function gonext()
{
	 var path=$(location).attr('pathname');  

	 document.getElementById("form1").action="/PMS/nextRedirectPage?path='"+path+"'&workflowTypeId=3&uniqueId="+$("#projectId").val();
		document.getElementById("form1").method="POST";
		document.getElementById("form1").submit();

}

function redirectPage(path){
	var encProjectId = $('#encProjectId').val();
	//alert(projectId);
	openWindowWithPost('GET',path,{"encProjectId" : encProjectId},'_self');
}

/*-------------------------------------- Add Multiple Milestone Rows [23-10-2023] --------------------------------------*/
var totalRowInTable = 4;
var editmode=1;

function hideAddButton() {
    $('.addRowButton').addClass('hidden');
    $('.addRowButton:last').removeClass('hidden');
}

$(document).ready(function() {
	var startDate=$("#startProjDate").val();
	var endDate=$("#endProjDate").val();
	var dateData = startDate.split('/');
	var monthval = parseInt(dateData[1])-1;
	var selectedDate = new Date(dateData[2],monthval,dateData[0]);	
	var enddateData = endDate.split('/');
	var endMonthval = parseInt(enddateData[1])-1;
	var selectedEndDate = new Date(enddateData[2],endMonthval,enddateData[0]);
	
	/*$(".deleteRowButton").addClass("hidden");*/
	/*---------- DatePicker according to Project start date and enddate -------------*/
    /*$("#expectedStartDate_0").datepicker({
        dateFormat: 'dd/mm/yy',
        changeMonth: true,
		changeYear:true,
	    minDate: selectedDate,
	    maxDate:selectedEndDate
    });*/
	if (startDate === "" || endDate === "") {
        // Display a warning message
        /*for (var i = 0; i < totalRowInTable; i++) {
            $("#expectedStartDate_" + i).prop('readonly', true);
        }*/
    }
	else{
		for (var i = 0; i < totalRowInTable; i++) {
	        var tableRow = $("<tr id=" + i + ">");
	        $("#expectedStartDate_" + i).datepicker({
	            dateFormat: 'dd/mm/yy',
	            changeMonth: true,
	            changeYear: true,
	            minDate: selectedDate,
	            maxDate: selectedEndDate
	        });
	    }
	}	
	
	hideAddButton();
	
	$(document).on("click", '.BaselineStartDate', function() {
		if (startDate === "" && endDate === "") {
	        // Display a warning message
			swal({
		        title: "Warning",
		        text: "Project Start Date and End Date are not available. Please set them before entering the baseline date of the milestone.",
		        icon: "warning",
		        button: "OK",
		    });
	    }
		else if (startDate === "" && endDate !== "") {
	        // Display a warning message
			swal({
		        title: "Warning",
		        text: "Project Start Date is not available. Please set it before entering the baseline date of the milestone.",
		        icon: "warning",
		        button: "OK",
		    });
	    }
		else if (startDate !== "" && endDate === "") {
	        // Display a warning message
			swal({
		        title: "Warning",
		        text: "Project End Date is not available. Please set it before entering the baseline date of the milestone.",
		        icon: "warning",
		        button: "OK",
		    });
	    }
	});
	
	/*---------------- (+) Click the plus button ----------------*/
    $(document).on("click", '.addRowButton', function() {
    	/*$('.milestoneName_'+ (totalRowInTable-1)).text("");
    	$('.expectedStartDate_'+ (totalRowInTable-1)).text("");
    	var milestoneId = $.trim($("input[id='milestoneId_" + (totalRowInTable-1) + "']").val());
        var milestoneName = $.trim($("input[id='milestoneName_" + (totalRowInTable-1) + "']").val());
        var dateValue = $.trim($("input[id='expectedStartDate_" + (totalRowInTable-1) + "']").val());
        var milestoneDescription = $("#strDescription_" + (totalRowInTable-1)).val();

        var validation=false;
        if(milestoneName == '' && dateValue == ''){
        	$('.milestoneName_'+ (totalRowInTable-1)).text("Milestone name is required and cannot be empty");
        	$('.expectedStartDate_'+ (totalRowInTable-1)).text("Expected Start Date is required and cannot be empty");	
        }else if(milestoneName==''){
        	$('.milestoneName_'+ (totalRowInTable-1)).text("Milestone name is required and cannot be empty");
        }else if(dateValue==''){
        	$('.expectedStartDate_'+ (totalRowInTable-1)).text("Baseline Date is required and cannot be empty");	
        }else{
        	saveMilestoneDetails(milestoneId,milestoneName,dateValue,milestoneDescription,'Single');
        	$(".deleteRowButton").removeClass("hidden");
        	validation=true;
        }*/
    	var validation=true;
    	for(var i=0;i<totalRowInTable;i++){
	    	var milestoneId = $.trim($("input[id='milestoneId_" + (i) + "']").val());
	        var milestoneName = $.trim($("input[id='milestoneName_" + (i) + "']").val());
	        var dateValue = $.trim($("input[id='expectedStartDate_" + (i) + "']").val());
	        var milestoneDescription = $("#strDescription_" + (i)).val();
	        var milestoneValid = $("#validField_" + (i)).val();

	        if(milestoneValid == ''){
	        	if(milestoneName == '' && dateValue == ''){
		        	$('.milestoneName_'+ (i)).text("Milestone name is required and cannot be empty");
		        	$('.expectedStartDate_'+ (i)).text("Baseline Date is required and cannot be empty");
		        	validation=false;
		        }else if(milestoneName==''){
		        	$('.milestoneName_'+ (i)).text("Milestone name is required and cannot be empty");
		        	$('.expectedStartDate_'+ (i)).text("");
		        	validation=false;
		        }else if(dateValue==''){
		        	$('.expectedStartDate_'+ (i)).text("Baseline Date is required and cannot be empty");
		        	$('.milestoneName_'+ (i)).text("");
		        	validation=false;
		        }else{
		        	$('.milestoneName_'+ (i)).text("");
		        	$('.expectedStartDate_'+ (i)).text("");
		        }
	        }
	        
    	}
        
        if (validation) {
        	/*for(var i=0;i<totalRowInTable;i++){
    	    	var milestoneId = $.trim($("input[id='milestoneId_" + (i) + "']").val());
    	        var milestoneName = $.trim($("input[id='milestoneName_" + (i) + "']").val());
    	        var dateValue = $.trim($("input[id='expectedStartDate_" + (i) + "']").val());
    	        var milestoneDescription = $("#strDescription_" + (i)).val();
    	        var milestoneValid = $("#validField_" + (i)).val();

    	        var validation=false;
    	        if((milestoneName != '' || dateValue != '') && milestoneValid == ''){
    	        	if(milestoneId == '' && milestoneId == ''){
    	        		milestoneId = 0;
    	        	}
    	        	var project_id = $("#projectId").val();
    	        	
    	        	$.ajaxRequest.ajax({
    	        		type : "POST",
    	        		url : "/PMS/mst/saveProjectMilestone",
    	        		data : {
    	        			numId:milestoneId,
    	        	        milestoneName: milestoneName,
    	        	        expectedStartDate: dateValue,
    	        	        projectId: project_id,
    	        	        strDesription: milestoneDescription
    	        	    },
    	        		async:false,
    	        		success : function(response) {
    	        	            var userinfoMessageDiv = $(response).find('#userinfo-message');
    	        	            if (userinfoMessageDiv.length > 0) {
    	        	                var successMessage = userinfoMessageDiv.find('p.success_msg').text();
    	        	                var idMatch = successMessage.match(/Id (\d+)/);
    	        	                if (idMatch) {
    	        	                    var id = idMatch[1];
    	        	                    var elementId = '#milestoneId_' + (i);
    	        	                    $(elementId).val(id);
    	        	                }
    	        	            }
    	        		}, error: function(data) {
    	        			
    	        	   }
    	        	})
    	        	validation=true;
    	        }
        	}*/
        	
            var i = totalRowInTable;
            totalRowInTable += 1;
            var tableRow = $("<tr id="+i+'row'+">");
            tableRow.append('<td><input class="hidden" id="validField_'+i+'"/><input class="hidden" id="milestoneId_'+i+'"/><div class="input-container-milestone"><i class="fa fa-user icon_milestone"></i> <input class="input-field" id="milestoneName_' + i + '" onblur="saveField('+ i +')"/></div><div class="alert-danger text-center errorMessageClass milestoneName_'+i+'" role="alert"></div></td>');
            tableRow.append('<td><div class="input-container-milestone"><i class="fa fa-file-text-o icon_milestone"></i><textarea class="input-field form-control" rows="2" id="strDescription_' + i + '" onblur="saveField('+ i +')"></textarea></div></td>');
            tableRow.append('<td><div class="input-container-milestone"><i class="fa fa-calendar icon_milestone"></i><input class="input-field" readonly="true" id="expectedStartDate_' + i + '" onchange="saveField('+ i +')"/></div><div class="alert-danger text-center errorMessageClass expectedStartDate_'+i+'" role="alert"></div></td>');
            tableRow.append('<td class="ActionColumn"><div align="center" class="pad-top pad-bottom" style="display: inline-flex"><button type="button" class="btn_milestone btn-primary addRowButton"><i class="fa fa-plus-circle btn_milestone btn-primary"></i></button>&nbsp;&nbsp;<button type="button" class="btn_milestone btn-danger deleteRowButton" onclick="deleteMilestoneDetails('+i+')"><i class="fa fa-minus-circle btn_milestone btn-danger"></i></button></div></td></tr>');

            $('#milestoneMultipleData').append(tableRow);
            hideAddButton();
            $("#expectedStartDate_" + i).datepicker({
                dateFormat: 'dd/mm/yy',
                changeMonth: true,
        		changeYear:true,
        	    minDate: selectedDate,
        	    maxDate:selectedEndDate
            });
        } 
    });
    
    $(document).on('click','#editMiletsone',function(e){ 
		var resultArray = $(this).closest('tr').find('td').map( function(){
			return $(this).text();}).get();
   	 	/*swal({
	      title: "Please ensure that you have saved any existing data from the above form.Would you like to proceed??",
	      icon: "warning",
	      buttons: [
	                'No',
	                'Yes'
	              ],
	      dangerMode: true,
	    }).then(function(isConfirm) {
	    	if (isConfirm) {*/
	    		EditMilestoneDetails(resultArray);
		  /*}});*/
    });
    
    $(document).on('click','#saveMiletsone',function(e){ 
    	var validation=true;
    	var isallblank=true;
    	for(var i=0;i<totalRowInTable;i++){
	    	var milestoneId = $.trim($("input[id='milestoneId_" + (i) + "']").val());
	        var milestoneName = $.trim($("input[id='milestoneName_" + (i) + "']").val());
	        var dateValue = $.trim($("input[id='expectedStartDate_" + (i) + "']").val());
	        var milestoneDescription = $("#strDescription_" + (i)).val();
	        var milestoneValid = $("#validField_" + (i)).val();

	        if(milestoneName != '' && dateValue == ''){
	        	$('.expectedStartDate_'+ (i)).text("Baseline Date is required and cannot be empty");
	        	$('.milestoneName_'+ (i)).text("");
	        	validation=false;
	        	isallblank=false;
	        }else if(milestoneName == '' && dateValue != ''){
	        	$('.milestoneName_'+ (i)).text("Milestone name is required and cannot be empty");
	        	$('.expectedStartDate_'+ (i)).text("");
	        	validation=false;
	        	isallblank=false;
	        }else if(milestoneName != '' && dateValue != ''){
	        	$('.milestoneName_'+ (i)).text("");
	        	$('.expectedStartDate_'+ (i)).text("");
	        	isallblank=false;
	        }
	        
    	}
    	if(editmode==1 && !isallblank){
	    	if(validation){
	    		swal({
	    			  title: "Attention",
	    			  text: "Once the project is ongoing, you will not be able to modify the baseline date.",
	    			  type: 'success',						  
	    		      buttons: [					               
	    		                'No',
	    		                'Yes'
	    		              ],	     
	    		    }).then(function(isConfirm) {
	    		    	if (isConfirm) {
					    	for(var i=0;i<totalRowInTable;i++){
						    	milestoneId = $.trim($("input[id='milestoneId_" + (i) + "']").val());
						        milestoneName = $.trim($("input[id='milestoneName_" + (i) + "']").val());
						        dateValue = $.trim($("input[id='expectedStartDate_" + (i) + "']").val());
						        milestoneDescription = $("#strDescription_" + (i)).val();
						        milestoneValid = $("#validField_" + (i)).val();
					
						        validation=false;
						        if((milestoneName != '' || dateValue != '') && milestoneValid == ''){
						        	saveMilestoneDetails(milestoneId,milestoneName,dateValue,milestoneDescription,'Multiple');
						        	validation=true;
						        }
					    	}
					    	location.reload();
	    		    	}
	    		    });
	    	}  		
    	}
    	else if(isallblank){
    		swal("Please enter atleast one milestone.");
    	}
    	else{
	    	if(validation){
			    	for(var i=0;i<totalRowInTable;i++){
				    	milestoneId = $.trim($("input[id='milestoneId_" + (i) + "']").val());
				        milestoneName = $.trim($("input[id='milestoneName_" + (i) + "']").val());
				        dateValue = $.trim($("input[id='expectedStartDate_" + (i) + "']").val());
				        milestoneDescription = $("#strDescription_" + (i)).val();
				        milestoneValid = $("#validField_" + (i)).val();
			
				        validation=false;
				        if((milestoneName != '' || dateValue != '') && milestoneValid == ''){
				        	saveMilestoneDetails(milestoneId,milestoneName,dateValue,milestoneDescription,'Multiple');
				        	validation=true;
				        }
			    	}
			    	location.reload();
	    	}
    	}
    	
    });
});

function saveMilestoneDetails(milestoneId,milestoneName,date,description,callByMethod)
{
	
	if(milestoneId == '' && milestoneId == ''){
		milestoneId = 0;
	}
	var project_id = $("#projectId").val();
	
	$.ajaxRequest.ajax({
		type : "POST",
		url : "/PMS/mst/saveProjectMilestone",
		data : {
			numId:milestoneId,
	        milestoneName: milestoneName,
	        expectedStartDate: date,
	        projectId: project_id,
	        strDesription: description
	    },
		async:false,
		success : function(response) {	
			editmode = 0;
			if(callByMethod=='Single'){
	            var userinfoMessageDiv = $(response).find('#userinfo-message');
	            if (userinfoMessageDiv.length > 0) {
	                var successMessage = userinfoMessageDiv.find('p.success_msg').text();
	                var idMatch = successMessage.match(/Id (\d+)/);
	                if (idMatch) {
	                    var id = idMatch[1];
	                    var elementId = '#milestoneId_' + (totalRowInTable - 1);
	                    $(elementId).val(id);
	                }
	            }
			}
		}, error: function(data) {
	 	  
	   }
	});
}

/*--------------------- Edit Function Get the Value from Table and Display in the fields for Edit [23-10-2023] --------------*/
function EditMilestoneDetails(resultArray)
{
	$('.ActionColumn').addClass("hidden");
	$('#saveMiletsone').text('Update');
	var allowDate= $('#allowDateEdit').val();

	/*if(allowDate==0){
		$("#expectedStartDate_0").datepicker("option", "disabled", true);
	}
	else{
		$("#expectedStartDate_0").datepicker("enable");
	}
*/
	//Added by devesh on 26-10-23 to hide all rows when edit is clicked
	if(editmode==1){
		for(var i=0;i<totalRowInTable;i++){
			let j=i+1;
			/*$('#milestoneId_'+j).val("");*/
			$('#milestoneName_'+j).val("");	
			$('#expectedStartDate_'+j).val("");
			$('#strDescription_'+j).val("");
			$('#'+j+"row").addClass("hidden");
			$("#validField_" + (j)).val("True");
			var milestoneId = $('#milestoneId_'+i+'').val();
			/*console.log("milestoneId..."+milestoneId);*/
			$.ajaxRequest.ajax({
				type : "POST",
				url : "/PMS/mst/deleteMilestoneDetail",
				data : {
					milestoneId:milestoneId,
			    },
				success : function(response) {
					/*alert("Success"+milestoneId);*/
					

				}, error: function(data) {
					/*alert("Failed"+milestoneId);*/

			   }
			});
		}
	}
	//End of loop
	$('#milestoneId_0').val(resultArray[1].trim());
	$('#milestoneName_0').val(resultArray[2].trim());	
	$('#expectedStartDate_0').val(resultArray[3]);
	$('#strDescription_0').val(resultArray[4]);
	$('.milestoneName_0').text("");
	$('.expectedStartDate_0').text("");
	
	
	//Bhavesh(21-02-24) date editable	
if($('#actionId').val()=="true"){
		
	
	$("#expectedStartDate_0").datepicker("enable");
	}
else{
	
	
	$("#expectedStartDate_0").datepicker("option", "disabled", true);
}
	
	/*if(resultArray[6].trim()=='Active'){
		$('#toggle-off').removeAttr('checked');
		$('#toggle-on').attr('checked',true);

	}else{
		$('#toggle-on').removeAttr('checked');
		$('#toggle-off').attr('checked',true);
	}*/
	editmode=0;
}

/*--------------------- Delete Function to delete or invalid the milestone from Table [23-10-2023] --------------*/
function deleteMilestoneDetails(i)
{
	var milestoneId = $('#milestoneId_'+i+'').val();
	if ($('#'+i+"row").is(':last-child')) {
		$('#milestoneId_'+i).val("");
		$('#milestoneName_'+i).val("");	
		$('#expectedStartDate_'+i).val("");
		$('#strDescription_'+i).val("");
	} else {
		$('#milestoneId_'+i).val("");
		$('#milestoneName_'+i).val("");	
		$('#expectedStartDate_'+i).val("");
		$('#strDescription_'+i).val("");
		$('#'+i+"row").addClass("hidden");
		$("#validField_" + (i)).val("True");
	}

	$.ajaxRequest.ajax({
		type : "POST",
		url : "/PMS/mst/deleteMilestoneDetail",
		data : {
			milestoneId:milestoneId,
	    },
		success : function(response) {

		}, error: function(data) {

	   }
	});
}

//Added by devesh on 30-10-23 to autosave fields
function saveField(i){
	var milestoneId = $.trim($("input[id='milestoneId_" + (i) + "']").val());
    var milestoneName = $.trim($("input[id='milestoneName_" + (i) + "']").val());
    var dateValue = $.trim($("input[id='expectedStartDate_" + (i) + "']").val());
    var milestoneDescription = $("#strDescription_" + (i)).val();
    var milestoneValid = $("#validField_" + (i)).val();
    var validation=true;
    
    // Find the table and iterate through its rows to check if milestoneName exists
    var table = $('#example1').DataTable();
    var rows = table.rows().data();

    var milestoneNameExists = false;

    rows.each(function (index, data) {
        var rowMilestoneName = index[2]; // Assuming milestoneName is in the third column (0-based index)
        if (milestoneName === rowMilestoneName) {
            milestoneNameExists = true;
            return false; // Exit the loop since a match is found
        }
    });
    if(editmode==1){
    	if(milestoneNameExists){
        	swal("Milestone with this name already exists. Please enter a different name.");
        	validation=false;
        }
    }

    if(milestoneName != '' && dateValue == ''){
    	$('.expectedStartDate_'+ (i)).text("Baseline Date is required and cannot be empty");
    	$('.milestoneName_'+ (i)).text("");
    	validation=false;
    }else if(milestoneName == '' && dateValue != ''){
    	$('.milestoneName_'+ (i)).text("Milestone name is required and cannot be empty");
    	$('.expectedStartDate_'+ (i)).text("");
    	validation=false;
    }else {
    	$('.milestoneName_'+ (i)).text("");
    	$('.expectedStartDate_'+ (i)).text("");
    }

    if (validation) {

    	if(milestoneId == '' && milestoneId == ''){
    		milestoneId = 0;
    	}
    	var project_id = $("#projectId").val();
    	
    	$.ajaxRequest.ajax({
    		type : "POST",
    		url : "/PMS/mst/saveProjectMilestone",
    		data : {
    			numId:milestoneId,
    	        milestoneName: milestoneName,
    	        expectedStartDate: dateValue,
    	        projectId: project_id,
    	        strDesription: milestoneDescription
    	    },
    		async:false,
    		success : function(response) {
    	            var userinfoMessageDiv = $(response).find('#userinfo-message');
    	            if (userinfoMessageDiv.length > 0) {
    	                var successMessage = userinfoMessageDiv.find('p.success_msg').text();
    	                var idMatch = successMessage.match(/Id (\d+)/);
    	                if (idMatch) {
    	                    var id = idMatch[1];
    	                    var elementId = '#milestoneId_' + (i);
    	                    $(elementId).val(id);
    	                }
    	            }
    		}, error: function(data) {
    			
    	   }
    	})
    	}
    }
//End of Function\

//Bhavesh(22-02-23) hide the delete buttton from first row
$(document).ready(function(){
	
	$(".deleteRowButton_0").addClass("hidden");
	
	
	
});