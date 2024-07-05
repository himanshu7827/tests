<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html lang="en">
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<style>
small.help-block {
    display: block;
    margin-top: 2px;
    margin-bottom: 10px;
    color: #a94442 !important;
}

.input-container-short{
    display: -ms-flexbox;
    display: flex;
    width: 100%;
    height: 40;
    font-size: 14px;
	}
	
.icon_short{
    /* padding: 15px; */
    background: /* #d2b47e */ #cbdeff;
    color: white;
    min-width: 50px;
    height: 40;
    text-align: center;
    color: #5577c1;
    padding-top: 13px;
    padding-bottom: 13px;
}

</style>

</head>
<style>
.bg_grey{
background: LemonChiffon !important;
}
</style>
<body>
	<section id="main-content" class="main-content merge-left">
		<div class=" container wrapper1">
			<div class="row">
				<div class=" col-md-12 pad-top-double  text-left">
					<ol class="breadcrumb bold">
						<li>Home</li>

						<li class="active">Project Category Designation </li>
					</ol>
					<div class="row padded"></div>
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

					
			<form:form id="form1" modelAttribute="designationForClientModel" method="post">
					
							
							<div class="container">
								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
									<div class="panel panel-info panel-info1">
										<div class="panel-heading">
											<h3 class="text-center text-shadow">
												Project Category Designation Mapping
											</h3>
										</div>
										<div class="panel-body">
											<form:hidden path="numId"/>											
											<div class="row pad-top">

												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
													<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
														<label class="label-class"><spring:message
																code="application.project.category.label" />:<span
															style="color: red;">*</span></label>
													</div>
													<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
														<div class="input-container">
															<form:select path="projectCategoryId" class="select2Option data">
																<form:option value="0">-- Select Category --</form:option>
																<c:forEach items="${categoryList}" var="category">
																	<form:option value="${category.numId}">
																		<c:out value="${category.categoryName}"></c:out>
																	</form:option>
																</c:forEach>
															</form:select>							

														</div>
													</div>
												</div>

												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
													<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
														<label class="label-class"><spring:message
																code="employee.designation" />:<span
															style="color: red;">*</span></label>
													</div>
													<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">													
														<div class="input-container">
															<form:select path="designationId" class="select2Option data" disabled="disabled">
																<form:option value="0">-- Select Designation --</form:option>
																<c:forEach items="${designationList}" var="designation">
																	<form:option value="${designation.numId}">
																		<c:out value="${designation.designationName}"></c:out>
																	</form:option>
																</c:forEach>
															</form:select>							

														</div>												
														
													</div>
												</div>

											</div>

											<div class="row pad-top">											
												<!-- New field added by devesh on 30-04-24 to get base cost according to deputed site -->
												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group" id="deputedAtDiv" style="display: none;">
													<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
														<label class="label-class"><spring:message code="employee.deputedat"/>:<span
															style="color: red;">*</span></label>
													</div>
													<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
														<div class="input-container">
															<form:select path="numDeputedAt" class="select2Option data">
																<form:option value="0"> --- Select Deputed At --- </form:option>	
															  	<form:option value="1">CDAC</form:option>											    
														   		<form:option value="2">At Client</form:option>
															</form:select>
															<form:errors path="numDeputedAt" cssClass="error" />								
														</div>
													</div>
												</div>
												<!-- End of new field -->
											
												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
													<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
														<label class="label-class"><spring:message
																code="manpower.cost" />:<span
															style="color: red;">*</span></label>
													</div>
													<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
														<div class="input-container-short">
														<i class="fa fa-inr icon_short"></i>
														<form:input class="input-field" path="cost" />
														</div>
													</div>
												</div>
																		
												<!-- New fields added by devesh on 30-04-24 to get from and to date for base cost -->
												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
													<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
														<label class="label-class">From:<span style="color: red;">*</span></label>
													</div>
													<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
														<div class="input-container-short">
														<i class="fa fa-calendar icon_short"></i>
														<form:input class="input-field" path="fromDate" id="fromDate"/>
														</div>
													</div>
												</div>
											
												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
													<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
														<label class="label-class">To:</label>
													</div>
													<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
														<div class="input-container-short">
														<i class="fa fa-calendar icon_short"></i>
														<form:input class="input-field" path="toDate" id="toDate"/>
														</div>
													</div>
												</div>
												<!-- End of new fields -->
											</div>
											
										</div>

											<div class="row pad-top pad-bottom form_btn center">
											<button type="button" class="btn btn-primary font_14"  id="save">
												<span class="pad-right"><i class="fa fa-folder"></i></span>Save
											</button>
											
										</div>
									</div>
								</div>
							</div>
						</form:form>
								

				</div>
			</div>
		</div>
		
		<!-- Details table added by devesh on 06-05-24 -->
		<div class="container">
			<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
				<div class=" datatable_row pad-bottom">
					<fieldset class="fieldset-border">
		
						<legend class="bold legend_details">Details :</legend>
					
						<table id="baseCostDetails" class="table table-striped table-bordered"
							style="width: 100%">
							<thead class="datatable_thead bold ">
								<tr>
								<%------------- Add Serial Number Columns and Remove Select Columns [23-10-2023] ----------%>
									<th style="width: 8%"><spring:message code="forms.serialNo"/></th>
									<th style="width: 24%">Designation</th>
									<th style="width: 27%">Project Category</th>
									<th ><spring:message code="employee.deputedat"/></th>
									<th style="width: 15%">Base Cost</th>						
									<th style="width: 13%">From Date</th>
									<th style="width: 13%">To Date</th>
								</tr>
							</thead>
							<tbody class="">
							</tbody>
						</table>
					</fieldset>
				</div>
			</div>
		</div>
		<!-- End of details table -->

		
	</section>
	
	
	
	<script type="text/javascript" src="/PMS/resources/app_srv/PMS/transaction/js/projectCategoryDesignationMapping.js"></script>
</body>
</html>