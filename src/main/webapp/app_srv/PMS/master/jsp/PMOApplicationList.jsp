<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<html lang="en">
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- <link rel="stylesheet"
	href="/PMS/resources/app_srv/PMS/global/css/OrgChart/jquery.orgchart.css"> -->
<link rel="stylesheet"
	href="/PMS/resources/app_srv/PMS/global/css/bootstrap-dropdownhover.css">


</head>
<style>
/* .btn.dropdown-toggle:after, .nav-link.dropdown-toggle:after{
     content: none !important; 
}
.btn-secondary{
padding: 12px 12px !important;
} */
</style>
<body>

	<section id="main-content" class="main-content merge-left">
		<div class=" container wrapper1">
			<div class="row">
	<div class=" col-md-12 pad-top-double  text-left">
		<ol class="breadcrumb bold">
			<li>Home</li>
		
			<li class="active"><spring:message code="proposal.report.proposalDetail"/></li>
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
		
	<!--Start data table-->


		<div class="row pad-top pad-bottom">
				<div class="pull-right">
					 <div class="col-md-4">				 
						<button type='button' class="btn btn-orange "  onclick="window.open('/PMS/applicationBasicDetails','_blank')" ><spring:message code="form.create.proposal"/> <i class="fa fa-plus-circle "></i> </button>
					</div>
				</div>
			</div>
			
			
	<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
	<div class=" datatable_row pad-bottom">
		<fieldset class="fieldset-border">
		<div class="col-md-12 col-xs-12 col-sm-12 col-lg-12 groupdiv pad-top"
							id="groupdiv">							
<%-- 								<c:forEach items="${groupnames}" var="grp">								
									<c:choose>
										<c:when test="${fn:length(grp.groupName) >= 41}">
											<div class="col-md-5 col-lg-5 col-sm-5 col-xs-5 pad-top">
										</c:when>
										<c:when test="${fn:length(grp.groupName) >= 34}">
											<div class="col-md-4 col-lg-4 col-sm-4 col-xs-4 pad-top">
										</c:when>
										<c:when test="${fn:length(grp.groupName) >= 29}">
											<div class="col-md-3 col-lg-3 col-sm-3 col-xs-3 pad-top">
										</c:when>
										<c:otherwise>
											<div class="col-md-2 col-lg-2 col-sm-2 col-xs-2 pad-top">
										</c:otherwise>
									</c:choose>
										<button class="btn btn1 "
											data-toggle="tooltip" data-placement="top"
											title="Click here to see details"
											style="font-size:17px; font-weight:600;background-color:${grp.bgColor}" onclick="loadGroupWiseApplications('${grp.encGroupId}')"><c:out value="${grp.groupName}"/></button>
									</div>
									
									</c:forEach> --%>
<c:forEach items="${groupnames}" var="grp">		
<c:if test="${grp.showProjects eq 1 }" >
	<c:choose>
		<c:when test="${fn:length(grp.groupName) >= 41}">
			<div class="col-md-5 col-lg-5 col-sm-5 col-xs-5 pad-top">
				<button class="btn btn1"
					data-toggle="tooltip" data-placement="top"
					title="Click here to see details"
					style="font-size:17px; font-weight:600;background-color:${grp.bgColor}" onclick="loadGroupWiseApplications('${grp.encGroupId}')">
					<c:out value="${grp.groupName}"/>
				</button>
			</div>
		</c:when>
		<c:when test="${fn:length(grp.groupName) >= 34}">
			<div class="col-md-4 col-lg-4 col-sm-4 col-xs-4 pad-top">
				<button class="btn btn1"
					data-toggle="tooltip" data-placement="top"
					title="Click here to see details"
					style="font-size:17px; font-weight:600;background-color:${grp.bgColor}" onclick="loadGroupWiseApplications('${grp.encGroupId}')">
					<c:out value="${grp.groupName}"/>
				</button>
			</div>
		</c:when>
		<c:when test="${fn:length(grp.groupName) >= 29}">
			<div class="col-md-3 col-lg-3 col-sm-3 col-xs-3 pad-top">
				<button class="btn btn1"
					data-toggle="tooltip" data-placement="top"
					title="Click here to see details"
					style="font-size:17px; font-weight:600;background-color:${grp.bgColor}" onclick="loadGroupWiseApplications('${grp.encGroupId}')">
					<c:out value="${grp.groupName}"/>
				</button>
			</div>
		</c:when>
		<c:otherwise>
			<div class="col-md-2 col-lg-2 col-sm-2 col-xs-2 pad-top">
				<button class="btn btn1"
					data-toggle="tooltip" data-placement="top"
					title="Click here to see details"
					style="font-size:17px; font-weight:600;background-color:${grp.bgColor}" onclick="loadGroupWiseApplications('${grp.encGroupId}')">
					<c:out value="${grp.groupName}"/>
				</button>
			</div>
		</c:otherwise>
	</c:choose>
	</c:if>
</c:forEach>
						</div>
		<div class="col-md-12 col-xs-12 col-sm-12 col-lg-12  pad-top">
			<table id="example" class="table table-striped table-bordered margin-top"
				style="width: 100%">
				<thead class="datatable_thead bold ">
					<tr>
					    <th><spring:message code="serial.no"/></th>
						<th><spring:message code="proposal.proposalTitle"/></th>
<%-- 						<th><spring:message code="application.businesstype.label"/></th>
 --%>						<th><spring:message code="application.projecttype.label"/></th>
						<th><spring:message code="application.project.category.label"/></th>
						<%-- <th ><spring:message code="Client_Contact_Person_Master.organisationName"/></th>--%>
						<th><spring:message code="group.groupName"/></th> 
						<th >Total Outlay</th>
						<th><spring:message code="forms.action"/></th>
 					</tr>
				</thead>
				<tbody class="">
					<c:forEach items="${activeGroupData}" var="appdetail" varStatus="theCount">
							<tr >
						    <td>${theCount.count}</td>
							<td ><a class="bold" title='Click Here to View Proposal Details' onclick="viewProjectDetails('/PMS/mst/proposalDetails/${appdetail.encNumId}')">${appdetail.proposalTitle}</a></td>
<%-- 							<td>${appdetail.businessTypeName}</td>
 --%>							<td>${appdetail.projectTypeName}</td>
							<td>${appdetail.categoryName}</td>
							<%-- <td >${appdetail.organisationName }</td>--%>
							<td >${appdetail.groupName}</td> 
							
							<td id="amount_${theCount.count}" class="convert_lakhs">${appdetail.totalProposalCost}</td>	
					<td>
							 <div class="dropdown show">
 							 <a class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" onmouseover="viewApplicationStatus(${appdetail.numId})" aria-haspopup="true" aria-expanded="false">
  								<i class="icon-th-large icon-1halfx blue pad-top" aria-hidden="true"></i>
 						 </a>
  								<ul class="dropdown-menu pull-right" aria-labelledby="dropdownMenuLink" id="${appdetail.numId}">
  							</ul>
						</div>
							</td>	
						</tr>
					</c:forEach>
				</tbody>
			</table>
			</div>
			<!--End of data table-->
		</fieldset>
	</div>
	<!--End of datatable_row-->
</div>
</div>

</section>



<script>

</script>
	
</body>
<script type="text/javascript" src="/PMS/resources/app_srv/PMS/master/js/PMOApplicationList.js"></script>

</html>