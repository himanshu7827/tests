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
			<li class="active"><spring:message code="master.interview.exit.employee"/></li>
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
		
		<c:choose>
		
			<c:when test="${not empty result}">
				<div><p class="success_msg">${result}</p></div> 
			</c:when>
			<c:otherwise>
			
		
		<form:form id="form1" name="form1" modelAttribute="exitInterviewModel" action="/PMS/saveExitInterview" method="post">
			<form:hidden path="numIds"/>
			<form:hidden path="numId"/>
			<div class="container ">
		<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
				<div class="panel panel-info panel-info1">
					<div class="panel-heading panel-heading-fd"><h4 class="text-center "><spring:message code="master.interview.exit.employee"/></h4></div>
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 text-justify">
					<p style="float: left;margin:18px;" ><span class="bold orange font_16">We would like to understand your experience with us.Please take a moment to complete.</span></p>
					</div>
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
						<th style="width:30%;"><spring:message code="master.questions"/></th>
						<th style="width:40%;"><spring:message code="master.answer"/></th>
						
					</tr>
				</thead>
				<tbody class="">
					<c:forEach items="${data}" var="exitInterviewData" varStatus="theCount">
						<tr>
							<td>${theCount.count}</td>
							<td>${exitInterviewData.strQuestions} <input type="hidden" class="questions" id="question_${theCount.index}" value="${exitInterviewData.numId}"/></td>
																			
							<td>
							<c:set var="answerName" value="${fn:split(exitInterviewData.answer, ',')}"></c:set>
							
							<c:set var="answerId" value="${fn:split(exitInterviewData.answerIds, ',')}"></c:set>
							<select class="select2Option answers" id="answer_${theCount.index}"> 
								<option value="0">Select Experience</option>
								<c:forEach var="answer" items="${answerName}" varStatus="loop">
								<option value="${answerId[loop.index]}"> ${answer} </option>
								</c:forEach>
							</select>
							</td>
							
						</tr>
					</c:forEach>
				</tbody>
			</table>
			
			<!--End of data table-->
		<!-- </fieldset> -->
	</div>
	</div>
	
	<div class="row pad-top">
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

								<label class="label-class"><spring:message code="fla.email"/>:<span
									style="color: red;">*</span></label>
							</div>

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container">
									<i class="fa fa-envelope icon"></i> 
									<form:input class="input-field" placeholder="FLA Email" path="flaEmail"  />
									<form:errors path="flaEmail" cssClass="error" />
								</div>
							</div>
						</div>	
						
										
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

								<label class="label-class"><spring:message code="sla.email"/>:<span
									style="color: red;">*</span></label>
							</div>

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container">
									<i class="fa fa-envelope icon"></i> 
									<form:input class="input-field" placeholder="SLA Email" path="slaEmail" />
									<form:errors path="slaEmail" cssClass="error" />
								</div>
							</div>
						</div>
						</div>								
		
		<div class="row pad-top">
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12 form-group">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">

								<label class="label-class"><spring:message code="emp.remarks"/>:</label>
							</div>

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
								<div class="input-container">
									<i class="fa fa-user icon"></i> 
									<form:textarea class="input-field" placeholder="Employee Remarks" path="employeeRemarks"  />
									<form:errors path="employeeRemarks" cssClass="error" />
								</div>
							</div>
						</div>	
						
		</div>					
					<div class="row pad-top  form_btn">
					
						<button type="button" class="btn btn-primary font_14" id="save">
							<span class="pad-right"><i class="fa fa-folder"></i></span>Save
						</button>
						<!-- <button type="button" class="btn btn-primary reset font_14" id="reset">
							<span class="pad-right"><i class="fa fa-refresh"></i></span>Reset
						</button> -->
					</div>
					</div> 
					</div>
					
					</div>
					</div>
		</form:form>
		
		</c:otherwise>
	</c:choose>	
		
</div>
</section>

<script type="text/javascript" src="/PMS/resources/app_srv/PMS/transaction/js/ExitInterviewMaster.js"></script>

		
	
</body>
</html>