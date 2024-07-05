var numericRegex='';
var numericErrorMessage='';
	messageResource.init({	  
		filePath : '/PMS/resources/app_srv/PMS/global/js'
	});
	messageResource.load('regexValidationforjs', function(){
		  // get value corresponding  to a key from regexValidationforjs.properties  
		numericRegex= messageResource.get('amount.regex', 'regexValidationforjs');
		numericErrorMessage = messageResource.get('amount.regex.message', 'regexValidationforjs');
	});

$(document).ready(function() {
    $('.select2Option').select2({
    	 width: '100%'
    });
	$('#result').hide();

    populateMonts();
    
    $('#tempMonth').change(function(){    
    	var selectedMonth = $('#tempMonth').val();
		var radioValue = $("input[name='radioButton']:checked").val();		

    	$('#projectDetails').empty();
    	if(selectedMonth == 0){
    		$('#month').val(0);
    		$('#year').val(0);
    		clearForm(1);
    		$('#employeeId').val(0).trigger('change');
    	}else if(radioValue=="value1" ){
    		var temp = selectedMonth.split('_');
    		$('#month').val(temp[0]);
    		$('#year').val(temp[1]);
    		populateEmployeeProjectDetails(temp[0],temp[1]);
    		$('#employeeId').val(0).trigger('change');   		
    	}
    	else{
    		var temp = selectedMonth.split('_');
    		$('#month').val(temp[0]);
    		$('#year').val(temp[1]);
    	}
    });
    
    $('#employeeId').change(function(){
    	$('#projectDetails').empty();
    	var selectedEmployee = $('#employeeId').val();
		if(selectedEmployee){
    	if(selectedEmployee == 0){
    		clearForm(2);
    	}else{  
    		populateEmployeeSalary(selectedEmployee);
    		populateEmployeeProject(selectedEmployee);
    		
    	}
		}
    });

    
    $('#salaryByAuthority').blur(function(){    	
    	var salaryByAuthority = $('#salaryByAuthority').val();
		if(salaryByAuthority){
			if(validateNumeric(salaryByAuthority)){
				if(salaryByAuthority <= 0){
					sweetSuccess("Attention","Non zero value For Change in Salary is allowed");				
					return false;
				}else{
					$('.utilizationPercentage').each(function(){
						var fieldId = this.id;
						var fieldVal = this.value;
						//console.log(fieldId);
						//console.log(fieldVal);						
						calculateCostInProject(fieldId);						
					});
				}
			}else{
				$('#salaryByAuthority').val(0);
				sweetSuccess("Attention",numericErrorMessage +" For Change in Salary");				
				return false;
			}
			
		}
    	
    });
    
});

function populateMonts(){
	$('#tempMonth').empty();
	var options='<option value="0" selected="selected">-- Select Month --</option>';
    var now = new Date();
    var months = new Array("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
    for (var i = 0; i <= 11; i++) {
        var past = new Date(now); 
        past.setMonth(now.getMonth() - i);
        var displayText = months[past.getMonth()] + ' ' + past.getFullYear();
        var optionValue = (past.getMonth()+1)+"_"+past.getFullYear();
        options+="<option value="+optionValue+">"+displayText+" </option>"      
    }
    $('#tempMonth').append(options).trigger('change');
    
}


function clearForm(count){
	if(count==1){
		$('#employeeId').empty();
	}	
	$('#salaryBySystem').val('0.0');
	$('#salaryByAuthority').val('0.0');	
	$('#projectDetails').empty();
}

	function populateEmployeeProjectDetails(month,year){
		
		$.ajaxRequest.ajax({
		    type: "POST",
		    url: "/PMS/mst/getEmployeeFromProject",
		    data: {"month": month,
		    	"year":year	},
		    async:false,
		    success: function(response) {
		    	//console.log(response);
		    	$('#employeeId').empty();
		    	var employeeOptions="<option value='0'> -- Select Employee -- </option>";
		    	for(var i=0;i<response.length;i++){
		    		var rowData = response[i];
		    		if(rowData[2]){
		    			employeeOptions+="<option value='"+rowData[0]+"'>"+ rowData[1] +" - "+rowData[2]+" % </option>";	
		    		}else{
		    			employeeOptions+="<option value='"+rowData[0]+"'>"+ rowData[1]+"</option>";	
		    		}
		    			    		
		    	}
		    	$('#employeeId').append(employeeOptions);
		    	
		    },error: function(response){
		    	//console.log(response);
		    }
			
		});		
	}
	
	function populateEmployeeProject(employeeId){
		var selectedMonth = $('#tempMonth').val();
		var temp = selectedMonth.split('_');
		var month = temp[0];
		var year = temp[1];
		
		
		$.ajaxRequest.ajax({
		    type: "POST",
		    url: "/PMS/getUtilizationForEmployee",
		    data: {"month": month,
		    	"year":year,
		    	"employeeId":employeeId},
		    async:false,
		    success: function(response) {
		    	console.log(response);
		    	var projectRow="";
		    	for(var i=0;i<response.length;i++){
		    		var dataRow = response[i];
		    		if(i==0){
		    			$('#numId').val(dataRow[5]);
		    			if(dataRow[6] != 0){
		    				 				
		    				$('#salaryByAuthority').val(dataRow[6]).change();
		    			}
		    		}
		    		
		    		
		    		projectRow+="<tr> <td> <input type='hidden' id='utilizationDetails_"+i+".projectId' name='utilizationDetails["+i+"].projectId' value='"+dataRow[0]+"'/> ";
		    		projectRow+="<input type='hidden' id='utilizationDetails_"+i+".numId' name='utilizationDetails["+i+"].numId' value='"+dataRow[2]+"'/> "+ dataRow[1]+" </td> ";
		    		projectRow+=" <td> <input type='text' id='utilizationDetails_"+i+".utilization' name='utilizationDetails["+i+"].utilization' class='input-field utilizationPercentage' onblur='calculateCostInProject(this.id)' value='"+dataRow[3]+"'/> </td>";
		    		//projectRow+=" <td> <input type='text' class='input-field estimatedSalary'  id='utilizationDetails["+i+"].salaryInProject' name='utilizationDetails["+i+"].salaryInProject' value='"+dataRow[3]+"'/> </td> <td> <input type='text'  id='utilizationDetails["+i+"].utilization' name='utilizationDetails["+i+"].utilization' class='input-field'/></td></tr>";
		    		projectRow+=" <td> <input type='text' class='input-field estimatedSalary'  id='utilizationDetails_"+i+".salaryInProject' name='utilizationDetails["+i+"].salaryInProject' readonly='readonly' value='"+dataRow[4]+"'/> </td> </tr>";
		    	}
		    	$('#projectDetails').append(projectRow);
		    },error: function(response){
		    	//console.log(response);
		    }
			
		});	
		
	}
	
	function populateEmployeeSalary(employeeId){
		var selectedMonth = $('#tempMonth').val();
		var temp = selectedMonth.split('_');
		var month = temp[0];
		var year = temp[1];
		
		$.ajaxRequest.ajax({
		    type: "POST",
		    url: "/PMS/mst/getEmployeeSalary",
		    data: {"month": month,
		    	"year":year,
		    	"employeeId":employeeId
		    	},
		    async:false,
		    success: function(response) {
		    	//console.log(response);
		    	if(response == 0.0){
		    		$('#salaryBySystem').val(0.0);
		    		$('#salaryByAuthority').val(0.0);
		    		confirmWithRedirect("Attention","No Salary Detail found. Would you like to Add Salary Details?","success","/PMS/mst/EmployeeSalaryMaster","");
		    	}else{
		    		$('#salaryBySystem').val(response);
		    		$('#salaryByAuthority').val(response);
		    	}
		    	
		    },error: function(response){
		    	//console.log(response);
		    }
			
		});	
		
	}
	
	function calculateCostInProject(fieldId){
	//	console.log(fieldId);
		var salaryByAuthority = $('#salaryByAuthority').val();		
		if(salaryByAuthority){
			if(validateNumeric(salaryByAuthority)){
				
					var fieldVal = $('input[id^="'+fieldId+'"]').val();		
					var tempId = fieldId.split('.')[0];
					if(fieldVal){
						if(validateNumeric(fieldVal)){
							if(fieldVal>100){
								$('input[id^="'+fieldId+'"]').val(0).trigger('blur');
								sweetSuccess("Attention","Utilization can not be greater than 100%");
								return false;
							}else{
								var estimatedSalaryForProject = (salaryByAuthority*fieldVal)/100;							
								$('input[id^="'+tempId+'.salaryInProject"]').val(estimatedSalaryForProject);
							}
														
							
						}else{
							$('input[id^="'+fieldId+'"]').val(0);
							$('input[id^="'+tempId+'.salaryInProject"]').val(0);
							sweetSuccess("Attention",numericErrorMessage);
							return false;
						}
					}else{
						sweetSuccess("Attention","Value of Utilization is mandatory");
						return false;
					}
							
			}else{
				sweetSuccess("Attention",numericErrorMessage);
				return false;
			}
			
		}else{
			sweetSuccess("Attention","Change in Salary Field Value is mandatory");
			return false;
		}
		
	}
	
	function validateNumeric(amount){		
		numericRegex = new RegExp(numericRegex);			
		 if (numericRegex.test(amount)) {  
		    return (true)  ;
		  }else{
			  sweetSuccess("Attention",numericErrorMessage)  ;
			  return (false)  ; 
		  }		
	}
	

	$(document).ready(function() {
		$('#save').click(function(){
			var submitFlag= true;
			
			var month = $('#tempMonth').val();
			if(!month){
				sweetSuccess('Attention','Month is required');
				submitFlag= false;
				return false;
			}else if(month && month ==0){
				sweetSuccess('Attention','Month is required');
				submitFlag= false;
				return false;
			}
			
			var employeeId = $('#employeeId').val();
			if(!employeeId){
				sweetSuccess('Attention','Employee Name is required');
				submitFlag= false;
				return false;
			}else if(employeeId && employeeId==0){
				sweetSuccess('Attention','Employee Name is required');
				submitFlag= false;
				return false;
			}
			
			var salaryByAuthority = $('#salaryByAuthority').val();
			if(salaryByAuthority){
				if(validateNumeric(salaryByAuthority)){
					if(salaryByAuthority <= 0){
						sweetSuccess("Attention","Non zero value For Change in Salary is allowed");
						submitFlag= false;
						return false;
					}
				}else{
					sweetSuccess("Attention",numericErrorMessage +" For Change in Salary");
					submitFlag= false;
					return false;
				}
				
			}else{
				sweetSuccess('Attention','Change in Salary  is required');
				submitFlag= false;
				return false;
			}
			
			
			var projectsForEmployee= $("#projectDetails> tr").length;
			
			if(projectsForEmployee && projectsForEmployee!=0){
				var totalUtilization = 0;
				
				$('.utilizationPercentage').each(function(){
					var fieldValue = this.value;
					if(!fieldValue){
						sweetSuccess('Attention','Utilization is Required in all Row(s)');
						submitFlag= false;
						return false;
					}else{
						if(validateNumeric(fieldValue)){
							totalUtilization+=parseFloat(fieldValue);
						}else{
							sweetSuccess("Attention",numericErrorMessage +" For utilization");
							submitFlag= false;
							return false;
						}
					}
				})
					
			if(totalUtilization>100){
					sweetSuccess('Attention','Total Utilization can not be greater than 100');
					submitFlag= false;
					return false;
				}
				
				if(totalUtilization == 0){
					sweetSuccess('Attention','Please enter some Utilization');
					submitFlag= false;
					return false;
				}
				
				
			}else{
				sweetSuccess('Attention','Operation not allowed without Project');
				submitFlag= false;
				return false;
			}
			
			if(submitFlag){
				saveFormData();
			}else{
				console.log('Not Allowed');
			}
			
		});
		
	})
	
	function saveFormData(){
		var url = "/PMS/saveMonthlyUtilizationForEmployee";
		$.ajaxRequest.ajax({
		    type: "POST",
		    url: url,
		    data: $("#form1").serialize(),
		    async:false,
		    success: function(response) {
		    	//console.log(response);	
		    	if(response.length>0){
		    		if(response[0].indexOf("#") >= 0){		    			
		    			$('#errorMessage').empty();
		    			$('#successMessage').empty();
				    	var message = '';
				    	$.each(response, function(index, errorString){

		                    var array = errorString.split("#");
		                   // console.log(array);
		                   // console.log(index);
		                    if(index == 0){
		                    	message=array[1];
		                    }else{
		                    	message=message + '<br>'+array[1];
		                    }
				    	 });
				    	$('#errorMessage').html(message).show();
				    	 
				    	setTimeout(function(){ $("#errorMessage").hide('blind', {}, 500)}, 3000);
		                    
		    		}else{
		    			$('#errorMessage').empty();
		    			if(response[0] == "Success"){
		    				$('#errorMessage').empty();
		    				$('#successMessage').empty();
		    				
		    				var selectedMonth = $('#tempMonth').val();
		    				$('#tempMonth').val(selectedMonth).trigger('change');	
		    				
		    				$('#successMessage').text('Utilization saved successfully').show(); 
		    				
		    				setTimeout(function(){ $("#successMessage").hide('blind', {}, 500)	}, 3000);
		    				
		    			}
		    			
		    		}		    		
		    	}
		    },error: function(response){
		    	//console.log(response);
		    	        
		    }
			
		});	
	}
	
	
	$(function() {

		$('#submit').on('click', function(e) {
			var month = $('#tempMonth').val();
			var submitFlag= true;
			if(!month){
				sweetSuccess('Attention','Month is required');
				submitFlag= false;
				return false;
			}else if(month && month ==0){
				sweetSuccess('Attention','Month is required');
				submitFlag= false;
				return false;
			}
			if(submitFlag =true){
			var formData = new FormData();
			formData.append('utilizationDocumentFile', $('#utilizationDocumentFile')[0].files[0]);
			formData.append('month', $('#month').val());
			formData.append('year', $('#year').val());

		    
		    $.ajax({
		        type: "POST",
		        url: "/PMS/importUtilizationDetail",	        
		        data: formData,
		        processData: false,
	            contentType: false,
	            cache: false,
	            async: false,
		        success: function(data) {
		        	var responseMsg = '';
		        	for(var i =0;i<data.length;i++){
		    			var msg=data[i];
		    			responseMsg+='<tr><td>'+msg+'</td></tr>';	      
		        	}
		        	
		        	$('#responseTable').empty();
		        	$('#result').show();
		        	$('#responseTable').append(responseMsg);
			    	$('#tempMonth').val(0).trigger('change');
			    	$('#utilizationDocumentFile').val('');
		        },
		        error: function() {
		        	
		            alert('Error');
		        }
		    });
		    return false;
			}
		});
		
		$("input[name='radioButton']").click(function() {
			
		var radioValue = $("input[name='radioButton']:checked").val();		
		if(radioValue){
			if(radioValue=="value1"){
		    	$('#tempMonth').val(0).trigger('change');
				$('.importExcel').hide();				
				$('.individualUtilization').show();
				$('.selectMonth').show();

				
			}else{
		    	$('#tempMonth').val(0).trigger('change');
				$('.importExcel').show();
				$('.individualUtilization').hide();
				$('.selectMonth').show();

			}
		}
		});
		
		var defaultValue = $("input[name='radioButton']:checked").val();	
			if(!defaultValue){
				$('.importExcel').hide();
				$('.individualUtilization').hide();
				$('.selectMonth').hide();

			}
		});

