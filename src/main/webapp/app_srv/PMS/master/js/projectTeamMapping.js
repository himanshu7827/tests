$(document).ready(function() {	
		var table = $('#datatable').DataTable();
		table.destroy();		
	});


$('.select2Option').select2({
	 width: '100%'
});



$(document).ready(function(){

	
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
	    	  numProjectId:{
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
	          },  numRoleId:{	        	
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
	          }
	      }
	  });
});

$(document).on('change','#numProjectId',function(e){
	
	var projectName=$( "#numProjectId option:selected" ).text();
	
	$('#projectName').html(projectName);
	/*Bhavesh (04-08-23) added if(projectName=='' || projectName==null )*/
	if(projectName=='' || projectName==null ){
		 $('#projectName').text($('#pName').text());
		
		 console.log($('#pName').val());
	}
	/*Bhavesh (04-08-23) added if(projectName=='' || projectName==null )*/
	
	$('#existingTeamTable').find("tr:gt(0)").remove();
	$('#newMemberTable').find("tr:gt(0)").remove();	
	$('#projectStartDate').empty();
	$('#projectEndDate').empty();
	
	var numProjectId = $('#numProjectId').val();	
		if(numProjectId != 0){
			$('.detailsDiv').removeClass('hidden');
			loadProjectCategoryId(numProjectId);
			loadProjectTeam(numProjectId);
			/*Bhavesh (10-08-23) call the handleSelectChange() to set the value of start Date,end date and involvement if role is HOD or Project In-charge  */
			handleSelectChange();
			/*Bhavesh (10-08-23)*/
		}else{
			$('.detailsDiv').addClass('hidden');
		}	
	});
function loadProjectCategoryId(numProjectId){
	$.ajaxRequest.ajax({
        type: "POST",
        url: "/PMS/mst/projectCategory",
        data:{"numProjectId":numProjectId},
        async:false,       		
		success : function(data) {
			$('#categoryId').val(data);
		}
		});
}
var userRoleId;
function loadProjectTeam(numProjectId){
	 userRoleId=$('#userRole').val();
	$.ajaxRequest.ajax({
        type: "POST",
        url: "/PMS/mst/projectTeamWiseEmployees",
        data:{"numProjectId":numProjectId},
        async:false,       		
		success : function(data) {
			
			
			var inputData = JSON.parse(data);
			console.log(inputData);
			//console.log(inputData);
			var newEmployees ='<option value="0" >------Select------</option>';
			//Bhavesh(6-07-2023)added deputed_at
			var deputedAt="" ;
			var row=0;
			var seloption = "";
			seloption +='<option value="0">------Select------</option>';
			
			var selectId;
			
			var manpowerTypes = []; // Bhavesh (13-11-23) Initialize an empty array to store the manpowerDetails.type values
			var manpowerIds = []; // Bhavesh (13-11-23) Initialize an empty array to store the manpowerDetails.reqNumId values
			jQuery(inputData).each(function(i, item){
				if(i==0){					
					$('#projectStartDate').text(item.projectStartDate);
					$('#projectEndDate').text(item.projectEndDate);
				}
				else if(i==1){
					jQuery(item.arr).each(function(j, manpowerDetails){
						if(manpowerDetails.type!=1){
							manpowerTypes.push('\n'+manpowerDetails.reqDetails);
							manpowerIds.push('\n'+manpowerDetails.reqNumId);
						seloption+='<option value='+manpowerDetails.reqNumId+'>'+manpowerDetails.reqDetails+'</option>';
						}else{
							manpowerTypes.push('\n'+manpowerDetails.reqDetails);
							manpowerIds.push('\n'+manpowerDetails.reqNumId);
							seloption+='<option class="red" value='+manpowerDetails.reqNumId+'>'+manpowerDetails.reqDetails+'</option>';
	
						}
						
					});
					}
				else{
					 selectId = item.employeeId+"_"+item.numId;	
					
					if(item.numId != 0){
						var tableRow = "<tr id=empDetail_"+selectId+"> ";
						var showClass="show_"+selectId;
						var editClass ="edit_"+selectId;
						
						tableRow+="<td><div class='input-container hidden "+editClass+"'><select class='select2Option1 reqSelect' id=reqNumId_"+selectId+" name='reqNumId'>"+seloption+"</select></div>";
						if(item.reqId!=0){
							tableRow+=" <div class="+showClass+"><span id=selectedRequirement_"+selectId+"></span></td> ";
							
						}else{
							tableRow+="</td>";
						}
						
                        tableRow+="<td><div class='input-container hidden "+editClass+"'><select class='select2Option1' id=empRole_"+selectId+" class='empRole'></select></div><div class="+showClass+" id=selectedRole_"+selectId+"></div></td>";
						
						
						if(item.designationName!=null && item.primaryProject == 1 ){
							tableRow+="<td> <a title='Click here to view employee details' data-toggle='modal' data-target='#employeeDetailsModel' onclick=viewEmployeeDetails('"+item.encEmployeeId+"') class='text-center bold font_16'> <b>"+item.employeeName+"</b></a>" +
									" <p><b>"+item.designationName+"</b> [ "+item.roleName+" ("+item.projectName+") ]<a class='btn btn-info white emp_info_new' data-toggle='modal' data-target='#empProjectDetails' data-whatever='"+item.encEmployeeId+";"+item.employeeName+"'><span>"+item.countProjectMapping+"</span></a> </p></td>";					
						}else if(item.designationName!=null){
							tableRow+="<td> <a title='Click here to view employee details' data-toggle='modal' data-target='#employeeDetailsModel' onclick=viewEmployeeDetails('"+item.encEmployeeId+"') class='text-center bold font_16'>"+item.employeeName+"</a>,<p> <b>"+item.designationName+"</b><a class='btn btn-info white emp_info_new' data-toggle='modal' data-target='#empProjectDetails' data-whatever='"+item.encEmployeeId+";"+item.employeeName+"'><span>"+item.countProjectMapping+"</span></a></p> </td>";					
						}else if(item.countProjectMapping == 0){
								tableRow+="<td> <a title='Click here to view employee details' data-toggle='modal' data-target='#employeeDetailsModel' onclick=viewEmployeeDetails('"+item.encEmployeeId+"') class='text-center bold font_16'>"+item.employeeName+" </a></td>";					
						}else{
								tableRow+="<td> <a title='Click here to view employee details' data-toggle='modal' data-target='#employeeDetailsModel' onclick=viewEmployeeDetails('"+item.encEmployeeId+"') class='text-center bold font_16'>"+item.employeeName+" </a><a class='btn btn-info white emp_info_new' data-toggle='modal' data-target='#empProjectDetails' data-whatever='"+item.encEmployeeId+";"+item.employeeName+"'><span>"+item.countProjectMapping+"</span></a></td>";					
						}
						//Bhavesh(6-07-2023)added deputed_at
						if(item.num_deputed_at==1){
							tableRow+="<td><div class='input-container"+editClass+"'> CDAC</div></td>";
						}
						else if(item.num_deputed_at==2){
							tableRow+="<td><div class='input-container "+editClass+"'>CLIENT</div></td>";
						} 
						else{
							tableRow+="<td><div class='input-container "+editClass+"'> NULL</div></td>";
						}
						
						
						tableRow+="<td><div class='input-container hidden "+editClass+"'> <input type='hidden' id=min_"+selectId+" value ="+item.startDate+" /> <input type='text' readonly='readonly' class='startDate' id=cal_"+selectId+" /> </div>  <div class="+showClass+">"+item.assignmentDate+"</div></td>";
						
						tableRow+="<td><div class='input-container hidden "+editClass+"'> <input type='hidden' id=max_"+selectId+" value ="+item.endDate+" /> <input type='text' readonly='readonly' class='endDate' id=endCal_"+selectId+" /></div> ";
						
						if(item.endDate){
							tableRow+="<div class="+showClass+">"+item.endDate+"</div> </td>";
						}else{
							tableRow+="<div class="+showClass+"></div> </td>";
						}
						
						tableRow+="<td><div class='input-container hidden "+editClass+"'> <input id=involve_"+selectId+" value ="+item.numInvolvment+" /> </div>  <div class="+showClass+">"+item.numInvolvment+"</div></td>";
						
						
						if(item.primaryProject == 1){
							tableRow+="<td><input type='hidden' id=pdefault_"+selectId+" value="+item.primaryProject+" /> <input type='checkbox'  class='primaryProject' id=primary_"+selectId+" value='1' checked /> </td>";
						}else{
							tableRow+="<td> <input type='hidden' id=pdefault_"+selectId+" value="+item.primaryProject+" /> <input type='checkbox'  class='primaryProject' id=primary_"+selectId+" value='1' /> </td>";
						}
						
						tableRow +="<td><div class='"+showClass+"'><input type='hidden' value='"+showClass+"'></input><span title='Edit' class='fa fa-pencil-square-o btn btn-primary a-btn-slide-text' style='margin-left: 0%;'";
						tableRow +="id='edit' aria-hidden='true'></span></div> <div class='hidden "+editClass+"'><a title='Cancel'><i class='cancel fa fa-eye-slash fa-2x btn btn-info a-btn-slide-text-noBgColor' aria-hidden='true'></i></a> &nbsp; <a class='Update' title='Update'><i class='fa fa-check-square-o fa-2x btn btn-primary a-btn-slide-text'></i></a> </div> </td>";
						
						tableRow +='</tr>';
						$('#existingTeamTable').append(tableRow);
						$('#allroles').find('option').clone().appendTo('#empRole_'+selectId);
						newEmployees += '<option   value="'+item.employeeId+'" label="'+item.startDate+'" >'+item.employeeName+' ['+item.designationName+']  </option>';
						//Bhavesh(6-07-2023)added deputed_at
						if(item.num_deputed_at==1){
						deputedAt+='<p id='+item.employeeId+' class="class1 hidden"><b>CDAC</b></p>';
						}else if(item.num_deputed_at==2){
						deputedAt+='<p id='+item.employeeId+' class="class1 hidden"><b>CLIENT</b></p>';
						}
						else {
							deputedAt+='<p id='+item.employeeId+' class="class1 hidden"><b>NULL</b></p>';
						}
						}else{
						newEmployees += '<option  value="'+item.employeeId+'" label="'+item.startDate+'">'+item.employeeName+' ['+item.Designation+']</option>';
						if(item.num_deputed_at==1){
							deputedAt+='<p id='+item.employeeId+' class="class1 hidden"><b>CDAC</b></p>';
							}else if(item.num_deputed_at==2){
							deputedAt+='<p id='+item.employeeId+' class="class1 hidden"><b>CLIENT</b></p>';
							}
							else {
								deputedAt+='<p id='+item.employeeId+' class="class1 hidden"><b>NULL</b></p>';
							}
					}
					
					
					
				}
				if(item.reqId!=0){				
					$("#reqNumId_"+selectId +" option[value="+item.reqId+"]").prop("selected", true);
			        $("#reqNumId_"+selectId).trigger("change");
			        var req=$( "#reqNumId_"+selectId+" option:selected" ).text();			      
			        $("#selectedRequirement_"+selectId).append(req);
				}
				if(item.roleId!=0){				
				$("#empRole_"+selectId +" option[value="+item.roleId+"]").prop("selected", true);
		        $("#empRole_"+selectId).trigger("change");		       		      
		        $("#selectedRole_"+selectId).append(item.roleName);
			}
			$("#cal_"+selectId).val(item.assignmentDate);
		        $("#cal_"+selectId).trigger("input");
		        
		        /*Bhavesh(10-08-23) set the end date in edit existing Team Member */
		        $("#endCal_"+selectId).val(item.endDate);
		        $("#endCal_"+selectId).trigger("input");
		        /*Bhavesh(10-08-23)*/
			});	
			
			
			
	        if(newEmployees){
	        	
	        	//*Bhavesh (13-11-23) adding the row in the table acccording to the nunber of manpower types
	        	for(var i=0;i<manpowerTypes.length;i++){        
	        		console.log("newEmployees "+newEmployees);
				var tableRoww = "<tr id='empDetail"+i+"'>";	
				     
				 tableRoww+="<td  width='20%'><div class='input-container'><span id=reqNumIdDetails"+i+" >"+manpowerTypes[i]+"</span><span id=reqNumId"+i+" class=hidden>"+manpowerIds[i]+"</span></div></td> ";
				
				      tableRoww+="<td  width='10%'> <div class='input-container'><select class='select2Option1' id=reqEmpRole"+i+"></select></div> </td>";	

				     
			
					 tableRoww+="<td width='20%'> <div class='input-container'><select  class='select2Option1' id=reqEmpId"+i+" name='reqEmpId' onchange=setEmployee("+i+","+manpowerTypes.length+")>"+newEmployees+"</select><span id=sp1 class=hidden></span></div> </td>";					
					
					//Bhavesh(6-07-2023)added deputed_at
						tableRoww+="<td  width='5%'> <div class='input-container'>"+SetDeputedAt(deputedAt,i)+"</div> </td>";
					
					
					
					
					tableRoww+="<td  width='12%'><input type='hidden' id=min_Date/> <input type='text' readonly='readonly' class='startNew cal' id=cal"+i+" /></td>";
					tableRoww+="<td  width='12%'><input type='hidden' id=max_date /> <input type='text' readonly='readonly' class='endDateNew endCal' id=endCal"+i+" /></td>";
					tableRoww+="<td  width='5%'><input id=involve"+i+" class=involve  /></td>";
					tableRoww+="<td  width='5%'> <input type='hidden' id=pdefault  /> <input type='checkbox'  class='primaryProject' id=primary value='1' /> </td>";
					
					tableRoww +="<td  width='10%'> <a><i class='fa fa-check-square-o fa-2x SaveNew green' onclick=saveRowWise("+i+")></i> </a></td>";
					
					
			        tableRoww+="</tr>";
			        $('#newMemberTable').append(tableRoww);
			        $('#allroles').find('option').clone().appendTo('#reqEmpRole'+i);  
			        
			     /* //Get the select element
			        var selectElement = document.getElementById('reqEmpId');

			        // Add an event listener to handle the selection change event
			        selectElement.addEventListener('click', function() {
			          // Get the selected value
			          var selectedValue =this.value;
			          console.log(selectElement.options[selectElement.selectedIndex].text);

			          // Call your desired function passing the selected value
			          yourFunctionName(selectedValue);
			        });

			        // Example function that handles the selected value
			        function yourFunctionName(selectedValue) {
			          // Perform actions with the selected value
			          console.log('Selected value: ' + selectedValue);
			   }     }*/
	        	}
			}			
		
			initiateCalendar();
		}
	});
	
	if(userRoleId==5){
		

		
	var opt="<option class='blue font_16' value='addRequirement'>Create Additional Requirement</option>";
	$('.reqSelect').append(opt);
	$('#reqNumId').append(opt);
	}
	$('.select2Option1').select2({
		dropdownAutoWidth : true
	});
	
	/*Bhavesh (13-11-23) if  userRoleId==5 then it will show Create Additional Requirement button
	if(userRoleId==5||userRoleId==9){
		
		
		 

	}*/
	
	
}
userRoleId=$('#userRole').val();
if(userRoleId==5||userRoleId==9){
	

	 $("#addRequirement").removeClass("hide");
	 $("#addRequirement").click(function(){
		 $('#modalAnchor').click();
         
      });
	 
}

//Bhavesh (13-11-23) show the row wise deputed At
function SetDeputedAt(deputedAt,deputedAtRow){
	  var tempElement = document.createElement('div');
	  tempElement.innerHTML = deputedAt;

	  // Find all <p> elements with the id attribute
	  var paragraphs = tempElement.querySelectorAll('p[id]');
       var dep=tempElement.querySelectorAll('p[class]');
       /*console.log(dep)*/
	  // Iterate through the found elements and update their id values
	  for (var i = 0; i < paragraphs.length; i++) {
	    var oldId = paragraphs[i].getAttribute('id');
	    var newId = oldId+"_"+deputedAtRow;
	    paragraphs[i].setAttribute('id', newId);
	    var newClass="classDep_"+deputedAtRow+" hidden";
	    dep[i].setAttribute('class', newClass);
	  }
	  var resultHtml = tempElement.innerHTML;
	 /* console.log("asdas:"+resultHtml);*/
	  return resultHtml;
}

//Bhavesh (13-11-23) End of show the row wise deputed At

//Bhavesh (13-11-23) map the employee
var empid;

function setEmployee(RowNo,manpowerLength){
	/*$('.class1').addClass('hidden');*/
	$('.classDep_'+RowNo).addClass('hidden');
	 empid=$('#reqEmpId'+RowNo).val();
	/*console.log("empId....."+empid);*/

	$('#'+empid+'_'+RowNo).removeClass('hidden');
	$('#sp1').val(empid);
 

	/*$('#reqEmpRole'+e).on('click', function() {
		var empRole=$('#reqEmpRole'+e).val();
		
		if(empRole==15||empRole==4){
			$('#cal').val($('#projectStartDate').text());
	    	$('#cal').trigger('input');
	    	$('#endCal').val($('#projectEndDate').text());
	    	$('#endCal').trigger('input');
	    	$('#involve').val('5');
		}
		
		else if(empRole!=15||empRole!=4){
			
	    	$('#cal').val('');
	    	$('#cal').trigger('input');
	    	$('#endCal').val('');
	    	$('#endCall').trigger('input');
	    	$('#involve').val('');
		}
	    console.log($('#reqEmpRole'+e).val());
	});*/
}
//Bhavesh (13-11-23) end of map the employee


//Bhavesh (13-11-23) saves the employee according to the requrement Mapping
var mapdetails;
var mapid;
var emprole;
var involve;
var startDate1;
var endDate1;
function saveRowWise(RowNo){
	/*+manpowerTypes[i]+"</span><span id=reqNumId"+i+" class=hidden>"+manpowerIds[i]+"
*/	
	mapdetails=$('#reqNumIdDetails'+RowNo).text();
	
	/* mapid=manpowerIds[v];reqNumId*/
	mapid =$('#reqNumId'+RowNo).text();
	emprole=$('#reqEmpRole'+RowNo).val();
	involve=$('#involve'+RowNo).val();
     startDate1=$('#cal'+RowNo).val();
	 endDate1=$('#endCal'+RowNo).val();
	/* alert(startDate1);
	 alert(endDate1);
	 alert(mapdetails);
	 alert(emprole);
	 alert(involve);*/
	 /*console.log("mapid1"+mapid);
	 console.log("mapdetails1"+mapdetails);*/
	 
	 
	
}	

//Bhavesh (13-11-23) End of saves the employee according to the requrement Mapping



function initiateCalendar(){

	
	var projectEnd = $('#projectEndDate').text().trim();
	var endDateParts = projectEnd.split("/");
	var projectEndDate = new Date(+endDateParts[2], endDateParts[1] - 1, +endDateParts[0]);
	var start=$('#projectStartDate').text().trim();
	var startParts=start.split("/");
	var startProj=new Date(+startParts[2], startParts[1] - 1, +startParts[0]);
	
	$('.startDate').each(function(){
		var fieldId = this.id;
		var selectId = fieldId.split('cal_')[1];
		var dateString = $('#min_'+selectId).val(); 
		var dateParts = dateString.split("/");		
		var  dateObject= new Date(+dateParts[2], dateParts[1] - 1, +dateParts[0]);	
		$('#'+fieldId).datepicker({
			dateFormat:'dd/mm/yy',
			  changeMonth: true,
			  changeYear: true,
			  minDate: dateObject,
			  maxDate: projectEndDate,
			  defaultDate:dateObject,
			  onSelect: function(date){
			    	//$('#additionalStartDate').trigger('input');
			    	var dateData = date.split('/');
			    	var monthval = parseInt(dateData[1])-1;
			        var selectedDate = new Date(dateData[2],monthval,dateData[0]);       
			     
			       //Set Minimum Date of EndDatePicker After Selected Date of StartDatePicker
			        $("#endCal_"+selectId).datepicker( "option", "minDate", selectedDate );       

			    }
			});
	});
	$('.cal').datepicker({
		dateFormat:'dd/mm/yy',
		  changeMonth: true,
		  changeYear: true,
		  minDate: start,
		  maxDate: projectEndDate,
		  defaultDate:start,
		  onSelect: function(date){
		    	//$('#additionalStartDate').trigger('input');
		    	var dateData = date.split('/');
		    	var monthval = parseInt(dateData[1])-1;
		        var selectedDate = new Date(dateData[2],monthval,dateData[0]);       

		       //Set Minimum Date of EndDatePicker After Selected Date of StartDatePicker
		        $("#endCal").datepicker( "option", "minDate", selectedDate );       

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
	$("#additionalEndDate").datepicker({ 
		dateFormat: 'dd/mm/yy', 
	    changeMonth: true,
	    changeYear:true,
	    onSelect: function(date){
	    	$('#additionalEndDate').trigger('input');
	    }
	});
	/*$('.endDate').datepicker({
		dateFormat:'dd/mm/yy',
		  changeMonth: true,
		  changeYear: true,
		  minDate: start,
		  maxDate: projectEndDate,
		  defaultDate:start
		});*/
	//Added by devesh on 06-09-23 to fix validation on end date of resources
	$('.endDate').each(function(){
		var fieldId = this.id;
		var selectId = fieldId.split('endCal_')[1];
		var dateString = $('#cal_'+selectId).val(); 
		var dateParts = dateString.split("/");		
		var  dateObject= new Date(+dateParts[2], dateParts[1] - 1, +dateParts[0]);	
		$('#'+fieldId).datepicker({
			dateFormat:'dd/mm/yy',
			  changeMonth: true,
			  changeYear: true,
			  minDate: dateObject,
			  maxDate: projectEndDate,
			  defaultDate:dateObject
			});
	});
	//End of validation
	  $('.endCal').datepicker({
			dateFormat:'dd/mm/yy',
			  changeMonth: true,
			  changeYear: true,
			  minDate: start,//Added by devesh on 05-09-23
			  maxDate: projectEndDate,
			  defaultDate:start
			});
	  $("#additionalStartDate").datepicker( "option", "minDate", startProj ); 
	 $("#additionalStartDate").datepicker( "option", "maxDate", projectEndDate ); 
	  $("#additionalEndDate").datepicker( "option", "maxDate", projectEndDate ); 
	
}

$(document).ready(function(){

$('#save').click(function(){	   	
			var bootstrapValidator = $("#form1").data('bootstrapValidator');
			bootstrapValidator.validate();
		    if(bootstrapValidator.isValid()){
		    	var numPreviousItems = $('.previousMembers').length;
		    	var numNewItems =$('.newMembers:checkbox:checked').length;
		    	
		    	if(numPreviousItems == 0 && numNewItems == 0){
		    		sweetSuccess('Attention','Please select atleast 1 Team Member');
		    		return false;
		    	}else{
		    		prepareDataAndSubmit();
		    	}		    	
			}
		});
});
function onClickSave(val){
	var bootstrapValidator = $("#form1").data('bootstrapValidator');
	bootstrapValidator.validate();
    if(bootstrapValidator.isValid()){
    	var numPreviousItems = $('.previousMembers').length;
    	var numNewItems =$('.newMembers:checkbox:checked').length;
    	
    	if(numPreviousItems == 0 && val == 1){
    		sweetSuccess('Attention','Please select atleast 1 Team Member');
    		return false;
    	}else{
    		prepareDataAndSubmit(val);
    	}		    	
	}
}

function prepareDataAndSubmit(){
	
	/*  $(".previousMembers").each(function() {
		   fieldId = this.id;
		
		  var tempId = fieldId.split('_');
		  var rowObject = new Object();
		  rowObject.employeeId = tempId[0];
		  rowObject.numId = tempId[1];
		  if($("#cal_"+fieldId).datepicker("getDate")){
			  rowObject.startDate =  $("#cal_"+fieldId).datepicker({dateFormat: 'dd/mm/yy' }).val();
		  }else{
			  rowObject.startDate =  $("#projectStartDate").text();
		  }
		  rowObject.numProjectId= numProjectId;
		  rowObject.numRoleId = numRoleId;
		  var x=$('#primary_'+fieldId).is(':checked');
		  if(x == true){
			  //viewPrimaryRoleDetail(rowObject.employeeId);		 
			  rowObject.primaryRole = 1;  
		  }else{
			  rowObject.primaryRole=0;
		  }
		 var requirementId = $("reqNumId_"+fieldId).val();
		 var requirementText = $("reqNumId_"+fieldId).text();
		  involvment=$('#involve_'+fieldId).val();
		  
		  if(!involvment){
			  $('#empDetail_'+fieldId).addClass('highlight');
			  sweetSuccess('Attention','Please enter Involvement');
			  return false;
		  }else{
			 if(!($.isNumeric(involvment))){
				 $('#empDetail_'+fieldId).addClass('highlight');
				  sweetSuccess('Attention','Only numeric value allowed');
				  return false;
			 }else{
				 if(involvment> 100 || involvment <0){
					 $('#empDetail_'+fieldId).addClass('highlight');
					  sweetSuccess('Attention','Involvement can not be more than 100 and less than 0');
					  return false;
				 }
			 }
		  }
		  
		  rowObject.numInvolvment=involvment;
		  rowObject.reqNumId=requirementId;
		  rowObject.reqDetails=requirementText;
		  inputArray.push(rowObject);
	    });*/
	  
		  
}


$(document).on('change','#reqEmpId',function(e){
	//Bhavesh(6-07-2023)added class hidden
	$('.class1').addClass('hidden');
	var projectEnd = $('#projectEndDate').text().trim();
	var empid=$('#reqEmpId').val();
	//Bhavesh(6-07-2023)added  class remove hidden
	$('#'+empid).removeClass('hidden');
	var numProjectId = $('#numProjectId').val();
	$('.startNew').val('');
	$('.startNew').trigger('input');
	$('#endCal').val('');
	$('#endCal').trigger('input');
	
	if(empid==0){
		sweetSuccess('Attention','Please select employee');
	}else
	{
					var start=$('#reqEmpId :selected').attr('label');
					//alert(start);
					var initialStartDate = null;
					if(start){
						var tempStart = start.split("/");
						initialStartDate = new Date(tempStart[2],tempStart[1]-1,tempStart[0]);
					}
					//alert(initialStartDate);
					
				
					$('#cal').datepicker('option', 'minDate', initialStartDate);
}
				
			
	
});


$(document).on('change','.primaryProject',function(e){
		var projectId=$('#numProjectId').val();
		var fieldId = this.id;
		var temp = fieldId.split('_');
		var empId=temp[1];
		var roleId=temp[2];
		//console.log(roleId);
		var defaultPrimaryProject=0; 
		
		if(temp.length == 3){
			var pdefault = 'pdefault_'+temp[1]+"_"+temp[2];
			//console.log(pdefault);
			defaultPrimaryProject  = $('#'+pdefault).val();
		}else{
			sweetSuccess('Attention','Something went wrong');
		}
		
		//console.log(defaultPrimaryProject);
		
		 if ($('#'+fieldId).is(":checked")){	
			 swal({
				  title: "Attention",
				  text: "Do you want to allocate Primary Role ?",
				  type: 'success',						  
			      buttons: [					               
			                'No',
			                'Yes'
			              ],	     
			    }).then(function(isConfirm) {
			      if (isConfirm) {
					  viewPrimaryRoleDetail(empId,projectId,roleId);		 
		
					}
			      else
			    	  {
			    	  var checkBox = 'primary_'+empId+"_"+roleId;
		 				$('#'+checkBox).prop("checked", false);
			    	  }
			      
			    });
			 }
		 else{
			 if(defaultPrimaryProject == 1){
				 //sweetSuccess('Attention','You are removing Primary Role from Employee');
				 swal({
					  title: "Attention",
					  text: "Do you want to remove Employee Primary Role ?",
					  type: 'success',						  
				      buttons: [					               
				                'No',
				                'Yes'
				              ],	     
				    }).then(function(isConfirm) {
				      if (isConfirm) {
						  uncheckPrimaryRole(empId,projectId,roleId);		 
			
						}
				      else
			    	  {
			    	  var checkBox = 'primary_'+empId+"_"+roleId;
		 				$('#'+checkBox).prop("checked", true);
			    	  }
				      
				    });
			 }
		 }		 
	});
	
$('#empProjectDetails').on('show.bs.modal', function(event) {
	var button = $(event.relatedTarget);
	var input = button.data('whatever');
	var inputs=input.split(';');
	var empId=inputs[0];
	var empName=inputs[1];
	$('#employeeName').text(empName);
	//console.log(input);
	
	var tableData = "";
	$.ajaxRequest.ajax({
		type : "POST",
		url : "/PMS/mst/activeRolesByEmpId",
		data : {"encEmpId": empId},
		async:false,
		success : function(response) {
		//console.log(response);
		for(var i =0;i<response.length;i++){
			var row=response[i];
			var serialNo=i+1;
			if(row.primaryProject == 1)
			tableData+='<tr class="orange bg_grey"><td >'+serialNo+'</td>';
			else
				tableData+='<tr ><td >'+serialNo+'</td>';

			tableData+='<td >'+row.strProjectName+'</td>';
			tableData+='<td >'+row.strRoleName+'</td>';
			tableData+='<td >'+row.strStartDate+'</td>';
			if(row.strEndDate !=null){
				tableData+='<td >'+row.strEndDate+'</td>';
			}
			else{
				tableData+='<td></td>'
			}
			tableData+='<td class="hidden ">'+row.primaryProject+'</td>';

			tableData+='</tr>';
			//console.log(row.primaryProject);
			

		}
		
			tableData+='</tbody>';
			$('#datatable1 tbody').empty();						
			$('#datatable1').append(tableData);	
			
		},
		error : function(e) {
			alert('Error: ' + e);			
		}
	});				
});

function viewPrimaryRoleDetail(empId,projectId,roleId){
		 $.ajaxRequest.ajax({			
				type : "POST",
				url : "/PMS/mst/primaryRoleDetail",
				data:{"empId":empId,"projectId":projectId,"roleId":roleId},
			
				success : function(response) { 
					//console.log(response);
					 
					 if(response){
		   	        	  sweetSuccess('Attention',response);

		   	          }
					 if(response == "")
						 {
						 
						 sweetSuccess('Attention','You can not perform this action Employee present in other project');
						 var checkBox = 'primary_'+empId+"_"+roleId;
			 			 $('#'+checkBox).prop("checked", false);
						 
						 }
					
		   	         
		               	               
				},			
				error : function(e) {
					alert(e);
				}
			});  	
	}

function uncheckPrimaryRole(empId,projectId,roleId){
		 $.ajaxRequest.ajax({			
				type : "POST",
				url : "/PMS/mst/uncheckPrimaryRole",
				data:{"empId":empId,"projectId":projectId,"roleId":roleId},
			
				success : function(response) { 
		            	// console.log(response); 
		   	                  
		               	               
				},			
				error : function(e) {
					alert(e);
				}
			});  	
	}

$(document).on('click','#edit',function(e){
	var resultArray1 = $(this).closest('td').find('div').map( function()
			{
			return $(this).text();
			}).get();
	var ty=$(this).closest('div').find('input');
	var id=ty.val().split("_");
	$('.edit_'+id[1]+'_'+id[2]+'').removeClass('hidden');
	$('.show_'+id[1]+'_'+id[2]+'').addClass('hidden');
});
$(document).on('click','.cancel',function(e){
	var resultArray1 = $(this).closest('td').find('div').map( function()
			{
			return $(this).text();
			}).get();
	var ty=$(this).closest('td').find('input');
	var id=ty.val().split("_");
	$('.edit_'+id[1]+'_'+id[2]+'').addClass('hidden');
	$('.show_'+id[1]+'_'+id[2]+'').removeClass('hidden');
});
$(document).on('click','.Update',function(e){
	//var numRoleId = $('#numRoleId').val();
	var numProjectId = $('#numProjectId').val();
	var inputArray = [];
	var reqVal="";
	var fieldId="";
	var involvment="";
	var resultArray1 = $(this).closest('td').find('div').map( function()
			{
			return $(this).text();
			}).get();
	var ty=$(this).closest('td').find('input');
	var id=ty.val().split("_");
	var startDate=$('#cal_'+id[1]+'_'+id[2]+'').val();
	 if(startDate){
		}else{
			startDate=  $("#projectStartDate").text();
	  }
	 var numRoleId=$('#empRole_'+id[1]+'_'+id[2]+'').val();
	var endDate=$('#endCal_'+id[1]+'_'+id[2]+'').val();
	var mapping=$('#reqNumId_'+id[1]+'_'+id[2]+'').val();
	var mappingDetails=$( "#reqNumId_"+id[1]+"_"+id[2]+" option:selected" ).text();
	var primary=$('#primary_'+id[1]+'_'+id[2]+'').val();
	var involvement=$('#involve_'+id[1]+'_'+id[2]+'').val();
	if(mapping==0){
		sweetSuccess('Attention',"Please select role mapping");
	}
	else if(involvement==0 || involvement==''){
		sweetSuccess('Attention',"Involvelment should be greater than 0");
	}
	else{
	
	$.ajaxRequest.ajax({
        type: "POST",
        url: "/PMS/mst/saveprojectTeamMapping",
        data:{"numProjectId":numProjectId,
        "numId":id[2],
        "numEmpId":id[1],
        	"numRoleId":numRoleId,
        	"strStartDate":startDate,
        	"strEndDate":endDate,
        	"primaryProject":primary,
        	"numManReqId":mapping,
        	"strManReqDetails":mappingDetails,
        	"involvement":involvement},
        async:false,       		
		success : function(data) {
			if(data=="success"){
				sweetSuccess('Success'," Record updated successfully");
				var proj=$('#numProjectId').val();
				$('#numProjectId').val(proj);
				$('#numProjectId').trigger('change');
			}else if (data=="Fail"){
				sweetSuccess('Error',"Something went wrong");
				return false;
			}
			else {
				sweetSuccess('Attention',data);
				return false;
			}
			
		},error : function(data) {
			
		}
			
	});
}
});
$(document).on('click','.SaveNew',function(e){
	/*alert("in action");*/
	var numRoleId = $('#reqEmpRole').val();
	var numProjectId = $('#numProjectId').val();
	
	/*console.log("save new numRoleId numProjectId  "+numRoleId+"+"+ numProjectId)*/
	var startDate=startDate1;
	if(startDate){
	}else{
		startDate=$('#reqEmpId :selected').attr('label');
  }
	var endDate=endDate1;
	/*var mapping=$('#reqNumId').val();
	var mappingDetails=$( "#reqNumId option:selected" ).text();*/
	var mapping=mapid;
	var mappingDetails=mapdetails;
	var primary=$('#primary').val();
	var involvement=parseInt(involve, 10);
	
	if($('#reqEmpId').val()==0){
		sweetSuccess('Attention',"Please select employee");
	}
	else if(mapping==0){
		sweetSuccess('Attention',"Please select role mapping");
	}
	else if(involvement==0 || involvement==''){
		sweetSuccess('Attention',"Involvelment should be greater than 0");
	}
	else if(involvement==0 || involvement==''){
		sweetSuccess('Attention',"Involvelment should be greater than 0");
	}
	
	else if(!$.isNumeric(involvement)){
		sweetSuccess('Attention','Only Numeric value is allowed in Involvement');
	}
	else if(emprole==null||emprole== undefined){
		sweetSuccess('numRoleId is undefined');
		return false;
	}
	else {
		
		/*console.log(" mappingDetails... "+ mappingDetails)*/
		var resultText = $(this).closest('tr').html();
		/*console.log(resultText);*/
		/*alert("Mapping.... "+mapping);
		alert("reqEmpId.... "+$('#sp1').val());
		alert("numRoleId.... "+emprole);
		alert("startDate,... "+startDate);
		alert("end,... "+endDate);
		alert("primary,... "+primary);
		alert("mappingDetails,... "+mappingDetails);
		alert("involvement,... "+involvement);
		alert("numProjectId,... "+numProjectId);*/
		
	$.ajaxRequest.ajax({
        type: "POST",
        url: "/PMS/mst/saveprojectTeamMapping",
        data:{"numProjectId":numProjectId,
        "numId":0,
       "numEmpId":$('#sp1').val(),
       
        /*"numEmpId": empid,*/
        	"numRoleId":emprole,
        	"strStartDate":startDate,
        	"strEndDate":endDate,
        	"primaryProject":primary,
        	"numManReqId":mapping,
        	"strManReqDetails":mappingDetails,
        	"involvement":involvement},
        async:false,       		
		success : function(data)  {
			if(data=="success"){
				sweetSuccess('Success'," Record updated successfully");
				var proj=$('#numProjectId').val();
				$('#numProjectId').val(proj);
				$('#numProjectId').trigger('change');
			}else if (data=="Fail"){
				sweetSuccess('Error',"Something went wrong");
				return false;
			}
			else {
				sweetSuccess('Attention',data);
				return false;
			}
			
		},error : function(data) {
			
		}
			
	});
	console.log("ASdsa");
}
});

/*$(document).on('change','.reqSelect',function(e){	
if($(this).val()=='addRequirement'){
	// alert();
	 $("#Additionalinvolvement").val();
	 $('#modalAnchor').click();
	
}
});*/
/*$(document).on('change','#reqNumId',function(e){	
	if($(this).val()=='addRequirement'){
		// alert();
		 $("#Additionalinvolvement").val();
		 $('#modalAnchor').click();

	}
	});*/



$('#saveAdditionalRequirement').click(function(){
	var involvement = $('#Additionalinvolvement').val();
	//bhavesh 04-08-23 added idpurpose
	var idpurpose= $('#idPurpose').val();
	//bhavesh 04-08-23
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
	//bhavesh 04-08-23 the condition if (!idpurpose)
	else if (!idpurpose){
		console.log(idpurpose.length)
		sweetSuccess('Attention','Purpose is required must be less than or equals to 200 characters');
		return false;
	}
	//bhavesh 04-08-23
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
	else if(!$.isNumeric(involvement)){
		sweetSuccess('Attention','Only Numeric value is allowed in Involvement');
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
	    		//bhavesh 04-08-23 added purpose
	    		"purpose":$('#idPurpose').val(),
	    		//bhavesh 04-08-23
	    		"strDescription":$('#strDescription').val(),
	    		"numProjectRoles":$('#projectRole').val(),
	    		"designationId":$('#projectDesignations').val(),
	    		"actualRatePerManMonth":$('#actualCost').text(),//Added by devesh on 06-05-24 to save base cost in manpower requirement
	    		"ratePerManMonth":$('#ratePerManMonth').val(),
	    		"requiredType":1,
	    		"valid":"true",
	    		"involvement":$('#Additionalinvolvement').val()
	        	} ,			
			success : function(data) {
				if(data>0){
					 swal({
					      title: "Additional Requirement Created Successfully ",
					    }).then(function(isConfirm) {
					      if (isConfirm) {
					    	 /* Bhavesh(08-09-23) when click on ok button the page will be refresh to set the start and end date*/
					    	  location.reload();
					    	  /*Bhavesh(08-09-23)*/
					    	  console.log("bqnbw");
					    	  $('#cal').val($('#projectStartDate').text());
						    	$('#cal').trigger('input');
						    	$('#endCal').val($('#projectEndDate').text());
						    	$('#endCal').trigger('input');
						    	$('#involve').val('5');
					    	  //sweetSuccess('Success','Additional Requirement added successfully');	
								$('#closeModal').click();
								var projectId=$('#numProjectId').val();
								$('#numProjectId').change();
								//$('.reqSelect').val(data).trigger('change'); 
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
	$('#Additionalinvolvement').val('');
	 $("#idPurpose").val("");
	 $("#ratePerManMonth").val("");
	 
});

$(document).ready(function(){
	
	$('#projectDesignations, #deputedVal').change(function(){ //modified by devesh on 06-05-24 to bind event handler to deputedVal
		var designationId= $('#projectDesignations').val();
		var categoryId = $('#categoryId').val();
		var deputedAt = $('#deputedVal').val(); //Added by devesh on 06-05-24 to get deputed value
		if(designationId == 0){
			$('#actualCost').text(0);
			$('#actualRatePerManMonth').val(0);
		}else{
			
			//Added by devesh on 06-05-24 to match deputedAt value with project_category_designation_mapping table
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
					 "numDeputedAt": numDeputedAt //New parameter added by devesh on 06-05-24
					 
				 },
				 success: function(data) { 	
					 if(data.cost){
						 $('#actualCost').text(data.cost);
							$('#actualRatePerManMonth').val(data.cost);
							$("#ratePerManMonth").val($('#actualCost').text());
							
					 }
					 else{
						 $('#actualCost').text(0); 
					 }
				 }
			
			})
		}
	});
	

	
});

/*Bhavesh(07-08-23)added restoreprop function to reset values*/
function restoreprop(){
	
	$('#projectRole').val(37).trigger('change');
	$('#projectDesignations').val(8).trigger('change');
	$('#deputedVal').val(2).trigger('change');
	$('#strDescription').val('Responsible for the Overall Monitoring of the Project.');	
	$('#additionalEndDate').val($('#projectEndDate').text());
	$('#additionalEndDate').trigger('input');
	$('#additionalStartDate').val( $('#projectStartDate').text());
	$('#additionalStartDate').trigger('input');
	$('#Additionalinvolvement').val('5');
	 $("#idPurpose").val('Responsible for the Overall Monitoring of the Project.');
	
	
} 

/*Bhavesh(07-08-23)*/

/*Bhavesh(07-08-23)added Function to scroll to the top of the page*/
//Function to scroll to the top of the page
function scrollToTop() {
    window.scrollTo({
        top: 0,
        behavior: 'smooth' 
    });
}


// Attach scrollToTop function to the button's click event
const scrollButton = document.getElementById('projectRole');
const scrollButton1 = document.getElementById('additionalStartDate');
const scrollButton2 = document.getElementById('additionalEndDate');

scrollButton.addEventListener('click', scrollToTop);
scrollButton1.addEventListener('click', scrollToTop);
scrollButton2.addEventListener('click', scrollToTop);
/*scrollButton3.addEventListener('click', scrollToTop);*/

/*Bhavesh(07-08-23)*/


$(document).ready(function() {
	/*Bhavesh(09-08-23) set the value of start date and end date in new employee mapping if role is 'Head of Department' or 'Project In-charge' */
	var allRolesText = $('#allroles').text().trim();
	var values = allRolesText.split('\n'); // Split the text into an array of values

	if (values.length > 0) {
	  var firstValue = values[0].trim(); // Trim the first value
	  console.log("Trimmed First Value:", firstValue);

	  // Ignore the rest of the values if needed
	}
	/*if(firstValue=='Head of Department'||firstValue=='Project In-charge'){*/
		$('#cal').val($('#projectStartDate').text());
    	$('#cal').trigger('input');
    	$('#endCal').val($('#projectEndDate').text());
    	$('#endCal').trigger('input');
    	$('#involve').val('5');
/*	}
	else if(firstValue!='Head of Department'||firstValue!='Project In-charge'){*/
		/*$('#cal').val('');
		$('#cal').trigger('input');
		$('#endCal').val('');
    	$('#endCall').trigger('input');
    	$('#involve').val('');*/
	/*}
	*/

	
	
	
	
	$('#reqEmpRole').on('click', function() {
		var empRole=$('#reqEmpRole').val();
		
		/*if(empRole==15||empRole==4){*/
			$('#cal').val($('#projectStartDate').text());
	    	$('#cal').trigger('input');
	    	$('#endCal').val($('#projectEndDate').text());
	    	$('#endCal').trigger('input');
	    	$('#involve').val('5');
		/*}
		
		else if(empRole!=15||empRole!=4){*/
			
	    	$('#cal').val('');
	    	$('#cal').trigger('input');
	    	$('#endCal').val('');
	    	$('#endCall').trigger('input');
	    	$('#involve').val('');
		/*}*/
	    console.log($('#reqEmpRole').val());
	});
    
	$('#reqEmpId').on('click', function() {
		var empRole=$('#reqEmpRole').val();
		var empId=$('#reqEmpId').val();
		console.log('hdfjhfjh'+empRole);
		/*if((empRole==15&&empId)||(empRole==4&&empId)){*/
			$('#cal').val($('#projectStartDate').text());
	    	$('#cal').trigger('input');
	    	$('#endCal').val($('#projectEndDate').text());
	    	$('#endCal').trigger('input');
	    	$('#involve').val('5');
		/*}
		
		else if(empRole!=15||empRole!=4){
			*/
	    	$('#cal').val('');
	    	$('#cal').trigger('input');
	    	$('#endCal').val('');
	    	$('#endCall').trigger('input');
	    	$('#involve').val('');
		/*}*/
	   /* console.log($('#reqEmpRole').val());*/
	});
	
	/*Bhavesh(09-08-23)*/

	//*Bhavesh(08-08-23)Bind a click event to the button with data-toggle to autofill the data
	
    $('[data-toggle="modal"]').on('click', function() {
        
       
        $('#projectRole').val(37).trigger('change');
    	$('#projectDesignations').val(8).trigger('change');
    	$('#deputedVal').val(0).trigger('change');
    	$('#strDescription').val('Responsible for the Overall Monitoring of the Project.');	
    	$('#additionalEndDate').val($('#projectEndDate').text());
    	$('#additionalEndDate').trigger('input');
    	$('#additionalStartDate').val( $('#projectStartDate').text());
    	$('#additionalStartDate').trigger('input');
    	$('#Additionalinvolvement').val('5');
    	 $("#idPurpose").val('Responsible for the Overall Monitoring of the Project.');
    	 
    	
    });
});
/*Bhavesh(08-08-23)*/

function handleSelectChange() {
    var selectedValue = $('#numProjectId').val();
    console.log("Selected Value:", selectedValue);
    
    /*Bhavesh(09-08-23) set the value of start date and end date in new employee mapping if role is 'Head of Department' or 'Project In-charge' */
	var allRolesText = $('.allroles').text().trim();
	var values = allRolesText.split('\n'); // Split the text into an array of values

	if (values.length > 0) {
	  var firstValue = values[0].trim(); // Trim the first value
	  console.log("Trimmed First Value:", firstValue);

	  // Ignore the rest of the values if needed
	}
	/*if(firstValue=='Head of Department'||firstValue=='Project In-charge'){*/
		$('.cal').val($('#projectStartDate').text());
    	$('.cal').trigger('input');
    	$('.endCal').val($('#projectEndDate').text());
    	$('.endCal').trigger('input');
    	$('.involve').val('5');
/*	}
	else if(firstValue!='Head of Department'||firstValue!='Project In-charge'){
		$('#cal').val('');
		$('#cal').trigger('input');
		$('#endCal').val('');
    	$('#endCall').trigger('input');
    	$('#involve').val('');
	}
	*/
	
	
	
	
	$('#reqEmpRole').on('click', function() {
		var empRole=$('#reqEmpRole').val();
		
		/*if(empRole==15||empRole==4){*/
			$('#cal').val($('#projectStartDate').text());
	    	$('#cal').trigger('input');
	    	$('#endCal').val($('#projectEndDate').text());
	    	$('#endCal').trigger('input');
	    	$('#involve').val('5');
		/*}
		
		else if(empRole!=15||empRole!=4){*/
			
	    	/*$('#cal').val('');
	    	$('#cal').trigger('input');
	    	$('#endCal').val('');
	    	$('#endCall').trigger('input');
	    	$('#involve').val('');*/
		/*}*/
	    console.log($('#reqEmpRole').val());
	});
    
	$('#reqEmpId').on('click', function() {
		var empRole=$('#reqEmpRole').val();
		var empId=$('#reqEmpId').val();
		console.log('hdfjhfjh'+empRole);
		/*if((empRole==15&&empId)||(empRole==4&&empId)){*/
			$('#cal').val($('#projectStartDate').text());
	    	$('#cal').trigger('input');
	    	$('#endCal').val($('#projectEndDate').text());
	    	$('#endCal').trigger('input');
	    	$('#involve').val('5');
		/*}
		
		else if(empRole!=15||empRole!=4){*/
			
	    	/*$('#cal').val('');
	    	$('#cal').trigger('input');
	    	$('#endCal').val('');
	    	$('#endCall').trigger('input');
	    	$('#involve').val('');*/
		/*}*/
	   /* console.log($('#reqEmpRole').val());*/
	});
	
	/*Bhavesh(09-08-23)*/
    
    
}



