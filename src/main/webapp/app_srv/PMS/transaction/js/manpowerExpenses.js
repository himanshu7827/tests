$(document).ready(function() {
		
		  $('.select2Option').select2({
		    	 width: '100%'
		    });
	});



	

		
		$(document).ready(function() {
			$('#example').DataTable();
			
			$(".btn-pref .btn").click(function () {
			    $(".btn-pref .btn").removeClass("btn-primary").addClass("btn-default");			    
			    $(this).removeClass("btn-default").addClass("btn-primary");   
			});
			});
		
		$('.cont').click(function(){

			  var nextId = $(this).parents('.tab-pane').next().attr("id");
			  $('[href=#'+nextId+']').tab('show');

			})
			
		
		

	
	function downloadManpowerExpensesExcel(){
		var encProjectId = $('#encProjectId').val();
		var fromDate = $('#fromDate').val();
		var toDate = $('#toDate').val();
		
		openWindowWithPost('POST', '/PMS/downloadManpowerExpensesExcel', {"encProjectId":encProjectId,"startDate":fromDate,"endDate":toDate}, '_blank');
		
		/*$.ajaxRequest.ajax({

			type : "POST",
			url : "/PMS/downloadManpowerExpensesExcel",
			data : {"encProjectId": encProjectId , 
					"startDate":fromDate,
					"endDate":toDate},			
			success : function(response) {
				if(response){
					openWindowWithPost('POST', '/PMS/downloadManpowerExpensesExcel', {"encProjectId":encProjectId,"startDate":fromDate,"endDate":toDate}, '_blank');
				}else{
					swal("File download Failed.!");
				}				
			}
		});*/
	}
	
	function initiateMonthPicker() {
		//$("#fromDate").datepicker("destroy");
        //$("#toDate").datepicker("destroy");	
		//var start=$('#startdate').val();
		//var startParts=start.split("/");
		//var startProj=new Date(+startParts[2], startParts[1] - 1, +startParts[0]);
		//var projectEnd = $('#enddate').val();
		//var endDateParts = projectEnd.split("/");
		//var projectEndDate = new Date(+endDateParts[2], endDateParts[1] - 1, +endDateParts[0]);
		
		$("#fromDate").datepicker({
		    dateFormat:'dd/mm/yy',
			changeMonth: true,
			changeYear: true,
			//minDate: startProj,
			//maxDate: projectEndDate,
			minDate: '-10y',
			maxDate: '+10y',
	        onSelect: function(selectedDate) {
	            $("#toDate").datepicker("option", "minDate", selectedDate);
	            if($("#toDate").val()!="") $('#downloadbutton').removeClass('hidden');
	        }
        });
        $("#toDate").datepicker({
            dateFormat:'dd/mm/yy',
			changeMonth: true,
			changeYear: true,
			//minDate: startProj,
			//maxDate: projectEndDate,
			minDate: '-10y',
			maxDate: '+10y',
            onSelect: function(selectedDate) {
                $("#fromDate").datepicker("option", "maxDate", selectedDate);
                if($("#fromDate").val()!="") $('#downloadbutton').removeClass('hidden');
            }
        });
	};
	
	function monthlyReport(){
	
		var encParentId=$('#monthlyParentId').val();
		var encCategoryId='';
		var categoryId=0;
		var requestURL='';
		var subcat=$('#numSubCategoryId').val();
		if(subcat!=0){		
			categoryId=$('#numSubCategoryId').val();
			 requestURL = $('#strCatgController_'+categoryId).val();			
		}
		else{
			 categoryId=$('#numCategoryId').val();
			//alert(categoryId);
		requestURL = $('#strCatgController_'+categoryId).val();
		//alert(requestURL);
		}
		
		
		if(encParentId==''||encParentId==0){
			swal("Please Select Month of Progress Report");
		}
		else if($('#isSubCatPresent').val()==1 && subcat==0){
			swal("Please Select Sub-Category");
		}
		else if(categoryId==0&&subcat==0){
			swal("Please Select Category");
		}
		else if(requestURL){
			//alert(categoryId);
			openWindowWithPost('GET','/PMS/'+requestURL,{"encMonthlyProgressId":encParentId,"encCategoryId":categoryId},'_self');
		}else{
			//alert(requestURL);
			console.log("ÚRL Not mapped");
		}		
	}
	
	$(document).on('change','#encProjectId',function(e){
		console.log($(this).val());
		var encProjectId = $('#encProjectId').val();
		if(encProjectId == 0){
			$('#fromDate').val("");
			$('#toDate').val("");
			$('#downloadbutton').addClass('hidden');
			$('#monthpickerDiv').addClass('hidden');
		}else{

			/*$.ajaxRequest.ajax({

				type : "POST",
				url : "/PMS/getProjectStartAndEndDate",
				data : {"encProjectId": encProjectId},
				async:false,
				success : function(response) {
					if((response[0]!=null || response[0]!='') && (response[1]!=null || response[1]!='')){
						$('#startdate').val(response[0]);
						$('#enddate').val(response[1]);
						$('#monthpickerDiv').removeClass('hidden');
						initiateMonthPicker();
					}
					else{
						alert(error);
					}
					
				}
			});*/
			$('#monthpickerDiv').removeClass('hidden');
			initiateMonthPicker();
			
		}
		
	});
	
function checkGroups(){
	if ($('input.group_check').is(':checked')) {
		$('#encProjectId option').prop('selected', true);
		$("#encProjectId").trigger('change');
	}
	else{
		$("#encProjectId").val('');
		$("#encProjectId").trigger('change');
	}
}