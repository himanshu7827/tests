<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html lang="en">
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!--     <link rel="stylesheet"
	href="/PMS/resources/app_srv/PMS/global/css/select2.css">
    <link rel="stylesheet" href="/PMS/resources/app_srv/PMS/global/css/jquery-ui.css">
	
	<link rel="stylesheet" href="/PMS/resources/app_srv/PMS/master/css/bootstrapValidator.css">	 -->

</head>
<body>

	<section id="main-content" class="main-content merge-left">

	<div class=" container wrapper1">
		<div class="row">
			<div class=" col-md-12 pad-top-double  text-left">
				<ol class="breadcrumb bold">
					<li>Home</li>
					
					<li class="active"><spring:message code="menu.employee.update.profile"/></li>
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
		<div class="container pad-bottom">
		<sec:authorize access="hasAuthority('READ_EMPLOYEE_MST')">
		<form:form id="form0" modelAttribute="employeeMasterModel">
		
			

     <c:if test = "${profileFlag == 0}">
				<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12" id="searchDiv">
					<div class="panel panel-info panel-info1">

						<div class="panel-body ">
							<div class="row pad-top" id="searchEmployeeDiv">
								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group ">
									<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-center">

										<label class="label-class"><spring:message code="employee.corporateEmployeeId"/>:<span
											style="color: red;">*</span></label>
									</div>


									<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 ">
										<div class="input-container">
											<i class="fa fa-user icon"></i>
											<form:input class="input-field"
												 path="searchEmployeeId"/>
											<form:errors path="searchEmployeeId" cssClass="error" />
										</div>
									</div>
									<div class="col-md-4 col-lg-4 col-sm-4 col-xs-12 ">
										<button type="button" class="btn btn-orange"
											id="searchEmployee">
											<span class="pad-right"><i class="fa fa-search"></i></span>Search
											Employee
										</button>
									</div>
									<div id="empExistDiv">
									<span style="color: red;">Employee does not exist
									</span>
									</div>
								</div>
							</div>
						</div>
					</div>
					</div>
					</c:if>
			

		</form:form>
</sec:authorize>

<sec:authorize access="hasAnyAuthority('READ_EMPLOYEE_MST','USERPROFILE')">
		<form:form id="form1" modelAttribute="employeeMasterModel" action="/PMS/mst/UpdateEmployeeMasterProfile" method="post">
			<%-- <form:hidden path="numId"/> --%>
			<form:hidden path="updateFlag" />
			<form:hidden path="numId" />
		   <form:hidden path="employeeName" />
		   <form:hidden path="officeEmail" />
		   		   <form:hidden path="dateOfBirth" />
		   		   <form:hidden path="dateOfJoining" />
		   
			
		<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
				<div class="panel panel-info panel-info1">
					<div class="panel-heading"><h3 class="text-center text-shadow"><spring:message code="menu.employee.master.label"/></h3></div>
					<div class="panel-body">
					
					<div class="row pad-top">
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

								<label class="label-class"><spring:message code="Employee_Type_Master.employeeTypeName"/>:<span
									style="color: red;">*</span></label>
							</div>

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container">
			<form:select class="select2Option" path="employeeTypeId" style="width:100%">
								<form:option value="${List.numId}">
								<c:out value="${List.strEmpTypeName}"></c:out>
						    </form:option>
				</form:select> 
								<form:errors path="employeeTypeId" cssClass="error" />
								</div>
							</div>
						</div>
					
					
											<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group" id="employeeIdDiv">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<label class="label-class" for="pwd"><spring:message code="employee.corporateEmployeeId"/>:<span style="color: red;">*</span></label>
							</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container" >
									<i class="fa fa-key icon"></i> 			
									<form:input  class="input-field"  placeholder="Employee Id" path="numId" readonly="true" disabled="true"/>

									<form:errors path="numId" cssClass="error" />
								</div>
							</div>
						</div>
						</div>
						
						<div class="row pad-top">
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

								<label class="label-class"><spring:message code="employee_Role.employee_Name"/>:<span
									style="color: red;">*</span></label>
							</div>

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container">
									<i class="fa fa-user icon"></i> 
									<form:input class="input-field" placeholder="Employee Name" path="employeeName" readonly="true" disabled="true" />
									<form:errors path="employeeName" cssClass="error" />
								</div>
							</div>
						</div>	
						
										
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

								<label class="label-class"><spring:message code="employee.officeEmail"/>:<span
									style="color: red;">*</span></label>
							</div>

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container">
									<i class="fa fa-envelope icon"></i> 
									<form:input class="input-field" placeholder="Office Email" path="officeEmail" disabled="true"/>
									<form:errors path="officeEmail" cssClass="error" />
								</div>
							</div>
						</div>
						</div>
						
						<div class="row pad-top">
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<label class="label-class" for="pwd"><spring:message code="employee.alternateEmail"/>:</label>
							</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container">
									<i class="fa fa-envelope icon"></i> 
									<form:input class="input-field"  placeholder="Alternate Email" path="alternateEmail"/>
									<form:errors path="alternateEmail" cssClass="error" />
								</div>
							</div>
						</div>
					
															
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

								<label class="label-class"><spring:message code="mobileNo.label"/>:<span
									style="color: red;">*</span></label>
							</div>

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container">
									<i class="fa fa-user icon"></i> 
									<form:input class="input-field" placeholder="Mobile Number" path="mobileNumber"/>
									<form:errors path="mobileNumber" cssClass="error" />
								</div>
							</div>
						</div>
						</div>
						<div class="row pad-top">
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<label class="label-class" for="pwd"><spring:message code="master.contact"/>:</label>
							</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container">
									<i class="fa fa-key icon"></i> 
									<form:input class="input-field"  placeholder="Contact Number" path="contactNumber"/>
									<form:errors path="contactNumber" cssClass="error" />
								</div>
							</div>
						</div>
					

					
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<label class="label-class"><spring:message code="employee.dateofBirth"/>:<span
									style="color: red;">*</span></label>
							</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container">
									<i class="fa fa-calendar icon"></i>
									 <form:input class="input-field" readonly='true' path="dateOfBirth" placeholder="Date of Birth"/>							
								<form:errors path="dateOfBirth" cssClass="error" />
								</div>
							</div>
						</div>
						</div>
						
						<div class="row pad-top">
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<label class="label-class" for="pwd"><spring:message code="employee.dateofJoining"/>:<span
									style="color: red;">*</span></label>
							</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container">
									<i class="fa fa-calendar icon"></i>
									 <form:input class="input-field" readonly='true' path="dateOfJoining" placeholder="Date of Joining"/>							
								<form:errors path="dateOfJoining" cssClass="error" />
								</div>
							</div>
						</div>
					
					
										
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<label class="label-class" for="pwd"><spring:message code="employee.gender"/>:<span style="color: red;">*</span></label>
							</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container">
									<form:select path="gender" class="select2Option">
									<form:option value="${employeeMasterModel.gender}">
								<c:out value="${employeeMasterModel.gender}"></c:out>
						    </form:option>
						    
									</form:select>
									<form:errors path="gender" cssClass="error" />
									
								</div>
							</div>
						</div>
						</div>
						<div class="row pad-top">
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

								<label class="label-class"><spring:message code="employee.designation"/>:<span
									style="color: red;">*</span></label>
							</div>

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container">
								<form:select  path="designation" class="select2Option" >
							<form:option value="${List4.numId}">
								<c:out value="${List4.designationName}"></c:out>
								</form:option>
							</form:select>
							<form:errors path="designation" cssClass="error" />
								</div>
							</div>
						</div>						
					
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

								<label class="label-class"><spring:message code="group.organisationName"/>:</label>
							</div>

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container">
										<form:select  path="organisationId" class="select2Option" >
								<form:option value="${List1.numId}">
								<c:out value="${List1.organisationName}"></c:out>
								</form:option>
				</form:select>
								<form:errors path="organisationId" cssClass="error" />
									
								</div>
							</div>
						</div>
					</div>
					
						<div class="row pad-top">
						<c:if test="${groupList != null }">
						
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

								<label class="label-class"><spring:message code="group.groupName"/>:</label>
							</div>


							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container">
										<form:select class="select2Option" path="groupId">
								<form:option value="${groupList.numId}">
								<c:out value="${groupList.groupName}"></c:out>
								</form:option>
				</form:select>
								<form:errors path="groupId" cssClass="error" />
									
								</div>
							</div>
						</div>
												</c:if>
					
					<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<label class="label-class" for="pwd"><spring:message code="employee.category"/>:<span style="color: red;">*</span></label>
							</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container">
									<form:select path="category" class="select2Option">
												<form:option value="${employeeMasterModel.category}">
								<c:out value="${employeeMasterModel.category}"></c:out>
								</form:option>
											
									</form:select>
									 <form:errors path="category" cssClass="error" />
									
								</div>
							</div>
						</div>
						
	
					</div>
	
					
					
					<sec:authorize access="hasAnyAuthority('WRITE_EMPLOYEE_MST','USERPROFILE')">
					<!-- <div class="row pad-top pad-bottom center">
						<button type="button" class="btn btn-success font_18" id="save">
							<span class="pad-right"><i class="fa fa-folder"></i></span>Save
						</button>
						<button type="reset" class="btn btn-info reset font_18" id="reset">
							<span class="pad-right"><i class="fa fa-refresh"></i></span>Reset
						</button>
					</div> -->
						<div class="row pad-top form_btn center">

										<button type="button" class="btn btn-primary font_14"
											id="save">
											<span class="pad-right"><i class="fa fa-folder"></i></span>Update
										</button>
									<!-- 	<button type="button" class="btn btn-primary reset font_14"
											id="reset">
											<span class="pad-right"><i class="fa fa-refresh"></i></span>Reset
										</button> -->
									</div>
					
					</sec:authorize>
					</div>
					</div>
					</div>
					
		</form:form> 
		</sec:authorize>
		

</div>
	</div>

	</section>
	<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/master/js/updateEmployeeMaster.js"></script>
 <!--    <script type="text/javascript" src="/PMS/resources/app_srv/PMS/master/js/bootstrapValidator.js"></script>
	<script src="/PMS/resources/app_srv/PMS/global/js/jquery-ui.js"></script>
	<script type="text/javascript" src="/PMS/resources/app_srv/PMS/global/js/select2.min.js"></script> -->
	

</body>
</html>