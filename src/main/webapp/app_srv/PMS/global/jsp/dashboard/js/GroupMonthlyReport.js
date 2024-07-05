$("#strMonthAndYear").MonthPicker( {
		
	    Button: "<i class='pad-left-half font_eighteen fa fa-calendar'></i>",
	    changeMonth: true,
	    changeYear: true,
	    dateFormat: 'mm/yy',
	    MaxMonth: 0,
	   
	    OnAfterChooseMonth: function(){ 
	    	//$("#divCat").removeClass('hidden');	    	
	       var date = $(this).val();
	      
	        var res = date.split("/");
	        var month = res[0];
	        var year = res[1];
	        //alert(month);
	        //alert(year);
	        if (month > 0 && year > 0){
	        	
	        	var monthNames = [ "January", "February", "March", "April", "May", "June",
	                "July", "August", "September", "October", "November", "December" ];
					if(month == 0){
						var d = new Date();
						$('#displayMonth').text(monthNames[d.getMonth()-1]);
						$('#displayYear').text(", "+d.getFullYear());
					}else{
						$('#displayMonth').text(monthNames[month-1]);
						$('#displayYear').text(", "+year);
					}
					
					
						var groupId = $('#groupId').val();
						//alert(groupId);
		            	monthlyProgressReport(groupId,month,year);
		            
	        }
	    }
	    });

function monthlyProgressReport(groupId,month,year){
	//alert(groupId);
	var monthNames = [ "January", "February", "March", "April", "May", "June",
	                   "July", "August", "September", "October", "November", "December" ];
	if(month == 0){
		var d = new Date();
		$('#displayMonth').text(monthNames[d.getMonth()-1]);
		$('#displayYear').text(", "+d.getFullYear());
	}else{
		$('#displayMonth').text(monthNames[month-1]);
		$('#displayYear').text(", "+year);
	}
	//alert("here");
	$.ajaxRequest.ajax({

		type : "POST",
		url : "/PMS/getMonthlyProgressByGroup",
		data : {"groupId": groupId ,
				"month": month ,
				"year":year},		
		success : function(response) {
			if(response!=null || response!=''){
				
			  $('#monthlyParentId').val(response.encMonthlyProgressId);
			  getNextRoleActions(response.encMonthlyProgressId,response.encPageId,response.encWorkflowId);
			}
			else{
				alert(error);
			}
			
			}
	});
}

function monthlyReport(){
	//alert("here");
	var encParentId=$('#monthlyParentId').val();
	var encCategoryId='';
	var categoryId=0;
	var requestURL='';
	

	categoryId=$('#numCategoryId').val();
		//alert(categoryId);
	requestURL = $('#strCatgController_'+categoryId).val();

	
	if(encParentId==''||encParentId==0){
		swal("Please Select Month of Group Report");
	}
	
	else if(categoryId==0){
		swal("Please Select Category");
	}
	else if(requestURL){
		
		openWindowWithPost('GET','/PMS/'+requestURL,{"encMonthlyProgressId":encParentId,"encCategoryId":categoryId},'_self');
	}else{
		//alert(requestURL);
		console.log("ÚRL Not mapped");
	}		
	

}

function previewOfReport(){
	
	if($("#strMonthAndYear").val()==''){
		sweetSuccess('Attention','Group Report For Month is mandatory!');
		return false;
	}
	var encParentId=$('#monthlyParentId').val();
	
	openWindowWithPost('POST','/PMS/getGroupPreviewDetails',{"encMonthlyProgressId":encParentId},'_blank');

	}

$(document).ready(function(){
	$('.select2Option').select2({
		 width: '100%'
	});
});
