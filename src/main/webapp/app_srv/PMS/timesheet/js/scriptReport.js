$(document).ready(function () {
	        $('.select2').select2();
	        let isGroupFormSubmitRe = document.getElementById("isGroupFormSubmitRe").value;
	        if(isGroupFormSubmitRe == "true"){
	        	document.getElementById("groupWise").click();
	        }  
	    });


// R1 - Finding Employee details under GC
function submitEmployeeSelectForm() {    
	let selectedEmpIdName = document.getElementById("selectedEmpIdName").value;
    let selectedYear = document.getElementById("selectedYear").value;

    // Check if an employee is selected
    if (selectedEmpIdName.trim() === "") {
        swal("Please select an employee.");
        return false; // Prevent form submission
    }

    // If an employee is selected, proceed with form submission
    document.getElementById("selectEmployee").submit();
}
function submitSelectGroupYearForm(){    
	document.getElementById("selectGroupYear").submit();
    
}
function submitYearIndividualEmployee(){    
    document.getElementById("selectYearIndividualEmployee").submit();
}
//R0 - Radio button for user wise & group wise
function showUserWise() {
    document.getElementById("userWiseUI").style.display = "block";
    document.getElementById("groupWiseUI").style.display = "none";
}

function showGroupWise() {
    document.getElementById("userWiseUI").style.display = "none";
    document.getElementById("groupWiseUI").style.display = "block";
}


