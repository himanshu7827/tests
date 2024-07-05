
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
			
			<li class="active">Designations for Client</li>
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
		
			<%-- <sec:authorize access="hasAuthority('WRITE_DESIGNATION_MST')">	 --%>
		<form:form id="form1" modelAttribute="designationForClientModel" action="saveDesignationForClient" method="post">
			<form:hidden path="numId"/>
			<form:hidden path="valid" value="true"/>
	<div class="container ">
		<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
				<div class="panel panel-info panel-info1">
					<div class="panel-heading panel-heading-fd"><h4 class="text-center ">Designation For Client</h4></div>
					<div class="panel-body text-center">
			
				<div class="row pad-top ">
								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">

								<label class="label-class"><spring:message code="Designation_Master.designationName"/>:<span
									style="color: red;">*</span></label>
							</div>

							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
									<i class="fa fa-user icon"></i> 
									<form:input class="input-field" path="designationName"/>
									<form:errors path="designationName" cssClass="error" />
								</div>
							</div>
						</div>
						</div>
								<div class="row ">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
								<label class="label-class" ><spring:message code="Designation_Master.designationDescription"/>: </label>
							</div>
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
										<i class="fa fa-file-text-o icon "></i> 
									<form:textarea class="input-field" rows="2" path="desription"></form:textarea>
									<form:errors path="desription" cssClass="error" />
								</div>
							</div>
						</div>
					</div>
						
						<div class="row pad-top ">
					<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
								<label class="label-class" ><spring:message code="Designation_Master.designationShortCode"/>: </label>
							</div>
						<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
									<i class="fa fa-th-large icon"></i>
									<form:input class="input-field"  path="designationShortCode"/>
									<form:errors path="designationShortCode" cssClass="error" />
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
	<%-- </sec:authorize> --%>
	
<div class="container">
	<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
	<div class=" datatable_row pad-bottom">
		<fieldset class="fieldset-border">

			<legend class="bold legend_details">Details :</legend>	
			

			<table id="example" class="table table-striped table-bordered display select dataTable"
				style="width: 100%">
				
				<thead class="datatable_thead bold ">				 
					<tr id="row">						
						<th class="hidden" ></th>
						<th>Designation Name</th>	
						<th>Designation Short Code</th>
						<th>Designation Description</th>					
						<th>Edit</th>
					</tr>
				</thead>
				<tbody class="ui-sortable">
					<c:forEach items="${data}" var="degModel" varStatus="loop">
						<tr id="row-${loop.index}">						
							<td class="hidden">${degModel.numId}</td>
							<td>${degModel.designationName}</td>
						    <td>${degModel.designationShortCode}</td>
							<td>${degModel.desription}</td>
							
							<td>
								<%-- <sec:authorize access="hasAuthority('WRITE_DESIGNATION_MST')"> --%>
									<span class="fa fa-pencil-square-o btn btn-primary a-btn-slide-text" id="edit" aria-hidden="true"></span>
								<%-- </sec:authorize> --%>
							</td>

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

<script type="text/javascript" src="/PMS/resources/app_srv/PMS/transaction/js/designationForClient.js"></script>
	 
	
</body>
</html>