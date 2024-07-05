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
			<li class="active"><spring:message code="menu.employee.Role.Map.label"/></li>
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
		
 		<sec:authorize access="hasAuthority('WRITE_EMP_ROLE_MAP_MST')">	
 		<form:form id="form1" name="form1" modelAttribute="employeeMasterModel" action="/PMS/mst/saveEmployeeRoleMapping" method="post">
			<form:hidden path="numId"/>
			<div class="container">
		<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
				<div class="panel panel-info panel-info1">
					<div class="panel-heading panel-heading-fd"><h4 class="text-center "><spring:message code="menu.employee.Role.Map.label"/></h4></div>
					<div class="panel-body text-center">
			
					<div class="row pad-top ">
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
								<label class="label-class"><spring:message code="employeeMaster.name.label"/>:<span
									style="color: red;">*</span></label>
							</div>
					<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
				<form:select path="employeeId"  class="select2Option"  style="width:100%">
				          <form:option value="0">--Select Employee---</form:option>
							<c:forEach items="${List}" var="empData" >
								<form:option value="${empData.numId}">${empData.employeeName}&nbsp;&nbsp;<p>[${empData.numId }]</p>
								</form:option>
							</c:forEach>
				</form:select>
						<form:errors path="employeeId" cssClass="error" />
						
						</div>
						</div>
						</div>
				</div>
					
						
						<div class="row pad-top ">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
								<label class="label-class"><spring:message code="Client_Contact_Person_Master.contactPersonRoles"/>:<span
									style="color: red;">*</span></label>
							</div>

							 <div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
										<form:select  path="roles" class="select2Option" style="width:100%">
										 <form:option value="0">-- Select Role --</form:option>
							  <c:forEach items="${List1}" var="role" >
								<form:option value="${role.id}">
								<c:out value="${role.name}"></c:out>
								</form:option>
							</c:forEach> 
				</form:select>
								<form:errors path="roles" cssClass="error" />
									
								</div>
							</div>
							</div>
							</div>
										
					<div class="row pad-top  form_btn">
					<!-- <button type="button" class="btn btn-primary reset font_14" id="searchId">
							<span class="pad-right"><i class="fa fa-refresh"></i></span>Search
						</button> -->
					
						<button type="button" class="btn btn-primary font_14" id="save">
							<span class="pad-right"><i class="fa fa-folder"></i></span>Save
						</button>
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
 		
	
</div>
</section>

 <script type="text/javascript" src="/PMS/resources/app_srv/PMS/master/js/EmployeeRolesMapping.js"></script>

	
	
</body>
</html>