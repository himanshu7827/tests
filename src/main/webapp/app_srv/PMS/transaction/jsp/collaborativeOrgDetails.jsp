<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en">
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">


<style>
.oddRow {
	background-color: #c2c3ea;
}

</style>

<script>
var previousOrg = '${outputArray}';
</script>

</head>

<body>


	<section id="main-content" class="main-content merge-left">

		<div class=" container wrapper1">
		<div class="row">		
		<jsp:include page="/app_srv/PMS/global/jsp/ProcessFlow.jsp" >
<jsp:param name="moduleTypeId" value="1"/>
<jsp:param name="applicationId" value="${applicationId}"/>
</jsp:include>

</div>
			<div class="row">
				<div class=" col-md-12 pad-top-double  text-left">
					<ol class="breadcrumb bold">
						<li>Home</li>
						<!-- <li>Consumer Forms For Medical Devices</li> -->
						<li class="active"><spring:message
								code="form.application.collaborative.dtl" /></li>
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


			<form:form id="form1" modelAttribute="collaborativeOrgDetailsModel"
				action="" method="post">
			
 						<div class="container">
 						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
						<div class="panel panel-info panel-info1">
							
							<div class="panel-body text-center">
							
						

							<div class="row" id="collaborativeOrgDtlDiv">
									<div class="panel-heading panel-heading-fd">
										<h4 class="text-center ">
											<spring:message code="form.application.collaborative.dtl" />
										</h4>
									</div>
									<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group">
										<div align='right' class="pad-top pad-bottom">
											<button type='button' class="btn btn-primary" id="addRow">
												Add More Details <i	class="fa fa-plus-circle btn btn-primary"></i>
											</button>
										</div>
										<form:hidden path="applicationId" value="${applicationId}"/>
										<table id="collaborativeOrgDtlTbl"
											class="table table-striped table-bordered" width="40%">
											<thead>
												<tr>
													<th><spring:message code="group.organisationName"/></th>
													<th><spring:message code="master.contact"/></th>
													<th><spring:message code="application.email.label"/></th>
													<th><spring:message code="application.website.label"/></th>
													<th><spring:message code="group.groupAddress"/></th>
													<th><spring:message code="forms.action"></spring:message></th>
												</tr>
											</thead>


											<tbody>
											
										
											</tbody>
										</table>
									</div>

								</div>


							

								<div class="row pad-top  form_btn">
									<button type="button" class="btn btn-primary font_14" id="save">
										<span class="pad-right"><i class="fa fa-folder"></i></span>Save
									</button>
								</div>
								
														<br>
								<button type="button" class="btn btn-info reset font_14 " id="prev" onclick="goprev()" style="float: left;">
									<span class="pad-right"><i class="fa fa-arrow-left"></i></span>Prev
									</button> 
									
										<br>									
									<button type="button" class="btn btn-info reset font_14 " id="next" onclick="gonext()" style="float: right;">
									<span class="pad-right"><i class="fa fa-arrow-right"></i></span>Next
									</button>
							</div>
						</div>
					</div>
				</div>
				
			
			</form:form>

		</div>
	</section>
	<script type="text/javascript"
		src="/PMS/resources/app_srv/PMS/transaction/js/collaborativeOrgDetails.js"></script>
</body>


</html>