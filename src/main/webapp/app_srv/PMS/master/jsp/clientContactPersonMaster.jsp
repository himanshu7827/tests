
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
						<li class="active">Contact Person Master (For Funding Org)</li>
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

				<sec:authorize access="hasAuthority('WRITE_CLIENT_CONTACT_MST')"> 
					<form:form id="form1" modelAttribute="contactPersonMasterModel"
						action="/PMS/mst/saveUpdateContactPersonMaster" method="post">
						<form:hidden path="numId" />
						<form:hidden path="referrerValue" value="${referrerValue}"/>
						<form:hidden path="idCheck" />
						<%-- <form:hidden path="referrerValue" value="${referrerValue}"/> --%>
						
						<div class="container">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="panel panel-info panel-info1">
									<div class="panel-heading panel-heading-fd">
										<h4 class="text-center ">Contact Person Master  (For Funding Org)</h4>
									</div>
									<div class="panel-body text-center">
										<div class="row pad-top " id="stage_name_row">
													<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">


													<label class="label-class"><spring:message
															code="Client_Contact_Person_Master.name" />:<span
														style="color: red;">*</span></label>
												</div>
												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
													<div class="input-container">
														<form:select path="organisationId" id="organisationId"
															class="select2Option">
															<form:option value="0">-- Select Organization --</form:option>
															<c:forEach items="${clientMasterList}"
																var="clientMasterList">
																<form:option value="${clientMasterList.numId}">
																	<c:out value="${clientMasterList.clientName}"></c:out>
																</form:option>
															</c:forEach>
														</form:select>
													</div>
												</div>
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

										
										<div class="row ">
								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify name1">

												<label class="label-class"><spring:message
														code="Client_Contact_Person_Master.clientContactPersonName" />:<span
													style="color: red;">*</span></label>
											</div>

												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
												<div class="input-container">
													<i class="fa fa-user icon"></i>
													<form:input class="input-field"
														
														path="strContactPersonName" />
													<form:errors path="strContactPersonName" cssClass="error" />
												</div>
											</div>
										</div>
										</div>
											<div class="row ">
								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="Client_Contact_Person_Master.contactPersonDesignation" />:<span
													style="color: red;">*</span> </label>
											</div>
												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
												<div class="input-container">
													<i class="fa fa-star icon"></i>
													<form:input class="input-field"
													
														path="strDesignation" />
													<form:errors path="strDesignation" cssClass="error" />
												</div>
											</div>
										</div>
										</div>
										<div class="row ">
								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="thirdParty.contact.number" />:<span
													style="color: red;">*</span> </label>
											</div>
												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
												<div class="input-container">
													<i class="fa fa-mobile icon"></i>
													<form:input class="input-field"
														
														path="strMobileNumber" />
													<form:errors path="strMobileNumber" cssClass="error" />
												</div>
											</div>
										</div>
										</div>
										<div class="row ">
								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="Client_Contact_Person_Master.contactPersonEmailId" />:<span
													style="color: red;">*</span> </label>
											</div>
											<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
												<div class="input-container">
													<i class="fa fa-envelope-o icon"></i>
													<form:input class="input-field"
														path="strEmailId" />
													<form:errors path="strEmailId" cssClass="error" />
												</div>
											</div>
										</div></div>
										<%-- <div class="row ">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="Client_Contact_Person_Master.contactPersonRoles" />:<span
													style="color: red;">*</span> </label>
											</div>
											<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
												<div class="input-container">
													<i class="fa fa-user-circle-o icon"></i>
													<form:input class="input-field"
														 path="strRoles" />
													<form:errors path="strRoles" cssClass="error" />
												</div>
											</div>
										</div>
										</div> 
									<div class="row ">
								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="Client_Contact_Person_Master.contactPersonResponsibility" />:<span
													style="color: red;">*</span> </label>
											</div>
												<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
												<div class="input-container">
													<i class="fa fa-tachometer icon"></i>
													<form:input class="input-field"
													
														path="strResponsibility" />
													<form:errors path="strResponsibility" cssClass="error" />
												</div>
											</div>
										</div>
										</div>--%>
											<div class="row ">
								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="Client_Contact_Person_Master.contactPersonOfficeAddress" />:<span
													style="color: red;">*</span> </label>
											</div>
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
													<i class="fa fa-address-book icon "></i>
													<form:textarea class="input-field" rows="2"
														
														path="strOfficeAddress"></form:textarea>
													<form:errors path="strOfficeAddress" cssClass="error" />
												</div>
											</div>
										</div>
										</div>
										<%-- <div class="row ">
								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
							<div class="col-md-3 col-lg-3 col-sm-6 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="Client_Contact_Person_Master.contactPersonResidenceAddress" />:</label>
											</div>
											<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
												<div class="input-container">
													<i class="fa fa-address-book-o icon "></i>
													<form:textarea class="input-field" rows="2"
													
														path="strResidenceAddress"></form:textarea>
													<form:errors path="strResidenceAddress" cssClass="error" />
												</div>
											</div>
										</div>
</div> --%>



										<div class="row ">
								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-2 col-sm-push-1">
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
					
						<button type="button" class="btn btn-primary font_14" id="save">
							<span class="pad-right"><i class="fa fa-folder"></i></span>Save
						</button>
						<button type="button" class="btn btn-primary reset font_14" id="reset">
							<span class="pad-right"><i class="fa fa-refresh"></i></span>Reset
						</button>
						
						<c:if test="${not empty referrerValue}">
							<button type="button" class="btn btn-primary reset font_14" id="backPage">
								<span class="pad-right"><i class="fa fa-arrow-left"></i></span>Back
							</button>
						</c:if>
					</div>
									</div>

									<!-- <span
										class="pull-right padded margin-right bg-blue-text hidden"
										id="addnewrecord"> <a href="#"
										class="new_record blue no-underline font_16 blink text-shadow"
										id="new_record" title="Click Here to Add New Record">Click
											Here to Add New Record</a>
									</span> -->

								</div>
							</div>
						</div>
					</form:form>
			</sec:authorize>
<div class="container">

				<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 hidden"
					id="stagedetails">
					<div class=" datatable_row pad-bottom">
		<fieldset class="fieldset-border">

			<legend class="bold legend_details">Details :</legend>
							<%-- <sec:authorize access="hasAuthority('WRITE_CLIENT_CONTACT_MST')"> --%>
							<!-- 	<div class="row">
									<div class="pull-right">
										<div class="col-md-4">
											<input type="button" value="Delete" id="delete"
												class="btn btn-primary a-btn-slide-text">
										</div>
									</div>
								</div> -->
							<%-- </sec:authorize> --%>

							<script>
								$(document).ready(
										function() {

											$('#userinfo-message').delay(5000)
													.fadeOut();
											$('#delete').click(function() {
												ClientContactDelete();
											});
										});

								function ClientContactDelete() {
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

									document.getElementById("form1").action = "/PMS/mst/deleteClientContact";
									document.getElementById("form1").method = "POST";
									document.getElementById("form1").submit();
								}
							</script>

							<table id="datatable"
								class="table table-striped table-bordered hidden"
								style="width: 100%">
								<thead class="datatable_thead bold ">
									<tr>
										<th class="width20 check">Select</th>
										<th>Contact Person Name</th>
										<th>Contact Person Designation</th>
										<th>Contact Person Mobile Number</th>
										<th>Contact Person Email Id</th>
										<th>Contact Person Roles</th>
										<th>Contact Person Responsibility</th>
										<th>Contact Person Office Address</th>
										<th>Contact Person Residence Address</th>
										<th>Status</th>
										<th>Edit</th>
									</tr>
								</thead>
								<tbody class="">
									<c:forEach items="${data}" var="clientContactPersonModel">
										<tr>


											<td><input type="checkbox" path="checkbox"
												class="CheckBox" id="Checkbox"
												value="${clientContactPersonModel.numId}" autocomplete="off">${clientContactPersonModel.numId}</td>
											<td>${clientContactPersonModel.strContactPersonName}</td>
											<td>${clientContactPersonModel.strDesignation}</td>
											<td>${clientContactPersonModel.strMobileNumber}</td>
											<td>${clientContactPersonModel.strEmailId}</td>
											<td>${clientContactPersonModel.strRoles}</td>
											<td>${clientContactPersonModel.strResponsibility}</td>
											<td>${clientContactPersonModel.strOfficeAddress}</td>
											<td>${clientContactPersonModel.strResidenceAddress}</td>

											<c:choose>
												<c:when test="${clientContactPersonModel.valid}">
													<td>Active</td>
												</c:when>
												<c:otherwise>
													<td>Inactive</td>
												</c:otherwise>
											</c:choose>

											<td><sec:authorize
													access="hasAuthority('WRITE_CLIENT_CONTACT_MST')">
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
		src="/PMS/resources/app_srv/PMS/master/js/clientContactPersonMaster.js"></script>

</body>
</html>