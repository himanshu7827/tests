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
						<li class="active">Document Format</li>
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


			<form:form id="form1" name="form1"
				modelAttribute="documentFormatModel"
				action="/PMS/mst/saveUpdateDocumentFormat" method="post">
				<form:hidden path="numId" />
				<div class="container">
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
						<div class="panel panel-info panel-info1">
							<div class="panel-heading panel-heading-fd">
								<h4 class="text-center ">Document Format</h4>
							</div>
							<div class="panel-body text-center">
								<div class="row pad-top ">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
										<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">

											<label class="label-class"><spring:message
													code="documentFormat.master.label" />:<span
												style="color: red;">*</span></label>
										</div>

										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
												<i class="fa fa-file-text-o icon"></i>
												<form:input class="input-field" path="formatName" />
												<form:errors path="formatName" cssClass="error" />
											</div>
										</div>
									</div>
								</div>
									<!--Icon  -->
								<div class="row ">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
										<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">
											<label class="label-class"><spring:message
													code="form.icon.lable" /> :</label>
										</div>
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
												<i class="fa fa-th-large icon"></i>
												<form:input class="input-field" path="icon" />
												<form:errors path="icon" cssClass="error" />
											</div>
										</div>


									</div>
								</div>
								<!-- End Icon -->
								<div class="row  ">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
										<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">

											<label class="label-class"><spring:message
													code="documentFormat.mime.label" />:<span
												style="color: red;">*</span></label>
										</div>

										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="col-md-2 col-lg-2 col-sm-4 col-xs-12 ">
												<form:radiobutton path="mimeType" value="1" id="toggle-on" />
												<form:label path="mimeType" for="toggle-on1"
													class="btn inline  zero round no-pad">
													<span>Yes</span>
												</form:label>
											</div>
											<div class="col-md-2 col-lg-2 col-sm-4 col-xs-12 ">
												<form:radiobutton path="mimeType" value="-1" id="toggle-off" />

												<form:label path="mimeType" for="toggle-off1"
													class="btn round inline zero no-pad">
													<span class="">No</span>
												</form:label>
											</div>

										</div>
									</div>
								</div>
							
								<div class="row ">

									<div id="mimeDiv"
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1 form-group hidden">
										<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">
											<label class="label-class"><spring:message
													code="documentFormat.mimes.label" /> :</label>
										</div>
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
												<i class="fa fa-align-justify icon"></i>
												<form:input class="input-field" path="mimeTypes" />
												<form:errors path="mimeTypes" cssClass="error" />
											</div>
										</div>
									</div>
								</div>

								<div class="row">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
										<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">
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

							<table id="example" class="table table-striped table-bordered"
								style="width: 100%">
								<thead class="datatable_thead bold ">
									<tr>

										<th data-toggle="true"><spring:message
												code="forms.serialNo" /></th>
										<th><spring:message code="documentFormat.format.label" /></th>
										<th><spring:message code="documentFormat.mime.label" /></th>
										<th><spring:message code="documentFormat.mimes.label" /></th>
										<th><spring:message code="form.icon.lable" /></th>
										<th><spring:message code="forms.status" /></th>
										<th class="width10"><spring:message code="forms.edit" /></th>
									</tr>
								</thead>
								<tbody class="">
									<c:forEach items="${documentFormatList}" var="documentFormat">
										<tr>

											<td><input type="checkbox" class="CheckBox"
												id="CheckBox" value="${documentFormat.numId}" />${documentFormat.numId}</td>
											<td data-hide="all">${documentFormat.formatName}</td>
											<c:choose>
												<c:when test="${documentFormat.mimeType == 1}">
													<td>Yes</td>
												</c:when>
												<c:otherwise>
													<td>No</td>
												</c:otherwise>
											</c:choose>
											<%-- <td>${documentFormat.mimeType}</td> --%>
											<td>${documentFormat.mimeTypes}</td>
											<td>${documentFormat.icon}</td>
											<c:choose>
												<c:when test="${documentFormat.valid}">
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
		src="/PMS/resources/app_srv/PMS/master/js/documentFormat.js"></script>
</body>
</html>