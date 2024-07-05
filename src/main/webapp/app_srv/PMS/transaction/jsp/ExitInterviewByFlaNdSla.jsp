<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
			<li class="active"><spring:message code="master.interview.exit"/></li>
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
		
	
			
		<sec:authorize access="hasAuthority('WRITE_ExitInterviewByFlaNdSla_MST')">	
 		
		<form:form id="form1" name="form1" modelAttribute="exitInterviewModel"  method="post">
			<form:hidden path="numIds"/>
			<form:hidden path="numId"/>
			<div class="container ">
		<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
				<div class="panel panel-info panel-info1">
					<div class="panel-heading panel-heading-fd"><h4 class="text-center "><spring:message code="master.interview.exit"/></h4></div>
					<div class="panel-body text-center">
			
									
 										 
	<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
	<div class=" datatable_row pad-bottom">
<!-- 		<fieldset class="fieldset-border">
 -->		
		
						

<!-- 			<legend class="bold legend_details">Details :</legend>
 -->					
			<table id="datatable" class="table table-striped table-bordered"
				style="width: 100%">
				<thead class="datatable_thead bold ">
					<tr>
						<th style="width:10%;"><spring:message code="serial.no"/></th>
						<th ><spring:message code="employee.corporateEmployeeId"/></th>
						<th ><spring:message code="employee_Role.employee_Name"/></th>
						<th ><spring:message code="emp.remarks"/></th>
						<th style="width:20%;">Approve/Reject</th>
						
						
						
					</tr>
				</thead>
				<tbody class="">
					<c:forEach items="${data}" var="exitInterviewData" varStatus="theCount">
						<tr>
							<td>${theCount.count}</td>
							<td>${exitInterviewData.empId}</td>
							<td>${exitInterviewData.empName}</td>
							<td>${exitInterviewData.employeeRemarks}</td>
							<td>
							<span class="fa  btn btn-primary a-btn-slide-text" id="accept" aria-hidden="true" data-toggle="modal" data-target="#exitByFla" data-whatever='${exitInterviewData.numId}@#1@#1'>Accept</span>
							&nbsp;&nbsp;&nbsp;&nbsp;<span class="fa  btn btn-primary a-btn-slide-text" id="reject" aria-hidden="true" data-toggle="modal" data-target="#exitByFla" data-whatever='${exitInterviewData.numId}@#1@#2' >Reject</span>
							
							</td>
							
						</tr>
					</c:forEach>
				</tbody>
			</table>
			
			
			<table id="datatable1" class="table table-striped table-bordered"
				style="width: 100%">
				<thead class="datatable_thead bold ">
					<tr>
						<th style="width:10%;"><spring:message code="serial.no"/></th>
						<th ><spring:message code="employee.corporateEmployeeId"/></th>
						<th ><spring:message code="employee_Role.employee_Name"/></th>
						<th ><spring:message code="emp.remarks"/></th>
						<th ><spring:message code="fla.remarks"/></th>						
						<th style="width:20%;">Approve/Reject</th>
						
						
						
					</tr>
				</thead>
				<tbody class="">
					<c:forEach items="${data1}" var="exitInterviewData" varStatus="theCount">
						<tr>
							<td>${theCount.count}</td>
							<td>${exitInterviewData.empId}</td>
							<td>${exitInterviewData.empName}</td>
							<td>${exitInterviewData.employeeRemarks}</td>
							<td>${exitInterviewData.flaRemarks}</td>
							<td>
							<span class="fa  btn btn-primary a-btn-slide-text" id="accept" aria-hidden="true" data-toggle="modal" data-target="#exitByFla" data-whatever='${exitInterviewData.numId}@#2@#1' >Accept</span>
							&nbsp;&nbsp;&nbsp;&nbsp;<span class="fa  btn btn-primary a-btn-slide-text" id="reject" aria-hidden="true" data-toggle="modal" data-target="#exitByFla" data-whatever='${exitInterviewData.numId}@#2@#2'>Reject</span>
							
							</td>
							
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<!--End of data table-->
		<!-- </fieldset> -->
	</div>
	</div>
	
					</div> 
					</div>
					
					</div>
					</div>
		</form:form>
		 		</sec:authorize>
 		
	
		
</div>
</section>

<script type="text/javascript" src="/PMS/resources/app_srv/PMS/transaction/js/ExitInterviewByFlaNdSla.js"></script>

<!-- Modal For Accept Exit Interview -->
	<div class="modal amount_receive_deatil_modal" id="exitByFla"
		tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
		aria-hidden="true" data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title  center" id="exampleModalLabel">Approve</h4>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				
				<div class="modal-body">

					<form:form id="form" class="exitInterviewModel" name="form" modelAttribute="exitInterviewModel" action="/PMS/mst/saveFlaNdSlaRemarks" method="post">
						<input type="hidden" id="exitInterviewId" name='exitInterviewId'/>
						<input type="hidden" id="approvalId" name='approvalId'/>	
						<input type="hidden" id="statusValue" name='statusValue'/>	
							
				 <div class="form-group">
                        <h4>Remarks:</h4>    
                        <div class="col-md-12 col-xs-12 col-lg-12 col-sm-12 pad-top">              
                     <form:textarea class="form-control " 
									path="remarks" rows="4" placeholder="Enter your Remarks"></form:textarea>
									<form:errors path="remarks" cssClass="error" /> 
									
				  </div>
				  </div>
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
</html>