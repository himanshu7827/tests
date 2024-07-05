$(document).ready(function() {
	
		$('#example').DataTable();
		 $('#userinfo-message').delay(5000).fadeOut();
		$('#delete').click(function(){
			ProcessDelete();
		});
	});

	$(document).on('click','#edit',function(e){
		
		var resultArray = $(this).closest('tr').find('td').map( function()
				{
				return $(this).text();
				}).get();
		console.log(resultArray);
		
		$('#numProcessId').val(resultArray[0].trim());
		$('#strProcessName').val(resultArray[1].trim());
		$('#strProcessPath').val(resultArray[2].trim());
		$('#strProcessDescription').val(resultArray[3].trim());
		$('#strProcessEcode').val(resultArray[4].trim());
			if(resultArray[5].trim()=='Active'){
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
	});
	
	$('#save').click(function(){
		
		$('#strProcessName').validatebox({
		    required: true,
		    validType: ['minLength[1]','maxLength[250]']
		});
		
		$('#strProcessPath').validatebox({
		    required: true,
		    validType: ['minLength[1]','maxLength[100]']
		}); 
		
		$('#strProcessDescription').validatebox({
		    required: true,
		    validType: ['minLength[1]','maxLength[250]']
		}); 
		
		$('#strProcessEcode').validatebox({
		    required: true,
		    validType: ['minLength[1]','maxLength[50]']
		}); 
		
		
		var istoggle="";
	   	 if($("#toggle-on").prop("checked")==true) {	   		 
	   		 istoggle=true;
	   		 }
	   	 else if($("#toggle-off").prop("checked")==true){
	   	  istoggle=false;
	   	 }
	   	 $('#valid').val(istoggle);
		
		   if($('#form1').form('validate')){				
				document.getElementById("form1").submit();
				resetForm();
		   }
	});
		
});

function resetForm(){
	$('#numProcessId').val('');
	$('#strProcessName').val('');
	$('#strProcessPath').val('');
	$('#strProcessDescription').val('');
	$('#strProcessEcode').val('');

	$('#save').text('Save');
}


function ProcessDelete(){
    var chkArray = [];
     
    $(".CheckBox:checked").each(function() {
        chkArray.push($(this).val());
    });
    
    var selected;
    selected = chkArray.join(','); 
   
    if(selected.length >= 1){
    	
    	
    	 swal({
		      title: "Are you sure you want to delete the record?",
		      icon: "warning",
		      buttons: [
		                'No',
		                'Yes'
		              ],
		      
		    }).then(function(isConfirm) {
		      if (isConfirm) {
			    	  $("#numIds").val(selected);
			  		  submit_form_delete();
			      }
			      else{
			    	  
			      return false;
	    }
    });
    }
    else{
    
        swal("Please select at least one Role to delete");  
}
 
}

function submit_form_delete()
{
document.form1.action = "/PMS/mst/deleteProcessMaster";
document.getElementById("form1").submit();
}

