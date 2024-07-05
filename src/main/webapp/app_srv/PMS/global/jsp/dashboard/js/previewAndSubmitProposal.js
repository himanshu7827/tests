$(document).ready(function() {
	populateData();
	
});

function populateData(){
	
	previousOrg = previousOrg.replace(/\\n/g, "\\n")  
    .replace(/\\'/g, "\\'")
    .replace(/\\"/g, '\\"')
    .replace(/\\&/g, "\\&")
    .replace(/\\r/g, "\\r")
    .replace(/\\t/g, "\\t")
    .replace(/\\b/g, "\\b")
    .replace(/\\f/g, "\\f");
//remove non-printable and other non-valid JSON chars
	previousOrg = previousOrg.replace(/[\u0000-\u0019]+/g,"");
	
	
	$('#collaborativeOrgDtlTbl').find("tr:gt(0)").remove();
	var inputData = JSON.parse(previousOrg);
	if(inputData.length >0){
		jQuery(inputData).each(function(i, item){
			var SerialNo=i+1;
			var rowId = "tr_"+item.encNumId;
			
				var tableRow = "<tr id='"+rowId+"'> ";
				tableRow+= "<td> "+SerialNo+"</td>";
				tableRow+= "<td> "+item.organisationName+"</td>";
				tableRow+= "<td>  "+item.contactNumber+"</td>";
				tableRow+= "<td> "+item.email+"</td>";
				tableRow+= "<td> "+item.website+"</td>";
			
				tableRow+="<td> "+item.organisationAddress+"</td>";
							
				tableRow +="</tr>";
				
				$('#example1').append(tableRow);
				
		});
	}else{
		$('#CollaborationDetails').addClass('hidden');
	}
	

}



$('#save').click(function(){	
	var proposalCost=$("#proposalCost").val();
	var totalProposalCost=$("#totalProposalCost").val();
	var encApplicationId =$('#encApplicationId').val();
	if(parseFloat(totalProposalCost)<parseFloat(proposalCost)){
		sweetSuccess("Attention","CDAC Noida Outlay Share can not be greater than Total Proposal Cost");
		return false;
	}
	  swal({
		  title: "Save Proposal",
		  type: 'success',
		  text: "Are you Sure? You wish to save the proposal?",
		  
		  
	      buttons: [
	                'No',
	                'Yes'
	              ],	     
	    }).then(function(isConfirm) {
	      if (isConfirm) {
			 console.log('Submit');
				$.ajaxRequest.ajax({
					type : "POST",
					url : "/PMS/mst/submitProposal",
					data : {									
							"encApplicationId":encApplicationId
						},
					success : function(response) {
						if (response === true){
							sweetSuccess('Success','Proposal Saved Successfully!');	
							
							setTimeout(window.location.href='/PMS/ViewApplicationDetails', 3000)
						}else{
							sweetSuccess('Error','Something went wrong!');
						}						
					}, error: function(data) {
						sweetSuccess('Error','Something went wrong!');
				   }
			});
	      } else {
	    	  console.log('Leave');
	      }
	    }); 
});

function goprev()
{
	 var path=$(location).attr('pathname');  
	 document.getElementById("form1").action="/PMS/prevRedirectPage?path='"+path+"'&moduleTypeId=1&uniqueId="+$("#applicationId").val();
		document.getElementById("form1").method="POST";
		document.getElementById("form1").submit();

}


