<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
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
						<li class="active"><spring:message
								code="menu.employee.release.label" /></li>
					</ol>
				</div>
			</div>
			<div class="padding-bottom"></div>
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
			<div class="container pad-bottom pad-top">				
				<div class="panel panel-info panel-info1">
					<div class="panel-body ">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group ">
							<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-center">
								<label class="label-class">Search Based On:<span
									style="color: red;">*</span></label>
							</div>
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 ">
								<div class="input-container">
									<i class="fa fa-user icon"></i>
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
						
						<div id="empExistDiv" class="pad-top hidden">
							<span style="color: red;">Employee does not exist</span>
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
	</section>
	<section id="employeeDetails" class="hidden">
		<div class="container">
		<div class="panel panel-info panel-info1">
			
			<div class="panel-body">
				<div class="center pad-top pad-bottom bold"> This Information is loaded based on <span class="green" id="dataLoadedBasedOn"></span></div>
				<div class="row pad-top">
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<label class="label-class"><spring:message code="employeeMaster.name.label"/> :</label>
							</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div id="employeeName"></div>
							</div>
						</div>
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<label class="label-class"><spring:message code="employeeMaster.officemail.label"/>:</label>
							</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div id="emailId"></div>
							</div>
						</div>
					</div>
					<div class="row pad-top">
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<label class="label-class"><spring:message code="mobileNo.label"/>:</label>
							</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div id="mobileNo"></div>
							</div>
						</div>
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<label class="label-class"><spring:message code="employee.employmentstatus"/>:</label>
							</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div id="employmentstatus"></div>
							</div>
						</div>
					</div>
					<div class="row pad-top">
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<label class="label-class"><spring:message code="employee.dateofBirth"/>:</label>
							</div>

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div id="dob"></div>
							</div>
						</div>
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<label class="label-class"><spring:message code="employee.dateofJoining"/>:</label>
							</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div id="doj"></div>
							</div>
						</div>
					</div>
					
					<div class="row pad-top">
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<label class="label-class"><spring:message code="employee_Role.group_Name"/>:</label>
							</div>
							<input id="employeeId" type ="hidden"/>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div id="groupName"></div>
							</div>
						</div>
						
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<label class="label-class"><spring:message code="milestone.review.strRemarks"/>:<span
													style="color: red;">*</span></label>
							</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container">
									<i class="fa fa-comment icon"></i>
									 <textarea id="releaseRemark" class="form-control" rows="2"></textarea>							
								</div>
							</div>
						</div>
						
					</div>
					
					<div class="row pad-top">
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<label class="label-class"><spring:message code="employee.dateOfResign"/>:</label>
							</div>
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container">
									<i class="fa fa-calendar icon"></i>
									 <input class="input-field" readonly='readonly' id="dateOfResignation" placeholder="Date of Resign"/>								
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
									 <input class="input-field" readonly='readonly' id="dateOfRelease" placeholder="Date of Release"/>
								</div>
							</div>
						</div>
					</div>
					
					<div class="pad-top pad-bottom"><b class="red">Note : </b> if Effective Upto Date is not chosen explicitly, Release date will be considered. </div>
	    		
	    			<table class="table table-responsive table-bordered table-strip">
						<thead>
							<tr>
								<th width="30%"><spring:message code="Designation_Master.designationName"/></th>
								<th width="25%"><spring:message code="project_Team_Details.role_Name"/></th>								
								<th width="15%"><spring:message code="project_Team_Details.effective_From"/></th>
								<th width="15%"><spring:message code="project_Team_Details.effective_Upto"/> <spring:message code="form.if.any.label"/></th>
								<th width="15%"><spring:message code="project_Team_Details.effective_Upto"/></th>
							</tr>
						</thead>
						
						<tbody id="teamDetailsBody">
							
						</tbody>
					</table>
					
				</div>
			</div>	
			
			<div class="row pad-top pad-bottom form_btn center">
						<button type="button" class="btn btn-primary font_14" id="save">
							<span class="pad-right"><i class="fa fa-folder"></i></span>Release Employee
						</button>						
					</div>
			
		</div>
	</section>
	
	<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/master/js/releaseEmployee.js"></script>

</body>
</html>