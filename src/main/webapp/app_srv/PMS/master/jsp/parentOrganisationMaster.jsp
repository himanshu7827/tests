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
						<li class="active"><spring:message code="parentOrganisation.label"/></li>
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

		<sec:authorize access="hasAuthority('WRITE_PARENT_ORGANISATION')">
			<form:form id="form1" modelAttribute="parentOrganisationMasterModel"
				action="/PMS/mst/saveUpdateParentOrganisationMaster" method="post">
				
				<form:hidden path="numId" />
				<div class="container ">
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
						<div class="panel panel-info panel-info1">
							<div class="panel-heading panel-heading-fd">
								<h4 class="text-center "><spring:message code="parentOrganisation.label"/></h4>
							</div>
							<div class="panel-body text-center">

								<div class="row pad-top ">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
										<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
											<label class="label-class"><spring:message code="group.organisationName"/>:<span
												style="color: red;">*</span></label>
										</div>

										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
												<i class="fa fa-user icon"></i>
												<form:input class="input-field"
													 path="organisationName" />
												<form:errors path="organisationName" cssClass="error" />
											</div>
										</div>
									</div>
								</div>
								<div class="row  ">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
										<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">

											<label class="label-class"><spring:message code="master.shortname"/>: </label>
										</div>
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
												<i class="fa fa-th-large icon"></i>
												<form:input class="input-field" 
													path="organisationShortName" />
												<form:errors path="organisationShortName" cssClass="error" />
											</div>
										</div>
									</div>
								</div>

								<div class="row">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
										<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
											<label class="label-class"><spring:message code="group.groupAddress"/>:<span
												style="color: red;">*</span></label>
										</div>
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
												<i class="fa fa-file-text-o icon "></i>
											<form:textarea class="form-control" rows="2"
												path="organisationAddress"></form:textarea>
											<form:errors path="organisationAddress" cssClass="error" />
										</div>
									</div>
									</div>
									</div>
									<div class="row">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
										<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
											<label class="label-class" ><spring:message code="master.contact"/>:<span
												style="color: red;">*</span></label>
										</div>
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
												<i class="fa fa-phone icon"></i>
												<form:input class="input-field" path="contactNumber"
													/>
												<form:errors path="contactNumber" cssClass="error" />
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
		</sec:authorize>

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
									<th class="width20">Select</th>
									<th><spring:message code="group.organisationName"/></th>
									<th><spring:message code="master.shortname"/></th>
									<th><spring:message code="group.groupAddress"/></th>
									<th><spring:message code="master.contact"/></th>									
									<th><spring:message code="forms.status"/></th>
									<th><spring:message code="forms.edit"/></th>									
								</tr>
							</thead>
							<tbody class="">
								<c:forEach items="${data}" var="organisationModel">
									<tr>
										<td><input type="checkbox" class="CheckBox" id="CheckBox"
											value="${organisationModel.numId}" autocomplete="off">${organisationModel.numId}</td>
										<td>${organisationModel.organisationName}</td>
										<td>${organisationModel.organisationShortName}</td>
										<td>${organisationModel.organisationAddress}</td>
										<td>${organisationModel.contactNumber}</td>										
										<c:choose>
											<c:when test="${organisationModel.valid}">
												<td>Active</td>
											</c:when>
											<c:otherwise>
												<td>Inactive</td>
											</c:otherwise>
										</c:choose>
										<td>
										<sec:authorize access="hasAuthority('WRITE_PARENT_ORGANISATION')">
											<span
												class="fa fa-pencil-square-o btn btn-primary a-btn-slide-text"
												id="edit" aria-hidden="true"></span>
										</sec:authorize>
										</td>

									</tr>
								</c:forEach>
							</tbody>
						</table>
						
					</fieldset>
				
				</div>
				<!--End of datatable_row-->
			</div>
		</div>
		</div>
	</section>


	<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/master/js/parentOrganisationMaster.js"></script>
</body>
</html>