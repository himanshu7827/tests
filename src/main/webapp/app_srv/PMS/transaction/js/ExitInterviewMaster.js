var emailRegex='';
var emailErrorMessage='';
messageResource.init({	  
filePath : '/PMS/resources/app_srv/PMS/global/js'
});

messageResource.load('regexValidationforjs', function(){
	  // get value corresponding  to a key from regexValidationforjs.properties  	
	emailRegex= messageResource.get('email.regex', 'regexValidationforjs');
	emailErrorMessage = messageResource.get('email.regex.message', 'regexValidationforjs');	
	});
var inputArray = [];
$(document).ready(function() {
		$('#example').DataTable();
		$('.select2Option').select2({width:"100%"});	
		
		$('#form1').bootstrapValidator({
//		      live: 'disabled',
			excluded: ':disabled',
		      message: 'This value is not valid',
		      feedbackIcons: {
		          valid: 'glyphicon glyphicon-ok',
		          invalid: 'glyphicon glyphicon-remove',
		          validating: 'glyphicon glyphicon-refresh'
		      },
		      fields: {
		    	  flaEmail: {
		              group: '.col-md-12',
		              validators: {
		                  notEmpty: {
		                      message: 'FLA Email is required and cannot be empty'
		                  },
		                  stringLength: {
		                        max: 50,
		                        message: 'FLA Email must be less than 50 characters'
		                    },
		                    regexp: {
		                        regexp: emailRegex,
		                       message: emailErrorMessage
		                    }
		              }
		          },
		          slaEmail: {
		              group: '.col-md-12',
		              validators: {
		                  notEmpty: {
		                      message: 'SLA Email is required and cannot be empty'
		                  },
		                  stringLength: {
		                        max: 50,
		                        message: 'SLA Email must be less than 50 characters'
		                    },
		                    regexp: {
		                        regexp: emailRegex,
		                       message: emailErrorMessage
		                    }
		                    
		                    }
		          },
		          employeeRemarks: {
		              group: '.col-md-12',
		              validators: {
		                  
		                  stringLength: {
		                        max: 100,
		                        message: 'Employee Remarks must be less than 100 characters'
		                    },
		              }
		          },
		          
		      }
		  });		
		
});

$('#save').click(function(){
	var submissionFlag = 1;
	$('.questions').each(
			function(index) {
				var questionId = this.id;
				var questionValue=this.value;
				var splitQuesId=questionId.split('_');
				quesId=splitQuesId[1];
				var answerId="answer_"+quesId;
				var answerValue=$('#'+answerId).val();
				var rowObject = new Object();
				  rowObject.questionValue=questionValue;
				  rowObject.answerValue=answerValue;
				  if(answerValue==0){
					  submissionFlag=0;
					  	sweetSuccess('Attention','Experience is mandatory in each Row');
					  	return false;
				  }
				  inputArray.push(rowObject);
			}
	);
	
	var flaEmail=$('#flaEmail').val();
	var slaEmail=$('#slaEmail').val();
	var employeeRemarks=$('#employeeRemarks').val();
	var bootstrapValidator = $("#form1").data('bootstrapValidator');
	bootstrapValidator.validate();
	 if(bootstrapValidator.isValid() && submissionFlag==1){
		 $.ajaxRequest.ajax({
		        type: "POST",
		        url: "/PMS/saveExitInterview",
		        data:{"flaEmail":flaEmail,
		        	  "slaEmail":slaEmail,
		        	  "employeeRemarks":employeeRemarks,
		        	  "exitInterviewDetails":JSON.stringify(inputArray)},
		        async:false,       		
				success : function(data) {
					if(data>0){
						sweetSuccess('Success'," Record saved/updated successfully");
						$('.select2Option').val('0').trigger('change');
						$('#form1').hide();
					}else{
						sweetSuccess('Error',"Something went wrong");
					}
					$('#flaEmail').val('');
					$('#slaEmail').val('');
					$('#employeeRemarks').val('');
					
				},error : function(data) {
					
				}
					
			});
		
	}else{
		console.log('Not Validated');
		 
	}
	
	
	
});