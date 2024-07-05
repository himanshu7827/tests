$(document).ready(function(){
	
	$('#proposalList_dataTable').DataTable({
		
		 "bLengthChange": false
	 });

	
});
function loadGroupWiseProposals(encGroupId){
	 table = $('#proposalList_dataTable').DataTable().clear().destroy();
		var tableData = '';
	$.ajaxRequest.ajax({
		type : "POST",
		url : "/PMS/getProposalDetailByGruopId",
		data : "encGroupId=" + encGroupId,
		success : function(response) {
			//console.log(response);
			for (var i = 0; i < response.length; i++) {
				var row = response[i];
				//console.log(row);
				var sno=i+1;
				tableData +='<tr ><td style="width:2%" >'+sno+'</td>';
				tableData += '<td class="font_14" style="width:10%"> '+ row.groupName + ' </a> </td>';
				tableData += ' <td class="font_14 ">'+ row.proposalTitle+  '</td> </tr>';
			
		}
			$('#proposalList_dataTable > tbody').html('');
			$('#proposalList_dataTable').append(tableData);

			 table= $('#proposalList_dataTable').DataTable({
				 searching: false,
				 "bLengthChange": false
			 });
			 
			
			 $('#proposalList_dataTable .filters th[class="comboBox"]').each( function ( i ) {
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
			                                $('#proposalList_dataTable .filters th[class="textBox"]').each( function () {                 
			                                    $(this).html( '<div class="lighter"><input type="text"  style="width:100%" /></div>' );
			             
			              } );
			                             
			                                // DataTable
			                                var table = $('#proposalList_dataTable').DataTable();
			            
			             // Apply the search
			                                 table.columns().eq( 0 ).each( function ( colIdx ) {
			                            $( 'input', $('.filters th')[colIdx] ).on( 'keyup change', function () {
			                                table
			                                            .column( colIdx )
			                                            .search( this.value )
			                                            .draw() ;
			                                    
			                                                            } );
			      } );
			                              
			}
		
		});
}