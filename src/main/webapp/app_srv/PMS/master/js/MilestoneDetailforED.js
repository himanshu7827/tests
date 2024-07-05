$(document).ready(function() {
	/*added the function of date picker by varun 0n 06-07-2023*/
	 var dateFormat = "dd/mm/yy",
	    from = $( "#from" )
	      .datepicker({
	    	dateFormat: 'dd/mm/yy', 
	  	    changeMonth: true,
	  	    changeYear:true,
	  	    maxDate: '0',
	  	    minDate : '-5Y'
	      })
	      .on( "change", function() {
	        to.datepicker( "option", "minDate", getDate( this ) );
	      }),
	    to = $( "#to" ).datepicker({
	      dateFormat: 'dd/mm/yy', 
	      changeMonth: true,
	      changeYear:true,
	      maxDate: '0',
	      minDate : '-5Y'
	    })
	    .on( "change", function() {
	      from.datepicker( "option", "maxDate", getDate( this ) );
	    });

		//$('#example').DataTable();
		var  table= $('#milestoneTable').DataTable( {
			 dom: 'Bfrtip',	
			 "paging":   false,
		        "ordering": false,
		        "info":     false,
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
		
		$('#milestoneTable .filters th[class="comboBox"]').each( function ( i ) {
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
/*       $('#milestoneTable .filters th[class="textBox"]').each( function () {                 
           $(this).html( '<div class="lighter"><input type="text"  style="width:100%" /></div>' );

       } );*/
       //var table = $('#milestoneTable').DataTable();
       table.columns().eq( 0 ).each( function ( colIdx ) {
           $( 'input', $('.filters th')[colIdx] ).on( 'keyup change', function () {
               table
                           .column( colIdx )
                           .search( this.value )
                           .draw() ;
                   
                                           } );
} );
       
		
});

function getDate( element ) {
    var date;
    try {
      date = $.datepicker.parseDate( dateFormat, element.value );
    } catch( error ) {
      date = null;
    }

    return date;
  }
/*added the button function and call the milestone table on 06-07-2023*/
$('.go-btn').click(function() {
	  var startRange = $("#from").val();
	  var endRange = $("#to").val();
	 // console.log("go-btn2 startRange"+startRange)
	 // console.log("go-btn2 endRange"+endRange)
	  if(!startRange){
		  sweetSuccess('Attention','Please select Start Range');
		  return false;
	  }
	  var selectedRange = startRange;
	  if(endRange){
		  selectedRange = selectedRange +' to '+endRange;
	  }
	 
	  $('#asOnDate').text(selectedRange);
	  //alert('Called');
	  MilestonesTable(startRange,endRange);
});

// Added below function for filter the milestone excedded data 
function calculateDates(){
	console.log
	var d = new Date();
	var symbol= $("#filterSymbol").val();
	var selValue=$("#months").val();

	if(selValue==0){
	
		$('#milestoneTable').empty();
		$('#milestoneTable').DataTable().destroy();
		var foot = $("#milestoneTable").find('tfoot');
		 if(foot.length){
			 $("#milestoneTable tfoot").remove();
		 }
		var  table= $('#milestoneTable').DataTable( {
			destroy: true,
			dom: 'Bfrtip',		       
	        "ordering": false,
	        "paging":   false,
	        buttons: [
	             'excel', 'pdf', 'print'
	        ],
	        dom: 'Bfrtip',
			"columnDefs": [ {
				
		        "orderable": false,
	            "targets": 0
	        } ],
	        "order": [[ 1, 'asc' ]]
	    } );
		
	}
	else{
		
	var lastMonth = new Date();
	lastMonth.setMonth(lastMonth.getMonth() - 1);
	var durationBefore=selValue;
	d.setMonth(d.getMonth() - durationBefore);
	 var lastDayOfLastMonth=lastday(lastMonth.getFullYear(),lastMonth.getMonth());
	 var fromMonth=d.getMonth()+1;
	 var toMonth=lastMonth.getMonth()+1;
	 fromMonth=fromMonth+"";
	 toMonth=toMonth+"";
	 lastDayOfLastMonth=lastDayOfLastMonth+"";
	
	 if(fromMonth.length==1)
	 {
		 fromMonth="0"+fromMonth; 
	 }	 
	 if(toMonth.length==1)
		{ 
		 toMonth="0"+toMonth;
		}
	 if(selValue!=0)
		{
			$("#from").val("01"+"/"+fromMonth+"/"+d.getFullYear());
			$("#to").val(lastDayOfLastMonth+"/"+toMonth+"/"+lastMonth.getFullYear());
			
		}
		else
			{
				$("#from").val("");
				$("#to").val("");
				
			}
	
		var compDate="01"+"/"+fromMonth+"/"+d.getFullYear();
//console.log(compDate + "" + symbol);
	pendingPaymentsTableWithInvoiceDate(compDate,symbol);
	}
}

var lastday = function(y,m){
	return  new Date(y, m, 0).getDate();
	}	

function pendingPaymentsTableWithInvoiceDate(compDate,symbol){	
	$('#milestoneTable').DataTable().clear().destroy();
	var groupColumn = 0;
	var tableData = '';
	$.ajaxRequest.ajax({
		type : "POST",
		url : "/PMS/mst/MilestoneExceededDeadlineDetailByDates",
		data : {"compDate":compDate,
				"symbol":symbol
				},
		success : function(response) {
		 console.log(response);
			for (var i = 0; i < response.length; i++) {
				var row = response[i];
			var strReferenceNumber=row.strProjectReference;
				if(!strReferenceNumber){
					strReferenceNumber='';
				}
				var clickFunction = "viewProjectDetails('/PMS/projectDetails/"+ row.encProjectId + "')";
			/*	tableData += '<tr><td>''</td><td> <a class="font_14" title="Click to View Complete Information" onclick='+clickFunction+'>'*/
				/*tableData += '<tr><td>'+(i+1)+'</td><td class="font_14">'+ row.groupName +  '</td><td> <a class="font_14" title="Click to View Complete Information" onclick='+clickFunction+' > '
						+ row.strProjectName + ' </a> <br/> <span class="bold blue font_14 text-left">'+ strReferenceNumber+'</span> </td>';*/
				tableData += '<tr><td>' + (i+1) + '</td><td class="font_14">' + row.groupName + '</td><td> <a class="font_14" title="Click to View Complete Information" onclick="' + clickFunction + '"> '
	             + row.strProjectName + ' </a> <br/> <span class="bold blue font_14 text-left">' + strReferenceNumber + '</span> </td>';
/*				tableData += '<tr> <td class="font_14">' + row.groupName + '</td> <td>  <a class="font_14" title="Click to View Complete Information" onclick='+clickFunction+' > '
				+ row.strProjectName + ' </a> <br/> <span class="bold blue font_14 text-left">'+ strReferenceNumber+'</span> </td>';*/
				
				tableData += ' <td>'+ row.milestoneName +'</td>';
				
				if(row.completionDate!=null){
				tableData += ' <td class=" text-center">'	+ row.completionDate + '</td>';
				}
				if(row.expectedStartDate!=null){
				tableData += ' <td class=" text-center">'	+ row.expectedStartDate + '</td>';
				}
				tableData += ' <td><div class="text-center"><a class="btn btn-primary"  onclick=viewProjectDetails("/PMS/mst/MilestoneReviewMaster/'+ row.encMilestsoneId + '")>Review</a></td> </tr>';			
			
			}		

			$('#milestoneTable  tbody').append(tableData); 
		          
			$('#milestoneTable').DataTable( {					
		        dom: 'Bfrtip', 
		        "paging":   false,
		        buttons: [
		             'excel', 'pdf', 'print'
		        ],
		        
		        /*"columnDefs": [
		                       { "visible": false, "targets": groupColumn }
		                   ],
		                   "order": [[ groupColumn, 'asc' ]],		                  
		                   "drawCallback": function ( settings ) {
		                       var api = this.api();
		                       var rows = api.rows( {page:'current'} ).nodes();
		                       var last=null;
		           
		                       api.column(groupColumn, {page:'current'} ).data().each( function ( group, i ) {
		                           if ( last !== group ) {
		                               $(rows).eq( i ).before(
		                                   '<tr class="group"><td colspan="1">'+group+'</td></tr>'
		                               );
		          
		                               last = group;
		                           }
		                       } );
		                       
		                   }*/
		    });
			
		   /* $('#datatable1 tbody').on( 'click', 'tr.group', function () {
		        var currentOrder = table.order()[0];
		        if ( currentOrder[0] === groupColumn && currentOrder[1] === 'asc' ) {
		            table.order( [ groupColumn, 'desc' ] ).draw();
		        }
		        else {
		            table.order( [ groupColumn, 'asc' ] ).draw();
		        }
		    } ); 
*/   
			/*var  table= $('#milestoneTable').DataTable( {
				 dom: 'Bfrtip',	
				 "paging":   false,
			        "ordering": false,
			        "info":     false,
			        buttons: [
					             'excel', 'pdf', 'print'
					        ],
			"columnDefs": [ {
		            "orderable": false,
	            "targets": 0
	        } ],
	        "order": [[ 1, 'asc' ]]
	    } );*/
			
		
			

			 // Setup - add a text input
	/*       $('#milestoneTable .filters th[class="textBox"]').each( function () {                 
	           $(this).html( '<div class="lighter"><input type="text"  style="width:100%" /></div>' );

	       } );*/
	       //var table = $('#milestoneTable').DataTable();
	    
			  
		}
				
	});

	
		

}
/*added the function to call the controller by varun on 06-07-2023*/
function MilestonesTable(startDate,endDate){	
	$('#milestoneTable').DataTable().clear().destroy();
	var groupColumn = 0;
	var tableData = '';
	$.ajaxRequest.ajax({
		type : "POST",
		url : "/PMS/mst/MilestoneExceededDeadlineDetailByDates",
		data : {"compDate":startDate,
			"symbol":endDate
			},
		success : function(response) {
		 console.log(response);
			for (var i = 0; i < response.length; i++) {
				var row = response[i];
			var strReferenceNumber=row.strProjectReference;
				if(!strReferenceNumber){
					strReferenceNumber='';
				}
				var clickFunction = "viewProjectDetails('/PMS/projectDetails/"+ row.encProjectId + "')";
			/*	tableData += '<tr><td>''</td><td> <a class="font_14" title="Click to View Complete Information" onclick='+clickFunction+'>'*/
				/*tableData += '<tr><td>'+(i+1)+'</td><td class="font_14">'+ row.groupName +  '</td><td> <a class="font_14" title="Click to View Complete Information" onclick='+clickFunction+' > '
						+ row.strProjectName + ' </a> <br/> <span class="bold blue font_14 text-left">'+ strReferenceNumber+'</span> </td>';*/
				tableData += '<tr><td>' + (i+1) + '</td><td class="font_14">' + row.groupName + '</td><td> <a class="font_14" title="Click to View Complete Information" onclick="' + clickFunction + '"> '
	             + row.strProjectName + ' </a> <br/> <span class="bold blue font_14 text-left">' + strReferenceNumber + '</span> </td>';
/*				tableData += '<tr> <td class="font_14">' + row.groupName + '</td> <td>  <a class="font_14" title="Click to View Complete Information" onclick='+clickFunction+' > '
				+ row.strProjectName + ' </a> <br/> <span class="bold blue font_14 text-left">'+ strReferenceNumber+'</span> </td>';*/
				
				tableData += ' <td>'+ row.milestoneName +'</td>';
				
				if(row.completionDate!=null){
				tableData += ' <td class=" text-center">'	+ row.completionDate + '</td>';
				}
				if(row.expectedStartDate!=null){
				tableData += ' <td class=" text-center">'	+ row.expectedStartDate + '</td>';
				}
				tableData += ' <td><div class="text-center"><a class="btn btn-primary"  onclick=viewProjectDetails("/PMS/mst/MilestoneReviewMaster/'+ row.encMilestsoneId + '")>Review</a></td> </tr>';			
			
			}		

			$('#milestoneTable  tbody').append(tableData); 
		          
			$('#milestoneTable').DataTable( {					
		        dom: 'Bfrtip', 
		        "paging":   false,
		        buttons: [
		             'excel', 'pdf', 'print'
		        ],
		        
		        /*"columnDefs": [
		                       { "visible": false, "targets": groupColumn }
		                   ],
		                   "order": [[ groupColumn, 'asc' ]],		                  
		                   "drawCallback": function ( settings ) {
		                       var api = this.api();
		                       var rows = api.rows( {page:'current'} ).nodes();
		                       var last=null;
		           
		                       api.column(groupColumn, {page:'current'} ).data().each( function ( group, i ) {
		                           if ( last !== group ) {
		                               $(rows).eq( i ).before(
		                                   '<tr class="group"><td colspan="1">'+group+'</td></tr>'
		                               );
		          
		                               last = group;
		                           }
		                       } );
		                       
		                   }*/
		    });
			
		   /* $('#datatable1 tbody').on( 'click', 'tr.group', function () {
		        var currentOrder = table.order()[0];
		        if ( currentOrder[0] === groupColumn && currentOrder[1] === 'asc' ) {
		            table.order( [ groupColumn, 'desc' ] ).draw();
		        }
		        else {
		            table.order( [ groupColumn, 'asc' ] ).draw();
		        }
		    } ); 
*/   
			/*var  table= $('#milestoneTable').DataTable( {
				 dom: 'Bfrtip',	
				 "paging":   false,
			        "ordering": false,
			        "info":     false,
			        buttons: [
					             'excel', 'pdf', 'print'
					        ],
			"columnDefs": [ {
		            "orderable": false,
	            "targets": 0
	        } ],
	        "order": [[ 1, 'asc' ]]
	    } );*/
			
		
			

			 // Setup - add a text input
	/*       $('#milestoneTable .filters th[class="textBox"]').each( function () {                 
	           $(this).html( '<div class="lighter"><input type="text"  style="width:100%" /></div>' );

	       } );*/
	       //var table = $('#milestoneTable').DataTable();
	    
			  
		}
				
	});

	
		

}
