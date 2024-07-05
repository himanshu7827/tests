<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en">
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<style>
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
						<li class="active">Document Type Master</li>
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

			<sec:authorize access="hasAuthority('WRITE_DOCUMENT_MST')">
				<form:form id="form_document_mst"
					modelAttribute="documentTypeMasterModel"
					action="/PMS/mst/saveUpdateDocTypeMaster" method="post">
					<form:hidden path="numId" />
					<form:hidden path="numIds" />
					<div class="container ">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
							<div class="panel panel-info panel-info1">
								<div class="panel-heading panel-heading-fd">
									<h4 class="text-center ">Document Type Master</h4>
								</div>
								<div class="panel-body text-center">

									<div class="row pad-top ">
										<div
											class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
											<div
												class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">

												<label class="label-class "><spring:message
														code="document.documentName" /> :<span
													style="color: red;">*</span></label>
											</div>

											<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
												<div class="input-container">
													<i class="fa fa-files-o icon"></i>
													<form:input type="text" class="input-field"
														id="docTypeName" path="docTypeName" />
													<form:errors path="docTypeName" cssClass="error" />
												</div>
											</div>
										</div>
									</div>
									
									<!--Short Code  -->
									<div class="row  ">
										<div
											class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
											<div
												class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="master.shortCode" /> :</label>
											</div>
											<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
												<div class="input-container">
													<i class="fa fa-th-large icon"></i>
													<form:input class="input-field" path="shortCode" />
													<form:errors path="shortCode" cssClass="error" />
												</div>
											</div>


										</div>
									</div>
									<!-- End short code -->
									
									<!--Icon  -->
									<div class="row  ">
										<div
											class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
											<div
												class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
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

									<div class="row ">
										<div
											class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
											<div
												class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="document.description" /> : </label>
											</div>
											<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
												<div class="input-container">
													<i class="fa fa-file-text-o icon "></i>
													<form:textarea class="form-control" rows="2"
														path="description" />
													<form:errors path="description" cssClass="error" />
												</div>
											</div>
										</div>

									</div>
									
									<div class="row  ">
										<div
											class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
											<div
												class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="doctype.displaySeq" /> :</label>
											</div>
											<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
												<div class="input-container">
													<i class="fa fa-th-large icon"></i>
													<form:input class="input-field" path="displaySeq" />
													<form:errors path="displaySeq" cssClass="error" />
												</div>
											</div>


										</div>
									</div>
									
											<div class="row">
				<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
										<div class="col-md-2 col-lg-2 col-sm-2 col-xs-12 text-justify">
								<label class="label-class"><spring:message code="docupload.document.format"/> :<span
									style="color: red;">*</span></label>
							</div>
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
									<form:select path="docTypeFormatId" class="select2Option" multiple="true" id="docTypeFormatId">
<%-- 									<form:option value="0">--Select Thrust--</form:option>
 --%> 							<c:forEach items="${documentFormatList}" var="listformat" >
								<form:option value="${listformat.numId}">
								<c:out value="${listformat.formatName}"></c:out></form:option>
							</c:forEach>
									</form:select>
								</div>
							</div>
						</div>
						
						
					
									
									<div class="row  ">
										<div
											class="col-md-6 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
											<div
												class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
												<label class="label-class"><spring:message
														code="docType.showOnDashboard" /> :</label>
											</div>
											
											<div class="col-md-1 col-lg-1 col-sm-2 col-xs-6">
												<div class="input-container">
													<!-- <i class="fa fa-th-large icon"></i> -->
													<form:checkbox  class="input-field" path="showOnDashboard" value='true'/>
													<form:errors path="showOnDashboard" cssClass="error" />
												</div>
											</div>


										</div>
									</div>
									
									<div class="row ">
										<div
											class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
											<div
												class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
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
							<sec:authorize access="hasAuthority('WRITE_DOCUMENT_MST')">
								<!-- 	<div class="row">
				<div class="pull-right">
					 <div class="col-md-4">				 
						<input type= "button" value="Delete" id="delete" name="Delete" class="btn btn-primary a-btn-slide-text" >
					</div>
				</div>
			</div> -->
							</sec:authorize>
							<table id="example" class="table table-striped table-bordered"
								style="width: 100%">
								<thead class="datatable_thead bold ">
									<tr>
										<th class="width20"><spring:message code="master.select" /></th>
										<th><spring:message code="document.documentName" /></th>
										<th><spring:message code="document.description" /></th>
										<th><spring:message code="master.shortCode" /></th>
										<th><spring:message code="form.icon.lable" /></th>	
										<th><spring:message code="doctype.displaySeq" /></th>	
										<th><spring:message code="docType.showOnDashboard" /></th>										
										<th><spring:message code="forms.status" /></th>
										<th><spring:message code="forms.edit" /></th>
										<th class="hidden"><spring:message code="docupload.document.format"/></th>
										
									</tr>
								</thead>
								<tbody class="">
									<c:forEach items="${data}" var="docModel">
										<tr>
											<td><input type="checkbox" class="CheckBox"
												id="CheckBox" value="${docModel.numId}" autocomplete="off" />${docModel.numId}</td>
											<td>${docModel.docTypeName}</td>
											<td>${docModel.description}</td>
											<td>${docModel.shortCode}</td>
											<td>${docModel.icon}</td>
											<td>${docModel.displaySeq}</td>
											<td><c:choose>
													<c:when test="${docModel.showOnDashboard}">Yes</c:when>
													<c:otherwise>No </c:otherwise>
												</c:choose> 
											</td>
											<c:choose>
												<c:when test="${docModel.valid}">
													<td>Active</td>
												</c:when>
												<c:otherwise>
													<td>Inactive</td>
												</c:otherwise>
											</c:choose>
											<sec:authorize access="hasAuthority('WRITE_DOCUMENT_MST')">
												<td><span
													class="fa fa-pencil-square-o btn btn-primary a-btn-slide-text"
													id="edit" aria-hidden="true"></span></td>
											</sec:authorize>
											<td id="tId" class="hidden">${docModel.selectedFormatId}</td>
											
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

	<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/master/js/documentMaster.js"></script>



</body>

</html>