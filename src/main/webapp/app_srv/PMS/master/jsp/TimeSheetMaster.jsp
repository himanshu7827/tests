<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en" xml:lang="en">
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<style>
	.description-column{
	max-width:200px;
	word-wrap:break-word;
	}
</style>
</head>
<body>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en">
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>SubtaskDescription</title>

</head>
<body>

	<section id="main-content" class="main-content merge-left">

		<div class=" container wrapper1">
			<div class="row">
				<div class=" col-md-12 pad-top-double  text-left">
					<ol class="breadcrumb bold">
						<li>Home</li>
						<!-- <li>Consumer Forms For Medical Devices</li> -->
						<li class="active">Sub Task Description Master</li>
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


			<form:form id="form1" modelAttribute="timesheetMasterModel"
				action="/PMS/mst/saveUpdateDescMaster" method="post">
				<form:hidden path="descId" />
				<input type="hidden" id="subtaskId_hidden" value="0"/>
				<div class="container ">
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
						<div class="panel panel-info panel-info1">
							<div class="panel-heading panel-heading-fd">
								<h4 class="text-center ">Sub Task Description Master</h4>
							</div>
							
							<!-- For Task Field by Himanshu on 10-05-2024 -->
							<div class="panel-body text-center">

								<div class="row pad-top ">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
										<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">

											<label class="label-class"><spring:message
													code="timesheet.taskName" /> :<span style="color: red;">*</span></label>

										</div>
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 subtaskId_hid">
											<div class="input-container">
												<form:select path="taskId" class="select2Option" onChange="getSubTaskBySelectTask()">
													<form:option value="0"> -- Select Task -- </form:option>
													<c:forEach items="${taskList}" var="taskList">
														<form:option value="${taskList.taskId}">${taskList.taskName}</form:option>
													</c:forEach>
												</form:select>
												<form:errors path="taskId" cssClass="error" />
											</div>
										</div>
										
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 hidden taskId_freeze">
											<div class="input-container">
												
												<form:input class="form-control" maxlength="150" rows="2"
													path="taskName"></form:input>
											</div>
										</div>
										
									</div>
								</div>
								<div class="row  ">
									<!-- For Sub Task Field by Himanshu on 10-05-2024 -->
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
										<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">

											<label class="label-class"><spring:message
													code="timesheet.subtaskName" /> :<span style="color: red;">*</span></label>

										</div>
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 subtaskId_hid">
											<div class="input-container">
												<form:select path="subtaskId" class="select2Option">
													<form:option value="0"> -- Select Sub Task -- </form:option>
													<%-- <c:forEach items="${subtaskList}" var="subtaskList">
														<form:option value="${subtaskList.subtaskId}">${subtaskList.subtaskName}</form:option>
													</c:forEach> --%>
												</form:select>
												<form:errors path="subtaskId" cssClass="error" />
											</div>
										</div>
										
										
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 hidden taskId_freeze">
											<div class="input-container">
												<form:input class="form-control" maxlength="150" rows="2"
													path="subtaskName" ></form:input>
											</div>
										</div>
										
										
									</div>
								</div>
								
								<!-- For Description Field by Himanshu on 10-05-2024 -->
								<div class="row">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
										<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
											<label class="label-class"><spring:message
													code="timesheet.subtaskDescription" />:<span style="color: red;">*</label>
										</div>

										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
												<i class="fa fa-file-text-o icon "></i>
												<form:textarea class="form-control" maxlength="150" rows="2"
													path="subtaskDescription"></form:textarea>
												<form:errors path="subtaskDescription" cssClass="error" />
											</div>
										</div>


									</div>
								</div>
								
									<!-- For Status Field by Himanshu on 10-05-2024 -->
									<div class="row ">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
								<label class="label-class"><spring:message
													code="forms.status" /> :</label>
							</div>
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
							<div class="col-md-6 col-lg-2 col-sm-4 col-xs-12 ">
								<form:radiobutton path="valid"  value="true" id="toggle-on"	 />
									<form:label path="valid" for="toggle-on"
										class="btn inline  zero round no-pad">
										<span>Active</span>
									</form:label>
									</div>
									<div class="col-md-6 col-lg-2 col-sm-4 col-xs-12 ">
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
						<button type="button" class="btn btn-primary reset font_14" id="resetMaster">
							<span class="pad-right"><i class="fa fa-refresh"></i></span>Reset
						</button>
						<button type="button" class="btn btn-primary font_14" id="test">
							<span class="pad-right"><i class="fa fa-folder"></i></span>Test
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


			<!-- To print all details by Himanshu on 10-05-2024 -->
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
								<caption class="hidden">All Task Details</caption>
								<thead class="datatable_thead bold ">
									<tr>
										<th>S.No.</th>
										<th class="hidden-column" hidden>Task Id</th>
										<th class="hidden-column" hidden>Sub Task Id</th>
										<th class="hidden-column" hidden>Description Id</th>
										<th>Task Name</th>
										<th>Sub Task Name</th>
										<th>Description</th>
										<th>Status</th>
										
										
										<th>Edit</th>
									</tr>
								</thead>
								<tbody class="">
									<c:forEach items="${data}" var="timesheetModel" varStatus="theCount">
										<tr>
											<td>${theCount.count}</td>
											<td class="hidden-column" hidden>${timesheetModel.taskId}</td>
											<td class="hidden-column" hidden>${timesheetModel.subtaskId}</td>
											<td class="hidden-column" hidden>${timesheetModel.descId}</td>
											
											<td>${timesheetModel.taskName}</td>
											<td>${timesheetModel.subtaskName}</td>
											<td class="description-column">${timesheetModel.subtaskDescription}</td>
											
											<c:choose>
												<c:when test="${timesheetModel.valid}">
													<td>Active</td>
												</c:when>
												<c:otherwise>
													<td>Inactive</td>
												</c:otherwise>
											</c:choose>
						

											

											<td><span
												class="fa fa-pencil-square-o btn btn-primary a-btn-slide-text"
												id="edit_timesheet" aria-hidden="true"></span></td>

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
		src="/PMS/resources/app_srv/PMS/master/js/TimeSheetMaster.js"></script>

	
		

</body>
</html>
</body>
</html>