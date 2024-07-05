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
			<li class="active"><spring:message code="menu.master.employesalary.label"/></li>
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
        
        <%-- <div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
				<div class="panel panel-info panel-info1">
					<div class="panel-heading panel-heading-fd"><h4 class="text-center "><spring:message code="menu.master.employesalary.label"/></h4></div>
		</div> --%>
	
		<sec:authorize access="hasAuthority('WRITE_EMP_SALARY_MST')">
				<div class="container ">

					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
						<div class="col-md-2 col-lg-2 col-sm-12 col-xs-12">
							<span class="bold  font_16 ">Select an option:</span>
						</div>
						<div class="col-md-3 col-lg-3 col-sm-12 col-xs-12">
							<span class="bold orange font_14"> <input type="radio"
								name="radioButton" value="value1"> Update individual
								Employee Salary
							</span>
						</div>
						<div class="col-md-4 col-lg-4 col-sm-12 col-xs-12">
							<span class="bold orange font_14"> <input type="radio"
								name="radioButton" value="value2"> Updating Employee
								Salary via excel file
							</span>

						</div>
					</div>
				</div>
				<form:form id="form1" name="form1" modelAttribute="employeeSalaryModel" action="/PMS/mst/SaveEmployeeSalaryMaster" method="post" class="hidden row pad-top">
			<form:hidden path="numIds"/>
			<form:hidden path="numId"/>
			<div class="container ">
		<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
				<div class="panel panel-info panel-info1">
					<div class="panel-heading panel-heading-fd"><h4 class="text-center "><spring:message code="menu.master.employesalary.label"/></h4></div>
					<div class="panel-body text-center">
			
				<div class="row pad-top ">
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
								<label class="label-class"><spring:message code="employeeMaster.name.label"/>:<span
									style="color: red;">*</span></label>
							</div>
					<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
				<form:select path="employeeId"  class="select2Option" style="width:100%">
				          <form:option value="0">--Select Employee---</form:option>
							<c:forEach items="${data}" var="empData" >
								<form:option value="${empData.numId}">${empData.employeeName}
								</form:option>
							</c:forEach>
				</form:select>
						
						</div>
						</div>
						</div>
				</div>
					
			<div class="row  ">
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
								<label class="label-class" for="pwd"><spring:message code="employee_Role.start_Date"/>:<span
									style="color: red;">*</span> </label>
							</div>
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
									<i class="fa fa-calendar icon"></i>
									 <form:input class="input-field" readonly='true' path="startDate" />							
								<form:errors path="startDate" cssClass="error" />
								</div>
							</div>
				</div>
				</div>
				<div class="row  ">
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
								<label class="label-class" for="pwd"><spring:message code="employee_Role.end_Date"/>:</label>
							</div>
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
									<i class="fa fa-calendar icon"></i>
									 <form:input class="input-field" readonly='true' path="endDate" />							
								<form:errors path="endDate" cssClass="error" />
								</div>
							</div>
				</div>
				</div>
				
			<div class="row  ">
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
								<label class="label-class"><spring:message code="Employee_Salary"/>:<span
									style="color: red;">*</span></label>
							</div>

							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
									<i class="fa fa-inr icon"></i> 
									<form:input class="input-field" placeholder="Salary" path="salary"/>
									<form:errors path="salary" cssClass="error" />
								</div>
							</div>
						</div>
						</div>
						
						
					<div class="row ">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
								<label class="label-class"><spring:message
													code="forms.status" /> :</label>
							</div>
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
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
					</div>	
					
					
					<div class="row pad-top  form_btn">
					
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
		<form:form id="form" class="employeeSalaryModel hidden row pad-top" enctype="multipart/form-data" action="/PMS/mst/importSalaryDetail" method="post">
				<div class="container ">
		<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
				<div class="panel panel-info panel-info1">
					<div class="panel-heading panel-heading-fd"><h4 class="text-center "><spring:message code="menu.master.employesalary.label"/></h4></div>
											<h5 class="red"><p>*Ensure Row1 is column Heading.</p>
											<p>*Include column of excel file:<span class="blue">Employee Id,Employee Salary and Start Date.</span></p>										
											<p>*Start Date should be in <span class="blue">DD/MM/YYYY</span> Format.</p>																					
											</h5>
								
				 <div class="row pad-top ">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">

								<label class="label-class"><b>Select a file to Upload:</b></label>
							</div>

							 <div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
								<input type="file" class="input-field fileUpload" name="salaryDocumentFile" id="salaryDocumentFile" />
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

	<!--End of panel-->
	<!--Start data table-->
	<div class="container" >
	<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
	<div class=" datatable_row pad-bottom">
						<div id="salaryTable">
	
		<fieldset class="fieldset-border">

			<legend class="bold legend_details">Details :</legend>
			<table id="datatable" class="table table-striped table-bordered"
				style="width: 100%">
				<thead class="datatable_thead bold ">
					<tr>
						<th class="width20"><spring:message code="master.select"/></th>					
						<th><spring:message code="employee_Role.start_Date"/></th>
						<th><spring:message code="employee_Role.end_Date"/></th>
						<th><spring:message code="Employee_Salary"/></th>
						 <th><spring:message code="forms.status"/></th> 
						<th><spring:message code="forms.edit"/></th>
					</tr>
				</thead>
				<tbody class="">
					
				</tbody>
			</table>
			
			<!--End of data table-->
		</fieldset>
		</div>
	</div>
	<!--End of datatable_row-->
</div>
	</div>
</div>
<!-- <div id="salaryImportFile">
			<span class="fa  btn btn-primary font_14" id="import" aria-hidden="true" data-toggle="modal" data-target="#importFile" >Import File</span>

</div> -->

</section>

 <!-- Modal For import file -->
	<!--<div class="modal amount_receive_deatil_modal" id="importFile"
		tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
		aria-hidden="true" data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title  center" id="exampleModalLabel">Upload File</h4>
 					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				
				<div class="modal-body">

					
					
				</div>
			</div>
		</div>
	</div>
 -->
	
<script type="text/javascript" src="/PMS/resources/app_srv/PMS/master/js/EmployeeSalary.js"></script>
	
</body>
</html>