$('.select2Option').select2({
width: '70%'
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


var groupIds=$("#selectedGroup").val().toString();
var selectYear=$("#selectYear").val();

	if(groupIds==''||groupIds==null){
		swal("Please Select Group to Generate Report!", "", "warning");
		return false;
		}
	else if(selectYear==''){
		swal("Please Select Employee Contract to Generate Report!", "", "warning");
		return false;
	}
	else{
		openWindowWithPost('POST', '/PMS/generateReportForEmplDetails', {"groupIds":groupIds,"selectYear":selectYear,"eCode":"eEmpDetails"}, '_blank');  
	}
	

}




function resetData(){

$('#selectedGroup').val('');
$("#selectedGroup").trigger('change');

// Uncheck
//$("#checkbox").attr("AllGroup", false);
document.getElementById("AllGroup").checked = false;

}

