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
			<li class="active"><spring:message code="milestone.review.detail"/></li>
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
		 <sec:authorize access="hasAuthority('READ_MILESTONE_REVIEW_DETAIL_MST')">
		
		<form:form id="form1" modelAttribute="projectMilestoneForm" action="/PMS/mst/MilestoneReviewDetailMaster" method="post">
		
		
			<div class="container ">
		<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
				
				<div class="panel panel-info panel-info1">
					<div class="panel-heading panel-heading-fd"><h4 class="text-center "><spring:message code="milestone.review.detail"/></h4></div>
					<div class="panel-body text-center">
						<c:if test="${not empty referrer}">
							<div class="pull-right pad-top pad-bottom">
								<a href="${referrer}" class="btn btn-orange font_14">												
											<span class="pad-right"><i class="fa fa-arrow-left"></i></span>Back
								</a>
							</div>
						</c:if>
			
				<div class="row pad-top ">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group">
							<div class="col-md-4 col-lg-4 col-sm-6 col-xs-12 ">

								<label class="label-class pad-top"><spring:message code="master.noofdays"/>:<span
									style="color: red;">*</span></label>
							</div>

							 <div class="col-md-4 col-lg-4 col-sm-4 col-xs-12">
								<div class="input-container">
									<i class="fa fa-film icon"></i> 
									<form:input class="input-field"  path="noOfDays" placeholder="No. of Days" value="${days}" onblur="myFunction()"/>
									<form:errors path="noOfDays" cssClass="error" />
								</div>								
							</div>
							<div class="col-md-4 col-lg-4 col-sm-6 col-xs-12 text-justify">
									<label class="pad-top"><span>Days</span></label>
								</div>
						</div>
						</div>

					
				
					
					</div> 
					</div>
					
					</div>
					</div>
		
		</form:form>
		</sec:authorize>
	<!-- </div> -->
	<!--End of panel-->
	<!--Start data table-->
	<div class="container">
	<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
	<div class=" datatable_row pad-bottom">
		<fieldset class="fieldset-border">

			<legend class="bold legend_details">Details :</legend>
		
			<table id="datatable" class="table table-striped table-bordered"
				style="width: 100%">
				<thead class="datatable_thead bold ">
					<tr>
							<th><spring:message code="serial.no"/></th>
						<th style="width:12%;"><spring:message code="Project_Payment_Schedule.milestoneName"/></th>
						<th><spring:message code="Project_Payment_Schedule.projectName"/></th>
						<th style="width:12%;"><spring:message code="employee_Role.group_Name"/></th> 
 						<th style="width:12%;"><spring:message code="Expected_CompletionDate"/></th> 
  						<th><spring:message code="forms.action"/></th> 
 
					</tr>
				</thead>
				<tbody class="">
					<c:forEach items="${data1}" var="milestoneData" varStatus="theCount">
						<tr>
							<td>${theCount.count}</td>
							<td>${milestoneData.milestoneName}</td>
							<td>${milestoneData.strProjectName}</td>							
							<td>${milestoneData.groupName}</td>
 							<c:choose>
								<c:when test="${not empty milestoneData.completionDate}">
									<td>${milestoneData.completionDate}</td>
								</c:when>
								<c:when test="${not empty milestoneData.expectedStartDate}">
									<td>${milestoneData.expectedStartDate}</td>
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
		</fieldset>
	</div>
	<!--End of datatable_row-->
</div>
</div>
<c:if test="${not empty data2}">  
<div class="container">
	<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
	<div class=" datatable_row pad-bottom">
		<fieldset class="fieldset-border">

			<legend  class="bold" style="width: 25%;font-size: 16px" >Milestone With Pending Review:</legend>
		
			<table id="datatable1" class="table table-striped table-bordered"
				style="width: 100%">
				<thead class="datatable_thead bold ">
					<tr>
							<th><spring:message code="serial.no"/></th>
						<th style="width:12%;"><spring:message code="Project_Payment_Schedule.milestoneName"/></th>
						<th><spring:message code="Project_Payment_Schedule.projectName"/></th>
						<th style="width:12%;"><spring:message code="employee_Role.group_Name"/></th> 
 						<th style="width:12%;"><spring:message code="Expected_CompletionDate"/></th> 
  						<th><spring:message code="forms.action"/></th> 
 
					</tr>
				</thead>
				<tbody class="">
					<c:forEach items="${data2}" var="milestoneData" varStatus="theCount">
						<tr>
							<td>${theCount.count}</td>
							<td>${milestoneData.milestoneName}</td>
							<td>${milestoneData.strProjectName}</td>							
							<td>${milestoneData.groupName}</td>
 							<c:choose>
								<c:when test="${not empty milestoneData.completionDate}">
									<td>${milestoneData.completionDate}</td>
								</c:when>
								<c:when test="${not empty milestoneData.expectedStartDate}">
									<td>${milestoneData.expectedStartDate}</td>
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
		</fieldset>
	</div>
	<!--End of datatable_row-->
</div>
</div>
</c:if>
</div>
</section>

 <script type="text/javascript" src="/PMS/resources/app_srv/PMS/master/js/MilestoneReviewDetail.js"></script>
 
	
	
</body>
</html>