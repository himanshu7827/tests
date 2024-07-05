<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html lang="en">
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">


<style>
.oddRow {
	background-color: #c2c3ea;
}
</style>


</head>

<body>


	<section id="main-content" class="main-content merge-left">

		<div class=" container wrapper1">
			<div class="row">
	
			</div>
			<div class="row">
				<div class=" col-md-12 pad-top-double  text-left">
					<ol class="breadcrumb bold">
						<li>Home</li>						
						<li class="active"><spring:message code="form.label.activeuser"/></li>
					</ol>
				</div>
			</div>
			<div class="row "></div>
			<c:if test="${message != null && message != ''}">
				<c:choose>
					<c:when test="${status=='success'}">
						<div id="userinfo-message">
							<p class="success_msg">${message}</p>
						</div>
					</c:when>
					<c:otherwise>
						<div id="userinfo-message">
							<p class="error_msg">${message}</p>
						</div>
					</c:otherwise>
				</c:choose>
			</c:if>

		<sec:authorize access="hasAuthority('READ_ALL_SESSIONS')">
			<table class="table table-striped table-bordered"
								style="width: 100%">
				<thead class="datatable_thead bold ">
					<tr>
						<th><spring:message code="dashboard.hr.employee.label"/></th>
						<th><spring:message code="dashboard.employee.type.label"/></th>
						<th><spring:message code="Client_Contact_Person_Master.contactPersonMobileNumber"/></th>
						<th><spring:message code="employee.officeEmail"/></th>
						<th><spring:message code="employee.designation"/></th>
						
						
					</tr>
				</thead>
				<tbody>
					 <c:forEach items="${users}" var="user" >
						<tr>					
						<td>${user.employeeName}</td>
						<td>${user.employeeType}</td>
						<td class="text-right">${user.mobileNumber}</td>
						<td>${user.officeEmail}</td>
						<td>${user.designation}</td> 
					
					</tr>
					</c:forEach> 
				</tbody>
			</table>
		</sec:authorize>
	</div>
	</section>
	<script type="text/javascript" src="/PMS/resources/app_srv/PMS/transaction/js/allSessions.js"></script>
</body>
</html>