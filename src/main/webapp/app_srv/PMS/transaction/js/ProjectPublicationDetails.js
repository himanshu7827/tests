$('.select2Option').select2({
	 width: '100%'
});

//date picker

$('#dtpubdt').datepicker({
	   dateFormat: 'dd/mm/yy', 
	    changeMonth: true,
	    changeYear:true,  
	    //dateFormat: 'MM yy',
	    onSelect: function(date){
	    	$('#dtpubdt').trigger('input');
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
	//alert($('#numProjectPublicationId').val());
	var bootstrapValidator = $("#form1").data('bootstrapValidator');
	bootstrapValidator.validate();
	
	if(bootstrapValidator.isValid()){
		document.getElementById("add").disabled = true; 
		/*document.form1.submit();*/
		$( "#form1")[0].submit();
		return true;

	}else{}
	
});

function formatDate(date) {
	//alert("date = "+date);  
	
	var monthNames = [
	    "January", "February", "March",
	    "April", "May", "June", "July",
	    "August", "September", "October",
	    "November", "December"
	  ];

	
	var  today = new Date(date);
	
	  var day = today.getDate();
	  var monthIndex = today.getMonth();
	  var year = today.getFullYear();

	  return  monthNames[monthIndex] + ', ' + year;
}



function resetData(){
		$('#numProjectPublicationId').val(0);
		$('#dtpubdt').val('');
		$('#strPublicaionTitle').val('');
		$('#strAuthorDetails').val('');
		$('#strJournalName').val('');

		$('#strConferenceCity').val('');
		$('#strReferenceNumber').val('');
		
		$('#strPublisher').val('');
		$('#strOrganisation').val('');
		$('#strPublicationType').val(0);
		$("#strPublicationType").trigger('change');
		
		
		
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
  	  
    	strPublicaionTitle: {
            group: '.col-md-12',
            validators: {
                notEmpty: {
                    message: 'Publication Title is required and cannot be empty'
                },
                stringLength: {
                    max: 500,
                    message: ' must be less than 500 characters'
             }
               
            }
        },
        dtpubdt: {
            group: '.col-md-12',
            validators: {
                notEmpty: {
                    message: 'Date is required and cannot be empty'
                },
                date:{
  	        	  format: 'DD/MM/YYYY'
  	        	  }
               
            }
        },
        strPublicationType:{
      	  group: '.col-md-12',
      	  validators: {
                callback: {
                    message: 'Please choose publication type',
                    callback: function(value, validator) {
                        // Get the selected options
                        var options = validator.getFieldElements('strPublicationType').val();
                        return (options != 0);
                    }
                }
            }
        },
        strJournalName: {
            group: '.col-md-12',
            validators: {
                notEmpty: {
                    message: 'Journal name is required and cannot be empty'
                }
               
            }
        },
           
        strAuthorDetails: {
            group: '.col-md-12',
            validators: {
                notEmpty: {
                    message: 'Author Details is required and cannot be empty'
                },
                stringLength: {
                    max: 500,
                    message: ' must be less than 500 characters'
             }
               
            }
        },
        strConferenceCity: {
            group: '.col-md-12',
            validators: {
                notEmpty: {
                    message: 'Conference city is required and cannot be empty'
                },stringLength: {
                    max: 200,
                    message: ' must be less than 200 characters'
             }
               
            }
        },
        strReferenceNumber: {
            
            validators: {
            	stringLength: {
                    max: 200,
                    message: ' must be less than 200 characters'
             }
               
            }
        },
        strPublisher: {            
            validators: {
            	stringLength: {
                    max: 200,
                    message: ' must be less than 200 characters'
             }
               
            }
        },        
        strOrganisation: {            
            validators: {
            	stringLength: {
                    max: 200,
                    message: ' must be less than 200 characters'
             }
               
            }
        },
        strPublicationDescription: {            
            validators: {
            	stringLength: {
                    max: 200,
                    message: ' must be less than 200 characters'
             }
               
            }
        } 
        

        
    
       
    }
});

$(document).ready(function() {
	
	$('#description').hide();
	
});

function show(){
	var answer=document.getElementById("strPublicationType");
	 if(answer[answer.selectedIndex].value == "Other") {
		 $('#description').show();
	
		
	}
	 else{
		 $('#description').hide();
		 $('#description').val('');
		 
	 }
}



$(document).on('click','#Edit',function(e){
	$('#form1').data('bootstrapValidator').resetForm(true);
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

			
			//alert(hiddenID);
			$('#numProjectPublicationId').val($.trim(hiddenID));
			$('#strPublicationType').val($.trim(resultArray1[2]));
			$("#strPublicationType").trigger('change');
			$('#dtpubdt').val($.trim(resultArray1[3]));
			$('#strPublicaionTitle').val($.trim(resultArray1[4]));
			$('#strAuthorDetails').val($.trim(resultArray1[5]));
			$('#strJournalName').val($.trim(resultArray1[6]));
			$('#strConferenceCity').val($.trim(resultArray1[7]));
			$('#strReferenceNumber').val($.trim(resultArray1[8]));
			$('#strPublisher').val($.trim(resultArray1[9]));
			$('#strOrganisation').val($.trim(resultArray1[10]));
			$('#strPublicationDescription').val($.trim(resultArray1[12]));
			
			
		
			});