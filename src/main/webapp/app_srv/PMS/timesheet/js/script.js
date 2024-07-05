function selectDate(date) {
    document.getElementById('selectedDate').value = date;
  }
function selectDateNumber(number) {
	// Set the selected number in the hidden input
	document.getElementById('selectedDateNumber').value = number;
	
	// Submit the form
	document.querySelector('.calendarForm').submit();
	}

  document.getElementsByClassName("monthList")[0].addEventListener("change", function() {
      document.getElementsByClassName("calendarForm")[0].submit();
  });

  document.getElementsByClassName("yearList")[0].addEventListener("change", function() {
      document.getElementsByClassName("calendarForm")[0].submit();
  });
  
 
  $(document).ready(function() {
      $('.validateWeekEffort').mask('99:99');
      $('.validateWeekEffortPopUp').mask('99:99');
  });
   
  //auto complete of inputs
  $('.validateWeekEffort').on('blur', function(){
	let value = $(this).val();
	//console.log(value);
    let time = value.split(':');
    let plainTime = time[0] + time[1];
    let actualTime = plainTime.replace(/_/g, '');
    //console.log(actualTime)
    if(actualTime.length == 1){
		let newValue = '0' + actualTime + ':00';
		$(this).val(newValue);
	}
	else if(actualTime.length == 2){
		let newValue = actualTime + ':00';
		$(this).val(newValue);
	}
    
  });
  $('.validateWeekEffortPopUp').on('blur', function(){
	let value = $(this).val();
	//console.log(value);
    let time = value.split(':');
    let plainTime = time[0] + time[1];
    let actualTime = plainTime.replace(/_/g, '');
    //console.log(actualTime)
    if(actualTime.length == 1){
		let newValue = '0' + actualTime + ':00';
		$(this).val(newValue);
	}
	else if(actualTime.length == 2){
		let newValue = actualTime + ':00';
		$(this).val(newValue);
	}
    
  });

 /* //automatic filling of input
  document.addEventListener("DOMContentLoaded", function () {
      // Get the table element
      let table = document.getElementById("timesheetTable");
      
      // Iterate through each row
      for (let i = 0, row; row = table.rows[i]; i++) {
          // Iterate through each column starting from the second column
          for (let j = 1, col; col = row.cells[j]; j++) {
              // Get the current and previous input fields
              let currentInput = col.querySelector("input:not([type='hidden'])");
              let previousInput = row.cells[j - 1].querySelector("input");
              // If the current input is empty and there is a previous input, copy the value
              if (currentInput && !currentInput.value && previousInput && previousInput.value) {
                  currentInput.value = previousInput.value;
              }
          }
      }
  });*/
  //making project code bold
  document.addEventListener("DOMContentLoaded", function() {
	    let projectNamesElements = document.querySelectorAll('.projectNamesUI span');
	    projectNamesElements.forEach(function(element) {
	        let projectName = element.innerText;
	        let delimiterIndex = projectName.indexOf(' - ');
	        if (delimiterIndex !== -1) {
	            let firstPart = projectName.substring(0, delimiterIndex);
	            let secondPart = projectName.substring(delimiterIndex + 3);
	            element.innerHTML = '<strong>' + firstPart + '</strong> - ' + secondPart;
	        }
	    });
	});

  //Validation for each and every input field
  function checkWeekEffort(input) {
      let inputVal = $(input).val();
      if(inputVal.length == 0){
    	  $(input).val('00:00');
      }
      let time = inputVal.split(':').map(Number);
      let hours = time[0];
      let minutes = time[1];

      if (hours > 167 || minutes > 59 || hours < 0 || minutes < 0) {
          swal('Invalid input minutes must be between 0 and 59.');
          $(input).val(''); // Clear the input value
          $(input).blur();
      } else {
    	  isFormChanged = true;
          // Valid input, you can proceed with further processing
          //console.log('Valid input:', hours, 'hours', minutes, 'minutes');
      }
  }
  
  //Validation for only Miscellaneous Activity input
  //Get all <tr> elements
  let rows = document.querySelectorAll('tr');
  let previousWeek = document.getElementById("previousWeekNumberPopUp").value
  let activeWeek = document.getElementById("activeWeekNumberPopUp").value
  // Loop through each row
  rows.forEach(function(row) {
      // Check if the row contains "Miscellaneous" in its text content
      if (row.textContent.includes("Miscellaneous")) {
          // Get all <input> elements within this row that are not hidden
          let inputs = row.querySelectorAll('input[type="text"]:not([type="hidden"])');

          // Loop through each input element
          inputs.forEach(function(input, index) {
              // Do something with each input element
              input.setAttribute("onChange", "checkWeekEffort(this);checkWeekEffortMisc(this,"+index+")")
          });
      }
  });
  function checkWeekEffortMisc(input,index) {
      let inputVal = $(input).val();
      
      let time = inputVal.split(':').map(Number);
      let hours = time[0];
      let minutes = time[1];

      if (hours > 17 ) {
    	  if(index == "0"){ 
    		  swal('Effort in Miscellaneous Activity can\'t be greater than 10% of total Effort for Week '+previousWeek);
    	  }else if(index == "1"){
    		  swal('Effort in Miscellaneous Activity can\'t be greater than 10% of total Effort for Week '+activeWeek);
    	  }
          
          $(input).val(''); // Clear the input value
          $(input).blur();
      } else {
    	  isFormChanged = true;
          // Valid input, you can proceed with further processing
          //console.log('Valid input:', hours, 'hours', minutes, 'minutes');
      }
  }
  

  let isFormChanged = false;
  $('input.validateWeekEffort').on('change', function() {
	  isFormChanged = true;
  });
  //check weather the data is updated or entered for first time in timesheet
let allRowsInTimesheet = document.querySelectorAll('tr.misc-activity-rows, tr.project-rows');
let wasTimesheetEmpty = true;
for(let i = 0; i<allRowsInTimesheet.length; i++){
	let inputFields = allRowsInTimesheet[i].querySelectorAll('input[type="text"]');
	if((inputFields[0] !==undefined && inputFields[0].value.trim() !== '') || (inputFields[1] !==undefined && inputFields[1].value.trim() !== '')){
		wasTimesheetEmpty = false;
		break;
	}
}
	
	//what will happen if submit is clicked
  document.getElementById("timesheetForm").addEventListener("submit", function (event) {
	if(isFormChanged === false){
		swal("Please fill or update atleast one field");
		event.preventDefault();
		return false;
	}
	
	// Get the textarea and checkboxes
	/*let textarea = document.querySelector('textarea[name="miscOtherActivityDesc"]');
	let checkbox1 = document.querySelector('input[name="miscOtherActivityPreviousWeek"]');
	let checkbox2 = document.querySelector('input[name="miscOtherActivityActiveWeek"]');

	// Check if any of the checkboxes is checked and no text is entered in the textarea
	if ((checkbox1.checked || checkbox2.checked) && textarea.value.trim() === '') {
		swal("Please fill the Miscellaneous Activities textbox area");
		event.preventDefault();
		return false;
	} 
	// Check if text is entered in the textarea but no checkboxes are checked
	if (!checkbox1.checked && !checkbox2.checked && textarea.value.trim() !== '') {
		swal("Please select either Week "+previousWeek+" or Week "+activeWeek+" checkbox to save data");
		event.preventDefault();
		return false;
	} 
	
	let lastMiscActivityRow  = document.querySelector('tr.misc-activity-rows:last-of-type');
	let lastMiscActivityRowInputs = lastMiscActivityRow .querySelectorAll('input[type="text"]');
	let weekNumbersList = JSON.parse(decodeURIComponent(document.getElementById("weekNumbers").getAttribute("value")));
	
	// Check if text is entered in the textarea and checkboxes are also checked but no efforts are filled in input fields
	if(lastMiscActivityRowInputs.length == 1){
		if(checkbox1.checked && textarea.value.trim() !== '' && weekNumbersList[0] == activeWeek){
			swal("Please check only the Week "+activeWeek+" checkbox");
			event.preventDefault();
			return false;
		}
		if(checkbox2.checked && textarea.value.trim() !== '' && weekNumbersList[0] == activeWeek && (lastMiscActivityRowInputs[0].value.trim() === '' || lastMiscActivityRowInputs[0].value.trim() === '00:00')){
				swal("Please fill data in Week "+activeWeek);
				event.preventDefault();
				return false;
		}
		if(checkbox2.checked && textarea.value.trim() !== '' && weekNumbersList[weekNumbersList.length - 1] == previousWeek){
			swal("Please check only the Week "+previousWeek+" checkbox");
			event.preventDefault();
			return false;
		}
		if(checkbox1.checked && textarea.value.trim() !== '' && weekNumbersList[weekNumbersList.length - 1] == previousWeek && (lastMiscActivityRowInputs[0].value.trim() === '' || lastMiscActivityRowInputs[0].value.trim() === '00:00')){
				swal("Please fill data in Week "+previousWeek);
				event.preventDefault();
				return false;
		}
	}
	else{
		if (checkbox1.checked && textarea.value.trim() !== '' && (lastMiscActivityRowInputs[0].value.trim() === '' || lastMiscActivityRowInputs[0].value.trim() === '00:00')) {
		swal("Please fill data in Week "+previousWeek);
		event.preventDefault();
		return false;
		}
		
		if (checkbox2.checked && textarea.value.trim() !== '' && (lastMiscActivityRowInputs[1].value.trim() === '' || lastMiscActivityRowInputs[1].value.trim() === '00:00')) {
			swal("Please fill data in Week "+activeWeek);
			event.preventDefault();
			return false;
		}
	}*/
	
	//event.preventDefault(); // Prevent default form submission
	let weekEffortInputs = document.querySelectorAll('.validateWeekEffort:not([disabled])');
  	let weekEffort = [];
  	for (let i = 0; i < weekEffortInputs.length; i++) {
          let value = weekEffortInputs[i].value.trim();
          weekEffort.push(value);
      }
  	//console.log(weekEffort);
  	
  	let weekNumbersInputs = document.getElementById("validateWeekNumber").value;
  	let weekNumbers= JSON.parse(weekNumbersInputs);
  	
    //console.log(weekNumberSubmitted);

    //let centerWeekSelected = weekNumbers[2];

    let interval = weekNumbers.length;
    //let startingIndex = Math.ceil(interval / 2) - (centerWeekSelected - weekNumberSubmitted) - 1;

    let separateWeekEffort = [];

    for (let startIndex = 0; startIndex < interval; startIndex++) {
        let innerList = [];

        for (let i = startIndex; i < weekEffort.length; i += interval) {
            innerList.push(weekEffort[i]);
        }

        separateWeekEffort.push(innerList);
    }
    event.preventDefault();
    let success = true;
    let success40 = true;
    let weekNumberLess40 = [];
    for(let p =0; p<separateWeekEffort.length; p++){
      let validateSum = 0;
      // Calculate the sum
      for (let i = 0; i < separateWeekEffort[p].length; i++) {
          if (separateWeekEffort[p][i].trim() !== "") {
        	let timeParts = separateWeekEffort[p][i].split(":");
        	    
    	    // Parse hours and minutes
    	    let hours = parseInt(timeParts[0], 10);
    	    let minutes = parseInt(timeParts[1], 10);

    	    // Calculate total minutes
    	    let totalMinutes = hours * 60 + minutes;
          	validateSum += totalMinutes;
          }
      }
      // Check if the sum is greater than 168
      if (validateSum > 10080) {
    	// Display an alert
          swal("Total effort cannot be greater than 168 hours in week " +weekNumbers[p])
              .then(function () {
                  // Code to execute after the alert is closed
            	  event.preventDefault();
              });
          
          success = false;
          break;
      }
      let activeWeekIndex = weekNumbers.indexOf(parseInt(activeWeek));
      let previousWeekIndex = weekNumbers.indexOf(parseInt(previousWeek));
      
      if(activeWeekIndex == p || previousWeekIndex == p){
		if (validateSum < 2400 && validateSum > 0) {
			success40 =false;
			weekNumberLess40.push(weekNumbers[p]);
		}
		
	  }
     
    }
   
	if(!success40 && success){
		    // Display a confirmation alert
		    let str='';
		    if(weekNumberLess40.length == 2){
				str = "Your effort is less than 40 hours in week " + weekNumberLess40[0] + " and week " + weekNumberLess40[1];
			}
			else if(weekNumberLess40.length == 1){
				str = "Your effort is less than 40 hours in week " + weekNumberLess40[0];
			}
		    swal({
		         text: str + ". Do you still want to submit?",
                  buttons: [                                                       
	                'No',
	                'Yes'
	              ]

		    }).then(function(isConfirm) {
		        if (isConfirm) {
		            // User clicked Yes, allow form submission
		            let submitMessage = "Data has been saved successfully"
			    	if(!wasTimesheetEmpty){
						submitMessage = "Data has been updated successfully";
					}
					
			    	swal(submitMessage).then(function(isConfirm) {
			
			            if (isConfirm) {
			
			            	document.getElementById("timesheetForm").submit();       
			
			            }
			
			          });
			    	event.preventDefault();
		            // Submit the form programmatically if needed
		            //document.getElementById("timesheetForm").submit();
		        } else {
		            // User clicked No, prevent form submission
		            event.preventDefault();
		        }
		    });
	}
	else{
		if(success == true){
	    	// Submit the form if the sum is within the allowed range
	    	let submitMessage = "Data has been saved successfully"
	    	if(!wasTimesheetEmpty){
				submitMessage = "Data has been updated successfully";
			}
			
	    	swal(submitMessage).then(function(isConfirm) {
	
	            if (isConfirm) {
	
	            	document.getElementById("timesheetForm").submit();       
	
	            }
	
	          });
	    	event.preventDefault();
    	         
    	}
	}
    
    
  });
  
//DYNAMIC CHANGING VALUE CODE
  
//Add an event listener for input changes within the table
  let weekNumbers = JSON.parse(decodeURIComponent(document.getElementById("weekNumbers").getAttribute("value")));
  let holidayCountWeekKeys = JSON.parse(decodeURIComponent(document.getElementById("holidayCountWeekKeys").getAttribute("value")));
  let holidayCount = JSON.parse(decodeURIComponent(document.getElementById("holidayCount").getAttribute("value")));
//Create a new array to store the updated holiday counts
  const updatedHolidayCount = weekNumbers.map(function(weekNumber) {
      if (holidayCountWeekKeys.includes(weekNumber)) {
          // If the weekNumber is in holidayCountWeekKeys, get the corresponding index
          const index = holidayCountWeekKeys.indexOf(weekNumber);
          return holidayCount[index];
      } else {
          // If the weekNumber is not in holidayCountWeekKeys, set the count to 0
          return 0;
      }
  });
  $('#timesheetTable').on('blur', 'input.dynamicWeekEffort', dynamicTimesheetForm);
  function dynamicTimesheetForm(){
	//for total efforts in a week
	let weekNumbersList = JSON.parse(decodeURIComponent(document.getElementById("weekNumbers").getAttribute("value")));
	let timesheetFormRows = document.querySelectorAll('#timesheetTable tbody tr');
	let timesheetFormRowsNoVac = [];
	timesheetFormRows.forEach(row => {
    let vacationTD = row.querySelectorAll('td')[1];
    if (vacationTD.innerText.trim() !== 'Vacation') {
        timesheetFormRowsNoVac.push(row); // Remove the row if the inner content of the first TD is 'vacation'
    }
	});
	let timesheetFormFootRows = document.querySelectorAll('#timesheetTable tfoot tr');
	let allInputFields = [];
	for(let i=0; i<timesheetFormRowsNoVac.length; i++){
		//let inputFields = timesheetFormRowsNoVac[i].querySelectorAll('input:[type="hidden"]:not([type="checkbox"])');
		let inputFields = timesheetFormRowsNoVac[i].querySelectorAll('input:not([disabled]):not([type="checkbox"])');
		allInputFields.push(inputFields)
		
	}
	//console.log(allInputFields)
	let totalWeekResult = [];
	const regex = /^(?!\d{2}:\d{2}$).*$/;
	for(let i = 0 ;i<weekNumbersList.length; i++){
		let sumPerWeek = 0;
		for(let j = 0; j<allInputFields.length; j++){
			

			sumPerWeek = sumPerWeek + HHMMtoMinutes(allInputFields[j][i].value.trim() == '0' || regex.test(allInputFields[j][i].value.trim())?'':allInputFields[j][i].value.trim());
			//totalWeekResult[i] = totalWeekResult[i] + HHMMtoMinutes(allInputFields[j][i].value.trim() == '0'?'':allInputFields[j][i].value.trim());
			//console.log(allInputFields[j][i].value);
		}
		//console.log(sumPerWeek);
		totalWeekResult.push(sumPerWeek);
	}
	//console.log("==========")
	//console.log(totalWeekResult);
	for(let i =0; i<totalWeekResult.length; i++){
		timesheetFormFootRows[0].querySelectorAll('td')[i+2].innerText =minutesToHHMM(totalWeekResult[i]) == '' ?'0':minutesToHHMM(totalWeekResult[i]);
	}

	for(let k = 0; k<totalWeekResult.length; k++){
		if(totalWeekResult[k]>10080){
			swal("Total effort cannot be greater than 168 hours in week "+weekNumbersList[k]);
		}
		if(totalWeekResult[k]>2400 - 480*(updatedHolidayCount[k])){
			let blinkColor = '#fce9e9';
          	$('#timesheetTable tbody td:nth-child(' + (k+3) + ')').css('background-color', blinkColor);
		}
		else{
			$('#timesheetTable tbody td:nth-child(' + (k+3) + ')').css('background-color', '');
		}
		
	}
	//for sum of all week in a project
	let allInputFieldsWithVac = [];
		for(let i=0; i<timesheetFormRows.length; i++){
			//let inputFields = timesheetFormRowsNoVac[i].querySelectorAll('input:[type="hidden"]:not([type="checkbox"])');
			let inputFields = timesheetFormRows[i].querySelectorAll('input:not([disabled]):not([type="checkbox"])');
			allInputFieldsWithVac.push(inputFields)
			
		}
		//console.log(timesheetFormRows);
		//console.log(allInputFieldsWithVac);
	for(let i = 0; i< timesheetFormRows.length; i++){
		
		let lastTd = timesheetFormRows[i].querySelectorAll('td')[timesheetFormRows[i].querySelectorAll('td').length - 1];
		let totalSum = 0;
		for(let j = 0; j<allInputFieldsWithVac[i].length; j++){
			totalSum = totalSum + HHMMtoMinutes(allInputFieldsWithVac[i][j].value.trim() == '0' || regex.test(allInputFieldsWithVac[i][j].value.trim())?'':allInputFieldsWithVac[i][j].value.trim());
		}
		lastTd.innerText = minutesToHHMM(totalSum) == ''?'0':minutesToHHMM(totalSum);
		//console.log(lastTd.innerText);
	}
	
  }
  
  
  
  
  
  
  
  
  
  
  
  
  //CODE FOR POPUP TABLE FOR PROJECT
  	//FOR TOGGLING OF TASK, SUB-TASK AND SUB-SUB-TASK IN DETAILED POPUP
  	function toggleSubCategories(button) {
		
		//first collapse all open category and sub-category
		let allSubCategoryToggleIcon = document.querySelectorAll('.category .toggle-icon');
	    let allSubCategory =  document.querySelectorAll('.sub-category');
	    let allSubSubCategoryToggleIcon = document.querySelectorAll('.sub-category .toggle-icon');
	    let allSubSubCategory =  document.querySelectorAll('.sub-sub-category');
	    
		let clickedButtonRow = button.parentNode.parentNode;
		let currentClickedSubCategories = [];
		let nextClickedSubCategory = clickedButtonRow.nextElementSibling;
		
	    while (nextClickedSubCategory) {
		    if (nextClickedSubCategory.classList.contains('sub-category')) {
		        currentClickedSubCategories.push(nextClickedSubCategory);
		    }
		    if (nextClickedSubCategory.classList.contains('category')) {
		        break;
		    }
		    nextClickedSubCategory = nextClickedSubCategory.nextElementSibling;
		}
		
		for(let i = 0; i<allSubSubCategory.length; i++){
			
			if(allSubSubCategory[i].style.display == 'table-row'){
				allSubSubCategory[i].style.display = 'none';
			}
		}
		for(let i = 0; i<allSubSubCategoryToggleIcon.length; i++){
			if(allSubSubCategoryToggleIcon[i].classList.contains('fa-minus')){
				allSubSubCategoryToggleIcon[i].classList.remove('fa-minus');
				allSubSubCategoryToggleIcon[i].classList.add('fa-plus');
				allSubSubCategoryToggleIcon[i].style.color = "#28a745";
			}
		}
		
		for(let i = 0; i<allSubCategory.length; i++){
			let flag = true;
			for(let j = 0;j<currentClickedSubCategories.length; j++){
				if(allSubCategory[i] == currentClickedSubCategories[j]){
					flag = false;
				}
			}
			if(allSubCategory[i].style.display == 'table-row' && flag){
				allSubCategory[i].style.display = 'none';
			}
		}
		for(let i = 0; i<allSubCategoryToggleIcon.length; i++){
			if(allSubCategoryToggleIcon[i].classList.contains('fa-minus')){
				allSubCategoryToggleIcon[i].classList.remove('fa-minus');
				allSubCategoryToggleIcon[i].classList.add('fa-plus');
				allSubCategoryToggleIcon[i].style.color = "#28a745";
			}
		}
		//show current open category
	    let currentRow = button.parentNode.parentNode; // This is the parent row of the button
	    let nextRow = currentRow.nextElementSibling; // Start with the next sibling
	    // Loop through all next siblings
	    
	    while (nextRow) {
	        if (nextRow.classList.contains('sub-category')) { // Check if it's a sub-category
	            toggleRowDisplay(nextRow, button);
	        }
	        nextRow = nextRow.nextElementSibling; // Move to the next sibling
	        if (!nextRow || nextRow.classList.contains('category')) {
	        	break;
	        }
	    }
	}

	function toggleSubSubCategories(button) {
		let clickedButtonRow = button.parentNode.parentNode;
		let clickedButtonSubSubCategory = [];
	    let clickedButtonNextRow = clickedButtonRow.nextElementSibling; // Start with the next sibling
	    // to get all sub sub category in a clicked sub category
	    while (clickedButtonNextRow) {
	        if (clickedButtonNextRow.classList.contains('sub-sub-category')) { // Check if it's a sub-sub-category
	            clickedButtonSubSubCategory.push(clickedButtonNextRow);
	        }
	         // Move to the next sibling
	        if (!clickedButtonNextRow || clickedButtonNextRow.classList.contains('sub-category')) {
	        	break;
	        }
	        clickedButtonNextRow = clickedButtonNextRow.nextElementSibling;
	    }
	    //to get all sub sub category row in a category
	    let clickedButtonAllSubSubCategoryAfter = [];
	    let clickedButtonAllSubSubCategoryAfterRow = clickedButtonRow.nextElementSibling;
	    while (clickedButtonAllSubSubCategoryAfterRow) {
	        if (clickedButtonAllSubSubCategoryAfterRow.classList.contains('sub-sub-category')) { // Check if it's a sub-sub-category
	            clickedButtonAllSubSubCategoryAfter.push(clickedButtonAllSubSubCategoryAfterRow);
	        }
	         // Move to the next sibling
	        if (clickedButtonAllSubSubCategoryAfterRow.classList.contains('category')) {
	        	break;
	        }
	        clickedButtonAllSubSubCategoryAfterRow = clickedButtonAllSubSubCategoryAfterRow.nextElementSibling;
	    }
	    let clickedButtonAllSubSubCategoryBefore = [];
	    let clickedButtonAllSubSubCategoryBeforeRow = clickedButtonRow.previousElementSibling;
	    while (clickedButtonAllSubSubCategoryBeforeRow) {
	        if (clickedButtonAllSubSubCategoryBeforeRow.classList.contains('sub-sub-category')) { // Check if it's a sub-sub-category
	            clickedButtonAllSubSubCategoryBefore.push(clickedButtonAllSubSubCategoryBeforeRow);
	        }
	        // Move to the next sibling
	        if (clickedButtonAllSubSubCategoryBeforeRow.classList.contains('category')) {
	        	break;
	        }
	        clickedButtonAllSubSubCategoryBeforeRow = clickedButtonAllSubSubCategoryBeforeRow.previousElementSibling; 
	    }
	    
	    let clickedButtonAllSubSubCategory = clickedButtonAllSubSubCategoryBefore.concat(clickedButtonAllSubSubCategoryAfter);
	    //to get all sub category row in a category
	    let clickedButtonAllSubCategoryAfter = [];
	    let clickedButtonAllSubCategoryAfterRow = clickedButtonRow.nextElementSibling;
	    while (clickedButtonAllSubCategoryAfterRow) {
	        if (clickedButtonAllSubCategoryAfterRow.classList.contains('sub-category')) { // Check if it's a sub-sub-category
	            clickedButtonAllSubCategoryAfter.push(clickedButtonAllSubCategoryAfterRow);
	        }
	         // Move to the next sibling
	        if (clickedButtonAllSubCategoryAfterRow.classList.contains('category')) {
	        	break;
	        }
	        clickedButtonAllSubCategoryAfterRow = clickedButtonAllSubCategoryAfterRow.nextElementSibling;
	    }
	    let clickedButtonAllSubCategoryBefore = [];
	    let clickedButtonAllSubCategoryBeforeRow = clickedButtonRow.previousElementSibling;
	    while (clickedButtonAllSubCategoryBeforeRow) {
	        if (clickedButtonAllSubCategoryBeforeRow.classList.contains('sub-category')) { // Check if it's a sub-sub-category
	            clickedButtonAllSubCategoryBefore.push(clickedButtonAllSubCategoryBeforeRow);
	        }
	         // Move to the next sibling
	        if (clickedButtonAllSubCategoryBeforeRow.classList.contains('category')) {
	        	break;
	        }
	        clickedButtonAllSubCategoryBeforeRow = clickedButtonAllSubCategoryBeforeRow.previousElementSibling;
	    }
	    let clickedButtonAllSubCategory = clickedButtonAllSubCategoryBefore.concat(clickedButtonAllSubCategoryAfter);
	    for(let i=0; i<clickedButtonAllSubCategory.length; i++){
			if(clickedButtonAllSubCategory[i].querySelector('i').classList.contains('fa-minus')){
				clickedButtonAllSubCategory[i].querySelector('i').classList.remove('fa-minus');
				clickedButtonAllSubCategory[i].querySelector('i').classList.add('fa-plus');
				clickedButtonAllSubCategory[i].querySelector('i').style.color = "#28a745";
			}
		}
		for(let i=0; i<clickedButtonAllSubSubCategory.length; i++){
			let flag = true;
			for(let j=0; j<clickedButtonSubSubCategory.length; j++){
				if(clickedButtonAllSubSubCategory[i] == clickedButtonSubSubCategory[j] ){
					flag = false;
					break;
				}
			}
			if(clickedButtonAllSubSubCategory[i].style.display == 'table-row' && flag){
				clickedButtonAllSubSubCategory[i].style.display = 'none';
			}
		}
		//open current clicked sub-category
	    let currentRow = button.parentNode.parentNode; // This is the parent row of the button
	    let nextRow = currentRow.nextElementSibling; // Start with the next sibling
	    // Loop through all next siblings
	    while (nextRow) {
	        if (nextRow.classList.contains('sub-sub-category')) { // Check if it's a sub-sub-category
	            toggleRowDisplay(nextRow, button);
	        }
	        nextRow = nextRow.nextElementSibling; // Move to the next sibling
	        if (!nextRow || nextRow.classList.contains('sub-category')) {
	        	break;
	        }
	    }
	}
	//for auto-scrolling
	document.querySelectorAll('.toggle-icon').forEach(function(icon) {
	    icon.addEventListener('click', function() {
	        // Find the parent row of the clicked '+' icon
	        let parentRow = icon.closest('tr');
	        if (parentRow) {
	            // Scroll the parent row into view
	            parentRow.scrollIntoView({ behavior: 'smooth', block: 'end' }); // Adjusted to scroll to the end of the parent element
	        }
	    });
	});


	function toggleRowDisplay(row, button) {
	    if (row.style.display === 'none') {
	        row.style.display = 'table-row'; // Show the row
	        button.classList.remove('fa-plus'); // Remove class for plus icon
	        button.classList.add('fa-minus');
	        button.style.color="#FF0000";
	    } else {
	        row.style.display = 'none'; // Hide the row
	        button.classList.remove('fa-minus'); // Remove class for plus icon
	        button.classList.add('fa-plus');
	        button.style.color="#28a745";
	    }
	}

  	//RETRIVING DATA FROM BACKEND
  	let taskProjectDetails = []; // Array to store input data
  	let taskProjectTimesheetTypeDetails = [];
	let projectTaskDetails = [];
	let projectSubTaskDescIdsPlain = JSON.parse(document.getElementById("projectSubTaskDescIdsPlainPopUp").value);
	let projectTaskIds = JSON.parse(document.getElementById("projectTaskIdsPopUp").value);
	//Function to show the popup with project details
	
	let localStorageData = [];
	  function showPopup(projectName , projectId, projectTimesheetType) {
			let firstTimeOpen = true;
	      // Set the heading of the popup
	      //document.getElementById("popupHeading").textContent = projectName;
			
	   		// Store the projectId in a data attribute of the popup
	      //document.getElementById("popup").setAttribute("data-project-id", projectId);
	      // Show the popup
	      let selectedYearPopUp = document.getElementById("yearSelectDB").value;
	      let selectedEmpId = document.getElementById("employeeIdDB").value;
	      
	      let projectToPick = 0;
			for(let i = 0; i<localStorageData.length;i++){
				if(localStorageData[i].projectId == projectId){
					firstTimeOpen = false;
					projectToPick = i;
					break;
				}
			}
			
	      if(projectTimesheetType == 'b'){
			document.getElementsByClassName("popupHeading")[0].textContent = projectName;
			document.getElementById("popupBasic").setAttribute("data-project-id", projectId);
			document.getElementById("popupBasic").style.display = "block";
			if(document.getElementById("popupDetailed").style.display === "block"){
				document.getElementById("popupDetailed").style.display = "none"
			}
			//ajax call to auto populate
			
			
			if(firstTimeOpen){
				$.ajax({
	 	        type: "POST",
	 	        url: "/PMS/getProjectPopUpData",
	 	        data: {
	 	            "selectedYear": selectedYearPopUp,
	 	            "activeWeek": activeWeek,
	 	            "previousWeek": previousWeek,
	 	            "selectedEmpId": selectedEmpId,
	 	            "projectTimesheetType": projectTimesheetType,
	 	            "projectId": projectId
	 	        },
	 	        success: function (response) {
	 	            let basicPopupInputs = document.querySelectorAll("#popupBasic tr td input");
	 	            for(let i = 0; i<basicPopupInputs.length; i++){
						let id = basicPopupInputs[i].id;
						let parts = id.split("-");
						if(parts[1] == "0"){
							let requiredEffort = response[0][parts[0]];
							basicPopupInputs[i].value = minutesToHHMM(parseInt(requiredEffort));
						}
						else{
							let requiredEffort = response[1][parts[0]];
							basicPopupInputs[i].value = minutesToHHMM(parseInt(requiredEffort));
						}
					}
	 	            

	 	        },
	 	        error: function (xhr, textStatus, errorThrown) {
	 	            console.error("Error occurred: " + textStatus, errorThrown);
	 	        }
	 	    });
			}
			else{
				let basicPopupInputs = document.querySelectorAll("#popupBasic tr td input");
				for(let i = 0; i<basicPopupInputs.length; i++){
					basicPopupInputs[i].value = localStorageData[projectToPick].efforts[i];
				}
				
			}
			
		  }
		  
		  
		  else if(projectTimesheetType == 'd'){
			document.getElementsByClassName("popupHeading")[1].textContent = projectName;
			document.getElementById("popupDetailed").setAttribute("data-project-id", projectId);
			document.getElementById("popupDetailed").style.display = "block";
			if(document.getElementById("popupBasic").style.display === "block"){
				document.getElementById("popupBasic").style.display = "none"
			}
			//ajax call to auto populate
			if(firstTimeOpen){
				$.ajax({
	 	        type: "POST",
	 	        url: "/PMS/getProjectPopUpData",
	 	        data: {
	 	            "selectedYear": selectedYearPopUp,
	 	            "activeWeek": activeWeek,
	 	            "previousWeek": previousWeek,
	 	            "selectedEmpId": selectedEmpId,
	 	            "projectTimesheetType": projectTimesheetType,
	 	            "projectId": projectId
	 	        },
	 	        success: function (response) {
					let detailedPopupInputs = document.querySelectorAll("#popupDetailed tr td input");
	 	            for(let i = 0; i<detailedPopupInputs.length; i++){
						let id = detailedPopupInputs[i].id;
						let parts = id.split("-");
						if(parts[1] == "0"){
							let requiredEffort = response[0][parts[0]];
							detailedPopupInputs[i].value = minutesToHHMM(parseInt(requiredEffort));
						}
						else{
							let requiredEffort = response[1][parts[0]];
							detailedPopupInputs[i].value = minutesToHHMM(parseInt(requiredEffort));
						}
					}
					
					autoPopulateDetailedTimesheet();
					for(let i = 0; i<detailedPopupInputs.length; i++){
						detailedPopupInputs[i].addEventListener("blur", autoPopulateDetailedTimesheet);
					}
					
	 	        },
	 	        error: function (xhr, textStatus, errorThrown) {
	 	            console.error("Error occurred: " + textStatus, errorThrown);
	 	        }
	 	    });
			}
			else{
				let detailedPopupInputs = document.querySelectorAll("#popupDetailed tr td input");
				for(let i = 0; i<detailedPopupInputs.length; i++){
					detailedPopupInputs[i].value = localStorageData[projectToPick].efforts[i];
				}
			}			
	 	    //firstTimeOpenDetailed = false;
		  }
		  
		 /* document.getElementById("popup").style.display = "block";*/
	  }
	//HELPING FUNCTIONS 
	function HHMMtoMinutes(HHMM){
		if(HHMM == '' || HHMM.length == 0){
			return 0;
		}
		let timeParts = HHMM.split(":");
	    


	    // Parse hours and minutes
	    let hours = parseInt(timeParts[0], 10);
	    let minutes = parseInt(timeParts[1], 10);

	    // Calculate total minutes
	    let totalMinutes = hours * 60 + minutes; 
	    return totalMinutes;
	}
	
	function minutesToHHMM(totalMinutes) {
		if(totalMinutes == 0){
			return '';
		}
	    // Calculate hours and minutes
	    let hours = Math.floor(totalMinutes / 60);
	    let minutes = totalMinutes % 60;

	    // Format hours and minutes as HH:MM
	    let HH = String(hours).padStart(2, '0'); // Ensure two-digit format
	    let MM = String(minutes).padStart(2, '0'); // Ensure two-digit format

	    return HH + ':' + MM;
	}
	
	//WHAT SHOULD HAPPEN WHEN 'OK' BUTTON IS CLICKED
	// Add event listener to the OK button
	
    $('.savePopUpButton').click(function() {
		let buttonTimesheetType = $(this).attr('timesheetType');
        let popup;
        let projectId;
        let rows;
        if(buttonTimesheetType === 'b'){
			popup = document.getElementById("popupBasic");
        	projectId = popup.getAttribute("data-project-id"); 
        	rows = document.querySelectorAll("#popupTableBasic tbody tr");
        	taskProjectTimesheetTypeDetails.push('b');
		}
		else if(buttonTimesheetType === 'd'){
			popup = document.getElementById("popupDetailed");
        	projectId = popup.getAttribute("data-project-id"); 
        	rows = document.querySelectorAll("#popupTableDetailed tbody tr.sub-sub-category");
        	taskProjectTimesheetTypeDetails.push('d');
		}
        taskProjectDetails.push(projectId);
        
        // Loop through each row in the popup table
        //let rows = document.querySelectorAll("#popupTable tbody tr.sub-sub-category");
        let previousWeekSumMinutes = 0;
        let activeWeekSumMinutes = 0;
        let list1 = [];
        let previousWeekElement = document.getElementsByClassName(projectId + previousWeek);
        let activeWeekElement = document.getElementsByClassName(projectId + activeWeek);
        //for local storage
        let localStorageDataWeekEffort = [];
        
        rows.forEach(function(row, count) {
			let taskOrSubTaskDescId;
			if(buttonTimesheetType === 'b'){
				taskOrSubTaskDescId = projectTaskIds[count];
			}
			else if(buttonTimesheetType === 'd'){
				taskOrSubTaskDescId = projectSubTaskDescIdsPlain[count]; 
			}
            //let taskOrSubTaskDescId = projectSubTaskDescIdsPlain[count]; // Get task id
            let week1Value = row.cells[1].querySelector("input").value.trim(); // Get value of week 1 input
            let week2Value = row.cells[2].querySelector("input").value.trim(); // Get value of week 2 input
            localStorageDataWeekEffort.push(week1Value);
            localStorageDataWeekEffort.push(week2Value);
           if(week1Value.length==0){
          	 previousWeekSumMinutes = previousWeekSumMinutes + 0;
           }else{
          	 previousWeekSumMinutes = previousWeekSumMinutes + HHMMtoMinutes(week1Value);
           }
           
           if(week2Value.length==0){
          	 activeWeekSumMinutes = activeWeekSumMinutes + 0;
           }else{
          	 activeWeekSumMinutes = activeWeekSumMinutes + HHMMtoMinutes(week2Value);
           }
            
            
            //document.getElementById(projectId+previousWeek).value = minutesToHHMM(previousWeekSumMinutes);
            /*//document.getElementById(projectId+activeWeek).value = minutesToHHMM(activeWeekSumMinutes);
            if (!previousWeekElement || previousWeekElement.length <= 0){
				week1Value='';
			}
			if (!activeWeekElement || activeWeekElement.length <= 0) {
				week2Value='';
			}*/
            let list2 = [taskOrSubTaskDescId, week1Value, week2Value];
            list1.push(list2);
            
            //popUpInputData.push({projectId: { taskId: {previousWeek: week1Value, activeWeek: week2Value} }}); // Push data to array
        });
        projectTaskDetails.push(list1);
        
        // Update input fields only if the projectId+previousWeek and projectId+activeWeek elements exist
        console.log("foccccuusss")
        console.log(previousWeekElement);
        console.log(activeWeekElement);
        if (previousWeekElement && previousWeekElement.length > 0) {
      	  previousWeekElement[0].value = minutesToHHMM(previousWeekSumMinutes);
        }
        if (activeWeekElement && activeWeekElement.length > 0) {
      	  activeWeekElement[0].value = minutesToHHMM(activeWeekSumMinutes);
        }
        
        // Send inputData and projectId to the controller
        // Here, you can use an input field to send data back to the controller
        
        
        document.getElementById("projectTaskDetails").value = projectTaskDetails;
        document.getElementById("taskProjectDetails").value = taskProjectDetails;
        document.getElementById("taskProjectTimesheetTypeDetails").value = taskProjectTimesheetTypeDetails
        console.log("===============");
        console.log(taskProjectDetails);
        console.log(projectTaskDetails);
        console.log(taskProjectTimesheetTypeDetails);
        console.log("===============");
        // Close the popup
        dynamicTimesheetForm();
        
        //saving data in local storage so that we can show data when we reopen project 
     
		for(let i = 0; i<localStorageData.length; i++){
			if(localStorageData[i].projectId == projectId){
				localStorageData = localStorageData.filter(function(item) {return item != localStorageData[i];});
			}
		}
		//console.log(localStorageData)
        let data = {
		    projectId: projectId,
		    efforts: localStorageDataWeekEffort
		};
		localStorageData.push(data);
        popup.style.display = "none";
        // Submit the form or perform any other action as needed
        // For example:
        // document.getElementById("formId").submit(); // Submit the form containing the input fields
    });
    //POP UP WILL APPEAR WHEN ANY PROJECT IS CLICKED
	  // Add event listeners to project name cells
	  //let projectNameCells = document.querySelectorAll('.projectNamesUI');
	  
	let projectCells = document.querySelectorAll('.project-rows');
	   projectCells.forEach(function(cell) {
       cell.addEventListener('click', function() {
		  let projectNameUI = this.querySelector('td.projectNamesUI');
          let projectName = projectNameUI.textContent.trim(); // Get the text content of the cell
          let projectId = projectNameUI.getAttribute("projectId");
          let projectTimesheetType = projectNameUI.getAttribute("projectTimesheetType");
          showPopup(projectName , projectId, projectTimesheetType); // Show popup with the project details
	      });
	});
	
	/*projectCells.forEach(function(cell) {
	   let disabledInput = cell.querySelectorAll('td input[disabled]');
	   disabledInput.forEach(function(input){
		input.addEventListener('click', function() {
		  let projectNameUI = this.parentElement.ParentElement.querySelector('td.projectNamesUI');
          let projectName = projectNameUI.textContent.trim(); // Get the text content of the cell
          let projectId = projectNameUI.getAttribute("projectId");
          let projectTimesheetType = projectNameUI.getAttribute("projectTimesheetType");
          showPopup(projectName , projectId, projectTimesheetType); // Show popup with the project details
	      });
	   })
       
	});*/
	 
	  
	 
	//POP UP WILL GET CLOSED WHEN 'CANCEL' BUTTON IS CLICKED
	// Close the popup when clicking outside of it or on the cancel button
	window.onclick = function(event) {
      let popupDetailed = document.getElementById("popupDetailed");
      let popupBasic = document.getElementById("popupBasic");
      if (event.target == popupDetailed || event.target.classList.contains('cancelPopUpButton')) {
			/*let projectId = event.target.parentElement.parentElement.getAttribute('data-project-id');
	          for(let i = 0; i<localStorageData.length; i++){
				if(localStorageData[i].projectId == projectId){
					localStorageData = localStorageData.filter(function(item) {return item != localStorageData[i];});
				}
		  	}*/
          popupDetailed.style.display = "none";
      }
      if (event.target == popupBasic || event.target.classList.contains('cancelPopUpButton')) {
			/*let projectId = event.target.parentElement.parentElement.getAttribute('data-project-id');
	          for(let i = 0; i<localStorageData.length; i++){
				if(localStorageData[i].projectId == projectId){
					localStorageData = localStorageData.filter(function(item) {return item != localStorageData[i];});
				}
		  	}*/
          popupBasic.style.display = "none";
          //firstTimeOpenBasic = true;
      }
	}
  
  
  //AUTO POPULATE OF POP UP (detailed timesheet)
  function autoPopulateDetailedTimesheet(){
	  let allCategoryRow = document.querySelectorAll('#popupTableDetailed tr.category');
	  for(let i=0; i<allCategoryRow.length; i++){
		let categorySum1 = 0;
		let categorySum2 = 0;
		let categoryNextSibling = allCategoryRow[i].nextElementSibling
		while(categoryNextSibling){
			if(categoryNextSibling.classList.contains('sub-sub-category')){
				let categoryNextSiblingtd = categoryNextSibling.querySelectorAll('td')
				categorySum1 = categorySum1 + HHMMtoMinutes(categoryNextSiblingtd[1].querySelector('input').value.trim());
				categorySum2 = categorySum2 + HHMMtoMinutes(categoryNextSiblingtd[2].querySelector('input').value.trim());
			}
			if(categoryNextSibling.classList.contains('category')){
				break;
			}
			categoryNextSibling = categoryNextSibling.nextElementSibling
		}
		allCategoryRow[i].querySelectorAll('td')[1].innerText = minutesToHHMM(categorySum1);
		allCategoryRow[i].querySelectorAll('td')[2].innerText = minutesToHHMM(categorySum2);
	  }
	  
	  let allSubCategoryRow = document.querySelectorAll('#popupTableDetailed tr.sub-category');
	  for(let i=0; i<allSubCategoryRow.length; i++){
		let subCategorySum1 = 0;
		let subCategorySum2 = 0;
		let subCategoryNextSibling = allSubCategoryRow[i].nextElementSibling
		while(subCategoryNextSibling){
			if(subCategoryNextSibling.classList.contains('sub-sub-category')){
				let subCategoryNextSiblingtd = subCategoryNextSibling.querySelectorAll('td')
				subCategorySum1 = subCategorySum1 + HHMMtoMinutes(subCategoryNextSiblingtd[1].querySelector('input').value.trim());
				subCategorySum2 = subCategorySum2 + HHMMtoMinutes(subCategoryNextSiblingtd[2].querySelector('input').value.trim());
			}
			if(subCategoryNextSibling.classList.contains('sub-category')){
				break;
			}
			subCategoryNextSibling = subCategoryNextSibling.nextElementSibling
		}
		allSubCategoryRow[i].querySelectorAll('td')[1].innerText = minutesToHHMM(subCategorySum1);
		allSubCategoryRow[i].querySelectorAll('td')[2].innerText = minutesToHHMM(subCategorySum2);
	  }
  }
  
		  
  //POP UP FOR HELP
  document.getElementById('helpLink').addEventListener('click', function(event) {
  event.preventDefault();
  document.getElementById('popupHelp').style.display = 'block';
  });

  function closePopupHelp() {
      document.getElementById('popupHelp').style.display = 'none';
  }
  function toggleList(icon) {
	    let list = icon.parentElement.nextElementSibling; // Get the sibling ul element
	    
	    if (list.style.display === 'none') {
	        list.style.display = 'block'; // Show the list
	        icon.classList.remove('fa-plus'); // Remove class for plus icon
	        icon.classList.add('fa-minus'); // Add class for minus icon
	    } else {
	        list.style.display = 'none'; // Hide the list
	        icon.classList.remove('fa-minus'); // Remove class for minus icon
	        icon.classList.add('fa-plus'); // Add class for plus icon
	    }
	}

	
//Getting data from backend of all misc other activity for TEXT AREA so to give the functionality of searching
let allMiscActivityDescString = document.getElementById("allMiscActivityDescTemp").value;
let allMiscActivityDescArray = allMiscActivityDescString.substring(1, allMiscActivityDescString.length - 1).split("^,");
let lastItem = allMiscActivityDescArray.pop(); // Remove the last item from the array
lastItem = lastItem.replace("^", ""); // Remove the "^" character from the last item
allMiscActivityDescArray.push(lastItem); // Add the modified last item back to the array
let formattedJSON = "[" + allMiscActivityDescArray.map(function(item) {
    return '"' + item.trim() + '"';
}).join(", ") + "]";
let allMiscActivityDesc = JSON.parse(formattedJSON); // Use allMiscActivityDescString instead of formattedJSON
	
	
// Define the autocomplete function - for seaching in Miscellaneous text area
$(function() {
    //var allMiscActivityDesc = JSON.parse(document.getElementById("allMiscActivityDesc").value);

    // Enable autocomplete on the textarea
    $('textarea[name="miscOtherActivityDesc"]').autocomplete({
        source: allMiscActivityDesc, // Provide the list of suggestions
        minLength: 1, // Minimum number of characters before the autocomplete activates
        autoFocus: true // Automatically focus on the first item in the autocomplete list
    });
});


/*//ADDING TEXT AREA AND CHECKBOXES
//Get all <td> elements
let tdElements = document.getElementsByTagName('td');

// Loop through each <td> element
for (let i = 0; i < tdElements.length; i++) {
  // Check if the inner text contains "Miscellaneous"
  if (tdElements[i].textContent.includes("Miscellaneous")) {
      // Create a space element
      let space = document.createElement('span');
      space.innerHTML = "&nbsp;";
      // Append the space element before the textarea
      let icon = document.createElement('span');
      icon.setAttribute('id', 'miscellaneousToggle');
      icon.setAttribute('style', 'cursor: pointer;');
      icon.innerHTML = "<i class='toggle-icon fa fa-plus' style='color: #28a745;'></i>";
      tdElements[i].appendChild(space);
      tdElements[i].appendChild(icon);
      
      // Create a new div to contain the textarea and checkboxes
      let container = document.createElement('div');
      container.setAttribute('id', 'miscellaneousDetails');
      container.setAttribute('style', 'display: none; align-items: center; margin-left: 10px;');
      container.style.display = 'flex'; // Use flexbox to align items horizontally
      container.style.alignItems = 'center'; // Align items vertically
      
      // Create a new textarea element
      let textareaElement = document.createElement('textarea');
      textareaElement.setAttribute('name', 'miscOtherActivityDesc');
      textareaElement.setAttribute('placeholder', 'Add Other Miscellaneous Activity (Optional)');
      container.appendChild(textareaElement);
      
      // Create the first checkbox container
      let checkboxContainer1 = document.createElement('div');
      checkboxContainer1.style.display = 'flex'; // Use flexbox to align items horizontally
      checkboxContainer1.style.alignItems = 'center'; // Align items vertically
      checkboxContainer1.style.marginLeft = '10px'; // Adjust margin for spacing
      
      // Create the first checkbox
      let checkbox1 = document.createElement('input');
      checkbox1.type = 'checkbox';
      checkbox1.name = 'miscOtherActivityPreviousWeek';
      checkbox1.value = previousWeek; // Assuming previousWeek is defined
      
      // Create the label for the first checkbox
      let label1 = document.createElement('label');
      label1.innerHTML = 'Week ' + previousWeek;
      label1.style.marginLeft = '5px'; // Adjust margin for spacing
      label1.style.marginBottom = '0px';
      label1.style.marginTop = '5px';
      
      // Append the first checkbox and label to the checkbox container
      checkboxContainer1.appendChild(checkbox1);
      checkboxContainer1.appendChild(label1);
      
      // Create the second checkbox container
      let checkboxContainer2 = document.createElement('div');
      checkboxContainer2.style.display = 'flex'; // Use flexbox to align items horizontally
      checkboxContainer2.style.alignItems = 'center'; // Align items vertically
      checkboxContainer2.style.marginLeft = '10px'; // Adjust margin for spacing
      
      // Create the second checkbox
      let checkbox2 = document.createElement('input');
      checkbox2.type = 'checkbox';
      checkbox2.name = 'miscOtherActivityActiveWeek';
      checkbox2.value = activeWeek; // Assuming activeWeek is defined
      
      // Create the label for the second checkbox
      let label2 = document.createElement('label');
      label2.innerHTML = 'Week ' + activeWeek;
      label2.style.marginLeft = '5px'; // Adjust margin for spacing
      label2.style.marginBottom = '0px';
      label2.style.marginTop = '5px';
      
      // Append the second checkbox and label to the checkbox container
      checkboxContainer2.appendChild(checkbox2);
      checkboxContainer2.appendChild(label2);
      
      // Append the checkbox containers to the main container
      container.appendChild(checkboxContainer1);
      container.appendChild(checkboxContainer2);
      
      // Append the container after the space
      tdElements[i].appendChild(container);
      
      // Break the loop if you only want to add it to the first occurrence
      break;
  }
}
//Get the toggle and details elements
let toggle = document.getElementById('miscellaneousToggle');
let details = document.getElementById('miscellaneousDetails');

// Add click event listener to toggle display
toggle.addEventListener('click', function() {
    if (details.style.display === 'none') {
        details.style.display = 'flex';
        toggle.innerHTML = "<i class='toggle-icon fa fa-minus' style='color: #f54254;'></i>";
    } else {
        details.style.display = 'none';
        toggle.innerHTML = "<i class='toggle-icon fa fa-plus' style='color: #28a745;'></i>";
    }
});*/


//SHOWING ONLY ACTIVE AND PREVIOUS WEEK COLUMN
//Get all table rows
shortViewTimesheetToggleFun();
// Get the checkbox element
const myCheckbox = document.getElementById('myCheckbox');

// Add event listener for change event
myCheckbox.addEventListener('change', function() {
  if (this.checked) {
    // Call function when checkbox is checked
    fullViewTimesheetToggleFun();
  } else {
    // Call function when checkbox is unchecked
    shortViewTimesheetToggleFun();
  }
});

function shortViewTimesheetToggleFun() {
    let haveProjects = document.getElementById("haveProjects").value;
	if(haveProjects == "false"){
		let rows = document.querySelectorAll('#timesheetTable tbody tr');
		
		// Get all column headers rows
		let headers = document.querySelectorAll('#timesheetTable thead tr');
		
		let footers = document.querySelectorAll('#timesheetTable tfoot tr');
		
		let header_th_top = headers[0].querySelectorAll('th');
		
		let header_th_below = headers[1].querySelectorAll('th');
		
		let footer_td = footers[0].querySelectorAll('td');
		
		for (let i = 0; i < header_th_top.length; i++) {
			let headerText = header_th_top[i].textContent.trim();
			if(headerText !== "S. No." && headerText !== "Project Reference Number & Name" && headerText !== "Week "+previousWeek && headerText !== "Week "+activeWeek && headerText !== "Total Project Effort"){
				header_th_top[i].style.display = 'none';
				header_th_below[i].style.display = 'none';
				footer_td[i].style.display = 'none';
				for (let j = 0; j < rows.length; j++) {
					let single_row_td = rows[j].querySelectorAll('td');
					single_row_td[i].style.display = 'none';
				}
			}
		}
	}else{
		let rows = document.querySelectorAll('#timesheetTable tbody tr');
		
		// Get all column headers rows
		let headers = document.querySelectorAll('#timesheetTable thead tr');
		
		let footers = document.querySelectorAll('#timesheetTable tfoot tr');
		
		let header_th_top = headers[0].querySelectorAll('th');
		
		let footer_td = footers[0].querySelectorAll('td');
		
		for (let i = 0; i < header_th_top.length; i++) {
			let headerText = header_th_top[i].textContent.trim();
			if(headerText !== "" && headerText !== "Activity Name" && headerText !== "Week "+previousWeek && headerText !== "Week "+activeWeek && headerText !== "Total Project Effort"){
				header_th_top[i].style.display = 'none';
				footer_td[i].style.display = 'none';
				for (let j = 0; j < rows.length; j++) {
					let single_row_td = rows[j].querySelectorAll('td');
					single_row_td[i].style.display = 'none';
				}
			}
		}
	}
}

function fullViewTimesheetToggleFun() {
    let haveProjects = document.getElementById("haveProjects").value;
	if(haveProjects == "false"){
		let rows = document.querySelectorAll('#timesheetTable tbody tr');
		
		// Get all column headers rows
		let headers = document.querySelectorAll('#timesheetTable thead tr');
		
		let footers = document.querySelectorAll('#timesheetTable tfoot tr');
		
		let header_th_top = headers[0].querySelectorAll('th');
		
		let header_th_below = headers[1].querySelectorAll('th');
		
		let footer_td = footers[0].querySelectorAll('td');
		
		for (let i = 0; i < header_th_top.length; i++) {
			let headerText = header_th_top[i].textContent.trim();
			if(headerText !== "S. No." && headerText !== "Project Reference Number & Name" && headerText !== "Week "+previousWeek && headerText !== "Week "+activeWeek && headerText !== "Total Project Effort"){
				header_th_top[i].style.display = 'table-cell';
				header_th_below[i].style.display = 'table-cell';
				footer_td[i].style.display = 'table-cell';
				for (let j = 0; j < rows.length; j++) {
					let single_row_td = rows[j].querySelectorAll('td');
					single_row_td[i].style.display = 'table-cell';
				}
			}
		}
	}else{
		let rows = document.querySelectorAll('#timesheetTable tbody tr');
		
		// Get all column headers rows
		let headers = document.querySelectorAll('#timesheetTable thead tr');
		
		let footers = document.querySelectorAll('#timesheetTable tfoot tr');
		
		let header_th_top = headers[0].querySelectorAll('th');
		
		let footer_td = footers[0].querySelectorAll('td');
		
		for (let i = 0; i < header_th_top.length; i++) {
			let headerText = header_th_top[i].textContent.trim();
			if(headerText !== "" && headerText !== "Activity Name" && headerText !== "Week "+previousWeek && headerText !== "Week "+activeWeek && headerText !== "Total Project Effort"){
				header_th_top[i].style.display = 'table-cell';
				footer_td[i].style.display = 'table-cell';
				for (let j = 0; j < rows.length; j++) {
					let single_row_td = rows[j].querySelectorAll('td');
					single_row_td[i].style.display = 'table-cell';
				}
			}
		}
	}
}
