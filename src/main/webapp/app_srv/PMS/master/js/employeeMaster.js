var numericRegex='';
var numericErrorMessage='';
var nameRegex='';
var nameErrorMessage='';
var mobileRegex='';
var mobileErrorMessage='';
var emailRegex='';
var emailErrorMessage='';
messageResource.init({	  
filePath : '/PMS/resources/app_srv/PMS/global/js'
});

messageResource.load('regexValidationforjs', function(){
	  // get value corresponding  to a key from regexValidationforjs.properties  
	numericRegex= messageResource.get('numeric.regex', 'regexValidationforjs');
	numericErrorMessage = messageResource.get('numeric.regex.message', 'regexValidationforjs');
	nameRegex= messageResource.get('name.regex', 'regexValidationforjs');
	nameErrorMessage = messageResource.get('name.regex.message', 'regexValidationforjs');
	emailRegex= messageResource.get('email.regex', 'regexValidationforjs');
	emailErrorMessage = messageResource.get('email.regex.message', 'regexValidationforjs');
	mobileRegex= messageResource.get('mobileno.regex', 'regexValidationforjs');
	mobileErrorMessage = messageResource.get('mobileno.regex.message', 'regexValidationforjs');
	});

$(document).ready(function() {
    $('.select2Option').select2({
    	 width: '100%'
    });
    
	$('#result').hide();
    $('#empExistDiv').hide();
    $('#reset').click(function(){
		resetForm();
		 $('#form1').data('bootstrapValidator').resetForm(true);
	});
    
    $('#form0').bootstrapValidator({
//	      live: 'disabled',
		excluded: ':disabled',
	      message: 'This value is not valid',
	      feedbackIcons: {
	          valid: 'glyphicon glyphicon-ok',
	          invalid: 'glyphicon glyphicon-remove',
	          validating: 'glyphicon glyphicon-refresh'
	      },
	      fields: {
	    	  searchEmployeeId: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Corporate Employee Id is required and cannot be empty'
	                  },
	                  stringLength: {
	                        max: 10,
	                        message: 'Corporate Employee Id must be less than 10 digits'
	                    },
	                  regexp: {
	                        regexp: numericRegex,
	                       message: numericErrorMessage
	                    }
	              }
	          }
	      }
	      })
    
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
	    	  
	    	  numId: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Corporate Employee Id is required and cannot be empty'
	                  }, 
	                  stringLength: {
	                        max: 10,
	                        message: 'Corporate Employee Id must be less than 10 digits'
	                    },
	                  regexp: {
	                        regexp: numericRegex,
	                       message: numericErrorMessage
	                    }
	              }
	          },
	          
	          employeeName: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Employee Name is required and cannot be empty'
	                  }, 
	                  stringLength: {
	                        max: 150,
	                        message: 'Employee name must be less than 150 characters'
	                    },
	                  regexp: {
	                        regexp: nameRegex,
	                       message: nameErrorMessage
	                    }
	              }
	          },
	          
	          officeEmail: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Office Email is required and cannot be empty'
	                  }, 
	                  stringLength: {
	                        max: 100,
	                        message: 'Office Email must be less than 100 characters'
	                    },
	                  regexp: {
	                        regexp: emailRegex,
	                       message: emailErrorMessage
	                    }
	              }
	          },
	          alternateEmail: {
	              group: '.col-md-12',
	              validators: { 
	                  stringLength: {
	                        max: 100,
	                        message: 'Alternate Email must be less than 100 characters'
	                    },
	                  regexp: {
	                        regexp: emailRegex,
	                       message: emailErrorMessage
	                    }
	              }
	          },
	          mobileNumber: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Mobile Number is required and cannot be empty'
	                  }, 
	                  stringLength: {
	                        max: 15,
	                        message: 'Mobile Number must be less than 15 digits'
	                    },  
	                  regexp: {
	                        regexp: mobileRegex,
	                       message: mobileErrorMessage
	                    }
	              }
	          },
	          contactNumber:{
	        	  group: '.col-md-12',
	        	  validators: {
	        	  stringLength: {
                      max: 15,
                      message: 'Contact Number must be less than 15 digits'
                  }
	        	  }
	          },
	          
	          dateOfBirth: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Date Of Birth is required and cannot be empty'
	                  },    
	              }
	          },
	         
	          dateOfJoining: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Date Of Joining is required and cannot be empty'
	                  },    
	              }
	          },
	          
	          gender:{
	        	  group: '.col-md-12',
	        	  validators: {
	                  callback: {
	                      message: 'Please choose Gender',
	                      callback: function(value, validator) {
	                          // Get the selected options
	                          var options = validator.getFieldElements('gender').val();
	                          return (options != 0);
	                      }
	                  }
	              }
	          },
	          
	         
	      
	          designation:{
	        	  group: '.col-md-12',
	        	  validators: {
	                  callback: {
	                      message: 'Please choose Designation',
	                      callback: function(value, validator) {
	                          // Get the selected options
	                          var options = validator.getFieldElements('designation').val();
	                          return (options != 0);
	                      }
	                  }
	              }
	          },
	          
	         /* postId:{
	        	  group: '.col-md-12',
	        	  validators: {
	                  callback: {
	                      message: 'Please choose Post',
	                      callback: function(value, validator) {
	                          // Get the selected options
	                          var options = validator.getFieldElements('postId').val();
	                          return (options != 0);
	                      }
	                  }
	              }
	          },*/
	          organisationId:{
	        	  group: '.col-md-12',
	        	  validators: {
	                  callback: {
	                      message: 'Please choose Organisation',
	                      callback: function(value, validator) {
	                          // Get the selected options
	                          var options = validator.getFieldElements('organisationId').val();
	                          return (options != 0);
	                      }
	                  }
	              }
	          },
	          
	          groupId:{
	        	  group: '.col-md-12',
	        	  validators: {
	                  callback: {
	                      message: 'Please choose Group Name',
	                      callback: function(value, validator) {
	                          // Get the selected options
	                          var options = validator.getFieldElements('groupId').val();
	                          return (options != 0);
	                      }
	                  }
	              }
	          },
	         
	          
	          
	          employeeTypeId:{
	        	  group: '.col-md-12',
	        	  validators: {
	                  callback: {
	                      message: 'Please choose Employee Type',
	                      callback: function(value, validator) {
	                          // Get the selected options
	                          var options = validator.getFieldElements('employeeTypeId').val();
	                          return (options != 0);
	                      }
	                  }
	              }
	          },
	          
	          thirdPartyId:{
	        	  group: '.col-md-12',
	        	  validators: {
	                  callback: {
	                      message: 'Please choose Outsource Type',
	                      callback: function(value, validator) {
	                          var options = validator.getFieldElements('thirdPartyId').val();

	                          return (options != 0);
	                 
	                      }
	                  }
	              }
	          },
	          
	         /* roles:{
	        	  group: '.col-md-12',
	        	  validators: {
	                  callback: {
	                      message: 'Please choose Roles between 1 to 30',
	                      callback: function(value, validator) {
	                          // Get the selected options
	                          var options = validator.getFieldElements('roles').val();
	  	                      return (options.length >= 1 && options.length<=30);
	                      }
	                  }
	              }
	          },*/
	          
	         
	          
	          category:{
	        	  group: '.col-md-12',
	        	  validators: {
	                  callback: {
	                      message: 'Please choose Category',
	                      callback: function(value, validator) {
	                          // Get the selected options
	                          var options = validator.getFieldElements('category').val();
	                          return (options != 0);
	                      }
	                  }
	              }
	          },
	          
	          employmentStatus:{
	        	  group: '.col-md-12',
	        	  validators: {
	                  callback: {
	                      message: 'Please choose Employment Status',
	                      callback: function(value, validator) {
	                          // Get the selected options
	                          var options = validator.getFieldElements('employmentStatus').val();
	                          return (options != 0);
	                      }
	                  }
	              }
	          },
	          dateOfRelease:{
	        	  validators: {
	                    notEmpty: {
	                        message: 'The Release date is required and cannot be empty'
	                    },
	        	  date:{
	        	  format: 'DD/MM/YYYY'
	        	  }
	          }
	          },
	          dateOfResignation:{
	        	  validators: {
	                    notEmpty: {
	                        message: 'The Resignation date is required and cannot be empty'
	                    },
	        	  date:{
	        	  format: 'DD/MM/YYYY'
	        	  }
	          }
	          },
	          dtContractStartDate:{
	        	  validators: {
	                    notEmpty: {
	                        message: 'The Contract Start date is required and cannot be empty'
	                    },
	        	  date:{
	        	  format: 'DD/MM/YYYY'
	        	  }
	          }
	          },
	          dtContractEndDate:{
	        	  validators: {
	                    notEmpty: {
	                        message: 'The Contract End date is required and cannot be empty'
	                    },
	        	  date:{
	        	  format: 'DD/MM/YYYY'
	        	  }
	          }
	          },
	          numDeputedAt:{
	        	  group: '.col-md-12',
	        	  validators: {
	                  callback: {
	                      message: 'Please select Deputation',
	                      callback: function(value, validator) {
	                          // Get the selected options
	                          var options = validator.getFieldElements('numDeputedAt').val();
	                          return (options != 0);
	                      }
	                  }
	              }
	          }
	          
	          
	      }
	  });

    
   $('#dateOfBirth').datepicker({
		   dateFormat: 'dd/mm/yy', 
		    changeMonth: true,
		    changeYear:true, 
		    maxDate:"-18y",
		    yearRange: '-65y:c+nn',
		 
		    onSelect: function(date){
		    	$('#dateOfBirth').trigger('input');
		    	var dateData = date.split('/');
		    	var monthval = parseInt(dateData[1])-1;
		    	 var selectedDate = new Date(dateData[2],monthval,dateData[0]);
		        var year = selectedDate.getFullYear();
		        var month = selectedDate.getMonth();
		        var day = selectedDate.getDate();
		        var dt = new Date(year + 18, month, day);
		        var currentDate=new Date();
           
                 var range=dt.getFullYear() +':'+currentDate.getFullYear();
		       //Set Minimum Date of EndDatePicker After Selected Date of StartDatePicker
		       // $("#dateOfJoining").datepicker( "option", "yearRange",range );
		        $("#dateOfJoining").datepicker( "option", "minDate", dt );
		        //$("#dateOfJoining").datepicker( "option", "maxDate", currentDate );
		    }
		   
   });
   

   $('#dateOfJoining').datepicker({
	       /* yearRange: '-47y:c+nn',*/
	        dateFormat: 'dd/mm/yy', 
		    changeMonth: true,
		    changeYear:true,
		    maxDate:new Date(),
		    onSelect: function(date){
		    	$('#dateOfJoining').trigger('input');
		    	var dateData = date.split('/');
		    	var monthval = parseInt(dateData[1])-1;
		    	 var selectedDate = new Date(dateData[2],monthval,dateData[0]);
		    	 var currentDate=new Date();
          
		       //Set Minimum Date of EndDatePicker After Selected Date of StartDatePicker
		        $("#dateOfResignation").datepicker( "option", "minDate",selectedDate );
		        $("#dateOfResignation").datepicker( "option", "maxDate", currentDate );
		        $("#dtContractStartDate").datepicker( "option", "minDate",selectedDate );

		        
		    }
	   });
   
  
   
   
   $('#dateOfResignation').datepicker({
	   dateFormat: 'dd/mm/yy', 
	    changeMonth: true,
	    changeYear:true, 
	    onSelect: function(date){
	    	$('#dateOfResignation').trigger('input');
	    	var dateData = date.split('/');
	    	var monthval = parseInt(dateData[1])-1;
	    	 var selectedDate = new Date(dateData[2],monthval,dateData[0]);
	    	 var currentDate=new Date();
     
	       //Set Minimum Date of EndDatePicker After Selected Date of StartDatePicker
	        $("#dateOfRelease").datepicker( "option", "minDate",selectedDate );
	        $("#dateOfRelease").datepicker( "option", "maxDate", currentDate );
	        
	    }
  });
   
   $('#dateOfRelease').datepicker({
	   dateFormat: 'dd/mm/yy', 
	   changeMonth: true,
	   changeYear:true,
	   onSelect: function(date){
	    	$('#dateOfRelease').trigger('input');
	    	}
   });
   
   $('#dtContractStartDate').datepicker({
	   dateFormat: 'dd/mm/yy', 
	   changeMonth: true,
	   changeYear:true,
	   onSelect: function(date){
	    	$('#dtContractStartDate').trigger('input');
	    	var dateData = date.split('/');
	    	var monthval = parseInt(dateData[1])-1;
	    	 var selectedDate = new Date(dateData[2],monthval,dateData[0]);
	    	 var currentDate=new Date();
   
	       //Set Minimum Date of EndDatePicker After Selected Date of StartDatePicker
	        $("#dtContractEndDate").datepicker( "option", "minDate",selectedDate );
	        
	    }
   });
   
   $('#dtContractEndDate').datepicker({
	   dateFormat: 'dd/mm/yy', 
	   changeMonth: true,
	   changeYear:true,
	   onSelect: function(date){
	    	$('#dtContractEndDate').trigger('input');
	    	}
	  
   });
   
   
   $('#save').click(function(){	
	  

	 /*  var istoggle="";
	   	 if($("#toggle-on").prop("checked")==true) {	   		 
	   		 istoggle=true;
	   	}
	   	 else if($("#toggle-off").prop("checked")==true){
	   	  istoggle=false;
	   	 }*/
	   	 $('#valid').val(true);
	   	 
	   	var employmentStatus= $('#employmentStatus').val();
		var employeeTypeId = $('#employeeTypeId').val();
	   	if(employeeTypeId=='4'){
	   	 $('#form1').bootstrapValidator('enableFieldValidators', 'thirdPartyId',true);
	   	}else{
		   	 $('#form1').bootstrapValidator('enableFieldValidators', 'thirdPartyId',false);

	   	}
	     if(employmentStatus == 'Relieved' || employmentStatus == 'Notice Period'){ 		  
			 
			    $('#form1').bootstrapValidator('enableFieldValidators', 'dateOfResignation', true);
			    $('#form1').bootstrapValidator('enableFieldValidators', 'dateOfRelease', true);
			 
		  }
		  else{
			  $('#form1').bootstrapValidator('enableFieldValidators', 'dateOfResignation', false);
			  $('#form1').bootstrapValidator('enableFieldValidators', 'dateOfRelease', false);
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
	   
		/*if($('#form1').form('validate')){				
				document.getElementById("form1").submit();
				resetForm();
		   }*/
   });
   
});

function resetForm(){
	$('#numId').val(0);
	$('#numId').attr("readonly", false); 
 	$('#searchEmployeeId').val('');
	$('#employeeName').val('');
	$('#officeEmail').val('');
	$('#alternateEmail').val('');
	$('#mobileNumber').val('');
	$('#contactNumber').val('');
	$('#dateOfBirth').val('');
	$('#dateOfJoining').val('');
	$('#dateOfResignation').val('');
	$('#dateOfRelease').val('');
	$('#organisationId').val(0).trigger('change');
	$('#employeeTypeId').val(0).trigger('change');
	$('#thirdPartyId').val(0).trigger('change');
	$('#gender').val(0).trigger('change');
	$('#postId').val(0).trigger('change');
	$('#designation').val(0).trigger('change');
	$('#groupId').val(0).trigger('change');
	$('#roles').val('').trigger('change');
	$('#category').val(0).trigger('change');
	$('#employmentStatus').val(0).trigger('change');
	$('#updateFlag').val(0);
	$('#numDeputedAt').val(0).trigger('change');
	$('#save').text('Save');
 	
}

$(document).on('change','#organisationId',function(e){
	var orgId = $(this).find(":selected").val();
	groupdetail(orgId);
  });  

function groupdetail(organisationId){
	$.ajaxRequest.ajax({
	    type: "POST",
	    url: "/PMS/mst/GroupData",
	    data: {"organisation": organisationId},
	    async:false,
	    success: function(response) {
		
	    	$('#groupId').find('option').remove();
	    	var seloption = "";
	  	seloption += "<option value='0'>-- Select Group --</option>";
	
	  	for(var i=0;i<response.length;i++){
	  		
	  		var rowData = response[i];
	  		seloption += "<option value="+rowData.numId+">"+rowData.groupName+"</option>";
	  	}
	     	$('#groupId').append(seloption);
	       	$("#groupId").trigger("change");
	    }
	});
}

/*$(document).on('click','#searchEmployee',function(e){

	var employeeId = $("#searchEmployeeId").val();	
	console.log(employeeId);
	detailOfEmployee(employeeId);
}); */ 

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
				url: "/PMS/mst/searchEmployeeDataByName",
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
						tableData+="<tr><td><input type='radio' name='selectedEmp' onclick='getSelectedEmp()' value="+row.numId+"> </td><td>"+row.employeeName+"</td> <td>"+row.strDesignation+"</td><td>"+row.groupName+"</td><td>"+row.numId+"</td><td>"+row.officeEmail+"</td></tr>";

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
			
			detailOfEmployee(searchEmployeeId);
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
			detailOfEmployeeByEmailId(searchEmployeeEmail);
			/*}*/
		}
		
	})
});

function detailOfEmployeeByEmailId(employeeEmailId){

$.ajaxRequest.ajax({
 type: "POST",
 url: "/PMS/mst/EmployeeDataByEmailId",
 data: {"officeEmail": employeeEmailId},
 success: function(response) {	 
	 if(response.numId == null){
		 $('#empExistDiv').show();		 
	 }
	 else{

	 $('#form1').data('bootstrapValidator').resetForm(true);
	 $('#empExistDiv').hide();
 $('#numId').prop("readonly","readonly");

$('#employeeName').val(response.employeeName);
$('#officeEmail').val(response.officeEmail);
$('#alternateEmail').val(response.alternateEmail);
$('#mobileNumber').val(response.mobileNumber);
$('#contactNumber').val(response.contactNumber);
$('#dateOfBirth').val(response.dateOfBirth);
$('#dateOfJoining').val(response.dateOfJoining);
$('#gender').val(response.gender).trigger('change');
$('#designation').select2('destroy');
$('#designation').val(response.designation).select2({
	 width: '100%'
});
designationDetail(response.designation);
$('#organisationId').val(response.organisationId).select2({
	 width: '100%'
});
groupdetail(response.organisationId);
//$('#organisationId').val(response.organisationId).trigger('change');
$('#groupId').val(response.groupId).trigger('change');
$('#postId').val(response.postId).trigger('change');
$('#employeeTypeId').val(response.employeeTypeId).trigger('change');
$('#thirdPartyId').val(response.thirdPartyId).trigger('change');
$('#roles').val(response.roles).trigger('change');
$('#category').val(response.category).trigger('change');
$('#employmentStatus').val(response.employmentStatus).trigger('change');
$('#dateOfResignation').val(response.dateOfResignation);
$('#dateOfRelease').val(response.dateOfRelease);
$('#dtContractStartDate').val(response.dtContractStartDate);
$('#dtContractEndDate').val(response.dtContractEndDate);
$('#numDeputedAt').val(response.numDeputedAt).trigger('change');
$('#numId').val(response.numId);
$('#updateFlag').val(1);
/*$('#valid').val(response.valid).toggle();*/
		$('select.select2Option').select2({width: '100%'});
		if(response.valid=='1'){
			$('#toggle-off').removeAttr('checked');
			$('#toggle-on').attr('checked',true);

		}else{
			$('#toggle-on').removeAttr('checked');
			$('#toggle-off').attr('checked',true);
		}
		
		designationDetail(response.designation);
	 }	
	 
 }		
 
});



}

function detailOfEmployee(employeeId){

	if($.isNumeric(employeeId)){
		
	
	
	$.ajaxRequest.ajax({
 type: "POST",
 url: "/PMS/mst/EmployeeData",
 data: {"searchEmployeeId": employeeId},
 success: function(response) {	 
	 if(response.numId == null){
		 $('#empExistDiv').show();		 
	 }
	 else{

		 $('#form1').data('bootstrapValidator').resetForm(true);
		 $('#empExistDiv').hide();
 $('#numId').prop("readonly","readonly");

$('#employeeName').val(response.employeeName);
$('#officeEmail').val(response.officeEmail);
$('#alternateEmail').val(response.alternateEmail);
$('#mobileNumber').val(response.mobileNumber);
$('#contactNumber').val(response.contactNumber);
$('#dateOfBirth').val(response.dateOfBirth);
$('#dateOfJoining').val(response.dateOfJoining);
$('#gender').val(response.gender).trigger('change');
$('#designation').select2('destroy');
$('#designation').val(response.designation).select2({
	 width: '100%'
});
designationDetail(response.designation);
$('#organisationId').val(response.organisationId).select2({
	 width: '100%'
});
groupdetail(response.organisationId);
//$('#organisationId').val(response.organisationId).trigger('change');
$('#groupId').val(response.groupId).trigger('change');
$('#postId').val(response.postId).trigger('change');
$('#employeeTypeId').val(response.employeeTypeId).trigger('change');
$('#thirdPartyId').val(response.thirdPartyId).trigger('change');
$('#roles').val(response.roles).trigger('change');
$('#category').val(response.category).trigger('change');
$('#employmentStatus').val(response.employmentStatus).trigger('change');
$('#dateOfResignation').val(response.dateOfResignation);
$('#dateOfRelease').val(response.dateOfRelease);
$('#dtContractStartDate').val(response.dtContractStartDate);
$('#dtContractEndDate').val(response.dtContractEndDate);
$('#numDeputedAt').val(response.numDeputedAt).trigger('change');
$('#numId').val(response.numId);
$('#updateFlag').val(1);
/*$('#valid').val(response.valid).toggle();*/
		$('select.select2Option').select2({width: '100%'});
		if(response.valid=='1'){
			$('#toggle-off').removeAttr('checked');
			$('#toggle-on').attr('checked',true);

		}else{
			$('#toggle-on').removeAttr('checked');
			$('#toggle-off').attr('checked',true);
		}
		
		designationDetail(response.designation);
	 }	
	 
 }		
 
});
}else{
	sweetSuccess('Attention','Not a valid Input');
	return false;
	}


}
$(document).ready(function() {
	$('#thirdPartyDiv').hide();
	var isThirdPartySpecific=0;
    $('#employeeTypeId').change(function(){
    	var employeeTypeId = $('#employeeTypeId').val();
    	isThirdPartySpecific=0;
	    if(employeeTypeId == '4'){ 
	    	isThirdPartySpecific=1;
	    $('#employeeIdDiv').hide();
	    $('#thirdPartyDiv').show();
	    $.ajax({
			type:"POST",
			 async:false,
			url:"/PMS/mst/getAllThirdParty",		
			success:function(response){
				//console.log(response);
				var options = '<option value="0"> -- Select -- </option>';
				
					for(var i =0;i<response.length;i++){
						var dataRow = response[i];
					options+="<option value="+dataRow.numId+">"+dataRow.agencyName+"</option>";
					
				}
					$("#thirdPartyId").empty();
					$('#thirdPartyId').append(options);
					
					 $('.select2Option').select2({
				    	 width: '100%'
				    });
				    
				
			},

       
        });
	    $('#form1').bootstrapValidator('enableFieldValidators', 'numId', false);
	  }
	  else
		  {
		  $('#employeeIdDiv').show();
		  $('#thirdPartyDiv').hide();
		  $('#form1').bootstrapValidator('enableFieldValidators', 'numId', true);
		  }
	    $.ajax({
			type:"POST",
			 async:false,
			url:"/PMS/mst/getSpecificDesignations",		
			data:"isThirdPartySpecific="+isThirdPartySpecific,
			success:function(response){
				//console.log(response);
				var options = '<option value="0"> -- Select -- </option>';
				
					for(var i =0;i<response.length;i++){
						var dataRow = response[i];
					options+="<option value="+dataRow.numId+">"+dataRow.strDesignationName+"</option>";
					
				}
					$("#designation").empty();
					$('#designation').append(options);
			    
				
			},

       
        });
	});	
	});
 
$(document).ready(function() {
	$('#employmentStatus').change(function(){
	   	var employmentStatus= $('#employmentStatus').val();

	     if(employmentStatus == 'Working' || employmentStatus == 'Deputation'){ 
		    $('#resignDateDiv').hide();
		    $('#form1').bootstrapValidator('enableFieldValidators', 'dateOfResignation', false);
		    $('#form1').bootstrapValidator('enableFieldValidators', 'dateOfRelease', false);
		  }
	     else{ 
			    $('#resignDateDiv').show();
			    $('#form1').bootstrapValidator('enableFieldValidators', 'dateOfResignation', true);
			    $('#form1').bootstrapValidator('enableFieldValidators', 'dateOfRelease', true);
			  }
		  
		});	
		});

$(document).ready(function() {
	$('#employeeTypeId').change(function(){
	   	var employeeTypeId= $('#employeeTypeId').val();

	     if(employeeTypeId == '36' || employeeTypeId == '37' || employeeTypeId == '4'){ 
		    $('#contractDiv').show();
		    $('#form1').bootstrapValidator('enableFieldValidators', 'dtContractStartDate', true);
		    $('#form1').bootstrapValidator('enableFieldValidators', 'dtContractEndDate', true);
		   // $('#form1').bootstrapValidator('enableFieldValidators', 'postId', true);

		  }
	     else{ 	    	 
			    $('#contractDiv').hide();
			    $('#form1').bootstrapValidator('enableFieldValidators', 'dtContractStartDate', false);
			    $('#form1').bootstrapValidator('enableFieldValidators', 'dtContractEndDate', false);
			  //  $('#form1').bootstrapValidator('enableFieldValidators', 'postId', false);

			  }
		  
		});	
		});



function validateNumber($num){			
	 var regex = new RegExp(numericRegex);	
	    return regex.test($num); 
}

function myFunction() {
	var numId = $('#numId').val();	
	
	if(numId && numId>0 && validateNumber(numId)){
		
		$.ajaxRequest.ajax({
	        	 type: "POST",
	        	 url: "/PMS/mst/EmployeeData",
	        	 data: {"searchEmployeeId": numId},
	        	 success: function(response) { 
	        		 if(response.numId != null){
	        			 
	        			  swal({
	        			  title: 'Employee Exist',
	        			  text: "You want to upadate the record",
	        			  type: 'warning',
	        			  showCancelButton: true,
	        			  confirmButtonColor: '#3085d6',
	        			  cancelButtonColor: '#d33',
	        			  confirmButtonText: 'Yes',
	        			  buttons: [
	        			            'No',
	        			            'Yes'
	        			          ],
	        		 }).then(function(isConfirm) {
	        			    if (isConfirm) {
	        			 
	        	$('#form1').data('bootstrapValidator').resetForm(true);
	        	 $('#numId').prop("readonly","readonly");
	        	$('#employeeName').val(response.employeeName);
	        	$('#officeEmail').val(response.officeEmail);
	        	$('#alternateEmail').val(response.alternateEmail);
	        	$('#mobileNumber').val(response.mobileNumber);
	        	$('#contactNumber').val(response.contactNumber);
	        	$('#dateOfBirth').val(response.dateOfBirth);
	        	$('#dateOfJoining').val(response.dateOfJoining);
	        	$('#gender').val(response.gender).trigger('change');
	        	/*$('#designation').val(response.designation).trigger('change');*/
	        	$('#designation').val(response.designation).select2({
	        		 width: '100%'
	        	});
	        	designationDetail(response.designation);
	        	/*$('#organisationId').val(response.organisationId)
	        	.trigger('change');*/
	        	$('#organisationId').val(response.organisationId).select2({
	        		 width: '100%'
	        	});
	        	groupdetail(response.organisationId);
	        	$('#groupId').val(response.groupId).trigger('change');
	        	$('#employeeTypeId').val(response.employeeTypeId).trigger('change');
	        	//set third party organisation id to element
	        	$('#thirdPartyId').val(response.thirdPartyId).trigger('change');
	        	$('#roles').val(response.roles).trigger('change');
	        	$('#category').val(response.category).trigger('change');
	        	$('#employmentStatus').val(response.employmentStatus).trigger('change');
	        	$('#dateOfResignation').val(response.dateOfResignation);
	        	$('#dateOfRelease').val(response.dateOfRelease);
	        	$('#dtContractStartDate').val(response.dtContractStartDate);
	        	$('#dtContractEndDate').val(response.dtContractEndDate);
	        	$('#numId').val(response.numId);
	        	$('#updateFlag').val(1);
	        	$('#valid').val(response.valid).toggle();
	        			$('select.select2Option').select2({width: '100%'});
	        			if(response.valid=='1'){
	        				$('#toggle-off').removeAttr('checked');
	        				$('#toggle-on').attr('checked',true);

	        			}else{
	        				$('#toggle-on').removeAttr('checked');
	        				$('#toggle-off').attr('checked',true);
	        			}
	        			
	        			designationDetail(response.designation);
	        			   
	        		 }
	        		
	        		 else {
	        			 	   			 	
	        			 	resetForm();

	        		      }
	        	 
	        	       	});

	        		 }
	        	 }
});
}else{

 	resetForm();
}
}
  

function emailFunction() {
	var numId = $('#numId').val();
	var email = $('#officeEmail').val();
	$.ajax({
   	 type: "POST",
   	 url: "/PMS/mst/EmployeeDataByEmail",
   	 data: {"searchEmployeeId": numId ,"officeEmail":email},
   	 success: function(response) { 
   		 if(!response){
   			 swal({ text: "Employee Office Email already exist"});
   			$('#officeEmail').val('');
   		 }
   		    		 		 
   	 }
   	 
   	 });	 
	}

$(document).ready(function() {
	
	if('${profileFlag}' =='1'){
   	 $('#numId').prop("readonly","readonly");	
	}
	
	$("#emailDiv").hide();
	$("#empNameDiv").hide();
	$("#empdetails").hide();
	
})


$(document).on('change','#designation',function(e){
	var designationId = $(this).find(":selected").val();
	
	designationDetail(designationId);
  }); 

function designationDetail(designationId){
	if(designationId != 0){
		$.ajaxRequest.ajax({
		    type: "POST",
		    url: "/PMS/mst/getDesignationData",
		    data: {"designationId": designationId},
		    async:false,
		    success: function(response) {
	        	$('#designation').select2('destroy');
	        	$('#designation').val(response.numId).select2({
	           	 width: '100%'
	            });
	        	if(response.numIsGroupSpecific == 2 || response.numIsGroupSpecific == 1){
	        		$('#form1').bootstrapValidator('enableFieldValidators', 'organisationId', true);
					$('#form1').bootstrapValidator('enableFieldValidators', 'groupId', true);
					
	        	}else{		    	
				$('#form1').bootstrapValidator('enableFieldValidators', 'groupId', false);
				if(response.numIsOrganisationSpecific == 1){
					$('#form1').bootstrapValidator('enableFieldValidators', 'organisationId', true);
			    }else{
			    	$('#form1').bootstrapValidator('enableFieldValidators', 'organisationId', false);
			    }
		    }
		}		
	});
	}
}


$(function() {

	$('#submit').on('click', function(e) {
		var submitFlag=true;
		var option=$('#optionValue').val();
		if(option==0){
			sweetSuccess('Attention','Option is required');
			submitFlag= false;
			return false;
		}
		if(submitFlag ==true){
		var formData = new FormData();
		formData.append('employeeDocumentFile', $('#employeeDocumentFile')[0].files[0]);
		formData.append('optionValue' , $('#optionValue').val());
	    $.ajax({
	        type: "POST",
	        url: "/PMS/mst/importEmployeeDetail",	        
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
	        	$('#optionValue').val('0').trigger('change');
	        	$('#employeeDocumentFile').val('');

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
			$('#form0').removeClass('hidden');
			$('#form1').removeClass('hidden');
			$('#form').addClass('hidden');
			$('#result').hide();


		}else{
			$('#form').removeClass('hidden');
			$('#form0').addClass('hidden');
			$('#form1').addClass('hidden');

		}
	}
	});
	});

$(document).on('change','#employeeTypeId',function(e){
	var employeeTypeId = $('#employeeTypeId option:selected').text();
	console.log(employeeTypeId);
	if(employeeTypeId){
		if($.trim(employeeTypeId) == 'Regular'){
			$('#contractDiv').hide();
			$('#form1').bootstrapValidator('enableFieldValidators', 'dtContractStartDate',false);
			$('#form1').bootstrapValidator('enableFieldValidators', 'dtContractEndDate',false);
		}else{
			$('#contractDiv').show();
			$('#form1').bootstrapValidator('enableFieldValidators', 'dtContractStartDate',true);
			$('#form1').bootstrapValidator('enableFieldValidators', 'dtContractEndDate',true);
		}
	}
	
  });

function openSelSearch(){
	var selectedVal=$('#searchDrop').val();

	if(selectedVal=='EmpId'){
		resetForm();
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
		resetForm();
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
		resetForm();
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
	detailOfEmployee(selEmpId);
	
}

function validateEmail(email){		
	 var regex = new RegExp(emailRegex);	 
	    return regex.test(email);
}

function downloadTemplate(documentName){
	openWindowWithPost('POST','/PMS/downloadTemplate',{"template" : "EMPLOYEE_REG_TEMPLATE","documentTypeName":documentName},'_blank');
}