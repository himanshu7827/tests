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
			<li class="active">Question Master</li>
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
		
		<sec:authorize access="hasAuthority('READ_QUESTION_MST')">	
		<form:form id="form1" name="form1" modelAttribute="questionMasterModel" action="/PMS/mst/saveQuestionMaster" method="post">
			<form:hidden path="numIds"/>
			<form:hidden path="numId"/>
			<div class="container ">
		<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
				<div class="panel panel-info panel-info1">
					<div class="panel-heading panel-heading-fd"><h4 class="text-center "><spring:message code="master.question.label"/></h4></div>
					<div class="panel-body text-center">
			
				<div class="row pad-top ">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">

								<label class="label-class"><spring:message code="master.questions"/>:<span
									style="color: red;">*</span></label>
							</div>

							 <div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
									<i class="fa fa-film icon"></i> 
									<form:input class="input-field"  path="strQuestions"/>
									<form:errors path="strQuestions" cssClass="error" />
								</div>
							</div>
						</div>
						</div>

					<div class="row pad-top ">
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
					<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
					<label class="label-class"> <spring:message code="master.answer" /> :<span style="color: red;">*</span></label>
					</div>

							 <div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
								<form:errors path="answer" cssClass="error" />
									<div class="input-container">
									<form:select path="answerId" id="answerId" multiple="true" class="select2Option" style="width:100%">
  								<c:forEach items="${data1}" var="answer" >
								<form:option value="${answer.numId}">
								<c:out value="${answer.strAnswer}"></c:out></form:option>
							</c:forEach>
													</form:select>
												</div>
											</div>
										</div>
									</div>
									</div>

					<%-- <div class="row ">
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
					 --%>
					
					
					<div class="row pad-top  form_btn">
								<sec:authorize access="hasAuthority('WRITE_QUESTION_MST')">	
					
						<button type="button" class="btn btn-primary font_14" id="save">
							<span class="pad-right"><i class="fa fa-folder"></i></span>Save
						</button>
						</sec:authorize>
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
						<th><spring:message code="master.name"/></th>
						<th><spring:message code="master.answer"/></th>
						<th class="hidden"></th>						
 						<th><spring:message code="forms.edit"/></th>
					</tr>
				</thead>
				<tbody class="">
					<c:forEach items="${data}" var="questionData">
						<tr>
						
							<td><input type="checkbox"  class="CheckBox" id="CheckBox"  value="${questionData.numId}" />${questionData.numId}</td>
							<td>${questionData.strQuestions}</td>
							<td>${questionData.answer}</td>
							<td class="hidden">${questionData.answerIds}</td>
							
							 <%-- <c:choose>
								<c:when test="${skillData.valid}">
									<td class='hidden'>Active</td>
								</c:when>
								<c:otherwise>
									<td class='hidden'>Inactive</td>
								</c:otherwise>
							</c:choose>  --%>
							<sec:authorize access="hasAuthority('WRITE_QUESTION_MST')">	
							
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

 <script type="text/javascript" src="/PMS/resources/app_srv/PMS/master/js/QuestionMaster.js"></script>
 
	
	
</body>
</html>