$('.select2Option').select2({
width: '70%'
});
$(document).ready(function() {

$('#projectDiv').hide();


});
$("#strMonthAndYear").MonthPicker( {

    Button: "<i class='pad-left-half font_eighteen fa fa-calendar'></i>",
    changeMonth: true,
    changeYear: true,
    dateFormat: 'mm/yy',
   /* MaxMonth: 0,*/
    OnAfterChooseMonth: function(){
    $("#divCat").removeClass('hidden');    
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


            //var encProjectId = $('#encProjectId').val();
            //monthlyProgressReport(encProjectId,month,year);
$("#month").val(month);
$("#year").val(year);
           
        }
    }
    });


function generateReport(){

var month=$("#month").val();
var year=$("#year").val();

if(month=='' || month==null){
	swal("Please Select Month to Generate Report!", "", "warning");
	return false;
}


openWindowWithPost('POST', '/PMS/generateReportForBirthDayDetails', {"month":month,"year":year,"eCode":"eBirth"}, '_blank');  
			

}


function resetData(){
$('#strMonthAndYear').val('');
$('#month').val('');
$('#year').val('');

}

