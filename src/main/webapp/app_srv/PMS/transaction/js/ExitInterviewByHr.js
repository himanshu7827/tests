$(document).ready(function() {
	$('.select2Option').select2({width:"element"});
		 $('#userinfo-message').delay(5000).fadeOut();
		 
		 var table = $('#datatable').DataTable();
			table.destroy();
 
			$(document).on('change','#empId',function(e){
				var employeeId = $(this).find(":selected").val();
				empDetails(employeeId);
			}); 
					 
			 	  });

function  empDetails(empId){	
	var tableData = "";
	$.ajaxRequest.ajax({
				 type: "POST",
				 url: "/PMS/rolesByEmpId",
				 data: "empId="+ empId,
				 success: function(data) {
					 console.log(data);
						for(var i =0;i<data.length;i++){
							var row = data[i];
							var serialNo=i+1;
							var startDateId = 'startDate_'+row.numId;
							tableData+="<tr><td>"+serialNo+"</td>" ;
							tableData+="<td>"+row.strProjectName+"</td>";
							tableData+="<td>"+row.strRoleName+"</td>";
							tableData+="<td>"+row.strGroupName+"</td>";
							tableData+="<td class='startDate' id="+startDateId+">"+row.strStartDate+"</td> ";
							if(row.strEndDate){
								tableData+="<td>"+row.strEndDate+"</td> ";
							}
							else{
							tableData+="<td><input  class='endDate' id='endDate_"+row.numId+"' readonly='readonly'/></td>" ;
									} 
							tableData+="</tr>";
									
						}
						tableData+='</tbody>';
						$('#datatable tbody').empty();						
						$('#datatable').append(tableData);	
						
						/*$('.endDate').datepicker({
							  dateFormat:'dd/mm/yy',
							  changeMonth: true,
							  changeYear: true,
							  minDate:,
							  maxDate:new Date(),
							});*/
						initiateCalendar();
						
					},				
					error : function(e) {
						alert('Error: ' + e);
				 	}
			 });
			 
			 $('#datatable').removeClass('hidden');
			
		 	}


var inputArray = [];
$('.releaseEmployee').click(function(){
	var submissionFlag = 1;
	$('.endDate').each(
			function(index) {
				var dateValue=this.value;
				var dateId=this.id;
				var splitDateId=dateId.split('_');
				var roleId=splitDateId[1];
				var rowObject = new Object();
					rowObject.endDate=dateValue;
					rowObject.roleId=roleId;
					 if(dateValue==0){
						  submissionFlag=0;
						  	sweetSuccess('Attention','End Date is mandatory in each Row');
						  	return false;
					  }

				  inputArray.push(rowObject);
			}
	);
	
		var employeeId=$('#empId').val();
		if(submissionFlag==1){
		 $.ajaxRequest.ajax({
		        type: "POST",
		        url: "/PMS/saveExitInterviewByHr",
		        data:{
		        	 "employeeId":employeeId,
		        	  "endDateDetails":JSON.stringify(inputArray)},
		        async:false,       		
				success : function(data) {
					if(data>0){
						sweetSuccess('Success'," Record saved/updated successfully");
					}else{
						sweetSuccess('Error',"Something went wrong");
					}
					
					
				},error : function(data) {
					
				}
					
			});
		}
		
});

function initiateCalendar(){

	$('.startDate').each(function(){
		var fieldId = this.id;
	
		var selectId = fieldId.split('startDate_')[1];		
		var dateString = $('#'+fieldId).text();		
		var dateParts = dateString.split("/");		
		var  dateObject= new Date(+dateParts[2], dateParts[1] - 1, +dateParts[0]);	
		
		$('#endDate_'+selectId).datepicker({
			dateFormat:'dd/mm/yy',
			  changeMonth: true,
			  changeYear: true,
			  minDate: dateObject,
			  maxDate:new Date(),
			  defaultDate:new Date()
			});
	});
}
