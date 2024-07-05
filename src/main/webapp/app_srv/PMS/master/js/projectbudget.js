var amountRegex='';
var amountErrorMessage='';
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


$(document).ready(function(){
		
	$('#save').click(function(){		
		var projectId =$('#projectId').val();
			  swal({
				  title: "Attention",
				  text: "A new Copy of Budget will be created. Are you sure?",
				  type: 'success',
				  
			      buttons: [
			                'No',
			                'Yes'
			              ],	     
			    }).then(function(isConfirm) {
			      if (isConfirm) {
					 console.log('Lock');
						$.ajaxRequest.ajax({
							type : "POST",
							url : "/PMS/saveProjectBudgetDetails",
							data : {									
									"numProjectId":projectId
								},
							success : function(response) {
								if (response === true){
									sweetSuccess('Attention','Budget Copy Created Successfully');
								}else{
									sweetSuccess('Attention','Something went wrong!');
								}						
							}, error: function(data) {
						 	  /* if(data.responseText == 'Unauthorised Access'){
								   window.location.href= "/PMS/Homepage";
							   }else{
						        objConfig.error(data, textStatus, jqXHR);   
							   }*/
						   }
					});
			      } else {
			    	  console.log('Leave');
			      }
			    }); 
				
		})
	
});



function validateAmount() {
	var validationFlag= true;
		
		$('.validateamt')
				.each(
						function(index) {
							var fieldId = this.id;
							var fieldValue = this.value;							
							if (!fieldValue) {								
								sweetSuccess('Attention','Amount is required in each row' );
								validationFlag= false;
							}
							else{
								if(!validateNumeric(fieldValue)){
									$('#'+fieldId).val('');
									sweetSuccess("Attention",numericErrorMessage);
									validationFlag= false;
									return false;
								}
							}
						});
		
		return validationFlag;
	}


function validateNumeric(amount){		
	numericRegex = new RegExp(numericRegex);			
	 if (numericRegex.test(amount)) {  
	    return (true)  ;
	  }else{
		  sweetSuccess("Attention",numericErrorMessage);
		  return (false)  ; 
	  }		
}

function saveAmount(key,totalid){
	var fieldVal = $('#'+key).val();	
	
	if(!fieldVal){
		$('#'+key).val(0.0);
		fieldVal =0;
	}
	

		console.log(fieldVal);
		if(validateNumeric(fieldVal)){
			console.log('Validated');
			var projectId = $('#projectId').val();
			if(projectId){
				saveBudgetAmount(key,fieldVal,projectId);
			}			
		}else{
			console.log('Not Validated');
			$('#'+key).val(0.0);
		}

	var total=0;
	var resultArray1 = $('#'+key).closest('tr').find('td').find('input').map( function()
			{
			return $(this).val();
		}).get();
	  for(var i=0;i<resultArray1.length;i++)
      {                  
        if(isNaN(resultArray1[i])){
        continue;
         }
          total += Number(resultArray1[i]);
       }
	  $("[id='"+totalid+"_Total']").text(total);
   //$('#'+totalid+"_Total").text(total);
    total=0;
    $('.total').each(function() {
    	
    	total += parseFloat($(this).text());
   });
    $('#grandTotal').text(total);
}

function updateTotal(key){
	var fieldVal = $('#'+key).val();	
}
function saveBudgetAmount(key,amount,projectId){
	
	$.ajaxRequest.ajax({
		type : "POST",
		url : "/PMS/saveProjectBudgetDetailsMaster",
		data : {
				"key":key,
				"amount":amount,
				"numProjectId":projectId
			},
		success : function(response) {
			/*if (response === true){
				sweetSuccess('Attention','Budget Copy Created Successfully');
			}else{
				sweetSuccess('Attention','Something went wrong!');
			}*/					
		}, error: function(data) {
	 	  /* if(data.responseText == 'Unauthorised Access'){
			   window.location.href= "/PMS/Homepage";
		   }else{
	        objConfig.error(data, textStatus, jqXHR);   
		   }*/
	   }
});
	
}

function goprev()
{
	 var path=$(location).attr('pathname');  

	 document.getElementById("form1").action="/PMS/prevRedirectPage?path='"+path+"'&workflowTypeId=3&uniqueId="+$("#projectId").val();
		document.getElementById("form1").method="POST";
		document.getElementById("form1").submit();

}



function gonext()
{
	 var path=$(location).attr('pathname');  

	 document.getElementById("form1").action="/PMS/nextRedirectPage?path='"+path+"'&workflowTypeId=3&uniqueId="+$("#projectId").val();
		document.getElementById("form1").method="POST";
		document.getElementById("form1").submit();

}


$(".highlight_tr > td > input").focus(function(e){
    $(this).parent().parent().addClass('highlight_row');
});

function redirectPage(path){
	var encProjectId = $('#encProjectId').val();
	//alert(projectId);
	openWindowWithPost('GET',path,{"encProjectId" : encProjectId},'_self');
}