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
	<c:if test="${projectId>0 }">
<jsp:include page="/app_srv/PMS/global/jsp/ProcessFlow.jsp" >
<jsp:param name="moduleTypeId" value="2"/>
<jsp:param name="applicationId" value="0"/>

</jsp:include>
</c:if>
</div>
<div class="row padded">
	<div class=" col-md-12 pad-top-double  text-left">
					<ol class="breadcrumb bold">
						<li>Home</li>
						<!-- <li>Consumer Forms For Medical Devices</li> -->
						<li class="active">Project Budget Master</li>
					</ol>
				</div>
		
		 </div>
		 <div class="pad-top pad-bottom"><p class="success_msg">Amount is accepted in INR Only. <br/> Click on 'Save Details' to save the budget details </p></div> 
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
		
		
		
		<form:form id="form1" name="form1" modelAttribute="projectbudgethead" action="/PMS/saveProjectBudgetDetailsMaster" method="post">
			<form:errors path="validateError" cssClass="error" />
	
			<input type="hidden" id="projectId" value="${projectId}" />
			<input type="hidden" id="encProjectId" value="${encProjectId}"/>
				<div class="panel panel-info panel-info1">
					<div class="panel-heading"><h3 class="text-center text-shadow"><spring:message code="project.budget.master"/></h3></div>
					<div class="panel-body">
											<c:set var="grandTotal" value="0" />
					
						<table id="docUploadTbl" class="table table-striped table-bordered" width="40%">
						
						<c:forEach items="${dataMap}" var="parentMap" varStatus="loop">
						
							<c:choose>
								<c:when test="${loop.index==0}">
									<tr class="datatable_thead"> 
										<th> ${parentMap.key} </th>
											<c:forEach items="${parentMap.value}" var="childMap">
												<th class="text-right"> ${childMap.key}</th>
											</c:forEach>
										<th class="text-right">Head wise Total (in INR)</th>
									</tr>
								</c:when>
								<c:otherwise>
										<tr class="highlight_tr"> 
										<th> ${parentMap.key} </th>
										<c:set var = "total" value = "0"/>
											<c:forEach items="${parentMap.value}" var="childMap">
												<td> <input id='${childMap.key}' class="input-field text-right" value='${childMap.value}' onblur="saveAmount('${childMap.key}','${parentMap.key}')"/>  </td>
											    <c:set var="total" value="${total + childMap.value}" />
											</c:forEach>
											<th id='${parentMap.key}_Total' class="total text-right vertical-center">${total }</th>
										<c:set var="grandTotal" value="${grandTotal + total}" />
											
											</tr>
								</c:otherwise>
							</c:choose>
						
						</c:forEach>
						
					</table>
					
					<h4><div style="float: right;"> Total Budget  : <span id="grandTotal" class="text-right vertical-center">${grandTotal }</span>(in INR)</div></h4>
						</div>	
							<sec:authorize access="hasAuthority('WRITE_PROJECT_BUDGET_MST')">	
					<div class="row pad-top pad-bottom form_btn center" >
					
						<button type="button" class="btn btn-primary font_14" id="save">
							<span class="pad-right"><i class="fa fa-folder"></i></span>Save Details
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
					<%-- <br>
					<c:if test="${projectId>0 }"> 
									<button type="button" class="btn btn-primary reset font_14 " id="prev" onclick="goprev()" style="background: #3d558e !important;float: left;">
									<span class="pad-right"><i class="fa fa-arrow-left"></i></span>Prev
									</button>
									
										<br>
									<button type="button" class="btn btn-primary reset font_14 " id="next" onclick="gonext()" style="background: #3d558e !important;float: right;">
									<span class="pad-right"><i class="fa fa-arrow-right"></i></span>Next
									</button>
									</c:if> --%>
					</sec:authorize>
					</div>
					
			
		</form:form>
	
</div>
</section>


	<script type="text/javascript" src="/PMS/resources/app_srv/PMS/master/js/projectbudget.js"></script>

</body>
</html>