var numberRegex='';
var numberErrorMessage='';
var emailRegex ='';
var emailErrorMessage ='';
messageResource.init({	  
filePath : '/PMS/resources/app_srv/PMS/global/js'
});

messageResource.load('regexValidationforjs', function(){
	  // get value corresponding to a key from regexValidationforjs.properties
	numberRegex= messageResource.get('numeric.regex', 'regexValidationforjs');
	numberErrorMessage = messageResource.get('numeric.regex.message', 'regexValidationforjs');
	emailRegex = messageResource.get('email.regex', 'regexValidationforjs');
	emailErrorMessage = messageResource.get('email.regex.message', 'regexValidationforjs');
	});

$(document).ready(function(){
	$('#searchEmployee').click(function(){
		var validationFlag = true;
		var searchEmployeeId = $('#searchEmployeeId').val().trim();
		var searchEmployeeEmail = $('#searchEmployeeEmail').val().trim();
		var selectedVal=$("#searchDrop").val();
		
	/*	if(selectedVal=='0'){
			sweetSuccess('Attention','Please select any option for search');
			return false;
		}*/
		
	
		if(selectedVal=='EmpName'){
			var empName=$("#searchEmployeeName").val().trim();
			if(!empName && !empName){
				sweetSuccess('Attention','Please Enter Employee Name');
				return false;
			}
			$.ajaxRequest.ajax({
				type: "POST",
				url: "/PMS/mst/searchRelievedEmployeeDataByName",
				data: {"employeeName": empName,
						
						},
				success: function(data) {
					
					if(data.length>0){
						$("#empdetails").show();
					}
					else{
						sweetSuccess('Attention','Employee Details not found');
						$("#empdetails").hide();
						return false;
						
					}
					var tableData="";
					for(var i =0;i<data.length;i++){
						var row = data[i];
		  			    var sno=i+1;    			       			  
						tableData+="<tr><td><input type='radio' name='selectedEmp' onclick='getSelectedEmp()' value="+row.numId+"> </td><td>"+row.employeeName+"</td> <td>"+row.strDesignation+"</td><td>"+row.groupName+"</td><td>"+row.numId+"</td><td>"+row.officeEmail+"</td><td>"+row.dateOfRelease+"</td></tr>";

		 			} 
		 			tableData+='</tbody>';
		 			
					$('#datatable1 tbody').empty();						
					$('#datatable1').append(tableData);	
				}
			});
		}
		else if(selectedVal=='EmpId'){
			if(searchEmployeeId){
				if(!validateNumber(searchEmployeeId)){
					
					sweetSuccess('Attention',numberErrorMessage);
					$('#searchEmployeeId').addClass('red');
					return false;
				}
			}
			if(!searchEmployeeId ){
				validationFlag = false;
				sweetSuccess('Attention','Please Enter Employee Id');
				return false;
			}
			
			detailOfEmployee(searchEmployeeId,'',1);
		}
		else if(selectedVal=='Email'){
			
			$("#empdetails").hide();
			if(!searchEmployeeEmail){
				validationFlag = false;
				sweetSuccess('Attention','Please Enter Email');
				return false;
			}
			
		
			
			if(searchEmployeeEmail){
				if(!validateEmail(searchEmployeeEmail)){
					validationFlag = false;
					sweetSuccess('Attention',emailErrorMessage);
					$('#searchEmployeeEmail').addClass('red');
					return false;
				}
			}
		
		/*	if(validationFlag == true){
				// Load EmployeeDate
				if(!searchEmployeeId){
					searchEmployeeId = 0;
				}*/
				detailOfEmployee(0,searchEmployeeEmail,1);
			/*}*/
		}
		
	})
});

function validateNumber(value){		
	var numericRegex = new RegExp(numberRegex);			
	return numericRegex.test(value);
}

function validateEmail(email){		
	 var regex = new RegExp(emailRegex);	 
	    return regex.test(email);
}

	function detailOfEmployee(searchEmployeeId,searchEmployeeEmail,type){
		
		$.ajaxRequest.ajax({
			type: "POST",
			url: "/PMS/mst/searchRelievedEmployeeData",
			data: {"searchEmployeeId": searchEmployeeId,
					"searchEmployeeEmail": searchEmployeeEmail
					},
			success: function(response) {				
				$('#employeeDetails').addClass('hidden');
				if(!response){
					sweetSuccess('Attention','Employee Details not found');
					return false;
				}else if(response.hasOwnProperty('numId')){
					//Populate Employee Data
				/*	if(response.employmentStatus == 'Relieved'){
						sweetSuccess('Attention','Employee Already Released');
						return false;
					}*/
					$('#employeeId').val(response.numId);
					$('#employeeDetails').removeClass('hidden');
					if(type==1){
						$('#dataLoadedBasedOn').html(response.dataLoadedBasedOn);
					}
					else{
						$('#dataLoadedBasedOn').html('Employee Name');
					}
					$('#employeeName').html(response.employeeName);
					$('#emailId').html(response.officeEmail);
					$('#mobileNo').html(response.mobileNumber);
					$('#employmentstatus').html(response.employmentStatus);
					$('#dob').html(response.dateOfBirth);
					$('#doj').html(response.dateOfJoining);
					$('#dateOfRes').html(response.dateOfResignation);
					$('#dateOfRel').html(response.dateOfRelease);
					$('#remarks').html(response.releaseRemark);
					
					$('#groupName').html(response.groupName);
					
					$('#teamDetailsBody').empty();
					var assignedTeam = response.employeeRoleList;
					var teamDetails ='';
					for(var i=0; i< assignedTeam.length;i++){
						teamDetails+= '<tr><td> <input id="role_'+i+'" type="hidden" value="'+assignedTeam[i].encRoleId+'"/>';
						teamDetails+= '<input type="hidden" id="empId_'+i+'" value="'+assignedTeam[i].encEmployeeId+'"/>'+assignedTeam[i].strProjectName+'</td>';
						teamDetails+= '<td>'+assignedTeam[i].strRoleName+'</td>';
						teamDetails+= '<td class="startDate" id="startDate_'+i+'">'+ assignedTeam[i].strStartDate +'</td>'; 
						var endDate = assignedTeam[i].strEndDate;
						if(!endDate){
							endDate ='';
						}
						teamDetails+= '<td id="end_'+i+'">'+endDate+'</td></tr>';
						/*teamDetails+= '<td> <input type="hidden" id="empRole_'+i+'" value="'+assignedTeam[i].encId+'"/>';
						teamDetails+= '<input class="datePicker input-field" id="picker_'+i+'" readonly="readonly"></td></tr>';*/
					
					}
					$('#teamDetailsBody').append(teamDetails);
					initiateDatePickers();
					initiateCalendar();
					
				}else{
					sweetSuccess('Attention','Something went Wrong');
					return false;
				}
			}
		});
	}
	 
function initiateDatePickers(){
	var currentDate=new Date();
	var strDOJ = $('#doj').text().trim();
	var dojData = strDOJ.split('/');
	var dojMonth = parseInt(dojData[1])-1;
	var dateOfJoining = new Date(dojData[2],dojMonth,dojData[0]);
	 $('#dateOfResignation').datepicker({
		   dateFormat: 'dd/mm/yy', 
		    changeMonth: true,
		    changeYear:true, 
		    minDate : dateOfJoining,
		    maxDate : currentDate,
		    onSelect: function(date){		    	
		    	var dateData = date.split('/');
		    	var monthval = parseInt(dateData[1])-1;
		    	var selectedDate = new Date(dateData[2],monthval,dateData[0]);	 
	     
		        $("#dateOfRelease").datepicker( "option", "minDate",selectedDate );
		      
		        
		    }
	  });
	   
	   $('#dateOfRelease').datepicker({
		   dateFormat: 'dd/mm/yy', 
		   changeMonth: true,
		   changeYear:true,
		   maxDate: currentDate,
		   onSelect: function(date){
			   initiateCalendar();
		    }
	   });
}


function initiateCalendar(){
	
	var release= $('#dateOfRelease').val();
	var baseEndDate;
	var currentDate=new Date();
	if(release){
		var releaseParts = release.split("/");
		baseEndDate = new Date(+releaseParts[2], releaseParts[1] - 1, +releaseParts[0]);
		$(".datePicker").datepicker("destroy");
		$(".datePicker").each(function(index){
			var currentPickerValue = $('#picker_'+index).val();
				
			if(currentPickerValue){
				var endDateParts =currentPickerValue.split("/");
				var currentDatePicker = new Date(+endDateParts[2], endDateParts[1] - 1, +endDateParts[0]);
				if(currentDatePicker > baseEndDate){					
					$('#picker_'+index).val('');
				}
			}
		});
		
	}else{		
		var currentDate = new Date();	
		baseEndDate = currentDate;
		
	}

	$('.startDate').each(function(){
		var fieldId = this.id;
		var defaultDate;
		var selectId = fieldId.split('startDate_')[1];		
		var dateString = $('#'+fieldId).text().trim(); 
		
		var dateParts = dateString.split("/");		
		var dateObject= new Date(+dateParts[2], dateParts[1] - 1, +dateParts[0]);
		
		var endString = $('#end_'+selectId).text().trim();
		if(endString){
			var endParts = endString.split("/");		
			defaultDate= new Date(+endParts[2], endParts[1] - 1, +endParts[0]);
		}
		
		if(!defaultDate){
			defaultDate = baseEndDate;
		}
		
		$('#picker_'+selectId).datepicker({
			dateFormat:'dd/mm/yy',
			  changeMonth: true,
			  changeYear: true,
			  minDate: dateObject,
			  maxDate: baseEndDate,
			  defaultDate:defaultDate
			});
	});
	
}



function validateAndSubmitForm(){
	
	var submissionFlag = true;
	
	var releaseRemark = $('#releaseRemark').val().trim();
	if(!releaseRemark){
		sweetSuccess('Attention','Release Remark is required');
		submissionFlag = false;
		return false;
	}else{
		var releaseLength = releaseRemark.length;
		if(releaseLength > 2000){
			sweetSuccess('Attention','Only 2000 chars are allowed in Release Remark. Current length is '+releaseLength);
			submissionFlag = false;
			return false;
		}
	}

	
	if(submissionFlag == true){
		//Save Details
		var releaseDate = $('#dateOfRelease').val();	
		var resignationDate = $('#dateOfResignation').val();
		if(!resignationDate){
			resignationDate='';
		}
		if(!releaseDate){
			releaseDate='';
		}
		var employeeId = $('#employeeId').val();
		var teamArray = [];
		$('.datePicker').each(function(){
			 var rowObject = new Object();
			 var fieldId = this.id;
			 var tempId = fieldId.split('_');
			 var empRoleId = $('#empRole_'+tempId[1]).val();
			 
			 var fieldVal = this.value;			 
			 if(!fieldVal){
				 fieldVal = $('#end_'+tempId[1]).text().trim();
			 }			 
			
			 rowObject.empRoleId = empRoleId;
			 rowObject.releaseDate = fieldVal;
			 teamArray.push(rowObject);
		});
		var team = JSON.stringify(teamArray);
		
		
		var formData = new FormData();
		formData.append('dateOfResignation',resignationDate);
		formData.append('dateOfRelease',releaseDate);
		formData.append('releaseRemark',releaseRemark);
		formData.append("teamDetails",team);		
		formData.append("employmentStatus","Relieved");
		formData.append("numId",employeeId);
		
		
		
		$.ajax({
		    url: "/PMS/mst/releaseEmployee", 
		    type: "POST",		   
		    cache: false,
		    contentType: false,
		    processData: false,
		    data: formData,
			success :function(e){		    	            
		          if(e == true){		        	  
		        	  sweetSuccess('Attention', 'Employee Release Successfully');
		        	  setTimeout(function(){
		        		  location.reload(true);
		        	  },3000);
		        	 return true;		        	  
		          }else if(e == false){
		        	  sweetSuccess('Attention', 'Something went wrong');
		        	  return false;
		          }else{
		        	  sweetSuccess('Attention', 'You are not authorised');
		        	  return false;  
		          }
		          
		        }
		});		
	}	
}

$(document).ready(function(){
	$('#save').click(function(){
		validateAndSubmitForm();
	});
	/*$("#empIdDiv").hide();*/
	$("#emailDiv").hide();
	$("#empNameDiv").hide();
	$("#empdetails").hide();
});

function openSelSearch(){
	var selectedVal=$('#searchDrop').val();

	if(selectedVal=='EmpId'){
		$("#empdetails").hide();
		$("#empIdDiv").show();
		$("#emailDiv").hide();
		$("#empNameDiv").hide();
		$("#searchEmployeeEmail").val('');
		$("#searchEmployeeName").val('');
		$("#searchEmployeeId").val('');
		$('#employeeDetails').addClass('hidden');
	}
	else if(selectedVal=='Email'){
		$("#searchEmployeeId").val('');
		$("#searchEmployeeName").val('');
		$("#searchEmployeeEmail").val('');
		$("#empdetails").hide();
		$("#emailDiv").show();
		$("#empIdDiv").hide();
		$("#empNameDiv").hide();
		$('#employeeDetails').addClass('hidden');
	}
	else if(selectedVal=='EmpName'){
		$("#searchEmployeeEmail").val('');
		$("#searchEmployeeId").val('');
		$("#searchEmployeeName").val('');
		$("#empNameDiv").show();
		$("#emailDiv").hide();
		$("#empIdDiv").hide();
		$('#employeeDetails').addClass('hidden');
	}
}

function getSelectedEmp(){
	
	var selEmpId = $("input[name='selectedEmp']:checked").val();
	detailOfEmployee(selEmpId,'',2);
	
}