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
	
	$("#strJobCode").hide();
	if($("#noOfPositions").val()==0){
		$("#noOfPositions").val('');
	}
       });
    
$(document).ready(function() {
$('#approvedOn').datepicker({
	   dateFormat: 'dd/mm/yy', 
	   changeMonth: true,
	   changeYear:true,
	   onSelect: function(date){
	    	$('#approvedOn').trigger('input');
	    	}
	  
});

$('#createdOn').datepicker({
	   dateFormat: 'dd/mm/yy', 
	   changeMonth: true,
	   changeYear:true,
	   onSelect: function(date){
	    	$('#createdOn').trigger('input');
	    	}
	  
});
});


	function openJobCode(){
		var noOfPositions= $("#noOfPositions").val();
		var type=/^[0-9]+$/.test(noOfPositions);
		//alert(type);
		if (type) { 
			console.log(noOfPositions);
			$('#jobCodeDiv').empty();
		
			if(noOfPositions==0){
				$('#strJobCode').hide();
				sweetSuccess("Attention","Number of Positions can not be zero");	
				return false;
			}else{
				$('#strJobCode').show();
				for(var i=0;i<noOfPositions;i++){				
					$('#jobCodeDiv').append('<div class="pad-top "><input name="jobReferences['+i+']" class="input-field  jobCodes"/> </div>');
				}
			}

        } 
        else { 
        	$('#strJobCode').hide();
        	sweetSuccess("Attention","Number of Positions should be numeric"); 
        	return false;
        } 
			

	}
	
	$(document).on('change','#projectId',function(e){
		/*$('#approvedDetails tbody').empty();*/
		$("#details").empty();
		var projectId=$("#projectId").val();
		
		$.ajax({
			type : "POST",
			url : "/PMS/getApprovedJobDetails",
			data : "projectId=" + projectId,
			success : function(response) {
				
				var data;
	        	var count;
	        	var tableRow = '';
	        	$.each( response, function(index,value){
	        		
	        		 data=value;
	        		 count=index;
	        		 /*alert(count);*/
	        		 var arr =  count.split('**');
	        		 var designation=arr[0];
	        		 var projNameDur=arr[1];
	        		 var arr1 =projNameDur.split('@@');
	        		 var approvedOn=arr1[0];
	        		
	        		 var other=arr1[1];
	        		 var arr2 =other.split('##');
	        		 var projectName=arr2[0];
	        		 var duration=arr2[1];
	        		 var desgWithDateDuration="";
	        		 desgWithDateDuration+= "<span class='bold' style='font-size: large;'>"+designation +"</span><br /> <span style='font-size: small;'> Approved on "+approvedOn +" for "+duration +" months </span>";
     			 	 var jobCode="";
     			 
						for(var i = 0; i< data.length;i++){
							var index=i+1;
							jobCode+= "<span  style='font-size: small;'>"+index+".</span><span class='bold' style='font-size: medium;'> "+  data[i].jobCode+" </span> <span class='green' style='font-size: small;'>("+data[i].status+ ")</span>   <br /> ";
		
						}
					tableRow = "<tbody>";	
					tableRow = "<tr> ";
					tableRow+="<td><span class='text-left' style='font-size: medium;'>"+projectName+"</span> </td>";
		        	tableRow+="<td> "+desgWithDateDuration+" </td>";
					tableRow+="<td> "+jobCode+" </td>";
					tableRow +="</tr>";		
					tableRow+='</tbody>';
					/*$('#approvedDetails tbody').empty();*/
					$('#approvedDetails').append(tableRow);

					
	        		})
		
			},
			error : function(e) {
				alert('Error: ' + e);			
			}
		});	
	});
	
	
function addJobCodes(oldseq,noOfPositions){
	
	
	var addRow='';
	for(var i = oldseq; i<=noOfPositions;i++){		
				

				addRow+='<div class="row termval" id="strJobCode">';
				addRow+='<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">';
		
				addRow+='<div class="col-md-2 col-lg-2 col-sm-2 col-xs-12 text-justify">';
				addRow+='<label class="label-class">Job Code ('+i+'):<span style="color: red;">*</span></label>';
				addRow+='</div>';
				addRow+='<div class="col-md-2 col-lg-2 col-sm-2 col-xs-12" id="codeJob">';
				addRow+='<div class="input-container">';	
				addRow+='<form:input class="input-field" path="jobCode"  id="jobCode"></form:input>';
				addRow+='</div></div></div></div>';
				

	}
	$("#strJobCode").show();
	$('#strJobCode').append(addRow);


}

function removeProposalDurationRow(oldseq,noOfPositions){
	 
	for(var i = oldseq; i>noOfPositions;i--){		
		
		var durationid = $('#jobCode'+i).val();
		if(durationid!=0){

			$( "#strJobCode"+i).remove()
		}
		else{
			$( "#strJobCode"+i).remove();
		}
	}
}


function getProjectDetails(){
	
var numGroupId=	$("#groupId").val();
$.ajax({
	type : "POST",
	url : "/PMS/mst/getProjectNameByGroupId",
	data : "numGroupId=" + numGroupId,
	success : function(response) {
		console.log(response);
		var seloption = "";
			for (var i = 0; i < response.length; i++) {
				seloption += "<option  value="+response[i].numId+">"
					+ response[i].strProjectName +" </option>";
		}	        
			$('#projectId').append(seloption);
			$("#projectId").trigger("change"); 	
	},
	error : function(e) {
		alert('Error: ' + e);			
	}
});	
}



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
    
   
    
    $('#form1').bootstrapValidator({
		excluded: ':disabled',
	      message: 'This value is not valid',
	      feedbackIcons: {
	          valid: 'glyphicon glyphicon-ok',
	          invalid: 'glyphicon glyphicon-remove',
	          validating: 'glyphicon glyphicon-refresh'
	      },
	      fields: {
	
	    	  noOfPositions: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Number of Positions is required and cannot be empty'
	                  }, 
	                  stringLength: {
	                        max: 2,
	                        message: 'Number of Positions can not be greater than 2 digits'
	                    },
	                  regexp: {
	                	  regexp: numericRegex,
	                       message: numericErrorMessage
	                    }
	              }
	          },
	          jobCode: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Job Code is required and cannot be empty'
	                  }, 
	                  stringLength: {
	                        max: 6,
	                        message: 'Job Code can not be greater than 6 digits'
	                    },
	                  regexp: {
	                	  regexp: numericRegex,
	                       message: numericErrorMessage
	                    }
	              }
	          },
	          durationInMonths: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'Duration in months is required and cannot be empty'
	                  }, 
	                  stringLength: {
	                        max: 2,
	                        message: 'Duration in months can not be greater than 2 digits'
	                    },
	                  regexp: {
	                	  regexp: numericRegex,
	                       message: numericErrorMessage
	                    }
	              }
	          },
	           
	        /*  createdOn:{
	        	  validators: {
	                    notEmpty: {
	                        message: 'Created On is required and cannot be empty'
	                    },
	        	  date:{
	        	  format: 'DD/MM/YYYY'
	        	  }
	          }
	          },*/
	          approvedOn:{
	        	  validators: {
	                    notEmpty: {
	                        message: 'Approved On date is required and cannot be empty'
	                    },
	        	  date:{
	        	  format: 'DD/MM/YYYY'
	        	  }
	          }
	          },
	       
	          
	          
	      }
	  });

   
   
  
   
   $('#save').click(function(){	
	  
	if($("#groupId").val()==0){
		sweetSuccess('Attention','Group Name is mandatory');
		return false;
	}
	
	if($("#projectId").val()==0){
		sweetSuccess('Attention','Project Name is Mandatory');
		return false;
	}
	
	if($("#designationId").val()==0){
		sweetSuccess('Attention','Designation is Mandatory');
		return false;
	}
	//alert($(".jobCodes").val()=='');
	if($(".jobCodes").val()==''){
		sweetSuccess('Attention','Job Code is Mandatory');
		return false;
	}
	var bootstrapValidator = $("#form1").data('bootstrapValidator');
	bootstrapValidator.validate();
    if(bootstrapValidator.isValid()){	
    	$.ajax({
    		type : "POST",
    		url : "/PMS/checkUniqueJobCode",
    		data :$('#form1').serialize(),
    		success : function(response) {
    			//alert(response);
    		  	if(response==1){
	        		sweetSuccess('Attention','Job Code is already exist');
					return false;
				}
    		  	else if(response==2){
    		  		sweetSuccess('Attention','Job Codes can not be same');
					return false;
    		  	}
    		  	else{
    		  		document.getElementById("save").disabled = true; 
    				$('#form1')[0].submit();
    				return true;
    		  	}
    		},
    		error : function(e) {
    			alert('Error: ' + e);			
    		}
    	});	
    	
    	
		
    }
			 
	   
		
   });
   
});

function resetForm(){
	$('#strJobCode').hide();
 	$('#createdOn').val('');
	$('#approvedOn').val('');
	$('#noOfPositions').val('');
	$('#durationInMonths').val('');
	$('.jobCodes').val('');
	$('#groupId').val(0).trigger('change');
	$('#projectId').val(0).trigger('change');
	$('#designationId').val(0).trigger('change');
	$("#details").empty();
	//$('#approvedDetails').empty();
}

$(document).on('change','#organisationId',function(e){
	var orgId = $(this).find(":selected").val();
	groupdetail(orgId);
  });  



/*function changeDateFormat(appDate){
	alert(appDate);
	string newDate = moment(appDate, "YY/MM/DD").format("DD/MM/YY");
	return newDate;
}*/


function validateNumber($num){			
	 var regex = new RegExp(numericRegex);	
	    return regex.test($num); 
}

  



