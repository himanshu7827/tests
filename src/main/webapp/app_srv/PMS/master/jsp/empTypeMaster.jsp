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
			<li class="active">Employee Type Master</li>
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
		
		<sec:authorize access="hasAuthority('WRITE_EMP_TYPE_MST')">
		<form:form id="form1" modelAttribute="empTypeMasterModel" action="/PMS/mst/saveUpdateEmpTypeMaster" method="post">
			<form:hidden path="numId"/>
			<form:hidden path="idCheck"/>
	<div class="container ">
		<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12">
				<div class="panel panel-info panel-info1">
					<div class="panel-heading panel-heading-fd"><h4 class="text-center ">Employee Type Master</h4></div>
					<div class="panel-body text-center">
			
				<div class="row pad-top ">
							<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">


								<label class="label-class"><spring:message code="Employee_Type_Master.employeeTypeName"/>:<span
									style="color: red;">*</span></label>
							</div>

							 <div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
									<i class="fa fa-user icon"></i> 
									<form:input class="input-field"  path="strEmpTypeName"/>
									<form:errors path="strEmpTypeName" cssClass="error" />
								</div>
							</div>
						</div>
						</div>
					<div class="row ">
						<div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
							<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
								<label class="label-class" ><spring:message code="Employee_Type_Master.employeeShortName"/>: </label>
							</div>
							<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
								<div class="input-container">
									<i class="fa fa-th-large icon"></i> 
									<form:input class="input-field"  path="empShortName"/>
									<form:errors path="empShortName" cssClass="error" />
								</div>
							</div>
						</div>
					</div>
					
					<div class="row">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
										<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
											<label class="label-class"><spring:message
													code="employee.empType.color" />:<span style="color: red;">*</span> </label>
										</div>
										
										
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
												<i class="fa fa-paint-brush icon"></i>
												<form:input class="input-field" path="bgColor"
												 />
												<form:errors path="bgColor" cssClass="error" />
											</div>
										</div>
									</div>
									</div>
									<div class="row">
									<div
										class="col-md-12 col-lg-12 col-sm-12 col-xs-12 form-group col-md-push-3 col-sm-push-2">
										<div class="col-md-2 col-lg-2 col-sm-6 col-xs-12 text-justify">
											<label class="label-class"><spring:message
													code="employee.empType.hierarchy" />:<span style="color: red;">*</span> </label>
										</div>
										
										
									<div class="col-md-6 col-lg-6 col-sm-6 col-xs-12">
											<div class="input-container">
												<i class="fa fa-list-alt icon"></i>
												<form:input class="input-field" path="hierarchy"
												 />
												<form:errors path="hierarchy" cssClass="error" />
											</div>
										</div>
									</div>
									</div>
					

					
					
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
			<sec:authorize access="hasAuthority('WRITE_EMP_TYPE_MST')">
			<!-- <div class="row">
				<div class="pull-right">
					 <div class="col-md-4">				 
						<input type= "button" value="Delete"   id="delete" class="btn btn-primary a-btn-slide-text" >
					</div>
				</div>
			</div> -->
			</sec:authorize>
			
			<script>

	   $(document).ready(function() {
			
			
			 $('#userinfo-message').delay(5000).fadeOut();
			$('#delete').click(function(){
				EmpTypeDelete();
			});
		});
	  
	  		 
	  function EmpTypeDelete(){
    var chkArray = [];
     
		     
		    $(".CheckBox:checked").each(function() {
		        chkArray.push($(this).val());
		    });
		    
		    var selected;
		    selected = chkArray.join(','); 
		    if(selected.length >= 1){
		    	
		    	
		    	 swal({
				      title: "Are you sure you want to delete the record?",
				      icon: "warning",
				      buttons: [
				                'No',
				                'Yes'
				              ],
				      dangerMode: true,
				    }).then(function(isConfirm) {
				      if (isConfirm) {
					    	  $("#idCheck").val(selected);
					    	 
					  		  submit_form_delete();
					      }
			  	
					      else{
					    	
					      return false;
			    }
		    });
		    }
		    else{
		        swal("Please select at least one checkbox to delete");  
		    }
		    
	  } 
function submit_form_delete()
{

 document.getElementById("form1").action = "/PMS/mst/deleteEmpType";
document.getElementById("form1").method = "POST";
document.getElementById("form1").submit(); 
} 
 


</script>

			<table id="example" class="table table-striped table-bordered"
				style="width: 100%">
				<thead class="datatable_thead bold ">
					<tr>
						<th class="width20 check">Select</th>
						<th><spring:message code="Employee_Type_Master.employeeTypeName" /></th>	
						<th><spring:message code="Employee_Type_Master.employeeShortName" /></th>	
						<th><spring:message code="employee.empType.color" /></th>
						<th><spring:message code="employee.empType.hierarchy" /></th>				
						<th><spring:message code="forms.status" /></th>
						<th>Edit</th>
					</tr>
				</thead>
				<tbody class="">
					<c:forEach items="${data}" var="empTypeModel">
						<tr>
						
						
							<td><input type="checkbox"  path="checkbox" class="CheckBox" id="Checkbox" value="${empTypeModel.numId}" autocomplete="off">${empTypeModel.numId}</td>
							<td>${empTypeModel.strEmpTypeName}</td>
						
							<td>${empTypeModel.empShortName}</td>
							<td>${empTypeModel.bgColor}</td>
							<td>${empTypeModel.hierarchy}</td>
							
							
							<c:choose>
								<c:when test="${empTypeModel.valid}">
									<td>Active</td>
								</c:when>
								<c:otherwise>
									<td>Inactive</td>
								</c:otherwise>
							</c:choose>
							
							<td>
							<sec:authorize access="hasAuthority('WRITE_EMP_TYPE_MST')">
								<span class="fa fa-pencil-square-o btn btn-primary a-btn-slide-text" id="edit" aria-hidden="true"></span>
								</sec:authorize>
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
	<script type="text/javascript" src="/PMS/resources/app_srv/PMS/master/js/empTypeMaster.js"></script>
	
</body>
</html>