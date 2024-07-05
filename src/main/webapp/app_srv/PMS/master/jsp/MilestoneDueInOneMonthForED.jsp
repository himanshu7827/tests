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
			<li class="active">Milestones Due In Next One Month</li>
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

	<div class="container">
	<div class="container">
<div class="panel panel-info panel-glance">
						<div class="panel-heading font_eighteen center">Milestones Due In Next One Month</div>
						<div class="panel-body">
							
	<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
	<div class=" datatable_row pad-bottom">
		
			<table id="milestoneTable" class="table table-striped table-bordered mileDataTable"
				width="100%">
				<thead class="datatable_thead bold ">
					<tr>
						<th width="5%"><spring:message code="serial.no"/></th>	
						<th width="10%" style="width: 50px;"><spring:message code="group.groupName"/></th>					
						<th width="35%"><spring:message code="Project_Module_Master.projectName"/></th>
						<%-- <th width="12%"><spring:message code="project.projectRefNo"/></th>
						<th width="20%"><spring:message code="dashboard.client.name"/></th>	 --%>					
						<th width="18%"><spring:message code="Project_Payment_Schedule.milestoneName"/></th> 
 						<th width="10%"><spring:message code="Expected_CompletionDate"/></th> 
  						<th width="8%"><spring:message code="forms.action"/></th> 
 
					</tr>
				</thead>
				<thead class="filters" >
					<tr>
						<th width="5%"><spring:message code="serial.no"/></th>	
						<th width="10%" style="width: 50px;" class="comboBox"><spring:message code="group.groupName"/></th>					
						<th width="35%"></th>
						<!-- <th width="12%"></th>
						<th width="20%"></th>	 -->					
						<th width="18%"></th> 
 						<th width="10%"></th> 
  						<th width="8%"></th> 
 
					</tr>
				</thead>
				<tbody class="">
					<c:forEach items="${data1}" var="milestoneData" varStatus="theCount">
						<tr>
							<td>${theCount.count}</td>	
							<td>${milestoneData.groupName}</td>							
							<td><a class="font_14" title="Click to View Complete Information" onclick="viewProjectDetails('/PMS/projectDetails/${milestoneData.encProjectId}')">${milestoneData.strProjectName}</a><p class="font_14 orange">${milestoneData.clientName}
																				</p><p class="bold blue font_14 text-left">${milestoneData.strProjectReference}</p></td>						
							<%-- <td class="bold blue font_14 text-right">${milestoneData.strProjectReference}</td>
							<td class="bold orange font_12">${milestoneData.clientName}</td> --%>
							<td>${milestoneData.milestoneName}</td>
 							<c:choose>
								<c:when test="${not empty milestoneData.completionDate}">
									<td class="text-right">${milestoneData.completionDate}</td>
								</c:when>
								<c:when test="${not empty milestoneData.expectedStartDate}">
									<td class="text-right">${milestoneData.expectedStartDate}</td>
								</c:when>
								<c:otherwise>
									<td></td>
								</c:otherwise>
							</c:choose>
 								
 							<td><a class='btn btn-primary' onclick="viewProjectDetails('/PMS/mst/MilestoneReviewMaster/${milestoneData.encMilestsoneId}')">Review</a></td>
							
						</tr>
					</c:forEach>
				</tbody>
			</table>
			
			<!--End of data table-->
	</div>
	</div>
	</div>
	<!--End of datatable_row-->
</div>
</div>
</div>

</div>
</section>

 <script type="text/javascript" src="/PMS/resources/app_srv/PMS/master/js/MilestoneDetailforED.js"></script>
 
	
	
</body>
</html>