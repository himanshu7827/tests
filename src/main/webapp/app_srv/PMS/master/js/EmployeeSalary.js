var amountRegex='';
var amountRegexMessage='';

messageResource.init({	  
filePath : '/PMS/resources/app_srv/PMS/global/js'
});

messageResource.load('regexValidationforjs', function(){	
	amountRegex = messageResource.get('amount.regex', 'regexValidationforjs');
	amountRegexMessage = messageResource.get('amount.regex.message', 'regexValidationforjs');
	});

function  empDetails(empId){	
	var tableData = "";
	$.ajaxRequest.ajax({
				 type: "POST",
				 url: "/PMS/mst/EmployeeSalaryData",
				 data: "employeeId="+ empId,
				 success: function(data) { 	  
						for(var i =0;i<data.length;i++){
							var row = data[i];
							var recordStatus= '';
							if(row.valid==true){
								recordStatus='Active';
									}else{recordStatus='Inactive';
										}
							tableData+="<tr><td><input type='checkbox' class='CheckBox' id='CheckBox' name='idCheck' value="+row.numId+" />"+row.numId+"</td> row.numId" +
									"<td>"+row.startDate+"</td>";
							if(row.endDate){
								tableData+="<td>"+row.endDate+"</td> ";
							}else{
								tableData+="<td></td> ";
							}
							
							tableData+="<td>"+row.salary+"</td> <td>"+recordStatus+"</td> <td><i class='fa fa-pencil-square-o btn btn-primary a-btn-slide-text' id='edit' /></td> </tr>"
									
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
			 
			 $('#datatable').removeClass('hidden');
			$('#salaryDetails').removeClass('hidden');
			
		 	}

$(document).ready(function() {
	$('.select2Option').select2({width:"100%"});
	$('#result').hide();
	$('#salaryTable').hide();


	$('#example').DataTable();
		 $('#userinfo-message').delay(5000).fadeOut();
		 
		 var table = $('#datatable').DataTable();
			table.destroy();
 
		 $(document).on('change','#employeeId',function(e){
				var employeeId = $(this).find(":selected").val();
				empDetails(employeeId);
			}); 
			 
			 	  });


	$(document).on('click','#edit',function(e){
		
		var resultArray = $(this).closest('tr').find('td').map( function()
				{
				return $(this).text();
				}).get();
		console.log(resultArray);
		$('#numId').val(resultArray[0].trim());
		/*$('#employeeId').val(resultArray[0].trim());*/	
		$('#startDate').val(resultArray[1].trim()).trigger('change');	
		$('#endDate').val(resultArray[2].trim()).trigger('change');	
		$('#salary').val(resultArray[3].trim());	
		if(resultArray[4].trim()=='Active'){
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
	    	  startDate: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Start Date is required and cannot be empty'
	                  },
	                 
	              }
	          },
	          
	          salary: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Salary required and cannot be empty'
	                  },
	                  regexp: {
	                        regexp: amountRegex,
	                       message: amountRegexMessage
	                    },
	                    stringLength: {
	                        max: 9,
	                        message: 'salary must be less than 9 digits'
	                    }
	                  
	              }
	          },employeeId:{
	        	  group: '.col-md-12',
	        	  validators: {
	                  callback: {
	                      message: 'Please choose Employee Name',
	                      callback: function(value, validator) {
	                          // Get the selected options
	                          var options = validator.getFieldElements('employeeId').val();
	                          return (options != 0);
	                      }
	                  }
	        	  }
	          }
	          
		      
		         
	      }
	  });
	
	   $('#startDate').datepicker({
		   dateFormat: 'dd/mm/yy', 
		    changeMonth: true,
		    changeYear:true, 
		    onSelect: function(date){
		    	$('#startDate').trigger('input');
		    	var dateData = date.split('/');
		    	var monthval = parseInt(dateData[1])-1;
		        var selectedDate = new Date(dateData[2],monthval,dateData[0]);       
		        console.log(selectedDate);
		       //Set Minimum Date of EndDatePicker After Selected Date of StartDatePicker
		        $("#endDate").datepicker( "option", "minDate", selectedDate );
		        $("#endDate").datepicker( "option", "maxDate", '+5y' );
		    }
	   });
	   
	   $('#endDate').datepicker({
		   dateFormat: 'dd/mm/yy', 
		    changeMonth: true,
		    changeYear:true,  
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
	$('#numId').val('');
	$('#employeeId').val("0").trigger('change');
	$('#startDate').val('');
	$('#endDate').val('');
	$('#salary').val('');
	$('#save').text('Save');
}

$(function() {

	$('#submit').on('click', function(e) {
		var formData = new FormData();
		formData.append('salaryDocumentFile', $('#salaryDocumentFile')[0].files[0]);
		
	    
	    $.ajax({
	        type: "POST",
	        url: "/PMS/mst/importSalaryDetail",	        
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
	        },
	        error: function() {
	        	
	            alert('Error');
	        }
	    });
	    return false;
	});
	
	$("input[name='radioButton']").click(function() {
	var radioValue = $("input[name='radioButton']:checked").val();
	if(radioValue){
		if(radioValue=="value1"){
			$('#form1').removeClass('hidden');
			$('#form').addClass('hidden');
			$('#salaryTable').show();
			$('#result').hide();


		}else{
			$('#form').removeClass('hidden');
			$('#form1').addClass('hidden');
			$('#salaryTable').hide();

		}
	}
	});
	

	
	});

