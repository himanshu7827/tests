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
			<li class="active"><spring:message code="menu.master.interview.exit.hr.label"/></li>
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
		
<%-- 		<sec:authorize access="hasAuthority('')">	
 --%>		<form:form id="form1" name="form1" modelAttribute="exitInterviewModel"  method="post">
			<form:hidden path="numIds"/>
			<form:hidden path="numId"/>
			<div class="container ">
		<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
				<div class="panel panel-info panel-info1">
					<div class="panel-heading panel-heading-fd"><h4 class="text-center "><spring:message code="menu.master.interview.exit.hr.label"/></h4></div>
					<div class="panel-body text-center">
			
				<div class="row pad-top ">
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
								<label class="label-class"><spring:message code="employeeMaster.name.label"/> :<span
									style="color: red;">*</span></label>
							</div>
					<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
				<form:select path="empId"  class="select2Option"  style="width:100%">
				          <form:option value="0">--Select Employee---</form:option>
							<c:forEach items="${data}" var="empData" >
								<form:option value="${empData.empId}">
								<c:out value="${empData.empName} [Employee Id:${empData.empId}]"></c:out>
								
								</form:option>
							</c:forEach>
				</form:select>
						
						</div>
						</div>
						</div>
				</div>
					
			
					</div>
					</div>
			
		</div>
		</div>
		</form:form>
<%-- 		</sec:authorize>
 --%>
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
						<th class="width20"><spring:message code="master.select"/></th>					
						<th><spring:message code="Project_Payment_Schedule.projectName"/></th>
						<th><spring:message code="employee_Role.role_Name"/></th> 
						<th><spring:message code="employee_Role.group_Name"/></th>
						<th><spring:message code="docupload.document.startDate"/></th>
						<th><spring:message code="employee_Role.end_Date"/></th>
						
					</tr>
				</thead>
				<tbody class="">
					
				</tbody>
			</table>
			<div class="row pad-top  form_btn center">
			<button type="button" class="btn btn-primary font_14 releaseEmployee" >
			<span class="pad-right" ><i class="fa fa-folder"></i></span>Release
			</button>
			</div>
			
			<!--End of data table-->
		</fieldset>
	</div>
	<!--End of datatable_row-->
	
</div>
	</div>
</div>
</section>

	
<script type="text/javascript" src="/PMS/resources/app_srv/PMS/transaction/js/ExitInterviewByHr.js"></script>
	
</body>
</html>