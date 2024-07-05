
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
		<c:if test="${projectId>0 }">
<jsp:include page="/app_srv/PMS/global/jsp/ProcessFlow.jsp" >
<jsp:param name="moduleTypeId" value="2"/>
<jsp:param name="applicationId" value="0"/>

</jsp:include>
</c:if>
</div>
			<div class="row">
				<div class=" col-md-12 pad-top-double  text-left">
					<ol class="breadcrumb bold">
						<li>Home</li>
						<!-- <li>Consumer Forms For Medical Devices</li> -->
						<li class="active">Project Module Master</li>
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

			<sec:authorize access="hasAuthority('WRITE_PROJECT_MODULE_MST')">
				<form:form id="form1" modelAttribute="projectModuleMasterModel"
					action="/PMS/mst/saveUpdateProjectModuleMaster" method="post">
					<form:hidden path="numId" />
					<form:hidden path="idCheck" />
					<input type="hidden" id="projectid" value="${projectId}" />
					<input type="hidden" id="encProjectId" value="${encProjectId}"/>
					<div class="container ">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
							<div class="panel panel-info panel-info1">
								<div class="panel-heading panel-heading-fd">
									<h4 class="text-center ">Project Module Master</h4>
								</div>
								<div class="panel-body text-center">

									<div class="row pad-top ">
										<div
											class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
											<div
												class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="Project_Module_Master.projectName" />:<span
													style="color: red;">*</span></label>
											</div>
											
											<c:choose>
									<c:when test="${projectId==0}">
											
											<div class="col-md-5 col-lg-5 col-sm-6 col-xs-12">
												<div class="input-container">												
													<form:select path="projectId" id="projectId"
														class="select2Option">
														<form:option value="0">-- Select Project --</form:option>




														<c:forEach items="${projectList}" var="projectList">
															<form:option value="${projectList.numId}">
																<c:out value="${projectList.strProjectName}"></c:out>
																
															</form:option>
														</c:forEach>
													</form:select>
												</div>
											</div>
											</c:when>
									<c:otherwise>
								<div class="col-md-5 col-lg-5 col-sm-6 col-xs-12">
								<%-- <c:forEach items="${projectList}" var="projectData">
								<h5 class="bold">${projectData.strProjectName}</h5>
								</c:forEach> --%>
									<form:hidden path="projectId" value="${projectId}"/>
									<h5 class="bold">${projectData.strProjectName}
										<c:if test="${not empty projectData.projectRefNo}">
 								<span class="bold blue font_14">&nbsp;(${projectData.projectRefNo}) </span>
								</c:if>
									</h5>
								</div>
										
									</c:otherwise>
								</c:choose>
										</div>

									</div>


									<c:choose>
										<c:when test="${showForm == 1}">
											<div class="row pad-top" id="frm"></div>
										</c:when>
										<c:otherwise>
											<div class="row pad-top hidden" id="frm"></div>
										</c:otherwise>
									</c:choose>



									<div class="row  ">
										<div
											class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
											<div
												class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="Project_Module_Master.projectModuleName" />:<span
													style="color: red;">*</span></label>
											</div>

											<div class="col-md-5 col-lg-5 col-sm-6 col-xs-12">
												<div class="input-container">
													<i class="fa fa-file icon"></i>
													<form:input class="input-field" path="strModuleName" />
													<form:errors path="strModuleName" cssClass="error" />
												</div>
											</div>
										</div>
									</div>
									<div class="row  ">
										<div
											class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
											<div
												class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="Project_Module_Master.projectModuleDescription" />:<span
													style="color: red;">*</span> </label>
											</div>
										<div class="col-md-5 col-lg-5 col-sm-6 col-xs-12">
												<div class="input-container">
													<i class="fa fa-file-text-o icon pad-top-double"></i>
													<form:textarea class="input-field" rows="2"
														path="strModuleDescription"></form:textarea>
													<form:errors path="strModuleDescription" cssClass="error" />
												</div>
											</div>
										</div>
									</div>

									<%-- <div class="row ">
										<div
											class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
											<div
												class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="forms.status" /> :</label>
											</div>
										<div class="col-md-5 col-lg-5 col-sm-6 col-xs-12">
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
									</div> --%>

									<div class="row pad-top">
										<a href="#"
											class="new_record blue no-underline font_16  text-shadow hidden"
											id="new_record" title="Click Here to Add New Record"> <span
											class="pull-right padded margin-right bg-blue-text hidden blink"
											id="addnewrecord"> Click Here to Add New Record</span></a>
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
				<c:if test="${projectId>0 }"> 
												<div class="row padded pull-left">
									<button type="button" class="btn btn-info reset font_14 " id="prev" onclick="goprev()" >
									<span class="pad-right"><i class="fa fa-arrow-left"></i></span>Prev
									</button>
									</div>
										<div class="row padded pull-right">
									<button type="button" class="btn btn-info reset font_14 " id="next" onclick="gonext()" >
									<span class="pad-right"><i class="fa fa-arrow-right"></i></span>Next
									</button>
									</div>
									</c:if>
				<%-- <c:if test="${projectId>0 }"> 
									<button type="button" class="btn btn-primary reset font_14 " id="prev" onclick="goprev()" style="background: #3d558e !important;float: left;">
									<span class="pad-right"><i class="fa fa-arrow-left"></i></span>Prev
									</button>
									
										<br>
									<button type="button" class="btn btn-primary reset font_14 " id="next" onclick="gonext()" style="background: #3d558e !important;float: right;">
									<span class="pad-right"><i class="fa fa-arrow-right"></i></span>Next
									</button>
</c:if> --%>
									<!-- 			

							<span class="pull-right padded margin-right bg-blue-text" id="addnewrecord">
								<a href="#"
								class="new_record blue no-underline font_16 blink text-shadow"
								id="new_record" title="Click Here to Add New Record">Click
									Here to Add New Record</a>
							</span> -->

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
		

			

						<table id="datatable"
							class="table table-striped table-bordered hidden"
							style="width: 100%">
							<thead class="datatable_thead bold ">
								<tr>
									<th class="width20 check">Select</th>
									<th>Project Module Name</th>
									<th>Project Module Description</th>
									<!-- <th>Status</th> -->
									<th>Edit</th>
								</tr>
							</thead>
							<tbody >
								<c:forEach items="${data}" var="projectModuleModel">
									<tr>


										<td><input type="checkbox" path="checkbox"
											class="CheckBox" id="Checkbox"
											value="${projectModuleModel.numId}" autocomplete="off">${projectModuleModel.numId}</td>
										<td>${projectModuleModel.strModuleName}</td>
										<td>${projectModuleModel.strModuleDescription}</td>


										<%-- <c:choose>
											<c:when test="${projectModuleModel.valid}">
												<td>Active</td>
											</c:when>
											<c:otherwise>
												<td>Inactive</td>
											</c:otherwise>
										</c:choose> --%>

										<td><sec:authorize
												access="hasAuthority('WRITE_PROJECT_MODULE_MST')">
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
			<!--End of main-container-->
			<!-- </div> -->
			<!-- end of container-->
</div>
</div>

	</section>


			<script>
							$(document).ready(function() {
								/* $('#toggle-off').removeAttr('Checked');
								$('#toggle-on').attr('checked',true); */
								$('#userinfo-message').delay(5000).fadeOut();
								$('#delete').click(function() {
									ProjectModuleDelete();
								});
							});

							function ProjectModuleDelete() {
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

								document.getElementById("form1").action = "/PMS/mst/deletetProjectModule";
								document.getElementById("form1").method = "POST";
								document.getElementById("form1").submit();
							}
						</script>

	<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/master/js/projectModuleMaster.js"></script>


</body>
</html>