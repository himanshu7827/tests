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
						<!-- <li>Consumer Forms For Medical Devices</li> -->
						<li class="active">Project Expenditure Details</li>
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

			<form:form id="form1" modelAttribute="projectExpenditureDetailsModel"
				action="/PMS/mst/saveUpdateProjectExpenditureDetails" method="post">
				<form:hidden path="numId" />
				<form:hidden path="idCheck" />
				<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
					<div class="panel panel-info panel-info1">
						<div class="panel-heading">
							<h3 class="text-center text-shadow">Project Expenditure Details</h3>
						</div>
						<div class="panel-body">

							<div class="row pad-top" id="stage_name_row">

								<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-6">
										<label class="label-class"><spring:message code="project_Expenditure_Details.project_Name"/>:<span
											style="color: red;">*</span></label>
									</div>
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
										<div class="input-container">
											<form:select path="numProjectId"  class="select2Option">
												<form:option value="0">-- Select Project --</form:option> 
												
												  
											 <c:forEach items="${projectList}"
													var="projectList">
													<form:option value="${projectList.numId}">
														<c:out value="${projectList.strProjectName}"></c:out>
													</form:option> 
												</c:forEach> 
											</form:select>
										</div>
									</div>
								</div>

						

								<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-6">
										<label class="label-class"><spring:message code="project_Expenditure_Details.budgetHead_Name"/>:<span
											style="color: red;">*</span></label>
									</div>
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
									 	<div class="input-container">
											<form:select path="numBudgetHeadId" id="numBudgetHeadId" class="select2Option">
												<form:option value="0">-- Select BudgetHead --</form:option> 
												  
											 	<c:forEach items="${budgetHeadList}"
													var="budgetHeadList">
													<form:option value="${budgetHeadList.numId}">
														<c:out value="${budgetHeadList.strBudgetHeadName}"></c:out>
													</form:option> 
												</c:forEach> 
											</form:select>
										</div> 
										
									</div>
								</div>
							</div>
									<div class="row pad-top" >
								<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-6">
										<label class="label-class"><spring:message code="project_Expenditure_Details.expenditure_Head_Name"/>:<span
											style="color: red;">*</span></label>
									</div>
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
										<div class="input-container">
											<form:select path="numExpenditureHeadId" id="numExpenditureHeadId" class="select2Option">
												<form:option value="0">-- Select Expenditure Head --</form:option> 
												
												<c:forEach items="${expenditureHeadList}"
													var="expenditureHeadList">
													<form:option value="${expenditureHeadList.numId}">
														<c:out value="${expenditureHeadList.strExpenditureHeadName}"></c:out>
													</form:option> 
												</c:forEach>  
											</form:select>
										</div>
									</div>
								</div>
					

							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<label class="label-class" ><spring:message code="project_Expenditure_Details.expenditure_Date"/>:<span style="color: red;">*</span></label>
									</div>
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
										 <div class="input-container">
											<i class="fa fa-calendar icon"></i>
									 		<form:input class="input-field" readonly='true' path="dtExpenditureDate" id="dtExpenditureDate" placeholder="Expenditure Date"/>							
											<form:errors path="dtExpenditureDate" cssClass="error" />
										</div> 
	
									</div>
							</div> 
							</div>
							<div class="row pad-top" >
							
								<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
								<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
										<label class="label-class"><spring:message code="project_Expenditure_Details.expenditure_Amount"/>:<span style="color: red;">*</span>
										</label>
									</div>
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-6">
										<div class="input-container">
											<i class="fa fa-inr icon"></i>
											<form:input class="input-field" placeholder="Expenditure Amount" id="numExpenditureAmount" 
												path="numExpenditureAmount" />
											<form:errors path="numExpenditureAmount" cssClass="error" />
										</div>
									</div>
								</div>
								
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
										<label class="label-class"><spring:message code="project_Expenditure_Details.expenditure_Description"/>:<span style="color: red;">*</span>
										</label>
									</div>
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-6">
										<div class="input-container">
											<i class="fa fa-th-large icon pad-top-double"></i>
												<form:textarea class="form-control" placeholder="Expenditure Description" 
									path="strExpenditureDescription"></form:textarea>
									<form:errors path="strExpenditureDescription" cssClass="error" />
										</div>
									</div>
								</div>
								 
								</div>
			
						
								<div class="row pad-top " id="frm">
							
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group ">
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<label class="label-class">Status :</label>
										</div>
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 ">
												<form:radiobutton path="valid" value="true" id="toggle-on" />
												<form:label path="valid" for="toggle-on"
													class="btn inline  zero round no-pad">
													<span>Active</span>
												</form:label>
											</div>
											<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
												<form:radiobutton path="valid" value="false" id="toggle-off" />

												<form:label path="valid" for="toggle-off"
													class="btn round inline zero no-pad">
													<span class="">Inactive</span>
												</form:label>
											</div>
										</div>
									</div>
								
</div>


		<sec:authorize access="hasAuthority('WRITE_PROJECT_EXPENDITURE_DETAILS')"> 

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
							<!-- <span class="pull-right padded margin-right bg-blue-text" id="addnewrecord">
								<a href="#"
								class="new_record blue no-underline font_16 blink text-shadow"
								id="new_record" title="Click Here to Add New Record">Click
									Here to Add New Record</a>
							</span> -->

						</div>
					</div>
					</div>
	
			</form:form>
			
	

	<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
	<div class=" datatable_row pad-bottom">
		<fieldset class="fieldset-border">

			<legend class="bold legend_details">Details :</legend>
			<table id="datatable" class="table table-striped table-bordered"
				style="width: 100%">
				<thead class="datatable_thead bold ">
								<tr>
									<th class="width20 check"><spring:message code="serial.no"/></th>
									<th><spring:message code="Project_Payment_Schedule.projectName"/></th>
									<th><spring:message code="project_Expenditure_Details.budgetHead_Name"/></th>
									<th><spring:message code="project_Expenditure_Details.expenditure_Head_Name"/></th>
									<th><spring:message code="project_Expenditure_Details.expenditure_Date"/></th> 
									<th><spring:message code="project_Expenditure_Details.expenditure_Amount"/></th>
									<th><spring:message code="project_Expenditure_Details.expenditure_Description"/></th>
									<th><spring:message code="projectMaster.status"/></th> 
									<th><spring:message code="forms.edit"/></th>
									
									
									<th class="hidden" >Project Id</th>
									<th class="hidden">BudgetHea Id</th>
									<th class="hidden">Expenditure Head Id</th>
									
								</tr>
							</thead>
						</table>

						<!--End of data table-->
					</fieldset>
				</div>
				<!--End of datatable_row-->
			</div>
			</div>
			<!--End of main-container-->
			<!-- </div> -->
			<!-- end of container-->
		</section>



	 <script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/master/js/projectExpenditureDetails.js"></script> 


</body>
</html>