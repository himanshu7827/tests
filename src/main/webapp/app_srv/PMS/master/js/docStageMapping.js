var nameRegex='';
var amountRegex='';
var nameErrorMessage='';
sequenceErrorMessage='';
messageResource.init({	  
filePath : '/PMS/resources/app_srv/PMS/global/js'
});

messageResource.load('regexValidationforjs', function(){
	  // get value corresponding  to a key from regexValidationforjs.properties  
	nameRegex= messageResource.get('name.regex', 'regexValidationforjs');
	nameErrorMessage = messageResource.get('name.regex.message', 'regexValidationforjs');
	
	amountRegex= messageResource.get('amount.regex', 'regexValidationforjs');
	sequenceErrorMessage = messageResource.get('sequence.regex.message', 'regexValidationforjs');
	});





$(document).ready(function() {
		var table = $('#example').DataTable();
		table.destroy();
		
	
		 $('#userinfo-message').delay(5000).fadeOut();
			$('#delete').click(function(){
				DocStageMapDelete();
			});
			  $('.select2Option').select2({
			    	 width: '100%'
			    });
			
	});


	

$(document).ready(function(){	

$('#doc_name_row').hide();
$('#doc_seq_row').hide();
$('#is_mand_row').hide();



$('#new_record').click(function() {
	
	$('#doc_name_row').show();
	$('#doc_seq_row').show();
	$('#is_mand_row').show();
	
});

	$('#reset').click(function(){
		resetForm();
		 $('#form_doc_stage_mst').data('bootstrapValidator').resetForm(true);
	});
	 console.log(amountRegex);
	 console.log(sequenceErrorMessage);
	 
	$('#form_doc_stage_mst').bootstrapValidator({
//	      live: 'disabled',
		excluded: ':disabled',
	      message: 'This value is not valid',
	      feedbackIcons: {
	          valid: 'glyphicon glyphicon-ok',
	          invalid: 'glyphicon glyphicon-remove',
	          validating: 'glyphicon glyphicon-refresh'
	      },
	      fields: {  
	    	  docSeq: {
	              group: '.col-md-12',
	              validators: {
	                 
	                  stringLength: {
	                        max: 4,
	                        message: 'The Sequence Number must be less than 4 characters'
	                    },
	                    regexp: {
	                        regexp: /^\d+$/,
	                       message: 'Only Numeric values are allowed'
	                    }
	              }
	          },stageId:{
	        	  group: '.col-md-12',
	        	  validators: {
	                  callback: {
	                      message: 'Please choose Stage',
	                      callback: function(value, validator) {
	                          // Get the selected options
	                          var options = validator.getFieldElements('stageId').val();
	                          return (options != 0);
	                      }
	                  }
	              }
	          },documentId:{
	        	  group: '.col-md-12',
	        	  validators: {
	                  callback: {
	                      message: 'Please choose Document',
	                      callback: function(value, validator) {
	                          // Get the selected options
	                          var options = validator.getFieldElements('documentId').val();
	                          return (options != 0);
	                      }
	                  }
	              }
	          }
	         
	          
	      
	         
	      }
	  });
	

	
	
	
	
	
	
	$('#save').click(function(){
		$('#new_record').hide();
	/*	$('#docTypeName').validatebox({
		    required: true,
		    validType: ['minLength[1]','maxLength[500]']
		});
		$('#strStageName').validatebox({
		    required: true,
		    validType: ['minLength[10]','maxLength[500]']
		}); 
		
	
		
		$('#docSeq').validatebox({		    
		    validType: ['maxLength[20]']
		});*/
		
		var istoggle="";
	   	 if($("#toggle-on").prop("checked")==true) {	   		 
	   		 istoggle=true;
	   		 }
	   	 else if($("#toggle-off").prop("checked")==true){
	   	  istoggle=false;
	   	 }
	   	 $('#mandatory').val(istoggle);
		
		  /* if($('#form_doc_stage_mst').form('validate')){				
				document.getElementById("form_doc_stage_mst").submit();
				resetForm();
		   }*/
	   	 
	   	var bootstrapValidator = $("#form_doc_stage_mst").data('bootstrapValidator');
		bootstrapValidator.validate();
	    if(bootstrapValidator.isValid()){
	    	console.log(' Validated');
	    	document.getElementById("save").disabled = true; 
	    	$('#form_doc_stage_mst')[0].submit();
			return true;	
		}else{
			console.log('Not Validated');
			 
		}
	    
	    $('#form_doc_stage_mst').data('bootstrapValidator').resetForm(true);
	});
		
});

function resetForm(){
	$('#numId').val('');
	$('#docTypeName').val('');
	$('#strStageName').val('');
	$('#docSeq').val('');
	$('#save').text('Save');
}




$(document).on('change','#stageId',function(e){
	var stageId = $('#stageId').val();
	if(stageId == 0){
		$('#datatable_box').addClass('hidden');
		$('#example').addClass('hidden');
		
		$('#record_span').addClass('hidden');
		$('#new_record').addClass('hidden');
		
		table.destroy();
	}else{
		$('#datatable_box').removeClass('hidden');
		$('#example').removeClass('hidden');
		$('#record_span').removeClass('hidden');
		$('#new_record').removeClass('hidden');
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
	
		var value = $('#stageId').val();
		//alert("on change   "+value);
		 $.ajax({
				type:"POST",
				url:"/PMS/mst/getDocumentByStageId",
				data:{stageId:value},
				success:function(response){
					if(response.length  >0){
						var tableData ="<tbody>";
						for(var i = 0 ; i <response.length;i++){					
							tableData+="<tr><td><input type='checkbox' class='CheckBox' id='CheckBox'  name='numIds' value="+response[i].numId+"/></td> <td>"+response[i].docTypeName+" </td> <td>"+response[i].strStageName+" </td><td> "+response[i].docSeq+"</td></tr>";
										
						}
					
						tableData+='</tbody>';
						$('#example tbody').empty();
						$('#example').append(tableData);
						table.destroy();						
						table = $('#example').DataTable();
						
					}else{
						$('#example').addClass('hidden');
						table.destroy();	
						
					}
					
					
				},

	       
	        });
	}
		
	});


//Delete
function DocStageMapDelete(){
	alert("dfsff");
    var chkArray = [];
     
    $(".CheckBox:checked").each(function() {
        chkArray.push($(this).val());
        alert($(this).val());
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
		      dangerMode: true,
		    }).then(function(isConfirm) {
		      if (isConfirm) {
		    	  alert("inside confirm");
			    	  $("#numIds").val(selected);
			  		  submit_form_delete();
			      }
	  	 /* if(confirm("Are you sure you want to delete the record"))
		  {
	  		  
	       $("#userinfo-message").show().delay(3000).fadeOut();
	    }*/
			      else{
			    	  
			      return false;
	    }
    });
    }
    else{
        swal("Please select at least one Detail to delete");  
}
 
}

function submit_form_delete()
{
	alert("inside submit_form_delete");
document.form_doc_stage_mst.action = "/PMS/mst/deleteDocStageMapping";
document.getElementById("form_doc_stage_mst").submit();
}
