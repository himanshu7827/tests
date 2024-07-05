<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

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
			<li class="active">Task Details Master</li>
		</ol>
	</div>
</div>
<div class="row ">
		
		 </div>
		 <c:if test="${message != null && message != ''}">
		 	<c:choose>
		 		<c:when test="${status=='success'}">
		 			<div id="userinfo-message"><p class="success_msg">${message}</p></div> 
		 		</c:when>
		 		<c:otherwise>
		 			<div id="userinfo-message"><p class="error_msg">${message}</p></div> 
		 		</c:otherwise>		 	
		 	</c:choose>       		
        </c:if>	
		
		<sec:authorize access="hasAuthority('WRITE_TASKDETAIL_MST')">	
		<form:form id="form1" name="form1" modelAttribute="taskDetailsModel" action="/PMS/mst/saveTaskDetailsMaster" method="post"
						enctype="multipart/form-data">
		
			<form:hidden path="numIds"/>
			<form:hidden path="numId"/>
			<form:hidden path="activityRadioValue"/>
			<div class="container ">
		<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
				<div class="panel panel-info panel-info1">
					<div class="panel-heading panel-heading-fd"><h4 class="text-center ">Task Details Master</h4></div>
					<div class="panel-body text-center">
			
				<div class="row pad-top ">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">

								<label class="label-class"><spring:message code="task.name"/>:<span
									style="color: red;">*</span></label>
							</div>

							 <div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
									<i class="fa fa-film icon"></i> 
									<form:input class="input-field"  path="taskName"/>
									<form:errors path="taskName" cssClass="error" />
								</div>
							</div>
						</div>
						</div>
						
						<div class="row pad-top ">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">

								<label class="label-class"><spring:message code="Project_Module_Master.projectName"/>:<span
									style="color: red;">*</span></label>
							</div>

							 <div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
									<form:select path="projectId"  class="select2Option"  style="width:100%">
				           <form:option value="0">--Select Project--</form:option>
							<c:forEach items="${projectData}" var="projectData" >
								<form:option value="${projectData.numId}">
								${projectData.strProjectName}
								</form:option>
							</c:forEach>
				                   </form:select>
				                 <form:errors path="projectId" cssClass="error" />
				                 </div>
							</div>
						</div>
						</div>
						
						
						<div class="row pad-top ">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">

								<label class="label-class"><spring:message code="task.expectedTime"/>:<span
									style="color: red;">(in hr)*</span></label>
							</div>

							 <div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
									<i class="fa fa-film icon"></i> 
									<form:input class="input-field"  path="expectedTime"/>
									<form:errors path="expectedTime" cssClass="error" />
								</div>
							</div>
						</div>
						</div>
						
						<div class="row pad-top ">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">

								<label class="label-class"><spring:message code="task.priority"/>:<span
									style="color: red;">*</span></label>
							</div>

							 <div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
									<form:select path="priority" class="select2Option">
										<form:option value="0"> --- Select Priority --- </form:option>	
										<form:option value="Urgent">Urgent</form:option>											    
										<form:option value="High">High</form:option>
										<form:option value="Medium">Medium</form:option>
										<form:option value="low">low</form:option>
									</form:select>
									<form:errors path="priority" cssClass="error" />
								</div>
							</div>
						</div>
						</div>
						
						<div class="row pad-top ">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">

								<label class="label-class"><spring:message code="document.description"/>:<span
									style="color: red;">*</span></label>
							</div>

							 <div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
									<i class="fa fa-file-text-o icon "></i>
									<form:textarea class="form-control" 
									path="taskDescription"></form:textarea>
									<form:errors path="taskDescription" cssClass="error" />
								</div>
							</div>
						</div>
						</div>
						
						<div class="row ">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
											<label class="label-class"><spring:message
													code="task.withMilestone" />:<span
												style="color: red;">*</span></label>
										</div>

										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="col-md-2 col-lg-2 col-sm-4 col-xs-12 ">
												<form:radiobutton class="toggle-on" path="withMilestone" value="1" id="toggle-on1" />
												<form:label path="withMilestone" for="toggle-on1"
													class="btn inline  zero round no-pad">
													<span>Yes</span>
												</form:label>
											</div>
											<div class="col-md-2 col-lg-2 col-sm-4 col-xs-12 ">
												<form:radiobutton class="toggle-off" path="withMilestone" value="0" id="toggle-off2" />

												<form:label path="withMilestone" for="toggle-off2"
													class="btn round inline zero no-pad">
													<span class="">No</span>
												</form:label>
											</div>

										</div>
									</div>
								</div>
								
								<div class="row ">

									<div id="milestoneDiv"
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2 form-group hidden">
										<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
											<label class="label-class"><spring:message
													code="Project_Payment_Schedule.milestoneName" /> :<span
												style="color: red;">*</span></label>
										</div>
										<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
												<form:select path="milestoneId"  class="select2Option"  style="width:100%">
				              		<form:option value="0">--Select Milestone--</form:option>
				   			    	<c:forEach items="${milestonelist}" var="milestoneData" >
								<form:option value="${milestoneData.numId}">
								${milestoneData.milestoneName}
								</form:option>
							</c:forEach>
				                   </form:select>
				                 <form:errors path="milestoneId" cssClass="error" />
											</div>
										</div>
									</div>
								</div>
								<table id="milestoneTable"
										class="table table-striped table-bordered hidden" width="80%">
										<thead>
											<tr>
												
												<th><spring:message code="master.select" /></th>
												<th><spring:message code="task.activity" /></th>
												<th><spring:message code="Project_Module_Master.projectModuleName" /></th>
 												
											</tr>
										</thead>

										<tbody>
										</tbody>
									</table>
									<div class="row pad-top ">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">

								<label class="label-class">Select a file to Upload:</label>
							</div>

							 <div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
				<input type="file" class="input-field fileUpload" name="taskDocumentFile" >	

								</div>
							</div>
						</div>
						</div>
											
								<div class="row ">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2 ">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
								<label class="label-class"><spring:message
													code="forms.status" /> :</label>
							</div>
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
							<div class="col-md-2 col-lg-2 col-sm-4 col-xs-12 ">
								<form:radiobutton path="valid"  value="true" class="toggle-on" id="toggle-on"	 />
									<form:label path="valid" for="toggle-on"
										class="btn inline  zero round no-pad">
										<span>Active</span>
									</form:label>
									</div>
									<div class="col-md-2 col-lg-2 col-sm-4 col-xs-12 ">
								<form:radiobutton path="valid" value="false" class="toggle-off" id="toggle-off" />

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
						<th class="width20"><spring:message code="master.select"/></th>
						<th><spring:message code="task.name"/></th>
						<th class="hidden"></th>
						<th><spring:message code="Project_Module_Master.projectName"/></th>
						<th><spring:message code="task.expectedTime"/></th>
						<th><spring:message code="task.priority"/></th>
						<th><spring:message code="master.description"/></th>
						<th class="hidden"></th>
						<th class="hidden"></th>
						<th><spring:message code="Project_Payment_Schedule.milestoneName"/></th>
						<th class="hidden"></th>
						<th><spring:message code="task.activity" /></th>
						<th><spring:message code="task.download" /></th>
					    <th><spring:message code="forms.status"/></th> 
						<th><spring:message code="forms.edit"/></th>
					</tr>
				</thead>
				<tbody class="">
					<c:forEach items="${list}" var="taskdetail">
						<tr>
						
							<td><input type="checkbox"  class="CheckBox" id="CheckBox"  value="${taskdetail.numId}" />${taskdetail.numId}</td>
							<td>${taskdetail.taskName}</td>
							<td class="hidden">${taskdetail.projectId}</td>
							<td>${taskdetail.projectName}</td> 
							<td>${taskdetail.expectedTime}</td>
							<td>${taskdetail.priority}</td>
							<td >${taskdetail.taskDescription}</td>
							<td class="hidden">${taskdetail.withMilestone}</td>
							<td class="hidden">${taskdetail.milestoneId}</td>
							<td>${taskdetail.milestoneName}</td>
							<td>${taskdetail.activity}</td>
							<td class="hidden">${taskdetail.activityRadioValue}</td>
							<td>
								<c:if test="${taskdetail.documentId ne 0}">
										<a onclick='downloadDocument("${taskdetail.encDocumentId}")'> <i class="fa fa-download fa-3x" style="margin-left: 21px;" aria-hidden="true"></i> </a>
								</c:if>								
							</td>
							  
							  <c:choose>
								<c:when test="${taskdetail.valid}">
									<td>Active</td>
								</c:when>
								<c:otherwise>
									<td>Inactive</td>
								</c:otherwise>
							</c:choose>
							
							<sec:authorize access="hasAuthority('WRITE_TASKDETAIL_MST')">	
							
							<td>
								<span class="fa fa-pencil-square-o btn btn-primary a-btn-slide-text" id="edit" aria-hidden="true"></span>
							</td>
                            </sec:authorize>
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

<script type="text/javascript" src="/PMS/resources/app_srv/PMS/master/js/TaskDetails.js"></script>

	
	
</body>
</html>