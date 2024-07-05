$('#amtreceive').on('show.bs.modal', function(event) {
		var button = $(event.relatedTarget);
		var input = button.data('whatever');
		var inputs = input.split(';');
		var projectId= inputs[0];
		
		var resultArray = $('#'+projectId).find('td').map( function()
				{
				return $(this).text();
				}).get();
		var result=resultArray[2].trim();
		var resultSplit=result.split('\n');
		var split1=resultSplit[0];
		var split2=resultSplit[1];
		$('#projectForPayment').text(split1);
		$('#clientIdForPayment').text(split2);
		var startDate= inputs[1];
		var projectCost= inputs[2].trim();
		//console.log(projectCost);
		var tableData = "";
	$.ajax({
    		type : "POST",
    		url : "/PMS/mst/getProjectPaymentReceivedByProjectId",
    		data : "encProjectId=" + projectId,
    		async:false,
    		success : function(response) {
    		//console.log(response);
    			  var SumOfReceivedAmt=0
    			  var paymentReceive=0;
    			  var paymentDue=0;
     			  //paymentDue =parseFloat(convertINRToAmount(projectCost))*100000;
     			 paymentDue =parseFloat(convertINRToAmount(projectCost));
  			   tableData+="<tr><td>"+startDate+"</td><td><b>Total Project Cost</b></td><td class='text-right'><b>"+convertAmountToINR(paymentDue)+"</b></td><td class='text-right'>₹ "+paymentReceive+"</td></tr>";

			   for(var i =0;i<response.length;i++){
					var row = response[i]; 
    			   var newAmt =row.numReceivedAmount;
    			   SumOfReceivedAmt+=newAmt;
   			       tableData+="<tr><td>"+row.strdtPayment+"</td> ";
    			  tableData+="<td><p>"+row.strPaymentMode+" , "+row.strUtrNumber+"</p><p class='pad-left'><b>" ;
    			  if(row.encInvoiceId!=null){
    				  tableData+="Payment Received Against Invoice Ref No. : </b><a title='Click here to view Invoice details' data-toggle='modal' data-target='#invoiceDetail' onclick=viewInvoiceDetails('"+row.encInvoiceId+"') class='text-center'>"+row.invoiceCode+"</a></p></td>";
    					tableData+="<td class='text-right'>"+row.paymentDue+"</td> <td class='text-right'>"+convertAmountToINR(newAmt)+"</td></tr>";

    			  }else{
    				  tableData+="Payment Received without Invoice Details : </b></p></td>";  
    					tableData+="<td class='text-right'>"+row.paymentDue+"</td> <td class='text-right'>"+convertAmountToINR(row.numBalanceAmount)+"</td></tr>";

    			  }
    			}
    		if(SumOfReceivedAmt && SumOfReceivedAmt>0){
    			tableData+="<tr><td colspan='3' class='font_eighteen text-right'><b>Total Payment Received:</b></td><td class='font_eighteen text-right'><b>"+convertAmountToINR(SumOfReceivedAmt)+"</b></td> </tr>";
    			tableData+="<tr><td colspan='3' class='font_eighteen text-right'><b>Payment Remaining:</b></td><td class='font_eighteen text-right'><b>"+row.paymentDue+"</b></td> </tr>";

    		}
    			tableData+='</tbody>';
				$('#datatable tbody').empty();						
				$('#datatable').append(tableData);		
    		},
    		error : function(e) {
    			alert('Error: ' + e);			
    		}
    	});				
	});

$(document).ready(function() {

		//$('#example').DataTable();
		var  table= $('#example').DataTable( {
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
       
		
});

var scrollPos = 0;
$('#clientDetails').on('show.bs.modal', function(event) {
	
	var button = $(event.relatedTarget);
	var input = button.data('whatever');
	//console.log("data"+input);
	if (typeof input !== 'undefined')
	{
	var inputs = input.split(';');
	var clientId= inputs[0];
	var projectId= inputs[1];
	var endUserId= inputs[2];
	loadClientData(clientId,projectId,endUserId);
	}
	  $('body').css({
          overflow: 'hidden',
          position: 'fixed',
          top : -scrollPos
      });
})
 .on('hide.bs.modal', function (){
        $('body').css({
            overflow: '',
            position: '',
            top: ''
        }).scrollTop(scrollPos);
    });

