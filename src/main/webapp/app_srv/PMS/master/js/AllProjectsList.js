function startWorkFlow(applicationId,moduleTypeId,businessTypeId,projectTypeId,categoryId){
			openWindowWithPost('POST','/PMS/startProjectWorkflow',{"numId":applicationId,"moduleTypeId":moduleTypeId,"businessTypeId":businessTypeId,"projectTypeId":projectTypeId,"categoryId":categoryId},'_blank');
			}
	
	
	



$(document).ready(function() {
	$('#example').DataTable({
		 dom: 'Bfrtip',
		"paging":   false, 
		"ordering": false,
		buttons: [
			           'excel', 'pdf', 'print'
			        ],
	});
});


function closeProject(){
	var radioValue = $("input[name='projectIdRadio']:checked").val();

	if(!radioValue){
		sweetSuccess('Attention','Please select Project for closure');
		return false;
	}
	swal({
		  title: "Do you want to initiate for Project Closure?",
		  buttons: [					               
	                'No',
	                'Yes'
	              ],	     
	    }).then(function(isConfirm) {
	      if (isConfirm) {
	    	 
	    	  $.ajax({
	    	        type: "POST",
	    	        url: "/PMS/mst/getDetailOfTransaction",
	    	     
	    	        data: {"encProjectId":radioValue},			
	    			success : function(response) { 
	    			
	    				if(response=='success'){
	    					openWindowWithPost('GET','/PMS/mst/projectClosure',{"encProjectId" : radioValue},'_self');
	    				}
	    				else{
	    					sweetSuccess("Attention",response);
	    					return false;
	    				}
	    			},
	    			error : function(e) {
	    			}
	    	  	});
	      } else {
	    	  
	      }
	    }); 
	
	
	/*else{
		openWindowWithPost('GET','/PMS/mst/projectClosure',{"encProjectId" : radioValue},'_self');
	}*/
}

/*$(document).ready(function() {
	  var valuesSet = new Set();
	  $(".underclos").each(function() {
	    var value = $(this).text().trim();
	    valuesSet.add(value);
	  });
	  var value1;
	  var valuesSet1 = new Set();
	  $(".data").each(function() {
	    value1 = $(this).text();
	    valuesSet1.add(value1);
	  });
	  
	  var valuesSet3 = new Set();
	  $(".underclos1").each(function() {
	    var value3 = $(this).text().trim();
	    valuesSet3.add(value3);
	  });
	  
	  var valuesSet5 = new Set();
	  $(".underclos2").each(function() {
	    var value5 = $(this).text().trim();
	    valuesSet5.add(value5);
	  });
	  var valuesSet6 = new Set();
	  $(".underclos3").each(function() {
	    var value6 = $(this).text().trim();
	    valuesSet6.add(value6);
	  });
	  var valuesSet7 = new Set();
	  $(".underclos4").each(function() {
	    var value7 = $(this).text().trim();
	    valuesSet7.add(value7);
	  });
	  var valuesSet8 = new Set();
	  $(".underclos5").each(function() {
	    var value8 = $(this).text().trim();
	    valuesSet8.add(value8);
	  });
	  var valuesSet9 = new Set();
	  $(".underclos6").each(function() {
	    var value9 = $(this).text().trim();
	    valuesSet9.add(value9);
	  });


	  var valuesSet4 = new Set();
	  $(".data1").each(function() {
	    var value4 = $(this).text();
	    valuesSet4.add(value4);
	  });

	  console.log(valuesSet);
	  console.log(valuesSet4.size);
	  console.log(valuesSet3);
	  
	  var projectRefNo;
	  var value = new Set();
	  // Loop through each element with class "unique" individually
	  $(".unique").each(function() {
	     projectRefNo = $(this).text().trim();
	     value.add(projectRefNo);
	    // Check if the current element's 'projectRefrenceNo' exists in 'valuesSet'
	    if (valuesSet.has(projectRefNo)) {
	      // If the element's 'projectRefrenceNo' exists in 'valuesSet', set its class to "golden-text"
	      $(this).removeClass("grey-text").addClass("golden-text");
	    } else {
	      // If not, set its class to "grey-text"
	      $(this).removeClass("golden-text").addClass("grey-text");
	    }
	  });
	  var strProjectName
	  $(".unique1").each(function() {
		    strProjectName = $(this).text().trim();
		    
		    // Check if the current element's 'projectRefrenceNo' exists in 'valuesSet'
		    if (valuesSet3.has(strProjectName)) {
		      // If the element's 'projectRefrenceNo' exists in 'valuesSet', set its class to "golden-text"
		      $(this).removeClass("grey-text").addClass("golden-text");
		    } else {
		      // If not, set its class to "grey-text"
		      $(this).removeClass("golden-text").addClass("grey-text");
		    }
		  });
	  $(".unique2").each(function() {
		    var businessType = $(this).text().trim();
		    
		    // Check if the current element's 'projectRefrenceNo' exists in 'valuesSet'
		    if (valuesSet5.has(businessType)) {
		      // If the element's 'projectRefrenceNo' exists in 'valuesSet', set its class to "golden-text"
		      $(this).removeClass("grey-text").addClass("golden-text");
		    } else {
		      // If not, set its class to "grey-text"
		      $(this).removeClass("golden-text").addClass("grey-text");
		    }
		  });
	  $(".unique3").each(function() {
		    var startDate = $(this).text().trim();
		    
		    // Check if the current element's 'projectRefrenceNo' exists in 'valuesSet'
		    if (valuesSet6.has(startDate)) {
		      // If the element's 'projectRefrenceNo' exists in 'valuesSet', set its class to "golden-text"
		      $(this).removeClass("grey-text").addClass("golden-text");
		    } else {
		      // If not, set its class to "grey-text"
		      $(this).removeClass("golden-text").addClass("grey-text");
		    }
		  });
	  $(".unique4").each(function() {
		    var endDate = $(this).text().trim();
		    
		    // Check if the current element's 'projectRefrenceNo' exists in 'valuesSet'
		    if (valuesSet7.has(endDate)) {
		      // If the element's 'projectRefrenceNo' exists in 'valuesSet', set its class to "golden-text"
		      $(this).removeClass("grey-text").addClass("golden-text");
		    } else {
		      // If not, set its class to "grey-text"
		      $(this).removeClass("golden-text").addClass("grey-text");
		    }
		  });
	  $(".unique5").each(function() {
		    var projectDuration = $(this).text().trim();
		    
		    // Check if the current element's 'projectRefrenceNo' exists in 'valuesSet'
		    if (valuesSet8.has(projectDuration)) {
		      // If the element's 'projectRefrenceNo' exists in 'valuesSet', set its class to "golden-text"
		      $(this).removeClass("grey-text").addClass("golden-text");
		    } else {
		      // If not, set its class to "grey-text"
		      $(this).removeClass("golden-text").addClass("grey-text");
		    }
		  });
	  $(".unique6").each(function() {
		    var strTotalCost = $(this).text().trim();
		    
		    // Check if the current element's 'projectRefrenceNo' exists in 'valuesSet'
		    if (valuesSet9.has(strTotalCost)) {
		      // If the element's 'projectRefrenceNo' exists in 'valuesSet', set its class to "golden-text"
		      $(this).removeClass("grey-text").addClass("golden-text");
		    } else {
		      // If not, set its class to "grey-text"
		      $(this).removeClass("golden-text").addClass("grey-text");
		    }
		  });

	 
	});

*/