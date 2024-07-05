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
						<li class="active">Group Master</li>
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


			<form:form id="form1" modelAttribute="groupMasterModel"
				action="/PMS/mst/saveUpdateGroupMaster" method="post">
				<form:hidden path="numId" />
				<div class="container ">
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
						<div class="panel panel-info panel-info1">
							<div class="panel-heading panel-heading-fd">
								<h4 class="text-center ">Group Master</h4>
							</div>
							<div class="panel-body text-center">

								<div class="row pad-top ">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
										<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">

											<label class="label-class"><spring:message
													code="group.organisationName" /> :<span style="color: red;">*</span></label>

										</div>
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
												<form:select path="organisationId" class="select2Option">
													<form:option value="0"> -- Select Organisation -- </form:option>
													<c:forEach items="${organisationList}" var="organisation">
														<form:option value="${organisation.numId}">${organisation.organisationName}</form:option>
													</c:forEach>
												</form:select>
												<form:errors path="organisationId" cssClass="error" />
											</div>
										</div>
									</div>
								</div>
								<div class="row  ">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
										<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">


											<label class="label-class"><spring:message
													code="group.groupName" />:<span style="color: red;">*</span></label>
										</div>

										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
												<i class="fa fa-user icon"></i>
												<form:input class="input-field"
													path="groupName" />
												<form:errors path="groupName" cssClass="error" />
											</div>
										</div>
									</div>
								</div>

								<div class="row  ">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
										<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
											<label class="label-class"><spring:message
													code="master.shortname" />: </label>
										</div>
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">

												<i class="fa fa-th-large icon"></i>
												<form:input class="input-field" 
													path="groupShortName" />
												<form:errors path="groupShortName" cssClass="error" />
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
										<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
											<label class="label-class"><spring:message
													code="group.groupAddress" />: </label>
										</div>

										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
												<i class="fa fa-file-text-o icon "></i>
												<form:textarea class="form-control" rows="2"
													path="groupAddress"></form:textarea>
												<form:errors path="groupAddress" cssClass="error" />
											</div>
										</div>


									</div>
								</div>
								<div class="row">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
										<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
											<label class="label-class"><spring:message
													code="master.contact" />: </label>
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
											<div class="row">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
										<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
											<label class="label-class"><spring:message
													code="group.groupColor" />: </label>
										</div>
										
										
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
												<i class="fa fa-paint-brush icon"></i>
												<form:input class="input-field" path="bgColor"
												 />
												<form:errors path="bgColor" cssClass="error" />
											</div>
										</div>
									</div>
									</div>
									
									<div class="row">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
										<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
											<label class="label-class"><spring:message
													code="group.groupcode" />: </label>
										</div>
										
										
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
												<i class="fa fa-paint-brush icon"></i>
												<form:input class="input-field" path="strCode"
												 />
												<form:errors path="strCode" cssClass="error" />
											</div>
										</div>
									</div>
									</div>
									<%----- Add Radio buttons for proposal year check  [12-04-2024]----%>
									<div class="row p-2">
										<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
											<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
												<label class="label-class">Proposal Year Check ? </label>
											</div>
											<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
												<div class="input-container">
													<div class="col-md-2 col-lg-2 col-sm-4 col-xs-12 ">
														<form:radiobutton path="proposalYearCheck"  value="true" id="proposalToggle-on"	 />
														<form:label path="proposalYearCheck" for="proposalToggle-on"
															class="btn inline zero round no-pad">
															<span>Yes</span>
														</form:label>
													</div>
													<div class="col-md-2 col-lg-2 col-sm-4 col-xs-12 ">
														<form:radiobutton path="proposalYearCheck" value="false" id="proposalToggle-off" />
														<form:label path="proposalYearCheck" for="proposalToggle-off"
															 class="btn round inline zero no-pad">
															<span>No</span>
														</form:label>
													</div>											
												</div>	
											</div>
										</div>
									</div>
									<%----- End of Add Radio buttons for proposal year check  [12-04-2024]----%>
									
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
			<!-- </div> -->
			<!--End of panel-->
			<!--Start data table-->

			<div class="container">
				<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
					<div class=" datatable_row pad-bottom">
						<fieldset class="fieldset-border">

							<legend class="bold legend_details">Details :</legend>
							<!-- 	<div class="row">
							<div class="pull-right">
								<div class="col-md-4">
									<input type="submit" value="Delete" onclick="" id="delete"
										class="btn btn-primary a-btn-slide-text">
								</div>
							</div>
						</div> -->
							<table id="example" class="table table-striped table-bordered"
								style="width: 100%">
								<thead class="datatable_thead bold ">
									<tr>
										<th class="width20 hidden">Select</th>
										<th class="width20">S.No.</th>
										<th>Organisation Name</th>
										<th>Organisation Address</th>
										<th>Group Name</th>
										<th>Short Code</th>
										<th>Address</th>
										<th>Contact Number</th>
										<th>Status</th>
										<th>Group Color</th>
										<th>Group Code</th>
										<th>Proposal Year Check</th>
										<th>Edit</th>
									</tr>
								</thead>
								<tbody class="">
									<c:forEach items="${data}" var="groupModel" varStatus="loop">
										<tr>
											<td class="text-center">${loop.count}</td>
											<td class="hidden"><input type="checkbox" value="${groupModel.numId}"
												/>${groupModel.numId}</td>
											<td>${groupModel.organisationName}
												(${groupModel.organisationId})</td>
											<td>${groupModel.organisationAddress}</td>
											<td>${groupModel.groupName}</td>
											<td>${groupModel.groupShortName}</td>
											<td>${groupModel.groupAddress}</td>
											<td>${groupModel.contactNumber}</td>

											<c:choose>
												<c:when test="${groupModel.valid}">
													<td>Active</td>
												</c:when>
												<c:otherwise>
													<td>Inactive</td>
												</c:otherwise>
											</c:choose>
											<td>${groupModel.bgColor}</td>
											<td>${groupModel.strCode}</td>
											<%----- Get Column Value of proposal year check  [12-04-2024]----%>
											<c:choose>
												<c:when test="${groupModel.proposalYearCheck}">
													<td>Yes</td>
												</c:when>
												<c:otherwise>
													<td>No</td>
												</c:otherwise>
											</c:choose>
											<%----- Get Column Value of proposal year check  [12-04-2024]----%>
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
		src="/PMS/resources/app_srv/PMS/master/js/groupMaster.js"></script>

</body>
</html>