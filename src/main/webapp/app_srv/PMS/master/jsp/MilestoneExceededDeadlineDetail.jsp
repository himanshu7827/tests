<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html lang="en">
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">


</head>
<body>

<section id="main-content" class="main-content merge-left">

	<div class=" container wrapper1">
	<div class="row">
	<div class=" col-md-12 pad-top-double  text-left">
		<ol class="breadcrumb bold">
			<li>Home</li>
			<!-- <li>Consumer Forms For Medical Devices</li> -->
			<li class="active">Milestones Exceeded Deadline</li>
		</ol>
	</div>
</div>
<div class="row ">
		
		 </div>
		 <c:if test="${message != null && message != ''}">
		 	<c:choose>
		 		<c:when test="${status=='success'}">
		 			<div id="userinfo-message"><p class="success_msg">${message}</p></div> 
		 		</c:when>
		 		<c:otherwise>
		 			<div id="userinfo-message"><p class="error_msg">${message}</p></div> 
		 		</c:otherwise>		 	
		 	</c:choose>       		
        </c:if>	


<c:if test="${not empty data2}">  
<div class="container">
<div class="panel panel-info panel-glance">
						<div class="panel-heading font_eighteen center">Milestones Exceeded Deadline</div>
						<div class="panel-body">
						<!-- 	Added the filter of less than and greater than  -->
						
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 zero">
					<!-- 	comment the line by varun on 06-07-2023 -->
		          <!--     <div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 date-bar pad-bottom"> -->
			                  <div class="col-md-5 col-lg-5 col-sm-5 col-xs-5 date-bar pad-bottom ">
			                    <!--  change the width 32 to 43% by varun 0n 06-07-2023 -->
								<select id="filterSymbol" onchange="calculateDates()" class="inline form-control" style="width:43%">
								<option value=">=">Less than (<) </option>
								<option value="<=">Greater than (>)</option>
								
								</select>
			 <!--  change the width 32 to 43% by varun 0n 06-07-2023 -->
			               <label class="" for="from">Last:</label> <select id="months" onchange="calculateDates()" class="inline form-control" style="width:43%">
								<option value="0">Select Months/Year's</option>
								<option value="3">3 Months</option>
								<option value="6">6 Months</option>
								<option value="12">1 Years</option>
								<option value="24">2 Years</option>
								</select>
									</div> 
									
									<div id="milesAnothergcFilter">
								<div
								class="col-md-1 col-lg-1 col-sm-1 col-xs-2 pad-bottom " style="padding-top:4px;">
								<h3 style="color: #1578c2;;"><strong>OR</strong></h3>
								</div>
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-6  date-bar  pad-bottom ">

								<label class="font_16" for="from">From</label> <input type="text"
									id="from" name="from" readonly /> <label class="font_16"
									for="to">To</label> <input type="text" id="to"
									name="to" readonly /> &nbsp; &nbsp;
								<button type="button"  class="btn btn-success go-btn ">Go</button>

							</div>
							</div>
									
									
								</div>
											
	<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
	<div class=" datatable_row pad-bottom" style="padding-top:25px;">
	
		
			<table id="datatable1" class="table table-striped"
				style="width: 100%">
				<thead class="datatable_thead bold ">
					<tr>
					<!-- added the serial no and count by  05-06-23 -->
						  <th width="5%"><spring:message code="serial.no"/></th> 	 				
						<th width="40%"><spring:message code="Project_Module_Master.projectName"/></th>
						<%-- <th width="12%"><spring:message code="project.projectRefNo"/></th>
						<th width="20%"><spring:message code="dashboard.client.name"/></th>	 --%>
						<%-- <th width="15%"><spring:message
													code="publicationdetail.Organization" /></th>  --%>					
						<th width="30%"><spring:message code="Project_Payment_Schedule.milestoneName"/></th> 
 						<th width="20%"><spring:message code="Expected_CompletionDate"/></th> 
  						<th width="10%"><spring:message code="forms.action"/></th> 
					</tr>
				</thead>
				<tbody class="">
					<c:forEach items="${data2}" var="milestoneData" varStatus="theCount">
						<tr>
							  <td>${theCount.count}</td>   
							  
													
							<td><a class="font_14" title="Click to View Complete Information" onclick="viewProjectDetails('/PMS/projectDetails/${milestoneData.encProjectId}')">${milestoneData.strProjectName}</a><p class="bold blue font_14 text-left">${milestoneData.strProjectReference}</p></td>						
						<%-- 	<td class="bold blue font_14 text-right">${milestoneData.strProjectReference}</td>
							<td class="bold orange font_12">${milestoneData.clientName}</td> --%>
							<%-- <td><p class="font_14 orange">${milestoneData.clientName}</p></td> --%>
																				
							<td class="">${milestoneData.milestoneName}</td>
 							<c:choose>
								<c:when test="${not empty milestoneData.completionDate}">
									<td class="text-center">${milestoneData.completionDate}</td>
								</c:when>
								<c:when test="${not empty milestoneData.expectedStartDate}">
									<td class="text-center">${milestoneData.expectedStartDate}</td>
								</c:when>
								<c:otherwise>
									<td></td>
								</c:otherwise>
							</c:choose>
 								
 							
 							<td><div class="text-center"><a class='btn btn-primary '  onclick="viewProjectDetails('/PMS/mst/MilestoneReviewMaster/${milestoneData.encMilestsoneId}')">Review</a></div></td>
							
						</tr>
					</c:forEach>
				</tbody>
			</table>
			
			<!--End of data table-->
	
	</div>
	<!--End of datatable_row-->
	</div>
	</div>
	
</div>
</div>
</c:if>
</div>
</section>

 <script type="text/javascript" src="/PMS/resources/app_srv/PMS/master/js/MilestoneDetail.js"></script>
 
	
	
</body>
</html>