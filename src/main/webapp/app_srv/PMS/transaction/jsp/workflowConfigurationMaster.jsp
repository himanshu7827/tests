<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
				<div class=" col-md-12 pad-top-double  text-left">
					<ol class="breadcrumb bold">
						<li>Home</li>
						<!-- <li>Consumer Forms For Medical Devices</li> -->
						<li class="active"><spring:message
								code="form.application.basic.dtl" /></li>
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


			<form:form id="form1" modelAttribute="workflowConfigurationModel"
				action="" method="post">
				 <form:hidden path="numId" /> 
				<div class="container">
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
						<div class="panel panel-info panel-info1">
							<div class="panel-heading panel-heading-fd">
								<h4 class="text-center ">
									<spring:message code="application.workflow.label" />
								</h4>
							</div>
							<div class="panel-body text-center">

								<div class="row pad-top">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
										<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">

											<label class="label-class"><spring:message
													code="application.moduletype.label" />:<span
												style="color: red;">*</span></label>
										</div>

										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
												<form:select class="select2Option" path="moduleTypeId" id="moduleTypeId">
													<option value="0">-- Select Module Type --</option>
													<c:forEach items="${moduleTypesList}"
														var="moduleTypeModel">
														<option value="${moduleTypeModel.moduleTypeId}">
															${moduleTypeModel.moduleTypeName}</option>
													</c:forEach>
												</form:select>
 												<form:errors path="moduleTypeId" cssClass="error" />
											</div>
										</div>
									</div>
								</div>
								
								<div class="row pad-top">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
										<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">

											<label class="label-class"><spring:message
													code="application.businesstype.label" />:<span
												style="color: red;">*</span></label>
										</div>

										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
												<form:select class="select2Option" path="businessTypeId" id="businessTypeId">
													<option value="0">-- Select Business Type --</option>
													<c:forEach items="${businessTypeModelList}"
														var="businessTypeModel">
														<option value="${businessTypeModel.numId}">
															${businessTypeModel.businessTypeName}</option>
													</c:forEach>
												</form:select>
												<form:errors path="businessTypeId" cssClass="error" />
											</div>
										</div>
									</div>
								</div>

								<div class="row ">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
										<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">

											<label class="label-class"><spring:message
													code="application.projecttype.label" />:<span
												style="color: red;">*</span></label>
										</div>

										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
												<form:select class="select2Option" path="projectTypeId" id="projectTypeId">
													<option value="0">-- Select Project Type --</option>
													<c:forEach items="${projectTypeModelList}"
														var="projectTypeModel">
														<option value="${projectTypeModel.numId}">
															${projectTypeModel.projectTypeName}</option>
													</c:forEach>
												</form:select>
												<form:errors path="projectTypeId" cssClass="error" />
											</div>

										</div>
									</div>
								</div>

								<div class="row ">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
										<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">

											<label class="label-class"><spring:message
													code="application.project.category.label" />:<span
												style="color: red;">*</span></label>
										</div>

										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
												<form:select class="select2Option" path="categoryId" id="categoryId">
													<option value="0">-- Select Project Category --</option>
													<c:forEach items="${projectCategoryModelList}"
														var="projectCategoryModel">
														<option value="${projectCategoryModel.numId}">
															${projectCategoryModel.categoryName}</option>
													</c:forEach>
												</form:select>
												<form:errors path="categoryId" cssClass="error" />
											</div>

										</div>
									</div>
								</div>

				
			

								<div class="row pad-top  form_btn">
								
								<button type="button" class="btn btn-primary font_14" id="save">
										<span class="pad-right"><i class="fa fa-folder"></i></span>Save
									</button>
									
									<button type="button" class="btn btn-primary reset font_14"
										id="reset">
										<span class="pad-right"><i class="fa fa-refresh"></i></span>Reset
									</button>

									
									
									<button type="button" class="btn btn-primary reset font_14 hidden"
										id="next">
										<span class="pad-right"><i class="fa fa-refresh"></i></span>Next
									</button>
								
								</div>
							</div>
						</div>
					</div>
				</div>
			</form:form>
			<!-- </div> -->
			<!--End of panel-->
			
			<div class="container">
				<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
					<div class=" datatable_row pad-bottom">
						<fieldset class="fieldset-border">

							<legend class="bold legend_details">Details :</legend>
							<!-- <div class="row">
				<div class="pull-right">
					 <div class="col-md-4">				 
						<input type="button" value="Delete" id="delete" name="Delete" class="btn btn-primary a-btn-slide-text" >
					</div>
				</div>
			</div>
		 -->
							<table id="example" class="table table-striped table-bordered"
								style="width: 100%">
								<thead class="datatable_thead bold ">
									<tr>
										<th class="width20">Select</th>
										<th><spring:message code="application.moduletype.label"/>:</th>
										<th><spring:message code="application.businesstype.label"/>:</th>
										<th><spring:message code="application.projecttype.label"/>:</th>
										<th><spring:message code="application.project.category.label"/>:</th>
										<th><spring:message code="forms.status" /></th>
										<th>Edit</th>
									</tr>
								</thead>
								<tbody class="">
									<c:forEach items="${data}" var="processData">
										<tr>

											<td><input type="checkbox" class="CheckBox"
												id="CheckBox" value="${processData.numId}" />${processData.numId}</td>
											<td>${processData.moduleTypeName}</td>
											<td>${processData.businessTypeName}</td>
											<td>${processData.projectTypeName}</td>
											<td>${processData.categoryName}</td>

											<c:choose>
												<c:when test="${processData.valid}">
													<td>Active</td>
												</c:when>
												<c:otherwise>
													<td>Inactive</td>
												</c:otherwise>
											</c:choose>

											<td><span
												class="fa fa-pencil-square-o btn btn-primary a-btn-slide-text"
												id="edit" aria-hidden="true"></span></td>

										</tr>
									</c:forEach>
								</tbody>
							</table>

							<!--End of data table-->
						</fieldset>
						
					</div>
					<!--End of datatable_row-->
				</div>
			</div>
		</div>
	</section>
	<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/transaction/js/workflowconfigurationDetails.js"></script>
</body>
</html>