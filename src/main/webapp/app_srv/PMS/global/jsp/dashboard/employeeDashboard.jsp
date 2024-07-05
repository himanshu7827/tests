<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>
.employee_tasks .panel-primary>.panel-heading {
	color: #fff;
	background-color: #5466c0;
	border-color: #5466c0;
}

.employee_tasks .tab-content {
	min-height: 50%;
}

.employee_tasks .with-nav-tabs.panel-primary .nav-tabs>li>a,
	.employee_tasks .with-nav-tabs.panel-primary .nav-tabs>li>a:hover,
	.employee_tasks .with-nav-tabs.panel-primary .nav-tabs>li>a:focus {
	color: #fff !important;
}

.employee_tasks .with-nav-tabs.panel-primary .nav-tabs>.open>a,
	.employee_tasks .with-nav-tabs.panel-primary .nav-tabs>.open>a:hover,
	.employee_tasks .with-nav-tabs.panel-primary .nav-tabs>.open>a:focus,
	.employee_tasks .with-nav-tabs.panel-primary .nav-tabs>li>a:hover,
	.employee_tasks .with-nav-tabs.panel-primary .nav-tabs>li>a:focus {
	color: #fff !important;
	background-color: #5466c0 !important;
	border-color: transparent !important;
}

.employee_tasks .with-nav-tabs.panel-primary .nav-tabs>li.active>a,
	.employee_tasks .with-nav-tabs.panel-primary .nav-tabs>li.active>a:hover,
	.employee_tasks .with-nav-tabs.panel-primary .nav-tabs>li.active>a:focus
	{
	color: #5466c0 !important;
	background-color: #fff !important;
	border-color: #5466c0 !important;
	border-bottom-color: transparent !important;
}

.employee_tasks .panel-heading {
	padding: 0px;
	border-bottom: 0px;
	border-top-left-radius: 3px;
	border-top-right-radius: 3px;
}

.employee_tasks a {
	font-size: 16px;
	font-weight: 600;
}

.employee_tasks .nav.nav-pills, .nav.nav-tabs {
	margin-bottom: 0px !important;
}

thead.thead-theme {
	background: #aebbff;
}

.pad-top-40 {
	padding-top: 40px !important;
}

.employee_tasks .btn-success {
	color: #fff;
	background-color: #129570;
	border-color: #129570;
}

.employee_tasks .btn-danger {
	color: #fff;
	background-color: #e37873;
	border-color: #e37873;
}

.employee_task_deatil_modal .modal-dialog.modal-lg {
	padding-top: 10%;
}
.employee_task_assign_modal .modal-dialog.modal-lg {
	padding-top: 10%;
}
.font_color_blue{
color:#475dc8;
}
.font_color_pink{
color:#b83364;
}
.font_color_cyan{
color:#33a19c;
}
.font_color_purple{
color:#6c46ba;
}
</style>
<script>
	$(document).ready(function() {
		$('.select2Option').select2({
			width : '100%'
		});
	});
</script>
</head>
<body>
	<section id="main-content" class="main-content merge-left">
	<div class=" container wrapper1">
		<div class="row">
			<div class=" col-md-12 pad-top-double  text-left">
				<ol class="breadcrumb bold">
					<li>Home</li>

					<li class="active">Employee Dashboard</li>
				</ol>

			</div>
		</div>
		<div class="row employee_tasks">

			<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 employee_tasks">
				<div class="panel with-nav-tabs panel-primary">
					<div class="panel-heading">
						<ul class="nav nav-tabs">
							<li class="active"><a href="#activeTasks" data-toggle="tab">Active
									Tasks</a></li>
							<li><a id="AssignedTasks" href="#assignedTasks" data-toggle="tab">Assigned
									Tasks</a></li>
							<li><a href="#ongoingTasks" data-toggle="tab">Ongoing
									Tasks</a></li>
							<li><a href="#completedTasks" data-toggle="tab">Completed
									Tasks</a></li>

						</ul>
					</div>
					<div class="panel-body">
						<div class="tab-content">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 ">
								<button type="button"
									class="btn btn-labeled btn-info pull-right">
									<span class="btn-label"><i
										class="fa fa-refresh pad-right"></i></span>Refresh
								</button>
							</div>
							<div class="tab-pane fade pad-top-40 in active" id="activeTasks">
								<table id="example_active"
									class="table table-striped table-bordered " style="width: 100%">
									<thead class=" thead-theme">
										<tr>

											<th>S.no</th>
											<th>Active Tasks</th>
											<th>Actions</th>
											

										</tr>
									</thead>
									<tbody class="dash_tbody">
					             <c:forEach items="${list}" var="taskdetail" varStatus="theCount">
										<tr>
											<td>${theCount.count}</td>
											<td >
													<a href='#' data-toggle='modal' data-target='#emp' data-whatever='${taskdetail.encTaskId}' class='text-center'>
													${taskdetail.taskName} </a>
													</td>
											<td><span class="btn btn-success btn-md" data-toggle="modal" data-target="#emp1" data-whatever='${taskdetail.encTaskId},${taskdetail.encProjectId}'><i
													class="fa fa-check"></i> Accept</span></td>
											
										</tr>
										
					              </c:forEach>

									</tbody>
								</table>

							</div>
							<!--Assigned Tasks  -->
							<div class="tab-pane fade  pad-top-40 " id="assignedTasks">
							
									<table id="example_ongoing"
									class="table table-striped table-bordered " style="width: 100%">
									<thead class=" thead-theme">
										<tr>

											<th>S.no</th>
											<th>Assigned Tasks</th>
											<th>Task Details</th>
											<th>Actions</th>

										</tr>
									</thead>
									<tbody class="dash_tbody">
									<c:forEach items="${list1}" var="taskassign" varStatus="theCount">
										<tr>
											<td>${theCount.count}</td>
											<td class="font_14">${taskassign.taskName}</td>
											<td><span class="btn btn-primary btn-md"
												data-toggle="modal" data-target="#emp" data-whatever='${taskassign.encTaskId}'><i
													class="fa fa-th-large"></i> Details</span></td>

											<td id="ongoingActivity"><select  class="select2Option">
													<option value="0">--Select--</option>
													<option value="1_${taskassign.encTaskId}">Work In Progress</option>
													<option value="2_${taskassign.encTaskId}">Withdraw</option>
											</select></td>
											</tr>
									
										</c:forEach>
									</tbody>
								</table>
							
							</div>
							<!-- End of Assigned Tasks -->



							<!--Ongoing Tasks  -->
							<div class="tab-pane fade  pad-top-40 " id="ongoingTasks">
								<table id="example_ongoing_task"
									class="table table-striped table-bordered " style="width: 100%">
									<thead class=" thead-theme">
										<tr>

											<th>S.no</th>
											<th>Ongoing Tasks</th>
											<th>Task Details</th>
											<th>Assigned To</th>

										</tr>
									</thead>
									<tbody class="dash_tbody">
									<c:forEach items="${list2}" var="taskongoing" varStatus="theCount">

										<tr>
											<td>${theCount.count}</td>
											<td class="font_14">${taskongoing.taskName}</td>
											<td><span class="btn btn-primary btn-md"
												data-toggle="modal" data-target="#emp" data-whatever='${taskongoing.encTaskId}'><i
													class="fa fa-th-large"></i> Details</span></td>

											<td id="completedActivity"><select class="select2Option">
													<option value="0">--Select--</option>
													<option value="1_${taskongoing.encTaskId}">Completed</option>
													<option value="2_${taskongoing.encTaskId}">Withdraw</option>
													

											</select></td>
										
										</tr>
									</c:forEach>
									</tbody>
								</table>
							</div>
							<!--End Of Ongoing Tasks  -->
							<!--Completed Tasks  -->
							<div class="tab-pane fade  pad-top-40 " id="completedTasks">
								<table id="example_completed"
									class="table table-striped table-bordered " style="width: 100%">
									<thead class=" thead-theme">
										<tr>

											<th>S.no</th>
											<th>Completed Tasks</th>
											<th>Task Details</th>

										</tr>
									</thead>
									<tbody class="dash_tbody">
								<c:forEach items="${list3}" var="taskcompleted" varStatus="theCount">

										<tr>
											<td>${theCount.count}</td>
											<td class="font_14">${taskcompleted.taskName}</td>
											
											<td><span class="btn btn-primary btn-md"
												data-toggle="modal" data-target="#emp" data-whatever='${taskcompleted.encTaskId}'><i
													class="fa fa-th-large"></i> Details</span></td>
										
										</tr>
								</c:forEach>
									</tbody>
								</table>

							</div>
<!-- End Of Completed Tasks -->

						</div>
					</div>
				</div>
			</div>
		</div>

	</div>
	</section>

	<!-- Modal For Tasks -->
	<div class="modal employee_task_deatil_modal" id="emp"
		tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
		aria-hidden="true" data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title  center" id="exampleModalLabel">Details</h4>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<h5 class="pad-top ">Task Name:<span class="font_color_blue" id="taskName"></span></h5>
				<h5  class="pad-top ">Project:<span class="font_color_pink" id="projectName"></span></h5>
				<h5  class="pad-top ">Created By:<span class="font_color_cyan" id="createdBy"></span></h5>
				<h5  class="pad-top ">Expected Time(in hr):<span class="font_color_blue" id="expectedTime"></span></h5>
				<h5  class="pad-top ">Priority:<span class="font_color_pink" id="priority"></span></h5>
				<h5  class="pad-top ">Date of Assigning:<span class="font_color_purple" id="assignDate"></span></h5>
					<h5  class="pad-top bold">The details of the task are as following:<span class="font_color_blue " id="taskDescription"></span></h5>
				</div>
				<div class="modal-footer" id="modelFooter">
					
				</div>
			</div>
		</div>
	</div>

<!-- Modal For Tasks Assigned -->
	<div class="modal employee_task_assign_modal" id="emp1"
		tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
		aria-hidden="true" data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title  center" id="exampleModalLabel">Details</h4>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				
				<div class="modal-body">
<%-- 				 <form role="form" class="taskAssignForm">
 --%>				 <form:form id="form" class="taskAssignForm" name="form" modelAttribute="taskAssignmentModel" action="/PMS/mst/assignedTask" method="post"
						>
						<input type="hidden" id="encTaskId" name='encTaskId'/>
						<!-- <input type="hidden" id="numId" name='numId'/> -->
						<input type='hidden' name='encProjectId' id='encProjectId'/>
						
				 <div class="form-group">
                        <h4>Description:</h4>    
                        <div class="col-md-12 col-xs-12 col-lg-12 col-sm-12 pad-top">              
                    <form:textarea class="form-control " 
									path="description" rows="4" placeholder="Enter your Description"></form:textarea>
									<form:errors path="description" cssClass="error" />
									
				  </div>
				  </div>
					<!-- <h5> Select file : </h5><input type='file' name='taskDocumentFile' id='file' class='form-control' ><br> -->
          	 <div class="col-md-12 col-xs-12 col-lg-12 col-sm-12 pad-top ">  
          	 <div class="pull-right">
						 <button type="submit" class="btn btn-primary" id="submit">Save</button>
						
             			<button type="button" class="btn btn-primary"
						data-dismiss="modal">Close</button>
						</div>
						</div>
                   </form:form>
						
				</div>
			</div>
		</div>
	</div>
	
	

</body>
<script type="text/javascript" src="/PMS/resources/app_srv/PMS/master/js/employeeDashboard.js"></script>
</html>