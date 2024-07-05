var numericRegex='';
var numericErrorMessage='';
messageResource.init({	  
	filePath : '/PMS/resources/app_srv/PMS/global/js'
	});

	messageResource.load('regexValidationforjs', function(){
		  // get value corresponding  to a key from regexValidationforjs.properties  
		numericRegex= messageResource.get('numeric.regex', 'regexValidationforjs');
		numericErrorMessage = messageResource.get('numeric.regex.message', 'regexValidationforjs');
		
		});

	$(document).ready(function() {
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
	    	  
	    	  noOfDays: {
	              group: '.col-md-12',
	              validators: {
	                  notEmpty: {
	                      message: 'No. of Days is required and cannot be empty'
	                  }, 
	                  stringLength: {
	                        max: 3,
	                        message: 'No. of days must be less than 3 digits'
	                    },
	                  regexp: {
	                        regexp: numericRegex,
	                       message: numericErrorMessage
	                    }
	              }
	          },
	      }
	})
	})	
function validateNumber($num){			
	 var regex = new RegExp(numericRegex);	
	    return regex.test($num); 
}

function myFunction() {
	var tableData = "";
	var days = $('#noOfDays').val();
	if(days && days>0 && validateNumber(days)){
		$.ajaxRequest.ajax({
	        	 type: "POST",
	        	 url: "/PMS/mst/ReviewDetailByNoOfDays",
	        	 data: "days="+ days,
	        	 success: function(response) { 
	        		 console.log(response);
	        			for(var i =0;i<response.length;i++){
	        				serialNo=i+1;
							var row = response[i];
							tableData+="<tr><td>"+serialNo+"</td>"; 
							tableData+=	"<td>"+row.milestoneName+"</td>";
							tableData+=	"<td>"+row.strProjectName+"</td>";
							tableData+=	"<td>"+row.groupName+"</td> ";
							if(row.completionDate){
								tableData+=	"<td>"+row.completionDate+"</td> ";
	
							}
							else if(row.expectedStartDate){
								tableData+=	"<td>"+row.expectedStartDate+"</td> ";
	
							}
							else{
								tableData+=	"<td></td> ";

							}
							tableData+=	"<td><a class='btn btn-primary' onclick=viewProjectDetails('/PMS/mst/MilestoneReviewMaster/"+row.encMilestsoneId+"')>Review</a></td> ";

							
							tableData+="</tr>";
									
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
	        		 
	        		 
	        	 }
	        	
	
}