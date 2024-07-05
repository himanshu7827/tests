<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"
	href="/PMS/resources/app_srv/PMS/global/css/new/icon_font.css" />
<link rel="stylesheet" type="text/css"
	href="/PMS/resources/app_srv/PMS/global/css/new/jquery.transfer.css" />

<style>
		body {
	padding: 10px;
}

#StaffList {
  height: 350px;
  m
  argin-bottom: 10px;
}
#PresenterList,
#ContactList,
#FacilitatorList {
  height: 95px;
  margin-bottom: 10px;
}

.style-select select {
  padding: 0;
}

.style-select select option {
  padding: 4px 10px 4px 10px;
}

.style-select select option:hover {
  background: #EEEEEE;
}

.add-btns {
  padding: 0;
}

.add-btns input {
  margin-top: 25px;
  width: 100%;
}

.selected-left {
  float: left;
  width: 88%;
}

.selected-right {
  float: left;
}

.selected-right button {
  display: block;
  margin-left: 4px;
  margin-bottom: 2px;
}

@media (max-width: 517px) {
  .selected-right button {
    display: inline;
    margin-bottom: 5px;
  }
}

.subject-info-box-1,
.subject-info-box-2 {
  float: left;
  width: 45%;
}

.subject-info-box-1 select,
.subject-info-box-2 select {
  height: 200px;
  padding: 0;
}

.subject-info-box-1 select option,
.subject-info-box-2 select option {
  padding: 4px 10px 4px 10px;
}

.subject-info-box-1 select option:hover,
.subject-info-box-2 select option:hover {
  background: #EEEEEE;
}

.subject-info-arrows {
  float: left;
  width: 10%;
}

.subject-info-arrows input {
  width: 70%;
  margin-bottom: 5px;
}
		
	
	</style>

</head>
<body>
	<section id="main-content" class="main-content merge-left">
	<div class=" container wrapper1">
		<div class="row">
			<div class=" col-md-12 pad-top-double  text-left">
				<ol class="breadcrumb bold">
					<li>Home</li>

					<li class="active"><spring:message
											code="master.privilege" /></li>
				</ol>
			</div>
		</div>
		<div class="row padded"></div>
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
			<sec:authorize access="hasAuthority('WRITE_PRIVILEGE_MST')">	
		<form:form id="form1" modelAttribute="privilegeModel" action="/PMS/saveUpdatePrivilegeMaster" method="post">
			<form:hidden path="privlegeId"/>
	<div class="container ">
		<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
				<div class="panel panel-info panel-info1">
					<div class="panel-heading panel-heading-fd"><h4 class="text-center ">Privilege Master</h4></div>
					<div class="panel-body text-center">
			
				<div class="row pad-top ">
								<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">

								<label class="label-class"><spring:message code="Privelege_Master.privilegeName"/>:<span
									style="color: red;">*</span></label>
							</div>

							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
									<i class="fa fa-user icon"></i> 
									<form:input class="input-field" path="privlegeName"/>
									<form:errors path="privlegeName" cssClass="error" />
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
						
						
						<div class="container">
	<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
	<div class=" datatable_row pad-bottom">
		<fieldset class="fieldset-border">

			<legend class="bold legend_details">Details :</legend>
			<sec:authorize access="hasAuthority('WRITE_PRIVILEGE_MST')">
			</sec:authorize>
			
			
			
			<table id="example" class="table table-striped table-bordered"
				style="width: 100%">
				<thead class="datatable_thead bold ">
					<tr>
						<th class="width20 check">Select</th>
						<th>Privilege Name</th>	
						<th>Edit</th>
					</tr>
				</thead>
				<tbody class="">
					<c:forEach items="${privilegeList}" var="priv">
						<tr>
							<td><input type="checkbox"  path="checkbox" class="CheckBox" id="Checkbox" value="${priv.id}" autocomplete="off">${priv.id}</td>
						<td>${priv.name }</td>
								<td>
							<sec:authorize access="hasAuthority('WRITE_PRIVILEGE_MST')">
								<span class="fa fa-pencil-square-o btn btn-primary a-btn-slide-text" id="edit" aria-hidden="true"></span>
								</sec:authorize>
							</td>
						</tr>
						</c:forEach>
						</tbody>
						</table>
						
			</fieldset></div></div></div>
						</div>
						</section></body>


	<script type="text/javascript" src="/PMS/resources/app_srv/PMS/master/js/Privilege.js"></script>

</html>