$('.select2Option').select2({
width: '70%'
});
$(document).ready(function() {

//$('#projectDiv').hide();
//hidedata();

//$(".hidetable").addClass("hidden");

});
$(function() {
    var dateFormat = "dd/mm/yy",
        from = $("#fromvalidated")
            .datepicker({
                dateFormat: 'dd/mm/yy',
                changeMonth: true,
                changeYear: true,
                maxDate: '0',
                minDate: '-10Y'
            })
            .on("change", function() {
                if ($('#selectedProjectGroup').val() === null || $('#selectedProjectGroup').val().length === 0) {
                    swal({
                        
                        text: 'Kindly select the group',
                        
                    });
                    $(this).val(''); // Clear the date if no group is selected
                    return;
                }
                to.datepicker("option", "minDate", getDate(this));
            }),
        to = $("#tovalidated")
            .datepicker({
                dateFormat: 'dd/mm/yy',
                changeMonth: true,
                changeYear: true,
                maxDate: '0',
                minDate: '-10Y'
            })
            .on("change", function() {
                if ($('#selectedProjectGroup').val() === null || $('#selectedProjectGroup').val().length === 0) {
                    swal({
                       
                        text: 'Kindly select the group',
                       
                    });
                    $(this).val(''); // Clear the date if no group is selected
                    return;
                }
                from.datepicker("option", "maxDate", getDate(this));
            });
    
    $('.go-btnvalidate').click(function() {
    	
    	var opts = $('#lstBox2' + ' option');
        
        
       
        var groupselected = $("#selectedProjectGroup").val();
        var startRange = $("#fromvalidated").val();
        var endRange = $("#tovalidated").val();
        
       if (!groupselected || groupselected.length === 0) {
            sweetSuccess('Attention', 'Select the group');
            return false;
        }

        // Check if startRange is not selected
        if (!startRange) {
            sweetSuccess('Attention', 'Select Start Range');
            return false;
        }
        
        if (startRange && !endRange) {
            sweetSuccess('Attention', 'Select the To Range');
            return false;
        }
        
        if (groupselected && startRange && endRange &&(opts.length==0)) {
            sweetSuccess('Select the Available Project field');
            return false;
        }

        var selectedRange = startRange;
        if (endRange) {
            selectedRange = selectedRange + ' to ' + endRange;
        }
        
        getAllProjectsDetails();
    });




    /*$('.go-btnvalidate').click(function() {
    	var groupselected = $("#selectedProjectGroup").val();
        var startRange = $("#fromvalidated").val();
        var endRange = $("#tovalidated").val();
        if (!startRange) {
            sweetSuccess('Attention', 'Please select Start Range');
            return false;
        }
        var selectedRange = startRange;
        if (endRange) {
            selectedRange = selectedRange + ' to ' + endRange;
        }
        getAllProjectsDetails(flag);
        
        
    });*/

    function getDate(element) {
        var date;
        try {
            date = $.datepicker.parseDate(dateFormat, element.value);
        } catch (error) {
            date = null;
        }
        return date;
    }
});


function formatToIndianCurrency(number) {
    // Convert number to string and split it into parts for commas
    var parts = number.toString().split('.');
    parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ',');

    // Add Indian Rupee symbol and return
    return '₹' + parts.join('.');
}

/*document.getElementById('btnRight').addEventListener('click', function() {
    var listbox = document.getElementById('lstBox1');
    var selected = listbox.selectedOptions.length > 0;

    if (!selected) {
        alert('Kindly select the available field');
    } else {
        // Implement the logic to move the selected items to another list box if required
        console.log('Selected items:', listbox.selectedOptions);
    }
});*/

function getAllProjectsDetails() {
     
	var startDate = $("#fromvalidated").val();
	var endDate = $("#tovalidated").val();
    var groupIds = $("#selectedProjectGroup").val();
  
    groupIds = groupIds.join(',');
    console.log(groupIds); // Output the selected group IDs
    var opts = $('#lstBox2' + ' option');
    
    
    if (opts.length != 0) {
        $('#btnAllLeft').click();
    }
    
    
   
    
    $(".hidetable").addClass("hidden");
    

     var tableData = '';
     var table;
    $.ajax({
        type: "POST",
        url: "/PMS/mst/viewAllsProject",
        data: {
            "groupIds": groupIds,
        	"startDate":startDate,
			"endDate":endDate
        },
      
        success: function(response) {
			$('#AllviewProject_datatable').DataTable().clear().destroy();
            // Handle success response here
             // console.log("what is response"+ response);

			for (var i = 0; i < response.length; i++) {
			    var row = response[i];    
			    console.log(row);
			    var strReferenceNumber = row.projectRefrenceNo;
			    if (!strReferenceNumber) {
			        strReferenceNumber = '';
			    }
			    
			    var rowColor = "black";  // update the row color for closed projects [07-03-2024]

			    //var clickFunction = "viewProjectDetails('/PMS/projectDetails/" + row.encProjectId + "')";
			    tableData += '<tr style="color:' + rowColor + '">';
			    tableData += '<td class="hidden Serial font_14 text-center">' + (i + 1) + '</td>';
			    tableData += '<td class="hidden ProjectName"><a style="color:' + rowColor + '" class="font_14">'
			    + (row.strProjectName !== null ? row.strProjectName : '') + ' </a></td>';   
			    tableData += '<td class="hidden ReferenceNo font_14 text-center">' + (row.projectRefrenceNo !== null ? row.projectRefrenceNo : '') + '</td>';
			    tableData += '<td class="hidden ClientName font_14 text-center">' + row.clientName + '</td>';    
			    tableData += '<td class="hidden ProjectManager font_14 text-center">' + (row.strPLName !== null ? row.strPLName : '') + '</td>';
			    tableData += '<td class="hidden StartDate font_14 text-center">' + (row.startDate !== null ? row.startDate : '') + '</td>';                
			    tableData += '<td class="hidden EndDate font_14 text-center">' + (row.endDate !== null ? row.endDate : '') + '</td>';
			    tableData += '<td class="hidden WorkOrder font_14 text-center">' + (row.workOrderDate !== null ? row.workOrderDate : '') + '</td>';
			    tableData += '<td class="hidden MouDate font_14 text-center">' + (row.mouDate !== null ? row.mouDate : '') + '</td>';
			    tableData += '<td class="hidden ProjectType font_14 text-center">' + (row.projectType !== null ? row.projectType : '') + '</td>';
			    tableData += '<td class="hidden ProjectCategory font_14 text-center">' + (row.projectCategory !== null ? row.projectCategory : '') + '</td>';
			    tableData += '<td class="hidden ThrustArea font_14 text-center">' + (row.thrustAreas !== null ? row.thrustAreas : '') + '</td>';
			   // tableData += '<td class="hidden ProjectCost font_14 text-center">' + row.projectCost + '</td>';
			    tableData += '<td class="hidden ProjectCost font_14 text-center">' + formatToIndianCurrency(row.projectCost) + '</td>';
			    tableData += '<td class="hidden ProjectDuration font_14 text-center">' + row.projectDuration + '</td>';
			    tableData += '<td class="hidden TotalOutlay font_14 text-center">' +formatToIndianCurrency(row.totalOutlay) + '</td>';
			    tableData += '<td class="hidden ProjectDescription font_14 text-center">' + (row.description !== null ? row.description : '') + '</td>';
			    tableData += '<td class="hidden ProjectObjective font_14 text-center">' + (row.strProjectObjective !== null ? row.strProjectObjective : '') + '</td>';
			    tableData += '<td class="hidden ProjectAim font_14 text-center">' + (row.strProjectAim !== null ? row.strProjectAim : '') + '</td>';
			   // tableData += '<td class="hidden ProjectRemarks font_14 text-center">' + (row.strProjectRemarks !== null ? row.strProjectRemarks : '') + '</td>';
			    tableData += '<td class="hidden ValidationRemarks font_14 text-center">' + (row.strValidatedRemarks !== null ? row.strValidatedRemarks : '') + '</td>';
			    tableData += '<td class="hidden ValidationStatus font_14 text-center">' + (row.strValidateStatus !== null ? row.strValidateStatus : '') + '</td>';
			    tableData += '<td class="hidden ProjectStatus font_14 text-center">' + row.strProjectStatus + '</td>';
			    tableData += '<td class="hidden ProjectClosureStatus font_14 text-center">' + (row.closureStatus !== null ? row.closureStatus : '') + '</td>';
			    // Adding collaborativeOrgDetailsModels data
			    var emails = '';
			    var contactNumbers = '';
			    var organisationNames = '';
			    var organisationAddresses = '';
			    var websites = '';

			    if (row.collaborativeOrgDetailsModels && row.collaborativeOrgDetailsModels.length > 0) {
			        for (var j = 0; j < row.collaborativeOrgDetailsModels.length; j++) {
			            var collabDetail = row.collaborativeOrgDetailsModels[j];
			            if (collabDetail.email) {
			                if (emails !== '') {
			                    emails += ', ';
			                }
			                emails += collabDetail.email;
			            }
			            if (collabDetail.contactNumber) {
			                if (contactNumbers !== '') {
			                    contactNumbers += ', ';
			                }
			                contactNumbers += collabDetail.contactNumber;
			            }
			            if (collabDetail.organisationName) {
			                if (organisationNames !== '') {
			                    organisationNames += ', ';
			                }
			                organisationNames += collabDetail.organisationName;
			            }
			            if (collabDetail.organisationAddress) {
			                if (organisationAddresses !== '') {
			                    organisationAddresses += ', ';
			                }
			                organisationAddresses += collabDetail.organisationAddress;
			            }
			            if (collabDetail.website) {
			                if (websites !== '') {
			                    websites += ', ';
			                }
			                websites += collabDetail.website;
			            }
			        }
			    }

			    // Add collaborativeOrgDetailsModels data to the table
			    tableData += '<td class="hidden EmailCollaborative font_14 text-center">' + emails + '</td>';
			    tableData += '<td class="hidden ContactNumber font_14 text-center">' + contactNumbers + '</td>';
			    tableData += '<td class="hidden OrganisationName font_14 text-center">' + organisationNames + '</td>';
			    tableData += '<td class="hidden OrganisationAddress font_14 text-center">' + organisationAddresses + '</td>';
			    tableData += '<td class="hidden Website font_14 text-center">' + websites + '</td>';

			    tableData += '</tr>';
			}

					
						$('#AllviewProject_datatable tbody').append(tableData); 
						//alert("hello flag 3");
				
						 
						
						  
						  
						 // setTimeout(setReset, 2000);
				table = $('#AllviewProject_datatable').DataTable({
                dom: 'Bfrtip',
                "ordering": false,
                "paging": false,
                
         buttons: [
        {
            extend: 'excelHtml5',
            text: 'Export Excel',
            exportOptions: {
                columns: ':visible' // Export only visible columns
            },
            
           
          customize: function(xlsx) {
                var sheet = xlsx.xl.worksheets['sheet1.xml'];
                var styles = xlsx.xl['styles.xml'];

                // Define the new border style
                var borderXML = `
                    <border>
                        <left style="thin"><color auto="1"/></left>
                        <right style="thin"><color auto="1"/></right>
                        <top style="thin"><color auto="1"/></top>
                        <bottom style="thin"><color auto="1"/></bottom>
                    </border>`;

                // Append the border style to the borders list
                var borders = $('borders', styles);
                if (borders.length === 0) {
                    // If no borders section exists, create one
                    $('styleSheet', styles).append('<borders count="1"><border/></borders>');
                    borders = $('borders', styles);
                }
                var borderCount = $('border', borders).length;
                borders.append(borderXML);
                borders.attr('count', borderCount + 1);

                var wrapBorderStyleXML = `<xf numFmtId="0" fontId="0" fillId="0" borderId="${borderCount}" applyAlignment="1" applyBorder="1">
                    <alignment wrapText="1"/>
                </xf>`;
                var cellXfs = $('cellXfs', styles);
                var wrapBorderStyleIndex = $('xf', cellXfs).length;
                cellXfs.append(wrapBorderStyleXML);
                cellXfs.attr('count', wrapBorderStyleIndex + 1);

                var boldFontXML = `
                    <font>
                        <b/>
                    </font>`;
                var fonts = $('fonts', styles);
                if (fonts.length === 0) {
                    // If no fonts section exists, create one
                    $('styleSheet', styles).append('<fonts count="1"><font/></fonts>');
                    fonts = $('fonts', styles);
                }
                var fontCount = $('font', fonts).length;
                fonts.append(boldFontXML);
                fonts.attr('count', fontCount + 1);

                // Define the bold style with the new font, wrap text, and border
                var boldWrapBorderStyleXML = `<xf numFmtId="0" fontId="${fontCount}" fillId="0" borderId="${borderCount}" applyFont="1" applyAlignment="1" applyBorder="1">
                    <alignment wrapText="1" horizontal="center"/>
                </xf>`;
                var boldWrapBorderStyleIndex = $('xf', cellXfs).length;
                cellXfs.append(boldWrapBorderStyleXML);
                cellXfs.attr('count', boldWrapBorderStyleIndex + 1);

                // Ensure all cells in the sheet are defined
                var maxRow = Math.max(...$('row', sheet).map(function() { return parseInt($(this).attr('r'), 10); }).get());
                var maxCol = Math.max(...$('row c', sheet).map(function() {
                    return $(this).attr('r').replace(/[0-9]/g, '').charCodeAt(0) - 'A'.charCodeAt(0) + 1;
                }).get());

                for (var r = 1; r <= maxRow; r++) {
                    var row = $('row[r="' + r + '"]', sheet);
                    if (row.length === 0) {
                        sheet.append('<row r="' + r + '"></row>');
                        row = $('row[r="' + r + '"]', sheet);
                    }
                    for (var c = 1; c <= maxCol; c++) {
                        var cellRef = String.fromCharCode('A'.charCodeAt(0) + c - 1) + r;
                        if ($('c[r="' + cellRef + '"]', row).length === 0) {
                            row.append('<c r="' + cellRef + '"/>');
                        }
                    }
                }

                // Apply the wrap and border style to all cells
                $('row c', sheet).attr('s', wrapBorderStyleIndex);

                // Apply the bold wrap and border style to the header row (row 1 and 2)
                $('row[r="1"] c', sheet).attr('s', boldWrapBorderStyleIndex);
                $('row[r="2"] c', sheet).attr('s', boldWrapBorderStyleIndex);
            }






            
   
     
        },
        'print'
    ],
               
                "columnDefs": [{
                    "orderable": false,
                    "targets": 0
                }],
                "order": [
                    [1, 'asc']
                ]
            });

 },
        
        	
        error: function(xhr, status, error) {
            // Handle error response here
            console.error("Error:", status, error);
        }
    });
  
}


var hiddenstr = "";
var hiddenarr =[];
var removestr="";
var removearr =[];
function hiddentable(){
	
	
     $("#hiddenInputField").val( $("#lstBox1").val());
     hiddenstr = $("#hiddenInputField").val();
    
     //console.log("str" + str);
     hiddenarr = hiddenstr.split(',')

}

function hiddenremovetable(){
	
	
     $("#hiddenField").val( $("#lstBox2").val());
      removestr = $("#hiddenField").val();
     //console.log("strss" + hidestr);
     removearr = removestr.split(',')

     
}

$(document).ready(function(){
	$.fn.moveToListAndDelete = function (sourceList, destinationList) {
		
	    var opts = $(sourceList + ' option:selected');
	    if (opts.length == 0) {
	    	 swal(" Select The Project Field !");
	    }
 
	    $(opts).remove();
	    $(destinationList).append($(opts).clone());
	};
});

$(document).ready(function(){
$.fn.moveAllToListAndDelete = function (sourceList, destinationList) {
    var opts = $(sourceList + ' option');
    if (opts.length == 0) {
    	swal(" Select The Project Field !");
    }

    $(opts).remove();
    $(destinationList).append($(opts).clone());
};
});

$('#btnRight').click(function (e) {
   
   
   
   if($("#selectedProjectGroup").val()!=0){
	    $('select').moveToListAndDelete('#lstBox1', '#lstBox2');
	    //$('#lstBox1').trigger('change');
    $('#lstBox2').trigger('change');
    
var opts = $('#lstBox2' + ' option');
    
    
    if (opts.length==0) {
    	//console.log("hide the table ")
    	   $("#allviewprojectdiv").addClass("hidden");
    
    }
    else{
    	 $("#allviewprojectdiv").removeClass("hidden");
    }
    
    	
    
   
	 
 
   // $(".hidetable").addClass("hidden"); // Assuming you want to hide all columns first

    // Show selected columns based on the value of #hiddenInputField
    for(let i = 0; i<hiddenarr.length; i++){
	


    // Show selected columns based on the value of #hiddenInputField
    var selectedColumn = hiddenarr[i];
   
    
     if(selectedColumn == "Serial") {
        $(".Serial").removeClass("hidden");
    }
    
    
    if(selectedColumn == "ProjectName") {
        $(".ProjectName").removeClass("hidden");
    }
    
    if(selectedColumn == "ReferenceNo") {
        $(".ReferenceNo").removeClass("hidden");
    }
    
     if(selectedColumn == "ClientName") {
        $(".ClientName").removeClass("hidden");
    }  
    
     if(selectedColumn == "ProjectManager") {
        $(".ProjectManager").removeClass("hidden");
    }  
    
    if(selectedColumn == "StartDate") {
        $(".StartDate").removeClass("hidden");
    } 
     if(selectedColumn == "EndDate") {
        $(".EndDate").removeClass("hidden");
    } 
     if(selectedColumn == "WorkOrder") {
        $(".WorkOrder").removeClass("hidden");
    } 
    
     if(selectedColumn == "MouDate") {
        $(".MouDate").removeClass("hidden");
    } 
     
     if(selectedColumn == "ProjectType") {
         $(".ProjectType").removeClass("hidden");
     } 
     
     if(selectedColumn == "ProjectCategory") {
         $(".ProjectCategory").removeClass("hidden");
     } 
     
     if(selectedColumn == "ThrustArea") {
         $(".ThrustArea").removeClass("hidden");
     } 
    
     if(selectedColumn == "ProjectCost") {
        $(".ProjectCost").removeClass("hidden");
    } 
     if(selectedColumn == "ProjectDuration") {
        $(".ProjectDuration").removeClass("hidden");
    } 
     if(selectedColumn == "TotalOutlay") {
        $(".TotalOutlay").removeClass("hidden");
    } 
    
     if(selectedColumn == "ProjectDescription") {
        $(".ProjectDescription").removeClass("hidden");
    } 
    
     if(selectedColumn == "ProjectObjective") {
        $(".ProjectObjective").removeClass("hidden");
    } 
    
    if(selectedColumn == "ProjectAim") {
        $(".ProjectAim").removeClass("hidden");
    } 
    
   
    
      if(selectedColumn == "ValidationRemarks") {
        $(".ValidationRemarks").removeClass("hidden");
    } 
    
     if(selectedColumn == "ValidationStatus") {
        $(".ValidationStatus").removeClass("hidden");
    } 
    
     if(selectedColumn == "ProjectStatus") {
        $(".ProjectStatus").removeClass("hidden");
    }
     
     if(selectedColumn == "ProjectClosureStatus") {
         $(".ProjectClosureStatus").removeClass("hidden");
     }
     
     if(selectedColumn == "EmailCollaborative") {
         $(".EmailCollaborative").removeClass("hidden");
     }
     
     if(selectedColumn == "ContactNumber") {
         $(".ContactNumber").removeClass("hidden");
     }
     
     if(selectedColumn == "OrganisationName") {
         $(".OrganisationName").removeClass("hidden");
     }
     
     if(selectedColumn == "OrganisationAddress") {
         $(".OrganisationAddress").removeClass("hidden");
     }
     
     if(selectedColumn == "Website") {
         $(".Website").removeClass("hidden");
     }
     
     
 }
  }
   else{
	   swal("Select The Group !");	  
   }
    e.preventDefault();
});

   $('#btnLeft').click(function (e) {
	  
	   //setFlag(5);
	  //setReset();
    $('select').moveToListAndDelete('#lstBox2', '#lstBox1');
    //$('#hiddenField').val(''); 
    $('#lstBox1').trigger('change');
    //$('#lstBox2').trigger('change');
    //console.log("list box1" +  $('#lstBox1').val());
    //$("#allviewprojectdiv").addClass("hidden");
    //console.log("list box2" +  $('#lstBox2').val());
 var opts = $('#lstBox2' + ' option');
    
    
    if (opts.length==0) {
    	   $("#allviewprojectdiv").addClass("hidden");
    }
 
     for(let i = 0; i<removearr.length; i++){
	
	 

    // Show selected columns based on the value of #hiddenInputField
    var selectedColumnleft = removearr[i];
    
    if(selectedColumnleft=="Serial"){
		  $(".Serial").addClass("hidden");
		  
	}
	
	
	 if(selectedColumnleft=="ProjectName"){
		  $(".ProjectName").addClass("hidden");
		  
	}
	 
	 if(selectedColumnleft=="ReferenceNo"){
		  $(".ReferenceNo").addClass("hidden");
		  
	}
	
	 
	 if(selectedColumnleft=="ClientName"){
		  $(".ClientName").addClass("hidden");
		  
	}
	
		 if(selectedColumnleft=="ProjectManager"){
		  $(".ProjectManager").addClass("hidden");
		  
	}
	
	
	 if(selectedColumnleft=="StartDate"){
		  $(".StartDate").addClass("hidden");
		  
	}
	
	
	 if(selectedColumnleft=="EndDate"){
		  $(".EndDate").addClass("hidden");
		  
	}
	
	
	 if(selectedColumnleft=="WorkOrder"){
		  $(".WorkOrder").addClass("hidden");
		  
	}
	
	 if(selectedColumnleft=="MouDate"){
		  $(".MouDate").addClass("hidden");
		  
	}
	 
	 if(selectedColumnleft=="ProjectType"){
		  $(".ProjectType").addClass("hidden");
		  
	}
	 
	 if(selectedColumnleft=="ProjectCategory"){
		  $(".ProjectCategory").addClass("hidden");
		  
	}
	 
	 if(selectedColumnleft=="ThrustArea"){
		  $(".ThrustArea").addClass("hidden");
		  
	}
	
	
	 if(selectedColumnleft=="ProjectCost"){
		  $(".ProjectCost").addClass("hidden");
		  
	}
	
	
	 if(selectedColumnleft=="ProjectDuration"){
		  $(".ProjectDuration").addClass("hidden");
		  
	}
	
	
	 if(selectedColumnleft=="TotalOutlay"){
		  $(".TotalOutlay").addClass("hidden");
		  
	}
	
	if(selectedColumnleft=="ProjectDescription"){
		  $(".ProjectDescription").addClass("hidden");
		  
	}
	
		if(selectedColumnleft=="ProjectObjective"){
		  $(".ProjectObjective").addClass("hidden");
		  
	}
	
	if(selectedColumnleft=="ProjectAim"){
		  $(".ProjectAim").addClass("hidden");
		  
	}
	
	
	
	if(selectedColumnleft=="ValidationRemarks"){
		  $(".ValidationRemarks").addClass("hidden");
		  
	}
	
	if(selectedColumnleft=="ValidationStatus"){
		  $(".ValidationStatus").addClass("hidden");
		  
	}
	
	if(selectedColumnleft=="ProjectStatus"){
		  $(".ProjectStatus").addClass("hidden");
		  }
	
	if(selectedColumnleft=="ProjectClosureStatus"){
		  $(".ProjectClosureStatus").addClass("hidden");
		  }
	
	if(selectedColumnleft=="EmailCollaborative"){
		  $(".EmailCollaborative").addClass("hidden");
		  }
	
	if(selectedColumnleft=="ContactNumber"){
		  $(".ContactNumber").addClass("hidden");
		  }
	
	if(selectedColumnleft=="OrganisationName"){
		  $(".OrganisationName").addClass("hidden");
		  }
	
	if(selectedColumnleft=="OrganisationAddress"){
		  $(".OrganisationAddress").addClass("hidden");
		  }
	
	if(selectedColumnleft=="Website"){
		  $(".Website").addClass("hidden");
		  }
   
    }
     e.preventDefault();
});

$('#btnAllRight').click(function (e) {

	
	if($("#selectedProjectGroup").val()!=0){
 //$("#AllviewProject_datatable").removeClass("table-striped table-bordered").addClass("table-responsive");
    $('select').moveAllToListAndDelete('#lstBox1', '#lstBox2');
    $('#lstBox2').trigger('change');
       $("#allviewprojectdiv").removeClass("hidden");
		  $(".Serial").removeClass("hidden");
		  $(".ProjectName").removeClass("hidden");
		  $(".ReferenceNo").removeClass("hidden");
		  
		   $(".ClientName").removeClass("hidden");
		  $(".ProjectManager").removeClass("hidden");
		  $(".StartDate").removeClass("hidden");
		  $(".EndDate").removeClass("hidden");
		  $(".WorkOrder").removeClass("hidden");
		  $(".MouDate").removeClass("hidden");
		  $(".ProjectType").removeClass("hidden");
		  $(".ProjectCategory").removeClass("hidden");
		  $(".ThrustArea").removeClass("hidden");
		  $(".ProjectCost").removeClass("hidden");
		  $(".ProjectDuration").removeClass("hidden");
		  $(".TotalOutlay").removeClass("hidden");
		  $(".ProjectDescription").removeClass("hidden");
		  $(".ProjectObjective").removeClass("hidden");
		  $(".ProjectAim").removeClass("hidden");
		
		  $(".ValidationRemarks").removeClass("hidden");
		  $(".ValidationStatus").removeClass("hidden");
          $(".ProjectStatus").removeClass("hidden");
          $(".ProjectClosureStatus").removeClass("hidden");
          $(".EmailCollaborative").removeClass("hidden");
          $(".ContactNumber").removeClass("hidden");
          $(".OrganisationName").removeClass("hidden");
          $(".OrganisationAddress").removeClass("hidden");
          $(".Website").removeClass("hidden");
	  }
    else{	
		swal("Select The Group !");
	}
	e.preventDefault();
});

$('#btnAllLeft').click(function (e) {
	if($("#selectedProjectGroup").val()!=0){
    $('select').moveAllToListAndDelete('#lstBox2', '#lstBox1');
    $('#lstBox2').trigger('change');
     $("#allviewprojectdiv").addClass("hidden");
    	  $(".Serial").addClass("hidden");
		  $(".ProjectName").addClass("hidden");
		  
		  $(".ReferenceNo").addClass("hidden");
		  $(".ClientName").addClass("hidden");
		  $(".ProjectManager").addClass("hidden");
		  $(".StartDate").addClass("hidden");
		  $(".EndDate").addClass("hidden");
		  $(".WorkOrder").addClass("hidden");
		  $(".MouDate").addClass("hidden");
		  $(".ProjectType").addClass("hidden");
		  $(".ProjectCategory").addClass("hidden");
		  $(".ThrustArea").addClass("hidden");
		  $(".ProjectCost").addClass("hidden");
		  $(".ProjectDuration").addClass("hidden");
		  $(".TotalOutlay").addClass("hidden");
		  $(".ProjectDescription").addClass("hidden");
		  $(".ProjectObjective").addClass("hidden");
		  $(".ProjectAim").addClass("hidden");
		  
		  $(".ValidationRemarks").addClass("hidden");
		  $(".ValidationStatus").addClass("hidden");
		  $(".ProjectStatus").addClass("hidden");
		  $(".ProjectClosureStatus").addClass("hidden");
		  $(".EmailCollaborative").addClass("hidden");
		  $(".ContactNumber").addClass("hidden");
		  $(".OrganisationName").addClass("hidden");
		  $(".OrganisationAddress").addClass("hidden");
		  $(".Website").addClass("hidden");
	}
	else{
		swal("Select The Group !");
	}
    e.preventDefault();
});

function checkGroups(){
	if ($('input.group_check').is(':checked')) {
		$('#selectedProjectGroup option').prop('selected', true);
		$("#selectedProjectGroup").trigger('change');
	}
	else{
		$("#selectedProjectGroup").val('');
		$("#selectedProjectGroup").trigger('change');
	}
}



