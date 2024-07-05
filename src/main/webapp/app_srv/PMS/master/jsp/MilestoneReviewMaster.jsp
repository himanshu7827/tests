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
			<li class="active">Project Milestone Review Master</li>
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
		
 		<sec:authorize access="hasAuthority('READ_MILESTONE_REVIEW_MST')">
 		<form:form id="form1" name="form1" modelAttribute="milestoneReviewModel" action="/PMS/mst/saveMilestoneReview" method="post">
			<form:hidden path="numIds"/>
			<form:hidden path="numId"/>
			<form:hidden path="milestoneId" value="${data1.numId}"/>
			<form:hidden path="referrer" value="${referrer}"/>
			<input type="hidden" id="projectStartDate" value="${data1.projectStartDate}"/>
			<input type="hidden" id="projectEndDate" value="${data1.projectEndDate}"/>
			
			<div class="container ">
		<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
				<div class="panel panel-info panel-info1">
					<div class="panel-heading panel-heading-fd"><h4 class="text-center ">Project Milestone Review Master</h4></div>
					<div class="panel-body text-center">
			<c:if test="${not empty referrer}"> 
							<div class="pull-right pad-top pad-bottom">
								<a href="${referrer}" class="btn btn-orange font_14">												
											<span class="pad-right"><i class="fa fa-arrow-left"></i></span>Back
								</a>
							</div>
						</c:if> 
				<div class="row pad-top ">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
								<label class="label-class"><spring:message code="Project_Invoice_Master.projectName"/>:</label>
							</div>
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
									<h5 align="left" class="bold">${data1.strProjectName}</h5>								
								</div>	 						
						</div>
						</div>
						<div class="row ">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
								<label class="label-class"><spring:message code="client.name"/>:</label>
							</div>
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
									<h5 align="left" class="bold">${data1.clientName}</h5>								
								</div>	 						
						</div>
						</div>
					
						<div class="row ">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
								<label class="label-class"><spring:message code="Project_Payment_Schedule.milestoneName"/>:</label>
							</div>
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
									<h5 align="left" class="bold">${data1.milestoneName}</h5>								
								</div>					
						</div>
						</div>	
						<div class="row ">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
								<label class="label-class"><spring:message code="Expected_StartDate"/>:</label>
							</div>
							 <div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
									<h5 align="left" class="bold">${data1.expectedStartDate}</h5>								
								</div>				
						</div>
						</div>
						<c:if test="${not empty thisDate && not empty lastDate}">
   

						<div class="row ">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
								<label class="label-class"><spring:message code="form.thisDate"/>:</label>
							</div>
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
									<h5 align="left" class="bold">${thisDate}</h5>								
								</div>					
						</div>
						</div>	
						
						<%-- <div class="row ">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
								<label class="label-class"><spring:message code="form.second.last.date"/>:</label>
							</div>
							 <div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
									<h5 align="left" class="bold">${lastDate}</h5>								
								</div>				
						</div>
						</div> --%>
						</c:if>
						<div class="row  ">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
								<label class="label-class" ><spring:message code="milestone.review.date"/>:<span
									style="color: red;">*</span> </label>
							</div>
							 <div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
									<i class="fa fa-calendar icon"></i>
									 <form:input class="input-field" readonly='true' path="reviewDate" />							
								<form:errors path="reviewDate" cssClass="error" />
								</div>
							</div>
						</div>
						</div>
						
						<div class="row  ">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
								<label class="label-class" ><spring:message code="milestone.review.completion.date"/>:<span
									style="color: red;">*</span> </label>
							</div>
							 <div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
									<i class="fa fa-calendar icon"></i>
									 <form:input class="input-field" readonly='true' path="completionDate" />							
								<form:errors path="completionDate" cssClass="error" />
								</div>
							</div>
						</div>
						</div>
						
						<div class="row  ">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
								<label class="label-class" ><spring:message code="milestone.review.strRemarks"/>: <span style="color: red;" id="reviewRemarks">*</span></label>
							</div>
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
									<i class="fa fa-th icon"></i> 
									<form:textarea class="input-field" path="strRemarks"/>
									<form:errors path="strRemarks" cssClass="error" />
								</div>
							</div>
						</div>
						</div>
										

							<div class="row " id="completionStatus">
										<div
											class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
											<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="status.completed" /> :</label>
											</div>
											
											<div class="col-md-1 col-lg-1 col-sm-2 col-xs-6">
												<div class="input-container">
													<!-- <i class="fa fa-th-large icon"></i> -->
													<form:checkbox   class="input-field" path="status" value='true'/>
													<form:errors path="status" cssClass="error" />
												</div>
											</div>


										</div>
									</div>
					<div class="row pad-top  form_btn">
					 		<sec:authorize access="hasAuthority('WRITE_MILESTONE_REVIEW_MST')">
					
						<button type="button" class="btn btn-primary font_14" id="save">
							<span class="pad-right"><i class="fa fa-folder"></i></span>Save
						</button>
						</sec:authorize>
						<button type="button" class="btn btn-primary reset font_14" id="reset">
							<span class="pad-right"><i class="fa fa-refresh"></i></span>Reset
						</button>
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
		<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
	<div class=" datatable_row pad-bottom">
		<fieldset class="fieldset-border">

			<legend class="bold legend_details">Details :</legend>
		
			<table id="example1" class="table table-striped table-bordered"
				style="width: 100%">
				<thead class="datatable_thead bold ">
					<tr>
						<th><spring:message code="milestone.review.date"/></th>
						<th><spring:message code="milestone.review.completion.date"/></th>
						<th><spring:message code="milestone.review.strRemarks"/></th>	
<%-- 						<th><spring:message code="forms.status"/></th>	
 --%>											
					</tr>
				</thead>
				<tbody class="">

				<%--  <c:choose>
  <c:when test="${reviewDetail.reviewDate}">
  
<td><input type="hidden" id="reviDat" value="${reviewDetail.reviewDate}" /></td>
  </c:when>
  
  <c:otherwise>
    <td><input type="hidden" id="reviDat" value="null" /> </td>
  </c:otherwise>
</c:choose> --%>
					<c:forEach items="${data}" var="reviewDetail">
					
						<tr>
						
<%-- 							<td><input type="checkbox"  class="CheckBox" id="CheckBox"  value="${reviewDetail.numId}" />${reviewDetail.numId}</td>
 --%>						
<%--  <td><input type="hidden" id="reviDat" value="${reviewDetail.reviewDate}" />${reviewDetail.reviewDate}</td> --%>

<!-- Showing alert on same date  -->
 <td class="revtest">${reviewDetail.reviewDate}</td> 
 
<%--  <td>${reviewDetail.reviewDate} </td>  --%>
                   
                                 <td>${reviewDetail.completionDate}</td>
								<td>${reviewDetail.strRemarks}</td>
								<%-- <td><c:choose>
												<c:when test="${reviewDetail.status}">Completed</c:when>
												<c:otherwise>Not Completed </c:otherwise>
									</c:choose> 
								</td> --%>
							
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
	<!--End of datatable_row-->
</div>
	</div> 
</div>
</section>

	<script type="text/javascript" src="/PMS/resources/app_srv/PMS/master/js/MilestoneReviewMaster.js"></script>

</body>
</html>