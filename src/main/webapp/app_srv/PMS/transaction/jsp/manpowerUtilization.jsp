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
						<li class="active"><spring:message code="manpower.utilization.form.label"/></li>
					</ol>
				</div>
			</div>
			<c:if test="${not empty referer}">
				<div class="row pull-right">
					<a href="${referer}" class="btn btn-orange font_14">												
						<span class="pad-right"><i class="fa fa-arrow-left"></i></span>Back
					</a>
				</div>
			</c:if>
			
			
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
		
		<div class="container ">

					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
						<div class="col-md-2 col-lg-2 col-sm-12 col-xs-12">
							<span class="bold  font_16 ">Select an option:</span>
						</div>
						<div class="col-md-4 col-lg-4 col-sm-12 col-xs-12">
							<span class="bold orange font_14"> <input type="radio"
								name="radioButton" value="value1"> Update individual
								Employee Utilization
							</span>
						</div>
						<div class="col-md-4 col-lg-4 col-sm-12 col-xs-12">
							<span class="bold orange font_14"> <input type="radio"
								name="radioButton" value="value2"> Updating Employee
								Utilization via excel file
							</span>

						</div>
					</div>
				</div>
		
		<form:form id="form1" modelAttribute="manpowerUtilizationModel" 
					action="/PMS/saveMonthlyUtilizationForEmployee" method="post">	
								
					<div class="container pad-top">
				
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
							<div class="panel panel-info panel-info1">
								<div class="panel-heading panel-heading-fd">
									<h4 class="text-center "><spring:message code="manpower.utilization.form.label"/></h4>
								</div>
								<div class="panel-body text-center">
									<div class="red" id="errorMessage"></div>	
									<div class="green" id="successMessage"></div>	
									<div class="row pad-top">
										<form:hidden path="month"/>
										<form:hidden path="year"/>
										<form:hidden path="numId"/>
												
												<div class="selectMonth">			
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
											<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
				
												<label class="label-class"><spring:message code="form.month.label"/>:<span
													style="color: red;">*</span></label>
											</div>
				
											<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
												<div class="input-container">
													<select class="select2Option" id="tempMonth" style="width:100%">
														<option value="0">-- Select Month --</option>												
													</select>												
												</div>
											</div>
										</div>	
										</div>			
										<div class="importExcel">
											<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
												<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
														<label class="label-class"><b>Select a file to Upload:</b></label>
													
												</div>
												<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
													<div class="input-container">
														<input type="file" class="input-field fileUpload" name="utilizationDocumentFile" id="utilizationDocumentFile" />
													</div>
												</div>
											</div>
											
										<div class="row pad-top ">							
				          	 				<div class="col-md-12 col-xs-12 col-lg-12 col-sm-12 pad-top ">  
												<div class="row pad-bottom center  form_btn">
										 			<button type="button" class="btn btn-primary" id="submit"><span class="pad-right">
										 			<i class="fa fa-folder"></i></span>Save</button>
												</div>
											</div>
										</div>		
						
										<div id="result">
											<fieldset class="fieldset-border">					
											<legend class="bold legend_details">Details :</legend>					
											<table id="responseTable" class="table table-striped table-bordered"></table>
											</fieldset>
										</div>
											
										</div>
										<div class="individualUtilization">
										
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
											<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
				
												<label class="label-class"><spring:message code="employee_Role.employee_Name"/>:<span
													style="color: red;">*</span></label>
											</div>
				
											<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
												<div class="input-container">
												<form:select class="select2Option" path="employeeId" style="width:100%">
											<form:option value="0">-- Select Employee --</form:option>
												
											</form:select>
												<form:errors path="employeeId" cssClass="error" />
												</div>
											</div>
										</div>
										
										
									
									
									<div class="row pad-top">	
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
											<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
				
												<label class="label-class"><spring:message code="Employee_Salary"/>:<span
													style="color: red;">*</span></label>
											</div>
				
											<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
												<div class="input-container">
													<form:input  class="input-field"  placeholder="Employee Id" path="salaryBySystem" readonly="true"/>
												<form:errors path="salaryBySystem" cssClass="error" />
												</div>
											</div>
										</div>
											<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
											<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">				
												<label class="label-class"><spring:message code="manpower.utilization.change.salary.label"/>:</label>
											</div>
				
											<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
												<div class="input-container">
													<form:input  class="input-field"  path="salaryByAuthority" />
												<form:errors path="employeeId" cssClass="error" />
												</div>
											</div>
										</div>
									</div>
									
									<div class="col-md-12 pad-top " id="projectDetailsDiv">
									
										<table class="table table-striped table-bordered" > 
											<thead class="datatable_thead bold ">
												<tr> 
													<th> <spring:message code="Project_Payment_Schedule.projectName"/> </th>
													<th> <spring:message code="manpower.utilization.percentage.label"/></th>
													<%-- <th> <spring:message code="manpower.estimated.salary.label"/></th> --%>
													<th> <spring:message code="manpower.salary.inproject"/> </th>
												</tr>
											<tbody id="projectDetails">
											
											
											</tbody>
										</table>
									
									</div>
								
								<sec:authorize access="hasAuthority('WRITE_MANPOWER_UTIL_MST')">	
									
									<div class="row pad-top  form_btn">

										
										<button type="button" class="btn btn-primary reset font_14"	id="save">
											<span class="pad-right"><i class="fa fa-refresh"></i></span>Save
										</button>

									</div>
									</div>
									</sec:authorize>
								</div>
							</div>
						</div>
					</div>
					</div>
				</form:form>
				
	
		</div> 
		
	</section>


	<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/transaction/js/manpowerUtilization.js"></script>
</body>
</html>