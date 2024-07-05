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
    MaxMonth: 0,
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

function checkGroups(){
if ($('input.group_check').is(':checked')) {
 $('#selectedGroup option').prop('selected', true);
$("#selectedGroup").trigger('change');
}
else{
$("#selectedGroup").val('');
$("#selectedGroup").trigger('change');
$('#projectDiv').hide();
}
}

function generateReport(){

var projectIds=$("#selectedProject").val().toString();
var groupIds=$("#selectedGroup").val().toString();
var selectedProjectType=$("#selectedProjects").val();
var month=$("#month").val();
var year=$("#year").val();
//alert("projectIds");
if(month=='' || month==null){
	swal("Please Select Month to Generate Report!", "", "warning");
}
else if(year=='' || year==null){
	swal("Please Select Year to Generate Report!", "", "warning");
}
else if( $('#ReportType').val() == 'projectWise' ){
			if(groupIds==''||groupIds==null){
			swal("Please Select Group to Generate Report!", "", "warning");
			}
			else if(projectIds==''||projectIds==null){
			swal("Please Select Project to Generate Report!", "", "warning");
			}
			else{
				openWindowWithPost('POST', '/PMS/generateReportForInterface', {"month":month,"year":year,"groupIds":groupIds,"projectIds":projectIds,"eCode":"eMonProg","selectedProjectType":selectedProjectType}, '_blank');  
			}
}
else if( $('#ReportType').val() == 'groupWise' ){
	if(groupIds==''||groupIds==null){
		swal("Please Select Group to Generate Report!", "", "warning");
		}
	else{
		openWindowWithPost('POST', '/PMS/generateReportForInterface', {"month":month,"year":year,"groupIds":groupIds,"projectIds":projectIds,"eCode":"eMonthProgGroup","selectedProjectType":selectedProjectType}, '_blank');  
	}
}
else if( $('#ReportType').val() == 'internalReport' )
{
	//alert("HI");
	if(groupIds==''||groupIds==null){
		swal("Please Select Group to Generate Report!", "", "warning");
		}
	else{
		 openWindowWithPost('POST', '/PMS/generateReportForInterface', {"month":month,"year":year,"groupIds":groupIds,"projectIds":projectIds,"eCode":"eMonProgInternal","selectedProjectType":selectedProjectType}, '_blank');  
		}
	
}
else{
	swal("Please Select Report Type to Generate Report!", "", "warning");
	}
}

function getProjects(){

var month=$("#month").val();
var year=$("#year").val();
var groupIds=$("#selectedGroup").val();
var selectedProjectType=$("#selectedProjects").val();
/*alert(month);
alert(year);
alert(groupIds);
alert(selectedProjectType);*/
if(year==''||year==0||month==0||month==''){
swal("Please select month & year")
}

if(year!=""&&groupIds!=""&& ($('#ReportType').val() == 'projectWise')){
//alert("in ajax"+groupIds);
$.ajaxRequest.ajax({
       type: "POST",
       url: "/PMS/getProjectsByGroup",
       async:false,
       data:"month="+month+"&year="+year+"&groupIds="+groupIds+"&selectedProjectType="+selectedProjectType,
success : function(data) {
//alert("the response"+data);
if(data!=null&&data!=''){
var options= '';

jQuery(data).each(function(j, item){

options+='<option value="'+item.numId+'">'+item.strProjectName+'</option>';
});
$('#selectedProject').empty();
$('#selectedProject').append(options);
$('#projectDiv').show();

}
else{
swal("No project found for this group!", "", "warning");
document.getElementById("projectWise").checked = false;
//swal("No Project Found for Report");
$('#projectDiv').hide();
}

}
});
}
else{
$('#projectDiv').hide();
}
}
function checkProjects(){
if ($('input.project_check').is(':checked')) {
 $('#selectedProject option').prop('selected', true);
$("#selectedProject").trigger('change');
}
else{
$("#selectedProject").val('');
$("#selectedProject").trigger('change');
}
}

function resetData(){
$('#strMonthAndYear').val('');
$('#month').val('');
$('#year').val('');
$('#selectedGroup').val('');
$("#selectedGroup").trigger('change');
$('#selectedProjects').val('2').trigger('change');
$('#projectDiv').hide();
$('#ReportType').val('3').trigger('change');
document.getElementById("AllGroup").checked = false;
// Uncheck
//$("#checkbox").attr("AllGroup", false);
/*document.getElementById("AllGroup").checked = false;
document.getElementById("Allproject").checked = false;
document.getElementById("projectWise").checked = false;
document.getElementById("groupWise").checked = false;
document.getElementById("internalReport").checked = false;*/
}
$(document).ready(function(){
function groupWise(){
	/*if ($('input.group_wise').is(':checked')){
	document.getElementById("projectWise").checked = false;
	document.getElementById("internalReport").checked = false;*/
	$('#projectDiv').hide();
	$('#selectedProject').val('');
	$("#selectedProject").trigger('change');
	/*}*/
	
}
function projectWise(){
	if ( $('#ReportType').val() == 'projectWise'){
		/*document.getElementById("groupWise").checked = false;
		document.getElementById("internalReport").checked = false;*/
		getProjects();
		}
	else{
		$('#projectDiv').hide();
		$('#selectedProject').val('');
		$("#selectedProject").trigger('change');
	}
	
}
function internalReport(){
	/*if ($('input.internal_report').is(':checked')){
	document.getElementById("groupWise").checked = false;
	document.getElementById("projectWise").checked = false;*/
	$('#projectDiv').hide();
	$('#selectedProject').val('');
	$("#selectedProject").trigger('change');
	/*}*/	
}

$('#ReportType').on('change', function() {
    
      if ( $('#ReportType').val() == 'groupWise' ) groupWise();
      else if ( $('#ReportType').val() == 'projectWise' ) projectWise();
      else if ( $('#ReportType').val() == 'internalReport' ) internalReport();
    });	
});