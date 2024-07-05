<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.time.temporal.WeekFields"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.time.DayOfWeek"%>
<%@page import="java.time.LocalDate"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
<!--   <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css"> -->
  <!-- Select2 CSS files for Searching -->
  <link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.13/css/select2.min.css" rel="stylesheet" />
  <!-- Custom CSS files -->
  <link rel="stylesheet" href="<c:url value="/resources/app_srv/PMS/timesheet/css/styleReport.css"></c:url>">
  <title>Timesheet</title>
</head>
<body>

	
	<section id="main-content" class="main-content merge-left">
		<div class=" container wrapper1">
			<div>
				<header style="display: flex; justify-content:center">
					<h1 class="timesheetTitle" style="text-shadow: 2px 0px 2px #1e2787; padding-bottom: 20px;">
					<span style="color: #173faa;">R</span><span style="color: #1578c2">eports</span>
					</h1>
					<input type="hidden" id="roleIdLoggedUser" value="${roleIdOfLoggedUser }"/>
				</header>
		  	</div>
		</div>
		
		<div class="container">
		<div class="chart-container">
		<!-- R0 - Radio button for user wise & group wise -->
		<div style="display: flex;justify-content: center;">
			<span style="font-weight: 600; font-size: 16px; font-size: large">Select an option: </span>
		    <label style="margin-left: 50px;color:#c8751f;font-size: large;">
		      <input id="userWise" type="radio" name="reportType" value="userWise" checked onclick="showUserWise()">
		      Employee Wise
		    </label>
		
		    <label style="margin-left: 50px;color:#c8751f;font-size: large;">
		      <input id="groupWise" type="radio" name="reportType" value="groupWise" onclick="showGroupWise()">
		      Project Wise
		    </label>
		 </div>
		 <input type="hidden" id="yearToShow" name="yearToShow" value="${yearToShow}"></input>
		 <div id="userWiseUI" class="user-wise">
		 	<div style="background-color: #e6faff"; class="container hidden"><h3 style="padding:13px;">Employee Wise</h3></div>
		    <!-- R1 - Finding Employee details under GC -->
			<form id="selectEmployee" action="employeeSelectReport" method="post" style="display: flex; flex-wrap: wrap; justify-content: center;">
				
				<div class="form-group" style="margin-top: 30px; margin-right: 20px">
					<label style="font-size: 18px;" for="selectedEmpIdName">Employee: </label>
					<select id="selectedEmpIdName" class="select2" name="selectedEmpIdName" onchange="submitEmployeeSelectForm()" style="width: 200px; margin-right: 10px;">
						
						<c:choose>
						    <c:when test="${empty selectedEmpName}">
						        <option value="">Select Employee</option>
						    </c:when>
						    <c:otherwise>
						        <option value="${selectedEmpId}+${selectedEmpName}">${selectedEmpName}</option>
						    </c:otherwise>
						 </c:choose>
					    <c:if test="${not empty EmpNames}">
						    <c:forEach var="index" begin="0" end="${fn:length(EmpNames) - 1}">
						        <%-- <input type="hidden" name="selectedEmpName" value="${EmpNames[index]}"/> --%>
						        <option value="${EmpIds[index]}+${EmpNames[index]}">${EmpNames[index]}</option>
						    </c:forEach>
						</c:if>
						<c:if test="${empty EmpNames}">
						    <option value="" disabled>No employees available</option>
						</c:if>
					</select>
				</div>
				<div class="form-group" style="margin-top: 30px;">
			             <label style="font-size: 18px;" for="selectedYear">Calendar Year:</label>
						 <select id="selectedYear" class="select2" onchange="submitEmployeeSelectForm()" name="selectedYear">
						 	<c:choose>
									    <c:when test="${empty currentYear}">
									        <option>${selectedYear}</option>
									    </c:when>
									    <c:otherwise>
									        <option>${currentYear}</option>
									    </c:otherwise>
							 </c:choose>
						 	<c:forEach var="year" items="${comboYear }">
								<option value="${year }">${year }</option>
							</c:forEach>
						 </select>
				</div>
			</form>
			<c:choose>
				<c:when test="${empty selectedEmpName}">
					<div class="text-center"><img alt="selectEmpLogo" src="<c:url value="/resources/app_srv/PMS/timesheet/image/SelectEmp.png"></c:url>"></div>
				</c:when>
				<c:otherwise>
				
						<!-- R3 & R9/R4   -    LOGGED IN USER MONTH WISE & PROJECT(Pie)/ACTIVITY WISE REPORT -->
						<input type="hidden" id="xValues1" name="xValues1" value="${xValues1 }"></input>
						<input type="hidden" id="yValues1" name="yValues1" value="${yValues1 }"></input>
						<input type="hidden" id="xValues1_1" name="xValues1_1" value="${xValues1_1 }"></input>
						<input type="hidden" id="yValues1_1" name="yValues1_1" value="${yValues1_1 }"></input>
						
						<div class="row" style="display: flex;justify-content: center;">
							<!-- <div class="chart-heading">Logged In User Project Wise Report</div> -->
							<div class="col-md-12 chart"><canvas id="reportUserMonthWiseEffortColoumnChart"></canvas></div>
							
						</div>
			
						<div class="row">
							<div class="row" style="display: flex; align-items: center;">
								<div class="col-md-12">
									<label for="calenderSelectedMonth"style="margin-left: 10px; font-size: 16px;">Select Month:</label>
									 <select class="monthList select2" name="reportSelectedMonth" id="selectedMonth" style="width: 150px; max-height: 30px; font-weight: bold;">
										<option value="AllMonths">All Months</option>
									 	<c:forEach var="monthName" items="${monthNameList }">
											<option value="${monthName }">${monthName }</option>
										</c:forEach>
									 </select>
								</div>	
							</div>
							<div class="row" style="display: flex;justify-content: center; margin-top: 23px;">
								<%-- <input type="hidden" id="xValues5" name="xValues5" value="${xValues5}"></input>
								<input type="hidden" id="yValues5" name="yValues5" value="${yValues5 }"></input> --%>
								
								<%-- <div class="col-md-6 chart" ><canvas id="reportUserProjectWiseEffortPieChart"></canvas></div> --%>
								<div class="col-md-6 chart" ><div id="reportUserProjectWiseEffortPieChart" style="width: 700px; height: 500px;"></div></div>
								
								<%-- <input type="hidden" id="xValues3" name="xValues3" value="${xValues3}"></input>
								<input type="hidden" id="yValues3" name="yValues3" value="${yValues3 }"></input> --%>
								
								<%-- <div class="col-md-6 chart"><canvas id="reportUserActivityWiseEffortColumnChart"></canvas></div> --%>
								<div class="col-md-6 chart"><div id="reportUserActivityWiseEffortColumnChart" style="width: 700px; height: 500px;"></div></div>
							</div>
						</div>
				</c:otherwise>
			</c:choose>
		 </div>
		
		 <div id="groupWiseUI" class="group-wise">
		    <div style="background-color: #e6faff"; class="container hidden"><h3 style="padding:13px;">Project Wise</h3></div>
		    	<form id="selectGroupYear" action="selectGroupYear" method="post">
					<div style=" margin-top: 30px;" class="form-group">
				             <label style="font-size: 18px;" for="selectGroupYear">Calendar Year:</label>
							 <select id="selectGroupYear1" class="select2" onchange="submitSelectGroupYearForm()" name="selectGroupYear">
							 	<c:choose>
										    <c:when test="${empty currentYear}">
										        <option>${selectedYear}</option>
										    </c:when>
										    <c:otherwise>
										        <option>${currentYear}</option>
										    </c:otherwise>
								 </c:choose>
							 	<c:forEach var="year" items="${comboYearGroup }">
									<option value="${year }">${year }</option>
								</c:forEach>
							 </select>
					</div>
					<input type="hidden" id="isGroupFormSubmitRe" name="isGroupFormSubmitRe" value="${isGroupFormSubmitRe }"/>
					<input type="hidden" id="roleIdLoggedUser" value="${roleIdOfLoggedUser }"/>
					
					<div id="Groupdiv" style="margin-top: 30px;" class="form-group ">
				        <label style="font-size: 18px;" for="groupSelection">Group Name:</label>
				       	<input type="hidden" id="groupSelectionValue" value="${groupSelection }"/>
				        <select id="groupSelection" class="selectgroupnames" onchange="submitSelectGroupYearForm()" name="groupSelection">
				        <option value="">Select Group</option>
				            <c:choose>
				                <c:when test="${not empty groupOrganisation}">
				                    <c:forEach items="${groupOrganisation}" var="group">
				                        <option value="${group.numId}">${group.groupName}</option>
				                    </c:forEach>
				                </c:when>
				                <c:otherwise>
				                    <option value="">No groups available</option>
				                </c:otherwise>
				            </c:choose>
				        </select>
				    </div>
				</form>
			    
				<!-- R10	-	GROUP MONTH WISE REPORT (PROJECT + ACTIVITY)  -->
				<input type="hidden" id="xValuesProject" name="xValuesProject" value="${xValuesProject }"></input>
				<input type="hidden" id="yValuesProject" name="yValuesProject" value="${yValuesProject }"></input>
				<input type="hidden" id="xValuesActivity" name="xValuesActivity" value="${xValuesActivity }"></input>
				<input type="hidden" id="yValuesActivity" name="yValuesActivity" value="${yValuesActivity }"></input>
							
			
					<!-- <div class="chart-heading">Logged In User Project Wise Report</div> -->
					
						
				<div class="row" style="display: flex;justify-content: center;">
					<!-- <div class="chart-heading">Logged In User Project Wise Report</div> -->
					<div class="col-md-12 chart"><canvas id="reportProjectActivityGroupMonthWiseEffortColumnChart"></canvas></div>
				</div>
				
				<!-- R11/R12   -    GROUP PROJECT/ACTIVITY-MONTH WISE PIE CHART -->
					<div class="row">
						
						<div class="row" style="display: flex; align-items: center;">
							<div class="col-md-12">
							<label for="calenderSelectedMonth"style="margin-left: 10px; font-size: 16px;">Select Month:</label>
								 <select class="monthList select2" name="calenderSelectedMonth" id="selectedMonthGroup" style="width: 150px; max-height: 30px; font-weight: bold;">
									<option value="AllMonths">All Months</option>
								 	<c:forEach var="monthName" items="${monthNameList }">
										<option value="${monthName }">${monthName }</option>
									</c:forEach>
								 </select>
							</div>
						</div>
						<div class="row" style="display: flex;justify-content: center; margin-top: 23px;">
							<%-- <div class="col-md-6 chart" ><canvas id="reportProjectGroupWiseEffortPieChart"></canvas></div>
							<div class="col-md-6 chart" ><canvas id="reportActivityGroupWiseEffortPieChart"></canvas></div> --%>
							<div class="col-md-6 chart" ><div id="reportProjectGroupWiseEffortPieChart" style="width: 700px; height: 500px;"></div></div>
							<div class="col-md-6 chart" ><div  id="reportActivityGroupWiseEffortPieChart" style="width: 700px; height: 500px;"></div></div>
						</div>	
					</div>
		 	</div>
		</div>
		</div>
		
	</section>
	
	<!-- JS files for Charts -->
	<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/chartjs-plugin-datalabels"></script>
	<!-- JS files for Google Charts -->
	<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
	<!-- Select2 JS files for Searching -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.13/js/select2.min.js"></script>
	<!-- Custom JS files -->
	<script src="<c:url value="/resources/app_srv/PMS/timesheet/js/scriptReport.js"></c:url>"></script>
	<!-- Custom Internal JS -->
	<script type="text/javascript">
		$(document).ready(function () {
		    	
			if($("#roleIdLoggedUser").val() == 9|| $("#roleIdLoggedUser").val() == 6){
				$("#Groupdiv").removeClass("hidden");
				
			}else{
				
				$("#Groupdiv").addClass("hidden");
				
			}
	    	
	 	});
		
		/* $(document).ready(function () {
	    	
	    	activityGroupwisemonthpie();
	    	
	 	});
		$(document).ready(function () {
	    	
	    	projectGroupwisemonthpie();
	    	
	 	}); */
		
	 	//First time pie chart was coming small size so we commented above line from 371 - 380 and added below code 383 - 386
		$("#groupWise").click(function() {
			$("#groupSelection").val($("#groupSelectionValue").val());
	        projectGroupwisemonthpie();
	        activityGroupwisemonthpie();
	    });
		
		//R11 - GROUP PROJECT-MONTH WISE PIE CHART
		$('#selectedMonthGroup').on('change', function () {
	 		projectGroupwisemonthpie();
		 });
		//PREVIOUS CODE BY USING CHART.JS, NOW WE SHIFTED TO GOOGLE CHARTS
	 	/* function projectGroupwisemonthpie(){
	 		var existingChart = Chart.getChart('reportProjectGroupWiseEffortPieChart');
	
	 		if (existingChart) {
	 		  existingChart.destroy();
	 		}
	
	 		var selectGroupYear= $('#selectGroupYear1').val();
	 		//var selectedEmpIdName= $('#selectedEmpIdName').val();
	 		var selectedMonthGroup= $('#selectedMonthGroup').val();
	 		var groupSelection = $('#groupSelection').val();
		    var labels = [];
		    var dataInMinutes = [];
	
			//console.log('selectedMonthGroup:', selectedMonthGroup);
			//console.log('selectGroupYear:', selectGroupYear);
			//console.log('groupSelection:', groupSelection);
 			$.ajaxRequest.ajax({
 				type : "POST",
 				url : "/PMS/getMonthWiseProjectGroupPie",
 				data:{
 					"selectGroupYear":selectGroupYear,
 					"selectedMonthGroup":selectedMonthGroup,
 					"groupSelection": groupSelection
 					
 				},
 				success : function(response) {
 					labels = response.xValuesGroupMonth;
 					dataInMinutes = response.yValuesGroupMonth;
 					
 				// Define custom colors for pie chart
 				    var pieColors = [
 				        'rgba(75, 192, 192, 0.5)',
 				        'rgba(255, 99, 132, 0.5)',
 				        'rgba(255, 205, 86, 0.5)',
 				        'rgba(54, 162, 235, 0.5)',
 				        'rgba(255, 159, 64, 0.5)',
 				        'rgba(153, 102, 255, 0.5)',
 				        'rgba(255, 87, 34, 0.5)',
 				        'rgba(219, 112, 147, 0.5)',
 				        // Add more colors as needed
 				    ];
 				   var totalEffort = dataInMinutes.reduce(function (a, b) {
				        return a + b;
				    }, 0);
					  var percentageData = dataInMinutes.map(function (effort) {
					        return ((effort / totalEffort) * 100).toFixed(2);
					    });
 				    // Create a pie chart (change 'type' to 'pie')
 				    var ctx = document.getElementById('reportProjectGroupWiseEffortPieChart').getContext('2d');
 				    var pieChart = new Chart(ctx, {
 				        type: 'pie', // Change type to 'pie'
 				        data: {
 				            labels: labels,
 				            datasets: [{
 				                label: 'Effort',
 				                data: dataInMinutes,
 				                backgroundColor: pieColors, // Use colors directly for pie chart
 				                borderColor: pieColors,
 				                borderWidth: 1,
 				            }],
 				        },
 				        options: {
 				            plugins: {
 				                title: {
 				                    display: true,
 				                    text: 'Project Wise Project Effort Report ('+yearToShow+')',
 				                    font: {
 				                        size: 18,
 				                    },
 				                },
 				                legend: {
 				                    display: false, // Display legend for pie chart
 				                },
 				                tooltip: {
 				                    callbacks: {
 				                        label: function(context) {
 				                            var minutes = context.dataset.data[context.dataIndex];
 				                            var hours = Math.floor(minutes / 60);
 				                            var remainingMinutes = minutes % 60;
 				                           var percentage = percentageData[context.dataIndex];
 				                            return 'Effort: ' + hours + 'h ' + remainingMinutes + 'm' + ' - ' + percentage + '%';
 				                        },
 				                    },
 				                },
 				            },
 				        },
 				    });
 				},
 				error: function(xhr, textStatus, errorThrown) {
 			        console.error("Error occurred: " + textStatus, errorThrown);
 			    }
 				});
	 	} */
		function projectGroupwisemonthpie() {
		
			
        	
		    var selectGroupYear = $('#selectGroupYear1').val();
		    var selectedMonthGroup = $('#selectedMonthGroup').val();
		    var groupSelection = $('#groupSelection').val();
		    
		    $.ajax({
		        type: "POST",
		        url: "/PMS/getMonthWiseProjectGroupPie",
		        data: {
		            "selectGroupYear": selectGroupYear,
		            "selectedMonthGroup": selectedMonthGroup,
		            "groupSelection": groupSelection
		        },
		        success: function(response) {
		            var labels = response.xValuesGroupMonth;
		            var dataInMinutes = response.yValuesGroupMonth;
		          
		            var totalEffort = dataInMinutes.reduce(function(a, b) {
		                return a + b;
		            }, 0);

		            var percentageData = dataInMinutes.map(function(effort) {
		                return ((effort / totalEffort) * 100).toFixed(2);
		            });

		            google.charts.load('current', {
		                'packages': ['corechart']
		            });
		            google.charts.setOnLoadCallback(drawChart);

		            function drawChart() {
	 	                var data = new google.visualization.DataTable();
	 	                data.addColumn('string', 'Project');
	 	                data.addColumn('number', 'Effort');
	 	                data.addColumn({type: 'string', role: 'tooltip', 'p': {'html': true}}); // Add tooltip role

	 	                for (var i = 0; i < labels.length; i++) {
	 	                	var projectCode = labels[i].split(' - ')[0]; // Extracting project code
	 	                    var hours = Math.floor(dataInMinutes[i] / 60);
	 	                    var minutes = dataInMinutes[i] % 60;
	 	                   var tooltipContent =
		 	                	  '<div class="tooltip-content">' +
		 	                     '<strong>' + labels[i] + ':</strong><br>' +
		 	                     'Effort: ' + hours + 'h ' + minutes + 'm<br>' +
		 	                     'Percentage: ' + percentageData[i] + '%' +
		 	                 		'</div>';
	 	                    data.addRow([projectCode, dataInMinutes[i], tooltipContent]);
	 	                }

	 	                var options = {
	 	                	    title: 'Project Effort Report (' + selectGroupYear + ')',
	 	                	   	colors: ['#64B5B5', '#ff99cc', '#d9b3ff', '#4682B4', '#99ccff', '#9966CC', '#e6b3b3', '#CC6699'],
                                titleTextStyle: {
                                   fontSize: 15
                                },
	 	                    pieSliceText: 'percentage',
	 	                    legend: {
		                        position: 'right',
		                        textStyle: {
		                            fontSize: 12 // adjust font size if needed
		                        }
		                    },
	 	                    sliceVisibilityThreshold: 0,
	 	                   tooltip: { isHtml: true }// Hide slices with less than 1% of the total
	 	                };



		                var chart = new google.visualization.PieChart(document.getElementById('reportProjectGroupWiseEffortPieChart'));
		                if (data.getNumberOfRows() === 0) {
					    	data.addRow(['Data is not Filled', 1, 'No Data']); // Add a dummy row to display the custom message
					        options.pieSliceText = 'none'; // Hide slice text
					    }
		                chart.draw(data, options);
		            }

		        },
		        error: function(xhr, textStatus, errorThrown) {
		            console.error("Error occurred: " + textStatus, errorThrown);
		        }
		    });
		}
	 	
	 	//R12 - GROUP ACTIVITY-MONTH WISE PIE CHART 
		$('#selectedMonthGroup').on('change', function () {
			activityGroupwisemonthpie();
		 });
		//PREVIOUS CODE BY USING CHART.JS, NOW WE SHIFTED TO GOOGLE CHARTS
	 	/* function activityGroupwisemonthpie(){
	 		
	 		var existingChart = Chart.getChart('reportActivityGroupWiseEffortPieChart');
	
	 		if (existingChart) {
	 		  existingChart.destroy();
	 		}
	
	 		var selectGroupYear= $('#selectGroupYear1').val();
	 		//var selectedEmpIdName= $('#selectedEmpIdName').val();
	 		var selectedMonthGroup= $('#selectedMonthGroup').val();
	 		var groupSelection = $('#groupSelection').val();
		    var labels = [];
		    var dataInMinutes = [];
	
			console.log('selectedMonthGroup:', selectedMonthGroup);
			console.log('selectGroupYear:', selectGroupYear);
 			$.ajaxRequest.ajax({
 				type : "POST",
 				url : "/PMS/getMonthWiseActivityGroupPie",
 				data:{
 					"selectGroupYear":selectGroupYear,
 					"selectedMonthGroup":selectedMonthGroup,
 					"groupSelection" : groupSelection
 				},
 				success : function(response) {
 					labels = response.xValuesActivityGroupMonth;
 					dataInMinutes = response.yValuesActivityGroupMonth;
 					console.log('labelsGroups:', labels);
 				    console.log('dataInMinutesGroups:', dataInMinutes);
 				// Define custom colors for pie chart
 				    var pieColors = [
 				        'rgba(75, 192, 192, 0.5)',
 				        'rgba(255, 99, 132, 0.5)',
 				        'rgba(255, 205, 86, 0.5)',
 				        'rgba(54, 162, 235, 0.5)',
 				        'rgba(255, 159, 64, 0.5)',
 				        'rgba(153, 102, 255, 0.5)',
 				        'rgba(255, 87, 34, 0.5)',
 				        'rgba(219, 112, 147, 0.5)',
 				        // Add more colors as needed
 				    ];
 				   var totalEffort = dataInMinutes.reduce(function (a, b) {
				        return a + b;
				    }, 0);
					  var percentageData = dataInMinutes.map(function (effort) {
					        return ((effort / totalEffort) * 100).toFixed(2);
					    });
 				    // Create a pie chart (change 'type' to 'pie')
 				    var ctx = document.getElementById('reportActivityGroupWiseEffortPieChart').getContext('2d');
 				    var pieChart = new Chart(ctx, {
 				        type: 'pie', // Change type to 'pie'
 				        data: {
 				            labels: labels,
 				            datasets: [{
 				                label: 'Effort',
 				                data: dataInMinutes,
 				                backgroundColor: pieColors, // Use colors directly for pie chart
 				                borderColor: pieColors,
 				                borderWidth: 1,
 				            }],
 				        },
 				        options: {
 				            plugins: {
 				                title: {
 				                    display: true,
 				                    text: 'Project Wise Activity Effort Report ('+yearToShow+')',
 				                    font: {
 				                        size: 18,
 				                    },
 				                },
 				                legend: {
 				                    display: false, // Display legend for pie chart
 				                },
 				                tooltip: {
 				                    callbacks: {
 				                        label: function(context) {
 				                            var minutes = context.dataset.data[context.dataIndex];
 				                            var hours = Math.floor(minutes / 60);
 				                            var remainingMinutes = minutes % 60;
 				                           var percentage = percentageData[context.dataIndex];
 				                            return 'Effort: ' + hours + 'h ' + remainingMinutes + 'm' + ' - ' + percentage + '%';
 				                        },
 				                    },
 				                },
 				            },
 				        },
 				    });
 				},
 				error: function(xhr, textStatus, errorThrown) {
 			        console.error("Error occurred: " + textStatus, errorThrown);
 			    }
 				});
	 	} */
	 	function activityGroupwisemonthpie() {
			
			
		    var selectGroupYear = $('#selectGroupYear1').val();
		    var selectedMonthGroup = $('#selectedMonthGroup').val();
		    var groupSelection = $('#groupSelection').val();
		    
		    $.ajax({
		        type: "POST",
		        url: "/PMS/getMonthWiseActivityGroupPie",
		        data: {
		            "selectGroupYear": selectGroupYear,
		            "selectedMonthGroup": selectedMonthGroup,
		            "groupSelection": groupSelection
		        },
		        success: function(response) {
		            var labels = response.xValuesActivityGroupMonth;
		            var dataInMinutes = response.yValuesActivityGroupMonth;

		            var totalEffort = dataInMinutes.reduce(function(a, b) {
		                return a + b;
		            }, 0);

		            var percentageData = dataInMinutes.map(function(effort) {
		                return ((effort / totalEffort) * 100).toFixed(2);
		            });

		            google.charts.load('current', {
		                'packages': ['corechart']
		            });
		            google.charts.setOnLoadCallback(drawChart);

		            function drawChart() {
	 	                var data = new google.visualization.DataTable();
	 	                data.addColumn('string', 'Project');
	 	                data.addColumn('number', 'Effort');
	 	                data.addColumn({type: 'string', role: 'tooltip', 'p': {'html': true}}); // Add tooltip role

	 	                for (var i = 0; i < labels.length; i++) {
	 	                	var projectCode = labels[i].split(' - ')[0]; // Extracting project code
	 	                    var hours = Math.floor(dataInMinutes[i] / 60);
	 	                    var minutes = dataInMinutes[i] % 60;
	 	                   var tooltipContent =
	 	                	    labels[i] + ': ' +
	 	                	    'Effort: ' + hours + 'h ' + minutes + 'm, ' +
	 	                	    'Percentage: ' + percentageData[i] + '%';
	 	                    data.addRow([projectCode, dataInMinutes[i], tooltipContent]);
	 	                }

	 	                var options = {
 	                	    title: 'Non-Project Effort Report (' + selectGroupYear + ')',
 	                	    colors: ['#64B5B5', '#ff99cc', '#d9b3ff', '#4682B4', '#99ccff', '#9966CC', '#e6b3b3', '#CC6699'],
	 	                	titleTextStyle: {
	 	                	         fontSize: 15,
	 	                	},
	 	                    pieSliceText: 'percentage',
	 	                    legend: {
	 	                        position: 'right',
	 	                        	textStyle: {
			                            fontSize: 12 // adjust font size if needed
			                        }
	 	                    },
	 	                    sliceVisibilityThreshold: 0 // Hide slices with less than 1% of the total
	 	                };

		                var chart = new google.visualization.PieChart(document.getElementById('reportActivityGroupWiseEffortPieChart'));
		                if (data.getNumberOfRows() === 0) {
					    	data.addRow(['Data is not Filled', 1, 'No Data']); // Add a dummy row to display the custom message
					        options.pieSliceText = 'none'; // Hide slice text
					    }
		                chart.draw(data, options);
		            }
		        },
		        error: function(xhr, textStatus, errorThrown) {
		            console.error("Error occurred: " + textStatus, errorThrown);
		        }
		    });
		}
	 	
	 	
		//Year To show on UI
		var yearToShow = document.getElementById("yearToShow").getAttribute("value");
		// Function to generate random light color
		function getRandomColor() {
		    var minIntensity = 150; // Minimum intensity for RGB components
		    var color = "#";
	
		    for (var i = 0; i < 3; i++) {
		        // Generate a random value between minIntensity and 255
		        var intensity = Math.floor(Math.random() * (255 - minIntensity + 1) + minIntensity);
		        
		        // Convert intensity to hex and append to the color string
		        color += intensity.toString(16).padStart(2, '0');
		    }
	
		    return color;
		}
		
		// Function to generate random dark color
		function getRandomDarkColor() {
		    var maxIntensity = 100; // Maximum intensity for RGB components
		    var color = "#";

		    for (var i = 0; i < 3; i++) {
		        // Generate a random value between 0 and maxIntensity
		        var intensity = Math.floor(Math.random() * (maxIntensity + 1));

		        // Convert intensity to hex and append to the color string
		        color += intensity.toString(16).padStart(2, '0');
		    }

		    return color;
		}
	 
	 // R10	-	GROUP MONTH WISE REPORT (PROJECT + ACTIVITY) 
	   var xValuesStringProject = document.getElementById("xValuesProject").getAttribute("value");
	   console.log(xValuesStringProject);
		var xValidJsonStringProject = xValuesStringProject.replace(/([a-zA-Z]+)/g, '"$1"');
		// modified by varun below code for misc activity 
		
		var xValuesStringActivity = document.getElementById("xValuesActivity").getAttribute("value");
		var xValidJsonStringActivity = xValuesStringActivity.replace(/([a-zA-Z]+)/g, '"$1"');
		
			// Parse the modified string to get the array
		var xValuesProject = JSON.parse(xValidJsonStringProject);
		var xValuesActivity = JSON.parse(xValidJsonStringActivity);
		
		var yValuesStringProject = document.getElementById("yValuesProject").getAttribute("value");
		var yValidJsonStringProject = yValuesStringProject.replace(/([a-zA-Z]+)/g, '"$1"');
		
		var yValuesStringActivity = document.getElementById("yValuesActivity").getAttribute("value");
		var yValidJsonStringActivity = yValuesStringActivity.replace(/([a-zA-Z]+)/g, '"$1"');
			// Parse the modified string to get the array
		var yValuesProject = JSON.parse(yValidJsonStringProject);
		var yValuesActivity = JSON.parse(yValidJsonStringActivity);
		
			// Data
		var labels = xValuesProject;
	    var dataInMinutesProjectGroup = yValuesProject;
	    var dataInMinutesActivityGroup = yValuesActivity ;
	    console.log("hi jAy ");
		console.log(xValuesProject);
		console.log(yValuesProject);
		console.log(yValuesActivity);
	 	
	    var barColors = [
			'rgba(75, 192, 192, 0.8)',    // Teal
			'rgba(255, 99, 132, 0.8)',     // Red
			'rgba(255, 205, 86, 0.8)',     // Yellow
			'rgba(54, 162, 235, 0.8)',     // Blue
			'rgba(255, 159, 64, 0.8)',     // Orange
			'rgba(153, 102, 255, 0.8)',    // Purple
			'rgba(255, 87, 34, 0.8)',      // Dark Orange
			'rgba(219, 112, 147, 0.8)',
		   
		];
	    	
	    
	    function createGroupMonthProjectActivityChart() {
	    	
	    	
	    	  var ctx = document.getElementById('reportProjectActivityGroupMonthWiseEffortColumnChart').getContext('2d');

		        // Calculate sum of total project effort and total activity effort
		        var totalProjectEffort = dataInMinutesProjectGroup.reduce(function (sum, value) {
		            return sum + value;
		        }, 0);

		        var totalActivityEffort = dataInMinutesActivityGroup.reduce(function (sum, value) {
		            return sum + value;
		        }, 0);

		        // Convert totalProjectEffort and totalActivityEffort to hours and minutes
		        var totalProjectHours = Math.floor(totalProjectEffort / 60);
		        var totalProjectRemainingMinutes = totalProjectEffort % 60;

		        var totalActivityHours = Math.floor(totalActivityEffort / 60);
		        var totalActivityRemainingMinutes = totalActivityEffort % 60;

		        var barChart = new Chart(ctx, {
		            type: 'line',
		            data: {
		                labels: labels,
		                datasets: [
		                    {
		                        label: 'Project Effort',
		                        data: dataInMinutesProjectGroup,
		                        backgroundColor: barColors,
		                        borderColor: barColors,
		                        borderWidth: 1,
		                    },
		                    {
		                        label: 'Non-Project Effort',
		                        data: dataInMinutesActivityGroup,
		                        backgroundColor: barColors,
		                        borderColor: barColors,
		                        borderWidth: 1,
		                    },
		                ],
		            },
		            options: {
		                scales: {
		                    y: {
		                        beginAtZero: true,
		                        ticks: {
		                            callback: function (value) {
		                                // Convert minutes to hours and minutes
		                                var hours = Math.floor(value / 60);
		                                var remainingMinutes = value % 60;
		                                return hours + 'h ' + remainingMinutes + 'm';
		                            },
		                        },
		                    },
		                },
		                plugins: {
		                    title: {
		                        display: true,
		                        text: 'Project Wise Month Effort Report (' + yearToShow + ')',
		                        font: {
		                            size: 18,
		                        },
		                    },
		                    legend: {
		                        display: false,
		                    },
		                    tooltip: {
		                        callbacks: {
		                            label: function (context) {
		                                var datasetLabel = '';
		                                if (context.datasetIndex === 0) {
		                                    datasetLabel = 'Project Effort: ';
		                                } else if (context.datasetIndex === 1) {
		                                    datasetLabel = 'Non-Project Effort: ';
		                                }

		                                var minutes = context.dataset.data[context.dataIndex];
		                                var hours = Math.floor(minutes / 60);
		                                var remainingMinutes = minutes % 60;

		                                return datasetLabel + hours + 'h ' + remainingMinutes + 'm';
		                            },
		                        },
		                    },
		                },
		            },
		        });

		        var totalProjectText =
		            'Total Project Effort: ' +
		            totalProjectHours +
		            'h ' +
		            totalProjectRemainingMinutes +
		            'm';

		        var totalActivityText =
		            'Total Non-Project Effort: ' +
		            totalActivityHours +
		            'h ' +
		            totalActivityRemainingMinutes +
		            'm';

		        // Create div elements for each text
		        var totalProjectDiv = document.createElement('div');
		        totalProjectDiv.style.position = 'absolute';
		        totalProjectDiv.style.top = '7px';
		        totalProjectDiv.style.right = '20px';
		        totalProjectDiv.style.fontSize = '15px';
		        totalProjectDiv.style.fontWeight = 'bold'; // Set font weight to bold
		        totalProjectDiv.innerHTML = totalProjectText;

		        var totalActivityDiv = document.createElement('div');
		        totalActivityDiv.style.position = 'absolute';
		        totalActivityDiv.style.top = '31px'; // Adjust the top position for vertical separation
		        totalActivityDiv.style.right = '18px';
		        totalActivityDiv.style.fontSize = '15px';
		        totalActivityDiv.style.fontWeight = 'bold'; // Set font weight to bold
		        totalActivityDiv.innerHTML = totalActivityText;

		        // Append the divs to the chart container
		        ctx.canvas.parentNode.appendChild(totalProjectDiv);
		        ctx.canvas.parentNode.appendChild(totalActivityDiv);
		    } 
	    
	    createGroupMonthProjectActivityChart();
		
		//R3   -    LOGGED IN USER MONTH WISE REPORT
		var xValuesString1 = document.getElementById("xValues1").getAttribute("value");
		var xValidJsonString1 = xValuesString1.replace(/([a-zA-Z]+)/g, '"$1"');
		// modified by varun below code for misc activity 
		
		var xValuesString1_1 = document.getElementById("xValues1_1").getAttribute("value");
		var xValidJsonString1_1 = xValuesString1_1.replace(/([a-zA-Z]+)/g, '"$1"');
		
			// Parse the modified string to get the array
		var xValues1 = JSON.parse(xValidJsonString1);
		var xValues1_1 = JSON.parse(xValidJsonString1_1);
		
		var yValuesString1 = document.getElementById("yValues1").getAttribute("value");
		var yValidJsonString1 = yValuesString1.replace(/([a-zA-Z]+)/g, '"$1"');
		
		var yValuesString1_1 = document.getElementById("yValues1_1").getAttribute("value");
		var yValidJsonString1_1 = yValuesString1_1.replace(/([a-zA-Z]+)/g, '"$1"');
			// Parse the modified string to get the array
		var yValues1 = JSON.parse(yValidJsonString1);
		var yValues1_1 = JSON.parse(yValidJsonString1_1);
		
			// Data
		var labels = xValues1
	    var dataInMinutesProject = yValues1;
	    var dataInMinutesActivity = yValues1_1 ;
		
	 		// Define custom colors for bars
	    var barColors = [
		    'rgba(75, 192, 192, 0.8)',
		    'rgba(255, 99, 132, 0.8)',
		    'rgba(255, 205, 86, 0.8)',
		    'rgba(54, 162, 235, 0.8)',
		    'rgba(255, 159, 64, 0.8)',
		    'rgba(153, 102, 255, 0.8)',
		    'rgba(255, 87, 34, 0.8)',
		    'rgba(219, 112, 147, 0.8)',
		    // Add more colors as needed
		];
	    	// Create a bar chart
	    function createChart() {
	        var ctx = document.getElementById('reportUserMonthWiseEffortColoumnChart').getContext('2d');

	        // Calculate sum of total project effort and total activity effort
	        var totalProjectEffort = dataInMinutesProject.reduce(function (sum, value) {
	            return sum + value;
	        }, 0);

	        var totalActivityEffort = dataInMinutesActivity.reduce(function (sum, value) {
	            return sum + value;
	        }, 0);

	        // Convert totalProjectEffort and totalActivityEffort to hours and minutes
	        var totalProjectHours = Math.floor(totalProjectEffort / 60);
	        var totalProjectRemainingMinutes = totalProjectEffort % 60;

	        var totalActivityHours = Math.floor(totalActivityEffort / 60);
	        var totalActivityRemainingMinutes = totalActivityEffort % 60;

	        var barChart = new Chart(ctx, {
	            type: 'line',
	            data: {
	                labels: labels,
	                datasets: [
	                    {
	                        label: 'Project Effort',
	                        data: dataInMinutesProject,
	                        backgroundColor: barColors,
	                        borderColor: barColors,
	                        borderWidth: 1,
	                    },
	                    {
	                        label: 'Non-Project Effort',
	                        data: dataInMinutesActivity,
	                        backgroundColor: barColors,
	                        borderColor: barColors,
	                        borderWidth: 1,
	                    },
	                ],
	            },
	            options: {
	                scales: {
	                    y: {
	                        beginAtZero: true,
	                        ticks: {
	                            callback: function (value) {
	                                // Convert minutes to hours and minutes
	                                var hours = Math.floor(value / 60);
	                                var remainingMinutes = value % 60;
	                                return hours + 'h ' + remainingMinutes + 'm';
	                            },
	                        },
	                    },
	                },
	                plugins: {
	                    title: {
	                        display: true,
	                        text: 'Employee Wise Month Effort Report (' + yearToShow + ')',
	                        font: {
	                            size: 18,
	                        },
	                    },
	                    legend: {
	                        display: false,
	                    },
	                    tooltip: {
	                        callbacks: {
	                            label: function (context) {
	                                var datasetLabel = '';
	                                if (context.datasetIndex === 0) {
	                                    datasetLabel = 'Project Effort: ';
	                                } else if (context.datasetIndex === 1) {
	                                    datasetLabel = 'Non-Project Effort: ';
	                                }

	                                var minutes = context.dataset.data[context.dataIndex];
	                                var hours = Math.floor(minutes / 60);
	                                var remainingMinutes = minutes % 60;

	                                return datasetLabel + hours + 'h ' + remainingMinutes + 'm';
	                            },
	                        },
	                    },
	                },
	            },
	        });

	        var totalProjectText =
	            'Total Project Effort: ' +
	            totalProjectHours +
	            'h ' +
	            totalProjectRemainingMinutes +
	            'm';

	        var totalActivityText =
	            'Total Non-Project Effort: ' +
	            totalActivityHours +
	            'h ' +
	            totalActivityRemainingMinutes +
	            'm';

	        // Create div elements for each text
	        var totalProjectDiv = document.createElement('div');
	        totalProjectDiv.style.position = 'absolute';
	        totalProjectDiv.style.top = '7px';
	        totalProjectDiv.style.right = '20px';
	        totalProjectDiv.style.fontSize = '15px';
	        totalProjectDiv.style.fontWeight = 'bold'; // Set font weight to bold
	        totalProjectDiv.innerHTML = totalProjectText;

	        var totalActivityDiv = document.createElement('div');
	        totalActivityDiv.style.position = 'absolute';
	        totalActivityDiv.style.top = '31px'; // Adjust the top position for vertical separation
	        totalActivityDiv.style.right = '18px';
	        totalActivityDiv.style.fontSize = '15px';
	        totalActivityDiv.style.fontWeight = 'bold'; // Set font weight to bold
	        totalActivityDiv.innerHTML = totalActivityText;

	        // Append the divs to the chart container
	        ctx.canvas.parentNode.appendChild(totalProjectDiv);
	        ctx.canvas.parentNode.appendChild(totalActivityDiv);
	    }

	    // Call the function to create the chart
	    createChart(); 
	    
	 	// R9	-	 LOGGED IN USER PROJECT(Pie) WISE REPORT (Month filter)
	 	$(document).ready(function () {
	 		projectWiseMonthPie();
	 	});
	 
	 	$('#selectedMonth').on('change', function () {
	 		projectWiseMonthPie();
		 });
	 	
	 	
	 		
 		function projectWiseMonthPie() {
	 	    var selectedYear = $('#selectedYear').val();
	 	    var selectedEmpIdName = $('#selectedEmpIdName').val();
	 	    var selectedMonth = $('#selectedMonth').val();
	 	    var labels = [];
	 	    var dataInMinutes = [];

	 	    $.ajax({
	 	        type: "POST",
	 	        url: "/PMS/getMonthWiseProjectPie",
	 	        data: {
	 	            "selectedYear": selectedYear,
	 	            "selectedMonth": selectedMonth,
	 	            "selectedEmpIdName": selectedEmpIdName
	 	        },
	 	        success: function (response) {
	 	            labels = response.xValuesMonth;
	 	            dataInMinutes = response.yValuesMonth;

	 	            var totalEffort = dataInMinutes.reduce(function (a, b) {
	 	                return a + b;
	 	            }, 0);

	 	            var percentageData = dataInMinutes.map(function (effort) {
	 	                return ((effort / totalEffort) * 100).toFixed(2);
	 	            });

	 	            google.charts.load('current', {
	 	                'packages': ['corechart']
	 	            });
	 	            google.charts.setOnLoadCallback(drawChart);

	 	            function drawChart() {
	 	                var data = new google.visualization.DataTable();
	 	                data.addColumn('string', 'Project');
	 	                data.addColumn('number', 'Effort');
	 	                data.addColumn({type: 'string', role: 'tooltip', 'p': {'html': true}}); // Add tooltip role

	 	                for (var i = 0; i < labels.length; i++) {
	 	                	var projectCode = labels[i].split(' - ')[0];
	 	                    var hours = Math.floor(dataInMinutes[i] / 60);
	 	                    var minutes = dataInMinutes[i] % 60;
	 	                   var tooltipContent =
		 	                	  '<div class="tooltip-content">' +
		 	                     '<strong>' + labels[i] + ':</strong><br>' +
		 	                     'Effort: ' + hours + 'h ' + minutes + 'm<br>' +
		 	                     'Percentage: ' + percentageData[i] + '%' +
		 	                 		'</div>';
	 	                    data.addRow([projectCode, dataInMinutes[i], tooltipContent]);
	 	                }

	 	                var options = {
	 	                    title: 'Project Effort Report (' + yearToShow + ')',
	 	                   	colors: ['#64B5B5', '#ff99cc', '#d9b3ff', '#4682B4', '#99ccff', '#9966CC', '#e6b3b3', '#CC6699'],
		 	                titleTextStyle: {
		                	         fontSize: 15
		                	},
	 	                    pieSliceText: 'percentage',
	 	                    legend: {
	 	                        position: 'right',
	 	                       textStyle: {
	 	                            fontSize: 12 // adjust font size if needed
	 	                        }
	 	                    },
	 	                    sliceVisibilityThreshold: 0,
	 	                   tooltip: { isHtml: true }// Hide slices with less than 1% of the total
	 	                };

	 	                var chart = new google.visualization.PieChart(document.getElementById('reportUserProjectWiseEffortPieChart'));
	 	               	if (data.getNumberOfRows() === 0) {
					    	data.addRow(['Data is not Filled', 1, 'No Data']); // Add a dummy row to display the custom message
					        options.pieSliceText = 'none'; // Hide slice text
					    }
	 	                chart.draw(data, options);
	 	            }

	 	        },
	 	        error: function (xhr, textStatus, errorThrown) {
	 	            console.error("Error occurred: " + textStatus, errorThrown);
	 	        }
	 	    });
	 	}
	    
	    
		//R4   -    LOGGED IN USER ACTIVITY WISE(Pie) REPORT (Month filter)
		$(document).ready(function () {
	 		activityWiseMonthPie();
	 	});
	 
	 	$('#selectedMonth').on('change', function () {
	 		activityWiseMonthPie();
		 });
	 	
	 	
	 		
 		function activityWiseMonthPie() {
	 		var selectedYear= $('#selectedYear').val();
	 			var selectedEmpIdName= $('#selectedEmpIdName').val();
	 			var selectedMonth= $('#selectedMonth').val();
	 	    var labels = [];
	 	    var dataInMinutes = [];
	 			    
	 			    $.ajax({
	 			        type: "POST",
	 			        url: "/PMS/getMonthWiseActivityPie",
	 			        data: {
	 			        	"selectedYear":selectedYear,
	 						"selectedMonth":selectedMonth,
	 						"selectedEmpIdName":selectedEmpIdName
	 			        },
	 			        success: function(response) {
	 			   		labels = response.xValuesMonthsAct;
	 					dataInMinutes = response.yValuesMonthsAct;
	 			          
	 			            var totalEffort = dataInMinutes.reduce(function(a, b) {
	 			                return a + b;
	 			            }, 0);

	 			            var percentageData = dataInMinutes.map(function(effort) {
	 			                return ((effort / totalEffort) * 100).toFixed(2);
	 			            });

	 			            google.charts.load('current', {
	 			                'packages': ['corechart']
	 			            });
	 			            google.charts.setOnLoadCallback(drawChart);

	 			           function drawChart() {
	 		 	                var data = new google.visualization.DataTable();
	 		 	                data.addColumn('string', 'Project');
	 		 	                data.addColumn('number', 'Effort');
	 		 	                data.addColumn({type: 'string', role: 'tooltip', 'p': {'html': true}}); // Add tooltip role

	 		 	                for (var i = 0; i < labels.length; i++) {
	 		 	                	var projectCode = labels[i].split(' - ')[0]; 
	 		 	                    var hours = Math.floor(dataInMinutes[i] / 60);
	 		 	                    var minutes = dataInMinutes[i] % 60;
	 		 	                   var tooltipContent =
	 		 	                	    labels[i] + ': ' +
	 		 	                	    'Effort: ' + hours + 'h ' + minutes + 'm, ' +
	 		 	                	    'Percentage: ' + percentageData[i] + '%';
	 		 	                    data.addRow([projectCode, dataInMinutes[i], tooltipContent]);
	 		 	                }

	 		 	                var options = {
	 		 	                    title: 'Non-Project Effort Report (' + yearToShow + ')',
	 		 	                  	colors: ['#64B5B5', '#ff99cc', '#d9b3ff', '#4682B4', '#99ccff', '#9966CC', '#e6b3b3', '#CC6699'],
		 		 	                titleTextStyle: {
		 	                	         fontSize: 15,
		 	                	 	},
	 		 	                    pieSliceText: 'percentage',
	 		 	                  legend: {
	 		 	                        position: 'right',
	 		 	                      textStyle: {
	 		                             fontSize: 12 // adjust font size if needed
	 		                         }
	 		 	                    },
	 		 	                    sliceVisibilityThreshold: 0 // Hide slices with less than 1% of the total
	 		 	                };


	 			                var chart = new google.visualization.PieChart(document.getElementById('reportUserActivityWiseEffortColumnChart'));
	 			               	if (data.getNumberOfRows() === 0) {
							    	data.addRow(['Data is not Filled', 1, 'No Data']); // Add a dummy row to display the custom message
							        options.pieSliceText = 'none'; // Hide slice text
							    }
	 			                chart.draw(data, options);
	 			            }

	 			        },
	 			        error: function(xhr, textStatus, errorThrown) {
	 			            console.error("Error occurred: " + textStatus, errorThrown);
	 			        }
	 			    });
	 			}
		
	    
	  	
	  
	</script>
	
	
</body>
</html>
