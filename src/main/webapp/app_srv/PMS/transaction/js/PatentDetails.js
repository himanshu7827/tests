$('.select2Option').select2({
	 width: '100%'
});
/*messageResource.load('regexValidationforjs', function(){
	  // get value corresponding  to a key from regexValidationforjs.properties  
	nameRegex= messageResource.get('name.regex', 'regexValidationforjs');
	nameErrorMessage = messageResource.get('name.regex.message', 'regexValidationforjs');
	urname = messageResource.get('nonRestrictname.regex', 'regexValidationforjs');
	urErrorMessage = messageResource.get('nonRestrictname.regex.message', 'regexValidationforjs');
	});*/
//date picker

$('#dtFilingDate').datepicker({
	   dateFormat: 'dd/mm/yy', 
	    changeMonth: true,
	    changeYear:true,  
	    onSelect: function(date){
	    	$('#strDateOfApplication').trigger('input');
	    	}
});
$('#dtAwardDate').datepicker({
	   dateFormat: 'dd/mm/yy', 
	    changeMonth: true,
	    changeYear:true,  
	    onSelect: function(date){
	    	$('#strDateOfApplication').trigger('input');
	    	}
});
$(document).ready(function() {
    var table = $('#inboxTable').DataTable( {
    	"paging":   false,
        "ordering": false,
        "searching": false,
        rowReorder: true,
        responsive: {
	        details: {
	            type: 'column',
	            target: 'tr'
	        }
	    },
	    columnDefs: [ {
	        className: 'control',
	        orderable: false,
	        targets:   1
	    } ],
	    order: [ 1, 'asc' ]
    } );
} );

$('#add').click(function(){
	//alert("here");
	var bootstrapValidator = $("#form1").data('bootstrapValidator');
	bootstrapValidator.validate();
	//alert(bootstrapValidator.isValid());
	if(document.getElementById('isfiledy').checked && $('#dtFilingDate').val()==''){
		swal("Filing date cannot be empty")
		
	}
	else if(document.getElementById('strIsAwardedy').checked && $('#dtAwardDate').val()==''){
		swal("Award date cannot be empty")
		
	}
	else if(!($('#isfiledy').is(':checked'))&&(!($('#strIsAwardedy').is(':checked'))))
			
	{ swal("Please select status whether Patent is Filed or Awarded!."); }
	
	
	else if (bootstrapValidator.isValid()){
		document.getElementById("add").disabled = true; 
		/*document.form1.submit();*/
		$( "#form1")[0].submit();
		return true;
	    }
    else{
    	//swal(" not validated");
    }
});

function filedate(){
	// alert();
	if(document.getElementById('isfiledy').checked){ 
		
		 
		  $("#filingdate").show();
		  $("#Awarddate").hide();
		  $('#dtAwardDate').val('');
		
}
	else{
		$("#filingdate").hide();
		$('#dtFilingDate').val('');
		
	}
	}

function awarddate(){
	// alert();
	if(document.getElementById('strIsAwardedy').checked){ 
		
		 
		  $("#Awarddate").show();
		  $("#filingdate").hide();
			$('#dtFilingDate').val('');
		
}
	else{
		$("#Awarddate").hide();
		$('#dtAwardDate').val('');
		
	}
	}


			

$(document).ready(function(){
	$("#filingdate").hide();
	$("#Awarddate").hide();
	 
});



function resetData(){
	
	$('#strPatentTitle').val('');
	$('#strInventorName').val('');
	$("#filingdate").hide();
	$('#strInventorAddr').val('');
	$('#dtFilingDate').val('');
	$("#filingdate").hide();
	$('#strCountryDetials').val('');
/*	$('#strStatus').val('');*/
	
	$('#dtAwardDate').val('');
	$("#Awarddate").hide();
	$('#isfiledy').prop('checked', false);
	$('#strIsAwardedy').prop('checked', false);
	$('#form1').data('bootstrapValidator').resetForm(true);

}

$('#form1').bootstrapValidator({
//    live: 'disabled',
	excluded: ':disabled',
    message: 'This value is not valid',
    feedbackIcons: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
    },
    fields: {
  	  
    	strPatentTitle: {
            group: '.col-md-12',
            validators: {
                notEmpty: {
                    message: 'Patent Title is required and cannot be empty'
                },
                stringLength: {
                    max: 500,
                    message: ' must be less than 500 characters'
             }
               
            }
        },
        strInventorName: {
            group: '.col-md-12',
            validators: {
                notEmpty: {
                    message: 'Inventor name is required and cannot be empty'
                },
                stringLength: {
                    max: 200,
                    message: 'Inventor Name must be less than 200 characters'
             }
               
            }
        },
           
        strInventorAddr: {
            group: '.col-md-12',
            validators: {
                notEmpty: {
                    message: 'Inventor Address is required and cannot be empty'
                },
                stringLength: {
                    max: 500,
                    message: ' must be less than 500 characters'
             }
               
            }
        },
        strReferenceNum: {
            group: '.col-md-12',
            validators: {
                notEmpty: {
                    message: 'Reference Number is required and cannot be empty'
                },
                stringLength: {
                    max: 200,
                    message: ' must be less than 200 characters'
             }
               
            }
        },
        strCountryDetials: {
            group: '.col-md-12',
            validators: {
                notEmpty: {
                    message: 'Country Details is required and cannot be empty'
                },
                stringLength: {
                    max: 500,
                    message: ' must be less than 500 characters'
             }
               
            }
        },
   }
});

function viewProjectDetailss(url){
	var encProjectId=$('#encProjectId').val();
	var encCateoryId=$('#encCategoryId').val();
	
	openWindowWithPost('POST','/PMS/mst/downloadTaskFile',{"encId" : encProjectId,"encCategoryId":encCateoryId},'_blank');
	/*	var win = window.open(url, '_self');
		if (win) {	    
		    win.focus();
		} else {	   
		    alert('Please allow popups for this website');
		}*/
	}
	


$(document).on('click','#Edit',function(e){
	resetData();
	 $("#add").prop('value', 'Update');
	 var validDt=new Date();	
	 $('#viewpublicationdt').show();	
	 var resultArray1 = $(this).closest('tr').find('td').map( function()
					{
					return $(this).text();
				}).get();
			
	/*		var checkID = $(this).closest('tr').find('input[type=checkbox]').map( function()
					{
					return $(this).val();
				}).get();
			*/
			var hiddenID = $(this).closest('tr').find('input[type=hidden]').map( function()
					{
					return $(this).val();
				}).get();
			$('#projectPatentId').val($.trim(hiddenID));
			//alert($('#projectPatentId').val());
			$('#strPatentTitle').val($.trim(resultArray1[3]));
			$('#strInventorName').val($.trim(resultArray1[4]));
			$('#strInventorAddr').val($.trim(resultArray1[5]));
			$('#strReferenceNum').val($.trim(resultArray1[6]));
			if($.trim(resultArray1[7])=="Filed"){
				$("#isfiledy").prop("checked", true);
				$("#filingdate").show();
				$('#dtFilingDate').val($.trim(resultArray1[8]));
			}
			if($.trim(resultArray1[7])=="Awarded"){
				$("#strIsAwardedy").prop("checked", true);
				$("#Awarddate").show();
				$('#dtAwardDate').val($.trim(resultArray1[8]));
			}
			
			//$('#updatedtFilingDate').val($.trim(resultArray1[7]));
			/*if($.trim(resultArray1[8])=="Y"){
				$("#strIsAwardedy").prop("checked", true);
				
			}
			if($.trim(resultArray1[8])=="N"){
				$("#strIsAwardedn").prop("checked", true);
			}*/
			$('#strCountryDetials').val($.trim(resultArray1[9]));
			/*$('#strStatus').val($.trim(resultArray1[10]));*/
		
			});

$('#delete').click(function(){
	deletePatentDetails();
	});
function deletePatentDetails(){

    var chkArray = [];
     
    $(".CheckBox:checked").each(function() {
        chkArray.push($(this).val());
        
    });
    
    var selected;
    selected = chkArray.join(','); 
	//alert("amit="+selected);
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
		    	  
			    	 var projectPatentIds =selected;
			    	  var encCategoryId=$("#encCategoryId").val();
			    		var encMonthlyProgressId=$("#encMonthlyProgressId").val();
			    		 // alert("great="+selected);
			    	  $.ajax({
				    	        type: "POST",
				    	        url: "/PMS/deletePatentDetails",	        
				    	        data:{"projectPatentIds":projectPatentIds,"encCategoryId":encCategoryId,"encMonthlyProgressId":encMonthlyProgressId},
				    	        
				    	        success: function(res) {
				    	        	if(res)
				    	        		{
				    	        		swal({
						        		    title: "",
						        		    text: "Patent Details deleted Successfully!",
						        		    type: "success"
						        		}).then(function() {
						        			location.reload();
						        		});
				    	        		
				    	        		}
				    	        	else
				    	        		{
				    	        		  sweetSuccess('Attention','Error in Patent delete!');
					    	        	  return true;
				    	        		
				    	        		}
				    	        	 
				    	       },
				    	       error: function() {
				    		        	
				    		            alert('Error');
				    		        }
				    		});

				    	  
				      
			      }
	  	 
			      else{
			    	  
			      return false;
	    }
    });
    }
    else{
        swal("Please select at least one Detail to delete");  
}
 
}