function loadGroupWiseProposals(encGroupId){
	
	
	var startDate = $('#startDate').val();
	var endDate = $('#endDate').val();
	//alert(startDate);
	if(startDate && endDate)
	{
		$('#groupWiseProject').removeClass('hidden');
		$('#groupWiseProjectDtl').find('div').remove();
	var url = "/PMS/mst/ViewCompletedProjectDetails?encGroupId="+encGroupId+"&startDate="+startDate+"&endDate="+endDate;
	
	var data='<div > <iframe id="test" src='+url+' width="100%;" height="100%;" frameborder="0" ></iframe> </div>';
	
	$('#groupWiseProjectDtl').append(data);
	}
}




$(document).ready(function() {

	//$('#example').DataTable();
	var  table= $('#example').DataTable( {
	 dom: 'Bfrtip',
		"paging":   false, 
		"ordering": false,
		buttons: [
			           'excel', 'pdf', 'print'
			        ],
	"columnDefs": [ {
            "orderable": false,
        "targets": 0
    } ],
    "order": [[ 1, 'asc' ]]
} );
	
	table.on('order.dt search.dt', function () {
		table.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
	           cell.innerHTML = i+1;
	           table.cell(cell).invalidate('dom');
	     });
	}).draw();
	
	$('#example .filters th[class="comboBox"]').each( function ( i ) {
        var colIdx=$(this).index();
            var select = $('<select style="width:100%" ><option value="">All</option></select>')
                .appendTo( $(this).empty() )
                .on( 'change', function () {
                 var val = $.fn.dataTable.util.escapeRegex(
                                $(this).val()
                            );
                 table.column(colIdx)
                        .search( val ? '^'+val+'$' : '', true, false)
                        .draw();
                } );
     
            table.column(colIdx).data().unique().sort().each( function ( d, j ) {
                select.append( '<option value="'+d+'">'+d+'</option>' )
            } );
        } );
	

	 // Setup - add a text input
   $('#example .filters th[class="textBox"]').each( function () {                 
       $(this).html( '<div class="lighter"><input type="text"  style="width:100%" /></div>' );

   } );
   
   table.columns().eq( 0 ).each( function ( colIdx ) {
       $( 'input', $('.filters th')[colIdx] ).on( 'keyup change', function () {
           table
                       .column( colIdx )
                       .search( this.value )
                       .draw() ;
               
                                       } );
} );
  
   $('#amtreceive')
	.on(
			'show.bs.modal',
			function(event) {
				var button = $(event.relatedTarget);
				var input = button.data('whatever');				
				var inputs = input.split(';');
				var projectId = inputs[0];
				
				var split1 = $('#td2_'+projectId).find('p:eq(0)').text();
				var split2 = $('#td2_'+projectId).find('p:eq(1)').text();
				
				$('#projectForPayment').text(split1);
				$('#clientIdForPayment').text(split2);
				var startDate = inputs[1];
				var projectCost = inputs[2].trim();
				// console.log(projectCost);
				var tableData = "";
				$.ajax({
							type : "POST",
							url : "/PMS/mst/getProjectPaymentReceivedByProjectId",
							data : "encProjectId=" + projectId,
							async : false,
							success : function(response) {
								// console.log(response);
								var SumOfReceivedAmt = 0
								var paymentReceive = 0;
								var paymentDue = 0;
								
								paymentDue = parseFloat(convertINRToAmount(projectCost));
								tableData += "<tr><td>"
										+ startDate
										+ "</td><td><b>Total Project Cost</b></td><td class='text-right'><b>"
										+ convertAmountToINR(paymentDue)
										+ "</b></td><td class='text-right'>â‚¹ "
										+ paymentReceive + "</td></tr>";

								for (var i = 0; i < response.length; i++) {
									var row = response[i];
									var newAmt = row.numReceivedAmount;
									SumOfReceivedAmt += newAmt;
									tableData += "<tr><td>"
											+ row.strdtPayment + "</td> ";
									tableData += "<td><p>"
											+ row.strPaymentMode + " , "
											+ row.strUtrNumber
											+ "</p><p class='pad-left'><b>";
									if (row.encInvoiceId != null) {
										tableData += "Payment Received Against Invoice Ref No. : </b><a title='Click here to view Invoice details' data-toggle='modal' data-target='#invoiceDetail' onclick=viewInvoiceDetails('"
												+ row.encInvoiceId
												+ "') class='text-center'>"
												+ row.invoiceCode
												+ "</a></p></td>";
										tableData += "<td class='text-right'>"
												+ row.paymentDue
												+ "</td> <td class='text-right'>"
												+ convertAmountToINR(newAmt)
												+ "</td></tr>";

									} else {
										tableData += "Payment Received without Invoice Details : </b></p></td>";
										tableData += "<td class='text-right'>"
												+ row.paymentDue
												+ "</td> <td class='text-right'>"
												+ convertAmountToINR(row.numBalanceAmount)
												+ "</td></tr>";

									}
								}
								if (SumOfReceivedAmt
										&& SumOfReceivedAmt > 0) {
									tableData += "<tr><td colspan='3' class='font_eighteen text-right'><b>Total Payment Received:</b></td><td class='font_eighteen text-right'><b>"
											+ convertAmountToINR(SumOfReceivedAmt)
											+ "</b></td> </tr>";
									tableData += "<tr><td colspan='3' class='font_eighteen text-right'><b>Payment Remaining:</b></td><td class='font_eighteen text-right'><b>"
											+ row.paymentDue
											+ "</b></td> </tr>";

								}
								tableData += '</tbody>';
								$('#datatable tbody').empty();
								$('#datatable').append(tableData);
							},
							error : function(e) {
								alert('Error: ' + e);
							}
						});
				$(this).off('shown.bs.modal');
			});
   
	
});

$(document).ready(function() {
   
	  $('.select2Option').select2({
	    	 width: '100%'
	    });
	    
    
    
    var currentDate = new Date();
    
    $('#startDate').datepicker({
 	   dateFormat: 'dd/mm/yy', 
 	    changeMonth: true,
 	    changeYear:true,
 	    maxDate:currentDate,
 	    onSelect: function(date){ 	    	
 	    	var dateData = date.split('/');
 	    	var monthval = parseInt(dateData[1])-1;
 	    	 var selectedDate = new Date(dateData[2],monthval,dateData[0]);	    	  
 	      
 	        $("#endDate").datepicker( "option", "minDate",selectedDate );  
 	       getAllCompletedProjectByDate();
 	    }
   
   });
    
    $('#endDate').datepicker({
 	   dateFormat: 'dd/mm/yy', 
 	   changeMonth: true,
 	   changeYear:true ,
 	  maxDate:currentDate,
 	 onSelect: function(date){ 	
 	 getAllCompletedProjectByDate();
 	 }
    });
    
    
});
function getAllCompletedProjectByDate()
{
	
var startDate = $('#startDate').val();
var endDate = $('#endDate').val();

if(!startDate){
	startDate ='';
}

if(!endDate){
	endDate= '';
}

if(startDate && endDate)
	{
	
		openWindowWithPost('GET','/PMS/mst/getAllCompletedProject',{"startDate" : startDate,"endDate":endDate},'_self');
	}
}