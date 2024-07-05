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
											code="menu.employee.Role.Privilege.label" /></li>
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
			<sec:authorize access="hasAuthority('WRITE_ROLEPRIVILEGE_MST')">	 
		<form:form id="form_role_privilege" modelAttribute="rolePrivilegeModel" action="/PMS/saveRolePrivilege"
			method="post">				
			<form:hidden path="selectedPrivilege"/>
			<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
				<div class="panel panel-info panel-info1">
					<div class="panel-heading">
						<h4 class="text-center "><spring:message
											code="menu.employee.Role.Privilege.label" /></h4>
					</div>
					<div class="panel-body">
						<div class="row pad-top" id="">

							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group">
								<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12">
									<label class="label-class"><spring:message
											code="rolePrivilege.role" />:<span style="color: red;">*</span></label>
								</div>
								<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
									<div class="input-container">
										<form:select id="roleId" path="roleId" class="select2Option">
											<form:option value="0">Select Role</form:option>
											<c:forEach items="${roleList}" var="role">
												<form:option value="${role.roleId}">
													<c:out value="${role.roleName}"></c:out>
												</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>
							</div>

						</div>
						<div class="row pad-top ">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group">
								<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12">

									<label class="label-class"><spring:message
											code="rolePrivilege.roleName.label" /> :<span
										style="color: red;">*</span></label>
								</div>

								<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
									<div class="input-container">
										<i class="fa fa-user icon"></i>
										<form:input type="text" class="input-field" id="roleName"
											placeholder="Role Name" path="roleName" />
										<form:errors path="roleName" cssClass="error" />
									</div>
								</div>
								<!-- End short code -->
							</div>
						</div>
				
						
						<div class="row style-select">
			<div class="col-md-12">
				<div class="subject-info-box-1">
					<label>Available Privileges</label>
					<select multiple class="form-control" id="lstBox1">
						<c:forEach items="${privilegeList}" var="privilege" >
								<option value="${privilege.id}">
								<c:out value="${privilege.name}"></c:out></option>
							</c:forEach>
						
					</select>
				</div>

				<div class="subject-info-arrows text-center">		
				<br/>
				<br/>			
					<input type='button' id='btnAllRight' value='>>' class="btn btn-default"/>
					<input type='button' id='btnRight' value='>' class="btn btn-default"/>
					<input type='button' id='btnLeft' value='<' class="btn btn-default"/>
					<input type='button' id='btnAllLeft' value='<<' class="btn btn-default"/>
				</div>

				<div class="subject-info-box-2">
					<label>Selected Privileges</label>
					<select multiple class="form-control" name="lstBox2" id="lstBox2">
						
					</select>
				</div>

				<div class="clearfix"></div>
			</div>
		</div>


						<div class="row pad-top center  form_btn">

										<button type="button" class="btn btn-primary font_14"
											id="save">
											<span class="pad-right"><i class="fa fa-folder"></i></span>Save
										</button>
									<!-- 	<button type="button" class="btn btn-primary reset font_14"
											id="reset">
											<span class="pad-right"><i class="fa fa-refresh"></i></span>Reset
										</button> -->
									</div>
					</div>
				</div>
			</div>
		</form:form>
		</sec:authorize> 
		<!-- </div> -->
		<!--End of panel-->
	</div>
	</section>
	<!-- <script type="text/javascript" src="/PMS/resources/app_srv/PMS/global/js/new/jquery.transfer.js"></script>	 -->
	<script type="text/javascript" src="/PMS/resources/app_srv/PMS/global/js/new/jquery.selectlistactions.js"></script>  
	<script type="text/javascript" src="/PMS/resources/app_srv/PMS/master/js/rolePrivilege.js"></script>

</body>



</html>