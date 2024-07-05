<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
						<li class="active">Role Master</li>
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


			<form:form id="form1" name="form1" modelAttribute="projectRoleMasterForm"
				action="/PMS/mst/saveProjectRoleMaster" method="post">
				<form:hidden path="numIds" />
				<form:hidden path="numId" />
				<div class="container ">
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
						<div class="panel panel-info panel-info1">
							<div class="panel-heading panel-heading-fd">
								<h4 class="text-center ">Project Role Master</h4>
							</div>
							<div class="panel-body text-center">

								<div class="row pad-top ">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
										<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">


											<label class="label-class"><spring:message
													code="label.project.based.role" />:<span
												style="color: red;">*</span></label>
										</div>

										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
												<i class="fa fa-user icon"></i>
												<form:input class="input-field" placeholder="Name"
													path="roleName" />
												<form:errors path="roleName" cssClass="error" />
											</div>
										</div>
									</div>
								</div>
							
		<div class="panel-body text-center">

								<div class="row pad-top ">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
										<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">


											<label class="label-class"><spring:message
													code="employee.empType.hierarchy" />:<span
												style="color: red;">*</span></label>
										</div>

										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
												<i class="fa fa-user icon"></i>
												<form:input class="input-field" placeholder="Name"
													path="hierarchy" />
												<form:errors path="hierarchy" cssClass="error" />
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
								</div>
							</div>
						</div>

					</div>
				</div>
			</form:form>
			<!-- </div> -->
			<!--End of panel-->
			<!--Start data table-->
			<div class="container">
				<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
					<div class=" datatable_row pad-bottom">
						<fieldset class="fieldset-border">

							<legend class="bold legend_details">Details :</legend>
							<div class="row">
				<div class="pull-right">
					 <div class="col-md-4">				 
						<input type="button" value="Delete" id="delete" name="Delete" class="btn btn-primary a-btn-slide-text" >
					</div>
				</div>
			</div>
							<table id="example" class="table table-striped table-bordered"
								style="width: 100%">
								<thead class="datatable_thead bold ">
									<tr>
										<th class="width20">Select</th>
										<th class="hidden"></th>
										<th><spring:message code="employee_Role.role_Name" /></th>
										<th><spring:message code="employee.empType.hierarchy"/></th>
										<th>Edit</th>
									</tr>
								</thead>
								<tbody class="">
									<c:forEach items="${data}" var="roleData">
										<tr>

											<td><input type="checkbox" class="CheckBox"
												id="CheckBox" value="${roleData.numId}" /></td>
												<td class="hidden">${roleData.numId}</td>
											<td>${roleData.roleName}</td>
											<td>${roleData.hierarchy}</td>
									
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
		src="/PMS/resources/app_srv/PMS/master/js/ProjectRoleMaster.js"></script>


</body>
</html>