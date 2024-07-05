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
			
		
		

	
	function monthlyProgressReport(encProjectId,month,year){
		
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
		
		$.ajaxRequest.ajax({

			type : "POST",
			url : "/PMS/getMonthlyProgress",
			data : {"encProjectId": encProjectId , 
					"month":month ,
					"year" :year},			
			success : function(response) {
				if(response!=null || response!=''){
					
					$('#monthlyParentId').val(response.encMonthlyProgressId);
					if(response.categoryModel){
						$('#numCategoryId').find('option').not(':first').remove();
						$('.hiddenControls').remove();
						var options = '';
						var tempInputs = '';
						for(var j=0;j<response.categoryModel.length;j++){
							var temp = response.categoryModel[j];
							options +='<option value="'+temp.encCategoryId+'">'+temp.strCategoryName+'</option>';
							
							tempInputs +='<input type="hidden" class="hiddenControls" id="strCatgController_'+temp.encCategoryId+'" value="'+temp.strCategoryController+'"/>'
							
						}
						$('#numCategoryId').append(options).select2("destroy").select2({
					    	 width: '100%'
					    });
						
						$('#monthlyProress').append(tempInputs);						
					}
					getNextRoleActions(response.encMonthlyProgressId,response.encPageId,response.encWorkflowId);
				}
				else{
					alert(error);
				}
				
				}
		});
	}
	
	$(document).on('change','#numCategoryId',function(e){	
		
		$("#hrefDiv").removeClass('hidden');
		});
	function initiateMonthPicker() {
			
		var arr =$("#startdate").val().split("/");
		console.log(arr);
		var months = [ "January", "February", "March", "April", "May", "June",
		    "July", "August", "September", "October", "November", "December" ];
		var month_index =  parseInt(arr[1],10) - 1;
		console.log(month_index);
		var startmonth=month_index+1;
		var startYearFrom=arr[2];
		
		var todaymonth =new Date().getMonth();
		todaymonth=todaymonth+1;
		var todayYear=new Date().getFullYear();
		
		var totalmonths=(todayYear-startYearFrom)*12;
		
		if(todaymonth>startmonth){
			var diff=todaymonth-startmonth;
			totalmonths=totalmonths+diff;
			$("#startdate").val(totalmonths);
		} else if(todaymonth<startmonth){
			var diff=startmonth-todaymonth;
			totalmonths=totalmonths-diff;
			$("#startdate").val(totalmonths);
		}else{
			$("#startdate").val(totalmonths);
		}
		
	$("#strMonthAndYear").MonthPicker( {
		
	    Button: "<i class='pad-left-half font_eighteen fa fa-calendar'></i>",
	    changeMonth: true,
	    changeYear: true,
	    dateFormat: 'mm/yy',
	    MaxMonth: 0,
	    MinMonth: -$("#startdate").val(),
	    OnAfterChooseMonth: function(){ 
	    	$("#divCat").removeClass('hidden');	    	
	       var date = $(this).val();
	      
	        var res = date.split("/");
	        var month = res[0];
	        var year = res[1];
	       
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
					
					
		            	var encProjectId = $('#encProjectId').val();
		            	monthlyProgressReport(encProjectId,month,year);
		            
	        }
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
	
	$(document).on('change','#numCategoryId',function(e){	
		
		categoryId = $('#numCategoryId').val();		
			$.ajaxRequest.ajax({
		        type: "POST",
		        url: "/PMS/getChildCategoryByParentId",
		        async:false,
		        data: {"encCategoryId":categoryId,
		        	"parentId":$('#monthlyParentId').val()
		        },			
				success : function(data) {
					//alert(data);
					if(data!=''){
					//alert("sub category present");
					var options= '<option value="0"> -- Select Sub Category-- </option>';
					var value='<span></span>';
					if(categoryId!=0)
						{
							jQuery(data).each(function(j, item){
								
								value+='<input type="hidden" id=strCatgController_'+item.encCategoryId+' value="'+item.strCategoryController+'">';
								options+='<option value="'+item.encCategoryId+'">'+item.strCategoryName+'</option>';
							});
						}
					$("#subCatDiv").removeClass('hidden');	
					$('#numSubCategoryId').empty();
					$('#numSubCategoryId').append(options);
					$('#isSubCatPresent').val(1);	
					$('#hiddenFields').html(value);
					}
					else{
						$('#numSubCategoryId').val(0);
						$('#isSubCatPresent').val(0);	
						
						$("#subCatDiv").addClass('hidden');	
					}}
				});
	});
	
	function previewOfReport(){
		
		if($("#strMonthAndYear").val()==''){
			sweetSuccess('Attention','Progress Report For Month is mandatory!');
			return false;
		}
		var encParentId=$('#monthlyParentId').val();
		openWindowWithPost('POST','/PMS/getPreviewDetails',{"encMonthlyProgressId":encParentId},'_blank');
		
		}
	$(document).on('change','#encProjectId',function(e){
		var encProjectId = $('#encProjectId').val();
		/*----- Change the Project Name Default the value of category and monthYear [ 21-05-2024 ] ----*/
		$('#numSubCategoryId').val(0);
		$("#strMonthAndYear").val('');
		if(encProjectId == 0){
			$('#monthpickerDiv').addClass('hidden');
		}else{

			$.ajaxRequest.ajax({

				type : "POST",
				url : "/PMS/mst/getProjectStartDate",
				data : {"encProjectId": encProjectId},
				async:false,
				success : function(response) {
					if(response!=null || response!=''){
						$('#startdate').val(response);
						$('#monthpickerDiv').removeClass('hidden');
						initiateMonthPicker();
					}
					else{
						alert(error);
					}
					
					}
			});
			
		}
		
	});