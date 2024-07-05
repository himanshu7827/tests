function startWorkFlow(applicationId,moduleTypeId,businessTypeId,projectTypeId,categoryId){
openWindowWithPost('POST','/PMS/startWorkflow',{"numId":applicationId,"moduleTypeId":moduleTypeId,"businessTypeId":businessTypeId,"projectTypeId":projectTypeId,"categoryId":categoryId},'_blank');
}
$(document).ready(function() {
	$('#example').DataTable({
		 "bLengthChange": false,

	 });
});

function viewApplicationStatus(applicationId){
	console.log(applicationId);
	$.ajaxRequest.ajax({
		type : "POST",
		url : "/PMS/viewApplicationStatus",
		data : {
				"applicationId":applicationId				
			},
		success : function(response) {
			 $('#'+applicationId).find('li').remove();
			 var encNumId=response.encNumId;
			 console.log(encNumId);
			 if(response.convertedToProject==true){
					$('#'+applicationId).append('<li class="padded-half text-center"><span class="font_14 red ">No Action!</span></li>');
				}else{					
					/*$('#'+applicationId).append('<li> <a class="font_14" onclick ="openApplication(\''+encNumId+'\')"><span class="fa fa-pencil-square-o btn btn-info " id="edit" aria-hidden="true"></span><span class="pad-left-half">Edit</span></a> </li>');*/
					//Condition added by devesh on 14-02-24 to allow edit only when the date of submission is of current year
					if (response.dateOfSubEqualsCurrentYear) $('#'+applicationId).append('<li> <a class="font_14" onclick ="openApplication(\''+encNumId+'\')"><span class="fa fa-pencil-square-o btn btn-info " id="edit" aria-hidden="true"></span><span class="pad-left-half">Edit</span></a> </li>');
					//ENd of condition
					$('#'+applicationId).append('<li> <a class="font_14" onclick ="convertToProject('+applicationId+')"><span class="fa fa-share-square-o btn btn-info" id="edit" aria-hidden="true"></span> <span class="pad-left-half">Convert To Project</span> </a> </li>');					
				}		
		}, error: function(data) {
	 	  
	   }
});	
}

function openApplication(encNumId){
	var win = window.open("/PMS/applicationBasicDetails?encNumId="+encNumId,"_blank");
	if (win) {	    
	    win.focus();
	} else {	   
	    alert('Please allow popups for this website');
	}
}
	function convertToProject(applicationId){
		$.ajaxRequest.ajax({
			type : "POST",
			url : "/PMS/mst/proposalDetailsByApplicationId",
			data : {
					"applicationId":applicationId				
				},
			success : function(response) {
				 if(response){
					 console.log(response.status);
					 if(response.status=="SUB"){
					 swal({
						  title: "Attention",
						  text: "Once converted to Project ,  you would not be able to make any changes to the application. Do you wish to convert?",
						  type: 'success',						  
					      buttons: [					               
					                'No',
					                'Convert'
					              ],	     
					    }).then(function(isConfirm) {
					      if (isConfirm) {
							 console.log('Lock');
							 openWindowWithPost('POST','/PMS/mst/convertApplicationToProject',{"applicationId" : applicationId},'_blank');	
					      } else {
					    	  console.log('Leave');
					      }
					    });
					 }else{
							sweetSuccess('Warning','Proposal must be submitted before conversion');

					 }
				 }else{
					sweetSuccess('Warning','Proposal Details does not Exist. Please Update same before conversion');
				 }
			}, error: function(data) {
		 	  
		   }
	});	
		
	}
	/*function loadGroupWiseApplications(encGroupId){
	 $('#example').DataTable().destroy();
	 var tableId = $('#example').attr('id')
			var tableData = '';
		$.ajaxRequest.ajax({
			type : "POST",
			url : "/PMS/getProposalDetailByGruopId",
			data : "encGroupId=" + encGroupId,
			success : function(response) {
				console.log(response);
				for (var i = 0; i < response.length; i++) {
					var row = response[i];
					//console.log(row);
					var encNumId = row.encNumId;
					var sno=i+1;
					tableData +="<tr ><td >"+sno+"</td>";
					tableData += "<td class='font_14' > <a class='bold' title='Click Here to View Proposal Details' onclick=viewProjectDetails('/PMS/mst/proposalDetails/"+row.encNumId+"')>" + row.proposalTitle + " </a></td>";
					tableData += "<td class='font_14' >"+ row.businessTypeName + "</td>" ;
					tableData += "<td class='font_14'>"+ row.projectTypeName + "</td>"  ;
					tableData += "<td class='font_14' >"+ row.categoryName + "</td>";
					tableData += "<td class='font_14' >"+ row.organisationName + "</td>"; 
					tableData += "<td class='font_14' > "+ row.groupName + "</td>";
					tableData += "<td  class='font_14 text-right '><input type='hidden' id='hiddenAmt_"+row.encNumId+"' value='"+row.encNumId+"'/> <span class='convert_lakhs_td' id='amount_"+row.encNumId+"'>"+ row.totalProposalCost+  "</span></td>";
					tableData += "<td> <div class='dropdown show'> <a class='btn btn-secondary dropdown-toggle' data-toggle='dropdown' onmouseover='viewApplicationStatus("+row.numId+")' aria-haspopup='true' aria-expanded='false'><i class='icon-th-large icon-1halfx blue pad-top' aria-hidden='true'></i>";
					tableData += "</a><ul class='dropdown-menu pull-right' aria-labelledby='dropdownMenuLink' id='"+row.numId+"'></ul></div></td> </tr>	";
			}
				$('#example > tbody').html('');
				$('#example').append(tableData);
				convertToLakhsForComponent(tableId)
				$('#example').DataTable({
					 "bLengthChange": false,

				 });
                        
				}
			
			});
	}*/
	
	//Added by devesh on 24/08/23 to make proposal table with new controller
	function loadGroupWiseApplications(encGroupId){
		 $('#example').DataTable().destroy();
		 var tableId = $('#example').attr('id')
				var tableData = '';
			$.ajaxRequest.ajax({
				type : "POST",
				url : "/PMS/getProposalDetailByGruopIdnew",
				data : "encGroupId=" + encGroupId,
				success : function(response) {
					console.log(response);
					for (var i = 0; i < response.length; i++) {
						var row = response[i];
						//console.log(row);
						var encNumId = row[9];
						var sno=i+1;
						tableData +="<tr ><td >"+sno+"</td>";
						tableData += "<td class='font_14' > <a class='bold' title='Click Here to View Proposal Details' onclick=viewProjectDetails('/PMS/mst/proposalDetails/"+row[9]+"')>" + row[1] + " </a></td>";
						/*tableData += "<td class='font_14' >"+ row.businessTypeName + "</td>" ;*/
						tableData += "<td class='font_14'>"+ row[2] + "</td>"  ;
						tableData += "<td class='font_14' >"+ row[3] + "</td>";
						/*tableData += "<td class='font_14' >"+ row.organisationName + "</td>"; */
						tableData += "<td class='font_14' > "+ row[5] + "</td>";
						tableData += "<td  class='font_14 text-right '><input type='hidden' id='hiddenAmt_"+row[9]+"' value='"+row[9]+"'/> <span class='convert_lakhs_td' id='amount_"+row[9]+"'>"+ row[6]+  "</span></td>";
						//tableData += "<td  class='font_14 text-right '><input type='hidden' id='hiddenAmt_"+row[6]+"' value='"+row[6]+"'/> <span class='convert_lakhs_td' id='amount_"+row[6]"'>"+ row[5]+  "</span></td>";
						tableData += "<td> <div class='dropdown show'> <a class='btn btn-secondary dropdown-toggle' data-toggle='dropdown' onmouseover='viewApplicationStatus("+row[0]+")' aria-haspopup='true' aria-expanded='false'><i class='icon-th-large icon-1halfx blue pad-top' aria-hidden='true'></i>";
						tableData += "</a><ul class='dropdown-menu pull-right' aria-labelledby='dropdownMenuLink' id='"+row[0]+"'></ul></div></td> </tr>	";
				}
					$('#example > tbody').html('');
					$('#example').append(tableData);
					convertToLakhsForComponent(tableId)
					$('#example').DataTable({
						 "bLengthChange": false,

					 });
	                        
					}
				
				});
		}
	//End
	
	function convertToLakhsForComponent(tableId){
		$(".convert_lakhs_td").each(function() {	
			var fieldId=this.id;
			var fieldValue = this.innerText;
			if(fieldId){
				var convertedStringAmount = convertINRToAmount(fieldValue);
				
				var amount = (parseFloat(convertedStringAmount))/100000;
				
				var convertedIntegerAmount=convertAmountToINR(roundUpTo2Decimal(amount,2));
				
				$("#"+fieldId).text(convertedIntegerAmount +' Lakhs');
			}
		});
		}