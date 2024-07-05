$(document).ready(function() {
    $('.select2Option').select2({
    	 width: '100%'
    });
});
$('#form').bootstrapValidator({
//    live: 'disabled',
	excluded: ':disabled',
    message: 'This value is not valid',
    feedbackIcons: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
    },
    fields: {
    	description: {
            group: '.col-md-12',
            validators: {
                stringLength: {
                      max: 2000,
                      message: 'Description must be less than 2000 characters'
                  },
            }
        }
    }
});

	$('#emp').on('show.bs.modal', function(event) {
		var button = $(event.relatedTarget);
		var taskId = button.data('whatever');
		$.ajax({
    		type : "POST",
    		url : "/PMS/mst/getTaskDetailsData",
    		data : "encTaskId=" + taskId,
    		success : function(response) {
    			$('#taskName').text(response.taskName);
    			$('#projectName').text(response.projectName);
    			$('#createdBy').text(response.employeeName);
    			$('#assignDate').text(response.assignDate);
    			$('#taskDescription').text(response.taskDescription);
    			$('#expectedTime').text(response.expectedTime);
    			$('#priority').text(response.priority);
    			
    			if(response.documentId != 0){
    				$('#modelFooter').empty();
    				var download = "downloadDocument('"+response.encDocumentId+"')";
    				$('#modelFooter').append('<a id="fileuploadDownloadLink" href="#"> <button type="button" id="fileuploadDownloadBtn" class="btn-primary btn-orange" onclick='+download+'>Download File</button> </a>');
    			}else{
    				$('#modelFooter').empty();
    			}
 
    		},
    		error : function(e) {
    			alert('Error: ' + e);			
    		}
    	});					
	});

	function downloadDocument(documentId){
		openWindowWithPost('POST','/PMS/mst/downloadTaskFile',{"encDocumentId" : documentId},'_blank');
	}
	
	$('#emp1').on('show.bs.modal', function(event) {
		var button = $(event.relatedTarget);
		var input = button.data('whatever');
		var inputs = input.split(',');
		$('#encTaskId').val(inputs[0]);
		$('#encProjectId').val(inputs[1]);
	
	});

	
	$(function() {

		$('#submit').on('click', function(e) {
		    e.preventDefault();
		    $.ajax({
		        type: "POST",
		        url: "/PMS/mst/assignedTask",
		        data: $('form.taskAssignForm').serialize(),
		        success: function(response) {
		            if(response.status == true){
		            	swal({text:"Task Assigned Successfully"});
		            	$("#emp1").modal('hide');
		            	
		            	var data=response.taskDetailsModelList;
		            	var tableData='';
		            	for(var i =0;i<data.length;i++){
							var row = data[i];
							var num = i+1;
								
							tableData+='<tr> <td> '+num+' </td> <td> <a href="#"  data-toggle="modal" data-target="#emp" class="text-center" ';
							tableData+=' data-whatever="'+row.encTaskId+'"> '+row.taskName+' </a></td> <td> <span class="btn btn-success btn-md"';
							tableData+=' data-toggle="modal" data-target="#emp1" data-whatever="'+row.encTaskId+','+row.encProjectId+'"> <i class="fa fa-check"></i> Accept';
							tableData+=' </span></td> </tr>';
							
									
						}
						
						$('#example_active tbody').empty();						
						$('#example_active').append(tableData);	
						
		            }
		            else{
		            	swal({text:"Task is not assigned"});
		            	$("#emp1").modal('hide');

		            }
		        },
		        error: function() {
		            alert('Error');
		        }
		    });
		    return false;
		});
		});
		
	 $(document).on('change','#ongoingActivity',function(e){
			var Id = $(this).find(":selected").val();
			
			if(Id !=0){
				var inputs=Id.split('_');
				var encTaskId= inputs[1];
				var optionId= inputs[0];
				 swal({
	   			  text: "You want to upadate the status",
	   			  type: 'warning',
	   			  showCancelButton: true,
	   			  confirmButtonColor: '#3085d6',
	   			  cancelButtonColor: '#d33',
	   			  confirmButtonText: 'Yes',
	   			  buttons: [
	   			            'No, cancel it!',
	   			            'Yes, I am sure!'
	   			          ],
	   		 }).then(function(isConfirm) {
				      if (isConfirm) {
				    	  if(optionId==1){
				    		  ongoingDetails(encTaskId);
				    	  }
				    	  else if(optionId==2){
				    		  ongoingWithdrawDetail(encTaskId);
				    	  }
				
				    }else{
				    $('.select2Option').val(0).trigger('change');	
				    }
	   		 })
			}
				
		}); 
	 
	 function  ongoingDetails(encTaskId){
			
					 $.ajax({
						 type: "POST",
						 url: "/PMS/mst/ongoingTask",
						 data: {"encTaskId":encTaskId},
						 success: function(data) { 	
							 createAssignTable(data);

							},				
							error : function(e) {
								//$('#RecurringManpowerAmt').val(0);
								alert('Error: ' + e);
						 	}
					 });
					 
					 $('#example_ongoing').removeClass('hidden');
					
				 	}
	 function  ongoingWithdrawDetail(encTaskId){
			
					 $.ajax({
						 type: "POST",
						 url: "/PMS/mst/ongoingWithdrawTask",
						 data: {"encTaskId":encTaskId},
						 success: function(data) { 	
							 createAssignTable(data);

							},				
							error : function(e) {
								//$('#RecurringManpowerAmt').val(0);
								alert('Error: ' + e);
						 	}
					 });
					 
					 $('#example_ongoing').removeClass('hidden');
					
	 }
	 
	 $(document).on('change','#completedActivity',function(e){
			var Id = $(this).find(":selected").val();
			
			if(Id !=0){
				var inputs=Id.split('_');
				var encTaskId= inputs[1];
				var optionId= inputs[0];
				 swal({
	   			  text: "You want to upadate the status",
	   			  type: 'warning',
	   			  showCancelButton: true,
	   			  confirmButtonColor: '#3085d6',
	   			  cancelButtonColor: '#d33',
	   			  confirmButtonText: 'Yes',
	   			  buttons: [
	   			            'No, cancel it!',
	   			            'Yes, I am sure!'
	   			          ],
	   		 }).then(function(isConfirm) {			     
				    	  if (isConfirm) {
					    	  if(optionId==1){
					    		  completedDetails(encTaskId);
					    	  }
					    	  else if(optionId==2){
					    		  withdrawDetail(encTaskId);
					    	  }
				    }else{
				    $('.select2Option').val(0).trigger('change');	
				    }
	   		 })
			}
					
				}); 
	 
	 function  completedDetails(encTaskId){
			
					 $.ajax({
						 type: "POST",
						 url: "/PMS/mst/workInProgressTask",
						 data: {"encTaskId":encTaskId},
						 success: function(data) { 	
							 createOngoingTable(data);

							},				
							error : function(e) {
								//$('#RecurringManpowerAmt').val(0);
								alert('Error: ' + e);
						 	}
					 });
					 
					 $('#example_ongoing_task').removeClass('hidden');
					
				 	}
	 
	 function  withdrawDetail(encTaskId){
			var tableData = "";
					 $.ajax({
						 type: "POST",
						 url: "/PMS/mst/ongoingWithdrawTask",
						 data: {"encTaskId":encTaskId},
						 success: function(data) { 	
							 createAssignTable(data);

							},				
							error : function(e) {
								//$('#RecurringManpowerAmt').val(0);
								alert('Error: ' + e);
						 	}
					 });
					 
					 $('#example_ongoing').removeClass('hidden');
					
	 }
	 
	 
	 function createAssignTable(data){
		 
		 var tableData = "";
		 var response = data.taskDetailsModelList;
			for(var i =0;i<response.length;i++){
				var row=response[i];
				var num = i+1;
				
				tableData+='<tr><td>'+num+'</td>';
				tableData+='<td class="font_14">'+row.taskName+'</td>';
				tableData+='<td><span class="btn btn-primary btn-md" data-toggle="modal" data-target="#emp" data-whatever="'+row.encTaskId+'">';
				tableData+=' <i class="fa fa-th-large"></i> Details</span></td>';
				tableData+='<td id="ongoingActivity"><select class="select2Option"> <option value="0">--Select--</option> ';
				tableData+=' <option value="1_"'+row.encTaskId+'"">Work In Progress</option>  <option value="2_"'+row.encTaskId+'"">Withdraw</option></select></td></tr>';
				
			}
			
			$('#example_ongoing tbody').empty();	
			$('#example_ongoing').append(tableData);		
			$(".select2Option").select2({width: '100%'});
	 }
	 
function createOngoingTable(data){
	 var tableData = "";
	 var response = data.taskDetailsModelList;
		for(var i =0;i<response.length;i++){
			var row=response[i];
			var num = i+1;
			
			tableData+='<tr><td>'+num+'</td>';
			tableData+='<td class="font_14">'+row.taskName+'</td>';
			tableData+='<td><span class="btn btn-primary btn-md" data-toggle="modal" data-target="#emp" data-whatever="'+row.encTaskId+'">';
			tableData+=' <i class="fa fa-th-large"></i> Details</span></td>';
			tableData+='<td id="completedActivity"><select class="select2Option"> <option value="0">--Select--</option> ';
			tableData+=' <option value="1_"'+row.encTaskId+'"">Completed</option>  <option value="2_"'+row.encTaskId+'"">Withdraw</option></select></td></tr>';
			
		}
		$('#example_ongoing_task tbody').empty();	
		$('#example_ongoing_task').append(tableData);		
		$(".select2Option").select2({width: '100%'});
	 }

