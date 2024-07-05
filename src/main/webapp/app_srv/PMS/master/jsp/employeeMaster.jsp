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
					
					<li class="active"><spring:message code="menu.employee.master.label"/></li>
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
		<div class="container pad-bottom ">
		<sec:authorize access="hasAuthority('READ_EMPLOYEE_MST')">
		<div class="container ">

					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
						<div class="col-md-2 col-lg-2 col-sm-12 col-xs-12">
							<span class="bold  font_16 ">Select an option:</span>
						</div>
						<div class="col-md-3 col-lg-3 col-sm-12 col-xs-12">
							<span class="bold orange c"> <input type="radio"
								name="radioButton" value="value1"> Update individual
								Employee Record
							</span>
						</div>
						<div class="col-md-4 col-lg-4 col-sm-12 col-xs-12">
							<span class="bold orange font_14"> <input type="radio"
								name="radioButton" value="value2"> Updating Employee
								Records via excel file
							</span>

						</div>
					</div>
				</div>
		
		<form:form id="form0" class="hidden" modelAttribute="employeeMasterModel">
		
			

     <c:if test = "${profileFlag == 0}">
				<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12" id="searchDiv">
					<div class="panel panel-info panel-info1">

						<div class="panel-body ">
							<div class="row pad-top" id="searchEmployeeDiv">
								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group ">
								<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-center">
								<label class="label-class">Search Based On:<span
									style="color: red;">*</span></label>
							</div>
								<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 ">
								<div class="input-container">
									<!-- <i class="fa fa-user icon"></i> -->
									<select  style="width: 200%;" class="select2Option" id="searchDrop" onclick="openSelSearch()">
									<!-- <option value="0">--- Select Option ---</option> -->
									<option value="EmpId">Corporate Employee Id</option>
									<option value="Email">Email</option>
									<option value="EmpName">Employee Name</option>
									</select>									
								</div>
							</div>	
														
						</div>	
							
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group " id="emailDiv">
							<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-center">
								
							</div>
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 ">
								<div class="input-container">
									<i class="fa fa-user icon"></i>
									<input class="input-field" id="searchEmployeeEmail"/>									
								</div>
							</div>									
						</div>
						
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group" id="empIdDiv">
							<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-center">
							
							</div>
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 ">
								<div class="input-container">
									<i class="fa fa-user icon"></i>
									<input class="input-field" id="searchEmployeeId"/>										
								</div>
							</div>									
						</div>
						
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group" id="empNameDiv">
							<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-center">
							
							</div>
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 ">
								<div class="input-container">
									<i class="fa fa-user icon"></i>
									<input class="input-field" id="searchEmployeeName"/>										
								</div>
							</div>									
						</div>
						
						 
						
						<div class="pad-top center">
							<button type="button" class="btn btn-primary font_14" id="searchEmployee">
								<span class="pad-right"><i class="fa fa-search"></i></span>Search Employee
							</button>
						</div>
									<%-- <div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-center">

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
									</div> --%>
									<div id="empExistDiv">
									<span style="color: red;">Employee does not exist
									</span>
									</div>
									<div id="empdetails" class="col-md-12 col-lg-12 col-sm-12 col-xs-12 center pad-top pad-bottom bold" >
						<table id="datatable1"
									class="table table-striped table-bordered example_det" style="width: 100%">
						<thead class="datatable_thead bold">
						<tr>
						<th>Select</th>
						<th>Employee Name</th>
						<th>Designation</th>
						<th>Department</th>
						<th>Employee Id</th>
						<th>EmailId</th>
						<!-- <th>Release Date</th> -->
						</tr>
						</thead>
						<tbody>
						</tbody>
						</table>
					</div>
								</div>
							</div>
						</div>
					</div>
					
	</c:if>
			

		</form:form>
</sec:authorize>

<sec:authorize access="hasAnyAuthority('READ_EMPLOYEE_MST','USERPROFILE')">
		<form:form id="form1" class="hidden" modelAttribute="employeeMasterModel" action="/PMS/mst/saveUpdateEmployeeMaster" method="post">
			<%-- <form:hidden path="numId"/> --%>
			<form:hidden path="updateFlag" />
			<form:hidden path="valid" value="true"/>
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
							<form:option value="0">-- Select Employee Type --</form:option>
							<c:forEach items="${List}" var="empType" >
								<form:option value="${empType.numId}">
								<c:out value="${empType.strEmpTypeName}"></c:out>
						    </form:option>
							</c:forEach>
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
									<c:choose>
									<c:when test="${profileFlag == 1}">
									<form:input  class="input-field"  placeholder="Employee Id" path="numId" onblur="myFunction()" readonly="true"/>
									
									</c:when>
									<c:otherwise>
									<form:input  class="input-field"  placeholder="Employee Id" path="numId" onblur="myFunction()"/>
									
									</c:otherwise>
									</c:choose>
									<form:errors path="numId" cssClass="error" />
								</div>
							</div>
						</div>
						
						
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group" id="thirdPartyDiv">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

								<label class="label-class"><spring:message code="employee.thirdPartyName"/>:<span
									style="color: red;">*</span></label>
							</div>

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container">
				<form:select class="select2Option" path="thirdPartyId" style="width:100%">
							<form:option value="0">-- Select Outsource --</form:option>
							<c:forEach items="${data}" var="thirdParty" >
								<form:option value="${thirdParty.thirdPartyId}">
								<c:out value="${thirdParty.thirdPartyName}"></c:out>
						    </form:option>
							</c:forEach>
				</form:select>
								<form:errors path="thirdPartyId" cssClass="error" />
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
									<form:input class="input-field" placeholder="Employee Name" path="employeeName"  />
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
									<form:input class="input-field" placeholder="Office Email" path="officeEmail" onblur="emailFunction()"/>
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
								<label class="label-class" for="pwd"><spring:message code="postTrackerMaster.post.name"/>:<!-- <span
									style="color: red;">*</span> --></label>
							</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container">
									<form:select  path="postId" class="select2Option" >								
									<form:option value="0">-- Select Post --</form:option>
							 <c:forEach items="${List5}" var="post" >
								<form:option value="${post.numId}">
								<c:out value="${post.strCode} <b>[From:${post.startDate} To:${post.endDate}]</b>"></c:out>
								
								</form:option>
							</c:forEach>
							</form:select>
									<form:errors path="postId" cssClass="error" />
									
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
							 <form:option value="0">-- Select Designation --</form:option>
							 <c:forEach items="${List4}" var="designation" >
								<form:option value="${designation.numId}">
								<c:out value="${designation.strDesignationName}"></c:out>
								
								</form:option>
							</c:forEach>
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
										 <form:option value="0">-- Select Organisation --</form:option>
							<c:forEach items="${List1}" var="org" >
								<form:option value="${org.numId}">
								<c:out value="${org.organisationName}"></c:out>
								</form:option>
							</c:forEach>
				</form:select>
								<form:errors path="organisationId" cssClass="error" />
									
								</div>
							</div>
						</div>
					</div>
					
						<div class="row pad-top">
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

								<label class="label-class"><spring:message code="group.groupName"/>:</label>
							</div>

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container">
										<form:select class="select2Option" path="groupId">
										 <form:option value="0">-- Select Group --</form:option>
							<c:forEach items="${groupList}" var="group" >
								<form:option value="${group.numId}">
								<c:out value="${group.groupName}"></c:out>
								</form:option>
							</c:forEach>
				</form:select>
								<form:errors path="groupId" cssClass="error" />
									
								</div>
							</div>
						</div>
					
					
						
						
<%-- 												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

								<label class="label-class"><spring:message code="Client_Contact_Person_Master.contactPersonRoles"/>:<span
									style="color: red;">*</span></label>
							</div>

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container">
										<form:select class="select2Option" path="roles" >
										 <form:option value="0">-- Select Role --</form:option>
							<c:forEach items="${List3}" var="role" >
								<form:option value="${role.id}">
								<c:out value="${role.name}"></c:out>
								</form:option>
							</c:forEach>
				</form:select>
								<form:errors path="roles" cssClass="error" />
									
								</div>
							</div>
						</div> --%>
                     
				
				
					
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<label class="label-class" for="pwd"><spring:message code="employee.category"/>:<span style="color: red;">*</span></label>
							</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container">
									<form:select path="category" class="select2Option">
												<form:option value="0"> --- Select Category --- </form:option>	
											  <form:option value="General">General</form:option>
											  <form:option value="EWS">EWS</form:option>											    
											   <form:option value="OBC">OBC</form:option>
											   <form:option value="SC">SC</form:option>
											   <form:option value="ST">ST</form:option>
									</form:select>
									 <form:errors path="category" cssClass="error" />
									
								</div>
							</div>
						</div>
						   </div>
											<div class="row pad-top">
						
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

								<label class="label-class"><spring:message code="employee.employmentstatus"/>:<span
									style="color: red;">*</span></label>
							</div>

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container">
									<form:select path="employmentStatus" class="select2Option">
										<form:option value="0"> --- Select Employment Status --- </form:option>	
										<form:option value="Working">Working</form:option>											    
										<form:option value="Notice Period">Notice Period</form:option>
										<form:option value="Relieved">Relieved</form:option>
										<form:option value="Deputation">Deputation</form:option>
									</form:select>
									<form:errors path="employmentStatus" cssClass="error" />
									
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
												<form:option value="0"> --- Select Gender --- </form:option>	
											  <form:option value="Male">Male</form:option>											    
											   <form:option value="Female">Female</form:option>
									</form:select>
									<form:errors path="gender" cssClass="error" />
									
								</div>
							</div>
						</div>					
					</div>
					<div class="row pad-top" id="resignDateDiv">
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<label class="label-class"><spring:message code="employee.dateOfResign"/>:</label>
							</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container">
									<i class="fa fa-calendar icon"></i>
									 <form:input class="input-field" readonly='true' path="dateOfResignation" placeholder="Date of Resign"/>							
								<form:errors path="dateOfResignation" cssClass="error" />
								</div>
							</div>
						</div>
						
						
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<label class="label-class" for="pwd"><spring:message code="employee.dateOfRelease"/>:</label>
							</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container">
									<i class="fa fa-calendar icon"></i>
									 <form:input class="input-field" readonly='true' path="dateOfRelease" placeholder="Date of Release"/>							
								<form:errors path="dateOfRelease" cssClass="error" />
								</div>
							</div>
						</div>
					</div>	
					
					<div class="row pad-top" id="contractDiv">
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<label class="label-class"><spring:message code="employee.contractStartDate"/>:<span
									style="color: red;">*</span></label>
							</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container">
									<i class="fa fa-calendar icon"></i>
									 <form:input class="input-field" readonly='true' path="dtContractStartDate" placeholder="Contract Start Date"/>							
								<form:errors path="dtContractStartDate" cssClass="error" />
								</div>
							</div>
						</div>
						
						
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<label class="label-class" for="pwd"><spring:message code="employee.contractEndDate"/>:<span
									style="color: red;">*</span></label>
							</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container">
									<i class="fa fa-calendar icon"></i>
									 <form:input class="input-field" readonly='true' path="dtContractEndDate" placeholder="Contract End Date"/>							
								<form:errors path="dtContractEndDate" cssClass="error" />
								</div>
							</div>
						</div>
					</div>		
						<div class="row pad-top">
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<label class="label-class"><spring:message code="employee.deputedat"/>:<span
									style="color: red;">*</span></label>
							</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container">
									<form:select path="numDeputedAt" class="select2Option">
												<form:option value="0"> --- Select Deputed At --- </form:option>	
											  <form:option value="1">CDAC</form:option>											    
											   <form:option value="2">At Client</form:option>
									</form:select>
									<form:errors path="numDeputedAt" cssClass="error" />								
								</div>
							</div>
						</div>
						
						</div>
					
	<%-- 				<div class="row ">
						<div class="col-md-4 col-lg-4 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<label class="label-class">Status :</label>
							</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
							<div class="col-md-2 col-lg-2 col-sm-4 col-xs-12 ">
								<form:radiobutton path="valid"  value="true" id="toggle-on"	 />
									<form:label path="valid" for="toggle-on"
										class="btn inline  zero round no-pad">
										<span>Active</span>
									</form:label>
									</div>
									<div class="col-md-2 col-lg-2 col-sm-4 col-xs-12 ">
								<form:radiobutton path="valid" value="false" id="toggle-off" />

									<form:label path="valid" for="toggle-off"
										 class="btn round inline zero no-pad">
										<span class="">Inactive</span>
									</form:label>
</div>
							</div>
						</div>						
					</div>	 --%>	
					
					
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
											<span class="pad-right"><i class="fa fa-folder"></i></span>Save
										</button>
										<button type="button" class="btn btn-primary reset font_14"
											id="reset">
											<span class="pad-right"><i class="fa fa-refresh"></i></span>Reset
										</button>
									</div>
					
					</sec:authorize>
					</div>
					</div>
					</div>
					
		</form:form> 
		
		<form:form id="form" class="employeeMasterModel hidden row pad-top" enctype="multipart/form-data" action="/PMS/mst/importEmployeeDetail"  modelAttribute="employeeMasterModel" method="post">
				<div class="container ">
		<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
				<div class="panel panel-info panel-info1">
					<div class="panel-heading panel-heading-fd"><h4 class="text-center "><spring:message code="menu.employee.master.label"/></h4></div>
						<h5 class="red">*Ensure Row1 is column Heading.</h5>
						<br/>
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 pad-bottom">
							<div class="col-md-6 col-lg-6 col-sm-12 col-xs-12">
								<a href="javascript:void()" onclick="downloadTemplate('Employee Registration and Update format.xlsx')" >Template to Register/Update non outsource Employees </a>
							</div>
							<div class="col-md-6 col-lg-6 col-sm-12 col-xs-12">
								<a href="javascript:void()" onclick="downloadTemplate('OutSourced Employees Registration Format.xlsx')" >Template to Register/Update outsource Employees </a>
							</div>
						</div>		
				 <div class="row pad-top ">
				 
				<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
								<label class="label-class"><b>Select Option:</b><span
											style="color: red;">*</span></label>
							</div>
							 <div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
									<form:select path="optionValue" class="select2Option">
												<form:option value="0"> --- Select --- </form:option>	
											  <form:option value="1">Registration Process</form:option>											    
											   <form:option value="2">Update Process</form:option>
											   <form:option value="3">Outsource Employee</form:option>
									</form:select>
									
								</div>
							</div>
						</div> 
				 
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">

								<label class="label-class"><b>Select a file to Upload:</b></label>
							</div>

							 <div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
								<input type="file" class="input-field fileUpload" name="employeeDocumentFile" id="employeeDocumentFile" />
								</div>
							</div>
						</div>
		          	 	<div class="col-md-12 col-xs-12 col-lg-12 col-sm-12 pad-top ">  
							<div class="row pad-bottom center  form_btn">
								 <button type="button" class="btn btn-primary" id="submit"><span class="pad-right">
								 <i class="fa fa-folder"></i></span>Save</button>
								
							</div>
						</div>
					</div>
				</div>
			</div>
			</div>
                  
		</form:form>
					
					<div id="result">
					<fieldset class="fieldset-border">					
					<legend class="bold legend_details">Details :</legend>					
					<table id="responseTable" class="table table-striped table-bordered"></table>
					</fieldset>
					</div>	
		
		</sec:authorize>
		

</div>
	</div>

	</section>
	<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/master/js/employeeMaster.js"></script>
 <!--    <script type="text/javascript" src="/PMS/resources/app_srv/PMS/master/js/bootstrapValidator.js"></script>
	<script src="/PMS/resources/app_srv/PMS/global/js/jquery-ui.js"></script>
	<script type="text/javascript" src="/PMS/resources/app_srv/PMS/global/js/select2.min.js"></script> -->
	

</body>
</html>