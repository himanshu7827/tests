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
						<li class="active">Document Stage Master</li>
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

			<sec:authorize access="hasAuthority('WRITE_DOC_STAGE_MST')">
				<form:form id="form1" modelAttribute="docStageMasterModel"
					action="/PMS/mst/saveUpdateDocStageMaster" method="post">
					<form:hidden path="numId" />
					<form:hidden path="idCheck" />
					<div class="container">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
							<div class="panel panel-info panel-info1">
								<div class="panel-heading panel-heading-fd">
									<h4 class="text-center ">Document Stage Master</h4>
								</div>
								<div class="panel-body text-center">
									<div class="row pad-top ">
										<div
											class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
											<div
												class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">

												<label class="label-class"><spring:message
														code="Document_Stage_Master.stageName" />:<span
													style="color: red;">*</span></label>
											</div>

											<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
												<div class="input-container">
													<i class="fa fa-clipboard icon"></i>
													<form:input class="input-field"
														path="strStageName" />
													<form:errors path="strStageName" cssClass="error" />
												</div>
											</div>
										</div>
									</div>
									<div class="row ">
										<div
											class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
											<div
												class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="Document_Stage_Master.stageDescription" />:</label>
											</div>
											<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
												<div class="input-container">
													<i class="fa fa-file-text-o icon "></i>
													<form:textarea class="input-field"
														 rows="2"
														path="strStageDescription"></form:textarea>
													<form:errors path="strStageDescription" cssClass="error" />
												</div>
											</div>
										</div>
									</div>



									<div class="row ">
										<div
											class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
											<div
												class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">
												<label class="label-class"><spring:message
													code="forms.status" /> :</label>
											</div>
											<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
												<div class="col-md-2 col-lg-2 col-sm-4 col-xs-12 ">
													<form:radiobutton path="valid" value="true" id="toggle-on" />
													<form:label path="valid" for="toggle-on"
														class="btn inline  zero round no-pad">
														<span>Active</span>
													</form:label>
												</div>
												<div class="col-md-2 col-lg-2 col-sm-4 col-xs-12 ">
													<form:radiobutton path="valid" value="false"
														id="toggle-off" />

													<form:label path="valid" for="toggle-off"
														class="btn round inline zero no-pad">
														<span class="">Inactive</span>
													</form:label>
												</div>
											</div>
										</div>
									</div>



									<div class="row pad-top  form_btn">

										<button type="button" class="btn btn-primary font_14"
											id="save">
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
			</sec:authorize>

		<div class="container">
	<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
	<div class=" datatable_row pad-bottom">
		<fieldset class="fieldset-border">

			<legend class="bold legend_details">Details :</legend>
						<sec:authorize access="hasAuthority('WRITE_DOC_STAGE_MST')">
						<!-- 	<div class="row">
								<div class="pull-right">
									<div class="col-md-4">
										<input type="button" value="Delete" onclick="myfunc()"
											id="delete" class="btn btn-primary a-btn-slide-text">
									</div>
								</div>
							</div> -->
						</sec:authorize>


						<script>
							$(document).ready(function() {

								$('#userinfo-message').delay(5000).fadeOut();
								$('#delete').click(function() {
									DocStageDelete();
								});
							});

							function DocStageDelete() {
								var chkArray = [];

								$(".CheckBox:checked").each(function() {
									chkArray.push($(this).val());
								});

								var selected;
								selected = chkArray.join(',');
								if (selected.length >= 1) {

									swal(
											{
												title : "Are you sure you want to delete the record?",
												icon : "warning",
												buttons : [ 'No', 'Yes' ],
												dangerMode : true,
											}).then(function(isConfirm) {
										if (isConfirm) {
											$("#idCheck").val(selected);

											submit_form_delete();
										}

										else {

											return false;
										}
									});
								} else {
									swal("Please select at least one checkbox to delete");
								}

							}

							function submit_form_delete() {

								document.getElementById("form1").action = "/PMS/mst/deleteDocumentStage";
								document.getElementById("form1").method = "POST";
								document.getElementById("form1").submit();
							}
						</script>

						<table id="example" class="table table-striped table-bordered"
							style="width: 100%">
							<thead class="datatable_thead bold ">
								<tr>
									<th class="width20 check">Select</th>
									<th>Stage Name</th>
									<th>Stage Description</th>
									<th>Status</th>
									<th>Edit</th>
								</tr>
							</thead>
							<tbody class="">
								<c:forEach items="${data}" var="docStageModel">
									<tr>


										<td><input type="checkbox" path="checkbox"
											class="CheckBox" id="Checkbox" value="${docStageModel.numId}"
											autocomplete="off">${docStageModel.numId}</td>
										<td>${docStageModel.strStageName}</td>

										<td>${docStageModel.strStageDescription}</td>


										<c:choose>
											<c:when test="${docStageModel.valid}">
												<td>Active</td>
											</c:when>
											<c:otherwise>
												<td>Inactive</td>
											</c:otherwise>
										</c:choose>

										<td><sec:authorize
												access="hasAuthority('WRITE_DOC_STAGE_MST')">
												<span
													class="fa fa-pencil-square-o btn btn-primary a-btn-slide-text"
													id="edit" aria-hidden="true"></span>
											</sec:authorize></td>

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
		src="/PMS/resources/app_srv/PMS/master/js/docStageMaster.js"></script>

</body>
</html>