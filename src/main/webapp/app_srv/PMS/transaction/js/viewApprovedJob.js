$(document).ready(function() {
    $('.select2Option').select2({
    	 width: '100%'
    });
    
    
    $('.dateDiv').hide();
    
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
 	        
 	    }
   });
    
    $('#endDate').datepicker({
 	   dateFormat: 'dd/mm/yy', 
 	   changeMonth: true,
 	   changeYear:true ,
 	  maxDate:currentDate
    });
    
    
});

$(document).ready(function() {

	$("input[name='jobStatus']").change(function(){
		$('#dataSection').addClass('hidden');
		var selectedValue = $("input[name='jobStatus']:checked"). val();
		if(selectedValue == 'ALL' || selectedValue == 'CLS' || selectedValue == 'ASS' || selectedValue == 'EXP' ){
			$('.dateDiv').show();
		}else{
			$('.dateDiv').hide();
		}
	});
	
	$('#submit').click(function(){
		var selectedValue = $("input[name='jobStatus']:checked"). val();
		if(!selectedValue){
			sweetSuccess('Attention','Please select Job Status');
			return false;
		}	
		
		var groupId = $('#groupId').val();
		
		if(groupId.length == 0){
			$("#groupId option").each(function(){
				groupId.push($(this).val());
			});
		}
		
		var selectedGroups = groupId.toString();
		
		var startDate = $('#startDate').val();
		var endDate = $('#endDate').val();
		
		if(!startDate){
			startDate ='';
		}
		
		if(!endDate){
			endDate= '';
		}
		
		$('#jobDetailBody').empty();
		$('#jobDetailHead').empty();
		
		$.ajaxRequest.ajax({
		    type: "POST",
		    url: "/PMS/approvedJobs",
		    data: {"startDate":startDate,
		    		"endDate":endDate,
		    		"selectedGroups":selectedGroups,
		    		"jobStatus":selectedValue
		    		},
		    async:false,
		    success: function(response) {
		    	
		    	if(response.length == 2){
		    		var noOfCol = 0;		    		
		    		
		    		var tableHeader = response[0];
		    		var tableBody = response[1];
		    		
		    		if(tableHeader.length >0){		    		
		    		var tempHeader = '<tr><th width="25%" class="text-center">Group Name</th>';
		    			for(var x= 0 ;x<tableHeader.length;x++){
		    				tempHeader+='<th class="text-center">'+tableHeader[x]+'</th>';
		    			}
		    			tempHeader+='<th class="text-center"> Total </th></tr>';
		    			$('#jobDetailHead').append(tempHeader);
		    		
		    		$('#dataSection').removeClass('hidden');
		    		var tableData = '';
		    		$.each(tableBody, function(key, value) {
		    			tableData +='<tr>';		    			
		    			
		    				tableData += '<td class="bold" width="25%">'+key+ '</td>';
		    				var rowTotal =0;
		    				 for(var j = 0; j< value.length;j++){
		    					 if(j == 0){
		    						 noOfCol = value.length; 
		    					 }		    					 
		    					 var mapping = value[j];
		    					 if(!mapping.count){
		    						 tableData += '<td class="text-right">0</td>';
		    					 }else{
		    						 rowTotal+=parseInt(mapping.count);
		    						 tableData += '<td class="text-right totalTd_'+j+'"> <a onclick=getMemberDetails("'+mapping.encDesignationId+'","'+mapping.encGroupId+'")>'+mapping.count+'</a></td>';
		    					 }
		    					
		    				 }
		    				 tableData +='<td class="bold text-right colTotal">'+rowTotal+'</td>';
		    				 tableData +='</tr>'; 
		    			});	    			
		    		
		    			var totalRow ='<tr><td class="bold text-right"> Total </td>';
		    			
		    				for(var z=0;z<noOfCol;z++){		    					
		    					totalRow +='<td class="bold text-right" id="totalTd_'+z+'"> 0 </td>';
		    				}
		    				
		    				totalRow +='<td class="bold text-right" id="colTotal"></td> </tr>';
		    				tableData +=totalRow;
		    			$('#jobDetailBody').append(tableData);
		    			sumCol(noOfCol);
		    		}else{
		    			$('#dataSection').addClass('hidden');
		    			sweetSuccess('Attention','No Record Found');
		    			return false;
		    		}
		    	}else{
		    		$('#dataSection').addClass('hidden');
		    	}
		    }
		});
	
		
		
	});
});

function getMemberDetails(encDesignationId, encGroupId){
	console.log(encDesignationId);
	console.log(encGroupId);
}


function sumCol(colCount){	
	for(var i =0;i<colCount;i++){
		var className= '.totalTd_'+i;
		var total =0;
		$(className).each(function() {
			total+= parseInt($( this ).text());
		  });
		
		$('#totalTd_'+i).text(total);
	}
	
	var total =0;
	$('.colTotal').each(function() {
		total+= parseInt($( this ).text());
	  });
	
	$('#colTotal').text(total);
}