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
			<li class="active">End User (Client) Master</li>
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
		
		<sec:authorize access="hasAuthority('WRITE_CLIENT_MST')">
		<form:form id="form1" modelAttribute="clientMasterModel" action="/PMS/mst/saveUpdateendUserMaster" method="post">
			<form:hidden path="numId"/>
			<form:hidden path="referrerValue" value="${referrerValue}"/>
			<div class="container ">
		<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
				<div class="panel panel-info panel-info1">
					<div class="panel-heading panel-heading-fd"><h4 class="text-center ">End User (Client) Master</h4></div>
					<div class="panel-body text-center">
			
				<div class="row pad-top ">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">


									<label class="label-class "><spring:message code="master.name"/> :<span
									style="color: red;">*</span></label>
</div>
						 <div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
									<i class="fa fa-user icon"></i> 
									<form:input class="input-field"  path="clientName"/>
									<form:errors path="clientName" cssClass="error" />
								</div>
							</div>
						</div>
						</div>
						
							<div class="row  ">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">

								
									<label class="label-class "><spring:message code="master.contact"/> :<span
									style="color: red;">*</span></label>
							</div>
								 <div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
									<i class="fa fa-phone icon"></i> 
									<form:input class="input-field"  path="contactNumber"/>
									<form:errors path="contactNumber" cssClass="error" />
								</div>
							</div>
						</div>
					</div>

					<div class="row  ">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
								<label class="label-class">Address:<span
									style="color: red;">*</span></label>
							</div>
							 <div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
									<i class="fa fa-address-book icon"></i> 
								<form:textarea class="form-control" rows="2" path="clientAddress"></form:textarea>
								<form:errors path="clientAddress" cssClass="error" />
							</div>
						</div>
						</div>
						</div>
					<!--Short Code  -->
								<div class="row  ">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
								<label class="label-class" ><spring:message code="master.shortCode"/> :</label>
							</div>
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
									<i class="fa fa-th-large icon"></i>
									 <form:input class="input-field"	path="shortCode"/>
									 <form:errors path="shortCode" cssClass="error" />
								</div>
							</div>
							
							
						</div>
						</div>
						<!-- End short code -->
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
						
						<c:if test="${not empty referrerValue}">
							<button type="button" class="btn btn-primary reset font_14" id="backPage">
								<span class="pad-right"><i class="fa fa-arrow-left"></i></span>Back
							</button>
						</c:if>
						
					</div>
					</div>
					</div>
					</div>
					</div>
		</form:form>

</sec:authorize>

	<!--End of panel-->
	<!--Start data table-->
		<div class="container">
	<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
	<div class=" datatable_row pad-bottom">
		<fieldset class="fieldset-border">

			<legend class="bold legend_details">Details :</legend>

			<sec:authorize access="hasAuthority('WRITE_CLIENT_MST')">
			<div class="row">

				<!-- <div class="pull-right">
					 <div class="col-md-4">				 
						<input type= "submit" value="Delete"  onclick=""  id="delete" class="btn btn-primary a-btn-slide-text" >
					</div>
				</div> -->
			</div>
			</sec:authorize>
			
			<table id="example" class="table table-striped table-bordered"
				style="width: 100%">
				<thead class="datatable_thead bold ">
					<tr>
						<th class="width20">Select</th>
						<th>Name</th>
						<th>clientId</th>
						<th>Address</th>
						<th>Contact Number</th>
						<th>Short Code</th>
						<th>Status</th>
						<th>Edit</th>
					</tr>
				</thead>
				<tbody class="">
					<c:forEach items="${data}" var="clientModel">
						<tr>
							<td><input type="checkbox" value="${clientModel.numId}" autocomplete="off">${clientModel.numId}</td>
							<td>${clientModel.clientName}</td>
							<td>${clientModel.encStrId}</td>
							<td>${clientModel.clientAddress}</td>
							<td>${clientModel.contactNumber}</td>
							<td>${clientModel.shortCode}</td>
							
							<c:choose>
								<c:when test="${clientModel.valid}">
									<td>Active</td>
								</c:when>
								<c:otherwise>
									<td>Inactive</td>
								</c:otherwise>
							</c:choose>
							
							<td>
								<span class="fa fa-pencil-square-o btn btn-primary a-btn-slide-text" id="edit" aria-hidden="true"></span>
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

	
	<script type="text/javascript" src="/PMS/resources/app_srv/PMS/master/js/endUserMaster.js"></script>
	
</body>
</html>