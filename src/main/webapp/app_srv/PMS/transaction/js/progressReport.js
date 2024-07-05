var categoryId="";
$('.select2Option').select2({
	 width: '100%'
});
/*$("#strMonthAndYear").datepicker({
    dateFormat: 'MM yy',
    changeMonth: true,
    changeYear: true,
    showButtonPanel: true,

    onClose: function(dateText, inst) {
        var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
        var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
        $(this).val($.datepicker.formatDate('MM yy', new Date(year, month, 1)));
    }
});

$(".strMonthAndYear").focus(function () {
    $(".ui-datepicker-calendar").hide();
    $("#ui-datepicker-div").position({
        my: "center top",
        at: "center bottom",
        of: $(this)
    });
});*/
$("#strMonthAndYear").MonthPicker( {
	
    Button: "<i class='pad-left-half font_eighteen fa fa-calendar'></i>",
    changeMonth: true,
    changeYear: true,
    dateFormat: 'mm/yy',
    MaxMonth: -1,
    OnAfterChooseMonth: function(){ 
     
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
        }
    }
    });

$(document).on('change','#numCategoryId',function(e){	
	
categoryId = $('#numCategoryId').val();
$("#hrefDiv").addClass('hidden');	
$("#iframeDiv").addClass('hidden');	
	$.ajaxRequest.ajax({
        type: "POST",
        url: "/PMS/getSubCategory",
        async:false,
        data: "numCategoryId="+categoryId ,			
		success : function(data) {
			
			
			
			var options= '<option value="0"> -- Select Sub Category-- </option>';
			var value='<span></span>';
			if(categoryId!=0)
				{
					jQuery(data).each(function(j, item){
						
						value+='<input type="hidden" id=strCatgController_'+item.numCategoryId+' value="'+item.strCategoryController+'">';
						options+='<option value="'+item.numCategoryId+'">'+item.strCategoryName+'</option>';
					});
				}
			
			$('#numSubCategoryId').empty();
			$('#numSubCategoryId').append(options);
			if(data.length>0 && categoryId!=0)
			{
				$('#subCatDiv').removeClass('hidden');
				  $("#hrefDiv").addClass('hidden');
				  
				  $('#hiddenFields').html(value);
			    
			}
			else if(data.length==0 && categoryId!=0)
				{
				
				  $('#subCatDiv').addClass('hidden');
				  $("#hrefDiv").removeClass('hidden');
				  
				}
			else
				{
				  $('#subCatDiv').addClass('hidden');
				  $("#hrefDiv").addClass('hidden');
				 
				}
		}
			
		});				
	if($('#isProjectBasedCat_'+categoryId).val()!=null && $('#isProjectBasedCat_'+categoryId).val()!='' && $('#isProjectBasedCat_'+categoryId).val()=='P')
	{
		$('#projectMainDiv').removeClass('hidden');
	}
	else
		{
		  $('#projectMainDiv').addClass('hidden');
		}


});


$(document).on('change','#numSubCategoryId',function(e){
	categoryId=$('#numSubCategoryId').val();
	$("#iframeDiv").addClass('hidden');
	$("#hrefDiv").removeClass('hidden');
});

function openIframe() {
	
		window.location.href="/PMS/"+$('#strCatgController_'+categoryId).val();
	/*	$("#iframeId").attr("src","/PMS/"+$('#strCatgController_'+categoryId).val()); 
		$("#iframeDiv").removeClass('hidden');*/
		
	}



